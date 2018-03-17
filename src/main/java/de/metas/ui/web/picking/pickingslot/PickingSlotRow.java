package de.metas.ui.web.picking.pickingslot;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.picking.PickingConstants;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.MediaType;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.Builder;
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
 * Rows shown in {@link PickingSlotView}. One row can represent a picking slot, a TU or a picked good (CU).
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString
public final class PickingSlotRow implements IViewRow
{
	public static PickingSlotRow cast(final IViewRow row)
	{
		return (PickingSlotRow)row;
	}

	private final PickingSlotRowId pickingSlotRowId;
	private final PickingSlotRowType type;
	private final DocumentPath documentPath;
	private final boolean processed;
	private final ImmutableMap<PickingSlotRowId, PickingSlotRow> includedHURows;
	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy

	private final ViewId includedViewId;

	//
	// Picking slot
	private final LookupValue pickingSlotBPartner;
	private final LookupValue pickingSlotBPLocation;
	private final LookupValue pickingSlotWarehouse;
	private final int pickingSlotLocatorId;
	private final ITranslatableString pickingSlotCaption;

	//
	// HU
	private final boolean huTopLevel;

	@ViewColumn(captionKey = "HUCode", widgetType = DocumentFieldWidgetType.Text, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 10)
	})
	private final String huCode;

	@ViewColumn(captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 30)
	})
	private final JSONLookupValue huProduct;

	@ViewColumn(captionKey = "M_HU_PI_Item_Product_ID", widgetType = DocumentFieldWidgetType.Text, //
			restrictToMediaTypes = { MediaType.SCREEN }, //
			layouts = {
					@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40),
					@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 40)
			})
	private final String huPackingInfo;

	@ViewColumn(captionKey = "QtyCU", widgetType = DocumentFieldWidgetType.Quantity, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 50)
	})
	private final BigDecimal huQtyCU;

	@Builder(builderMethodName = "fromSourceHUBuilder", builderClassName = "FromSourceHUBuilder")
	private PickingSlotRow(
			final int huId,
			final HUEditorRowType huEditorRowType,
			final String huCode,
			final JSONLookupValue product,
			final String packingInfo,
			final BigDecimal qtyCU,
			final boolean topLevelHU)
	{
		pickingSlotRowId = PickingSlotRowId.ofSourceHU(huId);

		this.type = PickingSlotRowType.forPickingHuRow(huEditorRowType);
		processed = true;
		documentPath = DocumentPath.rootDocumentPath(WEBUI_HU_Constants.WEBUI_HU_Window_ID, huId);

		//
		// HU
		this.huCode = huCode;
		huProduct = product;
		huPackingInfo = packingInfo;
		huQtyCU = qtyCU;
		huTopLevel = topLevelHU;

		//
		// Picking slot info
		pickingSlotWarehouse = null;
		pickingSlotLocatorId = -1;
		pickingSlotBPartner = null;
		pickingSlotBPLocation = null;
		pickingSlotCaption = null;

		//
		// Included rows - we don't show any
		this.includedHURows = ImmutableMap.of();

		this.includedViewId = null;
	}

	/**
	 * Picking slot row constructor.
	 * 
	 * @param pickingSlotId
	 * @param pickingSlotName
	 * @param pickingSlotWarehouse
	 * @param pickingSlotBPartner
	 * @param pickingSlotBPLocation
	 * @param includedHURows
	 */
	@Builder(builderMethodName = "fromPickingSlotBuilder", builderClassName = "FromPickingSlotBuilder")
	private PickingSlotRow(
			final int pickingSlotId,
			//
			final String pickingSlotName,
			final LookupValue pickingSlotWarehouse,
			final int pickingSlotLocatorId,
			final LookupValue pickingSlotBPartner,
			final LookupValue pickingSlotBPLocation,
			//
			final List<PickingSlotRow> includedHURows,
			final ViewId includedViewId)
	{
		pickingSlotRowId = PickingSlotRowId.ofPickingSlotId(pickingSlotId);

		type = PickingSlotRowType.forPickingSlotRow();
		processed = false;
		documentPath = DocumentPath.rootDocumentPath(PickingConstants.WINDOWID_PickingSlotView, pickingSlotId);

		// HU
		huCode = null;
		huProduct = null;
		huPackingInfo = null;
		huQtyCU = null;
		huTopLevel = false;

		// Picking slot info
		this.pickingSlotWarehouse = pickingSlotWarehouse;
		this.pickingSlotLocatorId = pickingSlotLocatorId;
		this.pickingSlotBPartner = pickingSlotBPartner;
		this.pickingSlotBPLocation = pickingSlotBPLocation;

		pickingSlotCaption = buildPickingSlotCaption(pickingSlotName, pickingSlotBPartner, pickingSlotBPLocation);

		// Included rows
		this.includedHURows = includedHURows == null ? ImmutableMap.of() : Maps.uniqueIndex(includedHURows, PickingSlotRow::getPickingSlotRowId);

		this.includedViewId = includedViewId;
	}

	/**
	 * Constructor for a row that represents an HU which is already assigned to a picking slot (aka a picked HU).
	 * 
	 * @param pickingSlotId
	 * @param huId
	 * @param huStorageProductId
	 * @param type
	 * @param processed
	 * @param code
	 * @param product
	 * @param packingInfo
	 * @param qtyCU
	 * @param includedHURows
	 */
	@Builder(builderMethodName = "fromPickedHUBuilder", builderClassName = "FromPickedHUBuilder")
	private PickingSlotRow(
			final int pickingSlotId,
			final int huId,
			final int huStorageProductId,
			//
			final HUEditorRowType huEditorRowType,
			final boolean processed,
			//
			final String huCode,
			final JSONLookupValue product,
			final String packingInfo,
			final BigDecimal qtyCU,
			final boolean topLevelHU,
			//
			final List<PickingSlotRow> includedHURows)
	{
		Preconditions.checkArgument(pickingSlotId > 0, "pickingSlotId > 0");
		Preconditions.checkArgument(huId > 0, "huId > 0");

		pickingSlotRowId = PickingSlotRowId.ofPickedHU(pickingSlotId, huId, huStorageProductId);

		this.type = PickingSlotRowType.forPickingHuRow(huEditorRowType);
		this.processed = processed;

		documentPath = DocumentPath.rootDocumentPath(WEBUI_HU_Constants.WEBUI_HU_Window_ID, huId);

		// HU
		this.huCode = huCode;
		huProduct = product;
		huPackingInfo = packingInfo;
		huQtyCU = qtyCU;
		huTopLevel = topLevelHU;

		// Picking slot info
		pickingSlotWarehouse = null;
		pickingSlotLocatorId = -1;
		pickingSlotBPartner = null;
		pickingSlotBPLocation = null;
		pickingSlotCaption = null;

		// Included rows
		this.includedHURows = includedHURows == null ? ImmutableMap.of() : Maps.uniqueIndex(includedHURows, PickingSlotRow::getPickingSlotRowId);

		this.includedViewId = null;
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
		return pickingSlotRowId.toDocumentId();
	}

	public PickingSlotRowId getPickingSlotRowId()
	{
		return pickingSlotRowId;
	}

	@Override
	public PickingSlotRowType getType()
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

	public Stream<PickingSlotRow> streamIncludedRowsRecursivelly()
	{
		return includedHURows.values()
				.stream()
				.flatMap(PickingSlotRow::streamThisRowAndIncludedRowsRecursivelly);
	}

	public Stream<PickingSlotRow> streamThisRowAndIncludedRowsRecursivelly()
	{
		final Stream<PickingSlotRow> thisRowStream = Stream.of(this);
		final Stream<PickingSlotRow> includedRowsStream = streamIncludedRowsRecursivelly();
		return Stream.concat(thisRowStream, includedRowsStream);
	}

	public Optional<PickingSlotRow> findIncludedRowById(final PickingSlotRowId includedRowId)
	{
		// This
		if (this.pickingSlotRowId.equals(includedRowId))
		{
			return Optional.of(this);
		}

		// Direct children
		{
			final PickingSlotRow row = includedHURows.get(includedRowId);
			if (row != null)
			{
				return Optional.of(row);
			}
		}

		// Ask all included HU rows
		return includedHURows.values()
				.stream()
				.map(includedHURow -> includedHURow.findIncludedRowById(includedRowId).orElse(null))
				.filter(row -> row != null)
				.findFirst();
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

	/**
	 * @return {@code true} if this row represents an actual picking slot and not any sort of HU that is also shown in this view.
	 */
	public boolean isPickingSlotRow()
	{
		return pickingSlotRowId.isPickingSlotRow();
	}

	public int getPickingSlotId()
	{
		return getPickingSlotRowId().getPickingSlotId();
	}

	public int getHuId()
	{
		return pickingSlotRowId.getHuId();
	}

	/**
	 * @return {@code true} is this row represents an HU that is assigned to a picking slot
	 */
	public boolean isPickedHURow()
	{
		return pickingSlotRowId.isPickedHURow();
	}

	public boolean isTopLevelHU()
	{
		return huTopLevel;
	}

	public boolean isLU()
	{
		return isPickedHURow() && getType().isLU();
	}

	public boolean isTU()
	{
		return isPickedHURow() && getType().isTU();
	}

	public boolean isCU()
	{
		return isPickedHURow() && getType().isCU();
	}

	public boolean isCUOrStorage()
	{
		return isPickedHURow() && getType().isCUOrStorage();
	}

	/**
	 * 
	 * @return {@code true} if this row represents an HU that is a source-HU for fine-picking.
	 */
	public boolean isPickingSourceHURow()
	{
		return pickingSlotRowId.isPickingSourceHURow();
	}

	public int getPickingSlotWarehouseId()
	{
		return pickingSlotWarehouse != null ? pickingSlotWarehouse.getIdAsInt() : -1;
	}

	public BigDecimal getHuQtyCU()
	{
		return huQtyCU;
	}

	public int getHuProductId()
	{
		return huProduct != null ? huProduct.getKeyAsInt() : 0;
	}

	@Override
	public ViewId getIncludedViewId()
	{
		return includedViewId;
	}

	public int getPickingSlotLocatorId()
	{
		return pickingSlotLocatorId;
	}

	public int getBPartnerId()
	{
		return pickingSlotBPartner != null ? pickingSlotBPartner.getIdAsInt() : -1;
	}

	public int getBPartnerLocationId()
	{
		return pickingSlotBPLocation != null ? pickingSlotBPLocation.getIdAsInt() : -1;
	}
}
