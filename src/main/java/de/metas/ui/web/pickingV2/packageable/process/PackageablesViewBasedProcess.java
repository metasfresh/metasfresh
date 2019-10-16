package de.metas.ui.web.pickingV2.packageable.process;

import java.util.stream.Stream;

import de.metas.inoutcandidate.lock.ShipmentScheduleLockRequest;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockType;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.pickingV2.packageable.PackageableView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
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

public abstract class PackageablesViewBasedProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Override
	protected abstract ProcessPreconditionsResolution checkPreconditionsApplicable();

	protected final ProcessPreconditionsResolution checkPreconditionsApplicable_SingleSelectedRow()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (!selectedRowIds.isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected final PackageableView getView()
	{
		return PackageableView.cast(super.getView());
	}

	@Override
	protected final PackageableRow getSingleSelectedRow()
	{
		return PackageableRow.cast(super.getSingleSelectedRow());
	}

	@Override
	protected final Stream<PackageableRow> streamSelectedRows()
	{
		return super.streamSelectedRows()
				.map(PackageableRow::cast);
	}

	protected final ShipmentScheduleLockRequest createLockRequest(final PackageableRow row)
	{
		return ShipmentScheduleLockRequest.builder()
				.shipmentScheduleIds(row.getShipmentScheduleIds())
				.lockType(ShipmentScheduleLockType.PICKING)
				.lockedBy(getLoggedUserId())
				.build();
	}

}
