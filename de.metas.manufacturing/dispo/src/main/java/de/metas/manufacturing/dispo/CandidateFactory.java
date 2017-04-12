package de.metas.manufacturing.dispo;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.manufacturing.dispo.Candidate.Type;

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
	@Autowired
	private CandidateRepository candidateRepository;

	/**
	 * Creates and returns a new stock candidate which takes its quantity from the next-younger stock candidate that has the same product and locator.
	 * If there is no such next-younger stock candidate (i.e. if this is the very first stock candidate to be created for the given product and locator), then a quantity of zero is taken.
	 *
	 * @param product
	 * @param locator
	 * @param projectedDate
	 * @return
	 */
	public Candidate createStockCandidate(final CandidatesSegment segment)
	{
		final Optional<Candidate> stock = candidateRepository.retrieveStockAt(segment);

		final BigDecimal quantity = stock.isPresent()
				? stock.get().getQuantity()
				: BigDecimal.ZERO;

		return Candidate.builder()
				.type(Type.STOCK)
				.productId(segment.getProductId())
				.warehouseId(segment.getWarehouseId())
				.locatorId(segment.getLocatorId())
				.date(segment.getProjectedDate())
				.quantity(quantity)
				.build();
	}
}
