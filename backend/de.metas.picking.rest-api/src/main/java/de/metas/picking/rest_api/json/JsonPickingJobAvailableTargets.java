package de.metas.picking.rest_api.json;

import de.metas.handlingunits.grai.GRAI;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonPickingJobAvailableTargets
{
	@NonNull @Singular List<JsonLUPickingTarget> targets;
	@NonNull @Singular List<JsonTUPickingTarget> tuTargets;

	/** When {@code true}, the mobile UI may offer the GRAI-scan TU-target flow for this job/line. */
	boolean graiScanEnabled;

	/**
	 * GRAIs already assigned to the line's effective loading unit (from prior picks on this LU). Lets the mobile
	 * capture panel mirror the server-side LU-wide dedupe: a re-scanned GRAI already in this list must not advance
	 * the scan count. Serialized as the canonical GRAI strings (via {@link GRAI}'s {@code @JsonValue}). Empty when
	 * no LU is resolved yet for the line.
	 */
	@NonNull @Singular("existingLuGrai") List<GRAI> existingLuGrais;
}
