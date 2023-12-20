/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.acct.gljournal_sap;

import de.metas.acct.Account;
import de.metas.acct.api.AccountId;
import de.metas.document.dimension.Dimension;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.tax.api.TaxId;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import static de.metas.testsupport.ModelAssert.assertThat;

public class SAPGLJournalLineTest
{
	@Test
	public void givenSAPGLJournalLine_withParentIdAndTaxId_whenIsTaxLine_thenReturnTrue()
	{
		final SAPGLJournalLine line = createMockLine()
				.toBuilder()
				.parentId(SAPGLJournalLineId.ofRepoId(1, 2))
				.taxId(TaxId.ofRepoId(2))
				.build();

		assertThat(line.isTaxLine()).isEqualTo(true);
	}

	@Test
	public void givenSAPGLJournalLine_withParentIdAndTaxId_whenIsGeneratedTaxLine_thenReturnTrue()
	{
		final SAPGLJournalLine line = createMockLine()
				.toBuilder()
				.parentId(SAPGLJournalLineId.ofRepoId(1, 2))
				.taxId(TaxId.ofRepoId(2))
				.build();

		assertThat(line.isGeneratedTaxLine()).isEqualTo(true);
	}

	@Test
	public void givenSAPGLJournalLine_withParentIdAndTaxId_whenIsBaseTaxLine_thenReturnFalse()
	{
		final SAPGLJournalLine line = createMockLine()
				.toBuilder()
				.parentId(SAPGLJournalLineId.ofRepoId(1, 2))
				.taxId(TaxId.ofRepoId(2))
				.build();

		assertThat(line.isBaseTaxLine()).isEqualTo(false);
	}

	@Test
	public void givenSAPGLJournalLine_with_NoParentId_TaxId_determineTaxBaseSAP_whenIsTaxLine_thenReturnTrue()
	{
		final SAPGLJournalLine line = createMockLine()
				.toBuilder()
				.parentId(null)
				.taxId(TaxId.ofRepoId(2))
				.determineTaxBaseSAP(true)
				.build();

		assertThat(line.isTaxLine()).isEqualTo(true);
	}

	@Test
	public void givenSAPGLJournalLine_with_NoParentId_TaxId_determineTaxBaseSAP_whenIsGeneratedTaxLine_thenReturnFalse()
	{
		final SAPGLJournalLine line = createMockLine()
				.toBuilder()
				.parentId(null)
				.taxId(TaxId.ofRepoId(2))
				.determineTaxBaseSAP(true)
				.build();

		assertThat(line.isGeneratedTaxLine()).isEqualTo(false);
	}

	@Test
	public void givenSAPGLJournalLine_with_NoParentId_TaxId_determineTaxBaseSAP_whenIsBaseTaxLine_thenReturnFalse()
	{
		final SAPGLJournalLine line = createMockLine()
				.toBuilder()
				.parentId(null)
				.taxId(TaxId.ofRepoId(2))
				.determineTaxBaseSAP(true)
				.build();

		assertThat(line.isBaseTaxLine()).isEqualTo(false);
	}

	@Test
	public void givenSAPGLJournalLine_with_NoParentId_TaxId_whenIsTaxLine_thenReturnFalse()
	{
		final SAPGLJournalLine line = createMockLine()
				.toBuilder()
				.parentId(null)
				.taxId(TaxId.ofRepoId(2))
				.build();

		assertThat(line.isTaxLine()).isEqualTo(false);
	}

	@Test
	public void givenSAPGLJournalLine_with_NoParentId_TaxId_whenIsGeneratedTaxLine_thenReturnFalse()
	{
		final SAPGLJournalLine line = createMockLine()
				.toBuilder()
				.parentId(null)
				.taxId(TaxId.ofRepoId(2))
				.build();

		assertThat(line.isGeneratedTaxLine()).isEqualTo(false);
	}

	@Test
	public void givenSAPGLJournalLine_with_NoParentId_TaxId_whenIsBaseTaxLine_thenReturnTrue()
	{
		final SAPGLJournalLine line = createMockLine()
				.toBuilder()
				.parentId(null)
				.taxId(TaxId.ofRepoId(2))
				.build();

		assertThat(line.isBaseTaxLine()).isEqualTo(true);
	}

	@NonNull
	private static SAPGLJournalLine createMockLine()
	{
		return SAPGLJournalLine.builder()
				.id(SAPGLJournalLineId.ofRepoId(1, 1))
				.account(Account.ofId(AccountId.ofRepoId(1)))
				.amount(Money.of(1, CurrencyId.ofRepoId(1)))
				.amountAcct(Money.of(1, CurrencyId.ofRepoId(1)))
				.dimension(Dimension.builder().build())
				.description("description")
				.line(SeqNo.ofInt(1))
				.orgId(OrgId.MAIN)
				.postingSign(PostingSign.CREDIT)
				.build();
	}
}
