package de.metas.material.event;

import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-event
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

/**
 * This simple implementation is intended to be used in local testing only.<br>
 * There is a subclass in metasfresh-material-dispo-commons that is injected at runtime and that has a working implementation for {@link #createProductDescriptor(Object)}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ProductDescriptorFactory
{
	public static ProductDescriptorFactory TESTING_INSTANCE = new ProductDescriptorFactory();
	
	protected ProductDescriptorFactory()
	{		
	}
	
	public ProductDescriptor createProductDescriptor(Object asiAwareModel)
	{
		throw new UnsupportedOperationException();
	}

	public ProductDescriptor forProductIdAndAttributeSetInstanceId(final int productId, final int attributeSetInstanceId)
	{
		throw new UnsupportedOperationException();
	}
	
	public final ProductDescriptor forProductIdOnly(final int productId)
	{
		return new ProductDescriptor(false, productId, null, -1); // complete == false
	}

	public final ProductDescriptor forProductIdAndEmptyAttribute(final int productId)
	{
		return new ProductDescriptor(true, productId, ProductDescriptor.STORAGE_ATTRIBUTES_KEY_EMPTY, 0); // complete == true
	}

	public final ProductDescriptor forProductAndAttributes(
			final int productId,
			@NonNull final String storageAttributesKey,
			final int attributeSetInstanceId)
	{
		return new ProductDescriptor(true, productId, storageAttributesKey, attributeSetInstanceId); // complete == true
	}

}
