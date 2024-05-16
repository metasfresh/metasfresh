package org.adempiere.ad.dao.impl;

import org.adempiere.ad.trx.api.ITrx;
import org.assertj.core.api.AbstractCharSequenceAssert;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

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

public class TypedSqlQueryTests
{
	@Test
	public void buildSQL()
	{
		final TypedSqlQuery<I_AD_Table> typedSqlQuery = new TypedSqlQuery<>(Env.getCtx(), I_AD_Table.class, "whereClause_0", ITrx.TRXNAME_None);

		final TypedSqlQuery<I_AD_Table> unionQuery1 = new TypedSqlQuery<>(Env.getCtx(), I_AD_Table.class, "whereClause_1", ITrx.TRXNAME_None);
		final TypedSqlQuery<I_AD_Table> unionQuery2 = new TypedSqlQuery<>(Env.getCtx(), I_AD_Table.class, "whereClause_2", ITrx.TRXNAME_None);
		typedSqlQuery.addUnion(unionQuery1, true);
		typedSqlQuery.addUnion(unionQuery2, true);

		final String result = typedSqlQuery.buildSQL(
				"customSelectClause",
				"customFromClause",
				null/* groupByClause */,
				true);
		assertThat(result).isNotEmpty();

		// the first select shall have "our" customFromClause, but the other two shall bring their own (in this case default) from clause
		// however, for the time being, all 3 selects shall have our customSelectClause
		assertThat(result).isEqualToIgnoringWhitespace(
				"customSelectClause customFromClause WHERE (whereClause_0)\n" +
						"UNION DISTINCT\n" +
						"(\n" +
						"   customSelectClause FROM AD_Table WHERE (whereClause_1)\n" +
						")\n" +
						"UNION DISTINCT\n" +
						"(\n" +
						"   customSelectClause FROM AD_Table WHERE (whereClause_2)\n" +
						")");
	}

	@Nested
	public class inlineSqlParameters
	{
		private AbstractCharSequenceAssert<?, String> assertThatInliningSqlParams(final String sql, final Object... params)
		{
			return assertThat(TypedSqlQuery.inlineSqlParams(sql, Arrays.asList(params)));
		}

		@Test
		public void standardCase()
		{
			assertThatInliningSqlParams("SELECT * FROM MD_Stock WHERE ((M_Product_ID = ?) AND (M_Warehouse_ID=?) AND (1=1))", 1000111, 540008)
					.isEqualTo("SELECT * FROM MD_Stock WHERE ((M_Product_ID = 1000111) AND (M_Warehouse_ID=540008) AND (1=1))");
		}

		@Test
		public void missingParams()
		{
			assertThatInliningSqlParams("SELECT * FROM Table where a=? and b=? and c=?", 1, "str")
					.isEqualTo("SELECT * FROM Table where a=1 and b='str' and c=?missing3?");
		}

		@Test
		public void exceeedingParams()
		{
			assertThatInliningSqlParams("SELECT * FROM Table where c=?", 1, "str", 3)
					.isEqualTo("SELECT * FROM Table where c=1 -- Exceeding params: 'str', 3");
		}
	}

	@Nested
	public class union
	{
		@Test
		public void givenUnionQueryWithOrderByOnRoot_whenBuildingSQL_thenUnionOrderByClauseIsIgnored()
		{
			final Properties ctx = new Properties();
			final String trxName = ITrx.TRXNAME_None;

			//given
			final TypedSqlQuery<I_C_OrderLine> query = new TypedSqlQuery<>(ctx, I_C_OrderLine.class, "M_Product_ID=1000002", trxName);
			query.setOrderBy("C_OrderLine_ID");
			query.addUnion(new TypedSqlQuery<>(ctx, I_C_OrderLine.class, "M_Product_ID=1000002", trxName).setOrderBy("M_Product_ID"), true);
			query.addUnion(new TypedSqlQuery<>(ctx, I_C_OrderLine.class, "M_Product_ID=1000002", trxName).setOrderBy("M_Product_ID"), true);

			//when
			final String sql = query.buildSQL(
					"avoidDBConnection",
					null,
					null/* groupByClause */,
					true);

			//then
			assertThat(sql).isEqualTo("avoidDBConnection  FROM C_OrderLine\n"
											  + " WHERE (M_Product_ID=1000002)\n"
											  + "UNION DISTINCT\n"
											  + "(\n"
											  + "avoidDBConnection  FROM C_OrderLine\n"
											  + " WHERE (M_Product_ID=1000002)\n"
											  + ")\n"
											  + "\n"
											  + "UNION DISTINCT\n"
											  + "(\n"
											  + "avoidDBConnection  FROM C_OrderLine\n"
											  + " WHERE (M_Product_ID=1000002)\n"
											  + ")\n"
											  + "\n"
											  + " ORDER BY C_OrderLine_ID");
		}

		@Test
		public void givenUnionQueryWithNoOrderByOnRoot_whenBuildingSQL_thenUnionOrderByClauseAreConsidered()
		{
			final Properties ctx = new Properties();
			final String trxName = ITrx.TRXNAME_None;

			//given
			final TypedSqlQuery<I_C_OrderLine> query = new TypedSqlQuery<>(ctx, I_C_OrderLine.class, "M_Product_ID=1000002", trxName);
			query.addUnion(new TypedSqlQuery<>(ctx, I_C_OrderLine.class, "M_Product_ID=1000002", trxName).setOrderBy("M_Product_ID"), true);
			query.addUnion(new TypedSqlQuery<>(ctx, I_C_OrderLine.class, "M_Product_ID=1000002", trxName).setOrderBy("C_OrderLine_ID"), true);

			//when
			final String sql = query.buildSQL(
					"avoidDBConnection",
					null,
					null/* groupByClause */,
					true);

			//then
			assertThat(sql).isEqualTo("avoidDBConnection  FROM C_OrderLine\n"
											  + " WHERE (M_Product_ID=1000002)\n"
											  + "UNION DISTINCT\n"
											  + "(\n"
											  + "avoidDBConnection  FROM C_OrderLine\n"
											  + " WHERE (M_Product_ID=1000002)\n"
											  + " ORDER BY M_Product_ID\n"
											  + ")\n"
											  + "\n"
											  + "UNION DISTINCT\n"
											  + "(\n"
											  + "avoidDBConnection  FROM C_OrderLine\n"
											  + " WHERE (M_Product_ID=1000002)\n"
											  + " ORDER BY C_OrderLine_ID\n"
											  + ")\n");
		}
	}
}
