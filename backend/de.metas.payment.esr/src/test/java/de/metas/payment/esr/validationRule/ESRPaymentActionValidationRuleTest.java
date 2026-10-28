package de.metas.payment.esr.validationRule;

/*
 * #%L
 * de.metas.payment.esr
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

import de.metas.payment.esr.ESRValidationRuleTools;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import org.adempiere.ad.validationRule.impl.PlainValidationContext;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Payment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static de.metas.payment.esr.model.X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice;
import static de.metas.payment.esr.model.X_ESR_ImportLine.ESR_PAYMENT_ACTION_Control_Line;
import static de.metas.payment.esr.model.X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment;
import static de.metas.payment.esr.model.X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning;
import static de.metas.payment.esr.model.X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner;
import static de.metas.payment.esr.model.X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income;
import static de.metas.payment.esr.model.X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount;
import static org.assertj.core.api.Assertions.*;

public class ESRPaymentActionValidationRuleTest
{

	@BeforeAll
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	protected POJOLookupMap db = POJOLookupMap.get();

	protected PlainValidationContext plainValidationCtx = new PlainValidationContext();

	final String openAmtStr = I_ESR_ImportLine.COLUMNNAME_ESR_Invoice_Openamt;
	final String paymentIdStr = I_ESR_ImportLine.COLUMNNAME_C_Payment_ID;
	final String invoiceIdStr = I_ESR_ImportLine.COLUMNNAME_C_Invoice_ID;

	@Test
	public void ESRPaymentActionValidationRule_NoItem_test()
	{
		plainValidationCtx.setValue(openAmtStr, "100.00");
		plainValidationCtx.setValue(paymentIdStr, "1000000");
		plainValidationCtx.setValue(invoiceIdStr, "1000001");

		final boolean accepted = new ESRPaymentActionValidationRule().getPostQueryFilter().accept(plainValidationCtx, null);

		assertThat(accepted).as("accepted").isFalse();
	}

	@Test
	public void ESRPaymentActionValidationRule_NoPayment_test()
	{
		plainValidationCtx.setValue(openAmtStr, "100.00");
		plainValidationCtx.setValue(paymentIdStr, "-1");
		plainValidationCtx.setValue(invoiceIdStr, "1000001");

		final boolean accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Write_Off_Amount, plainValidationCtx);

		assertThat(accepted).as("accepted").isFalse();
	}

	@Test
	public void ESRPaymentActionValidationRule_NoInvoice_test()
	{
		final I_C_Payment payment = db.newInstance(I_C_Payment.class);
		payment.setPayAmt(new BigDecimal(110));
		db.save(payment);

		final Integer paymentID = payment.getC_Payment_ID();
		final String paymentIDStr = paymentID.toString();

		plainValidationCtx.setValue(openAmtStr, "100.00");
		plainValidationCtx.setValue(paymentIdStr, paymentIDStr);
		plainValidationCtx.setValue(invoiceIdStr, "-1");

		// A line that has a payment but no invoice is money sitting on the partner with nothing to
		// settle. The accountant faces the same three-way decision as on an overpayment, so all
		// three overpayment actions must be offered -- see the comment in noActionGroup.
		boolean accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice, plainValidationCtx);
		assertThat(accepted).as("accepted").isTrue();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, plainValidationCtx);
		assertThat(accepted).as("accepted").isTrue();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Unable_To_Assign_Income, plainValidationCtx);
		assertThat(accepted).as("accepted").isTrue();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Control_Line, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();
	}

	/**
	 * The production shape of a line without an invoice: {@code ESR_Invoice_Openamt} is ZERO, because
	 * {@code ESRImportBL.updateOpenAmtAndStatusDontSave} only ever runs per invoice group and therefore
	 * never touches such a line. With a zero open amount neither the over- nor the underpayment group
	 * can fire, so the "no invoice" branch is the ONLY thing that can offer an action here.
	 * <p>
	 * Before the fix this left {@code Unable_To_Assign_Income} as the single choice, which cannot record
	 * a refund -- even though {@code MoneyTransferedBackESRActionHandler} explicitly implements the
	 * no-invoice case ("there is no invoice, so we transfer back all the money").
	 */
	@Test
	public void esrPaymentActionValidationRule_noInvoice_zeroOpenAmt_offersAllOverpaymentActions()
	{
		final I_C_Payment payment = db.newInstance(I_C_Payment.class);
		payment.setPayAmt(new BigDecimal("110"));
		db.save(payment);

		plainValidationCtx.setValue(openAmtStr, "0.00");
		plainValidationCtx.setValue(paymentIdStr, Integer.toString(payment.getC_Payment_ID()));
		plainValidationCtx.setValue(invoiceIdStr, "-1");

		ESRValidationRuleTools.assertAccepted(ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, plainValidationCtx);
		ESRValidationRuleTools.assertAccepted(ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice, plainValidationCtx);
		ESRValidationRuleTools.assertAccepted(ESR_PAYMENT_ACTION_Unable_To_Assign_Income, plainValidationCtx);

		// unchanged: an underpayment action makes no sense without an invoice, and the import sets
		// Duplicate_Payment itself -- it must never become manually selectable.
		ESRValidationRuleTools.assertRejected(ESR_PAYMENT_ACTION_Write_Off_Amount, plainValidationCtx);
		ESRValidationRuleTools.assertRejected(ESR_PAYMENT_ACTION_Keep_For_Dunning, plainValidationCtx);
		ESRValidationRuleTools.assertRejected(ESR_PAYMENT_ACTION_Duplicate_Payment, plainValidationCtx);
		ESRValidationRuleTools.assertRejected(ESR_PAYMENT_ACTION_Control_Line, plainValidationCtx);
	}

	/**
	 * A line with NO payment must stay locked down even without an invoice: nothing can be done with
	 * money that was never booked.
	 */
	@Test
	public void esrPaymentActionValidationRule_noInvoice_noPayment_offersNothing()
	{
		plainValidationCtx.setValue(openAmtStr, "0.00");
		plainValidationCtx.setValue(paymentIdStr, "-1");
		plainValidationCtx.setValue(invoiceIdStr, "-1");

		ESRValidationRuleTools.assertRejected(ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, plainValidationCtx);
		ESRValidationRuleTools.assertRejected(ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice, plainValidationCtx);
		ESRValidationRuleTools.assertRejected(ESR_PAYMENT_ACTION_Unable_To_Assign_Income, plainValidationCtx);
	}

	@Test
	public void ESRPaymentActionValidationRule_OverPayment_test()
	{
		final I_C_Payment payment = db.newInstance(I_C_Payment.class);
		payment.setPayAmt(new BigDecimal(110));
		db.save(payment);

		final Integer paymentID = payment.getC_Payment_ID();
		final String paymentIDStr = paymentID.toString();

		plainValidationCtx.setValue(openAmtStr, "-20.00");
		plainValidationCtx.setValue(paymentIdStr, paymentIDStr);
		plainValidationCtx.setValue(invoiceIdStr, "1000001");

		boolean accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice, plainValidationCtx);
		assertThat(accepted).as("accepted").isTrue();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, plainValidationCtx);
		assertThat(accepted).as("accepted").isTrue();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Unable_To_Assign_Income, plainValidationCtx);
		assertThat(accepted).as("accepted").isTrue();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Control_Line, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();
	}

	@Test
	public void ESRPaymentActionValidationRule_EqualAmts_test()
	{
		final I_C_Payment payment = db.newInstance(I_C_Payment.class);
		payment.setPayAmt(new BigDecimal(100));
		db.save(payment);

		final Integer paymentID = payment.getC_Payment_ID();
		final String paymentIDStr = paymentID.toString();

		plainValidationCtx.setValue(openAmtStr, "0.00");
		plainValidationCtx.setValue(paymentIdStr, paymentIDStr);
		plainValidationCtx.setValue(invoiceIdStr, "1000001");

		boolean accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Write_Off_Amount, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Keep_For_Dunning, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Unable_To_Assign_Income, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Control_Line, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();
	}

	@Test
	public void ESRPaymentActionValidationRule_UnderPayment_test()
	{
		final I_C_Payment payment = db.newInstance(I_C_Payment.class);
		payment.setPayAmt(new BigDecimal(90));
		db.save(payment);

		final Integer paymentID = payment.getC_Payment_ID();
		final String paymentIDStr = paymentID.toString();

		plainValidationCtx.setValue(openAmtStr, "110.00");
		plainValidationCtx.setValue(paymentIdStr, paymentIDStr);
		plainValidationCtx.setValue(invoiceIdStr, "1000001");

		boolean accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Write_Off_Amount, plainValidationCtx);
		assertThat(accepted).as("accepted").isTrue();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Keep_For_Dunning, plainValidationCtx);
		assertThat(accepted).as("accepted").isTrue();

		// metas-ts: talked with mo: that action only makes sense with overpayments (just commenting out because there is no particular task for this change)
		// accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Unable_To_Assign_Income, plainValidationCtx);
		// assertThat(accepted).as("accepted").isTrue();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Control_Line, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();
	}

	@Test
	public void ESRPaymentActionValidationRule_OverPayment_WrongActions_test()
	{
		final I_C_Payment payment = db.newInstance(I_C_Payment.class);
		payment.setPayAmt(new BigDecimal(110));
		db.save(payment);

		final Integer paymentID = payment.getC_Payment_ID();
		final String paymentIDStr = paymentID.toString();

		plainValidationCtx.setValue(openAmtStr, "-20.00");
		plainValidationCtx.setValue(paymentIdStr, paymentIDStr);
		plainValidationCtx.setValue(invoiceIdStr, "1000001");

		boolean accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Write_Off_Amount, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Keep_For_Dunning, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Unable_To_Assign_Income, plainValidationCtx);
		assertThat(accepted).as("accepted").isTrue();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Control_Line, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();
	}

	@Test
	public void ESRPaymentActionValidationRule_UnderPayment_WrongActions_test()
	{
		final I_C_Payment payment = db.newInstance(I_C_Payment.class);
		payment.setPayAmt(new BigDecimal(90));
		db.save(payment);

		final Integer paymentID = payment.getC_Payment_ID();
		final String paymentIDStr = paymentID.toString();

		plainValidationCtx.setValue(openAmtStr, "110.00");
		plainValidationCtx.setValue(paymentIdStr, paymentIDStr);
		plainValidationCtx.setValue(invoiceIdStr, "1000001");

		boolean accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice, plainValidationCtx);

		assertThat(accepted).as("accepted").isFalse();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();

		// metas-ts: talked with mo: that action only makes sense with overpayments (just commenting out because there is no particular task for this change)
		// accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Unable_To_Assign_Income, plainValidationCtx);
		// assertThat(accepted).as("accepted").isTrue();

		accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Control_Line, plainValidationCtx);
		assertThat(accepted).as("accepted").isFalse();
	}

	/**
	 * The payment's payAmt is equal to the open amount. We expect the underpayment actions to be valid, because there is still an open amount
	 */
	@Test
	public void esrPaymentActionValidationRule_partial_payment1()
	{
		final I_C_Payment payment = db.newInstance(I_C_Payment.class);
		payment.setPayAmt(new BigDecimal("35"));
		db.save(payment);

		final Integer paymentID = payment.getC_Payment_ID();
		final String paymentIDStr = paymentID.toString();

		plainValidationCtx.setValue(openAmtStr, "35.00");
		plainValidationCtx.setValue(paymentIdStr, paymentIDStr);
		plainValidationCtx.setValue(invoiceIdStr, "1000001");

		ESRValidationRuleTools.assertAccepted(ESR_PAYMENT_ACTION_Keep_For_Dunning, plainValidationCtx);
		ESRValidationRuleTools.assertAccepted(ESR_PAYMENT_ACTION_Write_Off_Amount, plainValidationCtx);

		ESRValidationRuleTools.assertRejected(ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice, plainValidationCtx);
		ESRValidationRuleTools.assertRejected(ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, plainValidationCtx);
		ESRValidationRuleTools.assertRejected(ESR_PAYMENT_ACTION_Unable_To_Assign_Income, plainValidationCtx);
	}

	/**
	 * The payment's payAmt greater than the open amount. Still we expect the underpayment actions to be valid, because there is still an open amount.
	 */
	@Test
	public void esrPaymentActionValidationRule_partial_payment2()
	{
		final I_C_Payment payment = db.newInstance(I_C_Payment.class);
		payment.setPayAmt(new BigDecimal("35.00"));
		db.save(payment);

		final Integer paymentID = payment.getC_Payment_ID();
		final String paymentIDStr = paymentID.toString();

		plainValidationCtx.setValue(openAmtStr, "25.00");
		plainValidationCtx.setValue(paymentIdStr, paymentIDStr);
		plainValidationCtx.setValue(invoiceIdStr, "1000001");

		ESRValidationRuleTools.assertAccepted(ESR_PAYMENT_ACTION_Keep_For_Dunning, plainValidationCtx);
		ESRValidationRuleTools.assertAccepted(ESR_PAYMENT_ACTION_Write_Off_Amount, plainValidationCtx);

		ESRValidationRuleTools.assertRejected(ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice, plainValidationCtx);
		ESRValidationRuleTools.assertRejected(ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, plainValidationCtx);
		ESRValidationRuleTools.assertRejected(ESR_PAYMENT_ACTION_Unable_To_Assign_Income, plainValidationCtx);
	}

	@Test
	public void ESRPaymentActionValidationRule_DuplicatePayment_test()
	{
		final I_C_Payment payment = db.newInstance(I_C_Payment.class);
		payment.setPayAmt(new BigDecimal(110));
		db.save(payment);

		final Integer paymentID = payment.getC_Payment_ID();
		final String paymentIDStr = paymentID.toString();

		plainValidationCtx.setValue(openAmtStr, "-20.00");
		plainValidationCtx.setValue(paymentIdStr, paymentIDStr);
		plainValidationCtx.setValue(invoiceIdStr, "1000001");

		boolean accepted = ESRValidationRuleTools.evaluatePaymentAction(ESR_PAYMENT_ACTION_Duplicate_Payment, plainValidationCtx);

		// Duplicate_Payment should never be available to set it manually
		assertThat(accepted).as("accepted").isFalse();

	}
}
