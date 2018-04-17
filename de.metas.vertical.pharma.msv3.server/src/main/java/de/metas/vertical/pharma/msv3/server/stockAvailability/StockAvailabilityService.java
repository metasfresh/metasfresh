package de.metas.vertical.pharma.msv3.server.stockAvailability;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.vertical.pharma.msv3.protocol.stockAvailability.AvailabilityType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQuery;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQueryItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponse;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponse.StockAvailabilityResponseBuilder;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItem;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3ProductExclude;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3ProductExcludesUpdateEvent;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailability;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailabilityUpdatedEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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
public class StockAvailabilityService
{
	private static final Logger logger = LoggerFactory.getLogger(StockAvailabilityService.class);

	@Autowired
	private JpaStockAvailabilityRepository stockAvailabilityRepo;
	@Autowired
	private JpaProductExcludeRepository productExcludeRepo;

	public StockAvailabilityResponse checkAvailability(final StockAvailabilityQuery query)
	{
		final BPartnerId bpartner = query.getBpartner();

		final StockAvailabilityResponseBuilder responseBuilder = StockAvailabilityResponse.builder()
				.id(query.getId())
				.availabilityType(AvailabilityType.SPECIFIC);

		for (final StockAvailabilityQueryItem queryItem : query.getItems())
		{
			final PZN pzn = queryItem.getPzn();
			final Quantity qtyRequired = queryItem.getQtyRequired();

			final Optional<Quantity> qtyOnHand = getQtyAvailable(pzn, bpartner);
			final Quantity qty = qtyOnHand
					.map(qtyRequired::min)
					.orElse(Quantity.ZERO);

			responseBuilder.item(StockAvailabilityResponseItem.builder()
					.pzn(pzn)
					.qty(qty)
					.build());
		}

		return responseBuilder.build();
	}

	public Optional<Quantity> getQtyAvailable(@NonNull final PZN pzn, @NonNull final BPartnerId bpartner)
	{
		if (productExcludeRepo.existsByPznAndBpartnerId(pzn.getValueAsLong(), bpartner.getBpartnerId()))
		{
			return Optional.empty();
		}

		final JpaStockAvailability jpaStockAvailability = stockAvailabilityRepo.findByPzn(pzn.getValueAsLong());
		if (jpaStockAvailability == null)
		{
			return Optional.empty();
		}

		final Quantity qty = Quantity.of(jpaStockAvailability.getQty());
		return Optional.of(qty);
	}

	public void handleEvent(@NonNull final MSV3StockAvailabilityUpdatedEvent event)
	{
		final String syncToken = event.getId();

		//
		// Update
		{
			final AtomicInteger countUpdated = new AtomicInteger();
			event.getItems().forEach(eventItem -> {
				updateStockAvailability(eventItem, syncToken);
				countUpdated.incrementAndGet();
			});
			logger.debug("Updated {} stock availability records", countUpdated);
		}

		//
		// Delete
		if (event.isDeleteAllOtherItems())
		{
			final long countDeleted = stockAvailabilityRepo.deleteInBatchBySyncTokenNot(syncToken);
			logger.debug("Deleted {} stock availability records", countDeleted);
		}

	}

	private void updateStockAvailability(@NonNull final MSV3StockAvailability request, final String syncToken)
	{
		if (request.isDelete())
		{
			stockAvailabilityRepo.deleteInBatchByPzn(request.getPzn());
		}
		else
		{
			JpaStockAvailability jpaStockAvailability = stockAvailabilityRepo.findByPzn(request.getPzn());
			if (jpaStockAvailability == null)
			{
				jpaStockAvailability = new JpaStockAvailability();
				jpaStockAvailability.setPzn(request.getPzn());
			}

			jpaStockAvailability.setQty(request.getQty());
			jpaStockAvailability.setSyncToken(syncToken);
			stockAvailabilityRepo.save(jpaStockAvailability);
		}
	}

	public void handleEvent(@NonNull final MSV3ProductExcludesUpdateEvent event)
	{
		final String syncToken = event.getId();

		//
		// Update
		{
			final AtomicInteger countUpdated = new AtomicInteger();
			event.getItems().forEach(eventItem -> {
				updateProductExclude(eventItem, syncToken);
				countUpdated.incrementAndGet();
			});
			logger.debug("Updated {} product exclude records", countUpdated);
		}

		//
		// Delete
		if (event.isDeleteAllOtherItems())
		{
			final long countDeleted = productExcludeRepo.deleteInBatchBySyncTokenNot(syncToken);
			logger.debug("Deleted {} product exclude records", countDeleted);
		}
	}

	private void updateProductExclude(@NonNull final MSV3ProductExclude request, final String syncToken)
	{
		if (request.isDelete())
		{
			productExcludeRepo.deleteInBatchByPznAndBpartnerId(request.getPzn().getValueAsLong(), request.getBpartnerId());
		}
		else
		{
			JpaProductExclude jpaProductExclude = productExcludeRepo.findByPznAndBpartnerId(request.getPzn().getValueAsLong(), request.getBpartnerId());
			if (jpaProductExclude == null)
			{
				jpaProductExclude = new JpaProductExclude();
				jpaProductExclude.setPzn(request.getPzn().getValueAsLong());
				jpaProductExclude.setBpartnerId(request.getBpartnerId());
			}

			jpaProductExclude.setSyncToken(syncToken);
			productExcludeRepo.save(jpaProductExclude);
		}
	}
}
