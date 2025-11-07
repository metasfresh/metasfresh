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
import de.metas.inoutcandidate.CarrierGoodsType;
import de.metas.shipper.gateway.commons.model.CarrierGoodsTypeRepository;
import de.metas.shipping.ShipperId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Carrier_Goods_Type;

@RequiredArgsConstructor
public class Carrier_Goods_Type_StepDef
{
	@NonNull private final CarrierGoodsTypeRepository carrierGoodsTypeRepository = SpringContextHolder.instance.getBean(CarrierGoodsTypeRepository.class);

	@NonNull private final Carrier_Goods_Type_StepDefData carrierGoodsTypeTable;
	@NonNull private final M_Shipper_StepDefData shipperTable;

	@And("metasfresh contains Carrier_Goods_Types:")
	public void add_Carrier_Goods_Types(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createCarrier_Goods_Type);
	}

	private void createCarrier_Goods_Type(@NonNull final DataTableRow row)
	{
		final ValueAndName valueAndName = row.suggestValueAndName();
		final ShipperId shipperId = shipperTable.getId(row.getAsIdentifier(I_Carrier_Goods_Type.COLUMNNAME_M_Shipper_ID));

		final CarrierGoodsType carrierGoodsType = carrierGoodsTypeRepository.getOrCreateGoodsType(shipperId, valueAndName.getValue(), valueAndName.getName());

		carrierGoodsTypeTable.put(row.getAsIdentifier(), carrierGoodsType);
	}
}
