package manual;

/*
 * #%L
 * de.metas.printing.esb.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.printing.client.Context;
import de.metas.printing.client.encoder.JsonBeanEncoder;
import de.metas.printing.client.endpoint.DirectoryPrintConnectionEndpoint;
import de.metas.printing.client.engine.PrintingEngine;
import de.metas.printing.client.util.Util;
import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrintPackageInfo;

// This is a manual test because on build server we don't have the XPS printer, so this test will always fail
@Ignore
// maven will skip it (because is not starting, nor ending with "Test"), but out new Run-All-Tests-Eclipse plugin would not skip it
public class PrintingEngineTestManual
{

	private static final String RESOURCE_TestPDF = "/test01.pdf";

	public static String printingServiceName = "Microsoft XPS Document Writer";
	public static String printingTray = ""; // Printing tray name not mandatory
	public static final int printingTrayNumber = 5; // Default tray value for XPS

	// public static String printingServiceName = "Canon MX350 series Printer WS";
	// public String printingTray = ""; // no tray

	// public static String printingServiceName = "WF-3540 Series(Netzwerk)";
	// public static final String printingTray = "Kassette 1";

	@Before
	public void init()
	{
		final Context ctx = Context.getContext();

		ctx.reset(); // make sure is clear

		ctx.setProperty(Context.CTX_SessionId, "sess" + UUID.randomUUID());

		ctx.setProperty(Context.CTX_PrintConnectionEndpoint, DirectoryPrintConnectionEndpoint.class);

		ctx.setProperty(PrintingEngine.CTX_RedirectToFolder, ctx.get(Context.CTX_TempDir));
		// ctx.setProperty(PrintingEngine.CTX_RedirectToFolder, "c:/tmp/test.printClient/out");
	}

	@Test
	public void test01() throws Exception
	{
		final PrintPackage printPackage = new PrintPackage();
		printPackage.setTransactionId("testTrx");
		printPackage.setPrintPackageId("456");
		printPackage.setPageCount(10);
		printPackage.setFormat(PrintPackage.FORMAT_PDF);
		printPackage.setPrintJobInstructionsID("123");
		printPackage.setCopies(2);
		final List<PrintPackageInfo> printPackageInfos = new ArrayList<PrintPackageInfo>();
		printPackage.setPrintPackageInfos(printPackageInfos);
		{
			final PrintPackageInfo info = new PrintPackageInfo();
			info.setPrintService(printingServiceName);
			info.setTray(printingTray);
			info.setTrayNumber(printingTrayNumber);
			info.setPageFrom(1);
			info.setPageTo(1);
			info.setCalX(-60);
			info.setCalY(100);
			printPackageInfos.add(info);
		}
		// {
		// final PrintPackageInfo info = new PrintPackageInfo();
		// info.setPrintService(printingServiceName);
		// info.setTray(printingTray);
		// info.setPageFrom(2);
		// info.setPageTo(2);
		// printPackageInfos.add(info);
		// }

		final PrintingEngine printingEngine = PrintingEngine.get();
		printingEngine.print(printPackage, getTestPDF());
	}

	@Test
	public void test02()
	{
		final PrintPackage printPackage = new JsonBeanEncoder().decodeStream(getClass().getResourceAsStream("/test02_printpackage.json"), PrintPackage.class);

		// make sure we are using the right printer
		printPackage.getPrintPackageInfos().get(0).setPrintService(printingServiceName);
		printPackage.getPrintPackageInfos().get(0).setTray(printingTray);
		printPackage.getPrintPackageInfos().get(0).setTrayNumber(printingTrayNumber);

		final byte[] data = Base64.decodeBase64(Util.toByteArray(getClass().getResourceAsStream("/test02_printpackage_data_base64.txt")));

		PrintingEngine.get().print(printPackage, new ByteArrayInputStream(data));
	}

	private InputStream getTestPDF()
	{
		return getClass().getResourceAsStream(RESOURCE_TestPDF);
	}
}
