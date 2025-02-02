/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ghidra.program.model.relocobj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ghidra.app.util.ProgramUtil;
import ghidra.program.model.address.Address;
import ghidra.program.model.address.AddressSetView;
import ghidra.util.DataConverter;

public class RelocationHighPair implements Relocation {
	private final RelocationTable relocationTable;
	private final Address address;
	private final int width;
	private final long bitmask;
	private final Address target;

	private final List<RelocationLowPair> relocations = new ArrayList<>();

	protected RelocationHighPair(RelocationTable relocationTable, Address address, int width,
			long bitmask, Address target) {
		this.relocationTable = relocationTable;
		this.address = address;
		this.width = width;
		this.bitmask = bitmask;
		this.target = target;
	}

	@Override
	public RelocationTable getRelocationTable() {
		return relocationTable;
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public long getBitmask() {
		return bitmask;
	}

	@Override
	public Address getTarget() {
		return target;
	}

	@Override
	public long getAddend() {
		return 0;
	}

	@Override
	public void delete() {
		relocationTable.delete(this);

		synchronized (relocations) {
			for (RelocationLowPair relocation : relocations) {
				relocationTable.delete(relocation);
			}
		}
	}

	protected void unapplyHi(byte[] buffer, AddressSetView addressSet, DataConverter dc) {
		if (!addressSet.contains(address, address.add(width - 1))) {
			throw new IllegalArgumentException("buffer does not contain high pair relocation");
		}

		int offset = (int) ProgramUtil.getOffsetWithinAddressSet(addressSet, address);
		long value = dc.getValue(buffer, offset, width) & ~bitmask;
		dc.putValue(value, width, buffer, offset);
	}

	protected void addRelocationLo(RelocationLowPair relocationLo) {
		synchronized (relocations) {
			if (!relocations.contains(relocationLo)) {
				relocations.add(relocationLo);
			}
		}
	}

	protected void removeRelocationLo(RelocationLowPair relocationLo) {
		synchronized (relocations) {
			relocations.remove(relocationLo);
		}
	}

	public List<RelocationLowPair> getLowPairs() {
		return Collections.unmodifiableList(relocations);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RelocationHighPair)) {
			return false;
		}

		RelocationHighPair rel = (RelocationHighPair) obj;
		return address.equals(rel.getAddress()) && width == rel.getWidth() &&
			bitmask == rel.getBitmask() && target.equals(rel.getTarget());
	}
}
