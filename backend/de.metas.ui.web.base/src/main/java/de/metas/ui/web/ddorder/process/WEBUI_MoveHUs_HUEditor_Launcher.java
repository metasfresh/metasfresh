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

package de.metas.ui.web.ddorder.process;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;
import org.eevolution.api.DDOrderLineId;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_OrderLine;

import static de.metas.ui.web.ddorder.HUsToMoveViewFactory.WINDOW_ID;
import static de.metas.ui.web.ddorder.HUsToMoveViewFactory.WINDOW_ID_STRING;

public class WEBUI_MoveHUs_HUEditor_Launcher extends ViewBasedProcessTemplate implements IProcessPrecondition
{

	private final IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);
	private final IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (getSelectedRowIds().isMoreThanOneDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override protected String doIt() throws Exception
	{
		final I_DD_OrderLine ddOrderLine = ddOrderDAO.getLineById(DDOrderLineId.ofRepoId(getRecord_ID()));

		final IView husToMove = createHUEditor(ddOrderLine);

		getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
				.viewId(husToMove.getViewId().getViewId())
				.profileId(WINDOW_ID_STRING)
				.target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
				.build());

		return MSG_OK;
	}

	private IView createHUEditor(final I_DD_OrderLine orderLine)
	{
		final IHUQueryBuilder huQuery = createHUQuery(orderLine);

		final CreateViewRequest request = CreateViewRequest.builder(WINDOW_ID, JSONViewDataType.includedView)
				.addStickyFilters(HUIdsFilterHelper.createFilter(huQuery))
				.setParentViewId(getView().getViewId())
				.setParentRowId(getSelectedRowIds().getSingleDocumentId())
				.build();

		return viewsRepo.createView(request);
	}

	private IHUQueryBuilder createHUQuery(final I_DD_OrderLine orderLine)
	{
		return handlingUnitsDAO
				.createHUQueryBuilder()
				.onlyNotLocked() // not already locked (NOTE: those which were enqueued to Transportation Order are locked)
				.addOnlyInLocatorId(orderLine.getM_Locator_ID())
				.addOnlyWithProductId(ProductId.ofRepoId(orderLine.getM_Product_ID()))
				.setOnlyActiveHUs(true)
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active);
	}
}
