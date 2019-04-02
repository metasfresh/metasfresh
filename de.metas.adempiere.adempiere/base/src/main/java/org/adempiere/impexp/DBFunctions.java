/**
 * 
 */
package org.adempiere.impexp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class DBFunctions
{
	private static final transient Logger log = LogManager.getLogger(DBFunctions.class);
	private final static String IMPORT_BEFORE_COMPLETE = "IMPORT_BEFORE_COMPLETE";
	private final static String IMPORT_AFTER_ROW = "IMPORT_AFTER_ROW";
	private static List<DBFunction> availableFunctions;
	private static List<DBFunction> availableAfterRowFunctions;
	private static List<DBFunction> availableBeforeCompleteFunctions;

	@Builder
	@Value
	public class DBFunction
	{
		final @NonNull String specific_schema;
		final @NonNull String routine_name;
	}

	final public List<DBFunction> fetchImportAfterRowFunctions(@NonNull final String tableName)
	{
		if (availableAfterRowFunctions == null)
		{
			availableAfterRowFunctions = new ArrayList<>();
			for (final DBFunction function : fetchImportFunctions(tableName))
			{
				if (isEligibleFunction(function))
				{
					if (function.getRoutine_name().contains(IMPORT_AFTER_ROW))
					{
						availableAfterRowFunctions.add(function);
					}
				}
				else
				{
					log.warn("Function {} from schema {} is not eliglible for importing process!", function.getRoutine_name(), function.getSpecific_schema());
				}
			}
		}

		return availableAfterRowFunctions;
	}

	final public List<DBFunction> fetchImportBeforeCompleteFunctions(@NonNull final String tableName)
	{
		if (availableBeforeCompleteFunctions == null)
		{
			availableBeforeCompleteFunctions = new ArrayList<>();
			for (final DBFunction function : fetchImportFunctions(tableName))
			{
				if (isEligibleFunction(function))
				{
					if (function.getRoutine_name().contains(IMPORT_BEFORE_COMPLETE))
					{
						availableBeforeCompleteFunctions.add(function);
					}
				}
				else
				{
					log.warn("Function {} from schema {} is not eliglible for importing process!", function.getRoutine_name(), function.getSpecific_schema());
				}
			}
		}

		return availableBeforeCompleteFunctions;
	}

	@Value
	@Builder
	public class DBFunctionParams
	{
		final int recordId;
		final int dataImportId;
	}

	final public void doDBFunctionCall(@NonNull final DBFunction function, @NonNull DBFunctionParams params)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(function.getSpecific_schema())
				.append(".")
				.append(function.getRoutine_name());

		if (params.getRecordId() > 0)
		{
			DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited, "SELECT " + sb.toString() + "(?,?)", new Object[] { params.getRecordId(), params.getDataImportId() });
		}
		else
		{
			DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited, "SELECT " + sb.toString() + "(?)", new Object[] { params.getDataImportId() });
		}
		log.info("\nCalling " + function);
	}

	private boolean isEligibleFunction(@NonNull final DBFunction function)
	{
		final String routine_name = function.getRoutine_name();
		return routine_name.contains(IMPORT_BEFORE_COMPLETE) || routine_name.contains(IMPORT_AFTER_ROW);
	}

	private List<DBFunction> fetchImportFunctions(@NonNull final String tableName)
	{
		if (availableFunctions == null)
		{
			final StringBuilder sql = new StringBuilder("SELECT routines.routine_name FROM information_schema.routines ")
					.append(" WHERE routines.routine_name ILIKE ? ")
					.append(" ORDER BY routines.routine_name ");
			final List<Object> sqlParams = Arrays.<Object> asList(tableName + "%");
			availableFunctions = DB.retrieveRowsOutOfTrx(sql.toString(), sqlParams, rs -> retrieveDBFunction(rs));
		}

		return availableFunctions;
	}

	private DBFunction retrieveDBFunction(@NonNull final ResultSet rs) throws SQLException
	{
		final String specific_schema = rs.getString("specific_schema");
		final String routine_name = rs.getString("routine_name");
		return DBFunction.builder()
				.specific_schema(specific_schema)
				.routine_name(routine_name)
				.build();
	}
}
