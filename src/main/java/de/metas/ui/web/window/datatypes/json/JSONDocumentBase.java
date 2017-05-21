package de.metas.ui.web.window.datatypes.json;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Maps;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.exceptions.InvalidDocumentPathException;

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

/**
 * Base class for all "Document"-ish JSONs
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
// @JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE) // cannot use it because of "otherProperties"
public abstract class JSONDocumentBase
{

	@JsonProperty("id")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String id;

	@JsonProperty("tabId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String tabId;
	//
	@JsonProperty("tabid")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Deprecated
	private final String tabid;

	@JsonProperty("rowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String rowId;

	@JsonProperty("deleted")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean deleted;

	@JsonProperty("fields")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonSerialize(using = JsonMapAsValuesListSerializer.class)
	private Map<String, JSONDocumentField> fieldsByName;

	/** Any other properties */
	private final Map<String, Object> otherProperties = new LinkedHashMap<>();

	protected JSONDocumentBase(final DocumentPath documentPath)
	{
		if (documentPath == null)
		{
			id = null;
			tabId = null;
			rowId = null;
		}
		else if (documentPath.isRootDocument())
		{
			id = documentPath.getDocumentId().toJson();
			tabId = null;
			rowId = null;
		}
		else if (documentPath.isSingleIncludedDocument())
		{
			id = documentPath.getDocumentId().toJson();
			tabId = documentPath.getDetailId().toJson();
			rowId = documentPath.getSingleRowId().toJson();
		}
		else
		{
			// shall not happen
			throw new InvalidDocumentPathException(documentPath, "only root path and single included document path are allowed");
		}
		
		tabid = tabId;
	}
	
	protected JSONDocumentBase(final DocumentId documentId)
	{
		id = documentId.toJson();
		tabId = null;
		tabid = tabId;
		rowId = null;
	}


	public final void setDeleted()
	{
		deleted = Boolean.TRUE;
	}

	@JsonAnyGetter
	private final Map<String, Object> getOtherProperties()
	{
		return otherProperties;
	}

	@JsonAnySetter
	private final void putOtherProperty(final String name, final Object jsonValue)
	{
		otherProperties.put(name, jsonValue);
	}

	protected final JSONDocumentBase putDebugProperty(final String name, final Object jsonValue)
	{
		otherProperties.put("debug-" + name, jsonValue);
		return this;
	}

	public final void setFields(final Collection<JSONDocumentField> fields)
	{
		fieldsByName = fields == null ? null : Maps.uniqueIndex(fields, (field) -> field.getField());
	}

	@JsonIgnore
	protected final int getFieldsCount()
	{
		return fieldsByName == null ? 0 : fieldsByName.size();
	}
}
