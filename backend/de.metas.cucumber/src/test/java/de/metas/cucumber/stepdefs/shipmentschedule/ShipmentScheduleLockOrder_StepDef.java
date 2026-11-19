/*
 * #%L
 * de.metas.cucumber
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
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.shipmentschedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderId;
import de.metas.util.Services;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps that check that the writers of an order's shipment schedules lock them in ascending {@code M_ShipmentSchedule_ID} order,
 * see {@link ShipmentScheduleRowLockProbe}.
 */
@RequiredArgsConstructor
public class ShipmentScheduleLockOrder_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IShipmentScheduleInvalidateRepository invalidSchedulesRepo = Services.get(IShipmentScheduleInvalidateRepository.class);

	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final ShipmentScheduleRecomputeRaceHarness recomputeRaceHarness;

	/**
	 * Flags all shipment schedules of the order for recompute, then runs the recompute pass and records in which order it locks them.
	 * Asserts that it locks all of them, in ascending {@code M_ShipmentSchedule_ID} order, and that it succeeds. Requires the paused
	 * recompute queues, and a recompute that changes each of the order's shipment schedules (e.g. their first one).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the shipment schedule recompute pass locks the shipment schedules of the order identified by order_1 in ID order
	 * </pre>
	 */
	@Then("^the shipment schedule recompute pass locks the shipment schedules of the order identified by (.*) in ID order$")
	public void recomputePassLocksInIdOrder(@NonNull final String orderIdentifier)
	{
		final OrderId orderId = OrderId.ofRepoId(orderTable.get(orderIdentifier).getC_Order_ID());
		final ImmutableList<Integer> shipmentScheduleIds = retrieveShipmentScheduleIds(orderId);

		trxManager.runInNewTrx(() -> invalidSchedulesRepo.invalidateShipmentSchedules(
				shipmentScheduleIds.stream().map(ShipmentScheduleId::ofRepoId).collect(ImmutableSet.toImmutableSet())));

		final ShipmentScheduleRowLockProbe.Result result = ShipmentScheduleRowLockProbe.recordLockOrder(shipmentScheduleIds, recomputeRaceHarness::runPendingRecomputePass);

		assertThat(result.lockOrder)
				.as("M_ShipmentSchedule_IDs of order %s in the order in which the recompute pass locked them", orderIdentifier)
				.containsExactlyElementsOf(shipmentScheduleIds);
		assertThat(result.writerFailure)
				.as("Failure of the recompute pass")
				.isNull();
	}

	/**
	 * Reactivates the order and records in which order the reactivation locks the order's shipment schedules. Asserts that it locks all of
	 * them, in ascending {@code M_ShipmentSchedule_ID} order, and that it succeeds and closes them.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the reactivation of the order identified by order_1 locks its shipment schedules in ID order
	 * </pre>
	 */
	@Then("^the reactivation of the order identified by (.*) locks its shipment schedules in ID order$")
	public void reactivationLocksInIdOrder(@NonNull final String orderIdentifier)
	{
		final OrderId orderId = OrderId.ofRepoId(orderTable.get(orderIdentifier).getC_Order_ID());
		final ImmutableList<Integer> shipmentScheduleIds = retrieveShipmentScheduleIds(orderId);

		final ShipmentScheduleRowLockProbe.Result result = ShipmentScheduleRowLockProbe.recordLockOrder(shipmentScheduleIds, () -> {
			trxManager.runInNewTrx(() -> {
				final I_C_Order order = InterfaceWrapperHelper.load(orderId, I_C_Order.class); // loaded in the new, thread-inherited transaction
				documentBL.processEx(order, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);
			});
			return null;
		});

		assertThat(result.lockOrder)
				.as("M_ShipmentSchedule_IDs of order %s in the order in which the reactivation locked them", orderIdentifier)
				.containsExactlyElementsOf(shipmentScheduleIds);
		assertThat(result.writerFailure)
				.as("Failure of the reactivation")
				.isNull();
		assertThat(queryBL.createQueryBuilderOutOfTrx(I_M_ShipmentSchedule.class)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_IsClosed, false)
				.create()
				.count())
				.as("Shipment schedules of order %s that are still open after its reactivation", orderIdentifier)
				.isZero();

		// a fresh instance for later doc actions, like a new request would have
		orderTable.putOrReplace(orderIdentifier, InterfaceWrapperHelper.load(orderId, I_C_Order.class));
	}

	private ImmutableList<Integer> retrieveShipmentScheduleIds(@NonNull final OrderId orderId)
	{
		final ImmutableList<Integer> ids = ImmutableList.copyOf(queryBL.createQueryBuilderOutOfTrx(I_M_ShipmentSchedule.class)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, orderId)
				.orderBy(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID)
				.create()
				.listIds());
		assertThat(ids).as("Shipment schedules of C_Order_ID=%s", orderId.getRepoId()).hasSizeGreaterThanOrEqualTo(2);
		return ids;
	}
}
