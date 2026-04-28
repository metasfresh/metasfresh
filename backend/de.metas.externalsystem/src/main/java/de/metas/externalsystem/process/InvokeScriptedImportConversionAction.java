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
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.externalservice.process.AlterExternalSystemServiceStatusAction;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedImportConversion;
import de.metas.externalsystem.scriptedimportconversion.ExternalSystemScriptedImportConversionConfig;
import de.metas.externalsystem.scriptedimportconversion.ExternalSystemScriptedImportConversionConfigId;
import de.metas.externalsystem.scriptedimportconversion.ExternalSystemScriptedImportConversionService;
import de.metas.process.IProcessPreconditionsContext;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.util.Map;

public class InvokeScriptedImportConversionAction extends AlterExternalSystemServiceStatusAction
{
	private final ExternalSystemScriptedImportConversionService externalSystemScriptedImportConversionService = SpringContextHolder.instance
			.getBean(ExternalSystemScriptedImportConversionService.class);

	@Override
	protected IExternalSystemChildConfigId getExternalChildConfigId()
	{
		final int id;

		if (this.childConfigId > 0)
		{
			id = this.childConfigId;
		}
		else
		{
			final IExternalSystemChildConfig childConfig = externalSystemConfigDAO.getChildByParentIdAndType(ExternalSystemParentConfigId.ofRepoId(getRecord_ID()), getExternalSystemType())
					.orElseThrow(() -> new AdempiereException("No childConfig found for type Invoke Scripted Import Conversion and parent config")
							.appendParametersToMessage()
							.setParameter("externalSystemParentConfigId", getRecord_ID()));

			id = childConfig.getId().getRepoId();
		}

		return ExternalSystemScriptedImportConversionConfigId.ofRepoId(id);
	}

	@Override
	protected Map<String, String> extractExternalSystemParameters(final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemScriptedImportConversionConfig externalSystemScriptedImportConversionConfig = ExternalSystemScriptedImportConversionConfig
				.cast(externalSystemParentConfig.getChildConfig());

		return externalSystemScriptedImportConversionService.getParameters(externalSystemScriptedImportConversionConfig);
	}

	@Override
	protected String getTabName()
	{
		return ExternalSystemType.ScriptedImportConversion.getValue();
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.ScriptedImportConversion;
	}

	@Override
	protected long getSelectedRecordCount(final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_ExternalSystem_Config_ScriptedImportConversion.Table_Name.equals(recordRef.getTableName()))
				.count();
	}
}
