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
	 * One of the triggering assignments, not the owner - when N requests dedup to one, the surviving stamp is
	 * arbitrary. Payload only: it anchors the resulting AD_EventLog record so an operator can zoom from the
	 * assignment to its reconcile log.
	 *
	 * <p>Excluded from equals/hashCode on purpose: the group key IS the identity. Without the exclusion, N
	 * requests of the same group would be N distinct requests, and the dedup in
	 * {@code DDOrderPickingReplenishmentService.scheduleReconcileAfterCommit} (an {@code ImmutableSet}) and
	 * {@code rebuildDrift()} (a {@code .distinct()}) would silently stop collapsing them into one reconcile.
	 */
	@NonNull @EqualsAndHashCode.Exclude PickingJobScheduleId triggeredBy;
}
