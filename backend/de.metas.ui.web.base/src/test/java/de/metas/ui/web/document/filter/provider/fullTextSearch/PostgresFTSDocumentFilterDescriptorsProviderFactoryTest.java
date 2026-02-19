/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.ui.web.document.filter.provider.fullTextSearch;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterInlineRenderMode;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class PostgresFTSDocumentFilterDescriptorsProviderFactoryTest
{
	private PostgresFTSDocumentFilterDescriptorsProviderFactory factory;
	private ISysConfigBL sysConfigBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		factory = new PostgresFTSDocumentFilterDescriptorsProviderFactory();
		sysConfigBL = Services.get(ISysConfigBL.class);
	}

	private CreateFiltersProviderContext contextForTable(final String tableName)
	{
		return CreateFiltersProviderContext.builder()
				.tableName(tableName)
				.fields(ImmutableList.of())
				.includedEntities(ImmutableList.of())
				.build();
	}

	private void enableFTS()
	{
		sysConfigBL.setValue(
				"de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterDescriptorsProviderFactory.enabled",
				true,
				ClientId.SYSTEM,
				OrgId.ANY);
	}

	@Nested
	class WhenDisabled
	{
		@ParameterizedTest
		@ValueSource(strings = { "C_BPartner", "C_Invoice", "M_Product" })
		void returnsNullProvider_forSupportedTables_whenDisabled(final String tableName)
		{
			// SysConfig defaults to false — don't call enableFTS()
			final DocumentFilterDescriptorsProvider result = factory.createFiltersProvider(contextForTable(tableName));
			assertThat(result).isInstanceOf(NullDocumentFilterDescriptorsProvider.class);
		}
	}

	@Nested
	class UnsupportedTables
	{
		@BeforeEach
		void beforeEach()
		{
			enableFTS();
		}

		@ParameterizedTest
		@ValueSource(strings = { "C_Order", "M_InOut", "C_Payment", "AD_User" })
		void returnsNullProvider_forUnsupportedTables(final String tableName)
		{
			final DocumentFilterDescriptorsProvider result = factory.createFiltersProvider(contextForTable(tableName));
			assertThat(result).isInstanceOf(NullDocumentFilterDescriptorsProvider.class);
		}

		@Test
		void returnsNull_forBlankTableName()
		{
			final CreateFiltersProviderContext context = CreateFiltersProviderContext.builder()
					.tableName("  ")
					.fields(ImmutableList.of())
					.includedEntities(ImmutableList.of())
					.build();

			final DocumentFilterDescriptorsProvider result = factory.createFiltersProvider(context);
			assertThat(result).isNull();
		}

		@Test
		void returnsNull_forNullTableName()
		{
			final CreateFiltersProviderContext context = CreateFiltersProviderContext.builder()
					.tableName(null)
					.fields(ImmutableList.of())
					.includedEntities(ImmutableList.of())
					.build();

			final DocumentFilterDescriptorsProvider result = factory.createFiltersProvider(context);
			assertThat(result).isNull();
		}
	}

	@Nested
	class SupportedTables
	{
		@BeforeEach
		void beforeEach()
		{
			enableFTS();
		}

		@Test
		void bpartner_returnsValidDescriptor()
		{
			assertValidDescriptorForTable(I_C_BPartner.Table_Name);
		}

		@Test
		void invoice_returnsValidDescriptor()
		{
			assertValidDescriptorForTable(I_C_Invoice.Table_Name);
		}

		@Test
		void product_returnsValidDescriptor()
		{
			assertValidDescriptorForTable(I_M_Product.Table_Name);
		}

		private void assertValidDescriptorForTable(final String tableName)
		{
			final DocumentFilterDescriptorsProvider result = factory.createFiltersProvider(contextForTable(tableName));

			assertThat(result).isInstanceOf(ImmutableDocumentFilterDescriptorsProvider.class);

			final DocumentFilterDescriptor descriptor = result.getByFilterIdOrNull(PostgresFTSDocumentFilterDescriptorsProviderFactory.FILTER_ID);
			assertThat(descriptor).isNotNull();
			assertThat(descriptor.getFilterId()).isEqualTo(PostgresFTSDocumentFilterDescriptorsProviderFactory.FILTER_ID);
			assertThat(descriptor.getInlineRenderMode()).isEqualTo(DocumentFilterInlineRenderMode.INLINE_PARAMETERS);
			assertThat(descriptor.isFrequentUsed()).isTrue();

			// Verify the SearchText parameter descriptor is present
			assertThat(descriptor.getParameterByName(PostgresFTSDocumentFilterDescriptorsProviderFactory.PARAM_SearchText))
					.isNotNull();

			// Verify the internal TableName parameter carries the correct table name
			final String actualTableName = descriptor.getInternalParameters().stream()
					.filter(p -> PostgresFTSDocumentFilterDescriptorsProviderFactory.PARAM_TableName.equals(p.getFieldName()))
					.map(DocumentFilterParam::getValueAsString)
					.findFirst()
					.orElse(null);
			assertThat(actualTableName).isEqualTo(tableName);
		}
	}
}
