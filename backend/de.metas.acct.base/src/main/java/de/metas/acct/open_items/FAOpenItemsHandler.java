package de.metas.acct.open_items;

import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import lombok.NonNull;

import java.util.Optional;
import java.util.Set;

public interface FAOpenItemsHandler
{
	@NonNull Set<FAOpenItemsHandlerMatchingKey> getMatchers();

	Optional<FAOpenItemTrxInfo> computeTrxInfo(FAOpenItemTrxInfoComputeRequest request);

	void onGLJournalLineCompleted(SAPGLJournalLine line);

	void onGLJournalLineReactivated(SAPGLJournalLine line);
}
