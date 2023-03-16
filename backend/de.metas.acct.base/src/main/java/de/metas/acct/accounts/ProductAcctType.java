package de.metas.acct.accounts;

import de.metas.util.Check;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product_Acct;

import javax.annotation.Nullable;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@AllArgsConstructor
public enum ProductAcctType
{
	P_Revenue_Acct(I_M_Product_Acct.COLUMNNAME_P_Revenue_Acct),
	P_Expense_Acct(I_M_Product_Acct.COLUMNNAME_P_Expense_Acct),
	P_Asset_Acct(I_M_Product_Acct.COLUMNNAME_P_Asset_Acct),
	P_COGS_Acct(I_M_Product_Acct.COLUMNNAME_P_COGS_Acct),
	P_PurchasePriceVariance_Acct(I_M_Product_Acct.COLUMNNAME_P_PurchasePriceVariance_Acct),
	P_InvoicePriceVariance_Acct(I_M_Product_Acct.COLUMNNAME_P_InvoicePriceVariance_Acct),
	P_TradeDiscountRec_Acct(I_M_Product_Acct.COLUMNNAME_P_TradeDiscountRec_Acct),
	P_TradeDiscountGrant_Acct(I_M_Product_Acct.COLUMNNAME_P_TradeDiscountGrant_Acct),
	P_CostAdjustment_Acct(I_M_Product_Acct.COLUMNNAME_P_CostAdjustment_Acct),
	P_InventoryClearing_Acct(I_M_Product_Acct.COLUMNNAME_P_InventoryClearing_Acct),
	P_WIP_Acct(I_M_Product_Acct.COLUMNNAME_P_WIP_Acct),
	P_MethodChangeVariance_Acct(I_M_Product_Acct.COLUMNNAME_P_MethodChangeVariance_Acct),
	P_UsageVariance_Acct(I_M_Product_Acct.COLUMNNAME_P_UsageVariance_Acct),
	P_RateVariance_Acct(I_M_Product_Acct.COLUMNNAME_P_RateVariance_Acct),
	P_MixVariance_Acct(I_M_Product_Acct.COLUMNNAME_P_MixVariance_Acct),
	P_FloorStock_Acct(I_M_Product_Acct.COLUMNNAME_P_FloorStock_Acct),
	P_CostOfProduction_Acct(I_M_Product_Acct.COLUMNNAME_P_CostOfProduction_Acct),
	P_Labor_Acct(I_M_Product_Acct.COLUMNNAME_P_Labor_Acct),
	P_Burden_Acct(I_M_Product_Acct.COLUMNNAME_P_Burden_Acct),
	P_OutsideProcessing_Acct(I_M_Product_Acct.COLUMNNAME_P_OutsideProcessing_Acct),
	P_Overhead_Acct(I_M_Product_Acct.COLUMNNAME_P_Overhead_Acct),
	P_Scrap_Acct(I_M_Product_Acct.COLUMNNAME_P_Scrap_Acct)

	//
	;

	@Getter @NonNull private final String columnName;

	@Nullable
	public static ProductAcctType ofName(@Nullable final String name)
	{
		if (Check.isBlank(name))
		{
			return null;
		}

		try
		{
			return valueOf(name);
		}
		catch (final Exception exception)
		{
			throw new AdempiereException("No " + ProductAcctType.class + " found for name: " + name);
		}
	}
}
