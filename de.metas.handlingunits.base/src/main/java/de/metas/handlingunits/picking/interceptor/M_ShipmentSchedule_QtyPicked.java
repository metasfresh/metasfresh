package de.metas.handlingunits.picking.interceptor;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;

@Interceptor(I_M_ShipmentSchedule_QtyPicked.class)
public class M_ShipmentSchedule_QtyPicked
{
	/**
	 * Asserts {@link I_M_ShipmentSchedule_QtyPicked#COLUMNNAME_M_LU_HU_ID} has a valid LU or null.
	 *
	 * @param alloc
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = { I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_LU_HU_ID })
	public void assertLUValid(final I_M_ShipmentSchedule_QtyPicked alloc)
	{
		final I_M_HU luHU = alloc.getM_LU_HU();
		if (luHU == null || luHU.getM_HU_ID() <= 0)
		{
			// not set => ok
			return;
		}

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		if (!handlingUnitsBL.isLoadingUnit(luHU))
		{
			final HUException ex = new HUException("Loading unit expected."
					+ "\n@M_LU_HU_ID@: " + handlingUnitsBL.getDisplayName(luHU)
					+ "\n@M_ShipmentSchedule_QtyPicked_ID@: " + alloc
					+ "\n@M_ShipmentSchedule_ID@: " + alloc.getM_ShipmentSchedule());
			// logger.warn(ex.getLocalizedMessage(), ex);
			throw ex;
		}
	}

	/**
	 * Asserts {@link I_M_ShipmentSchedule_QtyPicked#COLUMNNAME_M_LU_TU_ID} has a valid TU or null.
	 *
	 * @param alloc
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = { I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_TU_HU_ID })
	public void assertTUValid(final I_M_ShipmentSchedule_QtyPicked alloc)
	{
		final I_M_HU tuHU = alloc.getM_TU_HU();
		if (tuHU == null || tuHU.getM_HU_ID() <= 0)
		{
			// not set => ok
			return;
		}

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		if (!handlingUnitsBL.isTransportUnitOrVirtual(tuHU))
		{
			final HUException ex = new HUException("Transport unit expected."
					+ "\n@M_TU_HU_ID@: " + handlingUnitsBL.getDisplayName(tuHU)
					+ "\n@M_ShipmentSchedule_QtyPicked_ID@: " + alloc
					+ "\n@M_ShipmentSchedule_ID@: " + alloc.getM_ShipmentSchedule());
			// logger.warn(ex.getLocalizedMessage(), ex);
			throw ex;
		}
	}
}
