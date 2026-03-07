/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.qtyreservation;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.inoutcandidate.qty_reservation.QtyReservationId;
import org.compiere.model.I_M_QtyReservation;

public class M_QtyReservation_StepDefData extends StepDefData<I_M_QtyReservation>
		implements StepDefDataGetIdAware<QtyReservationId, I_M_QtyReservation>
{
	public M_QtyReservation_StepDefData()
	{
		super(I_M_QtyReservation.class);
	}

	@Override
	public QtyReservationId extractIdFromRecord(final I_M_QtyReservation record)
	{
		return QtyReservationId.ofRepoId(record.getM_QtyReservation_ID());
	}
}
