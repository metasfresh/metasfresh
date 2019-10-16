package de.metas.material.commons.attributes;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/*
 * #%L
 * metasfresh-material-commons
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

public class AttributesKeyPartPatternTest
{
	private ImmutableList<AttributesKeyPartPattern> samplePartsToTest = ImmutableList.of(
			AttributesKeyPartPattern.ALL,
			AttributesKeyPartPattern.OTHER,
			AttributesKeyPartPattern.NONE,
			AttributesKeyPartPattern.ofAttributeValueId(AttributeValueId.ofRepoId(1234)),
			AttributesKeyPartPattern.ofAttributeIdAndValue(AttributeId.ofRepoId(111), "valueStr"),
			AttributesKeyPartPattern.ofAttributeId(AttributeId.ofRepoId(111)));

	@Test
	public void makeSureToStringWorksForEachType()
	{
		final ImmutableMap<AttributeKeyPartPatternType, AttributesKeyPartPattern> //
		samplePartsByType = Maps.uniqueIndex(samplePartsToTest, AttributesKeyPartPattern::getType);

		for (AttributeKeyPartPatternType type : AttributeKeyPartPatternType.values())
		{
			final AttributesKeyPartPattern samplePart = samplePartsByType.get(type);
			if (samplePart == null)
			{
				throw new AdempiereException("No sample part defined for " + type);
			}

			// make sure runs correctly and does not fail
			samplePartsByType.toString();
		}
	}
}
