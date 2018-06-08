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

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.printing.api.IPrintPackageBL;
import de.metas.printing.api.IPrinterBL;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterHW_MediaSize;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;

@Interceptor(I_AD_PrinterHW.class)
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

	/**
	 * This interceptor shall only fire if the given {@code printerTrayHW} was created with replication.
	 * If it was created via the REST endpoint, then the business logic is called directly.
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW_REPLICATION)
	public void createPrinterConfigIfNoneExists(final I_AD_PrinterHW printerHW)
	{
		Services.get(IPrinterBL.class).createConfigAndDefaultPrinterMatching(printerHW);
	}
}
