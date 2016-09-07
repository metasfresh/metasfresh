package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.model.IDocumentViewSelection;

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
public final class JSONDocumentViewInfo implements Serializable
{
	public static final JSONDocumentViewInfo of(final IDocumentViewSelection view)
	{
		return new JSONDocumentViewInfo(view);
	}

	@JsonProperty("viewId")
	private final String viewId;
	@JsonProperty("size")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer size;

	public JSONDocumentViewInfo(final IDocumentViewSelection view)
	{
		super();
		viewId = view.getId();

		final int size = view.size();
		this.size = size >= 0 ? size : null;
	}

	public JSONDocumentViewInfo( //
			@JsonProperty("viewId") final String viewId //
			, @JsonProperty("size") final Integer size //
	)
	{
		super();
		this.viewId = viewId;
		this.size = size;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("viewId", viewId)
				.add("size", size)
				.toString();
	}

	public String getViewId()
	{
		return viewId;
	}

	public Integer getSize()
	{
		return size;
	}
}
