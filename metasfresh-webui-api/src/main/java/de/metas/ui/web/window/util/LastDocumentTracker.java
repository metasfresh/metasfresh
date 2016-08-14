package de.metas.ui.web.window.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.util.Check;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentId;

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

// FIXME this class was introduced for debugging... we shall get rid of it soon!
@Deprecated
@Component
// @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@SuppressWarnings("serial")
public class LastDocumentTracker implements Serializable
{
	private final Map<ArrayKey, DocumentId> lastDocumentIds = new HashMap<>();

	public LastDocumentTracker()
	{
		super();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(lastDocumentIds)
				.toString();
	}

	private static final ArrayKey mkKey(final int adWindowId, final String detailId)
	{
		return Util.mkKey(adWindowId, detailId);
	}

	public void add(final Document document)
	{
		Check.assumeNotNull(document, "Parameter document is not null");
		final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();
		final ArrayKey key = mkKey(entityDescriptor.getAD_Window_ID(), entityDescriptor.getDetailId());
		final DocumentId documentId = DocumentId.of(document.getDocumentId());
		lastDocumentIds.put(key, documentId);
	}

	public DocumentId getLastDocumentId(final int adWindowId, final String detailId, final DocumentId defaultValue)
	{
		final ArrayKey key = mkKey(adWindowId, detailId);
		return lastDocumentIds.getOrDefault(key, defaultValue);
	}

}
