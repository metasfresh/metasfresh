package de.metas.ui.web.handlingunits.process;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class WEBUI_M_ReceiptSchedule_GeneratePlanningHUs_UsingConfig extends WEBUI_M_ReceiptSchedule_GeneratePlanningHUs_Base implements IProcessDefaultParametersProvider
{
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		switch (parameter.getColumnName())
		{
			case PARAM_M_HU_PI_Item_Product_ID:
				return getDefaultLUTUConfiguration().getM_HU_PI_Item_Product_ID();
			case PARAM_M_LU_HU_PI_ID:
				return getDefaultLUTUConfiguration().getM_LU_HU_PI_ID();
			case PARAM_QtyCU:
				return getDefaultLUTUConfiguration().getQtyCU();
			case PARAM_QtyTU:
				return getDefaultLUTUConfiguration().getQtyTU();
			case PARAM_QtyLU:
				return getDefaultLUTUConfiguration().getQtyLU();
			default:
				return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	private I_M_HU_LUTU_Configuration _defaultLUTUConfiguration; // lazy

	private I_M_HU_LUTU_Configuration getDefaultLUTUConfiguration()
	{
		if (_defaultLUTUConfiguration == null)
		{
			final I_M_ReceiptSchedule receiptSchedule = getM_ReceiptSchedule();
			final I_M_HU_LUTU_Configuration defaultLUTUConfiguration = getCurrentLUTUConfiguration(receiptSchedule);
			adjustLUs(defaultLUTUConfiguration, receiptSchedule);
			_defaultLUTUConfiguration = defaultLUTUConfiguration;
		}
		return _defaultLUTUConfiguration;
	}

	private static void adjustLUs(final I_M_HU_LUTU_Configuration lutuConfiguration, final I_M_ReceiptSchedule fromReceiptSchedule)
	{
		final BigDecimal qtyToReceiveTU = Services.get(IReceiptScheduleBL.class).getQtyToMove(fromReceiptSchedule);

		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		final int qtyLU = lutuConfigurationFactory.calculateQtyLUForTotalQtyTUs(lutuConfiguration, qtyToReceiveTU);
		lutuConfiguration.setQtyLU(BigDecimal.valueOf(qtyLU));
	}

	private static final String PARAM_IsSaveLUTUConfiguration = "IsSaveLUTUConfiguration";
	@Param(parameterName = PARAM_IsSaveLUTUConfiguration)
	private boolean p_IsSaveLUTUConfiguration;

	//
	// Parameters
	private static final String PARAM_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID)
	private int p_M_HU_PI_Item_Product_ID;
	//
	private static final String PARAM_M_LU_HU_PI_ID = "M_LU_HU_PI_ID";
	@Param(parameterName = PARAM_M_LU_HU_PI_ID)
	private int p_M_LU_HU_PI_ID;
	//
	private static final String PARAM_QtyCU = "QtyCU";
	@Param(parameterName = PARAM_QtyCU)
	private BigDecimal p_QtyCU;
	//
	private static final String PARAM_QtyTU = "QtyTU";
	@Param(parameterName = PARAM_QtyTU)
	private BigDecimal p_QtyTU;
	//
	private static final String PARAM_QtyLU = "QtyLU";
	@Param(parameterName = PARAM_QtyLU)
	private BigDecimal p_QtyLU;

	@Override
	protected boolean isUpdateReceiptScheduleDefaultConfiguration()
	{
		return p_IsSaveLUTUConfiguration;
	}

	@Override
	protected I_M_HU_LUTU_Configuration createM_HU_LUTU_Configuration(final I_M_HU_LUTU_Configuration template)
	{
		// Validate parameters
		final int M_LU_HU_PI_ID = p_M_LU_HU_PI_ID;
		final int M_HU_PI_Item_Product_ID = p_M_HU_PI_Item_Product_ID;
		final BigDecimal qtyCU = p_QtyCU;
		final BigDecimal qtyTU = p_QtyTU;
		final BigDecimal qtyLU = p_QtyLU;
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

		final I_M_HU_LUTU_Configuration lutuConfigurationNew = InterfaceWrapperHelper.copy()
				.setFrom(template)
				.copyToNew(I_M_HU_LUTU_Configuration.class);
		//
		// CU
		lutuConfigurationNew.setQtyCU(qtyCU);

		//
		// TU
		final I_M_HU_PI_Item_Product tuPIItemProduct = InterfaceWrapperHelper.create(getCtx(), M_HU_PI_Item_Product_ID, I_M_HU_PI_Item_Product.class, ITrx.TRXNAME_None);
		final I_M_HU_PI tuPI = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
		lutuConfigurationNew.setM_HU_PI_Item_Product(tuPIItemProduct);
		lutuConfigurationNew.setM_TU_HU_PI(tuPI);
		lutuConfigurationNew.setQtyTU(qtyTU);

		//
		// LU
		if (M_LU_HU_PI_ID > 0)
		{
			final I_M_HU_PI luPI = InterfaceWrapperHelper.create(getCtx(), M_LU_HU_PI_ID, I_M_HU_PI.class, ITrx.TRXNAME_None);

			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			final I_M_HU_PI_Version luPIV = handlingUnitsDAO.retrievePICurrentVersion(luPI);
			final I_M_HU_PI_Item luPI_Item = handlingUnitsDAO.retrieveParentPIItemsForParentPI(tuPI, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, lutuConfigurationNew.getC_BPartner())
					.stream()
					.filter(piItem -> piItem.getM_HU_PI_Version_ID() == luPIV.getM_HU_PI_Version_ID())
					.findFirst()
					.orElseThrow(() -> new AdempiereException(tuPI.getName() + " cannot be loaded to " + luPI.getName()));

			lutuConfigurationNew.setM_LU_HU_PI(luPI);
			lutuConfigurationNew.setM_LU_HU_PI_Item(luPI_Item);
			lutuConfigurationNew.setQtyLU(qtyLU);
		}
		else
		{
			lutuConfigurationNew.setM_LU_HU_PI(null);
			lutuConfigurationNew.setM_LU_HU_PI_Item(null);
			lutuConfigurationNew.setQtyLU(null);
		}

		// InterfaceWrapperHelper.save(lutuConfigurationNew); // expected to not be saved (important)

		return lutuConfigurationNew;
	}

}
