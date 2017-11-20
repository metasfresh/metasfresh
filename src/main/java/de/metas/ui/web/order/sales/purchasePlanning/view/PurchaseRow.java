package de.metas.ui.web.order.sales.purchasePlanning.view;

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
public class PurchaseRow implements IViewRow
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

	@ViewColumn(captionKey = "C_UOM_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 30)
	})
	private final JSONLookupValue uom;

	@ViewColumn(captionKey = "QtyToDeliver", widgetType = DocumentFieldWidgetType.Quantity, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 40)
	})
	private final BigDecimal qtyToDeliver;

	public static final String FIELDNAME_QtyToPurchase = "qtyToPurchase";
	@ViewColumn(fieldName = FIELDNAME_QtyToPurchase, captionKey = "QtyToPurchase", widgetType = DocumentFieldWidgetType.Quantity, editor = ViewEditorRenderMode.ALWAYS, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 50)
	})
	private BigDecimal qtyToPurchase;

	public static final String FIELDNAME_DatePromised = "datePromised";
	@ViewColumn(fieldName = FIELDNAME_DatePromised, captionKey = "DatePromised", widgetType = DocumentFieldWidgetType.DateTime, editor = ViewEditorRenderMode.ALWAYS, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 60)
	})
	private Date datePromised;

	//
	private final PurchaseRowId rowId;
	private final int salesOrderId;
	private final IViewRowType rowType;
	private final ImmutableList<PurchaseRow> includedRows;
	private final ImmutableMap<PurchaseRowId, PurchaseRow> includedRowsByRowId;
	private final int purcaseCandidateRepoId;
	private final int orgId;
	private final int warehouseId;
	private final boolean readonly;

	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy
	private final ImmutableMap<String, ViewEditorRenderMode> viewEditorRenderModeByFieldName;

	private static final ImmutableMap<String, ViewEditorRenderMode> ViewEditorRenderModeByFieldName_ReadOnly = ImmutableMap.<String, ViewEditorRenderMode> builder()
			.put(FIELDNAME_QtyToPurchase, ViewEditorRenderMode.NEVER)
			.put(FIELDNAME_DatePromised, ViewEditorRenderMode.NEVER)
			.build();
	private static final ImmutableMap<String, ViewEditorRenderMode> ViewEditorRenderModeByFieldName_Inherit = ImmutableMap.of();

	@Builder
	private PurchaseRow(
			@NonNull final PurchaseRowId rowId,
			final int salesOrderId,
			@NonNull final IViewRowType rowType,
			@NonNull final JSONLookupValue product,
			@Nullable final JSONLookupValue vendorBPartner,
			@NonNull final JSONLookupValue uom,
			@Nullable final BigDecimal qtyToDeliver,
			@Nullable final BigDecimal qtyToPurchase,
			@Nullable final Date datePromised,
			@NonNull @Singular final ImmutableList<PurchaseRow> includedRows,
			final int purcaseCandidateRepoId,
			final int orgId,
			final int warehouseId,
			final boolean readonly)
	{
		Check.assume(salesOrderId > 0, "salesOrderId > 0");

		this.rowId = rowId;
		this.salesOrderId = salesOrderId;
		this.rowType = rowType;
		this.product = product;
		this.vendorBPartner = vendorBPartner;
		this.uom = uom;
		this.qtyToDeliver = qtyToDeliver;
		this.qtyToPurchase = qtyToPurchase != null ? qtyToPurchase : BigDecimal.ZERO;

		Check.assume(rowType == PurchaseRowType.GROUP || datePromised != null, "datePromised shall not be null");
		this.datePromised = datePromised;

		if (rowType == PurchaseRowType.LINE && !includedRows.isEmpty())
		{
			throw new AdempiereException("Lines does not allow included rows");
		}
		this.includedRows = includedRows;
		includedRowsByRowId = includedRows.stream().collect(ImmutableMap.toImmutableMap(PurchaseRow::getRowId, Function.identity()));

		this.purcaseCandidateRepoId = purcaseCandidateRepoId > 0 ? purcaseCandidateRepoId : -1;
		this.orgId = orgId;
		this.warehouseId = warehouseId;
		this.readonly = readonly;

		viewEditorRenderModeByFieldName = readonly ? ViewEditorRenderModeByFieldName_ReadOnly : ViewEditorRenderModeByFieldName_Inherit;

		//
		if (rowType == PurchaseRowType.GROUP)
		{
			updateQtyToPurchaseFromIncludedRows();
		}
	}

	private PurchaseRow(final PurchaseRow from)
	{
		this.rowId = from.rowId;
		this.salesOrderId = from.salesOrderId;
		this.rowType = from.rowType;
		this.product = from.product;
		this.vendorBPartner = from.vendorBPartner;
		this.uom = from.uom;
		this.qtyToDeliver = from.qtyToDeliver;
		this.qtyToPurchase = from.qtyToPurchase;
		this.datePromised = from.datePromised;
		this.includedRows = from.includedRows.stream().map(PurchaseRow::new).collect(ImmutableList.toImmutableList());
		includedRowsByRowId = this.includedRows.stream().collect(ImmutableMap.toImmutableMap(PurchaseRow::getRowId, Function.identity()));
		this.purcaseCandidateRepoId = from.purcaseCandidateRepoId;
		this.orgId = from.orgId;
		this.warehouseId = from.warehouseId;
		this.readonly = from.readonly;

		viewEditorRenderModeByFieldName = from.viewEditorRenderModeByFieldName;
		_fieldNameAndJsonValues = from._fieldNameAndJsonValues;
	}

	public PurchaseRow copy()
	{
		return new PurchaseRow(this);
	}

	public PurchaseRowId getRowId()
	{
		return rowId;
	}

	@Override
	public DocumentId getId()
	{
		return rowId.toDocumentId();
	}

	public int getSalesOrderId()
	{
		return salesOrderId;
	}

	public int getSalesOrderLineId()
	{
		return rowId.getSalesOrderLineId();
	}

	public int getPurcaseCandidateRepoId()
	{
		return purcaseCandidateRepoId;
	}

	@Override
	public IViewRowType getType()
	{
		return rowType;
	}

	@Override
	public boolean isProcessed()
	{
		return readonly;
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
	public Map<String, DocumentFieldWidgetType> getWidgetTypesByFieldName()
	{
		return ViewColumnHelper.getWidgetTypesByFieldName(PurchaseRow.class);
	}

	@Override
	public Map<String, ViewEditorRenderMode> getViewEditorRenderModeByFieldName()
	{
		return viewEditorRenderModeByFieldName;
	}

	private void resetFieldNameAndJsonValues()
	{
		_fieldNameAndJsonValues = null;
	}

	@Override
	public List<PurchaseRow> getIncludedRows()
	{
		return includedRows;
	}

	public PurchaseRow getIncludedRowById(final PurchaseRowId rowId)
	{
		final PurchaseRow row = includedRowsByRowId.get(rowId);
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
		resetFieldNameAndJsonValues();
	}

	public BigDecimal getQtyToPurchase()
	{
		return qtyToPurchase;
	}

	private void updateQtyToPurchaseFromIncludedRows()
	{
		final BigDecimal qtyToPurchaseSum = getIncludedRows()
				.stream()
				.map(PurchaseRow::getQtyToPurchase)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		setQtyToPurchase(qtyToPurchaseSum);
	}

	private void setDatePromised(@NonNull final Date datePromised)
	{
		this.datePromised = (Date)datePromised.clone();
		resetFieldNameAndJsonValues();
	}

	private void assertRowEditable()
	{
		if (readonly)
		{
			throw new AdempiereException("readonly");
		}
	}

	public void changeQtyToPurchase(@NonNull final PurchaseRowId rowId, @NonNull final BigDecimal qtyToPurchase)
	{
		assertRowEditable();

		final PurchaseRow row = getIncludedRowById(rowId);
		row.setQtyToPurchase(qtyToPurchase);

		updateQtyToPurchaseFromIncludedRows();
	}

	public void changeDatePromised(@NonNull final PurchaseRowId rowId, @NonNull final Date datePromised)
	{
		assertRowEditable();

		final PurchaseRow row = getIncludedRowById(rowId);
		row.setDatePromised(datePromised);
	}

	public int getProductId()
	{
		return product.getKeyAsInt();
	}

	public int getUOMId()
	{
		return uom.getKeyAsInt();
	}

	public int getVendorBPartnerId()
	{
		return vendorBPartner.getKeyAsInt();
	}

	public Date getDatePromised()
	{
		return datePromised;
	}

	public int getOrgId()
	{
		return orgId;
	}

	public int getWarehouseId()
	{
		return warehouseId;
	}
}
