/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.factoring.process;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * One detail row of the Factoring OP-Liste export.
 *
 * <p>Corresponds to one open invoice or credit note of a factoring customer
 * (C_BPartner.IsFactoring='Y') in the current org and the selected currency.
 *
 * <p>{@link #contractNo} and {@link #clientAccountId} come from the invoice's
 * bill-partner (the factoring customer), matching the domain rule that these
 * are per-customer values, not per-org.
 */
@Value
@Builder
public class FactoringOpListeDetailRow
{
	/** {@code C_BPartner.Value} of the factoring customer (max 20 chars in the CSV). */
	@NonNull String debitorNo;
	/** {@code C_BPartner.Name} of the factoring customer (max 50 chars in the CSV). */
	@NonNull String debitorName;
	/** {@code C_BPartner.FactoringContractNo} of the factoring customer. */
	@Nullable String contractNo;
	/** {@code C_BPartner.FactoringClientAccountId} of the factoring customer. */
	@Nullable String clientAccountId;
	/** {@code C_Invoice.DocumentNo}. */
	@NonNull String documentNo;
	@NonNull LocalDate dateInvoiced;
	@NonNull LocalDate dueDate;
	/** ISO3 code (e.g. {@code EUR}). */
	@NonNull String currencyIso;
	/** {@code C_Invoice.GrandTotal} — always the positive gross amount, direction encoded in {@link #debitCreditFlag}. */
	@NonNull BigDecimal grandTotal;
	/** {@code C_Invoice.OpenAmt} — positive; non-zero for a row to be included. */
	@NonNull BigDecimal openAmount;
	/** {@code C} for credit note (DocBaseType='ARC'), {@code D} for invoice. */
	@NonNull DebitCreditFlag debitCreditFlag;

	public enum DebitCreditFlag
	{
		D, C;

		public static DebitCreditFlag fromDocBaseType(@NonNull final String docBaseType)
		{
			return "ARC".equals(docBaseType) ? C : D;
		}
	}
}
