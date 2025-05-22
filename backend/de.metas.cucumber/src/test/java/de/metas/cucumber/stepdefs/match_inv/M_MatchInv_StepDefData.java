package de.metas.cucumber.stepdefs.match_inv;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.MatchInvId;

public class M_MatchInv_StepDefData extends StepDefData<MatchInv>
		implements StepDefDataGetIdAware<MatchInvId, MatchInv>
{
	public M_MatchInv_StepDefData() {super(MatchInv.class);}

	@Override
	public MatchInvId extractIdFromRecord(final MatchInv matchInv) {return matchInv.getId();}
}
