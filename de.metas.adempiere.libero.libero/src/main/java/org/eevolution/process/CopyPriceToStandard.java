/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): Victor Perez www.e-evolution.com *
 *****************************************************************************/

package org.eevolution.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.currency.ICurrencyBL;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;

/**
 * CopyPriceToStandard
 *
 * @author Victor Perez, e-Evolution, S.C.
 * @version $Id: CopyPriceToStandard.java,v 1.1 2004/06/22 05:24:03 vpj-cd Exp $
 */
public class CopyPriceToStandard extends JavaProcess
{
	// services
	private final transient ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);

	// parameters
	private OrgId p_AD_Org_ID;
	private AcctSchemaId p_C_AcctSchema_ID;
	private CostTypeId p_M_CostType_ID;
	private CostElementId p_M_CostElement_ID;
	private int p_M_PriceList_Version_ID = 0;

	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();

			if (para[i].getParameter() == null)
				;
			else if (name.equals("M_CostType_ID"))
			{
				p_M_CostType_ID = CostTypeId.ofRepoIdOrNull(para[i].getParameterAsInt());
			}
			else if (name.equals("AD_Org_ID"))
			{
				p_AD_Org_ID = OrgId.ofRepoIdOrAny(para[i].getParameterAsInt());
			}
			else if (name.equals("C_AcctSchema_ID"))
			{
				p_C_AcctSchema_ID = AcctSchemaId.ofRepoId(para[i].getParameterAsInt());
			}
			else if (name.equals("M_CostElement_ID"))
			{
				p_M_CostElement_ID = CostElementId.ofRepoIdOrNull(para[i].getParameterAsInt());
			}
			else if (name.equals("M_PriceList_Version_ID"))
			{
				p_M_PriceList_Version_ID = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else
			{
				log.error("prepare - Unknown Parameter: " + name);
			}
		}
	}

	@Override
	protected String doIt()
	{
		final AcctSchema acctSchema = getAcctSchema();
		final CostElement element = getCostElement();
		if (!element.isMaterialCostingMethod())
		{
			throw new LiberoException("Only Material Cost Elements are allowed");
		}

		int count_updated = 0;

		final I_M_PriceList_Version plv = load(p_M_PriceList_Version_ID, I_M_PriceList_Version.class);
		for (final I_M_ProductPrice pprice : getProductPrice(p_M_PriceList_Version_ID))
		{
			BigDecimal price = pprice.getPriceStd();
			final I_M_PriceList pl = Services.get(IPriceListDAO.class).getById(plv.getM_PriceList_ID());
			int C_Currency_ID = pl.getC_Currency_ID();
			if (C_Currency_ID != acctSchema.getCurrencyId().getRepoId())
			{
				price = currencyConversionBL.convert(getCtx(), pprice.getPriceStd(),
						C_Currency_ID, acctSchema.getCurrencyId().getRepoId(),
						getAD_Client_ID(), p_AD_Org_ID.getRepoId());
			}

			final I_M_Product product = Services.get(IProductDAO.class).getById(pprice.getM_Product_ID());
			final CostDimension d = new CostDimension(product, acctSchema, p_M_CostType_ID, p_AD_Org_ID, AttributeSetInstanceId.NONE, element.getId());
			final Collection<I_M_Cost> costs = d.toQuery(I_M_Cost.class, get_TrxName()).list();
			for (I_M_Cost cost : costs)
			{
				if (cost.getM_CostElement_ID() == element.getId().getRepoId())
				{
					cost.setFutureCostPrice(price);
					InterfaceWrapperHelper.save(cost);
					count_updated++;
					break;
				}
			}
		}
		return "@Updated@ #" + count_updated;
	}

	private AcctSchema getAcctSchema()
	{
		return Services.get(IAcctSchemaDAO.class).getById(p_C_AcctSchema_ID);
	}

	private CostElement getCostElement()
	{
		return Adempiere.getBean(ICostElementRepository.class).getById(p_M_CostElement_ID);
	}

	private static List<I_M_ProductPrice> getProductPrice(int priceListVersionId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ProductPrice.class)
				.addEqualsFilter(I_M_ProductPrice.COLUMN_M_PriceList_Version_ID, priceListVersionId)
				.addNotEqualsFilter(I_M_ProductPrice.COLUMN_PriceStd, BigDecimal.ZERO)
				.create()
				.list();
	}

	private static final class CostDimension
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
		// private ResourceId resourceId;
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

		/**
		 * Copy Constructor
		 *
		 * @param costDimension a <code>CostDimension</code> object
		 */
		private CostDimension(final CostDimension costDimension)
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

			// this.resourceId = ResourceId.ofRepoIdOrNull(product.getS_Resource_ID());
		}

		public <T> IQuery<T> toQuery(Class<T> clazz, String trxName)
		{
			return toQueryBuilder(clazz, trxName)
					.create();
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

}
