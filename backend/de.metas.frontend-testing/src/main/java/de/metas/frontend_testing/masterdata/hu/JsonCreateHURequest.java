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
	@Nullable Boolean isQrCodeOfAggregatedTU;
	@Nullable Integer splitOutTUsCountAfterQRCodes;
	@Nullable Boolean sourceHU;
	@Nullable BigDecimal weightNet;
	@Nullable String lotNo;
	@Nullable String bestBeforeDate;
	@Nullable String externalBarcode;

	/**
	 * {@code true} = build a single TU of the {@code packingInstructions} without its LU (e.g. a TU whose TU packing is also on a pallet packing instruction).
	 * Default: false
	 */
	@Nullable Boolean tuOnly;

	@JsonIgnore
	public boolean isGenerateHUQRCode() {return generateHUQRCode != null ? generateHUQRCode : true;}

	/**
	 * When {@code true}, generate one active {@code M_HU_QRCode_Assignment} per TU for the created HU and every HU
	 * included under it — the first half of the QR-code "surplus" test setup
	 * (see {@code CreateHUCommand.generateQRCodesForAllTUs} for the full mechanism).
	 */
	@JsonIgnore
	public boolean isGenerateHUQRCodesForAllTUs() {return generateHUQRCodesForAllTUs != null && generateHUQRCodesForAllTUs;}

	/**
	 * After the full-count codes are generated, split this many whole TUs OUT of the aggregate — the non-picking
	 * repack that completes the surplus setup (see {@code CreateHUCommand.splitOutTUsLeavingQRCodesBehind}).
	 * Only meaningful together with {@code generateHUQRCodesForAllTUs=true}.
	 */
	@JsonIgnore
	public int getSplitOutTUsCountAfterQRCodes() {return splitOutTUsCountAfterQRCodes != null ? splitOutTUsCountAfterQRCodes : 0;}

	/** When true, the response's {@code qrCode} is the QR code of the aggregated TU inside the created HU instead of the top-level HU's. */
	@JsonIgnore
	public boolean isQrCodeOfAggregatedTU() {return isQrCodeOfAggregatedTU != null && isQrCodeOfAggregatedTU;}

	@JsonIgnore
	public boolean isTuOnly() {return tuOnly != null && tuOnly;}

	@JsonIgnore
	public boolean isSourceHU() { return sourceHU != null && sourceHU;}
}
