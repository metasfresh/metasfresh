/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.elementvalue;

import de.metas.acct.api.ChartOfAccountsId;
import de.metas.organization.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ElementValueRepositoryTest
{
	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private ElementValueCreateOrUpdateRequest.ElementValueCreateOrUpdateRequestBuilder elementValueCreateOrUpdateRequest()
	{
		return ElementValueCreateOrUpdateRequest.builder()
				.existingElementValueId(null)
				.orgId(OrgId.ANY)
				.chartOfAccountsId(ChartOfAccountsId.ofRepoId(1))
				.value("accountNo")
				.name("account name")
				.accountSign("D") // Debit
				.accountType("A") // Asset
				.isSummary(true)
				.isDocControlled(true)
				.isPostActual(true)
				.isPostBudget(true)
				.isPostStatistical(true)
				.parentId(null)
				.defaultAccountName("defaultAccountName");
	}

	@Test
	public void save_load()
	{
		final ElementValueRepository elementValueRepository = new ElementValueRepository();

		final ElementValue elementValue = elementValueRepository.createOrUpdate(
				ElementValueCreateOrUpdateRequest.builder()
						.existingElementValueId(null)
						.orgId(OrgId.ANY)
						.chartOfAccountsId(ChartOfAccountsId.ofRepoId(1))
						.value("accountNo")
						.name("account name")
						.accountSign("D") // Debit
						.accountType("A") // Asset
						.isSummary(true)
						.isDocControlled(true)
						.isPostActual(true)
						.isPostBudget(true)
						.isPostStatistical(true)
						.parentId(null)
						.defaultAccountName("defaultAccountName")
						.build()
		);

		final ElementValue elementValueReloaded = elementValueRepository.getById(elementValue.getId());
		Assertions.assertThat(elementValueReloaded)
				.isEqualTo(elementValue)
				.isEqualTo(ElementValue.builder()
						.id(elementValue.getId())
						.orgId(OrgId.ANY)
						.chartOfAccountsId(ChartOfAccountsId.ofRepoId(1))
						.value("accountNo")
						.name("account name")
						.accountSign("D") // Debit
						.accountType("A") // Asset
						.isSummary(true)
						.isDocControlled(true)
						.isPostActual(true)
						.isPostBudget(true)
						.isPostStatistical(true)
						.parentId(null)
						.defaultAccountName("defaultAccountName")
						.build());

	}

	@Test
	public void save_with_summary_parent()
	{
		final ElementValueRepository elementValueRepository = new ElementValueRepository();

		final ElementValue parent = elementValueRepository.createOrUpdate(elementValueCreateOrUpdateRequest().value("1").isSummary(true).build());
		final ElementValue child = elementValueRepository.createOrUpdate(elementValueCreateOrUpdateRequest().value("10").isSummary(false).parentId(parent.getId()).build());
		Assertions.assertThat(child.getParentId()).isEqualTo(parent.getId());
	}

	@Test
	public void save_with_notSummary_parent()
	{
		final ElementValueRepository elementValueRepository = new ElementValueRepository();

		final ElementValue parent = elementValueRepository.createOrUpdate(elementValueCreateOrUpdateRequest().value("1").isSummary(false).build());
		Assertions.assertThatThrownBy(
						() -> elementValueRepository.createOrUpdate(elementValueCreateOrUpdateRequest().value("10").isSummary(false).parentId(parent.getId()).build())
				)
				.hasMessageStartingWith("Parent element value must be a summary element value");
	}

}