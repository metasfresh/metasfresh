package de.metas.ui.web.material.cockpit;

import java.util.List;
import java.util.function.Supplier;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_S_Resource;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.fresh.model.I_X_MRP_ProductInfo_V;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.ViewLayout.Builder;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
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
@ViewFactory(windowId = MaterialCockpitConstants.WINDOWID_MaterialCockpitView_String, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class MaterialCockpitViewFactory implements IViewFactory
{
	private final MaterialCockpitRowRepository materialCockpitRowRepository;

	private final MaterialCockpitFilters materialCockpitFilters;

	public MaterialCockpitViewFactory(
			@NonNull final MaterialCockpitRowRepository materialCockpitRowRepository,
			@NonNull final MaterialCockpitFilters materialCockpitFilters)
	{
		this.materialCockpitRowRepository = materialCockpitRowRepository;
		this.materialCockpitFilters = materialCockpitFilters;
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		assertWindowIdOfRequestIsCorrect(request);


		final ImmutableList<DocumentFilter> requestFilters = materialCockpitFilters.extractDocumentFilters(request);
		final ImmutableList<DocumentFilter> filtersToUse = request.isUseAutoFilters() ? createAutoFilters() : requestFilters;

		final Supplier<List<MaterialCockpitRow>> rowsSupplier = createRowsSupplier(filtersToUse);

		return new MaterialCockpitView(
				request.getViewId(),
				ITranslatableString.empty(),
				rowsSupplier,
				filtersToUse); // TODO take the filters from the request and append mine
	}

	private ImmutableList<DocumentFilter> createAutoFilters()
	{
		// filter need to be created by the factory and added to this view when it's created
		final String columnName = I_X_MRP_ProductInfo_V.COLUMNNAME_DateGeneral;
		final ITranslatableString filterCaption = Services.get(IMsgBL.class).translatable(columnName);
		final DocumentFilter dateFilter = DocumentFilter.builder()
				.setFilterId("materialCockpitFilter") // TODO shut the fuck up
				.setCaption(filterCaption)
				.addParameter(DocumentFilterParam.ofNameOperatorValue(columnName, Operator.EQUAL, Env.getDate(Env.getCtx())))
				.build();

		return ImmutableList.of(dateFilter);
	}

	private void assertWindowIdOfRequestIsCorrect(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		final WindowId windowId = viewId.getWindowId();

		Check.errorUnless(MaterialCockpitConstants.WINDOWID_MaterialCockpitView.equals(windowId),
				"The parameter request needs to have WindowId={}, but has {} instead; request={};",
				MaterialCockpitConstants.WINDOWID_MaterialCockpitView, windowId, request);
	}

	private Supplier<List<MaterialCockpitRow>> createRowsSupplier(@NonNull final List<DocumentFilter> filtersToUse)
	{
		// TODO: get the row ids for the request's filters AND our date-filter, if we added it

		final Supplier<List<MaterialCockpitRow>> rowsSupplier = () -> materialCockpitRowRepository.retrieveRowsByIds(filtersToUse);
		return rowsSupplier;
	}

	@Override
	public ViewLayout getViewLayout(@NonNull final WindowId windowId, @NonNull final JSONViewDataType viewDataType)
	{
		Check.errorUnless(MaterialCockpitConstants.WINDOWID_MaterialCockpitView.equals(windowId),
				"The parameter windowId needs to be {}, but is {} instead; viewDataType={}; ",
				MaterialCockpitConstants.WINDOWID_MaterialCockpitView, windowId, viewDataType);

		final Builder viewlayOutBuilder = ViewLayout.builder()
				.setWindowId(windowId)
				.setHasTreeSupport(true)
				.setTreeCollapsible(true)
				.setTreeExpandedDepth(ViewLayout.TreeExpandedDepth_AllCollapsed)
				.addElementsFromViewRowClass(MaterialCockpitRow.class, viewDataType)
				.setFilters(materialCockpitFilters.getFilterDescriptors());

		addProductionPlantColumns(viewlayOutBuilder);

		return viewlayOutBuilder.build();
	}

	private void addProductionPlantColumns(final Builder viewlayOutBuilder)
	{
		final List<I_S_Resource> plants = Services.get(IQueryBL.class)
				.createQueryBuilder(I_S_Resource.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Resource.COLUMNNAME_ManufacturingResourceType, X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant)
				.orderBy().addColumn(I_S_Resource.COLUMNNAME_Name).endOrderBy()
				.create()
				.list();

		for (final I_S_Resource plant : plants)
		{
			final DocumentLayoutElementDescriptor.Builder elementBuilder = DocumentLayoutElementDescriptor.builder()
					.setCaption(ITranslatableString.constant(plant.getName()))
					.setWidgetType(DocumentFieldWidgetType.Text)
					.setGridElement()
					.setViewEditorRenderMode(ViewEditorRenderMode.NEVER)
					.addField(DocumentLayoutElementFieldDescriptor.builder(createFieldNameForPlant(plant)));

			viewlayOutBuilder.addElement(elementBuilder);
		}
	}

	public static String createFieldNameForPlant(@NonNull final I_S_Resource plant)
	{
		return "Fresh_QtyOnHand_OnDate_" + plant.getValue();
	}

}
