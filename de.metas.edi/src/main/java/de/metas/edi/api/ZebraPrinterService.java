/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.api;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.esb.edi.model.I_EDI_DesadvLine_Pack;
import de.metas.process.PInstanceId;
import de.metas.report.ReportResultData;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.metas.edi.api.ZebraPrinterSysConfigType.*;

@Service
public class ZebraPrinterService
{
	private static final String ZEBRA_PRINTER_SYS_CONFIG_PREFIX = "de.metas.handlingunit.sscc18Label.zebra";
	private static final String SSCC18_FILE_NAME_TEMPLATE = "sscc_lables_:timestamp.csv";
	private static final String TIMESTAMP_PLACEHOLDER = ":timestamp";
	private static final String CSV_FORMAT = "text/csv";

	/**
	 * Creates a CSV file for SSCC18 labels based on given {@link I_EDI_DesadvLine_Pack} IDs.
	 *
	 * @return {@link ReportResultData} containing information required for SSCC18 labels in CSV format.
	 */
	public ReportResultData createCSV_FileForSSCC18_Labels( final Collection<Integer> desadvLinePack_IDs_ToPrint,
															final int clientId,
															final int adOrgId,
															final PInstanceId pInstanceId )
	{
		final Map<String, String> zebraConfigsByName = retrieveZebraPrinterConfigs(clientId, adOrgId);

		DB.createT_Selection(pInstanceId, desadvLinePack_IDs_ToPrint, ITrx.TRXNAME_ThreadInherited);

		final ImmutableList<List<String>> resultRows = DB.getSQL_ResultRowsAsListsOfStrings(zebraConfigsByName.get(SQL_SELECT.getSysConfigName()),
				Collections.singletonList(pInstanceId), ITrx.TRXNAME_ThreadInherited);

		Check.assumeNotEmpty(resultRows, "SSCC information records must be available!");


		final StringBuilder ssccLabelsInformationAsCSV = new StringBuilder(zebraConfigsByName.get(HEADER_LINE_1.getSysConfigName()));
		ssccLabelsInformationAsCSV.append("\n").append(zebraConfigsByName.get(HEADER_LINE_2.getSysConfigName()));

		final Joiner joiner = Joiner.on(",");

		resultRows.stream()
				.map(row -> row.stream().map(this::escapeCSV).collect(Collectors.toList()))
				.map(joiner::join)
				.forEach(row -> ssccLabelsInformationAsCSV.append("\n").append(row));

		return buildResult(ssccLabelsInformationAsCSV.toString(), zebraConfigsByName.get(FILE_ENCODING.getSysConfigName()));
	}

	private Map<String, String> retrieveZebraPrinterConfigs(final int clientId, final int adOrgId)
	{
		//TODO: when moving Zebra configs to a dedicated table make sure to follow a DDD design.
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final Map<String, String> zebraConfigsByName = sysConfigBL.getValuesForPrefix(ZEBRA_PRINTER_SYS_CONFIG_PREFIX, clientId, adOrgId);

		ZebraPrinterSysConfigType.listMandatoryConfigs()
				.forEach(configType -> Check.assumeNotEmpty(zebraConfigsByName.get(configType.getSysConfigName()), "ZebraSysConfig {} must be present!", configType.getSysConfigName()));

		return zebraConfigsByName;
	}

	private String escapeCSV(final String valueToEscape)
	{
		final String escapedQuote = "\"";
		return escapedQuote
				+ StringUtils.nullToEmpty(valueToEscape).replace(escapedQuote, escapedQuote + escapedQuote)
				+ escapedQuote;
	}

	private ReportResultData buildResult(final String fileData, final String fileEncoding)
	{
		final byte[] fileDataBytes = fileData.getBytes(Charset.forName(fileEncoding));

		return ReportResultData.builder()
				.reportData(fileDataBytes)
				.reportFilename(SSCC18_FILE_NAME_TEMPLATE.replace(TIMESTAMP_PLACEHOLDER, String.valueOf(System.currentTimeMillis())))
				.reportContentType(CSV_FORMAT)
				.build();
	}
}
