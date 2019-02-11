package de.metas.ui.web.order.products_proposal.view;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import de.metas.currency.Amount;
import de.metas.product.ProductId;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
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

@ToString(exclude = "_fieldNameAndJsonValues")
public class ProductsProposalRow implements IViewRow
{
	@ViewColumn(seqNo = 10, captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup)
	private final LookupValue product;

	@ViewColumn(seqNo = 20, captionKey = "M_AttributeSetInstance_ID", widgetType = DocumentFieldWidgetType.Text)
	private final String asiDescription;

	@ViewColumn(seqNo = 30, captionKey = "Price", widgetType = DocumentFieldWidgetType.Amount)
	private final Amount price;

	@ViewColumn(seqNo = 40, captionKey = "C_Currency_ID", widgetType = DocumentFieldWidgetType.Text)
	private final String currencyCode;

	public static final String FIELD_Qty = "qty";
	@ViewColumn(seqNo = 50, fieldName = FIELD_Qty, captionKey = "Qty", widgetType = DocumentFieldWidgetType.Quantity, editor = ViewEditorRenderMode.ALWAYS)
	@Getter
	private final BigDecimal qty;

	@ViewColumn(seqNo = 60, captionKey = "LastShipmentDays", widgetType = DocumentFieldWidgetType.Integer)
	private final Integer lastShipmentDays;

	private final DocumentId id;
	private ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy

	@Builder(toBuilder = true)
	private ProductsProposalRow(
			@NonNull final DocumentId id,
			@NonNull final LookupValue product,
			@Nullable final String asiDescription,
			@NonNull final Amount price,
			@Nullable final BigDecimal qty,
			@Nullable final Integer lastShipmentDays)
	{
		this.id = id;
		this.product = product;
		this.asiDescription = asiDescription;
		this.price = price;
		this.currencyCode = price.getCurrencyCode();
		this.qty = qty;
		this.lastShipmentDays = lastShipmentDays;
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
}
