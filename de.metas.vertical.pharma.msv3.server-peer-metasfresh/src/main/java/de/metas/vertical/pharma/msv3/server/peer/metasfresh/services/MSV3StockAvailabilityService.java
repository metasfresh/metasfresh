package de.metas.vertical.pharma.msv3.server.peer.metasfresh.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.stock.StockDataAggregateItem;
import de.metas.material.cockpit.stock.StockDataAggregateQuery;
import de.metas.material.cockpit.stock.StockDataQueryOrderBy;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.model.MSV3ServerConfig;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3EventVersion;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3ProductExclude;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3ProductExcludesUpdateEvent;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3ProductExcludesUpdateEvent.MSV3ProductExcludesUpdateEventBuilder;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailability;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailabilityUpdatedEvent;
import de.metas.vertical.pharma.msv3.server.peer.service.MSV3ServerPeerService;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer-metasfresh
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
public class MSV3StockAvailabilityService
{
	private static final String SYSCONFIG_STOCK_AVAILABILITY_BATCH_SIZE = "de.metas.vertical.pharma.msv3.server.peer.metasfresh.services.MSV3StockAvailabilityService.publishAll_StockAvailability.BatchSize";

	private static final Logger logger = LogManager.getLogger(MSV3StockAvailabilityService.class);

	private final StockRepository stockRepository;

	private final MSV3ServerConfigService msv3ServerConfigService;

	private final MSV3ServerPeerService msv3ServerPeerService;

	private final MSV3EventVersionGenerator eventVersionGenerator;

	public MSV3StockAvailabilityService(
			@NonNull final StockRepository stockRepository,
			@NonNull final MSV3ServerConfigService msv3ServerConfigService,
			@NonNull final MSV3ServerPeerService msv3ServerPeerService,
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory)
	{
		this.stockRepository = stockRepository;
		this.msv3ServerConfigService = msv3ServerConfigService;
		this.msv3ServerPeerService = msv3ServerPeerService;
		this.eventVersionGenerator = new MSV3EventVersionGenerator(documentNoBuilderFactory);
	}

	private MSV3ServerConfig getServerConfig()
	{
		return msv3ServerConfigService.getServerConfig();
	}

	public void publishAll()
	{
		publishAll_StockAvailability();
		publishAll_ProductExcludes();
	}

	private void publishAll_StockAvailability()
	{
		final MSV3EventVersion eventVersion = eventVersionGenerator.getNextEventVersion();

		final MSV3ServerConfig serverConfig = getServerConfig();
		if (!serverConfig.hasProducts())
		{
			Loggables.withLogger(logger, Level.WARN)
					.addLog("Asked to publish all stock availabilities but the MSV3 server has no products defined. Deleting all products. Check {}", serverConfig);
			msv3ServerPeerService.publishStockAvailabilityUpdatedEvent(MSV3StockAvailabilityUpdatedEvent.deleteAllOlderThan(eventVersion));
			return;
		}
		else
		{
			// we need the stream's close method to be called so it can clean up temporary DB data
			try (final Stream<MSV3StockAvailabilityUpdatedEvent> eventStream = streamMSV3StockAvailabilityUpdateEvents(serverConfig, eventVersion))
			{
				eventStream.forEach(msv3ServerPeerService::publishStockAvailabilityUpdatedEvent);
			}

			// finally delete the info that is based on earlier update events
			msv3ServerPeerService.publishStockAvailabilityUpdatedEvent(MSV3StockAvailabilityUpdatedEvent.deleteAllOlderThan(eventVersion));
		}
	}

	@VisibleForTesting
	Stream<MSV3StockAvailabilityUpdatedEvent> streamMSV3StockAvailabilityUpdateEvents(
			@NonNull final MSV3ServerConfig serverConfig,
			@NonNull final MSV3EventVersion eventVersion)
	{
		final StockDataAggregateQuery query = StockDataAggregateQuery.builder()
				.productCategoryIds(serverConfig.getProductCategoryIds())
				.warehouseIds(serverConfig.getWarehouseIds())
				.warehouseId(null) // accept also those which aren't in any warehouse yet
				.iteratorBatchSize(getPublishAllStockAvailabilityBatchSize())
				.orderBy(StockDataQueryOrderBy.ProductId)
				.build();

		final Stream<StockDataAggregateItem> stockRecordsStream = stockRepository.streamStockDataAggregateItems(query);

		final Stream<MSV3StockAvailability> stockAvailabilityStream = GuavaCollectors
				.groupByAndStream(stockRecordsStream, StockDataAggregateItem::getProductId)
				.map(records -> toMSV3StockAvailabilityOrNullIfFailed(serverConfig, records))
				.filter(Objects::nonNull);

		return GuavaCollectors
				.batchAndStream(stockAvailabilityStream, query.getIteratorBatchSize())
				.map(availabilityBatchesList -> createAvailabilityUpdatedEvent(availabilityBatchesList, eventVersion))
				.onClose(() -> stockRecordsStream.close())/*forward the close invocation*/;
	}

	private static MSV3StockAvailabilityUpdatedEvent createAvailabilityUpdatedEvent(
			@NonNull final List<MSV3StockAvailability> availabilityBatchesList,
			@NonNull final MSV3EventVersion eventVersion)
	{
		return MSV3StockAvailabilityUpdatedEvent.builder()
				.eventVersion(eventVersion)
				.items(availabilityBatchesList)
				.deleteAllOtherItems(false) // important!
				.build();
	}

	private void publishAll_ProductExcludes()
	{
		final MSV3ProductExcludesUpdateEventBuilder eventsBuilder = MSV3ProductExcludesUpdateEvent.builder()
				.deleteAllOtherItems(true);

		Services.get(IBPartnerProductDAO.class)
				.retrieveAllProductSalesExcludes()
				.forEach(productExclude -> eventsBuilder.item(MSV3ProductExclude.builder()
						.pzn(getPZNByProductId(productExclude.getProductId()))
						.bpartnerId(productExclude.getBpartnerId().getRepoId())
						.build()));

		msv3ServerPeerService.publishProductExcludes(eventsBuilder.build());
	}

	private MSV3StockAvailability toMSV3StockAvailabilityOrNullIfFailed(
			@NonNull final MSV3ServerConfig serverConfig,
			@NonNull final List<StockDataAggregateItem> records)
	{
		try
		{
			return toMSV3StockAvailability(serverConfig, records);
		}
		catch (Exception ex)
		{
			Loggables.withLogger(logger, Level.WARN)
					.addLog("Failed converting StockDataAggregateItems={} to {}", records, MSV3StockAvailability.class.getSimpleName());
			return null;
		}
	}

	private MSV3StockAvailability toMSV3StockAvailability(
			final MSV3ServerConfig serverConfig,
			@NonNull final List<StockDataAggregateItem> records)
	{
		Check.assumeNotEmpty(records, "records is not empty");

		final PZN pzn = getPZNByProductValue(records.get(0).getProductValue());
		final int qtyOnHand = calculateQtyOnHand(serverConfig, records);

		return MSV3StockAvailability.builder()
				.pzn(pzn.getValueAsLong())
				.qty(qtyOnHand)
				.build();
	}

	private int calculateQtyOnHand(final MSV3ServerConfig serverConfig, final List<StockDataAggregateItem> records)
	{
		if (serverConfig.getFixedQtyAvailableToPromise().isPresent())
		{
			return serverConfig.getFixedQtyAvailableToPromise().getAsInt();
		}

		return records.stream()
				.map(StockDataAggregateItem::getQtyOnHand)
				.reduce(BigDecimal.ZERO, BigDecimal::add)
				.setScale(0, RoundingMode.DOWN)
				.intValue();
	}

	public void handleStockChangedEvent(final StockChangedEvent stockChangedEvent)
	{
		final MSV3ServerConfig serverConfig = getServerConfig();
		if (!isEligible(serverConfig, stockChangedEvent))
		{
			logger.trace("Skip {} because it's not eligible for {}", stockChangedEvent, serverConfig);
			return;
		}

		final ProductId productId = ProductId.ofRepoId(stockChangedEvent.getProductId());
		final MSV3StockAvailability item = createStockAvailabilityEventForProductId(serverConfig, productId);

		final MSV3StockAvailabilityUpdatedEvent msv3Event = MSV3StockAvailabilityUpdatedEvent.ofSingle(
				item,
				eventVersionGenerator.getNextEventVersion());
		msv3ServerPeerService.publishStockAvailabilityUpdatedEvent(msv3Event);
	}

	private boolean isEligible(final MSV3ServerConfig serverConfig, final StockChangedEvent event)
	{
		final WarehouseId warehouseId = event.getWarehouseId();
		if (!serverConfig.getWarehouseIds().contains(warehouseId))
		{
			return false;
		}

		if (serverConfig.getProductCategoryIds().isEmpty())
		{
			return false;
		}

		final ProductCategoryId productCategoryId = Services.get(IProductDAO.class).retrieveProductCategoryByProductId(ProductId.ofRepoId(event.getProductId()));
		if (productCategoryId == null)
		{
			return false;
		}
		if (!serverConfig.getProductCategoryIds().contains(productCategoryId))
		{
			return false;
		}

		return true;
	}

	private MSV3StockAvailability createStockAvailabilityEventForProductId(final MSV3ServerConfig serverConfig, final ProductId productId)
	{
		return MSV3StockAvailability.builder()
				.pzn(getPZNByProductId(productId).getValueAsLong())
				.qty(getQtyOnHand(serverConfig, productId))
				.build();
	}

	private PZN getPZNByProductId(final ProductId productId)
	{
		try
		{
			final String productValue = Services.get(IProductDAO.class).retrieveProductValueByProductId(productId);
			return getPZNByProductValue(productValue);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed retrieving PZN for productId=" + productId, ex);
		}
	}

	private PZN getPZNByProductValue(final String productValue)
	{
		try
		{
			final long pznAsLong = Long.parseLong(productValue.trim());
			return PZN.of(pznAsLong);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed retrieving PZN for product value: " + productValue, ex);
		}
	}

	private int getQtyOnHand(final MSV3ServerConfig serverConfig, final ProductId productId)
	{
		if (serverConfig.getFixedQtyAvailableToPromise().isPresent())
		{
			return serverConfig.getFixedQtyAvailableToPromise().getAsInt();
		}

		return stockRepository.getQtyOnHandForProductAndWarehouseIds(productId, serverConfig.getWarehouseIds())
				.setScale(0, RoundingMode.DOWN)
				.intValue();
	}

	@Async
	public void publishProductAddedEvent(final ProductId productId)
	{
		final MSV3ServerConfig serverConfig = getServerConfig();

		final PZN pzn = getPZNByProductId(productId);
		final int qtyOnHand = serverConfig.getFixedQtyAvailableToPromise().orElse(0);
		final MSV3StockAvailability item = MSV3StockAvailability.builder()
				.pzn(pzn.getValueAsLong())
				.qty(qtyOnHand)
				.build();

		final MSV3StockAvailabilityUpdatedEvent event = MSV3StockAvailabilityUpdatedEvent.ofSingle(
				item,
				eventVersionGenerator.getNextEventVersion());
		msv3ServerPeerService.publishStockAvailabilityUpdatedEvent(event);
	}

	@Async
	public void publishProductChangedEvent(final ProductId productId)
	{
		final MSV3ServerConfig serverConfig = getServerConfig();

		final PZN pzn = getPZNByProductId(productId);
		final int qtyOnHand = getQtyOnHand(serverConfig, productId);

		final MSV3StockAvailability item = MSV3StockAvailability.builder()
				.pzn(pzn.getValueAsLong())
				.qty(qtyOnHand)
				.build();

		final MSV3StockAvailabilityUpdatedEvent event = MSV3StockAvailabilityUpdatedEvent.ofSingle(
				item,
				eventVersionGenerator.getNextEventVersion());
		msv3ServerPeerService.publishStockAvailabilityUpdatedEvent(event);
	}

	@Async
	public void publishProductDeletedEvent(final ProductId productId)
	{
		final PZN pzn = getPZNByProductId(productId);
		final MSV3StockAvailability item = MSV3StockAvailability.builder()
				.pzn(pzn.getValueAsLong())
				.delete(true)
				.build();

		final MSV3StockAvailabilityUpdatedEvent event = MSV3StockAvailabilityUpdatedEvent.ofSingle(
				item,
				eventVersionGenerator.getNextEventVersion());
		msv3ServerPeerService.publishStockAvailabilityUpdatedEvent(event);
	}

	public void publishProductExcludeAddedOrChanged(
			final ProductId productId,
			@NonNull final BPartnerId newBPartnerId,
			final BPartnerId oldBPartnerId)
	{
		final PZN pzn = getPZNByProductId(productId);

		final MSV3ProductExcludesUpdateEventBuilder eventBuilder = MSV3ProductExcludesUpdateEvent.builder();

		if (oldBPartnerId != null && !Objects.equals(newBPartnerId, oldBPartnerId))
		{
			eventBuilder.item(MSV3ProductExclude.builder()
					.pzn(pzn)
					.bpartnerId(oldBPartnerId.getRepoId())
					.delete(true)
					.build());
		}

		eventBuilder.item(MSV3ProductExclude.builder()
				.pzn(pzn)
				.bpartnerId(newBPartnerId.getRepoId())
				.build());

		msv3ServerPeerService.publishProductExcludes(eventBuilder.build());
	}

	public void publishProductExcludeDeleted(final ProductId productId, final BPartnerId... bpartnerIds)
	{
		final PZN pzn = getPZNByProductId(productId);

		final List<MSV3ProductExclude> eventItems = Stream.of(bpartnerIds)
				.filter(Objects::nonNull)
				.distinct()
				.map(bpartnerId -> MSV3ProductExclude.builder()
						.pzn(pzn)
						.bpartnerId(bpartnerId.getRepoId())
						.delete(true)
						.build())
				.collect(ImmutableList.toImmutableList());
		if (eventItems.isEmpty())
		{
			return;
		}

		msv3ServerPeerService.publishProductExcludes(MSV3ProductExcludesUpdateEvent.builder()
				.items(eventItems)
				.build());
	}

	private int getPublishAllStockAvailabilityBatchSize()
	{
		return Services
				.get(ISysConfigBL.class)
				.getIntValue(SYSCONFIG_STOCK_AVAILABILITY_BATCH_SIZE, 500);
	}

}
