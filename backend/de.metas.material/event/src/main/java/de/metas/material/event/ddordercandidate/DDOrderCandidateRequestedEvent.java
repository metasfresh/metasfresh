package de.metas.material.event.ddordercandidate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class DDOrderCandidateRequestedEvent implements MaterialEvent
{
	public static final String TYPE = "DDOrderCandidateRequestedEvent";

	@NonNull EventDescriptor eventDescriptor;
	@NonNull Instant dateOrdered;
	@NonNull DDOrderCandidateData ddOrderCandidateData;

	@Builder
	@Jacksonized
	private DDOrderCandidateRequestedEvent(
			@NonNull final EventDescriptor eventDescriptor,
			@NonNull final Instant dateOrdered,
			@NonNull final DDOrderCandidateData ddOrderCandidateData)
	{
		ddOrderCandidateData.assertMaterialDispoGroupIdIsSet();

		this.eventDescriptor = eventDescriptor;
		this.dateOrdered = dateOrdered;
		this.ddOrderCandidateData = ddOrderCandidateData;
	}
}
