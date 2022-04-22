package de.metas.ui.web.address.json;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.json.JSONDocumentField;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

/**
 * @implNote IMPORTANT: Keep in sync with {@link de.metas.ui.web.pattribute.json.JSONASIDocument} because on frontend side they are handled by the same code.
 */
@Value
@Builder
@Jacksonized
public class JSONAddressDocument
{
	@NonNull DocumentId id;
	@NonNull JSONAddressLayout layout;
	@NonNull Map<String, JSONDocumentField> fieldsByName;
}
