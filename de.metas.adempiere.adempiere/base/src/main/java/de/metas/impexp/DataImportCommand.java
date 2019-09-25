package de.metas.impexp;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.api.IParams;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.impexp.DataImportResult.DataImportResultBuilder;
import de.metas.impexp.SqlAndParamsExtractor.ParametersExtractor;
import de.metas.impexp.parser.ImpDataLineParser;
import de.metas.impexp.parser.ImpDataLineParserFactory;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.impexp.processing.ImportProcessResult;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
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

final class DataImportCommand
{
	private static final Logger logger = LogManager.getLogger(DataImportCommand.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IImportProcessFactory importProcessFactory;
	private final DataImportRunsService dataImportRunService;
	private final ImpDataLineParserFactory parserFactory = new ImpDataLineParserFactory();

	private static final Charset CHARSET = Charset.forName("UTF-8");
	private static final String SYSCONFIG_InsertBatchSize = "de.metas.impexp.insertBatchSize";
	private static final int SYSCONFIG_InsertBatchSize_DEFAULT = 10000;

	private final ClientId clientId;
	private final OrgId orgId;
	private final UserId userId;
	private final boolean completeDocuments;
	private final IParams additionalParameters;
	private final DataImportConfigId dataImportConfigId;
	private final ImpFormat importFormat;
	private final Resource data;
	private SqlAndParamsExtractor<ImpDataLine> _sqlInsertIntoImportTable; // lazy

	//
	// State
	private int sourceFile_totalLines = 0;
	private int sourceFile_validLines = 0;
	private int sourceFile_linesWithErrors = 0;
	//
	private DataImportRunId dataImportRunId = null;

	@Builder
	private DataImportCommand(
			@NonNull final IImportProcessFactory importProcessFactory,
			@NonNull final DataImportRunsService dataImportRunService,
			//
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final UserId userId,
			final boolean completeDocuments,
			final IParams additionalParameters,
			//
			@NonNull final DataImportConfigId dataImportConfigId,
			@NonNull final ImpFormat importFormat,
			//
			@NonNull final Resource data)
	{
		this.importProcessFactory = importProcessFactory;
		this.dataImportRunService = dataImportRunService;

		this.clientId = clientId;
		this.orgId = orgId;
		this.userId = userId;
		this.completeDocuments = completeDocuments;
		this.additionalParameters = CoalesceUtil.coalesce(additionalParameters, IParams.NULL);
		this.dataImportConfigId = dataImportConfigId;
		this.importFormat = importFormat;
		this.data = data;
	}

	public DataImportResult execute()
	{
		dataImportRunId = dataImportRunService.createNewRun(DataImportRunCreateRequest.builder()
				.orgId(orgId)
				.userId(userId)
				.completeDocuments(completeDocuments)
				.importFormatId(importFormat.getId())
				.dataImportConfigId(dataImportConfigId)
				.build());

		createImportRecordsFromSource();

		final DataImportResultBuilder resultCollector = DataImportResult.builder()
				.dataImportConfigId(dataImportConfigId)
				.importFormatName(importFormat.getName())
				.countSourceFileValidLines(sourceFile_validLines)
				.countSourceFileErrorLines(sourceFile_linesWithErrors);

		final PInstanceId importRecordsSelectionId = createSelectionIdFromDataImportConfigId();
		final ImportProcessResult validateResult = validateImportRecords(importRecordsSelectionId);
		resultCollector
				.importTableName(validateResult.getImportTableName())
				.countImportRecordsWithErrors(validateResult.getCountImportRecordsWithErrors().orElse(-1))
				.targetTableName(validateResult.getTargetTableName());

		scheduleToImportAsync(importRecordsSelectionId);

		return resultCollector.build();
	}

	private void createImportRecordsFromSource()
	{
		final int insertBatchSize = getInsertBatchSize();

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final Stream<List<ImpDataLine>> sourceLines = GuavaCollectors.batchAndStream(streamSourceLines(), insertBatchSize);
		sourceLines.forEach(this::insertIntoDatabase);
		stopwatch.stop();

		logger.debug("Inserted {} source lines into database in {}", sourceFile_totalLines, stopwatch);
	}

	private int getInsertBatchSize()
	{
		return sysConfigBL.getIntValue(SYSCONFIG_InsertBatchSize, SYSCONFIG_InsertBatchSize_DEFAULT);
	}

	private ImportProcessResult validateImportRecords(@NonNull final PInstanceId selectionId)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		try
		{
			return importProcessFactory.newImportProcessForTableName(importFormat.getImportTableName())
					.setCtx(Env.getCtx())
					.clientId(clientId)
					.validateOnly(true)
					.completeDocuments(completeDocuments)
					.setParameters(additionalParameters)
					.selectedRecords(selectionId)
					// .setLoggable(loggable)
					.run();
		}
		finally
		{
			stopwatch.stop();
			logger.debug("Validated import records of selectionId={} in {}", selectionId, stopwatch);
		}
	}

	private Stream<ImpDataLine> streamSourceLines()
	{
		final ImpDataLineParser parser = parserFactory.createParser(importFormat);
		final AtomicInteger nextLineNo = new AtomicInteger(1);

		return streamDataLineStrings()
				.map(lineStr -> createImpDataLine(parser, lineStr, nextLineNo));
	}

	private ImpDataLine createImpDataLine(final ImpDataLineParser parser, String lineStr, final AtomicInteger nextLineNo)
	{
		try
		{
			return ImpDataLine.builder()
					.fileLineNo(nextLineNo.getAndIncrement())
					.lineStr(lineStr)
					.cells(parser.parseDataCells(lineStr))
					.build();
		}
		catch (Exception ex)
		{
			return ImpDataLine.builder()
					.fileLineNo(nextLineNo.getAndIncrement())
					.lineStr(lineStr)
					.parseError(ErrorMessage.of(ex))
					.build();
		}
	}

	private Stream<String> streamDataLineStrings()
	{
		try
		{
			if (importFormat.isMultiLine())
			{
				return FileImportReader.readMultiLines(getData(), CHARSET).stream();
			}
			else
			{
				return FileImportReader.readRegularLines(getData(), CHARSET).stream();
			}
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed reading attachment", ex);
		}
	}

	private byte[] getData()
	{
		try
		{
			return Util.readBytes(data.getInputStream());
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed reading resource: " + data, ex);
		}
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
				sourceFile_totalLines++;
				if (line.hasErrors())
				{
					sourceFile_linesWithErrors++;
				}
				else
				{
					sourceFile_validLines++;
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
		final ImportTableDescriptor importTableDescriptor = importFormat.getImportTableDescriptor();
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
			final List<ImpFormatColumn> columns = importFormat.getColumns();
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

	private PInstanceId createSelectionIdFromDataImportConfigId()
	{
		Check.assumeNotNull(dataImportRunId, "dataImportRunId is not null");

		final ImportTableDescriptor importTableDescriptor = importFormat.getImportTableDescriptor();
		return Services.get(IQueryBL.class)
				.createQueryBuilder(importTableDescriptor.getTableName())
				.addEqualsFilter(ImportTableDescriptor.COLUMNNAME_C_DataImport_Run_ID, dataImportRunId)
				.addEqualsFilter(ImportTableDescriptor.COLUMNNAME_I_ErrorMsg, null)
				.create()
				.createSelection();
	}

	private void scheduleToImportAsync(final PInstanceId selectionId)
	{
		importProcessFactory
				.newAsyncImportProcessBuilder()
				.setCtx(Env.getCtx())
				.setImportTableName(importFormat.getImportTableName())
				.setImportFromSelectionId(selectionId)
				.buildAndEnqueue();
	}

}
