package de.metas.ui.web.order.sales.pricingConditions.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Evaluatees;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

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

	@ViewColumn(captionKey = "M_PricingSystem_ID", widgetType = DocumentFieldWidgetType.Lookup, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 30)
	})
	private final LookupValue pricingSystem;

	private static final String FIELDNAME_Price = "price";
	@ViewColumn(fieldName = FIELDNAME_Price, captionKey = "Price", widgetType = DocumentFieldWidgetType.CostPrice, layouts = {
			@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40),
			@ViewColumnLayout(when = JSONViewDataType.includedView, seqNo = 40)
	})
	private final BigDecimal priceValue;

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
	private final LookupDataSource paymentTermLookup;
	@Getter
	final int discountSchemaId;
	@Getter
	private final int discountSchemaBreakId;
	@Getter
	private final int copiedFromDiscountSchemaBreakId;

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
			//
			final boolean editable,
			final int discountSchemaId,
			final int discountSchemaBreakId,
			final int copiedFromDiscountSchemaBreakId)
	{
		id = buildDocumentId(bpartner, customer);

		this.bpartner = bpartner;
		this.customer = customer;

		this.paymentTerm = paymentTerm;
		this.paymentTermLookup = paymentTermLookup;
		this.discount = discount;

		this.price = price;
		this.pricingSystem = price.getPricingSystem();
		this.priceValue = price.getPriceValue();

		this.editable = editable;
		viewEditorRenderModeByFieldName = buildViewEditorRenderModeByFieldName(editable, price.getPriceType());

		this.discountSchemaId = discountSchemaId;
		this.discountSchemaBreakId = discountSchemaBreakId;
		this.copiedFromDiscountSchemaBreakId = copiedFromDiscountSchemaBreakId;
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
		if (priceType.isPriceValueRequired())
		{
			result.put(FIELDNAME_Price, ViewEditorRenderMode.ALWAYS);
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
		final PricingConditionsRowChangeRequestBuilder builder = PricingConditionsRowChangeRequest.builder();
		for (final JSONDocumentChangedEvent fieldChangeRequest : fieldChangeRequests)
		{
			final String fieldName = fieldChangeRequest.getPath();
			if (FIELDNAME_Price.equals(fieldName))
			{
				builder.price(Price.fixedPrice(fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO)));
			}
			else if (FIELDNAME_Discount.equals(fieldName))
			{
				builder.discount(fieldChangeRequest.getValueAsBigDecimal(BigDecimal.ZERO));
			}
			else if (FIELDNAME_PaymentTerm.equals(fieldName))
			{
				builder.paymentTerm(Optional.ofNullable(fieldChangeRequest.getValueAsIntegerLookupValue()));
			}
			else
			{
				throw new AdempiereException("Field " + fieldName + " is readonly or not found");
			}
		}

		return builder.build();
	}

	public PricingConditionsRow copyAndChange(final PricingConditionsRowChangeRequest request)
	{
		assertEditable();

		boolean changed = false;
		Price price = this.price;
		BigDecimal discount = this.discount;
		LookupValue paymentTerm = this.paymentTerm;
		int discountSchemaBreakId = this.discountSchemaBreakId;
		int copiedFromDiscountSchemaBreakId = this.copiedFromDiscountSchemaBreakId;

		if (request.getPrice() != null)
		{
			if (!request.isForceChangingPriceType() && price.getPriceType() != request.getPrice().getPriceType())
			{
				throw new AdempiereException("Changing price type from " + price + " to " + request.getPrice() + " is not allowed");
			}

			price = request.getPrice();
			changed = true;
		}
		if (request.getDiscount() != null)
		{
			discount = request.getDiscount();
			changed = true;
		}
		if (request.getPaymentTerm() != null)
		{
			paymentTerm = request.getPaymentTerm().orElse(null);
			changed = true;
		}
		if (request.getDiscountSchemaBreakId() != null)
		{
			discountSchemaBreakId = request.getDiscountSchemaBreakId();
			changed = true;
		}
		if (request.getSourceDiscountSchemaBreakId() != null)
		{
			copiedFromDiscountSchemaBreakId = request.getSourceDiscountSchemaBreakId();
			changed = true;
		}

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

	public LookupValuesList getFieldTypeahead(final String fieldName, final String query)
	{
		if (FIELDNAME_PaymentTerm.equals(fieldName))
		{
			return paymentTermLookup.findEntities(Evaluatees.empty(), query);
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

	public int getPaymentTermId()
	{
		return paymentTerm != null ? paymentTerm.getIdAsInt() : -1;
	}
}
