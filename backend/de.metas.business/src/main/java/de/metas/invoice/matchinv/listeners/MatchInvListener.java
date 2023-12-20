package de.metas.invoice.matchinv.listeners;

import de.metas.invoice.matchinv.MatchInv;

import java.util.List;

public interface MatchInvListener
{
	void onAfterCreated(MatchInv matchInv);

	void onAfterDeleted(List<MatchInv> matchInvs);
}
