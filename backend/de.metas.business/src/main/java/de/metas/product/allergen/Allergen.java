package de.metas.product.allergen;

import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class Allergen
{
	@NonNull AllergenId id;
	@NonNull ITranslatableString name;
	@Nullable String color;
}
