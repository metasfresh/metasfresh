package de.metas.ui.web.window.datatypes.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.Singular;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class JSONDocumentReferencesGroup
{
	@JsonProperty("caption")
	private final String caption;

	@JsonProperty("references")
	private final List<JSONDocumentReference> references;

	@JsonIgnore
	private boolean miscGroup;

	@JsonCreator
	@Builder
	private JSONDocumentReferencesGroup( //
			@JsonProperty("caption") final String caption //
			, final boolean isMiscGroup //
			, @JsonProperty("references") @Singular final List<JSONDocumentReference> references //
	)
	{
		this.caption = caption;
		this.miscGroup = isMiscGroup;
		this.references = references == null || references.isEmpty() ? ImmutableList.of() : ImmutableList.copyOf(references);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("caption", caption)
				.add("references", references)
				.toString();
	}
	
	public String getCaption()
	{
		return caption;
	}

	public List<JSONDocumentReference> getReferences()
	{
		return references;
	}
	
	public boolean isEmpty()
	{
		return references.isEmpty();
	}
	
	public boolean isMiscGroup()
	{
		return miscGroup;
	}
}
