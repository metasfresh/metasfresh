package de.metas.procurement.base.order.interceptor;

import java.math.BigDecimal;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.procurement.base.balance.IPMMBalanceChangeEventProcessor;
import de.metas.procurement.base.balance.PMMBalanceChangeEvent;
import de.metas.procurement.base.order.model.I_C_OrderLine;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Interceptor(I_C_OrderLine.class)
public class C_OrderLine
{
	private static final Logger logger = LogManager.getLogger(C_OrderLine.class);

	public static final transient C_OrderLine instance = new C_OrderLine();

	private C_OrderLine()
	{
		super();
	}

	private boolean isEligibleForTrackingQtyDelivered(final I_C_OrderLine orderLine)
	{
		// NOTE: according to FRESH-191, we shall track the QtyDelivered no matter what.
		// if (!orderLine.isMFProcurement()) return false;

		// NOTE2: keep in sync with PMM_Balance_Events_v view.

		// Track only purchase orders (FRESH-191)
		final I_C_Order order = orderLine.getC_Order();
		if (order == null || order.isSOTrx())
		{
			return false;
		}

		return true;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_C_OrderLine.COLUMNNAME_QtyDelivered)
	public void onQtyDeliveredChanged(final I_C_OrderLine orderLine)
	{
		// Do nothing if it's not eligible
		if (!isEligibleForTrackingQtyDelivered(orderLine))
		{
			return;
		}

		//
		// Calculate QtyDelivered (diff)
		final BigDecimal qtyDeliveredNew = orderLine.getQtyDelivered();
		final I_C_OrderLine orderLineOld = InterfaceWrapperHelper.createOld(orderLine, I_C_OrderLine.class);
		final BigDecimal qtyDeliveredOld = orderLineOld.getQtyDelivered();
		final BigDecimal qtyDeliveredDiff = qtyDeliveredNew.subtract(qtyDeliveredOld);

		updatePMMBalance(orderLineOld, qtyDeliveredDiff);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void onDelete(final I_C_OrderLine orderLine)
	{
		// Do nothing if it's not eligible
		if (!isEligibleForTrackingQtyDelivered(orderLine))
		{
			return;
		}

		updatePMMBalance(orderLine, orderLine.getQtyDelivered().negate());
	}

	/**
	 * Update PMM_Balance's QtyDelivered (after commit).
	 *
	 * @param orderLine
	 * @param qtyDeliveredDiff
	 */
	private final void updatePMMBalance(final I_C_OrderLine orderLine, final BigDecimal qtyDeliveredDiff)
	{
		// If there is no change in qty delivered then do nothing.
		if (qtyDeliveredDiff.signum() == 0)
		{
			logger.debug("Skip updating the PMM_Balance for {} because QtyDeliveredDiff is zero", orderLine);
			return;
		}

		// Create the event to update PMM_Balance's QtyDelivered
		final PMMBalanceChangeEvent event = PMMBalanceChangeEvent.builder()
				.setC_BPartner_ID(orderLine.getC_BPartner_ID())
				.setC_Flatrate_DataEntry_ID(orderLine.getC_Flatrate_DataEntry_ID())
				.setM_Product_ID(orderLine.getM_Product_ID())
				.setM_AttributeSetInstance_ID(orderLine.getPMM_Contract_ASI_ID()) // important: use the contracted ASI and NOT the order line ASI which was generated
				.setM_HU_PI_Item_Product_ID(orderLine.getM_HU_PI_Item_Product_ID())
				.setDate(orderLine.getDatePromised())
				.setQtyDelivered(qtyDeliveredDiff)
				.build();

		//
		// Schedule event after commit
		logger.debug("Scheduling event: {}", event);
		final ITrxListenerManager trxListenerManager = Services.get(ITrxManager.class).getTrxListenerManagerOrAutoCommit(ITrx.TRXNAME_ThreadInherited);
		trxListenerManager
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerWeakly(false) // register "hard", because that's how it was before
				.registerHandlingMethod(innerTrx -> {
					Services.get(IPMMBalanceChangeEventProcessor.class).addEvent(event);
					logger.debug("Event sent: {}", event);
				});
	}
}
