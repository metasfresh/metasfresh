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

package de.metas.camel.externalsystems.shopware6.currency;

import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.currency.JsonCurrencies;
import de.metas.camel.externalsystems.shopware6.api.model.currency.JsonCurrency;
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
public class GetCurrenciesRoute extends RouteBuilder
{
	public static final String GET_CURRENCY_ROUTE_ID = "Shopware6-getCurrencies";


	private final ProcessLogger processLogger;

	public GetCurrenciesRoute(@NonNull final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void configure()
	{
		final CachingProvider cachingProvider = Caching.getCachingProvider();
		final CacheManager cacheManager = cachingProvider.getCacheManager();
		final MutableConfiguration<GetCurrenciesRequest, Object> config = new MutableConfiguration<>();
		config.setTypes(GetCurrenciesRequest.class, Object.class);
		config.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.DAYS, 1)));
		final Cache<GetCurrenciesRequest, Object> cache = cacheManager.createCache("currency", config);

		final JCachePolicy jcachePolicy = new JCachePolicy();
		jcachePolicy.setCache(cache);

		from(direct(GET_CURRENCY_ROUTE_ID))
				.id(GET_CURRENCY_ROUTE_ID)
				.streamCaching()
				.policy(jcachePolicy)
				.log("Route invoked. Results will be cached")
				.process(this::getCurrencies);
	}

	private void getCurrencies(final Exchange exchange)
	{
		final GetCurrenciesRequest getCurrenciesRequest = exchange.getIn().getBody(GetCurrenciesRequest.class);

		if (getCurrenciesRequest == null)
		{
			throw new RuntimeException("No getCurrenciesRequest provided!");
		}
		final PInstanceLogger pInstanceLogger = PInstanceLogger.of(processLogger);

		final ShopwareClient shopwareClient = ShopwareClient
				.of(getCurrenciesRequest.getClientId(), getCurrenciesRequest.getClientSecret(), getCurrenciesRequest.getBaseUrl(), pInstanceLogger);

		final JsonCurrencies currencies = shopwareClient.getCurrencies();

		if (CollectionUtils.isEmpty(currencies.getCurrencyList()))
		{
			throw new RuntimeException("No currencies return from Shopware!");
		}

		final ImmutableMap<String, String> currencyId2IsoCode = currencies.getCurrencyList().stream()
				.collect(ImmutableMap.toImmutableMap(JsonCurrency::getId, JsonCurrency::getIsoCode));

		final CurrencyInfoProvider currencyInfoProvider = CurrencyInfoProvider.builder()
				.currencyId2IsoCode(currencyId2IsoCode)
				.build();

		exchange.getIn().setBody(currencyInfoProvider);
	}
}
