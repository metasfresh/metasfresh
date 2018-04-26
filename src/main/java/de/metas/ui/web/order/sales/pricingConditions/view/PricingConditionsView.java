package de.metas.ui.web.order.sales.pricingConditions.view;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.AbstractCustomView;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
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

public class PricingConditionsView extends AbstractCustomView<PricingConditionsRow> implements IEditableView
{
	public static PricingConditionsView cast(final Object viewObj)
	{
		return (PricingConditionsView)viewObj;
	}

	private final PricingConditionsRowData rowsData;
	private final List<RelatedProcessDescriptor> relatedProcessDescriptors;

	@Builder
	private PricingConditionsView(
			final ViewId viewId,
			final PricingConditionsRowData rowsData,
			@Singular final List<RelatedProcessDescriptor> relatedProcessDescriptors)
	{
		// TODO: filtering support: by IsCustomer, IsVendor
		super(viewId, ITranslatableString.empty(), rowsData, NullDocumentFilterDescriptorsProvider.instance);
		this.rowsData = rowsData;
		this.relatedProcessDescriptors = ImmutableList.copyOf(relatedProcessDescriptors);
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

	@Override
	public LookupValuesList getFieldTypeahead(final RowEditingContext ctx, final String fieldName, final String query)
	{
		return getById(ctx.getRowId()).getFieldTypeahead(fieldName, query);
	}

	@Override
	public LookupValuesList getFieldDropdown(final RowEditingContext ctx, final String fieldName)
	{
		return getById(ctx.getRowId()).getFieldDropdown(fieldName);
	}

	public boolean hasEditableRow()
	{
		return rowsData.hasEditableRow();
	}

	public PricingConditionsRow getEditableRow()
	{
		return rowsData.getEditableRow();
	}

	public void patchEditableRow(@NonNull PricingConditionsRowChangeRequest request)
	{
		rowsData.patchEditableRow(request);
	}
}
