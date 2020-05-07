package de.metas.handlingunits.hutransaction.impl;

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


import org.adempiere.util.Services;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.IHUTransactionBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;

public class HUTransactionBL implements IHUTransactionBL
{

	@Override
	public IHUTransactionCandidate createLUTransactionForAttributeTransfer(final I_M_HU luHU, final I_M_HU_PI_Item luItemPI, final IAllocationRequest request)
	{
		final I_M_HU_Item luItem = Services.get(IHandlingUnitsDAO.class).retrieveItem(luHU, luItemPI);

		final HUTransactionCandidate luTrx = new HUTransactionCandidate(AllocationUtils.getReferencedModel(request), // receipt schedule
				luItem, // huItem,
				null, // vhuItem (note: at this level we're not talking about VHUs but actual LU-HUs)
				request.getProduct(),
				request.getQuantity().toZero(),
				request.getDate()
				);

		return luTrx;
	}

}
