package de.metas.ui.web.handlingunits.process;




import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;

/*
 * #%L
 * metasfresh-webui-api
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

public class WEBUI_M_ReceiptSchedule_AttachPhoto extends ReceiptScheduleBasedProcess
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		// Allow only single selection
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Param(parameterName = "AD_Image_ID", mandatory = true)
	private int p_AD_Image_ID;

	// package-visible, non-final so a same-package unit test can substitute it; the action's body itself is
	// shared with the receipt-logistics window's adapter, which must attach the very same photo.
	ReceiptScheduleActions actions = ReceiptScheduleActions.newInstance();

	@Override
	protected String doIt()
	{
		actions.attachPhoto(getCtx(), getRecord(I_M_ReceiptSchedule.class), p_AD_Image_ID);

		return MSG_OK;
	}
}
