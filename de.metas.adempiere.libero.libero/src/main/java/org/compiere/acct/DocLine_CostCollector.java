/**
 *
 */
package org.compiere.acct;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.acct.api.ProductAcctType;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementType;
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
		final CostElementType costElementType = element.getCostElementType();
		final ProductAcctType acctType;
		if (CostElementType.Material.equals(costElementType))
		{
			acctType = ProductAcctType.Asset;
		}
		else if (CostElementType.Resource.equals(costElementType))
		{
			acctType = ProductAcctType.Labor;
		}
		else if (CostElementType.BurdenMOverhead.equals(costElementType))
		{
			acctType = ProductAcctType.Burden;
		}
		else if (CostElementType.Overhead.equals(costElementType))
		{
			acctType = ProductAcctType.Overhead;
		}
		else if (CostElementType.OutsideProcessing.equals(costElementType))
		{
			acctType = ProductAcctType.OutsideProcessing;
		}
		else
		{
			throw new LiberoException("@NotSupported@ " + element);
		}
		return getAccount(acctType, as);
	}

	@Override
	public MAccount getAccount(final ProductAcctType AcctType, final I_C_AcctSchema as)
	{
		final String acctName = AcctType == null ? null : AcctType.getColumnName();
		if (getM_Product_ID() <= 0 || acctName == null)
		{
			return super.getAccount(AcctType, as);
		}
		return getAccount(acctName, as);
	}

	public MAccount getAccount(final String acctName, final I_C_AcctSchema as)
	{
		final String sql = " SELECT "
				+ " COALESCE(pa." + acctName + ",pca." + acctName + ",asd." + acctName + ")"
				+ " FROM M_Product p"
				+ " INNER JOIN M_Product_Acct pa ON (pa.M_Product_ID=p.M_Product_ID)"
				+ " INNER JOIN M_Product_Category_Acct pca ON (pca.M_Product_Category_ID=p.M_Product_Category_ID AND pca.C_AcctSchema_ID=pa.C_AcctSchema_ID)"
				+ " INNER JOIN C_AcctSchema_Default asd ON (asd.C_AcctSchema_ID=pa.C_AcctSchema_ID)"
				+ " WHERE pa.M_Product_ID=? AND pa.C_AcctSchema_ID=?";
		final int validCombination_ID = DB.getSQLValueEx(ITrx.TRXNAME_None, sql, getM_Product_ID(), as.getC_AcctSchema_ID());
		if (validCombination_ID <= 0)
		{
			return null;
		}
		return MAccount.get(getCtx(), validCombination_ID);
	}

}
