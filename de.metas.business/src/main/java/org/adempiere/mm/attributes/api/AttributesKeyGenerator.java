package org.adempiere.mm.attributes.api;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.material.event.commons.ProductDescriptor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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

@Value
@Builder
public class AttributesKeyGenerator
{
	int attributeSetInstanceId;

	@NonNull
	@Default
	String valueDelimiter = ProductDescriptor.STORAGE_ATTRIBUTES_KEY_DELIMITER;

	@NonNull
	@Default
	Predicate<I_M_AttributeInstance> attributeInstanceFilter = ai -> true;

	/**
	 * Default value accessor that works for list and string attributes.
	 */
	@NonNull
	@Default
	Function<I_M_AttributeInstance, String> valueAccessor = ai -> {

		return ai.getValue();
	};

	public String createAttributesKey()
	{
		if (attributeSetInstanceId == AttributeConstants.M_AttributeSetInstance_ID_None)
		{
			return ProductDescriptor.STORAGE_ATTRIBUTES_KEY_UNSPECIFIED;
		}

		final I_M_AttributeSetInstance attributeSetInstance = load(attributeSetInstanceId, I_M_AttributeSetInstance.class);
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final String storageAttributesKey = attributeDAO.retrieveAttributeInstances(attributeSetInstance).stream()
				.filter(attributeInstanceFilter)
				.sorted(Comparator.comparing(I_M_AttributeInstance::getM_Attribute_ID))
				.map(valueAccessor)
				.collect(Collectors.joining(valueDelimiter));
		return storageAttributesKey;
	}
}
