package de.metas.acct.accounts;

import de.metas.acct.Account;
import de.metas.acct.api.AcctSchemaId;
import de.metas.product.acct.api.ActivityId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class ProductAccounts
{
	@NonNull AcctSchemaId acctSchemaId;

	@NonNull Optional<ActivityId> activityId;

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

	@NonNull
	public Account getAccount(@NonNull final ProductAcctType acctType)
	{
		final Account account;
		switch (acctType)
		{
			case P_Revenue_Acct:
				account = P_Revenue_Acct;
				break;
			case P_Expense_Acct:
				account = P_Expense_Acct;
				break;
			case P_Asset_Acct:
				account = P_Asset_Acct;
				break;
			case P_COGS_Acct:
				account = P_COGS_Acct;
				break;
			case P_PurchasePriceVariance_Acct:
				account = P_PurchasePriceVariance_Acct;
				break;
			case P_InvoicePriceVariance_Acct:
				account = P_InvoicePriceVariance_Acct;
				break;
			case P_TradeDiscountRec_Acct:
				account = P_TradeDiscountRec_Acct;
				break;
			case P_TradeDiscountGrant_Acct:
				account = P_TradeDiscountGrant_Acct;
				break;
			case P_CostAdjustment_Acct:
				account = P_CostAdjustment_Acct;
				break;
			case P_InventoryClearing_Acct:
				account = P_InventoryClearing_Acct;
				break;
			case P_WIP_Acct:
				account = P_WIP_Acct;
				break;
			case P_MethodChangeVariance_Acct:
				account = P_MethodChangeVariance_Acct;
				break;
			case P_UsageVariance_Acct:
				account = P_UsageVariance_Acct;
				break;
			case P_RateVariance_Acct:
				account = P_RateVariance_Acct;
				break;
			case P_MixVariance_Acct:
				account = P_MixVariance_Acct;
				break;
			case P_FloorStock_Acct:
				account = P_FloorStock_Acct;
				break;
			case P_CostOfProduction_Acct:
				account = P_CostOfProduction_Acct;
				break;
			case P_Labor_Acct:
				account = P_Labor_Acct;
				break;
			case P_Burden_Acct:
				account = P_Burden_Acct;
				break;
			case P_OutsideProcessing_Acct:
				account = P_OutsideProcessing_Acct;
				break;
			case P_Overhead_Acct:
				account = P_Overhead_Acct;
				break;
			case P_Scrap_Acct:
				account = P_Scrap_Acct;
				break;
			default:
				throw new IllegalArgumentException();
		}
		return account;
	}
}
