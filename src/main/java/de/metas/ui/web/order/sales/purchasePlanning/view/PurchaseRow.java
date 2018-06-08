package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.printing.esb.base.util.Check;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidatesGroup;
import de.metas.purchasecandidate.PurchaseDemand;
import de.metas.purchasecandidate.PurchaseDemandId;
import de.metas.purchasecandidate.availability.AvailabilityResult;
import de.metas.purchasecandidate.availability.AvailabilityResult.Type;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.quantity.Quantity;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.Builder;
import lombok.Getter;
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

public final class PurchaseRow implements IViewRow
{
	private static final Logger logger = LogManager.getLogger(PurchaseRow.class);

	@ViewColumn(captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 10)
	})
	private final LookupValue product;
	@ViewColumn(captionKey = "M_AttributeSetInstance_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 15),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 15)
	})
	private final LookupValue attributeSetInstance;

	@ViewColumn(captionKey = "Vendor_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 20)
	})
	private final LookupValue vendorBPartner;

	@ViewColumn(captionKey = I_C_PurchaseCandidate.COLUMNNAME_CustomerPriceGrossProfit, widgetType = DocumentFieldWidgetType.Amount, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 23),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 23)
	})
	private final BigDecimal salesNetPrice;

	@ViewColumn(captionKey = I_C_PurchaseCandidate.COLUMNNAME_PurchasePriceActual, widgetType = DocumentFieldWidgetType.Amount, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 25),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 25)
	})
	private final BigDecimal purchaseNetPrice;

	@ViewColumn(captionKey = "PercentGrossProfit", widgetType = DocumentFieldWidgetType.Amount, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 25),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 25)
	})
	private final BigDecimal profitPercent;

	@ViewColumn(captionKey = "Qty_AvailableToPromise", widgetType = DocumentFieldWidgetType.Quantity, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 30)
	})
	private final Quantity qtyAvailableToPromise;

	@ViewColumn(captionKey = "QtyToDeliver", widgetType = DocumentFieldWidgetType.Quantity, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 40)
	})
	private final Quantity qtyToDeliver;

	public static final String FIELDNAME_QtyToPurchase = "qtyToPurchase";
	@ViewColumn(fieldName = FIELDNAME_QtyToPurchase, captionKey = "QtyToPurchase", widgetType = DocumentFieldWidgetType.Quantity, editor = ViewEditorRenderMode.ALWAYS, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 50)
	})
	@Getter
	private Quantity qtyToPurchase;

	public static final String FIELDNAME_PurchasedQty = "purchasedQty";
	@ViewColumn(fieldName = FIELDNAME_PurchasedQty, captionKey = "PurchasedQty", widgetType = DocumentFieldWidgetType.Quantity, editor = ViewEditorRenderMode.NEVER, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 55),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 55)
	})
	@Getter
	private Quantity purchasedQty;

	@ViewColumn(captionKey = "C_UOM_ID", widgetType = DocumentFieldWidgetType.Text, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 60)
	})
	private final String uomOrAvailablility;

	public static final String FIELDNAME_DatePromised = "datePromised";
	@ViewColumn(fieldName = FIELDNAME_DatePromised, captionKey = "DatePromised", widgetType = DocumentFieldWidgetType.DateTime, editor = ViewEditorRenderMode.ALWAYS, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 70)
	})
	@Getter
	private LocalDateTime datePromised;

	//
	private final PurchaseRowId rowId;

	private ImmutableMap<PurchaseRowId, PurchaseRow> _includedRowsById = ImmutableMap.of();

	private final boolean readonly;
	@Getter
	private final PurchaseCandidatesGroup purchaseCandidatesGroup;

	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy

	private static final ImmutableMap<String, ViewEditorRenderMode> ViewEditorRenderModeByFieldName_ReadOnly = //
			ImmutableMap.<String, ViewEditorRenderMode> builder()
					.put(FIELDNAME_QtyToPurchase, ViewEditorRenderMode.NEVER)
					.put(FIELDNAME_DatePromised, ViewEditorRenderMode.NEVER)
					.build();
	private static final ImmutableMap<String, ViewEditorRenderMode> ViewEditorRenderModeByFieldName_Inherit = ImmutableMap.of();

	@Builder(builderMethodName = "groupRowBuilder", builderClassName = "GroupRowBuilder")
	private PurchaseRow(
			@NonNull final PurchaseDemand demand,
			@NonNull final Quantity qtyAvailableToPromise,
			@NonNull final List<PurchaseRow> includedRows,
			//
			@NonNull final PurchaseRowLookups lookups)
	{
		this.rowId = PurchaseRowId.groupId(demand.getId());

		this.product = lookups.createProductLookupValue(demand.getProductId());
		this.attributeSetInstance = lookups.createASILookupValue(demand.getAttributeSetInstanceId());

		this.vendorBPartner = null;

		final Quantity qtyToDeliver = demand.getQtyToDeliver();
		this.uomOrAvailablility = qtyToDeliver.getUOMSymbol();
		this.qtyAvailableToPromise = qtyAvailableToPromise;
		this.qtyToDeliver = qtyToDeliver;
		this.qtyToPurchase = null;
		this.purchasedQty = null;

		this.purchaseCandidatesGroup = null;
		this.salesNetPrice = null;
		this.purchaseNetPrice = null;
		this.profitPercent = null;

		this.datePromised = demand.getSalesDatePromised();

		this.readonly = true;

		setIncludedRows(ImmutableList.copyOf(includedRows));
		updateQtysFromIncludedRows();

		logger.trace("Group row created: {}, RO={} -- this={}", this.rowId, this.readonly, this);
	}

	@Builder(builderMethodName = "lineRowBuilder", builderClassName = "LineRowBuilder")
	private PurchaseRow(
			@NonNull final PurchaseCandidatesGroup purchaseCandidatesGroup,
			@NonNull final PurchaseRowLookups lookups)
	{
		final BPartnerId vendorId = purchaseCandidatesGroup.getVendorId();
		final ProductId productId = purchaseCandidatesGroup.getProductId();

		this.rowId = PurchaseRowId.lineId(purchaseCandidatesGroup.getDemandId(), vendorId);

		this.product = lookups.createProductLookupValue(productId);
		this.attributeSetInstance = null;

		this.vendorBPartner = lookups.createBPartnerLookupValue(vendorId);

		final Quantity qtyToPurchase = purchaseCandidatesGroup.getQtyToPurchase();
		this.uomOrAvailablility = lookups.createUOMLookupValue(qtyToPurchase.getUOM());
		this.qtyAvailableToPromise = null;
		this.qtyToDeliver = null;
		this.qtyToPurchase = qtyToPurchase;
		this.purchasedQty = purchaseCandidatesGroup.getPurchasedQty();

		this.purchaseCandidatesGroup = purchaseCandidatesGroup;

		final PurchaseProfitInfo profitInfo = purchaseCandidatesGroup.getProfitInfo();
		if (profitInfo != null)
		{
			this.salesNetPrice = profitInfo.getSalesNetPrice()
					.map(Money::getValue)
					.orElse(null);
			this.purchaseNetPrice = profitInfo.getPurchaseNetPrice().getValue();
			this.profitPercent = profitInfo.getProfitPercent()
					.map(percent -> percent.roundToHalf(RoundingMode.HALF_UP).getValueAsBigDecimal())
					.orElse(null);
		}
		else
		{
			this.salesNetPrice = null;
			this.purchaseNetPrice = null;
			this.profitPercent = null;
		}

		this.datePromised = purchaseCandidatesGroup.getPurchaseDatePromised();

		this.readonly = purchaseCandidatesGroup.isReadonly();

		logger.trace("Line row created: {}, RO={} -- this={}", this.rowId, this.readonly, this);
	}

	@Builder(builderMethodName = "availabilityDetailSuccessBuilder", builderClassName = "availabilityDetailSuccessBuilder")
	private PurchaseRow(
			@NonNull final AvailabilityResult availabilityResult,
			@NonNull final PurchaseRow lineRow)
	{
		final String availabilityText = !Check.isEmpty(availabilityResult.getAvailabilityText(), true)
				? availabilityResult.getAvailabilityText()
				: availabilityResult.getType().translate();

		this.rowId = lineRow.getRowId().withAvailabilityAndRandomDistinguisher(availabilityResult.getType());

		this.product = lineRow.product;
		this.attributeSetInstance = lineRow.attributeSetInstance;

		this.vendorBPartner = null;

		this.uomOrAvailablility = availabilityText;
		this.qtyAvailableToPromise = null;
		this.qtyToDeliver = null;
		this.qtyToPurchase = availabilityResult.getQty();
		this.purchasedQty = null;

		this.purchaseCandidatesGroup = null;
		this.salesNetPrice = null;
		this.purchaseNetPrice = null;
		this.profitPercent = null;

		this.datePromised = availabilityResult.getDatePromised();

		this.readonly = true;

		logger.trace("Availability success row created: {}, RO={} -- this={}", this.rowId, this.readonly, this);
	}

	@Builder(builderMethodName = "availabilityDetailErrorBuilder", builderClassName = "availabilityDetailErrorBuilder")
	private PurchaseRow(
			@NonNull final Throwable throwable,
			@NonNull final PurchaseRow lineRow)
	{
		this.rowId = lineRow.getRowId().withAvailabilityAndRandomDistinguisher(Type.NOT_AVAILABLE);

		this.product = lineRow.product;
		this.attributeSetInstance = lineRow.attributeSetInstance;

		this.vendorBPartner = null;

		this.uomOrAvailablility = AdempiereException.extractMessage(throwable);
		this.qtyAvailableToPromise = null;
		this.qtyToDeliver = null;
		this.qtyToPurchase = null;
		this.purchasedQty = null;

		this.purchaseCandidatesGroup = null;
		this.salesNetPrice = null;
		this.purchaseNetPrice = null;
		this.profitPercent = null;

		this.datePromised = null;

		this.readonly = true;

		logger.trace("Availability success row created: {}, RO={} -- this={}", this.rowId, this.readonly, this);
	}

	private PurchaseRow(@NonNull final PurchaseRow from)
	{
		this.rowId = from.rowId;
		this.product = from.product;
		this.attributeSetInstance = from.attributeSetInstance;
		this.vendorBPartner = from.vendorBPartner;
		this.qtyAvailableToPromise = from.qtyAvailableToPromise;
		this.uomOrAvailablility = from.uomOrAvailablility;

		this.purchaseCandidatesGroup = from.purchaseCandidatesGroup;
		this.salesNetPrice = from.salesNetPrice;
		this.purchaseNetPrice = from.purchaseNetPrice;
		this.profitPercent = from.profitPercent;

		this.qtyToDeliver = from.qtyToDeliver;
		this.qtyToPurchase = from.qtyToPurchase;
		this.purchasedQty = from.purchasedQty;
		this.datePromised = from.datePromised;

		setIncludedRows(from.getIncludedRows().stream()
				.map(PurchaseRow::copy)
				.collect(ImmutableList.toImmutableList()));

		this.readonly = from.readonly;

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

	public PurchaseDemandId getPurchaseDemandId()
	{
		return rowId.getPurchaseDemandId();
	}

	@Override
	public PurchaseRowType getType()
	{
		return rowId.getType();
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
		return readonly //
				? ViewEditorRenderModeByFieldName_ReadOnly //
				: ViewEditorRenderModeByFieldName_Inherit;
	}

	private void resetFieldNameAndJsonValues()
	{
		_fieldNameAndJsonValues = null;
	}

	@Override
	public Collection<PurchaseRow> getIncludedRows()
	{
		return _includedRowsById.values();
	}

	private void setIncludedRows(@NonNull final ImmutableList<PurchaseRow> includedRows)
	{
		this._includedRowsById = Maps.uniqueIndex(includedRows, PurchaseRow::getRowId);
	}

	public PurchaseRow getIncludedRowById(@NonNull final PurchaseRowId rowId)
	{
		final PurchaseRow includedRow = _includedRowsById.get(rowId);
		if (includedRow == null)
		{
			throw new EntityNotFoundException("Included row not found")
					.appendParametersToMessage()
					.setParameter("rowId", rowId)
					.setParameter("this", this);
		}
		return includedRow;
	}

	private void setQtyToPurchase(final Quantity qtyToPurchase)
	{
		Check.assume(qtyToPurchase == null || qtyToPurchase.signum() >= 0, "qtyToPurchase shall be positive");

		if (Objects.equals(this.qtyToPurchase, qtyToPurchase))
		{
			return;
		}

		this.qtyToPurchase = qtyToPurchase;
		resetFieldNameAndJsonValues();
	}

	private void setPurchasedQty(final Quantity purchasedQty)
	{
		Check.assume(purchasedQty == null || purchasedQty.signum() >= 0, "purchasedQty shall be positive");

		if (Objects.equals(this.purchasedQty, purchasedQty))
		{
			return;
		}

		this.purchasedQty = purchasedQty;
		resetFieldNameAndJsonValues();
	}

	private void updateQtysFromIncludedRows()
	{
		Quantity qtyToPurchaseSum = null;
		Quantity purchasedQtySum = null;
		for (final PurchaseRow includedRow : getIncludedRows())
		{
			qtyToPurchaseSum = Quantity.addNullables(qtyToPurchaseSum, includedRow.getQtyToPurchase());
			purchasedQtySum = Quantity.addNullables(purchasedQtySum, includedRow.getPurchasedQty());
		}

		this.setQtyToPurchase(qtyToPurchaseSum);
		this.setPurchasedQty(purchasedQtySum);
	}

	private void setDatePromised(@NonNull final LocalDateTime datePromised)
	{
		if (Objects.equals(this.datePromised, datePromised))
		{
			return;
		}

		this.datePromised = datePromised;
		resetFieldNameAndJsonValues();
	}

	private void assertRowEditable()
	{
		if (readonly)
		{
			throw new AdempiereException("readonly").setParameter("rowId", rowId);
		}
	}

	public void assertRowType(@NonNull final PurchaseRowType expectedRowType)
	{
		final PurchaseRowType rowType = getType();
		if (rowType != expectedRowType)
		{
			throw new AdempiereException("Expected " + expectedRowType + " but it was " + rowType + ": " + this);
		}

	}

	void changeIncludedRow(@NonNull final PurchaseRowId includedRowId, @NonNull final PurchaseRowChangeRequest request)
	{
		assertRowType(PurchaseRowType.GROUP);

		final PurchaseRow includedRow = getIncludedRowById(includedRowId);
		includedRow.changeRow(request);

		updateQtysFromIncludedRows();
	}

	private void changeRow(@NonNull final PurchaseRowChangeRequest request)
	{
		assertRowType(PurchaseRowType.LINE);
		assertRowEditable();

		if (request.getQtyToPurchase() != null)
		{
			final Quantity qtyToPurchase = request.getQtyToPurchase();
			setQtyToPurchase(qtyToPurchase);
		}
		else if (request.getQtyToPurchaseWithoutUOM() != null)
		{
			final BigDecimal qtyToPurchase = request.getQtyToPurchaseWithoutUOM();
			final I_C_UOM uom = getQtyToPurchase().getUOM();
			setQtyToPurchase(Quantity.of(qtyToPurchase, uom));
		}

		if (request.getPurchaseDatePromised() != null)
		{
			setDatePromised(request.getPurchaseDatePromised());
		}
	}

	public void setAvailabilityInfoRow(@NonNull PurchaseRow availabilityResultRow)
	{
		setAvailabilityInfoRows(ImmutableList.of(availabilityResultRow));
	}

	public void setAvailabilityInfoRows(@NonNull final List<PurchaseRow> availabilityResultRows)
	{
		assertRowType(PurchaseRowType.LINE);
		availabilityResultRows.forEach(availabilityResultRow -> availabilityResultRow.assertRowType(PurchaseRowType.AVAILABILITY_DETAIL));

		setIncludedRows(ImmutableList.copyOf(availabilityResultRows));
	}
}
