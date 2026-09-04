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

package de.metas.ui.web.receiptlogistics.process;

import de.metas.Profiles;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.process.ReceiptScheduleLUTUConfigurations;
import de.metas.ui.web.handlingunits.util.HUPackingInfoFormatter;
import de.metas.ui.web.handlingunits.util.HUPackingInfos;
import de.metas.util.Check;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;

import javax.annotation.Nullable;

/**
 * "HUs annehmen Voreinst." on the receipt-logistics window - the one-click primary path, and the window's
 * default quick action.
 * <p>
 * Rejects on the same two conditions as {@code WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingDefaults} and shows the
 * same packing caption on the button, so a row where no packing instruction resolves hides this action and
 * "CUs annehmen" stays as the one-click fallback (AC7b).
 */
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_RV_ReceiptLogistics_ReceiveHUs_UsingDefaults extends ReceiptLogisticsReceiveHUsProcess
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ProcessPreconditionsResolution shared = super.checkPreconditionsApplicable();
		if (!shared.isAccepted())
		{
			return shared;
		}

		final I_M_ReceiptSchedule receiptSchedule = getSelectedReceiptSchedule();
		if (huReceiptScheduleBL.getQtyToMoveTU(receiptSchedule).signum() <= 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("quantity to receive is <= 0");
		}

		final String defaultPackingInfo = buildDefaultPackingInfo(receiptSchedule);
		if (Check.isEmpty(defaultPackingInfo, true))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no default LU/TU configuration");
		}

		return shared.deriveWithCaptionOverride(defaultPackingInfo);
	}

	@Nullable
	private String buildDefaultPackingInfo(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_M_HU_LUTU_Configuration lutuConfig = ReceiptScheduleLUTUConfigurations.getCurrent(receiptSchedule);
		ReceiptScheduleLUTUConfigurations.adjustToDefaults(lutuConfig, receiptSchedule);

		return HUPackingInfoFormatter.newInstance()
				.setShowLU(false) // NOTE: don't show LU info because it makes the whole label too long
				.format(HUPackingInfos.of(lutuConfig));
	}

	@Override
	protected boolean isUpdateReceiptScheduleDefaultConfiguration()
	{
		return false;
	}

	@Override
	protected I_M_HU_LUTU_Configuration createLUTUConfiguration(
			@NonNull final I_M_HU_LUTU_Configuration template,
			@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		return ReceiptScheduleLUTUConfigurations.newDefaultCopy(template, receiptSchedule);
	}
}
