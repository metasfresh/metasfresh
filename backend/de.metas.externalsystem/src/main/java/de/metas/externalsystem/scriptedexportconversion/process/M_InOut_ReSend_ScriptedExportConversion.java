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
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;

import java.util.List;

/**
 * Re-triggers the scripted-export-conversion for any config with a non-Sent attempt for this M_InOut.
 */
public class M_InOut_ReSend_ScriptedExportConversion extends JavaProcess implements IProcessPrecondition
{
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
				scriptedExportService.getConfigsWithNonSentAttemptBySourceRecord(sourceRecord);

		int triggered = 0;
		for (final ExternalSystemScriptedExportConversionConfigId configId : configIds)
		{
			final ExternalSystemScriptedExportConversionConfig config =
					scriptedExportService.resolveConfigAndRecordPendingAsResend(configId, sourceRecord);

			scriptedExportService.executeInvokeScriptedExportConversionActionAndGetResult(
					config,
					m_inout_id,
					ExternalSystemInvocationContext.RESEND);

			triggered++;
		}

		return "@Processed@ #" + triggered;
	}
}
