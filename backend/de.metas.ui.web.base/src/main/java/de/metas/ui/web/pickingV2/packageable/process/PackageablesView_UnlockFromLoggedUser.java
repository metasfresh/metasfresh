package de.metas.ui.web.pickingV2.packageable.process;

import org.springframework.beans.factory.annotation.Autowired;

import de.metas.inoutcandidate.lock.ShipmentScheduleLockRepository;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockType;
import de.metas.inoutcandidate.lock.ShipmentScheduleUnLockRequest;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

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

public class PackageablesView_UnlockFromLoggedUser extends PackageablesViewBasedProcess
{
	@Autowired
	private ShipmentScheduleLockRepository locksRepo;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return acceptIfSingleSelectedRow()
				.and(this::acceptIfLockedByCurrentUser);
	}

	private ProcessPreconditionsResolution acceptIfSingleSelectedRow()
	{
		final DocumentIdsSelection rowIds = getSelectedRowIds();
		if (!rowIds.isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select a single row");
		}
		else
		{
			return ProcessPreconditionsResolution.accept();
		}
	}

	private ProcessPreconditionsResolution acceptIfLockedByCurrentUser()
	{
		final PackageableRow row = getSingleSelectedRow();
		if (!row.isLockedBy(getLoggedUserId()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not locked by current user");
		}
		else
		{
			return ProcessPreconditionsResolution.accept();
		}
	}

	@Override
	protected String doIt()
	{
		final PackageableRow row = getSingleSelectedRow();

		locksRepo.unlock(ShipmentScheduleUnLockRequest.builder()
				.shipmentScheduleIds(row.getShipmentScheduleIds())
				.lockType(ShipmentScheduleLockType.PICKING)
				.lockedBy(getLoggedUserId())
				.build());

		return MSG_OK;
	}
}
