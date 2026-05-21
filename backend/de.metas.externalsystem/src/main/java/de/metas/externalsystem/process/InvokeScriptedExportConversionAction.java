/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.process;

import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfig;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfigId;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionService;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.util.Check;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.util.Map;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ERROR_CONTEXT;

public class InvokeScriptedExportConversionAction extends InvokeExternalSystemProcess
{
	private final ExternalSystemScriptedExportConversionService externalSystemScriptedExportConversionService = SpringContextHolder.instance.getBean(ExternalSystemScriptedExportConversionService.class);

	public static final String PARAM_Record_ID = "Record_ID";
	@Param(parameterName = PARAM_Record_ID)
	private String outboundDataProcessRecordId;

	// me03#29231 — for consolidated multi-source-order shipments, one M_InOut links to N DESADVs via
	// the EDI_Desadv_M_InOut junction. The M_InOut JSON-export view emits one row per (m_inout_id,
	// edi_desadv_id) pair, so the JSON-export process must be invoked with both filters. This param
	// is forwarded to that process by ExternalSystemScriptedExportConversionService#getParameters.
	// Optional here: only the M_InOut JSON-export process declares it as mandatory; other table
	// exports (e.g. invoice) ignore it.
	public static final String PARAM_EDI_Desadv_ID = "EDI_Desadv_ID";
	@Param(parameterName = PARAM_EDI_Desadv_ID)
	private int ediDesadvId;

	@Param(parameterName = PARAM_ERROR_CONTEXT)
	private String errorContext;


	@Override
	protected IExternalSystemChildConfigId getExternalChildConfigId()
	{
		if (this.childConfigId <= 0)
		{
			throw new AdempiereException("Child Config ID is mandatory for this process!");
		}

		return ExternalSystemScriptedExportConversionConfigId.ofRepoId(this.childConfigId);
	}

	@Override
	protected Map<String, String> extractExternalSystemParameters(final ExternalSystemParentConfig externalSystemParentConfig)
	{
		if (Check.isBlank(outboundDataProcessRecordId))
		{
			throw new AdempiereException("Outbound data process record id is missing.");
		}

		final ExternalSystemScriptedExportConversionConfig externalSystemScriptedExportConversionConfig = ExternalSystemScriptedExportConversionConfig
				.cast(externalSystemParentConfig.getChildConfig());

		return externalSystemScriptedExportConversionService.getParameters(
				externalSystemScriptedExportConversionConfig,
				getProcessInfo().getCtx(),
				outboundDataProcessRecordId,
				ediDesadvId,
				errorContext);
	}

	@Override
	protected String getTabName()
	{
		return ExternalSystemType.ScriptedExportConversion.getValue();
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.ScriptedExportConversion;
	}

	@Override
	protected long getSelectedRecordCount(final IProcessPreconditionsContext context)
	{
		// called when a document is completed, not from the External System window
		return 0;
	}
}
