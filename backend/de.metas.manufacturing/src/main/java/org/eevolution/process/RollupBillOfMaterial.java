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
 * Teo Sarca, www.arhipac.ro *
 *****************************************************************************/

package org.eevolution.process;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingMethod;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_Product;
import org.eevolution.costing.BOMCostCalculator;
import org.eevolution.costing.BatchProcessBOMCostCalculatorRepository;

/**
 * Roll-UP Bill of Material
 */
public class RollupBillOfMaterial extends JavaProcess
{
	// services
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	// Parameters
	private ClientId clientId;
	private OrgId orgId;
	private AcctSchema acctSchema;
	private CostTypeId costTypeId;
	private CostingMethod costingMethod = CostingMethod.StandardCosting;
	private ProductId productId;
	private ProductCategoryId productCategoryId;
	private String productType = null;

	@Override
	protected void prepare()
	{
		this.clientId = ClientId.ofRepoId(getAD_Client_ID());

		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();

			if (para.getParameter() == null)
			{
				;
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_AD_Org_ID))
			{
				orgId = OrgId.ofRepoIdOrNull(para.getParameterAsInt());
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_C_AcctSchema_ID))
			{
				final AcctSchemaId p_C_AcctSchema_ID = AcctSchemaId.ofRepoId(para.getParameterAsInt());
				acctSchema = Services.get(IAcctSchemaDAO.class).getById(p_C_AcctSchema_ID);
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_M_CostType_ID))
			{
				costTypeId = CostTypeId.ofRepoIdOrNull(para.getParameterAsInt());
			}
			else if (name.equals(I_M_CostElement.COLUMNNAME_CostingMethod))
			{
				costingMethod = CostingMethod.ofNullableCode(para.getParameterAsString());
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_M_Product_ID))
			{
				productId = ProductId.ofRepoIdOrNull(para.getParameterAsInt());
			}
			else if (name.equals(I_M_Product.COLUMNNAME_M_Product_Category_ID))
			{
				productCategoryId = ProductCategoryId.ofRepoIdOrNull(para.getParameterAsInt());
			}
			else if (name.equals(I_M_Product.COLUMNNAME_ProductType))
			{
				productType = para.getParameterAsString();
			}
			else
			{
				log.error("prepare - Unknown Parameter: " + name);
			}
		}
	}	// prepare

	@Override
	protected String doIt()
	{
		final BatchProcessBOMCostCalculatorRepository bomCostCalculatorRepo = BatchProcessBOMCostCalculatorRepository.builder()
				.clientId(clientId)
				.orgId(orgId)
				.acctSchema(acctSchema)
				.costTypeId(costTypeId)
				.costingMethod(costingMethod)
				.build();

		final BOMCostCalculator calculator = BOMCostCalculator.builder()
				.repository(bomCostCalculatorRepo)
				.build();

		//@TODO the process is not used in WEB UI, need to be updated in necessary
/*		final int maxLowLevel = getMaxLowLevel();
		for (int lowLevel = maxLowLevel; lowLevel >= 0; lowLevel--)
		{
			for (final ProductId productId : getProductIdsByLowLevel(lowLevel))
			{
				calculator.rollup(productId);
			}
		}*/

		throw new UnsupportedOperationException("Process not implemented");
	}

	//@TODO the process is not used in WEB UI, need to be updated in necessary
/*	private int getMaxLowLevel()
	{
		return createProductsQuery()
				.addNotNull(I_M_Product.COLUMNNAME_LowLevel)
				.create()
				.maxInt(I_M_Product.COLUMNNAME_LowLevel);
	}

	private Set<ProductId> getProductIdsByLowLevel(final int lowLevel)
	{
		return createProductsQuery()
				.addEqualsFilter(I_M_Product.COLUMN_LowLevel, lowLevel)
				.create()
				.listIds(ProductId::ofRepoId);
	}*/

	private IQueryBuilder<I_M_Product> createProductsQuery()
	{
		final IQueryBuilder<I_M_Product> queryBuilder = queryBL.createQueryBuilder(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_Product.COLUMN_M_Product_ID) // just to have a predictable order
				.addEqualsFilter(I_M_Product.COLUMNNAME_AD_Client_ID, clientId)
				.addEqualsFilter(I_M_Product.COLUMNNAME_IsBOM, true);

		if (productId != null)
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, productId);
		}
		else if (productCategoryId != null)
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_Category_ID, productCategoryId);
		}
		if (productId == null && productType != null)
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMNNAME_ProductType, productType);
		}

		//
		return queryBuilder;
	}

}
