package de.metas.ui.web.order.sales.pricingConditions.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.lang.Percent;
import de.metas.pricing.conditions.PriceOverride;
import de.metas.pricing.conditions.PriceOverrideType;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreak.PricingConditionsBreakBuilder;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsBreakMatchCriteria;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.ui.web.order.sales.pricingConditions.view.PriceNetCalculator.PriceNetCalculateRequest;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest.CompletePriceChange;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest.PartialPriceChange;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest.PartialPriceChange.PartialPriceChangeBuilder;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest.PriceChange;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest.PricingConditionsRowChangeRequestBuilder;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;;

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
public class PricingConditionsRow implements IViewRow
{
	public static PricingConditionsRow cast(final IViewRow row)
	{
		return (PricingConditionsRow)row;
	}

	@ViewColumn(captionKey = "C_BPartner_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 10)
	})
	private final LookupValue bpartner;

	@ViewColumn(captionKey = "IsCustomer", widgetType = DocumentFieldWidgetType.YesNo, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 20)
	})
	@Getter
	private final boolean customer;

	@ViewColumn(captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 22),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 22)
	})
	private final LookupValue product;

	@ViewColumn(captionKey = "BreakValue", widgetType = DocumentFieldWidgetType.Number, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 23),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 23)
	})
	private final BigDecimal breakValue;

	public static final String FIELDNAME_PriceType = "priceType";
	@ViewColumn(fieldName = FIELDNAME_PriceType, captionKey = "Type", widgetType = DocumentFieldWidgetType.List, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 25),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 25)
	})
	private final LookupValue priceType;

	public static final String FIELDNAME_BasePricingSystem = "basePricingSystem";
	@ViewColumn(fieldName = FIELDNAME_BasePricingSystem, captionKey = "M_PricingSystem_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 30)
	})
	private final LookupValue basePricingSystem;

	private static final String FIELDNAME_FixedPrice = "fixedPrice";
	@ViewColumn(fieldName = FIELDNAME_FixedPrice, captionKey = "Price", widgetType = DocumentFieldWidgetType.CostPrice, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 40)
	})
	private final BigDecimal fixedPrice;

	private static final String FIELDNAME_BasePriceAddAmt = "basePriceAddAmt";
	@ViewColumn(fieldName = FIELDNAME_BasePriceAddAmt, captionKey = "Std_AddAmt", widgetType = DocumentFieldWidgetType.CostPrice, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 45),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 45)
	})
	private final BigDecimal basePriceAddAmt;

	private static final String FIELDNAME_Discount = "discount";
	@ViewColumn(fieldName = FIELDNAME_Discount, captionKey = "Discount", widgetType = DocumentFieldWidgetType.Number, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 50)
	})
	private final BigDecimal discount;

	public static final String FIELDNAME_PaymentTerm = "paymentTerm";
	@ViewColumn(fieldName = FIELDNAME_PaymentTerm, captionKey = "C_PaymentTerm_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 60)
	})
	private final LookupValue paymentTerm;

	@ViewColumn(captionKey = "PriceNet", widgetType = DocumentFieldWidgetType.Number, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 100),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 100)
	})
	private final BigDecimal priceNet;

	@ViewColumn(captionKey = "LastInOutDate", widgetType = DocumentFieldWidgetType.Date, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 110),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 110)
	})
	private final LocalDate dateLastInOut;

	@ViewColumn(captionKey = "Created", widgetType = DocumentFieldWidgetType.DateTime, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 120),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 120)
	})
	private final LocalDateTime dateCreated;

	//
	private final PricingConditionsRowLookups lookups;

	@Getter
	private final DocumentId id;
	@Getter
	private final boolean editable;

	@Getter
	private final PricingConditionsId pricingConditionsId;
	private final PricingConditionsBreak pricingConditionsBreak;

	private final PriceNetCalculator priceNetCalculator;

	@Getter
	private final PricingConditionsBreakId copiedFromPricingConditionsBreakId;

	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy
	private final ImmutableMap<String, ViewEditorRenderMode> viewEditorRenderModeByFieldName;

	@Builder(toBuilder = true)
	private PricingConditionsRow(
			@NonNull final PricingConditionsRowLookups lookups,
			@NonNull final LookupValue bpartner,
			final boolean customer,
			final PricingConditionsId pricingConditionsId,
			@NonNull final PricingConditionsBreak pricingConditionsBreak,
			final LocalDate dateLastInOut,
			@NonNull final PriceNetCalculator priceNetCalculator,
			final PricingConditionsBreakId copiedFromPricingConditionsBreakId,
			final boolean editable)
	{
		id = buildDocumentId(bpartner, customer);

		this.lookups = lookups;
		this.bpartner = bpartner;
		this.customer = customer;

		PricingConditionsBreakId.assertMatching(pricingConditionsId, pricingConditionsBreak.getId());
		this.pricingConditionsId = pricingConditionsId;
		this.pricingConditionsBreak = pricingConditionsBreak;

		this.product = lookups.lookupProduct(pricingConditionsBreak.getMatchCriteria().getProductId());
		this.breakValue = pricingConditionsBreak.getMatchCriteria().getBreakValue();

		this.paymentTerm = lookups.lookupPaymentTerm(pricingConditionsBreak.getPaymentTermId());
		this.discount = pricingConditionsBreak.getDiscount().getValueAsBigDecimal();

		final PriceOverride price = pricingConditionsBreak.getPriceOverride();
		this.priceType = lookups.lookupPriceType(price.getType());
		this.basePricingSystem = lookups.lookupPricingSystem(price.getBasePricingSystemId());
		this.basePriceAddAmt = price.getBasePriceAddAmt();
		this.fixedPrice = price.getFixedPrice();

		this.priceNetCalculator = priceNetCalculator;
		this.priceNet = priceNetCalculator.calculate(PriceNetCalculateRequest.builder()
				.pricingConditionsBreak(pricingConditionsBreak)
				.bpartnerId(BPartnerId.ofRepoId(bpartner.getIdAsInt()))
				.isSOTrx(customer)
				.build());

		this.dateLastInOut = dateLastInOut;
		this.dateCreated = pricingConditionsBreak.getDateCreated();

		this.editable = editable;
		viewEditorRenderModeByFieldName = buildViewEditorRenderModeByFieldName(editable, price.getType());

		this.copiedFromPricingConditionsBreakId = copiedFromPricingConditionsBreakId;
	}

	private static final DocumentId buildDocumentId(final LookupValue bpartner, final boolean customer)
	{
		return DocumentId.of(bpartner.getIdAsString() + "-" + (customer ? "C" : "V"));
	}

	private static final ImmutableMap<String, ViewEditorRenderMode> buildViewEditorRenderModeByFieldName(final boolean editable, final PriceOverrideType priceType)
	{
		if (!editable)
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<String, ViewEditorRenderMode> result = ImmutableMap.<String, ViewEditorRenderMode> builder()
				.put(FIELDNAME_Discount, ViewEditorRenderMode.ALWAYS)
				.put(FIELDNAME_PaymentTerm, ViewEditorRenderMode.ALWAYS);

		//
		result.put(FIELDNAME_PriceType, ViewEditorRenderMode.ALWAYS);
		if (priceType == PriceOverrideType.FIXED_PRICE)
		{
			result.put(FIELDNAME_FixedPrice, ViewEditorRenderMode.ALWAYS);
		}
		else if (priceType == PriceOverrideType.BASE_PRICING_SYSTEM)
		{
			result.put(FIELDNAME_BasePricingSystem, ViewEditorRenderMode.ALWAYS);
			result.put(FIELDNAME_BasePriceAddAmt, ViewEditorRenderMode.ALWAYS);
		}

		return result.build();
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		// TODO i think we shall make this method not mandatory in interface
		return null;
	}

	@Override
	public IViewRowType getType()
	{
		return DefaultRowType.Row;
	}

	@Override
	public boolean isProcessed()
	{
		return !editable;
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
		return ViewColumnHelper.getWidgetTypesByFieldName(getClass());
	}

	@Override
	public Map<String, ViewEditorRenderMode> getViewEditorRenderModeByFieldName()
	{
		return viewEditorRenderModeByFieldName;
	}

	@Override
	public List<? extends IViewRow> getIncludedRows()
	{
		return ImmutableList.of();
	}

	private final void assertEditable()
	{
		if (!editable)
		{
			throw new AdempiereException("Row is not editable");
		}
	}

	public PricingConditionsRow copyAndChange(final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		return copyAndChange(toChangeRequest(fieldChangeRequests));
	}

	private static PricingConditionsRowChangeRequest toChangeRequest(final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final PricingConditionsRowChangeRequestBuilder builder = PricingConditionsRowChangeRequest.builder()
				.priceChange(toPriceChangeRequest(fieldChangeRequests));

		for (final JSONDocumentChangedEvent fieldChangeRequest : fieldChangeRequests)
		{
			final String fieldName = fieldChangeRequest.getPath();
			if (FIELDNAME_Discount.equals(fieldName))
			{
				builder.discount(Percent.of(fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO)));
			}
			else if (FIELDNAME_PaymentTerm.equals(fieldName))
			{
				final LookupValue paymentTerm = fieldChangeRequest.getValueAsIntegerLookupValue();
				final OptionalInt paymentTermId = paymentTerm != null && paymentTerm.getIdAsInt() > 0 ? OptionalInt.of(paymentTerm.getIdAsInt()) : OptionalInt.empty();
				builder.paymentTermId(paymentTermId);
			}
		}

		return builder.build();
	}

	private static PartialPriceChange toPriceChangeRequest(final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final PartialPriceChangeBuilder builder = PartialPriceChange.builder();
		for (final JSONDocumentChangedEvent fieldChangeRequest : fieldChangeRequests)
		{
			final String fieldName = fieldChangeRequest.getPath();
			if (FIELDNAME_PriceType.equals(fieldName))
			{
				final LookupValue priceTypeLookupValue = fieldChangeRequest.getValueAsStringLookupValue();
				final PriceOverrideType priceType = priceTypeLookupValue != null ? PriceOverrideType.ofCode(priceTypeLookupValue.getIdAsString()) : null;
				builder.priceType(priceType);
			}
			else if (FIELDNAME_BasePricingSystem.equals(fieldName))
			{
				final LookupValue pricingSystem = fieldChangeRequest.getValueAsIntegerLookupValue();
				final OptionalInt pricingSystemId = pricingSystem != null ? OptionalInt.of(pricingSystem.getIdAsInt()) : OptionalInt.empty();
				builder.basePricingSystemId(pricingSystemId);
			}
			else if (FIELDNAME_BasePriceAddAmt.equals(fieldName))
			{
				builder.basePriceAddAmt(fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO));
			}
			else if (FIELDNAME_FixedPrice.equals(fieldName))
			{
				builder.fixedPrice(fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO));
			}
		}

		return builder.build();
	}

	public PricingConditionsRow copyAndChangeToEditable()
	{
		if (editable)
		{
			return this;
		}

		return toBuilder().editable(true).build();
	}

	public PricingConditionsRow copyAndChange(final PricingConditionsRowChangeRequest request)
	{
		assertEditable();

		boolean changed = false;

		final PricingConditionsBreak pricingConditionsBreak = applyTo(request, this.pricingConditionsBreak)
				.toTemporaryPricingConditionsBreakIfPriceRelevantFieldsChanged(this.pricingConditionsBreak);
		if (!Objects.equals(pricingConditionsBreak, this.pricingConditionsBreak))
		{
			changed = true;
		}

		//
		// Copied from ID
		PricingConditionsBreakId copiedFromPricingConditionsBreakId = this.copiedFromPricingConditionsBreakId;
		if (!Objects.equals(request.getSourcePricingConditionsBreakId(), copiedFromPricingConditionsBreakId))
		{
			copiedFromPricingConditionsBreakId = request.getSourcePricingConditionsBreakId();
			changed = true;
		}

		//
		if (!changed)
		{
			return this;
		}

		return toBuilder()
				.pricingConditionsBreak(pricingConditionsBreak)
				.copiedFromPricingConditionsBreakId(copiedFromPricingConditionsBreakId)
				.build();
	}

	private static PricingConditionsBreak applyTo(final PricingConditionsRowChangeRequest request, final PricingConditionsBreak pricingConditionsBreak)
	{
		final PricingConditionsBreakBuilder builder = pricingConditionsBreak.toBuilder();

		//
		// Discount%
		if (request.getDiscount() != null)
		{
			builder.discount(request.getDiscount());
		}

		//
		// Payment Term
		if (request.getPaymentTermId() != null)
		{
			builder.paymentTermId(request.getPaymentTermId().orElse(-1));
		}

		//
		// Price
		if (request.getPriceChange() != null)
		{
			final PriceOverride price = applyPriceChangeTo(request.getPriceChange(), pricingConditionsBreak.getPriceOverride());
			builder.priceOverride(price);
		}

		//
		// ID
		if (request.getPricingConditionsBreakId() != null)
		{
			builder.id(request.getPricingConditionsBreakId());
		}

		//
		// DateCreated
		if (request.getDateCreated() != null)
		{
			builder.dateCreated(request.getDateCreated());
		}

		return builder.build();
	}

	private static PriceOverride applyPriceChangeTo(final PriceChange priceChange, final PriceOverride price)
	{
		if (priceChange == null)
		{
			// no change
			return price;
		}
		else if (priceChange instanceof CompletePriceChange)
		{
			return ((CompletePriceChange)priceChange).getPrice();
		}
		else if (priceChange instanceof PartialPriceChange)
		{
			return applyPartialPriceChangeTo((PartialPriceChange)priceChange, price);
		}
		else
		{
			throw new AdempiereException("Unknow price change request: " + priceChange);
		}

	}

	private static PriceOverride applyPartialPriceChangeTo(final PartialPriceChange changes, final PriceOverride price)
	{
		final PriceOverrideType priceType = changes.getPriceType() != null ? changes.getPriceType() : price.getType();
		if (priceType == PriceOverrideType.NONE)
		{
			return PriceOverride.none();
		}
		else if (priceType == PriceOverrideType.BASE_PRICING_SYSTEM)
		{
			final int requestBasePricingSystemId = changes.getBasePricingSystemId() != null ? changes.getBasePricingSystemId().orElse(-1) : -1;
			final int basePricingSystemId = Util.firstGreaterThanZero(requestBasePricingSystemId, price.getBasePricingSystemId(), IPriceListDAO.M_PricingSystem_ID_None);
			final BigDecimal basePriceAddAmt = changes.getBasePriceAddAmt() != null ? changes.getBasePriceAddAmt() : price.getBasePriceAddAmt();
			return PriceOverride.basePricingSystem(
					basePricingSystemId,
					basePriceAddAmt != null ? basePriceAddAmt : BigDecimal.ZERO);
		}
		else if (priceType == PriceOverrideType.FIXED_PRICE)
		{
			final BigDecimal fixedPrice = Util.coalesce(changes.getFixedPrice(), price.getFixedPrice(), BigDecimal.ZERO);
			return PriceOverride.fixedPrice(fixedPrice);
		}
		else
		{
			throw new AdempiereException("Unknow price type: " + priceType);
		}
	}

	public LookupValuesList getFieldTypeahead(final String fieldName, final String query)
	{
		return lookups.getFieldTypeahead(fieldName, query);
	}

	public LookupValuesList getFieldDropdown(final String fieldName)
	{
		return lookups.getFieldDropdown(fieldName);
	}

	public BPartnerId getBpartnerId()
	{
		return BPartnerId.ofRepoId(bpartner.getIdAsInt());
	}

	public String getBpartnerDisplayName()
	{
		return bpartner.getDisplayName();
	}

	public boolean isVendor()
	{
		return !isCustomer();
	}

	public int getBasePriceSystemId()
	{
		return pricingConditionsBreak.getPriceOverride().getBasePricingSystemId();
	}

	public int getPaymentTermId()
	{
		return pricingConditionsBreak.getPaymentTermId();
	}

	public PriceOverride getPrice()
	{
		return pricingConditionsBreak.getPriceOverride();
	}

	public Percent getDiscount()
	{
		return pricingConditionsBreak.getDiscount();
	}

	public PricingConditionsBreakId getPricingConditionsBreakId()
	{
		return pricingConditionsBreak.getId();
	}

	public PricingConditionsBreakMatchCriteria getBreakMatchCriteria()
	{
		return pricingConditionsBreak.getMatchCriteria();
	}

	public boolean isTemporaryPricingConditions()
	{
		return pricingConditionsBreak.getId() == null;
	}
}
