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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


package de.metas.cucumber.stepdefs.shipmentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.workpackage.WorkPackageQueueUtil;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderId;
import de.metas.util.Services;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import javax.annotation.Nullable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions that interleave a sales order's reactivation with the creation of missing shipment schedules.
 */
@RequiredArgsConstructor
public class CreateMissingShipmentSchedules_ConcurrentReactivation_StepDef
{
	/**
	 * How long the reactivation's transaction is held open at most while the missing shipment schedules are created.
	 * The workpackage's commit waits for the reactivation (its foreign key checks lock order lines the reactivation updated and
	 * deleted), so this is longer than the workpackage's lock timeout.
	 */
	private static final int REACTIVATION_HOLD_SECONDS = 5;
	private static final int TIMEOUT_SECONDS = 60;
	private static final String CREATE_MISSING_PROCESSOR_SHORT_NAME = CreateMissingShipmentSchedulesWorkpackageProcessor.class.getSimpleName();

	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final WorkPackageQueueUtil workPackageQueueUtil;

	/**
	 * Completes the two given sales orders, then reactivates the first one in a transaction that stays open while a
	 * {@code CreateMissingShipmentSchedules} workpackage runs, with the workpackage's production settings (incl. its lock timeout).
	 * <p>
	 * This is the window in which the asynchronous creation of just-completed orders' shipment schedules meets the reactivation of
	 * one of them: the reactivation has deleted lines (e.g. the discount lines of a contract-created compensation group) but not yet
	 * committed, while the workpackage still sees the order as completed and its lines as lacking shipment schedules.
	 * <p>
	 * Asserts that
	 * <ul>
	 * <li>the workpackage's batch is rolled back - also the second order's schedules are not created - and the workpackage asks to
	 * be retried later, instead of failing;</li>
	 * <li>the reactivation succeeds;</li>
	 * <li>the retried workpackage succeeds and creates the second order's shipment schedules, and none for the deleted lines.</li>
	 * </ul>
	 * To make sure that the orders' lines still lack their shipment schedules when the reactivation starts, the completions don't
	 * enqueue the workpackage (as in production, if the async processor did not get to it yet); the workpackage, and its retry, are
	 * run by this step instead.
	 * <p>
	 * The first order must get at least one compensation line on completion (e.g. from a contract), and that line must need a
	 * shipment schedule. All lines of the second order must need shipment schedules.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the order identified by order_1 is completed together with the order identified by order_2, and then reactivated while their missing shipment schedules are being created
	 * </pre>
	 */
	@When("^the order identified by (.*) is completed together with the order identified by (.*), and then reactivated while their missing shipment schedules are being created$")
	public void completeThenReactivateWhileCreatingMissingShipmentSchedules(
			@NonNull final String orderIdentifier,
			@NonNull final String otherOrderIdentifier) throws InterruptedException
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		final I_C_Order otherOrder = orderTable.get(otherOrderIdentifier);
		final OrderId otherOrderId = OrderId.ofRepoId(otherOrder.getC_Order_ID());

		// A CreateMissingShipmentSchedules workpackage that is still pending from before would create the orders' shipment
		// schedules right after their completion, before the reactivation; let it finish first.
		StepDefUtil.tryAndWait(TIMEOUT_SECONDS, 500, () -> workPackageQueueUtil.countPendingWorkPackages(CREATE_MISSING_PROCESSOR_SHORT_NAME) == 0);

		try (final IAutoCloseable ignored = shipmentScheduleBL.postponeMissingSchedsCreationUntilClose())
		{
			complete(order);
			complete(otherOrder);

			final ImmutableList<Integer> compensationLineIds = retrieveCompensationLineIds(orderId);
			assertThat(compensationLineIds)
					.as("Compensation lines of the completed order %s", orderIdentifier)
					.isNotEmpty();
			assertThat(retrieveShipmentScheduleCount(orderId))
					.as("Shipment schedules of order %s right after its completion", orderIdentifier)
					.isZero();
			assertThat(retrieveShipmentScheduleCount(otherOrderId))
					.as("Shipment schedules of order %s right after its completion", otherOrderIdentifier)
					.isZero();

			final CountDownLatch reactivated = new CountDownLatch(1);
			final CountDownLatch release = new CountDownLatch(1);
			final AtomicReference<Throwable> reactivationFailure = new AtomicReference<>();

			final Thread reactivationThread = new Thread(
					() -> {
						try
						{
							trxManager.runInNewTrx(localTrxName -> {
								final I_C_Order orderInTrx = InterfaceWrapperHelper.load(orderId, I_C_Order.class); // loaded in the new, thread-inherited transaction
								documentBL.processEx(orderInTrx, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);
								reactivated.countDown();

								// Keep the reactivation (incl. its deleted lines) uncommitted while the workpackage runs; see REACTIVATION_HOLD_SECONDS.
								//noinspection ResultOfMethodCallIgnored
								release.await(REACTIVATION_HOLD_SECONDS, TimeUnit.SECONDS);
							});
						}
						catch (final Throwable t)
						{
							reactivationFailure.set(t);
						}
						finally
						{
							reactivated.countDown();
						}
					},
					"reactivate-" + orderIdentifier);
			reactivationThread.setDaemon(true);
			reactivationThread.start();

			final Throwable firstRunOutcome;
			try
			{
				assertThat(reactivated.await(TIMEOUT_SECONDS, TimeUnit.SECONDS))
						.as("Reactivation of order %s reached its end (uncommitted)", orderIdentifier)
						.isTrue();
				assertThat(reactivationFailure.get())
						.as("Failure while reactivating order %s", orderIdentifier)
						.isNull();
				assertThat(retrieveCompensationLineIds(orderId))
						.as("Before the reactivation commits, the compensation lines of order %s are still visible outside of its transaction", orderIdentifier)
						.containsExactlyElementsOf(compensationLineIds);

				firstRunOutcome = runCreateMissingShipmentSchedulesWorkpackage();
			}
			finally
			{
				release.countDown();
			}

			reactivationThread.join(TimeUnit.SECONDS.toMillis(TIMEOUT_SECONDS));
			assertThat(reactivationThread.isAlive()).as("Reactivation of order %s still running", orderIdentifier).isFalse();
			assertThat(reactivationFailure.get())
					.as("Failure while reactivating order %s", orderIdentifier)
					.isNull();

			assertThat(firstRunOutcome)
					.as("Outcome of creating the missing shipment schedules during the reactivation of order %s", orderIdentifier)
					.isInstanceOf(WorkpackageSkipRequestException.class);
			assertThat(retrieveShipmentScheduleCount(otherOrderId))
					.as("Shipment schedules of order %s after the rolled back batch", otherOrderIdentifier)
					.isZero();

			// the retry, like the async processor does after the skip timeout
			assertThat(runCreateMissingShipmentSchedulesWorkpackage())
					.as("Outcome of retrying the creation of the missing shipment schedules after the reactivation of order %s", orderIdentifier)
					.isNull();
			assertThat(retrieveShipmentScheduleCount(otherOrderId))
					.as("Shipment schedules of order %s after the retry", otherOrderIdentifier)
					.isEqualTo(retrieveOrderLineCount(otherOrderId));
			assertThat(retrieveShipmentScheduleCount(compensationLineIds))
					.as("Shipment schedules of the compensation lines that the reactivation of order %s deleted", orderIdentifier)
					.isZero();
		}

		// fresh instances for later doc actions, like a new request would have
		orderTable.putOrReplace(orderIdentifier, InterfaceWrapperHelper.load(orderId, I_C_Order.class));
		orderTable.putOrReplace(otherOrderIdentifier, InterfaceWrapperHelper.load(otherOrderId, I_C_Order.class));
	}

	private void complete(@NonNull final I_C_Order order)
	{
		order.setDocAction(IDocument.ACTION_Complete);
		documentBL.processEx(order, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	/**
	 * Runs a {@code CreateMissingShipmentSchedules} workpackage, like the async processor does.
	 *
	 * @return the exception the run ended with, or {@code null} if it succeeded
	 */
	@Nullable
	private Throwable runCreateMissingShipmentSchedulesWorkpackage()
	{
		final I_C_Queue_WorkPackage workPackage = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_Queue_WorkPackage.class);
		try
		{
			final IWorkpackageProcessor.Result result = new CreateMissingShipmentSchedulesWorkpackageProcessor().processWorkPackage(workPackage, ITrx.TRXNAME_None);
			assertThat(result).isEqualTo(IWorkpackageProcessor.Result.SUCCESS);
			return null;
		}
		catch (final RuntimeException e)
		{
			return e;
		}
	}

	private ImmutableList<Integer> retrieveCompensationLineIds(@NonNull final OrderId orderId)
	{
		return ImmutableList.copyOf(queryBL.createQueryBuilderOutOfTrx(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, true)
				.orderBy(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID)
				.create()
				.listIds());
	}

	private int retrieveOrderLineCount(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.count();
	}

	private int retrieveShipmentScheduleCount(@NonNull final ImmutableList<Integer> orderLineIds)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_M_ShipmentSchedule.class)
				.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID, orderLineIds)
				.create()
				.count();
	}

	private int retrieveShipmentScheduleCount(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_M_ShipmentSchedule.class)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.count();
	}
}
