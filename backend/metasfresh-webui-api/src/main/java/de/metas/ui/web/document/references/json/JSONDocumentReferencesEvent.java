package de.metas.ui.web.document.references.json;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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
@Value
public class JSONDocumentReferencesEvent
{
	public static final JSONDocumentReferencesEvent COMPLETED = new JSONDocumentReferencesEvent(Type.COMPLETED, null);

	public static JSONDocumentReferencesEvent partialResult(@NonNull final JSONDocumentReferencesGroup partialGroup)
	{
		return new JSONDocumentReferencesEvent(Type.PARTIAL_RESULT, partialGroup);
	}

	private enum Type
	{
		PARTIAL_RESULT, COMPLETED,
	}

	private final Type type;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONDocumentReferencesGroup partialGroup;

	private JSONDocumentReferencesEvent(
			@NonNull final Type type,
			@Nullable final JSONDocumentReferencesGroup partialGroup)
	{
		this.type = type;
		this.partialGroup = partialGroup;
	}
}
