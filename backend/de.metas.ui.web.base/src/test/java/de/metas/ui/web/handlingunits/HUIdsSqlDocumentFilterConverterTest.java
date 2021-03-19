package de.metas.ui.web.handlingunits;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.window.model.sql.SqlOptions;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HUIdsSqlDocumentFilterConverterTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(HUReservationRepository.class);
	}

	@Nullable
	private String getSql(final DocumentFilter filter)
	{
		final SqlDocumentFilterConverterContext context = SqlDocumentFilterConverterContext.EMPTY;
		return HUIdsFilterHelper.SQL_DOCUMENT_FILTER_CONVERTER.getSql(
				SqlParamsCollector.newInstance(),
				filter,
				SqlOptions.usingTableAlias("dummyTableAlias"),
				context);
	}

	@Nested
	class getSql
	{

		@Test
		void newEmpty()
		{
			final DocumentFilter filter = HUIdsFilterHelper.createFilter(HUIdsFilterData.newEmpty());
			assertThat(getSql(filter)).isEqualTo(HUIdsSqlDocumentFilterConverter.SQL_TRUE);
		}

		@Nested
		class ofHUIds
		{
			/**
			 * Verifies that if {@link HUIdsFilterHelper#createFilter(java.util.Collection)} is called with an empty list,
			 * then the filter's SQL does <b>not</b> select every single f**king HU on this planet.
			 */
			@Test
			void emptyInitialHUIds()
			{
				final DocumentFilter noHusFilter = HUIdsFilterHelper.createFilter(ImmutableList.of());
				assertThat(getSql(noHusFilter)).isEqualTo(HUIdsSqlDocumentFilterConverter.SQL_FALSE);
			}
		}
	}
}
