package de.metas.frontend_testing.expectations.request;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.handlingunits.QtyTU;
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
	@Nullable Identifier bpartner;
	@Nullable Identifier bpartnerLocation;
	@Nullable String huStatus;
	@Nullable Map<String, String> storages;
	@Nullable Map<String, String> attributes;
	@Nullable List<JsonHUExpectation> tus;
	@Nullable List<CU> cus;
	@Nullable Boolean isAggregatedTU;
	@Nullable QtyTU qtyTUs;
	/**
	 * Expected TU count of the LU packing-instruction item (M_HU_PI_Item.Qty) via which this TU is attached to its LU,
	 * e.g. 40 if it is attached via the product-specific item, 30 if via the generic one.
	 */
	@Nullable Integer parentPIItemQtyTUs;
	
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
