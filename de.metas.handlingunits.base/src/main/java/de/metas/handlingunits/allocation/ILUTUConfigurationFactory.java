package de.metas.handlingunits.allocation;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.adempiere.uom.api.Quantity;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

public interface ILUTUConfigurationFactory extends ISingletonService
{
	I_M_HU_LUTU_Configuration createLUTUConfiguration(I_M_HU_PI_Item_Product tuPIItemProduct, I_M_Product cuProduct, I_C_UOM cuUOM, I_C_BPartner bpartner);

	/**
	 * Create and configure a {@link ILUTUProducerAllocationDestination} for the given {@code lutuConfiguration} record
	 * 
	 * @param lutuConfiguration
	 * 
	 * @return
	 */
	ILUTUProducerAllocationDestination createLUTUProducerAllocationDestination(I_M_HU_LUTU_Configuration lutuConfiguration);

	/**
	 * Creates a copy of given configuration.
	 *
	 * NOTE: it is not saving the new configuration.
	 *
	 * @param lutuConfiguration
	 * @return copy of <code>lutuConfiguration</code>
	 */
	I_M_HU_LUTU_Configuration copy(I_M_HU_LUTU_Configuration lutuConfiguration);

	/**
	 * Decide if both parameters are not {@code null} and are "equal enough" (according to location, status, bpartner etc) for the LUTU-config user interface. 
	 * @param lutuConfiguration1
	 * @param lutuConfiguration2
	 * @return
	 */
	boolean isSameForHUProducer(I_M_HU_LUTU_Configuration lutuConfiguration1, I_M_HU_LUTU_Configuration lutuConfiguration2);

	/**
	 * Called before saving the configuration
	 *
	 * @param lutuConfiguration
	 */
	void assertNotChanged(I_M_HU_LUTU_Configuration lutuConfiguration);

	/**
	 * Save configuration<br>
	 * <br>
	 * NOTE: NEVER call <code>InterfaceWrapperHelper.save(lutuConfiguration)</code> directly!
	 *
	 * @param lutuConfiguration
	 */
	void save(I_M_HU_LUTU_Configuration lutuConfiguration);

	/**
	 * Save configuration, using <code>disableChangeCheckingOnSave</code> to flag whether saving is allowed to override check for update (i.e first entry, but it was saved twice in different areas)<br>
	 * <br>
	 * NOTE: NEVER call <code>InterfaceWrapperHelper.save(lutuConfiguration)</code> directly!
	 *
	 * @param lutuConfiguration
	 * @param disableChangeCheckingOnSave
	 */
	void save(I_M_HU_LUTU_Configuration lutuConfiguration, boolean disableChangeCheckingOnSave);

	boolean isNoLU(I_M_HU_LUTU_Configuration lutuConfiguration);

	int calculateQtyLUForTotalQtyTUs(I_M_HU_LUTU_Configuration lutuConfiguration, BigDecimal qtyTUsTotal);

	/**
	 * Adjust Qty CU, TU and LU to preciselly match our given <code>qtyTUsTotal</code>/<code>qtyCUsTotal</code>.
	 *
	 * TODO: more documentation needed here
	 *
	 * @param lutuConfiguration
	 * @param qtyTUsTotal
	 * @param qtyCUsTotal
	 */
	void adjustForTotalQtyTUsAndCUs(I_M_HU_LUTU_Configuration lutuConfiguration, BigDecimal qtyTUsTotal, BigDecimal qtyCUsTotal);

	/**
	 * Calculate how many LUs we would need (using given configuration) for given total CU quantity
	 * 
	 * @param lutuConfiguration
	 * @param qtyCUsTotal total CU quantity
	 * @param qtyCUsTotalUOM total CU quantity's UOM
	 * @return how many LUs are needed or ZERO if we are dealing with infinite capacities
	 */
	int calculateQtyLUForTotalQtyCUs(I_M_HU_LUTU_Configuration lutuConfiguration, BigDecimal qtyCUsTotal, I_C_UOM qtyCUsTotalUOM);

	/**
	 * Converts given quantity to {@link I_M_HU_LUTU_Configuration}'s UOM.
	 * 
	 * @param qty
	 * @param qtyUOM
	 * @param lutuConfiguration
	 * @return quantity converted to {@link I_M_HU_LUTU_Configuration}'s UOM.
	 */
	Quantity convertQtyToLUTUConfigurationUOM(BigDecimal qty, I_C_UOM qtyUOM, I_M_HU_LUTU_Configuration lutuConfiguration);
}
