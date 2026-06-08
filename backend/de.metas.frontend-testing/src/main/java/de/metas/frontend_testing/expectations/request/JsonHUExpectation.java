package de.metas.frontend_testing.expectations.request;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.generichumodel.HUType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonHUExpectation
{
	@Nullable Identifier warehouse;
	@Nullable Identifier locator;
	@Nullable String huStatus;
	/** Expected HU unit type. Use {@code "VirtualPI"} for bare VHUs/CUs, {@code "TransportUnit"} for TU boxes. */
	@Nullable String huType;
	@Nullable Map<String, String> storages;
	@Nullable Map<String, String> attributes;
	@Nullable List<JsonHUExpectation> tus;
	@Nullable List<CU> cus;
	@Nullable Boolean isAggregatedTU;
	@Nullable QtyTU qtyTUs;
	
	//
 	//
 	//

	@Value
	@Builder
	@Jacksonized
	public static class CU
	{
		@Nullable QtyAndUOMString qty;
		@Nullable Map<String, String> attributes;
	}
}
