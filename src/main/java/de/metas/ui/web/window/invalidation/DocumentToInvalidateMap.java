package de.metas.ui.web.window.invalidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString
final class DocumentToInvalidateMap
{
	private final Map<TableRecordReference, DocumentToInvalidate> documents = new HashMap<>();

	public DocumentToInvalidate getDocumentToInvalidate(final TableRecordReference rootDocumentRef)
	{
		return documents.computeIfAbsent(rootDocumentRef, DocumentToInvalidate::new);
	}

	public boolean isEmpty()
	{
		return documents.isEmpty();
	}

	public int size()
	{
		return documents.size();
	}

	public TableRecordReferenceSet getRootRecords()
	{
		return TableRecordReferenceSet.of(documents.keySet());
	}

	public Collection<DocumentToInvalidate> toCollection()
	{
		return documents.values();
	}
}
