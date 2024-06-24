/**
 * 
 */
package de.metas.impexp.processing;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
@Repository
public class DBFunctionsRepository
{
	private static final transient Logger log = LogManager.getLogger(DBFunctionsRepository.class);
	private static final String IMPORT_AFTER_ROW = "IMPORT_AFTER_ROW";
	private static final String IMPORT_AFTER_ALL = "IMPORT_AFTER_ALL";

	public DBFunctions retrieveByTableName(@NonNull final String tableName)
	{
		final ImmutableList<DBFunction> functions = retrieveAvailableImportFunctionsByTableName(tableName);

		final List<DBFunction> availableAfterRowFunctions = new ArrayList<>();
		final List<DBFunction> availableAfterAllFunctions = new ArrayList<>();
		for (final DBFunction function : functions)
		{
			if (isEligibleAfterRowFunction(function))
			{
				availableAfterRowFunctions.add(function);
			}
			else if (isEligibleAfterAllFunction(function))
			{
				availableAfterAllFunctions.add(function);
			}
			else
			{
				log.warn("Function {} from schema {} is not eliglible for importing process!", function.getName(), function.getSchema());
			}
		}

		return DBFunctions.builder()
				.tableName(tableName)
				.availableAfterRowFunctions(ImmutableList.copyOf(availableAfterRowFunctions))
				.availableAfterAllFunctions(ImmutableList.copyOf(availableAfterAllFunctions))
				.build();
	}

	private ImmutableList<DBFunction> retrieveAvailableImportFunctionsByTableName(@NonNull final String tableName)
	{
		final StringBuilder sql = new StringBuilder("SELECT routines.specific_schema, routines.routine_name FROM information_schema.routines ")
				.append(" WHERE routines.routine_name ILIKE ? ")
				.append(" ORDER BY routines.routine_name ");
		final List<Object> sqlParams = Arrays.<Object> asList(tableName + "_%");
		return ImmutableList.copyOf(DB.retrieveRowsOutOfTrx(sql.toString(), sqlParams, rs -> retrieveDBFunction(rs)));
	}

	private DBFunction retrieveDBFunction(@NonNull final ResultSet rs) throws SQLException
	{
		final String specific_schema = rs.getString("specific_schema");
		final String routine_name = rs.getString("routine_name");
		return DBFunction.builder()
				.schema(specific_schema)
				.name(routine_name)
				.build();
	}
	
	private boolean isEligibleAfterRowFunction(@NonNull final DBFunction function)
	{
		final String routine_name = function.getName();
		return StringUtils.containsIgnoreCase(routine_name, IMPORT_AFTER_ROW);
	}

	private boolean isEligibleAfterAllFunction(@NonNull final DBFunction function)
	{
		final String routine_name = function.getName();
		return StringUtils.containsIgnoreCase(routine_name, IMPORT_AFTER_ALL);
	}

}
