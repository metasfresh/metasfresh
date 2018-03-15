package de.metas.printing.model.validator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.printing.api.IPrintPackageBL;
import de.metas.printing.api.IPrinterBL;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_Printer;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterHW_MediaSize;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_Printer_Config;

@Validator(I_AD_PrinterHW.class)
public class AD_PrinterHW
{
	private final Logger logger = LogManager.getLogger(getClass());

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void setHostKey(final I_AD_PrinterHW printerHW)
	{
		final String hostKeyOld = printerHW.getHostKey();
		if (!Check.isEmpty(hostKeyOld, true))
		{
			// HostKey was already set, nothing to do
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(printerHW);
		final String hostKey = Services.get(IPrintPackageBL.class).getHostKeyOrNull(ctx);
		if (Check.isEmpty(hostKey, true))
		{
			logger.debug("HostKey not found in context");
			return;
		}

		// Finally, update the bean
		printerHW.setHostKey(hostKey);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteAttachedLines(final I_AD_PrinterHW printerHW)
	{
		final IPrintingDAO dao = Services.get(IPrintingDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(printerHW);
		final String trxName = InterfaceWrapperHelper.getTrxName(printerHW);

		final int printerID = printerHW.getAD_PrinterHW_ID();

		// Delete attached calibrations first
		final List<I_AD_PrinterHW_Calibration> calibrations = dao.retrieveCalibrations(ctx, printerID, trxName);
		dao.removeCalibrations(calibrations);

		// Delete media trays and sizes
		final List<I_AD_PrinterHW_MediaTray> trays = dao.retrieveMediaTrays(printerHW);
		dao.removeMediaTrays(trays);

		final List<I_AD_PrinterHW_MediaSize> sizes = dao.retrieveMediaSizes(printerHW);
		dao.removeMediaSizes(sizes);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_NEW_REPLICATION })
	public void createConfigAndDefaultMatching(final I_AD_PrinterHW printerHW)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(printerHW);

		final IPrinterBL printerBL = Services.get(IPrinterBL.class);
		final I_AD_Printer_Config printerConfig = printerBL.createPrinterConfigIfNoneExists(printerHW);

		final List<I_AD_Printer> printers = Services.get(IPrintingDAO.class).retrievePrinters(ctx, printerHW.getAD_Org_ID());

		if (printers.isEmpty())
		{
			// no logical printer defined, nothing to match
			return;
		}

		for (final I_AD_Printer printer : printers)
		{
			//
			// Generate default matching
			// Note that we must make sure the trx of 'printerHW' is used, because that trx has not yet been committed and there might be FK constraint violations when we save the new data outside of
			// it.
			final String trxName = InterfaceWrapperHelper.getTrxName(printerHW);
			printerBL.createPrinterMatchingIfNoneExists(printerConfig, printer, printerHW, trxName);
		}
	}
}
