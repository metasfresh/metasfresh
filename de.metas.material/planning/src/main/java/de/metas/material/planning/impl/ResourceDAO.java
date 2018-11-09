package de.metas.material.planning.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;
import org.compiere.model.X_M_Product;
import org.compiere.model.X_S_Resource;

import com.google.common.collect.ImmutableSet;

import de.metas.cache.annotation.CacheCtx;
import de.metas.material.planning.IResourceDAO;
import de.metas.material.planning.ResourceTypeId;
import de.metas.product.IProductDAO;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;

public class ResourceDAO implements IResourceDAO
{
	public I_S_ResourceType getResourceTypeById(@NonNull final ResourceTypeId resourceTypeId)
	{
		return loadOutOfTrx(resourceTypeId, I_S_ResourceType.class);
	}

	@Override
	public I_S_Resource getById(@NonNull final ResourceId resourceId)
	{
		return loadOutOfTrx(resourceId, I_S_Resource.class);
	}

	@Override
	@Cached(cacheName = I_S_Resource.Table_Name + "#by"
			+ "#" + I_S_Resource.COLUMNNAME_AD_Client_ID
			+ "#" + I_S_Resource.COLUMNNAME_IsManufacturingResource)
	public List<I_S_Resource> retrievePlants(final @CacheCtx Properties ctx)
	{
		final IQueryBuilder<I_S_Resource> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_S_Resource.class, ctx, ITrx.TRXNAME_None);

		final ICompositeQueryFilter<I_S_Resource> filters = queryBuilder.getCompositeFilter();

		//
		// Only manufacturing resources
		filters.addEqualsFilter(I_S_Resource.COLUMNNAME_IsManufacturingResource, true);

		//
		// Only Plant resources
		filters.addEqualsFilter(I_S_Resource.COLUMNNAME_ManufacturingResourceType, X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant);

		//
		// Only for current AD_Client_ID
		filters.addOnlyContextClient(ctx);

		//
		// Only active ones
		filters.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_S_Resource.COLUMNNAME_S_Resource_ID);

		return queryBuilder
				.create()
				.list(I_S_Resource.class);
	}

	@Override
	public I_S_Resource retrievePlant(final Properties ctx, final int resourceId)
	{
		for (final I_S_Resource plant : retrievePlants(ctx))
		{
			if (plant.getS_Resource_ID() == resourceId)
			{
				return plant;
			}
		}

		return null;
	}

	@Override
	public void onResourceChanged(@NonNull final I_S_Resource resource)
	{
		final ResourceId resourceId = ResourceId.ofRepoId(resource.getS_Resource_ID());
		final ResourceTypeId resourceTypeId = ResourceTypeId.ofRepoId(resource.getS_ResourceType_ID());

		final IProductDAO productsRepo = Services.get(IProductDAO.class);

		productsRepo.updateProductsByResourceIds(ImmutableSet.of(resourceId), (resourceId1, existingProduct) -> {
			final I_M_Product productToUpdate;
			if (existingProduct == null)
			{
				final I_S_ResourceType fromResourceType = getResourceTypeById(resourceTypeId);
				productToUpdate = InterfaceWrapperHelper.newInstance(I_M_Product.class);

				updateProductFromResourceType(productToUpdate, fromResourceType);
			}
			else
			{
				productToUpdate = existingProduct;
			}

			updateProductFromResource(productToUpdate, resource);
		});
	}

	private void updateProductFromResource(@NonNull final I_M_Product product, @NonNull final I_S_Resource from)
	{
		product.setProductType(X_M_Product.PRODUCTTYPE_Resource);
		product.setS_Resource_ID(from.getS_Resource_ID());
		product.setIsActive(from.isActive());

		product.setValue("PR" + from.getValue()); // the "PR" is a QnD solution to the possible problem that if the production resource's value is set to its ID (like '1000000") there is probably already a product with the same value.
		product.setName(from.getName());
		product.setDescription(from.getDescription());
	}

	@Override
	public void onResourceTypeChanged(final I_S_ResourceType resourceType)
	{
		final Set<ResourceId> resourceIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_S_Resource.class) // in trx!
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Resource.COLUMNNAME_S_ResourceType_ID, resourceType.getS_ResourceType_ID())
				.create()
				.listIds(ResourceId::ofRepoId);
		if (resourceIds.isEmpty())
		{
			return;
		}

		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		productsRepo.updateProductsByResourceIds(resourceIds, product -> updateProductFromResourceType(product, resourceType));
	}

	private void updateProductFromResourceType(final I_M_Product product, final I_S_ResourceType from)
	{
		product.setProductType(X_M_Product.PRODUCTTYPE_Resource);
		product.setC_UOM_ID(from.getC_UOM_ID());
		product.setM_Product_Category_ID(from.getM_Product_Category_ID());
	}
}
