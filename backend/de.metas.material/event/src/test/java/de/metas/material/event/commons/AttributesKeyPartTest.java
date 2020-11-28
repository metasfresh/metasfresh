package de.metas.material.event.commons;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * metasfresh-material-event
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

public class AttributesKeyPartTest
{
	static final ImmutableList<AttributesKeyPart> specialKeyParts = ImmutableList.of(AttributesKeyPart.ALL, AttributesKeyPart.OTHER, AttributesKeyPart.NONE);

	@Nested
	class normalizeValues
	{
		@Test
		void normalizeStringValue()
		{
			assertThat(AttributesKeyPart.normalizeStringValue(null)).isEqualTo("");
			assertThat(AttributesKeyPart.normalizeStringValue("")).isSameAs("");
			assertThat(AttributesKeyPart.normalizeStringValue("   abc   ")).isSameAs("   abc   ");
		}

		@Test
		void normalizeNumberValue()
		{
			assertThat(AttributesKeyPart.normalizeNumberValue(null)).isEqualTo("");
			assertThat(AttributesKeyPart.normalizeNumberValue(new BigDecimal("12.3450000"))).isEqualTo("12.345");
		}

		@Test
		void normalizeDateValue()
		{
			assertThat(AttributesKeyPart.normalizeDateValue(null)).isEqualTo("");
			assertThat(AttributesKeyPart.normalizeDateValue(LocalDate.of(2019, Month.SEPTEMBER, 2))).isEqualTo("2019-09-02");
		}
	}

	@Nested
	class parseString
	{
		@Test
		void specialCodes()
		{
			for (final AttributesKeyPart specialKeyPart : specialKeyParts)
			{
				assertThat(AttributesKeyPart.parseString(specialKeyPart.getStringRepresentation())).isSameAs(specialKeyPart);
			}
		}

		@Test
		void stringAttribute()
		{
			final AttributesKeyPart stringPart = AttributesKeyPart.ofStringAttribute(AttributeId.ofRepoId(111), "stringValue");
			assertThat(AttributesKeyPart.parseString(stringPart.getStringRepresentation())).isEqualTo(stringPart);
		}

		@Test
		void numberAttribute()
		{
			final AttributesKeyPart numberPart = AttributesKeyPart.ofNumberAttribute(AttributeId.ofRepoId(111), new BigDecimal("12.345000"));
			assertThat(AttributesKeyPart.parseString(numberPart.getStringRepresentation())).isEqualTo(numberPart);
		}

		@Test
		void dateAttribute()
		{
			final AttributesKeyPart datePart = AttributesKeyPart.ofDateAttribute(AttributeId.ofRepoId(111), LocalDate.of(2019, Month.SEPTEMBER, 2));
			assertThat(AttributesKeyPart.parseString(datePart.getStringRepresentation())).isEqualTo(datePart);
		}

		@Test
		void attributeValueId()
		{
			final AttributesKeyPart attributeValueIdPart = AttributesKeyPart.ofAttributeValueId(AttributeValueId.ofRepoId(1234));
			assertThat(AttributesKeyPart.parseString(attributeValueIdPart.getStringRepresentation())).isEqualTo(attributeValueIdPart);
		}
	}

	@Nested
	class factoryMethods
	{
		@Test
		void ofStringAttribute()
		{
			final AttributesKeyPart stringPart = AttributesKeyPart.ofStringAttribute(AttributeId.ofRepoId(111), "stringValue");
			assertThat(stringPart.getStringRepresentation()).isEqualTo("111=stringValue");
		}

		@Test
		void ofNumberAttribute()
		{
			final AttributesKeyPart numberPart = AttributesKeyPart.ofNumberAttribute(AttributeId.ofRepoId(111), new BigDecimal("12.345000"));
			assertThat(numberPart.getStringRepresentation()).isEqualTo("111=12.345");
		}

		@Test
		void ofDateAttribute()
		{
			final AttributesKeyPart datePart = AttributesKeyPart.ofDateAttribute(AttributeId.ofRepoId(111), LocalDate.of(2019, Month.SEPTEMBER, 2));
			assertThat(datePart.getStringRepresentation()).isEqualTo("111=2019-09-02");
		}

		@Test
		void ofAttributeValueId()
		{
			final AttributesKeyPart attributeValueIdPart = AttributesKeyPart.ofAttributeValueId(AttributeValueId.ofRepoId(1234));
			assertThat(attributeValueIdPart.getStringRepresentation()).isEqualTo("1234");
		}
	}

	@Nested
	class compareTests
	{
		final AttributesKeyPart regularAttributeValuePart = AttributesKeyPart.ofAttributeValueId(AttributeValueId.ofRepoId(1));
		final AttributesKeyPart stringAttributePart = AttributesKeyPart.ofStringAttribute(AttributeId.ofRepoId(1), "stringValue");

		@Test
		void specialCodesFirst()
		{
			for (final AttributesKeyPart specialPart : specialKeyParts)
			{
				assertThat(specialPart).usingDefaultComparator().isLessThan(regularAttributeValuePart);
				assertThat(specialPart).usingDefaultComparator().isLessThan(stringAttributePart);
			}
		}

		@Test
		void attributeValueParts_before_other_attributeParts()
		{
			assertThat(regularAttributeValuePart).usingDefaultComparator().isLessThan(stringAttributePart);
		}

	}
}
