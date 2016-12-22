package de.metas.ui.web.window.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.descriptor.DocumentViewAttributesLayout;
import de.metas.ui.web.view.json.JSONDocumentViewAttributesLayout;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentViewsRepository;

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

@RestController
@RequestMapping(value = DocumentViewAttributesRestController.ENDPOINT)
public class DocumentViewAttributesRestController
{
	private static final String PARAM_ViewId = "viewId";
	private static final String PARAM_DocumentId = "documentId";

	/* package */static final String ENDPOINT = DocumentViewRestController.ENDPOINT + "/{" + PARAM_ViewId + "}/{" + PARAM_DocumentId + "}";

	@Autowired
	private UserSession userSession;

	@Autowired
	private DocumentViewsRepository documentViewsRepo;

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.builder()
				.setUserSession(userSession)
				.build();
	}

	@GetMapping("/layout")
	public JSONDocumentViewAttributesLayout getAttributesLayout(
			@PathVariable(PARAM_ViewId) final String viewId //
			, @PathVariable(PARAM_DocumentId) final int documentId //
	)
	{
		userSession.assertLoggedIn();

		final DocumentViewAttributesLayout layout = documentViewsRepo.getView(viewId)
				.getById(documentId)
				.getAttributes()
				.getLayout();

		return JSONDocumentViewAttributesLayout.of(layout, newJSONOptions());
	}

	@GetMapping
	public JSONDocument getData(
			@PathVariable(PARAM_ViewId) final String viewId //
			, @PathVariable(PARAM_DocumentId) final int documentId //
	)
	{
		userSession.assertLoggedIn();

		final IDocumentViewAttributes attributes = documentViewsRepo.getView(viewId)
				.getById(documentId)
				.getAttributes();

		return JSONDocument.ofMap(attributes.getDocumentPath(), attributes.getData());
	}

	@PatchMapping
	public List<JSONDocument> processChanges(
			@PathVariable(PARAM_ViewId) final String viewId //
			, @PathVariable(PARAM_DocumentId) final int documentId //
			, @RequestBody final List<JSONDocumentChangedEvent> events //
	)
	{
		userSession.assertLoggedIn();

		return Execution.callInNewExecution("processChanges", () -> {
			documentViewsRepo.getView(viewId)
					.getById(documentId)
					.getAttributes()
					.processChanges(events);

			return JSONDocument.ofEvents(Execution.getCurrentDocumentChangesCollector(), newJSONOptions());
		});
	}

	@GetMapping("/attribute/{attributeName}/typeahead")
	public JSONLookupValuesList getAttributeTypeahead(
			@PathVariable(PARAM_ViewId) final String viewId//
			, @PathVariable(PARAM_DocumentId) final int documentId //
			, @PathVariable("attributeName") final String attributeName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		userSession.assertLoggedIn();

		return documentViewsRepo.getView(viewId)
				.getById(documentId)
				.getAttributes()
				.getAttributeTypeahead(attributeName, query)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@GetMapping("/attribute/{attributeName}/dropdown")
	public JSONLookupValuesList getAttributeDropdown(
			@PathVariable(PARAM_ViewId) final String viewId//
			, @PathVariable(PARAM_DocumentId) final int documentId //
			, @PathVariable("attributeName") final String attributeName //
	)
	{
		userSession.assertLoggedIn();

		return documentViewsRepo.getView(viewId)
				.getById(documentId)
				.getAttributes()
				.getAttributeDropdown(attributeName)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}
}
