package de.metas.ui.web.window.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.NonNull;
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

/**
 * Mutable ordered documents list.
 * 
 * It also contains {@link #getOrderBys()}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString
public final class OrderedDocumentsList
{
	public static OrderedDocumentsList of(final Collection<Document> documents, final DocumentQueryOrderByList orderBys)
	{
		return new OrderedDocumentsList(documents, orderBys);
	}

	public static OrderedDocumentsList newEmpty()
	{
		return new OrderedDocumentsList(ImmutableList.of(), DocumentQueryOrderByList.EMPTY);
	}

	public static OrderedDocumentsList newEmpty(final DocumentQueryOrderByList orderBys)
	{
		return new OrderedDocumentsList(ImmutableList.of(), orderBys);
	}

	private final ArrayList<Document> documents;
	private final DocumentQueryOrderByList orderBys;

	private OrderedDocumentsList(
			@Nullable final Collection<Document> documents,
			@NonNull final DocumentQueryOrderByList orderBys)
	{
		this.documents = documents == null ? new ArrayList<>() : new ArrayList<>(documents);
		this.orderBys = orderBys;
	}

	public ArrayList<Document> toList()
	{
		return documents;
	}

	public ImmutableMap<DocumentId, Document> toImmutableMap()
	{
		return Maps.uniqueIndex(documents, Document::getDocumentId);
	}

	public void addDocument(@NonNull final Document document)
	{
		documents.add(document);
	}

	public void addDocuments(@NonNull final Collection<Document> documents)
	{
		if (documents.isEmpty())
		{
			return;
		}

		documents.forEach(this::addDocument);
	}

	public int size()
	{
		return documents.size();
	}

	public boolean isEmpty()
	{
		return documents.isEmpty();
	}

	public Document get(final int index)
	{
		return documents.get(index);
	}

	public DocumentQueryOrderByList getOrderBys()
	{
		return orderBys;
	}
}
