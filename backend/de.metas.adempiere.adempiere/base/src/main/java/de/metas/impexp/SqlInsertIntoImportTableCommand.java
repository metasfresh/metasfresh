package de.metas.impexp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.impexp.config.DataImportConfigId;
import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.format.ImpFormatColumn;
import de.metas.impexp.format.ImportTableDescriptor;
import de.metas.impexp.parser.ImpDataLine;
import de.metas.impexp.util.SqlAndParamsExtractor;
import de.metas.impexp.util.SqlAndParamsExtractor.ParametersExtractor;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

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
 * Inserts {@link ImpDataLine}s into import tables.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
final class SqlInsertIntoImportTableCommand
{
	// services
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private static final int DEFAULT_InsertBatchSize = 10000;

	//
	// Parameters
	private final String importFormatName;
	private final ImportTableDescriptor importTableDescriptor;
	private final ImmutableList<ImpFormatColumn> columns;
	private final ClientId clientId;
	private final OrgId orgId;
	private final UserId userId;
	private final DataImportRunId dataImportRunId;
	private final DataImportConfigId dataImportConfigId;
	private final int insertBatchSize;
	private Stream<ImpDataLine> linesStream;

	//
	// State
	private SqlAndParamsExtractor<ImpDataLine> _sqlInsertIntoImportTable; // lazy
	private int countTotalRows = 0;
	private int countValidRows = 0;
	private final ArrayList<InsertIntoImportTableResult.Error> errors = new ArrayList<>();

	@Builder
	private SqlInsertIntoImportTableCommand(
			@NonNull final ImpFormat importFormat,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final UserId userId,
			@NonNull final DataImportRunId dataImportRunId,
			@Nullable final DataImportConfigId dataImportConfigId,
			final int insertBatchSize,
			@NonNull final Stream<ImpDataLine> linesStream)
	{
		this.importTableDescriptor = importFormat.getImportTableDescriptor();
		this.importFormatName = importFormat.getName();
		this.columns = importFormat.getColumns();

		this.clientId = clientId;
		this.orgId = orgId;
		this.userId = userId;
		this.dataImportRunId = dataImportRunId;
		this.dataImportConfigId = dataImportConfigId;
		this.insertBatchSize = insertBatchSize > 0 ? insertBatchSize : DEFAULT_InsertBatchSize;

		this.linesStream = linesStream;
	}

	public InsertIntoImportTableResult execute()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final Stream<List<ImpDataLine>> batchedStream = GuavaCollectors.batchAndStream(linesStream, insertBatchSize);
		batchedStream.forEach(this::insertIntoDatabase);
		stopwatch.stop();

		return InsertIntoImportTableResult.builder()
				.fromResource(null) // N/A
				.toImportTableName(importTableDescriptor.getTableName())
				.importFormatName(importFormatName)
				.dataImportConfigId(dataImportConfigId)
				//
				.duration(TimeUtil.toDuration(stopwatch))
				.dataImportRunId(dataImportRunId)
				.countTotalRows(countTotalRows)
				.countValidRows(countValidRows)
				.errors(errors)
				//
				.build();
	}

	private void insertIntoDatabase(final List<ImpDataLine> lines)
	{
		if (lines.isEmpty())
		{
			return;
		}

		trxManager.run(ITrx.TRXNAME_ThreadInherited, () -> insertIntoDatabaseInTrx(lines));
	}

	private void insertIntoDatabaseInTrx(final List<ImpDataLine> lines)
	{
		final SqlAndParamsExtractor<ImpDataLine> sqlAndParamsExtractor = getInsertIntoImportTableSql();
		final String sql = sqlAndParamsExtractor.getSql();

		PreparedStatement pstmt = null;
		final ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);

			for (final ImpDataLine line : lines)
			{
				final List<Object> params = sqlAndParamsExtractor.extractParameters(line);
				DB.setParameters(pstmt, params);
				pstmt.addBatch();

				// Update stats:
				countTotalRows++;
				if (line.hasErrors())
				{
					errors.add(InsertIntoImportTableResult.Error.builder()
							.message(line.getErrorMessageAsStringOrNull())
							.lineNo(line.getFileLineNo())
							.lineContent(line.getLineString())
							.build());
				}
				else
				{
					countValidRows++;
				}
			}

			pstmt.executeBatch();
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private SqlAndParamsExtractor<ImpDataLine> getInsertIntoImportTableSql()
	{
		SqlAndParamsExtractor<ImpDataLine> sqlInsertIntoImportTable = this._sqlInsertIntoImportTable;
		if (sqlInsertIntoImportTable == null)
		{
			sqlInsertIntoImportTable = this._sqlInsertIntoImportTable = createInsertIntoImportTableSql();
		}
		return sqlInsertIntoImportTable;
	}

	/**
	 * IMPORTANT: keep in sync with {@link #extractSqlInsertParams(ImpDataLine)}
	 */
	private SqlAndParamsExtractor<ImpDataLine> createInsertIntoImportTableSql()
	{
		final String tableName = importTableDescriptor.getTableName();
		final String keyColumnName = importTableDescriptor.getKeyColumnName();

		final StringBuilder sqlColumns = new StringBuilder();
		final StringBuilder sqlValues = new StringBuilder();
		final List<ParametersExtractor<ImpDataLine>> sqlParamsExtractors = new ArrayList<>();

		sqlColumns.append(keyColumnName);
		sqlValues.append(DB.TO_TABLESEQUENCE_NEXTVAL(tableName));

		//
		// Standard fields
		sqlColumns.append(", AD_Client_ID");
		sqlValues.append(", ").append(clientId.getRepoId());
		//
		sqlColumns.append(", AD_Org_ID");
		sqlValues.append(", ").append(orgId.getRepoId());
		//
		sqlColumns.append(", Created,CreatedBy,Updated,UpdatedBy,IsActive");
		sqlValues.append(", now(),").append(userId.getRepoId()).append(",now(),").append(userId.getRepoId()).append(",'Y'");
		//
		sqlColumns.append(", Processed, I_IsImported");
		sqlValues.append(", 'N', 'N'");

		//
		// I_LineNo
		if (importTableDescriptor.getImportLineNoColumnName() != null)
		{
			sqlColumns.append(", ").append(importTableDescriptor.getImportLineNoColumnName());
			sqlValues.append(", ?");
			sqlParamsExtractors.add(dataLine -> ImmutableList.of(dataLine.getFileLineNo()));
		}

		//
		// I_LineContext
		if (importTableDescriptor.getImportLineNoColumnName() != null)
		{
			sqlColumns.append(", ").append(importTableDescriptor.getImportLineContentColumnName());
			sqlValues.append(", ?");
			sqlParamsExtractors.add(dataLine -> Collections.singletonList(dataLine.getLineString()));
		}

		//
		// C_DataImport_Run_ID
		{
			Check.assumeNotNull(dataImportRunId, "dataImportRunId is not null");
			sqlColumns.append(", ").append(ImportTableDescriptor.COLUMNNAME_C_DataImport_Run_ID);
			sqlValues.append(", ").append(dataImportRunId.getRepoId());
		}

		//
		// C_DataImport_ID
		if (importTableDescriptor.getDataImportConfigIdColumnName() != null && dataImportConfigId != null)
		{
			sqlColumns.append(", ").append(importTableDescriptor.getDataImportConfigIdColumnName());
			sqlValues.append(", ").append(dataImportConfigId.getRepoId());
		}

		//
		// I_ErrorMsg
		{
			final int errorMaxLength = importTableDescriptor.getErrorMsgMaxLength();
			sqlColumns.append(", ").append(ImportTableDescriptor.COLUMNNAME_I_ErrorMsg);
			sqlValues.append(", ?");
			sqlParamsExtractors.add(dataLine -> Collections.singletonList(dataLine.getErrorMessageAsStringOrNull(errorMaxLength)));
		}

		//
		// Values
		{
			for (final ImpFormatColumn column : columns)
			{
				sqlColumns.append(", ").append(column.getColumnName());
				sqlValues.append(", ?");
			}
			sqlParamsExtractors.add(dataLine -> dataLine.getJdbcValues(columns));
		}

		return SqlAndParamsExtractor.<ImpDataLine> builder()
				.sql("INSERT INTO " + tableName + "(" + sqlColumns + ") VALUES (" + sqlValues + ")")
				.parametersExtractors(sqlParamsExtractors)
				.build();
	}

}
