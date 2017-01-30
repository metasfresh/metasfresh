package de.metas.ui.web.window.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.process.json.JSONDocumentActionsList;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayout;
import de.metas.ui.web.window.datatypes.json.JSONDocumentReferencesList;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.descriptor.DetailId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

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

@Api
@RestController
@RequestMapping(value = WindowRestController.ENDPOINT)
@Deprecated
public class WindowRestControllerDeprecated
{
	private static final String PARAM_WindowId = WebConfig.PARAM_WindowId;
	private static final String PARAM_DocumentId = WebConfig.PARAM_DocumentId;
	private static final String PARAM_TabId = WebConfig.PARAM_TabId;
	private static final String PARAM_RowId = WebConfig.PARAM_RowId;
	private static final String PARAM_Field = "field";
	private static final String PARAM_FieldsList = "fields";
	private static final String PARAM_Advanced = "advanced";
	private static final String PARAM_Advanced_DefaultValue = "false";
	
	@Autowired
	private UserSession userSession;

	@Autowired
	private WindowRestController windowRestController;

	@RequestMapping(value = "/layout", method = RequestMethod.GET)
	public JSONDocumentLayout getLayout_DEPRECATED(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailIdStr //
			, @RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();

		final DetailId detailId = DetailId.fromJson(detailIdStr);
		if (detailId == null)
		{
			return windowRestController.getLayout(adWindowId, advanced);
		}
		else
		{
			return windowRestController.getLayout(adWindowId, detailIdStr, advanced);
		}
	}

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	@Deprecated
	public List<JSONDocument> getData_DEPRECATED(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String idStr //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailIdStr //
			, @RequestParam(name = PARAM_RowId, required = false) final String rowIdStr //
			, @RequestParam(name = PARAM_FieldsList, required = false) @ApiParam("comma separated field names") final String fieldsListStr //
			, @RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();

		final DocumentPath documentPath = DocumentPath.builder()
				.setAD_Window_ID(adWindowId)
				.setDocumentId(idStr)
				.setDetailId(detailIdStr)
				.setRowId(rowIdStr)
				.allowNullRowId()
				.build();

		return windowRestController.getData(documentPath, fieldsListStr, advanced);
	}

	@RequestMapping(value = "/commit", method = RequestMethod.PATCH)
	@Deprecated
	public List<JSONDocument> patchDocument_DEPRECATED(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String idStr //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailIdStr //
			, @RequestParam(name = PARAM_RowId, required = false) final String rowIdStr //
			, @RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced //
			, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertDeprecatedRestAPIAllowed();

		final DocumentPath documentPath = DocumentPath.builder()
				.setAD_Window_ID(adWindowId)
				.setDocumentId(idStr)
				.allowNewDocumentId()
				.setDetailId(detailIdStr)
				.setRowId(rowIdStr)
				.allowNewRowId()
				.build();

		return windowRestController.patchDocument(documentPath, advanced, events);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@Deprecated
	public List<JSONDocument> deleteDocuments_DEPRECATED(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String idStr //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailIdStr //
			, @RequestParam(name = PARAM_RowId, required = false) @ApiParam("comma separated rowIds") final String rowIdsListStr //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();

		final DocumentPath documentPath = DocumentPath.builder()
				.setAD_Window_ID(adWindowId)
				.setDocumentId(idStr)
				.setDetailId(detailIdStr)
				.setRowIdsList(rowIdsListStr)
				.build();
		return windowRestController.deleteDocuments(ImmutableList.of(documentPath));
	}

	@RequestMapping(value = "/typeahead", method = RequestMethod.GET)
	@Deprecated
	public JSONLookupValuesList getDocumentFieldTypeahead_DEPRECATED(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String documentId //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailId //
			, @RequestParam(name = PARAM_RowId, required = false) final String rowIdStr //
			, @RequestParam(name = PARAM_Field, required = true) final String fieldName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();

		final DocumentPath documentPath = DocumentPath.singleWindowDocumentPath(adWindowId, documentId, detailId, rowIdStr);
		return windowRestController.getDocumentFieldTypeahead(documentPath, fieldName, query);
	}

	@RequestMapping(value = "/dropdown", method = RequestMethod.GET)
	@Deprecated
	public JSONLookupValuesList getDocumentFieldDropdown_DEPRECATED(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true, defaultValue = DocumentId.NEW_ID_STRING) final String documentId //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailId //
			, @RequestParam(name = PARAM_RowId, required = false) final String rowIdStr //
			, @RequestParam(name = PARAM_Field, required = true) final String fieldName //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();

		final DocumentPath documentPath = DocumentPath.singleWindowDocumentPath(adWindowId, documentId, detailId, rowIdStr);
		return windowRestController.getDocumentFieldDropdown(documentPath, fieldName);
	}

	@RequestMapping(value = "/documentActions", method = RequestMethod.GET)
	@Deprecated
	public JSONDocumentActionsList getDocumentActions_DEPRECATED(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String idStr //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailId //
			, @RequestParam(name = PARAM_RowId, required = false) final String rowIdStr //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		final DocumentPath documentPath = DocumentPath.singleWindowDocumentPath(adWindowId, idStr, detailId, rowIdStr);
		return windowRestController.getDocumentActions(documentPath);
	}

	@RequestMapping(value = "/documentReferences", method = RequestMethod.GET)
	@Deprecated
	public JSONDocumentReferencesList getDocumentReferences_DEPRECATED(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String idStr //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailId //
			, @RequestParam(name = PARAM_RowId, required = false) final String rowIdStr //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		final DocumentPath documentPath = DocumentPath.singleWindowDocumentPath(adWindowId, idStr, detailId, rowIdStr);
		return windowRestController.getDocumentReferences(documentPath);
	}

	@RequestMapping(value = "/documentPrint", method = RequestMethod.GET)
	@Deprecated
	public ResponseEntity<byte[]> getDocumentPrint_DEPRECATED(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String idStr //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return windowRestController.getDocumentPrint(adWindowId, idStr, "report.pdf");
	}

}
