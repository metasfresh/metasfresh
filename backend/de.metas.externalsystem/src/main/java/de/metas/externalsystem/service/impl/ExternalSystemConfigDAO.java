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

import de.metas.externalsystem.ExternalSystemConfigId;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.service.IExternalSystemConfigDAO;
import de.metas.rest_api.utils.MetasfreshId;
import org.adempiere.model.InterfaceWrapperHelper;

public class ExternalSystemConfigDAO implements IExternalSystemConfigDAO
{
	@Override
	public ExternalSystemParentConfig getById(ExternalSystemConfigId id)
	{
		final I_ExternalSystem_Config externalSystemConfig = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config.class);
		return ExternalSystemParentConfig.builder()
				.id(ExternalSystemConfigId.ofRepoId(externalSystemConfig.getExternalSystem_Config_ID()))
				.camelUrl(externalSystemConfig.getCamelURL())
				.name(externalSystemConfig.getName())
				.build();
	}

}
