package de.metas.printing.api.impl;

/*
 * #%L
 * de.metas.printing.base
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


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.printing.api.IPrinterBL;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_Printer;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterTray_Matching;
import de.metas.printing.model.I_AD_Printer_Config;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.printing.model.I_AD_Printer_Tray;

public class PrinterBLTest extends AbstractPrintingTest
{
	@BeforeClass
	public static void configure()
	{
		Adempiere.enableUnitTestMode();
	}

	@Test
	public void test_createPrinterMatchingIfNoneExists()
	{
		final POJOLookupMap db = POJOLookupMap.get();

		final I_AD_Printer_Config config = db.newInstance(I_AD_Printer_Config.class);
		db.save(config);

		final I_AD_Printer printer = db.newInstance(I_AD_Printer.class);
		db.save(printer);

		final I_AD_PrinterHW printerHW = db.newInstance(I_AD_PrinterHW.class);
		db.save(printerHW);

		final I_AD_Printer_Matching matching = Services.get(IPrinterBL.class).createPrinterMatchingIfNoneExists(config, printer, printerHW, ITrx.TRXNAME_None);

		final int printerID = matching.getAD_Printer_ID();
		final int hwPrinterID = matching.getAD_PrinterHW_ID();

		Assert.assertNotNull("Match not created", matching);
		Assert.assertTrue(printerID == printer.getAD_Printer_ID());
		Assert.assertTrue(hwPrinterID == printerHW.getAD_PrinterHW_ID());
	}

	/**
	 * Tests if createPrinterMatchingIfNoneExists only creates a matching if there isn't a matching yet.
	 */
	@Test
	public void test_dontCreateAnotherPrintMatchingIfOneExists()
	{
		final POJOLookupMap db = POJOLookupMap.get();

		final I_AD_Printer_Config config = db.newInstance(I_AD_Printer_Config.class);
		db.save(config);

		final I_AD_Printer printer = db.newInstance(I_AD_Printer.class);
		db.save(printer);

		final I_AD_PrinterHW firstPrinterHW = db.newInstance(I_AD_PrinterHW.class);
		db.save(firstPrinterHW);

		final I_AD_Printer_Matching matching = Services.get(IPrinterBL.class).createPrinterMatchingIfNoneExists(config, printer, firstPrinterHW, ITrx.TRXNAME_None);

		final int printerID = matching.getAD_Printer_ID();
		final int hwPrinterID = matching.getAD_PrinterHW_ID();

		Assert.assertNotNull("Match not created", matching);
		Assert.assertTrue(printerID == printer.getAD_Printer_ID());
		Assert.assertTrue(hwPrinterID == firstPrinterHW.getAD_PrinterHW_ID());

		final I_AD_PrinterHW secondPrinterHW = db.newInstance(I_AD_PrinterHW.class);
		db.save(secondPrinterHW);

		final I_AD_Printer_Matching secondMatching = Services.get(IPrinterBL.class).createPrinterMatchingIfNoneExists(config, printer, secondPrinterHW, ITrx.TRXNAME_None);
		Assert.assertNull("Another matching was created, despite the existing one", secondMatching);
	}

	@Test
	public void test_createTrayMatchingIfNoneExists()
	{
		final POJOLookupMap db = POJOLookupMap.get();

		final I_AD_Printer printer = db.newInstance(I_AD_Printer.class);
		db.save(printer);

		final I_AD_PrinterHW printerHW = db.newInstance(I_AD_PrinterHW.class);
		db.save(printerHW);

		final I_AD_Printer_Tray tray = db.newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		db.save(tray);

		final I_AD_PrinterHW_MediaTray printerTrayHW = db.newInstance(I_AD_PrinterHW_MediaTray.class);
		printerTrayHW.setAD_PrinterHW(printerHW);
		db.save(printerTrayHW);

		final I_AD_Printer_Matching matching = db.newInstance(I_AD_Printer_Matching.class);
		matching.setAD_Printer_ID(printer.getAD_Printer_ID());
		matching.setAD_PrinterHW(printerHW);
		db.save(matching);

		final I_AD_PrinterTray_Matching trayMatching = Services.get(IPrinterBL.class).createTrayMatchingIfNoneExists(matching, tray, printerTrayHW, ITrx.TRXNAME_None);

		final int trayID = trayMatching.getAD_Printer_Tray_ID();

		final int matchingID = trayMatching.getAD_Printer_Matching_ID();

		final int trayHWID = trayMatching.getAD_PrinterHW_MediaTray_ID();

		Assert.assertNotNull("Match not created", trayMatching);
		Assert.assertTrue(trayID == tray.getAD_Printer_Tray_ID());
		Assert.assertTrue(matchingID == matching.getAD_Printer_Matching_ID());
		Assert.assertTrue(trayHWID == printerTrayHW.getAD_PrinterHW_MediaTray_ID());
	}

	@Test
	public void test_dontCreateAnotherTrayMatchingIfOneExists()
	{
		final POJOLookupMap db = POJOLookupMap.get();

		final I_AD_Printer printer = db.newInstance(I_AD_Printer.class);
		db.save(printer);

		final I_AD_PrinterHW printerHW = db.newInstance(I_AD_PrinterHW.class);
		db.save(printerHW);

		final I_AD_Printer_Tray tray = db.newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		db.save(tray);

		final I_AD_PrinterHW_MediaTray firstPrinterTrayHW = db.newInstance(I_AD_PrinterHW_MediaTray.class);
		firstPrinterTrayHW.setAD_PrinterHW(printerHW);
		db.save(firstPrinterTrayHW);

		final I_AD_Printer_Matching matching = db.newInstance(I_AD_Printer_Matching.class);
		matching.setAD_Printer_ID(printer.getAD_Printer_ID());
		matching.setAD_PrinterHW(printerHW);
		db.save(matching);

		final I_AD_PrinterTray_Matching trayMatching = Services.get(IPrinterBL.class).createTrayMatchingIfNoneExists(matching, tray, firstPrinterTrayHW, ITrx.TRXNAME_None);

		Assert.assertNotNull("Match not created", trayMatching);

		final int trayID = trayMatching.getAD_Printer_Tray_ID();
		final int matchingID = trayMatching.getAD_Printer_Matching_ID();
		final int trayHWID = trayMatching.getAD_PrinterHW_MediaTray_ID();

		Assert.assertTrue(trayID == tray.getAD_Printer_Tray_ID());
		Assert.assertTrue(matchingID == matching.getAD_Printer_Matching_ID());
		Assert.assertTrue(trayHWID == firstPrinterTrayHW.getAD_PrinterHW_MediaTray_ID());

		final I_AD_PrinterHW_MediaTray secondPrinterTrayHW = db.newInstance(I_AD_PrinterHW_MediaTray.class);
		secondPrinterTrayHW.setAD_PrinterHW(printerHW);
		db.save(secondPrinterTrayHW);

		final I_AD_PrinterTray_Matching secondTrayMatching = Services.get(IPrinterBL.class).createTrayMatchingIfNoneExists(matching, tray, secondPrinterTrayHW, ITrx.TRXNAME_None);
		Assert.assertNull("Another tray matching was created, despite the existing one", secondTrayMatching);
	}

	@Test
	public void test_createCalibrationIfNoneExists()
	{
		final POJOLookupMap db = POJOLookupMap.get();

		final I_AD_Printer printer = db.newInstance(I_AD_Printer.class);
		db.save(printer);

		final I_AD_PrinterHW printerHW = db.newInstance(I_AD_PrinterHW.class);
		db.save(printerHW);

		final I_AD_Printer_Matching matching = db.newInstance(I_AD_Printer_Matching.class);
		matching.setAD_Printer_ID(printer.getAD_Printer_ID());
		matching.setAD_PrinterHW(printerHW);
		db.save(matching);

		final I_AD_Printer_Tray tray = db.newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		db.save(tray);

		final I_AD_PrinterHW_MediaTray printerTrayHW = db.newInstance(I_AD_PrinterHW_MediaTray.class);
		printerTrayHW.setAD_PrinterHW(printerHW);
		db.save(printerTrayHW);

		final I_AD_PrinterTray_Matching printerTrayMatching = db.newInstance(I_AD_PrinterTray_Matching.class);
		printerTrayMatching.setAD_Printer_Matching(matching);
		printerTrayMatching.setAD_Printer_Tray(tray);
		printerTrayMatching.setAD_PrinterHW_MediaTray(printerTrayHW);
		db.save(printerTrayMatching);

		final int trayHWID = printerTrayMatching.getAD_PrinterHW_MediaTray_ID();
		final int printerHW_ID = printerHW.getAD_PrinterHW_ID();

		final I_AD_PrinterHW_Calibration calibration = Services.get(IPrinterBL.class).createCalibrationIfNoneExists(printerTrayMatching);
		Assert.assertNotNull("Calibartion not created", calibration);
		Assert.assertTrue(printerHW_ID == calibration.getAD_PrinterHW_ID());
		Assert.assertTrue(trayHWID == calibration.getAD_PrinterHW_MediaTray_ID());

	}

	@Test
	public void test_dontCreateAnotherCalibrationIfOneExists()
	{
		final POJOLookupMap db = POJOLookupMap.get();

		final I_AD_Printer printer = db.newInstance(I_AD_Printer.class);
		db.save(printer);

		final I_AD_PrinterHW printerHW = db.newInstance(I_AD_PrinterHW.class);
		db.save(printerHW);

		final I_AD_Printer_Matching matching = db.newInstance(I_AD_Printer_Matching.class);
		matching.setAD_Printer_ID(printer.getAD_Printer_ID());
		matching.setAD_PrinterHW(printerHW);
		db.save(matching);

		final I_AD_Printer_Tray tray = db.newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		db.save(tray);

		final I_AD_PrinterHW_MediaTray printerTrayHW = db.newInstance(I_AD_PrinterHW_MediaTray.class);
		printerTrayHW.setAD_PrinterHW(printerHW);
		db.save(printerTrayHW);

		final I_AD_PrinterTray_Matching printerTrayMatching = db.newInstance(I_AD_PrinterTray_Matching.class);
		printerTrayMatching.setAD_Printer_Matching(matching);
		printerTrayMatching.setAD_Printer_Tray(tray);
		printerTrayMatching.setAD_PrinterHW_MediaTray(printerTrayHW);
		db.save(printerTrayMatching);

		final int trayHWID = printerTrayMatching.getAD_PrinterHW_MediaTray_ID();
		final int printerHW_ID = printerHW.getAD_PrinterHW_ID();

		final I_AD_PrinterHW_Calibration firstCalibration = Services.get(IPrinterBL.class).createCalibrationIfNoneExists(printerTrayMatching);
		Assert.assertNotNull("Calibartion not created", firstCalibration);
		Assert.assertTrue(printerHW_ID == firstCalibration.getAD_PrinterHW_ID());
		Assert.assertTrue(trayHWID == firstCalibration.getAD_PrinterHW_MediaTray_ID());

		//

		final I_AD_PrinterHW_Calibration secondCalibration = Services.get(IPrinterBL.class).createCalibrationIfNoneExists(printerTrayMatching);
		Assert.assertNull("Another calibartion was created, despite the existing one", secondCalibration);
	}

	/**
	 * Tests {@link PrinterBL#updatePrinterTrayMatching(I_AD_Printer_Matching)} where both the old and new HW printer have a tray
	 */
	@Test
	public void test_updateTrayMatching_NewAndOldPrinterWithTray()
	{
		final POJOLookupMap db = POJOLookupMap.get();
		db.clear();

		//
		// create HW printer and tray
		final I_AD_PrinterHW printerHW1 = db.newInstance(I_AD_PrinterHW.class);
		POJOWrapper.setInstanceName(printerHW1, "printerHW1");
		InterfaceWrapperHelper.save(printerHW1);

		final I_AD_PrinterHW_MediaTray printerTrayHW = db.newInstance(I_AD_PrinterHW_MediaTray.class);
		POJOWrapper.setInstanceName(printerTrayHW, "printerTrayHW");
		printerTrayHW.setAD_PrinterHW(printerHW1);
		InterfaceWrapperHelper.save(printerTrayHW);

		//
		// create logical printer and tray
		final I_AD_Printer printer = db.newInstance(I_AD_Printer.class);
		InterfaceWrapperHelper.save(printer);

		final I_AD_Printer_Tray tray = db.newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		InterfaceWrapperHelper.save(tray);

		//
		// Create printer and tray matching:
		// printer -> printerHW1
		// tray -> printerTrayHW
		final I_AD_Printer_Matching printerMatching = db.newInstance(I_AD_Printer_Matching.class);
		printerMatching.setAD_Printer_ID(printer.getAD_Printer_ID());
		printerMatching.setAD_PrinterHW(printerHW1);
		InterfaceWrapperHelper.save(printerMatching);

		final I_AD_PrinterTray_Matching printerTrayMatching = db.newInstance(I_AD_PrinterTray_Matching.class);
		printerTrayMatching.setAD_Printer_Matching(printerMatching);
		printerTrayMatching.setAD_Printer_Tray(tray);
		printerTrayMatching.setAD_PrinterHW_MediaTray(printerTrayHW);
		InterfaceWrapperHelper.save(printerTrayMatching);

		//
		// create another HW printer and tray
		final I_AD_PrinterHW printerHW2 = db.newInstance(I_AD_PrinterHW.class);
		POJOWrapper.setInstanceName(printerHW2, "printerHW2");
		InterfaceWrapperHelper.save(printerHW2);

		final I_AD_PrinterHW_MediaTray printerTrayHW2 = db.newInstance(I_AD_PrinterHW_MediaTray.class);
		POJOWrapper.setInstanceName(printerTrayHW2, "printerTrayHW2");
		printerTrayHW2.setAD_PrinterHW(printerHW2);
		InterfaceWrapperHelper.save(printerTrayHW2);

		//
		// change the printer matching and invoke the method under test
		printerMatching.setAD_PrinterHW(printerHW2);
		InterfaceWrapperHelper.save(printerMatching);

		new PrinterBL().updatePrinterTrayMatching(printerMatching);
		InterfaceWrapperHelper.refresh(printerTrayMatching);

		//
		// test
		final List<I_AD_PrinterTray_Matching> trayMatchings = Services.get(IPrintingDAO.class).retrievePrinterTrayMatchings(printerMatching);
		assertThat("After the HW printer change, there should be still one tray matching", trayMatchings.size(), equalTo(1));
		assertThat(trayMatchings.get(0).getAD_PrinterHW_MediaTray(), equalTo(printerTrayHW2));
		assertThat(trayMatchings.get(0), equalTo(printerTrayMatching));
	}

	/**
	 * Tests {@link PrinterBL#updatePrinterTrayMatching(I_AD_Printer_Matching)} where neither the old and nor the new HW printer have a tray.
	 */
	@Test
	public void test_updatTrayMatching_NewAndOldPrinterWithNoTray()
	{
		final POJOLookupMap db = POJOLookupMap.get();
		db.clear();

		//
		// create HW printer, but no tray
		final I_AD_PrinterHW printerHW1 = db.newInstance(I_AD_PrinterHW.class);
		db.save(printerHW1);

		//
		// create logical printer and tray
		final I_AD_Printer printer = db.newInstance(I_AD_Printer.class);
		db.save(printer);

		final I_AD_Printer_Tray tray = db.newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		db.save(tray);

		// create printer matching
		final I_AD_Printer_Matching printerMatching = db.newInstance(I_AD_Printer_Matching.class);
		printerMatching.setAD_Printer_ID(printer.getAD_Printer_ID());
		printerMatching.setAD_PrinterHW(printerHW1);
		db.save(printerMatching);

		//
		// create another HW printer
		final I_AD_PrinterHW printerHW2 = db.newInstance(I_AD_PrinterHW.class);
		db.save(printerHW2);

		//
		// change the printer matching and invoke the method under test
		printerMatching.setAD_PrinterHW(printerHW2);
		db.save(printerMatching);

		new PrinterBL().updatePrinterTrayMatching(printerMatching);

		//
		// test
		final List<I_AD_PrinterTray_Matching> trayMatchings = Services.get(IPrintingDAO.class).retrievePrinterTrayMatchings(printerMatching);
		assertThat("There should still be no tray matching", trayMatchings.isEmpty(), is(true));
	}

	/**
	 * Tests {@link PrinterBL#updatePrinterTrayMatching(I_AD_Printer_Matching)} where the old HW printer has a tray and the new HW printer is tray-less.
	 */
	@Test
	public void test_updatTrayMatching_NewPrinterNoTray()
	{
		final POJOLookupMap db = POJOLookupMap.get();
		db.clear();

		//
		// create HW printer and tray
		final I_AD_PrinterHW printerHW1 = db.newInstance(I_AD_PrinterHW.class);
		db.save(printerHW1);

		final I_AD_PrinterHW_MediaTray printerTrayHW = db.newInstance(I_AD_PrinterHW_MediaTray.class);
		printerTrayHW.setAD_PrinterHW(printerHW1);
		db.save(printerTrayHW);

		//
		// create logical printer and tray
		final I_AD_Printer printer = db.newInstance(I_AD_Printer.class);
		db.save(printer);

		final I_AD_Printer_Tray tray = db.newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		db.save(tray);

		// create printer and tray matching
		final I_AD_Printer_Matching printerMatching = db.newInstance(I_AD_Printer_Matching.class);
		printerMatching.setAD_Printer_ID(printer.getAD_Printer_ID());
		printerMatching.setAD_PrinterHW(printerHW1);
		db.save(printerMatching);

		final I_AD_PrinterTray_Matching printerTrayMatching = db.newInstance(I_AD_PrinterTray_Matching.class);
		printerTrayMatching.setAD_Printer_Matching(printerMatching);
		printerTrayMatching.setAD_Printer_Tray(tray);
		printerTrayMatching.setAD_PrinterHW_MediaTray(printerTrayHW);
		db.save(printerTrayMatching);

		//
		// create another HW printer and tray
		final I_AD_PrinterHW printerHW2 = db.newInstance(I_AD_PrinterHW.class);
		db.save(printerHW2);

		//
		// change the printer matching and invoke the method under test
		printerMatching.setAD_PrinterHW(printerHW2);
		db.save(printerMatching);

		new PrinterBL().updatePrinterTrayMatching(printerMatching);

		//
		// test
		final List<I_AD_PrinterTray_Matching> trayMatchings = Services.get(IPrintingDAO.class).retrievePrinterTrayMatchings(printerMatching);
		assertThat("There should be no tray matching anymore", trayMatchings.isEmpty(), is(true));
	}

	/**
	 * Tests {@link PrinterBL#updatePrinterTrayMatching(I_AD_Printer_Matching)} where the old HW printer is tray-less and the new HW printer has a tray.
	 */
	@Test
	public void test_updatTrayMatching_OldPrinterNoTray()
	{
		final POJOLookupMap db = POJOLookupMap.get();
		db.clear();

		//
		// create HW printer, *but no tray* (trayless printer)
		final I_AD_PrinterHW printerHW1 = db.newInstance(I_AD_PrinterHW.class);
		db.save(printerHW1);

		//
		// create logical printer and tray
		final I_AD_Printer printer = db.newInstance(I_AD_Printer.class);
		db.save(printer);

		final I_AD_Printer_Tray tray = db.newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		db.save(tray);

		// create printer and tray matching
		final I_AD_Printer_Matching printerMatching = db.newInstance(I_AD_Printer_Matching.class);
		printerMatching.setAD_Printer_ID(printer.getAD_Printer_ID());
		printerMatching.setAD_PrinterHW(printerHW1);
		db.save(printerMatching);

		//
		// create another HW printer, and tray
		final I_AD_PrinterHW printerHW2 = db.newInstance(I_AD_PrinterHW.class);
		db.save(printerHW2);

		final I_AD_PrinterHW_MediaTray printerTrayHW2 = db.newInstance(I_AD_PrinterHW_MediaTray.class);
		printerTrayHW2.setAD_PrinterHW(printerHW2);
		db.save(printerTrayHW2);

		//
		// change the printer matching and invoke the method under test
		printerMatching.setAD_PrinterHW(printerHW2);
		db.save(printerMatching);

		new PrinterBL().updatePrinterTrayMatching(printerMatching);

		//
		// test
		final List<I_AD_PrinterTray_Matching> trayMatchings = Services.get(IPrintingDAO.class).retrievePrinterTrayMatchings(printerMatching);
		assertThat("After the HW printer change, there should be one tray matching", trayMatchings.size(), equalTo(1));
		assertThat(trayMatchings.get(0).getAD_PrinterHW_MediaTray(), equalTo(printerTrayHW2));
	}
}
