package de.metas.frontend_testing.masterdata.hu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonCreateHURequest
{
	@Nullable Identifier product;
	@Nullable Identifier warehouse;
	@Nullable BigDecimal qty;
	@Nullable Identifier packingInstructions;
	@Nullable Boolean generateHUQRCode;
	@Nullable Boolean sourceHU;
	@Nullable BigDecimal weightNet;
	@Nullable String lotNo;
	@Nullable String bestBeforeDate;
	@Nullable String externalBarcode;

	/**
	 * When set, the created HU is added to the picking slot queue identified by this identifier.
	 * This bypasses the mobile picking app and therefore no GRAI is stamped on the HU —
	 * use this to simulate the "cross-dock" scenario where a TU enters a picking slot without
	 * passing through the GRAI-scanning mobile pick, so the HU Consolidation GRAI gate fires.
	 */
	@Nullable Identifier pickingSlot;

	@JsonIgnore
	public boolean isGenerateHUQRCode() {return generateHUQRCode != null ? generateHUQRCode : true;}

	@JsonIgnore
	public boolean isSourceHU() { return sourceHU != null && sourceHU;}
}
