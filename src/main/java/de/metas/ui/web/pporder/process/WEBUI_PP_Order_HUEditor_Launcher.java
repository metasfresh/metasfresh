package de.metas.ui.web.pporder.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.List;

import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.process.IADProcessDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.json.JSONViewDataType;

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
 * This process opens a HU editor window within the production window
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_PP_Order_HUEditor_Launcher
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{
	@Autowired
	private IViewsRepository viewsRepo;

	private final transient IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PPOrderLinesView ppOrderLineView = getView();
		if (!(ppOrderLineView.isStatusPlanning() || ppOrderLineView.isStatusReview()))
		{
			final String internalReason = StringUtils.formatMessage("The PP_Order of the the current ppOrderLineView is not in planning or in review; ppOrderLineView={}", ppOrderLineView);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}

		final PPOrderLineRow singleSelectedRow = getSingleSelectedRow();

		if (!singleSelectedRow.isIssue())
		{
			final String internalReason = StringUtils.formatMessage("The selected ppOrderLineRow is not an issue row; selectedRow={}", singleSelectedRow);
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

		final int ppOrderBomLineId = ppOrderLineRow.getPP_Order_BOMLine_ID();

		final ViewId ppOrderLineViewId = getView().getViewId();

		final List<Integer> availableHUsIDs = retrieveHuIdsToShowInEditor(ppOrderBomLineId);

		final IView husToPickView = viewsRepo.createView(
				CreateViewRequest.builder(WEBUI_HU_Constants.WEBUI_HU_Window_ID, JSONViewDataType.includedView)
						.setParentViewId(ppOrderLineViewId)
						.setParentRowId(ppOrderLineRow.getId())
						.addStickyFilters(HUIdsFilterHelper.createFilter(availableHUsIDs))
						.addAdditionalRelatedProcessDescriptor(createIssueTopLevelHusDescriptor())
						.addAdditionalRelatedProcessDescriptor(createIssueTUsDescriptor())
						.addAdditionalRelatedProcessDescriptor(createSelectHuAsSourceHuDescriptor())
						.build());

		getResult().setWebuiIncludedViewIdToOpen(husToPickView.getViewId().getViewId());

		return MSG_OK;
	}

	private List<Integer> retrieveHuIdsToShowInEditor(final int ppOrderBomLineId)
	{
		final I_PP_Order_BOMLine ppOrderBomLine = load(ppOrderBomLineId, I_PP_Order_BOMLine.class);

		final IHUPPOrderBL huppOrderBL = Services.get(IHUPPOrderBL.class);
		final IHUQueryBuilder huIdsToAvailableToIssueQuery = huppOrderBL.createHUsAvailableToIssueQuery(ppOrderBomLine);

		final List<Integer> availableHUsIDs = huIdsToAvailableToIssueQuery.createQuery().listIds().stream()
				.filter(huId -> !SourceHUsService.get().isHuOrAnyParentSourceHu(huId))
				.filter(huId -> !Services.get(IHUPPOrderQtyDAO.class).isHuIdIssued(huId))
				.collect(ImmutableList.toImmutableList());
		return availableHUsIDs;
	}

	private RelatedProcessDescriptor createIssueTopLevelHusDescriptor()
	{
		return RelatedProcessDescriptor.builder()
				.processId(adProcessDAO.retriveProcessIdByClassIfUnique(Env.getCtx(), WEBUI_PP_Order_HUEditor_IssueTopLevelHUs.class))
				.webuiQuickAction(true)
				.build();
	}

	private RelatedProcessDescriptor createSelectHuAsSourceHuDescriptor()
	{
		return RelatedProcessDescriptor.builder()
				.processId(adProcessDAO.retriveProcessIdByClassIfUnique(Env.getCtx(), WEBUI_PP_Order_HUEditor_Create_M_Source_HUs.class))
				.webuiQuickAction(true)
				.build();
	}

	private RelatedProcessDescriptor createIssueTUsDescriptor()
	{
		return RelatedProcessDescriptor.builder()
				.processId(adProcessDAO.retriveProcessIdByClassIfUnique(Env.getCtx(), WEBUI_PP_Order_HUEditor_IssueTUs.class))
				.webuiQuickAction(true)
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
