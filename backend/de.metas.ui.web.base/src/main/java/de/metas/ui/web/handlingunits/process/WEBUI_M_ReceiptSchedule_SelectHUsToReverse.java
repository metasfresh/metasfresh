package de.metas.ui.web.handlingunits.process;

import java.util.List;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.context.annotation.Profile;

import de.metas.Profiles;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.process.IProcessPreconditionsContext;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Profile(Profiles.PROFILE_Webui)
public class WEBUI_M_ReceiptSchedule_SelectHUsToReverse extends ReceiptScheduleBasedProcess
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return actions.checkHUsToReverseApplicable(context.getSelectedModel(I_M_ReceiptSchedule.class));
	}

	// package-visible, non-final so a same-package unit test can substitute it; shared with the
	// receipt-disposition delivery-planning window's adapter, which must offer the very same HUs.
	ReceiptScheduleActions actions = ReceiptScheduleActions.newInstance();

	@Override
	protected String doIt()
	{
		final List<I_M_HU> hus = actions.getHUsToReverse(getRecord(I_M_ReceiptSchedule.class));

		getResult().setRecordsToOpen(TableRecordReference.ofCollection(hus));

		return MSG_OK;
	}
}
