/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

import de.metas.externalsystem.ExternalSystemAlbertaConfigId;
import de.metas.externalsystem.ExternalSystemChildConfig;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Alberta;
import de.metas.externalsystem.service.IExternalSystemConfigAlbertaDAO;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.util.Services;
import lombok.NonNull;

public class InvokeAlbertaAction extends InvokeExternalSystemProcess
{
	public final IExternalSystemConfigAlbertaDAO externalSystemConfigDAO = Services.get(IExternalSystemConfigAlbertaDAO.class);

	protected long getSelectedRecordCount(final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_ExternalSystem_Config_Alberta.Table_Name.equals(recordRef.getTableName()))
				.count();
	}

	@Override
	protected ExternalSystemChildConfig getExternalConfig()
	{
		return externalSystemConfigDAO.getById(getRecordId());
	}

	private ExternalSystemAlbertaConfigId getRecordId()
	{
		final Integer id = this.configId != null ? this.configId.getValue() : getSelectedIncludedRecordIds(I_ExternalSystem_Config_Alberta.class).stream().findFirst().get();
		return ExternalSystemAlbertaConfigId.ofRepoId(id);
	}

	@NonNull
	protected String getTabName()
	{
		return "Alberta";
	}
}
