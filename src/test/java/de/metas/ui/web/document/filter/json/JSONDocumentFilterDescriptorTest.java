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
				final boolean frequent)
		{
			return DocumentFilterDescriptor.builder()
					.setFilterId(filterId)
					.setDisplayName(filterId)
					.setFrequentUsed(frequent)
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
					newFilter("default1", NOT_FREQUENT),
					newFilter("default2", NOT_FREQUENT),
					newFilter("default3", NOT_FREQUENT));

			final List<JSONDocumentFilterDescriptor> jsonFilters = JSONDocumentFilterDescriptor.ofCollection(filters, options);
			assertThat(jsonFilters).hasSize(1);
			assertThat(jsonFilters.get(0).getFilterId()).isNull();
			assertThat(jsonFilters.get(0).getIncludedFilters().get(0).getFilterId()).isEqualTo("default1");
			assertThat(jsonFilters.get(0).getIncludedFilters().get(1).getFilterId()).isEqualTo("default2");
			assertThat(jsonFilters.get(0).getIncludedFilters().get(2).getFilterId()).isEqualTo("default3");
		}

		@Test
		public void oneFrequentFilter_someNotFrequentFilters_someFrequentFilters()
		{
			final ImmutableList<DocumentFilterDescriptor> filters = ImmutableList.of(
					newFilter("default-date", FREQUENT),
					newFilter("default1", NOT_FREQUENT),
					newFilter("default2", NOT_FREQUENT),
					newFilter("default3", NOT_FREQUENT),
					newFilter("facet1", FREQUENT),
					newFilter("facet2", FREQUENT),
					newFilter("facet3", FREQUENT));

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
