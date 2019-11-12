package de.metas.material.planning;

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

import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.material.planning.exception.NoPlantForWarehouseException;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.ISingletonService;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

public interface IProductPlanningDAO extends ISingletonService
{
	@Value
	public static class ProductPlanningQuery
	{
		OrgId orgId;
		WarehouseId warehouseId;
		ResourceId plantId;
		ProductId productId;
		AttributeSetInstanceId attributeSetInstanceId;

		/**
		 * @param orgId may be null which means only the * org
		 * @param warehouseId may be null which means "no warehouse" (not any warehouse!)
		 * @param plantId may be null which means "no plantId"
		 * @param productId mandatory
		 * @param attributeSetInstanceId mandatory, but might contain the 0-ASI-Id;
		 */
		@Builder
		private ProductPlanningQuery(
				@Nullable final OrgId orgId,
				@Nullable final WarehouseId warehouseId,
				@Nullable final ResourceId plantId,
				@Nullable final ProductId productId,
				@NonNull final AttributeSetInstanceId attributeSetInstanceId)
		{
			this.orgId = orgId;
			this.warehouseId = warehouseId;
			this.plantId = plantId;
			this.productId = productId;
			this.attributeSetInstanceId = attributeSetInstanceId;
		}
	}

	I_PP_Product_Planning getById(int ppProductPlanningId);

	/**
	 * Find best matching product planning.
	 *
	 * @return matching product planning or null
	 */
	I_PP_Product_Planning find(ProductPlanningQuery productPlanningQuery);

	/**
	 * Search product plannings to find out which is the plant({@link I_S_Resource}) for given Org/Warehouse/Product.
	 *
	 * @param adOrgId
	 * @param warehouseId
	 * @param productId
	 * @return plant
	 * @throws NoPlantForWarehouseException if there was no plant found or if there was more then one plant found.
	 */
	I_S_Resource findPlant(final int adOrgId, final I_M_Warehouse warehouse, final int productId, int attributeSetInstanceId);

	/**
	 * Retrieve all warehouses which are directly assigned to our Org and Plant.
	 *
	 * @param ctx
	 * @param org
	 * @param plant
	 * @return
	 */
	List<I_M_Warehouse> retrieveWarehousesForPlant(Properties ctx, I_AD_Org org, I_S_Resource plant);

	void save(I_PP_Product_Planning productPlanningRecord);

	void setProductBOMIdIfAbsent(ProductId productId, ProductBOMId bomId);
}
