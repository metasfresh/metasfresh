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

import com.google.common.collect.ImmutableList;
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
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.apache.poi.ss.usermodel.Font;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;

import java.io.File;
import java.util.List;

/**
 * HU Trace Report for Retailers - Focus on IFS compliance and sales control
 * This report is designed for retailers and focuses on:
 * - Receipt and shipment information (no production data)
 * - Customer/recipient details
 * - Sales order and invoice references
 * - Shipper and delivery information
 * - Complete supply chain traceability
 * Used for IFS Food Safety compliance and sales control.
 */
public class M_HU_Trace_Report_Retailer_Excel extends JavaProcess
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
				.types(HUTraceType.typesToReportForRetailer())
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

	private static String getSql(@NonNull final PInstanceId pinstanceId)
	{
		return " SELECT  * FROM M_HU_Trace_Report_Retailer(" + pinstanceId.getRepoId() + ")";
	}

	private static List<String> getColumnHeaders()
	{
		return ImmutableList.of(
				I_M_HU_Trace.COLUMNNAME_LotNumber,
				I_M_HU_Trace.COLUMNNAME_HUTraceType,
				I_M_HU_Trace.COLUMNNAME_M_Product_ID,
				I_M_HU_Trace.COLUMNNAME_M_InOut_ID,
				I_M_InOut.COLUMNNAME_MovementDate,
				I_M_HU_Trace.COLUMNNAME_Qty,
				I_M_Product.COLUMNNAME_C_UOM_ID,
				"Recipient_No",
				"Recipient_Name",
				I_M_InOut.COLUMNNAME_BPartnerAddress,
				"Sales_Order_No",
				"Invoice_No",
				"Shipper",
				"Delivery_Date",
				"Receipt_Date",
				"Current_Stock",
				"TraceId",
				"ReportDate"
		);
	}
}
