package de.metas.report.jasper;

import org.junit.Assert;
import org.junit.Test;

import de.metas.report.jasper.JasperJdbcConnection;

/*
 * #%L
 * de.metas.report.jasper.server.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class JasperJdbcConnectionTest
{
	/**
	 * Testing this method de.metas.adempiere.report.jasper.JasperJdbcConnection.injectSecurityWhereClauses(String, String)
	 */

	@Test
	public void test_injectSecurityWhereClauses()
	{
		// all parameters null
		test_injectSecurityWhereClauses(null, null, null);

		// null where clause
		test_injectSecurityWhereClauses("SQL", "SQL", null);

		// simple generic test
		test_injectSecurityWhereClauses(

		"SELECT * from test"
				+ "\nWHERE OrgFilter;",

		"SELECT * from test",

		"OrgFilter");

		// realistic test with no WHERE and no ORDER BY
		test_injectSecurityWhereClauses(

		"SELECT * FROM report.saldobilanz_Report (?,?,?,?)"
				+ "\nWHERE AD_Org_ID IN (0,1000004);",

		"SELECT * FROM report.saldobilanz_Report (?,?,?,?);",

		"AD_Org_ID IN (0,1000004)");

		// realistic test with ORDER BY
		test_injectSecurityWhereClauses(

		"SELECT * FROM report.saldobilanz_Report (?,?,?,?)  " +
				"\nWHERE AD_Org_ID IN (0, 1000000)" +
				"\nORDER BY AD_Org_ID;",

		"SELECT * FROM report.saldobilanz_Report (?,?,?,?)  ORDER BY AD_Org_ID;",

		"AD_Org_ID IN (0, 1000000)");

		// realistic test with WHERE and ORDER BY. ";" used
		test_injectSecurityWhereClauses(
				"SELECT * FROM report.saldobilanz_Report (?,?,?,?) WHERE testwhere " +
						"\nAND AD_Org_ID IN (0, 1000000)" +
						"\nORDER BY AD_Org_ID;",

		"SELECT * FROM report.saldobilanz_Report (?,?,?,?) WHERE testwhere ORDER BY AD_Org_ID;",

		"AD_Org_ID IN (0, 1000000)");

		// realistic test with WHERE and ORDER BY. ";" not used
		test_injectSecurityWhereClauses(
				"SELECT * FROM report.saldobilanz_Report (?,?,?,?) WHERE testwhere " +
						"\nAND AD_Org_ID IN (0, 1000000)" +
						"\nORDER BY AD_Org_ID;",

		"SELECT * FROM report.saldobilanz_Report (?,?,?,?) WHERE testwhere ORDER BY AD_Org_ID",

		"AD_Org_ID IN (0, 1000000)");

	}

	private void test_injectSecurityWhereClauses(final String sqlExpected, final String sqlInput, final String securityWhereClause)
	{
		final String sqlActual = JasperJdbcConnection.injectSecurityWhereClauses(sqlInput, securityWhereClause);
		final String errmsg = "Invalid sql for "
				+ "\n sqlInput=" + sqlInput
				+ "\n securityWhereClause=" + securityWhereClause;
		Assert.assertEquals(errmsg, sqlExpected, sqlActual);
	}
}
