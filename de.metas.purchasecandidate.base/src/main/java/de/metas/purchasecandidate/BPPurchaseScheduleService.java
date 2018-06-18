package de.metas.purchasecandidate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.util.time.generator.DateSequenceGenerator;
import org.springframework.stereotype.Service;

import lombok.NonNull;

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

	public Optional<LocalDateTime> calculatePurchaseDatePromised(@NonNull final LocalDateTime salesPreparationDate, @NonNull final BPPurchaseSchedule schedule)
	{
		final Optional<LocalDate> purchaseDate = DateSequenceGenerator.builder()
				.dateFrom(LocalDate.MIN)
				.dateTo(LocalDate.MAX)
				// .shifter(shifter) // TODO: non business days aware shifter
				.frequency(schedule.getFrequency())
				.build()
				.generateCurrentPrevious(salesPreparationDate.toLocalDate());

		return purchaseDate.map(schedule::applyTimeTo);
	}
}
