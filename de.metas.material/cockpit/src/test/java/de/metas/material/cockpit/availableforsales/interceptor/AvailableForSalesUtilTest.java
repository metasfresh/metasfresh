package de.metas.material.cockpit.availableforsales.interceptor;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Color;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.material.cockpit.availableforsales.AvailableForSalesConfig;
import de.metas.material.cockpit.availableforsales.AvailableForSalesRepository;
import de.metas.material.cockpit.availableforsales.interceptor.AvailableForSalesUtil.CheckAvailableForSalesRequest;
import de.metas.material.cockpit.availableforsales.model.I_C_OrderLine;
import de.metas.material.cockpit.model.I_MD_Available_For_Sales_QueryResult;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.util.ColorId;

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

	private static final BigDecimal FOUR = new BigDecimal("4");
	private static final BigDecimal THREE = new BigDecimal("3");
	private static final BigDecimal SEVEN = new BigDecimal("7");

	private AvailableForSalesUtil availableForSalesUtil;
	private ColorId colorId;
	private OrderLineId orderLineId;
	private ProductId productId;
	private CheckAvailableForSalesRequest request;
	private AvailableForSalesConfig config;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_AD_Color colorRecord = newInstance(I_AD_Color.class);
		saveRecord(colorRecord);
		colorId = ColorId.ofRepoId(colorRecord.getAD_Color_ID());

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		saveRecord(productRecord);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
		orderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
		orderLineRecord.setQtyOrdered(THREE);
		orderLineRecord.setQtyEntered(THREE);
		orderLineRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		saveRecord(orderLineRecord);
		orderLineId = OrderLineId.ofRepoId(orderLineRecord.getC_OrderLine_ID());

		config = AvailableForSalesConfig.builder()
				.featureEnabled(true)
				.insufficientQtyAvailableForSalesColorId(colorId)
				.salesOrderLookBehindHours(3)
				.shipmentDateLookAheadHours(72)
				.runAsync(true)
				.build();

		request = CheckAvailableForSalesRequest.builder()
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.orderLineId(orderLineId)
				.preparationDate(TimeUtil.parseTimestamp("2019-04-04"))
				.productId(productId)
				.build();

		availableForSalesUtil = new AvailableForSalesUtil(new AvailableForSalesRepository());
	}

	@Test
	void retrieveDataAndUpdateOrderLines_2resultRecords_0withStock()
	{
		createQueryResultRecord(THREE, null/* qtyOnHandStock */);
		createQueryResultRecord(FOUR, null/* qtyOnHandStock */);

		// invoke the method under test
		availableForSalesUtil.retrieveDataAndUpdateOrderLines(ImmutableList.of(request), config);

		final I_C_OrderLine updatedOrderRecord = load(orderLineId, I_C_OrderLine.class);

		// we have -7 in foreseeable shipments 3 of which are in in updatedOrderRecord
		assertThat(updatedOrderRecord.getQtyAvailableForSales()).isEqualByComparingTo(FOUR.negate());
		assertThat(updatedOrderRecord.getInsufficientQtyAvailableForSalesColor_ID()).isEqualTo(colorId.getRepoId());
	}

	@Test
	void retrieveDataAndUpdateOrderLines_1resultRecord_0withtock()
	{
		createQueryResultRecord(THREE, null/* qtyOnHandStock */);

		// invoke the method under test
		availableForSalesUtil.retrieveDataAndUpdateOrderLines(ImmutableList.of(request), config);

		final I_C_OrderLine updatedOrderRecord = load(orderLineId, I_C_OrderLine.class);

		// we have -7 in foreseeable shipments 3 of which are in in updatedOrderRecord
		assertThat(updatedOrderRecord.getQtyAvailableForSales()).isZero();
		assertThat(updatedOrderRecord.getInsufficientQtyAvailableForSalesColor_ID()).isEqualTo(colorId.getRepoId());
	}

	@Test
	void retrieveDataAndUpdateOrderLines_2resultRecords_1withStock()
	{
		createQueryResultRecord(THREE/* qtyToBeShipped */, null/* qtyOnHandStock */);
		createQueryResultRecord(THREE/* qtyToBeShipped */, FOUR/* qtyOnHandStock */);

		// invoke the method under test
		availableForSalesUtil.retrieveDataAndUpdateOrderLines(ImmutableList.of(request), config);

		final I_C_OrderLine updatedOrderRecord = load(orderLineId, I_C_OrderLine.class);

		// we have -6 in foreseeable shipments 3 of which are in in updatedOrderRecord; we have 4 on stock => -6+3+4=1
		assertThat(updatedOrderRecord.getQtyAvailableForSales()).isEqualByComparingTo(ONE);
		assertThat(updatedOrderRecord.getInsufficientQtyAvailableForSalesColor_ID()).isEqualTo(colorId.getRepoId());
	}

	@Test
	void retrieveDataAndUpdateOrderLines_4()
	{
		createQueryResultRecord(THREE/* qtyToBeShipped */, null/* qtyOnHandStock */);
		createQueryResultRecord(THREE/* qtyToBeShipped */, TEN/* qtyOnHandStock */);

		availableForSalesUtil.retrieveDataAndUpdateOrderLines(ImmutableList.of(request), config);

		final I_C_OrderLine updatedOrderRecord = load(orderLineId, I_C_OrderLine.class);

		// we have -6 in foreseeable shipments 3 of which are in in updatedOrderRecord; we have 10 on stock => -6+3+10=7
		assertThat(updatedOrderRecord.getQtyAvailableForSales()).isEqualByComparingTo(SEVEN);
		assertThat(updatedOrderRecord.getInsufficientQtyAvailableForSalesColor()).isNull();
	}

	private void createQueryResultRecord(
			@Nullable final BigDecimal qtyToBeShipped,
			@Nullable final BigDecimal qtyOnHandStock)
	{
		final I_MD_Available_For_Sales_QueryResult resultRecord1 = newInstance(I_MD_Available_For_Sales_QueryResult.class);

		// IMPORTANT: when the rubber hits the road within AvailableForSalesRepository,
		// then this needs to be the index (starting with 0) of the respective AvailableForSalesQuery
		resultRecord1.setQueryNo(0);

		resultRecord1.setM_Product_ID(productId.getRepoId());
		resultRecord1.setStorageAttributesKey(AttributesKey.NONE.getAsString());
		resultRecord1.setQtyToBeShipped(qtyToBeShipped);
		resultRecord1.setQtyOnHandStock(qtyOnHandStock);
		saveRecord(resultRecord1);
	}

}
