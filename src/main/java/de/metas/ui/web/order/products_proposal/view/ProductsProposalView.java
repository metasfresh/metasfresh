package de.metas.ui.web.order.products_proposal.view;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.order.OrderId;
import de.metas.ui.web.document.filter.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.AbstractCustomView;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.Builder;
import lombok.NonNull;

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

public class ProductsProposalView extends AbstractCustomView<ProductsProposalRow> implements IEditableView
{
	public static ProductsProposalView cast(final Object viewObj)
	{
		return (ProductsProposalView)viewObj;
	}

	private final ProductsProposalRowsData rowsData;

	@Builder
	private ProductsProposalView(
			@NonNull final WindowId windowId,
			@NonNull final ProductsProposalRowsData rowsData)
	{
		super(
				ViewId.random(windowId),
				ITranslatableString.empty(),
				rowsData,
				NullDocumentFilterDescriptorsProvider.instance);

		this.rowsData = rowsData;
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return null;
	}

	@Override
	public LookupValuesList getFieldTypeahead(final RowEditingContext ctx, final String fieldName, final String query)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList getFieldDropdown(final RowEditingContext ctx, final String fieldName)
	{
		throw new UnsupportedOperationException();
	}

	public OrderId getOrderId()
	{
		return rowsData.getOrderId();
	}

	public List<ProductsProposalRow> getRowsWithQtySet()
	{
		return getRows()
				.stream()
				.filter(ProductsProposalRow::isQtySet)
				.collect(ImmutableList.toImmutableList());
	}
}
