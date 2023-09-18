package de.metas.acct.accounts;

import de.metas.acct.api.ValidCombinationQuery;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Value
@Builder
@Jacksonized
public class ValidCombinationUpdateEvent
{
	@NonNull Set<ValidCombinationQuery> multiQuery;
}
