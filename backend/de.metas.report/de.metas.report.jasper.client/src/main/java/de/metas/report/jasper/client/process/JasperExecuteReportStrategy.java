package de.metas.report.jasper.client.process;

import de.metas.process.ProcessInfo;
import de.metas.report.ExecuteReportStrategy;
import de.metas.report.client.ReportsClient;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportResult;
import de.metas.util.Check;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import org.springframework.stereotype.Component;

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
 * This is the default strategy that is always used, unless specified differently.
 * See {@link ExecuteReportStrategy} on how to invoke your on implementation.
 */
@Component
public class JasperExecuteReportStrategy implements ExecuteReportStrategy
{
	@Override
	public ExecuteReportResult executeReport(
			@NonNull final ProcessInfo processInfo,
			@Nullable final OutputType outputType)
	{
		final OutputType outputTypeEffective = Check.assumeNotNull(
				CoalesceUtil.coalesce(
						outputType,
						processInfo.getJRDesiredOutputType()),
				"From the given parameters, either outputType or processInfo.getJRDesiredOutputType() need to be non-null; processInfo={}",
				processInfo);

		final ReportsClient reportsClient = ReportsClient.get();
		final ReportResult reportResult = reportsClient.report(processInfo, outputTypeEffective);
		final byte[] reportData = reportResult.getReportContent();
		final String reportFilename = reportResult.getReportFilename();

		if (Check.isBlank(reportFilename)) // if the report returns some blanks, we ignore them
		{
			return ExecuteReportResult.of(outputTypeEffective, reportData);
		}
		else
		{
			return ExecuteReportResult.of(reportFilename, outputTypeEffective, reportData);
		}
	}
}
