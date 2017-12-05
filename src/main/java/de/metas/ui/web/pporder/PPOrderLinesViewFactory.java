package de.metas.ui.web.pporder;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.handlingunits.DefaultHUEditorViewFactory;
import de.metas.ui.web.pattribute.ASIRepository;
import de.metas.ui.web.view.ASIViewRowAttributesProvider;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.IncludedViewLayout;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.factory.standard.LayoutFactory;
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

@ViewFactory(windowId = PPOrderConstants.AD_WINDOW_ID_IssueReceipt_String, viewTypes = {})
public class PPOrderLinesViewFactory implements IViewFactory
{
	@Autowired
	private ASIRepository asiRepository;
	@Autowired
	private DefaultHUEditorViewFactory huEditorViewFactory;

	private final transient CCache<WindowId, ViewLayout> layouts = CCache.newLRUCache("PPOrderLinesViewFactory#Layouts", 10, 0);

	@Override
	public PPOrderLinesView createView(final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		final int ppOrderId = request.getSingleFilterOnlyId();

		final PPOrderLinesViewDataSupplier dataSupplier = PPOrderLinesViewDataSupplier.builder()
				.viewWindowId(viewId.getWindowId())
				.ppOrderId(ppOrderId)
				.asiAttributesProvider(ASIViewRowAttributesProvider.newInstance(asiRepository))
				.huSQLViewBinding(huEditorViewFactory.getSqlViewBinding())
				.build();

		return PPOrderLinesView.builder()
				.parentViewId(request.getParentViewId())
				.parentRowId(request.getParentRowId())
				.viewId(viewId)
				.viewType(request.getViewType())
				.referencingDocumentPaths(request.getReferencingDocumentPaths())
				.ppOrderId(ppOrderId)
				.dataSupplier(dataSupplier)
				.additionalRelatedProcessDescriptors(createAdditionalRelatedProcessDescriptors())
				.build();
	}

	@Override
	public IView filterView(final IView view, final JSONFilterViewRequest filterViewRequest)
	{
		throw new AdempiereException("View does not support filtering")
				.setParameter("view", view)
				.setParameter("filterViewRequest", filterViewRequest);
	}

	@Override
	public IView deleteStickyFilter(final IView view, final String filterId)
	{
		throw new AdempiereException("View does not allow removing sticky/static filter")
				.setParameter("view", view)
				.setParameter("filterId", filterId);
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType_NOTUSED, @Nullable final ViewProfileId profileId_NOTUSED)
	{
		return layouts.getOrLoad(windowId, () -> createViewLayout(windowId));
	}

	private final ViewLayout createViewLayout(final WindowId windowId)
	{
		return ViewLayout.builder()
				.setWindowId(windowId)
				.setCaption("PP Order Issue/Receipt")
				.setEmptyResultText(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_HINT)
				//
				.setHasAttributesSupport(true)
				.setHasTreeSupport(true)
				.setIncludedViewLayout(IncludedViewLayout.DEFAULT)
				//
				.addElementsFromViewRowClass(PPOrderLineRow.class, JSONViewDataType.grid)
				//
				.build();
	}

	private List<RelatedProcessDescriptor> createAdditionalRelatedProcessDescriptors()
	{
		return ImmutableList.of(
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Receipt.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_ReverseCandidate.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_ChangePlanningStatus_Planning.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_ChangePlanningStatus_Review.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_ChangePlanningStatus_Complete.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_HUEditor_Launcher.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_M_Source_HU_Delete.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_M_Source_HU_IssueTuQty.class));
	}

	private static RelatedProcessDescriptor createProcessDescriptorForIssueReceiptWindow(@NonNull final Class<?> processClass)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

		final int processId = adProcessDAO.retriveProcessIdByClassIfUnique(Env.getCtx(), processClass);
		Preconditions.checkArgument(processId > 0, "No AD_Process_ID found for %s", processClass);

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.windowId(PPOrderConstants.AD_WINDOW_ID_IssueReceipt.toInt())
				.anyTable()
				.webuiQuickAction(true)
				.build();
	}
}
