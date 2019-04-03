/**
 * 
 */
package org.adempiere.impexp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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

@RequiredArgsConstructor(staticName = "of")
public class DBFunctions
{
	private static final transient Logger log = LogManager.getLogger(DBFunctions.class);
	private final static String IMPORT_BEFORE_COMPLETE = "IMPORT_BEFORE_COMPLETE";
	private final static String IMPORT_AFTER_ROW = "IMPORT_AFTER_ROW";
	
	@NonNull
	private final String tableName;
	@Getter(lazy = true)
	private final List<DBFunction> availableFunctions = fetchImportFunctions();
	@Getter(lazy = true)
	private final Map<String, List<DBFunction>> sortedFunctions = mapFunctions();

	final public List<DBFunction> fetchImportBeforeCompleteFunctions()
	{
		return getSortedFunctions().get(IMPORT_BEFORE_COMPLETE);
	}

	final public List<DBFunction> fetchImportAfterRowFunctions()
	{
		return getSortedFunctions().get(IMPORT_AFTER_ROW);
	}

	private Map<String, List<DBFunction>> mapFunctions()
	{
		final List<DBFunction> availableAfterRowFunctions = new ArrayList<>();
		final List<DBFunction> availableBeforeCompleteFunctions = new ArrayList<>();
		for (final DBFunction function : getAvailableFunctions())
		{
			if (isEligibleFunction(function))
			{
				if (function.getRoutine_name().contains(IMPORT_AFTER_ROW))
				{
					availableAfterRowFunctions.add(function);
				}
				else
				{
					availableBeforeCompleteFunctions.add(function);
				}
			}
			else
			{
				log.warn("Function {} from schema {} is not eliglible for importing process!", function.getRoutine_name(), function.getSpecific_schema());
			}
		}

		final Map<String, List<DBFunction>> map = new HashMap<>();
		map.put(IMPORT_AFTER_ROW, availableAfterRowFunctions);
		map.put(IMPORT_BEFORE_COMPLETE, availableBeforeCompleteFunctions);
		return map;
	}

	private boolean isEligibleFunction(@NonNull final DBFunction function)
	{
		final String routine_name = function.getRoutine_name();
		return StringUtils.containsIgnoreCase(routine_name, IMPORT_BEFORE_COMPLETE) || StringUtils.containsIgnoreCase(routine_name, IMPORT_AFTER_ROW);
	}

	private List<DBFunction> fetchImportFunctions()
	{
		final StringBuilder sql = new StringBuilder("SELECT routines.specific_schema, routines.routine_name FROM information_schema.routines ")
				.append(" WHERE routines.routine_name ILIKE ? ")
				.append(" ORDER BY routines.routine_name ");
		final List<Object> sqlParams = Arrays.<Object> asList(tableName + "_%");
		return DB.retrieveRowsOutOfTrx(sql.toString(), sqlParams, rs -> retrieveDBFunction(rs));
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
