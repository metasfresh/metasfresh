package de.metas.acct.interceptor;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.annotations.VisibleForTesting;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.IAccountBL;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueRepository;
import de.metas.organization.OrgId;
import de.metas.treenode.TreeNodeService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.ModelValidator;

import java.util.concurrent.atomic.AtomicBoolean;

@Interceptor(I_C_ElementValue.class)
public class C_ElementValue
{
	private final IAcctSchemaDAO acctSchemasRepo;
	private final IAccountBL accountBL;
	private final TreeNodeService treeNodeService;

	private static final AtomicBoolean updateTreeNodeDisabled = new AtomicBoolean(false);

	public C_ElementValue(
			@NonNull final IAcctSchemaDAO acctSchemasRepo,
			@NonNull final IAccountBL accountBL,
			@NonNull final TreeNodeService treeNodeService)
	{
		this.acctSchemasRepo = acctSchemasRepo;
		this.accountBL = accountBL;
		this.treeNodeService = treeNodeService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_ElementValue elementValue)
	{
		validate(elementValue);
	}

	private void validate(final I_C_ElementValue elementValue)
	{
		if (elementValue.isAutoTaxAccount() && elementValue.getC_Tax_ID() <= 0)
		{
			throw new FillMandatoryException(I_C_ElementValue.COLUMNNAME_C_Tax_ID);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_C_ElementValue elementValue, final ModelChangeType changeType)
	{
		createValidCombinationIfNeeded(elementValue);

		if (changeType.isChange()
				&& InterfaceWrapperHelper.isValueChanged(elementValue, I_C_ElementValue.COLUMNNAME_Value, I_C_ElementValue.COLUMNNAME_Name))
		{
			accountBL.updateValueDescriptionByElementType(AcctSchemaElementType.Account, elementValue.getC_ElementValue_ID());
		}
	}

	@VisibleForTesting
	protected void createValidCombinationIfNeeded(final I_C_ElementValue elementValue)
	{
		if (elementValue.isSummary())
		{
			return;
		}

		final AccountDimension.Builder accountDimensionTemplate = AccountDimension.builder()
				//.setAcctSchemaId(acctSchema.getId())
				.setC_ElementValue_ID(elementValue.getC_ElementValue_ID())
				.setAD_Client_ID(elementValue.getAD_Client_ID())
				.setAD_Org_ID(OrgId.ANY.getRepoId());

		final ChartOfAccountsId chartOfAccountsId = ChartOfAccountsId.ofRepoId(elementValue.getC_Element_ID());
		for (final AcctSchema acctSchema : acctSchemasRepo.getByChartOfAccountsId(chartOfAccountsId))
		{
			accountBL.createIfMissing(accountDimensionTemplate.setAcctSchemaId(acctSchema.getId()).build());
		}
	}

	public static IAutoCloseable temporaryDisableUpdateTreeNode()
	{
		final boolean wasDisabled = updateTreeNodeDisabled.getAndSet(true);
		return () -> updateTreeNodeDisabled.set(wasDisabled);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_C_ElementValue.COLUMNNAME_Parent_ID, I_C_ElementValue.COLUMNNAME_SeqNo })
	public void updateTreeNode(final I_C_ElementValue record)
	{
		if (updateTreeNodeDisabled.get())
		{
			return;
		}

		final ElementValue elementValue = ElementValueRepository.toElementValue(record);
		treeNodeService.updateTreeNode(elementValue);
	}
}
