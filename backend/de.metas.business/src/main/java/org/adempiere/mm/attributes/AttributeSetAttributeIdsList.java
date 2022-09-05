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
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString(of = "byId")
public final class AttributeSetAttributeIdsList
{
	public static AttributeSetAttributeIdsList ofList(@NonNull final List<AttributeSetAttribute> list)
	{
		return !list.isEmpty() ? new AttributeSetAttributeIdsList(list) : EMPTY;
	}

	public static Collector<AttributeSetAttribute, ?, AttributeSetAttributeIdsList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(AttributeSetAttributeIdsList::ofList);
	}

	public static final AttributeSetAttributeIdsList EMPTY = new AttributeSetAttributeIdsList(ImmutableList.of());

	@Getter
	private final ImmutableList<AttributeId> attributeIdsInOrder;
	private final ImmutableMap<AttributeId, AttributeSetAttribute> byId;

	private AttributeSetAttributeIdsList(@NonNull final List<AttributeSetAttribute> list)
	{
		attributeIdsInOrder = list.stream()
				.sorted(Comparator.comparing(AttributeSetAttribute::getSeqNo))
				.map(AttributeSetAttribute::getAttributeId)
				.collect(ImmutableList.toImmutableList());
		byId = Maps.uniqueIndex(list, AttributeSetAttribute::getAttributeId);
	}

	public boolean contains(@NonNull final AttributeId attributeId)
	{
		return byId.containsKey(attributeId);
	}

	public Optional<AttributeSetAttribute> getByAttributeId(@NonNull final AttributeId attributeId)
	{
		return Optional.ofNullable(byId.get(attributeId));
	}
}
