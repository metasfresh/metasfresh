package org.adempiere.mm.attributes.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.Attribute;

import javax.annotation.Nullable;
import java.util.List;

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
	public Attribute getAttributeByCode(final AttributeCode attributeCode)
	{
		final Attribute attribute = getAttributeByCodeOrNull(attributeCode);
		if (attribute == null)
		{
			throw new AdempiereException("No active attribute found for `" + attributeCode + "`");
		}
		return attribute;
	}

	public Attribute getAttributeByCodeOrNull(final AttributeCode attributeCode)
	{
		return attributesByCode.get(attributeCode);
	}

	@Nullable
	public AttributeId getAttributeIdByCodeOrNull(@NonNull final AttributeCode attributeCode)
	{
		final Attribute attribute = getAttributeByCodeOrNull(attributeCode);
		return attribute != null ? attribute.getAttributeId() : null;
	}

	@NonNull
	public AttributeCode getAttributeCodeById(@NonNull final AttributeId id)
	{
		return getAttributeById(id).getAttributeCode();
	}

	@NonNull
	public Attribute getAttributeById(@NonNull final AttributeId id)
	{
		final Attribute attribute = attributesById.get(id);
		if (attribute == null)
		{
			throw new AdempiereException("No Attribute found for ID: " + id);
		}
		return attribute;
	}

	@NonNull
	public AttributeId getAttributeIdByCode(@NonNull final AttributeCode attributeCode)
	{
		return getAttributeByCode(attributeCode).getAttributeId();
	}

	@NonNull
	public ImmutableList<AttributeCode> getOrderedAttributeCodesByIds(@NonNull final List<AttributeId> orderedAttributeIds)
	{
		if (orderedAttributeIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return orderedAttributeIds.stream()
				.map(this::getAttributeCodeById)
				.collect(ImmutableList.toImmutableList());
	}

}
