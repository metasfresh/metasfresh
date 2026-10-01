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

import com.google.common.collect.ImmutableSet;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;
import de.metas.util.StringUtils;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.AbstractJavaValidationRule;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.compiere.util.NamePair;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ad
 *
 */
public class ESRPaymentActionValidationRule extends AbstractJavaValidationRule
{
	/**
	 * Actions for a line whose payment has nothing left to settle against its invoice: the money is
	 * sitting on the partner and the accountant has to say where it goes.
	 */
	private static final ImmutableSet<String> NO_ACTION_GROUP = ImmutableSet.of(
			X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income,
			X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner,
			X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice);

	private static final ImmutableSet<String> PARAMETERS = ImmutableSet.of(
			I_ESR_ImportLine.COLUMNNAME_ESR_Invoice_Openamt,
			I_ESR_ImportLine.COLUMNNAME_C_Payment_ID,
			I_ESR_ImportLine.COLUMNNAME_C_Invoice_ID,
			// read below to recognise a line the import itself flagged as a duplicate; it was already
			// read by the system-set-action branch without ever being declared here.
			I_ESR_ImportLine.COLUMNNAME_ESR_ImportLine_ID);

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

		if (paymentId <= 0 && !X_ESR_ImportLine.ESR_PAYMENT_ACTION_Reverse_Booking.equals(item.getID()))
		{
			// No payment. No rule valid.
			return false;
		}

		// 04690 these two actions are set by the system and imply that the record is readonly anyways.
		if (X_ESR_ImportLine.ESR_PAYMENT_ACTION_Control_Line.equals(item.getID())
				|| X_ESR_ImportLine.ESR_PAYMENT_ACTION_Reverse_Booking.equals(item.getID())
				|| X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts.equals(item.getID())
				|| X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice.equals(item.getID()))
		{
			final I_ESR_ImportLine importLine = getImportLineOrNull(evalCtx);
			if (importLine != null && item.getID().equals(importLine.getESR_Payment_Action()))
			{
				return true;
			}
		}

		// Actions for when we have Payment > Open amount
		final List<String> overPaymentGroup = new ArrayList<String>();
		overPaymentGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice);
		overPaymentGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner);
		overPaymentGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income); // metas-tsa: added per Mark request

		// Actions for when there is a payment but no invoice to settle it against.
		// Such a line is money sitting on the partner, so the accountant faces the same three-way
		// decision as on an overpayment: park it, offset it against the next invoice, or refund it.
		// The overPaymentGroup above cannot serve that case, because it is gated on a NEGATIVE
		// ESR_Invoice_Openamt and a line without an invoice keeps the column at zero -- only
		// ESRImportBL.updateOpenAmtAndStatusDontSave writes it, and that runs per invoice group.
		// Every handler behind these actions supports a line without an invoice: the refund one
		// branches on it explicitly ("there is no invoice, so we transfer back all the money"),
		// and the next-invoice one only needs the payment to set IsAutoAllocateAvailableAmt.
		// A duplicate payment is the same situation WITH an invoice attached, which is why the group
		// is NO_ACTION_GROUP above and the second way into it is isSettledInvoiceAwaitingDecision.

		// Actions for when we have Payment < Open amount
		final List<String> underPaymentGroup = new ArrayList<String>();
		underPaymentGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount);
		underPaymentGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning);
		underPaymentGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Discount);
		// Offered here deliberately, reversing the earlier "only makes sense with overpayments".
		// An under-payment can be one the accountant must leave unallocated: the money is theirs to
		// return, and the refund runs through payment selection, which only picks up a payment that is
		// still open. The other three under-payment actions all settle the invoice one way or another,
		// so without this one the case cannot be recorded at all.
		underPaymentGroup.add(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);

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
		if (NO_ACTION_GROUP.contains(item.getID()))
		{
			// the line is read here rather than up front because it costs a record load, and accept()
			// runs once per action: only these three items can ever be accepted by it.
			acceptNoActionItem = invoiceId <= 0 || isSettledInvoiceAwaitingDecision(evalCtx, openAmt);
		}

		return (acceptOverpaymentItem || acceptUnderPaymentItem || acceptNoActionItem);
	}

	/**
	 * Whether this line's payment settles nothing on its invoice, leaving the accountant to say where
	 * the money goes. Requires an EXACTLY zero open amount plus one of two marks on the line:
	 * <ul>
	 * <li>the import flagged it {@code Duplicate_Payment} -- the invoice was already paid by an earlier
	 * payment, so this one settles nothing;
	 * <li>or she has already chosen one of these actions. That choice OVERWRITES the flag, because the
	 * flag and her choice are the same column; without this second way in, the menu would be one-shot
	 * and reopening the line before processing it would offer her nothing.
	 * </ul>
	 * The open amount alone cannot key this: a payment that settles its invoice exactly has a zero open
	 * amount too and carries neither mark, because there is nothing to decide there.
	 */
	private boolean isSettledInvoiceAwaitingDecision(final IValidationContext evalCtx, final BigDecimal openAmt)
	{
		if (openAmt.signum() != 0)
		{
			return false;
		}

		final I_ESR_ImportLine importLine = getImportLineOrNull(evalCtx);
		if (importLine == null)
		{
			return false;
		}

		final String currentAction = importLine.getESR_Payment_Action();
		return X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment.equals(currentAction)
				|| NO_ACTION_GROUP.contains(currentAction);
	}

	/**
	 * @return the line this validation context is evaluated for, or {@code null} if the context carries
	 *         no usable {@code ESR_ImportLine_ID}. The ID is a plain context value, so it can name a
	 *         record that is gone, and {@code InterfaceWrapperHelper.create} returns {@code null} then.
	 */
	@Nullable
	private I_ESR_ImportLine getImportLineOrNull(final IValidationContext evalCtx)
	{
		final int importLineId = StringUtils.toIntegerOrZero(evalCtx.get_ValueAsString(I_ESR_ImportLine.COLUMNNAME_ESR_ImportLine_ID));
		if (importLineId <= 0)
		{
			return null;
		}

		return InterfaceWrapperHelper.create(Env.getCtx(), importLineId, I_ESR_ImportLine.class, ITrx.TRXNAME_None);
	}

	@Override
	public Set<String> getParameters(@Nullable final String contextTableName)
	{
		return PARAMETERS;
	}

}
