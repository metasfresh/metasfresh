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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.StringLikeFilter;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.model.ModelColumn;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.material.event.commons.AttributesKeyPart;

public class AttributesKeyQueryHelperTest
{
	private interface DummyModel
	{
		@SuppressWarnings("unused")
		String Table_Name = "DummyModel";
		ModelColumn<DummyModel, Object> COLUMN_StorageAttributesKey = new ModelColumn<>(DummyModel.class, "StorageAttributesKey", null);
	}

	private static AttributesKeyQueryHelper<DummyModel> newQueryHelper()
	{
		return AttributesKeyQueryHelper.createFor(DummyModel.COLUMN_StorageAttributesKey);
	}

	private static StringLikeFilter<DummyModel> newStringLikeFilter(final String likeExpression)
	{
		final boolean ignoreCase = false;
		return new StringLikeFilter<>(DummyModel.COLUMN_StorageAttributesKey.getColumnName(), likeExpression, ignoreCase);
	}

	private static AttributesKeyPattern pattern(final AttributesKeyPart part)
	{
		return AttributesKeyPattern.ofPart(AttributesKeyPartPattern.ofAttributesKeyPart(part));
	}

	private static IQueryFilter<DummyModel> createFilter(final AttributesKeyPattern... patterns)
	{
		final AttributesKeyQueryHelper<DummyModel> queryHelper = newQueryHelper();
		return queryHelper.createFilter(ImmutableList.copyOf(patterns));
	}

	@Nested
	public class createFilter
	{
		@Test
		public void empty()
		{
			assertThatThrownBy(() -> createFilter())
					.hasMessageContaining("is not empty");
		}

		@Test
		public void just_ALL()
		{
			final IQueryFilter<DummyModel> filter = createFilter(AttributesKeyPattern.ALL);
			assertThat(filter).isSameAs(ConstantQueryFilter.of(true));
		}

		@Test
		public void ALL_and_OTHERS()
		{
			final IQueryFilter<DummyModel> filter = createFilter(
					AttributesKeyPattern.ALL,
					AttributesKeyPattern.OTHER);

			assertThat(filter).isSameAs(ConstantQueryFilter.of(true));
		}

		@Test
		public void ALL_and_AnythingElse()
		{
			final IQueryFilter<DummyModel> filter = createFilter(
					AttributesKeyPattern.ALL,
					AttributesKeyPattern.attributeId(AttributeId.ofRepoId(111)),
					AttributesKeyPattern.attributeId(AttributeId.ofRepoId(222)),
					AttributesKeyPattern.attributeId(AttributeId.ofRepoId(333)));

			assertThat(filter).isSameAs(ConstantQueryFilter.of(true));
		}

		@Test
		public void just_OTHERS()
		{
			final IQueryFilter<DummyModel> filter = createFilter(AttributesKeyPattern.OTHER);
			assertThat(filter).isSameAs(ConstantQueryFilter.of(true));
		}

		@Test
		public void OTHERS_and_AnythingElse()
		{
			final IQueryFilter<DummyModel> filter = createFilter(
					AttributesKeyPattern.OTHER,
					AttributesKeyPattern.attributeId(AttributeId.ofRepoId(111)),
					AttributesKeyPattern.attributeId(AttributeId.ofRepoId(222)),
					AttributesKeyPattern.attributeId(AttributeId.ofRepoId(333)));

			assertThat(filter).isSameAs(ConstantQueryFilter.of(true));
		}

		@Test
		public void stringAttribute()
		{
			final AttributeId attributeId = AttributeId.ofRepoId(111);

			final IQueryFilter<DummyModel> filter = createFilter(
					pattern(AttributesKeyPart.ofStringAttribute(attributeId, "val")));

			assertThat(filter).isEqualTo(newStringLikeFilter("%111=val%"));
		}

		@Test
		public void numberAttribute()
		{
			final AttributeId attributeId = AttributeId.ofRepoId(111);

			final IQueryFilter<DummyModel> filter = createFilter(
					pattern(AttributesKeyPart.ofNumberAttribute(attributeId, new BigDecimal("66"))));

			assertThat(filter).isEqualTo(newStringLikeFilter("%111=66%"));
		}

		@Test
		public void attributeIdWildcard()
		{
			final IQueryFilter<DummyModel> filter = createFilter(
					AttributesKeyPattern.attributeId(AttributeId.ofRepoId(111)));

			assertThat(filter).isEqualTo(newStringLikeFilter("%111=%"));
		}

		@Test
		public void attributeValueId()
		{
			final AttributeValueId attributeValueId = AttributeValueId.ofRepoId(111);
			final IQueryFilter<DummyModel> filter = createFilter(
					pattern(AttributesKeyPart.ofAttributeValueId(attributeValueId)));

			assertThat(filter).isEqualTo(newStringLikeFilter("%111%"));
		}
	}
}
