package de.metas.frontend_testing.masterdata.shipment;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonGenerateShipmentsResponse
{
	/**
	 * The {@code M_InOut_ID}s of the shipments NEWLY created by this call (as strings) — i.e. the order's
	 * shipments that did not exist before generation. Empty when nothing could be shipped (e.g. the picked
	 * qty did not survive a prior reverse — the "can't recreate shipment after void" defect).
	 */
	@NonNull ImmutableList<String> newShipmentIds;

	/** Convenience: {@code newShipmentIds.size()}. {@code 0} == no shipment could be recreated. */
	int newShipmentCount;
}
