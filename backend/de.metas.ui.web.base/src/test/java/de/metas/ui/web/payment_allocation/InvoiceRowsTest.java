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
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
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
 * Covers the two in-memory states an {@link InvoiceRow} may carry across a view operation:
 * <ul>
 *     <li>Marking a row prepared-for-allocation flips the flag <i>in place</i> (no DB state changes, so no repository
 *     rebuild) and must keep the user's typed-but-not-yet-persisted amounts (service fee, payment bonus, bank fee, discount).</li>
 *     <li>A genuine {@code invalidate} (the underlying {@code C_Invoice} record changed) must <i>refresh</i> all
 *     repository-computed amounts from the fresh row and only carry over the prepared flag -- otherwise a stale
 *     prefilled amount would overwrite a freshly recomputed one.</li>
 * </ul>
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
	 * Marking does not rebuild the rows from the repository, so the user's typed amounts stay untouched and only the
	 * prepared flag flips. The repository mock throws if it is ever called, pinning the no-rebuild behaviour.
	 */
	@Test
	public void markPreparedForAllocation_keepsTypedEditableAmountsInPlace()
	{
		final InvoiceRow typedRow = rowBuilder()
				.isPreparedForAllocation(false)
				.discountAmt(euro("2"))
				.serviceFeeAmt(euro("10"))
				.bankFeeAmt(euro("5"))
				.paymentBonusAmt(euro("3"))
				.paymentBonusEnteredAmt(euro("3"))
				.build();

		final PaymentAndInvoiceRowsRepo repository = Mockito.mock(PaymentAndInvoiceRowsRepo.class, invocation -> {
			throw new AssertionError("Marking must not reload the rows from the repository: " + invocation);
		});

		final InvoiceRows invoiceRows = InvoiceRows.builder()
				.repository(repository)
				.initialRows(ImmutableList.of(typedRow))
				.evaluationDate(EVALUATION_DATE)
				.build();

		// when
		invoiceRows.markPreparedForAllocation(DocumentIdsSelection.of(ImmutableList.of(typedRow.getId())));

		// then: the flag is set in place and every typed amount is preserved
		final InvoiceRow markedRow = invoiceRows.getDocumentId2TopLevelRows().get(typedRow.getId());

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(markedRow).as("marked row").isNotNull();
		if (markedRow != null)
		{
			softly.assertThat(markedRow.isPreparedForAllocation()).as("isPreparedForAllocation").isTrue();
			softly.assertThat(markedRow.getServiceFeeAmt()).as("serviceFeeAmt").isEqualTo(euro("10"));
			softly.assertThat(markedRow.getBankFeeAmt()).as("bankFeeAmt").isEqualTo(euro("5"));
			softly.assertThat(markedRow.getPaymentBonusAmt()).as("paymentBonusAmt").isEqualTo(euro("3"));
			softly.assertThat(markedRow.getDiscountAmt()).as("discountAmt").isEqualTo(euro("2"));
		}
		softly.assertAll();
	}

	/**
	 * HIGH-2 regression guard: a genuine invalidate refreshes all repository-computed amounts from the fresh row and
	 * carries over ONLY the prepared flag. RED before the fix: {@code mergeFromOldRow} carried the old amounts too, so a
	 * stale prefilled value (e.g. an already-partly-paid invoice's old discount/bonus) overwrote the recomputed one.
	 */
	@Test
	public void invalidate_refreshesAmountsFromRepository_keepsOnlyPreparedFlag()
	{
		// The row as the user left it: marked prepared, with the amounts computed for the previous open amount.
		final InvoiceRow oldRow = rowBuilder()
				.isPreparedForAllocation(true)
				.openAmt(euro("100"))
				.discountAmt(euro("2"))
				.serviceFeeAmt(euro("10"))
				.bankFeeAmt(euro("5"))
				.paymentBonusAmt(euro("3"))
				.paymentBonusEnteredAmt(euro("3"))
				.build();

		// The row the repository rebuilds after the invoice changed: a smaller open amount and freshly recomputed amounts.
		final InvoiceRow freshRowFromRepo = rowBuilder()
				.isPreparedForAllocation(false)
				.openAmt(euro("80"))
				.discountAmt(euro("1"))
				.serviceFeeAmt(euro("4"))
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

		// when: the invoice record changed and the rows are invalidated
		invoiceRows.invalidateAll();

		// then: the amounts come from the fresh repository row; only the prepared flag is carried over
		final Map<DocumentId, InvoiceRow> rowsById = invoiceRows.getDocumentId2TopLevelRows();
		final InvoiceRow mergedRow = rowsById.get(oldRow.getId());

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(mergedRow).as("merged row").isNotNull();
		if (mergedRow != null)
		{
			softly.assertThat(mergedRow.isPreparedForAllocation()).as("isPreparedForAllocation (carried)").isTrue();
			softly.assertThat(mergedRow.getOpenAmt()).as("openAmt (refreshed)").isEqualTo(euro("80"));
			softly.assertThat(mergedRow.getDiscountAmt()).as("discountAmt (refreshed)").isEqualTo(euro("1"));
			softly.assertThat(mergedRow.getServiceFeeAmt()).as("serviceFeeAmt (refreshed)").isEqualTo(euro("4"));
			softly.assertThat(mergedRow.getBankFeeAmt()).as("bankFeeAmt (refreshed)").isEqualTo(Amount.zero(CurrencyCode.EUR));
			softly.assertThat(mergedRow.getPaymentBonusAmt()).as("paymentBonusAmt (refreshed)").isNull();
		}
		softly.assertAll();
	}
}
