package de.metas.ui.web.picking.pickingslot;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import de.metas.handlingunits.HuId;
import de.metas.picking.api.PickingSlotId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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

@EqualsAndHashCode
public final class PickingSlotRowId
{
	public static final PickingSlotRowId ofPickingSlotId(final PickingSlotId pickingSlotId)
	{
		final HuId huId = null;
		final int huStorageProductId = -1;
		return new PickingSlotRowId(pickingSlotId, huId, huStorageProductId);
	}

	public static final PickingSlotRowId ofPickedHU(final PickingSlotId pickingSlotId, final HuId huId, final int huStorageProductId)
	{
		return new PickingSlotRowId(pickingSlotId, huId, huStorageProductId);
	}

	public static final PickingSlotRowId ofSourceHU(final HuId huId)
	{
		final PickingSlotId pickingSlotId = null;
		final int huStorageProductId = -1;
		return new PickingSlotRowId(pickingSlotId, huId, huStorageProductId);
	}

	public static final PickingSlotRowId fromDocumentId(final DocumentId documentId)
	{
		final List<String> parts = DOCUMENT_ID_SPLITTER.splitToList(documentId.toJson());
		return fromStringPartsList(parts);
	}

	public static final PickingSlotRowId fromStringPartsList(final List<String> parts)
	{
		final int partsCount = parts.size();
		if (partsCount < 1)
		{
			throw new IllegalArgumentException("Invalid id: " + parts);
		}

		final PickingSlotId pickingSlotId = !Check.isEmpty(parts.get(0), true) ? PickingSlotId.ofRepoIdOrNull(Integer.parseInt(parts.get(0))) : null;
		final HuId huId = partsCount >= 2 ? HuId.ofRepoIdOrNull(Integer.parseInt(parts.get(1))) : null;
		final int huStorageProductId = partsCount >= 3 ? Integer.parseInt(parts.get(2)) : 0;

		return new PickingSlotRowId(pickingSlotId, huId, huStorageProductId);
	}

	@Getter
	private final PickingSlotId pickingSlotId;
	@Getter
	private final HuId huId;
	@Getter
	private final int huStorageProductId;

	private transient DocumentId _documentId; // lazy

	private static final String SEPARATOR = "-";
	private static final Joiner DOCUMENT_ID_JOINER = Joiner.on(SEPARATOR).skipNulls();
	private static final Splitter DOCUMENT_ID_SPLITTER = Splitter.on(SEPARATOR);

	@Builder
	private PickingSlotRowId(final PickingSlotId pickingSlotId, final HuId huId, final int huStorageProductId)
	{
		this.pickingSlotId = pickingSlotId;
		this.huId = huId;
		this.huStorageProductId = huStorageProductId > 0 ? huStorageProductId : 0;
	}

	@Override
	public String toString()
	{
		return toDocumentId().toJson();
	}

	public DocumentId toDocumentId()
	{
		DocumentId id = _documentId;
		if (id == null)
		{
			final String idStr = DOCUMENT_ID_JOINER.join(
					pickingSlotId != null ? pickingSlotId.getRepoId() : 0,
					huId != null ? huId.getRepoId() : null,
					huStorageProductId > 0 ? huStorageProductId : null);

			id = _documentId = DocumentId.ofString(idStr);
		}
		return id;
	}

	/** @return {@code true} if this row ID represents an actual picking slot and not any sort of HU that is also shown in this view. */
	public boolean isPickingSlotRow()
	{
		return getPickingSlotId() != null && getHuId() == null;
	}

	/** @return {@code true} is this row ID represents an HU that is assigned to a picking slot */
	public boolean isPickedHURow()
	{
		return getPickingSlotId() != null && getHuId() != null;
	}

	/** @return {@code true} if this row ID represents an HU that is a source-HU for fine-picking. */
	public boolean isPickingSourceHURow()
	{
		return getPickingSlotId() == null && getHuId() != null;
	}
}
