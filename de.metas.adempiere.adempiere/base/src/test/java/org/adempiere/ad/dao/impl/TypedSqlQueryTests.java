package org.adempiere.ad.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_Table;
import org.compiere.util.Env;
import org.junit.Test;

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
	public void test()
	{
		final TypedSqlQuery<I_AD_Table> typedSqlQuery = new TypedSqlQuery<>(Env.getCtx(), I_AD_Table.class, "whereClause_0", ITrx.TRXNAME_None);

		final TypedSqlQuery<I_AD_Table> unionQuery1 = new TypedSqlQuery<>(Env.getCtx(), I_AD_Table.class, "whereClause_1", ITrx.TRXNAME_None);
		final TypedSqlQuery<I_AD_Table> unionQuery2 = new TypedSqlQuery<>(Env.getCtx(), I_AD_Table.class, "whereClause_2", ITrx.TRXNAME_None);
		typedSqlQuery.addUnion(unionQuery1, true);
		typedSqlQuery.addUnion(unionQuery2, true);

		final String result = typedSqlQuery.buildSQL(new StringBuilder("selectClause"), true);
		assertThat(result).isNotEmpty();
		assertThat(result).isEqualToIgnoringWhitespace(
				"selectClause WHERE (whereClause_0)\n" +
						"UNION DISTINCT\n" +
						"(\n" +
						"   selectClause WHERE (whereClause_1)\n" +
						")\n" +
						"UNION DISTINCT\n" +
						"(\n" +
						"   selectClause WHERE (whereClause_2)\n" +
						")"
		);
	}
}
