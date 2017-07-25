package de.metas.ui.web.picking;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowAttributes;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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

@ToString
public final class PickingSlotRow implements IViewRow
{
	public static PickingSlotRow cast(final IViewRow row)
	{
		return (PickingSlotRow)row;
	}

	private final PickingSlotRowId id;
	private final IViewRowType type;
	
	
	private final DocumentPath documentPath;

	//
	// Picking slot
	private final boolean pickingSlotRow;
	private final int pickingSlotId;
	private final ITranslatableString pickingSlotCaption;
	private final LookupValue pickingSlotWarehouse;

	//
	// HU
	private final int huId;
	private final int huStorageProductId;

	//
	// HU
	@ViewColumn(captionKey = "HUCode", widgetType = DocumentFieldWidgetType.Text, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 10)
	})
	private final String huCode;
	
//	@ViewColumn(captionKey = "Processed", widgetType = DocumentFieldWidgetType.YesNo, layouts = {
//			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 20)
//	})
	// this is the "standard" processed flag which every IViewRow has-.
	// TODO: decide whether to display it or not
	private final boolean processed;

	@ViewColumn(captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 30)
	})
	private final JSONLookupValue huProduct;

	@ViewColumn(captionKey = "M_HU_PI_Item_Product_ID", widgetType = DocumentFieldWidgetType.Text, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 40)
	})
	private final String huPackingInfo;

	@ViewColumn(captionKey = "QtyCU", widgetType = DocumentFieldWidgetType.Quantity, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 50)
	})
	private final BigDecimal huQtyCU;

	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues;

	private final ImmutableMap<PickingSlotRowId, PickingSlotRow> includedHURows;

	/**
	 * Picking slot row constructor
	 */
	@Builder(builderMethodName = "fromPickingSlotBuilder", builderClassName = "FromPickingSlotBuilder")
	private PickingSlotRow(
			final int pickingSlotId,
			//
			final String pickingSlotName,
			final LookupValue pickingSlotWarehouse,
			final LookupValue pickingSlotBPartner,
			final LookupValue pickingSlotBPLocation,
			//
			final List<PickingSlotRow> includedHURows)
	{
		id = PickingSlotRowId.ofPickingSlotId(pickingSlotId);
		type = DefaultRowType.Row;
		processed = false;
		documentPath = DocumentPath.rootDocumentPath(PickingConstants.WINDOWID_PickingSlotView, id.toDocumentId());

		//
		// HU
		huId = -1;
		huStorageProductId = -1;
		huCode = null;
		huProduct = null;
		huPackingInfo = null;
		huQtyCU = null;

		//
		// Picking slot info
		pickingSlotRow = true;
		this.pickingSlotId = pickingSlotId;
		pickingSlotCaption = buildPickingSlotCaption(pickingSlotName, pickingSlotBPartner, pickingSlotBPLocation);
		this.pickingSlotWarehouse = pickingSlotWarehouse;

		//
		// Included rows
		this.includedHURows = includedHURows == null ? ImmutableMap.of() : Maps.uniqueIndex(includedHURows, PickingSlotRow::getPickingSlotRowId);
	}

	/**
	 * HU row constructor
	 *
	 * @param code
	 * @param product
	 * @param packingInfo
	 * @param qtyCU
	 * @param huRows
	 */
	@Builder(builderMethodName = "fromHUBuilder", builderClassName = "FromHUBuilder")
	private PickingSlotRow(
			final int pickingSlotId,
			final int huId,
			final int huStorageProductId,
			//
			// @NonNull final DocumentId id,
			final IViewRowType type,
			final boolean processed,
			// @NonNull final DocumentPath documentPath,
			//
			final String code,
			final JSONLookupValue product,
			final String packingInfo,
			final BigDecimal qtyCU,
			//
			final List<PickingSlotRow> includedHURows)
	{
		Preconditions.checkArgument(pickingSlotId > 0, "pickingSlotId > 0");
		Preconditions.checkArgument(huId > 0, "huId > 0");

		id = PickingSlotRowId.ofHU(pickingSlotId, huId, huStorageProductId);
		this.type = type;
		this.processed = processed;
		documentPath = DocumentPath.rootDocumentPath(WEBUI_HU_Constants.WEBUI_HU_Window_ID, id.toDocumentId());

		//
		// HU
		this.huId = huId;
		this.huStorageProductId = huStorageProductId;
		huCode = code;
		huProduct = product;
		huPackingInfo = packingInfo;
		huQtyCU = qtyCU;

		//
		// Picking slot info
		pickingSlotRow = false;
		this.pickingSlotId = pickingSlotId;
		pickingSlotCaption = null;
		pickingSlotWarehouse = null;

		//
		// Included rows
		this.includedHURows = includedHURows == null ? ImmutableMap.of() : Maps.uniqueIndex(includedHURows, PickingSlotRow::getPickingSlotRowId);
	}

	private static final ITranslatableString buildPickingSlotCaption(final String pickingSlotName, final LookupValue pickingSlotBPartner, final LookupValue pickingSlotBPLocation)
	{
		return ITranslatableString.compose(" ",
				ITranslatableString.constant(pickingSlotName),
				pickingSlotBPartner != null ? pickingSlotBPartner.getDisplayNameTrl() : ITranslatableString.empty(),
				pickingSlotBPLocation != null ? pickingSlotBPLocation.getDisplayNameTrl() : ITranslatableString.empty());
	}

	@Override
	public DocumentId getId()
	{
		return id.toDocumentId();
	}

	public PickingSlotRowId getPickingSlotRowId()
	{
		return id;
	}

	@Override
	public IViewRowType getType()
	{
		return type;
	}

	@Override
	public boolean isProcessed()
	{
		return processed;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	@Override
	public Map<String, Object> getFieldNameAndJsonValues()
	{
		if (_fieldNameAndJsonValues == null)
		{
			_fieldNameAndJsonValues = ViewColumnHelper.extractJsonMap(this);
		}
		return _fieldNameAndJsonValues;
	}

	@Override
	public List<PickingSlotRow> getIncludedRows()
	{
		return ImmutableList.copyOf(includedHURows.values());
	}

	public Optional<PickingSlotRow> findIncludedRowById(final PickingSlotRowId id)
	{
		// This
		if (this.id.equals(id))
		{
			return Optional.of(this);
		}
		
		// Direct children
		{
			final PickingSlotRow row = includedHURows.get(id);
			if (row != null)
			{
				return Optional.of(row);
			}
		}

		// Ask all included HU rows
		return includedHURows.values()
				.stream()
				.map(includedHURow -> includedHURow.findIncludedRowById(id).orElse(null))
				.filter(row -> row != null)
				.findFirst();
	}

	@Override
	public boolean hasAttributes()
	{
		return false;
	}

	@Override
	public IViewRowAttributes getAttributes() throws EntityNotFoundException
	{
		throw new EntityNotFoundException("Row does not support attributes");
	}

	@Override
	public boolean hasIncludedView()
	{
		return false;
	}

	@Override
	public boolean isSingleColumn()
	{
		return isPickingSlotRow();
	}

	@Override
	public ITranslatableString getSingleColumnCaption()
	{
		return pickingSlotCaption;
	}

	public boolean isPickingSlotRow()
	{
		return pickingSlotRow;
	}

	public int getPickingSlotId()
	{
		return pickingSlotId;
	}

	public int getHuId()
	{
		return huId;
	}

	public boolean isHURow()
	{
		return !isPickingSlotRow();
	}
	
	public int getPickingSlotWarehouseId()
	{
		return pickingSlotWarehouse != null ? pickingSlotWarehouse.getIdAsInt() : -1;
	}

	//
	//
	//
	//
	//
	@EqualsAndHashCode
	public static final class PickingSlotRowId
	{
		public static final PickingSlotRowId ofPickingSlotId(final int pickingSlotId)
		{
			return new PickingSlotRowId(pickingSlotId, -1, -1);
		}

		public static final PickingSlotRowId ofHU(final int pickingSlotId, final int huId, final int huStorageProductId)
		{
			return new PickingSlotRowId(pickingSlotId, huId, huStorageProductId);
		}

		@Getter
		private final int pickingSlotId;
		@Getter
		private final int huId;
		@Getter
		private final int huStorageProductId;

		private transient DocumentId _documentId;

		private static final String SEPARATOR = "-";

		@Builder
		private PickingSlotRowId(final int pickingSlotId, final int huId, final int huStorageProductId)
		{
			Preconditions.checkArgument(pickingSlotId > 0, "pickingSlotId > 0");
			this.pickingSlotId = pickingSlotId;
			this.huId = huId > 0 ? huId : -1;
			this.huStorageProductId = huStorageProductId > 0 ? huStorageProductId : -1;
		}

		@Override
		public String toString()
		{
			return toDocumentId().toJson();
		}

		public DocumentId toDocumentId()
		{
			DocumentId id = this._documentId;
			if (id == null)
			{
				final String idStr = Joiner.on(SEPARATOR).skipNulls().join(pickingSlotId,
						huId > 0 ? huId : null,
						huStorageProductId > 0 ? huStorageProductId : null);
				id = _documentId = DocumentId.ofString(idStr);
			}
			return id;
		}

		public static final PickingSlotRowId fromDocumentId(final DocumentId documentId)
		{
			final List<String> parts = Splitter.on(SEPARATOR).splitToList(documentId.toJson());
			final int partsCount = parts.size();
			if (partsCount < 1)
			{
				throw new IllegalArgumentException("Invalid id: " + documentId);
			}

			final int pickingSlotId = Integer.parseInt(parts.get(0));
			final int huId = partsCount >= 2 ? Integer.parseInt(parts.get(1)) : -1;
			final int huStorageProductId = partsCount >= 3 ? Integer.parseInt(parts.get(2)) : -1;

			return new PickingSlotRowId(pickingSlotId, huId, huStorageProductId);
		}

		public boolean isPickingSlotId()
		{
			return huId <= 0;
		}

		public PickingSlotRowId toPickingSlotId()
		{
			if (isPickingSlotId())
			{
				return this;
			}
			else
			{
				return new PickingSlotRowId(pickingSlotId, -1, -1);
			}
		}

	}

	public BigDecimal getHuQtyCU()
	{
		return huQtyCU;
	}
}
