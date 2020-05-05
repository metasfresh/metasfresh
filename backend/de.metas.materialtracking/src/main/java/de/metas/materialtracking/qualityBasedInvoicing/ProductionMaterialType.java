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


/**
 * Type of production material.
 *
 * @author tsa
 *
 */
public enum ProductionMaterialType
{
	/**
	 * Type to material that doesn't have an actual production issue or receipt behind it.
	 *
	 * @see IQualityBasedInvoicingBL#createPlainInvoicingItem(org.compiere.model.I_M_Product, java.math.BigDecimal, org.compiere.model.I_C_UOM)
	 */
	PLAIN

	/**
	 * Type for the material that went into the production (e.g. unwashed carrot).
	 */
	, RAW

	/**
	 * Type for the produced materials. Don't care whether it's the main or a co/by-product (e.g. washed carrot, big carrot co-product).
	 */
	, PRODUCED

	/**
	 * Type for the scrap materials.
	 *
	 * NOTE: at the moment this one is calculated as "Qty of raw materials" - "sum of produced Qtys"
	 */
	, SCRAP
}
