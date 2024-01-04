package de.metas.ad_reference;

import de.metas.i18n.ITranslatableString;
import de.metas.util.ColorId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class ADRefListItem
{
	@NonNull ReferenceId referenceId;
	@NonNull ADRefListId refListId;
	@NonNull String value;
	@Nullable String valueName;
	@NonNull ITranslatableString name;
	@Nullable ITranslatableString description;
	@Nullable
	ColorId colorId;
}
