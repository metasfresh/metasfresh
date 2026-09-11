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
 * "HUs annehmen Voreinst." on the receipt-disposition delivery-planning window - the one-click primary path and
 * the window's default quick action.
 * <p>
 * Rejects on the same two conditions as {@code WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingDefaults}: a quantity to
 * receive of zero, and an empty default packing info.
 * <p>
 * Note what that does NOT amount to, because the obvious reading is wrong. A row whose product has no
 * {@code M_HU_PI_Item_Product} does NOT hide this action. {@link HUPackingInfoFormatter} appends the TU name only
 * for a non-virtual TU, but appends the CU quantity whenever it is positive, and returns {@code null} only when
 * the whole string came out empty - so such a row still yields a caption like {@code "5 Stk"}, which is not
 * empty, and the action stays visible captioned by the quantity alone. The empty branch is reachable only when
 * the quantity is also zero, which the {@code getQtyToMoveTU} guard above has already rejected. "CUs annehmen"
 * is therefore NOT the one-click fallback for an unpacked product.
 * <p>
 * AC7b asks for exactly that fallback, so the acceptance criterion and the shipped behaviour disagree, and the
 * Playwright spec asserting AC7b fails on purpose rather than being relaxed to match. Which side gives is open -
 * see {@code ai-work/31789/pending-questions.md}, "QUEUED GATE: AC7b's fallback branch is effectively dead in the
 * SHARED implementation". This paragraph describes the code as it stands and is accurate either way.
 */
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveHUs_UsingDefaults extends ReceiptDispositionDeliveryPlanningReceiveHUsProcess
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
		// adjustToDefaults sizes this from the SCHEDULE, so on a planned row the caption would advertise the
		// whole order line (e.g. "100" on a row planned for 50) while the allocation books only the share.
		capToPlannedShare(lutuConfig, receiptSchedule, getSelectedDeliveryPlanningIdOrNull());

		return HUPackingInfoFormatter.newInstance()
				.setShowLU(false) // NOTE: don't show LU info because it makes the whole label too long
				.format(HUPackingInfos.of(lutuConfig));
	}

	/** {@code false} - the one-click defaults are DERIVED from the schedule, so a planned row's share caps them. */
	@Override
	protected boolean isQtyToReceiveOperatorStated()
	{
		return false;
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
