package de.metas.frontend_testing.masterdata.sales_order;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonSalesOrderCreateResponse
{
	@NonNull String id;
	@NonNull String documentNo;
	/** {@code ExternalSystem_ID} of {@code externalSystem} -- the value the launcher's facet ids are built from. */
	@Nullable String externalSystemId;
	/** {@code ExternalSystem.Name} of {@code externalSystem}, so a test asserts against masterdata rather than a hard-coded label. */
	@Nullable String externalSystemName;
}
