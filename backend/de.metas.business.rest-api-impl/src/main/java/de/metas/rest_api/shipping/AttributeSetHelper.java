/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.shipping;

import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_M_Attribute;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AttributeSetHelper
{
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	public List<CreateAttributeInstanceReq> toCreateAttributeInstanceReqList(@NonNull final Collection<JsonAttributeInstance> jsonAttributeInstances)
	{
		return jsonAttributeInstances.stream()
				.map(this::toCreateAttributeInstanceReq)
				.collect(ImmutableList.toImmutableList());
	}

	private CreateAttributeInstanceReq toCreateAttributeInstanceReq(@NonNull final JsonAttributeInstance jsonAttributeInstance)
	{
		return CreateAttributeInstanceReq.builder()
				.attributeCode(AttributeCode.ofString(jsonAttributeInstance.getAttributeCode()))
				.value(extractAttributeValueObject(jsonAttributeInstance))
				.build();
	}

	private Object extractAttributeValueObject(@NonNull final JsonAttributeInstance attributeInstance)
	{
		final I_M_Attribute attribute = attributeDAO.retrieveAttributeByValue(attributeInstance.getAttributeCode());
		final AttributeValueType targetAttributeType = AttributeValueType.ofCode(attribute.getAttributeValueType());

		switch (targetAttributeType)
		{
			case DATE:
				return attributeInstance.getValueDate();
			case NUMBER:
				return attributeInstance.getValueNumber();
			case STRING:
			case LIST:
				return attributeInstance.getValueStr();
			default:
				throw new IllegalArgumentException("@NotSupported@ @AttributeValueType@=" + targetAttributeType + ", @M_Attribute_ID@=" + attribute.getM_Attribute_ID());
		}
	}

}
