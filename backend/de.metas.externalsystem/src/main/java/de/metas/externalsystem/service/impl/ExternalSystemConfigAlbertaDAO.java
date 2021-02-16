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

package de.metas.externalsystem.service.impl;

import de.metas.externalsystem.ExternalSystemAlbertaConfigId;
import de.metas.externalsystem.ExternalSystemChildConfig;
import de.metas.externalsystem.ExternalSystemConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Alberta;
import de.metas.externalsystem.service.IExternalSystemConfigAlbertaDAO;
import de.metas.rest_api.utils.MetasfreshId;
import org.adempiere.model.InterfaceWrapperHelper;

public class ExternalSystemConfigAlbertaDAO implements IExternalSystemConfigAlbertaDAO
{
	@Override
	public ExternalSystemChildConfig getById(ExternalSystemAlbertaConfigId id)
	{
		final I_ExternalSystem_Config_Alberta config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_Alberta.class);
		return ExternalSystemChildConfig.builder()
				.id(ExternalSystemAlbertaConfigId.ofRepoId(config.getExternalSystem_Config_Alberta_ID()))
				.parentId(ExternalSystemConfigId.ofRepoId(config.getExternalSystem_Config_ID()))
				.apiKey(config.getApiKey())
				.baseUrl(config.getBaseURL())
				.name(config.getName())
				.tenant(config.getTenant())
				.build();
	}

}
