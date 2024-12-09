package de.metas.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.util.Check;
import de.metas.util.lang.SpringResourceUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.io.FileUtils;
import org.compiere.util.MimeType;
<<<<<<< HEAD
=======
import org.jetbrains.annotations.NotNull;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

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
public class ReportResultData
{
	Resource reportData;

	String reportFilename;

	String reportContentType;

	@Builder
	private ReportResultData(
			@NonNull final Resource reportData,
			@NonNull final String reportFilename,
			@NonNull final String reportContentType)
	{
		this.reportData = reportData;
		this.reportFilename = reportFilename;
		this.reportContentType = reportContentType;
	}

	@JsonCreator
	private ReportResultData(
			@JsonProperty("reportData") final byte[] reportData,
			@JsonProperty("reportFilename") @NonNull final String reportFilename,
			@JsonProperty("reportContentType") @NonNull final String reportContentType)
	{
		this.reportData = SpringResourceUtils.fromByteArray(reportData);
		this.reportFilename = reportFilename;
		this.reportContentType = reportContentType;
	}

	@JsonProperty("reportData")
	public byte[] getReportDataByteArray()
	{
		return SpringResourceUtils.toByteArray(reportData);
	}

	public static ReportResultData ofFile(@NonNull final File file)
	{
		final String reportFilename = file.getName();

		return ReportResultData.builder()
				.reportData(new FileSystemResource(file))
				.reportFilename(reportFilename)
				.reportContentType(MimeType.getMimeType(reportFilename))
				.build();
	}

<<<<<<< HEAD
=======
	public static ReportResultData ofFile(final @NotNull File file, @NotNull final String reportFilename)
	{
		return ReportResultData.builder()
				.reportData(new FileSystemResource(file))
				.reportFilename(reportFilename)
				.reportContentType(MimeType.getMimeType(reportFilename))
				.build();
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@JsonIgnore
	public boolean isEmpty()
	{
		try
		{
			return reportData.contentLength() <= 0;
		}
		catch (final IOException e)
		{
			return true; // reportdata couldn't be resolved, so we say it's empty
		}

	}

	public File writeToTemporaryFile(@NonNull final String filenamePrefix)
	{
		final File file = createTemporaryFile(filenamePrefix);
		try
		{
			FileUtils.copyInputStreamToFile(reportData.getInputStream(), file);
			return file;
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed writing " + file.getAbsolutePath(), ex);
		}
	}

	@NonNull
	private File createTemporaryFile(@NonNull final String filenamePrefix)
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
