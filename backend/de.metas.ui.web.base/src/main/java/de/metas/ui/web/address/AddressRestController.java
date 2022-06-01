package de.metas.ui.web.address;

import de.metas.location.LocationId;
import de.metas.ui.web.address.json.JSONAddressDocument;
import de.metas.ui.web.address.json.JSONAddressLayout;
import de.metas.ui.web.address.json.JSONCreateAddressRequest;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.pattribute.json.JSONCompleteASIRequest;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.datatypes.json.JSONDocumentOptions;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesPage;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import io.swagger.annotations.Api;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @implNote IMPORTANT: Keep the API endpoints/requests/responses in sync with {@link de.metas.ui.web.pattribute.ASIRestController} because on frontend side they are handled by the same code.
 */
@Api
@RestController
@RequestMapping(AddressRestController.ENDPOINT)
public class AddressRestController
{
	static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/address";

	private final UserSession userSession;
	private final AddressRepository addressRepo;

	public AddressRestController(
			@NonNull final UserSession userSession,
			@NonNull final AddressRepository addressRepo)
	{
		this.userSession = userSession;
		this.addressRepo = addressRepo;
	}

	private JSONDocumentOptions newJsonDocumentOpts()
	{
		return JSONDocumentOptions.of(userSession);
	}

	private JSONDocumentLayoutOptions newJsonDocumentLayoutOpts()
	{
		return JSONDocumentLayoutOptions.of(userSession);
	}

	@PostMapping({ "", "/" })
	public JSONAddressDocument createAddressDocument(@RequestBody final JSONCreateAddressRequest request)
	{
		userSession.assertLoggedIn();

		return Execution.callInNewExecution("createAddressDocument", () -> {
			final Document addressDoc = addressRepo.createNewFrom(LocationId.ofRepoIdOrNull(request.getTemplateId()));
			return toJson(addressDoc);
		});
	}

	private JSONAddressDocument toJson(final Document addressDoc)
	{
		return JSONAddressDocument.builder()
				.id(addressDoc.getDocumentId())
				.layout(JSONAddressLayout.of(addressRepo.getLayout(), newJsonDocumentLayoutOpts()))
				.fieldsByName(JSONDocument.ofDocument(addressDoc, newJsonDocumentOpts()).getFieldsByName())
				.build();
	}

	@GetMapping("/{docId}")
	public JSONAddressDocument getAddressDocument(@PathVariable("docId") final int docId)
	{
		userSession.assertLoggedIn();

		final Document addressDoc = addressRepo.getAddressDocumentForReading(docId);
		return toJson(addressDoc);
	}

	@PatchMapping("/{docId}")
	public List<JSONDocument> processChanges(
			@PathVariable("docId") final int docId,
			@RequestBody final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertLoggedIn();

		return Execution.callInNewExecution("processChanges", () -> {
			final IDocumentChangesCollector changesCollector = Execution.getCurrentDocumentChangesCollectorOrNull();
			addressRepo.processAddressDocumentChanges(docId, events, changesCollector);
			return JSONDocument.ofEvents(changesCollector, newJsonDocumentOpts());
		});
	}

	@GetMapping("/{docId}/field/{attributeName}/typeahead")
	public JSONLookupValuesPage getAttributeTypeahead(
			@PathVariable("docId") final int docId,
			@PathVariable("attributeName") final String attributeName,
			@RequestParam(name = "query") final String query)
	{
		userSession.assertLoggedIn();

		return addressRepo.getAddressDocumentForReading(docId)
				.getFieldLookupValuesForQuery(attributeName, query)
				.transform(page -> JSONLookupValuesPage.of(page, userSession.getAD_Language()));
	}

	@GetMapping("/{docId}/field/{attributeName}/dropdown")
	public JSONLookupValuesList getAttributeDropdown(
			@PathVariable("docId") final int docId,
			@PathVariable("attributeName") final String attributeName)
	{
		userSession.assertLoggedIn();

		return addressRepo.getAddressDocumentForReading(docId)
				.getFieldLookupValues(attributeName)
				.transform(list -> JSONLookupValuesList.ofLookupValuesList(list, userSession.getAD_Language()));
	}

	@PostMapping("/{docId}/complete")
	public JSONLookupValue complete(
			@PathVariable("docId") final int docId,
			@RequestBody final JSONCompleteASIRequest request)
	{
		userSession.assertLoggedIn();

		return Execution.callInNewExecution(
				"complete",
				() -> addressRepo.complete(docId, request.getEvents())
						.transform(lookupValue -> JSONLookupValue.ofLookupValue(lookupValue, userSession.getAD_Language())));
	}
}
