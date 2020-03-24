package de.metas.handlingunits.material.interceptor;

import java.time.Instant;
import java.util.HashMap;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeId;

import java.util.Objects;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class HUAttributeChanges
{
	@Getter
	private final HuId huId;
	private final HashMap<AttributeId, HUAttributeChange> attributes = new HashMap<>();

	@Getter
	private Instant lastChangeDate;

	public HUAttributeChanges(@NonNull final HuId huId)
	{
		this.huId = huId;
	}

	public void collect(@NonNull HUAttributeChange change)
	{
		Check.assumeEquals(huId, change.getHuId(), "Invalid HuId for {}. Expected: {}", change, huId);

		attributes.compute(change.getAttributeId(), (attributeId, previousChange) -> mergeChange(previousChange, change));

		lastChangeDate = max(lastChangeDate, change.getDate());
	}

	private static Instant max(@Nullable final Instant instant1, @Nullable final Instant instant2)
	{
		if (instant1 == null)
		{
			return instant2;
		}
		else if (instant2 == null)
		{
			return null;
		}
		else if (instant1.isAfter(instant2))
		{
			return instant1;
		}
		else
		{
			return instant2;
		}
	}

	private static HUAttributeChange mergeChange(
			@Nullable final HUAttributeChange previousChange,
			@NonNull final HUAttributeChange currentChange)
	{
		return previousChange != null
				? previousChange.mergeWithNextChange(currentChange)
				: currentChange;
	}

	public boolean isEmpty()
	{
		return attributes.isEmpty();
	}

	public AttributesKey getOldAttributesKey()
	{
		return toAttributesKey(HUAttributeChange::getOldAttributeKeyPartOrNull);
	}

	public AttributesKey getNewAttributesKey()
	{
		return toAttributesKey(HUAttributeChange::getNewAttributeKeyPartOrNull);
	}

	private AttributesKey toAttributesKey(final Function<HUAttributeChange, AttributesKeyPart> keyPartExtractor)
	{
		if (attributes.isEmpty())
		{
			return AttributesKey.NONE;
		}

		final ImmutableList<AttributesKeyPart> parts = attributes.values()
				.stream()
				.map(keyPartExtractor)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		return AttributesKey.ofParts(parts);
	}
}
