package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.window.model.DocumentFieldWarning;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONDocumentFieldWarning
{
	public static final JSONDocumentFieldWarning ofNullable(final DocumentFieldWarning fieldWarning, final String adLanguage)
	{
		if (fieldWarning == null)
		{
			return null;
		}

		return new JSONDocumentFieldWarning(fieldWarning, adLanguage);
	}

	@JsonProperty("caption")
	private final String caption;
	@JsonProperty("message")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String message;
	@JsonProperty("error")
	private final boolean error;

	private JSONDocumentFieldWarning(final DocumentFieldWarning fieldWarning, final String adLanguage)
	{
		caption = fieldWarning.getCaption().translate(adLanguage);
		message = fieldWarning.getMessage();
		error = fieldWarning.isError();
	}
}
