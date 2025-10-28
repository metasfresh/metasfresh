package de.metas.material.cockpit.availableforsales.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfig;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfigRepo;
import de.metas.material.cockpit.availableforsales.AvailableForSalesRepository;
import de.metas.material.cockpit.availableforsales.AvailableForSalesService;
import de.metas.material.cockpit.availableforsales.interceptor.AvailableForSalesUtil.CheckAvailableForSalesRequest;
import de.metas.material.cockpit.availableforsales.model.I_C_OrderLine;
import de.metas.material.cockpit.model.I_MD_Available_For_Sales_QueryResult;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.ColorId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Color;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2019 metas GmbH
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

class AvailableForSalesUtilTest
{

	private static final BigDecimal THREE = new BigDecimal("3");
	private static final BigDecimal FOUR = new BigDecimal("4");
	private static final BigDecimal SIX = new BigDecimal("6");
	private static final BigDecimal SEVEN = new BigDecimal("7");

	private AvailableForSalesUtil availableForSalesUtil;
	private ColorId colorId;
	private OrderLineId orderLineId;
	private OrderLineId qtyPerWarehouseOrderLineId;
	private ProductId productId;
	private WarehouseId warehouseId;
	private WarehouseId qtyPerWarehouseWarehouseId;
	private UomId uomId;
	private CheckAvailableForSalesRequest request;
	private CheckAvailableForSalesRequest qtyPerWarehouseRequest;
	private AvailableForSalesConfig config;
	private AvailableForSalesConfig qtyPerWarehouseConfig;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_AD_Color colorRecord = newInstance(I_AD_Color.class);
		saveRecord(colorRecord);
		colorId = ColorId.ofRepoId(colorRecord.getAD_Color_ID());

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
		uomId = UomId.ofRepoId(uomRecord.getC_UOM_ID());

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		saveRecord(productRecord);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final I_M_Warehouse warehouseRecord = newInstance(I_M_Warehouse.class);
		saveRecord(warehouseRecord);
		warehouseId = WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID());

		final I_M_Warehouse secondWarehouseRecord = newInstance(I_M_Warehouse.class);
		saveRecord(secondWarehouseRecord);
		qtyPerWarehouseWarehouseId = WarehouseId.ofRepoId(secondWarehouseRecord.getM_Warehouse_ID());

		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
		orderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
		orderLineRecord.setQtyOrdered(THREE);
		orderLineRecord.setQtyEntered(THREE);
		orderLineRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		orderLineRecord.setAD_Org_ID(OrgId.MAIN.getRepoId());
		orderLineRecord.setM_Warehouse_ID(warehouseId.getRepoId());
		saveRecord(orderLineRecord);
		orderLineId = OrderLineId.ofRepoId(orderLineRecord.getC_OrderLine_ID());

		final I_C_OrderLine qtyPerWarehouseOrderLineRecord = newInstance(I_C_OrderLine.class);
		qtyPerWarehouseOrderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
		qtyPerWarehouseOrderLineRecord.setQtyOrdered(THREE);
		qtyPerWarehouseOrderLineRecord.setQtyEntered(THREE);
		qtyPerWarehouseOrderLineRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		qtyPerWarehouseOrderLineRecord.setAD_Org_ID(OrgId.MAIN.getRepoId());
		qtyPerWarehouseOrderLineRecord.setM_Warehouse_ID(qtyPerWarehouseWarehouseId.getRepoId());
		saveRecord(qtyPerWarehouseOrderLineRecord);
		qtyPerWarehouseOrderLineId = OrderLineId.ofRepoId(qtyPerWarehouseOrderLineRecord.getC_OrderLine_ID());

		config = AvailableForSalesConfig.builder()
				.featureEnabled(true)
				.insufficientQtyAvailableForSalesColorId(colorId)
				.salesOrderLookBehindHours(3)
				.shipmentDateLookAheadHours(72)
				.runAsync(true)
				.build();

		qtyPerWarehouseConfig = AvailableForSalesConfig.builder()
				.featureEnabled(true)
				.insufficientQtyAvailableForSalesColorId(colorId)
				.salesOrderLookBehindHours(3)
				.shipmentDateLookAheadHours(72)
				.runAsync(true)
				.qtyPerWarehouse(true)
				.build();

		request = CheckAvailableForSalesRequest.builder()
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.orderLineId(orderLineId)
				.preparationDate(TimeUtil.parseTimestamp("2019-04-04"))
				.productId(productId)
				.warehouseId(warehouseId)
				.build();

		qtyPerWarehouseRequest = CheckAvailableForSalesRequest.builder()
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.orderLineId(qtyPerWarehouseOrderLineId)
				.preparationDate(TimeUtil.parseTimestamp("2019-04-04"))
				.productId(productId)
				.warehouseId(qtyPerWarehouseWarehouseId)
				.build();

		availableForSalesUtil = new AvailableForSalesUtil(new AvailableForSalesService(new AvailableForSalesConfigRepo(), new AvailableForSalesRepository()));
	}

	@Test
	void retrieveDataAndUpdateOrderLines_2resultRecords_0withStock()
	{
		createQueryResultRecord(THREE, null/* qtyOnHandStock */, null);
		createQueryResultRecord(FOUR, null/* qtyOnHandStock */, null);

		// invoke the method under test
		availableForSalesUtil.retrieveDataAndUpdateOrderLines(ImmutableList.of(request), config, OrgId.MAIN);

		final I_C_OrderLine updatedOrderRecord = load(orderLineId, I_C_OrderLine.class);

		// we have -7 in foreseeable shipments 3 of which are in in updatedOrderRecord
		assertThat(updatedOrderRecord.getQtyAvailableForSales()).isEqualByComparingTo(FOUR.negate());
		assertThat(updatedOrderRecord.getInsufficientQtyAvailableForSalesColor_ID()).isEqualTo(colorId.getRepoId());
	}

	@Test
	void retrieveDataAndUpdateOrderLines_1resultRecord_0withtock()
	{
		createQueryResultRecord(THREE, null/* qtyOnHandStock */, null);

		// invoke the method under test
		availableForSalesUtil.retrieveDataAndUpdateOrderLines(ImmutableList.of(request), config, OrgId.MAIN);

		final I_C_OrderLine updatedOrderRecord = load(orderLineId, I_C_OrderLine.class);

		// we have -7 in foreseeable shipments 3 of which are in in updatedOrderRecord
		assertThat(updatedOrderRecord.getQtyAvailableForSales()).isZero();
		assertThat(updatedOrderRecord.getInsufficientQtyAvailableForSalesColor_ID()).isEqualTo(colorId.getRepoId());
	}

	@Test
	void retrieveDataAndUpdateOrderLines_2resultRecords_1withStock()
	{
		createQueryResultRecord(THREE/* qtyToBeShipped */, null/* qtyOnHandStock */, null);
		createQueryResultRecord(THREE/* qtyToBeShipped */, FOUR/* qtyOnHandStock */, null);

		// invoke the method under test
		availableForSalesUtil.retrieveDataAndUpdateOrderLines(ImmutableList.of(request), config, OrgId.MAIN);

		final I_C_OrderLine updatedOrderRecord = load(orderLineId, I_C_OrderLine.class);

		// we have -6 in foreseeable shipments 3 of which are in in updatedOrderRecord; we have 4 on stock => -6+3+4=1
		assertThat(updatedOrderRecord.getQtyAvailableForSales()).isEqualByComparingTo(ONE);
		assertThat(updatedOrderRecord.getInsufficientQtyAvailableForSalesColor_ID()).isEqualTo(colorId.getRepoId());
	}

	@Test
	void retrieveDataAndUpdateOrderLines_4()
	{
		createQueryResultRecord(THREE/* qtyToBeShipped */, null/* qtyOnHandStock */, null);
		createQueryResultRecord(THREE/* qtyToBeShipped */, TEN/* qtyOnHandStock */, null);

		availableForSalesUtil.retrieveDataAndUpdateOrderLines(ImmutableList.of(request), config, OrgId.MAIN);

		final I_C_OrderLine updatedOrderRecord = load(orderLineId, I_C_OrderLine.class);

		// we have -6 in foreseeable shipments 3 of which are in in updatedOrderRecord; we have 10 on stock => -6+3+10=7
		assertThat(updatedOrderRecord.getQtyAvailableForSales()).isEqualByComparingTo(SEVEN);
		assertThat(updatedOrderRecord.getInsufficientQtyAvailableForSalesColor()).isNull();
	}

	@Test
	void retrieveDataAndUpdateOrderLines_isQtyPerWarehouse_1()
	{
		createQueryResultRecord(THREE/* qtyToBeShipped */, null/* qtyOnHandStock */, warehouseId);
		createQueryResultRecord(SEVEN/* qtyToBeShipped */, TEN/* qtyOnHandStock */, qtyPerWarehouseWarehouseId);

		availableForSalesUtil.retrieveDataAndUpdateOrderLines(ImmutableList.of(qtyPerWarehouseRequest), qtyPerWarehouseConfig, OrgId.MAIN);

		final I_C_OrderLine updatedOrderRecord = load(qtyPerWarehouseOrderLineId, I_C_OrderLine.class);

		// we have -7 in foreseeable shipments 3 of which are in updatedOrderRecord; we have 10 on stock => -7+3+10=6
		assertThat(updatedOrderRecord.getQtyAvailableForSales()).isEqualByComparingTo(SIX);
		assertThat(updatedOrderRecord.getInsufficientQtyAvailableForSalesColor()).isNull();
	}

	@Test
	void retrieveDataAndUpdateOrderLines_isQtyPerWarehouse_2()
	{
		createQueryResultRecord(THREE/* qtyToBeShipped */, null/* qtyOnHandStock */, warehouseId);
		createQueryResultRecord(SEVEN/* qtyToBeShipped */, ONE/* qtyOnHandStock */, qtyPerWarehouseWarehouseId);

		availableForSalesUtil.retrieveDataAndUpdateOrderLines(ImmutableList.of(qtyPerWarehouseRequest), qtyPerWarehouseConfig, OrgId.MAIN);

		final I_C_OrderLine updatedOrderRecord = load(qtyPerWarehouseOrderLineId, I_C_OrderLine.class);

		// we have -7 in foreseeable shipments 3 of which are in updatedOrderRecord; we have 1 on stock => -7+3+1=-3
		assertThat(updatedOrderRecord.getQtyAvailableForSales()).isEqualByComparingTo(THREE.negate());
		assertThat(updatedOrderRecord.getInsufficientQtyAvailableForSalesColor_ID()).isEqualTo(colorId.getRepoId());
	}
	
	private void createQueryResultRecord(
			@Nullable final BigDecimal qtyToBeShipped,
			@Nullable final BigDecimal qtyOnHandStock,
			@Nullable final WarehouseId warehouseId)
	{
		final I_MD_Available_For_Sales_QueryResult resultRecord1 = newInstance(I_MD_Available_For_Sales_QueryResult.class);

		// IMPORTANT: when the rubber hits the road within AvailableForSalesRepository,
		// then this needs to be the index (starting with 0) of the respective AvailableForSalesQuery
		resultRecord1.setQueryNo(0);

		resultRecord1.setM_Product_ID(productId.getRepoId());
		resultRecord1.setStorageAttributesKey(AttributesKey.NONE.getAsString());
		resultRecord1.setQtyToBeShipped(qtyToBeShipped);
		resultRecord1.setQtyOnHandStock(qtyOnHandStock);
		resultRecord1.setC_UOM_ID(uomId.getRepoId());
		resultRecord1.setM_Warehouse_ID(Optional.ofNullable(warehouseId)
				.map(WarehouseId::getRepoId)
				.orElse(this.warehouseId.getRepoId()));
		saveRecord(resultRecord1);
	}

}
