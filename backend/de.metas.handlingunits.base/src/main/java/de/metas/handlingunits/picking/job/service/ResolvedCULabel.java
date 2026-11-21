package de.metas.handlingunits.picking.job.service;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

/**
 * A CU label that was read with the picking line's effective format and checked against the line.
 */
@Value
@Builder
public class ResolvedCULabel
{
	@NonNull ProductId productId;
	@Nullable String lotNo;
	@Nullable LocalDate bestBeforeDate;
	/** only set in {@code SCAN_EACH_CU} mode */
	@Nullable String packId;
}
