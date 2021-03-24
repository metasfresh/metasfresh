package de.metas.material.dispo.service.candidatechange;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler.OnNewOrChangeAdvise;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/*
 * #%L
 * metasfresh-manufacturing-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Profile(Profiles.PROFILE_MaterialDispo)
public class CandidateChangeService
{
	private static final Logger logger = LogManager.getLogger(CandidateChangeService.class);

	private final Map<CandidateType, CandidateHandler> type2handler;

	public CandidateChangeService(@NonNull final Collection<CandidateHandler> handlers)
	{
		type2handler = createMapOfHandlers(handlers);
		logger.info("Handlers: {}", type2handler);
	}

	public Candidate onCandidateNewOrChange(@NonNull final Candidate candidate)
	{
		return onCandidateNewOrChange(candidate, OnNewOrChangeAdvise.DEFAULT);
	}

	/**
	 * Persists the given candidate and decides if further events shall be fired.
	 */
	public Candidate onCandidateNewOrChange(
			@NonNull final Candidate candidate,
			@NonNull final OnNewOrChangeAdvise advise)
	{
		candidate.validateNonStockCandidate();

		final CandidateHandler candidateChangeHandler = getHandlerFor(candidate);
		return candidateChangeHandler.onCandidateNewOrChange(candidate, advise);
	}

	public void onCandidateDelete(@NonNull final Candidate candidate)
	{
		candidate.validateNonStockCandidate();

		final CandidateHandler candidateChangeHandler = getHandlerFor(candidate);
		candidateChangeHandler.onCandidateDelete(candidate);
	}

	private CandidateHandler getHandlerFor(final Candidate candidate)
	{
		final CandidateHandler candidateChangeHandler = type2handler.get(candidate.getType());
		if (candidateChangeHandler == null)
		{
			throw new AdempiereException("The given candidate has an unsupported type")
					.appendParametersToMessage()
					.setParameter("type", candidate.getType())
					.setParameter("candidate", candidate);
		}
		return candidateChangeHandler;
	}

	@VisibleForTesting
	static Map<CandidateType, CandidateHandler> createMapOfHandlers(@NonNull final Collection<CandidateHandler> handlers)
	{
		final ImmutableMap.Builder<CandidateType, CandidateHandler> builder = ImmutableMap.builder();
		for (final CandidateHandler handler : handlers)
		{
			if (handler.getHandeledTypes().isEmpty())
			{
				logger.warn("Skip handler because no handled types provided: {}", handler);
				continue;
			}

			for (final CandidateType type : handler.getHandeledTypes())
			{
				builder.put(type, handler); // builder already prohibits duplicate keys
			}
		}
		return builder.build();
	}
}
