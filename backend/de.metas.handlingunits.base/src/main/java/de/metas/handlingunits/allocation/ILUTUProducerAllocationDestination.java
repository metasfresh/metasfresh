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

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.allocation.transfer.LUTUResult;
import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * It's an {@link IHUProducerAllocationDestination} which can be configured to produce TUs on LUs.
 *
 * @author tsa
 */
public interface ILUTUProducerAllocationDestination extends IHUProducerAllocationDestination
{
	void setTUPI(@NonNull HUPIItemProductId piItemProductId, @Nullable ProductId productId);

	I_M_HU_PI getTUPI();

	void setTUPI(@NonNull HuPackingInstructionsId tuPIId);

	/**
	 * Set the PI for the TU that shall be built.
	 */
	void setTUPI(final I_M_HU_PI tuPI);

	/**
	 * Register another capacity spec with this producer.
	 */
	void addCUPerTU(Capacity tuCapacity);

	/**
	 * @param qtyCUPerTU quantity
	 */
	void addCUPerTU(ProductId cuProductId, BigDecimal qtyCUPerTU, I_C_UOM cuUOM);

	/**
	 * Gets single TU capacity.
	 * <p>
	 * If there is no TU capacity defined or there are more than one TU capacities, this method will throw an exception.
	 *
	 * @return TU capacity
	 */
	Capacity getSingleCUPerTU();

	I_M_HU_PI getLUPI();

	void setLUPI(final HuPackingInstructionsId luPIId);

	/**
	 * Specifies the PI for the loading unit. May be {@code null} for the case that a TU without LU is needed.
	 */
	void setLUPI(final I_M_HU_PI luPI);

	/**
	 * See {@link #setLUItemPI(I_M_HU_PI_Item)}.
	 */
	I_M_HU_PI_Item getLUItemPI();

	void setLUItemPI(@NonNull HuPackingInstructionsItemId luPIItemId);

	/**
	 * Sets LU PI's PI Item (with ItemType=HU) on which the TU will be included. May be {@code null} for the case that a TU without LU is needed.
	 */
	void setLUItemPI(final I_M_HU_PI_Item luItemPI);

	/**
	 * @return true if there is a LU configuration set, so only TUs will be generated
	 */
	boolean isNoLU();

	/**
	 * Convenience method for the case that a top-level TU is required.
	 * Call {@link #setTUPI(I_M_HU_PI)}, {@link #setLUItemPI(I_M_HU_PI_Item)}, {@link #setMaxLUs(int)} and {@link #setCreateTUsForRemainingQty(boolean)} accordingly.
	 */
	void setNoLU();

	/**
	 * Let this producer create as many LUs as needed.
	 * <p>
	 * Same as calling {@link #setMaxLUs(int)} with {@link Integer#MAX_VALUE} parameter.
	 */
	void setMaxLUsInfinite();

	/**
	 * @return true if producer will create as many LUs as needed
	 */
	boolean isMaxLUsInfinite();

	/**
	 * Set the maximum number of LUs to be created.
	 * <p>
	 * If <code>maxLUs</code> is ZERO then no LUs will be created. In this case only "remaining" TUs will be created.<br/>
	 * Please check the configuration methods: {@link #setCreateTUsForRemainingQty(boolean)}, {@link #setMaxTUsForRemainingQty(int)}.
	 */
	void setMaxLUs(final int maxLUs);

	void setMaxTUsPerLUInfinite();

	/**
	 * @return maximum number of LUs to be created
	 */
	int getMaxLUs();

	/**
	 * Sets how many TUs shall be created for one LU.
	 * <p>
	 * If this value is not set, it will be taken from {@link #setLUItemPI(I_M_HU_PI_Item)} configuration.
	 */
	void setMaxTUsPerLU(final int maxTUsPerLU);

	/**
	 * Gets how many TUs shall be created for one LU. Note, this is returning only the value which was set by {@link #setMaxTUsPerLU(int)}.
	 */
	int getMaxTUsPerLU();

	/**
	 * Gets how many TUs shall be created for one LU.
	 * <p>
	 * If TUs/LU was not set using {@link #setMaxTUsPerLU(int)}, this method will also check the {@link #getLUItemPI()}'s settings.
	 * <p>
	 * The returned value of this method will be used in the actual LU /TU generation.
	 */
	int getMaxTUsPerLU_Effective();

	/**
	 * @param maxTUsForRemainingQty how many TUs to create for the remaining Qty (i.e. after all LUs were created)
	 */
	void setMaxTUsForRemainingQty(final int maxTUsForRemainingQty);

	/**
	 * Sets maximum TUs for remaining Qty to infinite (i.e. generate as many as needed).
	 */
	void setMaxTUsForRemainingQtyInfinite();

	/**
	 * @return true if maximum TUs for remaining Qty are infinite (i.e. generate as many as needed).
	 */
	boolean isMaxTUsForRemainingQtyInfinite();

	/**
	 * @return How many TUs to create for the remaining Qty (i.e. after all LUs were created)
	 */
	int getMaxTUsForRemainingQty();

	/**
	 * @param createTUsForRemainingQty true if we shall create TU handling units for the remaining qty.
	 */
	void setCreateTUsForRemainingQty(final boolean createTUsForRemainingQty);

	/**
	 * @return true if we shall create TU handling units for the remaining qty
	 */
	boolean isCreateTUsForRemainingQty();

	/**
	 * If this instance was created via {@link ILUTUConfigurationFactory#createLUTUProducerAllocationDestination(I_M_HU_LUTU_Configuration)} then this getter shall return the config passed to the factory.
	 * <p>
	 * When this getter returns the LU/TU config, then this producer won't consider any further changes on this configuration.
	 * It is used only for {@link I_M_HU#setM_HU_LUTU_Configuration(I_M_HU_LUTU_Configuration)}.
	 * Also, please don't rely on values from this configuration when calculating how much it will allocate.
	 *
	 * @return LU/TU configuration reference or null
	 */
	I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration();

	/**
	 * Adds given HU to the list of already created LUs (if it's an LU) or to the list of already created TUs for remaining Qty.
	 * <p>
	 * NOTE: if the number of LU/TUs exceeds the requested number of LU/TUs, this <code>hu</code> won't be added.
	 */
	void addCreatedLUOrTU(I_M_HU hu);

	/**
	 * @return How many LUs were actually created
	 */
	int getCreatedLUsCount();

	/**
	 * Calculates total CU quantity for a single TU capacity that was defined.
	 *
	 * @see #getSingleCUPerTU()
	 */
	Quantity calculateTotalQtyCU();

	/**
	 * Set existing HUs to be considered when we do the LU/TU creation.
	 * <p>
	 * All the matching HUs (that have the same LU/TU configuration) will be considered as "already created". Those who were not matched will be destroyed.
	 */
	void setExistingHUs(IHUAllocations existingHUs);

	LUTUResult getResult();
}
