/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.product.impl;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.ArrayList;
import java.util.List;

import de.metas.organization.OrgId;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_Planning;

import com.google.common.collect.ImmutableList;

import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.product.IProductDAO;
import de.metas.product.IProductPlanningSchemaBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPlanningSchema;
import de.metas.product.ProductPlanningSchemaId;
import de.metas.product.ProductPlanningSchemaSelector;
import de.metas.product.ResourceId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

public class ProductPlanningSchemaBL implements IProductPlanningSchemaBL
{

	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	@Override
	public List<I_PP_Product_Planning> createDefaultProductPlanningsForAllProducts()
	{
		return ProductPlanningSchemaDAO
				.streamProductsWithNoProductPlanningButWithSchemaSelector()
				.flatMap(product -> createOrUpdateProductPlanningsForProductSelector(product).stream())
				.collect(ImmutableList.toImmutableList());
	}

	private List<I_PP_Product_Planning> createOrUpdateProductPlanningsForProductSelector(@NonNull final I_M_Product product)
	{
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		final OrgId orgId = OrgId.ofRepoId(product.getAD_Org_ID());
		final ProductPlanningSchemaSelector selector = ProductPlanningSchemaSelector.ofNullableCode(product.getM_ProductPlanningSchema_Selector());
		if (selector == null)
		{
			return ImmutableList.of();
		}

		return createOrUpdateProductPlanningsForSelector(productId, orgId, selector);
	}

	@Override
	public List<I_PP_Product_Planning> createOrUpdateProductPlanningsForSelector(
			final @NonNull ProductId productId,
			final @NonNull OrgId orgId,
			final @NonNull ProductPlanningSchemaSelector selector)
	{
		final List<I_PP_Product_Planning> createdPlannings = new ArrayList<>();

		for (final ProductPlanningSchema productPlanningSchema : ProductPlanningSchemaDAO.retrieveSchemasForSelectorAndOrg(selector, orgId))
		{
			createdPlannings.add(createOrUpdateProductPlanningAndSave(productId, orgId, productPlanningSchema));
		}

		return createdPlannings;
	}

	@Override
	public List<I_PP_Product_Planning> createOrUpdateDefaultProductPlanningsForSchemaId(@NonNull final ProductPlanningSchemaId schemaId)
	{
		final List<I_PP_Product_Planning> createdPlannings = new ArrayList<>();

		final ProductPlanningSchema schema = ProductPlanningSchemaDAO.getById(schemaId);
		final ProductPlanningSchemaSelector selector = schema.getSelector();

		for (final I_PP_Product_Planning planning : ProductPlanningSchemaDAO.retrieveProductPlanningsForSchemaId(schema.getId()))
		{
			final ProductId productId = ProductId.ofRepoId(planning.getM_Product_ID());
			final I_M_Product product = productDAO.getById(productId);
			final ProductPlanningSchemaSelector productSchemaSelector = ProductPlanningSchemaSelector.ofNullableCode(product.getM_ProductPlanningSchema_Selector());
			if (!selector.equals(productSchemaSelector))
			{
				delete(planning);
			}
			else
			{
				updateProductPlanningFromSchemaAndSave(planning, schema);
			}
		}

		for (final ImmutablePair<ProductId, OrgId> productAndOrg : productDAO.retrieveProductsAndOrgsForSchemaSelector(selector))
		{
			final I_PP_Product_Planning planning = createOrUpdateProductPlanningAndSave(productAndOrg.getLeft(), productAndOrg.getRight(), schema);
			createdPlannings.add(planning);
		}

		return createdPlannings;
	}

	private I_PP_Product_Planning createOrUpdateProductPlanningAndSave(@NonNull final ProductId productId, final @NonNull OrgId orgId, @NonNull final ProductPlanningSchema schema)
	{
		final ProductPlanningSchemaId schemaId = schema.getId();

		I_PP_Product_Planning productPlanning = ProductPlanningSchemaDAO.retrievePlanningForProductAndSchema(productId, schemaId);
		if (productPlanning == null)
		{
			productPlanning = newInstance(I_PP_Product_Planning.class);
		}

		productPlanning.setM_Product_ID(productId.getRepoId());
		productPlanning.setM_Product_PlanningSchema_ID(schemaId.getRepoId());

		updateProductPlanningFromSchemaAndSave(productPlanning, schema);

		productPlanning.setAD_Org_ID(orgId.getRepoId());

		return productPlanning;
	}

	private void updateProductPlanningFromSchemaAndSave(
			final I_PP_Product_Planning productPlanning,
			final ProductPlanningSchema schema)
	{
		productPlanning.setIsAttributeDependant(schema.isAttributeDependant());
		productPlanning.setS_Resource_ID(ResourceId.toRepoId(schema.getPlantId()));
		productPlanning.setM_Warehouse_ID(WarehouseId.toRepoId(schema.getWarehouseId()));
		productPlanning.setPlanner_ID(UserId.toRepoId(schema.getPlannerId()));
		productPlanning.setIsManufactured(StringUtils.ofBoolean(schema.getManufactured()));
		productPlanning.setIsCreatePlan(schema.isCreatePlan());
		productPlanning.setIsDocComplete(schema.isCompleteGeneratedDocuments());
		productPlanning.setAD_Workflow_ID(PPRoutingId.toRepoId(schema.getRoutingId()));
		productPlanning.setDD_NetworkDistribution_ID(DistributionNetworkId.toRepoId(schema.getDistributionNetworkId()));
		productPlanning.setIsPickDirectlyIfFeasible(schema.isPickDirectlyIfFeasible());
		productPlanning.setOnMaterialReceiptWithDestWarehouse(schema.getOnMaterialReceiptWithDestWarehouse().getCode());

		saveRecord(productPlanning);
	}
}
