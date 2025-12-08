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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode
@ToString(of = "byId")
public final class AttributeSetAttributeIdsList
{
	@NonNull @Getter AttributeSetId attributeSetId;
	@NonNull @Getter private final ImmutableList<AttributeId> attributeIdsInOrder;
	@NonNull private final ImmutableMap<AttributeId, AttributeSetAttribute> byId;

	@Builder
	private AttributeSetAttributeIdsList(
			@NonNull AttributeSetId attributeSetId,
			@NonNull final List<AttributeSetAttribute> list)
	{
		this.attributeSetId = attributeSetId;
		this.attributeIdsInOrder = list.stream()
				.sorted(Comparator.comparing(AttributeSetAttribute::getSeqNo))
				.map(AttributeSetAttribute::getAttributeId)
				.collect(ImmutableList.toImmutableList());
		this.byId = Maps.uniqueIndex(list, AttributeSetAttribute::getAttributeId);
	}

	public static AttributeSetAttributeIdsList empty(@NonNull final AttributeSetId attributeSetId)
	{
		return AttributeSetAttributeIdsList.builder()
				.attributeSetId(attributeSetId)
				.list(ImmutableList.of())
				.build();
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
