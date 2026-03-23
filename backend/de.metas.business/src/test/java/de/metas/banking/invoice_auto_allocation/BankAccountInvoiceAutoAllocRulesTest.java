/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.banking.invoice_auto_allocation;

import de.metas.banking.BankAccountId;
import de.metas.document.DocTypeId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

class BankAccountInvoiceAutoAllocRulesTest
{
	@Nested
	class isAutoAllocate
	{
		@Test
		public void noRestrictions()
		{
			Assertions.assertThat(BankAccountInvoiceAutoAllocRules.NO_RESTRICTIONS.isAutoAllocate(BankAccountId.ofRepoId(1), DocTypeId.ofRepoId(2)))
					.isTrue();
		}

		@Test
		public void invoiceDocType_restricted_to_one_BankAccount()
		{
			final BankAccountInvoiceAutoAllocRules rules = BankAccountInvoiceAutoAllocRules.ofRulesList(Collections.singletonList(
					BankAccountInvoiceAutoAllocRule.builder().bankAccountId(BankAccountId.ofRepoId(10)).invoiceDocTypeId(DocTypeId.ofRepoId(20)).build()
			));

			Assertions.assertThat(rules.isAutoAllocate(BankAccountId.ofRepoId(10), DocTypeId.ofRepoId(20)))
					.isTrue();
			Assertions.assertThat(rules.isAutoAllocate(BankAccountId.ofRepoId(10), DocTypeId.ofRepoId(21)))
					.isFalse();

			Assertions.assertThat(rules.isAutoAllocate(BankAccountId.ofRepoId(11), DocTypeId.ofRepoId(20)))
					.isFalse();
			Assertions.assertThat(rules.isAutoAllocate(BankAccountId.ofRepoId(11), DocTypeId.ofRepoId(21)))
					.isTrue();
		}

		@Test
		public void invoiceDocType_restricted_to_two_BankAccount()
		{
			final BankAccountInvoiceAutoAllocRules rules = BankAccountInvoiceAutoAllocRules.ofRulesList(Arrays.asList(
					BankAccountInvoiceAutoAllocRule.builder().bankAccountId(BankAccountId.ofRepoId(10)).invoiceDocTypeId(DocTypeId.ofRepoId(20)).build(),
					BankAccountInvoiceAutoAllocRule.builder().bankAccountId(BankAccountId.ofRepoId(11)).invoiceDocTypeId(DocTypeId.ofRepoId(20)).build()
			));

			Assertions.assertThat(rules.isAutoAllocate(BankAccountId.ofRepoId(10), DocTypeId.ofRepoId(20)))
					.isTrue();
			Assertions.assertThat(rules.isAutoAllocate(BankAccountId.ofRepoId(10), DocTypeId.ofRepoId(21)))
					.isFalse();

			Assertions.assertThat(rules.isAutoAllocate(BankAccountId.ofRepoId(11), DocTypeId.ofRepoId(20)))
					.isTrue();
			Assertions.assertThat(rules.isAutoAllocate(BankAccountId.ofRepoId(11), DocTypeId.ofRepoId(21)))
					.isFalse();

			Assertions.assertThat(rules.isAutoAllocate(BankAccountId.ofRepoId(12), DocTypeId.ofRepoId(20)))
					.isFalse();
			Assertions.assertThat(rules.isAutoAllocate(BankAccountId.ofRepoId(12), DocTypeId.ofRepoId(21)))
					.isTrue();
		}
		
		@Test
		public void invoiceDocType_restricted_no_BankAcccount()
		{
			final BankAccountInvoiceAutoAllocRules rules = BankAccountInvoiceAutoAllocRules.ofRulesList(Arrays.asList(
					BankAccountInvoiceAutoAllocRule.builder().bankAccountId(BankAccountId.ofRepoId(10)).invoiceDocTypeId(DocTypeId.ofRepoId(20)).build(),
					BankAccountInvoiceAutoAllocRule.builder().bankAccountId(BankAccountId.ofRepoId(11)).invoiceDocTypeId(DocTypeId.ofRepoId(20)).build()
			));

			Assertions.assertThat(rules.isAutoAllocate(null, DocTypeId.ofRepoId(20)))
					.isFalse();

			Assertions.assertThat(rules.isAutoAllocate(null, DocTypeId.ofRepoId(21)))
					.isTrue();
		}
	}
}