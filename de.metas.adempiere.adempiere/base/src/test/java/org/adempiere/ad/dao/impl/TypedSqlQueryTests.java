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
	public void buildSQL()
	{
		final TypedSqlQuery<I_AD_Table> typedSqlQuery = new TypedSqlQuery<>(Env.getCtx(), I_AD_Table.class, "whereClause_0", ITrx.TRXNAME_None);

		final TypedSqlQuery<I_AD_Table> unionQuery1 = new TypedSqlQuery<>(Env.getCtx(), I_AD_Table.class, "whereClause_1", ITrx.TRXNAME_None);
		final TypedSqlQuery<I_AD_Table> unionQuery2 = new TypedSqlQuery<>(Env.getCtx(), I_AD_Table.class, "whereClause_2", ITrx.TRXNAME_None);
		typedSqlQuery.addUnion(unionQuery1, true);
		typedSqlQuery.addUnion(unionQuery2, true);

		final String result = typedSqlQuery.buildSQL(
				new StringBuilder("customSelectClause"),
				new StringBuilder("customFromClause"),
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
						")"
		);
	}
}
