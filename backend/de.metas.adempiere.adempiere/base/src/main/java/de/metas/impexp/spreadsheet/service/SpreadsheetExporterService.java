/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.impexp.spreadsheet.service;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpreadsheetExporterService
{
	private static final Logger logger = LogManager.getLogger(SpreadsheetExporterService.class);

	public void processDataFromSQL(
			@NonNull final String sql,
			@NonNull final DataConsumer<ResultSet> dataConsumer)
	{
		processDataFromSQL(sql, Evaluatees.empty(), dataConsumer);
	}

	/**
	 * Execute the given query and add the result to the given {@code dataConsumer}.
	 *
	 * @param evalCtx can be used to replace expressions like e.g. {@code @C_BPartner_ID/0@} from the given {@code sql}.
	 */
	public void processDataFromSQL(
			@NonNull final String sql,
			@NonNull final Evaluatee evalCtx,
			@NonNull final DataConsumer<ResultSet> dataConsumer)
	{
		final String sqlParsed = StringExpressionCompiler.instance
				.compile(sql)
				.evaluate(evalCtx, OnVariableNotFound.Fail);

		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			loggable.addLog("Execute SQL={}", sqlParsed);

			final ImmutablePair<Connection, PreparedStatement> connAndStmt = DB.prepareConnectionAndStatementForDataExport(sqlParsed, null/* sqlParams */);
			conn = connAndStmt.getLeft();
			pstmt = connAndStmt.getRight();

			rs = pstmt.executeQuery();

			loggable.addLog("Execute SQL done; push data to dataConsumer={}", dataConsumer);
			final ResultSetMetaData meta = rs.getMetaData();

			// always show spreadsheet header, even if there are no rows
			final List<String> header = new ArrayList<>();
			for (int col = 1; col <= meta.getColumnCount(); col++)
			{
				final String columnName = meta.getColumnLabel(col);
				header.add(columnName);
			}
			// we need to do the consuming right here, while the resultset is open.
			dataConsumer.putHeader(header);
			dataConsumer.putResult(rs);

			loggable.addLog("Push data to dataConsumer done");
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sqlParsed);
		}
		finally
		{
			DB.close(rs, pstmt);
			DB.close(conn);
		}
	}
}
