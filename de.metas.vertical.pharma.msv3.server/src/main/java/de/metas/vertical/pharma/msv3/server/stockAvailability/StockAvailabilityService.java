package de.metas.vertical.pharma.msv3.server.stockAvailability;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.transaction.Transactional;

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
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItemPart;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItemPartType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilitySubstitutionReason;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3EventVersion;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3ProductExclude;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3ProductExcludesUpdateEvent;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailability;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailabilityUpdatedEvent;
import de.metas.vertical.pharma.msv3.server.stockAvailability.jpa.JpaProductExclude;
import de.metas.vertical.pharma.msv3.server.stockAvailability.jpa.JpaProductExcludeRepository;
import de.metas.vertical.pharma.msv3.server.stockAvailability.jpa.JpaStockAvailability;
import de.metas.vertical.pharma.msv3.server.stockAvailability.jpa.JpaStockAvailabilityRepository;
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
			final Quantity qtyOnHand = getQtyAvailable(pzn, bpartner).orElse(Quantity.ZERO);

			final StockAvailabilityResponseItem item;
			if (qtyRequired.compareTo(qtyOnHand) <= 0)
			{
				item = createStockAvailabilityResponseItem_Available(pzn, qtyRequired);
			}
			else
			{
				item = createStockAvailabilityResponseItem_NotAvailable(pzn, qtyRequired);
			}

			responseBuilder.item(item);
		}

		return responseBuilder.build();
	}

	private StockAvailabilityResponseItem createStockAvailabilityResponseItem_Available(final PZN pzn, final Quantity qty)
	{
		return StockAvailabilityResponseItem.builder()
				.pzn(pzn)
				.qty(qty)
				.part(StockAvailabilityResponseItemPart.builder()
						.type(StockAvailabilityResponseItemPartType.NORMAL)
						.reason(StockAvailabilitySubstitutionReason.NO_INFO)
						.qty(qty)
						.build())
				.build();
	}

	private StockAvailabilityResponseItem createStockAvailabilityResponseItem_NotAvailable(final PZN pzn, final Quantity qty)
	{
		return StockAvailabilityResponseItem.builder()
				.pzn(pzn)
				.qty(qty)
				.part(StockAvailabilityResponseItemPart.builder()
						.type(StockAvailabilityResponseItemPartType.NOT_DELIVERABLE)
						.reason(StockAvailabilitySubstitutionReason.MISSING)
						.qty(qty)
						.build())
				.build();
	}

	public Optional<Quantity> getQtyAvailable(@NonNull final PZN pzn, @NonNull final BPartnerId bpartner)
	{
		if (productExcludeRepo.existsByPznAndMfBpartnerId(pzn.getValueAsLong(), bpartner.getBpartnerId()))
		{
			return Optional.empty();
		}

		final JpaStockAvailability jpaStockAvailability = stockAvailabilityRepo.findByMfPzn(pzn.getValueAsLong());
		if (jpaStockAvailability == null)
		{
			return Optional.empty();
		}

		final Quantity qty = Quantity.of(jpaStockAvailability.getMfQty());
		return Optional.of(qty);
	}

	@Transactional
	public void handleEvent(@NonNull final MSV3StockAvailabilityUpdatedEvent event)
	{
		final String mfSyncToken = event.getId();
		final MSV3EventVersion mfEventVersion = event.getEventVersion();

		//
		// Update
		{
			final AtomicInteger countUpdated = new AtomicInteger();

			for (final MSV3StockAvailability eventItem : event.getItems())
			{
				updateStockAvailability(eventItem, mfSyncToken, mfEventVersion);
				countUpdated.incrementAndGet();
			}
			logger.debug("Updated {} stock availability records", countUpdated);
		}

		//
		// Delete
		if (event.isDeleteAllOtherItems())
		{
			final long countDeleted = stockAvailabilityRepo
					.deleteInBatchByMfSyncTokenNotAndMfEventVersionLessThan(
							mfSyncToken,
							mfEventVersion.getAsInt());
			logger.debug("Deleted {} stock availability records", countDeleted);
		}

	}

	private void updateStockAvailability(
			@NonNull final MSV3StockAvailability request,
			final String mfSyncToken,
			@NonNull final MSV3EventVersion mfEventVersion)
	{
		if (request.isDelete())
		{
			stockAvailabilityRepo
					.deleteInBatchByMfPznAndMfEventVersionLessThan(
							request.getPzn(),
							mfEventVersion.getAsInt());
		}
		else
		{
			JpaStockAvailability jpaStockAvailability = stockAvailabilityRepo.findByMfPzn(request.getPzn());
			if (jpaStockAvailability == null)
			{
				jpaStockAvailability = new JpaStockAvailability();
				jpaStockAvailability.setMfPzn(request.getPzn());
			}
			else if (jpaStockAvailability.getMfEventVersion() > mfEventVersion.getAsInt())
			{
				logger.debug("Discard request with mfEventVersion={} because our local record has mfEventVersion={}; request={}",
						mfEventVersion, jpaStockAvailability.getMfEventVersion(), request);
				return;
			}

			jpaStockAvailability.setMfEventVersion(mfEventVersion.getAsInt());
			jpaStockAvailability.setMfQty(request.getQty());
			jpaStockAvailability.setMfSyncToken(mfSyncToken);
			stockAvailabilityRepo.save(jpaStockAvailability);
		}
	}

	@Transactional
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
			productExcludeRepo.deleteInBatchByPznAndMfBpartnerId(request.getPzn().getValueAsLong(), request.getBpartnerId());
		}
		else
		{
			JpaProductExclude jpaProductExclude = productExcludeRepo.findByPznAndMfBpartnerId(request.getPzn().getValueAsLong(), request.getBpartnerId());
			if (jpaProductExclude == null)
			{
				jpaProductExclude = new JpaProductExclude();
				jpaProductExclude.setPzn(request.getPzn().getValueAsLong());
				jpaProductExclude.setMfBpartnerId(request.getBpartnerId());
			}

			jpaProductExclude.setSyncToken(syncToken);
			productExcludeRepo.save(jpaProductExclude);
		}
	}
}
