package de.metas.inoutcandidate.api.impl;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Wither;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.inout.util.DeliveryLineCandidate;
import org.adempiere.inout.util.ShipmentSchedulesDuringUpdate;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.user.UserRepository;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BPartner;
import de.metas.material.cockpit.model.I_MD_Stock;
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
	private static final int WAREHOUSE_ID = 35;

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
		final TestSetupSpec spec = TestSetupSpec.builder()
				.productStocked(false)
				.qtyOrdered(TEN)
				.deliveryRule(DeliveryRule.FORCE)
				.build();

		final ShipmentSchedulesDuringUpdate result1 = setupAndInvoke(spec);
		assertOneResultWithQtyToDeliver(result1, TEN);

		final ShipmentSchedulesDuringUpdate result2 = setupAndInvoke(spec.withProductStocked(true));
		assertOneResultWithQtyToDeliver(result2, TEN);
	}

	@Test
	public void generate_emptyStock_availability()
	{
		final TestSetupSpec spec = TestSetupSpec.builder()
				.productStocked(false)
				.qtyOrdered(TEN)
				.deliveryRule(DeliveryRule.AVAILABILITY)
				.build();

		final ShipmentSchedulesDuringUpdate result1 = setupAndInvoke(spec);
		assertOneResultWithQtyToDeliver(result1, TEN); // not stocked;

		final ShipmentSchedulesDuringUpdate result2 = setupAndInvoke(spec.withProductStocked(true));
		assertThat(result2.getAllLines()).isEmpty(); // product is stocked, but there is nothing on stock
	}

	@Test
	public void generate_partialStock_availability()
	{
		final TestSetupSpec spec = TestSetupSpec.builder()
				.productStocked(false)
				.qtyOrdered(TEN)
				.deliveryRule(DeliveryRule.AVAILABILITY)
				.qtyStock(ONE)
				.build();

		final ShipmentSchedulesDuringUpdate result1 = setupAndInvoke(spec);
		assertOneResultWithQtyToDeliver(result1, TEN); // not stocked;

		final ShipmentSchedulesDuringUpdate result2 = setupAndInvoke(spec.withProductStocked(true));
		assertOneResultWithQtyToDeliver(result2, ONE); // product is stocked, and the qty-on-hand ist 1
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
		final OlAndSched olAndSched = setup(spec);

		// invoke the method under test
		final ShipmentSchedulesDuringUpdate result = shipmentScheduleBL.generate(
				Env.getCtx(),
				ImmutableList.of(olAndSched),
				null/* firstRun */,
				ITrx.TRXNAME_None);
		return result;
	}

	private OlAndSched setup(@NonNull final TestSetupSpec spec)
	{
		final I_C_Order orderRecord = newInstance(I_C_Order.class);
		orderRecord.setDatePromised(TimeUtil.parseTimestamp("2018-10-19"));
		orderRecord.setM_Warehouse_ID(WAREHOUSE_ID);
		saveRecord(orderRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setIsStocked(spec.isProductStocked()); // <==
		productRecord.setProductType(X_M_Product.PRODUCTTYPE_Item);
		saveRecord(productRecord);

		final I_MD_Stock stockRecord = newInstance(I_MD_Stock.class);
		stockRecord.setM_Product(productRecord);
		stockRecord.setM_Warehouse_ID(WAREHOUSE_ID);
		stockRecord.setQtyOnHand(spec.getQtyStock());
		saveRecord(stockRecord);

		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
		orderLineRecord.setC_Order(orderRecord);
		orderLineRecord.setM_Product(productRecord);
		orderLineRecord.setQtyOrdered(spec.getQtyOrdered()); // <==
		orderLineRecord.setDatePromised(TimeUtil.parseTimestamp("2018-10-20"));
		saveRecord(orderLineRecord);

		final I_C_BPartner bPartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bPartnerRecord);

		final I_M_ShipmentSchedule shipmentScheduleRecord = newInstance(I_M_ShipmentSchedule.class);
		shipmentScheduleRecord.setIsClosed(false);
		shipmentScheduleRecord.setC_BPartner(bPartnerRecord);
		shipmentScheduleRecord.setQtyDelivered(BigDecimal.ONE);
		shipmentScheduleRecord.setQtyOrdered_Override(new BigDecimal("23"));
		shipmentScheduleRecord.setQtyOrdered_Calculated(new BigDecimal("24"));
		shipmentScheduleRecord.setM_Warehouse_ID(WAREHOUSE_ID);
		shipmentScheduleRecord.setC_Order(orderRecord);
		shipmentScheduleRecord.setC_OrderLine(orderLineRecord);
		shipmentScheduleRecord.setM_Product(productRecord);
		shipmentScheduleRecord.setBPartnerAddress_Override("BPartnerAddress_Override"); // not flagged as mandatory in AD, but always set
		shipmentScheduleRecord.setAD_Table_ID(getTableId(I_C_OrderLine.class));
		shipmentScheduleRecord.setRecord_ID(orderLineRecord.getC_OrderLine_ID());

		shipmentScheduleRecord.setDeliveryRule(spec.getDeliveryRule().getCode()); // <==

		final OlAndSched olAndSched = OlAndSched.builder()
				.deliverRequest(() -> orderLineRecord.getQtyOrdered())
				.shipmentSchedule(shipmentScheduleRecord)
				.build();
		return olAndSched;
	}

	@Value
	@Builder
	@Wither
	private static class TestSetupSpec
	{
		boolean productStocked;
		BigDecimal qtyOrdered;
		DeliveryRule deliveryRule;

		@Default
		BigDecimal qtyStock = ZERO;
	}
}
