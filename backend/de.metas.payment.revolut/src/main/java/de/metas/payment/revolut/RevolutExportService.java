/*
 * #%L
 * de.metas.payment.revolut
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.payment.revolut;

import de.metas.banking.PaySelectionId;
import de.metas.location.ICountryDAO;
import de.metas.payment.revolut.model.RevolutExportCsvRow;
import de.metas.payment.revolut.model.RevolutPaymentExport;
import de.metas.report.ReportResultData;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class RevolutExportService
{
	private static final String EXPORT_FILE_NAME_TEMPLATE = "Revolut_Export_:C_PaySelection_ID:_:timestamp.csv";
	private static final String EXPORT_ID_PLACEHOLDER = ":C_PaySelection_ID:";
	private static final String TIMESTAMP_PLACEHOLDER = ":timestamp";
	private static final String CSV_FORMAT = "text/csv";

	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

	private final RevolutExportRepo revolutExportRepo;

	public RevolutExportService(@NonNull final RevolutExportRepo revolutExportRepo)
	{
		this.revolutExportRepo = revolutExportRepo;
	}

	@NonNull
	public List<RevolutPaymentExport> saveAll(@NonNull final List<RevolutPaymentExport> request)
	{
		return revolutExportRepo.saveAll(request);
	}

	@NonNull
	public ReportResultData exportToCsv(
			@NonNull final PaySelectionId paySelectionId,
			@NonNull final List<RevolutPaymentExport> exportList)
	{
		final StringBuilder csvBuilder = new StringBuilder(RevolutExportCsvRow.getCSVHeader());

		exportList.stream()
				.map(RevolutExportCsvRow::new)
				.map(row -> row.toCSVRow(countryDAO))
				.forEach(row -> csvBuilder.append("\n").append(row));

		return buildResult(csvBuilder.toString(), paySelectionId);
	}

	@NonNull
	private ReportResultData buildResult(
			@NonNull final String fileData,
			@NonNull final PaySelectionId paySelectionId)
	{
		final byte[] fileDataBytes = fileData.getBytes(StandardCharsets.UTF_8);

		return ReportResultData.builder()
				.reportData(new ByteArrayResource(fileDataBytes))
				.reportFilename(EXPORT_FILE_NAME_TEMPLATE
										.replace(EXPORT_ID_PLACEHOLDER, String.valueOf(paySelectionId.getRepoId()))
										.replace(TIMESTAMP_PLACEHOLDER, String.valueOf(System.currentTimeMillis())))
				.reportContentType(CSV_FORMAT)
				.build();
	}
}
