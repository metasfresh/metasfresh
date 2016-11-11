package de.metas.ui.web.process.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.process.descriptor.ProcessLayout;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutElement;
import de.metas.ui.web.window.datatypes.json.JSONOptions;

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

@SuppressWarnings("serial")
public class JSONProcessLayout implements Serializable
{
	public static JSONProcessLayout of(final ProcessLayout layout, final JSONOptions jsonOpts)
	{
		return new JSONProcessLayout(layout, jsonOpts);
	}

	@JsonProperty("caption")
	private final String caption;
	@JsonProperty("description")
	private final String description;
	@JsonProperty("elements")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentLayoutElement> elements;

	public JSONProcessLayout(final ProcessLayout layout, final JSONOptions jsonOpts)
	{
		final String adLanguage = jsonOpts.getAD_Language();
		caption = layout.getCaption(adLanguage);
		description = layout.getDescription(adLanguage);

		elements = JSONDocumentLayoutElement.ofList(layout.getElements(), jsonOpts);
	}

}
