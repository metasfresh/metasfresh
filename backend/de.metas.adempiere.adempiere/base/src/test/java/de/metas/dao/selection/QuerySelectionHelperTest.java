package de.metas.dao.selection;

import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class QuerySelectionHelperTest
{
	@Nested
	public class buildUUIDSelectionSqlSelectFrom
	{
		@Test
		public void simpleQuery()
		{
			final Properties ctx = new Properties();
			final String trxName = ITrx.TRXNAME_None;

			final TypedSqlQuery<I_C_OrderLine> query = new TypedSqlQuery<>(
					ctx,
					I_C_OrderLine.class,
					"M_Product_ID=1",
					trxName);

			assertThat(QuerySelectionHelper.buildUUIDSelectionSqlSelectFrom("uuid", query, "C_OrderLine_ID"))
					.isEqualTo("INSERT INTO T_Query_Selection (UUID, Line, Record_ID) SELECT 'uuid', row_number() OVER (), C_OrderLine.C_OrderLine_ID  FROM C_OrderLine" +
							"\n WHERE (M_Product_ID=1)");
		}

		@Test
		public void simpleQueryWithOrderBy()
		{
			final Properties ctx = new Properties();
			final String trxName = ITrx.TRXNAME_None;

			final TypedSqlQuery<I_C_OrderLine> query = new TypedSqlQuery<>(
					ctx,
					I_C_OrderLine.class,
					"M_Product_ID=1",
					trxName);
			query.setOrderBy("C_OrderLine_ID");

			assertThat(QuerySelectionHelper.buildUUIDSelectionSqlSelectFrom("uuid", query, "C_OrderLine_ID"))
					.isEqualTo("INSERT INTO T_Query_Selection (UUID, Line, Record_ID) SELECT 'uuid', row_number() OVER (ORDER BY C_OrderLine_ID), C_OrderLine.C_OrderLine_ID  FROM C_OrderLine"
							+ "\n WHERE (M_Product_ID=1)"
							+ "\n ORDER BY C_OrderLine_ID");
		}

		@Test
		public void unionDistinctWithOrderBy()
		{
			final Properties ctx = new Properties();
			final String trxName = ITrx.TRXNAME_None;

			final TypedSqlQuery<I_C_OrderLine> query = new TypedSqlQuery<>(ctx, I_C_OrderLine.class, "M_Product_ID=1000002", trxName).setOrderBy("C_OrderLine_ID");
			query.addUnion(new TypedSqlQuery<>(ctx, I_C_OrderLine.class, "M_Product_ID=1000003", trxName).setOrderBy("C_OrderLine_ID"), /* distinct */true);
			query.addUnion(new TypedSqlQuery<>(ctx, I_C_OrderLine.class, "M_Product_ID=1000007", trxName).setOrderBy("C_OrderLine_ID"), /* distinct */true);

			assertThat(QuerySelectionHelper.buildUUIDSelectionSqlSelectFrom("uuid", query, "C_OrderLine_ID"))
					.isEqualTo("INSERT INTO T_Query_Selection (UUID, Line, Record_ID)\n" +
							"SELECT 'uuid', row_number() over (), C_OrderLine_ID\n" +
							"FROM (\n" +
							"SELECT C_OrderLine_ID  FROM C_OrderLine\n" +
							" WHERE (M_Product_ID=1000002)\n" +
							"UNION DISTINCT\n" +
							"(\n" +
							"SELECT C_OrderLine_ID  FROM C_OrderLine\n" +
							" WHERE (M_Product_ID=1000003)\n" +
							")\n" +
							"\n" +
							"UNION DISTINCT\n" +
							"(\n" +
							"SELECT C_OrderLine_ID  FROM C_OrderLine\n" +
							" WHERE (M_Product_ID=1000007)\n" +
							")\n" +
							"\n" +
							" ORDER BY C_OrderLine_ID\n" +
							") t");
		}

	}
}
