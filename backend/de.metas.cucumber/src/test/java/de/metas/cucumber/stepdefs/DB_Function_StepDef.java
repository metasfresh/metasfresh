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

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions that run plain SQL in the test DB: creating or replacing PL/pgSQL functions (used by scenarios that verify
 * DB-function-driven sequence providers), running a migration script's statements verbatim, and hiding an {@code AD_MigrationScript} record for one scenario.
 */
public class DB_Function_StepDef
{
	/**
	 * The {@code AD_MigrationScript} rows deleted by this scenario, as a JSON array; restored by {@link #restoreDeletedMigrationScriptRecords()}.
	 */
	@Nullable
	private String deletedMigrationScriptRowsJson;

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
	 * Runs the given SQL statement(s) in the test database, outside any transaction (each statement commits on its own).
	 * Meant for a migration script's statements copied verbatim, so the scenario proves exactly what the migration does.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the following SQL is executed in the DB:
	 * """
	 * UPDATE C_Invoice_Candidate ic
	 * SET Processed_Override = NULL
	 * WHERE ic.Processed_Override = 'Y'
	 * ;
	 * """
	 * </pre>
	 */
	@And("the following SQL is executed in the DB:")
	public void execute_sql(@NonNull final String sql)
	{
		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_None);
	}

	/**
	 * Deletes the {@code AD_MigrationScript} record(s) whose {@code Name} ends with the given file name, to emulate a database on which that migration was never applied.
	 * The rows are restored unchanged by an {@code @After} hook, so a failing scenario cannot leave them deleted.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the AD_MigrationScript record of 5821320_sys_Event_InvoicingError_message.sql is deleted for this scenario
	 * </pre>
	 */
	@And("^the AD_MigrationScript record of (.*) is deleted for this scenario$")
	public void delete_migration_script_record_for_this_scenario(@NonNull final String fileName)
	{
		assertThat(deletedMigrationScriptRowsJson).as("only one AD_MigrationScript deletion per scenario is supported").isNull();

		final String namePattern = "%" + fileName;
		final String rowsJson = DB.getSQLValueStringEx(ITrx.TRXNAME_None,
				"SELECT json_agg(ms)::text FROM AD_MigrationScript ms WHERE ms.Name LIKE ?", namePattern);
		assertThat(rowsJson).as("AD_MigrationScript record of %s", fileName).isNotNull();

		deletedMigrationScriptRowsJson = rowsJson;
		DB.executeUpdateAndThrowExceptionOnFail("DELETE FROM AD_MigrationScript WHERE Name LIKE ?", new Object[] { namePattern }, ITrx.TRXNAME_None);
	}

	@After
	public void restoreDeletedMigrationScriptRecords()
	{
		if (deletedMigrationScriptRowsJson == null)
		{
			return;
		}
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO AD_MigrationScript SELECT * FROM json_populate_recordset(NULL::AD_MigrationScript, ?::json) ON CONFLICT (AD_MigrationScript_ID) DO NOTHING",
				new Object[] { deletedMigrationScriptRowsJson },
				ITrx.TRXNAME_None);
		deletedMigrationScriptRowsJson = null;
	}
}
