package de.metas.ui.web.picking;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.exceptions.EntityNotFoundException;
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

@ToString
public final class PickingSlotRow implements IViewRow
{
	private final DocumentId id;
	private final IViewRowType type;
	private final boolean processed;
	private final DocumentPath documentPath;

	//
	// Picking slot
	private final boolean pickingSlotRow;
	private final ITranslatableString pickingSlotCaption;

	//
	// HU
	@ViewColumn(captionKey = "HUCode", widgetType = DocumentFieldWidgetType.Text, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 10)
	})
	private final String huCode;

	@ViewColumn(captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 20)
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
			final LookupValue pickingSlotBPLocation)
	{
		this.id = DocumentId.of(pickingSlotId);
		this.type = DefaultRowType.Row;
		this.processed = false;
		this.documentPath = DocumentPath.rootDocumentPath(PickingConstants.WINDOWID_PickingSlotView, id);

		//
		// HU
		huCode = null;
		huProduct = null;
		huPackingInfo = null;
		huQtyCU = null;

		//
		// Picking slot info
		pickingSlotRow = true;
		pickingSlotCaption = buildPickingSlotCaption(pickingSlotName, pickingSlotBPartner, pickingSlotBPLocation);
	}

	/**
	 * HU row constructor
	 *
	 * @param code
	 * @param product
	 * @param packingInfo
	 * @param qtyCU
	 */
	@Builder(builderMethodName = "fromHUBuilder", builderClassName = "FromHUBuilder")
	private PickingSlotRow(@NonNull final DocumentId id,
			final IViewRowType type,
			final boolean processed,
			@NonNull final DocumentPath documentPath,
			//
			final String code,
			final JSONLookupValue product,
			final String packingInfo,
			final BigDecimal qtyCU)
	{
		this.id = id;
		this.type = type;
		this.processed = processed;
		this.documentPath = documentPath;

		//
		// HU
		huCode = code;
		huProduct = product;
		huPackingInfo = packingInfo;
		huQtyCU = qtyCU;

		//
		// Picking slot info
		pickingSlotRow = false;
		pickingSlotCaption = null;
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
	public List<? extends IViewRow> getIncludedRows()
	{
		return ImmutableList.of();
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
		return pickingSlotRow;
	}
	
	@Override
	public ITranslatableString getSingleColumnCaption()
	{
		return pickingSlotCaption;
	}
}
