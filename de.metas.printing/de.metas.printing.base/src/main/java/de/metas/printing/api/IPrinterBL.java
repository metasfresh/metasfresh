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

import de.metas.printing.model.I_AD_Printer;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterTray_Matching;
import de.metas.printing.model.I_AD_Printer_Config;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.printing.model.I_AD_Printer_Tray;

public interface IPrinterBL extends ISingletonService
{

	/**
	 * If there is not yet any printer matching between the given <code>printerHW</code>'s host key, then this method creates one.
	 * @param printerHW
	 * @return
	 */
	I_AD_Printer_Config createPrinterConfigIfNoneExists(I_AD_PrinterHW printerHW);
	
	/**
	 * If there is not yet any printer matching between <code>printer</code> and any printerHW with the same hostName that <code>printerHW</code>, then this method creates one.
	 * 
	 * @param config the printer config to which the new matching shall belong
	 * @param printer
	 * @param printerHW
	 * @param trxName
	 * @return the created printer matching or <code>null</code> if none was created.
	 */
	I_AD_Printer_Matching createPrinterMatchingIfNoneExists(I_AD_Printer_Config config, I_AD_Printer printer, I_AD_PrinterHW printerHW, String trxName);

	/**
	 * If <code>printerMatching</code> has not yet any tray matching for <code>tray</code>, then this method creates one.
	 * 
	 * @param printerMatching
	 * @param tray
	 * @param printerTrayHW
	 * @param trxName
	 * @return the created tray mapping or <code>null</code> if none was created.
	 */
	I_AD_PrinterTray_Matching createTrayMatchingIfNoneExists(I_AD_Printer_Matching printerMatching, I_AD_Printer_Tray tray, I_AD_PrinterHW_MediaTray printerTrayHW, String trxName);

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
	 * @param AD_PrinterHW_ID
	 * @return
	 */
	boolean isPDFPrinter(final int AD_PrinterHW_ID);
}
