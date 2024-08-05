package de.metas.material.event.ddordercandidate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.commons.EventDescriptor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class DDOrderCandidateCreatedEvent extends AbstractDDOrderCandidateEvent
{
	public static final String TYPE = "DDOrderCandidateCreatedEvent";

	@JsonCreator
	@Builder
	public DDOrderCandidateCreatedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("ddOrderCandidate") @NonNull final DDOrderCandidateData ddOrderCandidate)
	{
		super(eventDescriptor, ddOrderCandidate, null);
	}

	public static DDOrderCandidateCreatedEvent of(@NonNull final DDOrderCandidateData data)
	{
		return builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(data.getClientAndOrgId()))
				.ddOrderCandidate(data)
				.build();
	}

	public static Optional<DDOrderCandidateCreatedEvent> castIfApplies(@Nullable final AbstractDDOrderCandidateEvent event)
	{
		return event instanceof DDOrderCandidateCreatedEvent
				? Optional.of((DDOrderCandidateCreatedEvent)event)
				: Optional.empty();
	}
}
