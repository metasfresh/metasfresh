package de.metas.handlingunits.attribute;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

/**
 * Common logic around {@link IWeightable}.
 *
 * @author tsa
 *
 */
public interface IWeightableBL extends ISingletonService
{
	/**
	 * Boolean property which if set, it will allow user to change weights but ONLY on VHU level
	 *
	 * @task http://dewiki908/mediawiki/index.php/08270_Wareneingang_POS_multiple_lines_in_1_TU_%28107035315495%29
	 */
	String PROPERTY_WeightableOnlyIfVHU = IWeightable.class.getName() + ".WeightableOnlyIfVHU";

	/**
	 *
	 * @param uomType UOM Type (i.e. {@link I_C_UOM#getUOMType()})
	 * @return true if it's a weightable UOM Type
	 */
	boolean isWeightableUOMType(String uomType);

	/**
	 *
	 * @param uom
	 * @return true if given uom is weightable
	 */
	boolean isWeightable(I_C_UOM uom);

	/**
	 *
	 * @param product
	 * @return true if product's stocking UOM is weightable
	 */
	boolean isWeightable(I_M_Product product);

}
