package de.metas.inout.api;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;


import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
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

// TODO rename
public class ReceiptLineFindForwardToLocatorTool
{
	private ReceiptLineFindForwardToLocatorTool()
	{
	}

	public static LocatorId findLocatorIdOrNull(
			@NonNull final I_M_InOutLine receiptLine,
			@Nullable final LocatorId locatorToId)
	{
		// In case the line is in dispute, use the Warehouse for Issues as destination warehouse (see 06365)
		// NOTE: we apply this rule only where and not in general, because general we don't want to do this for every warehouse.
		if (create(receiptLine, de.metas.inout.model.I_M_InOutLine.class).isInDispute())
		{
			// TODO: output nice message whenever I_M_InOutLine.isInDispute is set to true
			final I_M_Warehouse warehouseForIssues = Services.get(IWarehouseDAO.class).retrieveWarehouseForIssuesOrNull(Env.getCtx());
			Check.assumeNotNull(warehouseForIssues, "Warehouse for issues shall be defined");
			final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
			final LocatorId locatorIdForIssues = warehouseBL.getDefaultLocatorId(WarehouseId.ofRepoId(warehouseForIssues.getM_Warehouse_ID()));

			return locatorIdForIssues;
		}

		final LocatorId effectiveLocatorToId = coalesceSuppliers(
				() -> locatorToId,
				() -> ReceiptLineFindForwardToLocatorTool.findDestinationLocatorOrNull(receiptLine));

		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
		final LocatorId receiptLineLocatorId = warehouseDAO.getLocatorIdByRepoIdOrNull(receiptLine.getM_Locator_ID());
		if (Objects.equals(effectiveLocatorToId, receiptLineLocatorId))
		{
			return null;
		}

		return effectiveLocatorToId;
	}

	private static LocatorId findDestinationLocatorOrNull(@NonNull final I_M_InOutLine receiptLine)
	{
		final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);

		final List<I_M_ReceiptSchedule> receiptScheduleRecords = receiptScheduleDAO.retrieveRsForInOutLine(receiptLine);
		return findDestinationLocatorOrNullForReceiptSchedules(receiptScheduleRecords);
	}

	private static LocatorId findDestinationLocatorOrNullForReceiptSchedules(
			@NonNull final List<I_M_ReceiptSchedule> receiptScheduleRecords)
	{
		final Integer warehouseDestRepoId = CollectionUtils.extractSingleElementOrDefault(receiptScheduleRecords, I_M_ReceiptSchedule::getM_Warehouse_Dest_ID, -1);

		if (warehouseDestRepoId <= 0)
		{
			return null;
		}

		final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

		final I_M_Warehouse targetWarehouse = loadOutOfTrx(warehouseDestRepoId, I_M_Warehouse.class);
		final I_M_Locator locatorTo = warehouseBL.getDefaultLocator(targetWarehouse);

		// Skip if we don't have a target warehouse
		if (locatorTo == null)
		{
			return null;
		}
		return LocatorId.ofRecordOrNull(locatorTo);
	}
}
