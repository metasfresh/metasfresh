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
import java.util.Objects;
import java.util.Properties;

import de.metas.logging.TableRecordMDC;
import de.metas.printing.HardwarePrinterId;
import de.metas.printing.OutputType;
import de.metas.printing.api.IPrintClientsBL;
import de.metas.printing.model.I_AD_Printer_Config;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
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
import de.metas.util.Check;
import de.metas.util.Services;
import org.slf4j.MDC;

@Interceptor(I_AD_PrinterHW.class)
public class AD_PrinterHW
{
	private final Logger logger = LogManager.getLogger(getClass());
	private final IPrintClientsBL printClientsBL = Services.get(IPrintClientsBL.class);

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void setHostKey(final I_AD_PrinterHW printerHW)
	{
		final String hostKeyOld = printerHW.getHostKey();
		if (Check.isNotBlank(hostKeyOld))
		{
			return; // HostKey was already set, nothing to do
		}

		if (!Objects.equals(OutputType.Queue, OutputType.ofNullableCode(printerHW.getOutputType())))
		{
			return; // no hostkey needed
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(printerHW);
		final String hostKey = printClientsBL.getHostKeyOrNull(ctx);
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

		final HardwarePrinterId printerID = HardwarePrinterId.ofRepoId(printerHW.getAD_PrinterHW_ID());

		// Delete attached calibrations first
		final List<I_AD_PrinterHW_Calibration> calibrations = dao.retrieveCalibrations(ctx, printerID.getRepoId(), trxName);
		dao.removeCalibrations(calibrations);

		// Delete media trays and sizes
		final List<I_AD_PrinterHW_MediaTray> trays = dao.retrieveMediaTrays(printerID);
		dao.removeMediaTrays(trays);

		final List<I_AD_PrinterHW_MediaSize> sizes = dao.retrieveMediaSizes(printerHW);
		dao.removeMediaSizes(sizes);
	}

	/**
	 * Needed because we want to order by ConfigHostKey and "unspecified" shall always be last.
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_AD_Printer_Config.COLUMNNAME_ConfigHostKey)
	public void trimBlankHostKeyToNull(@NonNull final I_AD_PrinterHW printerHW)
	{
		try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(printerHW))
		{
			final String normalizedString = StringUtils.trimBlankToNull(printerHW.getHostKey());
			printerHW.setHostKey(normalizedString);
		}
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
