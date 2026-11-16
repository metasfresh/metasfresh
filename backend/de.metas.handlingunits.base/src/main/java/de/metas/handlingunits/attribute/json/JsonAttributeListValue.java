package de.metas.handlingunits.attribute.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * One allowed value of a {@link JsonAttributeValueType#LIST} attribute (i.e. one {@code M_AttributeValue}), offered
 * to the mobile-UI operator as a dropdown option.
 */
@Value
@Builder
@Jacksonized
public class JsonAttributeListValue
{
	@NonNull String value;
	@NonNull String caption;
}
