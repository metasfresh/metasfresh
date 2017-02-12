package test.integration.banking;

/*
 * #%L
 * de.metas.banking.ait
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


import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MBankStatement;
import org.compiere.model.MPayment;
import org.compiere.model.X_C_Payment;

import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.event.TestEvent;
import de.metas.adempiere.ait.test.annotation.ITEventListener;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_Payment;
import junit.framework.Assert;

public class PaymentTestListener
{

	@ITEventListener(
			driver = PaymentTestDriver.class,
			tasks = "025b",
			eventTypes = EventType.PAYMENT_CASH_COMPLETE_AFTER)
	public void onCashPaymentCreated(final TestEvent evt)
	{
		final MPayment payment = (MPayment)InterfaceWrapperHelper.getPO(evt.getObj());
		assertPaymentAndBSLConsistent(payment);
	}

	@ITEventListener(
			driver = PaymentTestDriver.class,
			tasks = "025b",
			eventTypes = EventType.BANKSTATEMENT_COMPLETE_AFTER,
			desc = "a payment can't be voided anymore, when it is referenced in a bank statement line whose paren has already been completed")
	public void onBankStatementCompleted(final TestEvent evt)
	{
		final MBankStatement bsPO = (MBankStatement)InterfaceWrapperHelper.getPO(evt.getObj());

		final MPayment payment = (MPayment)bsPO.getLines(true)[0].getC_Payment();

		// attempt to void payment
		assertFalse("We should not be able to void " + payment, payment.voidIt());
		assertEquals("We should not have a reversal for " + payment, 0, payment.getReversal_ID());
		assertThat(payment + " has wrong DocStatus", payment.getDocStatus(), equalTo(X_C_Payment.DOCSTATUS_Completed));

		assertPaymentAndBSLConsistent(payment);
	}

	@ITEventListener(
			driver = PaymentTestDriver.class,
			tasks = "025b",
			eventTypes = EventType.PAYMENT_CASH_COMPLETED_VOID_AFTER,
			desc = "When a completed cash payment is voided, then a reversal cash payment is ceated, together with respective bank statement lines")
	public void onPaymentVoided(final TestEvent evt)
	{
		final MPayment payment = (MPayment)InterfaceWrapperHelper.getPO(evt.getObj());

		payment.load(evt.getSource().getTrxName());

		Assert.assertEquals("Expected reversed " + payment + " not to reference an onvoice", 0, payment.getC_Invoice_ID());

		final MPayment reversalPayment = InterfaceWrapperHelper.getPO(payment.getReversal());
		final List<I_C_BankStatementLine> revbsls = BankStatementHelper.fetchBankStatementLinesForPayment(InterfaceWrapperHelper.create(reversalPayment, I_C_Payment.class), I_C_BankStatementLine.class);
		Assert.assertEquals("We should have one BSL for " + reversalPayment, 1, revbsls.size());
		final I_C_BankStatementLine revbsl = revbsls.get(0);

		assertPaymentAndBSLConsistent(reversalPayment);

		// verify that the amounts of 'revbls' are the ampounts of 'payment' times -1
		Assert.assertEquals(payment.getPayAmt().negate(), revbsl.getTrxAmt());
		Assert.assertEquals(payment.getDiscountAmt().negate(), revbsl.getDiscountAmt());
		Assert.assertEquals(payment.getWriteOffAmt().negate(), revbsl.getWriteOffAmt());
		Assert.assertEquals(payment.getOverUnderAmt().negate(), revbsl.getOverUnderAmt());
	}

	private void assertPaymentAndBSLConsistent(final MPayment payment)
	{
		final List<I_C_BankStatementLine> bsls = BankStatementHelper.fetchBankStatementLinesForPayment(InterfaceWrapperHelper.create(payment, I_C_Payment.class), I_C_BankStatementLine.class);
		assertEquals("We should have one BSL for " + payment, 1, bsls.size());

		final I_C_BankStatementLine bsl = bsls.get(0);
		assertEquals(bsl.getC_Payment_ID(), payment.getC_Payment_ID());
		assertEquals(bsl.getC_Invoice_ID(), payment.getC_Invoice_ID());
		assertEquals(bsl.getTrxAmt(), payment.getPayAmt());

		assertEquals(bsl.isOverUnderPayment(), payment.isOverUnderPayment());
		assertTrue(bsl.isProcessed());
		assertEquals(bsl.getDiscountAmt(), payment.getDiscountAmt());
		assertEquals(bsl.getWriteOffAmt(), payment.getWriteOffAmt());
		assertEquals(bsl.getOverUnderAmt(), payment.getOverUnderAmt());

		if (payment.getC_Invoice_ID() > 0)
		{
			assertEquals("Invoice grand total should be same with the sum of all other amounts",
					payment.getC_Invoice().getGrandTotal(), bsl.getTrxAmt()
							.add(payment.getOverUnderAmt())
							.add(bsl.getDiscountAmt())
							.add(bsl.getWriteOffAmt()));
		}
	}
}
