/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.contract.flatrate.view;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.model.I_C_Flatrate_DataEntry_Detail;
import de.metas.i18n.ITranslatableString;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.contract.flatrate.model.DataEntryDetailsRow;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class DataEntryDetailsView extends AbstractCustomView<DataEntryDetailsRow> implements IEditableView
{
	private final ImmutableList<RelatedProcessDescriptor> processes;
	
	@Builder
	protected DataEntryDetailsView(
			@NonNull final WindowId windowId,
			@Nullable final ITranslatableString description, 
			@NonNull final IRowsData<DataEntryDetailsRow> rowsData, 
			@NonNull final ImmutableList<RelatedProcessDescriptor> processes)
	{
		super(ViewId.random(windowId), description, rowsData, NullDocumentFilterDescriptorsProvider.instance);
		this.processes = processes;
	}

	public static DataEntryDetailsView cast(@NonNull final Object viewObj)
	{
		return (DataEntryDetailsView)viewObj;
	}

	@Override
	public LookupValuesPage getFieldTypeahead(final RowEditingContext ctx, final String fieldName, final String query)
	{
		return null;
	}

	@Override
	public LookupValuesList getFieldDropdown(final RowEditingContext ctx, final String fieldName)
	{
		return null;
	}

	@Nullable
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId_ignored)
	{
		return I_C_Flatrate_DataEntry_Detail.Table_Name;
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return processes;
	}
}
