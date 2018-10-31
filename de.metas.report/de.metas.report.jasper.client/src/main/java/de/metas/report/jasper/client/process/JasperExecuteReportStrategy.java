package de.metas.report.jasper.client.process;

import org.springframework.stereotype.Component;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.process.ProcessInfo;
import de.metas.report.ExecuteReportStrategy;
import de.metas.report.jasper.client.JRClient;
import lombok.NonNull;



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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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
			@NonNull final OutputType outputType)
	{
		final JRClient jrClient = JRClient.get();
		final byte[] reportData = jrClient.report(processInfo, outputType);

		return new ExecuteReportResult(outputType, reportData);
	}
}
