/**
 *
 */
package de.metas.manufacturing.acct;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.ProductAcctType;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingDocumentRef;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.acct.DocLine;
import org.compiere.model.MAccount;
import org.compiere.util.DB;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.model.I_PP_Cost_Collector;

/**
 * @author Teo Sarca, www.arhipac.ro
 *
 */
public class DocLine_CostCollector extends DocLine<Doc_PPCostCollector>
{
	public DocLine_CostCollector(
			final I_PP_Cost_Collector cc,
			final Doc_PPCostCollector doc)
	{
		super(InterfaceWrapperHelper.getPO(cc), doc);

		final IPPCostCollectorBL costCollectorBL = Services.get(IPPCostCollectorBL.class);
		final Quantity movementQty = costCollectorBL.getQuantities(cc).getMovementQty();
		setQty(movementQty, false);

		setReversalLine_ID(cc.getReversal_ID());
	}

	public MAccount getAccountForCostElement(
			final AcctSchema as,
			final CostElement costElement)
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
	@NonNull
	public MAccount getAccount(
			@NonNull final ProductAcctType acctType,
			@NonNull final AcctSchema as)
	{
		final ProductId productId = getProductId();
		if (productId == null)
		{
			return super.getAccount(acctType, as);
		}
		else
		{
			final String acctColumnName = acctType.getColumnName();
			final String sql = " SELECT "
					+ " COALESCE(pa." + acctColumnName + ",pca." + acctColumnName + ",asd." + acctColumnName + ")"
					+ " FROM M_Product p"
					+ " INNER JOIN M_Product_Acct pa ON (pa.M_Product_ID=p.M_Product_ID)"
					+ " INNER JOIN M_Product_Category_Acct pca ON (pca.M_Product_Category_ID=p.M_Product_Category_ID AND pca.C_AcctSchema_ID=pa.C_AcctSchema_ID)"
					+ " INNER JOIN C_AcctSchema_Default asd ON (asd.C_AcctSchema_ID=pa.C_AcctSchema_ID)"
					+ " WHERE pa.M_Product_ID=? AND pa.C_AcctSchema_ID=?";
			final AccountId accountId = AccountId.ofRepoIdOrNull(DB.getSQLValueEx(ITrx.TRXNAME_None, sql, productId, as.getId()));
			if (accountId == null)
			{
				throw newPostingException().setAcctSchema(as).setDetailMessage("No Product Account for account type " + acctType + ", product " + productId + " and " + as);
			}

			return services.getAccountById(accountId);
		}
	}

	public AggregatedCostAmount getCreateCosts(final AcctSchema as)
	{
		final AcctSchemaId acctSchemaId = as.getId();

		if (isReversalLine())
		{
			return services.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(acctSchemaId)
					.reversalDocumentRef(CostingDocumentRef.ofCostCollectorId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofCostCollectorId(getReversalLine_ID()))
					.date(getDateAcct())
					.build());
		}
		else
		{
			return services.createCostDetail(
					CostDetailCreateRequest.builder()
							.acctSchemaId(acctSchemaId)
							.clientId(getClientId())
							.orgId(getOrgId())
							.productId(getProductId())
							.attributeSetInstanceId(getAttributeSetInstanceId())
							.documentRef(CostingDocumentRef.ofCostCollectorId(get_ID()))
							.qty(getQty())
							.amt(CostAmount.zero(as.getCurrencyId())) // N/A
							.date(getDateAcct())
							.build());
		}
	}
}
