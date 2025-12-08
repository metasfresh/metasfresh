package de.metas.ad_reference;

import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public
class ADRefListItemCreateRequest
{
	@NonNull
	ReferenceId referenceId;

	@NonNull
	String value;

	@NonNull
	ITranslatableString name;
}
