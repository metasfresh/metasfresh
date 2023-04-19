package de.metas.material.planning.impl;

import de.metas.material.planning.IResourceProductService;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.resource.ResourceService;
import de.metas.resource.ResourceType;
import de.metas.resource.ResourceTypeId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
	private ResourceService _resourceService;

	private ResourceService resourceService()
	{
		ResourceService resourceService = this._resourceService;
		if (resourceService == null)
		{
			resourceService = this._resourceService = SpringContextHolder.instance.getBean(ResourceService.class);
		}
		return resourceService;
	}

	@Override
	public ResourceType getResourceTypeById(final ResourceTypeId resourceTypeId)
	{
		return resourceService().getResourceTypeById(resourceTypeId);
	}

	@Override
	public ResourceType getResourceTypeByResourceId(final ResourceId resourceId)
	{
		return resourceService().getResourceTypeByResourceId(resourceId);
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
	public I_S_Resource getById(@NonNull final ResourceId resourceId)
	{
		return loadOutOfTrx(resourceId, I_S_Resource.class);
	}
}
