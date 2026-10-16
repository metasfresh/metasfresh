/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.payment_allocation;

import com.google.common.collect.ImmutableList;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import org.adempiere.service.ClientId;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

/**
 * Covers that a user-typed, in-memory-only editable amount of an {@link InvoiceRow}
 * (service fee, payment bonus, bank fee, discount) survives the repository rebuild that
 * {@link InvoicesView#markPreparedForAllocation} triggers via {@code invalidateAll()}:
 * marking an invoice "prepared for allocation" must not blank a typed cell, so the rebuild
 * must carry over the user's in-memory edits, not only {@code isPreparedForAllocation}.
 */
public class InvoiceRowsTest
{
	private static final ZonedDateTime EVALUATION_DATE = LocalDate.parse("2020-04-01").atStartOfDay(ZoneId.of("Europe/Berlin"));
	private static final InvoiceId INVOICE_ID = InvoiceId.ofRepoId(1_000_001);

	private static Amount euro(final String amount)
	{
		return Amount.of(amount, CurrencyCode.EUR);
	}

	private static InvoiceRow.InvoiceRowBuilder rowBuilder()
	{
		final InvoiceDocBaseType docBaseType = InvoiceDocBaseType.CustomerInvoice;
		return InvoiceRow.builder()
				.invoiceId(INVOICE_ID)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ofRepoId(1)))
				.docTypeName(TranslatableStrings.anyLanguage("invoice"))
				.documentNo("invoiceNo_1")
				.dateInvoiced(LocalDate.parse("2020-04-01"))
				.bpartner(IntegerLookupValue.of(1, "BPartner"))
				.docBaseType(docBaseType)
				.invoiceAmtMultiplier(InvoiceAmtMultiplier.builder()
						.soTrx(docBaseType.getSoTrx())
						.isCreditMemo(docBaseType.isCreditMemo())
						.isAPAdjusted(false)
						.isCreditMemoAdjusted(true)
						.build()
						.intern())
				.grandTotal(euro("100"))
				.openAmt(euro("100"))
				.discountAmt(Amount.zero(CurrencyCode.EUR));
	}

	/**
	 * RED before the fix: {@code mergeFromOldRow} kept only the prepared flag, so the typed
	 * service fee / payment bonus / bank fee / discount of the old row were dropped on rebuild.
	 */
	@Test
	public void invalidate_keepsTypedEditableAmountsAndPreparedFlag()
	{
		// The row as the user left it: typed fees + bonus, and marked prepared for allocation.
		final InvoiceRow oldRow = rowBuilder()
				.isPreparedForAllocation(true)
				.discountAmt(euro("2"))
				.serviceFeeAmt(euro("10"))
				.bankFeeAmt(euro("5"))
				.paymentBonusAmt(euro("3"))
				.paymentBonusEnteredAmt(euro("3"))
				.build();

		// The row as the repository rebuilds it: fresh defaults, none of the user's edits (marking changes no DB state).
		final InvoiceRow freshRowFromRepo = rowBuilder()
				.isPreparedForAllocation(false)
				.discountAmt(Amount.zero(CurrencyCode.EUR))
				.serviceFeeAmt(Amount.zero(CurrencyCode.EUR))
				.bankFeeAmt(Amount.zero(CurrencyCode.EUR))
				.paymentBonusAmt(null)
				.build();

		final PaymentAndInvoiceRowsRepo repository = Mockito.mock(PaymentAndInvoiceRowsRepo.class);
		Mockito.when(repository.getInvoiceRowsListByInvoiceId(any(), any()))
				.thenReturn(ImmutableList.of(freshRowFromRepo));

		final InvoiceRows invoiceRows = InvoiceRows.builder()
				.repository(repository)
				.initialRows(ImmutableList.of(oldRow))
				.evaluationDate(EVALUATION_DATE)
				.build();

		// when: the repository rebuild that markPreparedForAllocation triggers
		invoiceRows.invalidateAll();

		// then: the typed edits survive, not only the prepared flag
		final Map<DocumentId, InvoiceRow> rowsById = invoiceRows.getDocumentId2TopLevelRows();
		final InvoiceRow mergedRow = rowsById.get(oldRow.getId());

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(mergedRow).as("merged row").isNotNull();
		if (mergedRow != null)
		{
			softly.assertThat(mergedRow.isPreparedForAllocation()).as("isPreparedForAllocation").isTrue();
			softly.assertThat(mergedRow.getServiceFeeAmt()).as("serviceFeeAmt").isEqualTo(euro("10"));
			softly.assertThat(mergedRow.getBankFeeAmt()).as("bankFeeAmt").isEqualTo(euro("5"));
			softly.assertThat(mergedRow.getPaymentBonusAmt()).as("paymentBonusAmt").isEqualTo(euro("3"));
			softly.assertThat(mergedRow.getDiscountAmt()).as("discountAmt").isEqualTo(euro("2"));
		}
		softly.assertAll();
	}
}
