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

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.quantity.Capacity;
import de.metas.quantity.CapacityInterface;
import de.metas.quantity.Quantity;

/**
 * It's an {@link IHUProducerAllocationDestination} which can be configured to produce TUs on LUs.
 *
 * @author tsa
 *
 */
public interface ILUTUProducerAllocationDestination extends IHUProducerAllocationDestination
{
	I_M_HU_PI getTUPI();

	/**
	 * Set the PI for the TU that shall be build.
	 *
	 * @param tuPI
	 */
	void setTUPI(final I_M_HU_PI tuPI);

	/**
	 * Register another capacity spec with this producer.
	 */
	void addCUPerTU(Capacity tuCapacity);

	/**
	 *
	 * @param qtyCUPerTU quantity, {@link IHUCapacityDefinition#DEFAULT}, {@link IHUCapacityDefinition#INFINITY}
	 */
	void addCUPerTU(I_M_Product cuProduct, BigDecimal qtyCUPerTU, I_C_UOM cuUOM);

	/**
	 * Gets single TU capacity.
	 *
	 * If there is no TU capacity defined or there are more than one TU capacities, this method will throw an exception.
	 *
	 * @return TU capacity
	 */
	Capacity getSingleCUPerTU();

	/**
	 * Gets TU defined capacity for given product
	 *
	 * @param cuProduct
	 * @return TU capacity or <code>null</code>
	 */
	CapacityInterface getCUPerTU(I_M_Product cuProduct);

	I_M_HU_PI getLUPI();

	/**
	 * Specifies the PI for the loading unit. May be {@code null} for the case that a TU without LU is needed.
	 *
	 * @param luPI
	 */
	void setLUPI(final I_M_HU_PI luPI);

	/**
	 * See {@link #setLUItemPI(I_M_HU_PI_Item)}.
	 *
	 * @return
	 */
	I_M_HU_PI_Item getLUItemPI();

	/**
	 * Sets LU PI's PI Item (with ItemType=HU) on which the TU will be included. May be {@code null} for the case that a TU without LU is needed.
	 *
	 * @param luItemPI
	 */
	void setLUItemPI(final I_M_HU_PI_Item luItemPI);

	/**
	 *
	 * @return true if there is LU configuration set, so only TUs will be generated
	 */
	boolean isNoLU();

	/**
	 * Convenience method for the case that a top-level TU is required.
	 * Call {@link #setTUPI(I_M_HU_PI)}, {@link #setLUItemPI(I_M_HU_PI_Item)}, {@link #setMaxLUs(int)} and {@link #setCreateTUsForRemainingQty(boolean)} accordingly.
	 */
	void setNoLU();

	/**
	 * Let this producer to create as many LUs as needed.
	 *
	 * Same as calling {@link #setMaxLUs(int)} with {@link Integer#MAX_VALUE} parameter.
	 */
	void setMaxLUsInfinite();

	/**
	 *
	 * @return true if producer will create as many LUs as needed
	 */
	boolean isMaxLUsInfinite();

	/**
	 * Set maximum amount of LUs to be created.
	 *
	 * If <code>maxLUs</code> is ZERO then no LUs will be created. In this case only "remaining" TUs will be created.<br/>
	 * Please check the configuration methods: {@link #setCreateTUsForRemainingQty(boolean)}, {@link #setMaxTUsForRemainingQty(int)}.
	 *
	 * @param maxLUs
	 */
	void setMaxLUs(final int maxLUs);

	/**
	 *
	 * @return maximum amount of LUs to be created
	 */
	int getMaxLUs();

	/**
	 * Sets how many TUs shall be created for one LU.
	 *
	 * If this value is not set, it will be taken from {@link #setLUItemPI(I_M_HU_PI_Item)} configuration.
	 *
	 * @param maxTUsPerLU how many TUs shall be created for one LU
	 */
	void setMaxTUsPerLU(final int maxTUsPerLU);

	/**
	 * Gets how many TUs shall be created for one LU. Note, this is returning only the value which was set by {@link #setMaxTUsPerLU(int)}.
	 *
	 * @return how many TUs shall be created for one LU
	 */
	int getMaxTUsPerLU();

	/**
	 * Gets how many TUs shall be created for one LU.
	 *
	 * If TUs/LU was not set using {@link #setMaxTUsPerLU(int)}, this method will also check the {@link #getLUItemPI()}'s settings.
	 *
	 * The returned value of this method will be used in actual LU/TU generation.
	 *
	 * @return how many TUs shall be created for one LU
	 */
	int getMaxTUsPerLU_Effective();

	/**
	 * @return How may TUs were maximum created for an LU
	 */
	int getMaxTUsPerLU_ActuallyCreated();

	/**
	 * @param maxTUsForRemainingQty how many TUs to create for remaining Qty (i.e. after all LUs were created)
	 */
	void setMaxTUsForRemainingQty(final int maxTUsForRemainingQty);

	/**
	 * Sets maximum TUs for remaining Qty to infinite (i.e. generate as many as needed).
	 */
	void setMaxTUsForRemainingQtyInfinite();

	/**
	 * @return true if maximum TUs for remaining Qty is infinite (i.e. generate as many as needed).
	 */
	boolean isMaxTUsForRemainingQtyInfinite();

	/**
	 * @return How many TUs to create for remaining Qty (i.e. after all LUs were created)
	 */
	int getMaxTUsForRemainingQty();

	/**
	 * @return How may TUs for remaining Qty were maximum created
	 */
	int getMaxTUsForRemainingQty_ActuallyCreated();

	/**
	 * @param createTUsForRemainingQty true if we shall create TU handling units for remaining qty.
	 * @see #loadRe
	 */
	void setCreateTUsForRemainingQty(final boolean createTUsForRemainingQty);

	/**
	 * @return true if we shall create TU handling units for remaining qty
	 * @see #loadRemaining(IAllocationRequest)
	 */
	boolean isCreateTUsForRemainingQty();

	/**
	 * If this instance was created via {@link ILUTUConfigurationFactory#createLUTUProducerAllocationDestination(I_M_HU_LUTU_Configuration)} then this getter shall return the config that was passed to the factory.
	 *
	 * When the lutu config is returned by this getter, then this producer won't consider any further changes on this configuration. It is used only for {@link I_M_HU#setM_HU_LUTU_Configuration(I_M_HU_LUTU_Configuration)}.
	 * Also, please don't rely on values from this configuration when calculating how much it will allocate but better ask methods like {@link #getQtyCUPerTU()} etc.
	 *
	 * @return LU/TU configuration reference or null
	 */
	I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration();

	/**
	 * Adds given HU to the list of already created LUs (if it's an LU) or to the list of already created TUs for remaining Qty.
	 *
	 * NOTE: if number of LU/TUs exceed requested number of LU/TUs this hu won't be added.
	 *
	 * @param hu
	 */
	void addCreatedLUOrTU(I_M_HU hu);

	/**
	 * @return How many LUs were actually created
	 */
	int getCreatedLUsCount();

	/**
	 * @return How many TUs were actually created for remaining Qty
	 */
	int getCreatedTUsForRemainingQtyCount();

	/**
	 * Calculate maximum total CU quantity that this producer can accept for given product.
	 *
	 * @param cuProduct
	 * @return Can return following values
	 *         <ul>
	 *         <li>{@link IAllocationRequest#QTY_INFINITE} if it can accept infinite quantity (i.e. some of the CU/TU, TU/LU, count LUs etc quantities are infinite)
	 *         <li>{@link BigDecimal#ZERO} if this configuration cannot accept any quantity
	 *         <li>positive quantity if maxium quantity could be calculated
	 *         </ul>
	 *
	 *         The UOM of returned quantity is {@link #getCUUOM()}.
	 */
	Quantity calculateTotalQtyCU(I_M_Product cuProduct);

	/**
	 * Calculates total CU quantity for single TU capacity that was defined.
	 *
	 * @see #calculateTotalQtyCU(I_M_Product)
	 * @see #getSingleCUPerTU()
	 */
	Quantity calculateTotalQtyCU();

	/**
	 * Set existing HUs to be considered when we do the LU/TU creation.
	 *
	 * All the matching HUs (that have the same LU/TU configuration) will be considered as "already created". Those who were not matched will be destroyed.
	 *
	 * @param existingHUs
	 */
	void setExistingHUs(IHUAllocations existingHUs);

}
