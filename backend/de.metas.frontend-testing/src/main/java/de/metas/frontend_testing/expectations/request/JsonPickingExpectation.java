package de.metas.frontend_testing.expectations.request;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonPickingExpectation
{
	//
 	// Match
	@NonNull Identifier pickingJobId;

	//
	// Expect
	@Nullable List<JsonShipmentScheduleExpectation> shipmentSchedules;
}
