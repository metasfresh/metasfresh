/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.inoutcandidate.process;

import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.order.OrderLineId;
import de.metas.shipping.process.AddOrderLinesToShipperTransportation;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryFilter;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class M_ReceiptSchedule_AddTo_M_ShipperTransportation extends AddOrderLinesToShipperTransportation
{
	private static final AdMessageKey MSG_RECEIPT_SCHEDULE_ASSIGNED_TO_PROCESSED_TRANSPORTATION_ORDER = AdMessageKey.of("ReceiptScheduleAssignedToProcessedTransportationOrder");
	private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);

	@Override
	protected Set<OrderLineId> getOrderLineIds()
	{
		final IQueryFilter<I_M_ReceiptSchedule> queryFilterOrElseFalse = getProcessInfo().getQueryFilterOrElseFalse();
		return receiptScheduleDAO.createQueryForReceiptScheduleSelection(getCtx(), queryFilterOrElseFalse)
				.create()
				.stream()
				.map(rs -> OrderLineId.ofRepoIdOrNull(rs.getC_OrderLine_ID()))
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}

	@Override
	protected AdMessageKey getFailureMessage()
	{
		return MSG_RECEIPT_SCHEDULE_ASSIGNED_TO_PROCESSED_TRANSPORTATION_ORDER;
	}

}
