/**
 *
 */
package de.metas.manufacturing.acct;

<<<<<<< HEAD
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.ProductAcctType;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
=======
import de.metas.acct.Account;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResultsList;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingDocumentRef;
<<<<<<< HEAD
=======
import de.metas.i18n.ExplainedOptional;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.acct.DocLine;
<<<<<<< HEAD
import org.compiere.model.MAccount;
import org.compiere.util.DB;
=======
import org.compiere.util.DB;
import org.eevolution.api.CostCollectorType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.model.I_PP_Cost_Collector;

/**
 * @author Teo Sarca, www.arhipac.ro
<<<<<<< HEAD
 *
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 */
public class DocLine_CostCollector extends DocLine<Doc_PPCostCollector>
{
	public DocLine_CostCollector(
			final I_PP_Cost_Collector cc,
			final Doc_PPCostCollector doc)
	{
		super(InterfaceWrapperHelper.getPO(cc), doc);

		final IPPCostCollectorBL costCollectorBL = Services.get(IPPCostCollectorBL.class);
<<<<<<< HEAD
		final Quantity movementQty = costCollectorBL.getQuantities(cc).getMovementQty();
		setQty(movementQty, false);

		setReversalLine_ID(cc.getReversal_ID());
	}

	public MAccount getAccountForCostElement(
=======

		final CostCollectorType costCollectorType = CostCollectorType.ofCode(cc.getCostCollectorType());
		final Quantity movementQty = costCollectorBL.getQuantities(cc).getMovementQty()
				// CO/BY product quantities are negative, so we are negating them here to get a positive "received" qty
				.negateIf(costCollectorType.isCoOrByProductReceipt());

		if (CostCollectorType.ComponentIssue.equals(costCollectorType))
		{
			setQty(movementQty, true); // we can see it as a sales transactions
		}
		else
		{
		setQty(movementQty, false);
		}
		setReversalLine_ID(cc.getReversal_ID());
	}

	public Account getAccountForCostElement(
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
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
=======
			return ProductAcctType.P_Asset_Acct;
		}
		else if (CostElementType.Resource.equals(costElementType))
		{
			return ProductAcctType.P_Labor_Acct;
		}
		else if (CostElementType.BurdenMOverhead.equals(costElementType))
		{
			return ProductAcctType.P_Burden_Acct;
		}
		else if (CostElementType.Overhead.equals(costElementType))
		{
			return ProductAcctType.P_Overhead_Acct;
		}
		else if (CostElementType.OutsideProcessing.equals(costElementType))
		{
			return ProductAcctType.P_OutsideProcessing_Acct;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		else
		{
			throw newPostingException().setDetailMessage("@NotSupported@ " + costElement);
		}
	}

	@Override
	@NonNull
<<<<<<< HEAD
	public MAccount getAccount(
=======
	public Account getAccount(
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
			return services.getAccountById(accountId);
		}
	}

	public AggregatedCostAmount getCreateCosts(final AcctSchema as)
=======
			return Account.of(accountId, acctType.getAccountConceptualName());
		}
	}

	public ExplainedOptional<AggregatedCostAmount> getCreateCosts(final AcctSchema as)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final AcctSchemaId acctSchemaId = as.getId();

		if (isReversalLine())
		{
<<<<<<< HEAD
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
=======
			return services.createReversalCostDetailsOrEmpty(CostDetailReverseRequest.builder()
					.acctSchemaId(acctSchemaId)
					.reversalDocumentRef(CostingDocumentRef.ofCostCollectorId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofCostCollectorId(getReversalLine_ID()))
									.date(getDateAcctAsInstant())
									.build())
					.map(CostDetailCreateResultsList::toAggregatedCostAmount);
		}
		else
		{
			return services.createCostDetailOrEmpty(
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					CostDetailCreateRequest.builder()
							.acctSchemaId(acctSchemaId)
							.clientId(getClientId())
							.orgId(getOrgId())
							.productId(getProductId())
							.attributeSetInstanceId(getAttributeSetInstanceId())
							.documentRef(CostingDocumentRef.ofCostCollectorId(get_ID()))
							.qty(getQty())
							.amt(CostAmount.zero(as.getCurrencyId())) // N/A
<<<<<<< HEAD
							.date(getDateAcct())
							.build());
=======
									.date(getDateAcctAsInstant())
									.build())
					.map(CostDetailCreateResultsList::toAggregatedCostAmount);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}
}
