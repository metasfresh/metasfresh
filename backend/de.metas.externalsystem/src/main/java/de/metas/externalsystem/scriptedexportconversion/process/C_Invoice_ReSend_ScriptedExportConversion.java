/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.externalsystem.scriptedexportconversion.process;

import de.metas.externalsystem.ExternalSystemInvocationContext;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfig;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfigId;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionService;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;

import java.util.List;

/**
 * Re-triggers the scripted-export-conversion for a selection of C_Invoice records.
 * When {@code IsOnlyNotSentSuccessfully=Y} only configs in error/invalid state are re-sent.
 * When {@code IsOnlyNotSentSuccessfully=N} configs in any terminal state (including already-Sent)
 * are re-triggered; in-flight and DontSend configs are always skipped.
 */
public class C_Invoice_ReSend_ScriptedExportConversion extends JavaProcess implements IProcessPrecondition
{
	private final ExternalSystemScriptedExportConversionService scriptedExportService =
			SpringContextHolder.instance.getBean(ExternalSystemScriptedExportConversionService.class);

	@Param(parameterName = "IsOnlyNotSentSuccessfully")
	private boolean isOnlyNotSentSuccessfully;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize().isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		// Collect IDs first to close the DB cursor before making external-service calls.
		final List<Integer> invoiceIds = retrieveSelectedRecordsQueryBuilder(I_C_Invoice.class).create().listIds();

		int triggered = 0;
		for (final int c_invoice_id : invoiceIds)
		{
			final TableRecordReference sourceRecord = TableRecordReference.of(I_C_Invoice.Table_Name, c_invoice_id);

			final List<ExternalSystemScriptedExportConversionConfigId> configIds = isOnlyNotSentSuccessfully
					? scriptedExportService.getResendableConfigsBySourceRecord(sourceRecord)
					: scriptedExportService.getMatchingConfigIdsBySourceRecord(sourceRecord);

			for (final ExternalSystemScriptedExportConversionConfigId configId : configIds)
			{
				final ExternalSystemScriptedExportConversionConfig config =
						scriptedExportService.resolveConfigAndRecordPendingAsResend(configId, sourceRecord);

				scriptedExportService.executeInvokeScriptedExportConversionActionAndGetResult(
						config,
						c_invoice_id,
						ExternalSystemInvocationContext.RESEND);

				triggered++;
			}
		}

		return "@Processed@ #" + triggered;
	}
}
