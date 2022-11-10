package de.metas.inoutcandidate.api;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.common.util.CoalesceUtil;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Returns the "effective" values for a given receipt schedules when it has both an "original" and an "override" column.
 */

@Repository
public class ReceiptScheduleEffectiveRepository
{

	/**
	 * Get the effective movement date based on MovementDate and MovementDate_Override
	 */
	public ZonedDateTime getMovementDate(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final ZoneId timeZone = (Services.get(IOrgDAO.class))
				.getTimeZone(OrgId.ofRepoIdOrAny(receiptSchedule.getAD_Org_ID()));

		return TimeUtil.asZonedDateTime(
				CoalesceUtil.coalesceSuppliers(
						receiptSchedule::getMovementDate_Override,
						receiptSchedule::getMovementDate), timeZone);
	}

}
