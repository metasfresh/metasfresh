package de.metas.ui.web.order.pricingconditions.view;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_DiscountSchemaBreak;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pricing.conditions.PriceSpecification;
import de.metas.pricing.conditions.PriceSpecificationType;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
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
import de.metas.util.lang.Percent;
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

@ToString(exclude = "values")
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

	@ViewColumn(captionKey = I_M_DiscountSchemaBreak.COLUMNNAME_BreakValue, widgetType = DocumentFieldWidgetType.Number, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 23),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 23)
	})
	private final BigDecimal breakValue;

	public static final String FIELDNAME_BasePriceType = "basePriceType";
	@ViewColumn(fieldName = FIELDNAME_BasePriceType, captionKey = I_M_DiscountSchemaBreak.COLUMNNAME_PriceBase, widgetType = DocumentFieldWidgetType.List, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 25),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 25)
	})
	private final LookupValue basePriceType;

	public static final String FIELDNAME_BasePricingSystem = "basePricingSystem";
	@ViewColumn(fieldName = FIELDNAME_BasePricingSystem, captionKey = I_M_DiscountSchemaBreak.COLUMNNAME_Base_PricingSystem_ID, widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 30)
	})
	private final LookupValue basePricingSystem;

	/** ..coming from either the pricing system or set as fixed price by the user */
	static final String FIELDNAME_BasePrice = "basePrice";
	@ViewColumn(fieldName = FIELDNAME_BasePrice, captionKey = "PriceStd", widgetType = DocumentFieldWidgetType.CostPrice, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 40)
	})
	@Getter
	private final BigDecimal basePriceAmt;

	static final String FIELDNAME_PricingSystemSurcharge = "pricingSystemSurchargeAmt";
	@ViewColumn(fieldName = FIELDNAME_PricingSystemSurcharge, captionKey = I_M_DiscountSchemaBreak.COLUMNNAME_PricingSystemSurchargeAmt, widgetType = DocumentFieldWidgetType.CostPrice, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 45),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 45)
	})
	private final BigDecimal pricingSystemSurchargeAmt;

	static final String FIELDNAME_C_Currency_ID = "currency";
	@ViewColumn(fieldName = FIELDNAME_C_Currency_ID, captionKey = "C_Currency_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 47),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 47)
	})
	private final LookupValue currency;

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

	public static final String FIELDNAME_PaymentDiscount = "paymentDiscount";
	@ViewColumn(fieldName = FIELDNAME_PaymentDiscount, captionKey = "PaymentDiscount", widgetType = DocumentFieldWidgetType.Number, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 70)
	})
	private final BigDecimal paymentDiscountOverride;

	@ViewColumn(captionKey = I_C_OrderLine.COLUMNNAME_PriceActual, widgetType = DocumentFieldWidgetType.Number, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 100),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 100)
	})
	private final BigDecimal netPrice;

	@ViewColumn(captionKey = "LastInOutDate", widgetType = DocumentFieldWidgetType.LocalDate, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 130),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 130)
	})
	private final LocalDate dateLastInOut;

	private static final String FIELDNAME_CreatedBy = "createdBy";
	@ViewColumn(fieldName = FIELDNAME_CreatedBy, captionKey = "CreatedBy", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 120),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 120)
	})
	private final LookupValue createdBy;

	@ViewColumn(captionKey = "Created", widgetType = DocumentFieldWidgetType.Timestamp, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 110),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 110)
	})
	private final Instant dateCreated;

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

	private final ViewRowFieldNameAndJsonValuesHolder<PricingConditionsRow> values;

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

		this.statusColor = pricingConditionsBreak.isTemporaryPricingConditionsBreak() ? lookups.getTemporaryPriceConditionsColor() : null;

		PricingConditionsBreakId.assertMatching(pricingConditionsId, pricingConditionsBreak.getId());
		this.pricingConditionsId = pricingConditionsId;
		this.pricingConditionsBreak = pricingConditionsBreak;

		this.product = lookups.lookupProduct(pricingConditionsBreak.getMatchCriteria().getProductId());

		this.breakValue = pricingConditionsBreak.getMatchCriteria().getBreakValue();

		this.paymentTerm = lookups.lookupPaymentTerm(pricingConditionsBreak.getPaymentTermIdOrNull());

		this.paymentDiscountOverride = Optional
				.ofNullable(pricingConditionsBreak.getPaymentDiscountOverrideOrNull())
				.map(Percent::toBigDecimal)
				.orElse(null);

		final PriceSpecification price = pricingConditionsBreak.getPriceSpecification();
		this.basePriceType = lookups.lookupPriceType(price.getType());

		this.discount = pricingConditionsBreak.getDiscount().toBigDecimal();

		this.basePricingSystemPriceCalculator = basePricingSystemPriceCalculator;
		switch (price.getType())
		{
			case NONE:
			{
				this.basePricingSystem = null;
				this.basePriceAmt = null;
				this.pricingSystemSurchargeAmt = null;
				this.currency = null;
				break;
			}
			case BASE_PRICING_SYSTEM:
			{
				final BasePricingSystemPriceCalculatorRequest calculatorRequest = BasePricingSystemPriceCalculatorRequest.builder()
						.pricingConditionsBreak(pricingConditionsBreak)
						.bpartnerId(BPartnerId.ofRepoId(bpartner.getIdAsInt()))
						.isSOTrx(customer)
						.build();
				final Money basePrice = basePricingSystemPriceCalculator.calculate(calculatorRequest);

				this.basePricingSystem = lookups.lookupPricingSystem(price.getBasePricingSystemId());
				this.basePriceAmt = basePrice.toBigDecimal();
				final Money surcharge = price.getPricingSystemSurcharge();
				this.pricingSystemSurchargeAmt = surcharge != null ? surcharge.toBigDecimal() : null;
				this.currency = lookups.lookupCurrency(surcharge != null ? surcharge.getCurrencyId() : null);
				break;
			}
			case FIXED_PRICE:
			{
				final Money fixedPrice = price.getFixedPrice();

				this.basePricingSystem = null;
				this.basePriceAmt = fixedPrice != null ? fixedPrice.toBigDecimal() : null;
				this.pricingSystemSurchargeAmt = null;
				this.currency = lookups.lookupCurrency(fixedPrice != null ? fixedPrice.getCurrencyId() : null);
				break;
			}
			default:
			{
				throw new AdempiereException("Unknown " + PriceSpecificationType.class + ": " + price.getType());
			}
		}

		this.netPrice = calculateNetPrice();

		this.dateLastInOut = dateLastInOut;
		this.dateCreated = pricingConditionsBreak.getDateCreated();
		this.createdBy = lookups.lookupUser(pricingConditionsBreak.getCreatedById());

		this.editable = editable;

		this.values = ViewRowFieldNameAndJsonValuesHolder.<PricingConditionsRow> builder(PricingConditionsRow.class)
				.widgetTypesByFieldName(ViewColumnHelper.getWidgetTypesByFieldName(getClass()))
				.viewEditorRenderModeByFieldName(buildViewEditorRenderModeByFieldName(
						editable,
						price.getType(),
						pricingConditionsBreak.isTemporaryPricingConditionsBreak()))
				.build();

		this.copiedFromPricingConditionsBreakId = copiedFromPricingConditionsBreakId;

		this.id = buildDocumentId(this.pricingConditionsBreak, this.bpartner, this.customer, this.editable);
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

	private static final ImmutableMap<String, ViewEditorRenderMode> buildViewEditorRenderModeByFieldName(
			final boolean editable,
			final PriceSpecificationType priceType,
			final boolean temporaryPricingConditionsBreak)
	{
		if (!editable)
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<String, ViewEditorRenderMode> result = ImmutableMap
				.<String, ViewEditorRenderMode> builder()
				.put(FIELDNAME_Discount, ViewEditorRenderMode.ALWAYS)
				.put(FIELDNAME_PaymentTerm, ViewEditorRenderMode.ALWAYS)
				.put(FIELDNAME_PaymentDiscount, ViewEditorRenderMode.ALWAYS);

		//
		result.put(FIELDNAME_BasePriceType, ViewEditorRenderMode.ALWAYS);

		if (priceType == null || priceType == PriceSpecificationType.NONE)
		{
			result.put(FIELDNAME_BasePrice, ViewEditorRenderMode.NEVER)
					.put(FIELDNAME_BasePricingSystem, ViewEditorRenderMode.NEVER)
					.put(FIELDNAME_PricingSystemSurcharge, ViewEditorRenderMode.NEVER)
					.put(FIELDNAME_C_Currency_ID, ViewEditorRenderMode.NEVER);
		}
		else if (priceType == PriceSpecificationType.FIXED_PRICE)
		{
			result.put(FIELDNAME_BasePrice, ViewEditorRenderMode.ALWAYS)
					.put(FIELDNAME_BasePricingSystem, ViewEditorRenderMode.NEVER)
					.put(FIELDNAME_PricingSystemSurcharge, ViewEditorRenderMode.NEVER)
					.put(FIELDNAME_C_Currency_ID, ViewEditorRenderMode.ALWAYS);
		}
		else if (priceType == PriceSpecificationType.BASE_PRICING_SYSTEM)
		{
			result.put(FIELDNAME_BasePrice, ViewEditorRenderMode.NEVER)
					.put(FIELDNAME_BasePricingSystem, ViewEditorRenderMode.ALWAYS)
					.put(FIELDNAME_PricingSystemSurcharge, ViewEditorRenderMode.ALWAYS)
					.put(FIELDNAME_C_Currency_ID, ViewEditorRenderMode.ALWAYS);
		}

		return result.build();
	}

	private BigDecimal calculateNetPrice()
	{
		if (basePriceAmt == null)
		{
			return null;
		}

		final BigDecimal priceBeforeDiscount = basePriceAmt
				.add(coalesce(pricingSystemSurchargeAmt, ZERO));

		final int precision = 2; // TODO: hardcoded

		final BigDecimal priceAfterDiscount = pricingConditionsBreak
				.getDiscount()
				.subtractFromBase(priceBeforeDiscount, precision);
		return priceAfterDiscount;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		// TODO i think we shall make this method not mandatory in interface
		return null;
	}

	@Override
	public boolean isProcessed()
	{
		return !editable;
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

	public CurrencyId getCurrencyId()
	{
		return CurrencyId.ofRepoId(currency.getIdAsInt());
	}
}
