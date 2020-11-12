/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.print;

import de.metas.process.AdProcessId;
import de.metas.report.DocumentReportRequest;
import de.metas.report.DocumentReportService;
import de.metas.report.ReportResultData;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

@Service
public class WebuiDocumentPrintService
{
	private final DocumentCollection documentCollection;
	private final DocumentReportService documentReportService;

	public WebuiDocumentPrintService(
			@NonNull final DocumentCollection documentCollection,
			@NonNull final DocumentReportService documentReportService)
	{
		this.documentCollection = documentCollection;
		this.documentReportService = documentReportService;
	}

	public ReportResultData createDocumentPrint(@NonNull final WebuiDocumentPrintRequest request)
	{
		final DocumentPath documentPath = request.getDocumentPath();
		final Document document = documentCollection.getDocumentReadonly(documentPath);
		final int windowNo = document.getWindowNo();
		final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();

		final AdProcessId printProcessId = entityDescriptor.getPrintProcessId();
		final TableRecordReference recordRef = documentCollection.getTableRecordReference(documentPath);

		return documentReportService.createReport(DocumentReportRequest.builder()
				.callingFromProcessId(printProcessId)
				.documentRef(recordRef)
				.clientId(document.getClientId())
				.orgId(document.getOrgId())
				.userId(request.getUserId())
				.roleId(request.getRoleId())
				.windowNo(windowNo) // important: required for ProcessInfo.findReportingLanguage
				.printPreview(true)
				//.setJRDesiredOutputType(OutputType.PDF)
				.build());
	}

}
