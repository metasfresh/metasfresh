package de.metas.ui.web.order.sales.pricingConditions.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsBreakMatchCriteria;
import de.metas.pricing.conditions.PricingConditionsId;
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
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
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
	@Getter
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
	@Getter
	private final BigDecimal discount;

	public static final String FIELDNAME_PaymentTerm = "paymentTerm";
	@ViewColumn(fieldName = FIELDNAME_PaymentTerm, captionKey = "C_PaymentTerm_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 60)
	})
	@Getter
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
	private final Price price;
	private final PriceNetCalculator priceNetCalculator;

	@Getter
	private final PricingConditionsId pricingConditionsId;
	@Getter
	private final PricingConditionsBreakId pricingConditionsBreakId;
	@Getter
	private final PricingConditionsBreakId copiedFromPricingConditionsBreakId;
	@Getter
	private final boolean temporaryPricingConditions;

	@Getter
	private final PricingConditionsBreakMatchCriteria breakMatchCriteria;

	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy
	private final ImmutableMap<String, ViewEditorRenderMode> viewEditorRenderModeByFieldName;

	@Builder(toBuilder = true)
	private PricingConditionsRow(
			@NonNull final PricingConditionsRowLookups lookups,
			@NonNull final LookupValue bpartner,
			final boolean customer,
			final LookupValue product,
			@NonNull final BigDecimal breakValue,
			//
			final LookupValue paymentTerm,
			@NonNull final BigDecimal discount,
			//
			@NonNull final Price price,
			@NonNull final PriceNetCalculator priceNetCalculator,
			final BigDecimal priceNet,
			//
			final LocalDate dateLastInOut,
			final LocalDateTime dateCreated,
			//
			final boolean editable,
			final PricingConditionsId pricingConditionsId,
			final PricingConditionsBreakId pricingConditionsBreakId,
			final Boolean temporaryPricingConditions,
			final PricingConditionsBreakId copiedFromPricingConditionsBreakId,
			@NonNull final PricingConditionsBreakMatchCriteria breakMatchCriteria)
	{
		id = buildDocumentId(bpartner, customer);

		this.lookups = lookups;

		this.bpartner = bpartner;
		this.customer = customer;
		this.product = product;
		this.breakValue = breakValue;

		this.paymentTerm = paymentTerm;
		this.discount = discount;

		this.price = price;
		this.priceType = lookups.lookupPriceType(price.getPriceType());
		this.basePricingSystem = price.getBasePricingSystem();
		this.basePriceAddAmt = price.getBasePriceAddAmt();
		this.fixedPrice = price.getFixedPrice();

		this.priceNetCalculator = priceNetCalculator;
		this.priceNet = priceNet != null ? priceNet : priceNetCalculator.calculate(price, discount, paymentTerm);

		this.dateLastInOut = dateLastInOut;
		this.dateCreated = dateCreated;

		this.editable = editable;
		viewEditorRenderModeByFieldName = buildViewEditorRenderModeByFieldName(editable, price.getPriceType());

		PricingConditionsBreakId.assertMatching(pricingConditionsId, pricingConditionsBreakId);
		this.pricingConditionsId = pricingConditionsId;
		this.pricingConditionsBreakId = pricingConditionsBreakId;

		this.copiedFromPricingConditionsBreakId = copiedFromPricingConditionsBreakId;

		if (temporaryPricingConditions != null)
		{
			this.temporaryPricingConditions = temporaryPricingConditions;
		}
		else
		{
			this.temporaryPricingConditions = computeIsTemporaryConditions(this.pricingConditionsBreakId);
		}

		this.breakMatchCriteria = breakMatchCriteria;
	}

	private static final DocumentId buildDocumentId(final LookupValue bpartner, final boolean customer)
	{
		return DocumentId.of(bpartner.getIdAsString() + "-" + (customer ? "C" : "V"));
	}

	private static final boolean computeIsTemporaryConditions(final PricingConditionsBreakId pricingConditionsBreakId)
	{
		return pricingConditionsBreakId == null;
	}

	private static final ImmutableMap<String, ViewEditorRenderMode> buildViewEditorRenderModeByFieldName(final boolean editable, final PriceType priceType)
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
		if (priceType.isFixedPrice())
		{
			result.put(FIELDNAME_FixedPrice, ViewEditorRenderMode.ALWAYS);
		}
		else if (priceType.isBasePricingSystem())
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
				builder.discount(fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO));
			}
			else if (FIELDNAME_PaymentTerm.equals(fieldName))
			{
				builder.paymentTerm(Optional.ofNullable(fieldChangeRequest.getValueAsIntegerLookupValue()));
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
				final PriceType priceType = priceTypeLookupValue != null ? PriceType.ofCode(priceTypeLookupValue.getIdAsString()) : null;
				builder.priceType(priceType);
			}
			else if (FIELDNAME_BasePricingSystem.equals(fieldName))
			{
				builder.basePricingSystem(Optional.ofNullable(fieldChangeRequest.getValueAsIntegerLookupValue()));
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

		boolean valueChanged = false;

		//
		// Discount%
		BigDecimal discount = this.discount;
		if (request.getDiscount() != null)
		{
			discount = request.getDiscount();
			valueChanged = valueChanged || !Objects.equals(discount, this.discount);
		}

		//
		// Payment Term
		LookupValue paymentTerm = this.paymentTerm;
		if (request.getPaymentTerm() != null)
		{
			paymentTerm = request.getPaymentTerm().orElse(null);
			valueChanged = valueChanged || !Objects.equals(paymentTerm, this.paymentTerm);
		}

		//
		// Price
		final Price price = applyPriceChangeTo(request.getPriceChange(), this.price);
		BigDecimal priceNet = this.priceNet;
		if (!Objects.equals(price, this.price))
		{
			priceNet = priceNetCalculator.calculate(price, discount, paymentTerm);
			valueChanged = true;
		}

		boolean changed = valueChanged;

		//
		// ID
		PricingConditionsBreakId pricingConditionsBreakId = this.pricingConditionsBreakId;
		if (request.getPricingConditionsBreakId() != null)
		{
			pricingConditionsBreakId = request.getPricingConditionsBreakId();
			changed = changed || !Objects.equals(pricingConditionsBreakId, this.pricingConditionsBreakId);
		}

		//
		// Copied from ID
		PricingConditionsBreakId copiedFromPricingConditionsBreakId = this.copiedFromPricingConditionsBreakId;
		if (request.getSourcePricingConditionsBreakId() != null)
		{
			copiedFromPricingConditionsBreakId = request.getSourcePricingConditionsBreakId();
			changed = changed || !Objects.equals(copiedFromPricingConditionsBreakId, this.copiedFromPricingConditionsBreakId);
		}

		//
		final boolean temporaryPricingConditions = valueChanged || computeIsTemporaryConditions(pricingConditionsBreakId);
		changed = changed || !Objects.equals(temporaryPricingConditions, this.temporaryPricingConditions);

		//
		if (!changed)
		{
			return this;
		}

		return toBuilder()
				.price(price)
				.priceNet(priceNet)
				.discount(discount)
				.paymentTerm(paymentTerm)
				.pricingConditionsBreakId(pricingConditionsBreakId)
				.copiedFromPricingConditionsBreakId(copiedFromPricingConditionsBreakId)
				.temporaryPricingConditions(temporaryPricingConditions)
				.build();
	}

	private static Price applyPriceChangeTo(final PriceChange priceChange, final Price price)
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

	private static Price applyPartialPriceChangeTo(final PartialPriceChange changes, final Price price)
	{
		final PriceType priceType = changes.getPriceType() != null ? changes.getPriceType() : price.getPriceType();
		if (priceType == PriceType.NONE)
		{
			return Price.none();
		}
		else if (priceType == PriceType.BASE_PRICING_SYSTEM)
		{
			final LookupValue basePricingSystem = changes.getBasePricingSystem() != null ? changes.getBasePricingSystem().orElse(null) : price.getBasePricingSystem();
			final BigDecimal basePriceAddAmt = changes.getBasePriceAddAmt() != null ? changes.getBasePriceAddAmt() : price.getBasePriceAddAmt();
			return Price.basePricingSystem(
					basePricingSystem != null ? basePricingSystem : IntegerLookupValue.of(0, ""),
					basePriceAddAmt != null ? basePriceAddAmt : BigDecimal.ZERO);
		}
		else if (priceType == PriceType.FIXED_PRICE)
		{
			final BigDecimal fixedPrice = changes.getFixedPrice() != null ? changes.getFixedPrice() : price.getFixedPrice();
			return Price.fixedPrice(fixedPrice);
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

	public int getBpartnerId()
	{
		return bpartner.getIdAsInt();
	}

	public boolean isVendor()
	{
		return !isCustomer();
	}

	public int getBasePriceSystemId()
	{
		Check.assumeNotNull(basePricingSystem, "Parameter pricingSystem is not null");
		return basePricingSystem.getIdAsInt();
	}

	public int getPaymentTermId()
	{
		final LookupValue paymentTerm = getPaymentTerm();
		return paymentTerm != null ? paymentTerm.getIdAsInt() : -1;
	}
}
