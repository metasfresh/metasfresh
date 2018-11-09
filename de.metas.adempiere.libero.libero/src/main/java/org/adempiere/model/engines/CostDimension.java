package org.adempiere.model.engines;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_Product;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostElementId;
import de.metas.costing.CostSegment;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.IProductCostingBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Immutable Cost Dimension
 *
 * @author Teo Sarca, www.arhipac.ro
 */
public final class CostDimension
{
	private static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
	private static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
	private static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
	private static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
	private static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";
	private static final String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";
	private static final String COLUMNNAME_M_CostType_ID = "M_CostType_ID";

	private final ClientId clientId;
	private OrgId orgId;
	private final ProductId productId;
	private ResourceId resourceId;
	private AttributeSetInstanceId attributeSetInstanceId;
	private final CostTypeId costTypeId;
	private final AcctSchemaId acctSchemaId;
	private final CostElementId costElementId;

	public CostDimension(
			final I_M_Product product,
			final AcctSchema as,
			final CostTypeId costTypeId,
			final OrgId orgId,
			final AttributeSetInstanceId attributeSetInstanceId,
			final CostElementId costElementId)
	{
		this.clientId = as.getClientId();
		this.orgId = orgId != null ? orgId : OrgId.ANY;
		this.productId = product != null ? ProductId.ofRepoId(product.getM_Product_ID()) : null;
		this.attributeSetInstanceId = attributeSetInstanceId != null ? attributeSetInstanceId : AttributeSetInstanceId.NONE;
		this.costTypeId = costTypeId;
		this.acctSchemaId = as.getId();
		this.costElementId = costElementId;
		updateForProduct(product, as);
	}

	public CostDimension(
			int clientId,
			int orgId,
			int productId,
			int attributeSetInstanceId,
			int costTypeId,
			int acctSchemaId,
			int costElementId)
	{
		this.clientId = ClientId.ofRepoId(clientId);
		this.orgId = OrgId.ofRepoId(orgId);
		this.productId = ProductId.ofRepoId(productId);
		this.attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNone(attributeSetInstanceId);
		this.costTypeId = CostTypeId.ofRepoIdOrNull(costTypeId);
		this.acctSchemaId = AcctSchemaId.ofRepoId(acctSchemaId);
		this.costElementId = CostElementId.ofRepoIdOrNull(costElementId);
		//
		updateForProduct(null, null);
	}

	/**
	 * Copy Constructor
	 *
	 * @param costDimension a <code>CostDimension</code> object
	 */
	public CostDimension(final CostDimension costDimension)
	{
		this.clientId = costDimension.clientId;
		this.orgId = costDimension.orgId;
		this.productId = costDimension.productId;
		this.attributeSetInstanceId = costDimension.attributeSetInstanceId;
		this.costTypeId = costDimension.costTypeId;
		this.acctSchemaId = costDimension.acctSchemaId;
		this.costElementId = costDimension.costElementId;
	}

	private void updateForProduct(I_M_Product product, AcctSchema as)
	{
		if (product == null)
		{
			product = Services.get(IProductDAO.class).getById(productId);
		}
		if (product == null)
		{
			// incomplete specified dimension [SKIP]
			return;
		}
		if (as == null)
		{
			as = Services.get(IAcctSchemaDAO.class).getById(this.acctSchemaId);
		}
		final CostingLevel costingLevel = Services.get(IProductCostingBL.class).getCostingLevel(product, as);
		if (CostingLevel.Client.equals(costingLevel))
		{
			orgId = OrgId.ANY;
			attributeSetInstanceId = AttributeSetInstanceId.NONE;
		}
		else if (CostingLevel.Organization.equals(costingLevel))
		{
			attributeSetInstanceId = AttributeSetInstanceId.NONE;
		}
		else if (CostingLevel.BatchLot.equals(costingLevel))
		{
			orgId = OrgId.ANY;
		}

		this.resourceId = ResourceId.ofRepoIdOrNull(product.getS_Resource_ID());
	}

	public ClientId getClientId()
	{
		return clientId;
	}

	public OrgId getOrgId()
	{
		return orgId;
	}

	public ProductId getProductId()
	{
		return productId;
	}

	public ResourceId getResourceId()
	{
		return resourceId;
	}

	public AttributeSetInstanceId getAttributeSetInstanceId()
	{
		return attributeSetInstanceId;
	}

	public CostTypeId getCostTypeId()
	{
		return costTypeId;
	}

	/**
	 * @return the c_AcctSchema_ID
	 */
	public AcctSchemaId getAcctSchemaId()
	{
		return acctSchemaId;
	}

	public CostElementId getCostElementId()
	{
		return costElementId;
	}

	public <T> IQuery<T> toQuery(Class<T> clazz, String trxName)
	{
		return toQueryBuilder(clazz, trxName)
				.create();
	}

	public <T> IQuery<T> toQuery(final Class<T> clazz, final String whereClause, final Object[] params, final String trxName)
	{
		final IQueryBuilder<T> queryBuilder = toQueryBuilder(clazz, trxName);

		if (!Check.isEmpty(whereClause, true))
		{
			final IQueryFilter<T> sqlFilter = TypedSqlQueryFilter.of(whereClause, params);
			queryBuilder.filter(sqlFilter);
		}

		return queryBuilder.create();
	}

	public <T> IQueryBuilder<T> toQueryBuilder(final Class<T> clazz, final String trxName)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(clazz);
		final POInfo poInfo = POInfo.getPOInfo(tableName);

		final IQueryBuilder<T> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(clazz, Env.getCtx(), trxName);

		queryBuilder.addEqualsFilter(COLUMNNAME_AD_Client_ID, this.clientId);
		queryBuilder.addEqualsFilter(COLUMNNAME_M_Product_ID, this.productId);
		queryBuilder.addEqualsFilter(COLUMNNAME_M_AttributeSetInstance_ID, attributeSetInstanceId);
		queryBuilder.addEqualsFilter(COLUMNNAME_C_AcctSchema_ID, this.acctSchemaId);

		//
		// Filter by organization, only if it's set and we are querying the M_Cost table.
		// Else, you can get no results.
		// e.g. querying PP_Order_Cost, have this.AD_Org_ID=0 but PP_Order_Costs are created using PP_Order's AD_Org_ID
		// see http://dewiki908/mediawiki/index.php/07741_Rate_Variance_%28103334042334%29.
		if (!this.orgId.isAny() || I_M_Cost.Table_Name.equals(tableName))
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_AD_Org_ID, orgId);
		}

		if (this.costElementId != null)
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_M_CostElement_ID, costElementId);
		}

		if (this.costTypeId != null && poInfo.hasColumnName(COLUMNNAME_M_CostType_ID))
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_M_CostType_ID, this.costTypeId);
		}

		return queryBuilder;
	}

	public CostSegment toCostSegment()
	{
		final AcctSchema as = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);
		return CostSegment.builder()
				.clientId(clientId)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(attributeSetInstanceId)
				.costTypeId(costTypeId)
				.acctSchemaId(acctSchemaId)
				.costingLevel(as.getCosting().getCostingLevel())
				.build();
	}

	@Override
	protected Object clone()
	{
		return new CostDimension(this);
	}

	@Override
	public String toString()
	{
		final String TAB = ";";
		return "CostDimension["
				+ "AD_Client_ID = " + this.clientId + TAB
				+ "AD_Org_ID = " + this.orgId + TAB
				+ "M_Product_ID = " + this.productId + TAB
				+ "M_AttributeSetInstance_ID = " + this.attributeSetInstanceId + TAB
				+ "M_CostType_ID = " + this.costTypeId + TAB
				+ "C_AcctSchema_ID = " + this.acctSchemaId + TAB
				+ "M_CostElement_ID = " + this.costElementId + TAB
				+ "]";
	}
}
