package de.metas.payment.esr.validationRule;

import de.metas.payment.esr.ESRValidationRuleTools;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import org.adempiere.ad.validationRule.impl.PlainValidationContext;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import de.metas.payment.esr.model.X_ESR_ImportLine;
import org.compiere.model.I_C_Payment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static de.metas.payment.esr.model.X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice;
import static de.metas.payment.esr.model.X_ESR_ImportLine.ESR_PAYMENT_ACTION_Discount;
import static de.metas.payment.esr.model.X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning;
import static de.metas.payment.esr.model.X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner;
import static de.metas.payment.esr.model.X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income;
import static de.metas.payment.esr.model.X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins which payment actions {@link ESRPaymentActionValidationRule} offers for a line that carries its
 * own payment against an invoice that is ALREADY FULLY SETTLED, so {@code ESR_Invoice_Openamt} is
 * exactly ZERO.
 * <p>
 * That shape falls between the rule's groups: the overpayment group requires a NEGATIVE open amount,
 * the underpayment group a POSITIVE one, and the no-invoice group requires no invoice at all.
 */
public class ESRPaymentActionDuplicateLineDiagnosisTest
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

	final String importLineIdStr = I_ESR_ImportLine.COLUMNNAME_ESR_ImportLine_ID;

	private List<String> acceptedActionsFor(final String openAmt, final String invoiceId)
	{
		return acceptedActionsFor(openAmt, invoiceId, null);
	}

	/**
	 * @param currentAction the action the import itself already put on the line, or {@code null} for none.
	 */
	private List<String> acceptedActionsFor(final String openAmt, final String invoiceId, final String currentAction)
	{
		final I_C_Payment payment = db.newInstance(I_C_Payment.class);
		payment.setPayAmt(new BigDecimal("92.45"));
		db.save(payment);

		final I_ESR_ImportLine importLine = db.newInstance(I_ESR_ImportLine.class);
		importLine.setC_Payment_ID(payment.getC_Payment_ID());
		importLine.setESR_Invoice_Openamt(new BigDecimal(openAmt));
		importLine.setESR_Payment_Action(currentAction);
		db.save(importLine);

		plainValidationCtx.setValue(openAmtStr, openAmt);
		plainValidationCtx.setValue(paymentIdStr, Integer.toString(payment.getC_Payment_ID()));
		plainValidationCtx.setValue(invoiceIdStr, invoiceId);
		plainValidationCtx.setValue(importLineIdStr, Integer.toString(importLine.getESR_ImportLine_ID()));

		final String[] candidates = {
				ESR_PAYMENT_ACTION_Unable_To_Assign_Income,
				ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner,
				ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice,
				ESR_PAYMENT_ACTION_Write_Off_Amount,
				ESR_PAYMENT_ACTION_Keep_For_Dunning,
				ESR_PAYMENT_ACTION_Discount };

		final List<String> accepted = new ArrayList<>();
		for (final String action : candidates)
		{
			if (ESRValidationRuleTools.evaluatePaymentAction(action, plainValidationCtx))
			{
				accepted.add(action);
			}
		}
		return accepted;
	}

	/**
	 * A second payment for an invoice that is already settled: the line gets its own payment, the
	 * invoice stays on the line, and the invoice's open amount is zero.
	 */
	@Test
	public void flaggedDuplicate_settledInvoice_zeroOpenAmt_offersTheOverpaymentActions()
	{
		final List<String> accepted = acceptedActionsFor("0.00", "1000001",
				X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment);

		assertThat(accepted)
				.as("actions offered on a line the import flagged as a duplicate (own payment, invoice present, invoice already settled so open amount = 0)")
				.contains(ESR_PAYMENT_ACTION_Unable_To_Assign_Income,
						ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner,
						ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice);
	}

	/**
	 * The counter-case that forbids the blunt "open amount &lt;= 0" gate: a payment that settles its
	 * invoice EXACTLY also has a zero open amount, but there is nothing to decide there, so the
	 * overpayment actions must stay hidden.
	 */
	@Test
	public void exactlySettledInvoice_notFlagged_offersNoOverpaymentAction()
	{
		assertThat(acceptedActionsFor("0.00", "1000001", null))
				.as("actions offered when a payment settles its invoice exactly")
				.doesNotContain(ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner,
						ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice);
	}

	/** Control: same line, but the invoice is overpaid -- the classic overpayment shape. */
	@Test
	public void control_negativeOpenAmt_offersTheOverpaymentActions()
	{
		assertThat(acceptedActionsFor("-92.45", "1000001"))
				.as("actions offered when the invoice is overpaid (open amount negative)")
				.contains(ESR_PAYMENT_ACTION_Unable_To_Assign_Income,
						ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner,
						ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice);
	}

	/** Control: no invoice at all. */
	@Test
	public void control_noInvoice_offersTheOverpaymentActions()
	{
		assertThat(acceptedActionsFor("0.00", "-1"))
				.as("actions offered on a line without an invoice")
				.contains(ESR_PAYMENT_ACTION_Unable_To_Assign_Income,
						ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner,
						ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice);
	}
}
