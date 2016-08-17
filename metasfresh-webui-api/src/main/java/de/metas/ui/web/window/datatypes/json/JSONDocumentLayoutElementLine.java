package de.metas.ui.web.window.datatypes.json;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import de.metas.ui.web.window.descriptor.DocumentLayoutElementLineDescriptor;
import io.swagger.annotations.ApiModel;

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

@ApiModel("element-line")
@SuppressWarnings("serial")
public class JSONDocumentLayoutElementLine extends ArrayList<JSONDocumentLayoutElement>
{

	public static List<JSONDocumentLayoutElementLine> ofList(final List<DocumentLayoutElementLineDescriptor> elementLines)
	{
		return elementLines.stream()
				.map(elementLine -> of(elementLine))
				.collect(GuavaCollectors.toImmutableList());
	}

	public static JSONDocumentLayoutElementLine of(final DocumentLayoutElementLineDescriptor elementLine)
	{
		return new JSONDocumentLayoutElementLine(elementLine);
	}

	private JSONDocumentLayoutElementLine(final DocumentLayoutElementLineDescriptor elementLine)
	{
		super();

		final List<JSONDocumentLayoutElement> elements = JSONDocumentLayoutElement.ofList(elementLine.getElements());
		addAll(elements);
	}

}
