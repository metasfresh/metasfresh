package de.metas.cucumber.stepdefs.match_po;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.order.MatchPOId;
import org.compiere.model.I_M_MatchPO;

public class M_MatchPO_StepDefData extends StepDefData<I_M_MatchPO>
		implements StepDefDataGetIdAware<MatchPOId, I_M_MatchPO>
{
	public M_MatchPO_StepDefData() {super(I_M_MatchPO.class);}

	@Override
	public MatchPOId extractIdFromRecord(final I_M_MatchPO record) {return MatchPOId.ofRepoId(record.getM_MatchPO_ID());}
}
