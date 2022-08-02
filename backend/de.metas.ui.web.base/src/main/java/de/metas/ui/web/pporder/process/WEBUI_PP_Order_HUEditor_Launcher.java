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

package de.metas.ui.web.pporder.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.handlingunits.filter.HUIdsFilterHelper;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.eevolution.api.BOMComponentIssueMethod;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.util.List;

/**
 * This process opens a HU editor window within the production window
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class WEBUI_PP_Order_HUEditor_Launcher
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{
	private final IHUPPOrderBL huppOrderBL = Services.get(IHUPPOrderBL.class);
	private final IPPOrderBOMDAO orderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();

		}

		final PPOrderLineRow singleSelectedRow = getSingleSelectedRow();

		if (!singleSelectedRow.isIssue())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not an issue row");
		}

		final ProductId productId = singleSelectedRow.getProductId();
		if (productId == null || !productBL.isStocked(productId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a stockable product");
		}

		final PPOrderLinesView ppOrderLineView = getView();

		/*
		 * The IssueMethod IssueOnlyForReceived is designed for BOM components that are added only after the main product was received.
		 * For example, if a BOM consists in several products that will, in the end, be wrapped together, the "wrap" product will have this issue method.
		 * This means that after all the products needed for the BOM are received, they will be wrapped with a piece of wrap that is big enough to fit them all and we will issue only the needed quantity of it.
		 */
		final boolean isIssueOnlyWhatWasReceived = BOMComponentIssueMethod.IssueOnlyForReceived.equals(singleSelectedRow.getIssueMethod());
		if (isIssueOnlyWhatWasReceived)
		{
			// this action shall always be available for lines with issue method IssueOnlyWhatWasReceived, no matter the pp_Order status or if the line was processed.
			return ProcessPreconditionsResolution.accept();

		}
		if (!ppOrderLineView.isStatusPlanning() && !ppOrderLineView.isStatusReview())
		{
			final String internalReason = StringUtils.formatMessage("The PP_Order of the the current ppOrderLineView is not in planning or in review; ppOrderLineView={}", ppOrderLineView);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}

		if (singleSelectedRow.isProcessed())
		{
			final String internalReason = StringUtils.formatMessage("The selected ppOrderLineRow is already flagged as processed; selectedRow={}", singleSelectedRow);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final PPOrderLineRow ppOrderLineRow = getSingleSelectedRow();

		final PPOrderBOMLineId ppOrderBomLineId = ppOrderLineRow.getOrderBOMLineId();

		final ViewId ppOrderLineViewId = getView().getViewId();

		final List<HuId> availableHUsIDs = retrieveHuIdsToShowInEditor(ppOrderBomLineId);

		final IView husToPickView = getViewsRepo().createView(
				CreateViewRequest.builder(WEBUI_HU_Constants.WEBUI_HU_Window_ID, JSONViewDataType.includedView)
						.setParentViewId(ppOrderLineViewId)
						.setParentRowId(ppOrderLineRow.getId())
						.addStickyFilters(HUIdsFilterHelper.createFilter(availableHUsIDs))
						.addAdditionalRelatedProcessDescriptor(createIssueTopLevelHusDescriptor())
						.addAdditionalRelatedProcessDescriptor(createIssueTUsDescriptor())
						.addAdditionalRelatedProcessDescriptor(createSelectHuAsSourceHuDescriptor())
						.build());

		getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
				.viewId(husToPickView.getViewId().getViewId())
				.target(ViewOpenTarget.IncludedView)
				.build());

		return MSG_OK;
	}

	@NonNull
	private List<HuId> retrieveHuIdsToShowInEditor(final PPOrderBOMLineId ppOrderBomLineId)
	{
		final I_PP_Order_BOMLine ppOrderBomLine = orderBOMsRepo.getOrderBOMLineById(ppOrderBomLineId);

		return huppOrderBL.retrieveAvailableToIssue(ppOrderBomLine);
	}

	private RelatedProcessDescriptor createIssueTopLevelHusDescriptor()
	{
		return RelatedProcessDescriptor.builder()
				.processId(adProcessDAO.retrieveProcessIdByClassIfUnique(WEBUI_PP_Order_HUEditor_IssueTopLevelHUs.class))
				.displayPlace(DisplayPlace.ViewQuickActions)
				.build();
	}

	private RelatedProcessDescriptor createSelectHuAsSourceHuDescriptor()
	{
		return RelatedProcessDescriptor.builder()
				.processId(adProcessDAO.retrieveProcessIdByClassIfUnique(WEBUI_PP_Order_HUEditor_Create_M_Source_HUs.class))
				.displayPlace(DisplayPlace.ViewQuickActions)
				.build();
	}

	private RelatedProcessDescriptor createIssueTUsDescriptor()
	{
		return RelatedProcessDescriptor.builder()
				.processId(adProcessDAO.retrieveProcessIdByClassIfUnique(WEBUI_PP_Order_HUEditor_IssueTUs.class))
				.displayPlace(DisplayPlace.ViewQuickActions)
				.build();
	}

	@Override
	protected PPOrderLinesView getView()
	{
		return PPOrderLinesView.cast(super.getView());
	}

	@Override
	protected PPOrderLineRow getSingleSelectedRow()
	{
		return PPOrderLineRow.cast(super.getSingleSelectedRow());
	}

}
