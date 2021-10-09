package de.metas.ui.web.material.cockpit;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;

import de.metas.i18n.TranslatableStrings;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.material.cockpit.filters.MaterialCockpitFilters;
import de.metas.ui.web.material.cockpit.process.MD_Cockpit_DocumentDetail_Display;
import de.metas.ui.web.material.cockpit.process.MD_Cockpit_PricingConditions;
import de.metas.ui.web.material.cockpit.process.MD_Cockpit_ShowStockDetails;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.ViewLayout.Builder;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.factory.standard.DefaultDocumentDescriptorFactory;
import de.metas.util.Check;
import de.metas.util.Services;
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
@ViewFactory(windowId = MaterialCockpitUtil.WINDOWID_MaterialCockpitView_String, //
		viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class MaterialCockpitViewFactory
		implements IViewFactory
{

	/** Please keep its prefix in sync with {@link MaterialCockpitRow#SYSCFG_PREFIX} */
	public static final String SYSCFG_DisplayIncludedRows = "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.DisplayIncludedRows";

	private final MaterialCockpitRowRepository materialCockpitRowRepository;

	private final MaterialCockpitFilters materialCockpitFilters;

	public MaterialCockpitViewFactory(
			@NonNull final MaterialCockpitRowRepository materialCockpitRowRepository,
			@NonNull final MaterialCockpitFilters materialCockpitFilters,
			@NonNull final DefaultDocumentDescriptorFactory defaultDocumentDescriptorFactory)
	{
		this.materialCockpitRowRepository = materialCockpitRowRepository;
		this.materialCockpitFilters = materialCockpitFilters;

		defaultDocumentDescriptorFactory.addUnsupportedWindowId(MaterialCockpitUtil.WINDOWID_MaterialCockpitView);
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		assertWindowIdOfRequestIsCorrect(request);

		final DocumentFilterDescriptorsProvider filterDescriptors = materialCockpitFilters.getFilterDescriptors();
		final DocumentFilterList requestFilters = materialCockpitFilters.extractDocumentFilters(request);
		final DocumentFilterList filtersToUse = request.isUseAutoFilters() ? materialCockpitFilters.createAutoFilters() : requestFilters;

		final MaterialCockpitView view = MaterialCockpitView.builder()
				.viewId(request.getViewId())
				.description(TranslatableStrings.empty())
				.filters(filtersToUse)
				.filterDescriptors(filterDescriptors)
				.rowsData(materialCockpitRowRepository.createRowsData(filtersToUse))
				.relatedProcessDescriptor(createProcessDescriptor(MD_Cockpit_DocumentDetail_Display.class))
				.relatedProcessDescriptor(createProcessDescriptor(MD_Cockpit_PricingConditions.class))
				.relatedProcessDescriptor(createProcessDescriptor(MD_Cockpit_ShowStockDetails.class))
				.build();

		return view;
	}

	private void assertWindowIdOfRequestIsCorrect(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		final WindowId windowId = viewId.getWindowId();

		Check.errorUnless(MaterialCockpitUtil.WINDOWID_MaterialCockpitView.equals(windowId),
				"The parameter request needs to have WindowId={}, but has {} instead; request={};",
				MaterialCockpitUtil.WINDOWID_MaterialCockpitView, windowId, request);
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			@Nullable final ViewProfileId profileId)
	{
		Check.errorUnless(MaterialCockpitUtil.WINDOWID_MaterialCockpitView.equals(windowId),
				"The parameter windowId needs to be {}, but is {} instead; viewDataType={}; ",
				MaterialCockpitUtil.WINDOWID_MaterialCockpitView, windowId, viewDataType);

		final boolean displayIncludedRows = Services.get(ISysConfigBL.class).getBooleanValue(SYSCFG_DisplayIncludedRows, true);

		final Builder viewlayOutBuilder = ViewLayout.builder()
				.setWindowId(windowId)
				.setHasTreeSupport(displayIncludedRows)
				.setTreeCollapsible(true)
				.setTreeExpandedDepth(ViewLayout.TreeExpandedDepth_AllCollapsed)
				.setAllowOpeningRowDetails(false)
				.addElementsFromViewRowClass(MaterialCockpitRow.class, viewDataType)
				.setFilters(materialCockpitFilters.getFilterDescriptors().getAll());

		return viewlayOutBuilder.build();
	}

	private final RelatedProcessDescriptor createProcessDescriptor(@NonNull final Class<?> processClass)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(processClass);
		if (processId == null)
		{
			throw new AdempiereException("No processId found for " + processClass);
		}

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.anyTable().anyWindow()
				.displayPlace(DisplayPlace.ViewQuickActions)
				.build();
	}

}
