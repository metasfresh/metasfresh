/*
 * #%L
 * de-metas-salesorder
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.salesorder.interceptor;

import ch.qos.logback.classic.Level;
import de.metas.async.AsyncBatchId;
import de.metas.async.service.AsyncBatchObserver;
import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;
import de.metas.logging.LogManager;
import de.metas.order.DeliveryRule;
import de.metas.order.OrderId;
import de.metas.salesorder.async.CompleteShipAndInvoiceEnqueuer;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order.class)
@Component
public class C_Order_AutoProcess_Async
{
	private static final Logger logger = LogManager.getLogger(C_Order_AutoProcess_Async.class);

	private final static String SYS_Config_AUTO_SHIP_AND_INVOICE = "AUTO_SHIP_AND_INVOICE";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final CompleteShipAndInvoiceEnqueuer completeShipAndInvoiceEnqueuer;
	private final AsyncBatchObserver asyncBatchObserver;

	public C_Order_AutoProcess_Async(
			@NonNull final CompleteShipAndInvoiceEnqueuer completeShipAndInvoiceEnqueuer,
			@NonNull final AsyncBatchObserver asyncBatchObserver)
	{
		this.completeShipAndInvoiceEnqueuer = completeShipAndInvoiceEnqueuer;
		this.asyncBatchObserver = asyncBatchObserver;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void createMissingShipmentSchedules(@NonNull final I_C_Order orderRecord)
	{
		if (orderRecord.isSOTrx())
		{
			trxManager
					.getTrxListenerManager(InterfaceWrapperHelper.getTrxName(orderRecord))
					.runAfterCommit(() -> enqueueGenerateSchedulesAfterCommit(orderRecord));
		}
	}

	private void enqueueGenerateSchedulesAfterCommit(@NonNull final I_C_Order orderRecord)
	{
		final OrderId orderId = OrderId.ofRepoId(orderRecord.getC_Order_ID());

		if (isEligibleForAutoProcessing(orderRecord))
		{
			Loggables.withLogger(logger, Level.INFO).addLog("OrderId: {} qualified for auto ship and invoice! Enqueueing order.", orderId);
			final String trxName = InterfaceWrapperHelper.getTrxName(orderRecord);
			completeShipAndInvoiceEnqueuer.enqueue(orderId, trxName);
		}
		else
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Schedule generating missing shipments for orderId: {}", orderId);
			CreateMissingShipmentSchedulesWorkpackageProcessor.scheduleIfNotPostponed(orderRecord);
		}
	}

	private boolean isEligibleForAutoProcessing(@NonNull final I_C_Order orderRecord)
	{
		final boolean featureEnabled = sysConfigBL.getBooleanValue(SYS_Config_AUTO_SHIP_AND_INVOICE, false, orderRecord.getAD_Client_ID(), orderRecord.getAD_Org_ID());

		final DeliveryRule deliveryRule = DeliveryRule.ofCode(orderRecord.getDeliveryRule());

		final boolean canDoAutoShipAndInvoice = featureEnabled && deliveryRule.isBasedOnDelivery();
		if (!canDoAutoShipAndInvoice)
		{
			return false;
		}

		//dev-note: check to see if the order is not already involved in another async job
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(orderRecord.getC_Async_Batch_ID());

		if (asyncBatchId == null)
		{
			return true;
		}

		return !asyncBatchObserver.isAsyncBatchObserved(asyncBatchId);
	}
}
