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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.IProductPlanningSchemaBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPlanningSchema;
import de.metas.product.ProductPlanningSchemaId;
import de.metas.product.ProductPlanningSchemaSelector;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.compiere.model.I_M_Product;

import java.util.ArrayList;
import java.util.List;

public class ProductPlanningSchemaBL implements IProductPlanningSchemaBL
{

	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	@VisibleForTesting final ProductPlanningSchemaDAO productPlanningSchemaDAO = new ProductPlanningSchemaDAO();

	@Override
	public List<ProductPlanning> createDefaultProductPlanningsForAllProducts()
	{
		return productPlanningDAO.streamProductsWithNoProductPlanningButWithSchemaSelector()
				.flatMap(product -> createOrUpdateProductPlanningsForProductSelector(product).stream())
				.collect(ImmutableList.toImmutableList());
	}

	private List<ProductPlanning> createOrUpdateProductPlanningsForProductSelector(@NonNull final I_M_Product product)
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
	public List<ProductPlanning> createOrUpdateProductPlanningsForSelector(
			final @NonNull ProductId productId,
			final @NonNull OrgId orgId,
			final @NonNull ProductPlanningSchemaSelector selector)
	{
		final List<ProductPlanning> createdPlannings = new ArrayList<>();

		for (final ProductPlanningSchema productPlanningSchema : productPlanningSchemaDAO.retrieveSchemasForSelectorAndOrg(selector, orgId))
		{
			createdPlannings.add(createOrUpdateProductPlanningAndSave(productId, orgId, productPlanningSchema));
		}

		return createdPlannings;
	}

	@Override
	public List<ProductPlanning> createOrUpdateDefaultProductPlanningsForSchemaId(@NonNull final ProductPlanningSchemaId schemaId)
	{
		final List<ProductPlanning> createdPlannings = new ArrayList<>();

		final ProductPlanningSchema schema = productPlanningSchemaDAO.getById(schemaId);
		final ProductPlanningSchemaSelector selector = schema.getSelector();

		for (final ProductPlanning planning : productPlanningDAO.retrieveActiveProductPlanningsBySchemaId(schema.getIdNotNull()))
		{
			final ProductId productId = planning.getProductId();
			final I_M_Product product = productDAO.getById(productId);
			final ProductPlanningSchemaSelector productSchemaSelector = ProductPlanningSchemaSelector.ofNullableCode(product.getM_ProductPlanningSchema_Selector());
			if (!selector.equals(productSchemaSelector))
			{
				productPlanningDAO.deleteById(planning.getIdNotNull());
			}
			else
			{
				updateProductPlanningFromSchemaAndSave(planning, schema);
			}
		}

		for (final ImmutablePair<ProductId, OrgId> productAndOrg : productDAO.retrieveProductsAndOrgsForSchemaSelector(selector))
		{
			final ProductPlanning planning = createOrUpdateProductPlanningAndSave(productAndOrg.getLeft(), productAndOrg.getRight(), schema);
			createdPlannings.add(planning);
		}

		return createdPlannings;
	}

	private ProductPlanning createOrUpdateProductPlanningAndSave(@NonNull final ProductId productId, final @NonNull OrgId orgId, @NonNull final ProductPlanningSchema schema)
	{
		final ProductPlanningSchemaId schemaId = schema.getIdNotNull();

		ProductPlanning productPlanning = productPlanningDAO.retrieveActiveProductPlanningByProductAndSchemaId(productId, schemaId).orElse(null);

		final ProductPlanning.ProductPlanningBuilder builder = productPlanning != null ? productPlanning.toBuilder() : ProductPlanning.builder();

		builder.productId(productId);
		builder.productPlanningSchemaId(schemaId);
		updateProductPlanningFromSchema(builder, schema);
		builder.orgId(orgId);

		productPlanning = productPlanningDAO.save(builder.build());

		return productPlanning;
	}

	private void updateProductPlanningFromSchemaAndSave(
			final ProductPlanning productPlanning,
			final ProductPlanningSchema schema)
	{
		final ProductPlanning.ProductPlanningBuilder builder = productPlanning.toBuilder();
		updateProductPlanningFromSchema(builder, schema);
		productPlanningDAO.save(builder.build());
	}

	private static void updateProductPlanningFromSchema(
			final ProductPlanning.ProductPlanningBuilder builder,
			final ProductPlanningSchema schema)
	{
		builder.isAttributeDependant(schema.isAttributeDependant());
		builder.plantId(schema.getPlantId());
		builder.warehouseId(schema.getWarehouseId());
		builder.plannerId(schema.getPlannerId());
		builder.isManufactured(StringUtils.toBoolean(schema.getManufactured()));
		builder.isCreatePlan(schema.isCreatePlan());
		builder.isDocComplete(schema.isCompleteGeneratedDocuments());
		builder.workflowId(schema.getRoutingId());
		builder.distributionNetworkId(schema.getDistributionNetworkId());
		builder.isPickDirectlyIfFeasible(schema.isPickDirectlyIfFeasible());
		builder.onMaterialReceiptWithDestWarehouse(schema.getOnMaterialReceiptWithDestWarehouse());
	}
}
