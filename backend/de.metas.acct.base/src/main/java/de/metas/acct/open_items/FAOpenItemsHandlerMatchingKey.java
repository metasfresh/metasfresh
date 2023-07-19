package de.metas.acct.open_items;

import de.metas.acct.AccountConceptualName;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class FAOpenItemsHandlerMatchingKey
{
	@NonNull AccountConceptualName accountConceptualName;
	@NonNull String docTableName;
}
