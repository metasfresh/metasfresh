package de.metas.ui.web.address.json;

import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * @implNote IMPORTANT: Keep in sync with {@link de.metas.ui.web.pattribute.json.JSONCompleteASIRequest} because on frontend side they are handled by the same code.
 */
@Value
@Builder
@Jacksonized
public class JSONCompleteAddressRequest
{
	/** Events to process before completing the ASI */
	List<JSONDocumentChangedEvent> events;
}
