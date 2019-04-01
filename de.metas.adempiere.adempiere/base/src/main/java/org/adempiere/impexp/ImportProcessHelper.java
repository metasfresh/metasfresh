/**
 * 
 */
package org.adempiere.impexp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

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
public class ImportProcessHelper
{
	private final transient Logger log = LogManager.getLogger(ImportProcessHelper.class);
	private final static String IMPORT_BEFORE_COMPLETE = "IMPORT_BEFORE_COMPLETE";
	private final static String IMPORT_AFTER_ROW = "IMPORT_AFTER_ROW";

	final public List<String> fetchImportBeforeCompleteFunctions(@NonNull final String tableName)
	{
		return fetchImportFunctions(tableName)
				.stream()
				.filter(function -> function.contains(IMPORT_BEFORE_COMPLETE))
				.collect(ImmutableList.toImmutableList());
	}

	final public List<String> fetchImportAfterRowFunctions(@NonNull final String tableName)
	{
		return fetchImportFunctions(tableName)
				.stream()
				.filter(function -> function.contains(IMPORT_AFTER_ROW))
				.collect(ImmutableList.toImmutableList());
	}

	private List<String> fetchImportFunctions(@NonNull final String pattern)
	{
		final StringBuilder sql = new StringBuilder("SELECT routines.routine_name FROM information_schema.routines ")
				.append(" WHERE routines.routine_name LIKE ")
				.append(DB.TO_STRING(pattern + "%"))
				.append(" ORDER BY routines.routine_name ");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final List<String> functions = new ArrayList<>();
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None); // out of transaction
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				functions.add(rs.getString("routine_name"));
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql.toString());
		}

		return functions.stream()
				.peek(function -> {
					if (!(function.contains(IMPORT_AFTER_ROW) || function.contains(IMPORT_BEFORE_COMPLETE)))
					{
						log.warn("Function {} does not contain {}, neither {}.", function, IMPORT_AFTER_ROW, IMPORT_BEFORE_COMPLETE);
					}
				})
				.filter(function -> function.contains(IMPORT_AFTER_ROW) || function.contains(IMPORT_BEFORE_COMPLETE))
				.collect(ImmutableList.toImmutableList());
	}

	@Value
	@Builder
	public class DBFunctionParams
	{
		final int recordId;
		final int dataImportId;
	}

	final public void doDBFunctionCall(@NonNull final String functionCall, @NonNull DBFunctionParams params, @Nullable final String trxName)
	{
		if (params.getRecordId() > 0)
		{
			DB.executeFunctionCallEx(trxName, "SELECT " + functionCall + "(?,?)", new Object[] { params.getRecordId(), params.getDataImportId() });
		}
		else
		{
			DB.executeFunctionCallEx(trxName, "SELECT " + functionCall + "(?)", new Object[] { params.getDataImportId() });
		}
		log.info("\nCalling " + functionCall);
	}
}
