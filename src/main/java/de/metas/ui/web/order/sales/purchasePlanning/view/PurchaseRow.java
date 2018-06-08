package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.money.Money;
import de.metas.printing.esb.base.util.Check;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidatesGroup;
import de.metas.purchasecandidate.PurchaseCandidatesGroup.PurchaseCandidatesGroupBuilder;
import de.metas.purchasecandidate.PurchaseDemand;
import de.metas.purchasecandidate.PurchaseDemandId;
import de.metas.purchasecandidate.availability.AvailabilityResult;
import de.metas.purchasecandidate.availability.AvailabilityResult.Type;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoRequest;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoService;
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
	// services
	@Nullable
	private final PurchaseProfitInfoService purchaseProfitInfoService;
	@Nullable
	private final PurchaseRowLookups lookups;

	//
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
	private BigDecimal salesNetPrice;

	@ViewColumn(captionKey = I_C_PurchaseCandidate.COLUMNNAME_PurchasePriceActual, widgetType = DocumentFieldWidgetType.Amount, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 25),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 25)
	})
	private BigDecimal purchaseNetPrice;

	@ViewColumn(captionKey = "PercentGrossProfit", widgetType = DocumentFieldWidgetType.Amount, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 25),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 25)
	})
	private BigDecimal profitPercent;

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
	private String uomOrAvailablility;

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
	private PurchaseCandidatesGroup purchaseCandidatesGroup;

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
		purchaseProfitInfoService = null; // not needed
		this.lookups = lookups;

		rowId = PurchaseRowId.groupId(demand.getId());

		product = lookups.createProductLookupValue(demand.getProductId());
		attributeSetInstance = lookups.createASILookupValue(demand.getAttributeSetInstanceId());

		vendorBPartner = null;

		final Quantity qtyToDeliver = demand.getQtyToDeliver();
		uomOrAvailablility = qtyToDeliver.getUOMSymbol();
		this.qtyAvailableToPromise = qtyAvailableToPromise;
		this.qtyToDeliver = qtyToDeliver;
		qtyToPurchase = null;
		purchasedQty = null;

		purchaseCandidatesGroup = null;
		salesNetPrice = null;
		purchaseNetPrice = null;
		profitPercent = null;

		datePromised = demand.getSalesDatePromised();

		readonly = true;

		setIncludedRows(ImmutableList.copyOf(includedRows));
		updateQtysFromIncludedRows();
	}

	@Builder(builderMethodName = "lineRowBuilder", builderClassName = "LineRowBuilder")
	private PurchaseRow(
			@NonNull final PurchaseCandidatesGroup purchaseCandidatesGroup,
			//
			@NonNull final PurchaseRowLookups lookups,
			@NonNull final PurchaseProfitInfoService purchaseProfitInfoService)
	{
		this.purchaseProfitInfoService = purchaseProfitInfoService;
		this.lookups = lookups;

		final BPartnerId vendorId = purchaseCandidatesGroup.getVendorId();
		final ProductId productId = purchaseCandidatesGroup.getProductId();

		rowId = PurchaseRowId.lineId(purchaseCandidatesGroup.getDemandId(), vendorId);

		product = lookups.createProductLookupValue(productId);
		attributeSetInstance = null;

		vendorBPartner = lookups.createBPartnerLookupValue(vendorId);

		qtyAvailableToPromise = null;
		qtyToDeliver = null;

		readonly = false;

		// Keep it last (like all setters called from ctor)
		setPurchaseCandidatesGroup(purchaseCandidatesGroup);
	}

	@Builder(builderMethodName = "availabilityDetailSuccessBuilder", builderClassName = "availabilityDetailSuccessBuilder")
	private PurchaseRow(
			@NonNull final AvailabilityResult availabilityResult,
			@NonNull final PurchaseRow lineRow)
	{
		purchaseProfitInfoService = null; // not needed
		lookups = null; // not needed

		rowId = lineRow.getRowId().withAvailabilityAndRandomDistinguisher(availabilityResult.getType());

		product = lineRow.product;
		attributeSetInstance = lineRow.attributeSetInstance;

		vendorBPartner = null;

		final String availabilityText = !Check.isEmpty(availabilityResult.getAvailabilityText(), true)
				? availabilityResult.getAvailabilityText()
				: availabilityResult.getType().translate();
		uomOrAvailablility = availabilityText;
		qtyAvailableToPromise = null;
		qtyToDeliver = null;
		qtyToPurchase = availabilityResult.getQty();
		purchasedQty = null;

		purchaseCandidatesGroup = null;
		salesNetPrice = null;
		purchaseNetPrice = null;
		profitPercent = null;

		datePromised = availabilityResult.getDatePromised();

		readonly = true;
	}

	@Builder(builderMethodName = "availabilityDetailErrorBuilder", builderClassName = "availabilityDetailErrorBuilder")
	private PurchaseRow(
			@NonNull final Throwable throwable,
			@NonNull final PurchaseRow lineRow)
	{
		purchaseProfitInfoService = null; // not needed
		lookups = null; // not needed

		rowId = lineRow.getRowId().withAvailabilityAndRandomDistinguisher(Type.NOT_AVAILABLE);

		product = lineRow.product;
		attributeSetInstance = lineRow.attributeSetInstance;

		vendorBPartner = null;

		uomOrAvailablility = AdempiereException.extractMessage(throwable);
		qtyAvailableToPromise = null;
		qtyToDeliver = null;
		qtyToPurchase = null;
		purchasedQty = null;

		purchaseCandidatesGroup = null;
		salesNetPrice = null;
		purchaseNetPrice = null;
		profitPercent = null;

		datePromised = null;

		readonly = true;
	}

	private PurchaseRow(@NonNull final PurchaseRow from)
	{
		purchaseProfitInfoService = from.purchaseProfitInfoService;
		lookups = from.lookups;

		rowId = from.rowId;
		product = from.product;
		attributeSetInstance = from.attributeSetInstance;
		vendorBPartner = from.vendorBPartner;
		qtyAvailableToPromise = from.qtyAvailableToPromise;
		uomOrAvailablility = from.uomOrAvailablility;

		purchaseCandidatesGroup = from.purchaseCandidatesGroup;
		salesNetPrice = from.salesNetPrice;
		purchaseNetPrice = from.purchaseNetPrice;
		profitPercent = from.profitPercent;

		qtyToDeliver = from.qtyToDeliver;
		qtyToPurchase = from.qtyToPurchase;
		purchasedQty = from.purchasedQty;
		datePromised = from.datePromised;

		setIncludedRows(from.getIncludedRows().stream()
				.map(PurchaseRow::copy)
				.collect(ImmutableList.toImmutableList()));

		readonly = from.readonly;

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
		_includedRowsById = Maps.uniqueIndex(includedRows, PurchaseRow::getRowId);
	}

	PurchaseRow getIncludedRowById(@NonNull final PurchaseRowId rowId)
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

	private I_C_UOM getCurrentUOM()
	{
		return getQtyToPurchase().getUOM();
	}

	private void setPurchaseCandidatesGroup(@NonNull final PurchaseCandidatesGroup purchaseCandidatesGroup)
	{
		if (Objects.equals(this.purchaseCandidatesGroup, purchaseCandidatesGroup))
		{
			return;
		}

		// NOTE: we assume purchaseCandidatesGroup's vendor and product wasn't changed!

		this.purchaseCandidatesGroup = purchaseCandidatesGroup;
		setQtyToPurchase(purchaseCandidatesGroup.getQtyToPurchase());
		setPurchasedQty(purchaseCandidatesGroup.getPurchasedQty());
		setProfitInfo(purchaseCandidatesGroup.getProfitInfo());
		setDatePromised(purchaseCandidatesGroup.getPurchaseDatePromised());
	}

	private void setQtyToPurchase(final Quantity qtyToPurchase)
	{
		if (Objects.equals(this.qtyToPurchase, qtyToPurchase))
		{
			return;
		}

		this.qtyToPurchase = qtyToPurchase;
		uomOrAvailablility = qtyToPurchase != null ? lookups.createUOMLookupValue(qtyToPurchase.getUOM()) : null;

		resetFieldNameAndJsonValues();
	}

	private void setPurchasedQty(final Quantity purchasedQty)
	{
		if (Objects.equals(this.purchasedQty, purchasedQty))
		{
			return;
		}

		this.purchasedQty = purchasedQty;
		resetFieldNameAndJsonValues();
	}

	private void setDatePromised(final LocalDateTime datePromised)
	{
		if (Objects.equals(this.datePromised, datePromised))
		{
			return;
		}

		this.datePromised = datePromised;
		resetFieldNameAndJsonValues();
	}

	private void setProfitInfo(final PurchaseProfitInfo profitInfo)
	{
		if (profitInfo != null)
		{
			salesNetPrice = profitInfo.getSalesNetPrice()
					.map(Money::getValue)
					.orElse(null);
			purchaseNetPrice = profitInfo.getPurchaseNetPrice().getValue();
			profitPercent = profitInfo.getProfitPercent()
					.map(percent -> percent.roundToHalf(RoundingMode.HALF_UP).getValueAsBigDecimal())
					.orElse(null);
		}
		else
		{
			salesNetPrice = null;
			purchaseNetPrice = null;
			profitPercent = null;
		}

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

		setQtyToPurchase(qtyToPurchaseSum);
		setPurchasedQty(purchasedQtySum);
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

		final PurchaseRow lineRow = getIncludedRowById(includedRowId);
		lineRow.changeRow(request);

		updateQtysFromIncludedRows();
	}

	private void changeRow(@NonNull final PurchaseRowChangeRequest request)
	{
		assertRowType(PurchaseRowType.LINE);
		assertRowEditable();

		//
		final PurchaseCandidatesGroup candidatesGroup = getPurchaseCandidatesGroup();
		final PurchaseCandidatesGroupBuilder newCandidatesGroup = candidatesGroup.toBuilder();
		boolean hasChanges = false;

		//
		// QtyToPurchase
		final Quantity qtyToPurchase = request.getQtyToPurchase(this::getCurrentUOM);
		boolean qtyToPurchaseChanged = false;
		if (qtyToPurchase != null
				&& !Objects.equals(candidatesGroup.getQtyToPurchase(), qtyToPurchase))
		{
			newCandidatesGroup.qtyToPurchase(qtyToPurchase);
			qtyToPurchaseChanged = true;
			hasChanges = true;
		}

		//
		// PurchaseDatePromised
		final LocalDateTime purchaseDatePromised = request.getPurchaseDatePromised();
		if (purchaseDatePromised != null)
		{
			newCandidatesGroup.purchaseDatePromised(purchaseDatePromised);
			hasChanges = true;
		}

		//
		// Recompute Profit Info
		if (qtyToPurchaseChanged)
		{
			final PurchaseProfitInfo profitInfo = purchaseProfitInfoService.calculateNoFail(PurchaseProfitInfoRequest.builder()
					.salesOrderAndLineIds(candidatesGroup.getSalesOrderAndLineIds())
					.qtyToPurchase(qtyToPurchase)
					.vendorProductInfo(candidatesGroup.getVendorProductInfo())
					.build());
			newCandidatesGroup.profitInfo(profitInfo);
			hasChanges = true;
		}

		//
		// Stop here if there were no changes
		if (!hasChanges)
		{
			return;
		}

		//
		setPurchaseCandidatesGroup(newCandidatesGroup.build());
	}

	public void setAvailabilityInfoRow(@NonNull final PurchaseRow availabilityResultRow)
	{
		setAvailabilityInfoRows(ImmutableList.of(availabilityResultRow));
	}

	public void setAvailabilityInfoRows(@NonNull final List<PurchaseRow> availabilityResultRows)
	{
		assertRowType(PurchaseRowType.LINE);
		availabilityResultRows.forEach(availabilityResultRow -> availabilityResultRow.assertRowType(PurchaseRowType.AVAILABILITY_DETAIL));

		setIncludedRows(ImmutableList.copyOf(availabilityResultRows));
	}

	public PurchaseProfitInfo getProfitInfo()
	{
		final PurchaseCandidatesGroup candidatesGroup = getPurchaseCandidatesGroup();
		return candidatesGroup != null ? candidatesGroup.getProfitInfo() : null;
	}
}
