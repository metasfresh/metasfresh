package de.metas.datev.process;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import org.adempiere.util.Check;
import org.compiere.Adempiere;
import org.compiere.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Joiner;

import de.metas.data.export.api.IExportDataSource;
import de.metas.data.export.api.impl.JdbcExporterBuilder;
import de.metas.datev.DATEVCsvExporter;
import de.metas.datev.DATEVExportFormat;
import de.metas.datev.DATEVExportFormatRepository;
import de.metas.datev.model.I_DATEV_Export;
import de.metas.datev.model.I_DATEV_ExportFormat;
import de.metas.datev.model.I_DATEV_ExportLine;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-datev
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

public class DATEV_ExportFile extends JavaProcess implements IProcessPrecondition
{
	@Autowired
	private DATEVExportFormatRepository exportFormatRepo;

	@Param(parameterName = I_DATEV_ExportFormat.COLUMNNAME_DATEV_ExportFormat_ID, mandatory = true)
	private int datevExportFormatId;

	public DATEV_ExportFile()
	{
		Adempiere.autowire(this);
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final DATEVExportFormat exportFormat = exportFormatRepo.getById(datevExportFormatId);
		final I_DATEV_Export datevExport = getRecord(I_DATEV_Export.class);

		final IExportDataSource dataSource = createDataSource(exportFormat, datevExport.getDATEV_Export_ID());

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		DATEVCsvExporter.builder()
				.exportFormat(exportFormat)
				.dataSource(dataSource)
				.build()
				.export(out);

		getResult().setReportData(
				out.toByteArray(), // data
				buildFilename(datevExport), // filename
				"text/csv"); // content type

		return MSG_OK;
	}

	private IExportDataSource createDataSource(@NonNull final DATEVExportFormat exportFormat, final int datevExportId)
	{
		Check.assume(datevExportId > 0, "datevExportId > 0");

		final JdbcExporterBuilder builder = new JdbcExporterBuilder(I_DATEV_ExportLine.Table_Name)
				.addEqualsWhereClause(I_DATEV_ExportLine.COLUMNNAME_DATEV_Export_ID, datevExportId)
				.addOrderBy(I_DATEV_ExportLine.COLUMNNAME_DATEV_ExportLine_ID);

		exportFormat
				.getColumns()
				.forEach(formatColumn -> builder.addField(formatColumn.getCsvHeaderName(), formatColumn.getColumnName()));

		return builder.createDataSource();
	}

	private static String buildFilename(final I_DATEV_Export datevExport)
	{
		final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		final Timestamp dateAcctFrom = datevExport.getDateAcctFrom();
		final Timestamp dateAcctTo = datevExport.getDateAcctTo();

		return Joiner.on("_")
				.skipNulls()
				.join("datev",
						dateAcctFrom != null ? dateFormatter.format(TimeUtil.asLocalDate(dateAcctFrom)) : null,
						dateAcctTo != null ? dateFormatter.format(TimeUtil.asLocalDate(dateAcctTo)) : null)
				+ ".csv";
	}
}
