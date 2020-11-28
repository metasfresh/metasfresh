package de.metas.inoutcandidate.picking_bom;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.SetMultimap;

import de.metas.cache.CCache;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanningId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Service
public class PickingBOMService
{
	private final IProductPlanningDAO productPlanningsRepo = Services.get(IProductPlanningDAO.class);
	private final IProductBOMDAO bomsRepo = Services.get(IProductBOMDAO.class);

	private final CCache<Integer, PickingBOMsReversedIndex> pickingBOMsReversedIndexCache = CCache.<Integer, PickingBOMsReversedIndex> builder()
			.additionalTableNameToResetFor(I_PP_Product_Planning.Table_Name)
			.additionalTableNameToResetFor(I_PP_Product_BOM.Table_Name)
			.additionalTableNameToResetFor(I_PP_Product_BOMLine.Table_Name)
			.build();

	public Optional<PickingOrderConfig> getPickingOrderConfig(
			final OrgId orgId,
			final WarehouseId warehouseId,
			final ProductId productId,
			final AttributeSetInstanceId asiId)
	{
		final ProductPlanningQuery productPlanningQuery = ProductPlanningQuery.builder()
				.orgId(orgId)
				.warehouseId(warehouseId)
				.productId(productId)
				.attributeSetInstanceId(asiId)
				.build();

		final I_PP_Product_Planning productPlanning = productPlanningsRepo.find(productPlanningQuery).orElse(null);
		if (productPlanning == null)
		{
			return Optional.empty();
		}

		return extractPickingOrderConfig(productPlanning);
	}

	public Optional<PickingOrderConfig> extractPickingOrderConfig(@NonNull final I_PP_Product_Planning productPlanning)
	{
		if (!StringUtils.toBoolean(productPlanning.getIsManufactured()))
		{
			return Optional.empty();
		}
		if (!productPlanning.isPickingOrder())
		{
			return Optional.empty();
		}

		final ResourceId plantId = ResourceId.ofRepoIdOrNull(productPlanning.getS_Resource_ID());
		if (plantId == null)
		{
			throw new FillMandatoryException("PP_Plant_ID")
					.setParameter("productPlanning", productPlanning)
					.appendParametersToMessage();
		}

		final ProductBOMId bomId = ProductBOMId.ofRepoIdOrNull(productPlanning.getPP_Product_BOM_ID());
		if (bomId == null)
		{
			throw new FillMandatoryException("PP_Product_BOM_ID")
					.setParameter("productPlanning", productPlanning)
					.appendParametersToMessage();
		}

		return Optional.of(PickingOrderConfig.builder()
				.productPlanningId(ProductPlanningId.ofRepoId(productPlanning.getPP_Product_Planning_ID()))
				.plantId(plantId)
				.bomId(bomId)
				.plannerId(UserId.ofRepoIdOrNull(productPlanning.getPlanner_ID()))
				.build());
	}

	public PickingBOMsReversedIndex getPickingBOMsReversedIndex()
	{
		return pickingBOMsReversedIndexCache.getOrLoad(0, this::retrievePickingBOMsReversedIndex);
	}

	private PickingBOMsReversedIndex retrievePickingBOMsReversedIndex()
	{
		final Set<ProductBOMId> pickingBOMIds = productPlanningsRepo.retrieveAllPickingBOMIds();
		if (pickingBOMIds.isEmpty())
		{
			return PickingBOMsReversedIndex.EMPTY;
		}

		final ImmutableMap<ProductBOMId, I_PP_Product_BOM> bomsById = bomsRepo.getByIds(pickingBOMIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> ProductBOMId.ofRepoId(record.getPP_Product_BOM_ID()),
						record -> record));
		if (bomsById.isEmpty())
		{
			return PickingBOMsReversedIndex.EMPTY;
		}

		final List<I_PP_Product_BOMLine> bomLines = bomsRepo.retrieveLinesByBOMIds(pickingBOMIds);
		if (bomLines.isEmpty())
		{
			return PickingBOMsReversedIndex.EMPTY;
		}

		final SetMultimap<ProductId, ProductId> bomProductIdsByComponentId = HashMultimap.create();
		for (final I_PP_Product_BOMLine bomLine : bomLines)
		{
			final ProductId componentId = ProductId.ofRepoId(bomLine.getM_Product_ID());
			final ProductBOMId bomId = ProductBOMId.ofRepoId(bomLine.getPP_Product_BOM_ID());
			final I_PP_Product_BOM bom = bomsById.get(bomId);
			ProductId bomProductId = ProductId.ofRepoId(bom.getM_Product_ID());
			bomProductIdsByComponentId.put(componentId, bomProductId);
		}

		return PickingBOMsReversedIndex.ofBOMProductIdsByComponentId(bomProductIdsByComponentId);
	}

}
