/**
 *
 */
package de.metas.handlingunits.model.validator;

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


import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_C_Order;
import de.metas.handlingunits.order.api.impl.OrderPackingMaterialDocumentLinesBuilder;

/**
 * @author cg
 *
 */

@Validator(I_C_Order.class)
public class C_Order
{

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_PREPARE })
	public void addPackingMaterialLine(final I_C_Order order)
	{
		final OrderPackingMaterialDocumentLinesBuilder packingMaterialOrderLineBuilder = new OrderPackingMaterialDocumentLinesBuilder(order);

		// Reset existing packing material order lines. They might eventually be removed, if they don't get new quantities assigned from the builder
		packingMaterialOrderLineBuilder.deactivateAndUnlinkAllPackingMaterialOrderLinesFromOrder();

		// Create/Update/Delete packing material order lines
		packingMaterialOrderLineBuilder.addAllOrderLinesFromOrder();
		packingMaterialOrderLineBuilder.create();
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE })
	public void removePackingMaterialLine(final I_C_Order order)
	{
		final OrderPackingMaterialDocumentLinesBuilder packingMaterialOrderLineBuilder = new OrderPackingMaterialDocumentLinesBuilder(order);
		packingMaterialOrderLineBuilder.deactivateAndUnlinkAllPackingMaterialOrderLinesFromOrder();
	}
}
