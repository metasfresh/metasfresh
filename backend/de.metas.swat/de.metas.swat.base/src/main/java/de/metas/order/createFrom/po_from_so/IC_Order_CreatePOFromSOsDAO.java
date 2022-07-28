/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.order.createFrom.po_from_so;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_OrderLine;

import de.metas.order.model.I_C_Order;
import de.metas.util.ISingletonService;

/**
 * @author metas-dev <dev@metasfresh.com>
 * Task http://dewiki908/mediawiki/index.php/09557_Wrong_aggregation_on_OrderPOCreate_%28109614894753%29
 */
public interface IC_Order_CreatePOFromSOsDAO extends ISingletonService
{
	/**
	 * Take the process filter parameters and create an iterator to retrieve all matching sales orders.
	 *
	 * @param allowMultiplePOOrders if <code>false</code>, then only those sales orders will be returned that have <code>Link_Order_ID == null</code>.
	 */
	Iterator<I_C_Order> createSalesOrderIterator(IContextAware context,
			boolean allowMultiplePOOrders,
			int c_Order_ID,
			int c_BPartner_ID,
			int vendor_ID,
			String poReference,
			Timestamp datePromised_From,
			Timestamp datePromised_To,
			boolean IsVendorInOrderLinesRequired);

	/**
	 * Retrieve all order lines for the given (sales-)order that have QtyReserved>0 and also match the additional filters added via {@link #addAdditionalOrderLinesFilter(IQueryFilter)}.
	 *
	 * @param allowMultiplePOOrders if <code>false</code>, then only those sales order lines will be returned that don't have a purchase order line yet.
	 * @param purchaseQtySource     see {@link IC_Order_CreatePOFromSOsBL#getConfigPurchaseQtySource()}.
	 */
	List<I_C_OrderLine> retrieveOrderLines(I_C_Order order,
			boolean allowMultiplePOOrders,
			String purchaseQtySource);

	void addAdditionalOrderLinesFilter(IQueryFilter<I_C_OrderLine> filter);

}
