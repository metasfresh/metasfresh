package org.adempiere.ad.dao.impl;

import com.google.common.collect.ImmutableList;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class QueryOrderByTest
{

	private ArrayList<I_AD_User> users;
	private I_AD_User user_null;
	private I_AD_User user_20;
	private I_AD_User user_10;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		users = new ArrayList<>();
		user_null = newInstance(I_AD_User.class);
		saveRecord(user_null);
		users.add(user_null);

		user_20 = newInstance(I_AD_User.class);
		user_20.setC_Greeting_ID(20);
		saveRecord(user_20);
		users.add(user_20);

		user_10 = newInstance(I_AD_User.class);
		user_10.setC_Greeting_ID(10);
		saveRecord(user_10);
		users.add(user_10);
	}

	@Test
	public void getComparator_descending_nulls_last()
	{

		final QueryOrderByItem queryOrderByItem = new QueryOrderByItem(I_AD_User.COLUMNNAME_C_Greeting_ID, Direction.Descending, Nulls.Last);
		final QueryOrderBy queryOrderBy = QueryOrderBy.of(ImmutableList.of(queryOrderByItem));

		// invoke the method under test
		final Comparator<Object> comparator = queryOrderBy.getComparator();

		users.sort(comparator);
		assertThat(users.get(0).getAD_User_ID()).isEqualTo(user_20.getAD_User_ID());
		assertThat(users.get(1).getAD_User_ID()).isEqualTo(user_10.getAD_User_ID());
		assertThat(users.get(2).getAD_User_ID()).isEqualTo(user_null.getAD_User_ID());
	}

	@Test
	public void getComparator_descending_nulls_first()
	{

		final QueryOrderByItem queryOrderByItem = new QueryOrderByItem(I_AD_User.COLUMNNAME_C_Greeting_ID, Direction.Descending, Nulls.First);
		final QueryOrderBy queryOrderBy = QueryOrderBy.of(ImmutableList.of(queryOrderByItem));

		// invoke the method under test
		final Comparator<Object> comparator = queryOrderBy.getComparator();

		users.sort(comparator);
		assertThat(users.get(0).getAD_User_ID()).isEqualTo(user_null.getAD_User_ID());
		assertThat(users.get(1).getAD_User_ID()).isEqualTo(user_20.getAD_User_ID());
		assertThat(users.get(2).getAD_User_ID()).isEqualTo(user_10.getAD_User_ID());
	}

	@Test
	public void getComparator_ascending_nulls_last()
	{

		final QueryOrderByItem queryOrderByItem = new QueryOrderByItem(I_AD_User.COLUMNNAME_C_Greeting_ID, Direction.Ascending, Nulls.Last);
		final QueryOrderBy queryOrderBy = QueryOrderBy.of(ImmutableList.of(queryOrderByItem));

		// invoke the method under test
		final Comparator<Object> comparator = queryOrderBy.getComparator();

		users.sort(comparator);
		assertThat(users.get(0).getAD_User_ID()).isEqualTo(user_10.getAD_User_ID());
		assertThat(users.get(1).getAD_User_ID()).isEqualTo(user_20.getAD_User_ID());
		assertThat(users.get(2).getAD_User_ID()).isEqualTo(user_null.getAD_User_ID());
	}

	@Test
	public void getComparator_ascending_nulls_first()
	{

		final QueryOrderByItem queryOrderByItem = new QueryOrderByItem(I_AD_User.COLUMNNAME_C_Greeting_ID, Direction.Ascending, Nulls.First);
		final QueryOrderBy queryOrderBy = QueryOrderBy.of(ImmutableList.of(queryOrderByItem));

		// invoke the method under test
		final Comparator<Object> comparator = queryOrderBy.getComparator();

		users.sort(comparator);
		assertThat(users.get(0).getAD_User_ID()).isEqualTo(user_null.getAD_User_ID());
		assertThat(users.get(1).getAD_User_ID()).isEqualTo(user_10.getAD_User_ID());
		assertThat(users.get(2).getAD_User_ID()).isEqualTo(user_20.getAD_User_ID());
	}

	@Test
	public void getSql_identityMapper_realColumn()
	{
		final QueryOrderBy queryOrderBy = QueryOrderBy.of(ImmutableList.of(
				new QueryOrderByItem("C_Order_ID", Direction.Ascending, Nulls.Last)));

		assertThat(queryOrderBy.getSql()).isEqualTo("C_Order_ID ASC NULLS LAST");
	}

	@Test
	public void getSql_mapper_expandsVirtualColumn()
	{
		final QueryOrderBy queryOrderBy = QueryOrderBy.of(ImmutableList.of(
				new QueryOrderByItem("MyVirtualCol", Direction.Descending, Nulls.First)));

		final String sql = queryOrderBy.getSql(col -> "(SELECT x FROM t WHERE t.id = " + col + ".id)");

		assertThat(sql).isEqualTo("((SELECT x FROM t WHERE t.id = MyVirtualCol.id)) DESC NULLS FIRST");
	}

	@Test
	public void getSql_mapper_realColumnReturnedAsIs()
	{
		final QueryOrderBy queryOrderBy = QueryOrderBy.of(ImmutableList.of(
				new QueryOrderByItem("C_Order_ID", Direction.Ascending, Nulls.Last)));

		// Mapper returns the bare column name → emitted without parentheses
		final String sql = queryOrderBy.getSql(col -> col);

		assertThat(sql).isEqualTo("C_Order_ID ASC NULLS LAST");
	}

	@Test
	public void getSql_mapper_returnsNull_skipsItem()
	{
		final QueryOrderBy queryOrderBy = QueryOrderBy.of(ImmutableList.of(
				new QueryOrderByItem("UnknownCol", Direction.Ascending, Nulls.Last)));

		final String sql = queryOrderBy.getSql(col -> null);

		assertThat(sql).isNull();
	}

	@Test
	public void toString_empty_returnsEmptyString_notNull()
	{
		// NONE singleton — exercise toString() on empty list (regression guard against NPE in string concatenation)
		assertThat(QueryOrderBy.NONE.toString()).isEqualTo("");
	}
}
