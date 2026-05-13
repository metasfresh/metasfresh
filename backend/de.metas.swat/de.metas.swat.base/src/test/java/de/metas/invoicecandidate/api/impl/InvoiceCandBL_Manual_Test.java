/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import de.metas.common.util.time.SystemTime;
import de.metas.order.InvoiceRule;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Unit tests for {@link InvoiceCandBL} behaviour when {@link InvoiceRule#Manual} is used.
 *
 * <p>TC5 from me03#28882: verifies that {@code computeDateToInvoice} returns {@code null} for Manual,
 * and that {@code isSkipCandidateFromInvoicing} only includes the Manual candidate when the dedicated
 * {@code isInvoiceManualRule} flag is set (decoupled from {@code ignoreInvoiceSchedule}).</p>
 */
class InvoiceCandBL_Manual_Test
{
	private InvoiceCandBL invoiceCandBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Ensure today's date is set in context so isSkipCandidateFromInvoicing can compare against it
		Env.setContext(Env.getCtx(), Env.CTXNAME_Date, SystemTime.asDayTimestamp());

		invoiceCandBL = new InvoiceCandBL();
	}

	/**
	 * TC5, step 1: {@code computeDateToInvoice} must return {@code null} for {@link InvoiceRule#Manual}.
	 * This is the trigger that drives the skip-filter behaviour.
	 */
	@Test
	void computeDateToInvoice_returns_null_for_Manual()
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setInvoiceRule(InvoiceRule.Manual.getCode());
		ic.setQtyDelivered(BigDecimal.ONE);
		ic.setQtyOrdered(BigDecimal.ONE);
		saveRecord(ic);

		assertThat(invoiceCandBL.computeDateToInvoice(ic))
				.as("computeDateToInvoice must return null for InvoiceRule=Manual")
				.isNull();
	}

	/**
	 * TC5 — Manual rule is decoupled from {@code ignoreInvoiceSchedule}:
	 * <ul>
	 *   <li>After {@code set_DateToInvoice_DefaultImpl}, {@code DateToInvoice} is {@code null}.</li>
	 *   <li>{@code isSkipCandidateFromInvoicing(ic, ignoreInvoiceSchedule=false, isInvoiceManualRule=false)} → skipped.</li>
	 *   <li>{@code isSkipCandidateFromInvoicing(ic, ignoreInvoiceSchedule=true, isInvoiceManualRule=false)} → still skipped
	 *       (ignoreInvoiceSchedule alone does NOT unlock Manual).</li>
	 *   <li>{@code isSkipCandidateFromInvoicing(ic, ignoreInvoiceSchedule=false, isInvoiceManualRule=true)} → NOT skipped.</li>
	 * </ul>
	 */
	@Test
	void isSkipCandidateFromInvoicing_only_invoices_Manual_when_dedicated_flag_is_set()
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setInvoiceRule(InvoiceRule.Manual.getCode());
		ic.setQtyDelivered(BigDecimal.ONE);
		ic.setQtyOrdered(BigDecimal.ONE);
		saveRecord(ic);

		// Simulate the recompute step that populates DateToInvoice
		invoiceCandBL.set_DateToInvoice_DefaultImpl(ic);

		assertThat(ic.getDateToInvoice())
				.as("DateToInvoice must be null after set_DateToInvoice_DefaultImpl for Manual rule")
				.isNull();

		// Default user run: neither flag set → skipped
		assertThat(invoiceCandBL.isSkipCandidateFromInvoicing(ic, /* ignoreInvoiceSchedule= */ false, /* isInvoiceManualRule= */ false))
				.as("IC with Manual rule must be skipped when neither bypass flag is set")
				.isTrue();

		// IgnoreInvoiceSchedule alone does NOT include Manual (decoupled)
		assertThat(invoiceCandBL.isSkipCandidateFromInvoicing(ic, /* ignoreInvoiceSchedule= */ true, /* isInvoiceManualRule= */ false))
				.as("IC with Manual rule must STILL be skipped when only IgnoreInvoiceSchedule=true (Manual axis is independent)")
				.isTrue();

		// Dedicated Manual flag set → NOT skipped
		assertThat(invoiceCandBL.isSkipCandidateFromInvoicing(ic, /* ignoreInvoiceSchedule= */ false, /* isInvoiceManualRule= */ true))
				.as("IC with Manual rule must NOT be skipped when IsInvoiceManualRule=true")
				.isFalse();

		// Both flags set: also NOT skipped (Manual axis takes precedence for Manual rule)
		assertThat(invoiceCandBL.isSkipCandidateFromInvoicing(ic, /* ignoreInvoiceSchedule= */ true, /* isInvoiceManualRule= */ true))
				.as("IC with Manual rule must NOT be skipped when both bypass flags are true")
				.isFalse();
	}

	/**
	 * Backward-compat sanity: the 2-arg overload delegates with {@code isInvoiceManualRule=false},
	 * so it always skips Manual candidates regardless of {@code ignoreInvoiceSchedule}.
	 */
	@Test
	void two_arg_overload_always_skips_Manual()
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setInvoiceRule(InvoiceRule.Manual.getCode());
		ic.setQtyDelivered(BigDecimal.ONE);
		ic.setQtyOrdered(BigDecimal.ONE);
		saveRecord(ic);
		invoiceCandBL.set_DateToInvoice_DefaultImpl(ic);

		assertThat(invoiceCandBL.isSkipCandidateFromInvoicing(ic, /* ignoreInvoiceSchedule= */ false)).isTrue();
		assertThat(invoiceCandBL.isSkipCandidateFromInvoicing(ic, /* ignoreInvoiceSchedule= */ true)).isTrue();
	}
}
