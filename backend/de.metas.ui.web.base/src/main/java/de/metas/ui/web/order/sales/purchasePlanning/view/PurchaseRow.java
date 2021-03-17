package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.bpartner.BPartnerId;
import de.metas.money.Money;
import de.metas.printing.esb.base.util.Check;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.PurchaseCandidatesGroup;
import de.metas.purchasecandidate.PurchaseCandidatesGroup.PurchaseCandidatesGroupBuilder;
import de.metas.purchasecandidate.PurchaseDemand;
import de.metas.purchasecandidate.availability.AvailabilityResult;
import de.metas.purchasecandidate.availability.AvailabilityResult.Type;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoRequest;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoService;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.quantity.Quantity;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
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

@ToString(doNotUseGetters = true)
public final class PurchaseRow implements IViewRow
{
	// services
	@Nullable
	private final PurchaseProfitInfoService purchaseProfitInfoService;
	@Nullable
	private final PurchaseRowLookups lookups;

	public static final String FIELDNAME_QtyToPurchase = "qtyToPurchase";
	public static final String FIELDNAME_PurchasedQty = "purchasedQty";
	public static final String FIELDNAME_DatePromised = "datePromised";

	//
	@ViewColumn(captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup, seqNo = 10)
	private final LookupValue product;

	/** TODO: show it if/when it's needed and QAed */
	// @ViewColumn(captionKey = "M_AttributeSetInstance_ID", widgetType = DocumentFieldWidgetType.Lookup, seqNo = 15)
	private final LookupValue attributeSetInstance;

	@ViewColumn(captionKey = "Vendor_ID", widgetType = DocumentFieldWidgetType.Lookup, seqNo = 20)
	private final LookupValue vendorBPartner;

	@ViewColumn(captionKey = I_C_PurchaseCandidate.COLUMNNAME_ProfitSalesPriceActual, widgetType = DocumentFieldWidgetType.Amount, seqNo = 23)
	private Money profitSalesPriceActual;

	@ViewColumn(captionKey = I_C_PurchaseCandidate.COLUMNNAME_ProfitPurchasePriceActual, widgetType = DocumentFieldWidgetType.Amount, seqNo = 25)
	private Money profitPurchasePriceActual;

	@ViewColumn(captionKey = "PercentGrossProfit", widgetType = DocumentFieldWidgetType.Amount, seqNo = 25)
	private BigDecimal profitPercent;

	@ViewColumn(captionKey = "Qty_AvailableToPromise", widgetType = DocumentFieldWidgetType.Quantity, seqNo = 30)
	private final Quantity qtyAvailableToPromise;

	@ViewColumn(captionKey = "QtyToDeliver", widgetType = DocumentFieldWidgetType.Quantity, seqNo = 40)
	private final Quantity qtyToDeliver;

	@ViewColumn(fieldName = FIELDNAME_QtyToPurchase, captionKey = "QtyToPurchase", widgetType = DocumentFieldWidgetType.Quantity, seqNo = 50)
	@Getter
	private Quantity qtyToPurchase;

	@ViewColumn(fieldName = FIELDNAME_PurchasedQty, captionKey = "PurchasedQty", widgetType = DocumentFieldWidgetType.Quantity, seqNo = 55)
	@Getter(AccessLevel.PRIVATE)
	private Quantity purchasedQty;

	@ViewColumn(captionKey = "C_UOM_ID", widgetType = DocumentFieldWidgetType.Text, seqNo = 60)
	private String uomOrAvailablility;

	@ViewColumn(fieldName = FIELDNAME_DatePromised, captionKey = "DatePromised", widgetType = DocumentFieldWidgetType.ZonedDateTime, seqNo = 70)
	@Getter
	private ZonedDateTime datePromised;

	//
	private final PurchaseRowId rowId;

	private ImmutableMap<PurchaseRowId, PurchaseRow> _includedRowsById = ImmutableMap.of();

	private final boolean readonly;

	@Getter(AccessLevel.PRIVATE)
	private PurchaseCandidatesGroup purchaseCandidatesGroup;

	private final ViewRowFieldNameAndJsonValuesHolder<PurchaseRow> values;

	private static final ImmutableMap<String, ViewEditorRenderMode> ViewEditorRenderModeByFieldName_ReadOnly = //
			ImmutableMap.<String, ViewEditorRenderMode> builder()
					.put(FIELDNAME_QtyToPurchase, ViewEditorRenderMode.NEVER)
					.put(FIELDNAME_DatePromised, ViewEditorRenderMode.NEVER)
					.build();
	private static final ImmutableMap<String, ViewEditorRenderMode> ViewEditorRenderModeByFieldName_Editable = //
			ImmutableMap.<String, ViewEditorRenderMode> builder()
					.put(FIELDNAME_QtyToPurchase, ViewEditorRenderMode.ALWAYS)
					.put(FIELDNAME_DatePromised, ViewEditorRenderMode.ALWAYS)
					.build();

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
		profitSalesPriceActual = null;
		profitPurchasePriceActual = null;
		profitPercent = null;

		datePromised = demand.getSalesPreparationDate();

		readonly = true;

		values = createViewRowFieldNameAndJsonValuesHolder(readonly);

		setIncludedRows(ImmutableList.copyOf(includedRows));
		updateQtysFromIncludedRows();
	}

	private static ViewRowFieldNameAndJsonValuesHolder<PurchaseRow> createViewRowFieldNameAndJsonValuesHolder(final boolean readonly)
	{
		return ViewRowFieldNameAndJsonValuesHolder.builder(PurchaseRow.class)
				.widgetTypesByFieldName(ViewColumnHelper.getWidgetTypesByFieldName(PurchaseRow.class))
				.viewEditorRenderModeByFieldName(readonly ? ViewEditorRenderModeByFieldName_ReadOnly : ViewEditorRenderModeByFieldName_Editable)
				.build();
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

		readonly = purchaseCandidatesGroup.isReadonly();
		rowId = PurchaseRowId.lineId(purchaseCandidatesGroup.getPurchaseDemandId(), vendorId, readonly);

		product = lookups.createProductLookupValue(productId);
		attributeSetInstance = null;

		vendorBPartner = lookups.createBPartnerLookupValue(vendorId);

		qtyAvailableToPromise = null;
		qtyToDeliver = null;

		values = createViewRowFieldNameAndJsonValuesHolder(readonly);

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
		profitSalesPriceActual = null;
		profitPurchasePriceActual = null;
		profitPercent = null;

		datePromised = availabilityResult.getDatePromised();

		readonly = true;

		values = createViewRowFieldNameAndJsonValuesHolder(readonly);
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
		profitSalesPriceActual = null;
		profitPurchasePriceActual = null;
		profitPercent = null;

		datePromised = null;

		readonly = true;

		values = createViewRowFieldNameAndJsonValuesHolder(readonly);
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
		profitSalesPriceActual = from.profitSalesPriceActual;
		profitPurchasePriceActual = from.profitPurchasePriceActual;
		profitPercent = from.profitPercent;

		qtyToDeliver = from.qtyToDeliver;
		qtyToPurchase = from.qtyToPurchase;
		purchasedQty = from.purchasedQty;
		datePromised = from.datePromised;

		setIncludedRows(from.getIncludedRows().stream()
				.map(PurchaseRow::copy)
				.collect(ImmutableList.toImmutableList()));

		readonly = from.readonly;

		values = from.values.copy();
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
		return getRowId().toDocumentId();
	}

	public List<DemandGroupReference> getDemandGroupReferences()
	{
		Check.assume(PurchaseRowType.LINE.equals(getType()), "only 'line'-type rows have demandGroupReferences; this={}", this);
		return purchaseCandidatesGroup.getDemandGroupReferences();
	}

	@Override
	public PurchaseRowType getType()
	{
		return getRowId().getType();
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
	public ImmutableSet<String> getFieldNames()
	{
		return values.getFieldNames();
	}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
	{
		return values.get(this);
	}

	@Override
	public Map<String, DocumentFieldWidgetType> getWidgetTypesByFieldName()
	{
		return values.getWidgetTypesByFieldName();
	}

	@Override
	public Map<String, ViewEditorRenderMode> getViewEditorRenderModeByFieldName()
	{
		return values.getViewEditorRenderModeByFieldName();
	}

	private void resetFieldNamesAndValues()
	{
		values.clearValues();
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
		setProfitInfo(purchaseCandidatesGroup.getProfitInfoOrNull());
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

		resetFieldNamesAndValues();
	}

	private void setPurchasedQty(final Quantity purchasedQty)
	{
		if (Objects.equals(this.purchasedQty, purchasedQty))
		{
			return;
		}

		this.purchasedQty = purchasedQty;
		resetFieldNamesAndValues();
	}

	private void setDatePromised(final ZonedDateTime datePromised)
	{
		if (Objects.equals(this.datePromised, datePromised))
		{
			return;
		}

		this.datePromised = datePromised;
		resetFieldNamesAndValues();
	}

	private void setProfitInfo(@Nullable final PurchaseProfitInfo profitInfo)
	{
		if (profitInfo != null)
		{
			profitSalesPriceActual = profitInfo.getProfitSalesPriceActual().orElse(null);
			profitPurchasePriceActual = profitInfo.getProfitPurchasePriceActual().orElse(null);
			profitPercent = profitInfo.getProfitPercent()
					.map(percent -> percent.roundToHalf(RoundingMode.HALF_UP).toBigDecimal())
					.orElse(null);
		}
		else
		{
			profitSalesPriceActual = null;
			profitPurchasePriceActual = null;
			profitPercent = null;
		}

		resetFieldNamesAndValues();
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
			throw new AdempiereException("readonly").setParameter("rowId", getRowId());
		}
	}

	public void assertRowType(@NonNull final PurchaseRowType expectedRowType)
	{
		getRowId().assertRowType(expectedRowType);
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
		final ZonedDateTime purchaseDatePromised = request.getPurchaseDatePromised();
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
			newCandidatesGroup.profitInfoOrNull(profitInfo);
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

		// If there is at least one "available on vendor" row,
		// we shall order directly and not aggregate later on a Purchase Order.
		final boolean allowPOAggregation = availabilityResultRows
				.stream()
				.noneMatch(row -> row.getRowId().isAvailableOnVendor());

		setIncludedRows(ImmutableList.copyOf(availabilityResultRows));
		setPurchaseCandidatesGroup(getPurchaseCandidatesGroup().allowingPOAggregation(allowPOAggregation));
	}

	public Stream<PurchaseCandidatesGroup> streamPurchaseCandidatesGroup()
	{
		final Stream<PurchaseCandidatesGroup> includedRowsStream = getIncludedRows()
				.stream()
				.flatMap(PurchaseRow::streamPurchaseCandidatesGroup);

		final PurchaseCandidatesGroup candidatesGroup = getPurchaseCandidatesGroup();
		if (candidatesGroup == null)
		{
			return includedRowsStream;
		}
		else
		{
			return Stream.concat(Stream.of(candidatesGroup), includedRowsStream);
		}
	}
}
