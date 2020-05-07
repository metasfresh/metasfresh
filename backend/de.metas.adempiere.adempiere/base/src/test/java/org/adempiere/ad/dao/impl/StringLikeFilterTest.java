package org.adempiere.ad.dao.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class StringLikeFilterTest
{

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void accept_substring()
	{
		final I_AD_Column column = createColumn("BLAHblahMySUBStringblah");
		final StringLikeFilter<Object> substringFilter = new StringLikeFilter<>(I_AD_Column.COLUMNNAME_Name, "MySubString", true);

		final boolean accepted = substringFilter.accept(column);

		assertThat(accepted).isTrue();
	}

	@Test
	public void accept_like_expression()
	{
		final I_AD_Column column = createColumn("BLAHblahMySUBStringblah");
		final StringLikeFilter<Object> substringFilter = new StringLikeFilter<>(I_AD_Column.COLUMNNAME_Name, "My%str_ng", true);

		final boolean accepted = substringFilter.accept(column);

		assertThat(accepted).isTrue();
	}

	@Test
	public void toSql()
	{
		final StringLikeFilter<Object> substringFilter = new StringLikeFilter<>(I_AD_Column.COLUMNNAME_Name, "My%Str_ng", true);

		final String filterSql = substringFilter.getSql();

		assertThat(filterSql).isEqualTo("Name  ILIKE  '%My%Str_ng%'");
	}

	private I_AD_Column createColumn(@NonNull final String columnValue)
	{
		final I_AD_Column column = newInstance(I_AD_Column.class);
		column.setName(columnValue);
		save(column);
		return column;
	}

	@Test
	public void test_equals()
	{
		final StringLikeFilter<I_Test> filter1 = new StringLikeFilter<>(I_AD_Column.COLUMNNAME_Name, "MySubString", true);
		final StringLikeFilter<I_Test> filter2 = new StringLikeFilter<>(I_AD_Column.COLUMNNAME_Name, "MySubString", true);
		assertThat(filter1).isEqualTo(filter2);

		filter1.getSql(); // trigget sql build
		assertThat(filter1).isEqualTo(filter2);
	}
}
