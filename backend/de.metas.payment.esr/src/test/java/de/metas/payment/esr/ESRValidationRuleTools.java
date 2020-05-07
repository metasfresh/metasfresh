package de.metas.payment.esr;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import static org.junit.Assert.assertTrue;

import org.adempiere.ad.validationRule.impl.PlainValidationContext;
import org.adempiere.util.Check;
import org.compiere.util.NamePair;

import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.validationRule.ESRPaymentActionValidationRule;

public final class ESRValidationRuleTools
{
	private ESRValidationRuleTools()
	{
	}

	/**
	 * 
	 * 
	 * @param action Note that a {@code null} string is OK
	 * @return
	 */
	public static NamePair mkListItem(final String action)
	{
		final NamePair item = new NamePair(action)
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
	 * 
	 * @param action may not be {@code null}.
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
	 * 
	 * @param action may not be {@code null}.
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
		assertTrue("Action " + action + " with validationCtx " + plainValidationCtx + "was not accepted", accepted);
	}

	public static void assertRejected(final String action, final PlainValidationContext plainValidationCtx)
	{
		final boolean rejected = !ESRValidationRuleTools.evaluatePaymentAction(action, plainValidationCtx);
		assertTrue("Action " + action + " with validationCtx " + plainValidationCtx + "was not rejected", rejected);
	}
}
