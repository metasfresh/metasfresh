package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import groovy.transform.Immutable;
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
public final class HUEditorRowId
{
	public static HUEditorRowId ofTopLevelHU(final int topLevelHUId)
	{
		final int storageProductId = -1;
		final String json = null; // to be computed when needed
		final DocumentId documentId = null; // to be computed when needed

		return new HUEditorRowId(topLevelHUId,
				-1, //  topLevelHUId parameter
				storageProductId,
				json, documentId);
	}

	public static HUEditorRowId ofHU(final int huId, final int topLevelHUId)
	{
		final int storageProductId = -1;
		final String json = null; // to be computed when needed
		final DocumentId documentId = null; // to be computed when needed
		return new HUEditorRowId(huId, storageProductId, topLevelHUId, json, documentId);
	}

	public static HUEditorRowId ofHUStorage(final int huId, final int topLevelHUId, final int storageProductId)
	{
		Preconditions.checkArgument(storageProductId > 0);

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
		final int huId;
		final int storageProductId;
		{
			final String idStrPart = partsIterator.next();
			final List<String> idParts = ID_SPLITTER.splitToList(idStrPart);
			if (idParts.size() == 1)
			{
				huId = Integer.parseInt(idParts.get(0));
				storageProductId = -1;
			}
			else if (idParts.size() == 2)
			{
				huId = Integer.parseInt(idParts.get(0));
				storageProductId = Integer.parseInt(idParts.get(1));
			}
			else
			{
				throw new IllegalArgumentException("Invalid HU rowId: " + json + ". Cannot parse ID part: " + idStrPart);
			}
		}

		//
		// Others
		int topLevelHUId = -1;
		while (partsIterator.hasNext())
		{
			final String part = partsIterator.next();
			if (part.startsWith(PREFIX_TopLevelHUId))
			{
				final String topLevelHUIdStr = part.substring(PREFIX_TopLevelHUId.length());
				topLevelHUId = Integer.parseInt(topLevelHUIdStr);
			}
			else
			{
				throw new IllegalArgumentException("Invalid HU rowId: " + json + ". Cannot parse part: " + part);
			}
		}

		return new HUEditorRowId(huId, storageProductId, topLevelHUId, json, documentId);
	}

	public static DocumentIdsSelection rowIdsFromTopLevelM_HU_IDs(final Collection<Integer> huIds)
	{
		final int topLevelHUId = -1;
		return huIds.stream()
				.map(huId -> ofHU(huId, topLevelHUId))
				.map(HUEditorRowId::toDocumentId)
				.collect(DocumentIdsSelection.toDocumentIdsSelection());
	}

	public static Set<Integer> extractHUIdsOnly(final DocumentIdsSelection rowIds)
	{
		return rowIds.stream()
				.map(HUEditorRowId::ofDocumentId)
				.filter(HUEditorRowId::isHU)
				.map(HUEditorRowId::getHuId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static final String PREFIX_TopLevelHUId = "T";

	private static final String ID_SEPARATOR = "-";
	private static final Splitter ID_SPLITTER = Splitter.on(ID_SEPARATOR).omitEmptyStrings();

	private static final String PARTS_SEPARATOR = "_";
	private static final Joiner PARTS_JOINER = Joiner.on(PARTS_SEPARATOR).skipNulls();
	private static final Splitter PARTS_SPLITTER = Splitter.on(PARTS_SEPARATOR).omitEmptyStrings();

	private final int huId;
	private final int storageProductId;
	private final int topLevelHUId;
	private transient String _json; // lazy
	private transient DocumentId _documentId; // lazy

	private HUEditorRowId(final int huId, final int storageProductId, final int topLevelHUId, final String json, final DocumentId documentId)
	{
		Preconditions.checkArgument(huId > 0, "huId shall be positive");

		this.huId = huId;
		this.storageProductId = storageProductId > 0 ? storageProductId : -1;
		this.topLevelHUId = topLevelHUId > 0 && topLevelHUId != huId ? topLevelHUId : -1;

		_json = json;
		_documentId = documentId;
	}

	@Override
	public String toString()
	{
		return toJson();
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(huId, storageProductId, topLevelHUId);
	};

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj instanceof HUEditorRowId)
		{
			final HUEditorRowId other = (HUEditorRowId)obj;
			return huId == other.huId
					&& storageProductId == other.storageProductId
					&& topLevelHUId == other.topLevelHUId;
		}
		else
		{
			return false;
		}
	};

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

	private static final String toJson(final int huId, final int storageProductId, final int topLevelHUId)
	{
		// IMPORTANT: top level row shall be perfectly convertible to integers, else, a lot of APIs could fail

		final String idStrPart;
		if (storageProductId > 0)
		{
			idStrPart = huId + ID_SEPARATOR + storageProductId;
		}
		else
		{
			idStrPart = String.valueOf(huId);
		}

		final String topLevelHUIdPart = topLevelHUId > 0 ? PREFIX_TopLevelHUId + topLevelHUId : null;
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
		return huId > 0 && topLevelHUId <= 0 && storageProductId <= 0;
	}

	public boolean isHU()
	{
		return storageProductId <= 0;
	}

	public int getHuId()
	{
		return huId;
	}

	public HUEditorRowId toTopLevelRowId()
	{
		if (isTopLevel())
		{
			return this;
		}

		final int huId = getTopLevelHUId();
		final int storageProductId = -1;
		final int topLevelHUId = -1;
		final String json = null;
		final DocumentId documentId = null;
		return new HUEditorRowId(huId, storageProductId, topLevelHUId, json, documentId);
	}

	public int getTopLevelHUId()
	{
		if (topLevelHUId > 0)
		{
			return topLevelHUId;
		}

		return huId;
	}

	public int getStorageProductId()
	{
		return storageProductId;
	}
}
