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

package de.metas.ui.web.handlingunits.process;

import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * Whether a receipt schedule may be received at all - the ONE definition, so that a second window offering the
 * same actions cannot grow a second, drifting one.
 * <p>
 * Extracted unchanged from {@code WEBUI_M_ReceiptSchedule_ReceiveHUs_Base}, whose package-private class made the
 * rule unreachable from the receipt-disposition delivery-planning window's actions even though the method itself was public.
 */
@UtilityClass
public class ReceiptScheduleReceiveEligibility
{
	public static ProcessPreconditionsResolution check(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		// Receipt schedule shall not be already closed
		if (Services.get(IReceiptScheduleBL.class).isClosed(receiptSchedule))
		{
			return ProcessPreconditionsResolution.reject("receipt schedule closed");
		}

		// Receipt schedule shall not be about packing materials
		if (receiptSchedule.isPackagingMaterial())
		{
			return ProcessPreconditionsResolution.reject("not applying for packing materials");
		}

		return ProcessPreconditionsResolution.accept();
	}
}
