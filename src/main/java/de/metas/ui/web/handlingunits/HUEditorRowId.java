package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Joiner;
import java.util.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

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

@Immutable
@EqualsAndHashCode
public final class HUEditorRowId
{
	public static HUEditorRowId ofTopLevelHU(@NonNull final HuId topLevelHUId)
	{
		return new HUEditorRowId(
				topLevelHUId,
				(ProductId)null, // storageProductId
				(HuId)null, // topLevelHUId parameter
				(String)null, // json, to be computed when needed
				(DocumentId)null  // to be computed when needed
		);
	}

	public static HUEditorRowId ofHU(final HuId huId, final HuId topLevelHUId)
	{
		final ProductId storageProductId = null;
		final String json = null; // to be computed when needed
		final DocumentId documentId = null; // to be computed when needed
		return new HUEditorRowId(huId, storageProductId, topLevelHUId, json, documentId);
	}

	public static HUEditorRowId ofHUStorage(final HuId huId, final HuId topLevelHUId, @NonNull final ProductId storageProductId)
	{
		final String json = null; // to be computed when needed
		final DocumentId documentId = null; // to be computed when needed
		return new HUEditorRowId(huId, storageProductId, topLevelHUId, json, documentId);
	}

	public static HUEditorRowId ofDocumentId(@NonNull final DocumentId documentId)
	{
		return fromJson(documentId.toJson(), documentId);
	}

	@JsonCreator
	public static HUEditorRowId fromJson(final String json)
	{
		final DocumentId documentId = null; // to be computed when needed
		return fromJson(json, documentId);
	}

	private static HUEditorRowId fromJson(@NonNull final String json, final DocumentId documentId)
	{
		//
		// Split json to parts
		final Iterator<String> partsIterator;
		{
			final List<String> parts = PARTS_SPLITTER.splitToList(json);
			if (parts.isEmpty())
			{
				throw new IllegalArgumentException("Invalid HU rowId: " + json);
			}
			partsIterator = parts.iterator();
		}

		//
		// huId and storageProductId
		final HuId huId;
		final ProductId storageProductId;
		{
			final String idStrPart = partsIterator.next();
			final List<String> idParts = ID_SPLITTER.splitToList(idStrPart);
			if (idParts.size() == 1)
			{
				huId = HuId.ofRepoId(Integer.parseInt(idParts.get(0)));
				storageProductId = null;
			}
			else if (idParts.size() == 2)
			{
				huId = HuId.ofRepoId(Integer.parseInt(idParts.get(0)));
				storageProductId = ProductId.ofRepoId(Integer.parseInt(idParts.get(1)));
			}
			else
			{
				throw new IllegalArgumentException("Invalid HU rowId: " + json + ". Cannot parse ID part: " + idStrPart);
			}
		}

		//
		// Others
		HuId topLevelHUId = null;
		while (partsIterator.hasNext())
		{
			final String part = partsIterator.next();
			if (part.startsWith(PREFIX_TopLevelHUId))
			{
				final String topLevelHUIdStr = part.substring(PREFIX_TopLevelHUId.length());
				topLevelHUId = HuId.ofRepoId(Integer.parseInt(topLevelHUIdStr));
			}
			else
			{
				throw new IllegalArgumentException("Invalid HU rowId: " + json + ". Cannot parse part: " + part);
			}
		}

		return new HUEditorRowId(huId, storageProductId, topLevelHUId, json, documentId);
	}

	public static DocumentIdsSelection rowIdsFromTopLevelHuIds(final Collection<HuId> huIds)
	{
		final HuId topLevelHUId = null;
		return huIds.stream()
				.map(huId -> ofHU(huId, topLevelHUId))
				.map(HUEditorRowId::toDocumentId)
				.collect(DocumentIdsSelection.toDocumentIdsSelection());
	}

	public static Set<HuId> extractHUIdsOnly(final DocumentIdsSelection rowIds)
	{
		return rowIds.stream()
				.map(HUEditorRowId::ofDocumentId)
				// .filter(HUEditorRowId::isHU) // accept even CUs (i.e. product storages)
				.map(HUEditorRowId::getHuId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static final String PREFIX_TopLevelHUId = "T";

	private static final String ID_SEPARATOR = "-";
	private static final Splitter ID_SPLITTER = Splitter.on(ID_SEPARATOR).omitEmptyStrings();

	private static final String PARTS_SEPARATOR = "_";
	private static final Joiner PARTS_JOINER = Joiner.on(PARTS_SEPARATOR).skipNulls();
	private static final Splitter PARTS_SPLITTER = Splitter.on(PARTS_SEPARATOR).omitEmptyStrings();

	private final HuId huId;
	private final ProductId storageProductId;
	private final HuId topLevelHUId;
	private transient String _json; // lazy
	private transient DocumentId _documentId; // lazy

	private HUEditorRowId(
			@NonNull final HuId huId,
			final ProductId storageProductId,
			final HuId topLevelHUId,
			final String json,
			final DocumentId documentId)
	{
		this.huId = huId;
		this.storageProductId = storageProductId != null ? storageProductId : null;
		this.topLevelHUId = topLevelHUId != null && !topLevelHUId.equals(huId) ? topLevelHUId : null;

		_json = json;
		_documentId = documentId;
	}

	@Override
	public String toString()
	{
		return toJson();
	}

	@JsonValue
	public String toJson()
	{
		String json = _json;
		if (json == null)
		{
			json = _json = toJson(huId, storageProductId, topLevelHUId);
		}
		return json;
	}

	private static String toJson(final HuId huId, final ProductId storageProductId, final HuId topLevelHUId)
	{
		// IMPORTANT: top level row shall be perfectly convertible to integers, else, a lot of APIs could fail

		final String idStrPart;
		if (storageProductId != null)
		{
			idStrPart = huId.getRepoId() + ID_SEPARATOR + storageProductId.getRepoId();
		}
		else
		{
			idStrPart = String.valueOf(huId.getRepoId());
		}

		final String topLevelHUIdPart = topLevelHUId != null ? PREFIX_TopLevelHUId + topLevelHUId.getRepoId() : null;
		return PARTS_JOINER.join(idStrPart, topLevelHUIdPart);
	}

	public DocumentId toDocumentId()
	{
		DocumentId documentId = _documentId;
		if (documentId == null)
		{
			documentId = _documentId = DocumentId.of(toJson());
		}
		return documentId;
	}

	public boolean isTopLevel()
	{
		return huId != null && topLevelHUId == null && storageProductId == null;
	}

	public boolean isHU()
	{
		return storageProductId == null;
	}

	public HuId getHuId()
	{
		return huId;
	}

	public HUEditorRowId toTopLevelRowId()
	{
		if (isTopLevel())
		{
			return this;
		}

		final HuId huId = getTopLevelHUId();
		final ProductId storageProductId = null;
		final HuId topLevelHUId = null;
		final String json = null;
		final DocumentId documentId = null;
		return new HUEditorRowId(huId, storageProductId, topLevelHUId, json, documentId);
	}

	public HuId getTopLevelHUId()
	{
		if (topLevelHUId != null)
		{
			return topLevelHUId;
		}

		return huId;
	}

	public ProductId getStorageProductId()
	{
		return storageProductId;
	}
}
