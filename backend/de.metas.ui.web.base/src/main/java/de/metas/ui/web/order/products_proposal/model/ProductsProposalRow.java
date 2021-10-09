package de.metas.ui.web.order.products_proposal.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.currency.Amount;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.i18n.ITranslatableString;
import de.metas.order.OrderLineId;
import de.metas.pricing.ProductPriceId;
import de.metas.product.ProductId;
import de.metas.ui.web.order.products_proposal.filters.ProductsProposalViewFilter;
import de.metas.ui.web.order.products_proposal.service.Order;
import de.metas.ui.web.order.products_proposal.service.OrderLine;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout.Displayed;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString(exclude = { "values" })
public class ProductsProposalRow implements IViewRow
{
	public static ProductsProposalRow cast(final IViewRow row)
	{
		return (ProductsProposalRow)row;
	}

	public static final String FIELD_Product = "product";
	@ViewColumn(seqNo = 10, fieldName = FIELD_Product, captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup)
	@Getter
	private final LookupValue product;

	public static final String FIELD_Qty = "qty";
	@ViewColumn(seqNo = 20, fieldName = FIELD_Qty, captionKey = "Qty", widgetType = DocumentFieldWidgetType.Quantity, editor = ViewEditorRenderMode.ALWAYS)
	@Getter
	private final BigDecimal qty;

	public static final String FIELD_PackDescription = "PackDescription";
	@ViewColumn(seqNo = 30, fieldName = FIELD_PackDescription, widgetType = DocumentFieldWidgetType.Text)
	@Getter
	private final ITranslatableString packingDescription;

	public static final String FIELD_ASI = "asi";
	@ViewColumn(seqNo = 40, fieldName = FIELD_ASI, captionKey = "M_AttributeSetInstance_ID", widgetType = DocumentFieldWidgetType.Text)
	@Getter
	private final ProductASIDescription asiDescription;

	public static final String FIELD_LastShipmentDays = "lastShipmentDays";
	@ViewColumn(seqNo = 50, fieldName = FIELD_LastShipmentDays, captionKey = "LastShipmentDays", widgetType = DocumentFieldWidgetType.Integer)
	@Getter
	private final Integer lastShipmentDays;

	public static final String FIELD_Price = "price";
	@ViewColumn(seqNo = 60, fieldName = FIELD_Price, captionKey = "Price", widgetType = DocumentFieldWidgetType.Amount)
	private final BigDecimal userEnteredPrice;

	public static final String FIELD_Currency = "currency";
	@ViewColumn(seqNo = 61, fieldName = FIELD_Currency, captionKey = "C_Currency_ID", widgetType = DocumentFieldWidgetType.Text)
	private final String currencyCodeStr;

	public static final String FIELD_IsCampaignPrice = "isCampaignPrice";
	@ViewColumn(seqNo = 70, fieldName = FIELD_IsCampaignPrice, captionKey = "IsCampaignPrice", widgetType = DocumentFieldWidgetType.YesNo)
	private final boolean isCampaignPrice;

	public static final String FIELD_BPartner = "bpartner";
	@ViewColumn(displayed = Displayed.FALSE, fieldName = FIELD_BPartner, captionKey = "C_BPartner_ID", widgetType = DocumentFieldWidgetType.Lookup)
	private final LookupValue bpartner;

	public static final String FIELD_LastSalesInvoiceDate = "lastSalesInvoiceDate";
	@ViewColumn(displayed = Displayed.FALSE, fieldName = FIELD_LastSalesInvoiceDate, captionKey = "LastSalesInvoiceDate", widgetType = DocumentFieldWidgetType.LocalDate)
	@Getter
	private final LocalDate lastSalesInvoiceDate;

	public static final String FIELD_Description = "description";
	@ViewColumn(displayed = Displayed.FALSE, fieldName = FIELD_Description, captionKey = "Description", widgetType = DocumentFieldWidgetType.Text, editor = ViewEditorRenderMode.ALWAYS)
	@Getter
	private final String description;

	private final DocumentId id;
	@Getter
	private final int seqNo;
	@Getter
	private final HUPIItemProductId packingMaterialId;
	@Getter
	private final ProductPriceId productPriceId;
	@Getter
	private final ProductPriceId copiedFromProductPriceId;
	@Getter
	private final ProductProposalPrice price;

	@Getter
	private final OrderLineId existingOrderLineId;

	private final ViewRowFieldNameAndJsonValuesHolder<ProductsProposalRow> values;
	private static final ImmutableMap<String, ViewEditorRenderMode> EDITOR_RENDER_MODES = ImmutableMap.<String, ViewEditorRenderMode> builder()
			.put(FIELD_Qty, ViewEditorRenderMode.ALWAYS)
			.put(FIELD_Price, ViewEditorRenderMode.ALWAYS)
			.put(FIELD_Description, ViewEditorRenderMode.ALWAYS)
			.build();

	@Builder(toBuilder = true)
	private ProductsProposalRow(
			@NonNull final DocumentId id,
			@Nullable final LookupValue bpartner,
			@NonNull final LookupValue product,
			@Nullable final ITranslatableString packingDescription,
			@Nullable final HUPIItemProductId packingMaterialId,
			@Nullable final ProductASIDescription asiDescription,
			@NonNull final ProductProposalPrice price,
			@Nullable final BigDecimal qty,
			@Nullable final Integer lastShipmentDays,
			@Nullable final LocalDate lastSalesInvoiceDate,
			@Nullable final String description,
			final int seqNo,
			@Nullable final ProductPriceId productPriceId,
			@Nullable final ProductPriceId copiedFromProductPriceId,
			@Nullable final OrderLineId existingOrderLineId)
	{
		this.id = id;

		this.bpartner = bpartner;

		this.product = product;
		this.packingDescription = packingDescription;
		this.packingMaterialId = packingMaterialId;
		this.asiDescription = asiDescription != null ? asiDescription : ProductASIDescription.NONE;

		this.price = price;
		this.isCampaignPrice = price.isCampaignPriceUsed();
		this.userEnteredPrice = price.getUserEnteredPrice().getAsBigDecimal();
		this.currencyCodeStr = price.getCurrencyCode().toThreeLetterCode();

		this.qty = qty;

		this.lastShipmentDays = lastShipmentDays;
		this.lastSalesInvoiceDate = lastSalesInvoiceDate;

		this.description = description;

		this.seqNo = seqNo;
		this.productPriceId = productPriceId;
		this.copiedFromProductPriceId = copiedFromProductPriceId;

		this.existingOrderLineId = existingOrderLineId;

		this.values = ViewRowFieldNameAndJsonValuesHolder.builder(ProductsProposalRow.class)
				.viewEditorRenderModeByFieldName(EDITOR_RENDER_MODES)
				.build();
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
	public Map<String, ViewEditorRenderMode> getViewEditorRenderModeByFieldName()
	{
		return values.getViewEditorRenderModeByFieldName();
	}

	public boolean isPriceEditable()
	{
		return isFieldEditable(FIELD_Price);
	}

	@SuppressWarnings("SameParameterValue")
	private boolean isFieldEditable(final String fieldName)
	{
		final ViewEditorRenderMode renderMode = getViewEditorRenderModeByFieldName().get(fieldName);
		return renderMode != null && renderMode.isEditable();
	}

	@Override
	public DocumentId getId()
	{
		return id;
	}

	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Nullable
	@Override
	public DocumentPath getDocumentPath()
	{
		return null;
	}

	public ProductId getProductId()
	{
		return getProduct().getIdAs(ProductId::ofRepoId);
	}

	public String getProductName()
	{
		return getProduct().getDisplayName();
	}

	public boolean isQtySet()
	{
		final BigDecimal qty = getQty();
		return qty != null && qty.signum() != 0;
	}

	public ProductsProposalRow withLastShipmentDays(final Integer lastShipmentDays)
	{
		if (Objects.equals(this.lastShipmentDays, lastShipmentDays))
		{
			return this;
		}
		else
		{
			return toBuilder().lastShipmentDays(lastShipmentDays).build();
		}
	}

	public boolean isChanged()
	{
		return getProductPriceId() == null
				|| !getPrice().isPriceListPriceUsed();
	}

	public boolean isMatching(@NonNull final ProductsProposalViewFilter filter)
	{
		return Check.isEmpty(filter.getProductName())
				|| getProductName().toLowerCase().contains(filter.getProductName().toLowerCase());
	}

	public ProductsProposalRow withExistingOrderLine(@Nullable final Order order)
	{
		if (order == null)
		{
			return this;
		}

		final OrderLine existingOrderLine = order.getFirstMatchingOrderLine(getProductId(), getPackingMaterialId()).orElse(null);
		if (existingOrderLine == null)
		{
			return this;
		}

		final Amount existingPrice = Amount.of(existingOrderLine.getPriceEntered(), order.getCurrency().getCurrencyCode());
		
		return toBuilder()
				.qty(existingOrderLine.isPackingMaterialWithInfiniteCapacity()
						? existingOrderLine.getQtyEnteredCU()
						: BigDecimal.valueOf(existingOrderLine.getQtyEnteredTU()))
				.price(ProductProposalPrice.builder()
						.priceListPrice(existingPrice)
						.build())
				.existingOrderLineId(existingOrderLine.getOrderLineId())
				.description(existingOrderLine.getDescription())
				.build();
	}
}
