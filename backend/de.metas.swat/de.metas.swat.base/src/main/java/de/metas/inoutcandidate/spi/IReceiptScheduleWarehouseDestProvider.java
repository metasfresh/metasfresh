package de.metas.inoutcandidate.spi;

import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_AttributeSetInstance;

import java.util.Optional;
import java.util.Properties;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Implementations of this interface are responsible for providing the warehouse destination to be used for receipt schedule destination warehouse.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface IReceiptScheduleWarehouseDestProvider
{
	/**
	 * Context used for evaluating if the destination warehouse
	 */
	interface IContext
	{
		Properties getCtx();

		int getAD_Client_ID();

		int getAD_Org_ID();

		int getM_Warehouse_ID();

		int getM_Product_ID();

		I_M_AttributeSetInstance getM_AttributeSetInstance();
	}

	/**
	 * @return destination warehouse for given order line
	 */
	Optional<WarehouseId> getWarehouseDest(IContext context);
}
