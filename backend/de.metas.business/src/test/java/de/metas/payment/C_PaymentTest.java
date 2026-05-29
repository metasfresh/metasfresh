/*
 * #%L
 * de.metas.business
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

package de.metas.payment;

import de.metas.invoice.InvoiceId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for the proforma full-payment guard ({@link C_Payment#assertProformaPaymentIsFull}).
 * <p>
 * Tests the static helper directly to bypass the {@code IInvoiceBL} / {@code Services.get} chain
 * the instance method needs. The wrapper instance method's only responsibility is to look up the
 * proforma invoice and forward to the helper, which is the trivial part.
 */
class C_PaymentTest
{
	private static final InvoiceId PROFORMA_INVOICE_ID = InvoiceId.ofRepoId(1234567);
	private static final BigDecimal PROFORMA_GRAND_TOTAL = new BigDecimal("20596.32");

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();  // wires the in-memory Msg/translation provider
	}

	@Test
	void assertProformaPaymentIsFull_acceptsExactGrandTotal()
	{
		assertThatCode(() -> C_Payment.assertProformaPaymentIsFull(
				PROFORMA_INVOICE_ID, PROFORMA_GRAND_TOTAL, PROFORMA_GRAND_TOTAL))
				.doesNotThrowAnyException();
	}

	@Test
	void assertProformaPaymentIsFull_acceptsNegatedReversalAmount()
	{
		// Reversal payment: PayAmt = -GrandTotal. abs() equals GrandTotal → must pass.
		assertThatCode(() -> C_Payment.assertProformaPaymentIsFull(
				PROFORMA_INVOICE_ID, PROFORMA_GRAND_TOTAL, PROFORMA_GRAND_TOTAL.negate()))
				.doesNotThrowAnyException();
	}

	@Test
	void assertProformaPaymentIsFull_rejectsPartialPayment()
	{
		assertThatThrownBy(() -> C_Payment.assertProformaPaymentIsFull(
				PROFORMA_INVOICE_ID, PROFORMA_GRAND_TOTAL, new BigDecimal("10000.00")))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void assertProformaPaymentIsFull_rejectsOverpayment()
	{
		assertThatThrownBy(() -> C_Payment.assertProformaPaymentIsFull(
				PROFORMA_INVOICE_ID, PROFORMA_GRAND_TOTAL, PROFORMA_GRAND_TOTAL.add(BigDecimal.ONE)))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void assertProformaPaymentIsFull_rejectsZeroPayment()
	{
		assertThatThrownBy(() -> C_Payment.assertProformaPaymentIsFull(
				PROFORMA_INVOICE_ID, PROFORMA_GRAND_TOTAL, BigDecimal.ZERO))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void assertProformaPaymentIsFull_rejectsRoundingDifference()
	{
		// Even a one-cent difference must fail — no tolerance, since proforma payments have no
		// C_AllocationLine rows so a residual cannot be reconciled.
		assertThatThrownBy(() -> C_Payment.assertProformaPaymentIsFull(
				PROFORMA_INVOICE_ID, PROFORMA_GRAND_TOTAL, PROFORMA_GRAND_TOTAL.subtract(new BigDecimal("0.01"))))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void assertProformaPaymentIsFull_acceptsExactWithDifferentScale()
	{
		// 20596.32 vs 20596.320000 — same value, different BigDecimal scale.
		// compareTo (used by the validator) is scale-agnostic; equals is not.
		assertThatCode(() -> C_Payment.assertProformaPaymentIsFull(
				PROFORMA_INVOICE_ID, PROFORMA_GRAND_TOTAL, new BigDecimal("20596.320000")))
				.doesNotThrowAnyException();
	}
}
