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
		/** The received HU itself — the only handle for a bare VHU/CU, which has neither an LU nor a TU parent. */
		@Nullable Identifier hu;
		@Nullable Identifier lu;
		@Nullable Identifier tu;
		@Nullable QtyAndUOMString qty;
	}
}

