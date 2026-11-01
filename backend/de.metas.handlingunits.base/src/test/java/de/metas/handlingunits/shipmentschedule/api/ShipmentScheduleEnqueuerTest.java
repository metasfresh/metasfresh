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
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * A closed shipment schedule is not put into a new shipment run that ships the entered quantity (quantity type D / PD),
 * but it is put into a new run that ships picks only (quantity type P).
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
