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
@EqualsAndHashCode
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(name = "MaterialDescriptor", value = MaterialDescriptor.class) })
public class ProductDescriptor
{
	public static ProductDescriptor forProductIdAndAttributeSetInstanceId(final int productId, final int attributeSetInstanceId)
	{
		throw new UnsupportedOperationException();
	}

	public static final ProductDescriptor forProductIdOnly(final int productId)
	{
		return new ProductDescriptor(false, // complete == false
				productId,
				ProductDescriptor.STORAGE_ATTRIBUTES_KEY_UNSPECIFIED,
				-1);
	}

	public static final ProductDescriptor forProductIdAndEmptyAttribute(final int productId)
	{
		return new ProductDescriptor(true, productId, "", 0); // complete == true
	}

	public static final ProductDescriptor forProductAndAttributes(
			final int productId,
			@NonNull final String storageAttributesKey,
			final int attributeSetInstanceId)
	{
		return new ProductDescriptor(true, productId, storageAttributesKey, attributeSetInstanceId); // complete == true
	}

	public static final String STORAGE_ATTRIBUTES_KEY_UNSPECIFIED = new String("");

	/**
	 * The delimiter should not contain any character that has a "regexp" meaning
	 * and would interfere with {@link String#replaceAll(String, String)}.
	 */
	public static final String STORAGE_ATTRIBUTES_KEY_DELIMITER = "§&§";

	@JsonProperty
	boolean productDescriptorComplete;

	@Getter
	int productId;

	@Getter
	String storageAttributesKey;
	
	/**
	 * This ID is only here so that the candidate row's attributes can be displayed properly in the UI.
	 */
	@Getter
	int attributeSetInstanceId;

	@JsonCreator
	public ProductDescriptor(
			@JsonProperty("productDescriptorComplete") final boolean complete,
			@JsonProperty("productId") final int productId,
			@JsonProperty("storageAttributesKey") @NonNull final String storageAttributesKey,
			@JsonProperty("attributeSetInstanceId") final int attributeSetInstanceId)
	{
		this.productDescriptorComplete = complete;
		this.productId = productId;
		this.storageAttributesKey = storageAttributesKey;
		this.attributeSetInstanceId = attributeSetInstanceId;

		asssertCompleteness();
	}

	public ProductDescriptor asssertCompleteness()
	{
		if (productDescriptorComplete)
		{
			Preconditions.checkArgument(productId > 0,
					"Given parameter productId=%s needs to be >0, because complete=true", productId);
			Preconditions.checkArgument(attributeSetInstanceId >= 0,
					"Given parameter attributeSetInstanceId needs to >=0, because complete=true");
			Preconditions.checkNotNull(storageAttributesKey,
					"Given storageAttributeKey date needs to not-null, because complete=true");
		}
		return this;
	}

	@JsonProperty("productDescriptorComplete")
	public boolean isComplete()
	{
		return productDescriptorComplete;
	}
}
