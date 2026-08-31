/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.api.impl;

import de.metas.inout.PriorityRule;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

public class ShipmentScheduleEffectiveBLPriorityRuleTest
{
	private IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		shipmentScheduleEffectiveBL = new ShipmentScheduleEffectiveBL();
	}

	private static I_M_ShipmentSchedule createSched(
			@Nullable final PriorityRule priorityRule,
			@Nullable final PriorityRule priorityRuleOverride)
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setPriorityRule(priorityRule != null ? priorityRule.getCode() : null);
		sched.setPriorityRule_Override(priorityRuleOverride != null ? priorityRuleOverride.getCode() : null);
		return sched;
	}

	@Test
	void overrideWins()
	{
		final I_M_ShipmentSchedule sched = createSched(PriorityRule.Low, PriorityRule.Urgent);

		assertThat(shipmentScheduleEffectiveBL.getPriorityRule(sched)).isEqualTo(PriorityRule.Urgent);
	}

	@Test
	void fallsBackToPriorityRuleWhenOverrideIsNotSet()
	{
		final I_M_ShipmentSchedule sched = createSched(PriorityRule.High, null);

		assertThat(shipmentScheduleEffectiveBL.getPriorityRule(sched)).isEqualTo(PriorityRule.High);
	}

	@Test
	void fallsBackToMediumWhenNeitherIsSet()
	{
		final I_M_ShipmentSchedule sched = createSched(null, null);

		assertThat(shipmentScheduleEffectiveBL.getPriorityRule(sched)).isEqualTo(PriorityRule.Medium);
	}
}
