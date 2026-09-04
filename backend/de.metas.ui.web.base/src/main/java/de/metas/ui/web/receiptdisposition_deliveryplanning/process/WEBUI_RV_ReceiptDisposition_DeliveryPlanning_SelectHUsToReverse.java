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
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.ProcessPreconditionsResolution;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * "Korrektur" on the receipt-logistics window: offers the selected row's already-received HUs for reversal,
 * exactly as {@code WEBUI_M_ReceiptSchedule_SelectHUsToReverse} does on window 541954 - the same three
 * refusals (closed schedule, packing material, nothing received) asked of the ONE shared definition.
 * <p>
 * Note what it does NOT ask: the receive actions' "no selected planning may already be processed" guard. A
 * planned row whose receipt exists is exactly the row this action is for.
 */
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_RV_ReceiptDisposition_DeliveryPlanning_SelectHUsToReverse extends ReceiptDispositionDeliveryPlanningPassThroughProcess
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isSingleSelectedRow())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return actions.checkHUsToReverseApplicable(getSelectedReceiptSchedule());
	}

	@Override
	protected String doIt()
	{
		final List<I_M_HU> hus = actions.getHUsToReverse(getSelectedReceiptSchedule());

		getResult().setRecordsToOpen(TableRecordReference.ofCollection(hus));

		return MSG_OK;
	}
}
