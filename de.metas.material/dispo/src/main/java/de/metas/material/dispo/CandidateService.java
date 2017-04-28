package de.metas.material.dispo;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.event.MaterialDemandEvent;
import de.metas.material.event.ProductionOrderEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrder.PPOrderBuilder;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.pporder.PPOrderLine.PPOrderLineBuilder;
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

public class CandidateService
{
	private final CandidateRepository candidateRepository;

	public CandidateService(@NonNull final CandidateRepository candidateRepository)
	{
		this.candidateRepository = candidateRepository;
	}

	public void requestOrder(final Integer groupId)
	{
		final List<Candidate> group = candidateRepository.retrieveGroup(groupId);
		if (group.isEmpty())
		{
			return;
		}

		switch (group.get(0).getSubType())
		{
			case PRODUCTION:
				requestProductionOrder(group);
				break;

			default:
				break;
		}

	}

	/**
	 * 
	 * @param group a non-empty list of candidates that all have {@link SubType#PRODUCTION}, 
	 * all have the same {@link Candidate#getGroupId()}
	 * and all have appropriate not-null {@link Candidate#getProductionDetail()}s.
	 * @return
	 */
	@VisibleForTesting
	ProductionOrderEvent requestProductionOrder(List<Candidate> group)
	{
		final PPOrderBuilder ppOrderBuilder = PPOrder.builder();
		final PPOrderLineBuilder ppOrderLineBuilder = PPOrderLine.builder();

		for (final Candidate groupMember : group)
		{

		}
		return ProductionOrderEvent.builder()
				.when(Instant.now())
				.build();
	}
}
