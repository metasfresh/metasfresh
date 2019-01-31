package de.metas.bpartner.product.stats;

import java.time.ZonedDateTime;
import java.util.Collection;

import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.bpartner.BPartnerId;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;
import de.metas.product.ProductId;
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

/**
 * Gets {@link AbstractTransactionEvent} events, converts and sends them to {@link BPartnerProductStatsService}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
@Service
@Profile(Profiles.PROFILE_App) // it's important to have just *one* instance of this listener, because on each event needs to be handled exactly once.
class MaterialTransactionEventDispatcher
		implements MaterialEventHandler<AbstractTransactionEvent>
{
	private final BPartnerProductStatsService bpartnerProductStatsService;

	MaterialTransactionEventDispatcher(@NonNull final BPartnerProductStatsService bpartnerProductStatsService)
	{
		this.bpartnerProductStatsService = bpartnerProductStatsService;
	}

	@Override
	public Collection<Class<? extends AbstractTransactionEvent>> getHandeledEventType()
	{
		return ImmutableList.of(TransactionCreatedEvent.class, TransactionDeletedEvent.class);
	}

	@Override
	public void handleEvent(final AbstractTransactionEvent event)
	{
		if (event.getReceiptId() == null && event.getShipmentId() == null)
		{
			return;
		}

		final ProductId productId = ProductId.ofRepoId(event.getMaterialDescriptor().getProductId());
		final ZonedDateTime date = TimeUtil.asZonedDateTime(event.getMaterialDescriptor().getDate());

		if (event.getReceiptId() != null)
		{
			final BPartnerId vendorId = event.getMaterialDescriptor().getVendorId();

			if (event instanceof TransactionCreatedEvent)
			{
				bpartnerProductStatsService.handleEvent(ReceiptCreatedEvent.builder()
						.vendorId(vendorId)
						.productId(productId)
						.receiptDate(date)
						.build());
			}
			else if (event instanceof TransactionDeletedEvent)
			{
				bpartnerProductStatsService.handleEvent(ReceiptDeletedEvent.builder()
						.vendorId(vendorId)
						.productId(productId)
						.receiptDate(date)
						.build());
			}
		}
		else if (event.getShipmentId() != null)
		{
			final BPartnerId customerId = event.getMaterialDescriptor().getCustomerId();

			if (event instanceof TransactionCreatedEvent)
			{
				bpartnerProductStatsService.handleEvent(ShipmentCreatedEvent.builder()
						.customerId(customerId)
						.productId(productId)
						.shipmentDate(date)
						.build());
			}
			else if (event instanceof TransactionDeletedEvent)
			{
				bpartnerProductStatsService.handleEvent(ShipmentDeletedEvent.builder()
						.customerId(customerId)
						.productId(productId)
						.shipmentDate(date)
						.build());
			}
		}
	}

}
