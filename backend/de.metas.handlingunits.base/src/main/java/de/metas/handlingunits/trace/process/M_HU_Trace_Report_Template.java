/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.handlingunits.trace.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEventQuery;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.handlingunits.trace.HUTraceType;
import de.metas.impexp.spreadsheet.excel.JdbcExcelExporter;
import de.metas.impexp.spreadsheet.service.SpreadsheetExporterService;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.apache.poi.ss.usermodel.Font;
import org.compiere.SpringContextHolder;

import java.io.File;
import java.util.List;
import java.util.Set;

public abstract class M_HU_Trace_Report_Template extends JavaProcess
{
	private final HUTraceRepository huTraceRepository = SpringContextHolder.instance.getBean(HUTraceRepository.class);
	private final SpreadsheetExporterService spreadsheetExporterService = SpringContextHolder.instance.getBean(SpreadsheetExporterService.class);

	@Param(parameterName = I_M_HU_Trace.COLUMNNAME_M_Product_ID)
	private ProductId p_M_Product_ID;

	@Param(parameterName = I_M_HU_Trace.COLUMNNAME_LotNumber)
	private String p_LotNumber;

	@Param(parameterName = I_M_HU_Trace.COLUMNNAME_VHU_ID)
	private HuId p_VHU_ID;

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final HUTraceEventQuery.HUTraceEventQueryBuilder huTraceEventQueryBuilder = HUTraceEventQuery.builder();

		if (p_M_Product_ID != null)
		{
			huTraceEventQueryBuilder.productId(p_M_Product_ID);
		}

		if (p_LotNumber != null)
		{
			huTraceEventQueryBuilder.lotNumber(p_LotNumber);
		}

		final HUTraceEventQuery huTraceEventQuery = huTraceEventQueryBuilder
				.types(getTraceTypes())
				.recursionMode(HUTraceEventQuery.RecursionMode.BOTH)
				.build();

		final PInstanceId pInstanceId = huTraceRepository.queryToSelection(huTraceEventQuery);
		if (pInstanceId == null)
		{
			throw new AdempiereException("@NotFound@: " + huTraceEventQuery);
		}

		final JdbcExcelExporter jdbcExcelExporter = JdbcExcelExporter.builder()
				.ctx(getCtx())
				.columnHeaders(getColumnHeaders())
				.build();

		jdbcExcelExporter.setFontCharset(Font.ANSI_CHARSET);

		spreadsheetExporterService.processDataFromSQL(getSql(pInstanceId), jdbcExcelExporter);

		final File tempFile = jdbcExcelExporter.getResultFile();

		getResult().setReportData(tempFile);

		return MSG_OK;
	}

	@NonNull
	protected abstract Set<HUTraceType> getTraceTypes();

	@NonNull
	protected abstract String getSql(@NonNull PInstanceId pinstanceId);

	@NonNull
	protected abstract List<String> getColumnHeaders();
}
