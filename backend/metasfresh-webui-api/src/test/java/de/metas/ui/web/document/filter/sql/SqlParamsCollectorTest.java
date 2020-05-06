package de.metas.ui.web.document.filter.sql;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.compiere.db.Database;
import org.compiere.util.DisplayType;
import org.junit.Test;

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

public class SqlParamsCollectorTest
{
	@Test
	public void newInstance()
	{
		final SqlParamsCollector newInstance = SqlParamsCollector.newInstance();
		assertThat(newInstance).isNotNull();
		assertThat(newInstance.isCollecting()).isTrue();
		assertThat(newInstance.toList()).isEmpty();

		assertThat(newInstance.placeholder("string")).isEqualTo("?");
		assertThat(newInstance.placeholder(10)).isEqualTo("?");
		assertThat(newInstance.placeholder(null)).isEqualTo("?");
		assertThat(newInstance.placeholder(BigDecimal.TEN)).isEqualTo("?");

		final List<Object> collectedParams = newInstance.toList();
		assertThat(collectedParams).containsExactly("string", 10, null, BigDecimal.TEN);
	}

	@Test
	public void notCollecting()
	{
		final SqlParamsCollector newInstance = SqlParamsCollector.notCollecting();
		assertThat(newInstance).isNotNull();
		assertThat(newInstance.isCollecting()).isFalse();
		assertThat(newInstance.toList()).isNull();

		assertThat(newInstance.placeholder("string")).isEqualTo("'string'");
		assertThat(newInstance.placeholder(10)).isEqualTo("10");
		assertThat(newInstance.placeholder(null)).isEqualTo("NULL");

		assertThat(newInstance.placeholder(BigDecimal.TEN)).isEqualTo(Database.TO_NUMBER(BigDecimal.TEN, DisplayType.Number));

		final List<Object> collectedParams = newInstance.toList();
		assertThat(collectedParams).isNull();
	}

	@Test
	public void wrapNullable_nullList()
	{
		final SqlParamsCollector fromNullable = SqlParamsCollector.wrapNullable(null);
		assertThat(fromNullable).isNotNull();
		assertThat(fromNullable.isCollecting()).isFalse();
		assertThat(fromNullable.toList()).isNull();
		assertThat(fromNullable.placeholder("string")).isEqualTo("'string'");
	}

	@Test
	public void wrapNullable()
	{
		final ArrayList<Object> list = new ArrayList<>();
		list.add("preexistingString");

		final SqlParamsCollector fromNullable = SqlParamsCollector.wrapNullable(list);
		assertThat(fromNullable).isNotNull();
		assertThat(fromNullable.isCollecting()).isTrue();
		assertThat(fromNullable.toList()).isNotNull();
		assertThat(fromNullable.placeholder("anotherString")).isEqualTo("?");

		assertThat(list).containsExactly("preexistingString", "anotherString");
	}

}
