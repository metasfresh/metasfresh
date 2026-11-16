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
	/**
	 * When {@code true}, assert the HU's consignee was STRIPPED, i.e. {@code C_BPartner_ID <= 0}.
	 */
	@Nullable Boolean consigneeCleared;
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
	/**
	 * Optional identifier to BIND the HU this expectation matches, so a later call can resolve it
	 * (e.g. {@code Backend.getHUQRCodeByIdentifier}). Registered when this expectation is a nested
	 * LU-child TU (see {@code AssertHUExpectationsCommand.assertTUs}); the manufacturing
	 * {@code receivedHUs.tu} binder only walks UPWARD and returns {@code null} from an LU, so this is
	 * the way to bind an LU's inner concrete TU. Reuses the same register-or-verify semantics as
	 * {@code receivedHUs} ({@code MasterdataContext.putSameOrMissingId}): binds if new, asserts equal
	 * if already known. Purely additive — an expectation that omits it is unaffected.
	 */
	@Nullable Identifier tu;
	@Nullable Map<String, String> storages;
	@Nullable Map<String, String> attributes;
	/**
	 * Attribute codes that MUST be ABSENT on this HU (the HU carries no value for each listed code).
	 * <p>
	 * Additive to {@link #attributes} (which asserts a code is present with a given value). This is the
	 * explicit, positive way to assert that a container HU (TU / LU) stays size-/attribute-neutral —
	 * i.e. a mixed-size container is never mislabelled with a single size.
	 * <p>
	 * Note: a {@code null} value inside {@link #attributes} keeps its long-standing "don't assert"
	 * (skip) meaning and is deliberately NOT overloaded to mean "absent"; absence is expressed only
	 * here, so no existing expectation changes behaviour.
	 */
	@Nullable List<String> attributesAbsent;
	@Nullable List<JsonHUExpectation> tus;
	@Nullable List<CU> cus;
	@Nullable Boolean isAggregatedTU;
	@Nullable QtyTU qtyTUs;
	/**
	 * When {@code true}, assert this HU is assigned to a shipment line
	 * ({@code M_InOutLine} of a sales shipment, i.e. {@code M_InOut.IsSOTrx=Y}).
	 * Polled, because shipment generation is async.
	 * When {@code false}, assert this HU is NOT on any sales-shipment line (no poll).
	 */
	@Nullable Boolean shipped;

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
		/**
		 * Attribute codes that MUST be ABSENT on this CU. See {@link JsonHUExpectation#attributesAbsent}.
		 */
		@Nullable List<String> attributesAbsent;
	}
}
