/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for creating or replacing PL/pgSQL functions in the test DB.
 * Used by scenarios that verify DB-function-driven sequence providers.
 */
@RequiredArgsConstructor
public class DB_Function_StepDef
{
	private static final Pattern FUNCTION_NAME_PATTERN = Pattern.compile("[A-Za-z0-9_]+(\\.[A-Za-z0-9_]+)?");

	@NonNull private final C_Invoice_StepDefData invoiceTable;

	/**
	 * Creates (or replaces) a PL/pgSQL function in the test database.
	 * The function body is supplied inline in the feature file as a docstring.
	 * The complete {@code CREATE OR REPLACE FUNCTION …} DDL is passed verbatim to the DB.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the following PL/pgSQL function is created or replaced in the DB:
	 * """
	 * CREATE OR REPLACE FUNCTION test_lotno(p_pp_order_id numeric, p_at timestamptz)
	 *   RETURNS text LANGUAGE sql AS $$ SELECT 'L' || to_char(p_at AT TIME ZONE 'UTC','DDD') || 'M2' $$
	 * """
	 * </pre>
	 */
	@And("the following PL\\/pgSQL function is created or replaced in the DB:")
	public void create_or_replace_pg_function(@NonNull final String ddl)
	{
		DB.executeUpdateAndThrowExceptionOnFail(ddl, ITrx.TRXNAME_None);
	}

	/**
	 * Calls a SQL function that takes the ID of a {@code C_Invoice} and returns a row set,
	 * splits the text in the first column of the first row by line breaks
	 * and asserts the listed lines (1-based {@code LineNo}); lines not listed are not checked.
	 * The function is called with the ID cast to {@code numeric}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>LineNo</b> — (required) 1-based line number of the text result<br>
	 *   <b>Value</b> — (required) expected text of that line (empty for a blank line)<br>
	 * @cucumber.depends StepDefData: C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the SQL function de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_QR_Code called with the ID of invoice_1 returns these lines:
	 *   | LineNo | Value |
	 *   | 1      | SPC   |
	 * </pre>
	 */
	@Then("^the SQL function (\\S+) called with the ID of (\\S+) returns these lines:$")
	public void sql_function_called_with_invoice_id_returns_lines(
			@NonNull final String functionName,
			@NonNull final String invoiceIdentifier,
			@NonNull final DataTable dataTable)
	{
		assertThat(FUNCTION_NAME_PATTERN.matcher(functionName).matches())
				.as("function name %s must be [schema.]name", functionName)
				.isTrue();

		final int invoiceId = invoiceTable.getId(invoiceIdentifier).getRepoId();
		final String text = retrieveFirstColumnOfFirstRow("SELECT * FROM " + functionName + "(?::numeric)", invoiceId);
		final String[] lines = text.split("\n", -1);

		DataTableRows.of(dataTable).forEach(row -> {
			final int lineNo = row.getAsInt("LineNo");
			final String expected = row.getAsOptionalString("Value").orElse("");
			assertThat(lineNo).as("LineNo").isBetween(1, lines.length);
			assertThat(lines[lineNo - 1]).as("%s line %s", functionName, lineNo).isEqualTo(expected);
		});
	}

	private static String retrieveFirstColumnOfFirstRow(@NonNull final String sql, final int id)
	{
		try (final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None))
		{
			pstmt.setInt(1, id);
			try (final ResultSet rs = pstmt.executeQuery())
			{
				if (!rs.next())
				{
					throw new AdempiereException("Function returned no row for ID " + id + ": " + sql);
				}
				return rs.getString(1);
			}
		}
		catch (final SQLException e)
		{
			throw new AdempiereException("Failed calling " + sql, e);
		}
	}
}
