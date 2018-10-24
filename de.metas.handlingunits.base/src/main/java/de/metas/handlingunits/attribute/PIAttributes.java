package de.metas.handlingunits.attribute;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ToString
public final class PIAttributes implements Iterable<I_M_HU_PI_Attribute>
{
	public static final PIAttributes of(@NonNull final Collection<I_M_HU_PI_Attribute> piAttributes)
	{
		return new PIAttributes(piAttributes);
	}

	private final ImmutableList<I_M_HU_PI_Attribute> list;
	private final ImmutableMap<AttributeId, I_M_HU_PI_Attribute> attributesByAttributeId;

	private PIAttributes(@NonNull final Collection<I_M_HU_PI_Attribute> piAttributes)
	{
		list = ImmutableList.copyOf(piAttributes);
		attributesByAttributeId = Maps.uniqueIndex(piAttributes, piAttribute -> AttributeId.ofRepoId(piAttribute.getM_Attribute_ID()));
	}

	@Override
	public Iterator<I_M_HU_PI_Attribute> iterator()
	{
		return list.iterator();
	}

	// public List<I_M_HU_PI_Attribute> toList()
	// {
	// return list;
	// }

	public boolean hasActiveAttribute(@NonNull final AttributeId attributeId)
	{
		final I_M_HU_PI_Attribute piAttribute = getByAttributeIdOrNull(attributeId);
		return piAttribute != null && piAttribute.isActive();
	}

	public I_M_HU_PI_Attribute getByAttributeId(final AttributeId attributeId)
	{
		final I_M_HU_PI_Attribute piAttribute = getByAttributeIdOrNull(attributeId);
		if (piAttribute == null)
		{
			throw new AdempiereException("No " + attributeId + " found. Available attributeIds are:  " + getAttributeIds());
		}
		return piAttribute;
	}

	public Optional<I_M_HU_PI_Attribute> getByAttributeIdIfExists(final AttributeId attributeId)
	{
		final I_M_HU_PI_Attribute piAttribute = getByAttributeIdOrNull(attributeId);
		return Optional.ofNullable(piAttribute);
	}

	private I_M_HU_PI_Attribute getByAttributeIdOrNull(final AttributeId attributeId)
	{
		return attributesByAttributeId.get(attributeId);
	}

	public PIAttributes addIfAbsent(@NonNull final PIAttributes from)
	{
		final LinkedHashMap<AttributeId, I_M_HU_PI_Attribute> piAttributesNew = new LinkedHashMap<>(attributesByAttributeId);
		from.attributesByAttributeId.forEach(piAttributesNew::putIfAbsent);

		return new PIAttributes(piAttributesNew.values());
	}

	public int getSeqNoByAttributeId(final AttributeId attributeId, final int seqNoIfNotFound)
	{
		final I_M_HU_PI_Attribute piAttribute = getByAttributeIdOrNull(attributeId);
		if (piAttribute == null)
		{
			return seqNoIfNotFound;
		}
		return piAttribute.getSeqNo();
	}

	public ImmutableSet<AttributeId> getAttributeIds()
	{
		return attributesByAttributeId.keySet();
	}
}
