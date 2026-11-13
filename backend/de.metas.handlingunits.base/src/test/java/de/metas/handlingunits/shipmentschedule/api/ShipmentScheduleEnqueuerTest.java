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

import com.google.common.collect.ImmutableSet;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.impl.ShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.lock.api.impl.PlainLockManager;
import de.metas.lock.exceptions.LockFailedException;
import de.metas.lock.spi.impl.PlainLockDatabase;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Covers {@link ShipmentScheduleEnqueuer}: the "no valid records" outcome and the lock handling of a run,
 * and that a closed shipment schedule is not put into a new shipment run that ships the entered quantity (quantity type D / PD),
 * but is put into a new run that ships picks only (quantity type P).
 */
class ShipmentScheduleEnqueuerTest
{
	private static final AdMessageKey MSG_NO_VALID_RECORDS = AdMessageKey.of("de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords");

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IShipmentScheduleInvalidateBL.class, new ShipmentScheduleInvalidateBL(new PickingBOMService())
		{
			@Override
			public boolean isFlaggedForRecompute(@NonNull final ShipmentScheduleId shipmentScheduleId)
			{
				return false; // the real check is a plain SQL query, which the in-memory test database can't run
			}
		});
	}

	/**
	 * The selection matched no shipment schedule when it was locked, and one became eligible right afterwards
	 * (e.g. the recompute committed its quantity to deliver in between).
	 * Nothing was shippable when "generate shipments" locked its selection, so the user gets the "no valid records" message.
	 * <p>
	 * A lock manager double locks nothing for the selection filter. On a real database, holding the lock statement back while only
	 * not yet eligible schedules are selected needs a picking job schedule in the selection (the only other record that statement locks).
	 */
	@Test
	void emptyLock_failsWithNoValidRecords()
	{
		Services.registerService(ILockManager.class, new NothingMatchedAtLockTimeLockManager());

		final I_M_ShipmentSchedule eligibleAfterLocking = newInstance(I_M_ShipmentSchedule.class);
		eligibleAfterLocking.setHeaderAggregationKey("headerAggregationKey");
		saveRecord(eligibleAfterLocking);

		final ShipmentScheduleWorkPackageParameters workPackageParameters = ShipmentScheduleWorkPackageParameters.builder()
				.adPInstanceId(PInstanceId.ofRepoId(1))
				.queryFilters(Services.get(IQueryBL.class).createCompositeQueryFilter(I_M_ShipmentSchedule.class)
						.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, eligibleAfterLocking.getM_ShipmentSchedule_ID()))
				.quantityType(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
				.completeShipments(true)
				.isShipmentDateToday(false)
				.build();

		final Throwable failure = catchThrowable(() -> ShipmentScheduleEnqueuer.newInstance().createWorkpackages(workPackageParameters));

		final String expectedMessage = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_NO_VALID_RECORDS).translate(Env.getAD_Language());
		assertThat(failure)
				.as("Outcome of generating shipments for a selection that matched nothing when it was locked")
				.isInstanceOf(AdempiereException.class)
				.isNotInstanceOf(LockFailedException.class)
				.hasMessage(expectedMessage);
	}

	/**
	 * Only the schedules locked by the run's main lock are enqueued (one that became eligible after locking is left for the next run),
	 * and they are read over a snapshot, so that the lock handover to the work packages does not shrink the result while it is iterated.
	 */
	@Test
	void lockedSchedulesQuery_iteratesOverSnapshot()
	{
		final ILockManager lockManager = Services.get(ILockManager.class);

		final I_M_ShipmentSchedule locked = newInstance(I_M_ShipmentSchedule.class);
		locked.setHeaderAggregationKey("key");
		saveRecord(locked);
		final I_M_ShipmentSchedule notLocked = newInstance(I_M_ShipmentSchedule.class);
		notLocked.setHeaderAggregationKey("key");
		saveRecord(notLocked);

		final I_M_ShipmentSchedule lockedButFilteredOut = newInstance(I_M_ShipmentSchedule.class);
		lockedButFilteredOut.setHeaderAggregationKey("key");
		lockedButFilteredOut.setIsActive(false);
		saveRecord(lockedButFilteredOut);

		final ILock mainLock = lockManager.lock()
				.setOwner(LockOwner.newOwner("ShipmentScheduleEnqueuerTest"))
				.setAutoCleanup(true)
				.addRecordByModel(locked)
				.addRecordByModel(lockedButFilteredOut)
				.acquire();

		final IQuery<I_M_ShipmentSchedule> query = ShipmentScheduleEnqueuer.newInstance()
				.createLockedShipmentSchedulesQuery(mainLock, Services.get(IQueryBL.class).createCompositeQueryFilter(I_M_ShipmentSchedule.class)
						.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, locked.getM_ShipmentSchedule_ID(), notLocked.getM_ShipmentSchedule_ID(), lockedButFilteredOut.getM_ShipmentSchedule_ID())
						.addOnlyActiveRecordsFilter());

		assertThat(query.<Boolean>getOption(IQuery.OPTION_GuaranteedIteratorRequired)).isTrue();
		final List<I_M_ShipmentSchedule> result = query.list(I_M_ShipmentSchedule.class);
		assertThat(result).extracting(I_M_ShipmentSchedule::getM_ShipmentSchedule_ID).containsExactly(locked.getM_ShipmentSchedule_ID());
	}

	/**
	 * Locks nothing for a selection filter, as if no record matched the filter at lock time.
	 */
	private static class NothingMatchedAtLockTimeLockManager extends PlainLockManager
	{
		private final PlainLockDatabase lockDatabase = new PlainLockDatabase()
		{
			@Override
			protected int lockByFilters(final ILockCommand lockCommand)
			{
				return 0;
			}
		};

		@Override
		public PlainLockDatabase getLockDatabase()
		{
			return lockDatabase;
		}
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
	 * Review scenario: run A shipped schedule X with its entered quantity and closed it; X is not processed yet (the recompute did not run).
	 * A new "Auswahl Liefern" run C for X is attempted: no workpackage is created, so X's entered quantity is not shipped again.
	 * The open schedule Y, enqueued the same way, gets its workpackage.
	 */
	@Test
	void createWorkpackages_closedScheduleIsNotEnqueued_openScheduleIs()
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
	void createWorkpackages_pickedQtyRun_closedScheduleWithPicksIsEnqueued()
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
	void createWorkpackages_pickedAndEnteredQtyRun_closedScheduleIsNotEnqueued()
	{
		final I_M_ShipmentSchedule closed = createSchedule(831, true);

		assertThatThrownBy(() -> enqueue(closed, M_ShipmentSchedule_QuantityTypeToUse.TYPE_BOTH))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("NoValidRecords");
		assertThat(countWorkpackages()).isZero();
	}

	private ShipmentScheduleEnqueuer.Result enqueue(final I_M_ShipmentSchedule schedule)
	{
		return enqueue(schedule, M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);
	}

	private ShipmentScheduleEnqueuer.Result enqueue(final I_M_ShipmentSchedule schedule, final M_ShipmentSchedule_QuantityTypeToUse quantityType)
	{
		return ShipmentScheduleEnqueuer.newInstance(Env.getCtx(), ITrx.TRXNAME_None)
				.createWorkpackages(ShipmentScheduleWorkPackageParameters.builder()
						.adPInstanceId(PInstanceId.ofRepoId(1))
						.scheduleIds(ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(ImmutableSet.of(ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID()))))
						.quantityType(quantityType)
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

	private I_M_ShipmentSchedule createSchedule(final int id, final boolean closed)
	{
		final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_ShipmentSchedule_ID(id);
		schedule.setIsClosed(closed);
		schedule.setProcessed(false); // closed, but the recompute did not process it yet
		schedule.setQtyToDeliver_Override(new BigDecimal("7"));
		InterfaceWrapperHelper.save(schedule);
		return schedule;
	}
}
