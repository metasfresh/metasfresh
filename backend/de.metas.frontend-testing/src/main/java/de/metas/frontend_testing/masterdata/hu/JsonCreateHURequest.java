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
	/**
	 * Partner of the created top-level HU (the LU, or the TU when the packing instructions have no LU). Identifier of a partner created in the same request.
	 */
	@Nullable Identifier bpartner;
	/**
	 * Partner of the HUs included in the created top-level HU (TUs and their VHUs). Absent means no partner ({@code C_BPartner_ID} NULL).
	 * Only applied when {@code bpartner} or {@code tuBPartner} is set.
	 */
	@Nullable Identifier tuBPartner;
	@Nullable Boolean generateHUQRCode;
	@Nullable Boolean sourceHU;
	@Nullable BigDecimal weightNet;
	@Nullable String lotNo;
	@Nullable String bestBeforeDate;
	@Nullable String externalBarcode;

	@JsonIgnore
	public boolean isGenerateHUQRCode() {return generateHUQRCode != null ? generateHUQRCode : true;}

	@JsonIgnore
	public boolean isSourceHU() { return sourceHU != null && sourceHU;}
}
