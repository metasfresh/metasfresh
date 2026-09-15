package de.metas.frontend_testing.masterdata.hu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonCreateHURequest
{
	@Nullable Identifier product;
	@Nullable Identifier warehouse;
	/** Optional: when set, the HU is created on this specific locator (must belong to {@link #warehouse}); when null, the warehouse's default locator is used. */
	@Nullable Identifier locator;

	/**
	 * When {@link #packingInstructions} has a FINITE capacity, an explicit {@code qty} here means:
	 * load this total across the LU's TUs, under-filling the last TU — forcing it to be created as a
	 * real, individually addressable (non-aggregate) HU instead of coalescing into an aggregate row.
	 * When absent, the total is derived from the packing instructions (exact fill).
	 */
	@Nullable BigDecimal qty;
	@Nullable Identifier packingInstructions;
	@Nullable Boolean generateHUQRCode;
	@Nullable Boolean generateHUQRCodesForAllTUs;
	@Nullable Integer splitOutTUsCountAfterQRCodes;
	@Nullable Boolean sourceHU;
	@Nullable BigDecimal weightNet;
	@Nullable String lotNo;
	@Nullable String bestBeforeDate;
	@Nullable String externalBarcode;

	/**
	 * Additional products to stock onto the SAME HU created by this request, on top of {@link #product}/{@link #qty}.
	 * Each entry is loaded onto the already-created HU (not into a separate one), so the resulting {@code M_HU}
	 * ends up with one {@code M_HU_Storage} row per distinct product.
	 */
	@Nullable List<AdditionalProduct> additionalProducts;

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

	@JsonIgnore
	public boolean isSourceHU() { return sourceHU != null && sourceHU;}

	@Value
	@Builder
	@Jacksonized
	public static class AdditionalProduct
	{
		@NonNull Identifier product;
		@NonNull BigDecimal qty;
	}
}
