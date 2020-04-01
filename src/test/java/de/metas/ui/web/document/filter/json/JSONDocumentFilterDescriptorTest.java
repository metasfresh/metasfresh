package de.metas.ui.web.document.filter.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class JSONDocumentFilterDescriptorTest
{
	@Nested
	public class ofCollection
	{
		private static final boolean FREQUENT = true;
		private static final boolean NOT_FREQUENT = false;

		private JSONDocumentLayoutOptions options;

		@BeforeEach
		public void beforeEach()
		{
			options = JSONDocumentLayoutOptions.ofAdLanguage("de_DE");
		}

		private DocumentFilterDescriptor newFilter(
				final String filterId,
				final boolean frequent,
				final int sortNo)
		{
			return DocumentFilterDescriptor.builder()
					.setFilterId(filterId)
					.setDisplayName(filterId)
					.setFrequentUsed(frequent)
					.setSortNo(sortNo)
					.build();
		}

		@Test
		public void emptyCollection()
		{
			final ImmutableList<DocumentFilterDescriptor> filters = ImmutableList.of();
			final List<JSONDocumentFilterDescriptor> jsonFilters = JSONDocumentFilterDescriptor.ofCollection(filters, options);
			assertThat(jsonFilters).isEmpty();
		}

		@Test
		public void someNotFrequentFilters()
		{
			final ImmutableList<DocumentFilterDescriptor> filters = ImmutableList.of(
					newFilter("default1", NOT_FREQUENT, 1),
					newFilter("default2", NOT_FREQUENT, 2),
					newFilter("default3", NOT_FREQUENT, 3));

			final List<JSONDocumentFilterDescriptor> jsonFilters = JSONDocumentFilterDescriptor.ofCollection(filters, options);
			assertThat(jsonFilters).hasSize(1);
			assertThat(jsonFilters.get(0).getFilterId()).isNull();
			assertThat(jsonFilters.get(0).getIncludedFilters().get(0).getFilterId()).isEqualTo("default1");
			assertThat(jsonFilters.get(0).getIncludedFilters().get(1).getFilterId()).isEqualTo("default2");
			assertThat(jsonFilters.get(0).getIncludedFilters().get(2).getFilterId()).isEqualTo("default3");
		}

		@Nested
		public class oneFrequentFilter_someNotFrequentFilters_someFrequentFilters
		{
			@Test
			public void orderedFilters()
			{
				test(ImmutableList.of(
						newFilter("default-date", FREQUENT, 1),
						newFilter("default1", NOT_FREQUENT, 2),
						newFilter("default2", NOT_FREQUENT, 3),
						newFilter("default3", NOT_FREQUENT, 4),
						newFilter("facet1", FREQUENT, 5),
						newFilter("facet2", FREQUENT, 6),
						newFilter("facet3", FREQUENT, 7)));
			}

			@Test
			public void shuffledFilters()
			{
				test(ImmutableList.of(
						newFilter("facet2", FREQUENT, 6),
						newFilter("default1", NOT_FREQUENT, 2),
						newFilter("default2", NOT_FREQUENT, 3),
						newFilter("default3", NOT_FREQUENT, 4),
						newFilter("default-date", FREQUENT, 1),
						newFilter("facet1", FREQUENT, 5),
						newFilter("facet3", FREQUENT, 7)));
			}

			private void test(final List<DocumentFilterDescriptor> filters)
			{
				final List<JSONDocumentFilterDescriptor> jsonFilters = JSONDocumentFilterDescriptor.ofCollection(filters, options);
				assertThat(jsonFilters).hasSize(5);

				assertThat(jsonFilters.get(0).getFilterId()).isEqualTo("default-date");

				assertThat(jsonFilters.get(1).getFilterId()).isNull();
				assertThat(jsonFilters.get(1).getIncludedFilters().get(0).getFilterId()).isEqualTo("default1");
				assertThat(jsonFilters.get(1).getIncludedFilters().get(1).getFilterId()).isEqualTo("default2");
				assertThat(jsonFilters.get(1).getIncludedFilters().get(2).getFilterId()).isEqualTo("default3");

				assertThat(jsonFilters.get(2).getFilterId()).isEqualTo("facet1");
				assertThat(jsonFilters.get(3).getFilterId()).isEqualTo("facet2");
				assertThat(jsonFilters.get(4).getFilterId()).isEqualTo("facet3");
			}
		}
	}
}
