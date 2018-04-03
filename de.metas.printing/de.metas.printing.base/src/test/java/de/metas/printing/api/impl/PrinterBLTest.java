package de.metas.printing.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;

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

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.junit.Assert;
import org.junit.Before;
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
	private PrinterBL printerBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		printerBL = new PrinterBL();
	}

	@Test
	public void test_createPrinterMatchingIfNoneExists()
	{
	final I_AD_Printer_Config config = newInstance(I_AD_Printer_Config.class);
		save(config);

		final I_AD_Printer printer = newInstance(I_AD_Printer.class);
		save(printer);

		final I_AD_PrinterHW printerHW = newInstance(I_AD_PrinterHW.class);
		save(printerHW);

		final I_AD_Printer_Matching matching = printerBL.createPrinterMatchingIfNoneExists(config, printer, printerHW);

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
		final I_AD_Printer_Config config = newInstance(I_AD_Printer_Config.class);
		save(config);

		final I_AD_Printer printer = newInstance(I_AD_Printer.class);
		save(printer);

		final I_AD_PrinterHW firstPrinterHW = newInstance(I_AD_PrinterHW.class);
		save(firstPrinterHW);

		final I_AD_Printer_Matching matching = printerBL.createPrinterMatchingIfNoneExists(config, printer, firstPrinterHW);

		final int printerID = matching.getAD_Printer_ID();
		final int hwPrinterID = matching.getAD_PrinterHW_ID();

		Assert.assertNotNull("Match not created", matching);
		Assert.assertTrue(printerID == printer.getAD_Printer_ID());
		Assert.assertTrue(hwPrinterID == firstPrinterHW.getAD_PrinterHW_ID());

		final I_AD_PrinterHW secondPrinterHW = newInstance(I_AD_PrinterHW.class);
		save(secondPrinterHW);

		final I_AD_Printer_Matching secondMatching = printerBL.createPrinterMatchingIfNoneExists(config, printer, secondPrinterHW);
		Assert.assertNull("Another matching was created, despite the existing one", secondMatching);
	}

	@Test
	public void test_createTrayMatchingIfNoneExists()
	{
		final I_AD_Printer printer = newInstance(I_AD_Printer.class);
		save(printer);

		final I_AD_PrinterHW printerHW = newInstance(I_AD_PrinterHW.class);
		save(printerHW);

		final I_AD_Printer_Tray tray = newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		save(tray);

		final I_AD_PrinterHW_MediaTray printerTrayHW = newInstance(I_AD_PrinterHW_MediaTray.class);
		printerTrayHW.setAD_PrinterHW(printerHW);
		save(printerTrayHW);

		final I_AD_Printer_Matching matching = newInstance(I_AD_Printer_Matching.class);
		matching.setAD_Printer_ID(printer.getAD_Printer_ID());
		matching.setAD_PrinterHW(printerHW);
		save(matching);

		final I_AD_PrinterTray_Matching trayMatching = printerBL.createTrayMatchingIfNoneExists(matching, tray, printerTrayHW);

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
		final I_AD_Printer printer = newInstance(I_AD_Printer.class);
		save(printer);

		final I_AD_PrinterHW printerHW = newInstance(I_AD_PrinterHW.class);
		save(printerHW);

		final I_AD_Printer_Tray tray = newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		save(tray);

		final I_AD_PrinterHW_MediaTray firstPrinterTrayHW = newInstance(I_AD_PrinterHW_MediaTray.class);
		firstPrinterTrayHW.setAD_PrinterHW(printerHW);
		save(firstPrinterTrayHW);

		final I_AD_Printer_Matching matching = newInstance(I_AD_Printer_Matching.class);
		matching.setAD_Printer_ID(printer.getAD_Printer_ID());
		matching.setAD_PrinterHW(printerHW);
		save(matching);

		final I_AD_PrinterTray_Matching trayMatching = printerBL.createTrayMatchingIfNoneExists(matching, tray, firstPrinterTrayHW);

		Assert.assertNotNull("Match not created", trayMatching);

		final int trayID = trayMatching.getAD_Printer_Tray_ID();
		final int matchingID = trayMatching.getAD_Printer_Matching_ID();
		final int trayHWID = trayMatching.getAD_PrinterHW_MediaTray_ID();

		Assert.assertTrue(trayID == tray.getAD_Printer_Tray_ID());
		Assert.assertTrue(matchingID == matching.getAD_Printer_Matching_ID());
		Assert.assertTrue(trayHWID == firstPrinterTrayHW.getAD_PrinterHW_MediaTray_ID());

		final I_AD_PrinterHW_MediaTray secondPrinterTrayHW = newInstance(I_AD_PrinterHW_MediaTray.class);
		secondPrinterTrayHW.setAD_PrinterHW(printerHW);
		save(secondPrinterTrayHW);

		final I_AD_PrinterTray_Matching secondTrayMatching = printerBL.createTrayMatchingIfNoneExists(matching, tray, secondPrinterTrayHW);
		Assert.assertNull("Another tray matching was created, despite the existing one", secondTrayMatching);
	}

	@Test
	public void test_createCalibrationIfNoneExists()
	{
		final I_AD_Printer printer = newInstance(I_AD_Printer.class);
		save(printer);

		final I_AD_PrinterHW printerHW = newInstance(I_AD_PrinterHW.class);
		save(printerHW);

		final I_AD_Printer_Matching matching = newInstance(I_AD_Printer_Matching.class);
		matching.setAD_Printer_ID(printer.getAD_Printer_ID());
		matching.setAD_PrinterHW(printerHW);
		save(matching);

		final I_AD_Printer_Tray tray = newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		save(tray);

		final I_AD_PrinterHW_MediaTray printerTrayHW = newInstance(I_AD_PrinterHW_MediaTray.class);
		printerTrayHW.setAD_PrinterHW(printerHW);
		save(printerTrayHW);

		final I_AD_PrinterTray_Matching printerTrayMatching = newInstance(I_AD_PrinterTray_Matching.class);
		printerTrayMatching.setAD_Printer_Matching(matching);
		printerTrayMatching.setAD_Printer_Tray(tray);
		printerTrayMatching.setAD_PrinterHW_MediaTray(printerTrayHW);
		save(printerTrayMatching);

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
		final I_AD_Printer printer = newInstance(I_AD_Printer.class);
		save(printer);

		final I_AD_PrinterHW printerHW = newInstance(I_AD_PrinterHW.class);
		save(printerHW);

		final I_AD_Printer_Matching matching = newInstance(I_AD_Printer_Matching.class);
		matching.setAD_Printer_ID(printer.getAD_Printer_ID());
		matching.setAD_PrinterHW(printerHW);
		save(matching);

		final I_AD_Printer_Tray tray = newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		save(tray);

		final I_AD_PrinterHW_MediaTray printerTrayHW = newInstance(I_AD_PrinterHW_MediaTray.class);
		printerTrayHW.setAD_PrinterHW(printerHW);
		save(printerTrayHW);

		final I_AD_PrinterTray_Matching printerTrayMatching = newInstance(I_AD_PrinterTray_Matching.class);
		printerTrayMatching.setAD_Printer_Matching(matching);
		printerTrayMatching.setAD_Printer_Tray(tray);
		printerTrayMatching.setAD_PrinterHW_MediaTray(printerTrayHW);
		save(printerTrayMatching);

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
		//
		// create HW printer and tray
		final I_AD_PrinterHW printerHW1 = newInstance(I_AD_PrinterHW.class);
		POJOWrapper.setInstanceName(printerHW1, "printerHW1");
		save(printerHW1);

		final I_AD_PrinterHW_MediaTray printerTrayHW = newInstance(I_AD_PrinterHW_MediaTray.class);
		POJOWrapper.setInstanceName(printerTrayHW, "printerTrayHW");
		printerTrayHW.setAD_PrinterHW(printerHW1);
		save(printerTrayHW);

		//
		// create logical printer and tray
		final I_AD_Printer printer = newInstance(I_AD_Printer.class);
		save(printer);

		final I_AD_Printer_Tray tray = newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		save(tray);

		//
		// Create printer and tray matching:
		// printer -> printerHW1
		// tray -> printerTrayHW
		final I_AD_Printer_Matching printerMatching = newInstance(I_AD_Printer_Matching.class);
		printerMatching.setAD_Printer_ID(printer.getAD_Printer_ID());
		printerMatching.setAD_PrinterHW(printerHW1);
		save(printerMatching);

		final I_AD_PrinterTray_Matching printerTrayMatching = newInstance(I_AD_PrinterTray_Matching.class);
		printerTrayMatching.setAD_Printer_Matching(printerMatching);
		printerTrayMatching.setAD_Printer_Tray(tray);
		printerTrayMatching.setAD_PrinterHW_MediaTray(printerTrayHW);
		save(printerTrayMatching);

		//
		// create another HW printer and tray
		final I_AD_PrinterHW printerHW2 = newInstance(I_AD_PrinterHW.class);
		POJOWrapper.setInstanceName(printerHW2, "printerHW2");
		save(printerHW2);

		final I_AD_PrinterHW_MediaTray printerTrayHW2 = newInstance(I_AD_PrinterHW_MediaTray.class);
		POJOWrapper.setInstanceName(printerTrayHW2, "printerTrayHW2");
		printerTrayHW2.setAD_PrinterHW(printerHW2);
		save(printerTrayHW2);

		//
		// change the printer matching and invoke the method under test
		printerMatching.setAD_PrinterHW(printerHW2);
		save(printerMatching);

		new PrinterBL().updatePrinterTrayMatching(printerMatching);
		refresh(printerTrayMatching);

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
		//
		// create HW printer, but no tray
		final I_AD_PrinterHW printerHW1 = newInstance(I_AD_PrinterHW.class);
		save(printerHW1);

		//
		// create logical printer and tray
		final I_AD_Printer printer = newInstance(I_AD_Printer.class);
		save(printer);

		final I_AD_Printer_Tray tray = newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		save(tray);

		// create printer matching
		final I_AD_Printer_Matching printerMatching = newInstance(I_AD_Printer_Matching.class);
		printerMatching.setAD_Printer_ID(printer.getAD_Printer_ID());
		printerMatching.setAD_PrinterHW(printerHW1);
		save(printerMatching);

		//
		// create another HW printer
		final I_AD_PrinterHW printerHW2 = newInstance(I_AD_PrinterHW.class);
		save(printerHW2);

		//
		// change the printer matching and invoke the method under test
		printerMatching.setAD_PrinterHW(printerHW2);
		save(printerMatching);

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
		//
		// create HW printer and tray
		final I_AD_PrinterHW printerHW1 = newInstance(I_AD_PrinterHW.class);
		save(printerHW1);

		final I_AD_PrinterHW_MediaTray printerTrayHW = newInstance(I_AD_PrinterHW_MediaTray.class);
		printerTrayHW.setAD_PrinterHW(printerHW1);
		save(printerTrayHW);

		//
		// create logical printer and tray
		final I_AD_Printer printer = newInstance(I_AD_Printer.class);
		save(printer);

		final I_AD_Printer_Tray tray = newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		save(tray);

		// create printer and tray matching
		final I_AD_Printer_Matching printerMatching = newInstance(I_AD_Printer_Matching.class);
		printerMatching.setAD_Printer_ID(printer.getAD_Printer_ID());
		printerMatching.setAD_PrinterHW(printerHW1);
		save(printerMatching);

		final I_AD_PrinterTray_Matching printerTrayMatching = newInstance(I_AD_PrinterTray_Matching.class);
		printerTrayMatching.setAD_Printer_Matching(printerMatching);
		printerTrayMatching.setAD_Printer_Tray(tray);
		printerTrayMatching.setAD_PrinterHW_MediaTray(printerTrayHW);
		save(printerTrayMatching);

		//
		// create another HW printer and tray
		final I_AD_PrinterHW printerHW2 = newInstance(I_AD_PrinterHW.class);
		save(printerHW2);

		//
		// change the printer matching and invoke the method under test
		printerMatching.setAD_PrinterHW(printerHW2);
		save(printerMatching);

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
		//
		// create HW printer, *but no tray* (trayless printer)
		final I_AD_PrinterHW printerHW1 = newInstance(I_AD_PrinterHW.class);
		save(printerHW1);

		//
		// create logical printer and tray
		final I_AD_Printer printer = newInstance(I_AD_Printer.class);
		save(printer);

		final I_AD_Printer_Tray tray = newInstance(I_AD_Printer_Tray.class);
		tray.setAD_Printer_ID(printer.getAD_Printer_ID());
		save(tray);

		// create printer and tray matching
		final I_AD_Printer_Matching printerMatching = newInstance(I_AD_Printer_Matching.class);
		printerMatching.setAD_Printer_ID(printer.getAD_Printer_ID());
		printerMatching.setAD_PrinterHW(printerHW1);
		save(printerMatching);

		//
		// create another HW printer, and tray
		final I_AD_PrinterHW printerHW2 = newInstance(I_AD_PrinterHW.class);
		save(printerHW2);

		final I_AD_PrinterHW_MediaTray printerTrayHW2 = newInstance(I_AD_PrinterHW_MediaTray.class);
		printerTrayHW2.setAD_PrinterHW(printerHW2);
		save(printerTrayHW2);

		//
		// change the printer matching and invoke the method under test
		printerMatching.setAD_PrinterHW(printerHW2);
		save(printerMatching);

		new PrinterBL().updatePrinterTrayMatching(printerMatching);

		//
		// test
		final List<I_AD_PrinterTray_Matching> trayMatchings = Services.get(IPrintingDAO.class).retrievePrinterTrayMatchings(printerMatching);
		assertThat("After the HW printer change, there should be one tray matching", trayMatchings.size(), equalTo(1));
		assertThat(trayMatchings.get(0).getAD_PrinterHW_MediaTray(), equalTo(printerTrayHW2));
	}
}
