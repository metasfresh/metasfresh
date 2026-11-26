/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.shipmentschedule.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.ShipmentScheduleAllowConsolidatePredicateComposite;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.modelvalidator.InOutCandidateValidator;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;

import javax.annotation.Nullable;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * A closed shipment schedule is not put into a new shipment run that ships the entered quantity (quantity type D / PD),
 * but it is put into a new run that ships picks only (quantity type P).
 * A run with its own async batch is one group, whatever a schedule recompute saved between the batch stamp and the enqueue.
 */
class ShipmentScheduleEnqueuerTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		// the real isFlaggedForRecompute runs plain SQL; nothing here is flagged for recompute
		Services.registerService(IShipmentScheduleInvalidateBL.class, Mockito.mock(IShipmentScheduleInvalidateBL.class));
	}

	/**
	 * The filter every shipment run is enqueued (and locked) by keeps an open schedule and leaves out a closed one.
	 */
	@Test
	void queryFilter_keepsOpenSchedule_leavesOutClosedSchedule()
	{
		final I_M_ShipmentSchedule open = createSchedule(801, false);
		final I_M_ShipmentSchedule closed = createSchedule(802, true);

		final ShipmentScheduleWorkPackageParameters parameters = ShipmentScheduleWorkPackageParameters.builder()
				.adPInstanceId(PInstanceId.ofRepoId(1))
				.scheduleIds(ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(ImmutableSet.of(
						ShipmentScheduleId.ofRepoId(open.getM_ShipmentSchedule_ID()),
						ShipmentScheduleId.ofRepoId(closed.getM_ShipmentSchedule_ID()))))
				.quantityType(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
				.build();

		final List<Integer> matchingIds = Services.get(IQueryBL.class).createQueryBuilder(I_M_ShipmentSchedule.class)
				.filter(parameters.getShipmentSchedulesQueryFiltersEffective())
				.create()
				.list()
				.stream()
				.map(I_M_ShipmentSchedule::getM_ShipmentSchedule_ID)
				.collect(Collectors.toList());

		assertThat(matchingIds).containsExactly(801);
	}

	/**
	 * Which shipment schedules a run puts into workpackages.
	 */
	@Nested
	class CreateWorkpackages
	{
		/**
		 * Review scenario: run A shipped schedule X with its entered quantity and closed it; X is not processed yet (the recompute did not run).
		 * A new "Auswahl Liefern" run C for X is attempted: no workpackage is created, so X's entered quantity is not shipped again.
		 * The open schedule Y, enqueued the same way, gets its workpackage.
		 */
		@Test
		void closedScheduleIsNotEnqueued_openScheduleIs()
		{
			final I_M_ShipmentSchedule shippedAndClosedX = createSchedule(811, true);
			shippedAndClosedX.setQtyDelivered(new BigDecimal("7"));
			InterfaceWrapperHelper.save(shippedAndClosedX);
			final I_M_ShipmentSchedule openY = createSchedule(812, false);

			assertThatThrownBy(() -> enqueue(shippedAndClosedX))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("NoValidRecords");
			assertThat(countWorkpackages()).isZero();

			final ShipmentScheduleEnqueuer.Result result = enqueue(openY);
			assertThat(result.getEnqueuedPackagesCount()).isEqualTo(1);
			assertThat(countWorkpackages()).isEqualTo(1);
		}

		/**
		 * Picks on a schedule that an earlier shipment closed (e.g. ship-on-LU-close: shipping LU1 closed X, X also has picks in LU2;
		 * or HU consolidation; or a schedule closed by hand while its picking job is open) are shipped by a later picked-quantity run.
		 * A pick cannot ship twice: a shipped pick has a shipment line and is no longer among the not-shipped picks the P path ships from.
		 */
		@Test
		void pickedQtyRun_closedScheduleWithPicksIsEnqueued()
		{
			final I_M_ShipmentSchedule closedWithPicks = createSchedule(821, true);
			createPick(closedWithPicks, new BigDecimal("5"));

			final ShipmentScheduleEnqueuer.Result result = enqueue(closedWithPicks, M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY);

			assertThat(result.getEnqueuedPackagesCount()).isEqualTo(1);
			assertThat(countWorkpackages()).isEqualTo(1);
		}

		/**
		 * A run that ships picks and the entered quantity (PD) leaves out a closed schedule too, because it would ship the entered quantity again.
		 */
		@Test
		void pickedAndEnteredQtyRun_closedScheduleIsNotEnqueued()
		{
			final I_M_ShipmentSchedule closed = createSchedule(831, true);

			assertThatThrownBy(() -> enqueue(closed, M_ShipmentSchedule_QuantityTypeToUse.TYPE_BOTH))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("NoValidRecords");
			assertThat(countWorkpackages()).isZero();
		}
	}

	/**
	 * Mirrors the production interceptor M_ShipmentSchedule.updateHeaderAggregationKey (BEFORE_NEW / BEFORE_CHANGE),
	 * which rebuilds the header key on every save. It can be switched off for the emulated PO UPDATE below,
	 * because PO runs the interceptor on the in-memory model and then writes the columns with plain SQL.
	 */
	@Interceptor(de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class)
	static class M_ShipmentSchedule
	{
		boolean enabled = true;

		@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
		public void updateHeaderAggregationKey(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule schedule)
		{
			if (enabled)
			{
				Services.get(IShipmentScheduleBL.class).updateHeaderAggregationKey(schedule);
			}
		}
	}

	/**
	 * A shipment run with its own async batch ships all its schedules of one order in one workpackage,
	 * whatever a schedule recompute saved between the run's batch stamp and the enqueue.
	 */
	@Nested
	class RunWithOwnAsyncBatch
	{
		private M_ShipmentSchedule interceptor;
		private IShipmentScheduleBL shipmentScheduleBL;

		/**
		 * The run stamps its async batch on its schedules (own committed trx in production), as ShipmentService.groupSchedulesByAsyncBatch does.
		 */
		private AsyncBatchId stampRunBatch(final I_M_ShipmentSchedule... schedules)
		{
			final I_C_Async_Batch asyncBatch = InterfaceWrapperHelper.newInstance(I_C_Async_Batch.class);
			InterfaceWrapperHelper.save(asyncBatch);
			final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoId(asyncBatch.getC_Async_Batch_ID());
			shipmentScheduleBL.setAsyncBatchAndSave(ImmutableList.copyOf(schedules), asyncBatchId);
			return asyncBatchId;
		}

		@BeforeEach
		void beforeEach()
		{
			SpringContextHolder.registerJUnitBean(new ShipmentScheduleAllowConsolidatePredicateComposite(ImmutableList.of(sched -> false)));
			InOutCandidateValidator.registerSSAggregationKeyDependencies();
			shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
			interceptor = new M_ShipmentSchedule();
			Services.get(IModelInterceptorRegistry.class).addModelInterceptor(interceptor);
		}

		/**
		 * Race: a schedule recompute loads schedule 2 BEFORE the shipment run stamps the run's async batch
		 * (ShipmentService.groupSchedulesByAsyncBatch -> ShipmentScheduleBL.setAsyncBatchAndSave), and saves it AFTER the stamp but BEFORE the enqueuer
		 * reads the schedules. The recompute leaves C_Async_Batch_ID alone (loaded 0 == order 0, OrderLineShipmentScheduleHandler.updateShipmentScheduleFromOrder),
		 * but its save rebuilds HeaderAggregationKey from the stale in-memory model. Both schedules belong to one run, so they get one workpackage.
		 */
		@Test
		void staleRecomputeSaveBetweenBatchStampAndEnqueue_oneWorkpackage()
		{
			final I_M_ShipmentSchedule schedule1 = createScheduleOfOneOrder(841);
			final I_M_ShipmentSchedule schedule2 = createScheduleOfOneOrder(842);

			// the recompute loads schedule 2 before the run stamps the batch
			final I_M_ShipmentSchedule staleSchedule2 = InterfaceWrapperHelper.load(842, I_M_ShipmentSchedule.class);

			final AsyncBatchId asyncBatchId = stampRunBatch(schedule1, schedule2);
			final String stampedKey2 = InterfaceWrapperHelper.load(842, I_M_ShipmentSchedule.class).getHeaderAggregationKey();
			assertThat(stampedKey2).as("the stamp puts the batch into the stored key").isEqualTo(InterfaceWrapperHelper.load(841, I_M_ShipmentSchedule.class).getHeaderAggregationKey());
			assertThat(staleSchedule2.getC_Async_Batch_ID()).as("the recompute's copy is stale").isZero();

			// the recompute's save: a real change, then PO's BEFORE_CHANGE interceptor, then PO's UPDATE of every column a setter was called for
			saveLikePO(staleSchedule2, stale -> stale.setQtyToDeliver(new BigDecimal("3")), interceptor);

			final I_M_ShipmentSchedule storedSchedule2 = InterfaceWrapperHelper.load(842, I_M_ShipmentSchedule.class);
			assertThat(storedSchedule2.getC_Async_Batch_ID()).as("the recompute keeps the run's batch").isEqualTo(asyncBatchId.getRepoId());
			assertThat(storedSchedule2.getHeaderAggregationKey()).as("a save that changes no key column keeps the stored key, which includes the run's batch").isEqualTo(stampedKey2);

			final ShipmentScheduleEnqueuer.Result result = enqueueRun(asyncBatchId, schedule1, schedule2);

			assertThat(result.getEnqueuedPackagesCount()).as("one run, one workpackage; stored key of schedule 2 = %s", storedSchedule2.getHeaderAggregationKey()).isEqualTo(1);
		}

		/**
		 * Fresh timing between the run's batch stamp and the enqueue: the stamp changes HeaderAggregationKey, so
		 * M_ShipmentSchedule.invalidateSchedulesWithOldAndNewHeaderAggregationKey flags the order's schedules for a recompute. A recompute that loads
		 * schedule 2 AFTER the stamp sees the run's batch, which differs from the order's empty batch, so
		 * OrderLineShipmentScheduleHandler.updateShipmentScheduleFromOrder (the "set only on a real change" guard) clears C_Async_Batch_ID;
		 * the save rebuilds HeaderAggregationKey without the batch. If that save commits before the enqueuer reads the schedules,
		 * both schedules still belong to one run, so they get one workpackage.
		 */
		@Test
		void freshRecomputeClearsBatchBetweenBatchStampAndEnqueue_oneWorkpackage()
		{
			final I_M_ShipmentSchedule schedule1 = createScheduleOfOneOrder(851);
			final I_M_ShipmentSchedule schedule2 = createScheduleOfOneOrder(852);

			final AsyncBatchId asyncBatchId = stampRunBatch(schedule1, schedule2);

			// the recompute loads schedule 2 after the stamp and copies the order's empty batch (same guard as OrderLineShipmentScheduleHandler.updateShipmentScheduleFromOrder)
			final I_M_ShipmentSchedule freshSchedule2 = InterfaceWrapperHelper.load(852, I_M_ShipmentSchedule.class);
			final int orderAsyncBatchId = 0;
			if (freshSchedule2.getC_Async_Batch_ID() != orderAsyncBatchId)
			{
				freshSchedule2.setC_Async_Batch_ID(orderAsyncBatchId);
			}
			InterfaceWrapperHelper.save(freshSchedule2);

			final I_M_ShipmentSchedule storedSchedule2 = InterfaceWrapperHelper.load(852, I_M_ShipmentSchedule.class);
			assertThat(storedSchedule2.getC_Async_Batch_ID()).as("the fresh recompute cleared the run's batch").isZero();

			final ShipmentScheduleEnqueuer.Result result = enqueueRun(asyncBatchId, schedule1, schedule2);

			assertThat(result.getEnqueuedPackagesCount()).as("one run, one workpackage; stored key of schedule 2 = %s", storedSchedule2.getHeaderAggregationKey()).isEqualTo(1);
		}
	}

	/**
	 * Emulates PO.save of a model that was loaded earlier: the in-memory changes and the BEFORE_CHANGE interceptor run on the stale model,
	 * then PO.saveUpdate writes every column whose setter was called (m_newValues != null, PO.java saveUpdate), equal to the loaded value or not;
	 * all other columns keep what is in the DB now. The in-memory POJO save would instead write the whole stale copy.
	 */
	private void saveLikePO(
			final I_M_ShipmentSchedule staleModel,
			final Consumer<I_M_ShipmentSchedule> changes,
			final M_ShipmentSchedule interceptor)
	{
		// record every setter call on the stale model: a proxy over a spy of its POJOWrapper (setters end in POJOWrapper.setValue)
		final POJOWrapper recordingWrapper = Mockito.spy(POJOWrapper.getWrapper(staleModel));
		final I_M_ShipmentSchedule recorder = (I_M_ShipmentSchedule)Proxy.newProxyInstance(
				I_M_ShipmentSchedule.class.getClassLoader(), new Class<?>[] { I_M_ShipmentSchedule.class }, recordingWrapper);
		changes.accept(recorder);
		interceptor.updateHeaderAggregationKey(recorder);

		final I_M_ShipmentSchedule current = InterfaceWrapperHelper.load(staleModel.getM_ShipmentSchedule_ID(), I_M_ShipmentSchedule.class);
		for (final Invocation invocation : Mockito.mockingDetails(recordingWrapper).getInvocations())
		{
			if ("setValue".equals(invocation.getMethod().getName()))
			{
				POJOWrapper.getWrapper(current).setValue((String)invocation.getArgument(0), invocation.getArgument(1));
			}
		}
		interceptor.enabled = false; // the UPDATE itself runs no interceptor
		try
		{
			InterfaceWrapperHelper.save(current);
		}
		finally
		{
			interceptor.enabled = true;
		}
	}

	private ShipmentScheduleEnqueuer.Result enqueue(final I_M_ShipmentSchedule schedule)
	{
		return enqueue(schedule, M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);
	}

	private ShipmentScheduleEnqueuer.Result enqueue(final I_M_ShipmentSchedule schedule, final M_ShipmentSchedule_QuantityTypeToUse quantityType)
	{
		return enqueue(null, quantityType, schedule);
	}

	private ShipmentScheduleEnqueuer.Result enqueueRun(@NonNull final AsyncBatchId asyncBatchId, final I_M_ShipmentSchedule... schedules)
	{
		return enqueue(asyncBatchId, M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER, schedules);
	}

	private ShipmentScheduleEnqueuer.Result enqueue(
			@Nullable final AsyncBatchId asyncBatchId,
			final M_ShipmentSchedule_QuantityTypeToUse quantityType,
			final I_M_ShipmentSchedule... schedules)
	{
		final ImmutableSet<ShipmentScheduleId> scheduleIds = Arrays.stream(schedules)
				.map(schedule -> ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID()))
				.collect(ImmutableSet.toImmutableSet());
		return ShipmentScheduleEnqueuer.newInstance(Env.getCtx(), ITrx.TRXNAME_None)
				.createWorkpackages(ShipmentScheduleWorkPackageParameters.builder()
						.adPInstanceId(PInstanceId.ofRepoId(1))
						.scheduleIds(ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(scheduleIds))
						.quantityType(quantityType)
						.asyncBatchId(asyncBatchId)
						.completeShipments(true)
						.build());
	}

	private int countWorkpackages()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Queue_WorkPackage.class).create().count();
	}

	private void createPick(final I_M_ShipmentSchedule schedule, final BigDecimal qtyPicked)
	{
		final I_M_ShipmentSchedule_QtyPicked pick = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		pick.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
		pick.setQtyPicked(qtyPicked);
		InterfaceWrapperHelper.save(pick);
	}

	/**
	 * Same header values (partner, location, warehouse) for every id, so the schedules belong in one shipment.
	 */
	private I_M_ShipmentSchedule createScheduleOfOneOrder(final int id)
	{
		return createSchedule(id, schedule -> {
			schedule.setC_BPartner_ID(100);
			schedule.setC_BPartner_Location_ID(200);
			schedule.setM_Warehouse_ID(300);
		});
	}

	private I_M_ShipmentSchedule createSchedule(final int id, final boolean closed)
	{
		return createSchedule(id, schedule -> {
			schedule.setIsClosed(closed);
			schedule.setProcessed(false); // closed, but the recompute did not process it yet
		});
	}

	private I_M_ShipmentSchedule createSchedule(final int id, final Consumer<I_M_ShipmentSchedule> customizer)
	{
		final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_ShipmentSchedule_ID(id);
		schedule.setQtyToDeliver_Override(new BigDecimal("7"));
		customizer.accept(schedule);
		InterfaceWrapperHelper.save(schedule);
		return schedule;
	}
}
