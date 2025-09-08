package de.metas.material.event.commons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

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

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = "attributeSetInstanceId")
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(name = "MaterialDescriptor", value = MaterialDescriptor.class) })
public class ProductDescriptor
{
	public static ProductDescriptor completeForProductIdAndEmptyAttribute(final int productId)
	{
		return new ProductDescriptor(productId, AttributesKey.NONE, AttributeSetInstanceId.NONE.getRepoId());
	}

	public static ProductDescriptor forProductAndAttributes(
			final int productId,
			@NonNull final AttributesKey attributesKey,
			final int attributeSetInstanceId)
	{
		return new ProductDescriptor(productId, attributesKey, attributeSetInstanceId);
	}

	public static ProductDescriptor forProductAndAttributes(
			final int productId,
			@NonNull final AttributesKey attributesKey)
	{
		return new ProductDescriptor(productId, attributesKey, AttributeSetInstanceId.NONE.getRepoId());
	}

	@Getter
	int productId;

	@Getter
	AttributesKey storageAttributesKey;

	/**
	 * This ID is only here so that the candidate row's attributes can be displayed properly in the UI.
	 * It may not cause otherwise equal ProductDescriptors to seem as unequal.
	 */
	@Getter
	int attributeSetInstanceId;

	@JsonCreator
	public ProductDescriptor(
			@JsonProperty("productId") final int productId,
			@JsonProperty("storageAttributesKey") @NonNull final AttributesKey storageAttributesKey,
			@JsonProperty("attributeSetInstanceId") final int attributeSetInstanceId)
	{
		Preconditions.checkArgument(productId > 0,
				"Given parameter productId=%s needs to be >0", productId);
		Preconditions.checkArgument(attributeSetInstanceId >= -1,
				"Given parameter attributeSetInstanceId needs to >=-1");

		this.productId = productId;
		this.storageAttributesKey = storageAttributesKey;
		if (AttributesKey.NONE.equals(storageAttributesKey)
				|| AttributesKey.ALL.equals(storageAttributesKey)
				|| AttributesKey.OTHER.equals(storageAttributesKey))
		{
			// discard the given attribueSetInstanceId if it is not about a "real" ASI.
			this.attributeSetInstanceId = 0;
		}
		else
		{
			this.attributeSetInstanceId = attributeSetInstanceId;
		}
	}

}
