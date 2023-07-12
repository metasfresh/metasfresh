package de.metas.acct.open_items;

import de.metas.acct.AccountConceptualName;
import lombok.NonNull;

import java.util.Optional;
import java.util.Set;

public interface FAOpenItemsHandler
{
	@NonNull Set<AccountConceptualName> getHandledAccountConceptualNames();

	Optional<FAOpenItemTrxInfo> computeTrxInfo(FAOpenItemTrxInfoComputeRequest request);
}
