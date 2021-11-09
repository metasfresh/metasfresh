package de.metas.payment.esr;

import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.validationRule.ESRPaymentActionValidationRule;
import de.metas.util.Check;
import org.adempiere.ad.validationRule.impl.PlainValidationContext;
import org.compiere.util.NamePair;

import static org.assertj.core.api.Assertions.*;

public final class ESRValidationRuleTools
{
	private ESRValidationRuleTools()
	{
	}

	/**
	 * @param action Note that a {@code null} string is OK
	 * @return
	 */
	public static NamePair mkListItem(final String action)
	{
		final NamePair item = new NamePair(action, null/* description */)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getID()
			{
				return action;
			}
		};
		return item;
	}

	public static PlainValidationContext mkPlainValidationContext(
			final I_ESR_ImportLine importLine)
	{
		Check.assumeNotNull(importLine, "Param 'importLine' is not null");
		Check.assumeNotNull(importLine.getESR_Invoice_Openamt(), "ESR_Invoice_Openamt or {} is not null", importLine);

		final PlainValidationContext plainValidationCtx = new PlainValidationContext();

		plainValidationCtx.setValue(I_ESR_ImportLine.COLUMNNAME_ESR_Invoice_Openamt, importLine.getESR_Invoice_Openamt().toPlainString());
		plainValidationCtx.setValue(I_ESR_ImportLine.COLUMNNAME_C_Payment_ID, Integer.toString(importLine.getC_Payment_ID()));
		plainValidationCtx.setValue(I_ESR_ImportLine.COLUMNNAME_C_Invoice_ID, Integer.toString(importLine.getC_Invoice_ID()));

		return plainValidationCtx;
	}

	/**
	 * @param action             may not be {@code null}.
	 * @param plainValidationCtx
	 * @return
	 */
	public static boolean evaluatePaymentAction(
			final String action,
			final PlainValidationContext plainValidationCtx)
	{
		final NamePair item = mkListItem(action);
		boolean accepted = new ESRPaymentActionValidationRule().getPostQueryFilter().accept(plainValidationCtx, item);
		return accepted;
	}

	/**
	 * @param action     may not be {@code null}.
	 * @param importLine
	 * @return
	 */
	public static boolean evalPaymentActionOK(
			final String action,
			final I_ESR_ImportLine importLine)
	{
		boolean accepted = new ESRPaymentActionValidationRule()
				.getPostQueryFilter()
				.accept(
						mkPlainValidationContext(importLine),
						mkListItem(action));

		return accepted;
	}

	public static void assertAccepted(final String action, final PlainValidationContext plainValidationCtx)
	{
		final boolean accepted = ESRValidationRuleTools.evaluatePaymentAction(action, plainValidationCtx);
		assertThat(accepted)
				.as("action=" + action + ", validationCtx=" + plainValidationCtx)
				.isTrue();
	}

	public static void assertRejected(final String action, final PlainValidationContext plainValidationCtx)
	{
		final boolean rejected = !ESRValidationRuleTools.evaluatePaymentAction(action, plainValidationCtx);
		assertThat(rejected)
				.as("action=" + action + ", validationCtx=" + plainValidationCtx)
				.isTrue();
	}
}
