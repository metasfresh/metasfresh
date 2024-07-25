package de.metas.cucumber.stepdefs.ddordercandidate;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;

public class DD_Order_Candidate_StepDefData extends StepDefData<DDOrderCandidate>
		implements StepDefDataGetIdAware<DDOrderCandidateId, DDOrderCandidate>
{
	public DD_Order_Candidate_StepDefData()
	{
		super(DDOrderCandidate.class);
	}

	@Override
	public DDOrderCandidateId extractIdFromRecord(final DDOrderCandidate record) {return record.getIdNotNull();}
}
