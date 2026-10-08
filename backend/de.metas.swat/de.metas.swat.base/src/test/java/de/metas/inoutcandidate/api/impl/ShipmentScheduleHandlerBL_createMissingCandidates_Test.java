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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.api.CreateMissingCandidatesResult;
import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.getId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers {@link ShipmentScheduleHandlerBL#createMissingCandidates(Properties, QueryLimit)} -- the bounded batching
 * entry point plus the cross-handler {@code Budget} it threads across all registered {@link ShipmentScheduleHandler}s.
 */
class ShipmentScheduleHandlerBL_createMissingCandidates_Test
{
	private ShipmentScheduleHandlerBL shipmentScheduleHandlerBL;
	private Properties ctx;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		shipmentScheduleHandlerBL = new ShipmentScheduleHandlerBL();
		ctx = Env.getCtx();
	}

	@Test
	void boundedBatch_processesExactlyTheLimit_andReportsLimitReached()
	{
		final TestHandler handler = new TestHandler(I_C_OrderLine.Table_Name);
		handler.addMissingModels(5, I_C_OrderLine.class);
		shipmentScheduleHandlerBL.registerHandler(handler);

		final CreateMissingCandidatesResult result = shipmentScheduleHandlerBL.createMissingCandidates(ctx, QueryLimit.ofInt(3));

		assertThat(handler.createCandidatesForCallCount).isEqualTo(3);
		assertThat(result.getCreatedShipmentScheduleIds()).hasSize(3);
		assertThat(result.isLimitReached()).isTrue();
		// the 3 unprocessed models remain missing for a follow-up run
		assertThat(handler.remainingMissingCount()).isEqualTo(2);
	}

	@Test
	void exactMultiple_processesAll_limitReached_thenFollowUpRunFindsNothing()
	{
		final TestHandler handler = new TestHandler(I_C_OrderLine.Table_Name);
		handler.addMissingModels(3, I_C_OrderLine.class);
		shipmentScheduleHandlerBL.registerHandler(handler);

		// first run: budget == backlog size exactly
		final CreateMissingCandidatesResult firstResult = shipmentScheduleHandlerBL.createMissingCandidates(ctx, QueryLimit.ofInt(3));

		assertThat(handler.createCandidatesForCallCount).isEqualTo(3);
		assertThat(firstResult.getCreatedShipmentScheduleIds()).hasSize(3);
		assertThat(firstResult.isLimitReached())
				.as("budget was fully consumed by the exact-size backlog -- a follow-up run must be enqueued")
				.isTrue();
		assertThat(handler.remainingMissingCount()).isZero();

		// follow-up run: nothing left to process -- must terminate the re-enqueue chain
		final CreateMissingCandidatesResult followUpResult = shipmentScheduleHandlerBL.createMissingCandidates(ctx, QueryLimit.ofInt(3));

		assertThat(handler.createCandidatesForCallCount)
				.as("no further model was processed in the follow-up run")
				.isEqualTo(3);
		assertThat(followUpResult.getCreatedShipmentScheduleIds()).isEmpty();
		assertThat(followUpResult.isLimitReached())
				.as("an empty run must report false, or the re-enqueue chain would never terminate")
				.isFalse();
	}

	@Test
	void noLimit_processesEverything_andNeverReportsLimitReached()
	{
		final TestHandler handler = new TestHandler(I_C_OrderLine.Table_Name);
		handler.addMissingModels(7, I_C_OrderLine.class);
		shipmentScheduleHandlerBL.registerHandler(handler);

		final CreateMissingCandidatesResult result = shipmentScheduleHandlerBL.createMissingCandidates(ctx, QueryLimit.NO_LIMIT);

		assertThat(handler.createCandidatesForCallCount).isEqualTo(7);
		assertThat(result.getCreatedShipmentScheduleIds()).hasSize(7);
		assertThat(result.isLimitReached()).isFalse();
		assertThat(handler.remainingMissingCount()).isZero();
	}

	@Test
	void crossHandlerBudget_isThreadedInRegistrationOrder_andSpillsOverIntoTheSecondHandler()
	{
		final TestHandler firstHandler = new TestHandler(I_C_OrderLine.Table_Name);
		firstHandler.addMissingModels(2, I_C_OrderLine.class);
		final TestHandler secondHandler = new TestHandler(I_C_Order.Table_Name);
		secondHandler.addMissingModels(4, I_C_Order.class);

		// registration order matters: budget drains handlers in this order
		shipmentScheduleHandlerBL.registerHandler(firstHandler);
		shipmentScheduleHandlerBL.registerHandler(secondHandler);

		// budget=5: first handler's 2 models fully drain (using up 2), remaining 3 spill into the second handler
		final CreateMissingCandidatesResult result = shipmentScheduleHandlerBL.createMissingCandidates(ctx, QueryLimit.ofInt(5));

		assertThat(firstHandler.createCandidatesForCallCount).isEqualTo(2);
		assertThat(firstHandler.remainingMissingCount()).isZero();

		assertThat(secondHandler.retrieveWasCalled).isTrue();
		assertThat(secondHandler.lastLimitPassed).isEqualTo(QueryLimit.ofInt(3));
		assertThat(secondHandler.createCandidatesForCallCount).isEqualTo(3);
		assertThat(secondHandler.remainingMissingCount()).isEqualTo(1);

		assertThat(result.getCreatedShipmentScheduleIds()).hasSize(5);
		assertThat(result.isLimitReached()).isTrue();
	}

	@Test
	void crossHandlerBudget_exhaustedByFirstHandler_secondHandlerIsNeverOpened()
	{
		final TestHandler firstHandler = new TestHandler(I_C_OrderLine.Table_Name);
		firstHandler.addMissingModels(5, I_C_OrderLine.class);
		final TestHandler secondHandler = new TestHandler(I_C_Order.Table_Name);
		secondHandler.addMissingModels(4, I_C_Order.class);

		shipmentScheduleHandlerBL.registerHandler(firstHandler);
		shipmentScheduleHandlerBL.registerHandler(secondHandler);

		// budget=3: fully consumed by the first handler alone
		final CreateMissingCandidatesResult result = shipmentScheduleHandlerBL.createMissingCandidates(ctx, QueryLimit.ofInt(3));

		assertThat(firstHandler.createCandidatesForCallCount).isEqualTo(3);
		assertThat(firstHandler.remainingMissingCount()).isEqualTo(2);

		assertThat(secondHandler.retrieveWasCalled)
				.as("budget was already exhausted by the first handler; the second handler's iterator must never be opened")
				.isFalse();
		assertThat(secondHandler.createCandidatesForCallCount).isZero();

		assertThat(result.getCreatedShipmentScheduleIds()).hasSize(3);
		assertThat(result.isLimitReached()).isTrue();
	}

	@Test
	void lockedModels_areSkipped_withoutConsumingTheBudget_andAreReportedAsSkipped()
	{
		final TestHandler handler = new TestHandler(I_C_OrderLine.Table_Name);
		handler.addMissingModels(5, I_C_OrderLine.class);
		final List<Integer> modelIds = handler.missingModelIds();
		final LockingShipmentScheduleHandlerBL lockingBL = new LockingShipmentScheduleHandlerBL();
		lockingBL.lockedIds.add(modelIds.get(0));
		lockingBL.lockedIds.add(modelIds.get(1));
		lockingBL.registerHandler(handler);

		// budget=3: the limited retrieve returns the first 3 models, of which the first two are locked by a concurrent transaction
		final CreateMissingCandidatesResult result = lockingBL.createMissingCandidates(ctx, QueryLimit.ofInt(3));

		assertThat(handler.createCandidatesForCallCount).as("only the not locked model was processed").isEqualTo(1);
		assertThat(handler.missingModelIds()).containsExactly(modelIds.get(0), modelIds.get(1), modelIds.get(3), modelIds.get(4));
		assertThat(result.getCreatedShipmentScheduleIds()).hasSize(1);
		assertThat(result.getSkippedCount()).isEqualTo(2);
		assertThat(result.isAnySkipped()).isTrue();
		assertThat(result.isLimitReached()).as("the skipped models did not consume the budget").isFalse();

		// a later run, after the concurrent transaction ended (e.g. rolled back), processes them
		lockingBL.lockedIds.clear();
		final CreateMissingCandidatesResult laterResult = lockingBL.createMissingCandidates(ctx, QueryLimit.ofInt(3));

		assertThat(handler.createCandidatesForCallCount).isEqualTo(4);
		assertThat(handler.missingModelIds()).containsExactly(modelIds.get(4));
		assertThat(laterResult.isAnySkipped()).isFalse();
		assertThat(laterResult.isLimitReached()).isTrue();
	}

	/**
	 * Simulates records locked by a concurrent transaction, which a real database run detects with {@code FOR KEY SHARE SKIP LOCKED}.
	 */
	private static class LockingShipmentScheduleHandlerBL extends ShipmentScheduleHandlerBL
	{
		private final Set<Integer> lockedIds = new HashSet<>();

		@Override
		protected boolean lockIfStillMissingCandidates(final Properties ctx, final ShipmentScheduleHandler handler, final Object model)
		{
			return !lockedIds.contains(getId(model)) && super.lockIfStillMissingCandidates(ctx, handler, model);
		}
	}

	/**
	 * Test double for {@link ShipmentScheduleHandler}: models "missing a candidate" are tracked as an ordered map of
	 * (id -> model). {@link #retrieveModelsWithMissingCandidates} returns only up to the given {@code limit} of the
	 * still-missing models (in insertion order), mirroring how a real handler's bounded query behaves. Once
	 * {@link #createCandidatesFor} is invoked for a model, it is removed from the missing set -- so a follow-up
	 * {@code retrieveModelsWithMissingCandidates} call correctly no longer returns it.
	 */
	private static class TestHandler extends ShipmentScheduleHandler
	{
		private final String tableName;
		private final Map<Integer, Object> missingModelsById = new LinkedHashMap<>();

		int createCandidatesForCallCount = 0;
		boolean retrieveWasCalled = false;
		QueryLimit lastLimitPassed;

		TestHandler(final String tableName)
		{
			this.tableName = tableName;
		}

		void addMissingModels(final int count, final Class<?> modelClass)
		{
			for (int i = 0; i < count; i++)
			{
				final Object model = newInstance(modelClass);
				saveRecord(model);
				missingModelsById.put(getId(model), model);
			}
		}

		int remainingMissingCount()
		{
			return missingModelsById.size();
		}

		List<Integer> missingModelIds()
		{
			return ImmutableList.copyOf(missingModelsById.keySet());
		}

		@Override
		public Iterator<?> retrieveModelsWithMissingCandidates(final Properties ctx, final String trxName, final QueryLimit limit)
		{
			retrieveWasCalled = true;
			lastLimitPassed = limit;

			final int max = limit.toIntOrInfinit();
			final List<Object> result = new ArrayList<>();
			for (final Object model : missingModelsById.values())
			{
				if (result.size() >= max)
				{
					break;
				}
				result.add(model);
			}
			return result.iterator();
		}

		@Override
		public List<I_M_ShipmentSchedule> createCandidatesFor(final Object model)
		{
			createCandidatesForCallCount++;
			missingModelsById.remove(getId(model));

			// unsaved on purpose: the framework (ShipmentScheduleHandlerBL.invokeHandlerForModel) saves it
			final I_M_ShipmentSchedule newSched = newInstance(I_M_ShipmentSchedule.class);
			return ImmutableList.of(newSched);
		}

		@Override
		public void updateShipmentScheduleFromReferencedRecord(final I_M_ShipmentSchedule shipmentSchedule)
		{
			// not exercised by these tests
		}

		@Override
		public void invalidateCandidatesFor(final Object model)
		{
			// not exercised by these tests
		}

		@Override
		public String getSourceTable()
		{
			return tableName;
		}

		@Override
		public IDeliverRequest createDeliverRequest(final I_M_ShipmentSchedule sched, final I_C_OrderLine salesOrderLine)
		{
			throw new UnsupportedOperationException("not exercised by these tests");
		}
	}
}
