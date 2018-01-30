package org.adempiere.model.engines;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MProduct;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

import de.metas.costing.CostingLevel;
import de.metas.product.IProductBL;

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

	public static final int ANY = -10;

	private final int AD_Client_ID;
	private int AD_Org_ID;
	private int M_Product_ID;
	private int S_Resource_ID;
	private int M_AttributeSetInstance_ID;
	private final int M_CostType_ID;
	private final int C_AcctSchema_ID;
	private final int M_CostElement_ID;

	public CostDimension(
			I_M_Product product,
			I_C_AcctSchema as,
			int M_CostType_ID,
			int AD_Org_ID,
			int M_ASI_ID,
			int M_CostElement_ID)
	{
		this.AD_Client_ID = as.getAD_Client_ID();
		this.AD_Org_ID = AD_Org_ID;
		this.M_Product_ID = product != null ? product.getM_Product_ID() : ANY;
		this.M_AttributeSetInstance_ID = M_ASI_ID;
		this.M_CostType_ID = M_CostType_ID;
		this.C_AcctSchema_ID = as.getC_AcctSchema_ID();
		this.M_CostElement_ID = M_CostElement_ID;
		updateForProduct(product, as);
	}

	public CostDimension(
			int client_ID,
			int org_ID,
			int product_ID,
			int attributeSetInstance_ID,
			int costType_ID,
			int acctSchema_ID,
			int costElement_ID)
	{
		this.AD_Client_ID = client_ID;
		this.AD_Org_ID = org_ID;
		this.M_Product_ID = product_ID;
		this.M_AttributeSetInstance_ID = attributeSetInstance_ID;
		this.M_CostType_ID = costType_ID;
		this.C_AcctSchema_ID = acctSchema_ID;
		this.M_CostElement_ID = costElement_ID;
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
		this.AD_Client_ID = costDimension.AD_Client_ID;
		this.AD_Org_ID = costDimension.AD_Org_ID;
		this.M_Product_ID = costDimension.M_Product_ID;
		this.M_AttributeSetInstance_ID = costDimension.M_AttributeSetInstance_ID;
		this.M_CostType_ID = costDimension.M_CostType_ID;
		this.C_AcctSchema_ID = costDimension.C_AcctSchema_ID;
		this.M_CostElement_ID = costDimension.M_CostElement_ID;
	}

	private Properties getCtx()
	{
		return Env.getCtx(); // TODO
	}

	private void updateForProduct(I_M_Product product, I_C_AcctSchema as)
	{
		if (product == null)
		{
			product = MProduct.get(getCtx(), this.M_Product_ID);
		}
		if (product == null)
		{
			// incomplete specified dimension [SKIP]
			return;
		}
		if (as == null)
		{
			as = MAcctSchema.get(getCtx(), this.C_AcctSchema_ID);
		}
		final CostingLevel costingLevel = Services.get(IProductBL.class).getCostingLevel(product, as);
		if (CostingLevel.Client.equals(costingLevel))
		{
			AD_Org_ID = 0;
			M_AttributeSetInstance_ID = 0;
		}
		else if (CostingLevel.Organization.equals(costingLevel))
		{
			M_AttributeSetInstance_ID = 0;
		}
		else if (CostingLevel.BatchLot.equals(costingLevel))
		{
			AD_Org_ID = 0;
		}
		//
		this.S_Resource_ID = product.getS_Resource_ID();
	}

	/**
	 * @return the aD_Client_ID
	 */
	public int getAD_Client_ID()
	{
		return AD_Client_ID;
	}

	/**
	 * @return the aD_Org_ID
	 */
	public int getAD_Org_ID()
	{
		return AD_Org_ID;
	}

	public CostDimension setAD_Org_ID(final int adOrgId)
	{
		if (this.AD_Org_ID == adOrgId)
		{
			// no change
			return this;
		}
		final CostDimension d = new CostDimension(this);
		d.AD_Org_ID = adOrgId;
		return d;
	}

	/**
	 * @return the m_Product_ID
	 */
	public int getM_Product_ID()
	{
		return M_Product_ID;
	}

	public int getS_Resource_ID()
	{
		return S_Resource_ID;
	}

	public CostDimension setM_Product_ID(int M_Product_ID)
	{
		CostDimension d = new CostDimension(this);
		d.M_Product_ID = M_Product_ID;
		d.updateForProduct(null, null);
		//
		return d;
	}

	public CostDimension setM_Product(final I_M_Product product)
	{
		final CostDimension d = new CostDimension(this);
		d.M_Product_ID = product.getM_Product_ID();
		d.updateForProduct(product, null);
		return d;
	}

	/**
	 * @return the M_AttributeSetInstance_ID
	 */
	public int getM_AttributeSetInstance_ID()
	{
		return M_AttributeSetInstance_ID;
	}

	/**
	 * @return the m_CostType_ID
	 */
	public int getM_CostType_ID()
	{
		return M_CostType_ID;
	}

	/**
	 * @return the c_AcctSchema_ID
	 */
	public int getC_AcctSchema_ID()
	{
		return C_AcctSchema_ID;
	}

	/**
	 * @return the m_CostElement_ID
	 */
	public int getM_CostElement_ID()
	{
		return M_CostElement_ID;
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
		final Properties ctx = getCtx();
		final String tableName = InterfaceWrapperHelper.getTableName(clazz);
		final POInfo poInfo = POInfo.getPOInfo(ctx, tableName);

		final IQueryBuilder<T> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(clazz, ctx, trxName);

		queryBuilder.addEqualsFilter(COLUMNNAME_AD_Client_ID, this.AD_Client_ID);
		queryBuilder.addEqualsFilter(COLUMNNAME_M_Product_ID, this.M_Product_ID);
		queryBuilder.addEqualsFilter(COLUMNNAME_M_AttributeSetInstance_ID, this.M_AttributeSetInstance_ID);
		queryBuilder.addEqualsFilter(COLUMNNAME_C_AcctSchema_ID, this.C_AcctSchema_ID);

		//
		// Filter by organization, only if it's set and we are querying the M_Cost table.
		// Else, you can get no results.
		// e.g. querying PP_Order_Cost, have this.AD_Org_ID=0 but PP_Order_Costs are created using PP_Order's AD_Org_ID
		// see http://dewiki908/mediawiki/index.php/07741_Rate_Variance_%28103334042334%29.
		if (this.AD_Org_ID > 0 || I_M_Cost.Table_Name.equals(tableName))
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_AD_Org_ID, this.AD_Org_ID);
		}

		if (this.M_CostElement_ID != ANY)
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_M_CostElement_ID, this.M_CostElement_ID);
		}

		if (this.M_CostType_ID != ANY && poInfo.hasColumnName(COLUMNNAME_M_CostType_ID))
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_M_CostType_ID, this.M_CostType_ID);
		}

		return queryBuilder;
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
				+ "AD_Client_ID = " + this.AD_Client_ID + TAB
				+ "AD_Org_ID = " + this.AD_Org_ID + TAB
				+ "M_Product_ID = " + this.M_Product_ID + TAB
				+ "M_AttributeSetInstance_ID = " + this.M_AttributeSetInstance_ID + TAB
				+ "M_CostType_ID = " + this.M_CostType_ID + TAB
				+ "C_AcctSchema_ID = " + this.C_AcctSchema_ID + TAB
				+ "M_CostElement_ID = " + this.M_CostElement_ID + TAB
				+ "]";
	}
}
