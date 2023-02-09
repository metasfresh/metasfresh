/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.process;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_M_Product_Acct;
import org.compiere.util.DB;

import de.metas.acct.api.AcctSchemaId;
import de.metas.cache.CacheMgt;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.product.ProductCategoryId;

/**
 * Copy Product Catergory Default Accounts
 * 
 * @author Jorg Janke
 * @version $Id: ProductCategoryAcctCopy.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class ProductCategoryAcctCopy extends JavaProcess
{
	@Param(parameterName = "M_Product_Category_ID", mandatory = true)
	private ProductCategoryId fromProductCategoryId;

	@Param(parameterName = "C_AcctSchema_ID", mandatory = true)
	private AcctSchemaId fromAcctSchemaId;

	@Param(parameterName = "M_Product_Category_To_ID", mandatory = true)
	private ProductCategoryId toProductCategoryId;

	@Param(parameterName = "C_AcctSchema_To_ID", mandatory = true)
	private AcctSchemaId toAcctSchemaId;

	@Override
	protected String doIt()
	{
		//
		// Update
		int countUpdated;
		{
			final String sql = DB.convertSqlToNative("UPDATE M_Product_Acct pa "
					//
					+ "SET (P_Revenue_Acct,P_Expense_Acct,P_CostAdjustment_Acct,P_InventoryClearing_Acct,P_Asset_Acct,P_COGS_Acct,"
					+ " P_PurchasePriceVariance_Acct,P_InvoicePriceVariance_Acct,"
					+ " P_TradeDiscountRec_Acct,P_TradeDiscountGrant_Acct,"
					+ " P_WIP_Acct,P_FloorStock_Acct,P_MethodChangeVariance_Acct,P_UsageVariance_Acct,P_RateVariance_Acct,"
					+ " P_MixVariance_Acct,P_Labor_Acct,P_Burden_Acct,P_CostOfProduction_Acct,P_OutsideProcessing_Acct,P_Overhead_Acct,P_Scrap_Acct)="
					//
					+ " (SELECT P_Revenue_Acct,P_Expense_Acct,P_CostAdjustment_Acct,P_InventoryClearing_Acct,P_Asset_Acct,P_COGS_Acct,"
					+ " P_PurchasePriceVariance_Acct,P_InvoicePriceVariance_Acct,"
					+ " P_TradeDiscountRec_Acct,P_TradeDiscountGrant_Acct,"
					+ " P_WIP_Acct,P_FloorStock_Acct,P_MethodChangeVariance_Acct,P_UsageVariance_Acct,P_RateVariance_Acct,"
					+ " P_MixVariance_Acct,P_Labor_Acct,P_Burden_Acct,P_CostOfProduction_Acct,P_OutsideProcessing_Acct,P_Overhead_Acct,P_Scrap_Acct"
					+ " FROM M_Product_Category_Acct pca"
					+ " WHERE pca.M_Product_Category_ID=" + fromProductCategoryId.getRepoId()
					+ " AND pca.C_AcctSchema_ID=" + fromAcctSchemaId.getRepoId()
					+ "), Updated=now(), UpdatedBy=0 "
					//
					+ "WHERE pa.C_AcctSchema_ID=" + toAcctSchemaId.getRepoId()
					+ " AND EXISTS (SELECT 1 FROM M_Product p WHERE p.M_Product_ID=pa.M_Product_ID AND p.M_Product_Category_ID=" + toProductCategoryId.getRepoId() + ")");
			countUpdated = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
			addLog(0, null, BigDecimal.valueOf(countUpdated), "@Updated@");
		}

		//
		// Insert new Products
		int countCreated;
		{
			final String sql = "INSERT INTO M_Product_Acct "
					+ "(M_Product_ID,"
					+ " C_AcctSchema_ID,"
					+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
					+ " P_Revenue_Acct, P_Expense_Acct, P_CostAdjustment_Acct, P_InventoryClearing_Acct, P_Asset_Acct, P_CoGs_Acct,"
					+ " P_PurchasePriceVariance_Acct, P_InvoicePriceVariance_Acct,"
					+ " P_TradeDiscountRec_Acct, P_TradeDiscountGrant_Acct, "
					+ " P_WIP_Acct,P_FloorStock_Acct, P_MethodChangeVariance_Acct, P_UsageVariance_Acct, P_RateVariance_Acct,"
					+ " P_MixVariance_Acct, P_Labor_Acct, P_Burden_Acct, P_CostOfProduction_Acct, P_OutsideProcessing_Acct, P_Overhead_Acct, P_Scrap_Acct) "
					//
					+ " SELECT "
					+ " p.M_Product_ID,"
					+ toAcctSchemaId.getRepoId() + " AS C_AcctSchema_ID,"
					+ " p.AD_Client_ID, p.AD_Org_ID, 'Y', now(), 0, now(), 0,"
					+ " acct.P_Revenue_Acct, acct.P_Expense_Acct, acct.P_CostAdjustment_Acct, acct.P_InventoryClearing_Acct, acct.P_Asset_Acct, acct.P_CoGs_Acct,"
					+ " acct.P_PurchasePriceVariance_Acct, acct.P_InvoicePriceVariance_Acct,"
					+ " acct.P_TradeDiscountRec_Acct, acct.P_TradeDiscountGrant_Acct, "
					+ " acct.P_WIP_Acct, acct.P_FloorStock_Acct, acct.P_MethodChangeVariance_Acct, acct.P_UsageVariance_Acct, acct.P_RateVariance_Acct,"
					+ " acct.P_MixVariance_Acct, acct.P_Labor_Acct, acct.P_Burden_Acct, acct.P_CostOfProduction_Acct, acct.P_OutsideProcessing_Acct, acct.P_Overhead_Acct, acct.P_Scrap_Acct "
					+ " FROM M_Product p"
					+ " INNER JOIN M_Product_Category_Acct acct ON (acct.M_Product_Category_ID=" + fromProductCategoryId.getRepoId() + " AND acct.C_AcctSchema_ID=" + fromAcctSchemaId.getRepoId() + ")"
					+ " WHERE 1=1"
					+ " AND p.M_Product_Category_ID=" + toProductCategoryId.getRepoId()
					+ " AND NOT EXISTS (SELECT 1 FROM M_Product_Acct pa WHERE pa.M_Product_ID=p.M_Product_ID AND pa.C_AcctSchema_ID=" + toAcctSchemaId.getRepoId() + ")";
			countCreated = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
			addLog(0, null, BigDecimal.valueOf(countCreated), "@Created@");
		}

		return "@Created@=" + countCreated + ", @Updated@=" + countUpdated;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (success)
		{
			CacheMgt.get().reset(I_M_Product_Acct.Table_Name);
		}
	}
}
