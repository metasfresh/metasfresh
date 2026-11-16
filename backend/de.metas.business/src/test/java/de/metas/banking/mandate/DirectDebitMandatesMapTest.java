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

package de.metas.banking.mandate;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.organization.OrgId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DirectDebitMandatesMapTest
{
	private static final OrgId EXPORT_ORG = OrgId.ofRepoId(1000000);
	private static final BankAccountId BANK_ACCOUNT = BankAccountId.ofRepoId(10);

	private static int nextId = 1;

	private static DirectDebitMandate mandate(final OrgId orgId, final String reference)
	{
		return DirectDebitMandate.builder()
				.id(DirectDebitMandateId.ofRepoId(nextId++))
				.bankAccountId(BANK_ACCOUNT)
				.orgId(orgId)
				.type(DirectDebitMandateType.B2B)
				.reference(reference)
				.mandateDate(LocalDate.of(2026, 1, 10))
				.isRecurring(true)
				.build();
	}

	@Test
	void firstMandatePerBankAccountWins()
	{
		// the repository orders the specific org before org 0; the map itself only keeps the first mandate per bank account
		final DirectDebitMandatesMap map = DirectDebitMandatesMap.ofList(
				ImmutableList.of(mandate(EXPORT_ORG, "EXPORT"), mandate(OrgId.ANY, "ANY")));

		assertThat(map.getMandate(BANK_ACCOUNT)).get().extracting(DirectDebitMandate::getReference).isEqualTo("EXPORT");
	}

	@Test
	void firstMandatePerBankAccountWins_orgAnyFirst()
	{
		final DirectDebitMandatesMap map = DirectDebitMandatesMap.ofList(
				ImmutableList.of(mandate(OrgId.ANY, "ANY"), mandate(EXPORT_ORG, "EXPORT")));

		assertThat(map.getMandate(BANK_ACCOUNT)).get().extracting(DirectDebitMandate::getReference).isEqualTo("ANY");
	}

	@Test
	void orgAnyMandateAlone()
	{
		final DirectDebitMandatesMap map = DirectDebitMandatesMap.ofList(ImmutableList.of(mandate(OrgId.ANY, "ANY")));

		assertThat(map.getMandate(BANK_ACCOUNT)).get().extracting(DirectDebitMandate::getReference).isEqualTo("ANY");
	}

	@Test
	void noMandatesGivesEmpty()
	{
		assertThat(DirectDebitMandatesMap.ofList(ImmutableList.of()).getMandate(BANK_ACCOUNT)).isEmpty();
	}
}
