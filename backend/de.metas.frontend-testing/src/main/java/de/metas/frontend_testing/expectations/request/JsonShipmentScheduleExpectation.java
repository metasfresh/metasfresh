package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonShipmentScheduleExpectation
{
	@Nullable Boolean isScheduledForPicking;
	@Nullable BigDecimal qtyScheduledForPicking;
	@Nullable List<JsonShipmentScheduleQtyPickedExpectation> qtyPicked;
}
