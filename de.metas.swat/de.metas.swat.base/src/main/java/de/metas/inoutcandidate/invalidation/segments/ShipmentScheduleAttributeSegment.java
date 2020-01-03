package de.metas.inoutcandidate.invalidation.segments;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import lombok.NonNull;
import lombok.Value;

@Value
public class ShipmentScheduleAttributeSegment
{
	public static ShipmentScheduleAttributeSegment ofAttributeSetInstanceId(@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		final AttributeId attributeId = null;
		return new ShipmentScheduleAttributeSegment(attributeSetInstanceId, attributeId);
	}

	public static ShipmentScheduleAttributeSegment ofAttributeId(@NonNull final AttributeId attributeId)
	{
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.NONE;
		return new ShipmentScheduleAttributeSegment(attributeSetInstanceId, attributeId);
	}

	public static ShipmentScheduleAttributeSegment of(final AttributeSetInstanceId attributeSetInstanceId, final AttributeId attributeId)
	{
		return new ShipmentScheduleAttributeSegment(attributeSetInstanceId, attributeId);
	}

	AttributeSetInstanceId attributeSetInstanceId;
	AttributeId attributeId;

	private ShipmentScheduleAttributeSegment(
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@Nullable final AttributeId attributeId)
	{
		this.attributeSetInstanceId = attributeSetInstanceId != null ? attributeSetInstanceId : AttributeSetInstanceId.NONE;
		this.attributeId = attributeId;
	}
}
