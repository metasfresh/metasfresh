package de.metas.material.dispo.service.candidatechange;

import java.util.Collection;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler;
import lombok.NonNull;

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
public class CandidateChangeService
{

	private final Map<Type, CandidateHandler> type2handler;

	public CandidateChangeService(
			@NonNull final Collection<CandidateHandler> candidateChangeHandlers)
	{
		type2handler = Maps.uniqueIndex(candidateChangeHandlers, CandidateHandler::getHandeledType);
	}

	/**
	 * Persists the given candidate and decides if further events shall be fired.
	 * 
	 * @param candidate
	 * @return
	 */
	public Candidate onCandidateNewOrChange(@NonNull final Candidate candidate)
	{

		final CandidateHandler candidateChangeHandler = type2handler.get(candidate.getType());
		if (candidateChangeHandler == null)
		{

			throw new AdempiereException("Given 'candidate' parameter has an unsupported type").appendParametersToMessage()
					.setParameter("type", candidate.getType())
					.setParameter("candidate", candidate);
		}

		return candidateChangeHandler.onCandidateNewOrChange(candidate);
	}

}
