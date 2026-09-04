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
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import org.springframework.context.annotation.Profile;

/**
 * "Foto" on the receipt-logistics window: attaches an image to the selected row's receipt schedule, exactly as
 * {@code WEBUI_M_ReceiptSchedule_AttachPhoto} does on window 541954 - same parameter, same body (shared), and the
 * same single-selection rule. Offered on both row types, because the photo belongs to the receipt schedule and
 * both branches of the view have one.
 */
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_RV_ReceiptLogistics_AttachPhoto extends ReceiptLogisticsPassThroughProcess
{
	// package-visible rather than private so the same-package unit test can state the image production fills in
	// by @Param reflection; the parameter itself is the receipt-schedule window's, copied unchanged.
	@Param(parameterName = "AD_Image_ID", mandatory = true)
	int p_AD_Image_ID;

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
		actions.attachPhoto(getCtx(), getSelectedReceiptSchedule(), p_AD_Image_ID);

		return MSG_OK;
	}
}
