package de.metas.ui.web.window.datatypes.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.window.model.DocumentActionsList;

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

public class JSONDocumentActionsList
{
	public static JSONDocumentActionsList of(final DocumentActionsList documentActionsList, final JSONFilteringOptions jsonOpts)
	{
		return new JSONDocumentActionsList(documentActionsList, jsonOpts);
	}

	@JsonProperty("actions")
	private final List<JSONDocumentAction> actions;

	private JSONDocumentActionsList(final DocumentActionsList documentActionsList, final JSONFilteringOptions jsonOpts)
	{
		super();
		actions = JSONDocumentAction.ofList(documentActionsList.asList(), jsonOpts);
	}

	public List<JSONDocumentAction> getActions()
	{
		return actions;
	}
}
