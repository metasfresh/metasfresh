package de.metas.ui.web.pattribute.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.datatypes.json.JSONDocumentPath;

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
public final class JSONCreateASIRequest implements Serializable
{
	@JsonProperty("templateId")
	private final int templateId;

	//
	// Source
	@JsonProperty("source")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	private final JSONDocumentPath source;

	@JsonCreator
	private JSONCreateASIRequest(
			@JsonProperty("templateId") final int templateId //
			, @JsonProperty("source") final JSONDocumentPath source //
	)
	{
		super();
		this.templateId = templateId;
		this.source = source;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("templateId", templateId)
				.add("source", source)
				.toString();
	}

	public int getTemplateId()
	{
		return templateId;
	}

	public JSONDocumentPath getSource()
	{
		if(source == null)
		{
			new IllegalStateException("source is not set for " + this);
		}
		return source;
	}
}
