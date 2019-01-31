package de.metas.bpartner.product.stats;

import org.springframework.stereotype.Service;

import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class BPartnerProductStatsService
{
	private final BPartnerProductStatsRepository statsRepo;

	public BPartnerProductStatsService(
			@NonNull final BPartnerProductStatsRepository statsRepo)
	{
		this.statsRepo = statsRepo;
	}

	public void handleEvent(@NonNull final ShipmentCreatedEvent event)
	{
		final BPartnerProductStats stats = statsRepo.getOrCreateByPartnerAndProduct(event.getCustomerId(), event.getProductId());

		if (stats.getLastShipmentDate() == null || event.getShipmentDate().isAfter(stats.getLastShipmentDate()))
		{
			stats.setLastShipmentDate(event.getShipmentDate());
			statsRepo.save(stats);
		}
	}

	public void handleEvent(@NonNull final ShipmentDeletedEvent event)
	{
		// TODO
	}

	public void handleEvent(@NonNull final ReceiptCreatedEvent event)
	{
		final BPartnerProductStats stats = statsRepo.getOrCreateByPartnerAndProduct(event.getVendorId(), event.getProductId());

		if (stats.getLastReceiptDate() == null || event.getReceiptDate().isAfter(stats.getLastReceiptDate()))
		{
			stats.setLastShipmentDate(event.getReceiptDate());
			statsRepo.save(stats);
		}
	}

	public void handleEvent(@NonNull final ReceiptDeletedEvent event)
	{
		// TODO
	}

}
