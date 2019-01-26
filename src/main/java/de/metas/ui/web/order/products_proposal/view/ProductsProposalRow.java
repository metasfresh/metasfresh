package de.metas.ui.web.order.products_proposal.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

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
	@ViewColumn(captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup, seqNo = 10)
	private final LookupValue product;
	
	// TODO ASI column 

	// Show price with currency
	@ViewColumn(captionKey = "Price", widgetType = DocumentFieldWidgetType.Amount, seqNo = 20)
	private final Amount price;

	public static final String FIELD_Qty = "qty";
	@ViewColumn(fieldName = FIELD_Qty, captionKey = "Qty", widgetType = DocumentFieldWidgetType.Quantity, seqNo = 30, editor = ViewEditorRenderMode.ALWAYS)
	@Getter
	private final BigDecimal qty;

	// TODO: change it to "last shipment days" = now() - last shipment date
	@ViewColumn(captionKey = "LastShipmentDate", widgetType = DocumentFieldWidgetType.Amount, seqNo = 40)
	private final LocalDate lastShipmentDate;

	private final DocumentId id;
	private ImmutableMap<String, Object> _fieldNameAndJsonValues; // lazy

	@Builder(toBuilder = true)
	private ProductsProposalRow(
			@NonNull final DocumentId id,
			@NonNull final LookupValue product,
			@NonNull final Amount price,
			@Nullable final BigDecimal qty,
			@Nullable final LocalDate lastShipmentDate)
	{
		this.id = id;
		this.product = product;
		this.price = price;
		this.qty = qty;
		this.lastShipmentDate = lastShipmentDate;
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

	public boolean isQtySet()
	{
		final BigDecimal qty = getQty();
		return qty != null && qty.signum() != 0;
	}
}
