package de.metas.report;

import de.metas.process.ProcessInfo;
import de.metas.report.server.OutputType;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.springframework.core.io.Resource;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * Implement this interface to a have a report process creating custom binary data.
 * <p>
 * Common use case: a print format needs to consist of the "actual" document plus further concatenated documents.
 * To achieve this:
 * <li>write a custom implementation of this interface</li>
 * <li>subclass {@link ReportStarter}</li>
 * <li>implement {@link ReportStarter#getExecuteReportStrategy()} to return your implementation</li>
 * <li>set your subclass as {@code AD_Process.Classname} in the respective jasper-process (which is referenced by the {@code AD_PrintFormat})</li>
 */
public interface ExecuteReportStrategy
{
	/**
	 * @return never return {@code null}.
	 */
	ExecuteReportResult executeReport(ProcessInfo processInfo, OutputType outputType);

	@Value
	@ToString(exclude = "reportData")
	class ExecuteReportResult
	{
		public static ExecuteReportResult of(@NonNull final OutputType outputType, @NonNull final Resource reportData)
		{
			return new ExecuteReportResult(null, outputType, reportData);
		}

		public static ExecuteReportResult of(@NonNull final String filename, @NonNull final OutputType outputType, @NonNull final Resource reportData)
		{
			return new ExecuteReportResult(filename, outputType, reportData);
		}

		private  ExecuteReportResult(@Nullable final String filename, final OutputType outputType, @NonNull final Resource reportData)
		{
			this.filename = filename;
			this.outputType = outputType;
			this.reportData = reportData;
		}

		@Nullable
		String filename;

		OutputType outputType;

		Resource reportData;
	}
}
