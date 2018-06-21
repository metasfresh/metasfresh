package de.metas.ui.web.order.sales.hu.reservation.process;

import java.util.List;
import java.util.Set;

import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.reservation.HuReservationService;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.sales.hu.reservation.HUsToReserveViewFactory;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_C_OrderLineSO_Launch_HUEditor
		extends JavaProcess
		implements IProcessPrecondition
{
	private final HuReservationService huReservationService = Adempiere.getBean(HuReservationService.class);

	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final IWarehouseAdvisor warehouseAdvisor = Services.get(IWarehouseAdvisor.class);

	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final I_C_Order salesOrder = context.getSelectedModel(I_C_Order.class);

		if (!salesOrder.isSOTrx())
		{
			return ProcessPreconditionsResolution
					.rejectWithInternalReason("only sales orders are allowed");
		}

		final String docStatus = salesOrder.getDocStatus();
		final boolean reservationIsPossible = huReservationService
				.getDocstatusesThatAllowReservation()
				.contains(docStatus);
		if (!reservationIsPossible)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("C_Order.DocStatus=" + docStatus);
		}

		final Set<TableRecordReference> salesOrderLines = context.getSelectedIncludedRecords();
		if (salesOrderLines.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (salesOrderLines.size() != 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final List<Integer> availableHUIdsToPick = retrieveAvailableHuIdsForCurrentSalesOrderLine();

		createHUEditorView(availableHUIdsToPick);
		
		
		final String viewId = null; // TODO
		getResult().setWebuiIncludedViewIdToOpen(viewId);

		return MSG_OK;
	}

	private List<Integer> retrieveAvailableHuIdsForCurrentSalesOrderLine()
	{
		final I_C_OrderLine orderLineRecord = ListUtils.singleElement(getSelectedIncludedRecords(I_C_OrderLine.class));

		final I_M_Warehouse orderLineWarehouse = Check.assumeNotNull(
				warehouseAdvisor.evaluateWarehouse(orderLineRecord),
				"For currently selected sales order line, there needs to be a warehouse; C_OrderLine={}", orderLineRecord);

		final WarehouseId warehouseId = WarehouseId.ofRepoId(orderLineWarehouse.getM_Warehouse_ID());

		final List<WarehouseId> warehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(warehouseId);

		final IHUQueryBuilder query = handlingUnitsDAO
				.createHUQueryBuilder()
				.addOnlyWithProductId(orderLineRecord.getM_Product_ID())
				.addOnlyInWarehouseIds(warehouseIds);

		// TODO: consider adding ASI-Id if set, or filter in memory

		return query
				.createQuery()
				.listIds();
	}

	private void createHUEditorView(@NonNull final List<Integer> availableHUIdsToPick)
	{
		final ImmutableList<TableRecordReference> hus = availableHUIdsToPick
				.stream()
				.map(huId -> TableRecordReference.of(I_M_HU.Table_Name, huId))
				.collect(ImmutableList.toImmutableList());

		getResult()
				.setRecordsToOpen(
						hus,
						HUsToReserveViewFactory.WINDOW_ID_STRING);
	}

}
