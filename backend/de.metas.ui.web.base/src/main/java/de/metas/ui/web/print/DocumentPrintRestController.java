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

import de.metas.report.DocumentPrintOptions;
import de.metas.report.DocumentReportFlavor;
import de.metas.report.ReportResultData;
import de.metas.ui.web.print.json.JSONDocumentPrintingOptions;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.controller.WindowRestController;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "DocumentPrintRestController")
@RestController
@RequestMapping(value = DocumentPrintRestController.ENDPOINT)
public class DocumentPrintRestController
{
	public static final String ENDPOINT = WindowRestController.ENDPOINT;

	private final UserSession userSession;
	private final WebuiDocumentPrintService documentPrintService;

	public DocumentPrintRestController(
			@NonNull final UserSession userSession,
			@NonNull final WebuiDocumentPrintService documentPrintService)
	{
		this.userSession = userSession;
		this.documentPrintService = documentPrintService;
	}

	@GetMapping("/{windowId}/{documentId}/print/{filename:.*}")
	public ResponseEntity<Resource> createDocumentPrint(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@PathVariable("filename") final String filename,
			@RequestParam final Map<String, String> requestParams)
	{
		userSession.assertLoggedIn();

		return documentPrintService.createDocumentPrint(WebuiDocumentPrintRequest.builder()
						.flavor(DocumentReportFlavor.EMAIL)
						.documentPath(DocumentPath.rootDocumentPath(WindowId.fromJson(windowIdStr), documentIdStr))
						.userId(userSession.getLoggedUserId())
						.roleId(userSession.getLoggedRoleId())
						.printOptions(DocumentPrintOptions.ofMap(requestParams, "user HTTP request"))
						.build())
				.filter(report -> !report.isEmpty())
				.map(documentPrint -> toResponseEntity(documentPrint, filename))
				.orElseGet(() -> ResponseEntity.ok().build());
	}

	@NonNull
	private static ResponseEntity<Resource> toResponseEntity(@NonNull final ReportResultData documentPrint, @NonNull final String filename)
	{
		final Resource reportData = documentPrint.getReportData();
		final String reportContentType = documentPrint.getReportContentType();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(reportContentType));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		return new ResponseEntity<>(reportData, headers, HttpStatus.OK);
	}

	@GetMapping("/{windowId}/{documentId}/printingOptions")
	public JSONDocumentPrintingOptions getPrintingOptions(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentIdStr);

		return documentPrintService.getPrintingOptions(documentPath, userSession.getAD_Language());
	}
}
