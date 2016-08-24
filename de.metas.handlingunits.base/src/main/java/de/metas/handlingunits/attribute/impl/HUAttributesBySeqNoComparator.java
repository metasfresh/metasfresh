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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Comparator;

import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;

/**
 * Comparator which is ordering {@link I_M_HU_Attribute} by their {@link I_M_HU_PI_Attribute#getSeqNo()}.
 *
 * @author tsa
 *
 */
public final class HUAttributesBySeqNoComparator implements Comparator<I_M_HU_Attribute>
{
	public static final transient HUAttributesBySeqNoComparator instance = new HUAttributesBySeqNoComparator();

	private HUAttributesBySeqNoComparator()
	{
		super();
	}

	@Override
	public int compare(final I_M_HU_Attribute o1, final I_M_HU_Attribute o2)
	{
		// Same instance
		if(o1 == o2)
		{
			return 0;
		}
		
		// note: M_HU_Attribute.M_HU_PI_Attribute_ID is "uber" mandatory and SeqNo is an integer,
		// so i'm not checking for null and stuff
		int seqNo1 = o1.getM_HU_PI_Attribute().getSeqNo();
		int seqNo2 = o2.getM_HU_PI_Attribute().getSeqNo();

		// if the seqNo is zero/null, the attribute shall be last
		seqNo1 = seqNo1 == 0 ? Integer.MAX_VALUE : seqNo1;
		seqNo2 = seqNo2 == 0 ? Integer.MAX_VALUE : seqNo2;

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
}
