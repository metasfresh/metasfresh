package de.metas.ui.web.pickingV2.packageable.process;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockRepository;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockType;
import de.metas.inoutcandidate.lock.ShipmentScheduleUnLockRequest;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;

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

public class PackageablesView_UnlockAll extends PackageablesViewBasedProcess
{
	@Autowired
	private ShipmentScheduleLockRepository locksRepo;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!hasLockedShipmentScheduleIds())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no locked rows selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		locksRepo.unlock(ShipmentScheduleUnLockRequest.builder()
				.shipmentScheduleIds(streamLockedShipmentScheduleIds().collect(ImmutableSet.toImmutableSet()))
				.lockType(ShipmentScheduleLockType.PICKING)
				.lockedByAnyUser()
				.build());

		return MSG_OK;
	}

	private boolean hasLockedShipmentScheduleIds()
	{
		return streamLockedShipmentScheduleIds().anyMatch(Predicates.alwaysTrue());
	}

	private Stream<ShipmentScheduleId> streamLockedShipmentScheduleIds()
	{
		return streamSelectedRows()
				.filter(PackageableRow::isLocked)
				.flatMap(row -> row.getShipmentScheduleIds().stream())
				.distinct();
	}
}
