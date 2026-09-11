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
 * Whether a product with no {@code M_HU_PI_Item_Product} hides this action turns on the HU packing DATA, not on
 * the code alone, so neither "it hides" nor "it stays visible" is true unconditionally.
 * <p>
 * {@link HUPackingInfoFormatter} appends the TU name only for a non-virtual TU, appends the CU quantity only when
 * {@code isInfiniteQtyCUsPerTU()} is false AND the quantity is positive, and returns {@code null} when nothing was
 * appended. Such a product falls back to the virtual "No Packing Item", which carries INFINITE capacity, and only
 * {@link ReceiptScheduleLUTUConfigurations#adjustToDefaults} can turn that finite - which it does in its
 * non-{@code isNoLU} branch only. So:
 * <ul>
 * <li>the virtual TU HAS an LU parent association ({@code M_HU_PI_Item}): the CU becomes finite, the caption reads
 * e.g. {@code "5 Stk"}, and the action stays visible captioned by the quantity alone;</li>
 * <li>it has NONE: the configuration stays infinite-CU, the formatter skips the TU (virtual) and the CU (infinite)
 * alike, the caption is empty, and this action IS rejected - the empty branch fires without the quantity being
 * zero.</li>
 * </ul>
 * The association is client data ({@code AD_Client_ID} 1000000 on the reference stacks), not core, so the first
 * case is the current dataset's behaviour rather than a guarantee of this code.
 * <p>
 * AC7b asks for the "CUs annehmen" fallback, and on data carrying the association it does not appear - which is
 * what the Playwright spec asserting AC7b reports, failing on purpose rather than being relaxed to match. Which
 * side gives is open; see {@code ai-work/31789/pending-questions.md}, the AC7b gate entry.
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
