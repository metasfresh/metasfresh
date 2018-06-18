package de.metas.handlingunits.reservation;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;

import de.metas.document.engine.IDocument;
import de.metas.handlingunits.HuId;
import de.metas.order.OrderLineId;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Service
public class HuReservationService
{
	/**
	 * Creates an HU reservation record and creates dedicated reserved VHUs with HU status "reserved".
	 */
	// Shall we do something if the reservation is less that requested?
	public HuReservation makeReservation(HuReservationRequest reservationRequest)
	{
		throw new NotImplementedException("HuReservationService.makeReservation");
	}

	/**
	 * Deletes the respective reservation record. VHUs that were split off according to the reservation remain that way for the time being.
	 */
	public void deleteReservation(HuReservationId reservationId)
	{
		throw new NotImplementedException("HuReservationService.deleteReservation");
	}

	/**
	 * Returns a list that is based on the given {@code huIds} and contains the subset of HUs
	 * which are not ruled out because they are reserved for order lines.
	 */
	public List<HuId> retainAvailableHUsForOrderLine(List<HuId> huIds, OrderLineId orderLineId)
	{
		// TODO: avoid the n+1 problem when implementing this
		throw new NotImplementedException("HuReservationService.retainAvailableHUs");
	}

	public ImmutableSet<String> getDocstatusesThatAllowReservation()
	{
		return ImmutableSet.of(
				IDocument.STATUS_Drafted,
				IDocument.STATUS_InProgress,
				IDocument.STATUS_WaitingPayment,
				IDocument.STATUS_Completed);
	}
}
