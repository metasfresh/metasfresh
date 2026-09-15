package de.metas.product;

import de.metas.i18n.ITranslatableString;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class ProductValueAndName
{
	@NonNull String value;
	@NonNull ITranslatableString name;

	public String getNameTrl(final String adLanguage) {return name.translate(adLanguage);}
}
