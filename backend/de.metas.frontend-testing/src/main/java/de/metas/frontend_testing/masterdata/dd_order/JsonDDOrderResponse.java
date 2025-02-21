package de.metas.frontend_testing.masterdata.dd_order;

import de.metas.frontend_testing.JsonTestId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonDDOrderResponse
{
	@NonNull String documentNo;
	@NonNull String launcherCaption;
	@NonNull JsonTestId launcherTestId;

	@NonNull String warehouseFromFacetId;
	@NonNull String warehouseToFacetId;
}
