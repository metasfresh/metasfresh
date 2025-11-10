package de.metas.acct.api;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.impl.AcctSchemaPeriodControl;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.service.ClientId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder
@ToString(of = { "name", "currencyId", "costing" })
public class AcctSchema
{
	@NonNull
	AcctSchemaId id;
	@NonNull
	ClientId clientId;
	@NonNull
	OrgId orgId;

	@NonNull
	String name;

	@NonNull
	CurrencyId currencyId;
	@NonNull
	CurrencyPrecision standardPrecision;

	@NonNull
	AcctSchemaCosting costing;
	@NonNull
	AcctSchemaValidCombinationOptions validCombinationOptions;
	@NonNull
	TaxCorrectionType taxCorrectionType;
	@NonNull
	@Default
	ImmutableSet<OrgId> postOnlyForOrgIds = ImmutableSet.of();
	boolean accrual;
	boolean allowNegativePosting;
	boolean postTradeDiscount;
	boolean postServices;
	boolean postIfSameClearingAccounts;
	boolean isAllowMultiDebitAndCredit;

	boolean isAutoSetDebtoridAndCreditorid;
	int debtorIdPrefix;
	int creditorIdPrefix;

	@NonNull
	AcctSchemaGeneralLedger generalLedger;

	@NonNull
	AcctSchemaDefaultAccounts defaultAccounts;

	@NonNull
	AcctSchemaPeriodControl periodControl;

	@NonNull
	AcctSchemaElementsMap schemaElements;

	public boolean isPostOnlyForSomeOrgs()
	{
		return !postOnlyForOrgIds.isEmpty();
	}

	public boolean isAllowPostingForOrg(@NonNull final OrgId orgId)
	{
		if (postOnlyForOrgIds.isEmpty())
		{
			return true;
		}
		else
		{
			return postOnlyForOrgIds.contains(orgId);
		}
	}

	public boolean isDisallowPostingForOrg(@NonNull final OrgId orgId)
	{
		return !isAllowPostingForOrg(orgId);
	}

	public boolean isElementEnabled(@NonNull final AcctSchemaElementType elementType)
	{
		return getSchemaElements().isElementEnabled(elementType);
	}

	public AcctSchemaElement getSchemaElementByType(@NonNull final AcctSchemaElementType elementType)
	{
		return getSchemaElements().getByElementType(elementType);
	}

	public ImmutableSet<AcctSchemaElementType> getSchemaElementTypes()
	{
		return getSchemaElements().getElementTypes();
	}

	public ChartOfAccountsId getChartOfAccountsId()
	{
		return getSchemaElements().getChartOfAccountsId();
	}

	public boolean isAccountingCurrency(@NonNull final CurrencyId currencyId)
	{
		return CurrencyId.equals(this.currencyId, currencyId);
	}
}
