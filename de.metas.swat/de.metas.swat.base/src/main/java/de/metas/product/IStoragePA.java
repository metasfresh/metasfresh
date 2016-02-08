package de.metas.product;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Storage;
import org.compiere.model.MStorage;

public interface IStoragePA extends ISingletonService {

	Collection<I_M_Storage> retrieveStorages(int productId, String trxName);

	/**
	 * Retrieves all active {@link I_M_Storage}s that have the given product and
	 * (indirectly via {@link I_M_Locator}) warehouse.
	 * 
	 * Also retrieves storeges with <code>QtyOnHand=0</code>, which is a
	 * difference to
	 * {@link MStorage#getWarehouse(java.util.Properties, int, int, int, Timestamp, boolean, boolean, int, String)}
	 * .
	 * 
	 * @param productId
	 * @param warehouseId
	 * @param trxName
	 * @return
	 */
	Collection<I_M_Storage> retrieveAllStorages(int productId, int warehouseId,			String trxName);

	List<I_M_Storage> retrieveStorages(int warehouseId, int productId,
			int attributeSetInstanceId, int attributeSetId,
			boolean allAttributeInstances, Timestamp minGuaranteeDate,
			boolean fiFo, String trxName);

	int retrieveWarehouseId(I_M_Storage storage, String trxName);

	BigDecimal retrieveQtyAvailable(int M_Warehouse_ID, int M_Locator_ID,
			int M_Product_ID, int M_AttributeSetInstance_ID, String trxName);

	BigDecimal retrieveQtyOrdered(int productId, int warehouseId);

}
