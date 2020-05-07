package de.metas.order.process;

import org.adempiere.util.ISingletonService;

import de.metas.order.process.spi.IC_Order_CreatePOFromSOsListener;

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

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task http://dewiki908/mediawiki/index.php/09557_Wrong_aggregation_on_OrderPOCreate_%28109614894753%29
 */
public interface IC_Order_CreatePOFromSOsBL extends ISingletonService
{
	/**
	 * Register the given <code>listener</code> to allow other modules to provide additional column values while pruchase orders are created from sales orders.
	 *
	 * @param listener
	 */
	void registerListener(IC_Order_CreatePOFromSOsListener listener);

	/**
	 *
	 * @return a composite of all listeners that were added. Never returns <code>null</code>.
	 */
	IC_Order_CreatePOFromSOsListener getCompositeListener();

	/**
	 * Retrieve the name of the <code>C_OrderLine</code> column to aggregate the purchase order lines' qtys.
	 *
	 * @return <code>QtyOrdered</code> or <code>QtyReserved</code>. Never anything else.
	 */
	String getConfigPurchaseQtySource();
}
