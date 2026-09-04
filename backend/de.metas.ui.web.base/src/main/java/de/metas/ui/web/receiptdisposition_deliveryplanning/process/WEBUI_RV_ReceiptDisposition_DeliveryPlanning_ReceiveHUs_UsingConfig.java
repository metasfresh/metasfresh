/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.ui.web.receiptdisposition_deliveryplanning.process;

import de.metas.Profiles;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;
import de.metas.ui.web.handlingunits.process.ReceiptScheduleLUTUConfigurations;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.FillMandatoryException;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

/**
 * "HUs annehmen" on the receipt-logistics window: the operator states the packing - which TU, how many CUs per
 * TU, how many TUs, and optionally which LU and how many.
 * <p>
 * Parameters, defaults and validation are those of {@code WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingConfig}, so
 * the same {@code AD_Process_Para} set applies.
 */
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveHUs_UsingConfig extends ReceiptDispositionDeliveryPlanningReceiveHUsProcess
		implements IProcessDefaultParametersProvider
{
	private static final String PARAM_IsSaveLUTUConfiguration = "IsSaveLUTUConfiguration";
	@Param(parameterName = PARAM_IsSaveLUTUConfiguration)
	private boolean p_IsSaveLUTUConfiguration;

	private static final String PARAM_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID)
	private int p_M_HU_PI_Item_Product_ID;

	private static final String PARAM_M_LU_HU_PI_ID = "M_LU_HU_PI_ID";
	@Param(parameterName = PARAM_M_LU_HU_PI_ID)
	private int p_M_LU_HU_PI_ID;

	private static final String PARAM_QtyCUsPerTU = "QtyCUsPerTU";
	@Param(parameterName = PARAM_QtyCUsPerTU)
	private BigDecimal p_QtyCUsPerTU;

	private static final String PARAM_QtyTU = "QtyTU";
	@Param(parameterName = PARAM_QtyTU)
	private BigDecimal p_QtyTU;

	private static final String PARAM_QtyLU = "QtyLU";
	@Param(parameterName = PARAM_QtyLU)
	private BigDecimal p_QtyLU;

	@javax.annotation.Nullable
	private I_M_HU_LUTU_Configuration _defaultLUTUConfiguration; // lazy

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

	private I_M_HU_LUTU_Configuration getDefaultLUTUConfiguration()
	{
		if (_defaultLUTUConfiguration == null)
		{
			final I_M_ReceiptSchedule receiptSchedule = getSelectedReceiptSchedule();
			final I_M_HU_LUTU_Configuration defaultLUTUConfiguration = ReceiptScheduleLUTUConfigurations.getCurrent(receiptSchedule);
			huReceiptScheduleBL.adjustLUTUConfiguration(defaultLUTUConfiguration, receiptSchedule);
			_defaultLUTUConfiguration = defaultLUTUConfiguration;
		}
		return _defaultLUTUConfiguration;
	}

	/** {@code true} - the operator types QtyLU / QtyTU / QtyCUsPerTU, so the packing is booked as stated. */
	@Override
	protected boolean isQtyToReceiveOperatorStated()
	{
		return true;
	}

	@Override
	protected boolean isUpdateReceiptScheduleDefaultConfiguration()
	{
		return p_IsSaveLUTUConfiguration;
	}

	@Override
	protected I_M_HU_LUTU_Configuration createLUTUConfiguration(
			@NonNull final I_M_HU_LUTU_Configuration template,
			@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		if (p_M_HU_PI_Item_Product_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_M_HU_PI_Item_Product_ID);
		}
		if (p_QtyCUsPerTU == null || p_QtyCUsPerTU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCUsPerTU);
		}
		if (p_QtyTU == null || p_QtyTU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyTU);
		}
		if (p_M_LU_HU_PI_ID > 0 && (p_QtyLU == null || p_QtyLU.signum() <= 0))
		{
			throw new FillMandatoryException(PARAM_QtyLU);
		}

		return Services.get(ILUTUConfigurationFactory.class).createNewLUTUConfigWithParams(
				ILUTUConfigurationFactory.CreateLUTUConfigRequest.builder()
						.baseLUTUConfiguration(template)
						.qtyLU(p_QtyLU)
						.qtyTU(p_QtyTU)
						.qtyCUsPerTU(p_QtyCUsPerTU)
						.tuHUPIItemProductID(p_M_HU_PI_Item_Product_ID)
						.luHUPIID(p_M_LU_HU_PI_ID)
						.build());
	}
}
