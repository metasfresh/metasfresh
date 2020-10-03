/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.bpartner.product.stats;

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Set;

@Component
public class BPartnerProductStatsEventHandler
{
	private final BPartnerProductStatsRepository statsRepo;

	public BPartnerProductStatsEventHandler(@NonNull final BPartnerProductStatsRepository statsRepo)
	{
		this.statsRepo = statsRepo;
	}

	public void handleInOutChangedEvent(@NonNull final InOutChangedEvent event)
	{
		final BPartnerId bpartnerId = event.getBpartnerId();
		final Set<ProductId> productIds = event.getProductIds();

		if (event.isReversal())
		{
			statsRepo.recomputeStatistics(RecomputeStatisticsRequest.builder()
					.bpartnerId(bpartnerId)
					.productIds(productIds)
					.recomputeInOutStatistics(true)
					.build());
		}
		else
		{
			final ZonedDateTime movementDate = TimeUtil.asZonedDateTime(event.getMovementDate());
			final boolean isShipment = event.getSoTrx().isSales();

			final ImmutableMap<ProductId, BPartnerProductStats> statsByProductId = statsRepo.getByPartnerAndProducts(bpartnerId, productIds);

			for (final ProductId productId : productIds)
			{
				BPartnerProductStats stats = statsByProductId.get(productId);
				if (stats == null)
				{
					stats = BPartnerProductStats.newInstance(bpartnerId, productId);
				}

				if (isShipment)
				{
					stats.updateLastShipmentDate(movementDate);
				}
				else
				{
					stats.updateLastReceiptDate(movementDate);
				}

				statsRepo.save(stats);
			}
		}
	}

	public void handleInvoiceChangedEvent(@NonNull final InvoiceChangedEvent event)
	{
		// NOTE: only sales invoices are supported atm
		if (!event.getSoTrx().isSales())
		{
			return;
		}

		final BPartnerId bpartnerId = event.getBpartnerId();
		final Set<ProductId> productIds = event.getProductIds();

		if (event.isReversal())
		{
			statsRepo.recomputeStatistics(RecomputeStatisticsRequest.builder()
					.bpartnerId(bpartnerId)
					.productIds(productIds)
					.recomputeInvoiceStatistics(true)
					.build());
		}
		else
		{
			final ImmutableMap<ProductId, BPartnerProductStats> statsByProductId = statsRepo.getByPartnerAndProducts(bpartnerId, productIds);

			for (final ProductId productId : productIds)
			{
				BPartnerProductStats stats = statsByProductId.get(productId);
				if (stats == null)
				{
					stats = BPartnerProductStats.newInstance(bpartnerId, productId);
				}

				final BPartnerProductStats.LastInvoiceInfo lastSalesInvoice = extractLastSalesInvoiceInfo(event, productId);
				stats.updateLastSalesInvoiceInfo(lastSalesInvoice);

				statsRepo.save(stats);
			}
		}
	}

	private static BPartnerProductStats.LastInvoiceInfo extractLastSalesInvoiceInfo(final InvoiceChangedEvent event, final ProductId productId)
	{
		return BPartnerProductStats.LastInvoiceInfo.builder()
				.invoiceId(event.getInvoiceId())
				.invoiceDate(event.getInvoiceDate())
				.price(event.getProductPrice(productId))
				.build();
	}
}
