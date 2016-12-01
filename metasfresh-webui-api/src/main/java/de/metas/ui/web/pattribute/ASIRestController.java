package de.metas.ui.web.pattribute;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.pattribute.json.JSONCreateASIRequest;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
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
	ASIRepository productAttributesRepo;

	private static final ReasonSupplier REASON_Value_DirectSetFromCommitAPI = () -> "direct set from commit API";

	private JSONOptions newJsonOpts()
	{
		return JSONOptions.builder()
				.setUserSession(userSession)
				.build();
	}

	@RequestMapping(value = "/instance", method = RequestMethod.POST)
	public JSONDocument createASI(@RequestBody final JSONCreateASIRequest request)
	{
		userSession.assertLoggedIn();

		return Execution.callInNewExecution("createASI", () -> {
			if (request.getTemplateId() > 0)
			{
				final Document productAttributes = productAttributesRepo.createNewFrom(request.getTemplateId());
				return JSONDocument.ofDocument(productAttributes, newJsonOpts());
			}
			throw new AdempiereException("Invalid request: " + request);
		});
	}

	@RequestMapping(value = "/instance/{asiId}", method = RequestMethod.GET)
	public JSONDocument getASI(@PathVariable("asiId") final int asiId)
	{
		userSession.assertLoggedIn();
		final Document asiDoc = productAttributesRepo.getASIDocument(asiId);
		return JSONDocument.ofDocument(asiDoc, newJsonOpts());
	}

	@RequestMapping(value = "/instance/{asiId}", method = RequestMethod.PATCH)
	public List<JSONDocument> processChanges(
			@PathVariable("asiId") final int asiId //
			, @RequestBody final List<JSONDocumentChangedEvent> events //
	)
	{
		userSession.assertLoggedIn();

		return Execution.callInNewExecution("processChanges", () -> processChanges0(asiId, events));
	}

	private List<JSONDocument> processChanges0(final int asiId, final List<JSONDocumentChangedEvent> events)
	{
		final Document asiDoc = productAttributesRepo.getASIDocumentForWriting(asiId);
		asiDoc.processValueChanges(events, REASON_Value_DirectSetFromCommitAPI);
		
		productAttributesRepo.putASIDocument(asiDoc);

		//
		// Return the changes
		return JSONDocument.ofEvents(Execution.getCurrentDocumentChangesCollector(), newJsonOpts());
	}

	@RequestMapping(value = "/instance/{asiId}/attribute/{attributeName}/typeahead", method = RequestMethod.GET)
	public JSONLookupValuesList typeahead(
			@PathVariable("asiId") final int asiId //
			, @PathVariable("attributeName") final String attributeName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		userSession.assertLoggedIn();

		return productAttributesRepo.getASIDocument(asiId)
				.getFieldLookupValuesForQuery(attributeName, query)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@RequestMapping(value = "/instance/{asiId}/attribute/{attributeName}/dropdown", method = RequestMethod.GET)
	public JSONLookupValuesList dropdown(
			@PathVariable("asiId") final int asiId //
			, @PathVariable("attributeName") final String attributeName //
	)
	{
		userSession.assertLoggedIn();

		return productAttributesRepo.getASIDocument(asiId)
				.getFieldLookupValues(attributeName)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@RequestMapping(value = "/instance/{asiId}/complete", method = RequestMethod.GET)
	public JSONLookupValue complete(@PathVariable("asiId") final int asiId)
	{
		return Execution.callInNewExecution("complete", () -> {
			final Document asiDoc = productAttributesRepo.getASIDocumentForWriting(asiId);
			final LookupValue lookupValue = productAttributesRepo.complete(asiDoc);
			productAttributesRepo.removeASIDocument(asiDoc);
			return JSONLookupValue.ofLookupValue(lookupValue);
		});
	}
}
