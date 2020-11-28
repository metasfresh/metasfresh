package de.metas.printing.api;

import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterTray_Matching;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.util.ISingletonService;

public interface IPrinterBL extends ISingletonService
{
	/**
	 * If <code>printerHW</code> has not yet any tray matching for <code>tray</code>, then this method creates one.
	 *
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
	 */
	I_AD_PrinterHW_Calibration createCalibrationIfNoneExists(I_AD_PrinterTray_Matching printerTrayMatching);

	/**
	 * For the given <code>AD_Printer_Matching</code>, this method makes sure that all <code>_AD_PrinterTray_Matchings</code> have a locgical tray and a hardware tray that belong to the printer matching's logial and hardware printer.
	 */
	void updatePrinterTrayMatching(I_AD_Printer_Matching printerMatching);

	boolean isAttachToPrintPackagePrinter(I_AD_PrinterHW AD_PrinterHW);
}
