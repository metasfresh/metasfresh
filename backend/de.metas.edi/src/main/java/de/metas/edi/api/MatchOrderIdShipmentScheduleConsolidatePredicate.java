/*
 * #%L
 * de.metas.swat.base
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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.service.ClientId;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchOrderIdShipmentScheduleConsolidatePredicate implements IShipmentScheduleAllowConsolidatePredicate
{
	private static final Logger logger = LogManager.getLogger(MatchOrderIdShipmentScheduleConsolidatePredicate.class);

	@NonNull
	private final IDesadvBL desadvBL;

	@Override
	public boolean isSchedAllowsConsolidate(@NonNull final I_M_ShipmentSchedule sched)
	{
		final boolean isMatchUsingOrderId = desadvBL.isMatchUsingOrderId(ClientId.ofRepoId(sched.getAD_Client_ID()));
		if (isMatchUsingOrderId)
		{
			logger.debug("According to the SysConfig de.metas.edi.desadv.MatchUsingC_Order_ID consolidation into one shipment is not allowed");
			return false;
		}

		return true;
	}
}
