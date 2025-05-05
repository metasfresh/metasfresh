package de.metas.acct;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

@Value
@Builder
public class GLCategory
{
	@NonNull GLCategoryId id;
	@NonNull String name;
	@NonNull GLCategoryType categoryType;
	boolean isDefault;
	@NonNull ClientId clientId;
}
