/**
 * 
 */
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.AbstractJavaValidationRule;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.StringUtils;
import org.compiere.util.Env;
import org.compiere.util.NamePair;

import com.google.common.collect.ImmutableSet;

import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;

/**
 * @author ad
 * 
 */
public class ESRPaymentActionValidationRule extends AbstractJavaValidationRule
{
	private static final ImmutableSet<String> PARAMETERS = ImmutableSet.of(
			I_ESR_ImportLine.COLUMNNAME_ESR_Invoice_Openamt,
			I_ESR_ImportLine.COLUMNNAME_C_Payment_ID,
			I_ESR_ImportLine.COLUMNNAME_C_Invoice_ID);

	@Override
	public boolean accept(final IValidationContext evalCtx, final NamePair item)
	{
		//
		// If we are running without any validation context, allow all items
		if (evalCtx == IValidationContext.NULL
				|| evalCtx == IValidationContext.DISABLED)
		{
			return true;
		}

		final String openAmtStr = evalCtx.get_ValueAsString(I_ESR_ImportLine.COLUMNNAME_ESR_Invoice_Openamt);
		final String paymentIdStr = evalCtx.get_ValueAsString(I_ESR_ImportLine.COLUMNNAME_C_Payment_ID);
		final String invoiceIdStr = evalCtx.get_ValueAsString(I_ESR_ImportLine.COLUMNNAME_C_Invoice_ID);
		// final String esrDocumentStatus = evalCtx.get_ValueAsString(I_ESR_ImportLine.COLUMNNAME_ESR_Document_Status);

		if (null == item)
		{
			// Should never happen.
			return false;
		}

		final int paymentId = StringUtils.toIntegerOrZero(paymentIdStr);
		final int invoiceId = StringUtils.toIntegerOrZero(invoiceIdStr);
		final BigDecimal openAmt = StringUtils.toBigDecimalOrZero(openAmtStr);

		if (paymentId <= 0)
		{
			// No payment. No rule valid.
			return false;
		}

		// 04690 these two actions are set by the system and imply that the record is readonly anyways.
		if (X_ESR_ImportLine.ESR_PAYMENT_ACTION_Control_Line.equals(item.getID())
				|| X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts.equals(item.getID())
				|| X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice.equals(item.getID()))
		{
			final String importLineIdStr = evalCtx.get_ValueAsString(I_ESR_ImportLine.COLUMNNAME_ESR_ImportLine_ID);
			final int importLineId = StringUtils.toIntegerOrZero(importLineIdStr);
			if (importLineId > 0)
			{
				final I_ESR_ImportLine importLine = InterfaceWrapperHelper.create(Env.getCtx(), importLineId, I_ESR_ImportLine.class, ITrx.TRXNAME_None);
				if (item.getID().equals(importLine.getESR_Payment_Action()))
				{
					return true;
				}
			}
		}

		// Actions for when we have Payment > Open amount
		final List<String> overPaymentGroup = new ArrayList<String>();
		overPaymentGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice);
		overPaymentGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner);
		overPaymentGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income); // metas-tsa: added per Mark request

		final List<String> noActionGroup = new ArrayList<String>();
		// only show <code>X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income</code> action when c_invoice_id is empty
		if (invoiceId <= 0)
		{
			noActionGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);
		}

		// Actions for when we have Payment < Open amount
		final List<String> underPaymentGroup = new ArrayList<String>();
		underPaymentGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount);
		underPaymentGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning);
		// metas-ts: talked with mo: that action only makes sense with overpayments (just commenting out because there is no particular task for this change)
		// underPaymentGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);

		// Done like this so we can quickly add future actions to one (or both) groups.
		boolean acceptOverpaymentItem = false;
		boolean acceptUnderPaymentItem = false;
		boolean acceptNoActionItem = false;

		if (overPaymentGroup.contains(item.getID()))
		{
			acceptOverpaymentItem = openAmt.signum() < 0;
		}
		if (underPaymentGroup.contains(item.getID()))
		{
			acceptUnderPaymentItem = openAmt.signum() > 0;
		}
		if (noActionGroup.contains(item.getID()))
		{
			acceptNoActionItem = invoiceId <= 0;
		}

		return (acceptOverpaymentItem || acceptUnderPaymentItem || acceptNoActionItem);
	}

	@Override
	public Set<String> getParameters()
	{
		return PARAMETERS;
	}

}
