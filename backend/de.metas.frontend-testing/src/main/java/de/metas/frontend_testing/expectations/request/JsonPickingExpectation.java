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
	@Nullable Map<String, JsonShipmentScheduleExpectation> shipmentSchedules;
}
