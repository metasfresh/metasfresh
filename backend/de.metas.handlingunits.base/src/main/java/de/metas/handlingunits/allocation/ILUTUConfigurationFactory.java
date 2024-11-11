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

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.ISingletonService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public interface ILUTUConfigurationFactory extends ISingletonService
{
	/**
	 * @param tuPIItemProduct  may not be {@code null}
	 * @param noLUForVirtualTU determines if the method shall attempt to configure the lutuConfig with an LU if the given {@code tuPIItemProduct} is the virtual one.<br>
	 *                         Depending on the use case (and only if the packing instructions permit it!), the option to place a CU directly on a LU might or might not be what the user wants.<br>
	 */
	I_M_HU_LUTU_Configuration createLUTUConfiguration(
			@NonNull I_M_HU_PI_Item_Product tuPIItemProduct,
			@NonNull ProductId cuProductId,
			@NonNull UomId cuUomId,
			BPartnerId bpartnerId,
			boolean noLUForVirtualTU);

	/**
	 * Create and configure a {@link ILUTUProducerAllocationDestination} for the given {@code lutuConfiguration} record
	 */
	ILUTUProducerAllocationDestination createLUTUProducerAllocationDestination(I_M_HU_LUTU_Configuration lutuConfiguration);

	I_M_HU_LUTU_Configuration createNewLUTUConfigWithParams(ILUTUConfigurationFactory.CreateLUTUConfigRequest lutuConfigRequest);

	/**
	 * Creates a copy of given configuration.
	 * <p>
	 * NOTE: it is not saving the new configuration.
	 *
	 * @return copy of <code>lutuConfiguration</code>
	 */
	I_M_HU_LUTU_Configuration copy(I_M_HU_LUTU_Configuration lutuConfiguration);

	/**
	 * Decide if both parameters are not {@code null} and are "equal enough" (according to location, status, bpartner etc) for the LUTU-config user interface.
	 */
	boolean isSameForHUProducer(
			I_M_HU_LUTU_Configuration lutuConfiguration1,
			I_M_HU_LUTU_Configuration lutuConfiguration2);

	/**
	 * Called before saving the configuration
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
	void save(
			I_M_HU_LUTU_Configuration lutuConfiguration,
			boolean disableChangeCheckingOnSave);

	boolean isNoLU(I_M_HU_LUTU_Configuration lutuConfiguration);

	int calculateQtyLUForTotalQtyTUs(
			I_M_HU_LUTU_Configuration lutuConfiguration,
			BigDecimal qtyTUsTotal);

	/**
	 * Adjust Qty CU, TU and LU to preciselly match our given <code>qtyTUsTotal</code>/<code>qtyCUsTotal</code>.
	 * <p>
	 * TODO: more documentation needed here
	 *
	 * @param lutuConfiguration
	 * @param qtyTUsTotal
	 * @param qtyCUsTotal
	 */
	void adjustForTotalQtyTUsAndCUs(
			I_M_HU_LUTU_Configuration lutuConfiguration,
			BigDecimal qtyTUsTotal,
			BigDecimal qtyCUsTotal);

	/**
	 * Calculate how many LUs we would need (using given configuration) for given total CU quantity
	 *
	 * @param lutuConfiguration
	 * @param qtyCUsTotal       total CU quantity
	 * @return how many LUs are needed or ZERO if we are dealing with infinite capacities
	 */
	int calculateQtyLUForTotalQtyCUs(
			I_M_HU_LUTU_Configuration lutuConfiguration,
			Quantity qtyCUsTotal);

	/**
	 * Calculates how many CUs (in total).
	 *
	 * @param lutuConfiguration
	 * @return quantity; could be infinite or zero.
	 */
	Quantity calculateQtyCUsTotal(I_M_HU_LUTU_Configuration lutuConfiguration);

	/**
	 * Converts given quantity to {@link I_M_HU_LUTU_Configuration}'s UOM.
	 *
	 * @return quantity converted to {@link I_M_HU_LUTU_Configuration}'s UOM.
	 */
	Quantity convertQtyToLUTUConfigurationUOM(
			Quantity qty,
			I_M_HU_LUTU_Configuration lutuConfiguration);

	@Nullable
	static I_C_UOM extractUOMOrNull(@NonNull final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final UomId uomId = extractUomIdOrNull(lutuConfiguration);
		return uomId != null
				? Services.get(IUOMDAO.class).getById(uomId)
				: null;
	}

	@Nullable
	static UomId extractUomIdOrNull(final @NonNull I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		return UomId.ofRepoIdOrNull(lutuConfiguration.getC_UOM_ID());
	}

	static BPartnerId extractBPartnerIdOrNull(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		return BPartnerId.ofRepoIdOrNull(lutuConfiguration.getC_BPartner_ID());
	}

	static I_M_HU_PI_Item_Product extractHUPIItemProduct(@NonNull final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		I_M_HU_PI_Item_Product huPIItemProduct = extractHUPIItemProductOrNull(lutuConfiguration);
		if (huPIItemProduct == null)
		{
			throw new HUException("No PI Item Product set for " + lutuConfiguration);
		}
		return huPIItemProduct;
	}

	static I_M_HU_PI_Item_Product extractHUPIItemProductOrNull(@NonNull final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final HUPIItemProductId huPIItemProductId = HUPIItemProductId.ofRepoIdOrNull(lutuConfiguration.getM_HU_PI_Item_Product_ID());
		return huPIItemProductId != null
				? Services.get(IHUPIItemProductDAO.class).getRecordById(huPIItemProductId)
				: null;
	}

	@Value
	@Builder
	class CreateLUTUConfigRequest
	{
		@NonNull
		I_M_HU_LUTU_Configuration baseLUTUConfiguration;

		@NonNull
		BigDecimal qtyTU;

		@NonNull
		BigDecimal qtyCUsPerTU;

		@NonNull
		Integer tuHUPIItemProductID;

		@Nullable
		BigDecimal qtyLU;

		@Nullable
		Integer luHUPIID;
	}

}
