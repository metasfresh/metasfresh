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
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.shipping.CarrierProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_Carrier_Service;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for C_Order carrier-product / goods-type / service fields.
 * Tests the {@code C_Order} interceptor that auto-sets {@code Carrier_Goods_Type_ID}
 * when {@code Carrier_Product_ID} is set, and clears derived values when the product changes.
 */
@RequiredArgsConstructor
public class C_Order_Carrier_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final Carrier_Product_StepDefData carrierProductTable;
	@NonNull private final Carrier_Goods_Type_StepDefData carrierGoodsTypeTable;
	@NonNull private final Carrier_Service_StepDefData carrierServiceTable;

	/**
	 * Sets {@code Carrier_Product_ID} on a draft {@code C_Order} (identified by its scenario identifier)
	 * and saves the record so the {@code C_Order} interceptor fires.
	 * <p>
	 * Required columns: {@code C_Order_ID} (identifier), {@code Carrier_Product_ID} (identifier or {@code null}).
	 * <p>
	 * Example:
	 * <pre>
	 * When C_Order carrier product is set:
	 *   | C_Order_ID | Carrier_Product_ID |
	 *   | so1        | cp1                |
	 * </pre>
	 */
	@And("C_Order carrier product is set:")
	public void set_carrier_product_on_order(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::setCarrierProduct);
	}

	private void setCarrierProduct(@NonNull final DataTableRow row)
	{
		final I_C_Order order = row.getAsIdentifier(I_C_Order.COLUMNNAME_C_Order_ID).lookupNotNullIn(orderTable);
		InterfaceWrapperHelper.refresh(order);

		final CarrierProductId carrierProductId = row.getAsOptionalIdentifier(I_C_Order.COLUMNNAME_Carrier_Product_ID)
				.map(id -> id.lookupNotNullIdIn(carrierProductTable))
				.orElse(null);

		order.setCarrier_Product_ID(CarrierProductId.toRepoId(carrierProductId));
		InterfaceWrapperHelper.saveRecord(order);
		InterfaceWrapperHelper.refresh(order);

		// Store back in case subsequent steps need the updated record.
		orderTable.putOrReplace(row.getAsIdentifier(I_C_Order.COLUMNNAME_C_Order_ID), order);
	}

	/**
	 * Creates one {@code C_Order_Carrier_Service} bridge row per DataTable row.
	 * Used in scenarios that need pre-existing bridge rows so the "clear on change" behaviour can be verified.
	 * <p>
	 * Required columns: {@code C_Order_ID} (identifier), {@code Carrier_Service_ID} (identifier).
	 * <p>
	 * Example:
	 * <pre>
	 * And metasfresh contains C_Order_Carrier_Services:
	 *   | C_Order_ID | Carrier_Service_ID |
	 *   | so1        | cs1                |
	 * </pre>
	 */
	@And("metasfresh contains C_Order_Carrier_Services:")
	public void create_order_carrier_services(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createOrderCarrierService);
	}

	private void createOrderCarrierService(@NonNull final DataTableRow row)
	{
		final I_C_Order order = row.getAsIdentifier(I_C_Order.COLUMNNAME_C_Order_ID).lookupNotNullIn(orderTable);
		final CarrierServiceId serviceId = row.getAsIdentifier(I_C_Order_Carrier_Service.COLUMNNAME_Carrier_Service_ID).lookupNotNullIdIn(carrierServiceTable);

		final I_C_Order_Carrier_Service record = InterfaceWrapperHelper.newInstance(I_C_Order_Carrier_Service.class);
		record.setC_Order_ID(order.getC_Order_ID());
		record.setCarrier_Service_ID(serviceId.getRepoId());
		InterfaceWrapperHelper.saveRecord(record);
	}

	/**
	 * Asserts the {@code Carrier_Goods_Type_ID} on the order identified by the given DataTable rows.
	 * Accepts {@code null} to assert that the field is empty.
	 * <p>
	 * Required columns: {@code C_Order_ID} (identifier), {@code Carrier_Goods_Type_ID} (identifier or {@code null}).
	 * <p>
	 * Example:
	 * <pre>
	 * Then C_Order carrier goods type is:
	 *   | C_Order_ID | Carrier_Goods_Type_ID |
	 *   | so1        | cgt1                  |
	 * </pre>
	 */
	@Then("C_Order carrier goods type is:")
	public void verify_carrier_goods_type_on_order(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::verifyCarrierGoodsType);
	}

	private void verifyCarrierGoodsType(@NonNull final DataTableRow row)
	{
		final I_C_Order order = row.getAsIdentifier(I_C_Order.COLUMNNAME_C_Order_ID).lookupNotNullIn(orderTable);
		InterfaceWrapperHelper.refresh(order);

		final CarrierGoodsTypeId actualGoodsTypeId = CarrierGoodsTypeId.ofRepoIdOrNull(order.getCarrier_Goods_Type_ID());

		final CarrierGoodsTypeId expectedGoodsTypeId = row.getAsOptionalIdentifier(I_C_Order.COLUMNNAME_Carrier_Goods_Type_ID)
				.map(id -> id.lookupIdIn(carrierGoodsTypeTable))
				.orElse(null);

		assertThat(actualGoodsTypeId)
				.as("Carrier_Goods_Type_ID on order %s", row.getAsIdentifier(I_C_Order.COLUMNNAME_C_Order_ID))
				.isEqualTo(expectedGoodsTypeId);
	}

	/**
	 * Asserts that the given {@code C_Order} has no {@code C_Order_Carrier_Service} rows.
	 * <p>
	 * Required columns: {@code C_Order_ID} (identifier).
	 * <p>
	 * Example:
	 * <pre>
	 * Then C_Order has no carrier services assigned:
	 *   | C_Order_ID |
	 *   | so1        |
	 * </pre>
	 */
	@Then("C_Order has no carrier services assigned:")
	public void verify_no_carrier_services_on_order(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::verifyNoCarrierServices);
	}

	private void verifyNoCarrierServices(@NonNull final DataTableRow row)
	{
		final I_C_Order order = row.getAsIdentifier(I_C_Order.COLUMNNAME_C_Order_ID).lookupNotNullIn(orderTable);
		final int count = queryBL.createQueryBuilder(I_C_Order_Carrier_Service.class)
				.addEqualsFilter(I_C_Order_Carrier_Service.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.create()
				.count();
		assertThat(count)
				.as("C_Order_Carrier_Service count for order %s", row.getAsIdentifier(I_C_Order.COLUMNNAME_C_Order_ID))
				.isZero();
	}
}
