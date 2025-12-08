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
public class MultiAttributeSetAttributeIdsList
{
	public static final MultiAttributeSetAttributeIdsList EMPTY = new MultiAttributeSetAttributeIdsList(ImmutableList.of());

	private final ImmutableMap<AttributeSetId, AttributeSetAttributeIdsList> byAttributeSetId;

	private MultiAttributeSetAttributeIdsList(final Collection<AttributeSetAttributeIdsList> lists)
	{
		this.byAttributeSetId = Maps.uniqueIndex(lists, AttributeSetAttributeIdsList::getAttributeSetId);
	}

	public static MultiAttributeSetAttributeIdsList of(final Collection<AttributeSetAttributeIdsList> lists)
	{
		return lists.isEmpty() ? EMPTY : new MultiAttributeSetAttributeIdsList(lists);
	}

	public Set<AttributeId> getAttributeIds()
	{
		return byAttributeSetId.values().stream()
				.flatMap(list -> list.getAttributeIdsInOrder().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	public AttributeSetAttributeIdsList getByAttributeSetId(@NonNull final AttributeSetId attributeSetId)
	{
		final AttributeSetAttributeIdsList list = byAttributeSetId.get(attributeSetId);
		if (list == null)
		{
			throw new AdempiereException("No AttributeSet found for " + attributeSetId);
		}
		return list;
	}
}
