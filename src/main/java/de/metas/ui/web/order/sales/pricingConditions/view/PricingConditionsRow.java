package de.metas.ui.web.order.sales.pricingConditions.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.Evaluatees;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.pricing.conditions.PricingConditionsBreakMatchCriteria;
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
import de.metas.ui.web.window.model.lookup.LookupDataSource;
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

	private static final String FIELDNAME_PriceType = "priceType";
	@ViewColumn(fieldName = FIELDNAME_PriceType, captionKey = "Type", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 25),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 25)
	})
	private final LookupValue priceType;

	private static final String FIELDNAME_BasePricingSystem = "basePricingSystem";
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

	private static final String FIELDNAME_PaymentTerm = "paymentTerm";
	@ViewColumn(fieldName = FIELDNAME_PaymentTerm, captionKey = "C_PaymentTerm_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 60)
	})
	@Getter
	private final LookupValue paymentTerm;

	//
	@Getter
	private final DocumentId id;
	@Getter
	private final boolean editable;

	@Getter
	private final Price price;
	private final LookupDataSource priceTypeLookup;
	private final LookupDataSource pricingSystemLookup;

	private final LookupDataSource paymentTermLookup;
	@Getter
	final int discountSchemaId;
	@Getter
	private final int discountSchemaBreakId;
	@Getter
	private final int copiedFromDiscountSchemaBreakId;

	@Getter
	private final PricingConditionsBreakMatchCriteria breakMatchCriteria;

	private transient ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy
	private final ImmutableMap<String, ViewEditorRenderMode> viewEditorRenderModeByFieldName;

	@Builder(toBuilder = true)
	private PricingConditionsRow(
			@NonNull final LookupValue bpartner,
			final boolean customer,
			//
			final LookupValue paymentTerm,
			@NonNull final LookupDataSource paymentTermLookup,
			@NonNull final BigDecimal discount,
			//
			@NonNull final Price price,
			@NonNull final LookupDataSource priceTypeLookup,
			@NonNull final LookupDataSource pricingSystemLookup,
			//
			final boolean editable,
			final int discountSchemaId,
			final int discountSchemaBreakId,
			final int copiedFromDiscountSchemaBreakId,
			@NonNull final PricingConditionsBreakMatchCriteria breakMatchCriteria)
	{
		id = buildDocumentId(bpartner, customer);

		this.bpartner = bpartner;
		this.customer = customer;

		this.paymentTerm = paymentTerm;
		this.paymentTermLookup = paymentTermLookup;
		this.discount = discount;

		this.priceTypeLookup = priceTypeLookup;
		this.pricingSystemLookup = pricingSystemLookup;
		this.price = price;
		this.priceType = priceTypeLookup.findById(price.getPriceType().getCode());
		this.basePricingSystem = price.getBasePricingSystem();
		this.basePriceAddAmt = price.getBasePriceAddAmt();
		this.fixedPrice = price.getFixedPrice();

		this.editable = editable;
		viewEditorRenderModeByFieldName = buildViewEditorRenderModeByFieldName(editable, price.getPriceType());

		this.discountSchemaId = discountSchemaId;
		this.discountSchemaBreakId = discountSchemaBreakId;
		this.copiedFromDiscountSchemaBreakId = copiedFromDiscountSchemaBreakId;
		this.breakMatchCriteria = breakMatchCriteria;
	}

	private static final DocumentId buildDocumentId(final LookupValue bpartner, final boolean customer)
	{
		return DocumentId.of(bpartner.getIdAsString() + "-" + (customer ? "C" : "V"));
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

	public PricingConditionsRow copyAndChange(final PricingConditionsRowChangeRequest request)
	{
		assertEditable();

		boolean changed = false;

		//
		// Price
		final Price price;
		final PriceChange priceChange = request.getPriceChange();
		if (priceChange == null)
		{
			price = this.price;
		}
		else if (priceChange instanceof CompletePriceChange)
		{
			price = ((CompletePriceChange)priceChange).getPrice();
			changed = true;
		}
		else if (priceChange instanceof PartialPriceChange)
		{
			price = applyPartialPriceChangeTo((PartialPriceChange)priceChange, this.price);
			changed = true;
		}
		else
		{
			throw new AdempiereException("Unknow price change request: " + priceChange);
		}

		//
		BigDecimal discount = this.discount;
		if (request.getDiscount() != null)
		{
			discount = request.getDiscount();
			changed = true;
		}

		//
		LookupValue paymentTerm = this.paymentTerm;
		if (request.getPaymentTerm() != null)
		{
			paymentTerm = request.getPaymentTerm().orElse(null);
			changed = true;
		}

		//
		int discountSchemaBreakId = this.discountSchemaBreakId;
		if (request.getDiscountSchemaBreakId() != null)
		{
			discountSchemaBreakId = request.getDiscountSchemaBreakId();
			changed = true;
		}

		//
		int copiedFromDiscountSchemaBreakId = this.copiedFromDiscountSchemaBreakId;
		if (request.getSourceDiscountSchemaBreakId() != null)
		{
			copiedFromDiscountSchemaBreakId = request.getSourceDiscountSchemaBreakId();
			changed = true;
		}

		//
		if (!changed)
		{
			return this;
		}

		return toBuilder()
				.price(price)
				.discount(discount)
				.paymentTerm(paymentTerm)
				.discountSchemaBreakId(discountSchemaBreakId)
				.copiedFromDiscountSchemaBreakId(copiedFromDiscountSchemaBreakId)
				.build();
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
		if (FIELDNAME_PaymentTerm.equals(fieldName))
		{
			return paymentTermLookup.findEntities(Evaluatees.empty(), query);
		}
		else if (FIELDNAME_PriceType.equals(fieldName))
		{
			return priceTypeLookup.findEntities(Evaluatees.empty(), query);
		}
		else if (FIELDNAME_BasePricingSystem.equals(fieldName))
		{
			return pricingSystemLookup.findEntities(Evaluatees.empty(), query);
		}
		else
		{
			throw new AdempiereException("Field " + fieldName + " does not exist or it's not a lookup field");
		}
	}

	public LookupValuesList getFieldDropdown(final String fieldName)
	{
		if (FIELDNAME_PaymentTerm.equals(fieldName))
		{
			return paymentTermLookup.findEntities(Evaluatees.empty(), 20);
		}
		else if (FIELDNAME_PriceType.equals(fieldName))
		{
			return priceTypeLookup.findEntities(Evaluatees.empty(), 20);
		}
		else if (FIELDNAME_BasePricingSystem.equals(fieldName))
		{
			return pricingSystemLookup.findEntities(Evaluatees.empty(), 20);
		}
		else
		{
			throw new AdempiereException("Field " + fieldName + " does not exist or it's not a lookup field");
		}
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

	public boolean isTemporary()
	{
		return getDiscountSchemaBreakId() <= 0;
	}
}
