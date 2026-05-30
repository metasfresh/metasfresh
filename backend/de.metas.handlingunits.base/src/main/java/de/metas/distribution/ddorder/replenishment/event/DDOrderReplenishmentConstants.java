package de.metas.distribution.ddorder.replenishment.event;

import de.metas.event.Topic;
import lombok.experimental.UtilityClass;

/**
 * Dedicated event topic for DD_Order picking replenishment events.
 * One event per distinct {@link de.metas.inout.ShipmentScheduleId} is published to this topic
 * after the originating transaction commits.
 */
@UtilityClass
public class DDOrderReplenishmentConstants
{
	public static final Topic TOPIC = Topic.distributedAndAsync("de.metas.distribution.ddorder.picking.replenishment");
}
