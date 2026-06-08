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
	@Nullable String huStatus;
	/**
	 * Expected HU unit type. Pass the DB code (the value of {@code M_HU_PI_Version.HU_UnitType}):
	 * <ul>
	 *   <li>{@code "V"} — Virtual PI / bare VHU/CU ({@code HUType.VirtualPI})</li>
	 *   <li>{@code "TU"} — Transport Unit / box ({@code HUType.TransportUnit})</li>
	 *   <li>{@code "LU"} — Load Unit / pallet ({@code HUType.LoadLogistiqueUnit})</li>
	 * </ul>
	 * Matched via {@code HUType.ofCode(huType)}.
	 */
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
