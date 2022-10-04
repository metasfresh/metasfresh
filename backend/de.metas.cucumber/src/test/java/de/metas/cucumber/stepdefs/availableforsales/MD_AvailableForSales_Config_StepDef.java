/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.availableforsales;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfig;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfigRepo;
import de.metas.material.cockpit.model.I_MD_AvailableForSales_Config;
import de.metas.organization.OrgId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class MD_AvailableForSales_Config_StepDef
{
	private final AvailableForSalesConfigRepo availableForSalesConfigRepo = SpringContextHolder.instance.getBean(AvailableForSalesConfigRepo.class);

	@When("validate MD_AvailableForSales_Config")
	public void validate_MD_AvailableForSales_Config(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			validateMDAvailableForSalesConfig(tableRow);
		}
	}

	private void validateMDAvailableForSalesConfig(@NonNull final Map<String, String> row)
	{
		final int clientIdParam = DataTableUtil.extractIntForColumnName(row, I_MD_AvailableForSales_Config.COLUMNNAME_AD_Client_ID);
		final int orgIdParam = DataTableUtil.extractIntForColumnName(row, I_MD_AvailableForSales_Config.COLUMNNAME_AD_Org_ID);

		final OrgId orgId = OrgId.ofRepoIdOrNull(orgIdParam);
		assertThat(orgId).isNotNull();

		final ClientId clientId = ClientId.ofRepoId(clientIdParam);
		assertThat(clientId).isNotNull();

		final AvailableForSalesConfig availableForSalesConfig = availableForSalesConfigRepo.getConfig(
				AvailableForSalesConfigRepo.ConfigQuery.builder()
						.orgId(orgId)
						.clientId(clientId)
						.build());

		assertThat(availableForSalesConfig).isNotNull();

		final int salesOrderLookBehindHours = DataTableUtil.extractIntForColumnName(row, I_MD_AvailableForSales_Config.COLUMNNAME_SalesOrderLookBehindHours);
		assertThat(availableForSalesConfig.getSalesOrderLookBehindHours()).isEqualTo(salesOrderLookBehindHours);

		final int shipmentDateLookAheadHours = DataTableUtil.extractIntForColumnName(row, I_MD_AvailableForSales_Config.COLUMNNAME_ShipmentDateLookAheadHours);
		assertThat(availableForSalesConfig.getShipmentDateLookAheadHours()).isEqualTo(shipmentDateLookAheadHours);
	}
}
