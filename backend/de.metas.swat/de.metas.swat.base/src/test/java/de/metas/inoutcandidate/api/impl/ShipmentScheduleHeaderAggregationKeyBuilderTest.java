package de.metas.inoutcandidate.api.impl;

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
import de.metas.inoutcandidate.modelvalidator.InOutCandidateValidator;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.agg.key.IAggregationKeyRegistry;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

class ShipmentScheduleHeaderAggregationKeyBuilderTest
{
	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		// the key values consult isSchedAllowsConsolidate(); "never consolidate" avoids real bpartner master data
		SpringContextHolder.registerJUnitBean(new ShipmentScheduleAllowConsolidatePredicateComposite(
				ImmutableList.of(sched -> false)));

		// the production registration of both key value handlers
		InOutCandidateValidator.registerSSAggregationKeyDependencies();
	}

	/**
	 * The registry keeps its handlers in static fields that {@link AdempiereTestHelper#init()} does not reset, so a handler left
	 * registered here would run in every later test of the same JVM fork.
	 */
	@AfterEach
	void clearRegisteredHandlers()
	{
		final IAggregationKeyRegistry keyRegistry = Services.get(IAggregationKeyRegistry.class);
		keyRegistry.clearHandlers(ShipmentScheduleHeaderAggregationKeyBuilder.REGISTRATION_KEY);
		keyRegistry.clearHandlers(ShipmentScheduleHeaderAggregationKeyBuilder.REGISTRATION_KEY_WITHOUT_ASYNC_BATCH);
	}

	private static I_M_ShipmentSchedule createSched(final int bpartnerId, final int asyncBatchId)
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setC_BPartner_ID(bpartnerId);
		sched.setC_BPartner_Location_ID(200);
		sched.setM_Warehouse_ID(300);
		sched.setC_Async_Batch_ID(asyncBatchId);
		return sched;
	}

	@Test
	void withoutAsyncBatch_ignoresAsyncBatch()
	{
		final I_M_ShipmentSchedule batch1 = createSched(100, 1);
		final I_M_ShipmentSchedule batch2 = createSched(100, 2);

		assertThat(new ShipmentScheduleHeaderAggregationKeyBuilder().isSame(batch1, batch2))
				.as("the default key splits on C_Async_Batch_ID")
				.isFalse();
		assertThat(ShipmentScheduleHeaderAggregationKeyBuilder.withoutAsyncBatch().isSame(batch1, batch2))
				.as("the key without async batch does not split on C_Async_Batch_ID")
				.isTrue();
	}

	@Test
	void withoutAsyncBatch_stillSplitsOnOtherHeaderValues()
	{
		final I_M_ShipmentSchedule bpartner1 = createSched(100, 1);
		final I_M_ShipmentSchedule bpartner2 = createSched(101, 1);

		assertThat(ShipmentScheduleHeaderAggregationKeyBuilder.withoutAsyncBatch().isSame(bpartner1, bpartner2))
				.as("schedules of different business partners never share a shipment; an unregistered key value handler would give both the same empty key")
				.isFalse();
	}
}
