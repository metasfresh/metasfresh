/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.name.strategy;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.bpartner.name.NameAndGreeting;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BPartnerNameAndGreetingStrategies
{
	private static final Logger logger = LogManager.getLogger(BPartnerNameAndGreetingStrategies.class);

	private final ImmutableMap<BPartnerNameAndGreetingStrategyId, BPartnerNameAndGreetingStrategy> strategiesById;

	public BPartnerNameAndGreetingStrategies(
			@NonNull final Optional<List<BPartnerNameAndGreetingStrategy>> strategies)
	{
		this.strategiesById = strategies
				.map(list -> Maps.uniqueIndex(list, BPartnerNameAndGreetingStrategy::getId))
				.orElseGet(ImmutableMap::of);
		logger.info("Strategies: {}", this.strategiesById);
	}

	public ExplainedOptional<NameAndGreeting> compute(
			@NonNull final BPartnerNameAndGreetingStrategyId strategyId,
			@NonNull final ComputeNameAndGreetingRequest request)
	{
		return getStrategyById(strategyId).compute(request);
	}

	private BPartnerNameAndGreetingStrategy getStrategyById(@NonNull final BPartnerNameAndGreetingStrategyId strategyId)
	{
		final BPartnerNameAndGreetingStrategy strategy = strategiesById.get(strategyId);
		if (strategy == null)
		{
			throw new AdempiereException("No strategy found for ID=" + strategyId);
		}
		return strategy;
	}

}
