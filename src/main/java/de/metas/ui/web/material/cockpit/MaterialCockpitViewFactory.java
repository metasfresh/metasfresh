package de.metas.ui.web.material.cockpit;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.material.cockpit.filters.MaterialCockpitFilters;
import de.metas.ui.web.material.cockpit.process.MD_Cockpit_DocumentDetail_Display;
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
@ViewFactory(windowId = MaterialCockpitConstants.WINDOWID_MaterialCockpitView_String, //
		viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class MaterialCockpitViewFactory
		implements IViewFactory
{
	private final MaterialCockpitRowRepository materialCockpitRowRepository;

	private final MaterialCockpitFilters materialCockpitFilters;

	public MaterialCockpitViewFactory(
			@NonNull final MaterialCockpitRowRepository materialCockpitRowRepository,
			@NonNull final MaterialCockpitFilters materialCockpitFilters,
			@NonNull final DefaultDocumentDescriptorFactory defaultDocumentDescriptorFactory)
	{
		this.materialCockpitRowRepository = materialCockpitRowRepository;
		this.materialCockpitFilters = materialCockpitFilters;

		defaultDocumentDescriptorFactory.addUnsupportedWindowId(MaterialCockpitConstants.WINDOWID_MaterialCockpitView);
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		assertWindowIdOfRequestIsCorrect(request);

		final ImmutableList<DocumentFilter> requestFilters = materialCockpitFilters.extractDocumentFilters(request);
		final ImmutableList<DocumentFilter> filtersToUse = request.isUseAutoFilters() ? materialCockpitFilters.createAutoFilters() : requestFilters;

		final MaterialCockpitView view = MaterialCockpitView.builder()
				.viewId(request.getViewId())
				.description(ITranslatableString.empty())
				.filters(filtersToUse)
				.rowsData(materialCockpitRowRepository.createRowsData(filtersToUse))
				.relatedProcessDescriptor(createProcessDescriptor())
				.build();

		return view;
	}

	private void assertWindowIdOfRequestIsCorrect(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		final WindowId windowId = viewId.getWindowId();

		Check.errorUnless(MaterialCockpitConstants.WINDOWID_MaterialCockpitView.equals(windowId),
				"The parameter request needs to have WindowId={}, but has {} instead; request={};",
				MaterialCockpitConstants.WINDOWID_MaterialCockpitView, windowId, request);
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			@Nullable final ViewProfileId profileId)
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

		return viewlayOutBuilder.build();
	}

	private RelatedProcessDescriptor createProcessDescriptor()
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

		final int processId = adProcessDAO.retriveProcessIdByClassIfUnique(Env.getCtx(), MD_Cockpit_DocumentDetail_Display.class);
		Preconditions.checkArgument(processId > 0, "No AD_Process_ID found for class %s", MD_Cockpit_DocumentDetail_Display.class);

		final RelatedProcessDescriptor processDescriptor = RelatedProcessDescriptor.builder()
				.processId(processId)
				.webuiQuickAction(true)
				.build();
		return processDescriptor;
	}

}
