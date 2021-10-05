/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.shopware6.unit;

import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.unit.JsonUOM;
import de.metas.camel.externalsystems.shopware6.api.model.unit.JsonUnits;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jcache.policy.JCachePolicy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.util.concurrent.TimeUnit;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class GetUnitsRouteBuilder extends RouteBuilder
{
	public static final String GET_UOM_MAPPINGS_ROUTE_ID = "Shopware6-getUOMMappings";

	private final ProcessLogger processLogger;

	public GetUnitsRouteBuilder(@NonNull final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void configure()
	{
		final CachingProvider cachingProvider = Caching.getCachingProvider();
		final CacheManager cacheManager = cachingProvider.getCacheManager();
		final MutableConfiguration<de.metas.camel.externalsystems.shopware6.unit.GetUnitsRequest, Object> config = new MutableConfiguration<>();
		config.setTypes(de.metas.camel.externalsystems.shopware6.unit.GetUnitsRequest.class, Object.class);
		config.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.DAYS, 1)));
		final Cache<de.metas.camel.externalsystems.shopware6.unit.GetUnitsRequest, Object> cache = cacheManager.createCache("unit", config);

		final JCachePolicy jcachePolicy = new JCachePolicy();
		jcachePolicy.setCache(cache);

		from(direct(GET_UOM_MAPPINGS_ROUTE_ID))
				.id(GET_UOM_MAPPINGS_ROUTE_ID)
				.streamCaching()
				.policy(jcachePolicy)
				.log("Route invoked. Results will be cached")
				.process(this::getUnits);
	}

	private void getUnits(final Exchange exchange)
	{
		final GetUnitsRequest getUnitsRequest = exchange.getIn().getBody(GetUnitsRequest.class);

		if (getUnitsRequest == null)
		{
			throw new RuntimeException("No getUnitsRequest provided!");
		}

		final ShopwareClient shopwareClient = ShopwareClient
				.of(getUnitsRequest.getClientId(), getUnitsRequest.getClientSecret(), getUnitsRequest.getBaseUrl(), PInstanceLogger.of(processLogger));

		final JsonUnits units = shopwareClient.getUnits();

		if (CollectionUtils.isEmpty(units.getUnitList()))
		{
			throw new RuntimeException("No units return from Shopware!");
		}

		final ImmutableMap<String, String> unitId2Code = units.getUnitList()
				.stream()
				.collect(ImmutableMap.toImmutableMap(JsonUOM::getId, JsonUOM::getShortCode));

		final UOMInfoProvider uomInfoProvider = UOMInfoProvider.builder()
				.unitId2Code(unitId2Code)
				.build();

		exchange.getIn().setBody(uomInfoProvider);
	}
}
