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

import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.printing.HardwarePrinterId;
import de.metas.printing.HardwarePrinterRepository;
import de.metas.printing.OutputType;
import de.metas.printing.api.IPrintClientsBL;
import de.metas.printing.api.IPrinterBL;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_Printer_Config;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.util.Objects;
import java.util.Properties;

@Interceptor(I_AD_PrinterHW.class)
@RequiredArgsConstructor
public class AD_PrinterHW
{
	private static final Logger logger = LogManager.getLogger(AD_PrinterHW.class);
	private final IPrintClientsBL printClientsBL = Services.get(IPrintClientsBL.class);
	private final IPrinterBL printerBL = Services.get(IPrinterBL.class);
	private final HardwarePrinterRepository hardwarePrinterRepository;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_AD_PrinterHW record, final ModelChangeType timing)
	{
		if (timing.isNew())
		{
			setHostKey(record);
		}

		HardwarePrinterRepository.validateOnBeforeSave(record);
	}

	private void setHostKey(final I_AD_PrinterHW printerHW)
	{
		if (Check.isNotBlank(printerHW.getHostKey()))
		{
			return; // HostKey was already set, nothing to do
		}

		if (!Objects.equals(OutputType.Queue, OutputType.ofNullableCode(printerHW.getOutputType())))
		{
			return; // no hostkey needed
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(printerHW);
		final String hostKey = printClientsBL.getHostKeyOrNull(ctx);
		if (Check.isBlank(hostKey))
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
		final HardwarePrinterId hardwarePrinterId = HardwarePrinterId.ofRepoId(printerHW.getAD_PrinterHW_ID());
		hardwarePrinterRepository.deleteCalibrations(hardwarePrinterId);
		hardwarePrinterRepository.deleteMediaTrays(hardwarePrinterId);
		hardwarePrinterRepository.deleteMediaSizes(hardwarePrinterId);
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
		printerBL.createConfigAndDefaultPrinterMatching(printerHW);
	}
}
