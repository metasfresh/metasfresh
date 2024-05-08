package de.metas.inoutcandidate.api.impl;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.inout.util.DeliveryLineCandidate;
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate.CompleteStatus;
import org.adempiere.inout.util.ShipmentSchedulesDuringUpdate;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.api.impl.shipmentschedule_test_specs.OrderLineSpec;
import de.metas.inoutcandidate.api.impl.shipmentschedule_test_specs.OrderSpec;
import de.metas.inoutcandidate.api.impl.shipmentschedule_test_specs.PickFromOrderBOMLineSpec;
import de.metas.inoutcandidate.api.impl.shipmentschedule_test_specs.PickFromOrderSpec;
import de.metas.inoutcandidate.api.impl.shipmentschedule_test_specs.ProductSpec;
import de.metas.inoutcandidate.api.impl.shipmentschedule_test_specs.ShipmentScheduleSpec;
import de.metas.inoutcandidate.api.impl.shipmentschedule_test_specs.StockSpec;
import de.metas.inoutcandidate.api.impl.shipmentschedule_test_specs.TestSetupSpec;
import de.metas.inoutcandidate.api.impl.shipmentschedule_test_specs.TestSetupSpecHelper;
import de.metas.inoutcandidate.api.impl.shipmentschedule_test_specs.UomSpec;
import de.metas.order.DeliveryRule;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ExtendWith(AdempiereTestWatcher.class)
public class ShipmentScheduleUpdater_generate_Test
{
	private ShipmentScheduleUpdater shipmentScheduleUpdater;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));

		this.shipmentScheduleUpdater = ShipmentScheduleUpdater.newInstanceForUnitTesting();
	}

	private AbstractBigDecimalAssert<?> assertOneResultWithQtyToDeliver(@NonNull final ShipmentSchedulesDuringUpdate result)
	{
		assertThat(result.getAllLines()).hasSize(1);
		final DeliveryLineCandidate deliveryLineCandidate = result.getAllLines().get(0);

		return assertThat(deliveryLineCandidate.getQtyToDeliver());
	}

	private ShipmentSchedulesDuringUpdate setupAndInvoke(@NonNull final TestSetupSpec spec)
	{
		final ImmutableList<OlAndSched> olAndScheds = TestSetupSpecHelper.setup(spec);

		// invoke the method under test
		return shipmentScheduleUpdater.generate_FirstRun(olAndScheds);
	}

	@Nested
	public class emptyStock
	{
		/**
		 * Verifies that with delivery-rule = "force", qtyToDeliver is the ordered quantity, no matter whether the product is stocked or not.
		 */
		@Test
		public void force()
		{
			final UomSpec uom = UomSpec.builder().name("stockUom").build();
			final ProductSpec nonStockedProduct = ProductSpec.builder().value("prod1").uomValue("stockUom").stocked(false).build();

			final TestSetupSpec spec = TestSetupSpec.builder()
					.uom(uom)
					.product(nonStockedProduct)
					.order(OrderSpec.builder().value("order1").build())
					.orderLine(OrderLineSpec.builder().value("ol11").product("prod1").order("order1").qtyOrdered(TEN).build())
					.shipmentSchedule(ShipmentScheduleSpec.builder().product("prod1").order("order1").orderLine("ol11").qtyOrdered(TEN).deliveryRule(DeliveryRule.FORCE).build())
					.build();

			final ShipmentSchedulesDuringUpdate result1 = setupAndInvoke(spec);
			assertOneResultWithQtyToDeliver(result1).isEqualByComparingTo("10");

			final TestSetupSpec changedSpec = spec.withProduct(nonStockedProduct.withStocked(true));

			final ShipmentSchedulesDuringUpdate result2 = setupAndInvoke(changedSpec);
			assertOneResultWithQtyToDeliver(result2).isEqualByComparingTo("10");
		}

		@Test
		public void availability_item()
		{
			final ProductSpec nonStockedProduct = ProductSpec.builder().value("prod1").uomValue("stockUom").stocked(false).build();
			final TestSetupSpec spec = TestSetupSpec.builder()
					.uom(UomSpec.builder().name("stockUom").build())
					.product(nonStockedProduct)
					.order(OrderSpec.builder().value("order1").build())
					.orderLine(OrderLineSpec.builder().value("ol11").product("prod1").order("order1").qtyOrdered(TEN).build())
					.shipmentSchedule(ShipmentScheduleSpec.builder().product("prod1").order("order1").orderLine("ol11").qtyOrdered(TEN)
							.deliveryRule(DeliveryRule.AVAILABILITY).build())
					.build();

			final ShipmentSchedulesDuringUpdate result1 = setupAndInvoke(spec);
			assertOneResultWithQtyToDeliver(result1).isEqualByComparingTo("10"); // not stocked;

			final TestSetupSpec changedSpec = spec.withProduct(nonStockedProduct.withStocked(true));

			final ShipmentSchedulesDuringUpdate result2 = setupAndInvoke(changedSpec);
			assertThat(result2.getAllLines()).isEmpty(); // product is stocked, but there is nothing on stock
		}

		/** If the product's type is not "item", then deliver the ordered quantity */
		@ParameterizedTest
		@ValueSource(strings = {
				X_M_Product.PRODUCTTYPE_ExpenseType,
				X_M_Product.PRODUCTTYPE_Online,
				X_M_Product.PRODUCTTYPE_Resource,
				X_M_Product.PRODUCTTYPE_Service })
		public void availability_nonitem(final String productType)
		{
			final ProductSpec nonStockedProduct = ProductSpec.builder().value("prod1").uomValue("stockUom").stocked(false).productType(productType).build();
			final TestSetupSpec spec = TestSetupSpec.builder()
					.uom(UomSpec.builder().name("stockUom").build())
					.product(nonStockedProduct)
					.order(OrderSpec.builder().value("order1").build())
					.orderLine(OrderLineSpec.builder().value("ol11").product("prod1").order("order1").qtyOrdered(TEN).build())
					.shipmentSchedule(ShipmentScheduleSpec.builder().product("prod1").order("order1").orderLine("ol11").qtyOrdered(TEN)
							.deliveryRule(DeliveryRule.AVAILABILITY).build())
					.build();

			final ShipmentSchedulesDuringUpdate result1 = setupAndInvoke(spec);
			assertOneResultWithQtyToDeliver(result1).isEqualByComparingTo("10"); // not stocked;

			final TestSetupSpec changedSpec = spec.withProduct(nonStockedProduct.withStocked(true));

			final ShipmentSchedulesDuringUpdate result2 = setupAndInvoke(changedSpec);
			assertOneResultWithQtyToDeliver(result2).isEqualByComparingTo("10"); // treated as not stocked, because it's not an item;
		}
	}

	@Nested
	public class partialStock
	{
		@Test
		public void availability()
		{
			final UomSpec uom = UomSpec.builder().name("stockUom").build();
			final ProductSpec nonStockedProduct = ProductSpec.builder().value("prod1").uomValue("stockUom").stocked(false).build();

			final TestSetupSpec spec = TestSetupSpec.builder()
					.uom(uom)
					.product(nonStockedProduct)
					.stock(StockSpec.builder().product("prod1").qtyStock(new BigDecimal("1")).build())
					.stock(StockSpec.builder().product("prod1").qtyStock(new BigDecimal("3")).build())
					.order(OrderSpec.builder().value("order1").build())
					.orderLine(OrderLineSpec.builder().value("ol11").product("prod1").order("order1").qtyOrdered(TEN).build())
					.shipmentSchedule(ShipmentScheduleSpec.builder().product("prod1").order("order1").orderLine("ol11").qtyOrdered(TEN)
							.deliveryRule(DeliveryRule.AVAILABILITY)
							.build())
					.build();

			final ShipmentSchedulesDuringUpdate result1 = setupAndInvoke(spec);
			assertOneResultWithQtyToDeliver(result1).isEqualByComparingTo("10"); // not stocked;

			final TestSetupSpec changedSpec = spec.withProduct(nonStockedProduct.withStocked(true));

			final ShipmentSchedulesDuringUpdate result2 = setupAndInvoke(changedSpec);
			assertOneResultWithQtyToDeliver(result2).isEqualByComparingTo("4"); // product is stocked, and the qty-on-hand sums up to 4
		}
	}

	@Nested
	public class PickingBOM_ChristmasBox_5chocolates_3socks_DeliverByAvailability
	{
		@Builder(builderMethodName = "prepareTest", buildMethodName = "setupAndInvoke")
		private ShipmentSchedulesDuringUpdate setupTest(
				final int christmasPack_qtyOrdered,
				final int christmasPack_qtyOnHand,
				final int chocolate_qtyOnHand,
				final int socks_qtyOnHand)
		{
			final TestSetupSpec spec = TestSetupSpec.builder()
					.uom(UomSpec.builder().name("Each").build())
					.product(ProductSpec.builder().value("christmasPack").uomValue("Each").stocked(true).build())
					.product(ProductSpec.builder().value("chocolate").uomValue("Each").stocked(true).build())
					.product(ProductSpec.builder().value("socks").uomValue("Each").stocked(true).build())
					//
					.stock(StockSpec.builder().product("christmasPack").qtyStock(new BigDecimal(christmasPack_qtyOnHand)).build())
					.stock(StockSpec.builder().product("chocolate").qtyStock(new BigDecimal(chocolate_qtyOnHand)).build())
					.stock(StockSpec.builder().product("socks").qtyStock(new BigDecimal(socks_qtyOnHand)).build())
					//
					.order(OrderSpec.builder().value("order1").build())
					.orderLine(OrderLineSpec.builder().value("ol1").product("christmasPack").order("order1")
							.qtyOrdered(new BigDecimal(christmasPack_qtyOrdered))
							.build())
					.shipmentSchedule(ShipmentScheduleSpec.builder().product("christmasPack").order("order1").orderLine("ol1")
							.qtyOrdered(new BigDecimal(christmasPack_qtyOrdered))
							.deliveryRule(DeliveryRule.AVAILABILITY)
							.pickFromOrder(PickFromOrderSpec.builder()
									.mainProduct("christmasPack")
									.mainProductUOM("Each")
									.bomLine(PickFromOrderBOMLineSpec.builder()
											.product("chocolate")
											.uom("Each")
											.qtyForOneFinishedGood(new BigDecimal("5"))
											.build())
									.bomLine(PickFromOrderBOMLineSpec.builder()
											.product("socks")
											.uom("Each")
											.qtyForOneFinishedGood(new BigDecimal("3"))
											.build())
									.build())
							.build())
					.build();

			return setupAndInvoke(spec);
		}

		@Test
		public void qtyOnHand_0christmasPacks_11chocolates_4socks()
		{
			final ShipmentSchedulesDuringUpdate result = prepareTest()
					.christmasPack_qtyOrdered(99999)
					.christmasPack_qtyOnHand(0)
					.chocolate_qtyOnHand(11)
					.socks_qtyOnHand(4)
					.setupAndInvoke();
			assertOneResultWithQtyToDeliver(result).isEqualByComparingTo("1");
		}

		@Test
		public void qtyOnHand_0christmasPacks_11chocolates_6socks()
		{
			final ShipmentSchedulesDuringUpdate result = prepareTest()
					.christmasPack_qtyOrdered(99999)
					.christmasPack_qtyOnHand(0)
					.chocolate_qtyOnHand(11)
					.socks_qtyOnHand(6)
					.setupAndInvoke();
			assertOneResultWithQtyToDeliver(result).isEqualByComparingTo("2");
		}

		@Test
		public void qtyOnHand_10christmasPacks_11chocolates_6socks()
		{
			final ShipmentSchedulesDuringUpdate result = prepareTest()
					.christmasPack_qtyOrdered(99999)
					.christmasPack_qtyOnHand(10)
					.chocolate_qtyOnHand(11)
					.socks_qtyOnHand(6)
					.setupAndInvoke();
			assertOneResultWithQtyToDeliver(result).isEqualByComparingTo("12");
		}

		@Test
		public void qtyOnHand_10christmasPacks_11chocolates_6socks_order_11()
		{
			final ShipmentSchedulesDuringUpdate result = prepareTest()
					.christmasPack_qtyOrdered(11)
					.christmasPack_qtyOnHand(10)
					.chocolate_qtyOnHand(11)
					.socks_qtyOnHand(6)
					.setupAndInvoke();
			assertOneResultWithQtyToDeliver(result).isEqualByComparingTo("11");
		}

	}

	@Nested
	public class completeOrder
	{
		private ShipmentSchedulesDuringUpdate setup(final String qtyOnHand)
		{
			final UomSpec uom = UomSpec.builder().name("stockUom").build();

			final TestSetupSpec spec = TestSetupSpec.builder()
					.uom(uom)
					.product(ProductSpec.builder().value("prod1").uomValue("stockUom").stocked(true).build())
					//
					.stock(StockSpec.builder().product("prod1").qtyStock(new BigDecimal(qtyOnHand)).build())
					//
					.order(OrderSpec.builder().value("order1").build())
					.orderLine(OrderLineSpec.builder().value("ol1").product("prod1").order("order1").qtyOrdered(new BigDecimal("10")).build())
					.orderLine(OrderLineSpec.builder().value("ol2").product("prod1").order("order1").qtyOrdered(new BigDecimal("11")).build())
					//
					.shipmentSchedule(ShipmentScheduleSpec.builder().product("prod1").order("order1").orderLine("ol1").qtyOrdered(new BigDecimal("10"))
							.deliveryRule(DeliveryRule.COMPLETE_ORDER)
							.build())
					.shipmentSchedule(ShipmentScheduleSpec.builder().product("prod1").order("order1").orderLine("ol2").qtyOrdered(new BigDecimal("11"))
							.deliveryRule(DeliveryRule.COMPLETE_ORDER)
							.build())
					.build();

			return setupAndInvoke(spec);
		}

		@Test
		public void zeroQtyOnHand()
		{
			final ShipmentSchedulesDuringUpdate result = setup("0");

			assertThat(result.getCandidates()).hasSize(0);
		}

		@Test
		public void partialFirstLine()
		{
			final ShipmentSchedulesDuringUpdate result = setup("5");

			assertThat(result.getCandidates()).hasSize(1);
			final List<DeliveryLineCandidate> lines = ImmutableList.copyOf(result.getCandidates().get(0).getLines());
			assertThat(lines).hasSize(1);

			final DeliveryLineCandidate line1 = lines.get(0);
			assertThat(line1.getDeliveryRule()).isEqualTo(DeliveryRule.COMPLETE_ORDER);
			assertThat(line1.getQtyToDeliver()).isEqualTo("5");
			assertThat(line1.getCompleteStatus()).isEqualTo(CompleteStatus.INCOMPLETE_LINE);
		}

		@Test
		public void oneCompleteLine_onePartial()
		{
			final ShipmentSchedulesDuringUpdate result = setup("15");

			assertThat(result.getCandidates()).hasSize(1);
			final List<DeliveryLineCandidate> lines = ImmutableList.copyOf(result.getCandidates().get(0).getLines());
			assertThat(lines).hasSize(2);

			final DeliveryLineCandidate line1 = lines.get(0);
			assertThat(line1.getDeliveryRule()).isEqualTo(DeliveryRule.COMPLETE_ORDER);
			assertThat(line1.getQtyToDeliver()).isEqualTo("10");
			assertThat(line1.getCompleteStatus()).isEqualTo(CompleteStatus.OK);

			final DeliveryLineCandidate line2 = lines.get(1);
			assertThat(line2.getDeliveryRule()).isEqualTo(DeliveryRule.COMPLETE_ORDER);
			assertThat(line2.getQtyToDeliver()).isEqualTo("5");
			assertThat(line2.getCompleteStatus()).isEqualTo(CompleteStatus.INCOMPLETE_LINE);
		}

		@Test
		public void availableQtyOnHand()
		{
			final ShipmentSchedulesDuringUpdate result = setup("100");

			assertThat(result.getCandidates()).hasSize(1);
			final List<DeliveryLineCandidate> lines = ImmutableList.copyOf(result.getCandidates().get(0).getLines());
			assertThat(lines).hasSize(2);

			final DeliveryLineCandidate line1 = lines.get(0);
			assertThat(line1.getDeliveryRule()).isEqualTo(DeliveryRule.COMPLETE_ORDER);
			assertThat(line1.getQtyToDeliver()).isEqualTo("10");
			assertThat(line1.getCompleteStatus()).isEqualTo(CompleteStatus.OK);

			final DeliveryLineCandidate line2 = lines.get(1);
			assertThat(line2.getDeliveryRule()).isEqualTo(DeliveryRule.COMPLETE_ORDER);
			assertThat(line2.getQtyToDeliver()).isEqualTo("11");
			assertThat(line2.getCompleteStatus()).isEqualTo(CompleteStatus.OK);
		}

	}
}
