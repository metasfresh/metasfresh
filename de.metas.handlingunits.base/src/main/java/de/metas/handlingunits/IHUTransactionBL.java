package de.metas.handlingunits;

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


import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;

/**
 * Declare business logic to create different {@link IHUTransaction}s for different purposes.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHUTransactionBL extends ISingletonService
{
	/**
	 * Current use: creates a IHUTransaction for a LU, which is created from a receipt schedule.<br>
	 * Just so that HULoader.load0() will later on transfer the source's attributes also to the LU and not just to the TUs (task 06748).
	 *
	 * @param luHU
	 * @param luItemPI
	 * @param request
	 * @return
	 */
	IHUTransaction createLUTransactionForAttributeTransfer(I_M_HU luHU, I_M_HU_PI_Item luItemPI, IAllocationRequest request);

}
