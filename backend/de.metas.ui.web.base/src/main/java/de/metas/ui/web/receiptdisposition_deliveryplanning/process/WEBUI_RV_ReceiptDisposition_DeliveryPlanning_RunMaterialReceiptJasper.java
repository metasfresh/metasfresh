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
import de.metas.process.ProcessPreconditionsResolution;
import org.springframework.context.annotation.Profile;

/**
 * "Drucken Produktanlieferung" on the receipt-logistics window: runs the material-receipt Jasper for the selected
 * row's receipt schedule, exactly as {@code WEBUI_M_ReceiptSchedule_RunMaterialReceiptJasper} does on window
 * 541954. Offered on both row types - the report is about the schedule, which both branches have.
 */
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_RV_ReceiptDisposition_DeliveryPlanning_RunMaterialReceiptJasper extends ReceiptDispositionDeliveryPlanningPassThroughProcess
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (!isSingleSelectedRow())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		actions.runMaterialReceiptJasper(getSelectedReceiptSchedule());

		return MSG_OK;
	}
}
