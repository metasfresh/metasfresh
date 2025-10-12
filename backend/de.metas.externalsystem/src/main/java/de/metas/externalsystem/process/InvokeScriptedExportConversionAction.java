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
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfigId;
import de.metas.process.IProcessPreconditionsContext;
import org.adempiere.exceptions.AdempiereException;

import java.util.Map;

public class InvokeScriptedExportConversionAction extends InvokeExternalSystemProcess
{
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
		return null;
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
