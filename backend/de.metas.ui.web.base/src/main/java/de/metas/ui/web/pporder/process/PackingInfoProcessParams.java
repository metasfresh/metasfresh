package de.metas.ui.web.pporder.process;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.util.WEBUI_ProcessHelper;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PackingInfoProcessParams
{
	private final transient ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);

	public static final String PARAM_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID)
	private int tu_HU_PI_Item_Product_ID;
	//
	public static final String PARAM_M_HU_PI_Item_ID = "M_HU_PI_Item_ID";
	@Param(parameterName = PARAM_M_HU_PI_Item_ID)
	private int lu_PI_Item_ID;
	//
	public static final String PARAM_QtyCUsPerTU = "QtyCUsPerTU";
	@Param(parameterName = PARAM_QtyCUsPerTU)
	private BigDecimal qtyCUsPerTU;
	//
	public static final String PARAM_QtyTU = "QtyTU";
	@Param(parameterName = PARAM_QtyTU)
	private BigDecimal qtyTU;
	//
	public static final String PARAM_QtyLU = "QtyLU";
	@Param(parameterName = PARAM_QtyLU)
	private BigDecimal qtyLU;

	private final IDocumentLUTUConfigurationManager defaultLUTUConfigManager;
	private transient I_M_HU_LUTU_Configuration _defaultLUTUConfig;
	private final BigDecimal enforceAvailableQtyTU;
	private final boolean enforceOneLUorTU;

	private final boolean enforcePhysicalTU;

	@Builder
	public PackingInfoProcessParams(
			@NonNull final IDocumentLUTUConfigurationManager defaultLUTUConfigManager,
			@Nullable final BigDecimal enforceAvailableQtyTU,
			final boolean enforceOneLUorTU,
			final boolean enforcePhysicalTU)
	{
		this.enforcePhysicalTU = enforcePhysicalTU;
		this.defaultLUTUConfigManager = defaultLUTUConfigManager;
		this.enforceAvailableQtyTU = enforceAvailableQtyTU;
		this.enforceOneLUorTU = enforceOneLUorTU;
	}

	public Object getParameterDefaultValue(final String parameterName)
	{
		final I_M_HU_LUTU_Configuration defaultLUTUConfig = getDefaultLUTUConfig();

		switch (parameterName)
		{
			case PARAM_M_HU_PI_Item_Product_ID:
				return defaultLUTUConfig.getM_HU_PI_Item_Product_ID();
			case PARAM_M_HU_PI_Item_ID:
				return defaultLUTUConfig.getM_LU_HU_PI_Item_ID();
			case PARAM_QtyCUsPerTU:
				return defaultLUTUConfig.getQtyCUsPerTU();
			case PARAM_QtyTU:
				return defaultLUTUConfig.getQtyTU();
			case PARAM_QtyLU:
				return defaultLUTUConfig.getQtyLU();
			default:
				return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	/**
	 *
	 * @return a list of PI item products that match the selected CU's product and partner, sorted by name.
	 */
	public LookupValuesList getM_HU_PI_Item_Products()
	{
		final I_M_HU_LUTU_Configuration defaultLUTUConfig = getDefaultLUTUConfig();

		final ProductId productId = ProductId.ofRepoId(defaultLUTUConfig.getM_Product_ID());
		final BPartnerId bpartnerId = ILUTUConfigurationFactory.extractBPartnerIdOrNull(defaultLUTUConfig);

		final boolean includeVirtualItem = !enforcePhysicalTU;
		final LookupValuesList huPIItemProducts = WEBUI_ProcessHelper.retrieveHUPIItemProducts(Env.getCtx(), productId, bpartnerId, includeVirtualItem);

		return huPIItemProducts;
	}

	public LookupValuesList getM_HU_PI_Item_IDs(@Nullable final I_M_HU_PI_Item_Product pip)
	{
		if (pip == null)
		{
			return LookupValuesList.EMPTY;
		}

		final List<I_M_HU_PI_Item> luPIItems = getAvailableLuPIItems(pip, ILUTUConfigurationFactory.extractBPartnerIdOrNull(getDefaultLUTUConfig()));

		return luPIItems.stream()
				.map(luPIItem -> IntegerLookupValue.of(luPIItem.getM_HU_PI_Item_ID(), WEBUI_ProcessHelper.buildHUPIItemString(luPIItem)))
				.collect(LookupValuesList.collect());
	}

	private List<I_M_HU_PI_Item> getAvailableLuPIItems(
			@NonNull final I_M_HU_PI_Item_Product pip,
			@Nullable final BPartnerId bpartnerId)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final I_M_HU_PI piOfCurrentPip = pip.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();

		final List<I_M_HU_PI_Item> luPIItems = handlingUnitsDAO.retrieveParentPIItemsForParentPI(piOfCurrentPip,
				null, // huUnitType
				bpartnerId);
		return luPIItems;
	}

	public I_M_HU_LUTU_Configuration getDefaultLUTUConfig()
	{
		if (_defaultLUTUConfig == null)
		{
			final I_M_HU_LUTU_Configuration defaultLUTUConfig = defaultLUTUConfigManager.getCreateLUTUConfiguration();

			//
			// Apply adjustment rules to default LU/TU config
			if (enforceOneLUorTU)
			{
				adjustDefaultLUTUConfig_EnforceOneLUorTU(defaultLUTUConfig, enforceAvailableQtyTU);
			}
			else if (enforceAvailableQtyTU != null)
			{
				// IMPORTANT: this rule shall be applied if not enforceOneLUorTU
				adjustDefaultLUTUConfig_EnforceAvailableTUs(defaultLUTUConfig, enforceAvailableQtyTU);
			}

			if (enforcePhysicalTU)
			{
				// check if we need to do something
				boolean needToFallback;
				if (defaultLUTUConfig.getM_HU_PI_Item_Product_ID() <= 0)
				{
					needToFallback = true; // no piip specified at all
				}
				else
				{
					// check if the piip that we got is the virtual one. If yes, we need to fallback
					final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
					needToFallback = defaultLUTUConfig.getM_HU_PI_Item_Product_ID() == hupiItemProductDAO.retrieveVirtualPIMaterialItemProduct(Env.getCtx()).getM_HU_PI_Item_Product_ID();
				}
				if (needToFallback)
				{
					insertPhysicalFallbackTU(defaultLUTUConfig);
				}
			}

			//
			// Make sure nobody is overriding the existing configuration
			if (defaultLUTUConfig.getM_HU_LUTU_Configuration_ID() > 0)
			{
				InterfaceWrapperHelper.setSaveDeleteDisabled(defaultLUTUConfig, true);
			}
			_defaultLUTUConfig = defaultLUTUConfig;
		}
		return _defaultLUTUConfig;

	}

	private void insertPhysicalFallbackTU(@NonNull final I_M_HU_LUTU_Configuration defaultLUTUConfig)
	{
		final BPartnerId bpartnerId = ILUTUConfigurationFactory.extractBPartnerIdOrNull(defaultLUTUConfig);
		final ProductId productId = ProductId.ofRepoId(defaultLUTUConfig.getM_Product_ID());

		final List<I_M_HU_PI_Item_Product> availableHUPIItemProductRecords = WEBUI_ProcessHelper.retrieveHUPIItemProductRecords(
				Env.getCtx(),
				productId,
				bpartnerId,
				false); // includeVirtualItem == false

		Check.errorIf(availableHUPIItemProductRecords.isEmpty(),
				"There is no non-virtual M_HU_PI_Item_Product value for the given product and bPartner; product={}; bPartner={}",
				productId, bpartnerId);

		final I_M_HU_PI_Item_Product pip = availableHUPIItemProductRecords.get(0);
		defaultLUTUConfig.setM_HU_PI_Item_Product_ID(pip.getM_HU_PI_Item_Product_ID());
		defaultLUTUConfig.setM_TU_HU_PI_ID(pip.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI_ID());
		defaultLUTUConfig.setQtyCUsPerTU(pip.getQty());

		final List<I_M_HU_PI_Item> luPIItems = getAvailableLuPIItems(pip, bpartnerId);
		if (luPIItems.isEmpty())
		{
			defaultLUTUConfig.setM_LU_HU_PI_Item(null);
			defaultLUTUConfig.setM_LU_HU_PI(null);
		}
		else
		{
			final I_M_HU_PI_Item luPiItem = luPIItems.get(0);
			defaultLUTUConfig.setM_LU_HU_PI_Item(luPiItem);
			defaultLUTUConfig.setQtyTU(luPiItem.getQty());
			defaultLUTUConfig.setM_LU_HU_PI_ID(luPiItem.getM_HU_PI_Version().getM_HU_PI_ID());
		}
	}

	/**
	 * Modifies the given {@code defaultLUTUConfig} such that <b>one</b> top level HU (either LU or TU) is created.
	 * 
	 * @param defaultLUTUConfig
	 * @param availableQtyTU optional, may be {@code null}. If given, and the given {@code defaultLUTUConfig}'s top level HU is an LU, then this is the number of TUs within the LU.
	 */
	private void adjustDefaultLUTUConfig_EnforceOneLUorTU(
			@NonNull final I_M_HU_LUTU_Configuration defaultLUTUConfig,
			@Nullable final BigDecimal availableQtyTU)
	{
		if (lutuConfigurationFactory.isNoLU(defaultLUTUConfig))
		{
			//
			// Adjust TU
			defaultLUTUConfig.setIsInfiniteQtyTU(false);
			defaultLUTUConfig.setQtyTU(BigDecimal.ONE);
		}
		else
		{
			//
			// Adjust LU
			defaultLUTUConfig.setIsInfiniteQtyLU(false);
			defaultLUTUConfig.setQtyLU(BigDecimal.ONE);

			//
			// Adjust TU
			// * if the standard QtyTU is less than how much is available to be received => enforce the available Qty
			// * else always take the standard QtyTU
			// see https://github.com/metasfresh/metasfresh-webui/issues/228
			{
				if (availableQtyTU != null && availableQtyTU.signum() > 0 && availableQtyTU.compareTo(defaultLUTUConfig.getQtyTU()) < 0)
				{
					defaultLUTUConfig.setQtyTU(availableQtyTU);
				}
			}
		}
	}

	/**
	 * Modifies the given {@code defaultLUTUConfig} such that the TU quantity and (if applicable) also the LU quantity are consistent with the given {@code availableQtyTU}.
	 * 
	 * @param defaultLUTUConfig
	 * @param availableQtyTU
	 */
	private void adjustDefaultLUTUConfig_EnforceAvailableTUs(
			@NonNull final I_M_HU_LUTU_Configuration defaultLUTUConfig,
			@NonNull final BigDecimal availableQtyTU)
	{
		//
		// TU
		{
			if (availableQtyTU.signum() > 0 && availableQtyTU.compareTo(defaultLUTUConfig.getQtyTU()) < 0)
			{
				defaultLUTUConfig.setQtyTU(availableQtyTU);
			}
		}

		//
		// LU
		{
			final int qtyLU;
			if (availableQtyTU.signum() <= 0)
			{
				qtyLU = 0;
			}
			else
			{
				qtyLU = lutuConfigurationFactory.calculateQtyLUForTotalQtyTUs(defaultLUTUConfig, availableQtyTU);
			}

			defaultLUTUConfig.setQtyLU(BigDecimal.valueOf(qtyLU));
		}
	}

	public static Quantity calculateTotalQtyCUs(final I_M_HU_LUTU_Configuration lutuConfig)
	{
		final Quantity qtyCUsTotal = Services.get(ILUTUConfigurationFactory.class).calculateQtyCUsTotal(lutuConfig);
		if (qtyCUsTotal.isZero())
		{
			throw new AdempiereException("Zero quantity to receive");
		}
		else if (qtyCUsTotal.isInfinite())
		{
			throw new AdempiereException("Quantity to receive was not determined");
		}

		return qtyCUsTotal;
	}

	public I_M_HU_LUTU_Configuration createNewLUTUConfigFromDefaultsOnly()
	{
		final I_M_HU_LUTU_Configuration defaultLUTUConfigNewCopy = InterfaceWrapperHelper.copy()
				.setFrom(getDefaultLUTUConfig())
				.copyToNew(I_M_HU_LUTU_Configuration.class);

		lutuConfigurationFactory.save(defaultLUTUConfigNewCopy);
		return defaultLUTUConfigNewCopy;
	}

	public I_M_HU_LUTU_Configuration createAndSaveNewLUTUConfig()
	{
		final I_M_HU_LUTU_Configuration defaultLUTUConfig = getDefaultLUTUConfig();

		// Validate parameters
		final int lu_PI_Item_ID = getLuPiItemId(); // not mandatory
		final HUPIItemProductId M_HU_PI_Item_Product_ID = getTU_HU_PI_Item_Product_ID();
		final BigDecimal qtyCU = getQtyCUsPerTU();

		final BigDecimal qtyTU = M_HU_PI_Item_Product_ID.isVirtualHU() ? BigDecimal.ONE : this.qtyTU;

		if (qtyCU == null || qtyCU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCUsPerTU);
		}
		if (qtyTU == null || qtyTU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyTU);
		}

		final I_M_HU_LUTU_Configuration lutuConfigNew = InterfaceWrapperHelper.copy()
				.setFrom(defaultLUTUConfig)
				.copyToNew(I_M_HU_LUTU_Configuration.class);

		// CU
		lutuConfigNew.setQtyCUsPerTU(qtyCU);
		lutuConfigNew.setIsInfiniteQtyCU(false);

		// TU
		configureLUTUConfigTU(lutuConfigNew, M_HU_PI_Item_Product_ID, qtyTU);

		// LU
		configureLUTUConfigLU(lutuConfigNew, lu_PI_Item_ID, getQtyLU());

		lutuConfigurationFactory.save(lutuConfigNew);
		return lutuConfigNew;
	}

	private static void configureLUTUConfigTU(
			@NonNull final I_M_HU_LUTU_Configuration lutuConfigNew,
			final HUPIItemProductId M_HU_PI_Item_Product_ID,
			@NonNull final BigDecimal qtyTU)
	{
		final I_M_HU_PI_Item_Product tuPIItemProduct = Services.get(IHUPIItemProductDAO.class).getRecordById(M_HU_PI_Item_Product_ID);
		final I_M_HU_PI tuPI = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();

		lutuConfigNew.setM_HU_PI_Item_Product_ID(tuPIItemProduct.getM_HU_PI_Item_Product_ID());
		lutuConfigNew.setM_TU_HU_PI(tuPI);
		lutuConfigNew.setQtyTU(qtyTU);
		lutuConfigNew.setIsInfiniteQtyTU(false);
	}

	private static void configureLUTUConfigLU(
			@NonNull final I_M_HU_LUTU_Configuration lutuConfigNew,
			final int lu_PI_Item_ID,
			final BigDecimal qtyLU)
	{
		if (lu_PI_Item_ID > 0)
		{
			if (qtyLU == null || qtyLU.signum() <= 0)
			{
				throw new FillMandatoryException(PARAM_QtyLU);
			}

			final I_M_HU_PI_Item luPI_Item = loadOutOfTrx(lu_PI_Item_ID, I_M_HU_PI_Item.class);

			lutuConfigNew.setM_LU_HU_PI_Item(luPI_Item);
			lutuConfigNew.setM_LU_HU_PI(luPI_Item.getM_HU_PI_Version().getM_HU_PI());
			lutuConfigNew.setQtyLU(qtyLU);
			lutuConfigNew.setIsInfiniteQtyLU(false);
		}
		else
		{
			lutuConfigNew.setM_LU_HU_PI(null);
			lutuConfigNew.setM_LU_HU_PI_Item(null);
			lutuConfigNew.setQtyLU(BigDecimal.ZERO);
		}
	}

	public int getLuPiItemId()
	{
		return lu_PI_Item_ID;
	}

	public void setLuPiItemId(final int lu_PI_Item_ID)
	{
		this.lu_PI_Item_ID = lu_PI_Item_ID;
	}

	private HUPIItemProductId getTU_HU_PI_Item_Product_ID()
	{
		if (tu_HU_PI_Item_Product_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_M_HU_PI_Item_Product_ID);
		}
		return HUPIItemProductId.ofRepoId(tu_HU_PI_Item_Product_ID);
	}

	public void setTU_HU_PI_Item_Product_ID(final int tu_HU_PI_Item_Product_ID)
	{
		this.tu_HU_PI_Item_Product_ID = tu_HU_PI_Item_Product_ID;
	}

	public BigDecimal getQtyCUsPerTU()
	{
		return qtyCUsPerTU;
	}

	public void setQtyCUsPerTU(final BigDecimal qtyCU)
	{
		this.qtyCUsPerTU = qtyCU;
	}

	/**
	 * Called from the process class to set the TU qty from the process parameter.
	 */
	public void setQtyTU(final BigDecimal qtyTU)
	{
		this.qtyTU = qtyTU;
	}

	public BigDecimal getQtyLU()
	{
		return qtyLU;
	}

	public void setQtyLU(final BigDecimal qtyLU)
	{
		this.qtyLU = qtyLU;
	}

}
