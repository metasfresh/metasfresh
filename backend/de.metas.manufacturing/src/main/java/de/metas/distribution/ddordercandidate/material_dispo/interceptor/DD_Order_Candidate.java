package de.metas.distribution.ddordercandidate.material_dispo.interceptor;

import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateRepository;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.ddordercandidate.DDOrderCandidateCreatedEvent;
import de.metas.material.event.ddordercandidate.DDOrderCandidateData;
import de.metas.material.event.ddordercandidate.DDOrderCandidateUpdatedEvent;
import de.metas.material.replenish.ReplenishInfoRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_DD_Order_Candidate;
import org.springframework.stereotype.Component;

@Interceptor(I_DD_Order_Candidate.class)
@Component
@RequiredArgsConstructor
public class DD_Order_Candidate
{
	@NonNull private final DDOrderCandidateRepository ddOrderCandidateRepository;
	@NonNull private final ReplenishInfoRepository replenishInfoRepository;
	@NonNull private final PostMaterialEventService materialEventService;

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void afterNew(final I_DD_Order_Candidate record)
	{
		final DDOrderCandidateData data = toDDOrderCandidateData(record);
		materialEventService.postEventAfterNextCommit(DDOrderCandidateCreatedEvent.of(data));
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE })
	public void afterChange(final I_DD_Order_Candidate record)
	{
		final DDOrderCandidateData data = toDDOrderCandidateData(record);
		materialEventService.postEventAfterNextCommit(DDOrderCandidateUpdatedEvent.of(data));
	}

	private DDOrderCandidateData toDDOrderCandidateData(final I_DD_Order_Candidate record)
	{
		final DDOrderCandidate candidate = DDOrderCandidateRepository.fromRecord(record);

		return candidate.toDDOrderCandidateData()
				.fromWarehouseMinMaxDescriptor(getFromWarehouseMinMaxDescriptor(candidate))
				.build();
	}

	private MinMaxDescriptor getFromWarehouseMinMaxDescriptor(final DDOrderCandidate candidate)
	{
		return replenishInfoRepository.getBy(candidate.getSourceWarehouseId(), candidate.getProductId()).toMinMaxDescriptor();
	}
}
