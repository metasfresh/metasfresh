package de.metas.frontend_testing.masterdata.hu_package;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPackageResponse
{
	int packageId;
	@NonNull String documentNo;
}
