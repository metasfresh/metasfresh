package de.metas.purchasecandidate;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.ICalendarDAO;
import de.metas.util.Services;
import de.metas.util.calendar.IBusinessDayMatcher;
import de.metas.util.calendar.NullBusinessDayMatcher;
import de.metas.util.time.generator.BusinessDayShifter;
import de.metas.util.time.generator.BusinessDayShifter.OnNonBussinessDay;
import de.metas.util.time.generator.DateSequenceGenerator;
import de.metas.util.time.generator.IDateShifter;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;

/*
 * #%L
 * de.metas.purchasecandidate.base
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
public class BPPurchaseScheduleService
{
	private final BPPurchaseScheduleRepository bpPurchaseScheduleRepo;

	public BPPurchaseScheduleService(final BPPurchaseScheduleRepository bpPurchaseScheduleRepo)
	{
		this.bpPurchaseScheduleRepo = bpPurchaseScheduleRepo;
	}

	public Optional<BPPurchaseSchedule> getBPPurchaseSchedule(final BPartnerId bpartnerId, final LocalDate date)
	{
		return bpPurchaseScheduleRepo.getByBPartnerIdAndValidFrom(bpartnerId, date);
	}

	public Optional<ZonedDateTime> calculatePurchaseDatePromised(
			@NonNull final ZonedDateTime salesPreparationDate,
			@NonNull final BPPurchaseSchedule schedule)
	{
		final IBusinessDayMatcher businessDayMatcher;
		if (schedule.getNonBusinessDaysCalendarId() != null)
		{
			final ICalendarDAO calendarRepo = Services.get(ICalendarDAO.class);
			businessDayMatcher = calendarRepo.getCalendarNonBusinessDays(schedule.getNonBusinessDaysCalendarId());
		}
		else
		{
			businessDayMatcher = NullBusinessDayMatcher.instance;
		}

		final IDateShifter dateShifter = BusinessDayShifter.builder()
				.businessDayMatcher(businessDayMatcher)
				.onNonBussinessDay(OnNonBussinessDay.MoveToClosestBusinessDay)
				.build();

		final Optional<LocalDate> purchaseDayPromised = DateSequenceGenerator.builder()
				.dateFrom(LocalDate.MIN)
				.dateTo(LocalDate.MAX)
				.shifter(dateShifter)
				.frequency(schedule.getFrequency())
				.build()
				.calculatePrevious(salesPreparationDate.toLocalDate());

		// TODO: make sure that after applying the time, our date is BEFORE sales preparation time!
		return purchaseDayPromised
				.map(day -> schedule.applyTimeTo(day).atZone(salesPreparationDate.getZone()));
	}
}
