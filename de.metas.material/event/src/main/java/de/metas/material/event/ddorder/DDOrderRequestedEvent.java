package de.metas.material.event.ddorder;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DDOrderRequestedEvent implements MaterialEvent
{
	public static final String TYPE = "DDOrderRequestedEvent";

	@NonNull
	EventDescriptor eventDescriptor;

	@JsonProperty
	int groupId;

	@NonNull
	Date dateOrdered;

	@NonNull
	DDOrder ddOrder;
}
