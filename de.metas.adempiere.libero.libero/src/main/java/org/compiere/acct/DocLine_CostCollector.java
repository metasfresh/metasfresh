/**
 *
 */
package org.compiere.acct;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import org.adempiere.acct.api.ProductAcctType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.ProductCost;
import org.compiere.model.X_M_CostElement;
import org.compiere.util.DB;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.costing.CostElement;
import de.metas.material.planning.pporder.LiberoException;

/**
 * @author Teo Sarca, www.arhipac.ro
 *
 */
public class DocLine_CostCollector extends DocLine<Doc_PPCostCollector>
{
	public DocLine_CostCollector(final I_PP_Cost_Collector cc, final Doc_PPCostCollector doc)
	{
		super(InterfaceWrapperHelper.getPO(cc), doc);
	}

	public MAccount getAccount(final MAcctSchema as, final CostElement element)
	{
		final String costElementType = element.getCostElementType();
		final ProductAcctType acctType;
		if (X_M_CostElement.COSTELEMENTTYPE_Material.equals(costElementType))
		{
			acctType = ProductCost.ACCTTYPE_P_Asset;
		}
		else if (X_M_CostElement.COSTELEMENTTYPE_Resource.equals(costElementType))
		{
			acctType = ProductCost.ACCTTYPE_P_Labor;
		}
		else if (X_M_CostElement.COSTELEMENTTYPE_BurdenMOverhead.equals(costElementType))
		{
			acctType = ProductCost.ACCTTYPE_P_Burden;
		}
		else if (X_M_CostElement.COSTELEMENTTYPE_Overhead.equals(costElementType))
		{
			acctType = ProductCost.ACCTTYPE_P_Overhead;
		}
		else if (X_M_CostElement.COSTELEMENTTYPE_OutsideProcessing.equals(costElementType))
		{
			acctType = ProductCost.ACCTTYPE_P_OutsideProcessing;
		}
		else
		{
			throw new LiberoException("@NotSupported@ " + element);
		}
		return getAccount(acctType, as);
	}

	@Override
	public MAccount getAccount(final ProductAcctType AcctType, final MAcctSchema as)
	{
		final String acctName = AcctType == null ? null : AcctType.getColumnName();
		if (getM_Product_ID() <= 0 || acctName == null)
		{
			return super.getAccount(AcctType, as);
		}
		return getAccount(acctName, as);
	}

	public MAccount getAccount(final String acctName, final MAcctSchema as)
	{
		final String sql = " SELECT "
				+ " COALESCE(pa." + acctName + ",pca." + acctName + ",asd." + acctName + ")"
				+ " FROM M_Product p"
				+ " INNER JOIN M_Product_Acct pa ON (pa.M_Product_ID=p.M_Product_ID)"
				+ " INNER JOIN M_Product_Category_Acct pca ON (pca.M_Product_Category_ID=p.M_Product_Category_ID AND pca.C_AcctSchema_ID=pa.C_AcctSchema_ID)"
				+ " INNER JOIN C_AcctSchema_Default asd ON (asd.C_AcctSchema_ID=pa.C_AcctSchema_ID)"
				+ " WHERE pa.M_Product_ID=? AND pa.C_AcctSchema_ID=?";
		final int validCombination_ID = DB.getSQLValueEx(null, sql, getM_Product_ID(), as.get_ID());
		if (validCombination_ID <= 0)
		{
			return null;
		}
		return MAccount.get(as.getCtx(), validCombination_ID);
	}

}
