package de.metas.material.planning.ddordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddordercandidate.DDOrderCandidateAdvisedEvent;
import de.metas.material.event.ddordercandidate.DDOrderCandidateData;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.event.SupplyRequiredAdvisor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DDOrderCandidateAdvisedEventCreator implements SupplyRequiredAdvisor
{
	@NonNull private final DDOrderCandidateDemandMatcher demandMatcher;
	@NonNull private final DDOrderCandidateDataFactory ddOrderCandidateDataFactory;

	public List<DDOrderCandidateAdvisedEvent> createAdvisedEvents(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context)
	{
		if (!demandMatcher.matches(context))
		{
			return ImmutableList.of();
		}

		final ProductPlanning productPlanningData = context.getProductPlanning();

		return ddOrderCandidateDataFactory.create(supplyRequiredDescriptor, context)
				.stream()
				.map(ddOrderCandidate -> toDDOrderCandidateAdvisedEvent(ddOrderCandidate, supplyRequiredDescriptor, productPlanningData))
				.collect(ImmutableList.toImmutableList());
	}

	private static DDOrderCandidateAdvisedEvent toDDOrderCandidateAdvisedEvent(
			@NonNull final DDOrderCandidateData ddOrderCandidate,
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final ProductPlanning productPlanningData)
	{
		return DDOrderCandidateAdvisedEvent.builder()
				.eventDescriptor(supplyRequiredDescriptor.newEventDescriptor())
				.supplyRequiredDescriptor(supplyRequiredDescriptor)
				.ddOrderCandidate(ddOrderCandidate)
				.advisedToCreateDDOrder(productPlanningData.isCreatePlan())
				.pickIfFeasible(productPlanningData.isPickDirectlyIfFeasible())
				.build();
	}

}
