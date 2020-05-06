package de.metas.dunning_gateway.api;

import lombok.NonNull;

import javax.annotation.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.dunning_gateway.spi.DunningExportClient;
import de.metas.dunning_gateway.spi.DunningExportClientFactory;
import de.metas.dunning_gateway.spi.model.DunningToExport;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;

/*
 * #%L
 * metasfresh-invoice.gateway.commons
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
public class DunningExportServiceRegistry
{
	private final Map<String, DunningExportClientFactory> dunningExportClientFactoriesByGatewayId;

	public DunningExportServiceRegistry(Optional<List<DunningExportClientFactory>> dunningExportClientFactories)
	{
		dunningExportClientFactoriesByGatewayId = dunningExportClientFactories.orElse(ImmutableList.of())
				.stream()
				.filter(Predicates.notNull())
				.collect(GuavaCollectors.toImmutableMapByKey(DunningExportClientFactory::getDunningExportProviderId));
	}

	public boolean hasServiceSupport(@Nullable final String dunningExportGatewayId)
	{
		if (Check.isEmpty(dunningExportGatewayId, true))
		{
			return false;
		}
		return dunningExportClientFactoriesByGatewayId.containsKey(dunningExportGatewayId);
	}

	public List<DunningExportClient> createExportClients(@NonNull final DunningToExport dunning)
	{
		final Collection<DunningExportClientFactory> factories = dunningExportClientFactoriesByGatewayId.values();

		final ImmutableList.Builder<DunningExportClient> result = ImmutableList.builder();
		for (final DunningExportClientFactory factory : factories)
		{
			final Optional<DunningExportClient> newClientForDunning = factory.newClientForDunning(dunning);
			newClientForDunning.ifPresent(result::add);
		}
		return result.build();
	}
}
