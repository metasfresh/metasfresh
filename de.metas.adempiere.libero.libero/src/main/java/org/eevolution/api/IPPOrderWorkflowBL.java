package org.eevolution.api;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import org.adempiere.util.ISingletonService;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_Node;

public interface IPPOrderWorkflowBL extends ISingletonService
{

	void createOrderWorkflow(I_PP_Order ppOrder);

	/**
	 * Set Qty Required and DurationRequired (Duration * qtyOrdered) 
	 * @param qtyOrdered
	 */
	void setQtyOrdered(I_PP_Order_Node orderNode, BigDecimal qtyOrdered);

	/**
	 * Get Qty To Deliver (Open Qty)
	 * @return open qty
	 */
	BigDecimal getQtyToDeliver(I_PP_Order_Node node);

}
