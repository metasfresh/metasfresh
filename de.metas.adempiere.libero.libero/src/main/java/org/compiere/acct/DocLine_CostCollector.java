/**
 *
 */
package org.compiere.acct;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.MAccount;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.acct.api.ProductAcctType;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementType;
import de.metas.costing.CostResult;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostingService;
import de.metas.quantity.Quantity;

/**
 * @author Teo Sarca, www.arhipac.ro
 *
 */
public class DocLine_CostCollector extends DocLine<Doc_PPCostCollector>
{
	public DocLine_CostCollector(final I_PP_Cost_Collector cc, final Doc_PPCostCollector doc)
	{
		super(InterfaceWrapperHelper.getPO(cc), doc);

		setQty(Quantity.of(cc.getMovementQty(), getProductStockingUOM()), false);
		setReversalLine_ID(cc.getReversal_ID());
	}

	public MAccount getAccountForCostElement(final I_C_AcctSchema as, final CostElement costElement)
	{
		final ProductAcctType acctType = getProductAcctTypeByCostElement(costElement);
		return getAccount(acctType, as);
	}

	private ProductAcctType getProductAcctTypeByCostElement(final CostElement costElement)
	{
		final CostElementType costElementType = costElement.getCostElementType();
		if (CostElementType.Material.equals(costElementType))
		{
			return ProductAcctType.Asset;
		}
		else if (CostElementType.Resource.equals(costElementType))
		{
			return ProductAcctType.Labor;
		}
		else if (CostElementType.BurdenMOverhead.equals(costElementType))
		{
			return ProductAcctType.Burden;
		}
		else if (CostElementType.Overhead.equals(costElementType))
		{
			return ProductAcctType.Overhead;
		}
		else if (CostElementType.OutsideProcessing.equals(costElementType))
		{
			return ProductAcctType.OutsideProcessing;
		}
		else
		{
			throw newPostingException().setDetailMessage("@NotSupported@ " + costElement);
		}
	}

	@Override
	public MAccount getAccount(final ProductAcctType acctType, final I_C_AcctSchema as)
	{
		final int productId = getM_Product_ID();
		if (productId <= 0)
		{
			return super.getAccount(acctType, as);
		}

		final String acctColumnName = acctType.getColumnName();
		final String sql = " SELECT "
				+ " COALESCE(pa." + acctColumnName + ",pca." + acctColumnName + ",asd." + acctColumnName + ")"
				+ " FROM M_Product p"
				+ " INNER JOIN M_Product_Acct pa ON (pa.M_Product_ID=p.M_Product_ID)"
				+ " INNER JOIN M_Product_Category_Acct pca ON (pca.M_Product_Category_ID=p.M_Product_Category_ID AND pca.C_AcctSchema_ID=pa.C_AcctSchema_ID)"
				+ " INNER JOIN C_AcctSchema_Default asd ON (asd.C_AcctSchema_ID=pa.C_AcctSchema_ID)"
				+ " WHERE pa.M_Product_ID=? AND pa.C_AcctSchema_ID=?";
		final int validCombinationId = DB.getSQLValueEx(ITrx.TRXNAME_None, sql, productId, as.getC_AcctSchema_ID());
		if (validCombinationId <= 0)
		{
			return null;
		}

		return MAccount.get(getCtx(), validCombinationId);
	}

	public CostResult getCreateCosts(final I_C_AcctSchema as)
	{
		final ICostingService costDetailService = Adempiere.getBean(ICostingService.class);

		if (isReversalLine())
		{
			return costDetailService.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(as.getC_AcctSchema_ID())
					.reversalDocumentRef(CostingDocumentRef.ofCostCollectorId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofCostCollectorId(getReversalLine_ID()))
					.date(TimeUtil.asLocalDate(getDateDoc()))
					.build());
		}
		else
		{
			return costDetailService.createCostDetail(
					CostDetailCreateRequest.builder()
							.acctSchemaId(as.getC_AcctSchema_ID())
							.clientId(getAD_Client_ID())
							.orgId(getAD_Org_ID())
							.productId(getM_Product_ID())
							.attributeSetInstanceId(getM_AttributeSetInstance_ID())
							.documentRef(CostingDocumentRef.ofCostCollectorId(get_ID()))
							.qty(getQty())
							.amt(CostAmount.zero(as.getC_Currency_ID())) // N/A
							.date(TimeUtil.asLocalDate(getDateDoc()))
							.build());
		}
	}
}
