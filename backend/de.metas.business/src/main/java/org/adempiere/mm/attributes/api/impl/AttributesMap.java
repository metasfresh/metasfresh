package org.adempiere.mm.attributes.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.Attribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@ToString
class AttributesMap
{
	private final ImmutableMap<AttributeId, Attribute> attributesById;
	private final ImmutableMap<AttributeCode, Attribute> attributesByCode;

	AttributesMap(@NonNull final List<Attribute> attributes)
	{
		this.attributesById = Maps.uniqueIndex(attributes, Attribute::getAttributeId);
		this.attributesByCode = Maps.uniqueIndex(attributes, Attribute::getAttributeCode);
	}

	@NonNull
	public Attribute getByCode(final AttributeCode attributeCode)
	{
		final Attribute attribute = getByCodeOrNull(attributeCode);
		if (attribute == null)
		{
			throw new AdempiereException("No active attribute found for `" + attributeCode + "`");
		}
		return attribute;
	}

	public Attribute getByCodeOrNull(final AttributeCode attributeCode)
	{
		return attributesByCode.get(attributeCode);
	}

	@NonNull
	public Attribute getById(@NonNull final AttributeId id)
	{
		final Attribute attribute = getByIdOrNull(id);
		if (attribute == null)
		{
			throw new AdempiereException("No Attribute found for ID: " + id);
		}
		return attribute;
	}

	@Nullable
	public Attribute getByIdOrNull(final @NotNull AttributeId id)
	{
		return attributesById.get(id);
	}

	@NonNull
	public Set<Attribute> getByIds(@NonNull final Collection<AttributeId> ids)
	{
		if (ids.isEmpty()) {return ImmutableSet.of();}

		return ids.stream()
				.distinct()
				.map(this::getById)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public AttributeId getIdByCode(@NonNull final AttributeCode attributeCode)
	{
		return getByCode(attributeCode).getAttributeId();
	}

	public Set<Attribute> getByCodes(final Set<AttributeCode> attributeCodes)
	{
		if (attributeCodes.isEmpty()) {return ImmutableSet.of();}

		return attributeCodes.stream()
				.distinct()
				.map(this::getByCode)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public ImmutableList<AttributeCode> getOrderedAttributeCodesByIds(@NonNull final List<AttributeId> orderedAttributeIds)
	{
		if (orderedAttributeIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return orderedAttributeIds.stream()
				.map(this::getById)
				.filter(Attribute::isActive)
				.map(Attribute::getAttributeCode)
				.collect(ImmutableList.toImmutableList());
	}

	public Stream<Attribute> streamActive()
	{
		return attributesById.values().stream().filter(Attribute::isActive);
	}

	public boolean isActiveAttribute(final AttributeId id)
	{
		final Attribute attribute = getByIdOrNull(id);
		return attribute != null && attribute.isActive();
	}

}
