package de.metas.ui.web.order.products_proposal.view;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import de.metas.currency.Amount;
import de.metas.pricing.ProductPriceId;
import de.metas.product.ProductId;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
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

@ToString(exclude = { "_jsonValuesByFieldName", "_renderModeByFieldName" })
public class ProductsProposalRow implements IViewRow
{
	@ViewColumn(seqNo = 10, captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup)
	private final LookupValue product;

	@ViewColumn(seqNo = 20, captionKey = "M_AttributeSetInstance_ID", widgetType = DocumentFieldWidgetType.Text)
	private final String asiDescription;

	public static final String FIELD_Price = "price";
	@ViewColumn(seqNo = 30, fieldName = FIELD_Price, captionKey = "Price", widgetType = DocumentFieldWidgetType.Amount)
	private final BigDecimal price;

	@ViewColumn(seqNo = 40, captionKey = "C_Currency_ID", widgetType = DocumentFieldWidgetType.Text)
	private final String currencyCode;

	public static final String FIELD_Qty = "qty";
	@ViewColumn(seqNo = 50, fieldName = FIELD_Qty, captionKey = "Qty", widgetType = DocumentFieldWidgetType.Quantity, editor = ViewEditorRenderMode.ALWAYS)
	@Getter
	private final BigDecimal qty;

	@ViewColumn(seqNo = 60, captionKey = "LastShipmentDays", widgetType = DocumentFieldWidgetType.Integer)
	private final Integer lastShipmentDays;

	private final DocumentId id;
	@Getter
	private final ProductPriceId productPriceId;
	@Getter
	private final ProductPriceId copiedFromProductPriceId;
	private final Amount standardPrice;

	private ImmutableMap<String, Object> _jsonValuesByFieldName; // lazy
	private ImmutableMap<String, ViewEditorRenderMode> _renderModeByFieldName; // lazy

	@Builder(toBuilder = true)
	private ProductsProposalRow(
			@NonNull final DocumentId id,
			@NonNull final LookupValue product,
			@Nullable final String asiDescription,
			@NonNull final Amount standardPrice,
			@Nullable final BigDecimal price,
			@Nullable final BigDecimal qty,
			@Nullable final Integer lastShipmentDays,
			@Nullable final ProductPriceId productPriceId,
			@Nullable final ProductPriceId copiedFromProductPriceId)
	{
		this.id = id;

		this.product = product;
		this.asiDescription = asiDescription;

		this.standardPrice = standardPrice;
		this.currencyCode = standardPrice.getCurrencyCode();
		this.price = price != null ? price : standardPrice.getValue();

		this.qty = qty;

		this.lastShipmentDays = lastShipmentDays;

		this.productPriceId = productPriceId;
		this.copiedFromProductPriceId = copiedFromProductPriceId;
	}

	@Override
	public Map<String, Object> getFieldNameAndJsonValues()
	{
		ImmutableMap<String, Object> jsonValuesByFieldName = _jsonValuesByFieldName;
		if (jsonValuesByFieldName == null)
		{
			jsonValuesByFieldName = _jsonValuesByFieldName = ViewColumnHelper.extractJsonMap(this);
		}
		return jsonValuesByFieldName;
	}

	@Override
	public Map<String, ViewEditorRenderMode> getViewEditorRenderModeByFieldName()
	{
		ImmutableMap<String, ViewEditorRenderMode> renderModeByFieldName = _renderModeByFieldName;
		if (renderModeByFieldName == null)
		{
			renderModeByFieldName = _renderModeByFieldName = buildViewEditorRenderModeByFieldName();
		}
		return renderModeByFieldName;
	}

	private ImmutableMap<String, ViewEditorRenderMode> buildViewEditorRenderModeByFieldName()
	{
		final ImmutableMap.Builder<String, ViewEditorRenderMode> builder = ImmutableMap.builder();

		builder.put(FIELD_Qty, ViewEditorRenderMode.ALWAYS);
		if (isCopiedFromButNotSaved())
		{
			builder.put(FIELD_Price, ViewEditorRenderMode.ALWAYS);
		}

		return builder.build();
	}

	public boolean isPriceEditable()
	{
		return isFieldEditable(FIELD_Price);
	}

	private boolean isFieldEditable(final String fieldName)
	{
		final ViewEditorRenderMode renderMode = getViewEditorRenderModeByFieldName().get(fieldName);
		return renderMode != null ? renderMode.isEditable() : false;
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

	@Override
	public DocumentPath getDocumentPath()
	{
		return null;
	}

	public ProductId getProductId()
	{
		return product.getIdAs(ProductId::ofRepoId);
	}

	public String getProductName()
	{
		return product.getDisplayName();
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

	public boolean isCopiedFromButNotSaved()
	{
		return getCopiedFromProductPriceId() != null
				&& getProductPriceId() == null;
	}

	public boolean isManualPrice()
	{
		return price.compareTo(standardPrice.getValue()) != 0;
	}

	public Amount getPrice()
	{
		return Amount.of(price, currencyCode);
	}

	public boolean isMatching(@NonNull final ProductsProposalViewFilter filter)
	{
		if (!Check.isEmpty(filter.getProductName())
				&& !getProductName().toLowerCase().contains(filter.getProductName().toLowerCase()))
		{
			return false;
		}

		return true;
	}
}
