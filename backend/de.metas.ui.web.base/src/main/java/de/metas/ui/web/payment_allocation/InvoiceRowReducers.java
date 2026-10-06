/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.payment_allocation;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.ui.web.payment_allocation.InvoiceRow.InvoiceRowBuilder;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceRowReducers
{
	public static InvoiceRow reduce(
			@NonNull final InvoiceRow row,
			@NonNull final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final InvoiceRowBuilder rowBuilder = row.toBuilder();

		for (final JSONDocumentChangedEvent fieldChangeRequest : fieldChangeRequests)
		{
			fieldChangeRequest.assertReplaceOperation();
			final String fieldName = fieldChangeRequest.getPath();
			if (InvoiceRow.FIELD_DiscountAmt.contentEquals(fieldName))
			{
				final BigDecimal discountAmtBD = fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO);

				final CurrencyCode currencyCode = row.getDiscountAmt().getCurrencyCode();
				final Amount discountAmt = Amount.of(discountAmtBD, currencyCode);
				rowBuilder.discountAmt(discountAmt);
			}
			else if (InvoiceRow.FIELD_ServiceFeeAmt.contentEquals(fieldName))
			{
				final BigDecimal serviceFeeAmtBD = fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO);

				final CurrencyCode currencyCode = row.getCurrencyCode();
				final Amount serviceFeeAmt = Amount.of(serviceFeeAmtBD, currencyCode);
				rowBuilder.serviceFeeAmt(serviceFeeAmt);
			}
			else if (InvoiceRow.FIELD_PaymentBonusAmt.contentEquals(fieldName))
			{
				final BigDecimal paymentBonusAmtBD = fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO);

				final CurrencyCode currencyCode = row.getCurrencyCode();
				final Amount paymentBonusAmt = Amount.of(paymentBonusAmtBD, currencyCode);
				rowBuilder.paymentBonusAmt(paymentBonusAmt);
			}
			else if (InvoiceRow.FIELD_BankFeeAmt.contentEquals(fieldName))
			{
				final BigDecimal bankFeeAmtBD = fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO);

				final CurrencyCode currencyCode = row.getCurrencyCode();
				final Amount bankFeeAmt = Amount.of(bankFeeAmtBD, currencyCode);
				rowBuilder.bankFeeAmt(bankFeeAmt);
			}
			else
			{
				throw new AdempiereException("Changing " + fieldName + " is not allowed");
			}
		}

		return updatePaymentBonus(rowBuilder.build());
	}

	/**
	 * The payment bonus is checked against what the customer pays, which depends on the discount and the fees too; so it is checked after any change of the row.
	 * Shows the amount that will be booked, if the entered one cannot be because of the rounding of the VAT, and the note why the bonus is not the computed one.
	 */
	private static InvoiceRow updatePaymentBonus(@NonNull final InvoiceRow row)
	{
		final Amount paymentBonusAmt = row.getPaymentBonusAmt();
		if (paymentBonusAmt == null)
		{
			return row;
		}

		final PaymentBonusRowValues paymentBonusRowValues = PaymentBonusRowValues.entered(row.getPaymentBonusDeduction(), paymentBonusAmt, row.getMaxPaymentBonusAmt(), row.getPaymentBonusNote());
		return row.toBuilder()
				.paymentBonusAmt(paymentBonusRowValues.getPaymentBonusAmt())
				.paymentBonusNote(paymentBonusRowValues.getPaymentBonusNote())
				.build();
	}
}
