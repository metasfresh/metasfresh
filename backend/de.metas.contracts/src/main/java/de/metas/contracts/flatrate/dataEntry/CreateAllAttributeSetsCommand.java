/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.flatrate.dataEntry;

import com.google.common.collect.ImmutableList;
import de.metas.util.collections.MultiValueMap;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Takes a list of {@link AttributeListValue}s an creates {@link ImmutableAttributeSet}s for all their different combinations.
 */
@Value
public class CreateAllAttributeSetsCommand
{
	@NonNull
	List<AttributeListValue> attributeListValues;

	public static CreateAllAttributeSetsCommand withValues(@NonNull final List<AttributeListValue> attributeListValues)
	{
		return new CreateAllAttributeSetsCommand(attributeListValues);
	}

	@NonNull
	public List<ImmutableAttributeSet> run()
	{
		if (attributeListValues.isEmpty())
		{ // get this corner-case out of the way first
			return ImmutableList.of();
		}

		// group our attribute values
		final MultiValueMap<AttributeId, AttributeListValue> attributeId2Values = new MultiValueMap<>();
		final LinkedHashSet<AttributeId> attributeIdsSet = new LinkedHashSet<>();
		for (final AttributeListValue attributeListValue : attributeListValues)
		{
			attributeId2Values.add(attributeListValue.getAttributeId(), attributeListValue);
			attributeIdsSet.add(attributeListValue.getAttributeId());
		}
		final ImmutableList<AttributeId> attributeIds = attributeIdsSet.stream().collect(ImmutableList.toImmutableList());

		// we will create plenty of builders & eventually sets from this first one
		final ImmutableAttributeSet.Builder builder = ImmutableAttributeSet.builder();

		final List<ImmutableAttributeSet.Builder> builderList = recurse(0, attributeIds, attributeId2Values, builder);

		return builderList.stream()
				.map(ImmutableAttributeSet.Builder::build)
				.collect(ImmutableList.toImmutableList());
	}

	private List<ImmutableAttributeSet.Builder> recurse(
			final int currendAttributeIdIdx,
			@NonNull final List<AttributeId> attributeIds,
			@NonNull final MultiValueMap<AttributeId, AttributeListValue> attributeId2Values,
			@NonNull final ImmutableAttributeSet.Builder builder)
	{
		final LinkedList<ImmutableAttributeSet.Builder> result = new LinkedList<>();

		final AttributeId currentAttributeId = attributeIds.get(currendAttributeIdIdx);
		final List<AttributeListValue> valuesForCurrentAttribute = attributeId2Values.get(currentAttributeId);
		for (final AttributeListValue attributeListValue : valuesForCurrentAttribute)
		{
			final ImmutableAttributeSet.Builder copy = builder.createCopy();
			copy.attributeValue(attributeListValue);

			final int nextAttributeIdIdx = currendAttributeIdIdx + 1;
			final boolean listContainsMore = attributeIds.size() > nextAttributeIdIdx;

			if (listContainsMore)
			{
				result.addAll(recurse(nextAttributeIdIdx, attributeIds, attributeId2Values, copy));
			}
			else
			{
				result.add(copy);
			}
		}

		return result;
	}
}
