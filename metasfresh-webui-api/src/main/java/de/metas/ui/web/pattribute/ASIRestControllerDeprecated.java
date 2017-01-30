package de.metas.ui.web.pattribute;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.pattribute.json.JSONASILayout;
import de.metas.ui.web.pattribute.json.JSONCreateASIRequest;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
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
@Deprecated
public class ASIRestControllerDeprecated
{
	@Autowired
	private UserSession userSession;

	@Autowired
	private ASIRestController asiRestController;

	@RequestMapping(value = "/instance", method = RequestMethod.POST)
	@Deprecated
	public JSONDocument createASIDocument_DEPRECATED(@RequestBody final JSONCreateASIRequest request)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return asiRestController.createASIDocument(request);
	}

	@RequestMapping(value = "/instance/{asiDocId}/layout", method = RequestMethod.GET)
	@Deprecated
	public JSONASILayout getLayout_DEPRECATED(@PathVariable("asiDocId") final int asiDocId)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return asiRestController.getLayout(asiDocId);
	}

	@RequestMapping(value = "/instance/{asiDocId}", method = RequestMethod.GET)
	@Deprecated
	public JSONDocument getASIDocument_DEPRECATED(@PathVariable("asiDocId") final int asiDocId)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return asiRestController.getASIDocument(asiDocId);
	}

	@RequestMapping(value = "/instance/{asiDocId}", method = RequestMethod.PATCH)
	@Deprecated
	public List<JSONDocument> processChanges_DEPRECATED(
			@PathVariable("asiDocId") final int asiDocId //
			, @RequestBody final List<JSONDocumentChangedEvent> events //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return asiRestController.processChanges(asiDocId, events);
	}

	@RequestMapping(value = "/instance/{asiDocId}/attribute/{attributeName}/typeahead", method = RequestMethod.GET)
	@Deprecated
	public JSONLookupValuesList getAttributeTypeahead_DEPRECATED(
			@PathVariable("asiDocId") final int asiDocId //
			, @PathVariable("attributeName") final String attributeName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return asiRestController.getAttributeTypeahead(asiDocId, attributeName, query);
	}

	@RequestMapping(value = "/instance/{asiDocId}/attribute/{attributeName}/dropdown", method = RequestMethod.GET)
	@Deprecated
	public JSONLookupValuesList getAttributeDropdown_DEPRECATED(
			@PathVariable("asiDocId") final int asiDocId //
			, @PathVariable("attributeName") final String attributeName //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return asiRestController.getAttributeDropdown(asiDocId, attributeName);
	}

	@RequestMapping(value = "/instance/{asiDocId}/complete", method = RequestMethod.GET)
	@Deprecated
	public JSONLookupValue complete_DEPRECATED(@PathVariable("asiDocId") final int asiDocId)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return asiRestController.complete(asiDocId);
	}
	
	@RequestMapping(value = "/{asiDocId}/complete", method = RequestMethod.GET)
	@Deprecated
	public JSONLookupValue complete_DEPRECATED_2(@PathVariable("asiDocId") final int asiDocId)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return asiRestController.complete(asiDocId);
	}

}
