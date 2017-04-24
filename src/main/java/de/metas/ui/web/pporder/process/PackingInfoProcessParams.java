package de.metas.ui.web.pporder.process;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;
import lombok.Builder;
import lombok.NonNull;

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
	public static final String PARAM_M_LU_HU_PI_ID = "M_LU_HU_PI_ID";
	@Param(parameterName = PARAM_M_LU_HU_PI_ID)
	private int lu_HU_PI_ID;
	//
	public static final String PARAM_QtyCU = "QtyCU";
	@Param(parameterName = PARAM_QtyCU)
	private BigDecimal qtyCU;
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

	@Builder
	public PackingInfoProcessParams( //
			@NonNull final IDocumentLUTUConfigurationManager defaultLUTUConfigManager //
			, final BigDecimal enforceAvailableQtyTU //
			, final boolean enforceOneLUorTU //
	)
	{
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
			case PARAM_M_LU_HU_PI_ID:
				return defaultLUTUConfig.getM_LU_HU_PI_ID();
			case PARAM_QtyCU:
				return defaultLUTUConfig.getQtyCU();
			case PARAM_QtyTU:
				return defaultLUTUConfig.getQtyTU();
			case PARAM_QtyLU:
				return defaultLUTUConfig.getQtyLU();
			default:
				return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
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

	private void adjustDefaultLUTUConfig_EnforceOneLUorTU(@NonNull final I_M_HU_LUTU_Configuration defaultLUTUConfig, @Nullable final BigDecimal availableQtyTU)
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

	private void adjustDefaultLUTUConfig_EnforceAvailableTUs(@NonNull final I_M_HU_LUTU_Configuration defaultLUTUConfig, @NonNull final BigDecimal availableQtyTU)
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

	public I_M_HU_LUTU_Configuration createNewLUTUConfig()
	{
		final I_M_HU_LUTU_Configuration defaultLUTUConfig = getDefaultLUTUConfig();

		// Validate parameters
		final int M_LU_HU_PI_ID = getLU_HU_PI_ID();
		final int M_HU_PI_Item_Product_ID = getTU_HU_PI_Item_Product_ID();
		final BigDecimal qtyCU = getQtyCU();
		final BigDecimal qtyTU = getQtyTU();
		final BigDecimal qtyLU = getQtyLU();
		if (M_LU_HU_PI_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_M_LU_HU_PI_ID);
		}
		if (M_HU_PI_Item_Product_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_M_HU_PI_Item_Product_ID);
		}
		if (qtyCU == null || qtyCU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCU);
		}
		if (qtyTU == null || qtyTU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyTU);
		}
		if (qtyLU == null || qtyLU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyLU);
		}

		final I_M_HU_LUTU_Configuration lutuConfigNew = InterfaceWrapperHelper.copy()
				.setFrom(defaultLUTUConfig)
				.copyToNew(I_M_HU_LUTU_Configuration.class);
		//
		// CU
		lutuConfigNew.setQtyCU(qtyCU);
		lutuConfigNew.setIsInfiniteQtyCU(false);

		//
		// TU
		final I_M_HU_PI_Item_Product tuPIItemProduct = InterfaceWrapperHelper.create(Env.getCtx(), M_HU_PI_Item_Product_ID, I_M_HU_PI_Item_Product.class, ITrx.TRXNAME_None);
		final I_M_HU_PI tuPI = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
		lutuConfigNew.setM_HU_PI_Item_Product(tuPIItemProduct);
		lutuConfigNew.setM_TU_HU_PI(tuPI);
		lutuConfigNew.setQtyTU(qtyTU);
		lutuConfigNew.setIsInfiniteQtyTU(false);

		//
		// LU
		if (M_LU_HU_PI_ID > 0)
		{
			final I_M_HU_PI luPI = InterfaceWrapperHelper.create(Env.getCtx(), M_LU_HU_PI_ID, I_M_HU_PI.class, ITrx.TRXNAME_None);

			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			final I_M_HU_PI_Version luPIV = handlingUnitsDAO.retrievePICurrentVersion(luPI);
			final List<I_M_HU_PI_Item> luPI_ItemsAvailable = handlingUnitsDAO.retrieveParentPIItemsForParentPI(tuPI, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, lutuConfigNew.getC_BPartner());
			final I_M_HU_PI_Item luPI_Item = luPI_ItemsAvailable
					.stream()
					.filter(piItem -> piItem.getM_HU_PI_Version_ID() == luPIV.getM_HU_PI_Version_ID())
					.findFirst()
					.orElseThrow(() -> {
						final String luPI_ItemsAvailableStr = luPI_ItemsAvailable.stream()
								.map(item -> item.getM_HU_PI_Version().getM_HU_PI().getName())
								.distinct()
								.collect(Collectors.joining(", "));
						return new AdempiereException(tuPI.getName() + " cannot be loaded to " + luPI.getName()
								+ "\n Available LU PI items: " + luPI_ItemsAvailableStr);
					});;

			lutuConfigNew.setM_LU_HU_PI(luPI);
			lutuConfigNew.setM_LU_HU_PI_Item(luPI_Item);
			lutuConfigNew.setQtyLU(qtyLU);
			lutuConfigNew.setIsInfiniteQtyLU(false);
		}
		else
		{
			lutuConfigNew.setM_LU_HU_PI(null);
			lutuConfigNew.setM_LU_HU_PI_Item(null);
			lutuConfigNew.setQtyLU(null);
		}

		lutuConfigurationFactory.save(lutuConfigNew);
		return lutuConfigNew;
	}

	public int getLU_HU_PI_ID()
	{
		return lu_HU_PI_ID;
	}

	public void setLU_HU_PI_ID(final int lu_HU_PI_ID)
	{
		this.lu_HU_PI_ID = lu_HU_PI_ID;
	}

	public int getTU_HU_PI_Item_Product_ID()
	{
		return tu_HU_PI_Item_Product_ID;
	}

	public void setTU_HU_PI_Item_Product_ID(final int tu_HU_PI_Item_Product_ID)
	{
		this.tu_HU_PI_Item_Product_ID = tu_HU_PI_Item_Product_ID;
	}

	public BigDecimal getQtyCU()
	{
		return qtyCU;
	}

	public void setQtyCU(final BigDecimal qtyCU)
	{
		this.qtyCU = qtyCU;
	}

	public BigDecimal getQtyTU()
	{
		return qtyTU;
	}

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
