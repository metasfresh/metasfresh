package de.metas.ui.web.window.model;

import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.window.datatypes.WindowId;
import groovy.transform.Immutable;
import lombok.Builder;

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

@Immutable
public final class DocumentReference
{
	private final String id;
	private final ITranslatableString caption;
	private final WindowId windowId;
	private final int documentsCount;
	private final DocumentFilter filter;
	
	@Builder
	private DocumentReference(final String id, final ITranslatableString caption, final WindowId windowId, final int documentsCount, final DocumentFilter filter)
	{
		this.id = id;
		this.caption = caption;
		this.windowId = windowId;
		this.documentsCount = documentsCount;
		this.filter = filter;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("caption", caption)
				.add("windowId", windowId)
				.add("documentsCount", documentsCount)
				.add("filter", filter)
				.toString();
	}
	
	public String getId()
	{
		return id;
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public WindowId getWindowId()
	{
		return windowId;
	}

	public int getDocumentsCount()
	{
		return documentsCount;
	}

	@NotNull
	public DocumentFilter getFilter()
	{
		return filter;
	}
}
