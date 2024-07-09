package de.metas.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.util.time.SystemTime;
import de.metas.util.Check;
import de.metas.util.FileUtil;
import de.metas.util.StringUtils;
import de.metas.util.lang.SpringResourceUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.io.FileUtils;
import org.compiere.util.MimeType;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

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
	@NonNull Resource reportData;

	/**
	 * The filename to be used when the report data is stored (either via browser or directly on the metasfresh-server).
	 * Might be "influenced" on the go, see {@link #withReportFilename(String)}.
	 */
	@NonNull String reportFilename;
	@NonNull String reportContentType;

	@Builder(toBuilder = true)
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

		return ofFile(file, reportFilename);
	}

	public static ReportResultData ofFile(final @NotNull File file, @NotNull final String reportFilename)
	{
		return ReportResultData.builder()
				.reportData(new FileSystemResource(file))
				.reportFilename(reportFilename)
				.reportContentType(MimeType.getMimeType(reportFilename))
				.build();
	}

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

	public Path writeToDirectory(@NonNull final Path directory)
	{
		final Path file = FileUtil.findNotExistingFile(directory, getReportFilename(), 200)
				.orElseGet(() -> {
					final String extWithDot = MimeType.getExtensionByType(reportContentType);
					final String fileBaseName = FileUtil.getFileBaseName(getReportFilename());
					return directory.resolve(fileBaseName + "_" + SystemTime.millis() + extWithDot);
				});

		try
		{
			Files.copy(reportData.getInputStream(), file, StandardCopyOption.REPLACE_EXISTING);
			return file;
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed writing " + file, ex);
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

	public ReportResultData withReportFilename(@NonNull final String reportFilename)
	{
		final String reportFilenameNorm = StringUtils.trim(reportFilename);
		return !Objects.equals(this.reportFilename, reportFilenameNorm)
				? toBuilder().reportFilename(reportFilenameNorm).build()
				: this;
	}
}
