package de.metas.material.event.ddordercandidate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.model.I_DD_Order_Candidate;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class DDOrderCandidateRequestedEvent implements MaterialEvent
{
	public static final String TYPE = "DDOrderCandidateRequestedEvent";

	@NonNull EventDescriptor eventDescriptor;
	@NonNull Instant dateOrdered;
	@NonNull DDOrderCandidateData ddOrderCandidateData;
	boolean createDDOrder;

	@Builder
	@Jacksonized
	private DDOrderCandidateRequestedEvent(
			@NonNull final EventDescriptor eventDescriptor,
			@NonNull final Instant dateOrdered,
			@NonNull final DDOrderCandidateData ddOrderCandidateData,
			final boolean createDDOrder)
	{
		ddOrderCandidateData.assertMaterialDispoGroupIdIsSet();

		this.eventDescriptor = eventDescriptor;
		this.dateOrdered = dateOrdered;
		this.ddOrderCandidateData = ddOrderCandidateData;
		this.createDDOrder = createDDOrder;
	}

	@Nullable
	@Override
	public TableRecordReference getSourceTableReference()
	{
		return TableRecordReference.ofNullable(I_DD_Order_Candidate.Table_Name, ddOrderCandidateData.getExitingDDOrderCandidateId());
	}

	@Override
	public String getEventName() {return TYPE;}
}
