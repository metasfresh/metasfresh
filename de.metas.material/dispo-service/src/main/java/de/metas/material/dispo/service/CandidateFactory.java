package de.metas.material.dispo.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidatesSegment.DateOperator;
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
public class CandidateFactory
{
	private final CandidateRepository candidateRepository;

	public CandidateFactory(@NonNull final CandidateRepository candidateRepository)
	{
		this.candidateRepository = candidateRepository;
	}

	/**
	 * Creates and returns <b>but does not store</b> a new stock candidate which takes its quantity from the next-younger (or same-age!) stock candidate that has the same product and warehouse.
	 * If there is no such next-younger stock candidate (i.e. if this is the very first stock candidate to be created for the given product and locator), then a quantity of zero is taken.
	 *
	 * @param candidate
	 * @return a candidate with
	 *         <ul>
	 *         <li>type = {@link Type#STOCK}</li>
	 *         <li>qty = qty of the given {@code candidate} plus the next younger candidate's quantity
	 *         <li>groupId of the next younger-candidate (or null if there is none)
	 *         </ul>
	 */
	public Candidate createStockCandidate(@NonNull final Candidate candidate)
	{
		final Optional<Candidate> stock = candidateRepository.retrieveLatestMatch(candidate.mkSegmentBuilder()
				.type(Type.STOCK)
				.dateOperator(DateOperator.until)
				.build());

		final BigDecimal formerQuantity = stock.isPresent()
				? stock.get().getQuantity()
				: BigDecimal.ZERO;

		final Integer groupId = stock.isPresent()
				? stock.get().getGroupId()
				: null;

		return Candidate.builder()
				.type(Type.STOCK)
				.orgId(candidate.getOrgId())
				.clientId(candidate.getClientId())
				.productId(candidate.getProductId())
				.warehouseId(candidate.getWarehouseId())
				.date(candidate.getDate())
				.quantity(formerQuantity.add(candidate.getQuantity()))
				.reference(candidate.getReference())
				.parentId(candidate.getParentId())
				.seqNo(candidate.getSeqNo())
				.groupId(groupId)
				.build();
	}
}
