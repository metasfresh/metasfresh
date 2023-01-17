package de.metas.acct.accounts;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.product.ProductCategoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2020 metas GmbH
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
public class ProductCategoryAccounts
{
	@NonNull ProductCategoryId productCategoryId;
	@NonNull AcctSchemaId acctSchemaId;

	@Nullable CostingLevel costingLevel;
	@Nullable CostingMethod costingMethod;

	@NonNull AccountId P_Revenue_Acct;
	@NonNull AccountId P_Expense_Acct;
	@NonNull AccountId P_Asset_Acct;
	@NonNull AccountId P_COGS_Acct;
	@NonNull AccountId P_PurchasePriceVariance_Acct;
	@NonNull AccountId P_InvoicePriceVariance_Acct;
	@NonNull AccountId P_TradeDiscountRec_Acct;
	@NonNull AccountId P_TradeDiscountGrant_Acct;
	@NonNull AccountId P_CostAdjustment_Acct;
	@NonNull AccountId P_InventoryClearing_Acct;
	@NonNull AccountId P_WIP_Acct;
	@NonNull AccountId P_MethodChangeVariance_Acct;
	@NonNull AccountId P_UsageVariance_Acct;
	@NonNull AccountId P_RateVariance_Acct;
	@NonNull AccountId P_MixVariance_Acct;
	@NonNull AccountId P_FloorStock_Acct;
	@NonNull AccountId P_CostOfProduction_Acct;
	@NonNull AccountId P_Labor_Acct;
	@NonNull AccountId P_Burden_Acct;
	@NonNull AccountId P_OutsideProcessing_Acct;
	@NonNull AccountId P_Overhead_Acct;
	@NonNull AccountId P_Scrap_Acct;

	public AccountId getAccountId(@NonNull final ProductAcctType acctType)
	{
		switch (acctType)
		{
			case P_Revenue_Acct:
				return P_Revenue_Acct;
			case P_Expense_Acct:
				return P_Expense_Acct;
			case P_Asset_Acct:
				return P_Asset_Acct;
			case P_COGS_Acct:
				return P_COGS_Acct;
			case P_PurchasePriceVariance_Acct:
				return P_PurchasePriceVariance_Acct;
			case P_InvoicePriceVariance_Acct:
				return P_InvoicePriceVariance_Acct;
			case P_TradeDiscountRec_Acct:
				return P_TradeDiscountRec_Acct;
			case P_TradeDiscountGrant_Acct:
				return P_TradeDiscountGrant_Acct;
			case P_CostAdjustment_Acct:
				return P_CostAdjustment_Acct;
			case P_InventoryClearing_Acct:
				return P_InventoryClearing_Acct;
			case P_WIP_Acct:
				return P_WIP_Acct;
			case P_MethodChangeVariance_Acct:
				return P_MethodChangeVariance_Acct;
			case P_UsageVariance_Acct:
				return P_UsageVariance_Acct;
			case P_RateVariance_Acct:
				return P_RateVariance_Acct;
			case P_MixVariance_Acct:
				return P_MixVariance_Acct;
			case P_FloorStock_Acct:
				return P_FloorStock_Acct;
			case P_CostOfProduction_Acct:
				return P_CostOfProduction_Acct;
			case P_Labor_Acct:
				return P_Labor_Acct;
			case P_Burden_Acct:
				return P_Burden_Acct;
			case P_OutsideProcessing_Acct:
				return P_OutsideProcessing_Acct;
			case P_Overhead_Acct:
				return P_Overhead_Acct;
			case P_Scrap_Acct:
				return P_Scrap_Acct;
			default:
				throw new AdempiereException("Unknown account type: " + acctType);
		}
	}
}
