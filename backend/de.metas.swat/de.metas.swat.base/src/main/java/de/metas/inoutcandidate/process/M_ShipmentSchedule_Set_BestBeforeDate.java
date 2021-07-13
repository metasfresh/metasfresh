package de.metas.inoutcandidate.process;

import java.time.LocalDate;
import java.util.Iterator;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;

import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.api.ShipmentScheduleUserChangeRequest;
import de.metas.inoutcandidate.api.ShipmentScheduleUserChangeRequest.ShipmentScheduleUserChangeRequestBuilder;
import de.metas.inoutcandidate.api.ShipmentScheduleUserChangeRequestsList;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.lock.api.ILockManager;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import lombok.NonNull;

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

public class M_ShipmentSchedule_Set_BestBeforeDate extends JavaProcess implements IProcessPrecondition
{

	@Param(mandatory = true, parameterName = "BestBeforeDate")
	private LocalDate p_bestBeforeDate;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final ILockManager lockManager = Services.get(ILockManager.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize().isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx // we update each shipment sched within its own little trx, to avoid blocking on DB level
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_M_ShipmentSchedule> queryFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final ShipmentScheduleUserChangeRequestBuilder builder = ShipmentScheduleUserChangeRequest.builder().bestBeforeDate(p_bestBeforeDate);
		final IQueryFilter<I_M_ShipmentSchedule> notLockedFilter = lockManager.getNotLockedFilter(I_M_ShipmentSchedule.class);

		// get the selected shipment schedule IDs
		final Iterator<ShipmentScheduleId> ids = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMN_Processed, false)
				.filter(queryFilter)
				.filter(notLockedFilter)
				.create()
				.iterateIds(ShipmentScheduleId::ofRepoId);

		// update them one by one
		while (ids.hasNext())
		{
			final ShipmentScheduleUserChangeRequest singleRequest = builder.shipmentScheduleId(ids.next()).build();
			final ShipmentScheduleUserChangeRequestsList userChanges = ShipmentScheduleUserChangeRequestsList.of(ImmutableList.of(singleRequest));
			shipmentScheduleBL.applyUserChangesInTrx(userChanges);
		}
		return MSG_OK;
	}
}
