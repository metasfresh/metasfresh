package de.metas.payment.sepa.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.banking.mandate.DirectDebitMandate;
import de.metas.banking.mandate.DirectDebitMandateId;
import de.metas.banking.mandate.DirectDebitMandateType;
import de.metas.organization.OrgId;
import de.metas.payment.sepa.api.impl.DirectDebitMandateChecker.DirectDebitMandateProblem;
import de.metas.payment.sepa.api.impl.DirectDebitMandateChecker.LineWithMandate;
import de.metas.payment.sepa.api.impl.DirectDebitMandateChecker.ProblemType;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.payment.sepa.base
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

class DirectDebitMandateCheckerTest
{
	private static final LocalDate COLLECTION_DATE = LocalDate.of(2026, 11, 2);

	private DirectDebitMandateChecker checker;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		checker = new DirectDebitMandateChecker();
	}

	private static LineWithMandate line(final DirectDebitMandate mandate)
	{
		return LineWithMandate.builder()
				.partnerValue("debtor1")
				.partnerName("Debtor One")
				.iban("DE89370400440532013000")
				.mandate(mandate)
				.build();
	}

	@Test
	void severalLinesOfOneDebtorWithoutMandate_reportedOnce()
	{
		final ImmutableList<DirectDebitMandateProblem> problems = checker.check(ImmutableList.of(line(null), line(null)), COLLECTION_DATE);

		assertThat(problems).hasSize(1);
		assertThat(problems.get(0).getType()).isEqualTo(ProblemType.MISSING);
	}

	@Test
	void unusedOneOffMandateOnTwoLines_reportedOnceWithLineCount()
	{
		final DirectDebitMandate oneOff = DirectDebitMandate.builder()
				.id(DirectDebitMandateId.ofRepoId(1))
				.bankAccountId(BankAccountId.ofRepoId(10))
				.orgId(OrgId.ofRepoId(1000000))
				.type(DirectDebitMandateType.CORE)
				.reference("MREF-ONEOFF")
				.mandateDate(LocalDate.of(2026, 1, 10))
				.isRecurring(false)
				.build();

		final ImmutableList<DirectDebitMandateProblem> problems = checker.check(ImmutableList.of(line(oneOff), line(oneOff)), COLLECTION_DATE);

		assertThat(problems).hasSize(1);
		assertThat(problems.get(0).getType()).isEqualTo(ProblemType.ONE_OFF_MULTIPLE_LINES);
		assertThat(problems.get(0).getLineCount()).isEqualTo(2);
	}
}
