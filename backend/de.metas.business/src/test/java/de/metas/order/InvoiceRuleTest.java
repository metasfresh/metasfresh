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

package de.metas.order;

import org.compiere.model.X_C_Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvoiceRuleTest
{
	@Test
	void manual_code_is_M()
	{
		assertThat(X_C_Order.INVOICERULE_Manual).isEqualTo("M");
		assertThat(InvoiceRule.Manual.getCode()).isEqualTo("M");
	}

	@Test
	void ofCode_M_returns_Manual()
	{
		assertThat(InvoiceRule.ofCode("M")).isEqualTo(InvoiceRule.Manual);
	}

	@Test
	void isManual_returns_true_only_for_Manual()
	{
		assertThat(InvoiceRule.Manual.isManual()).isTrue();
		assertThat(InvoiceRule.Immediate.isManual()).isFalse();
		assertThat(InvoiceRule.AfterPick.isManual()).isFalse();
		assertThat(InvoiceRule.AfterDelivery.isManual()).isFalse();
	}

	@Test
	void existing_codes_are_unchanged()
	{
		assertThat(InvoiceRule.Immediate.getCode()).isEqualTo("I");
		assertThat(InvoiceRule.AfterDelivery.getCode()).isEqualTo("D");
		assertThat(InvoiceRule.AfterOrderDelivered.getCode()).isEqualTo("O");
		assertThat(InvoiceRule.OrderCompletelyDelivered.getCode()).isEqualTo("C");
		assertThat(InvoiceRule.CustomerScheduleAfterDelivery.getCode()).isEqualTo("S");
		assertThat(InvoiceRule.AfterPick.getCode()).isEqualTo("P");
	}
}
