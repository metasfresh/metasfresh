package de.metas.printing.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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


import java.util.List;
import java.util.Properties;

import javax.print.attribute.standard.MediaSize;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.printing.Printing_Constants;
import de.metas.printing.api.IPrinterBL;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_Printer;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterHW_MediaSize;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterTray_Matching;
import de.metas.printing.model.I_AD_Printer_Config;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.printing.model.I_AD_Printer_Tray;
import de.metas.printing.model.X_AD_PrinterHW;
import lombok.NonNull;

public class PrinterBL implements IPrinterBL
{

	@Override
	public I_AD_Printer_Config createPrinterConfigIfNoneExists(final I_AD_PrinterHW printerHW)
	{
		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
		final I_AD_Printer_Config existientPrinterConfig = printingDAO.retrievePrinterConfig(PlainContextAware.newOutOfTrx(InterfaceWrapperHelper.getCtx(printerHW)),
				printerHW.getHostKey(),
				printerHW.getUpdatedBy());
		if(existientPrinterConfig != null)
		{
			return existientPrinterConfig;
		}

		final I_AD_Printer_Config newPrinterConfig = InterfaceWrapperHelper.newInstance(I_AD_Printer_Config.class, printerHW);
		newPrinterConfig.setAD_Org_ID(printerHW.getAD_Org_ID());
		newPrinterConfig.setConfigHostKey(printerHW.getHostKey());
		newPrinterConfig.setIsSharedPrinterConfig(false); // not shared by default
		InterfaceWrapperHelper.save(newPrinterConfig);

		return newPrinterConfig;
	}

	@Override
	public I_AD_Printer_Matching createPrinterMatchingIfNoneExists(
			final I_AD_Printer_Config config,
			final I_AD_Printer printer,
			final I_AD_PrinterHW printerHW,
			final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(printer);

		// first search ; if exists return null
		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

		final I_AD_Printer_Matching exitentMatching = printingDAO.retrievePrinterMatchingOrNull(printerHW.getHostKey(), printer);
		if (exitentMatching != null)
		{
			return null;
		}

		final I_AD_Printer_Matching matching = InterfaceWrapperHelper.create(ctx, I_AD_Printer_Matching.class, trxName);
		matching.setAD_Printer_Config(config);
		matching.setAD_Org_ID(printerHW.getAD_Org_ID());
		matching.setAD_Printer_ID(printer.getAD_Printer_ID());
		matching.setAD_PrinterHW(printerHW);
		InterfaceWrapperHelper.save(matching);
		return matching;
	}

	@Override
	public I_AD_PrinterTray_Matching createTrayMatchingIfNoneExists(
			final I_AD_Printer_Matching printerMatching,
			final I_AD_Printer_Tray tray,
			final I_AD_PrinterHW_MediaTray printerTrayHW,
			final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(printerTrayHW);

		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

		// first search ; if exists return null
		final I_AD_PrinterTray_Matching exitentTrayMatching = printingDAO.retrievePrinterTrayMatchingOrNull(printerMatching, tray.getAD_Printer_Tray_ID());
		if (exitentTrayMatching != null)
		{
			return null;
		}

		final I_AD_PrinterTray_Matching trayMatching = InterfaceWrapperHelper.create(ctx, I_AD_PrinterTray_Matching.class, trxName);
		trayMatching.setAD_Org_ID(printerTrayHW.getAD_Org_ID());
		trayMatching.setAD_Printer_Matching(printerMatching);
		trayMatching.setAD_Printer_Tray(tray);
		trayMatching.setAD_PrinterHW_MediaTray(printerTrayHW);
		InterfaceWrapperHelper.save(trayMatching);

		return trayMatching;
	}

	@Override
	public I_AD_PrinterHW_Calibration createCalibrationIfNoneExists(final I_AD_PrinterTray_Matching printerTrayMatching)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(printerTrayMatching);
		final String trxName = InterfaceWrapperHelper.getTrxName(printerTrayMatching);

		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

		final I_AD_PrinterHW printerHW = printerTrayMatching.getAD_Printer_Matching().getAD_PrinterHW();
		final I_AD_PrinterHW_MediaSize hwMediaSize = printingDAO.retrieveMediaSize(printerHW, MediaSize.ISO.A4, true);
		final I_AD_PrinterHW_MediaTray hwMediaTray = printerTrayMatching.getAD_PrinterHW_MediaTray();

		final I_AD_PrinterHW_Calibration existingCalibration = printingDAO.retrieveCalibration(hwMediaSize, hwMediaTray);
		if (existingCalibration != null)
		{
			return null; // nothing to do, calibration already exists
		}

		final I_AD_PrinterHW_Calibration cal = InterfaceWrapperHelper.create(ctx, I_AD_PrinterHW_Calibration.class, trxName);

		cal.setAD_PrinterHW(printerHW);
		cal.setAD_PrinterHW_MediaSize(hwMediaSize);
		cal.setAD_PrinterHW_MediaTray(hwMediaTray);

		cal.setMeasurementX(Printing_Constants.AD_PrinterHW_Calibration_JASPER_REF_X_MM);
		cal.setMeasurementY(Printing_Constants.AD_PrinterHW_Calibration_JASPER_REF_Y_MM);

		InterfaceWrapperHelper.save(cal);

		return cal;
	}

	@Override
	public void updatePrinterTrayMatching(final I_AD_Printer_Matching printerMatching)
	{
		final IPrintingDAO dao = Services.get(IPrintingDAO.class);

		final List<I_AD_PrinterHW_MediaTray> hwTrays = dao.retrieveMediaTrays(printerMatching.getAD_PrinterHW());

		final List<I_AD_PrinterTray_Matching> existingPrinterTrayMatchings = dao.retrievePrinterTrayMatchings(printerMatching);

		final I_AD_PrinterHW_MediaTray defaultHWTray = hwTrays.isEmpty() ? null : hwTrays.get(0);

		if (defaultHWTray == null)
		{
			// the new HW printer has no tray
			// delete existing tray matchings;
			for (final I_AD_PrinterTray_Matching trayMatching : existingPrinterTrayMatchings)
			{
				InterfaceWrapperHelper.delete(trayMatching);
			}
		}
		else if (existingPrinterTrayMatchings.isEmpty() && defaultHWTray != null)
		{
			// the old HW printer didn't have a tray, but the new one does
			// create a new matching for every logical tray
			final I_AD_Printer printer = load(printerMatching.getAD_Printer_ID(), I_AD_Printer.class);
			for (final I_AD_Printer_Tray logicalTray : dao.retrieveTrays(printer))
			{
				final I_AD_PrinterTray_Matching trayMatching = InterfaceWrapperHelper.newInstance(I_AD_PrinterTray_Matching.class, printerMatching);
				trayMatching.setAD_Printer_Matching(printerMatching);
				trayMatching.setAD_Printer_Tray(logicalTray);
				trayMatching.setAD_PrinterHW_MediaTray(defaultHWTray);
				InterfaceWrapperHelper.save(trayMatching);
			}
		}
		else
		{
			final I_AD_Printer printer = load(printerMatching.getAD_Printer_ID(), I_AD_Printer.class);
			Check.assumeNotNull(defaultHWTray, "{} has at least one tray", printer);

			Check.assume(!existingPrinterTrayMatchings.isEmpty(), "{} has at least one tray matching", printerMatching);

			for (final I_AD_PrinterTray_Matching trayMatching : existingPrinterTrayMatchings)
			{
				trayMatching.setAD_PrinterHW_MediaTray(defaultHWTray);
				InterfaceWrapperHelper.save(trayMatching);
			}
		}
	}

	@Override
	public boolean isPDFPrinter(@NonNull final I_AD_PrinterHW printerHW)
	{
		return  X_AD_PrinterHW.OUTPUTTYPE_PDF.equals(printerHW.getOutputType());
	}
}
