package de.metas.frontend_testing.pickingslot;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/** {@code M_PickingSlot_ID}s of the picking slots to empty, see {@link EmptyPickingSlotsCommand}. */
@Value
@Builder
@JsonDeserialize(builder = JsonEmptyPickingSlotsRequest.JsonEmptyPickingSlotsRequestBuilder.class)
public class JsonEmptyPickingSlotsRequest
{
	@NonNull @Singular List<Integer> pickingSlotIds;

	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonEmptyPickingSlotsRequestBuilder {}
}
