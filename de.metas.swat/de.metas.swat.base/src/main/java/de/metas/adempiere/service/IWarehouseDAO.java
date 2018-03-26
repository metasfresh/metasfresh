package de.metas.adempiere.service;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Warehouse;

public interface IWarehouseDAO extends ISingletonService
{
	/**
	 * Retrieve the warehouse marked as IsIssueWarehouse; There should be one and only one entry of this kind (unique index)
	 * 
	 * @param ctx
	 * @return warehouse or null
	 */
	I_M_Warehouse retrieveWarehouseForIssuesOrNull(Properties ctx);

	/**
	 * Same as {@link #retrieveWarehouseForIssuesOrNull(Properties)} but it will fail if no warehouse found.
	 * 
	 * @param ctx
	 * @return warehouse
	 */
	I_M_Warehouse retrieveWarehouseForIssues(Properties ctx);

	/**
	 * 
	 * Retrieve the warehouse marked as IsQuarantineWarehouse
	 * 
	 * @return Quarantine warehouse or null
	 */
	org.adempiere.warehouse.model.I_M_Warehouse retrieveQuarantineWarehouseOrNull();
}
