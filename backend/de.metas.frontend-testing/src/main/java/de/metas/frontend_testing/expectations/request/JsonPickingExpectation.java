package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonPickingExpectation
{
	/** Expected {@code PickingJobDocStatus} constant name (e.g. {@code Drafted}), matched case-insensitively. */
	@Nullable String docStatus;

	@Nullable Map<String, JsonShipmentScheduleExpectation> shipmentSchedules;
}
