package de.metas.frontend_testing.masterdata.hu_package;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPackageRequest
{
	/** Identifier of an HU previously created under {@code handlingUnits}. */
	@NonNull Identifier hu;

	/** Identifier of a shipper previously created under {@code shippers}; {@code M_Package.M_Shipper_ID} is mandatory. */
	@NonNull Identifier shipper;
}
