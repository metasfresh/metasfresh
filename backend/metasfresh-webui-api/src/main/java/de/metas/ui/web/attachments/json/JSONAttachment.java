package de.metas.ui.web.attachments.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.attachments.IDocumentAttachmentEntry;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

@SuppressWarnings("serial")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@ToString
public class JSONAttachment implements Serializable
{
	public static JSONAttachment of(IDocumentAttachmentEntry entry)
	{
		return new JSONAttachment(entry.getId(), entry.getFilename());
	}

	@JsonProperty("id")
	@Getter
	private final String id;

	@JsonProperty("name")
	@Getter
	private final String name;

	@JsonProperty("allowDelete")
	@Setter
	@Getter
	private boolean allowDelete = false;

	/* package */ JSONAttachment(@JsonProperty("id") final DocumentId id, @JsonProperty("name") final String name)
	{
		this.id = id.toJson();
		this.name = name;
	}
}
