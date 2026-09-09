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
	/**
	 * When {@link #packingInstructions} has a FINITE capacity, an explicit {@code qty} here means:
	 * load this total across the LU's TUs, under-filling the last TU — forcing it to be created as a
	 * real, individually addressable (non-aggregate) HU instead of coalescing into an aggregate row.
	 * When absent, the total is derived from the packing instructions (exact fill).
	 */
	@Nullable BigDecimal qty;
	@Nullable Identifier packingInstructions;
	@Nullable Boolean generateHUQRCode;
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

	@Value
	@Builder
	@Jacksonized
	public static class AdditionalProduct
	{
		@NonNull Identifier product;
		@NonNull BigDecimal qty;
	}
}
