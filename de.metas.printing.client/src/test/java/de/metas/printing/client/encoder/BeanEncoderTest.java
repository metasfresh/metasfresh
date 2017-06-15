package de.metas.printing.client.encoder;

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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrintPackageInfo;

public class BeanEncoderTest
{
	private final IBeanEnconder[] parsers = new IBeanEnconder[] {
			new JsonBeanEncoder()
	};

	@Test
	public void parsePrintPackage_test01()
	{
		final PrintPackage printPackage = new PrintPackage();
		printPackage.setTransactionId("testTrx");
		printPackage.setPrintPackageId("456");
		printPackage.setPageCount(10);
		printPackage.setFormat("PDF");

		final List<PrintPackageInfo> printPackageInfos = new ArrayList<PrintPackageInfo>();
		printPackage.setPrintPackageInfos(printPackageInfos);

		{
			final PrintPackageInfo info = new PrintPackageInfo();
			info.setPrintService("service01");
			info.setTray("tray01");
			info.setPageFrom(1);
			info.setPageTo(3);
			printPackageInfos.add(info);
		}
		{
			final PrintPackageInfo info = new PrintPackageInfo();
			info.setPrintService("service02");
			info.setTray("tray02");
			info.setPageFrom(1);
			info.setPageTo(3);
			printPackageInfos.add(info);
		}

		final String printPackageExpectedStr = printPackage.toString();

		for (final IBeanEnconder parser : parsers)
		{
			final byte[] data = parser.encode(printPackage); // transport data

			final PrintPackage printPackageActual = parser.decodeBytes(data, PrintPackage.class);
			final String printPackageActualStr = printPackageActual.toString();

			Assert.assertEquals("Invalid data produced by parser " + parser, printPackageExpectedStr, printPackageActualStr);
		}
	}
}
