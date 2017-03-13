package de.metas.ui.web.view.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.view.descriptor.DocumentViewAttributesLayout;
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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONDocumentViewAttributesLayout implements Serializable
{
	public static JSONDocumentViewAttributesLayout of(final DocumentViewAttributesLayout layout, final JSONOptions jsonOpts)
	{
		return new JSONDocumentViewAttributesLayout(layout, jsonOpts);
	}

	private final List<JSONDocumentLayoutElement> elements;

	private JSONDocumentViewAttributesLayout(final DocumentViewAttributesLayout layout, final JSONOptions jsonOpts)
	{
		super();
		elements = JSONDocumentLayoutElement.ofList(layout.getElements(), jsonOpts);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("elements", elements)
				.toString();
	}

	public List<JSONDocumentLayoutElement> getElements()
	{
		return elements;
	}
}
