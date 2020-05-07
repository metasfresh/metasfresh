package org.adempiere.warehouse.spi;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;

/**
 * Service used to advice which shall be the Warehouse of given document/document lines
 * 
 * @author tsa
 * 
 */
public interface IWarehouseAdvisor extends ISingletonService
{
	/**
	 * Suggests warehouse to be used by given order line
	 * 
	 * @param orderLine
	 * @return
	 */
	public I_M_Warehouse evaluateWarehouse(final I_C_OrderLine orderLine);

	/**
	 * Suggests warehouse to be used by given order
	 * 
	 * @param order
	 * @return
	 */
	public I_M_Warehouse evaluateOrderWarehouse(final I_C_Order order);

	/**
	 * Suggests default warehouse to be used
	 * 
	 * @param ctx
	 * @return
	 */
	public int getDefaulWarehouseId(Properties ctx);

}
