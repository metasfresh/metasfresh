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

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.metas.printing.client.engine.PrintingEngine;
import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrintPackageInfo;

public abstract class AbstractMultiPageTestManual
{
	private static final String RESOURCE_TestPDF = "/test01.pdf";

	@Test
	public void test()
	{
		final String printService = "WF-3540 Series(Netzwerk)";

		final PrintPackageInfo printPackageInfo1 = new PrintPackageInfo();
		printPackageInfo1.setPageFrom(1);
		printPackageInfo1.setPageTo(1);
		printPackageInfo1.setPrintService(printService);
		printPackageInfo1.setTrayNumber(getTrayNumberForPage1()); // Kassette 1

		final PrintPackageInfo printPackageInfo2 = new PrintPackageInfo();
		printPackageInfo2.setPageFrom(2);
		printPackageInfo2.setPageTo(2);
		printPackageInfo2.setPrintService(printService);
		printPackageInfo2.setTrayNumber(getTrayNumberForPage2()); // Kassette 2
		printPackageInfo2.setCalX(60);
		printPackageInfo2.setCalY(120);

		final List<PrintPackageInfo> printPackageInfos = Arrays.asList(
				printPackageInfo1,
				printPackageInfo2
				);

		final PrintPackage printPackage = new PrintPackage();
		printPackage.setTransactionId("testTrx");
		printPackage.setPrintPackageId("456");
		printPackage.setPageCount(2);
		printPackage.setFormat(PrintPackage.FORMAT_PDF);
		printPackage.setPrintJobInstructionsID("123");
		printPackage.setCopies(getCopies());
		printPackage.setPrintPackageInfos(printPackageInfos);

		final PrintingEngine engine = PrintingEngine.get();
		engine.print(printPackage, getTestPDF());
	}

	protected abstract int getCopies();

	protected abstract int getTrayNumberForPage1();

	protected abstract int getTrayNumberForPage2();

	private InputStream getTestPDF()
	{
		return getClass().getResourceAsStream(RESOURCE_TestPDF);
	}
}
