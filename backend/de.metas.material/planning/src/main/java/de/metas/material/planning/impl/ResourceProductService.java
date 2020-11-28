package de.metas.material.planning.impl;

import java.time.temporal.TemporalUnit;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.material.planning.IResourceDAO;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.ResourceType;
import de.metas.material.planning.ResourceTypeId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.uom.UOMUtil;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class ResourceProductService implements IResourceProductService
{
	@Override
	public ResourceType getResourceTypeById(final ResourceTypeId resourceTypeId)
	{
		return Services.get(IResourceDAO.class).getResourceTypeById(resourceTypeId);
	}

	@Override
	public ResourceType getResourceTypeByResourceId(final ResourceId resourceId)
	{
		return Services.get(IResourceDAO.class).getResourceTypeByResourceId(resourceId);
	}

	@Override
	public I_M_Product getProductByResourceId(@NonNull final ResourceId resourceId)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);

		final ProductId productId = productsRepo.getProductIdByResourceId(resourceId);
		return productsRepo.getById(productId);
	}

	@Override
	public ProductId getProductIdByResourceId(final ResourceId resourceId)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		return productsRepo.getProductIdByResourceId(resourceId);
	}

	@Override
	public TemporalUnit getResourceTemporalUnit(final ResourceId resourceId)
	{
		final ProductId resourceProductId = getProductIdByResourceId(resourceId);
		final I_C_UOM uom = Services.get(IProductBL.class).getStockUOM(resourceProductId);
		return UOMUtil.toTemporalUnit(uom);
	}

	@Override
	public I_C_UOM getResoureUOM(final ResourceId resourceId)
	{
		final I_M_Product product = Services.get(IResourceProductService.class).getProductByResourceId(resourceId);
		return Services.get(IProductBL.class).getStockUOM(product);
	}
}
