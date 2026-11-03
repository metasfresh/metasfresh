package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Guard that aborts receipt generation loudly if any of the selected receipt schedules
 * belong to a vendor with an active delivery stop.
 *
 * <p>Atomicity: if ANY schedule is blocked, the whole batch is rejected with a clear
 * error message listing the blocked vendor names.
 *
 * <p>gh#28631
 */
@Service
public class ReceiptScheduleDeliveryStopGuard
{
	private static final AdMessageKey MSG = AdMessageKey.of("CannotReceive_DeliveryStop_Multi");

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	/**
	 * Throws an {@link AdempiereException} (user-validation) if any schedule in the given
	 * iterable has {@code IsDeliveryStop=true}.
	 *
	 * <p>The exception lists the distinct blocked vendor names in alphabetical order so the
	 * user knows which vendors to unselect before retrying.
	 *
	 * @param schedules receipt schedules to check; never {@code null}
	 */
	public void assertNoneBlocked(@NonNull final Iterable<I_M_ReceiptSchedule> schedules)
	{
		final Set<BPartnerId> blocked = new HashSet<>();
		for (final I_M_ReceiptSchedule schedule : schedules)
		{
			if (schedule.isDeliveryStop())
			{
				blocked.add(BPartnerId.ofRepoId(schedule.getC_BPartner_ID()));
			}
		}

		if (blocked.isEmpty())
		{
			return;
		}

		final String names = blocked.stream()
				.map(bpartnerId -> bpartnerDAO.getById(bpartnerId, I_C_BPartner.class))
				.map(I_C_BPartner::getName)
				.sorted()
				.collect(Collectors.joining(", "));

		throw new AdempiereException(MSG, names)
				.markAsUserValidationError();
	}
}
