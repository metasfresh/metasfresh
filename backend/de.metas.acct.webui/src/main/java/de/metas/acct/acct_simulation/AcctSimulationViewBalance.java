package de.metas.acct.acct_simulation;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AcctSimulationViewBalance
{
	@NonNull AmountBalance inDocumentCurrency;
	@NonNull AmountBalance inLocalCurrency;
}
