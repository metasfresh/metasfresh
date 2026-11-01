package de.metas.distribution.ddorder.replenishment.event;

import de.metas.distribution.ddorder.replenishment.DDOrderReplenishmentGroupKey;
import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.PickingJobScheduleId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

/**
 * Points the reconcile at the product group that needs recomputing. A pointer, never a snapshot: the handler
 * re-reads the group's live state.
 */
@Value
@Builder(toBuilder = true)
public class DDOrderReplenishmentRequest
{
	@NonNull DDOrderReplenishmentGroupKey groupKey;
	@NonNull ClientAndOrgId clientAndOrgId;

	/**
	 * One of the triggering assignments (arbitrary once requests dedup); excluded from equals/hashCode since the group key is the identity.
	 */
	@NonNull @EqualsAndHashCode.Exclude PickingJobScheduleId triggeredBy;
}
