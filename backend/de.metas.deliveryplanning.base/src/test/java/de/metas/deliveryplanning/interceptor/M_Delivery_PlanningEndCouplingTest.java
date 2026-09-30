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
 * The two ends of a planning do not drift apart - and the rule that keeps them together is which KIND of figure
 * may feed which.
 * <p>
 * <b>A plan is fed by a plan; an actual is fed by an actual.</b> Three couplings follow, and nothing else:
 * <ul>
 * <li><b>plan to plan, every direction</b> - what we intend to load is what we intend to discharge.</li>
 * <li><b>plan to actual, inbound only</b> - a vendor never reports what they loaded, so the plan is the only
 * figure there is. An outgoing load is ours, and only a shipment writes it.</li>
 * <li><b>actual to actual, outbound only</b> - the customer's unload is never reported back, so what actually
 * left our dock is the best knowledge there is. An incoming or dropship discharge is observed on our own
 * receipt and must never be guessed.</li>
 * </ul>
 * What is deliberately NOT a coupling: an actual load does not touch the planned discharge. A short load leaves
 * the plan standing to be re-planned rather than silently rewriting what was agreed - and a plan never creates
 * an actual, which would let typing a figure mark goods as delivered that never left the building.
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
	@DisplayName("plan to plan: an OUTGOING planning's planned discharge follows the planned LOAD")
	void outgoingPlannedDischargeFollowsPlannedLoad()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 10);
		record.setPlannedLoadedQuantity(BigDecimal.valueOf(3));

		interceptor.settleEnds(record, true, false);

		assertThat(record.getPlannedDischargeQuantity())
				.as("planning to load 3 and still planning to discharge 10 is not a plan, it is two plans")
				.isEqualByComparingTo("3");
	}

	@Test
	@DisplayName("plan to plan: an INCOMING planning follows it too")
	void incomingPlannedDischargeFollowsPlannedLoad()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming, 10);
		record.setPlannedLoadedQuantity(BigDecimal.valueOf(3));

		interceptor.settleEnds(record, true, false);

		assertThat(record.getPlannedDischargeQuantity()).isEqualByComparingTo("3");
	}

	@Test
	@DisplayName("plan to plan: a DROPSHIP planning follows it as well - the third direction is not a special case")
	void dropshipPlannedDischargeFollowsPlannedLoad()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Dropship, 10);
		record.setPlannedLoadedQuantity(BigDecimal.valueOf(3));

		interceptor.settleEnds(record, true, false);

		assertThat(record.getPlannedDischargeQuantity()).isEqualByComparingTo("3");
	}

	@Test
	@DisplayName("an ACTUAL load NEVER rewrites the planned discharge - a short load is re-planned, not hidden")
	void actualLoadDoesNotTouchThePlannedDischarge()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 10);
		record.setActualLoadQty(BigDecimal.valueOf(5));

		interceptor.settleEnds(record, false, true);

		assertThat(record.getPlannedDischargeQuantity())
				.as("loading 5 of a planned 10 is a fact about the load, not a new agreement about the discharge")
				.isEqualByComparingTo("10");
	}

	@Test
	@DisplayName("an ACTUAL load does not touch an INCOMING planning's planned discharge either")
	void actualLoadDoesNotTouchThePlannedDischargeIncoming()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming, 10);
		record.setActualLoadQty(BigDecimal.valueOf(5));

		interceptor.settleEnds(record, false, true);

		assertThat(record.getPlannedDischargeQuantity()).isEqualByComparingTo("10");
	}

	@Test
	@DisplayName("actual to actual: an OUTGOING planning's actual discharge follows what was ACTUALLY LOADED")
	void outgoingActualDischargeFollowsTheActualLoad()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 7);
		record.setActualDischargeQuantity(BigDecimal.ZERO);
		record.setActualLoadQty(BigDecimal.valueOf(4));

		interceptor.settleEnds(record, false, true);

		assertThat(record.getActualDischargeQuantity())
				.as("the customer never reports back, so what left our dock is the best knowledge there is")
				.isEqualByComparingTo("4");
	}

	@Test
	@DisplayName("a PLAN never creates an actual discharge - typing a figure is not an event")
	void plannedLoadEditDoesNotCreateAnActualDischarge()
	{
		// The owner found this on deliveryPlanningProcesses.feature: a planning whose plan was typed came out
		// with ActualDischargeQuantity = 5 while nothing had been loaded.
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 7);
		record.setActualDischargeQuantity(BigDecimal.ZERO);
		record.setPlannedLoadedQuantity(BigDecimal.valueOf(5));

		interceptor.settleEnds(record, true, false);

		assertThat(record.getPlannedDischargeQuantity())
				.as("the plan side does move - that is the plan-to-plan rule")
				.isEqualByComparingTo("5");
		assertThat(record.getActualDischargeQuantity())
				.as("nothing was loaded, so nothing can have been discharged")
				.isEqualByComparingTo("0");
	}

	@Test
	@DisplayName("an INCOMING planning's actual discharge is NOT assumed - that end is our own receipt")
	void incomingActualDischargeIsNotAssumed()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming, 7);
		record.setActualDischargeQuantity(BigDecimal.ZERO);
		record.setActualLoadQty(BigDecimal.valueOf(4));

		// Driven by an ACTUAL LOAD, which is exactly what carries across on an outgoing planning - so this proves
		// the direction guard, not merely that an inert trigger stayed inert.
		interceptor.settleEnds(record, false, true);

		assertThat(record.getActualDischargeQuantity())
				.as("receiving short of plan is the whole point - assuming the actual here would erase it")
				.isEqualByComparingTo("0");
	}

	@Test
	@DisplayName("a DROPSHIP planning's actual discharge is NOT assumed either - metasfresh books our own receipt for it")
	void dropshipActualDischargeIsNotAssumed()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Dropship, 7);
		record.setActualDischargeQuantity(BigDecimal.ZERO);
		record.setActualLoadQty(BigDecimal.valueOf(4));

		interceptor.settleEnds(record, false, true);

		assertThat(record.getActualDischargeQuantity())
				.as("a dropship ends at a customer, but we still book a receipt against it and that receipt reports "
						+ "the discharge - so the figure is observed, not assumed, exactly as for a plain inbound")
				.isEqualByComparingTo("0");
	}

	@Test
	@DisplayName("editing an INCOMING planning's planned LOAD settles BOTH the actual load and the planned discharge, in one pass")
	void editingIncomingPlannedLoadSettlesBothSides()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming, 9);
		record.setPlannedLoadedQuantity(BigDecimal.valueOf(3));
		record.setActualLoadQty(BigDecimal.valueOf(9));

		interceptor.settleEnds(record, true, false);

		assertThat(record.getActualLoadQty())
				.as("an inbound load is never reported, so it takes the plan")
				.isEqualByComparingTo("3");
		assertThat(record.getPlannedDischargeQuantity())
				.as("and the plan side follows the plan, directly - not via the actual")
				.isEqualByComparingTo("3");
		assertThat(record.getActualDischargeQuantity())
				.as("the actual discharge is outgoing-only - an incoming planning's discharge is our own receipt")
				.isEqualByComparingTo("0");
	}

	@Test
	@DisplayName("editing an OUTGOING planning's planned LOAD does NOT write the actual load - only a shipment does")
	void editingOutgoingPlannedLoadLeavesTheActualAlone()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 9);
		record.setPlannedLoadedQuantity(BigDecimal.valueOf(3));

		interceptor.settleEnds(record, true, false);

		assertThat(record.getActualLoadQty())
				.as("our own dock reports itself - assuming it would claim goods were loaded that never moved")
				.isEqualByComparingTo("0");
	}

	@Test
	@DisplayName("editing the planned discharge ALONE settles nothing - it is not even a trigger column")
	void directPlannedDischargeEditSettlesNothing()
	{
		// The class javadoc says a directly edited PlannedDischargeQuantity settles nothing downstream and is
		// not a trigger column. Until now that held only by code inspection: no test called settleEnds with
		// BOTH flags false, so removing the guards would not have failed anything here.
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 7);
		record.setPlannedLoadedQuantity(BigDecimal.valueOf(2));
		record.setActualLoadQty(BigDecimal.valueOf(4));
		record.setActualDischargeQuantity(BigDecimal.ZERO);

		interceptor.settleEnds(record, false, false);

		assertThat(record.getPlannedDischargeQuantity())
				.as("the typed plan stands - nothing rewrites it, least of all the load figures")
				.isEqualByComparingTo("7");
		assertThat(record.getActualLoadQty())
				.as("untouched: no rule writes the load end unless the PLANNED load was edited")
				.isEqualByComparingTo("4");
		assertThat(record.getActualDischargeQuantity())
				.as("untouched: the actual discharge moves only when the ACTUAL LOAD is settled")
				.isEqualByComparingTo("0");
	}

	@Test
	@DisplayName("a ZERO actual load clears the actual discharge but leaves the PLAN standing")
	void zeroActualLeavesThePlanAlone()
	{
		final I_M_Delivery_Planning record = planning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 10);
		record.setActualLoadQty(BigDecimal.ZERO);

		interceptor.settleEnds(record, false, true);

		assertThat(record.getPlannedDischargeQuantity())
				.as("the plan survives a not-yet-loaded row - otherwise a reversal plans the discharge down to "
						+ "nothing instead of returning it to be re-planned")
				.isEqualByComparingTo("10");
		assertThat(record.getActualDischargeQuantity())
				.as("the ACTUAL side takes the zero: claiming a discharge that never happened does not survive")
				.isEqualByComparingTo("0");
	}
}
