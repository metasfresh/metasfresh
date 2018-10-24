package de.metas.handlingunits.attribute.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Comparator;

import org.adempiere.mm.attributes.AttributeId;

import de.metas.handlingunits.attribute.PIAttributes;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import lombok.NonNull;
import lombok.ToString;

/**
 * Comparator which is ordering {@link I_M_HU_Attribute} by their {@link I_M_HU_PI_Attribute#getSeqNo()}.
 *
 * @author tsa
 *
 */
@ToString
public final class HUAttributesBySeqNoComparator implements Comparator<I_M_HU_Attribute>
{
	public static HUAttributesBySeqNoComparator of(PIAttributes piAttributes)
	{
		return new HUAttributesBySeqNoComparator(piAttributes);
	}

	private final PIAttributes piAttributes;

	private HUAttributesBySeqNoComparator(@NonNull final PIAttributes piAttributes)
	{
		this.piAttributes = piAttributes;
	}

	@Override
	public int compare(final I_M_HU_Attribute o1, final I_M_HU_Attribute o2)
	{
		// Same instance
		if (o1 == o2)
		{
			return 0;
		}

		final int seqNo1 = getSeqNo(o1);
		final int seqNo2 = getSeqNo(o2);
		if (seqNo1 < seqNo2)
		{
			return -1;
		}
		else if (seqNo1 == seqNo2)
		{
			// NOTE: basically those are equal but let's have a predictable order, so we are ordering them by M_Attribute_ID
			return o1.getM_Attribute_ID() - o2.getM_Attribute_ID();
		}
		// seqNo1 > seqNo2
		return 1;
	}

	private int getSeqNo(final I_M_HU_Attribute huAttribute)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(huAttribute.getM_Attribute_ID());
		final int seqNo = piAttributes.getSeqNoByAttributeId(attributeId, 0);
		// if the seqNo is zero/null, the attribute shall be last
		return seqNo != 0 ? seqNo : Integer.MAX_VALUE;
	}
}
