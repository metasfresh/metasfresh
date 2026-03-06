/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.adempiere.mm.attributes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.lang.SOTrx;
import de.metas.util.OptionalBoolean;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.mm.attributes.api.Attribute;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Getter
@EqualsAndHashCode
@ToString(of = "byId")
public final class AttributeSetDescriptor
{
	public static final AttributeSetDescriptor NONE = AttributeSetDescriptor.builder()
			.attributeSetId(AttributeSetId.NONE)
			.name("NONE")
			.isInstanceAttribute(false)
			.mandatoryType(AttributeSetMandatoryType.NotMandatory)
			.attributes(ImmutableList.of())
			.build();

	@NonNull private final AttributeSetId attributeSetId;
	@NonNull private final String name;
	@Nullable private final String description;
	private final boolean isInstanceAttribute;
	@NonNull private final AttributeSetMandatoryType mandatoryType;

	@NonNull private final ImmutableList<AttributeId> attributeIdsInOrder;
	@NonNull @Getter(AccessLevel.NONE) private final ImmutableMap<AttributeId, AttributeSetAttribute> byId;

	@Builder
	private AttributeSetDescriptor(
			@NonNull final AttributeSetId attributeSetId,
			@NonNull final String name,
			@Nullable final String description,
			final boolean isInstanceAttribute,
			@NonNull final AttributeSetMandatoryType mandatoryType,
			@NonNull final List<AttributeSetAttribute> attributes)
	{
		this.attributeSetId = attributeSetId;
		this.name = name;
		this.description = description;
		this.isInstanceAttribute = isInstanceAttribute;
		this.mandatoryType = mandatoryType;
		this.attributeIdsInOrder = attributes.stream()
				.sorted(Comparator.comparing(AttributeSetAttribute::getSeqNo))
				.map(AttributeSetAttribute::getAttributeId)
				.collect(ImmutableList.toImmutableList());
		this.byId = Maps.uniqueIndex(attributes, AttributeSetAttribute::getAttributeId);
	}

	public ImmutableList<Attribute> getAttributesInOrder()
	{
		return attributeIdsInOrder.stream()
				.map(byId::get)
				.map(AttributeSetAttribute::getAttribute)
				.collect(ImmutableList.toImmutableList());
	}

	public boolean contains(@NonNull final AttributeId attributeId)
	{
		return byId.containsKey(attributeId);
	}

	public Optional<AttributeSetAttribute> getByAttributeId(@NonNull final AttributeId attributeId)
	{
		return Optional.ofNullable(byId.get(attributeId));
	}

	public OptionalBoolean getMandatoryOnReceipt(@NonNull final AttributeId attributeId)
	{
		return getByAttributeId(attributeId)
				.map(AttributeSetAttribute::getMandatoryOnReceipt)
				.orElse(OptionalBoolean.UNKNOWN);
	}

	public OptionalBoolean getMandatoryOnShipment(@NonNull final AttributeId attributeId)
	{
		return getByAttributeId(attributeId)
				.map(AttributeSetAttribute::getMandatoryOnShipment)
				.orElse(OptionalBoolean.UNKNOWN);
	}

	public OptionalBoolean getMandatoryOnPicking(@NonNull final AttributeId attributeId)
	{
		return getByAttributeId(attributeId)
				.map(AttributeSetAttribute::getMandatoryOnPicking)
				.orElse(OptionalBoolean.UNKNOWN);
	}

	public boolean isASIMandatory(@NonNull final SOTrx soTrx)
	{
		if (!isInstanceAttribute)
		{
			return false;
		}
		else
		{
			return mandatoryType.isASIMandatory(soTrx);
		}
	}
}
