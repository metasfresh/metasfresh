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

import de.metas.banking.BankAccountId;
import de.metas.organization.OrgId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DirectDebitMandateTest
{
	private static final LocalDate COLLECTION_DATE = LocalDate.of(2026, 10, 20);

	private static DirectDebitMandate mandate(@Nullable final LocalDate dateLastUsed)
	{
		return DirectDebitMandate.builder()
				.id(DirectDebitMandateId.ofRepoId(1))
				.bankAccountId(BankAccountId.ofRepoId(10))
				.orgId(OrgId.ofRepoId(1000000))
				.type(DirectDebitMandateType.B2B)
				.reference("MREF-1")
				.mandateDate(LocalDate.of(2026, 1, 10))
				.isRecurring(true)
				.dateLastUsed(dateLastUsed)
				.build();
	}

	@Nested
	class withUsedOn
	{
		@Test
		void neverUsed_lastUseIsTheCollectionDate()
		{
			final DirectDebitMandate mandate = mandate(null);

			assertThat(mandate.withUsedOn(COLLECTION_DATE))
					.contains(mandate.toBuilder().dateLastUsed(COLLECTION_DATE).build());
		}

		@Test
		void usedBefore_lastUseMovesForwardToTheCollectionDate()
		{
			final DirectDebitMandate mandate = mandate(COLLECTION_DATE.minusDays(1));

			assertThat(mandate.withUsedOn(COLLECTION_DATE))
					.contains(mandate.toBuilder().dateLastUsed(COLLECTION_DATE).build());
		}

		@Test
		void usedOnTheCollectionDate_unchanged()
		{
			assertThat(mandate(COLLECTION_DATE).withUsedOn(COLLECTION_DATE)).isEmpty();
		}

		@Test
		void usedAfterTheCollectionDate_lastUseNeverMovesBack()
		{
			assertThat(mandate(COLLECTION_DATE.plusDays(5)).withUsedOn(COLLECTION_DATE)).isEmpty();
		}
	}
}
