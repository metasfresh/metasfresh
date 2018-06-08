package de.metas.printing.api;

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


import org.adempiere.util.ISingletonService;

import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterTray_Matching;
import de.metas.printing.model.I_AD_Printer_Matching;

public interface IPrinterBL extends ISingletonService
{
	/**
	 * If <code>printerHW</code> has not yet any tray matching for <code>tray</code>, then this method creates one.
	 *
	 * @param printerHW
	 * @return {@code true} if a matching was created.
	 */
	boolean createConfigAndDefaultPrinterMatching(I_AD_PrinterHW printerHW);

	/**
	 * If <code>printerMatching</code> has not yet any tray matching for <code>tray</code>, then this method creates one.
	 *
	 * @return {@code true} if a matching was created.
	 */
	boolean createDefaultTrayMatching(I_AD_PrinterHW_MediaTray printerTrayHW);

	/**
	 * If there is not yet any calibration record for the given tray matching, then this method created one. Note that the method uses the trxName of the given <code>printerTrayMatching</code>.
	 *
	 * @param printerTrayMatching
	 * @return
	 */
	I_AD_PrinterHW_Calibration createCalibrationIfNoneExists(I_AD_PrinterTray_Matching printerTrayMatching);

	/**
	 * For the given <code>AD_Printer_Matching</code>, this method makes sure that all <code>_AD_PrinterTray_Matchings</code> have a locgical tray and a hardware tray that belong to the printer matching's logial and hardware printer.
	 *
	 * @param printerMatching
	 */
	void updatePrinterTrayMatching(I_AD_Printer_Matching printerMatching);

	/**
	 * checks if the given printer is PDF printer
	 * @param AD_PrinterHW
	 * @return
	 */
	boolean isPDFPrinter(I_AD_PrinterHW AD_PrinterHW);
}
