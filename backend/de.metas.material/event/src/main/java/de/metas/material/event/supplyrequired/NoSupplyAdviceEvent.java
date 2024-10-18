package de.metas.material.event.supplyrequired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class NoSupplyAdviceEvent implements MaterialEvent
{
	public static final String TYPE = "NoSupplyAdviceEvent";

	@NonNull SupplyRequiredDescriptor supplyRequiredDescriptor;

	public static NoSupplyAdviceEvent of(@NonNull SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		return NoSupplyAdviceEvent.builder().supplyRequiredDescriptor(supplyRequiredDescriptor).build();
	}

	@JsonIgnore
	@Override
	public EventDescriptor getEventDescriptor()
	{
		return supplyRequiredDescriptor.getEventDescriptor();
	}

	@JsonIgnore
	public int getSupplyCandidateId() {return supplyRequiredDescriptor.getSupplyCandidateId();}
}
