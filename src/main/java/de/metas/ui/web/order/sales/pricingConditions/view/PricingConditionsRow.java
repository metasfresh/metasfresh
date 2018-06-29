package de.metas.ui.web.order.sales.pricingConditions.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.logging.LogManager;
import de.metas.pricing.conditions.PriceOverride;
import de.metas.pricing.conditions.PriceOverrideType;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsId;
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

	public static final String FIELDNAME_StatusColor = "statusColor";
	@ViewColumn(fieldName = FIELDNAME_StatusColor, captionKey = " ", widgetType = DocumentFieldWidgetType.Color, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 1),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 1)
	})
	private final String statusColor;

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

	static final String FIELDNAME_Price = "price";
	@ViewColumn(fieldName = FIELDNAME_Price, captionKey = "Price", widgetType = DocumentFieldWidgetType.CostPrice, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 40)
	})
	@Getter
	private final BigDecimal basePrice;

	static final String FIELDNAME_BasePriceAddAmt = "basePriceAddAmt";
	@ViewColumn(fieldName = FIELDNAME_BasePriceAddAmt, captionKey = "Std_AddAmt", widgetType = DocumentFieldWidgetType.CostPrice, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 45),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 45)
	})
	private final BigDecimal basePriceAddAmt;

	static final String FIELDNAME_Discount = "discount";
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

//	static final String FIELDNAME_PaymentDiscount = "paymentDiscount";
//	@ViewColumn(fieldName = FIELDNAME_PaymentDiscount, captionKey = "PaymentDiscount", widgetType = DocumentFieldWidgetType.Number, layouts = {
//			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70),
//			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 70)
//	})
//	private final BigDecimal paymentTermDiscountOverride;

	@ViewColumn(captionKey = "PriceNet", widgetType = DocumentFieldWidgetType.Number, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 100),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 100)
	})
	private final BigDecimal netPrice;

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

	private static final Logger logger = LogManager.getLogger(PricingConditionsRow.class);

	//
	private final PricingConditionsRowLookups lookups;

	@Getter
	private final DocumentId id;
	@Getter
	private final boolean editable;

	@Getter
	private final PricingConditionsId pricingConditionsId;
	@Getter
	private final PricingConditionsBreak pricingConditionsBreak;
	private final BasePricingSystemPriceCalculator basePricingSystemPriceCalculator;

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
			@NonNull final BasePricingSystemPriceCalculator basePricingSystemPriceCalculator,
			final PricingConditionsBreakId copiedFromPricingConditionsBreakId,
			final boolean editable)
	{
		this.lookups = lookups;
		this.bpartner = bpartner;
		this.customer = customer;

		statusColor = pricingConditionsBreak.isTemporaryPricingConditionsBreak() ? lookups.getTemporaryPriceConditionsColor() : null;

		PricingConditionsBreakId.assertMatching(pricingConditionsId, pricingConditionsBreak.getId());
		this.pricingConditionsId = pricingConditionsId;
		this.pricingConditionsBreak = pricingConditionsBreak;

		product = lookups.lookupProduct(pricingConditionsBreak.getMatchCriteria().getProductId());
		breakValue = pricingConditionsBreak.getMatchCriteria().getBreakValue();

		paymentTerm = lookups.lookupPaymentTerm(pricingConditionsBreak.getPaymentTermId());
		//paymentTermDiscountOverride = pricingConditionsBreak.getPaymentTermDiscountOverride().getValueAsBigDecimal();

		final PriceOverride price = pricingConditionsBreak.getPriceOverride();
		priceType = lookups.lookupPriceType(price.getType());
		basePricingSystem = lookups.lookupPricingSystem(price.getBasePricingSystemId());
		basePriceAddAmt = price.getBasePriceAddAmt();

		discount = pricingConditionsBreak.getDiscount().getValueAsBigDecimal();

		this.basePricingSystemPriceCalculator = basePricingSystemPriceCalculator;
		basePrice = calculateBasePrice(basePricingSystemPriceCalculator, BasePricingSystemPriceCalculatorRequest.builder()
				.pricingConditionsBreak(pricingConditionsBreak)
				.bpartnerId(BPartnerId.ofRepoId(bpartner.getIdAsInt()))
				.isSOTrx(customer)
				.build());

		netPrice = calculateNetPrice(basePrice, pricingConditionsBreak);

		this.dateLastInOut = dateLastInOut;
		dateCreated = pricingConditionsBreak.getDateCreated();

		this.editable = editable;
		viewEditorRenderModeByFieldName = buildViewEditorRenderModeByFieldName(editable, price.getType());

		this.copiedFromPricingConditionsBreakId = copiedFromPricingConditionsBreakId;

		id = buildDocumentId(this.pricingConditionsBreak, this.bpartner, this.customer, this.editable);
	}

	private static final DocumentId buildDocumentId(final PricingConditionsBreak pricingConditionsBreak, final LookupValue bpartner, final boolean customer, final boolean editableRow)
	{
		final StringBuilder idStr = new StringBuilder();
		idStr.append(bpartner.getIdAsString());
		idStr.append("-").append(customer ? "C" : "V");
		if (editableRow)
		{
			idStr.append("-").append("editable");
		}
		else if (pricingConditionsBreak.getId() != null)
		{
			// In case the row is not editable, we shall also append the pricing conditions break ID to make it unique,
			// else would fail in case we are showing all pricing conditions, for all break levels for a given product.
			// (e.g. when we are opening it from material cockpit)
			final PricingConditionsBreakId pricingConditionsBreakId = pricingConditionsBreak.getId();
			idStr.append("-").append(pricingConditionsBreakId.getDiscountSchemaBreakId());
		}

		return DocumentId.ofString(idStr.toString());
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
			result.put(FIELDNAME_Price, ViewEditorRenderMode.ALWAYS);
		}
		else if (priceType == PriceOverrideType.BASE_PRICING_SYSTEM)
		{
			result.put(FIELDNAME_BasePricingSystem, ViewEditorRenderMode.ALWAYS);
			result.put(FIELDNAME_BasePriceAddAmt, ViewEditorRenderMode.ALWAYS);
		}

		return result.build();
	}

	private static BigDecimal calculateBasePrice(
			@NonNull final BasePricingSystemPriceCalculator basePricingSystemPriceCalculator,
			@NonNull final BasePricingSystemPriceCalculatorRequest request)
	{
		try
		{
			final PricingConditionsBreak pricingConditionsBreak = request.getPricingConditionsBreak();
			final PriceOverride price = pricingConditionsBreak.getPriceOverride();
			final PriceOverrideType type = price.getType();
			if (type == PriceOverrideType.NONE)
			{
				return null;
			}
			else if (type == PriceOverrideType.BASE_PRICING_SYSTEM)
			{
				final BigDecimal basePrice = basePricingSystemPriceCalculator.calculate(request);
				// NOTE: assume BasePriceAddAmt was added
				// basePrice = basePrice.add(price.getBasePriceAddAmt());

				return basePrice;
			}
			else if (type == PriceOverrideType.FIXED_PRICE)
			{
				return price.getFixedPrice();
			}
			else
			{
				throw new AdempiereException("Unknown " + PriceOverrideType.class + ": " + type);
			}
		}
		catch (final Exception ex)
		{
			logger.warn("Failed calculating net price for {}. Returning null.", request, ex);
			return null;
		}
	}

	private static BigDecimal calculateNetPrice(final BigDecimal basePrice, @NonNull final PricingConditionsBreak pricingConditionsBreak)
	{
		if (basePrice == null)
		{
			return null;
		}

		final int precision = 2; // TODO: hardcoded
		final BigDecimal priceAfterDiscount = pricingConditionsBreak.getDiscount().subtractFromBase(basePrice, precision);
		return priceAfterDiscount;
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

	final void assertEditable()
	{
		if (!editable)
		{
			throw new AdempiereException("Row is not editable");
		}
	}

	public PricingConditionsRow copyAndChangeToEditable()
	{
		if (editable)
		{
			return this;
		}

		return toBuilder().editable(true).build();
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
}
