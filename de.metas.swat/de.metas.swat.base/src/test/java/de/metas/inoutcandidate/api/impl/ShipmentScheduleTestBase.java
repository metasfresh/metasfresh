package de.metas.inoutcandidate.api.impl;

import static de.metas.util.Check.assumeNotNull;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_M_Product;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import de.metas.adempiere.model.I_M_Product;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BPartner;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.order.DeliveryRule;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Builder.ObtainVia;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.UtilityClass;
import lombok.experimental.Wither;

@UtilityClass
public class ShipmentScheduleTestBase
{
	private static final int WAREHOUSE_ID = 35;

	public static I_M_ShipmentSchedule createShipmentSchedule(final BigDecimal qty)
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setQtyOrdered_Calculated(qty);
		shipmentSchedule.setAD_User_ID(123);
		shipmentSchedule.setAD_Org_ID(0);
		shipmentSchedule.setAD_Table_ID(0);
		shipmentSchedule.setBPartnerAddress("address");
		shipmentSchedule.setC_BPartner_ID(0);
		shipmentSchedule.setBill_BPartner_ID(0);
		shipmentSchedule.setC_BPartner_Location_ID(0);
		shipmentSchedule.setQtyReserved(qty);
		shipmentSchedule.setDeliveryRule(DeliveryRule.AVAILABILITY.getCode());

		save(shipmentSchedule);

		return shipmentSchedule;
	}

	public ImmutableList<OlAndSched> setup(@NonNull final TestSetupSpec spec)
	{
		final Map<String, I_C_UOM> name2uom = new HashMap<>();
		for (final UomSpec uom : spec.getUoms())
		{
			final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
			uomRecord.setName(uom.getName());
			saveRecord(uomRecord);

			name2uom.put(uomRecord.getName(), uomRecord);
		}

		final Map<String, I_M_Product> value2product = new HashMap<>();
		for (final ProductSpec product : spec.getProducts())
		{
			final I_C_UOM uomRecord = assumeNotNull(name2uom.get(product.getUomValue()), "");

			final I_M_Product productRecord = newInstance(I_M_Product.class);
			productRecord.setValue(product.getValue());
			productRecord.setIsStocked(product.isStocked());
			productRecord.setProductType(product.getProductType());
			productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());

			saveRecord(productRecord);

			value2product.put(productRecord.getValue(), productRecord);
		}

		final Map<String, I_C_Order> value2order = new HashMap<>();
		for (final OrderSpec order : spec.getOrders())
		{
			final I_C_Order orderRecord = newInstance(I_C_Order.class);
			orderRecord.setDatePromised(TimeUtil.parseTimestamp("2018-10-19"));
			orderRecord.setM_Warehouse_ID(WAREHOUSE_ID);
			saveRecord(orderRecord);

			value2order.put(order.getValue(), orderRecord);
		}

		final Map<String, I_C_OrderLine> value2orderLine = new HashMap<>();
		for (final OrderLineSpec orderLine : spec.getOrderLines())
		{
			final I_M_Product productRecord = assumeNotNull(value2product.get(orderLine.getProduct()), "");
			final I_C_Order orderRecord = assumeNotNull(value2order.get(orderLine.getOrder()), "");

			final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
			orderLineRecord.setC_Order_ID(orderRecord.getC_Order_ID());
			orderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
			orderLineRecord.setQtyOrdered(orderLine.getQtyOrdered()); // <==
			orderLineRecord.setDatePromised(TimeUtil.parseTimestamp("2018-10-20"));
			saveRecord(orderLineRecord);

			value2orderLine.put(orderLine.getValue(), orderLineRecord);

		}

		for (final StockSpec stock : spec.getStocks())
		{
			final I_M_Product productRecord = value2product.get(stock.getProduct());

			final I_MD_Stock stockRecord = newInstance(I_MD_Stock.class);
			stockRecord.setM_Product(productRecord);
			stockRecord.setM_Warehouse_ID(WAREHOUSE_ID);
			stockRecord.setQtyOnHand(stock.getQtyStock());
			saveRecord(stockRecord);
		}

		final I_C_BPartner bPartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bPartnerRecord);

		final ImmutableList.Builder<OlAndSched> olAndScheds = ImmutableList.builder();
		for (final ShipmentScheduleSpec shipmentSchedule : spec.getShipmentSchedules())
		{
			final I_M_Product productRecord = assumeNotNull(value2product.get(shipmentSchedule.getProduct()), "");
			final I_C_Order orderRecord = value2order.get(shipmentSchedule.getOrder());
			final I_C_OrderLine orderLineRecord = value2orderLine.get(shipmentSchedule.getOrderLine());

			final I_M_ShipmentSchedule shipmentScheduleRecord = newInstance(I_M_ShipmentSchedule.class);
			shipmentScheduleRecord.setM_Product_ID(productRecord.getM_Product_ID());
			shipmentScheduleRecord.setIsClosed(false);
			shipmentScheduleRecord.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
			shipmentScheduleRecord.setQtyDelivered(BigDecimal.ONE);
			shipmentScheduleRecord.setQtyOrdered_Override(new BigDecimal("23"));
			shipmentScheduleRecord.setQtyOrdered_Calculated(new BigDecimal("24"));
			shipmentScheduleRecord.setM_Warehouse_ID(WAREHOUSE_ID);
			shipmentScheduleRecord.setC_Order_ID(orderRecord.getC_Order_ID());
			shipmentScheduleRecord.setC_OrderLine_ID(orderLineRecord.getC_OrderLine_ID());
			shipmentScheduleRecord.setM_Product_ID(productRecord.getM_Product_ID());
			shipmentScheduleRecord.setBPartnerAddress_Override("BPartnerAddress_Override"); // not flagged as mandatory in AD, but always set
			shipmentScheduleRecord.setAD_Table_ID(getTableId(I_C_OrderLine.class));
			shipmentScheduleRecord.setRecord_ID(orderLineRecord.getC_OrderLine_ID());

			shipmentScheduleRecord.setDeliveryRule(shipmentSchedule.getDeliveryRule().getCode()); // <==

			final OlAndSched olAndSched = OlAndSched.builder()
					.deliverRequest(orderLineRecord::getQtyOrdered)
					.shipmentSchedule(shipmentScheduleRecord)
					.build();
			olAndScheds.add(olAndSched);
		}

		return olAndScheds.build();
	}

	@Value
	public static class TestSetupSpec
	{
		Map<String, UomSpec> names2uoms;

		Map<String, ProductSpec> values2products;

		List<StockSpec> stocks;

		List<OrderSpec> orders;

		List<OrderLineSpec> orderLines;

		List<ShipmentScheduleSpec> shipmentSchedules;

		@Builder(toBuilder = true)
		private TestSetupSpec(
				@Singular @ObtainVia(method = "getUoms") List<UomSpec> uoms,
				@Singular @ObtainVia(method = "getProducts") List<ProductSpec> products,
				@Singular List<StockSpec> stocks,
				@Singular List<OrderSpec> orders,
				@Singular List<OrderLineSpec> orderLines,
				@Singular List<ShipmentScheduleSpec> shipmentSchedules)
		{
			this.names2uoms = Maps.uniqueIndex(uoms, UomSpec::getName);
			this.values2products = Maps.uniqueIndex(products, ProductSpec::getValue);
			this.stocks = stocks;
			this.orders = orders;
			this.orderLines = orderLines;
			this.shipmentSchedules = shipmentSchedules;
		}

		public TestSetupSpec withProduct(ProductSpec productSpec)
		{
			final HashMap<String, ProductSpec> modifiedProducts = new HashMap<>(values2products);
			modifiedProducts.put(productSpec.getValue(), productSpec);

			return this.toBuilder().clearProducts().products(modifiedProducts.values()).build();
		}

		private List<ProductSpec> getProducts()
		{
			return ImmutableList.copyOf(values2products.values());
		}

		private List<UomSpec> getUoms()
		{
			return ImmutableList.copyOf(names2uoms.values());
		}
	}

	@Value
	@Builder
	@Wither
	public static class UomSpec
	{
		String name;
	}

	@Value
	@Builder
	@Wither
	public static class ProductSpec
	{
		@NonNull
		String value;

		boolean stocked;

		@NonNull
		String uomValue;

		@Default
		String productType= X_M_Product.PRODUCTTYPE_Item;
	}

	@Value
	@Builder
	@Wither
	public static class StockSpec
	{
		@NonNull
		String product;

		@Default
		BigDecimal qtyStock = ZERO;
	}

	@Value
	@Builder
	@Wither
	public static class OrderSpec
	{
		@NonNull
		String value;
	}

	@Value
	@Builder
	@Wither
	public static class OrderLineSpec
	{
		@NonNull
		String value;

		@NonNull
		String order;

		@NonNull
		String product;

		@NonNull
		BigDecimal qtyOrdered;
	}

	@Value
	@Builder
	@Wither
	public static class ShipmentScheduleSpec
	{
		@NonNull
		String product;

		@Nullable
		String order;

		@Nullable
		String orderLine;

		@NonNull
		BigDecimal qtyOrdered;

		@NonNull
		DeliveryRule deliveryRule;
	}
}
