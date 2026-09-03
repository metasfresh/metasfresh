package de.metas.inoutcandidate.modelvalidator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.document.exception.DocumentActionException;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;

import java.util.Collections;
import java.util.List;

@Validator(I_C_Order.class)
public class C_Order_ReceiptSchedule
{
	private static final AdMessageKey ERR_NoReactivationIfReceiptsCreated = AdMessageKey.of("ERR_NoReactivationIfReceiptsCreated");
	private static final AdMessageKey ERR_NoReactivationIfProcessedReceiptSchedules = AdMessageKey.of("ERR_NoReactivationIfProcessedReceiptSchedules");
	private static final AdMessageKey ERR_NoVoidIfProcessedReceiptSchedules = AdMessageKey.of("ERR_NoVoidIfProcessedReceiptSchedules");
	private static final String SYSCONFIG_PO_ALLOW_REACTIVATION_IF_RECEIPTS_CREATED = "PO_AllowReactivationIfReceiptsCreated";

	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	static boolean isEligibleForReceiptSchedule(final I_C_Order order)
	{
		final IOrderBL orderBL = Services.get(IOrderBL.class);

		Check.assumeNotNull(order, "order not null");

		// Only Purchase Orders are handled
		return !order.isSOTrx()
				&& !orderBL.isRequisition(order)
				&& !orderBL.isMediated(order);
	}

	/**
	 * On COMPLETE: first reopen any previously closed receipt schedules, then create new ones
	 * for order lines that don't have one yet.
	 * <p>
	 * Declaration order matters: reopenReceiptSchedules must run before createReceiptSchedules
	 * so that reopened schedules already have their final QtyOrdered and ASI when
	 * createReceiptSchedules fires the async workpackage (which reads these fields).
	 * The reopen combines IsClosed + QtyOrdered + ASI into a single save to produce exactly one
	 * ReceiptScheduleCreatedEvent with the correct storageAttributesKey.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void reopenReceiptSchedules(final I_C_Order order)
	{
		if (!isEligibleForReceiptSchedule(order))
		{
			return;
		}

		receiptScheduleBL.reopenReceiptSchedulesForOrder(order);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void createReceiptSchedules(final I_C_Order order)
	{
		if (!isEligibleForReceiptSchedule(order))
		{
			return;
		}

		// services
		final IReceiptScheduleProducerFactory receiptScheduleProducerFactory = Services.get(IReceiptScheduleProducerFactory.class);

		//
		// Generate receipt schedules from this order.
		// NOTE: because we got performance issues when there are a lot of data to be generated for each order line
		// (e.g. de.metas.handlingunits had to generate a lot of HUs for each order line)
		// We decided to create a workpackage for each order line (08168).

		// Get the receipt schedule producer
		final boolean createReceiptSchedulesAsync = true;
		final IReceiptScheduleProducer receiptScheduleProducer = receiptScheduleProducerFactory.createProducer(I_C_OrderLine.Table_Name, createReceiptSchedulesAsync);

		// Iterate order lines and call the producer for each of them.
		// NOTE: we intentionally do NOT skip order lines that already have a reopened receipt schedule.
		// The async workpackage will find the existing schedule and run the update path, which
		// synchronises M_AttributeSetInstance_ID (via cloneOrCreateASI) and other fields from the
		// order line. If the ASI hasn't changed, the storageAttributesKey stays the same and the
		// resulting ReceiptScheduleUpdatedEvent has zero deltas (no-op). If the ASI DID change
		// (e.g. user changed it during reactivation), the event correctly moves cockpit quantities
		// from the old to the new storageAttributesKey bucket.
		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order);
		for (final I_C_OrderLine orderLine : orderLines)
		{
			final List<I_M_ReceiptSchedule> previousSchedules = Collections.emptyList();
			receiptScheduleProducer.createOrUpdateReceiptSchedules(orderLine, previousSchedules);
		}
	}

	/**
	 * On REACTIVATE and VOID: close receipt schedules instead of deleting them.
	 * This preserves user modifications (delivery dates, override columns, transport orders).
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE, ModelValidator.TIMING_AFTER_VOID })
	public void closeReceiptSchedulesOnReactivateOrVoid(final I_C_Order order)
	{
		if (!isEligibleForReceiptSchedule(order))
		{
			return;
		}

		receiptScheduleBL.closeReceiptSchedulesForOrder(order);
	}

	/**
	 * Never reactivate a purchase order that already has created receipts.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void reactivateIfNoReceipts(final I_C_Order order)
	{
		if (!isEligibleForReceiptSchedule(order))
		{
			return;
		}

		// Throw an exception if at least one (even partial) receipt is linked to this order
		if (!isAllowReactivationIfReceiptsCreated() && hasReceipts(order))
		{
			throw new DocumentActionException(ERR_NoReactivationIfReceiptsCreated);
		}
	}

	private boolean isAllowReactivationIfReceiptsCreated()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_PO_ALLOW_REACTIVATION_IF_RECEIPTS_CREATED, false);
	}

	private boolean hasReceipts(final I_C_Order order)
	{
		final List<I_M_InOut> inouts = orderDAO.retrieveInOutsForMatchingOrderLines(order);

		return !inouts.isEmpty();
	}

	/**
	 * Never reactivate an order if it already has processed receipt schedules.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void reactivateIfNoReceiptScheduleProcessed(final I_C_Order order)
	{
		if (!isEligibleForReceiptSchedule(order))
		{
			return;
		}

		final boolean isAllowReactivationIfReceiptsCreated = isAllowReactivationIfReceiptsCreated();
		if (!isAllowReactivationIfReceiptsCreated && hasProcessedReceiptSchedules(order))
		{
			throw new DocumentActionException(ERR_NoReactivationIfProcessedReceiptSchedules);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_VOID })
	public void VoidIfNoReceiptScheduleProcessed(final I_C_Order order)
	{
		if (!isEligibleForReceiptSchedule(order))
		{
			return;
		}

		if (hasProcessedReceiptSchedules(order))
		{
			throw new DocumentActionException(ERR_NoVoidIfProcessedReceiptSchedules);

		}
	}

	public boolean hasProcessedReceiptSchedules(final I_C_Order order)
	{
		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order);
		for (final I_C_OrderLine orderLine : orderLines)
		{
			final I_M_ReceiptSchedule receiptSchedule = receiptScheduleDAO.retrieveForRecord(orderLine);

			// Return true only for schedules processed from REAL receipt activity,
			// not schedules that are merely "parked" (IsClosed=Y) due to PO reactivation/void.
			if (receiptSchedule != null && receiptSchedule.isProcessed() && !receiptSchedule.isIsClosed())
			{
				return true;
			}
		}

		return false;
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_CLOSE)
	public void closeReceiptSchedules(final I_C_Order order)
	{
		if (!isEligibleForReceiptSchedule(order))
		{
			return;
		}

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order);
		for (final I_C_OrderLine orderLine : orderLines)
		{
			final I_M_ReceiptSchedule receiptSchedule = receiptScheduleDAO.retrieveForRecord(orderLine);

			if (receiptSchedule == null || receiptScheduleBL.isClosed(receiptSchedule))
			{
				continue;
			}

			receiptScheduleBL.close(receiptSchedule);
		}
	}
}
