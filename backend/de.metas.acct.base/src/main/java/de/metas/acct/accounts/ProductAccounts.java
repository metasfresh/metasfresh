package de.metas.acct.accounts;

import de.metas.acct.api.AcctSchemaId;
import de.metas.product.acct.api.ActivityId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import de.metas.acct.Account;

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
