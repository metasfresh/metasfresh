package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Expectation for one compensation group (C_Order_CompensationGroup) of a sales order.
 * The ids are record ids, because the contract term and the schema are typically created in the UI, not as masterdata.
 */
@Value
@Builder
@Jacksonized
public class JsonOrderCompensationGroupExpectation
{
	/**
	 * Expected C_Order_CompensationGroup.C_Flatrate_Term_ID. When null, not asserted.
	 */
	@Nullable Integer flatrateTermId;

	/**
	 * Expected C_Order_CompensationGroup.C_CompensationGroup_Schema_ID. When null, not asserted.
	 */
	@Nullable Integer compensationGroupSchemaId;
}
