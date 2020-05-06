package de.metas.product;

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


import java.math.BigDecimal;
import java.util.Properties;

import org.compiere.model.I_M_Locator;

import de.metas.util.ISingletonService;

public interface IStorageBL extends ISingletonService
{
	void addQtyOrdered(Properties ctx,
			I_M_Locator locator,
			int productId, int attributeSetInstanceId,
			BigDecimal diffQtyOrdered,
			String trxName);

	void addQtyReserved(Properties ctx,
			I_M_Locator locator,
			int productId, int attributeSetInstanceId,
			BigDecimal diffQtyReserved,
			String trxName);

	/**
	 * Enqueue an async-workpackage to be processed by {@link org.adempiere.product.async.spi.impl.M_Storage_Add}, with the given parameters.
	 *
	 * @task http://dewiki908/mediawiki/index.php/08999_Lieferdisposition_a.frieden_%28104263801724%29
	 */
	void addAsync(Properties ctx,
			int M_Warehouse_ID,
			int M_Locator_ID,
			int M_Product_ID,
			int M_AttributeSetInstance_ID,
			int reservationAttributeSetInstance_ID,
			BigDecimal diffQtyOnHand,
			BigDecimal diffQtyReserved,
			BigDecimal diffQtyOrdered,
			String trxName);

	boolean add(Properties ctx,
			int M_Warehouse_ID,
			int M_Locator_ID,
			int M_Product_ID,
			int M_AttributeSetInstance_ID,
			int reservationAttributeSetInstance_ID,
			BigDecimal diffQtyOnHand,
			BigDecimal diffQtyReserved,
			BigDecimal diffQtyOrdered,
			String trxName);

}
