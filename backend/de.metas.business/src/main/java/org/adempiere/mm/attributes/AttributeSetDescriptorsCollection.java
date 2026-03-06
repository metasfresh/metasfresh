package org.adempiere.mm.attributes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.Set;

@EqualsAndHashCode
@ToString
public class AttributeSetDescriptorsCollection
{
	public static final AttributeSetDescriptorsCollection EMPTY = new AttributeSetDescriptorsCollection(ImmutableList.of());

	private final ImmutableMap<AttributeSetId, AttributeSetDescriptor> byAttributeSetId;

	private AttributeSetDescriptorsCollection(final Collection<AttributeSetDescriptor> lists)
	{
		this.byAttributeSetId = Maps.uniqueIndex(lists, AttributeSetDescriptor::getAttributeSetId);
	}

	public static AttributeSetDescriptorsCollection of(final Collection<AttributeSetDescriptor> lists)
	{
		return lists.isEmpty() ? EMPTY : new AttributeSetDescriptorsCollection(lists);
	}

	public Set<AttributeId> getAttributeIds()
	{
		return byAttributeSetId.values().stream()
				.flatMap(list -> list.getAttributeIdsInOrder().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	public AttributeSetDescriptor getByAttributeSetId(@NonNull final AttributeSetId attributeSetId)
	{
		final AttributeSetDescriptor list = byAttributeSetId.get(attributeSetId);
		if (list == null)
		{
			throw new AdempiereException("No AttributeSet found for " + attributeSetId);
		}
		return list;
	}
}
