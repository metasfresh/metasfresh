/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.rest_api.v2.externlasystem;

import com.google.common.collect.ImmutableMap;
import de.metas.common.externalsystem.JsonExternalSystemInfo;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfig;
import de.metas.organization.IOrgDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static de.metas.common.externalsystem.parameters.GRSSignumParameters.PARAM_BasePathForExportDirectories;

@Service
public class JsonExternalSystemRetriever
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@NonNull
	public JsonExternalSystemInfo retrieveExternalSystemInfo(@NonNull final ExternalSystemParentConfig parentConfig)
	{
		final IExternalSystemChildConfig childConfig = parentConfig.getChildConfig();

		final String orgCode = orgDAO.retrieveOrgValue(parentConfig.getOrgId());

		return JsonExternalSystemInfo.builder()
				.externalSystemConfigId(JsonMetasfreshId.of(parentConfig.getId().getRepoId()))
				.externalSystemName(JsonExternalSystemName.of(parentConfig.getType().getName()))
				.externalSystemChildConfigValue(childConfig.getValue())
				.orgCode(orgCode)
				.parameters(getParameters(parentConfig))
				.build();
	}

	@NonNull
	private static Map<String, String> getParameters(@NonNull final ExternalSystemParentConfig config)
	{
		switch (config.getType())
		{
			case GRSSignum:
				final ExternalSystemGRSSignumConfig grsConfig = ExternalSystemGRSSignumConfig.cast(config.getChildConfig());
				return getGRSSignumParameters(grsConfig);
			default:
				throw new AdempiereException("Unsupported externalSystemConfigType=" + config.getType());
		}
	}

	@NonNull
	private static Map<String, String> getGRSSignumParameters(@NonNull final ExternalSystemGRSSignumConfig grsSignumConfig)
	{
		final Map<String, String> parameters = new HashMap<>();

		if (grsSignumConfig.isCreateBPartnerFolders())
		{
			parameters.put(PARAM_BasePathForExportDirectories, grsSignumConfig.getBasePathForExportDirectories());
		}

		return ImmutableMap.copyOf(parameters);
	}
}
