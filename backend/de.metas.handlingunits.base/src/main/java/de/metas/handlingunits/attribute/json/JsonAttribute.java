package de.metas.handlingunits.attribute.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.mm.attributes.AttributeCode;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Generic mobile-UI JSON view of one attribute (code, caption, value type, current value and - for a
 * {@link JsonAttributeValueType#LIST} attribute - its allowed values).
 * <p>
 * Lifted (issue #31771 Task 6) from {@code de.metas.inventory.mobileui.rest_api.json.JsonAttribute} into this
 * shared module so it can be reused across mobile-UI apps (Inventory, Manufacturing, ...) that already depend on
 * {@code de.metas.handlingunits.base}, instead of creating a dependency between sibling app modules. The former
 * {@code of(Attribute, ...)}/{@code ofList(...)} factories were inventory-app-specific (they took the inventory
 * module's own {@code Attribute}/{@code Attributes} domain wrapper) and stayed behind in that module as a small
 * local adapter; this class now carries only the generic, dependency-free shape plus the new {@link #listValues}.
 */
@Value
@Builder
@Jacksonized
public class JsonAttribute
{
	@NonNull AttributeCode code;
	@NonNull String caption;
	@NonNull JsonAttributeValueType valueType;
	@Nullable Object value;
	@Nullable String valueFormatted;

	/** Allowed values, populated only when {@link #valueType} is {@link JsonAttributeValueType#LIST}. */
	@Nullable List<JsonAttributeListValue> listValues;
}
