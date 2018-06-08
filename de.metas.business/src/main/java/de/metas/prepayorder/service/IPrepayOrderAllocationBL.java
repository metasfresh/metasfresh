package de.metas.prepayorder.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MPayment;

/**
 * This service is called from {@link MOrder}, {@link MPayment}, {@link MInvoice} and {@link MAllocationLine}. It replaces the former jboss-aop aspect
 * <code>de.metas.prepayorder.aop.PaymentAllocateAsp</code>.
 * 
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/07286_get_rid_of_jboss-aop_for_good_%28104432455599%29
 */
public interface IPrepayOrderAllocationBL extends ISingletonService
{

	/**
	 * After {@link MPayment#allocateIt()}, this advice does one of the following:
	 * <ul>
	 * <li>If the payment header (C_Payment) references an order, it creates a C_AllocationHdr and a C_AllocationLine for that order</li>
	 * <li>For each payment allocate (C_PaymentAllocate) that references an order, the allocate's C_AllocationLine (which has been created by allocateIt()) is loaded and the payment allocate's
	 * C_Order_ID is set.</li>
	 * <li>If a C_Order_ID is set, it is asserted that the referenced order is a prepay order and that the payment is a receipt</li>
	 * </ul>
	 * 
	 * @param payment
	 * @param allocationLineWasCreated
	 * @return
	 * 
	 */
	void paymentAfterAllocateIt(I_C_Payment payment, boolean allocationLineWasCreated);

	/**
	 * Before {@link MOrder#completeIt()}, the advice does one of the following:
	 * 
	 * <ul>
	 * <li>If the order has docAction=DOCACTION_Prepare or a docType!=DOCSUBTYPE_PrepayOrder_metas, just the original method is called.</li>
	 * <li>Otherwise, it is checked if there are sufficient AllocationLines for this order. If not, the order is turned to STATUS_WaitingPayment. If there is a sufficient allocation, the original
	 * method is called</li>
	 * </ul>
	 * 
	 * @param order
	 * @return
	 */
	String orderBeforeCompleteIt(MOrder order);

	/**
	 * After {@link MInvoice#completeIt()}, if the invoice references a prepay order, then prepay order's allocation lines are also made to reference the invoice. Afterwards,
	 * {@link MInvoice#testAllocation()} is called to check if the invoice is paid now.
	 * 
	 * @param invoice
	 */
	void invoiceAfterCompleteIt(MInvoice invoice);

	/**
	 * Before {@link MInvoice#reverseCorrectIt()}, if the invoice references a prepay order, then for the prepay order's allocation lines, references to the invoice are deleted. Then, the original
	 * method (reverseCorrectIt) is called. Or with other words, we un-attach the invoice from the prepay order's allocation.
	 * 
	 * This has the effect, that the prepay order's allocation is not reversed inside <code>MInvoice.reverseCorrectIt()</code> (which is what we want).
	 * 
	 * @param invoice
	 * @return
	 */
	void invoiceBeforeReverseCorrectIt(MInvoice invoice);

	/**
	 * After {@link MAllocationLine#processIt(boolean)}, if the line references a prepay order and if the method was called with <code>reverse==true</code>, then the aspect does the following:
	 * <ul>
	 * <li>Checks if there are still sufficient allocation(s) for this order. If the order is not sufficiently paid for anymore, and no deliveries have been made yet, then the order is put back into
	 * status waiting payment.</li>
	 * </ul>
	 * Notes:
	 * <ul>
	 * <li>If parts of the order have already been delivered, the order is not changed. The missing payment will be dealt with via this order's the open invoice. The new PrepayCandidateProcessor (see
	 * below) makes sure that no further shipments are made by the shipment schedule, unless set explicitly there.</li>
	 * <li>If processIt was called with <code>reverse==false</code>, then the aspect calls order.</li>
	 * </ul>
	 * 
	 * @param allocLine
	 * @param reverse
	 * @return
	 * 
	 */
	void allocationLineAfterProcessIt(MAllocationLine allocLine, boolean reverse);

}
