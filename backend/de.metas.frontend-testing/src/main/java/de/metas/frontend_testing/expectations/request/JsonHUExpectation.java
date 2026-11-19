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
	/** Attribute codes that must exist on the HU and have a non-blank value (for values not known upfront, e.g. a generated SSCC18). */
	@Nullable List<String> attributesNotEmpty;
	/** Only {@code true} is supported ({@code false} is rejected: it would pass before the async shipment generation ran): the HU has active shipment-schedule allocations (by {@code M_LU_HU_ID}), all pointing to a completed shipment ({@code M_InOut.DocStatus=CO}). Waits for the async shipment generation. */
	@Nullable Boolean isShipmentCompleted;
	/** Number of distinct TUs ({@code M_ShipmentSchedule_QtyPicked.M_TU_HU_ID}) of this LU that have a completed shipment; also waits for the async shipment generation. */
	@Nullable Integer shippedTUCount;
	/** Number of {@code AD_Archive} records of this HU ({@code AD_Table_ID=M_HU}, {@code Record_ID}=HU), e.g. printed labels. */
	@Nullable Integer archiveCount;
	/** Optional filter of {@link #archiveCount}: only {@code AD_Archive} records of this {@code AD_Process_ID} (e.g. the HU label process) are counted. Requires {@code archiveCount}. */
	@Nullable Integer archiveProcessId;

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
