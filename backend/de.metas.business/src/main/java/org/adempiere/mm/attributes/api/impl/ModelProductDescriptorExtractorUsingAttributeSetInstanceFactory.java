package org.adempiere.mm.attributes.api.impl;

import com.google.common.base.Preconditions;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.springframework.stereotype.Service;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

@Service
public class ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory
		implements ModelProductDescriptorExtractor
{
	@Override
	public final ProductDescriptor createProductDescriptor(
			@NonNull final Object model,
			@NonNull final AttributesKey defaultAttributesKey)
	{
		final IAttributeSetInstanceAware asiAware = Services.get(IAttributeSetInstanceAwareFactoryService.class)
				.createOrNull(model);
		Preconditions.checkNotNull(asiAware,
				"The given parameter can't be represented as an IAttributeSetInstanceAware; model=%s", model);

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(asiAware.getM_AttributeSetInstance_ID());
		final AttributesKey storageAttributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(asiId)
				.orElse(defaultAttributesKey);

		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(
				asiAware.getM_Product_ID(),
				storageAttributesKey,
				asiId.getRepoId());

		return productDescriptor;
	}
}
