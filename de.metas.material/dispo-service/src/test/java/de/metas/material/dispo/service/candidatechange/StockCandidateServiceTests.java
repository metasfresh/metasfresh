package de.metas.material.dispo.service.candidatechange;

import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.CLIENT_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryCommands;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.event.commons.MaterialDescriptor;

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

public class StockCandidateServiceTests
{
	private StockCandidateService stockCandidateService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepositoryRetrieval candidateRepository = new CandidateRepositoryRetrieval();
		final CandidateRepositoryCommands candidateRepositoryCommands = new CandidateRepositoryCommands();
		stockCandidateService = new StockCandidateService(candidateRepository, candidateRepositoryCommands);

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.complete(true)
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal("10"))
				.date(NOW)
				.build();

		final Candidate stockCandidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescr)
				.build();
		candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(stockCandidate);
	}

	/**
	 * Verifies that if a new stock candidate is created with a time before any existing candidates, then that candidate is created with a zero quantity.
	 */
	@Test
	public void createStockCandidate_before_existing()
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.complete(true)
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.date(BEFORE_NOW)
				.quantity(BigDecimal.ONE)
				.build();

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescr)
				.build();

		final Candidate newCandidateBefore = stockCandidateService.createStockCandidate(candidate);
		assertThat(newCandidateBefore.getQuantity()).isEqualByComparingTo("1"); // WTF?? why not zero?
	}

	/**
	 * Verifies that if a new stock candidate is created with a time after an existing candidates, then that candidate is created with the predecessor's quantity.
	 */
	@Test
	public void createStockCandidate_after_existing()
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.complete(true)
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.date(AFTER_NOW)
				.quantity(BigDecimal.ONE)
				.build();

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescr)
				.build();

		final Candidate newCandidateAfter = stockCandidateService.createStockCandidate(candidate);
		assertThat(newCandidateAfter.getQuantity()).isEqualByComparingTo("11");
	}
}
