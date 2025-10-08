package org.adempiere.mm.attributes.api;

import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueType;

import javax.annotation.Nullable;

@Value
@Builder
public class Attribute
{
	@NonNull AttributeId attributeId;
	@NonNull AttributeCode attributeCode;
	@NonNull AttributeValueType valueType;
	@NonNull ITranslatableString displayName;
	@Nullable ITranslatableString description;
}
