/*
 * #%L
 * de.metas.deliveryplanning.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.deliveryplanning.interceptor;

import de.metas.deliveryplanning.DeliveryPlanningService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.X_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * The two ends of a planning do not drift apart: settling one constrains the other's plan.
 * <p>
 * One half already existed - a changed {@code PlannedLoadedQuantity} writes {@code ActualLoadQty} on an
 * incoming planning, where the load happens at the vendor and is taken to match the plan. The other was
 * missing: what was ACTUALLY loaded is the upper bound on what can ever be discharged, so a planned discharge
 * left at the original figure describes an impossibility - discharging goods that were never loaded.
 * <p>
 * That constraint is physical and applies in EVERY direction; it does not care who did the loading. What does
 * gate it is a zero actual, which means "not loaded yet" rather than "none will be".
 */
class M_Delivery_PlanningEndCouplingTest
{
	private M_Delivery_Planning interceptor;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		interceptor = new M_Delivery_Planning(mock(DeliveryPlanningService.class));
	}

	private static I_M_Delivery_Planning planning(final String transportDirection, final int plannedDischarge)
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setTransportDirection(transportDirection);
		record.setPlannedDischargeQuantity(BigDecimal.valueOf(plannedDischarge));
		return record;
	}

	@Test
	@DisplayName("an OUTGOING planning's planned discharge follows the quantity actually loaded")
	void outgoingPlannedDischargeFollowsActualLoad()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 10);
		record.setActualLoadQty(BigDecimal.valueOf(5));

		interceptor.onActualLoadQtyChanged(record);

		assertThat(record.getPlannedDischargeQuantity())
				.as("only 5 was loaded, so planning to discharge 10 describes goods that were never loaded")
				.isEqualByComparingTo("5");
	}

	@Test
	@DisplayName("an INCOMING planning follows it too - you cannot receive more than the vendor loaded")
	void incomingFollowsAsWell()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming, 10);
		record.setActualLoadQty(BigDecimal.valueOf(5));

		interceptor.onActualLoadQtyChanged(record);

		assertThat(record.getPlannedDischargeQuantity())
				.as("the constraint is physical, not directional - a receipt settles the ACTUAL discharge, not the plan")
				.isEqualByComparingTo("5");
	}

	@Test
	@DisplayName("a DROPSHIP planning follows it as well - the third direction is not a special case either")
	void dropshipFollowsAsWell()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Dropship, 10);
		record.setActualLoadQty(BigDecimal.valueOf(5));

		interceptor.onActualLoadQtyChanged(record);

		assertThat(record.getPlannedDischargeQuantity()).isEqualByComparingTo("5");
	}

	@Test
	@DisplayName("an OUTGOING planning's actual discharge is assumed from its plan - we never see the customer unload")
	void outgoingActualDischargeFollowsThePlan()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 7);
		record.setActualDischargeQuantity(BigDecimal.ZERO);

		interceptor.onPlannedDischargeQuantityChanged(record);

		assertThat(record.getActualDischargeQuantity())
				.as("the mirror of the incoming load rule: the end we cannot observe takes its plan as the actual")
				.isEqualByComparingTo("7");
	}

	@Test
	@DisplayName("an INCOMING planning's actual discharge is NOT assumed - that end is our own receipt")
	void incomingActualDischargeIsNotAssumed()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming, 7);
		record.setActualDischargeQuantity(BigDecimal.ZERO);

		interceptor.onPlannedDischargeQuantityChanged(record);

		assertThat(record.getActualDischargeQuantity())
				.as("receiving short of plan is the whole point - assuming the actual here would erase it")
				.isEqualByComparingTo("0");
	}

	@Test
	@DisplayName("the two rules compose: a loaded quantity settles BOTH ends of an outgoing planning")
	void loadSettlesBothEndsOfAnOutgoingPlanning()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 10);
		record.setActualDischargeQuantity(BigDecimal.valueOf(10));
		record.setActualLoadQty(BigDecimal.valueOf(6));

		interceptor.onActualLoadQtyChanged(record);
		interceptor.onPlannedDischargeQuantityChanged(record);

		assertThat(record.getPlannedDischargeQuantity()).isEqualByComparingTo("6");
		assertThat(record.getActualDischargeQuantity())
				.as("loading 6 of a planned 10 leaves all three discharge-side figures at 6, not a phantom 10")
				.isEqualByComparingTo("6");
	}

	@Test
	@DisplayName("a ZERO actual load is not a settlement - it means not loaded yet, and leaves the plan alone")
	void zeroActualLeavesThePlanAlone()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 10);
		record.setActualLoadQty(BigDecimal.ZERO);

		interceptor.onActualLoadQtyChanged(record);

		assertThat(record.getPlannedDischargeQuantity())
				.as("the same reading of a zero actual as PoolEnd#effectiveQty - otherwise a reversal plans the "
						+ "discharge down to nothing instead of returning it to be re-planned")
				.isEqualByComparingTo("10");
	}
}
