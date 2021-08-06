/*
 * #%L
 * de.metas.elasticsearch
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

package de.metas.fulltextsearch.config;

import org.assertj.core.api.Assertions;
import org.compiere.util.Evaluatees;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ESDocumentToIndexTemplateTest
{
	@Nested
	class resolve
	{
		ESDocumentToIndexTemplate template = ESDocumentToIndexTemplate.ofJsonString("{ \"property\": @PropertyValue@ }");

		@Test
		void nullValue()
		{
			Assertions.assertThat(
					template.resolve(Evaluatees.ofSingleton("PropertyValue", null), "123"))
					.usingRecursiveComparison()
					.isEqualTo(ESDocumentToIndex.builder().documentId("123").json("{ \"property\": null }").build());
		}

		@Test
		void missingValue()
		{
			Assertions.assertThat(
					template.resolve(Evaluatees.empty(), "123"))
					.usingRecursiveComparison()
					.isEqualTo(ESDocumentToIndex.builder().documentId("123").json("{ \"property\": null }").build());
		}

		@Test
		void stringValue()
		{
			Assertions.assertThat(
					template.resolve(Evaluatees.ofSingleton("PropertyValue", "string \"quoted\" value"), "123"))
					.usingRecursiveComparison()
					.isEqualTo(ESDocumentToIndex.builder().documentId("123").json("{ \"property\": \"string \\\"quoted\\\" value\" }").build());
		}

		@Test
		void intValue()
		{
			Assertions.assertThat(
					template.resolve(Evaluatees.ofSingleton("PropertyValue", 345), "123"))
					.usingRecursiveComparison()
					.isEqualTo(ESDocumentToIndex.builder().documentId("123").json("{ \"property\": 345 }").build());
		}

		@Test
		void longValue()
		{
			Assertions.assertThat(
					template.resolve(Evaluatees.ofSingleton("PropertyValue", 345L), "123"))
					.usingRecursiveComparison()
					.isEqualTo(ESDocumentToIndex.builder().documentId("123").json("{ \"property\": 345 }").build());
		}

		@Test
		void bigDecimalValue()
		{
			Assertions.assertThat(
					template.resolve(Evaluatees.ofSingleton("PropertyValue", new BigDecimal("567.89")), "123"))
					.usingRecursiveComparison()
					.isEqualTo(ESDocumentToIndex.builder().documentId("123").json("{ \"property\": 567.89 }").build());
		}

		@Test
		void booleanValue()
		{
			Assertions.assertThat(
					template.resolve(Evaluatees.ofSingleton("PropertyValue", true), "123"))
					.usingRecursiveComparison()
					.isEqualTo(ESDocumentToIndex.builder().documentId("123").json("{ \"property\": true }").build());
		}

	}
}