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
import de.metas.cucumber.stepdefs.ValueAndName;

import de.metas.shipper.gateway.commons.model.CarrierProduct;
import de.metas.shipper.gateway.commons.model.CarrierProductRepository;
import de.metas.shipping.ShipperId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Carrier_Product;

@RequiredArgsConstructor
public class Carrier_Product_StepDef
{
	@NonNull private final CarrierProductRepository carrierProductRepository = SpringContextHolder.instance.getBean(CarrierProductRepository.class);

	@NonNull private final Carrier_Product_StepDefData carrierProductTable;
	@NonNull private final M_Shipper_StepDefData shipperTable;

	@And("metasfresh contains Carrier_Products:")
	public void add_Carrier_Products(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createCarrier_Product);
	}

	private void createCarrier_Product(@NonNull final DataTableRow row)
	{
		final ValueAndName valueAndName = row.suggestValueAndName();
		final ShipperId shipperId = shipperTable.getId(row.getAsIdentifier(I_Carrier_Product.COLUMNNAME_M_Shipper_ID));

		final CarrierProduct carrierProduct = carrierProductRepository.getOrCreateCarrierProduct(shipperId, valueAndName.getValue(), valueAndName.getName());

		carrierProductTable.put(row.getAsIdentifier(), carrierProduct);
	}
}
