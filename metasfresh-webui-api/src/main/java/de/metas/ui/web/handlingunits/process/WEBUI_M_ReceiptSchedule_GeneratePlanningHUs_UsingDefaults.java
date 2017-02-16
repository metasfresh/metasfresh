package de.metas.ui.web.handlingunits.process;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.util.DisplayType;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;

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

public class WEBUI_M_ReceiptSchedule_GeneratePlanningHUs_UsingDefaults extends WEBUI_M_ReceiptSchedule_GeneratePlanningHUs_Base
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		return super.checkPreconditionsApplicable(context)
				.deriveWithCaptionOverride("Receive to " + buildDefaultPackingInfo(context));
	}

	private String buildDefaultPackingInfo(final IProcessPreconditionsContext context)
	{
		final I_M_ReceiptSchedule receiptSchedule = context.getSelectedModel(I_M_ReceiptSchedule.class);
		if (receiptSchedule == null)
		{
			return "...";
		}

		final I_M_HU_LUTU_Configuration lutuConfig = getCurrentLUTUConfiguration(receiptSchedule);

		final StringBuilder packingInfo = new StringBuilder();

		//
		// LU
		final I_M_HU_PI luPI = lutuConfig.getM_LU_HU_PI();
		if (luPI != null)
		{
			packingInfo.append(luPI.getName());
		}

		//
		// TU
		final I_M_HU_PI tuPI = lutuConfig.getM_TU_HU_PI();
		if (tuPI != null && !Services.get(IHandlingUnitsBL.class).isVirtual(tuPI))
		{
			if (packingInfo.length() > 0)
			{
				packingInfo.append(" x ");
			}

			final BigDecimal qtyTU = lutuConfig.getQtyTU();
			if (!lutuConfig.isInfiniteQtyTU() && qtyTU != null && qtyTU.signum() > 0)
			{
				packingInfo.append(qtyTU.intValue()).append(" ");
			}

			packingInfo.append(tuPI.getName());
		}

		//
		// CU
		final BigDecimal qtyCU = lutuConfig.getQtyCU();
		if (!lutuConfig.isInfiniteQtyCU() && qtyCU != null && qtyCU.signum() > 0)
		{
			if (packingInfo.length() > 0)
			{
				packingInfo.append(" x ");
			}

			final DecimalFormat qtyFormat = DisplayType.getNumberFormat(DisplayType.Quantity);
			packingInfo.append(qtyFormat.format(qtyCU));

			final I_C_UOM uom = lutuConfig.getC_UOM();
			final String uomSymbol = uom == null ? null : uom.getUOMSymbol();
			if (uomSymbol != null)
			{
				packingInfo.append(" ").append(uomSymbol);
			}
		}

		return packingInfo.toString();
	}

	@Override
	protected boolean isUpdateReceiptScheduleDefaultConfiguration()
	{
		return false;
	}

	@Override
	protected I_M_HU_LUTU_Configuration createM_HU_LUTU_Configuration(final I_M_HU_LUTU_Configuration template)
	{
		final I_M_HU_LUTU_Configuration lutuConfigurationNew = InterfaceWrapperHelper.copy()
				.setFrom(template)
				.copyToNew(I_M_HU_LUTU_Configuration.class);

		lutuConfigurationNew.setQtyLU(BigDecimal.ONE);

		return lutuConfigurationNew;
	}

}
