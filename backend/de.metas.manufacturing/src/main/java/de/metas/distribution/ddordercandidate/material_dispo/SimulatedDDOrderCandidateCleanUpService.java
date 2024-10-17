package de.metas.distribution.ddordercandidate.material_dispo;

import de.metas.distribution.ddordercandidate.DDOrderCandidateQuery;
import de.metas.distribution.ddordercandidate.DDOrderCandidateRepository;
import de.metas.material.commons.disposition.SimulatedCleanUpService;
import de.metas.order.OrderLineId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimulatedDDOrderCandidateCleanUpService implements SimulatedCleanUpService
{
	@NonNull private final DDOrderCandidateRepository ddOrderCandidateRepository;

	@Override
	public void cleanUpSimulated()
	{
		ddOrderCandidateRepository.delete(newQuery().build());
	}

	public void deleteSimulatedBySalesOrderLineId(@NonNull final OrderLineId salesOrderLineId)
	{
		ddOrderCandidateRepository.delete(newQuery().salesOrderLineId(salesOrderLineId).build());
	}

	private static DDOrderCandidateQuery.DDOrderCandidateQueryBuilder newQuery()
	{
		return DDOrderCandidateQuery.builder()
				.isSimulated(true);
	}
}
