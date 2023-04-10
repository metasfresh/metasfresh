/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.trace.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.trace.HUTraceEventQuery;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.handlingunits.trace.HUTraceType;
import de.metas.impexp.spreadsheet.excel.JdbcExcelExporter;
import de.metas.impexp.spreadsheet.service.SpreadsheetExporterService;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.Param;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.apache.poi.ss.usermodel.Font;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Trx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class M_HU_Trace_Report_Excel extends JavaProcess
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
	protected String doIt() throws Exception
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

		if (p_VHU_ID != null)
		{
			huTraceEventQueryBuilder.vhuId(p_VHU_ID);
		}

		final HUTraceEventQuery huTraceEventQuery = huTraceEventQueryBuilder
				.types(HUTraceType.typesToReport())
				.recursionMode(HUTraceEventQuery.RecursionMode.BOTH)
				.build();

		final PInstanceId pInstanceId = huTraceRepository.queryToSelection(huTraceEventQuery);

		if (pInstanceId == null)
		{
			throw new AdempiereException("@NotFound@: " + huTraceEventQuery);
		}

		Services.get(ITrxManager.class).commit(Trx.TRXNAME_ThreadInherited);
		final JdbcExcelExporter jdbcExcelExporter = JdbcExcelExporter.builder()
				.ctx(getCtx())
				.columnHeaders(getColumnHeaders())
				.build();

		jdbcExcelExporter.setFontCharset(Font.ANSI_CHARSET);

		spreadsheetExporterService.processDataFromSQL(getSql(pInstanceId), jdbcExcelExporter);

		final File tempFile = jdbcExcelExporter.getResultFile();

		final boolean backEndOrSwing = Ini.getRunMode() == Adempiere.RunMode.BACKEND || Ini.isSwingClient();

		if (backEndOrSwing)
		{
			Env.startBrowser(tempFile.toURI().toString());
		}
		else
		{
			getResult().setReportData(tempFile);
		}

		return MSG_OK;
	}

	private String getSql(@NonNull final PInstanceId pinstanceId)
	{

		final StringBuilder sqlBuilder = new StringBuilder().append(" SELECT  * FROM M_HU_Trace_Report(")
				.append(pinstanceId.getRepoId())
				.append(")");

		return sqlBuilder.toString();
	}

	private List<String> getColumnHeaders()
	{
		final List<String> columnHeaders = new ArrayList<>();
		columnHeaders.add(I_M_HU_Trace.COLUMNNAME_LotNumber);
		columnHeaders.add(I_M_HU_Trace.COLUMNNAME_HUTraceType);
		columnHeaders.add(I_M_HU_Trace.COLUMNNAME_M_Product_ID);
		columnHeaders.add(I_M_HU_Trace.COLUMNNAME_M_InOut_ID);
		columnHeaders.add(I_M_HU_Trace.COLUMNNAME_PP_Cost_Collector_ID);
		columnHeaders.add(I_M_HU_Trace.COLUMNNAME_PP_Order_ID);
		columnHeaders.add(I_M_HU_Trace.COLUMNNAME_M_Inventory_ID);
		columnHeaders.add(I_M_InOut.COLUMNNAME_MovementDate);
		columnHeaders.add(I_M_HU_Trace.COLUMNNAME_Qty);
		columnHeaders.add(I_M_Product.COLUMNNAME_C_UOM_ID);

		return columnHeaders;
	}

}