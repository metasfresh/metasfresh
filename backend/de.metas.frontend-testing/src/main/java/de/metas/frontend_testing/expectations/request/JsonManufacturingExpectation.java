package de.metas.frontend_testing.expectations.request;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonManufacturingExpectation
{
	@Nullable List<ReceivedHU> receivedHUs;

	//
 	//
 	//

	@Value
	@Builder
	@Jacksonized
	public static class ReceivedHU
	{
		/**
		 * Binds the received HU itself, whatever its structure. Needed for a bare VHU/CU receipt, which has
		 * neither an LU nor a TU parent — bind it here and assert its {@code huType} under {@code hus}.
		 */
		@Nullable Identifier hu;
		@Nullable Identifier lu;
		@Nullable Identifier tu;
		@Nullable QtyAndUOMString qty;
	}
}

