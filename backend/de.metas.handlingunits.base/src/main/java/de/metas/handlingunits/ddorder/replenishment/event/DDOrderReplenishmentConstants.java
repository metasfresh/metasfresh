package de.metas.handlingunits.picking.dd_order.reconcile.event;

import de.metas.event.Topic;
import lombok.experimental.UtilityClass;

/**
 * Dedicated event topic for DD_Order picking reconcile events.
 * One event per distinct {@link de.metas.inout.ShipmentScheduleId} is published to this topic
 * after the originating transaction commits.
 */
@UtilityClass
public class DDOrderReconciliationTopic
{
	public static final Topic TOPIC = Topic.distributedAndAsync("de.metas.distribution.ddorder.picking.reconcile");
}
