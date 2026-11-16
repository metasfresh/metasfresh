package de.metas.handlingunits.picking.job.massprinting;

import de.metas.handlingunits.HuId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Request to scan an LU and mass-print labels for all self-packed products on it.
 * The backend resolves open shipment schedules, creates picking jobs, picks from the LU,
 * and prints one HU label per packed box.
 */
@Value
@Builder
public class MassPrintingScanRequest
{
	/** The scanned LU HU id */
	@NonNull HuId luId;

	/** The picker executing the scan (from REST auth context) */
	@NonNull UserId pickerId;
}
