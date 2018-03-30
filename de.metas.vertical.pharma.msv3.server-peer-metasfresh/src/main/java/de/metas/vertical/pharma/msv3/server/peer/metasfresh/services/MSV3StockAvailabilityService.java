package de.metas.vertical.pharma.msv3.server.peer.metasfresh.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.material.cockpit.stock.StockDataQuery;
import de.metas.material.cockpit.stock.StockDataQueryOrderBy;
import de.metas.material.cockpit.stock.StockDataRecord;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.product.IProductDAO;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.model.MSV3ServerConfig;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailability;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailabilityUpdatedEvent;
import de.metas.vertical.pharma.msv3.server.peer.service.MSV3ServerPeerService;

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
public class MSV3StockAvailabilityService implements InitializingBean
{
	private static final Logger logger = LogManager.getLogger(MSV3StockAvailabilityService.class);

	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private MSV3ServerConfigService msv3ServerConfigService;
	@Autowired
	private MSV3ServerPeerService msv3ServerPeerService;

	@Override
	public void afterPropertiesSet()
	{
	}

	public void publishAll()
	{
		final MSV3ServerConfig serverConfig = msv3ServerConfigService.getServerConfig();
		if (!serverConfig.hasProducts())
		{
			logger.warn("Asked to publish all stock availabilities but the MSV3 server has no products defined. Doing nothing. Check {}", serverConfig);
			return;
		}

		final Stream<StockDataRecord> stockRecordsStream = stockRepository.streamStockDataRecords(StockDataQuery.builder()
				.productCategoryIds(serverConfig.getProductCategoryIds())
				.warehouseIds(serverConfig.getWarehouseIds())
				.warehouseId(null) // accept also those which aren't in any warehouse yet
				.orderBy(StockDataQueryOrderBy.ProductId)
				.build());

		final Stream<MSV3StockAvailability> stockAvailabilityStream = GuavaCollectors.groupByAndStream(stockRecordsStream, StockDataRecord::getProductId)
				.map(records -> toMSV3StockAvailabilityOrNullIfFailed(serverConfig, records))
				.flatMap(sa -> repeat(sa, 10000))
				.filter(Predicates.notNull());

		final List<MSV3StockAvailability> stockAvailabilities = stockAvailabilityStream.collect(ImmutableList.toImmutableList());

		msv3ServerPeerService.publishStockAvailabilityUpdatedEvent(MSV3StockAvailabilityUpdatedEvent.builder()
				.items(stockAvailabilities)
				.deleteAllOtherItems(true)
				.build());
	}

	// used for dev stress testing
	private Stream<MSV3StockAvailability> repeat(final MSV3StockAvailability sa, final int times)
	{
		final long pzn = sa.getPzn();

		return IntStream.rangeClosed(1, times)
				.mapToObj(i -> sa.toBuilder().pzn(pzn * 100000 + i).build());
	}

	private MSV3StockAvailability toMSV3StockAvailabilityOrNullIfFailed(final MSV3ServerConfig serverConfig, final List<StockDataRecord> records)
	{
		try
		{
			return toMSV3StockAvailability(serverConfig, records);
		}
		catch (Exception ex)
		{
			logger.warn("Failed converting {} to {}", records, MSV3StockAvailability.class, ex);
			return null;
		}
	}

	private MSV3StockAvailability toMSV3StockAvailability(final MSV3ServerConfig serverConfig, final List<StockDataRecord> records)
	{
		Check.assumeNotEmpty(records, "records is not empty");

		final long pzn = getPZNByProductValue(records.get(0).getProductValue());
		final int qtyOnHand = calculateQtyOnHand(serverConfig, records);
		return MSV3StockAvailability.builder()
				.pzn(pzn)
				.qty(qtyOnHand)
				.build();
	}

	private int calculateQtyOnHand(final MSV3ServerConfig serverConfig, final List<StockDataRecord> records)
	{
		final BigDecimal qtyOnHand = records.stream()
				.map(StockDataRecord::getQtyOnHand)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return applyQtyAvailableToPromiseMin(qtyOnHand, serverConfig.getQtyAvailableToPromiseMin());
	}

	public void handleStockChangedEvent(final StockChangedEvent event)
	{
		final MSV3ServerConfig serverConfig = msv3ServerConfigService.getServerConfig();
		if (!isEligible(serverConfig, event))
		{
			logger.trace("Skip {} because it's not eligible for {}", event, serverConfig);
			return;
		}

		final int productId = event.getProductId();
		final MSV3StockAvailability stockAvailability = createStockAvailabilityEventForProductId(serverConfig, productId);
		msv3ServerPeerService.publishStockAvailabilityUpdatedEvent(stockAvailability);
	}

	private boolean isEligible(final MSV3ServerConfig serverConfig, final StockChangedEvent event)
	{
		if (!serverConfig.getWarehouseIds().contains(event.getWarehouseId()))
		{
			return false;
		}

		if (serverConfig.getProductCategoryIds().isEmpty())
		{
			return false;
		}

		final int productCategoryId = Services.get(IProductDAO.class).retrieveProductCategoryByProductId(event.getProductId());
		if (productCategoryId <= 0)
		{
			return false;
		}
		if (!serverConfig.getProductCategoryIds().contains(productCategoryId))
		{
			return false;
		}

		return true;
	}

	private MSV3StockAvailability createStockAvailabilityEventForProductId(final MSV3ServerConfig serverConfig, final int productId)
	{
		return MSV3StockAvailability.builder()
				.pzn(getPZNByProductId(productId))
				.qty(getQtyOnHand(serverConfig, productId))
				.build();
	}

	private long getPZNByProductId(final int productId)
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

	private long getPZNByProductValue(final String productValue)
	{
		try
		{
			return Long.parseLong(productValue.trim());
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed retrieving PZN for product value: " + productValue, ex);
		}
	}

	private int getQtyOnHand(final MSV3ServerConfig serverConfig, final int productId)
	{
		final BigDecimal qtyOnHand = stockRepository.getQtyOnHandForProductAndWarehouseIds(productId, serverConfig.getWarehouseIds());
		return applyQtyAvailableToPromiseMin(qtyOnHand, serverConfig.getQtyAvailableToPromiseMin());
	}

	private int applyQtyAvailableToPromiseMin(final BigDecimal qtyOnHand, final int qtyAvailableToPromiseMin)
	{
		return Math.max(qtyOnHand.intValue(), qtyAvailableToPromiseMin);
	}
}
