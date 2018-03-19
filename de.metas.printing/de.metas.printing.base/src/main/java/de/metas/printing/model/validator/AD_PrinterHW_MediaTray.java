package de.metas.printing.model.validator;

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

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.printing.api.IPrinterBL;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_Printer;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.printing.model.I_AD_Printer_Tray;

@Validator(I_AD_PrinterHW_MediaTray.class)
public class AD_PrinterHW_MediaTray
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_NEW_REPLICATION })
	public void createDefaultMatching(final I_AD_PrinterHW_MediaTray printerTrayHW)
	{
		final I_AD_PrinterHW printerHW = printerTrayHW.getAD_PrinterHW();

		final List<I_AD_Printer_Matching> printerMatchings = Services.get(IPrintingDAO.class).retrievePrinterMatchings(printerHW);
		if (printerMatchings.isEmpty())
		{
			// no printer matching was found for printerHW => nothing to match
			return;
		}

		for (final I_AD_Printer_Matching printerMatching : printerMatchings)
		{
			final I_AD_Printer printer = load(printerMatching.getAD_Printer_ID(), I_AD_Printer.class);
			final List<I_AD_Printer_Tray> trays = Services.get(IPrintingDAO.class).retrieveTrays(printer);

			for (final I_AD_Printer_Tray tray : trays)
			{
				//
				// Create tray matching
				// Note that we must make sure the trx of 'printerTrayHW' is used, because that trx has not yet been committed and there might be FK constraint violations when we save the new data outside of
				// it.
				final String trxName = InterfaceWrapperHelper.getTrxName(printerTrayHW);
				Services.get(IPrinterBL.class).createTrayMatchingIfNoneExists(printerMatching, tray, printerTrayHW, trxName);
			}
		}
	}
}
