package de.metas.material.event.ddordercandidate;

import de.metas.material.event.commons.EventDescriptor;
import org.junit.jupiter.api.Test;

import static de.metas.material.event.EventTestHelper.assertEventEqualAfterSerializeDeserialize;

class DDOrderCandidateCreatedEventTest
{
	@Test
	void testSerializeDeserialize()
	{
		final DDOrderCandidateData ddOrderCandidate = DDOrderCandidateAdvisedEventTest.newDDOrderCandidateData();
		final EventDescriptor eventDescriptor = EventDescriptor.ofClientOrgAndTraceId(ddOrderCandidate.getClientAndOrgId(), "traceId");
		final DDOrderCandidateCreatedEvent event = DDOrderCandidateCreatedEvent.builder()
				.eventDescriptor(eventDescriptor)
				.ddOrderCandidate(ddOrderCandidate)
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}
}