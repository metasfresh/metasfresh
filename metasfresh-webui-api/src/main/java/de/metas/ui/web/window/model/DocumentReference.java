package de.metas.ui.web.window.model;

import org.adempiere.model.ZoomInfoFactory.ZoomInfo;

import com.google.common.base.MoreObjects;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.ui.web.window.model.filters.DocumentFilter;
import groovy.transform.Immutable;

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
	/* package */static DocumentReference of(final ZoomInfo zoomInfo)
	{
		return new DocumentReference(zoomInfo);
	}

	private final String id;
	private final ITranslatableString caption;
	private final int AD_Window_ID;
	private final int documentsCount;
	private final DocumentFilter filter;

	private DocumentReference(final ZoomInfo zoomInfo)
	{
		super();

		id = zoomInfo.getId();
		caption = ImmutableTranslatableString.constant(zoomInfo.getLabel());
		AD_Window_ID = zoomInfo.getAD_Window_ID();
		documentsCount = zoomInfo.getRecordCount();
		filter = DocumentFilter.of(zoomInfo.getQuery());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("caption", caption)
				.add("AD_Window_ID", AD_Window_ID)
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

	public int getAD_Window_ID()
	{
		return AD_Window_ID;
	}

	public int getDocumentsCount()
	{
		return documentsCount;
	}

	public DocumentFilter getFilter()
	{
		return filter;
	}
}
