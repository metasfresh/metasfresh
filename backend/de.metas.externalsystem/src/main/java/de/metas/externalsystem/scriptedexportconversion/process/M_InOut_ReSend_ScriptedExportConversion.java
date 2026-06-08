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

import de.metas.externalsystem.ExternalSystemErrorContext;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemExportStatusRepository;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemExportStatusService;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfig;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfigId;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionRepository;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionService;
import de.metas.inout.InOutId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;

import java.util.List;

/**
 * Per-record AD process that re-triggers the scripted-export-conversion for any config
 * that has a non-Sent (e.g. Error, Invalid, Pending) latest attempt for this M_InOut.
 *
 * <p>For each qualifying config:
 * <ol>
 *   <li>A new log row is created via {@link ExternalSystemExportStatusService#recordPendingAsResend} with
 *       {@code IsResend=Y} and status {@link de.metas.externalsystem.ExternalSystemExportStatus#Pending}.</li>
 *   <li>The scripted-export-conversion action is invoked via
 *       {@link ExternalSystemScriptedExportConversionService#executeInvokeScriptedExportConversionActionAndGetResult}
 *       with {@link ExternalSystemErrorContext#RESEND}.</li>
 * </ol>
 * Prior attempt rows are <em>never</em> mutated.
 *
 * <p>AD_Process_ID: 585633 (allocated from ID server 2026-06-09).
 */
public class M_InOut_ReSend_ScriptedExportConversion extends JavaProcess implements IProcessPrecondition
{
	private final ExternalSystemExportStatusRepository exportStatusRepository =
			SpringContextHolder.instance.getBean(ExternalSystemExportStatusRepository.class);

	private final ExternalSystemExportStatusService exportStatusService =
			SpringContextHolder.instance.getBean(ExternalSystemExportStatusService.class);

	private final ExternalSystemScriptedExportConversionRepository scriptedExportRepo =
			SpringContextHolder.instance.getBean(ExternalSystemScriptedExportConversionRepository.class);

	private final ExternalSystemScriptedExportConversionService scriptedExportService =
			SpringContextHolder.instance.getBean(ExternalSystemScriptedExportConversionService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.getSelectedIncludedRecords().size() > 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final int m_inout_id = getRecord_ID();
		final TableRecordReference sourceRecord = TableRecordReference.of(I_M_InOut.Table_Name, m_inout_id);

		final List<ExternalSystemScriptedExportConversionConfigId> configIds =
				exportStatusRepository.getConfigsWithNonSentAttemptBySourceRecord(sourceRecord);

		if (configIds.isEmpty())
		{
			return "No non-Sent scripted-export-conversion attempts found for this record — nothing to re-send.";
		}

		int triggered = 0;
		for (final ExternalSystemScriptedExportConversionConfigId configId : configIds)
		{
			// (a) create a new Pending row with IsResend=Y — does not mutate prior rows
			exportStatusService.recordPendingAsResend(configId, sourceRecord);

			// (b) invoke the scripted export conversion action with RESEND error context
			final ExternalSystemScriptedExportConversionConfig config = scriptedExportRepo.getById(configId);
			scriptedExportService.executeInvokeScriptedExportConversionActionAndGetResult(
					config,
					m_inout_id,
					ExternalSystemErrorContext.RESEND);

			triggered++;
		}

		return "@Processed@: " + triggered + " scripted-export-conversion config(s) re-triggered.";
	}
}
