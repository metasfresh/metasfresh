package de.metas.inoutcandidate.api.impl;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

import lombok.NonNull;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.inout.util.DeliveryLineCandidate;
import org.adempiere.inout.util.ShipmentSchedulesDuringUpdate;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.user.UserRepository;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleTestBase.OrderLineSpec;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleTestBase.OrderSpec;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleTestBase.ProductSpec;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleTestBase.ShipmentScheduleSpec;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleTestBase.StockSpec;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleTestBase.TestSetupSpec;
import de.metas.order.DeliveryRule;
import de.metas.util.Services;

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

public class ShipmentScheduleBL_generateTest
{

	private static final BigDecimal THREE = new BigDecimal("3");
	private static final BigDecimal FOUR = new BigDecimal("4");;

	private ShipmentScheduleBL shipmentScheduleBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final BPartnerBL bPartnerBL = new BPartnerBL(new UserRepository());
		Services.registerService(IBPartnerBL.class, bPartnerBL);

		this.shipmentScheduleBL = ShipmentScheduleBL.newInstanceForUnitTesting();
	}

	/**
	 * Verifies that with delivery-rule = "force", qtyToDeliver is the ordered quantity, no matter whether the product is stocked or not.
	 */
	@Test
	public void generate_emptyStock_force()
	{

		final ProductSpec nonStockedProduct = ProductSpec.builder().value("prod1").stocked(false).build();

		final TestSetupSpec spec = TestSetupSpec.builder()
				.product(nonStockedProduct)
				.order(OrderSpec.builder().value("order1").build())
				.orderLine(OrderLineSpec.builder().value("ol11").product("prod1").order("order1").qtyOrdered(TEN).build())
				.shipmentSchedule(ShipmentScheduleSpec.builder().product("prod1").order("order1").orderLine("ol11").qtyOrdered(TEN).deliveryRule(DeliveryRule.FORCE).build())
				.build();

		final ShipmentSchedulesDuringUpdate result1 = setupAndInvoke(spec);
		assertOneResultWithQtyToDeliver(result1, TEN);

		final TestSetupSpec changedSpec = spec.withProduct(nonStockedProduct.withStocked(true));

		final ShipmentSchedulesDuringUpdate result2 = setupAndInvoke(changedSpec);
		assertOneResultWithQtyToDeliver(result2, TEN);
	}

	@Test
	public void generate_emptyStock_availability()
	{
		final ProductSpec nonStockedProduct = ProductSpec.builder().value("prod1").stocked(false).build();
		final TestSetupSpec spec = specOneScheduleAvailabilityWithoutStock(nonStockedProduct);

		final ShipmentSchedulesDuringUpdate result1 = setupAndInvoke(spec);
		assertOneResultWithQtyToDeliver(result1, TEN); // not stocked;

		final TestSetupSpec changedSpec = spec.withProduct(nonStockedProduct.withStocked(true));

		final ShipmentSchedulesDuringUpdate result2 = setupAndInvoke(changedSpec);
		assertThat(result2.getAllLines()).isEmpty(); // product is stocked, but there is nothing on stock
	}

	/** If the product's type is not "item", then deliver the ordered quantity */
	@Test
	public void generate_emptyStock_availability_nonitem()
	{
		final ImmutableList<String> nonItemTypes = ImmutableList.of(
				X_M_Product.PRODUCTTYPE_ExpenseType,
				X_M_Product.PRODUCTTYPE_Online,
				X_M_Product.PRODUCTTYPE_Resource,
				X_M_Product.PRODUCTTYPE_Service);
		for (final String productType : nonItemTypes)
		{
			generate_emptyStock_availability_nonitem_performTestWithProductType(productType);
		}
	}

	private void generate_emptyStock_availability_nonitem_performTestWithProductType(final String productType)
	{
		final ProductSpec nonStockedProduct = ProductSpec.builder().value("prod1").stocked(false).productType(productType).build();
		final TestSetupSpec spec = specOneScheduleAvailabilityWithoutStock(nonStockedProduct);

		final ShipmentSchedulesDuringUpdate result1 = setupAndInvoke(spec);
		assertOneResultWithQtyToDeliver(result1, TEN); // not stocked;

		final TestSetupSpec changedSpec = spec.withProduct(nonStockedProduct.withStocked(true));

		final ShipmentSchedulesDuringUpdate result2 = setupAndInvoke(changedSpec);
		assertOneResultWithQtyToDeliver(result2, TEN); // treated as not stocked, because it's not an item;
	}

	private TestSetupSpec specOneScheduleAvailabilityWithoutStock(final ProductSpec nonStockedProduct)
	{
		return TestSetupSpec.builder()
				.product(nonStockedProduct)
				.order(OrderSpec.builder().value("order1").build())
				.orderLine(OrderLineSpec.builder().value("ol11").product("prod1").order("order1").qtyOrdered(TEN).build())
				.shipmentSchedule(ShipmentScheduleSpec.builder().product("prod1").order("order1").orderLine("ol11").qtyOrdered(TEN).deliveryRule(DeliveryRule.AVAILABILITY).build())
				.build();
	}

	@Test
	public void generate_partialStock_availability()
	{
		final ProductSpec nonStockedProduct = ProductSpec.builder().value("prod1").stocked(false).build();

		final TestSetupSpec spec = TestSetupSpec.builder()
				.product(nonStockedProduct)
				.stock(StockSpec.builder().product("prod1").qtyStock(ONE).build())
				.stock(StockSpec.builder().product("prod1").qtyStock(THREE).build())
				.order(OrderSpec.builder().value("order1").build())
				.orderLine(OrderLineSpec.builder().value("ol11").product("prod1").order("order1").qtyOrdered(TEN).build())
				.shipmentSchedule(ShipmentScheduleSpec.builder().product("prod1").order("order1").orderLine("ol11").qtyOrdered(TEN).deliveryRule(DeliveryRule.AVAILABILITY).build())
				.build();

		final ShipmentSchedulesDuringUpdate result1 = setupAndInvoke(spec);
		assertOneResultWithQtyToDeliver(result1, TEN); // not stocked;

		final TestSetupSpec changedSpec = spec.withProduct(nonStockedProduct.withStocked(true));

		final ShipmentSchedulesDuringUpdate result2 = setupAndInvoke(changedSpec);
		assertOneResultWithQtyToDeliver(result2, FOUR); // product is stocked, and the qty-on-hand sums up to 4
	}

	private void assertOneResultWithQtyToDeliver(
			@NonNull final ShipmentSchedulesDuringUpdate result,
			@NonNull final BigDecimal expectedQtyToDeliver)
	{
		assertThat(result.getAllLines()).hasSize(1);
		final DeliveryLineCandidate deliveryLineCandidate = result.getAllLines().get(0);

		assertThat(deliveryLineCandidate.getQtyToDeliver()).isEqualByComparingTo(expectedQtyToDeliver);
	}

	private ShipmentSchedulesDuringUpdate setupAndInvoke(@NonNull final TestSetupSpec spec)
	{
		final ImmutableList<OlAndSched> olAndScheds = ShipmentScheduleTestBase.setup(spec);

		// invoke the method under test
		final ShipmentSchedulesDuringUpdate result = shipmentScheduleBL.generate(
				Env.getCtx(),
				olAndScheds,
				null/* firstRun */,
				ITrx.TRXNAME_None);
		return result;
	}

}
