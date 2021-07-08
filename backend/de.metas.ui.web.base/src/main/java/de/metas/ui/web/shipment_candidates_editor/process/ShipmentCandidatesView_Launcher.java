package de.metas.ui.web.shipment_candidates_editor.process;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableSet;

import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.shipment_candidates_editor.ShipmentCandidatesViewFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class ShipmentCandidatesView_Launcher extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Autowired
	private IViewsRepository viewsFactory;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = getSelectedShipmentScheduleIds();
		if (shipmentScheduleIds.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		final ViewId viewId = viewsFactory.createView(CreateViewRequest.builder(ShipmentCandidatesViewFactory.WINDOWID)
				.setFilterOnlyIds(ShipmentScheduleId.toIntSet(shipmentScheduleIds))
				.build())
				.getViewId();

		getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
				.viewId(viewId.getViewId())
				.target(ViewOpenTarget.ModalOverlay)
				.build());

		return MSG_OK;
	}

	private ImmutableSet<ShipmentScheduleId> getSelectedShipmentScheduleIds()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		else if (selectedRowIds.isAll())
		{
			return getView().streamByIds(DocumentIdsSelection.ALL)
					.map(IViewRow::getId)
					.map(rowId -> ShipmentScheduleId.ofRepoId(rowId.toInt()))
					.collect(ImmutableSet.toImmutableSet());
		}
		else
		{
			return selectedRowIds
				.stream()
				.map(rowId -> ShipmentScheduleId.ofRepoId(rowId.toInt()))
				.collect(ImmutableSet.toImmutableSet());
	}
}
}
