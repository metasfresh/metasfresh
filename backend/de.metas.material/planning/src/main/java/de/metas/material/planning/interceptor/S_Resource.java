package de.metas.material.planning.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.model.ModelValidator;

import de.metas.material.planning.IResourceProductService;

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
@Interceptor(I_S_Resource.class)
public class S_Resource
{
	static final S_Resource INSTANCE = new S_Resource();

	private S_Resource()
	{
	}

	/**
	 * Creates a resource product.<br>
	 * Note that it's important to create it <b>after</b> new, because otherwise the given {@code resource}'s {@code Value} mit still be {@code null} (https://github.com/metasfresh/metasfresh/issues/1580)
	 * 
	 * @param resource
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void createResourceProduct(final I_S_Resource resource)
	{
		createOrUpdateProduct(resource);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void updateResourceProduct(final I_S_Resource resource)
	{
		createOrUpdateProduct(resource);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteResourceProduct(final I_S_Resource resource)
	{
		final I_M_Product product = retrieveProductOrNull(resource);
		if (product != null)
		{
			InterfaceWrapperHelper.delete(product);
		}
	}

	private void createOrUpdateProduct(final I_S_Resource resource)
	{
		final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);

		final I_M_Product exitingProduct = retrieveProductOrNull(resource);
		final I_M_Product productToUpdate;
		if (exitingProduct == null)
		{
			productToUpdate = InterfaceWrapperHelper.newInstance(I_M_Product.class, resource);
			resourceProductService.setResourceTypeToProduct(resource.getS_ResourceType(), productToUpdate);
		}
		else
		{
			productToUpdate = exitingProduct;
		}

		resourceProductService.setResourceToProduct(resource, productToUpdate);

		InterfaceWrapperHelper.save(productToUpdate);
	}

	private I_M_Product retrieveProductOrNull(final I_S_Resource resource)
	{
		final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
		final I_M_Product product = resourceProductService.retrieveProductForResource(resource);
		return product;
	}
}
