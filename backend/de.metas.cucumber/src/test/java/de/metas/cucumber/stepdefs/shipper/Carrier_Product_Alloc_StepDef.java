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
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.shipping.CarrierProductId;
import de.metas.shipper.gateway.commons.model.CarrierProductGoodsTypeAllocRepository;
import de.metas.shipper.gateway.commons.model.CarrierProductServiceAllocRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Carrier_Product_GoodsType_Alloc;
import org.compiere.model.I_Carrier_Product_Service_Alloc;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class Carrier_Product_Alloc_StepDef
{
	@NonNull private final CarrierProductGoodsTypeAllocRepository goodsTypeAllocRepo = SpringContextHolder.instance.getBean(CarrierProductGoodsTypeAllocRepository.class);
	@NonNull private final CarrierProductServiceAllocRepository serviceAllocRepo = SpringContextHolder.instance.getBean(CarrierProductServiceAllocRepository.class);

	@NonNull private final Carrier_Product_StepDefData carrierProductTable;
	@NonNull private final Carrier_Goods_Type_StepDefData carrierGoodsTypeTable;
	@NonNull private final Carrier_Service_StepDefData carrierServiceTable;

	/**
	 * Asserts that each row's Carrier_Product+Carrier_Goods_Type pair has an active allocation record.
	 * <p>
	 * Required columns: {@code Carrier_Product_ID}, {@code Carrier_Goods_Type_ID}
	 * <p>
	 * Example:
	 * <pre>
	 * Then Carrier_Product_GoodsType_Allocs are found:
	 *   | Carrier_Product_ID | Carrier_Goods_Type_ID |
	 *   | cp1                | cgt1                  |
	 * </pre>
	 */
	@Then("Carrier_Product_GoodsType_Allocs are found:")
	public void verify_carrier_product_goodstype_allocs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::verifyGoodsTypeAlloc);
	}

	private void verifyGoodsTypeAlloc(@NonNull final DataTableRow row)
	{
		final CarrierProductId carrierProductId = row.getAsIdentifier(I_Carrier_Product_GoodsType_Alloc.COLUMNNAME_Carrier_Product_ID).lookupNotNullIdIn(carrierProductTable);
		final CarrierGoodsTypeId goodsTypeId = row.getAsIdentifier(I_Carrier_Product_GoodsType_Alloc.COLUMNNAME_Carrier_Goods_Type_ID).lookupNotNullIdIn(carrierGoodsTypeTable);
		assertThat(goodsTypeAllocRepo.exists(carrierProductId, goodsTypeId))
				.as("Carrier_Product_GoodsType_Alloc for carrierProductId=%s, goodsTypeId=%s", carrierProductId, goodsTypeId)
				.isTrue();
	}

	/**
	 * Asserts that each row's Carrier_Product+Carrier_Service pair has an active allocation record.
	 * <p>
	 * Required columns: {@code Carrier_Product_ID}, {@code Carrier_Service_ID}
	 * <p>
	 * Example:
	 * <pre>
	 * Then Carrier_Product_Service_Allocs are found:
	 *   | Carrier_Product_ID | Carrier_Service_ID |
	 *   | cp1                | cs1                |
	 *   | cp1                | cs2                |
	 * </pre>
	 */
	@Then("Carrier_Product_Service_Allocs are found:")
	public void verify_carrier_product_service_allocs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::verifyServiceAlloc);
	}

	private void verifyServiceAlloc(@NonNull final DataTableRow row)
	{
		final CarrierProductId carrierProductId = row.getAsIdentifier(I_Carrier_Product_Service_Alloc.COLUMNNAME_Carrier_Product_ID).lookupNotNullIdIn(carrierProductTable);
		final CarrierServiceId serviceId = row.getAsIdentifier(I_Carrier_Product_Service_Alloc.COLUMNNAME_Carrier_Service_ID).lookupNotNullIdIn(carrierServiceTable);
		assertThat(serviceAllocRepo.exists(carrierProductId, serviceId))
				.as("Carrier_Product_Service_Alloc for carrierProductId=%s, serviceId=%s", carrierProductId, serviceId)
				.isTrue();
	}
}
