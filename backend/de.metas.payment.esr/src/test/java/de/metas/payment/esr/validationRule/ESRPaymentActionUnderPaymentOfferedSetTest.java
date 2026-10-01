package de.metas.payment.esr.validationRule;

import de.metas.payment.esr.ESRValidationRuleTools;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;
import org.adempiere.ad.validationRule.impl.PlainValidationContext;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Payment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the COMPLETE set of payment actions {@link ESRPaymentActionValidationRule} offers for an
 * under-payment line: a line that carries its own payment against an invoice whose
 * {@code ESR_Invoice_Openamt} is POSITIVE, with an action the import already set.
 * <p>
 * Enumerates every action code rather than probing a few, so the answer to "what may the accountant
 * choose here" is exhaustive rather than a sample.
 */
public class ESRPaymentActionUnderPaymentOfferedSetTest
{
	@BeforeAll
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	protected POJOLookupMap db = POJOLookupMap.get();
	protected PlainValidationContext plainValidationCtx = new PlainValidationContext();

	/**
	 * Every action code the reference list defines, read off the generated model class rather than
	 * listed by hand: a hand-written list silently stops being exhaustive the moment an action is
	 * added to {@code ESR_Payment_Action}, which is exactly what this class claims not to be.
	 */
	private static final List<String> ALL_ACTIONS = allPaymentActionCodes();

	private static List<String> allPaymentActionCodes()
	{
		final List<String> codes = new ArrayList<>();
		for (final Field field : X_ESR_ImportLine.class.getDeclaredFields())
		{
			// ESR_PAYMENT_ACTION_AD_Reference_ID is the reference's own ID, not an action code.
			if (field.getName().startsWith("ESR_PAYMENT_ACTION_")
					&& field.getType() == String.class)
			{
				try
				{
					codes.add((String)field.get(null));
				}
				catch (final IllegalAccessException e)
				{
					throw new IllegalStateException("Cannot read " + field.getName(), e);
				}
			}
		}
		assertThat(codes).as("action codes found on X_ESR_ImportLine").isNotEmpty();
		return codes;
	}

	private List<String> offeredFor(final String openAmt, final String currentAction)
	{
		final I_C_Payment payment = db.newInstance(I_C_Payment.class);
		payment.setPayAmt(new BigDecimal("34841.90"));
		db.save(payment);

		final I_ESR_ImportLine line = db.newInstance(I_ESR_ImportLine.class);
		line.setC_Payment_ID(payment.getC_Payment_ID());
		line.setESR_Invoice_Openamt(new BigDecimal(openAmt));
		line.setESR_Payment_Action(currentAction);
		db.save(line);

		plainValidationCtx.setValue(I_ESR_ImportLine.COLUMNNAME_ESR_Invoice_Openamt, openAmt);
		plainValidationCtx.setValue(I_ESR_ImportLine.COLUMNNAME_C_Payment_ID, Integer.toString(payment.getC_Payment_ID()));
		plainValidationCtx.setValue(I_ESR_ImportLine.COLUMNNAME_C_Invoice_ID, "1080178");
		plainValidationCtx.setValue(I_ESR_ImportLine.COLUMNNAME_ESR_ImportLine_ID, Integer.toString(line.getESR_ImportLine_ID()));

		final List<String> offered = new ArrayList<>();
		for (final String action : ALL_ACTIONS)
		{
			if (ESRValidationRuleTools.evaluatePaymentAction(action, plainValidationCtx))
			{
				offered.add(action);
			}
		}
		return offered;
	}

	/**
	 * Positive open amount, an invoice on the line, the import already set "keep for dunning":
	 * exactly the three under-payment actions, and nothing else.
	 */
	@Test
	public void underPayment_offersExactlyTheThreeUnderPaymentActions()
	{
		assertThat(offeredFor("35000.00", X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning))
				.as("every action offered on an under-payment line")
				.containsExactlyInAnyOrder(
						X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount,
						X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning,
						X_ESR_ImportLine.ESR_PAYMENT_ACTION_Discount);
	}

	/**
	 * The three the accountant might reach for are NOT among them: parking the income, refunding it,
	 * and offsetting it against the next invoice are all gated on an OVER-payment.
	 */
	@Test
	public void underPayment_doesNotOfferTheOverpaymentActions()
	{
		assertThat(offeredFor("35000.00", X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning))
				.as("over-payment actions on an under-payment line")
				.doesNotContain(
						X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income,
						X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner,
						X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice);
	}

	/**
	 * Reversal is not a way out either: it is only ever offered back when it is already the line's
	 * own action, so an under-payment line cannot be switched to it.
	 */
	@Test
	public void underPayment_doesNotOfferReversal()
	{
		assertThat(offeredFor("35000.00", X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning))
				.as("reversal on an under-payment line whose action is something else")
				.doesNotContain(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Reverse_Booking);
	}
}
