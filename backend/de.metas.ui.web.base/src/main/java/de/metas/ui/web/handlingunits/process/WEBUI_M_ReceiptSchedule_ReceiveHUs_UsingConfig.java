package de.metas.ui.web.handlingunits.process;

import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;
import de.metas.util.Services;
import org.adempiere.exceptions.FillMandatoryException;

import java.math.BigDecimal;

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

/**
 * Receive planning HUs using given configuration (parameters).
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingConfig extends WEBUI_M_ReceiptSchedule_ReceiveHUs_Base implements IProcessDefaultParametersProvider
{
	private final transient IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final transient ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		switch (parameter.getColumnName())
		{
			case PARAM_M_HU_PI_Item_Product_ID:
				return getDefaultLUTUConfiguration().getM_HU_PI_Item_Product_ID();
			case PARAM_M_LU_HU_PI_ID:
				return getDefaultLUTUConfiguration().getM_LU_HU_PI_ID();
			case PARAM_QtyCUsPerTU:
				return getDefaultLUTUConfiguration().getQtyCUsPerTU();
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
			huReceiptScheduleBL.adjustLUTUConfiguration(defaultLUTUConfiguration, receiptSchedule);
			_defaultLUTUConfiguration = defaultLUTUConfiguration;
		}
		return _defaultLUTUConfiguration;
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
	private static final String PARAM_QtyCUsPerTU = "QtyCUsPerTU";
	@Param(parameterName = PARAM_QtyCUsPerTU)
	private BigDecimal p_QtyCUsPerTU;
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
		final BigDecimal qtyCUsPerTU = p_QtyCUsPerTU;
		final BigDecimal qtyTU = p_QtyTU;
		final BigDecimal qtyLU = p_QtyLU;
		if (M_HU_PI_Item_Product_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_M_HU_PI_Item_Product_ID);
		}
		if (qtyCUsPerTU == null || qtyCUsPerTU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCUsPerTU);
		}
		if (qtyTU == null || qtyTU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyTU);
		}
		if (M_LU_HU_PI_ID > 0 && qtyLU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyLU);
		}

		final ILUTUConfigurationFactory.CreateLUTUConfigRequest lutuConfigRequest = ILUTUConfigurationFactory.CreateLUTUConfigRequest.builder()
			.baseLUTUConfiguration(template)
			.qtyLU(qtyLU)
			.qtyTU(qtyTU)
			.qtyCUsPerTU(qtyCUsPerTU)
			.tuHUPIItemProductID(M_HU_PI_Item_Product_ID)
			.luHUPIID(M_LU_HU_PI_ID)
			.build();

		return lutuConfigurationFactory.createNewLUTUConfigWithParams(lutuConfigRequest);
	}

}
