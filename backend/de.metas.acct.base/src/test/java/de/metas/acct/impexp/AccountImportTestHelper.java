package de.metas.acct.impexp;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.elementvalue.ChartOfAccounts;
import de.metas.elementvalue.ChartOfAccountsService;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueRepository;
import de.metas.elementvalue.ElementValueService;
import de.metas.treenode.TreeNode;
import de.metas.treenode.TreeNodeRepository;
import de.metas.treenode.TreeNodeService;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.tree.AdTreeId;
import org.compiere.model.I_I_ElementValue;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

class AccountImportTestHelper
{
	private final ChartOfAccountsService chartOfAccountsService;
	private final ElementValueService elementValueService;
	private final ElementValueRepository elementValueRepository;
	private final TreeNodeService treeNodeService;
	private final TreeNodeRepository treeNodeRepository;

	AccountImportTestHelper(
			@NonNull final ChartOfAccountsService chartOfAccountsService,
			@NonNull final ElementValueService elementValueService,
			@NonNull final ElementValueRepository elementValueRepository,
			@NonNull final TreeNodeService treeNodeService,
			@NonNull final TreeNodeRepository treeNodeRepository)
	{
		this.chartOfAccountsService = chartOfAccountsService;
		this.elementValueService = elementValueService;
		this.elementValueRepository = elementValueRepository;
		this.treeNodeService = treeNodeService;
		this.treeNodeRepository = treeNodeRepository;
	}

	@Builder(builderMethodName = "importRecord", builderClassName = "ImportRecordBuilder")
	private static I_I_ElementValue createImportRecord(
			String chartOfAccountsName,
			String value,
			String name,
			String parentValue,
			String accountType,
			String accountSign,
			boolean summary,
			boolean postActual,
			boolean postBudget,
			boolean postStatistical,
			boolean docControlled,
			String defaultAccountName)
	{
		final I_I_ElementValue importRecord = InterfaceWrapperHelper.newInstance(I_I_ElementValue.class);
		importRecord.setElementName(chartOfAccountsName);
		importRecord.setParentValue(parentValue);
		importRecord.setValue(value);
		importRecord.setName(name);
		importRecord.setAccountSign(accountSign);
		importRecord.setAccountType(accountType);
		importRecord.setIsSummary(summary);
		importRecord.setPostActual(postActual);
		importRecord.setPostBudget(postBudget);
		importRecord.setPostStatistical(postStatistical);
		importRecord.setIsDocControlled(docControlled);
		importRecord.setDefault_Account(defaultAccountName);

		InterfaceWrapperHelper.save(importRecord);
		return importRecord;
	}

	public void assertImported(final I_I_ElementValue importRecord)
	{
		assertChartOfAccountImported(importRecord);
		assertElementValueImported(importRecord);
	}

	public void assertChartOfAccountImported(final I_I_ElementValue importRecord)
	{
		final ChartOfAccountsId chartOfAccountsId = ChartOfAccountsId.ofRepoId(importRecord.getC_Element_ID());
		final ChartOfAccounts chartOfAccounts = chartOfAccountsService.getById(chartOfAccountsId);
		assertThat(chartOfAccounts.getName()).isEqualTo(importRecord.getElementName());
	}

	public void assertElementValueImported(final I_I_ElementValue importRecord)
	{
		final ElementValueId elementValueId = ElementValueId.ofRepoId(importRecord.getC_ElementValue_ID());
		final ElementValue elementValue = elementValueService.getById(elementValueId);
		assertThat(elementValue.getValue()).isEqualTo(importRecord.getValue());
		assertThat(elementValue.getName()).isEqualTo(importRecord.getName());
		assertThat(elementValue.getAccountType()).isEqualTo(importRecord.getAccountType());
		assertThat(elementValue.getAccountSign()).isEqualTo(importRecord.getAccountSign());
		assertThat(elementValue.isSummary()).isEqualTo(importRecord.isSummary());
		assertThat(elementValue.isPostActual()).isEqualTo(importRecord.isPostActual());
		assertThat(elementValue.isPostBudget()).isEqualTo(importRecord.isPostBudget());
		assertThat(elementValue.isPostStatistical()).isEqualTo(importRecord.isPostStatistical());
		assertThat(elementValue.isDocControlled()).isEqualTo(importRecord.isDocControlled());
		assertThat(elementValue.getDefaultAccountName()).isEqualTo(importRecord.getDefault_Account());

		if (!Check.isBlank(importRecord.getParentValue()))
		{
			final ElementValueId parentElementValueId = ElementValueId.ofRepoIdOrNull(importRecord.getParentElementValue_ID());
			assertThat(parentElementValueId).isNotNull();
			assertThat(elementValue.getParentId()).isEqualTo(parentElementValueId);

			final ElementValue parentElementValue = elementValueService.getById(parentElementValueId);
			assertThat(parentElementValue.getValue()).isEqualTo(importRecord.getParentValue());
		}
		else
		{
			final ElementValueId parentElementValueId = ElementValueId.ofRepoIdOrNull(importRecord.getParentElementValue_ID());
			assertThat(parentElementValueId).isNull();
			assertThat(elementValue.getParentId()).isNull();
		}
	}

	public static ImmutableSet<ChartOfAccountsId> extractChartOfAccountsIds(final List<I_I_ElementValue> importRecords)
	{
		return importRecords.stream()
				.map(importRecord -> ChartOfAccountsId.ofRepoId(importRecord.getC_Element_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	public void assertTreeStructureIsUpToDate(final ChartOfAccountsId chartOfAccountsId)
	{
		final ImmutableSet<TreeNode> nodesCreatedFromElementValues = elementValueRepository
				.getAllRecordsByChartOfAccountsId(chartOfAccountsId)
				.stream()
				.map(ElementValueRepository::toElementValue)
				.map(treeNodeService::toTreeNode)
				.collect(ImmutableSet.toImmutableSet());

		final AdTreeId chartOfAccountsTreeId = chartOfAccountsService.getById(chartOfAccountsId).getTreeId();

		final ImmutableSet<TreeNode> nodesFoundInDB = treeNodeRepository.getByTreeId(chartOfAccountsTreeId);

		assertThat(nodesFoundInDB).isEqualTo(nodesCreatedFromElementValues);
	}
}
