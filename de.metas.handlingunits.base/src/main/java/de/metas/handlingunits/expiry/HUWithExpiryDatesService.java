package de.metas.handlingunits.expiry;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.util.Services;
import lombok.NonNull;

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
public class HUWithExpiryDatesService
{
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final HUWithExpiryDatesRepository huWithExpiryDatesRepository;

	public HUWithExpiryDatesService(@NonNull final HUWithExpiryDatesRepository huWithExpiryDatesRepository)
	{
		this.huWithExpiryDatesRepository = huWithExpiryDatesRepository;
	}

	public MarkExpiredWhereWarnDateExceededResult markExpiredWhereWarnDateExceeded(@NonNull final LocalDate today)
	{
		return MarkExpiredWhereWarnDateExceededCommand.builder()
				.huWithExpiryDatesRepository(huWithExpiryDatesRepository)
				.handlingUnitsBL(handlingUnitsBL)
				.huTrxBL(huTrxBL)
				//
				.today(today)
				//
				.execute();
	}

	public UpdateMonthsUntilExpiryResult updateMonthsUntilExpiry(@NonNull final LocalDate today)
	{
		return UpdateMonthsUntilExpiryCommand.builder()
				.huWithExpiryDatesRepository(huWithExpiryDatesRepository)
				.handlingUnitsBL(handlingUnitsBL)
				//
				.today(today)
				//
				.execute();
	}
}
