package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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

@ApiModel("document-change-event")
public class JSONDocumentChangedEvent
{
	@JsonCreator
	public static final JSONDocumentChangedEvent of(@JsonProperty("op") final JSONOperation operation, @JsonProperty("path") final String path, @JsonProperty("value") final Object value)
	{
		return new JSONDocumentChangedEvent(operation, path, value);
	}

	@ApiModel("operation")
	public static enum JSONOperation
	{
		replace;
	}

	@JsonProperty("op")
	private final JSONOperation operation;
	@JsonProperty("path")
	private final String path;
	@JsonProperty("value")
	private final Object value;

	private JSONDocumentChangedEvent(final JSONOperation operation, final String path, final Object value)
	{
		super();
		this.operation = operation;
		this.path = path;
		this.value = value;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("op", operation)
				.add("path", path)
				.add("value", value)
				.toString();
	}

	public JSONOperation getOperation()
	{
		return operation;
	}

	public String getPath()
	{
		return path;
	}

	public Object getValue()
	{
		return value;
	}
}
