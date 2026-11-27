package de.metas.frontend_testing.masterdata.hu;

import com.google.common.collect.ImmutableList;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.gs1.ean13.EAN13;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPackingInstructionsRequest
{
	//
	// CU (VHU)
	boolean cu;

	//
	// TU
	@Nullable Identifier tu;
	@Nullable Identifier product;
	@Nullable BigDecimal qtyCUsPerTU;
	@Nullable EAN13 tu_ean;

	/**
	 * When true, the TU's material item gets, next to the regular capacity row for {@link #product}, a second
	 * {@code M_HU_PI_Item_Product} with no product ({@code M_Product_ID} null) and {@code IsAllowAnyProduct=N}.
	 * The {@code M_HU_PI_Item_Product} model interceptor accepts such a row, and it occurs in real master data.
	 * Requires {@link #tu}.
	 */
	boolean tuProductlessItemProduct;

	//
	// LU
	@Nullable Identifier lu;
	int qtyTUsPerLU;

	/**
	 * Optional product of the LU->TU item ({@link #qtyTUsPerLU}): the item is then bound to that product
	 * ({@code M_HU_PI_Item.M_Product_ID}) instead of being generic, so the LU has no generic item. Requires {@link #lu}.
	 */
	@Nullable Identifier luProduct;

	/**
	 * When true, an {@code M_HU_PI_Attribute} for the {@code SSCC18} attribute is added to the LU packing instruction version,
	 * so that LUs built from it carry an SSCC18. Requires {@link #lu}.
	 */
	boolean luSSCC18;

	/**
	 * Optional extra LU->TU items of the same LU packing instruction, each bound to a product
	 * ({@code M_HU_PI_Item.M_Product_ID}), e.g. {@code additionalLUItems: [{product: "XY", qtyTUsPerLU: 40}]}.
	 * They include the same TU packing instruction as the generic item ({@link #qtyTUsPerLU}). Requires {@link #lu}.
	 */
	@Builder.Default
	List<AdditionalLUItem> additionalLUItems = ImmutableList.of();

	//
	// GRAI mapping
	/**
	 * When true, a random canonical GRAI is generated and an {@code M_HU_PI_GRAI} row is created
	 * mapping that GRAI's (companyPrefix, assetType) to the TU packing instruction created by this command.
	 * The generated scannable GRAI is returned via {@link JsonPackingInstructionsResponse#getGrai()}.
	 */
	boolean graiMapping;

	/**
	 * Optional fixed override for the generated GRAI's {@code (companyPrefix, assetType)} pair — e.g. to build
	 * a Migros returnable-asset GRAI ({@code companyPrefix=7613204, assetType=00307}, see
	 * {@code de.metas.handlingunits.grai.DummyGRAITemplate}) mapped to this TU for the PO-reference-gate E2E
	 * scenarios, or a company prefix starting with {@code 8003} (the GS1 AI for GRAI) for the canonical-first
	 * parsing E2E. Only used when {@link #graiMapping} is {@code true}; when {@code null} a random pair is
	 * generated (the pre-existing behaviour). Set both or neither.
	 */
	@Nullable String graiCompanyPrefix;
	@Nullable String graiAssetType;

	public Identifier getTuNotNull() {return Check.assumeNotNull(tu, "tu must be set");}

	public Identifier getProductNotNull() {return Check.assumeNotNull(product, "product must be set");}

	public BigDecimal getQtyCUsPerTUNotNull() {return Check.assumeNotNull(qtyCUsPerTU, "qtyCUsPerTU must be set");}

	@Value
	@Builder
	@Jacksonized
	public static class AdditionalLUItem
	{
		@NonNull Identifier product;
		int qtyTUsPerLU;
	}
}
