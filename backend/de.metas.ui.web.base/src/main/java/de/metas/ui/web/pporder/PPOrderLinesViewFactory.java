/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ADReferenceService;
import de.metas.cache.CCache;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.ui.web.handlingunits.DefaultHUEditorViewFactory;
import de.metas.ui.web.pattribute.ASIRepository;
import de.metas.ui.web.view.ASIViewRowAttributesProvider;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.IncludedViewLayout;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.factory.standard.LayoutFactory;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@ViewFactory(windowId = PPOrderConstants.AD_WINDOW_ID_IssueReceipt_String)
public class PPOrderLinesViewFactory implements IViewFactory
{
	@NonNull private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private static final AdMessageKey MANUFACTURING_ISSUE_RECEIPT_CAPTION = AdMessageKey.of("de.metas.ui.web.pporder.MANUFACTURING_ISSUE_RECEIPT_CAPTION");

	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final ASIRepository asiRepository;
	@NonNull private final DefaultHUEditorViewFactory huEditorViewFactory;
	@NonNull private final HUReservationService huReservationService;
	@NonNull private final ADReferenceService adReferenceService;

	private final transient CCache<WindowId, ViewLayout> layouts = CCache.newLRUCache("PPOrderLinesViewFactory#Layouts", 10, 0);

	@Override
	public PPOrderLinesView createView(final @NonNull CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(request.getSingleFilterOnlyId());
		final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);
		final boolean hasSerialNumberSequence = ppOrderBL.hasSerialNumberSequence(ppOrderId);
		final PPOrderDocBaseType ppOrderDocBaseType = PPOrderDocBaseType.ofCode(ppOrder.getDocBaseType());

		final PPOrderLinesViewDataSupplier dataSupplier = PPOrderLinesViewDataSupplier
				.builder()
				.viewWindowId(viewId.getWindowId())
				.ppOrderId(ppOrderId)
				.asiAttributesProvider(ASIViewRowAttributesProvider.newInstance(asiRepository))
				.serialNoFromSequence(hasSerialNumberSequence)
				.huSQLViewBinding(huEditorViewFactory.getSqlViewBinding())
				.huReservationService(huReservationService)
				.adReferenceService(adReferenceService)
				.build();

		return PPOrderLinesView.builder()
				.parentViewId(request.getParentViewId())
				.parentRowId(request.getParentRowId())
				.viewId(viewId)
				.viewType(request.getViewType())
				.referencingDocumentPaths(request.getReferencingDocumentPaths())
				.ppOrderId(ppOrderId)
				.docBaseType(ppOrderDocBaseType)
				.dataSupplier(dataSupplier)
				.additionalRelatedProcessDescriptors(createAdditionalRelatedProcessDescriptors())
				.build();
	}

	@Override
	public IView filterView(
			@NonNull final IView view,
			@NonNull final JSONFilterViewRequest filterViewRequest,
			@NonNull final Supplier<IViewsRepository> viewsRepo)
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

	private ViewLayout createViewLayout(final WindowId windowId)
	{
		return ViewLayout.builder()
				.setWindowId(windowId)
				.setCaption(msgBL.getTranslatableMsgText(MANUFACTURING_ISSUE_RECEIPT_CAPTION))
				.setEmptyResultText(msgBL.getTranslatableMsgText(LayoutFactory.TAB_EMPTY_RESULT_TEXT))
				.setEmptyResultHint(msgBL.getTranslatableMsgText(LayoutFactory.TAB_EMPTY_RESULT_HINT))
				//
				.setHasAttributesSupport(true)
				.setHasTreeSupport(true)
				.setIncludedViewLayout(IncludedViewLayout.DEFAULT)
				//
				.addElementsFromViewRowClass(PPOrderLineRow.class, JSONViewDataType.grid)
				.setAllowOpeningRowDetails(false)
				//
				.build();
	}

	private List<RelatedProcessDescriptor> createAdditionalRelatedProcessDescriptors()
	{
		return ImmutableList.of(
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Receipt.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Pick.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_IssueServiceProduct.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_ReverseCandidate.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_ChangePlanningStatus_Planning.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_ChangePlanningStatus_Review.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_ChangePlanningStatus_Complete.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_HUEditor_Launcher.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_M_Source_HU_Delete.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_M_Source_HU_IssueTuQty.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_M_Source_HU_IssueCUQty.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_PrintLabel.class),
				createProcessDescriptorForIssueReceiptWindow(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Pick_ReceivedHUs.class));

	}

	private RelatedProcessDescriptor createProcessDescriptorForIssueReceiptWindow(@NonNull final Class<?> processClass)
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(processClass);

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.windowId(PPOrderConstants.AD_WINDOW_ID_IssueReceipt.toAdWindowIdOrNull())
				.anyTable()
				.displayPlace(DisplayPlace.ViewQuickActions)
				.build();
	}
}
