/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.window.descriptor.sql;

import org.adempiere.ad.column.ColumnSql;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ColumnSqlTest
{
	@Test
	void ofSql_toSqlString()
	{
		final ColumnSql columnSql = ColumnSql.ofSql("SELECT Name FROM M_Product WHERE M_Product.M_Product_ID=C_OrderLine.M_Product_ID", "C_OrderLine");
		assertThat(columnSql.toSqlString()).isEqualTo("SELECT Name FROM M_Product WHERE M_Product.M_Product_ID=C_OrderLine.M_Product_ID");
		assertThat(columnSql.withJoinOnTableNameOrAlias("ol").toSqlString()).isEqualTo("SELECT Name FROM M_Product WHERE M_Product.M_Product_ID=ol.M_Product_ID");
		assertThat(columnSql.withJoinOnTableNameOrAlias(null).toSqlString()).isEqualTo("SELECT Name FROM M_Product WHERE M_Product.M_Product_ID=M_Product_ID");
		assertThat(columnSql.withJoinOnTableNameOrAlias("").toSqlString()).isEqualTo("SELECT Name FROM M_Product WHERE M_Product.M_Product_ID=M_Product_ID");
	}

	@Test
	void equals()
	{
		final ColumnSql columnSql1 = ColumnSql.ofSql("sql");
		final ColumnSql columnSql2 = ColumnSql.ofSql("sql");
		columnSql2.toSqlString(); // to force setting the "_sqlBuilt" property

		assertThat(columnSql1).isEqualTo(columnSql2);
	}

	@Test
	void ofSql_toSqlString_with_JoinTableNameOrAliasIncludingDot()
	{
		final ColumnSql columnSql = ColumnSql.ofSql(
				"( select child.status from s_issue child where child.iseffortissue = 'Y' and child.s_parent_issue_id=@JoinTableNameOrAliasIncludingDot@s_issue_id and @JoinTableNameOrAliasIncludingDot@iseffortissue = 'N')",
				"S_Issue");

		assertThat(columnSql.withJoinOnTableNameOrAlias("master").toSqlString())
				.isEqualTo("( select child.status from s_issue child where child.iseffortissue = 'Y' and child.s_parent_issue_id=master.s_issue_id and master.iseffortissue = 'N')");
	}
}

