/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.document.archive.spi.impl;

import de.metas.process.PInstanceId;
import de.metas.report.DocTypePrintOptionsRepository;
import de.metas.report.DocumentPrintOptionDescriptorsRepository;
import de.metas.report.DocumentReportAdvisor;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.DocumentReportRequest;
import de.metas.report.DocumentReportService;
import de.metas.report.ReportResultData;
import lombok.NonNull;
import lombok.Setter;
import org.compiere.util.MimeType;

import java.util.List;

public class MockedDocumentReportService extends DocumentReportService
{
	@Setter
	private PInstanceId pinstanceIdToReturn = PInstanceId.ofRepoId(12345);

	public static final String MOCKED_REPORT_FILENAME = "report.pdf";

	public MockedDocumentReportService(
			final @NonNull List<DocumentReportAdvisor> advisors,
			final @NonNull DocumentPrintOptionDescriptorsRepository documentPrintOptionDescriptorsRepository,
			final @NonNull DocTypePrintOptionsRepository docTypePrintOptionsRepository,
			final @NonNull DocumentReportAdvisorUtil util)
	{
		super(
				advisors,
				documentPrintOptionDescriptorsRepository,
				docTypePrintOptionsRepository,
				util);
	}

	@Override
	protected ExecuteReportProcessResult executeReportProcess(final @NonNull DocumentReportRequest request)
	{
		return ExecuteReportProcessResult.builder()
				.reportData(ReportResultData.builder()
						.reportFilename(MOCKED_REPORT_FILENAME)
						.reportContentType(MimeType.TYPE_PDF)
						.reportData(new byte[] { 1, 2, 3 })
						.build())
				.reportPInstanceId(pinstanceIdToReturn)
				.build();
	}
}
