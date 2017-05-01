package de.metas.ui.web.window.datatypes.json;

import java.util.Collection;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.model.DocumentReference;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONDocumentReferencesList
{
	public static JSONDocumentReferencesList of(final Collection<DocumentReference> documentReferences, final JSONOptions jsonOpts)
	{
		if (documentReferences.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableList<JSONDocumentReference> references = documentReferences.stream()
				.map(documentReference -> JSONDocumentReference.of(documentReference, jsonOpts))
				.filter(jsonDocumentReference -> jsonDocumentReference != null)
				.collect(GuavaCollectors.toImmutableList());
		if (references.isEmpty())
		{
			return EMPTY;
		}

		return new JSONDocumentReferencesList(references);
	}

	private static final JSONDocumentReferencesList EMPTY = new JSONDocumentReferencesList(ImmutableList.of());

	@JsonProperty("references")
	private final List<JSONDocumentReference> references;

	@JsonCreator
	private JSONDocumentReferencesList(@JsonProperty("references") final List<JSONDocumentReference> references)
	{
		super();
		this.references = references == null || references.isEmpty() ? ImmutableList.of() : ImmutableList.copyOf(references);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(references)
				.toString();
	}
}
