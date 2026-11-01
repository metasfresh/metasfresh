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
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions that run plain SQL in the test DB: creating or replacing PL/pgSQL functions (used by scenarios that verify
 * DB-function-driven sequence providers), running a migration script from the source tree (whole, or one of its statements),
 * and hiding an {@code AD_MigrationScript} record for one scenario.
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
	 * Runs a migration script of the source tree as one JDBC statement string, outside any transaction, so the scenario runs exactly the shipped file.
	 * The path is relative to the repository root; it is looked up in the working directory and each of its parents
	 * (the module directory locally; in the cucumber Docker image, {@code docker-builds/Dockerfile.cucumber} copies the needed migration directories to the image root).
	 * A missing file fails the step.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the migration script "backend/de.metas.swat/de.metas.swat.base/src/main/sql/postgresql/system/47-de.metas.invoicecandidate/5832360_sys_reset_C_Invoice_Candidate_Processed_Override_DeliveryClosed.sql" is executed in the DB
	 * </pre>
	 */
	@And("the migration script {string} is executed in the DB")
	public void execute_migration_script(@NonNull final String repoRelativePath)
	{
		executeSqlWithResults(readMigrationScript(repoRelativePath), repoRelativePath);
	}

	/**
	 * Runs one statement of a migration script, given as a docstring, after checking that the script contains it (whitespace-insensitive),
	 * so a scenario that runs a script's statements one by one (to assert between them) fails when the script and the docstring drift apart.
	 * The script is looked up as in {@link #execute_migration_script(String)}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the following statement of the migration script "backend/.../5832370_sys_recompute_C_Invoice_Candidate_DeliveryClosed_at_deploy.sql" is executed in the DB:
	 * """
	 * INSERT INTO C_Invoice_Candidate_Recompute (C_Invoice_Candidate_ID)
	 * SELECT ...
	 * ;
	 * """
	 * </pre>
	 */
	@And("the following statement of the migration script {string} is executed in the DB:")
	public void execute_migration_script_statement(@NonNull final String repoRelativePath, @NonNull final String statement)
	{
		final String script = readMigrationScript(repoRelativePath);
		assertThat(normalizeWhitespace(script))
				.as("migration script %s contains the docstring statement (whitespace-insensitive)", repoRelativePath)
				.contains(normalizeWhitespace(statement));
		executeSqlWithResults(statement, repoRelativePath);
	}

	@NonNull
	private static String readMigrationScript(@NonNull final String repoRelativePath)
	{
		final List<Path> searched = new ArrayList<>();
		for (Path dir = Paths.get(System.getProperty("user.dir")).toAbsolutePath(); dir != null; dir = dir.getParent())
		{
			final Path candidate = dir.resolve(repoRelativePath);
			searched.add(candidate);
			if (Files.isRegularFile(candidate))
			{
				try
				{
					return new String(Files.readAllBytes(candidate), StandardCharsets.UTF_8);
				}
				catch (final IOException ex)
				{
					throw new AdempiereException("Failed reading migration script " + candidate, ex);
				}
			}
		}
		throw new AdempiereException("Migration script not found in the working directory or any of its parents")
				.appendParametersToMessage()
				.setParameter("repoRelativePath", repoRelativePath)
				.setParameter("searched", searched);
	}

	private static String normalizeWhitespace(@NonNull final String sql)
	{
		return sql.replaceAll("\\s+", " ").trim();
	}

	/**
	 * Unlike {@link DB#executeUpdateAndThrowExceptionOnFail(String, String)}, this accepts statements that return rows
	 * (a migration script may {@code SELECT backup_table(...)}).
	 */
	private static void executeSqlWithResults(@NonNull final String sql, @NonNull final String source)
	{
		try (final Connection connection = DB.createConnection(true /*autoCommit*/, Connection.TRANSACTION_READ_COMMITTED);
			 final Statement statement = connection.createStatement())
		{
			boolean isResultSet = statement.execute(sql);
			while (isResultSet || statement.getUpdateCount() != -1)
			{
				isResultSet = statement.getMoreResults();
			}
		}
		catch (final SQLException ex)
		{
			throw new AdempiereException("Failed executing the SQL of " + source, ex);
		}
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
