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
		@Nullable Identifier tu;
		@Nullable QtyAndUOMString qty;
	}
}

