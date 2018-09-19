package de.metas.ui.web.pickingV2.productsToPick;

import java.time.LocalDate;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import de.metas.quantity.Quantity;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ToString(exclude = "_fieldNameAndJsonValues")
public class ProductsToPickRow implements IViewRow
{
	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = "M_Product_ID", seqNo = 10)
	private final LookupValue product;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Lookup, captionKey = "M_Locator_ID", seqNo = 20)
	private final LookupValue locator;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, captionKey = "LotNumber", seqNo = 30)
	private final String lotNumberAttr;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Date, captionKey = "ExpiringDate", seqNo = 40)
	private final LocalDate expiringDateAttr;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, captionKey = "RepackNumber", seqNo = 50)
	private final String repackNumberAttr;

	@ViewColumn(widgetType = DocumentFieldWidgetType.YesNo, captionKey = "Bruch", seqNo = 60) // Damaged
	private final Boolean bruchAttr;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, captionKey = "Qty", seqNo = 70)
	private final Quantity qty;

	@ViewColumn(widgetType = DocumentFieldWidgetType.YesNo, captionKey = "Processed", seqNo = 80)
	private final boolean processed;

	//
	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy
	private final ProductsToPickRowId rowId;

	@Builder
	private ProductsToPickRow(
			@NonNull final ProductsToPickRowId rowId,
			@NonNull final LookupValue product,
			@NonNull final LookupValue locator,
			final String lotNumberAttr,
			final LocalDate expiringDateAttr,
			final String repackNumberAttr,
			final Boolean bruchAttr,
			@NonNull final Quantity qty,
			final boolean processed)
	{
		this.rowId = rowId;
		this.product = product;
		this.locator = locator;
		this.lotNumberAttr = lotNumberAttr;
		this.expiringDateAttr = expiringDateAttr;
		this.repackNumberAttr = repackNumberAttr;
		this.bruchAttr = bruchAttr;
		this.qty = qty;
		this.processed = processed;
	}

	@Override
	public DocumentId getId()
	{
		return rowId.toDocumentId();
	}

	@Override
	public boolean isProcessed()
	{
		return processed;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		// TODO Auto-generated method stub
		return null;
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
}
