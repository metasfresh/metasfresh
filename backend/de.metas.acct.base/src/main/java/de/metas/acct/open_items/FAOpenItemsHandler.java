package de.metas.acct.open_items;

import de.metas.acct.AccountConceptualName;
import lombok.NonNull;

import java.util.Optional;

public interface FAOpenItemsHandler
{
	@NonNull AccountConceptualName getHandledAccountConceptualName();

	Optional<FAOpenItemTrxInfo> computeTrxInfo(FAOpenItemTrxInfoComputeRequest request);
}
