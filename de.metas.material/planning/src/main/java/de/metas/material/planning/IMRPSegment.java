package de.metas.material.planning;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;

/**
 * MRP planning segment: a particular group of (Org/Plant/Warehouse) on which MRP is planning.
 *
 * NOTES to who will extend this interface: Please don't extend this interface, or if u extend, please MRPSegment.equals works!
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/07843_Review_quantity_of_CMP_runs
 */
public interface IMRPSegment
{
	@Override
	public boolean equals(final Object obj);

	int getAD_Client_ID();

	int getAD_Org_ID();

	I_AD_Org getAD_Org();

	int getM_Warehouse_ID();

	I_M_Warehouse getM_Warehouse();

	/**
	 * Returns a segment identical with this one, but having the given warehouse.
	 *
	 * @param warehouse
	 * @return
	 */
	IMRPSegment setM_Warehouse(I_M_Warehouse warehouse);

	int getPlant_ID();

	I_S_Resource getPlant();

	/**
	 * Returns a segment identical with this one, but having the given plant.
	 *
	 * @param plant
	 * @return
	 */
	IMRPSegment setPlant(I_S_Resource plant);

	int getM_Product_ID();

	I_M_Product getM_Product();

	/**
	 * Returns a segment identical with this one, but having the given product.
	 *
	 * @param product
	 * @return
	 */
	IMRPSegment setM_Product(I_M_Product product);
}
