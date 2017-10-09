package de.metas.material.dispo.service.candidatechange;

import org.adempiere.util.Check;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateCangeHandler;
import de.metas.material.dispo.service.candidatechange.handler.StockUpCandiateCangeHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandiateCangeHandler;
import de.metas.material.event.MaterialEventService;
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
@Lazy // .. because MaterialEventService needs to be lazy
public class CandidateChangeService
{
	private final DemandCandiateCangeHandler demandCandiateCangeHandler;
	
	private final StockUpCandiateCangeHandler stockUpCandiateCangeHandler;
	
	private final SupplyCandiateCangeHandler supplyCandiateCangeHandler;
	
	private final CandidateRepository candidateRepository;
	
	public CandidateChangeService(
			@NonNull final CandidateRepository candidateRepository,
			@NonNull final StockCandidateService stockCandidateService,
			@NonNull final MaterialEventService materialEventService)
	{
		this.candidateRepository = candidateRepository;
		
		this.demandCandiateCangeHandler = DemandCandiateCangeHandler.builder()
				.candidateRepository(candidateRepository)
				.materialEventService(materialEventService)
				.stockCandidateService(stockCandidateService)
				.build();
		
		this.supplyCandiateCangeHandler = SupplyCandiateCangeHandler.builder()
				.candidateRepository(candidateRepository)
				.materialEventService(materialEventService)
				.stockCandidateService(stockCandidateService)
				.build();
		
		this.stockUpCandiateCangeHandler = StockUpCandiateCangeHandler.builder()
				.candidateRepository(candidateRepository)
				.materialEventService(materialEventService)
				.build();
	}

	/**
	 * Persists the given candidate and decides if further events shall be fired.
	 * 
	 * @param candidate
	 * @return
	 */
	public Candidate onCandidateNewOrChange(@NonNull final Candidate candidate)
	{
		switch (candidate.getType())
		{
			case DEMAND:
				return demandCandiateCangeHandler.onDemandCandidateNewOrChange(candidate);
				
			case SUPPLY:
				return supplyCandiateCangeHandler.onSupplyCandidateNewOrChange(candidate);

			case STOCK_UP:
				return stockUpCandiateCangeHandler.onStockUpCandidateNewOrChange(candidate);

			default:
				Check.errorIf(true, "Param 'candidate' has unexpected type={}; candidate={}", candidate.getType(), candidate);
				return null;
		}
	}
	
	public void onCandidateDelete(@NonNull final TableRecordReference recordReference)
	{
		candidateRepository.deleteForReference(recordReference);
	}
}
