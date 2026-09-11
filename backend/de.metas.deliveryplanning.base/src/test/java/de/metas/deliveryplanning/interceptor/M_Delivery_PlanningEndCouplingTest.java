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
 * The incoming half already existed - a changed {@code PlannedLoadedQuantity} writes {@code ActualLoadQty},
 * because on an incoming planning the load happens at the vendor and is taken to match the plan. The outgoing
 * half was missing: what was ACTUALLY loaded is the upper bound on what can ever be discharged, so a planned
 * discharge left at the original figure describes an impossibility - discharging goods that were never loaded.
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
	@DisplayName("an INCOMING planning is left alone - there the discharge IS the receipt, not a consequence of loading")
	void incomingIsUntouched()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming, 10);
		record.setActualLoadQty(BigDecimal.valueOf(5));

		interceptor.onActualLoadQtyChanged(record);

		assertThat(record.getPlannedDischargeQuantity())
				.as("the incoming end already has its own rule, and the receipt is what settles it")
				.isEqualByComparingTo("10");
	}

	@Test
	@DisplayName("clearing the actual load - a reversal - takes the planned discharge back down with it")
	void reversalClearsBothEnds()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 10);
		record.setActualLoadQty(BigDecimal.ZERO);

		interceptor.onActualLoadQtyChanged(record);

		assertThat(record.getPlannedDischargeQuantity())
				.as("nothing is loaded any more, so nothing is planned to discharge - the same direction the mirror rule takes")
				.isEqualByComparingTo("0");
	}
}
