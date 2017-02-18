package de.metas.ui.web.process.json;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.datatypes.DocumentId;

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
public class JSONCreateProcessInstanceRequest implements Serializable
{
	@Deprecated
	public static JSONCreateProcessInstanceRequest of(final int adProcessId, final int adWindowId, final String idStr)
	{
		final String documentType = String.valueOf(adWindowId);
		final String viewId = null;
		final Set<String> viewDocumentIds = null;
		return new JSONCreateProcessInstanceRequest(adProcessId, documentType, idStr, viewId, viewDocumentIds);
	}

	@JsonProperty("processId")
	private final int processId;

	/** Document type (aka AD_Window_ID) */
	@JsonProperty("documentType")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String documentType;
	//
	@JsonProperty("documentId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String documentId;

	@JsonProperty("viewId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String viewId;
	//
	@JsonProperty("viewDocumentIds")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Set<String> viewDocumentIdsStrings;

	@JsonIgnore
	private transient Set<DocumentId> _viewDocumentIds;

	//
	// Calculated values
	private final transient Supplier<Integer> _adWindowId = Suppliers.memoize(() -> {
		final String documentType = getDocumentType();
		return Check.isEmpty(documentType, true) ? -1 : Integer.parseInt(documentType);
	});

	@JsonCreator
	private JSONCreateProcessInstanceRequest( //
			@JsonProperty("processId") final int processId //
			, @JsonProperty("documentType") final String documentType //
			, @JsonProperty("documentId") final String documentId //
			, @JsonProperty("viewId") final String viewId //
			, @JsonProperty("viewDocumentIds") final Set<String> viewDocumentIds //
	)
	{
		super();
		this.processId = processId;
		this.documentType = documentType;
		this.documentId = documentId;
		this.viewId = viewId;
		this.viewDocumentIdsStrings = viewDocumentIds == null ? null : ImmutableSet.copyOf(viewDocumentIds);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("processId", processId)
				.add("documentType", documentType)
				.add("documentId", documentId)
				.add("viewId", viewId)
				.add("viewDocumentIds", _viewDocumentIds != null ? _viewDocumentIds : viewDocumentIdsStrings)
				.toString();
	}

	public int getAD_Process_ID()
	{
		return processId;
	}

	public int getAD_Window_ID()
	{
		return _adWindowId.get();
	}

	public String getDocumentType()
	{
		return documentType;
	}

	public String getDocumentId()
	{
		return documentId;
	}

	public String getViewId()
	{
		return viewId;
	}

	public Set<DocumentId> getViewDocumentIds()
	{
		if(_viewDocumentIds == null)
		{
			_viewDocumentIds = DocumentId.ofStringSet(viewDocumentIdsStrings);
		}
		return _viewDocumentIds;
	}
}
