package de.metas.ui.web.material.cockpit;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.material.cockpit.process.MD_Cockpit_DocumentDetail_Display;
import de.metas.ui.web.process.view.ViewActionDescriptorsFactory;
import de.metas.ui.web.process.view.ViewActionDescriptorsList;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewZoomIntoFieldSupport;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.lookup.zoom_into.DocumentZoomIntoInfo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class MaterialCockpitView extends AbstractCustomView<MaterialCockpitRow> implements IViewZoomIntoFieldSupport
{
	public static MaterialCockpitView cast(final IView view)
	{
		return (MaterialCockpitView)view;
	}

	private final DocumentFilterList filters;

	private final List<RelatedProcessDescriptor> relatedProcessDescriptors;

	@Builder
	private MaterialCockpitView(
			@NonNull final ViewId viewId,
			@NonNull final ITranslatableString description,
			@NonNull final IRowsData<MaterialCockpitRow> rowsData,
			@NonNull final DocumentFilterList filters,
			@NonNull final DocumentFilterDescriptorsProvider filterDescriptors,
			@Singular final List<RelatedProcessDescriptor> relatedProcessDescriptors)
	{
		super(viewId,
				description,
				rowsData,
				filterDescriptors);

		this.filters = filters;
		this.relatedProcessDescriptors = ImmutableList.copyOf(relatedProcessDescriptors);
	}

	/**
	 * @return {@code null}, because each record of this view is based on > 1 tables.
	 */
	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return null;
	}

	@Override
	public DocumentFilterList getFilters()
	{
		return filters;
	}

	@Override
	public DocumentQueryOrderByList getDefaultOrderBys()
	{
		return DocumentQueryOrderByList.ofList(
				ImmutableList.of(
						DocumentQueryOrderBy.byFieldName(I_MD_Cockpit.COLUMNNAME_QtyStockEstimateSeqNo_AtDate),
						DocumentQueryOrderBy.byFieldName(I_MD_Cockpit.COLUMNNAME_ProductValue))
		);
	}

	@Override
	protected boolean isEligibleInvalidateEvent(final TableRecordReference recordRef)
	{
		final String tableName = recordRef.getTableName();
		return I_MD_Cockpit.Table_Name.equals(tableName)
				|| I_MD_Stock.Table_Name.equals(tableName);
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return relatedProcessDescriptors;
	}

	@Override
	public ViewActionDescriptorsList getActions()
	{
		return ViewActionDescriptorsFactory.instance
				.getFromClass(MD_Cockpit_DocumentDetail_Display.class);
	}

	@Override
	public DocumentZoomIntoInfo getZoomIntoInfo(@NonNull final DocumentId rowId, @NonNull final String fieldName)
	{
		return getById(rowId).getZoomIntoInfo(fieldName);
	}
}
