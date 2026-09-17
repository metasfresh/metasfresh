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

import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;

/**
 * Step definitions for creating or replacing PL/pgSQL functions in the test DB.
 * Used by scenarios that verify DB-function-driven sequence providers.
 */
public class DB_Function_StepDef
{
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
}
