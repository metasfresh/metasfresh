package de.metas.frontend_testing.masterdata.hu_package;

import de.metas.shipping.mpackage.PackageId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPackageResponse
{
	@NonNull PackageId packageId;
	@NonNull String documentNo;
}
