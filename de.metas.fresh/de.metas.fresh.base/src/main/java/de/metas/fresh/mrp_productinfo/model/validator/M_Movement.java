package de.metas.fresh.mrp_productinfo.model.validator;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;

import de.metas.fresh.mrp_productinfo.async.spi.impl.UpdateMRPProductInfoTableWorkPackageProcessor;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.util.Services;

@Interceptor(I_M_Movement.class)
public class M_Movement
{
	public static final M_Movement INSTANCE = new M_Movement();

	private M_Movement()
	{
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE,
			ModelValidator.TIMING_BEFORE_CLOSE,
			ModelValidator.TIMING_BEFORE_REACTIVATE,
			// yes, the following won't actually occur, but still, be on the safe side
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT })
	public void enqueueMaterialTransaction(final I_M_Movement movement)
	{
		final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
		final I_M_Warehouse directMoveWarehouse = huMovementBL.getDirectMove_Warehouse(InterfaceWrapperHelper.getCtx(movement), false);
		if (directMoveWarehouse == null)
		{
			return; // nothing to do
		}

		// note that we need to evaluate both incoming and outgoing inouts
		final List<I_M_MovementLine> movementLines = Services.get(IMovementDAO.class).retrieveLines(movement);
		for (final I_M_MovementLine movementLine : movementLines)
		{
			if (movementLine.getM_LocatorTo().getM_Warehouse_ID() == directMoveWarehouse.getM_Warehouse_ID())
			{
				UpdateMRPProductInfoTableWorkPackageProcessor.schedule(movementLine);
			}
		}
	}
}
