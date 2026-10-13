package de.metas.frontend_testing.masterdata.attribute;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.mm.attributes.AttributeId;

@Value
@Builder
@Jacksonized
public class JsonCreateAttributeResponse
{
	@NonNull AttributeId id;

	/** {@code M_Attribute.Value} - the {@code AttributeCode} to reference this attribute elsewhere (e.g. mfg editable-attributes list). */
	@NonNull String attributeValue;
}
