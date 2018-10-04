package org.compiere.apps.search.dao;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.util.List;

import org.compiere.apps.search.dao.impl.OrderLineHistoryVO;

import de.metas.util.ISingletonService;

/**
 * Used in gathering invoicing information
 *
 * @author al
 */
public interface IInvoiceHistoryDAO extends ISingletonService
{
	/**
	 * @param showDetail
	 * @param warehouseId
	 * @param asiId
	 * @return storage retrieval SQL, matched by warehouse & ASI, and "grouped by" depending on <code>showDetail</code>
	 */
	String buildStorageInvoiceHistorySQL(boolean showDetail, int warehouseId, int asiId);

	/**
	 * @param productId
	 * @param attributeSetInstanceId
	 * @param warehouseId
	 * @return aggregated data for order line history
	 */
	List<OrderLineHistoryVO> retrieveOrderLineHistory(int productId, int attributeSetInstanceId, int warehouseId);
}
