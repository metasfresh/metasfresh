package de.metas.report;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.io.Files;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.MimeType;
import org.compiere.util.Util;

import java.io.File;
import java.io.IOException;

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
 * Tiny and hopefully helpful class to exchange reporting data.
 */
@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(builder = ReportResultData.ReportResultDataBuilder.class)
public class ReportResultData
{
	byte[] reportData;
	String reportFilename;
	String reportContentType;

	@Builder
	private ReportResultData(
			@NonNull final byte[] reportData,
			@NonNull final String reportFilename,
			@NonNull final String reportContentType)
	{
		this.reportData = reportData;
		this.reportFilename = reportFilename;
		this.reportContentType = reportContentType;
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class ReportResultDataBuilder
	{
	}

	public static ReportResultData ofFile(@NonNull final File file)
	{
		final String reportFilename = file.getName();

		return ReportResultData.builder()
				.reportData(Util.readBytes(file))
				.reportFilename(reportFilename)
				.reportContentType(MimeType.getMimeType(reportFilename))
				.build();
	}

	public boolean isEmpty()
	{
		return reportData == null || reportData.length <= 0;
	}

	public File writeToTemporaryFile(final String filenamePrefix)
	{
		final File file = createTemporaryFile(filenamePrefix);

		try
		{
			Files.write(reportData, file);
			return file;
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed writing " + file.getAbsolutePath(), ex);
		}

	}

	@NonNull
	private File createTemporaryFile(final String filenamePrefix)
	{
		try
		{
			final String ext = MimeType.getExtensionByType(reportContentType);
			final String suffix = Check.isNotBlank(ext) ? "." + ext : "";
			return File.createTempFile(filenamePrefix, suffix);
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed creating temporary file with `" + filenamePrefix + "` prefix", ex);
		}
	}
}
