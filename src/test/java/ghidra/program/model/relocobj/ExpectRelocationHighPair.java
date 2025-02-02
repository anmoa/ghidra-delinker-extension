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

import ghidra.program.model.address.Address;

public class ExpectRelocationHighPair implements Relocation {
	private final long address;
	private final int width;
	private final long bitmask;
	private final long target;

	public ExpectRelocationHighPair(long address, int width, long bitmask, long target) {
		this.address = address;
		this.width = width;
		this.bitmask = bitmask;
		this.target = target;
	}

	// This equals() method is intentionally not implementing an equivalence relation.
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RelocationHighPair)) {
			return false;
		}

		RelocationHighPair relocation = (RelocationHighPair) obj;
		return address == relocation.getAddress().getOffset() && width == relocation.getWidth() &&
			bitmask == relocation.getBitmask() && target == relocation.getTarget().getOffset();
	}

	@Override
	public RelocationTable getRelocationTable() {
		throw new UnsupportedOperationException("Unimplemented method 'getRelocationTable'");
	}

	@Override
	public Address getAddress() {
		throw new UnsupportedOperationException("Unimplemented method 'getAddress'");
	}

	@Override
	public int getWidth() {
		throw new UnsupportedOperationException("Unimplemented method 'getWidth'");
	}

	@Override
	public long getBitmask() {
		throw new UnsupportedOperationException("Unimplemented method 'getBitmask'");
	}

	@Override
	public Address getTarget() {
		throw new UnsupportedOperationException("Unimplemented method 'getTarget'");
	}

	@Override
	public long getAddend() {
		throw new UnsupportedOperationException("Unimplemented method 'getAddend'");
	}

	@Override
	public void delete() {
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}
}
