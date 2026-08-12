package de.metas.impexp;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.impexp.format.ImportTableDescriptor;
import de.metas.impexp.parser.ImpDataLine;
import de.metas.logging.LogManager;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.HashMap;

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

	private final HashMap<String, Class<?>> importModelClassByTableName = new HashMap<>();
	private InsertIntoImportTableRequest lastRequest;
	private ImmutableList<ImpDataLine> lastRequestLines;
	private InsertIntoImportTableResult lastResult;

	public void registerImportModelClass(@NonNull final String importTableName, @NonNull final Class<?> importModelClass)
	{
		importModelClassByTableName.put(importTableName, importModelClass);
	}

	@Override
	public InsertIntoImportTableResult insertData(final InsertIntoImportTableRequest request)
	{
		logger.info("Got request: {}", request);
		this.lastRequest = request;

		final ImmutableList<ImpDataLine> lines = request.getStream()
				.collect(ImmutableList.toImmutableList());
		this.lastRequestLines = lines;
		logger.info("Got {} lines: {}", lines.size(), lines);

		if (!lines.isEmpty())
		{
			final Class<?> importModelClass = getImportModelClass(request.getImportFormat().getImportTableName());
			lines.forEach(line -> {
				final Object importRecord = InterfaceWrapperHelper.newInstance(importModelClass);
				InterfaceWrapperHelper.setValue(importRecord, ImportTableDescriptor.COLUMNNAME_C_DataImport_Run_ID, request.getDataImportRunId().getRepoId());
				// just set the essential
				InterfaceWrapperHelper.save(importRecord);
			});
		}

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

	private Class<?> getImportModelClass(final String modelTableName)
	{
		final Class<?> importModelClass = importModelClassByTableName.get(modelTableName);
		if (importModelClass == null)
		{
			throw new AdempiereException("No import model class found for importTableName=" + modelTableName + "."
					+ " Registered tables are: " + importModelClassByTableName);
		}
		return importModelClass;
	}

}
