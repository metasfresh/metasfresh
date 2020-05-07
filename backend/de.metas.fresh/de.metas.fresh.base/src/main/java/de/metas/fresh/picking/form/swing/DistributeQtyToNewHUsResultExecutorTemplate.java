package de.metas.fresh.picking.form.swing;

/*
 * #%L
 * de.metas.fresh.base
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


import java.math.BigDecimal;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * Abstract distribute command:
 * <ul>
 * <li>starts from an initial request: {@link #getInitialRequest()}
 * <li>asks for an result to be calculated (here you can plugin UI interactions): {@link #calculateResult(DistributeQtyToNewHUsRequest)}
 * <li>distributes the initial quantity from the request, as specified in result.
 * </ul>
 * 
 * This is a template class which implements all business logic but let's the caller to implement the glue methods.
 * 
 * @author tsa
 *
 */
public abstract class DistributeQtyToNewHUsResultExecutorTemplate
{
	/**
	 * Execute the distribution
	 */
	public final void execute()
	{
		//
		// Create distribution request based on current ProductKey, PickingSlot Key and the Qty field
		final DistributeQtyToNewHUsRequest request = getInitialRequest();

		//
		// Ask for the result and validate it
		final DistributeQtyToNewHUsResult result = calculateResult(request);
		if (result == null)
		{
			// NOTE: if result is null, we assume it's a cancel request
			return;
		}
		result.validate();

		//
		// Close any existing HU, if any
		closeCurrentHU();

		//
		// Distribute to subsequent HUs, as specified by the result
		for (final DistributeQtyToNewHUsResult.Item resultItem : result.getItems())
		{
			final int qtyHUs = resultItem.getQtyHU();
			for (int huCount = 1; huCount <= qtyHUs; huCount++)
			{
				//
				// Create the new HU
				final I_M_HU hu = createNewHU(result.getM_HU_PI_Item_Product());

				//
				// Pick to it
				final BigDecimal qtyToPack = resultItem.getQtyPerHU();
				loadQtyToHU(hu, qtyToPack);

				// Close the TU
				closeCurrentHU();
			}
		}
	}

	/**
	 * Gets initial request to be distributed.
	 * 
	 * @return
	 */
	protected abstract DistributeQtyToNewHUsRequest getInitialRequest();

	/**
	 * Process given request and return the result which we will have to execute (i.e. distribute to new HUs).
	 * 
	 * @param request
	 * @return <ul>
	 *         <li>result which shall be distibuted
	 *         <li><code>null</code> in case it's a cancel request
	 *         </ul>
	 */
	protected DistributeQtyToNewHUsResult calculateResult(final DistributeQtyToNewHUsRequest request)
	{
		return DistributeQtyToNewHUsResult.calculateResult(request);
	}

	/**
	 * Create a new HU on which we will distribute a part of our quantity.
	 * 
	 * @param piItemProduct
	 * @return hu
	 */
	protected abstract I_M_HU createNewHU(final I_M_HU_PI_Item_Product piItemProduct);

	/**
	 * Load given quantity into given HU.
	 * 
	 * @param hu
	 * @param qty
	 */
	protected abstract void loadQtyToHU(final I_M_HU hu, final BigDecimal qty);

	/**
	 * Close current HU. This method is called:
	 * <ul>
	 * <li>before starting to distribute the quantity, to make sure the initial state is clean and there are no pending/open HUs
	 * <li>after each {@link #loadQtyToHU(I_M_HU, BigDecimal)} to be sure that we are done with current HU
	 * </ul>
	 */
	protected abstract void closeCurrentHU();
}
