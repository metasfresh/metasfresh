/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.edi.api;

import de.metas.inoutcandidate.api.IShipmentScheduleAllowConsolidatePredicate;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderQuery;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchOrderIdShipmentScheduleConsolidatePredicate implements IShipmentScheduleAllowConsolidatePredicate
{
	private static final Logger logger = LogManager.getLogger(MatchOrderIdShipmentScheduleConsolidatePredicate.class);

	@NonNull
	private final IDesadvBL desadvBL;

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	/**
	 * Don't enforce different shipments if the given [@code sched}'s {@code C_Order} has no DESADV.
	 * Also see {@link de.metas.edi.model.validator.C_Order#addToDesadv(de.metas.edi.model.I_C_Order)}.
	 */
	@Override
	public boolean isSchedAllowsConsolidate(@NonNull final I_M_ShipmentSchedule sched)
	{
		final int orderID = sched.getC_Order_ID();
		if (orderID <= 0)
		{
			return true; // if there is no order, there won't be a DESADV.
		}

		final I_C_Order orderRecord = orderDAO
				.retrieveByOrderCriteria(OrderQuery.builder()
												 .orderId(orderID)
												 .orgId(OrgId.ofRepoId(sched.getAD_Org_ID()))
												 .build())
				.orElseThrow(() -> new AdempiereException("Unable to retrieve C_Order for C_Order_ID=" + orderID));

		final int desadvID = InterfaceWrapperHelper.create(orderRecord, de.metas.edi.model.I_C_Order.class).getEDI_Desadv_ID();
		if (desadvID <= 0)
		{
			return true; // if the order doesn't have a desadv, we shouldn't bother
		}

		final boolean isMatchUsingOrderId = desadvBL.isMatchUsingOrderId(ClientId.ofRepoId(sched.getAD_Client_ID()));
		if (isMatchUsingOrderId)
		{
			logger.debug("According to the SysConfig de.metas.edi.desadv.MatchUsingC_Order_ID consolidation into one shipment is not allowed");
			return false;
		}

		return true;
	}
}
