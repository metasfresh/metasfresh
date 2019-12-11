package de.metas.acct.model.validator;

import java.util.List;

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

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.MAccount;
import org.compiere.model.ModelValidator;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;

@Interceptor(I_C_ElementValue.class)
public class C_ElementValue
{
	private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);

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
	public void afterSave(final I_C_ElementValue elementValue)
	{
		createValidCombination(elementValue);
	}

	private void createValidCombination(final I_C_ElementValue elementValue)
	{
		final ClientId adClientId = ClientId.ofRepoId(elementValue.getAD_Client_ID());
		final ChartOfAccountsId chartOfAccountsId = ChartOfAccountsId.ofRepoId(elementValue.getC_Element_ID());
		for (final AcctSchema acctSchema : getAcctSchemasHavingElementId(adClientId, chartOfAccountsId))
		{
			MAccount.getCreate(AccountDimension.builder()
					.setAcctSchemaId(acctSchema.getId())
					.setC_ElementValue_ID(elementValue.getC_ElementValue_ID())
					.setAD_Client_ID(adClientId.getRepoId())
					.setAD_Org_ID(OrgId.ANY.getRepoId())
					.build());
		}
	}

	private List<AcctSchema> getAcctSchemasHavingElementId(
			final ClientId adClientId,
			final ChartOfAccountsId chartOfAccountsId)
	{
		return acctSchemasRepo.getAllByClient(adClientId)
				.stream()
				.filter(acctSchema -> isAcctSchemaMatchingElementId(acctSchema, chartOfAccountsId))
				.collect(ImmutableList.toImmutableList());
	}

	private static boolean isAcctSchemaMatchingElementId(
			final AcctSchema acctSchema,
			final ChartOfAccountsId chartOfAccountsId)
	{
		final AcctSchemaElement accountElement = acctSchema.getSchemaElementByType(AcctSchemaElementType.Account);
		return accountElement != null
				&& ChartOfAccountsId.equals(accountElement.getChartOfAccountsId(), chartOfAccountsId);
	}
}
