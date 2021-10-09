package de.metas.impexp;

import java.time.Duration;

import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.impexp.parser.ImpDataLine;
import de.metas.logging.LogManager;
import lombok.Getter;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Getter
@ToString
class MockedInsertIntoImportTableService implements InsertIntoImportTableService
{
	private static final Logger logger = LogManager.getLogger(MockedInsertIntoImportTableService.class);

	static
	{
		LogManager.setLoggerLevel(logger, Level.INFO);
	}

	private InsertIntoImportTableRequest lastRequest;
	private ImmutableList<ImpDataLine> lastRequestLines;
	private InsertIntoImportTableResult lastResult;

	@Override
	public InsertIntoImportTableResult insertData(final InsertIntoImportTableRequest request)
	{
		logger.info("Got request: {}", request);
		this.lastRequest = request;

		final ImmutableList<ImpDataLine> lines = request.getStream()
				.collect(ImmutableList.toImmutableList());
		this.lastRequestLines = lines;
		logger.info("Got {} lines: {}", lines.size(), lines);

		final InsertIntoImportTableResult result = InsertIntoImportTableResult.builder()
				.fromResource(null)
				.toImportTableName(request.getImportFormat().getImportTableName())
				.importFormatName(request.getImportFormat().getName())
				.dataImportConfigId(request.getDataImportConfigId())
				//
				.duration(Duration.ZERO)
				.dataImportRunId(request.getDataImportRunId())
				.countTotalRows(lines.size())
				.countValidRows(lines.size())
				//
				.build();
		this.lastResult = result;
		logger.info("Returning: {}", result);

		return result;
	}

}
