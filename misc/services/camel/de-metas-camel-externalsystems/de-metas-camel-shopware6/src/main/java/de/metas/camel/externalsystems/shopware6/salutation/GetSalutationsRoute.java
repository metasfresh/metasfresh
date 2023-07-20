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

package de.metas.camel.externalsystems.shopware6.salutation;

import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.salutation.JsonSalutation;
import de.metas.camel.externalsystems.shopware6.api.model.salutation.JsonSalutationItem;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jcache.policy.JCachePolicy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.SALUTATION_KEY_NOT_SPECIFIED;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class GetSalutationsRoute extends RouteBuilder
{
	public static final String GET_SALUTATION_ROUTE_ID = "Shopware6-getSalutations";

	private final ProcessLogger processLogger;

	public GetSalutationsRoute(@NonNull final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void configure()
	{
		final CachingProvider cachingProvider = Caching.getCachingProvider();
		final CacheManager cacheManager = cachingProvider.getCacheManager();
		final MutableConfiguration<GetSalutationsRequest, Object> config = new MutableConfiguration<>();
		config.setTypes(GetSalutationsRequest.class, Object.class);
		config.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.DAYS, 1)));
		final Cache<GetSalutationsRequest, Object> cache = cacheManager.createCache("salutation", config);

		final JCachePolicy jcachePolicy = new JCachePolicy();
		jcachePolicy.setCache(cache);

		from(direct(GET_SALUTATION_ROUTE_ID))
				.id(GET_SALUTATION_ROUTE_ID)
				.streamCaching()
				.policy(jcachePolicy)
				.log("Route invoked. Salutations will be cached")
				.process(this::getAndAttachSalutations);
	}

	private void getAndAttachSalutations(final Exchange exchange)
	{
		final GetSalutationsRequest getSalutationsRequest = exchange.getIn().getBody(GetSalutationsRequest.class);

		if (getSalutationsRequest == null)
		{
			throw new RuntimeException("No getSalutationsRequest provided!");
		}
		final PInstanceLogger pInstanceLogger = PInstanceLogger.of(processLogger);

		final ShopwareClient shopwareClient = ShopwareClient
				.of(getSalutationsRequest.getClientId(), getSalutationsRequest.getClientSecret(), getSalutationsRequest.getBaseUrl(), pInstanceLogger);

		final ImmutableMap<String, String> salutationId2DisplayName = shopwareClient.getSalutations()
				.map(JsonSalutation::getSalutationList)
				.map(salutationList -> salutationList
						.stream()
						.filter(salutationItem -> salutationItem!= null && !Objects.equals(salutationItem.getSalutationKey(), SALUTATION_KEY_NOT_SPECIFIED))
						.collect(ImmutableMap.toImmutableMap(JsonSalutationItem::getId, JsonSalutationItem::getDisplayName)))
				.orElseGet(ImmutableMap::of);

		final SalutationInfoProvider salutationInfoProvider = SalutationInfoProvider.builder()
				.salutationId2DisplayName(salutationId2DisplayName)
				.build();

		exchange.getIn().setBody(salutationInfoProvider);
	}
}