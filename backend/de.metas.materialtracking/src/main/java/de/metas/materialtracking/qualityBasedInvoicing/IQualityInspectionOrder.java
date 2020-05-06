package de.metas.materialtracking.qualityBasedInvoicing;

/*
 * #%L
 * de.metas.materialtracking
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
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;

import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityBasedConfig;

/**
 * Manufacturing order involved in quality process.
 *
 * We can have 2 types of quality orders:
 * <ul>
 * <li>quality inspection orders (if {@link #isQualityInspection()} is <code>true</code>)
 * <li>regular manufacturing orders
 * </ul>
 *
 * @author tsa
 *
 */
public interface IQualityInspectionOrder
{
	/**
	 * @return material tracking; never return null
	 */
	I_M_Material_Tracking getM_Material_Tracking();

	/**
	 *
	 * @return quality based configuration; never return null
	 */
	IQualityBasedConfig getQualityBasedConfig();

	/**
	 *
	 * @return true if this is a Quality Inspection Order; false if this is a regular order.
	 */
	boolean isQualityInspection();

	/**
	 *
	 * @return inspection number
	 * @throws AdempiereException if this is not a quality inspection (see {@link #isQualityInspection()})
	 */
	int getInspectionNumber();

	/**
	 *
	 * @return underlying manufacturing order; never return <code>null</code>
	 */
	I_PP_Order getPP_Order();

	/**
	 * Return the production materials, (but not scrap).
	 * @return
	 */
	List<IProductionMaterial> getProductionMaterials();

	IProductionMaterial getProductionMaterial(I_M_Product product);

	List<IProductionMaterial> getProductionMaterials(ProductionMaterialType type);

	IProductionMaterial getProductionMaterial(ProductionMaterialType type, I_M_Product product);

	/**
	 * @return raw production material; never return null
	 */
	IProductionMaterial getRawProductionMaterial();

	/**
	 * @return Main produced material (i.e. the one from PP_Order header); never return null
	 */
	IProductionMaterial getMainProductionMaterial();

	/**
	 * Gets scrap production materials.
	 *
	 * NOTE: at the moment, "Scrapped Qty" is calculated as "Qty of raw materials" - "sum of produced Qtys"
	 *
	 * @return scrap production material; never return null
	 */
	IProductionMaterial getScrapProductionMaterial();

	/**
	 *
	 * @return all manufacturing orders with the same material tracking that were not yet invoiced. That might include this one.
	 */
	List<IQualityInspectionOrder> getAllOrders();

	BigDecimal getAlreadyInvoicedNetSum();
}
