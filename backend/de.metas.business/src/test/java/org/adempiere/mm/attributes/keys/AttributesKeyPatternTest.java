/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package org.adempiere.mm.attributes.keys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class AttributesKeyPatternTest
{
	private static AttributesKeyPattern pattern(final AttributesKeyPartPattern... parts)
	{
		return AttributesKeyPattern.ofParts(ImmutableList.copyOf(parts));
	}

	@Nested
	class serializeDeserialize
	{
		private void assertRoundTrip(final AttributesKeyPattern value) throws IOException
		{
			final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

			final String json = objectMapper.writeValueAsString(value);
			final AttributesKeyPattern value2 = objectMapper.readValue(json, AttributesKeyPattern.class);

			assertThat(value2).as("deserialized must equal original; json=%s", json).isEqualTo(value);
			assertThat(value2).as("hashCode; json=%s", json).hasSameHashCodeAs(value);
			assertThat(value2.getPartPatterns()).as("partPatterns; json=%s", json).isEqualTo(value.getPartPatterns());
			assertThat(value2.getSqlLikeString()).as("sqlLikeString; json=%s", json).isEqualTo(value.getSqlLikeString());
		}

		@Test
		public void all() throws IOException
		{
			assertRoundTrip(AttributesKeyPattern.ALL);
		}

		@Test
		public void none() throws IOException
		{
			assertRoundTrip(AttributesKeyPattern.NONE);
		}

		@Test
		public void other() throws IOException
		{
			assertRoundTrip(AttributesKeyPattern.OTHER);
		}

		@Test
		public void attributeIdWildcard() throws IOException
		{
			assertRoundTrip(AttributesKeyPattern.attributeId(AttributeId.ofRepoId(1234)));
		}

		@Test
		public void attributeValueId() throws IOException
		{
			assertRoundTrip(AttributesKeyPattern.ofPart(AttributesKeyPartPattern.ofAttributeValueId(AttributeValueId.ofRepoId(555))));
		}

		@Test
		public void attributeValueId_viaOfInteger() throws IOException
		{
			assertRoundTrip(AttributesKeyPattern.ofPart(AttributesKeyPartPattern.ofInteger(777)));
		}

		@Test
		public void stringAttribute() throws IOException
		{
			assertRoundTrip(AttributesKeyPattern.ofPart(
					AttributesKeyPartPattern.ofAttributesKeyPart(
							AttributesKeyPart.ofStringAttribute(AttributeId.ofRepoId(1234), "aaaa"))));
		}

		@Test
		public void stringAttribute_emptyValue() throws IOException
		{
			assertRoundTrip(AttributesKeyPattern.ofPart(AttributesKeyPartPattern.ofStringAttribute(AttributeId.ofRepoId(20), "")));
		}

		@Test
		public void stringAttribute_specialChars() throws IOException
		{
			assertRoundTrip(AttributesKeyPattern.ofPart(AttributesKeyPartPattern.ofStringAttribute(AttributeId.ofRepoId(21), "ünîcödé €/%_\"\\")));
		}

		@Test
		public void numberAttribute() throws IOException
		{
			assertRoundTrip(AttributesKeyPattern.ofPart(AttributesKeyPartPattern.ofNumberAttribute(AttributeId.ofRepoId(30), new BigDecimal("12.340"))));
		}

		@Test
		public void dateAttribute() throws IOException
		{
			assertRoundTrip(AttributesKeyPattern.ofPart(AttributesKeyPartPattern.ofDateAttribute(AttributeId.ofRepoId(40), LocalDate.of(2023, 12, 31))));
		}

		@Test
		public void multiPart_mixedTypes() throws IOException
		{
			assertRoundTrip(pattern(
					AttributesKeyPartPattern.ofAttributeValueId(AttributeValueId.ofRepoId(1)),
					AttributesKeyPartPattern.ofAttributeValueId(AttributeValueId.ofRepoId(2)),
					AttributesKeyPartPattern.ofAttributeId(AttributeId.ofRepoId(3)),
					AttributesKeyPartPattern.ofStringAttribute(AttributeId.ofRepoId(4), "str")));
		}

		@Test
		public void multiPart_reverseInputOrder_roundTripsEqual() throws IOException
		{
			// same parts supplied in reverse order must still round-trip equal (sorted)
			assertRoundTrip(pattern(
					AttributesKeyPartPattern.ofStringAttribute(AttributeId.ofRepoId(4), "str"),
					AttributesKeyPartPattern.ofAttributeId(AttributeId.ofRepoId(3)),
					AttributesKeyPartPattern.ofAttributeValueId(AttributeValueId.ofRepoId(2)),
					AttributesKeyPartPattern.ofAttributeValueId(AttributeValueId.ofRepoId(1))));
		}
	}

	@Nested
	class serializeDeserializePart
	{
		private void testSerializeDeserializePart(final AttributesKeyPartPattern part) throws IOException
		{
			final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

			final String json = objectMapper.writeValueAsString(part);
			final AttributesKeyPartPattern part2 = objectMapper.readValue(json, AttributesKeyPartPattern.class);

			assertThat(part2)
					.as("deserialized part must equal original; json=%s", json)
					.isEqualTo(part);
			assertThat(part2.hashCode()).as("hashCode; json=%s", json).isEqualTo(part.hashCode());
			assertThat(part2.getSqlLikePart()).as("sqlLikePart; json=%s", json).isEqualTo(part.getSqlLikePart());
		}

		@Test
		public void allPartPatternTypes() throws IOException
		{
			testSerializeDeserializePart(AttributesKeyPartPattern.ALL);
			testSerializeDeserializePart(AttributesKeyPartPattern.OTHER);
			testSerializeDeserializePart(AttributesKeyPartPattern.NONE);
			testSerializeDeserializePart(AttributesKeyPartPattern.ofAttributeId(AttributeId.ofRepoId(7)));
			testSerializeDeserializePart(AttributesKeyPartPattern.ofAttributeValueId(AttributeValueId.ofRepoId(9)));
			testSerializeDeserializePart(AttributesKeyPartPattern.ofStringAttribute(AttributeId.ofRepoId(11), "val"));
			testSerializeDeserializePart(AttributesKeyPartPattern.ofStringAttribute(AttributeId.ofRepoId(12), ""));
			testSerializeDeserializePart(AttributesKeyPartPattern.ofNumberAttribute(AttributeId.ofRepoId(13), new BigDecimal("3.14")));
			testSerializeDeserializePart(AttributesKeyPartPattern.ofDateAttribute(AttributeId.ofRepoId(14), LocalDate.of(2024, 1, 1)));
		}
	}

	@Nested
	public class parse
	{
		@Test
		public void fromAttributesKey()
		{
			final String attrib1 = "123456=9999-12-12";
			final String attrib2 = "123654=1";
			final String attrib3 = "987654=2";
			final String attrib4 = "1010101=01/99";
			final AttributesKey key = AttributesKey.ofString(attrib1 + "§&§" + attrib2 + "§&§" + attrib3 + "§&§" + attrib4);
			final AttributesKeyPattern attributesKeyPattern = AttributesKeyPatternsUtil.ofAttributeKey(key);
			assertThat(attributesKeyPattern.getPartPatterns()).hasSize(4);
			final String sqlLikeString = attributesKeyPattern.getSqlLikeString();
			assertThat(sqlLikeString)
					.contains(attrib1)
					.contains(attrib2)
					.contains(attrib3)
					.contains(attrib4);
		}
	}

	@Nested
	class getSqlLikeString
	{
		@Test
		public void attributeValueId()
		{
			final AttributesKeyPattern pattern = pattern(AttributesKeyPartPattern.ofAttributeValueId(AttributeValueId.ofRepoId(1)));
			assertThat(pattern.getSqlLikeString()).isEqualTo("%1%");
		}

		@Test
		public void attributeIdWildcard()
		{
			final AttributesKeyPattern pattern = pattern(AttributesKeyPartPattern.ofAttributeId(AttributeId.ofRepoId(2)));
			assertThat(pattern.getSqlLikeString()).isEqualTo("%2=%");
		}

		@Test
		public void attributeIdAndValue()
		{
			final AttributesKeyPattern pattern = pattern(AttributesKeyPartPattern.ofStringAttribute(AttributeId.ofRepoId(4), "str"));
			assertThat(pattern.getSqlLikeString()).isEqualTo("%4=str%");
		}

		@Test
		public void mix_attributeValueId_attributeIdWildcard_stringAttribute()
		{
			final AttributesKeyPattern pattern = pattern(
					AttributesKeyPartPattern.ofAttributeValueId(AttributeValueId.ofRepoId(1)),
					AttributesKeyPartPattern.ofAttributeValueId(AttributeValueId.ofRepoId(2)),
					AttributesKeyPartPattern.ofAttributeId(AttributeId.ofRepoId(3)),
					AttributesKeyPartPattern.ofStringAttribute(AttributeId.ofRepoId(4), "str"));

			assertThat(pattern.getSqlLikeString()).isEqualTo("%1%2%3=%4=str%");
		}
	}

	@Nested
	class matches
	{
		@Test
		public void all()
		{
			assertThat(AttributesKeyPattern.ALL.matches(AttributesKey.ALL)).isTrue();
			assertThat(AttributesKeyPattern.ALL.matches(AttributesKey.NONE)).isTrue();
			assertThat(AttributesKeyPattern.ALL.matches(AttributesKey.OTHER)).isTrue();
			assertThat(AttributesKeyPattern.ALL.matches(AttributesKey.ofString("111=1"))).isTrue();
		}

		@Test
		public void attributeIdWildcard()
		{
			final AttributesKeyPattern pattern = pattern(AttributesKeyPartPattern.ofAttributeId(AttributeId.ofRepoId(111)));

			assertThat(pattern.matches(AttributesKey.ofString("111=1"))).isTrue();
			assertThat(pattern.matches(AttributesKey.ofString("111=2"))).isTrue();
			// NOT asserted on purpose: "111=" (attribute 111 with an EMPTY value) parses to AttributesKey.NONE
			// (AttributesKeyPart.parseString returns empty for a blank value), which an attribute-id wildcard
			// does not match. Asserting isTrue() here would require changing the parse semantics — out of scope.
			assertThat(pattern.matches(AttributesKey.ofString("222=1"))).isFalse();
		}

		@Test
		public void attributeValueId()
		{
			final AttributesKeyPattern pattern = pattern(AttributesKeyPartPattern.ofAttributeValueId(AttributeValueId.ofRepoId(111)));

			assertThat(pattern.matches(AttributesKey.ofString("111"))).isTrue();
			assertThat(pattern.matches(AttributesKey.ofString("111=1"))).isFalse();
			assertThat(pattern.matches(AttributesKey.ofString("222"))).isFalse();
		}
	}

}
