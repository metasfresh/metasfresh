package de.metas.ui.web.handlingunits.process;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.util.HUPackingInfoFormatter;
import de.metas.ui.web.handlingunits.util.HUPackingInfos;

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
				.deriveWithCaptionOverride(buildDefaultPackingInfo(context));
	}

	private String buildDefaultPackingInfo(final IProcessPreconditionsContext context)
	{
		final I_M_ReceiptSchedule receiptSchedule = context.getSelectedModel(I_M_ReceiptSchedule.class);
		if (receiptSchedule == null)
		{
			return null; // no override
		}

		final I_M_HU_LUTU_Configuration lutuConfig = getCurrentLUTUConfiguration(receiptSchedule);
		adjustLUTUConfiguration(lutuConfig, receiptSchedule);

		return HUPackingInfoFormatter.newInstance()
				.setShowLU(false) // NOTE: don't show LU info because makes the whole label to long. see https://github.com/metasfresh/metasfresh-webui-frontend/issues/315#issuecomment-280624562
				.format(HUPackingInfos.of(lutuConfig));
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

		adjustLUTUConfiguration(lutuConfigurationNew, getM_ReceiptSchedule());
		lutuConfigurationNew.setQtyLU(BigDecimal.ONE);

		// NOTE: don't save it
		return lutuConfigurationNew;
	}

	private static final void adjustLUTUConfiguration(final I_M_HU_LUTU_Configuration lutuConfig, final I_M_ReceiptSchedule receiptSchedule)
	{
		//
		// Adjust LU
		lutuConfig.setQtyLU(BigDecimal.ONE);

		//
		// Adjust TU
		// * if the standard QtyTU is less than how much is available to be received => enforce the available Qty
		// * else always take the standard QtyTU
		// see https://github.com/metasfresh/metasfresh-webui/issues/228
		{
			final BigDecimal qtyToMoveTU = Services.get(IHUReceiptScheduleBL.class).getQtyToMoveTU(receiptSchedule);

			if (qtyToMoveTU.signum() > 0 && qtyToMoveTU.compareTo(lutuConfig.getQtyTU()) < 0)
			{
				lutuConfig.setQtyTU(qtyToMoveTU);
			}
		}
	}

}
