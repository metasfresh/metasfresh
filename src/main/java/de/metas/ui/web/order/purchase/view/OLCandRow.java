package de.metas.ui.web.order.purchase.view;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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

@ToString(exclude = "_fieldNameAndJsonValues")
public class OLCandRow implements IViewRow
{
	@ViewColumn(captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 10)
	})
	private final JSONLookupValue product;
	@ViewColumn(captionKey = "Vendor_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 20)
	})
	private final JSONLookupValue vendorBPartner;

	@ViewColumn(captionKey = "QtyToDeliver", widgetType = DocumentFieldWidgetType.Quantity, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 30)
	})
	private final BigDecimal qtyToDeliver;

	public static final String FIELDNAME_QtyToPurchase = "qtyToPurchase";
	@ViewColumn(fieldName = FIELDNAME_QtyToPurchase, captionKey = "QtyToPurchase", widgetType = DocumentFieldWidgetType.Quantity, editor = ViewEditorRenderMode.ALWAYS, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 40)
	})
	private BigDecimal qtyToPurchase;

	public static final String FIELDNAME_DatePromised = "datePromised";
	@ViewColumn(fieldName = FIELDNAME_DatePromised, captionKey = "DatePromised", widgetType = DocumentFieldWidgetType.DateTime, editor = ViewEditorRenderMode.ALWAYS, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 50)
	})
	private Date datePromised;

	//
	private final OLCandRowId rowId;
	private final IViewRowType rowType;
	private final ImmutableList<OLCandRow> includedRows;
	private final ImmutableMap<OLCandRowId, OLCandRow> includedRowsByRowId;

	//
	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy

	@Builder
	private OLCandRow(
			@NonNull final OLCandRowId rowId,
			@NonNull final IViewRowType rowType,
			@NonNull final JSONLookupValue product,
			@Nullable final JSONLookupValue vendorBPartner,
			@Nullable final BigDecimal qtyToDeliver,
			@Nullable final BigDecimal qtyToPurchase,
			@Nullable final Date datePromised,
			@NonNull @Singular final ImmutableList<OLCandRow> includedRows)
	{
		this.rowId = rowId;
		this.rowType = rowType;
		this.product = product;
		this.vendorBPartner = vendorBPartner;
		this.qtyToDeliver = qtyToDeliver;
		this.qtyToPurchase = qtyToPurchase != null ? qtyToPurchase : BigDecimal.ZERO;

		Check.assume(rowType == OLCandRowType.GROUP || datePromised != null, "datePromised shall not be null");
		this.datePromised = datePromised;

		if (rowType == OLCandRowType.LINE && !includedRows.isEmpty())
		{
			throw new AdempiereException("Lines does not allow included rows");
		}
		this.includedRows = includedRows;
		includedRowsByRowId = includedRows.stream()
				.collect(ImmutableMap.toImmutableMap(OLCandRow::getRowId, Function.identity()));

		if (rowType == OLCandRowType.GROUP)
		{
			updateQtyToPurchaseFromIncludedRows();
		}
	}

	public OLCandRow copy()
	{
		final ImmutableList<OLCandRow> includedRowsCopy = includedRows.stream()
				.map(OLCandRow::copy)
				.collect(ImmutableList.toImmutableList());
		return new OLCandRow(rowId, rowType, product, vendorBPartner, qtyToDeliver, qtyToPurchase, datePromised, includedRowsCopy);
	}

	public OLCandRowId getRowId()
	{
		return rowId;
	}

	@Override
	public DocumentId getId()
	{
		return rowId.toDocumentId();
	}

	@Override
	public IViewRowType getType()
	{
		return rowType;
	}

	@Override
	public boolean isProcessed()
	{
		return false;
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

	@Override
	public List<OLCandRow> getIncludedRows()
	{
		return includedRows;
	}

	public OLCandRow getIncludedRowById(final OLCandRowId rowId)
	{
		final OLCandRow row = includedRowsByRowId.get(rowId);
		if (row == null)
		{
			throw new EntityNotFoundException("Row not found").setParameter("rowId", rowId);
		}
		return row;
	}

	private void setQtyToPurchase(@NonNull final BigDecimal qtyToPurchase)
	{
		Check.assume(qtyToPurchase.signum() >= 0, "qtyToPurchase shall be positive");
		this.qtyToPurchase = qtyToPurchase;
	}

	private BigDecimal getQtyToPurchase()
	{
		return qtyToPurchase;
	}

	private void updateQtyToPurchaseFromIncludedRows()
	{
		final BigDecimal qtyToPurchaseSum = getIncludedRows()
				.stream()
				.map(OLCandRow::getQtyToPurchase)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		setQtyToPurchase(qtyToPurchaseSum);
	}

	public void setDatePromised(@NonNull final Date datePromised)
	{
		this.datePromised = (Date)datePromised.clone();
	}

	private void assertRowTypeIsGroup(final String errorMsg)
	{
		if (getType() != OLCandRowType.GROUP)
		{
			throw new AdempiereException(errorMsg);
		}
	}

	public void changeQtyToPurchase(@NonNull final OLCandRowId rowId, @NonNull final BigDecimal qtyToPurchase)
	{
		assertRowTypeIsGroup("row not editable");

		final OLCandRow row = getIncludedRowById(rowId);
		row.setQtyToPurchase(qtyToPurchase);

		updateQtyToPurchaseFromIncludedRows();
	}

	public void changeDatePromised(@NonNull final OLCandRowId rowId, @NonNull final Date datePromised)
	{
		assertRowTypeIsGroup("row not editable");

		final OLCandRow row = getIncludedRowById(rowId);
		row.setDatePromised(datePromised);
	}
}
