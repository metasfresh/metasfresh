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

package de.metas.invoicecandidate.modelvalidator;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.InvoiceRule;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Verifies that the {@link C_Invoice_Candidate#preventInvoiceRuleManualWithIsAutoInvoice} interceptor
 * blocks {@code InvoiceRule = Manual} (direct or via override) together with {@code IsAutoInvoice = Y}.
 */
class C_Invoice_Candidate_InvoiceRuleManual_Test
{
	private C_Invoice_Candidate interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		interceptor = C_Invoice_Candidate.newInstanceForUnitTesting();
	}

	@Test
	void forbids_direct_Manual_with_IsAutoInvoice()
	{
		final I_C_Invoice_Candidate ic = newIC(InvoiceRule.Manual.getCode(), /* invoiceRuleOverride */ null, /* isAutoInvoice */ true);

		assertThatThrownBy(() -> interceptor.preventInvoiceRuleManualWithIsAutoInvoice(ic))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void forbids_Override_Manual_with_IsAutoInvoice()
	{
		// InvoiceRule from order is AfterDelivery, but user overrides to Manual on the IC
		final I_C_Invoice_Candidate ic = newIC(InvoiceRule.AfterDelivery.getCode(), InvoiceRule.Manual.getCode(), /* isAutoInvoice */ true);

		assertThatThrownBy(() -> interceptor.preventInvoiceRuleManualWithIsAutoInvoice(ic))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void allows_Override_NonManual_when_direct_is_Manual()
	{
		// Direct InvoiceRule is Manual (e.g. inherited oddly), but Override is AfterDelivery — effective is AfterDelivery
		final I_C_Invoice_Candidate ic = newIC(InvoiceRule.Manual.getCode(), InvoiceRule.AfterDelivery.getCode(), /* isAutoInvoice */ true);

		interceptor.preventInvoiceRuleManualWithIsAutoInvoice(ic);
		// reached without throwing → pass
		assertThat(ic.isAutoInvoice()).isTrue();
	}

	@Test
	void allows_Manual_without_IsAutoInvoice()
	{
		final I_C_Invoice_Candidate ic = newIC(InvoiceRule.Manual.getCode(), /* invoiceRuleOverride */ null, /* isAutoInvoice */ false);

		interceptor.preventInvoiceRuleManualWithIsAutoInvoice(ic);
		// reached without throwing → pass
		assertThat(ic.getInvoiceRule()).isEqualTo(InvoiceRule.Manual.getCode());
	}

	@Test
	void allows_other_rules_with_IsAutoInvoice()
	{
		for (final InvoiceRule rule : InvoiceRule.values())
		{
			if (rule.isManual())
			{
				continue;
			}
			final I_C_Invoice_Candidate ic = newIC(rule.getCode(), /* invoiceRuleOverride */ null, /* isAutoInvoice */ true);
			interceptor.preventInvoiceRuleManualWithIsAutoInvoice(ic);
			// reached without throwing → pass
			assertThat(ic.getInvoiceRule()).isEqualTo(rule.getCode());
		}
	}

	@Test
	void allows_null_InvoiceRule_with_IsAutoInvoice()
	{
		// defensive: a null rule must not blow up the interceptor
		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class);
		ic.setIsAutoInvoice(true);

		interceptor.preventInvoiceRuleManualWithIsAutoInvoice(ic);
		// reached without throwing → pass
		assertThat(ic.isAutoInvoice()).isTrue();
	}

	private static I_C_Invoice_Candidate newIC(final String invoiceRule, final String invoiceRuleOverride, final boolean isAutoInvoice)
	{
		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class);
		ic.setInvoiceRule(invoiceRule);
		ic.setInvoiceRule_Override(invoiceRuleOverride);
		ic.setIsAutoInvoice(isAutoInvoice);
		return ic;
	}
}
