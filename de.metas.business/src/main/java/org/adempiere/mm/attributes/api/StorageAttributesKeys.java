package org.adempiere.mm.attributes.api;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.Comparator;

import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.StorageAttributesKey;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public final class StorageAttributesKeys
{
	public static StorageAttributesKey createAttributesKeyFromASI(final int attributeSetInstanceId)
	{
		if (attributeSetInstanceId == AttributeConstants.M_AttributeSetInstance_ID_None)
		{
			return ProductDescriptor.STORAGE_ATTRIBUTES_KEY_ALL;
		}

		final I_M_AttributeSetInstance attributeSetInstance = load(attributeSetInstanceId, I_M_AttributeSetInstance.class);

		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final int[] attributeValueIds = attributeDAO.retrieveAttributeInstances(attributeSetInstance).stream()
				.filter(ai -> ai.getM_AttributeValue_ID() > 0)
				.filter(ai -> ai.getM_Attribute().isStorageRelevant())
				.sorted(Comparator.comparing(I_M_AttributeInstance::getM_Attribute_ID))
				.mapToInt(I_M_AttributeInstance::getM_AttributeValue_ID)
				.toArray();

		return StorageAttributesKey.ofAttributeValueIds(attributeValueIds);
	}
}
