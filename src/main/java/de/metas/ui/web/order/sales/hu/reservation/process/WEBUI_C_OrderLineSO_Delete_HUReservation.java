package de.metas.ui.web.order.sales.hu.reservation.process;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.reservation.HuReservationService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_C_OrderLineSO_Delete_HUReservation
		extends HUEditorProcessTemplate
		implements IProcessPrecondition
{
	@Autowired
	private HuReservationService huReservationService;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final boolean anyReservedHuSelected = streamSelectedHUs(Select.ALL)
				.anyMatch(I_M_HU::isReserved);

		return ProcessPreconditionsResolution.acceptIf(anyReservedHuSelected);
	}

	@Override
	@RunOutOfTrx // the service we invoke creates its own transaction
	protected String doIt()
	{
		final ImmutableList<HuId> selectedReservedHUs = streamSelectedHUIds(HUEditorRowFilter.ALL)
				.collect(ImmutableList.toImmutableList());

		huReservationService.deleteReservations(selectedReservedHUs);

		return MSG_OK;
	}

}
