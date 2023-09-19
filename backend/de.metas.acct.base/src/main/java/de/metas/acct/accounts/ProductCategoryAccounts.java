package de.metas.acct.accounts;

import de.metas.acct.Account;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.product.ProductCategoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

	@NonNull Account P_Revenue_Acct;
	@NonNull Account P_Expense_Acct;
	@NonNull Account P_Asset_Acct;
	@NonNull Account P_COGS_Acct;
	@NonNull Account P_PurchasePriceVariance_Acct;
	@NonNull Account P_InvoicePriceVariance_Acct;
	@NonNull Account P_TradeDiscountRec_Acct;
	@NonNull Account P_TradeDiscountGrant_Acct;
	@NonNull Account P_CostAdjustment_Acct;
	@NonNull Account P_InventoryClearing_Acct;
	@NonNull Account P_WIP_Acct;
	@NonNull Account P_MethodChangeVariance_Acct;
	@NonNull Account P_UsageVariance_Acct;
	@NonNull Account P_RateVariance_Acct;
	@NonNull Account P_MixVariance_Acct;
	@NonNull Account P_FloorStock_Acct;
	@NonNull Account P_CostOfProduction_Acct;
	@NonNull Account P_Labor_Acct;
	@NonNull Account P_Burden_Acct;
	@NonNull Account P_OutsideProcessing_Acct;
	@NonNull Account P_Overhead_Acct;
	@NonNull Account P_Scrap_Acct;
	@NonNull Account P_ExternallyOwnedStock_Acct;

	@NonNull
	public Account getAccount(@NonNull final ProductAcctType acctType)
	{
		return switch (acctType)
		{
			case P_Revenue_Acct -> P_Revenue_Acct;
			case P_Expense_Acct -> P_Expense_Acct;
			case P_Asset_Acct -> P_Asset_Acct;
			case P_COGS_Acct -> P_COGS_Acct;
			case P_PurchasePriceVariance_Acct -> P_PurchasePriceVariance_Acct;
			case P_InvoicePriceVariance_Acct -> P_InvoicePriceVariance_Acct;
			case P_TradeDiscountRec_Acct -> P_TradeDiscountRec_Acct;
			case P_TradeDiscountGrant_Acct -> P_TradeDiscountGrant_Acct;
			case P_CostAdjustment_Acct -> P_CostAdjustment_Acct;
			case P_InventoryClearing_Acct -> P_InventoryClearing_Acct;
			case P_WIP_Acct -> P_WIP_Acct;
			case P_MethodChangeVariance_Acct -> P_MethodChangeVariance_Acct;
			case P_UsageVariance_Acct -> P_UsageVariance_Acct;
			case P_RateVariance_Acct -> P_RateVariance_Acct;
			case P_MixVariance_Acct -> P_MixVariance_Acct;
			case P_FloorStock_Acct -> P_FloorStock_Acct;
			case P_CostOfProduction_Acct -> P_CostOfProduction_Acct;
			case P_Labor_Acct -> P_Labor_Acct;
			case P_Burden_Acct -> P_Burden_Acct;
			case P_OutsideProcessing_Acct -> P_OutsideProcessing_Acct;
			case P_Overhead_Acct -> P_Overhead_Acct;
			case P_Scrap_Acct -> P_Scrap_Acct;
			case P_ExternallyOwnedStock_Acct -> P_ExternallyOwnedStock_Acct;
		};
	}
}
