package de.metas.product.hazard_symbol;

import de.metas.i18n.ITranslatableString;
import de.metas.image.AdImageId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class HazardSymbol
{
	@NonNull HazardSymbolId id;
	@NonNull String value;
	@NonNull ITranslatableString name;
	@Nullable AdImageId imageId;
}
