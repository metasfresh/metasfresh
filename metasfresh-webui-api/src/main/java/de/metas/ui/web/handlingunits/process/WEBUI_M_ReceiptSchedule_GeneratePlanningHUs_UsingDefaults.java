package de.metas.ui.web.handlingunits.process;

import java.math.BigDecimal;

import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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
		// TODO: really implement it

		final I_M_ReceiptSchedule receiptSchedule = context.getSelectedModel(I_M_ReceiptSchedule.class);
		if (receiptSchedule == null)
		{
			return "...";
		}
		
		final I_M_HU_LUTU_Configuration lutuConfig = getCurrentLUTUConfiguration(receiptSchedule);
		
		StringBuilder packingInfo = new StringBuilder();
		packingInfo.append(lutuConfig.getM_HU_PI_Item_Product().getName());
		return packingInfo.toString();
	}

	@Override
	protected I_M_HU_LUTU_Configuration updateM_HU_LUTU_Configuration(final I_M_HU_LUTU_Configuration lutuConfigurationToEdit)
	{
		// preserve the default suggested one
		return lutuConfigurationToEdit;
	}

}
