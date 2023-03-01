/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.impexp.util;

import de.metas.common.util.time.SystemTime;
import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.format.ImpFormatColumn;
import de.metas.report.ReportResultData;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ByteArrayResource;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@UtilityClass
public class GenerateCSVTemplate
{
	private static final String CSV_TEMPLATE_NAME = ":Name_Template_:timestamp.csv";
	private static final String FILENAME_PLACEHOLDER = ":Name";
	private static final String TIMESTAMP_PLACEHOLDER = ":timestamp";
	private static final String CSV_FORMAT = "text/csv";

	@NonNull
	public ReportResultData generateCSVTemplate(@NonNull final ImpFormat impFormat)
	{
		final String header = impFormat.getColumns()
				.stream()
				.map(ImpFormatColumn::getColumnName)
				.collect(Collectors.joining(impFormat.getDelimiter()));

		return buildResult(header, impFormat.getName());
	}

	@NonNull
	private ReportResultData buildResult(@NonNull final String fileData, @NonNull final String fileName)
	{
		final byte[] fileDataBytes = fileData.getBytes(StandardCharsets.UTF_8);

		return ReportResultData.builder()
				.reportData(new ByteArrayResource(fileDataBytes))
				.reportFilename(CSV_TEMPLATE_NAME
										.replace(FILENAME_PLACEHOLDER, fileName)
										.replace(TIMESTAMP_PLACEHOLDER, String.valueOf(SystemTime.asInstant())))
				.reportContentType(CSV_FORMAT)
				.build();
	}
}
