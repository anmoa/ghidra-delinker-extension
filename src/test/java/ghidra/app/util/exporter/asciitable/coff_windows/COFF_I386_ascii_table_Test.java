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
package ghidra.app.util.exporter.asciitable.coff_windows;

import java.io.File;

import org.junit.Test;

import ghidra.DelinkerIntegrationTest;
import ghidra.app.util.exporter.CoffRelocatableObjectExporter;
import ghidra.program.model.address.AddressFactory;
import ghidra.program.model.address.AddressSetView;
import net.boricj.bft.coff.machines.i386.CoffRelocationType_i386;

public class COFF_I386_ascii_table_Test extends DelinkerIntegrationTest {
	private static final File mainFile =
		new File("src/test/resources/ascii-table/reference/coff_windows/i386/main.obj");

	@Override
	protected String getProgramName() {
		return "src/test/resources/ascii-table/reference/coff_windows/i386/ascii-table.exe.gzf";
	}

	@Test
	public void testExport_main_o() throws Exception {
		AddressFactory af = getProgram().getAddressFactory();
		AddressSetView set = af.getAddressSet(af.getAddress("00407310"), af.getAddress("004074d3"))	// .text
				.union(af.getAddressSet(af.getAddress("00475000"), af.getAddress("00475003")))	 	// .data
				.union(af.getAddressSet(af.getAddress("00469e50"), af.getAddress("00469ea7"))); 	// .rdata
		File exportedFile = exportObjectFile(set, new CoffRelocatableObjectExporter(), null);

		ObjectFile mainObjectFile = new CoffObjectFile(mainFile);
		CoffObjectFile exported = new CoffObjectFile(exportedFile);

		mainObjectFile.compareSectionBytes(".text$mn", exported, ".text");
		mainObjectFile.compareSectionBytes(".data", exported, ".data");
		mainObjectFile.compareSectionBytes(".rdata", exported, ".rdata");

		exported.hasSymbolAtAddress("_print_number", ".text", 0x00000000);
		exported.hasSymbolAtAddress("_print_ascii_entry", ".text", 0x00000070);
		exported.hasSymbolAtAddress("_main", ".text", 0x00000120);
		exported.hasSymbolAtAddress("_NUM_ASCII_PROPERTIES", ".rdata", 0x00000000);
		exported.hasSymbolAtAddress("_s_ascii_properties", ".rdata", 0x00000008);
		exported.hasSymbolAtAddress("_COLUMNS", ".data", 0x00000000);

		exported.hasUndefinedSymbol("_putchar");
		exported.hasUndefinedSymbol("_isalnum");
		exported.hasUndefinedSymbol("_isalpha");
		exported.hasUndefinedSymbol("_iscntrl");
		exported.hasUndefinedSymbol("_isdigit");
		exported.hasUndefinedSymbol("_isgraph");
		exported.hasUndefinedSymbol("_islower");
		exported.hasUndefinedSymbol("_isprint");
		exported.hasUndefinedSymbol("_ispunct");
		exported.hasUndefinedSymbol("_isspace");
		exported.hasUndefinedSymbol("_isupper");

		exported.hasRelocationAtAddress(".text", 0x00000047,
			CoffRelocationType_i386.IMAGE_REL_I386_REL32, "_putchar");
		exported.hasRelocationAtAddress(".text", 0x00000058,
			CoffRelocationType_i386.IMAGE_REL_I386_REL32, "_putchar");
		exported.hasRelocationAtAddress(".text", 0x0000007C,
			CoffRelocationType_i386.IMAGE_REL_I386_REL32, "_print_number");
		exported.hasRelocationAtAddress(".text", 0x00000086,
			CoffRelocationType_i386.IMAGE_REL_I386_REL32, "_putchar");
		exported.hasRelocationAtAddress(".text", 0x00000093,
			CoffRelocationType_i386.IMAGE_REL_I386_REL32, "_isgraph");
		exported.hasRelocationAtAddress(".text", 0x000000A4,
			CoffRelocationType_i386.IMAGE_REL_I386_REL32, "_putchar");
		exported.hasRelocationAtAddress(".text", 0x000000B0,
			CoffRelocationType_i386.IMAGE_REL_I386_REL32, "_putchar");
		exported.hasRelocationAtAddress(".text", 0x000000BA,
			CoffRelocationType_i386.IMAGE_REL_I386_REL32, "_putchar");
		exported.hasRelocationAtAddress(".text", 0x00000103,
			CoffRelocationType_i386.IMAGE_REL_I386_REL32, "_putchar");
		exported.hasRelocationAtAddress(".text", 0x0000010F,
			CoffRelocationType_i386.IMAGE_REL_I386_REL32, "_putchar");
		exported.hasRelocationAtAddress(".text", 0x00000147,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_COLUMNS");
		exported.hasRelocationAtAddress(".text", 0x00000154,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_COLUMNS");
		exported.hasRelocationAtAddress(".text", 0x00000164,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_COLUMNS");
		exported.hasRelocationAtAddress(".text", 0x00000170,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_NUM_ASCII_PROPERTIES");
		exported.hasRelocationAtAddress(".text", 0x00000176,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_s_ascii_properties");
		exported.hasRelocationAtAddress(".text", 0x00000180,
			CoffRelocationType_i386.IMAGE_REL_I386_REL32, "_print_ascii_entry");
		exported.hasRelocationAtAddress(".text", 0x0000018D,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_COLUMNS");
		exported.hasRelocationAtAddress(".text", 0x00000192,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_COLUMNS");
		exported.hasRelocationAtAddress(".text", 0x000001B2,
			CoffRelocationType_i386.IMAGE_REL_I386_REL32, "_putchar");

		exported.hasRelocationAtAddress(".rdata", 0x00000008,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_isgraph");
		exported.hasRelocationAtAddress(".rdata", 0x00000010,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_isprint");
		exported.hasRelocationAtAddress(".rdata", 0x00000018,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_iscntrl");
		exported.hasRelocationAtAddress(".rdata", 0x00000020,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_isspace");
		exported.hasRelocationAtAddress(".rdata", 0x00000028,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_ispunct");
		exported.hasRelocationAtAddress(".rdata", 0x00000030,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_isalnum");
		exported.hasRelocationAtAddress(".rdata", 0x00000038,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_isalpha");
		exported.hasRelocationAtAddress(".rdata", 0x00000040,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_isdigit");
		exported.hasRelocationAtAddress(".rdata", 0x00000048,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_isupper");
		exported.hasRelocationAtAddress(".rdata", 0x00000050,
			CoffRelocationType_i386.IMAGE_REL_I386_DIR32, "_islower");
	}
}
