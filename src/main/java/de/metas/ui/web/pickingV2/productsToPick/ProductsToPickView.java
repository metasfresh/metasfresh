package de.metas.ui.web.pickingV2.productsToPick;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.i18n.ITranslatableString;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsData;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

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

public class ProductsToPickView extends AbstractCustomView<ProductsToPickRow> implements IEditableView
{
	public static ProductsToPickView cast(final IView view)
	{
		return (ProductsToPickView)view;
	}

	private final ImmutableList<RelatedProcessDescriptor> relatedProcessDescriptors;
	private final ProductsToPickRowsData rowsData;
	private final ViewHeaderProperties headerProperties;

	@Builder
	private ProductsToPickView(
			@NonNull final ViewId viewId,
			final ITranslatableString description,
			final ViewHeaderProperties headerProperties,
			@NonNull final ProductsToPickRowsData rowsData,
			@NonNull @Singular final ImmutableList<RelatedProcessDescriptor> relatedProcessDescriptors)
	{
		super(viewId, description, rowsData, NullDocumentFilterDescriptorsProvider.instance);

		this.rowsData = rowsData;
		this.relatedProcessDescriptors = relatedProcessDescriptors;

		this.headerProperties = headerProperties;
	}

	@Override
	public ViewHeaderProperties getHeaderProperties()
	{
		return headerProperties;
	}

	@Override
	public DocumentQueryOrderByList getDefaultOrderBys()
	{
		return rowsData.getOrderBys();
	}

	@Override
	public boolean isAllowClosingPerUserRequest()
	{
		// don't allow closing per user request because the same view is used the the Picker and the Reviewer.
		// So the first one which is closing the view would delete it.
		return false;
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return relatedProcessDescriptors;
	}

	public boolean isEligibleForReview()
	{
		if (size() == 0)
		{
			return false;
		}

		final boolean allApproved = streamByIds(DocumentIdsSelection.ALL).allMatch(ProductsToPickRow::isApproved);
		if (allApproved)
		{
			return false;
		}

		return streamByIds(DocumentIdsSelection.ALL)
				.allMatch(ProductsToPickRow::isEligibleForReview);
	}

	public void updateViewRowFromPickingCandidate(@NonNull final DocumentId rowId, @NonNull final PickingCandidate pickingCandidate)
	{
		rowsData.updateViewRowFromPickingCandidate(rowId, pickingCandidate);
	}

	public boolean isApproved()
	{
		if (size() == 0)
		{
			return false;
		}

		return streamByIds(DocumentIdsSelection.ALL)
				.allMatch(ProductsToPickRow::isApproved);
	}

	@Override
	public LookupValuesList getFieldTypeahead(RowEditingContext ctx, String fieldName, String query)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList getFieldDropdown(RowEditingContext ctx, String fieldName)
	{
		throw new UnsupportedOperationException();
	}
}
