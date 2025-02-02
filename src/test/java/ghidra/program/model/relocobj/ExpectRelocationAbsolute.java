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

public class ExpectRelocationAbsolute extends AbstractExpectRelocationBitmask {
	public ExpectRelocationAbsolute(long address, int width, long target, long addend) {
		super(address, width, target, addend);
	}

	public ExpectRelocationAbsolute(long address, int width, long bitmask, long target,
			long addend) {
		super(address, width, bitmask, target, addend);
	}

	// This equals() method is intentionally not implementing an equivalence relation.
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RelocationAbsolute)) {
			return false;
		}

		return super.equals(obj);
	}
}
