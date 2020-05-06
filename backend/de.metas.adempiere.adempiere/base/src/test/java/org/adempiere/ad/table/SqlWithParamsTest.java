package org.adempiere.ad.table;

import static org.assertj.core.api.Assertions.assertThat;
import java.sql.Timestamp;

import org.adempiere.ad.table.RecordChangeLogEntryLoader.SqlWithParams;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

class SqlWithParamsTest
{
	@Test
	void test()
	{
		final SqlWithParams result = SqlWithParams
				.createEmpty()
				.add(new SqlWithParams("string1", ImmutableList.of(1, 2, "3")))
				.add(new SqlWithParams("string2", ImmutableList.of(4, 5, Timestamp.valueOf("2019-08-22 11:01:23"))))
				.withFinalOrderByClause("ORDER BY whatever");

		assertThat(result.getSql()).isEqualTo("string1\n UNION\nstring2\nORDER BY whatever");
		assertThat(result.getSqlParams()).containsExactly(1,2,"3",4,5,Timestamp.valueOf("2019-08-22 11:01:23"));
	}

}
