package de.metas.picking.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.picking.model.I_M_PickingSlot;

/**
 * @author al
 */
@Interceptor(I_M_PickingSlot.class)
@Component
public class M_PickingSlot
{
	/**
	 * If the picking slot is changed to <code>IsDynamic = 'Y'</code>, then this method resets the slot's partner and location to <code>null</code>.
	 * 
	 * @param pickingSlot
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = { I_M_PickingSlot.COLUMNNAME_IsDynamic })
	public void onIsDynamicChanged(final I_M_PickingSlot pickingSlot)
	{
		if (!pickingSlot.isDynamic())
		{
			//
			// We're only altering data if the pickingSlot has just been changed to Y
			return;
		}

		//
		// Reset the partner and location
		pickingSlot.setC_BPartner(null);
		pickingSlot.setC_BPartner_Location(null);
	}
}
