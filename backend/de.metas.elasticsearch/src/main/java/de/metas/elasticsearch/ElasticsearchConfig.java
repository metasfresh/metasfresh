/*
 * #%L
 * de.metas.elasticsearch
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

package de.metas.elasticsearch;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import java.util.Optional;

@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration
{
	private static final Logger logger = LogManager.getLogger(ElasticsearchConfig.class);

	private static final String PROP_host = "metasfresh.elasticsearch.host";
	private static final PropertyValueWithOrigin DEFAULT_host = PropertyValueWithOrigin.builder()
			.propertyName(PROP_host)
			.propertyValue("search:9200")
			.origin("default specified by " + ElasticsearchConfig.class)
			.build();

	private final Environment environment;

	public ElasticsearchConfig(final Environment environment) {this.environment = environment;}

	@Override
	@Bean
	public @NonNull RestHighLevelClient elasticsearchClient()
	{
		PropertyValueWithOrigin host = getPropertyValueWithOrigin(PROP_host, environment);
		if (host.isBlankValue())
		{
			host = DEFAULT_host;
		}

		logger.info("Config: {}", host);

		final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
				.connectedTo(host.getPropertyValue())
				.build();

		return RestClients.create(clientConfiguration).rest();
	}

	@SuppressWarnings("SameParameterValue")
	private static PropertyValueWithOrigin getPropertyValueWithOrigin(final String propertyName, final Environment environment)
	{
		final String value = environment.getProperty(propertyName);
		final String origin = getPropertySourceName(propertyName, environment).orElse("?");
		return PropertyValueWithOrigin.builder()
				.propertyName(propertyName)
				.propertyValue(value)
				.origin(origin)
				.build();
	}

	private static Optional<String> getPropertySourceName(final String propertyName, final Environment environment)
	{
		if (environment instanceof AbstractEnvironment)
		{
			final MutablePropertySources propertySources = ((AbstractEnvironment)environment).getPropertySources();
			for (final PropertySource<?> propertySource : propertySources)
			{
				final Object propertyValue = propertySource.getProperty(propertyName);
				if (propertyValue != null)
				{
					if (propertySource instanceof OriginLookup)
					{
						@SuppressWarnings({ "unchecked", "rawtypes" }) final Origin origin = ((OriginLookup)propertySource).getOrigin(propertyName);
						return Optional.of(origin.toString());
					}
					else
					{
						return Optional.of(propertySource.getName());
					}
				}
			}
		}

		return Optional.empty();
	}

	@Value
	@Builder
	private static class PropertyValueWithOrigin
	{
		@NonNull String propertyName;
		String propertyValue;
		String origin;

		@Override
		public String toString()
		{
			return propertyName + " = "
					+ (propertyValue != null ? "'" + propertyValue + "'" : "null")
					+ " (origin: " + origin + ")";
		}

		public boolean isBlankValue()
		{
			return propertyValue == null || Check.isBlank(propertyValue);
		}
	}
}
