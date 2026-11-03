package de.metas.frontend_testing.masterdata.receipt;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonReceiptCreateRequest
{
	@NonNull Identifier purchaseOrder;

	/**
	 * Optional identifier under which the receipt's single top-level HU (the LU if there is one, else the TU, else the VHU)
	 * is registered in the masterdata context, so that later steps (e.g. a manufacturing order's source HU) can refer to it.
	 */
	@Nullable Identifier receivedHU;
}
