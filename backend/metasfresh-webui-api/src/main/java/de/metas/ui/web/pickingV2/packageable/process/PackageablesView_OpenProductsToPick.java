package de.metas.ui.web.pickingV2.packageable.process;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.inoutcandidate.lock.ShipmentScheduleLockRepository;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockRequest;
import de.metas.inoutcandidate.lock.ShipmentScheduleUnLockRequest;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.pickingV2.productsToPick.ProductsToPickView;
import de.metas.ui.web.pickingV2.productsToPick.ProductsToPickViewFactory;

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

public class PackageablesView_OpenProductsToPick extends PackageablesViewBasedProcess
{
	@Autowired
	private ProductsToPickViewFactory productsToPickViewFactory;

	@Autowired
	private ShipmentScheduleLockRepository locksRepo;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return checkPreconditionsApplicable_SingleSelectedRow()
				.and(this::acceptIfRowNotLockedByOtherUser);
	}

	private ProcessPreconditionsResolution acceptIfRowNotLockedByOtherUser()
	{
		final PackageableRow row = getSingleSelectedRow();

		if (row.isNotLocked() || row.isLockedBy(getLoggedUserId()))
		{
			return ProcessPreconditionsResolution.accept();
		}
		else
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("row is locked by other users");
		}
	}

	@Override
	protected String doIt()
	{
		final PackageableRow row = getSingleSelectedRow();

		final ShipmentScheduleLockRequest lockRequest = createLockRequest(row);
		locksRepo.lock(lockRequest);

		try
		{
			final ProductsToPickView productsToPickView = productsToPickViewFactory.createView(row);

			getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
					.target(ViewOpenTarget.ModalOverlay)
					.viewId(productsToPickView.getViewId().toJson())
					.build());

			return MSG_OK;
		}
		catch (final Exception ex)
		{
			locksRepo.unlockNoFail(ShipmentScheduleUnLockRequest.of(lockRequest));

			throw AdempiereException.wrapIfNeeded(ex);
		}
	}
}
