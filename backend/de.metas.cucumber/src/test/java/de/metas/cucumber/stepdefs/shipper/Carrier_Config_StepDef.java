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

package de.metas.cucumber.stepdefs.shipper;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Carrier_Config;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public class Carrier_Config_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final M_Shipper_StepDefData shipperTable;

	/**
	 * Creates or updates a {@code Carrier_Config} record for the given shipper.
	 * Idempotent: if a config already exists for the shipper, all supplied columns are updated;
	 * columns absent from the data table leave the existing value unchanged (or apply the default on first creation).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Shipper_ID</b>            — (required) Cucumber identifier for the shipper.<br>
	 *   <b>ActorId</b>                 — (optional) nShift actor / account ID. Default: {@code 123}.<br>
	 *   <b>Base_url</b>                — (optional) nShift API base URL. Default: {@code https://notexistent.com}.<br>
	 *   <b>UserName</b>                — (optional) API username. Default: {@code user}.<br>
	 *   <b>Password</b>                — (optional) API password. Default: {@code password}.<br>
	 *   <b>ServiceLevel</b>            — (optional) Shipper service level token. Default: {@code serviceLevel}.<br>
	 *   <b>Client_Id</b>               — (optional) OAuth client ID. Default: none.<br>
	 *   <b>Client_Secret</b>           — (optional) OAuth client secret. Default: none.
	 * @cucumber.depends StepDefData: M_Shipper_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains Carrier_Configs:
	 *   | M_Shipper_ID    | ActorId | Base_url                | UserName | Password | ServiceLevel |
	 *   | nShift_coo_test | 123     | https://notexistent.com | user     | password | serviceLevel |
	 * </pre>
	 */
	@And("metasfresh contains Carrier_Configs:")
	public void add_Carrier_Configs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createOrUpdateCarrierConfig);
	}

	private void createOrUpdateCarrierConfig(@NonNull final DataTableRow row)
	{
		final ShipperId shipperId = row.getAsIdentifier(I_Carrier_Config.COLUMNNAME_M_Shipper_ID).lookupNotNullIdIn(shipperTable);

		@Nullable final I_Carrier_Config existingConfig = queryBL.createQueryBuilder(I_Carrier_Config.class)
				.addEqualsFilter(I_Carrier_Config.COLUMNNAME_M_Shipper_ID, shipperId)
				.create()
				.firstOnly();

		final I_Carrier_Config config = existingConfig != null
				? existingConfig
				: InterfaceWrapperHelper.newInstance(I_Carrier_Config.class);

		if (existingConfig == null)
		{
			config.setM_Shipper_ID(shipperId.getRepoId());
		}

		// For each field: use the data-table value if present; otherwise keep the existing DB value on update
		// or apply the default on creation.
		config.setActorId(row.getAsOptionalString(I_Carrier_Config.COLUMNNAME_ActorId)
				.orElse(existingConfig == null ? "123" : existingConfig.getActorId()));
		config.setBase_url(row.getAsOptionalString(I_Carrier_Config.COLUMNNAME_Base_url)
				.orElse(existingConfig == null ? "https://notexistent.com" : existingConfig.getBase_url()));
		config.setUserName(row.getAsOptionalString(I_Carrier_Config.COLUMNNAME_UserName)
				.orElse(existingConfig == null ? "user" : existingConfig.getUserName()));
		config.setPassword(row.getAsOptionalString(I_Carrier_Config.COLUMNNAME_Password)
				.orElse(existingConfig == null ? "password" : existingConfig.getPassword()));
		config.setServiceLevel(row.getAsOptionalString(I_Carrier_Config.COLUMNNAME_ServiceLevel)
				.orElse(existingConfig == null ? "serviceLevel" : existingConfig.getServiceLevel()));
		config.setClient_Id(row.getAsOptionalString(I_Carrier_Config.COLUMNNAME_Client_Id)
				.orElse(existingConfig == null ? null : existingConfig.getClient_Id()));
		config.setClient_Secret(row.getAsOptionalString(I_Carrier_Config.COLUMNNAME_Client_Secret)
				.orElse(existingConfig == null ? null : existingConfig.getClient_Secret()));

		InterfaceWrapperHelper.saveRecord(config);
	}
}
