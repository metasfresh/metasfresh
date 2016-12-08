package de.metas.ui.web.pattribute;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.pattribute.json.JSONASILayout;
import de.metas.ui.web.pattribute.json.JSONCreateASIRequest;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.Document;
import io.swagger.annotations.Api;

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
@RequestMapping(value = ASIRestController.ENDPOINT)
public class ASIRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/pattribute";

	@Autowired
	private UserSession userSession;

	@Autowired
	private ASIRepository asiRepo;

	private JSONOptions newJsonOpts()
	{
		return JSONOptions.builder()
				.setUserSession(userSession)
				.build();
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public JSONDocument createASIDocument(@RequestBody final JSONCreateASIRequest request)
	{
		userSession.assertLoggedIn();

		return Execution.callInNewExecution("createASI", () -> {
			final Document asiDoc = asiRepo.createNewFrom(request);
			return JSONDocument.ofDocument(asiDoc, newJsonOpts());
		});
	}

	@RequestMapping(value = "/{asiDocId}/layout", method = RequestMethod.GET)
	public JSONASILayout getLayout(@PathVariable("asiDocId") final int asiDocId)
	{
		userSession.assertLoggedIn();

		final ASILayout asiLayout = asiRepo.getLayout(asiDocId);
		return JSONASILayout.of(asiLayout, newJsonOpts());
	}

	@RequestMapping(value = "/{asiDocId}", method = RequestMethod.GET)
	public JSONDocument getASIDocument(@PathVariable("asiDocId") final int asiDocId)
	{
		userSession.assertLoggedIn();

		final Document asiDoc = asiRepo.getASIDocument(asiDocId);
		return JSONDocument.ofDocument(asiDoc, newJsonOpts());
	}

	@RequestMapping(value = "/{asiDocId}", method = RequestMethod.PATCH)
	public List<JSONDocument> processChanges(
			@PathVariable("asiDocId") final int asiDocId //
			, @RequestBody final List<JSONDocumentChangedEvent> events //
	)
	{
		userSession.assertLoggedIn();

		return Execution.callInNewExecution("processChanges", () -> {
			asiRepo.processASIDocumentChanges(asiDocId, events);
			return JSONDocument.ofEvents(Execution.getCurrentDocumentChangesCollector(), newJsonOpts());
		});
	}

	@RequestMapping(value = "/{asiDocId}/attribute/{attributeName}/typeahead", method = RequestMethod.GET)
	public JSONLookupValuesList getAttributeTypeahead(
			@PathVariable("asiDocId") final int asiDocId //
			, @PathVariable("attributeName") final String attributeName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		userSession.assertLoggedIn();

		return asiRepo.getASIDocument(asiDocId)
				.getFieldLookupValuesForQuery(attributeName, query)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@RequestMapping(value = "/{asiDocId}/attribute/{attributeName}/dropdown", method = RequestMethod.GET)
	public JSONLookupValuesList getAttributeDropdown(
			@PathVariable("asiDocId") final int asiDocId //
			, @PathVariable("attributeName") final String attributeName //
	)
	{
		userSession.assertLoggedIn();

		return asiRepo.getASIDocument(asiDocId)
				.getFieldLookupValues(attributeName)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@RequestMapping(value = "/{asiDocId}/complete", method = RequestMethod.GET)
	public JSONLookupValue complete(@PathVariable("asiDocId") final int asiDocId)
	{
		userSession.assertLoggedIn();

		return Execution.callInNewExecution("complete", () -> asiRepo
				.complete(asiDocId)
				.transform(JSONLookupValue::ofLookupValue));
	}
}
