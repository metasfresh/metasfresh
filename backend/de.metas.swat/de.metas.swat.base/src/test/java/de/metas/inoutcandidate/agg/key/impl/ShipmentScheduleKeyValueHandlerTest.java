package de.metas.inoutcandidate.agg.key.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.api.ShipmentScheduleAllowConsolidatePredicateComposite;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

public class ShipmentScheduleKeyValueHandlerTest
{
	private ShipmentScheduleKeyValueHandler handler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		// getValues() consults isSchedAllowsConsolidate(); register a composite whose single predicate
		// short-circuits to "not consolidate" so we don't depend on real bpartner master data.
		SpringContextHolder.registerJUnitBean(new ShipmentScheduleAllowConsolidatePredicateComposite(
				ImmutableList.of(sched -> false)));

		handler = new ShipmentScheduleKeyValueHandler();
	}

	// distinct, non-colliding ids so membership assertions can't be confused with
	// other zero-valued (AD_Org_ID, C_DocType_ID, C_Order_ID) or small-int columns.
	private static final int SHIPPER_ID = 4242;
	private static final int CARRIER_GOODS_TYPE_ID = 4343;
	private static final int CARRIER_PRODUCT_ID = 4444;

	private I_M_ShipmentSchedule createSched()
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		// minimal values so getValues() can run through the effective-BL lookups
		sched.setC_BPartner_ID(100);
		sched.setC_BPartner_Location_ID(200);
		sched.setM_Warehouse_ID(300);
		return sched;
	}

	private I_M_ShipmentSchedule createSchedWithShipperAndCarrierFields()
	{
		final I_M_ShipmentSchedule sched = createSched();
		// the fields under test
		sched.setM_Shipper_ID(SHIPPER_ID);
		sched.setCarrier_Goods_Type_ID(CARRIER_GOODS_TYPE_ID);
		sched.setCarrier_Product_ID(CARRIER_PRODUCT_ID);
		return sched;
	}

	@Test
	public void getValues_keepsShipper()
	{
		final I_M_ShipmentSchedule sched = createSchedWithShipperAndCarrierFields();

		final List<Object> values = handler.getValues(sched);

		assertThat(values)
				.as("carrier company (M_Shipper_ID) must still split shipments")
				.contains((Object)Integer.valueOf(SHIPPER_ID));
	}

	@Test
	public void getValues_noShipper_doesNotIncludeShipper()
	{
		// no shipper => the ShipperId-guarded block must not contribute anything;
		// a shipper id of 0 is what ShipperId.ofRepoIdOrNull treats as "no shipper".
		// Set the carrier fields anyway to prove they never leak in without the guard.
		final I_M_ShipmentSchedule sched = createSched();
		sched.setM_Shipper_ID(0);
		sched.setCarrier_Goods_Type_ID(CARRIER_GOODS_TYPE_ID);
		sched.setCarrier_Product_ID(CARRIER_PRODUCT_ID);

		final List<Object> values = handler.getValues(sched);

		assertThat(values)
				.as("with no shipper, the shipper/carrier block must contribute nothing")
				.doesNotContain((Object)Integer.valueOf(CARRIER_GOODS_TYPE_ID), (Object)Integer.valueOf(CARRIER_PRODUCT_ID))
				.noneMatch(value -> value instanceof String
						&& (((String)value).startsWith("cpr:") || ((String)value).startsWith("cgt:")));
	}

	@Test
	public void getValues_doesNotIncludeCarrierProductOrGoodsType()
	{
		final I_M_ShipmentSchedule sched = createSchedWithShipperAndCarrierFields();

		final List<Object> values = handler.getValues(sched);

		assertThat(values)
				.as("carrier goods-type and carrier product must no longer contribute to the shipment aggregation key")
				.noneMatch(value -> value instanceof String
						&& (((String)value).startsWith("cpr:") || ((String)value).startsWith("cgt:")));
	}
}
