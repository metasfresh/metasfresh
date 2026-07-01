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
	@Nullable Boolean generateHUQRCodesForAllTUs;
	@Nullable Boolean sourceHU;
	@Nullable BigDecimal weightNet;
	@Nullable String lotNo;
	@Nullable String bestBeforeDate;
	@Nullable String externalBarcode;

	@JsonIgnore
	public boolean isGenerateHUQRCode() {return generateHUQRCode != null ? generateHUQRCode : true;}

	/**
	 * When {@code true}, generate one active {@code M_HU_QRCode_Assignment} per TU for the created HU and every HU
	 * included under it (the full-count QR-code generation that the desktop "Print Labels" / {@code M_HU_Report_QRCode}
	 * process does for an aggregate LU while it stays Active). Used to set up the me03 #30767 "surplus" state:
	 * generate full-count codes on an active aggregate, then pick out a subset of its TUs so the code count exceeds the
	 * current TU count.
	 */
	@JsonIgnore
	public boolean isGenerateHUQRCodesForAllTUs() {return generateHUQRCodesForAllTUs != null && generateHUQRCodesForAllTUs;}

	@JsonIgnore
	public boolean isSourceHU() { return sourceHU != null && sourceHU;}
}
