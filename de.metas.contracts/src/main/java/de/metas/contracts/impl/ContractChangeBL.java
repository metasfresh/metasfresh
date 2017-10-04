package de.metas.contracts.impl;

/*
 * #%L
 * de.metas.contracts
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.slf4j.Logger;

import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IContractChangeDAO;
import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Contract_Change;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.ISubscriptionDAO.SubscriptionProgressQuery;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.logging.LogManager;
import de.metas.order.IOrderPA;

public class ContractChangeBL implements IContractChangeBL
{

	private static final Logger logger = LogManager.getLogger(ContractChangeBL.class);

	@Override
	public void cancelContract(
			final I_C_Flatrate_Term currentTerm,
			final Timestamp changeDate,
			final boolean isCloseInvoiceCandidate)
	{
		Check.assumeNotNull(currentTerm, "Param 'currentTerm' not null");
		Check.assumeNotNull(changeDate, "Param 'changeDate' not null");

		final IOrderPA orderPA = Services.get(IOrderPA.class);

		final ISubscriptionDAO subscriptionDAO = Services.get(ISubscriptionDAO.class);

		// get the subscription progress entries that we might be dealing with
		final SubscriptionProgressQuery currentTermQuery = SubscriptionProgressQuery.term(currentTerm).build();
		final List<I_C_SubscriptionProgress> sps = subscriptionDAO.retrieveSubscriptionProgresses(currentTermQuery);

		final Properties ctx = InterfaceWrapperHelper.getCtx(currentTerm);
		final String trxName = InterfaceWrapperHelper.getTrxName(currentTerm);

		if (changeDate.before(currentTerm.getEndDate()))
		{
			final I_C_Contract_Change changeConditions =
					Services.get(IContractChangeDAO.class).retrieveChangeConditions(currentTerm, X_C_Contract_Change.CONTRACTSTATUS_Gekuendigt, changeDate);

			// 'retrieveChangeConditions' would have thrown a exception in case of missing change conditions
			Check.assume(changeConditions != null, "");

			final int pricingSystemId = getPricingSystemId(currentTerm, changeConditions);

			if (currentTerm.getC_OrderLine_Term_ID() > 0)
			{
				final I_C_OrderLine currentTermOl = currentTerm.getC_OrderLine_Term();
				final I_C_Order currentTermOrder = currentTermOl.getC_Order();

				// we create an order to document that a change is taking place
				final I_C_Order termChangeOrder = orderPA.copyOrder(currentTermOrder, false, trxName);
				termChangeOrder.setM_PricingSystem_ID(pricingSystemId);

				termChangeOrder.setDateOrdered(SystemTime.asDayTimestamp());
				termChangeOrder.setDatePromised(changeDate);
				termChangeOrder.setDocAction(IDocument.ACTION_Complete);

				InterfaceWrapperHelper.save(termChangeOrder);

				final BigDecimal difference = computePriceDifference(ctx, sps, pricingSystemId, changeDate, trxName);

				final I_C_OrderLine termChangeOL = createChargeOrderLine(termChangeOrder, changeConditions, difference);

				final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
				docActionBL.processEx(termChangeOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
				logger.info("Completed new order {}", termChangeOrder);

				currentTerm.setC_OrderLine_TermChange_ID(termChangeOL.getC_OrderLine_ID());

				deleteDeliveriesAdjustOrderLine(sps, currentTermOrder, currentTermOl, changeDate);
			}
			else
			{
				deleteDeliveriesAdjustOrderLine(sps, null, null, changeDate);
			}

			// update EndDate
			if (changeDate.before(currentTerm.getStartDate()))
			{
				currentTerm.setEndDate(currentTerm.getStartDate());
			}
			else
			{
				currentTerm.setEndDate(new Timestamp(changeDate.getTime()));
			}
			
			// update contract status
			currentTerm.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_Quit);
		}

		currentTerm.setIsCloseInvoiceCandidate(isCloseInvoiceCandidate); 
		
		if (currentTerm.getC_FlatrateTerm_Next_ID() > 0)
		{
			// the canceled term has already been extended, so we need to cancel the next term as well
			cancelContract(currentTerm.getC_FlatrateTerm_Next(), changeDate, isCloseInvoiceCandidate);
		}
		else
		{
			// make sure that the canceled term won't be extended by the system
			currentTerm.setIsAutoRenew(false);
			currentTerm.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_Quit);
		}

		InterfaceWrapperHelper.save(currentTerm);
		
 		Services.get(IInvoiceCandidateHandlerBL.class).invalidateCandidatesFor(currentTerm);
	}

	private void deleteDeliveriesAdjustOrderLine(
			final List<I_C_SubscriptionProgress> sps,
			final I_C_Order oldOrder,
			final I_C_OrderLine oldOl,
			final Timestamp changeDate)
	{
		final IOrderPA orderPA = Services.get(IOrderPA.class);

		BigDecimal surplusQty = BigDecimal.ZERO;
		for (final I_C_SubscriptionProgress currentSP : sps)
		{
			if (!changeDate.after(currentSP.getEventDate()))
			{

				final String evtType = currentSP.getEventType();
				final String status = currentSP.getStatus();

				if (X_C_SubscriptionProgress.EVENTTYPE_Delivery.equals(evtType)
						&& (X_C_SubscriptionProgress.STATUS_Planned.equals(status) || X_C_SubscriptionProgress.STATUS_Open.equals(status)))
				{
					surplusQty = surplusQty.add(currentSP.getQty());
					InterfaceWrapperHelper.delete(currentSP);
				}
				else if (X_C_SubscriptionProgress.EVENTTYPE_BeginOfPause.equals(evtType) || X_C_SubscriptionProgress.EVENTTYPE_EndOfPause.equals(evtType))
				{
					InterfaceWrapperHelper.delete(currentSP);
				}
			}
		}

		if (oldOl != null && surplusQty.signum() != 0)
		{
			logger.info("Adjusting QtyOrdered of order " + oldOrder.getDocumentNo() + ", line " + oldOl.getLine());
			oldOl.setQtyOrdered(oldOl.getQtyOrdered().subtract(surplusQty));
			orderPA.reserveStock(oldOrder, oldOl);
		}
	}

	private I_C_OrderLine createChargeOrderLine(final I_C_Order newOrder, final I_C_Contract_Change changeConditions, final BigDecimal additionalCharge)
	{
		final MOrderLine chargeOlPO = new MOrderLine((MOrder)InterfaceWrapperHelper.getPO(newOrder));
		final de.metas.interfaces.I_C_OrderLine chargeOl = InterfaceWrapperHelper.create(chargeOlPO, de.metas.interfaces.I_C_OrderLine.class);

		chargeOlPO.setM_Product_ID(changeConditions.getM_Product_ID());
		chargeOlPO.setQtyOrdered(BigDecimal.ONE);
		chargeOlPO.setQtyEntered(BigDecimal.ONE);

		chargeOlPO.setPrice();

		chargeOl.setIsManualPrice(true);
		chargeOlPO.setPriceActual(additionalCharge.add(chargeOlPO.getPriceActual()));
		chargeOlPO.setPriceEntered(additionalCharge.add(chargeOlPO.getPriceEntered()));

		InterfaceWrapperHelper.save(chargeOl);

		logger.debug("created new order line " + chargeOlPO);
		return chargeOl;
	}

	private BigDecimal computePriceDifference(
			final Properties ctx,
			final List<I_C_SubscriptionProgress> sps,
			final int pricingSystemId,
			final Timestamp changeDate,
			final String trxName)
	{
		final List<I_C_SubscriptionProgress> deliveries = new ArrayList<>();
		for (final I_C_SubscriptionProgress currentSP : sps)
		{
			if (changeDate.after(currentSP.getEventDate())
					&& X_C_SubscriptionProgress.EVENTTYPE_Delivery.equals(currentSP.getEventType()))
			{
				deliveries.add(currentSP);
			}
		}

		if (deliveries.isEmpty())
		{
			return BigDecimal.ZERO;
		}

		final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);

		// compute the difference (see javaDoc of computePriceDifference for details)
		final BigDecimal difference = subscriptionBL.computePriceDifference(ctx, pricingSystemId, deliveries, trxName);
		logger.debug("The price difference to be applied on deliveries before the change is " + difference);
		return difference;
	}

	private int getPricingSystemId(final I_C_Flatrate_Term currentTerm, final I_C_Contract_Change changeConditions)
	{
		final int pricingSystemId;
		if (changeConditions.getM_PricingSystem_ID() > 0)
		{
			pricingSystemId = changeConditions.getM_PricingSystem_ID();
		}
		else
		{
			pricingSystemId = currentTerm.getC_Flatrate_Conditions().getM_PricingSystem_ID();
		}
		return pricingSystemId;
	}
	
	@Override
	public void endContract(I_C_Flatrate_Term currentTerm)
	{
		Check.assumeNotNull(currentTerm, "Param 'currentTerm' not null");
		currentTerm.setIsAutoRenew(false);
		currentTerm.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_EndingContract);
		InterfaceWrapperHelper.save(currentTerm);
	}
}
