/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.dao.sql;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SqlParamsInlinerTest
{
	@Test
	void standardCase()
	{
		final SqlParamsInliner sqlParamsInliner = SqlParamsInliner.builder().failOnError(false).build();

		assertThat(sqlParamsInliner.inline("SELECT * FROM MD_Stock WHERE ((M_Product_ID = ?) AND (M_Warehouse_ID=?) AND (1=1))", 1000111, 540008))
				.isEqualTo("SELECT * FROM MD_Stock WHERE ((M_Product_ID = 1000111) AND (M_Warehouse_ID=540008) AND (1=1))");
	}

	@Nested
	class missingParams
	{
		@Test
		void doNotFailOnError()
		{
			final SqlParamsInliner sqlParamsInliner = SqlParamsInliner.builder().failOnError(false).build();

			assertThat(sqlParamsInliner.inline("SELECT * FROM Table where a=? and b=? and c=?", 1, "str"))
					.isEqualTo("SELECT * FROM Table where a=1 and b='str' and c=?missing3?");
		}

		@Test
		void failOnError()
		{
			final SqlParamsInliner sqlParamsInliner = SqlParamsInliner.builder().failOnError(true).build();

			assertThatThrownBy(() -> sqlParamsInliner.inline("SELECT * FROM Table where a=? and b=? and c=?", 1, "str"))
					.hasMessageStartingWith("Missing SQL parameter with index=3");
		}
	}

	@Nested
	class exceeedingParams
	{
		@Test
		void doNotFailOnError()
		{
			final SqlParamsInliner sqlParamsInliner = SqlParamsInliner.builder().failOnError(false).build();

			assertThat(sqlParamsInliner.inline("SELECT * FROM Table where c=?", 1, "str", 3))
					.isEqualTo("SELECT * FROM Table where c=1 -- Exceeding params: 'str', 3");
		}

		@Test
		void failOnError()
		{
			final SqlParamsInliner sqlParamsInliner = SqlParamsInliner.builder().failOnError(true).build();

			assertThatThrownBy(() -> sqlParamsInliner.inline("SELECT * FROM Table where c=?", 1, "str", 3))
					.hasMessageStartingWith("Got more SQL params than needed: [str, 3]");
		}
	}
}