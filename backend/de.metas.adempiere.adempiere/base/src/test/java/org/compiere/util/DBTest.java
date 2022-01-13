package org.compiere.util;

import org.compiere.model.Null;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DBTest
{
	@Nested
	class buildSqlList
	{
		@Test
		public void null_paramsOut()
		{
			assertThatThrownBy(() -> {
				final List<Object> paramsOut = null;
				DB.buildSqlList(new ArrayList<>(), paramsOut);
			}).isInstanceOf(NullPointerException.class);
		}

		@Test
		public void null_paramsOutCollector()
		{
			assertThatThrownBy(() -> {
				final Consumer<Collection<?>> paramsOutCollector = null;
				DB.buildSqlList(new ArrayList<>(), paramsOutCollector);
			}).isInstanceOf(NullPointerException.class);
		}

		@Test
		public void empty_paramsIn()
		{
			{
				final List<Object> paramsOut = new ArrayList<>();
				final String sql = DB.buildSqlList((Collection<? extends Object>)null, paramsOut);
				assertThat(paramsOut).isEmpty();
				assertThat(sql).isSameAs(DB.SQL_EmptyList);
			}

			{
				final List<Object> paramsOut = new ArrayList<>();
				final String sql = DB.buildSqlList(Collections.emptyList(), paramsOut);
				assertThat(paramsOut).isEmpty();
				assertThat(sql).isSameAs(DB.SQL_EmptyList);
			}

		}

		@Test
		public void nonEmpty_paramIn_and_paramsOut()
		{
			final List<Object> paramsIn = new ArrayList<>();
			paramsIn.add("value1");
			paramsIn.add("value2");

			final List<Object> paramsInExpected = new ArrayList<>(paramsIn);

			final List<Object> paramsOut = new ArrayList<>();
			paramsOut.add("existing-value1");
			paramsOut.add(new Date());
			paramsOut.add(true);

			final String sqlExpected = "(?,?)";
			final List<Object> paramsOutExpected = new ArrayList<>(paramsOut);
			paramsOutExpected.addAll(paramsIn);

			final String sqlActual = DB.buildSqlList(paramsIn, paramsOut);

			assertThat(sqlActual).isEqualTo(sqlExpected);
			assertThat(paramsIn).isEqualTo(paramsInExpected);
			assertThat(paramsOut).isEqualTo(paramsOutExpected);
		}
	}

	@Nested
	class TO_BOOLEAN
	{
		@Test
		void true_value()
		{
			assertThat(DB.TO_BOOLEAN(true)).isEqualTo("'Y'");
		}

		@Test
		void false_value()
		{
			assertThat(DB.TO_BOOLEAN(false)).isEqualTo("'N'");
		}

		@Test
		void null_value()
		{
			assertThat(DB.TO_BOOLEAN(null)).isEqualTo("NULL");
		}
	}

	@Nested
	class TO_SQL
	{
		@Test
		void null_value()
		{
			assertThat(DB.TO_SQL(null)).isEqualTo("NULL");
		}

		@Test
		void null_instance_value()
		{
			assertThat(DB.TO_SQL(Null.NULL)).isEqualTo("NULL");
		}

	}
}
