package org.adempiere.acct.api;

import java.util.Map;

import org.compiere.model.I_M_Product_Acct;

import com.google.common.collect.ImmutableMap;

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

/**
 * Product Account Type
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public enum ProductAcctType
{
	/** Product Revenue account */
	Revenue(1, I_M_Product_Acct.COLUMNNAME_P_Revenue_Acct),
	/** Product Expense account */
	Expense(2, I_M_Product_Acct.COLUMNNAME_P_Expense_Acct),
	/** Product Asset account */
	Asset(3, I_M_Product_Acct.COLUMNNAME_P_Asset_Acct),
	/** Product COGS account */
	Cogs(4, I_M_Product_Acct.COLUMNNAME_P_COGS_Acct),
	/** Purchase Price Variance */
	PPV(5, I_M_Product_Acct.COLUMNNAME_P_PurchasePriceVariance_Acct),
	/** Invoice Price Variance */
	IPV(6, I_M_Product_Acct.COLUMNNAME_P_InvoicePriceVariance_Acct),
	/** Trade Discount Revenue */
	TDiscountRec(7, I_M_Product_Acct.COLUMNNAME_P_TradeDiscountRec_Acct),
	/** Trade Discount Costs */
	TDiscountGrant(8, I_M_Product_Acct.COLUMNNAME_P_TradeDiscountGrant_Acct),
	/** Cost Adjustment */
	CostAdjustment(9, I_M_Product_Acct.COLUMNNAME_P_CostAdjustment_Acct),
	/** Inventory Clearing */
	InventoryClearing(10, I_M_Product_Acct.COLUMNNAME_P_InventoryClearing_Acct),
	/** Work in Process */
	WorkInProcess(11, I_M_Product_Acct.COLUMNNAME_P_WIP_Acct),
	/** Method Change Variance */
	MethodChangeVariance(12, I_M_Product_Acct.COLUMNNAME_P_MethodChangeVariance_Acct),
	/** Material Usage Variance */
	UsageVariance(13, I_M_Product_Acct.COLUMNNAME_P_UsageVariance_Acct),
	/** Material Rate Variance */
	RateVariance(14, I_M_Product_Acct.COLUMNNAME_P_RateVariance_Acct),
	/** Mix Variance */
	MixVariance(15, I_M_Product_Acct.COLUMNNAME_P_MixVariance_Acct),
	/** Floor Stock */
	FloorStock(16, I_M_Product_Acct.COLUMNNAME_P_FloorStock_Acct),
	/** Cost Production */
	CostOfProduction(17, I_M_Product_Acct.COLUMNNAME_P_CostOfProduction_Acct),
	/** Labor */
	Labor(18, I_M_Product_Acct.COLUMNNAME_P_Labor_Acct),
	/** Burden */
	Burden(19, I_M_Product_Acct.COLUMNNAME_P_Burden_Acct),
	/** Outside Processing */
	OutsideProcessing(20, I_M_Product_Acct.COLUMNNAME_P_OutsideProcessing_Acct),
	/** Outside Overhead */
	Overhead(21, I_M_Product_Acct.COLUMNNAME_P_Overhead_Acct),
	/** Outside Processing */
	Scrap(22, I_M_Product_Acct.COLUMNNAME_P_Scrap_Acct)

	//
	;

	private static final Map<Integer, ProductAcctType> type2value;
	static
	{
		final ImmutableMap.Builder<Integer, ProductAcctType> type2valueBuilder = ImmutableMap.builder();
		for (ProductAcctType value : values())
		{
			type2valueBuilder.put(value.getType(), value);
		}
		type2value = type2valueBuilder.build();
	}

	private final int type;
	private final String columnName;

	ProductAcctType(final int type, final String columnName)
	{
		this.type = type;
		this.columnName = columnName;
	}

	public int getType()
	{
		return type;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public static final ProductAcctType ofType(final int acctType)
	{
		final ProductAcctType value = type2value.get(acctType);
		if (value == null)
		{
			throw new IllegalArgumentException("No " + ProductAcctType.class + " found for AcctType=" + acctType);
		}
		return value;
	}
}
