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

@RequiredArgsConstructor
public class Carrier_Config_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final M_Shipper_StepDefData shipperTable;

	@And("metasfresh contains Carrier_Configs:")
	public void add_Carrier_Configs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createCarrier_Config);
	}

	private void createCarrier_Config(@NonNull final DataTableRow row)
	{
		final ShipperId shipperId = row.getAsIdentifier(I_Carrier_Config.COLUMNNAME_M_Shipper_ID).lookupNotNullIdIn(shipperTable);
		final I_Carrier_Config carrierConfig = queryBL.createQueryBuilder(I_Carrier_Config.class)
				.addEqualsFilter(I_Carrier_Config.COLUMNNAME_M_Shipper_ID, shipperId)
				.create()
				.firstOnly();

        if(carrierConfig == null)
		{
			final I_Carrier_Config config = InterfaceWrapperHelper.newInstance(I_Carrier_Config.class);
			config.setM_Shipper_ID(shipperId.getRepoId());
			config.setActorId("123");
			config.setBase_url("https://notexistent.com");
			config.setUserName("user");
			config.setPassword("password");
			config.setServiceLevel("serviceLevel");
			InterfaceWrapperHelper.save(config);
		}
	}
}
