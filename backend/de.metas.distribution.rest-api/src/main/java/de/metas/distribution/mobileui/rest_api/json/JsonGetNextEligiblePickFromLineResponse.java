package de.metas.distribution.mobileui.rest_api.json;

import de.metas.distribution.mobileui.job.model.DistributionJobLineId;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonGetNextEligiblePickFromLineResponse
{
	@Nullable DistributionJobLineId lineId;

	/** Qty the scanned HU actually holds of the line's product. The mobile UI caps the proposed move-qty to
	 *  {@code min(qtyAvailable, line-remaining)} so scanning a partially-filling HU proposes that HU's content
	 *  rather than the whole outstanding line qty. Null when it cannot be determined. */
	@Nullable BigDecimal qtyAvailable;
}
