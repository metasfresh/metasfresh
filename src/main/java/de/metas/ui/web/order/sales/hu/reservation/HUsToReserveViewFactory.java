package de.metas.ui.web.order.sales.hu.reservation;

import org.adempiere.util.Services;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorViewBuilder;
import de.metas.ui.web.handlingunits.HUEditorViewFactoryTemplate;
import de.metas.ui.web.handlingunits.SqlHUEditorViewRepository.SqlHUEditorViewRepositoryBuilder;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper.ClassViewColumnOverrides;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.MediaType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import lombok.NonNull;

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

/**
 * TODO: make sure that the SQL created for this HU-editor view is restricted to HUs that are available for a given sales order.
 * Caveat: not yet sure if HUQuery is enough..at least right now, HUQueryBuilder doesn't cover "reservations".
 * We need to decide if we want to add that; problably yes,
 * bc we need to deal with reserved HUs at many places..e.g. we need to exclude them in picking or production as well...
 * *unless* of course the picking is done for the sales order they were reserved for.
 *
 * So, maybe it makes sense to have not a "HUsToReserveViewFactory", but a "HUsThatMatchHuQueryBuilderViewFactory".
 */
@ViewFactory(windowId = HUsToReserveViewFactory.WINDOW_ID_STRING, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class HUsToReserveViewFactory extends HUEditorViewFactoryTemplate
{
	public static final String WINDOW_ID_STRING = "husToReserve";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private final transient IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	public HUsToReserveViewFactory()
	{
		super(ImmutableList.of());
	}

	@Override
	protected DocumentFilterDescriptorsProvider createFilterDescriptorsProvider()
	{
		return ImmutableDocumentFilterDescriptorsProvider.builder()
				.addDescriptors(super.createFilterDescriptorsProvider())
				.build();
	}

	// @Override
	// protected Map<String, SqlDocumentFilterConverter> createFilterConvertersIndexedByFilterId()
	// {
	// final Map<String, SqlDocumentFilterConverter> converters = new HashMap<>();
	// converters.putAll(super.createFilterConvertersIndexedByFilterId());
	// return converters;
	// }

	@Override
	protected void customizeViewLayout(
			@NonNull final ViewLayout.Builder viewLayoutBuilder,
			final JSONViewDataType IGNORED)
	{
		viewLayoutBuilder
				.clearElements()
				.addElementsFromViewRowClassAndFieldNames(HUEditorRow.class,
						ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HUCode).restrictToMediaType(MediaType.SCREEN).build(),
						ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_Product),
						ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HU_UnitType).restrictToMediaType(MediaType.SCREEN).build(),
						ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_PackingInfo).restrictToMediaType(MediaType.SCREEN).build(),
						ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_QtyCU),
						ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_UOM),
						ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HUStatus).restrictToMediaType(MediaType.SCREEN).build(),
						ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_BestBeforeDate),
						ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_Locator));
	}

	@Override
	protected void customizeHUEditorViewRepository(@NonNull final SqlHUEditorViewRepositoryBuilder huEditorViewRepositoryBuilder)
	{
		huEditorViewRepositoryBuilder
				.showBestBeforeDate(true)
				.showLocator(true);
	}

	@Override
	protected void customizeHUEditorView(@NonNull final HUEditorViewBuilder huViewBuilder)
	{
		huViewBuilder
				.addAdditionalRelatedProcessDescriptor(createProcessDescriptor(de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Make_HuReservation.class))
				.clearOrderBys()
				.orderBy(DocumentQueryOrderBy.builder().fieldName(HUEditorRow.FIELDNAME_BestBeforeDate).ascending(true).nullsLast(true).build())
				.orderBy(DocumentQueryOrderBy.byFieldName(HUEditorRow.FIELDNAME_M_HU_ID));
	}

	private RelatedProcessDescriptor createProcessDescriptor(@NonNull final Class<?> processClass)
	{
		return RelatedProcessDescriptor.builder()
				.processId(adProcessDAO.retriveProcessIdByClassIfUnique(Env.getCtx(), processClass))
				.webuiQuickAction(true)
				.build();
	}
}
