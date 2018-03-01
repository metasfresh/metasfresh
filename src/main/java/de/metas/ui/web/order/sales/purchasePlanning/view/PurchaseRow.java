package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
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
import lombok.Getter;
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

	private static final Logger logger = LogManager.getLogger(PurchaseRow.class);

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
	@Getter
	private BigDecimal qtyToPurchase;

	public static final String FIELDNAME_PurchasedQty = "purchasedQty";
	@ViewColumn(fieldName = FIELDNAME_PurchasedQty, captionKey = "PurchasedQty", widgetType = DocumentFieldWidgetType.Quantity, editor = ViewEditorRenderMode.NEVER, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 45),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 45)
	})
	@Getter
	private BigDecimal purchasedQty;

	@ViewColumn(captionKey = "C_UOM_ID", widgetType = DocumentFieldWidgetType.Text, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 50)
	})
	private final String uomOrAvailablility;

	public static final String FIELDNAME_DatePromised = "datePromised";
	@ViewColumn(fieldName = FIELDNAME_DatePromised, captionKey = "DatePromised", widgetType = DocumentFieldWidgetType.DateTime, editor = ViewEditorRenderMode.ALWAYS, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 60)
	})
	private Date datePromised;

	//
	private final PurchaseRowId rowId;

	@Getter
	private final int salesOrderId;

	@Getter
	private final IViewRowType rowType;

	private ImmutableList<PurchaseRow> includedRows;

	@Getter
	private final int purchaseCandidateId;
	private final int orgId;
	private final int warehouseId;
	private final boolean readonly;

	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy
	private final ImmutableMap<String, ViewEditorRenderMode> viewEditorRenderModeByFieldName;

	private static final ImmutableMap<String, ViewEditorRenderMode> ViewEditorRenderModeByFieldName_ReadOnly = //
			ImmutableMap.<String, ViewEditorRenderMode> builder()
					.put(FIELDNAME_QtyToPurchase, ViewEditorRenderMode.NEVER)
					.put(FIELDNAME_DatePromised, ViewEditorRenderMode.NEVER)
					.build();
	private static final ImmutableMap<String, ViewEditorRenderMode> ViewEditorRenderModeByFieldName_Inherit = ImmutableMap.of();

	@Builder(toBuilder = true)
	private PurchaseRow(
			@NonNull final PurchaseRowId rowId,
			final int salesOrderId,
			@NonNull final IViewRowType rowType,
			@NonNull final JSONLookupValue product,
			@Nullable final JSONLookupValue vendorBPartner,
			@NonNull final String uomOrAvailablility,
			@Nullable final BigDecimal qtyToDeliver,
			@Nullable final BigDecimal qtyToPurchase,
			@Nullable final BigDecimal purchasedQty,
			@Nullable final Date datePromised,
			@Singular final ImmutableList<PurchaseRow> includedRows,
			final int purchaseCandidateId,
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
		this.uomOrAvailablility = uomOrAvailablility;
		this.qtyToDeliver = qtyToDeliver;
		this.qtyToPurchase = Util.coalesce(qtyToPurchase, BigDecimal.ZERO);
		this.purchasedQty = Util.coalesce(purchasedQty, BigDecimal.ZERO);

		Check.assume(rowType == PurchaseRowType.AVAILABILITY_DETAIL || datePromised != null, "datePromised shall not be null");
		this.datePromised = datePromised;

		if (rowType == PurchaseRowType.LINE && !includedRows.isEmpty())
		{
			throw new AdempiereException("Lines does not allow included rows");
		}

		setIncludedRows(includedRows);

		this.purchaseCandidateId = purchaseCandidateId > 0 ? purchaseCandidateId : -1;
		this.orgId = orgId;
		this.warehouseId = warehouseId;
		this.readonly = readonly;

		viewEditorRenderModeByFieldName = readonly //
				? ViewEditorRenderModeByFieldName_ReadOnly //
				: ViewEditorRenderModeByFieldName_Inherit;

		if (rowType == PurchaseRowType.GROUP)
		{
			updateQtysFromIncludedRows();
		}

		logger.trace("Created: {}, RO={} -- this={}", this.rowId, this.readonly, this);
	}

	private PurchaseRow(final PurchaseRow from)
	{
		this.rowId = from.rowId;
		this.salesOrderId = from.salesOrderId;
		this.rowType = from.rowType;
		this.product = from.product;
		this.vendorBPartner = from.vendorBPartner;
		this.uomOrAvailablility = from.uomOrAvailablility;
		this.qtyToDeliver = from.qtyToDeliver;
		this.qtyToPurchase = from.qtyToPurchase;
		this.purchasedQty = from.purchasedQty;
		this.datePromised = from.datePromised;

		setIncludedRows(from.includedRows.stream()
				.map(PurchaseRow::copy).collect(ImmutableList.toImmutableList()));

		this.purchaseCandidateId = from.purchaseCandidateId;
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

	public int getSalesOrderLineId()
	{
		return rowId.getSalesOrderLineId();
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

	public PurchaseRow getIncludedRowById(@NonNull final PurchaseRowId rowId)
	{
		final ImmutableMap<PurchaseRowId, PurchaseRow> includedRowsByRowId = this.includedRows.stream()
				.collect(ImmutableMap.toImmutableMap(PurchaseRow::getRowId, Function.identity()));

		final PurchaseRow row = includedRowsByRowId.get(rowId);
		if (row == null)
		{
			throw new EntityNotFoundException("Included row not found").appendParametersToMessage()
					.setParameter("rowId", rowId)
					.setParameter("this", this);
		}
		return row;
	}

	private void setQtyToPurchase(@NonNull final BigDecimal qtyToPurchase)
	{
		Check.assume(qtyToPurchase.signum() >= 0, "qtyToPurchase shall be positive");

		this.qtyToPurchase = qtyToPurchase;
		resetFieldNameAndJsonValues();
	}

	private void setPurchasedQty(@NonNull final BigDecimal purchasedQty)
	{
		Check.assume(qtyToPurchase.signum() >= 0, "purchasedQty shall be positive");

		this.purchasedQty = purchasedQty;
		resetFieldNameAndJsonValues();
	}

	private void updateQtysFromIncludedRows()
	{
		BigDecimal qtyToPurchaseSum = BigDecimal.ZERO;
		BigDecimal purchasedQtySum = BigDecimal.ZERO;
		for(final PurchaseRow includedRow: getIncludedRows())
		{
			qtyToPurchaseSum = qtyToPurchaseSum.add(includedRow.getQtyToPurchase());
			purchasedQtySum = purchasedQtySum.add(includedRow.getPurchasedQty());
		}

		this.setQtyToPurchase(qtyToPurchaseSum);
		this.setPurchasedQty(purchasedQtySum);
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
			throw new AdempiereException("readonly").setParameter("rowId", rowId);
		}
	}

	public void changeQtyToPurchase(
			@NonNull final PurchaseRowId rowId,
			@NonNull final BigDecimal qtyToPurchase)
	{
		Check.errorUnless(rowType == PurchaseRowType.GROUP, "The method changeQtyToPurchase() is only allowed for group rows; this={}", this);

		final PurchaseRow row = getIncludedRowById(rowId);
		row.assertRowEditable();
		row.setQtyToPurchase(qtyToPurchase);

		updateQtysFromIncludedRows();
	}

	public void changeDatePromised(
			@NonNull final PurchaseRowId rowId,
			@NonNull final Date datePromised)
	{
		Check.errorUnless(rowType == PurchaseRowType.GROUP,
				"The method changeDatePromisedOfIncludedRow() is only allowed for group rows; this={}", this);

		final PurchaseRow lineRow = getIncludedRowById(rowId);

		lineRow.assertRowEditable();
		lineRow.setDatePromised(datePromised);
	}

	public int getProductId()
	{
		return product.getKeyAsInt();
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

	public void setAvailabilityInfoRows(@NonNull final ImmutableList<PurchaseRow> availabilityResultRows)
	{
		Check.assume(rowType == PurchaseRowType.LINE,
				"The method changeQtyToPurchase() is only allowed for line rows; this={}", this);
		availabilityResultRows
				.forEach(availabilityResultRow -> Check.assume(
						availabilityResultRow.getRowType() == PurchaseRowType.AVAILABILITY_DETAIL,
						"The method changeQtyToPurchase() is only allowed for availability detail rows; this={} ", this));

		setIncludedRows(availabilityResultRows);
	}

	private void setIncludedRows(final ImmutableList<PurchaseRow> includedRows)
	{
		final ImmutableList<DocumentId> distinctRowIds = includedRows.stream().map(PurchaseRow::getId).distinct().collect(ImmutableList.toImmutableList());
		Check.errorIf(distinctRowIds.size() != includedRows.size(), "The given includedRows contain at least one duplicates rowId; includedRows={}", includedRows);

		this.includedRows = ImmutableList.copyOf(includedRows);
	}
}
