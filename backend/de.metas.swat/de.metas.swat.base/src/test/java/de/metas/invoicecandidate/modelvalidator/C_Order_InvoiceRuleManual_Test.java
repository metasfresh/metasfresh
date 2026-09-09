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

import de.metas.adempiere.model.I_C_Order;
import de.metas.order.InvoiceRule;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Verifies that the interceptor in {@link C_Order} forbids the contradictory combination
 * {@code InvoiceRule = Manual} + {@code IsAutoInvoice = Y}, and allows all other combinations.
 */
class C_Order_InvoiceRuleManual_Test
{
	private C_Order interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		interceptor = new C_Order();
	}

	@Test
	void forbids_Manual_with_IsAutoInvoice()
	{
		final I_C_Order order = newOrder(InvoiceRule.Manual, /* isAutoInvoice */ true);

		assertThatThrownBy(() -> interceptor.preventInvoiceRuleManualWithIsAutoInvoice(order))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void allows_Manual_without_IsAutoInvoice()
	{
		final I_C_Order order = newOrder(InvoiceRule.Manual, /* isAutoInvoice */ false);

		interceptor.preventInvoiceRuleManualWithIsAutoInvoice(order);
		// reached this line without throwing → pass
		assertThat(order.getInvoiceRule()).isEqualTo(InvoiceRule.Manual.getCode());
	}

	@Test
	void allows_other_invoice_rules_with_IsAutoInvoice()
	{
		for (final InvoiceRule rule : InvoiceRule.values())
		{
			if (rule.isManual())
			{
				continue;
			}
			final I_C_Order order = newOrder(rule, /* isAutoInvoice */ true);
			interceptor.preventInvoiceRuleManualWithIsAutoInvoice(order);
			// reached without throwing → pass
			assertThat(order.getInvoiceRule()).isEqualTo(rule.getCode());
		}
	}

	@Test
	void allows_null_invoice_rule_with_IsAutoInvoice()
	{
		// guarded fallback: getInvoiceRule()=null must not blow up the interceptor
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setIsAutoInvoice(true);

		interceptor.preventInvoiceRuleManualWithIsAutoInvoice(order);
		// reached without throwing → pass
		assertThat(order.isAutoInvoice()).isTrue();
	}

	private static I_C_Order newOrder(final InvoiceRule rule, final boolean isAutoInvoice)
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setInvoiceRule(rule.getCode());
		order.setIsAutoInvoice(isAutoInvoice);
		return order;
	}
}
