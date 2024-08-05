package de.metas.material.event.ddordercandidate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.commons.EventDescriptor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class DDOrderCandidateUpdatedEvent extends AbstractDDOrderCandidateEvent
{
	public static final String TYPE = "DDOrderCandidateUpdatedEvent";

	@JsonCreator
	@Builder
	public DDOrderCandidateUpdatedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("ddOrderCandidate") @NonNull final DDOrderCandidateData ddOrderCandidate)
	{
		super(eventDescriptor, ddOrderCandidate, null);
	}

	public static DDOrderCandidateUpdatedEvent of(@NonNull final DDOrderCandidateData data)
	{
		return builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(data.getClientAndOrgId()))
				.ddOrderCandidate(data)
				.build();
	}
}
