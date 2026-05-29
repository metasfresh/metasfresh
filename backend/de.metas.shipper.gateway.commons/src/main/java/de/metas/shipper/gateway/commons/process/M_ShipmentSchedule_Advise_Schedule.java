/*
 * #%L
 * de.metas.shipper.gateway.commons
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

package de.metas.shipper.gateway.commons.process;

import de.metas.common.util.time.SystemTime;
import de.metas.inoutcandidate.ShipmentScheduleQuery;
import de.metas.process.JavaProcess;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class M_ShipmentSchedule_Advise_Schedule extends JavaProcess
{
	@NonNull private final CarrierAdviseProcessService helper = SpringContextHolder.instance.getBean(CarrierAdviseProcessService.class);

	@Override
	protected String doIt() throws Exception
	{
		helper.requestCarrierAdvises(ShipmentScheduleQuery.builder()
				.fromCompleteOrderOrNullOrder(true)
				.preparationDate(SystemTime.asLocalDate())
				.includeWithQtyToDeliverZero(false)
				.includeProcessed(false)
				.orderByOrderId(true)
				.build());
		return MSG_OK;
	}
}
