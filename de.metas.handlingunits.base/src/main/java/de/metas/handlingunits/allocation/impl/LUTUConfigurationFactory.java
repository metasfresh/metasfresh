package de.metas.handlingunits.allocation.impl;

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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.util.Check;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.quantity.CapacityInterface;
import de.metas.quantity.Quantity;
import lombok.NonNull;

public class LUTUConfigurationFactory implements ILUTUConfigurationFactory
{
	private static final String DYNATTR_DisableChangeCheckingOnSave = LUTUConfigurationFactory.class.getName() + "#DisableChangeCheckingOnSave";

	@Override
	public ILUTUProducerAllocationDestination createLUTUProducerAllocationDestination(@NonNull final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final LUTUProducerDestination luProducerDestination = new LUTUProducerDestination();
		luProducerDestination.setM_HU_LUTU_Configuration(lutuConfiguration);

		// LU Configuration
		final I_M_HU_PI_Item luPIItem = lutuConfiguration.getM_LU_HU_PI_Item();
		final int qtyLU = lutuConfiguration.getQtyLU().intValueExact();
		final boolean qtyLUInfinite = lutuConfiguration.isInfiniteQtyLU();
		final int qtyTU = lutuConfiguration.getQtyTU().intValueExact();
		final boolean qtyTUInfinite = lutuConfiguration.isInfiniteQtyTU();

		if (!handlingUnitsBL.isNoPI(luPIItem))
		{
			final I_M_HU_PI luPI = luPIItem.getM_HU_PI_Version().getM_HU_PI();
			luProducerDestination.setLUItemPI(luPIItem);
			luProducerDestination.setLUPI(luPI);
			if (qtyLUInfinite)
			{
				luProducerDestination.setMaxLUsInfinite();

				//
				// 07378: Fix behavior when max LUs are infinite, created max TUs are the ones we specify (otherwise we end up creating infinite HUs for 3 x Tomatoes)
				luProducerDestination.setMaxTUsForRemainingQty(qtyTU);
			}
			else
			{
				luProducerDestination.setMaxLUs(qtyLU);
			}

			// TU configuration
			Check.assume(!qtyTUInfinite, "qtyTUInfinite shall be false when dealing with concrete LUs");
			luProducerDestination.setMaxTUsPerLU(qtyTU);
			luProducerDestination.setCreateTUsForRemainingQty(false);
		}
		else
		{
			luProducerDestination.setLUItemPI(null);
			luProducerDestination.setLUPI(null);
			luProducerDestination.setMaxLUs(0);

			// TU configuration
			// luProducerDestination.setMaxTUsPerLU(0); // no need to set
			luProducerDestination.setCreateTUsForRemainingQty(true); // we will create only TUs

			if (qtyTUInfinite)
			{
				luProducerDestination.setMaxTUsForRemainingQtyInfinite();
			}
			else
			{
				luProducerDestination.setMaxTUsForRemainingQty(qtyTU);
			}
		}

		//
		// TU Configuration
		final I_M_HU_PI tuPI = lutuConfiguration.getM_TU_HU_PI();
		luProducerDestination.setTUPI(tuPI);
		// TU Capacity
		final I_M_Product cuProduct = lutuConfiguration.getM_Product();
		final I_C_UOM cuUOM = lutuConfiguration.getC_UOM();
		final boolean qtyCUInfinite = lutuConfiguration.isInfiniteQtyCU();
		final BigDecimal qtyCUPerTU = qtyCUInfinite ? Quantity.QTY_INFINITE : lutuConfiguration.getQtyCU();
		luProducerDestination.addCUPerTU(cuProduct, qtyCUPerTU, cuUOM);

		//
		// Misc configuration
		luProducerDestination.setC_BPartner(lutuConfiguration.getC_BPartner());
		luProducerDestination.setC_BPartner_Location_ID(lutuConfiguration.getC_BPartner_Location_ID());
		luProducerDestination.setHUStatus(lutuConfiguration.getHUStatus());
		luProducerDestination.setM_Locator(lutuConfiguration.getM_Locator());

		//
		// Return it
		return luProducerDestination;
	}

	@Override
	public I_M_HU_LUTU_Configuration createLUTUConfiguration(
			@NonNull final I_M_HU_PI_Item_Product tuPIItemProduct,
			@NonNull final I_M_Product cuProduct,
			@NonNull final I_C_UOM cuUOM,
			final org.compiere.model.I_C_BPartner bpartner,
			final boolean noLUForVirtualTU)
	{
		// Services used:
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHUCapacityBL huCapacityBL = Services.get(IHUCapacityBL.class);

		//
		// Context
		final Properties ctx = InterfaceWrapperHelper.getCtx(tuPIItemProduct);
		final IContextAware contextProvider;
		final String threadTrxName = trxManager.getThreadInheritedTrxName();
		if (trxManager.isNull(threadTrxName))
		{
			contextProvider = PlainContextAware.newOutOfTrxAllowThreadInherited(ctx);
		}
		else
		{
			contextProvider = trxManager.createThreadContextAware(ctx);
		}

		//
		// LU/TU configuration (draft)
		final I_M_HU_LUTU_Configuration lutuConfiguration = InterfaceWrapperHelper.newInstance(I_M_HU_LUTU_Configuration.class, contextProvider);
		lutuConfiguration.setC_BPartner(bpartner);
		lutuConfiguration.setIsActive(true);

		//
		// TU Configuration
		final I_M_HU_PI tuPI = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
		final CapacityInterface tuCapacity = huCapacityBL.getCapacity(tuPIItemProduct, cuProduct, cuUOM);
		//
		lutuConfiguration.setM_HU_PI_Item_Product(tuPIItemProduct);
		lutuConfiguration.setM_TU_HU_PI(tuPI);
		lutuConfiguration.setM_Product(cuProduct);
		lutuConfiguration.setC_UOM(cuUOM);
		if (tuCapacity.isInfiniteCapacity())
		{
			lutuConfiguration.setIsInfiniteQtyCU(true);
			lutuConfiguration.setQtyCU(BigDecimal.ZERO);
		}
		else
		{
			lutuConfiguration.setIsInfiniteQtyCU(false);
			lutuConfiguration.setQtyCU(tuCapacity.getCapacityQty());
		}


		//
		// LU Configuration
		final I_M_HU_PI_Item luPIItem;
		if (noLUForVirtualTU && IHUPIItemProductDAO.VIRTUAL_HU_PI_Item_Product_ID == tuPIItemProduct.getM_HU_PI_Item_Product_ID())
		{
			luPIItem = null; // we don't care if there is a matching PIItem, because with noLUForVirtualTU we don't want to put the virtual HU onto an LU
		}
		else
		{
			luPIItem = handlingUnitsDAO.retrieveDefaultParentPIItem(tuPI, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, bpartner);
		}

		if (luPIItem != null)
		{
			final I_M_HU_PI luPI = luPIItem.getM_HU_PI_Version().getM_HU_PI();
			lutuConfiguration.setM_LU_HU_PI(luPI);
			lutuConfiguration.setM_LU_HU_PI_Item(luPIItem);

			lutuConfiguration.setIsInfiniteQtyLU(true); // we produce as many as needed
			lutuConfiguration.setQtyLU(BigDecimal.ZERO);

			final int qtyTU = luPIItem.getQty().intValueExact();
			lutuConfiguration.setIsInfiniteQtyTU(false);
			lutuConfiguration.setQtyTU(BigDecimal.valueOf(qtyTU));
		}
		else
		{
			lutuConfiguration.setM_LU_HU_PI(null);
			lutuConfiguration.setM_LU_HU_PI_Item(null);

			lutuConfiguration.setIsInfiniteQtyLU(false);
			lutuConfiguration.setQtyLU(BigDecimal.ZERO);

			lutuConfiguration.setIsInfiniteQtyTU(true); // as many as needed
			lutuConfiguration.setQtyTU(BigDecimal.ZERO);
		}

		return lutuConfiguration;
	}

	@Override
	public boolean isNoLU(@NonNull final I_M_HU_LUTU_Configuration lutuConfiguration)
	{

		return lutuConfiguration.getM_LU_HU_PI_Item_ID() <= 0;
	}

	private ArrayKey createKeyForHUProducer(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		Check.assumeNotNull(lutuConfiguration, "lutuConfiguration not null");

		final List<Object> keyItems = new ArrayList<>();

		//
		// LU: PI
		final int luPIItemId = lutuConfiguration.getM_LU_HU_PI_Item_ID();
		final boolean hasLU;
		if (luPIItemId > 0)
		{
			final int luPIId = lutuConfiguration.getM_LU_HU_PI_ID();
			Check.assume(luPIId > 0, "LU PI ID shall be set for {}", lutuConfiguration);

			keyItems.add(luPIItemId); // LU M_HU_PI_Item_ID
			keyItems.add(luPIId); // LU M_HU_PI_ID
			hasLU = true;
		}
		else
		{
			keyItems.add(-1); // LU M_HU_PI_Item_ID
			keyItems.add(-1); // LU M_HU_PI_ID
			hasLU = false;
		}

		//
		// LU: Qty
		// NOTE: we skip QtyLU because it's not relevant when we check if the configuration is still compliant with an HU

		//
		// TU: PI
		final int tuPIId = lutuConfiguration.getM_TU_HU_PI_ID();
		if (tuPIId > 0)
		{
			keyItems.add(tuPIId); // TU M_HU_PI_ID
		}
		else
		{
			keyItems.add(-1); // TU M_HU_PI_ID
		}

		//
		// TU: Qty
		if (lutuConfiguration.isInfiniteQtyTU())
		{
			keyItems.add(true); // IsInfiniteQtyTU
			keyItems.add(0); // Qty TU
		}
		// case: there is no LU. In this case we can ignore the QtyTU because it's not relevant when we check if configuration is still compiant with an HU
		else if (!hasLU)
		{
			keyItems.add(false); // IsInfiniteQtyTU
			keyItems.add("QtyTU_NA"); // Qty TU: N/A
		}
		else
		{
			keyItems.add(false); // IsInfiniteQtyTU
			keyItems.add(lutuConfiguration.getQtyTU().intValue()); // Qty TU
		}

		//
		// CU
		final int productId = lutuConfiguration.getM_Product_ID();
		final int uomId = lutuConfiguration.getC_UOM_ID();
		keyItems.add(productId > 0 ? productId : -1);
		keyItems.add(uomId > 0 ? uomId : -1);
		if (lutuConfiguration.isInfiniteQtyCU())
		{
			keyItems.add(true); // IsInfiniteQtyCU
			keyItems.add(BigDecimal.ZERO); // Qty CU
		}
		else
		{
			final BigDecimal qtyCU = NumberUtils.stripTrailingDecimalZeros(lutuConfiguration.getQtyCU());
			keyItems.add(false); // IsInfiniteQtyCU
			keyItems.add(qtyCU); // Qty CU
		}

		//
		// Misc
		final int locatorId = lutuConfiguration.getM_Locator_ID();
		final int bpartnerId = lutuConfiguration.getC_BPartner_ID();
		final int bpartnerLocationId = lutuConfiguration.getC_BPartner_Location_ID();
		final String huStatus = lutuConfiguration.getHUStatus();
		keyItems.add(locatorId > 0 ? locatorId : -1);
		keyItems.add(bpartnerId > 0 ? bpartnerId : -1);
		keyItems.add(bpartnerLocationId > 0 ? bpartnerLocationId : -1);
		keyItems.add(huStatus);
		keyItems.add(lutuConfiguration.isActive());

		return Util.mkKey(keyItems.toArray());
	}

	@Override
	public I_M_HU_LUTU_Configuration copy(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		Check.assumeNotNull(lutuConfiguration, "lutuConfiguration not null");
		final I_M_HU_LUTU_Configuration lutuConfigurationNew = InterfaceWrapperHelper.newInstance(I_M_HU_LUTU_Configuration.class, lutuConfiguration);
		final boolean honorIsCalculated = true;
		InterfaceWrapperHelper.copyValues(lutuConfiguration, lutuConfigurationNew, honorIsCalculated);

		return lutuConfigurationNew;
	}

	@Override
	public boolean isSameForHUProducer(final I_M_HU_LUTU_Configuration lutuConfiguration1, final I_M_HU_LUTU_Configuration lutuConfiguration2)
	{
		if (lutuConfiguration1 == lutuConfiguration2)
		{
			return true;
		}
		if (lutuConfiguration1 == null)
		{
			return false;
		}
		if (lutuConfiguration2 == null)
		{
			return false;
		}

		final ArrayKey key1 = createKeyForHUProducer(lutuConfiguration1);
		final ArrayKey key2 = createKeyForHUProducer(lutuConfiguration2);
		return key1.equals(key2);
	}

	@Override
	public void assertNotChanged(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		Check.assumeNotNull(lutuConfiguration, "lutuConfiguration not null");

		//
		// Check if we were explicitly asked to not check the changes
		final Boolean disableChangeCheckingOnSave = InterfaceWrapperHelper.getDynAttribute(lutuConfiguration, DYNATTR_DisableChangeCheckingOnSave);
		if (disableChangeCheckingOnSave != null && disableChangeCheckingOnSave == true)
		{
			return;
		}

		//
		// Make sure LU/TU configuration does not changed (from LUTU Producer perspective)
		final I_M_HU_LUTU_Configuration lutuConfigurationOld = InterfaceWrapperHelper.createOld(lutuConfiguration, I_M_HU_LUTU_Configuration.class);
		if (isSameForHUProducer(lutuConfiguration, lutuConfigurationOld))
		{
			return;
		}

		//
		// Prevent changing the configuration
		// NOTE: it's safe to throw an english/internal error because mainly this is an internal error
		throw new HUException("Changing already saved LU/TU Configuration is not allowed: " + lutuConfiguration);
	}

	@Override
	public void save(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final boolean disableChangeCheckingOnSave = false;
		save(lutuConfiguration, disableChangeCheckingOnSave);
	}

	@Override
	public void save(final I_M_HU_LUTU_Configuration lutuConfiguration, final boolean disableChangeCheckingOnSave)
	{
		final Object disableChangeCheckingOnSaveOld = InterfaceWrapperHelper.setDynAttribute(lutuConfiguration, DYNATTR_DisableChangeCheckingOnSave, disableChangeCheckingOnSave);
		try
		{
			InterfaceWrapperHelper.save(lutuConfiguration);
		}
		finally
		{
			InterfaceWrapperHelper.setDynAttribute(lutuConfiguration, DYNATTR_DisableChangeCheckingOnSave, disableChangeCheckingOnSaveOld);
		}
	}

	@Override
	public int calculateQtyLUForTotalQtyTUs(final I_M_HU_LUTU_Configuration lutuConfiguration, final BigDecimal qtyTUsTotal)
	{
		Check.assumeNotNull(lutuConfiguration, "lutuConfiguration not null");

		if (qtyTUsTotal == null || qtyTUsTotal.signum() <= 0)
		{
			return 0;
		}

		if (isNoLU(lutuConfiguration))
		{
			return 0;
		}

		final BigDecimal qtyTUsPerLU = lutuConfiguration.getQtyTU();
		if (qtyTUsPerLU.signum() == 0)
		{
			// Qty TU not available => cannot compute
			return 0;
		}

		final int qtyLU = qtyTUsTotal.divide(qtyTUsPerLU, 0, RoundingMode.UP).intValueExact();
		return qtyLU;
	}

	@Override
	public int calculateQtyLUForTotalQtyCUs(final I_M_HU_LUTU_Configuration lutuConfiguration, final BigDecimal qtyCUsTotal, final I_C_UOM qtyCUsTotalUOM)
	{
		Check.assumeNotNull(lutuConfiguration, "lutuConfiguration not null");

		if (qtyCUsTotal == null || qtyCUsTotal.signum() <= 0)
		{
			return 0;
		}

		if (isNoLU(lutuConfiguration))
		{
			return 0;
		}

		//
		// Calculate how many CUs we need for an LU
		final BigDecimal qtyCUsPerLU = lutuConfiguration.getQtyCU().multiply(lutuConfiguration.getQtyTU());
		if (qtyCUsPerLU.signum() <= 0)
		{
			return 0;
		}

		//
		// Convert the total QtyCU to our internal capacity UOM, to be able to compute using same UOM.
		final Quantity qtyCUsTotal_Converted = convertQtyToLUTUConfigurationUOM(qtyCUsTotal, qtyCUsTotalUOM, lutuConfiguration);

		//
		// Calculate how many LUs we need for given total QtyCU (converted to our capacity UOM)
		final int qtyLUs = qtyCUsTotal_Converted.getQty().divide(qtyCUsPerLU, 0, RoundingMode.UP).intValueExact();
		return qtyLUs;
	}

	@Override
	public Quantity calculateQtyCUsTotal(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final I_C_UOM uom = lutuConfiguration.getC_UOM();

		//
		// CU
		if (lutuConfiguration.isInfiniteQtyCU())
		{
			return Quantity.infinite(uom);
		}

		final BigDecimal qtyCU = lutuConfiguration.getQtyCU();
		if (qtyCU.signum() <= 0)
		{
			return Quantity.zero(uom);
		}

		//
		// TU
		if (lutuConfiguration.isInfiniteQtyTU())
		{
			return Quantity.infinite(uom);
		}
		final BigDecimal qtyTU = lutuConfiguration.getQtyTU();
		if (qtyTU.signum() <= 0)
		{
			return Quantity.zero(uom);
		}

		//
		//
		final BigDecimal qtyCUsPerLU = qtyCU.multiply(qtyTU);

		//
		// LU
		if (isNoLU(lutuConfiguration))
		{
			return new Quantity(qtyCUsPerLU, uom);
		}
		else
		{
			final BigDecimal qtyLU = lutuConfiguration.getQtyLU();
			if (qtyLU.signum() <= 0)
			{
				return Quantity.zero(uom);
			}
			else
			{
				final BigDecimal qtyCUsTotal = qtyCUsPerLU.multiply(qtyLU);
				return new Quantity(qtyCUsTotal, uom);
			}
		}
	}

	@Override
	public Quantity convertQtyToLUTUConfigurationUOM(final BigDecimal qtyValue, final I_C_UOM qtyUOM, final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final IUOMConversionContext uomConversionCtx = uomConversionBL.createConversionContext(lutuConfiguration.getM_Product());

		final Quantity qty = new Quantity(qtyValue, qtyUOM);
		final I_C_UOM uomTo = lutuConfiguration.getC_UOM();
		return uomConversionBL.convertQuantityTo(qty, uomConversionCtx, uomTo);
	}

	@Override
	public void adjustForTotalQtyTUsAndCUs(
			@NonNull final I_M_HU_LUTU_Configuration lutuConfiguration,
			@NonNull final BigDecimal qtyTUsTotal,
			@NonNull final BigDecimal qtyCUsTotal)
	{
		//
		// Case: Infinite Qty CU
		// e.g. we are receiving virtual PIs
		if (lutuConfiguration.isInfiniteQtyCU())
		{
			lutuConfiguration.setIsInfiniteQtyCU(false);
			lutuConfiguration.setQtyCU(qtyCUsTotal);

			lutuConfiguration.setIsInfiniteQtyTU(false);
			lutuConfiguration.setQtyTU(BigDecimal.ONE);

			lutuConfiguration.setIsInfiniteQtyLU(false);
			if (isNoLU(lutuConfiguration))
			{
				lutuConfiguration.setQtyLU(BigDecimal.ZERO);
			}
			else
			{
				lutuConfiguration.setQtyLU(BigDecimal.ONE);
			}

			// NOTE: we are returning here, because this is a corner case which we handled separatelly from other cases
			return;
		}

		Check.assume(!lutuConfiguration.isInfiniteQtyCU(), "Infinite QtyCU not allowed for {}", lutuConfiguration);
		final BigDecimal qtyCUsPerTU = lutuConfiguration.getQtyCU();

		//
		// Case: QtyTUs/LU is finite
		if (!lutuConfiguration.isInfiniteQtyTU())
		{
			final BigDecimal qtyTUsPerLU = lutuConfiguration.getQtyTU();
			Check.assume(qtyTUsPerLU.signum() > 0, "QtyTU shall be positive: {}", qtyTUsPerLU);

			//
			// Calculate how many LUs we will have based on Order's QtyTU
			final BigDecimal qtyLUs_Effective = qtyTUsTotal.divide(qtyTUsPerLU, 0, RoundingMode.UP);

			//
			// Calculate how many TUs we will have
			// NOTE: this is covering the case when for example your LU accepts 96xTUs but in your order you have just 10xTUs.
			// In this case, QtyTUs shall be only 10.
			final BigDecimal qtyTUs_Effective = qtyTUsPerLU.min(qtyTUsTotal);

			//
			// Calculate how many CUs we will have
			// NOTE: this is covering the case when for example your TU accepts 50xCUs but you have only 10xCUs.
			// In this case, QtyCUs shall only 10.
			final BigDecimal qtyCUs_Effective = qtyCUsPerTU.min(qtyCUsTotal);

			lutuConfiguration.setIsInfiniteQtyLU(false); // since we calculated it, we're not considering it infinite any longer
			lutuConfiguration.setQtyLU(qtyLUs_Effective);

			lutuConfiguration.setIsInfiniteQtyTU(false); // since we calculated it, we're not considering it infinite any longer
			lutuConfiguration.setQtyTU(qtyTUs_Effective);

			lutuConfiguration.setIsInfiniteQtyCU(false); // since we calculated it, we're not considering it infinite any longer
			lutuConfiguration.setQtyCU(qtyCUs_Effective);
		}
		//
		// Case: QtyTUs/LU is infinite
		else
		{
			Check.assume(!lutuConfiguration.isInfiniteQtyLU(), "LU cannot be infinite when TU already is infinite for {}", lutuConfiguration);

			final BigDecimal qtyTUs_Effective = qtyTUsTotal;
			final BigDecimal qtyCUs_Effective = qtyCUsPerTU.min(qtyCUsTotal);

			lutuConfiguration.setIsInfiniteQtyTU(false); // since we calculated it, we're not considering it infinite any longer
			lutuConfiguration.setQtyTU(qtyTUs_Effective);

			lutuConfiguration.setIsInfiniteQtyCU(false); // since we calculated it, we're not considering it infinite any longer
			lutuConfiguration.setQtyCU(qtyCUs_Effective);
		}
	}
}
