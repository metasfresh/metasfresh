package de.metas.ui.web.process;

import org.adempiere.util.Check;
import org.compiere.print.JRReportViewerProvider;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.process.ProcessInfo;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * An {@link JRReportViewerProvider} implementation which collects the report data and returns it via {@link #getReportData()} and {@link #getReportType()}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WebuiJRReportViewerProvider implements JRReportViewerProvider
{
	private final OutputType desiredReportType;
	
	private OutputType reportType;
	private byte[] reportData = null;

	public WebuiJRReportViewerProvider()
	{
		desiredReportType = OutputType.PDF;
		reportType = null;
	}

	@Override
	public void openViewer(final byte[] data, final OutputType type, final ProcessInfo pi) throws Exception
	{
		this.reportType = type;
		this.reportData = data;
	}

	@Override
	public OutputType getDesiredOutputType()
	{
		return desiredReportType;
	}

	public byte[] getReportData()
	{
		return reportData;
	}

	public OutputType getReportType()
	{
		Check.assumeNotNull(reportType, "Parameter type is not null");
		return reportType;
	}
}
