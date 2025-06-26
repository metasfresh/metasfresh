/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.postgrest;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.organization.OrgId;
import de.metas.postgrest.config.PostgRESTConfig;
import de.metas.postgrest.config.PostgRESTConfigRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_S_PostgREST_Config;

import java.util.Optional;

public class S_PostgREST_Config_StepDef
{
	private final PostgRESTConfigRepository postgRESTConfigRepository = SpringContextHolder.instance.getBean(PostgRESTConfigRepository.class);

	@And("metasfresh contains S_PostgREST_Config_StepDef")
	public void createPostgrestConfigs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createPostgrestConfig);
	}

	private void createPostgrestConfig(@NonNull final DataTableRow dataTableRow)
	{
		final OrgId orgId = dataTableRow.getAsIdentifier(I_S_PostgREST_Config.COLUMNNAME_AD_Org_ID).getAsId(OrgId.class);
		final Optional<PostgRESTConfig> configFor = postgRESTConfigRepository.getOptionalConfigFor(orgId);

		final PostgRESTConfig.PostgRESTConfigBuilder builder = configFor
				.map(PostgRESTConfig::toBuilder)
				.orElseGet(PostgRESTConfig::builder);

		builder.orgId(orgId)
				.baseURL(dataTableRow.getAsString(I_S_PostgREST_Config.COLUMNNAME_Base_url));
		dataTableRow.getAsOptionalDuration(I_S_PostgREST_Config.COLUMNNAME_Read_timeout).ifPresent(builder::readTimeout);
		dataTableRow.getAsOptionalDuration(I_S_PostgREST_Config.COLUMNNAME_Connection_timeout).ifPresent(builder::connectionTimeout);

		postgRESTConfigRepository.save(builder.build());
	}
}
