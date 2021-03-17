package de.metas.ui.web.view.descriptor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

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

public class SqlAndParamsTest
{
	@Nested
	public class andNullables
	{
		@Test
		public void empty()
		{
			final Optional<SqlAndParams> result = SqlAndParams.andNullables(ImmutableList.of());
			assertThat(result).isEmpty();
		}

		@Test
		public void onlyNullElements()
		{
			final ArrayList<SqlAndParams> collection = new ArrayList<>();
			collection.add(null);
			collection.add(null);
			collection.add(null);

			final Optional<SqlAndParams> result = SqlAndParams.andNullables(collection);
			assertThat(result).isEmpty();
		}

		@Test
		public void singleElement()
		{
			final SqlAndParams element = SqlAndParams.of("test");
			final ArrayList<SqlAndParams> collection = new ArrayList<>();
			collection.add(element);

			final Optional<SqlAndParams> result = SqlAndParams.andNullables(collection);
			assertThat(result).containsSame(element);
		}

		@Test
		public void singleElement_and_someNullValues()
		{
			final SqlAndParams element = SqlAndParams.of("test");
			final ArrayList<SqlAndParams> collection = new ArrayList<>();
			collection.add(null);
			collection.add(element);
			collection.add(null);
			collection.add(null);

			final Optional<SqlAndParams> result = SqlAndParams.andNullables(collection);
			assertThat(result).containsSame(element);
		}

		@Test
		public void twoElements_and_someNullValues()
		{
			final SqlAndParams element1 = SqlAndParams.of("test1");
			final SqlAndParams element2 = SqlAndParams.of("test2");
			final ArrayList<SqlAndParams> collection = new ArrayList<>();
			collection.add(null);
			collection.add(element1);
			collection.add(null);
			collection.add(element2);
			collection.add(null);
			collection.add(null);

			final Optional<SqlAndParams> result = SqlAndParams.andNullables(collection);
			assertThat(result).contains(SqlAndParams.of("(test1) AND (test2)"));
		}

		@Test
		public void fiveElements_and_someNullValues()
		{
			final SqlAndParams element1 = SqlAndParams.of("test1");
			final SqlAndParams element2 = SqlAndParams.of("test2");
			final SqlAndParams element3 = SqlAndParams.of("test3");
			final SqlAndParams element4 = SqlAndParams.of("test4");
			final SqlAndParams element5 = SqlAndParams.of("test5");
			final ArrayList<SqlAndParams> collection = new ArrayList<>();
			collection.add(null);
			collection.add(element1);
			collection.add(null);
			collection.add(element2);
			collection.add(null);
			collection.add(element3);
			collection.add(null);
			collection.add(element4);
			collection.add(null);
			collection.add(element5);
			collection.add(null);

			final Optional<SqlAndParams> result = SqlAndParams.andNullables(collection);
			assertThat(result).contains(SqlAndParams.of("(test1) AND (test2) AND (test3) AND (test4) AND (test5)"));
		}
	}
}
