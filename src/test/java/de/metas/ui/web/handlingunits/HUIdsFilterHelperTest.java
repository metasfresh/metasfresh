package de.metas.ui.web.handlingunits;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper.HUIdsSqlDocumentFilterConverter;
import de.metas.ui.web.window.model.sql.SqlOptions;

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

public class HUIdsFilterHelperTest
{
	/**
	 * Verifies that if {@link HUIdsFilterHelper#createFilter(java.util.Collection)} is called with an empty list, 
	 * then the filter's SQL does <b>not</b> select every single f**king HU on this planet.
	 */
	@Test
	public void testEmptyHUIdsCollection()
	{
		final DocumentFilter noHusFilter = HUIdsFilterHelper.createFilter(ImmutableList.of());
		final String sql = HUIdsFilterHelper.SQL_DOCUMENT_FILTER_CONVERTER.getSql(SqlParamsCollector.newInstance(), noHusFilter, SqlOptions.usingTableAlias("dummyTableAlias"));

		assertThat(sql).doesNotContain(HUIdsSqlDocumentFilterConverter.SQL_TRUE);
	}

}
