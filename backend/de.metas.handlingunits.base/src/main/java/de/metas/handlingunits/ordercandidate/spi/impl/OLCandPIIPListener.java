package de.metas.handlingunits.ordercandidate.spi.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.spi.IOLCandListener;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Component;

/**
 * See {@link #onOrderLineCreated(OLCand, org.compiere.model.I_C_OrderLine)}.
 */
@Component
public class OLCandPIIPListener implements IOLCandListener
{
	private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
	
	/**
	 * Sets the new order line's <code>M_HU_PI_Item_Product_ID</code> and IsManualQtyItemCapacity from the olCand's effective <code>M_HU_PI_Item_Product_ID</code>, so that the system won't have to guess.
	 */
	@Override
	public void onOrderLineCreated(@NonNull final OLCand olCand, @NonNull final org.compiere.model.I_C_OrderLine newOrderLine)
	{
		final I_C_OrderLine newOrderLineExt = InterfaceWrapperHelper.create(newOrderLine, I_C_OrderLine.class);
		newOrderLineExt.setM_HU_PI_Item_Product_ID(olCand.getHUPIProductItemId());

		newOrderLineExt.setIsManualQtyItemCapacity(olCand.unbox().isManualQtyItemCapacity());
		
		// note that I_C_OrderLine.setQtyItemCapacity was already called in the OLCandOrderFactory
	}
}
