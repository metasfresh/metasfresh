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

package de.metas.acct.impexp;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.elementvalue.ChartOfAccounts;
import de.metas.elementvalue.ChartOfAccountsCreateRequest;
import de.metas.elementvalue.ChartOfAccountsService;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Element;
import org.compiere.model.I_I_ElementValue;

class ChartOfAccountsImportHelper
{
	private final ChartOfAccountsService chartOfAccountsService;

	private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);

	ChartOfAccountsImportHelper(
			@NonNull final ChartOfAccountsService chartOfAccountsService)
	{
		this.chartOfAccountsService = chartOfAccountsService;
	}

	@NonNull
	private static String extractChartOfAccountsNameNotNull(final @NonNull I_I_ElementValue importRecord)
	{
		final String chartOfAccountsName = StringUtils.trimBlankToNull(importRecord.getElementName());
		if (chartOfAccountsName == null)
		{
			throw new FillMandatoryException(I_I_ElementValue.COLUMNNAME_ElementName);
		}
		return chartOfAccountsName;
	}

	public ChartOfAccountsId importChartOfAccounts(@NonNull final I_I_ElementValue importRecord)
	{
		ChartOfAccountsId chartOfAccountsId = ChartOfAccountsId.ofRepoIdOrNull(importRecord.getC_Element_ID());
		if (chartOfAccountsId != null)
		{
			return chartOfAccountsId;
		}

		// Try searching by ID
		chartOfAccountsId = chartOfAccountsService.getByName(extractChartOfAccountsNameNotNull(importRecord), ClientId.ofRepoId(importRecord.getAD_Client_ID()))
				.map(ChartOfAccounts::getId)
				.orElse(null);

		// Create a new Chart of Accounts if not found
		if (chartOfAccountsId == null)
		{
			final ChartOfAccounts newChartOfAccounts = chartOfAccountsService.createChartOfAccounts(
					ChartOfAccountsCreateRequest.builder()
							.name(extractChartOfAccountsNameNotNull(importRecord))
							.clientId(ClientId.ofRepoId(importRecord.getAD_Client_ID()))
							.build());
			chartOfAccountsId = newChartOfAccounts.getId();
		}

		// Update the import record
		importRecord.setC_Element_ID(chartOfAccountsId.getRepoId());
		InterfaceWrapperHelper.save(importRecord);

		setChartOfAccountsToDefaultSchemaElement(chartOfAccountsId);

		return chartOfAccountsId;

	}

	public void setChartOfAccountsToDefaultSchemaElement(@NonNull final ChartOfAccountsId chartOfAccountsId)
	{
		final AcctSchemaId primaryAcctSchemaId = acctSchemasRepo.getPrimaryAcctSchemaId(ClientId.METASFRESH);
		final AcctSchema acctSchema = acctSchemasRepo.getById(primaryAcctSchemaId);
		final AcctSchemaElement accountElement = acctSchema.getSchemaElementByType(AcctSchemaElementType.Account);
		accountElement.setChartOfAccountsId(chartOfAccountsId);
		acctSchemasRepo.saveAcctSchemaElement(accountElement);
	}
}
