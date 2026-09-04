package de.metas.frontend_testing.masterdata.hu;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.gs1.ean13.EAN13;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.mm.attributes.AttributeCode;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

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

	//
	// LU
	@Nullable Identifier lu;
	int qtyTUsPerLU;

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
	 * scenarios. Only used when {@link #graiMapping} is {@code true}; when {@code null} a random pair is
	 * generated (the pre-existing behaviour). Set both or neither.
	 */
	@Nullable String graiCompanyPrefix;
	@Nullable String graiAssetType;

	/**
	 * Sets {@code M_HU_PI_Item_Product.IsDefaultForProduct} on the created CU-TU allocation — the
	 * "Standard-Packvorschrift" that gets auto-defaulted onto document lines for this product.
	 * <p>
	 * TU requests only — a {@code cu} request creates no CU-TU allocation, so this has no effect there.
	 */
	boolean isDefaultForProduct;

	/**
	 * Points the product's existing product price(s) on the current price list version at the created
	 * CU-TU allocation, i.e. makes the packing instruction one that a price references. Requires the same
	 * request to give that product a price.
	 * <p>
	 * The link is expressed here rather than on the product request because products are created before
	 * packing instructions ({@code CreateMasterdataCommand}), so at price-creation time the packing
	 * instruction does not exist yet.
	 * <p>
	 * TU requests only — a {@code cu} request creates no CU-TU allocation, so this has no effect there.
	 */
	boolean referencedByProductPrice;

	/**
	 * {@code M_Attribute.Value} codes to declare as a writable {@code M_HU_PI_Attribute} slot on the
	 * created TU's {@code M_HU_PI_Version} (mirrors the cucumber step {@code metasfresh contains
	 * M_HU_PI_Attribute:} / {@code M_HU_PI_Attribute_StepDef}). Required for a generic (non-Lot/
	 * Best-before/Production, non-GRAI) attribute submitted at a mobile receive to actually be
	 * stamped onto the produced HU: the apply path's {@code hasAttribute} guard
	 * ({@code ReceiveGoodsCommand#setSubmittedAttributesForReceivedHUs} /
	 * {@code IHUAttributesBL#updateHUAttributeRecursive}) is gated by the HU's OWN
	 * {@code M_HU_PI_Version}'s {@code M_HU_PI_Attribute} rows, NOT by the product's
	 * {@code M_AttributeSet} membership (that membership only gates what the mobile UI *offers* -
	 * see {@code MaterialReceiptActivityHandler}'s {@code editableAttributes} intersection).
	 * <p>
	 * TU requests only - a {@code cu} request has no {@code M_HU_PI_Version} of its own (uses the
	 * system VIRTUAL PI), so this has no effect there.
	 */
	@Nullable List<AttributeCode> attributes;

	public Identifier getTuNotNull() {return Check.assumeNotNull(tu, "tu must be set");}

	public Identifier getProductNotNull() {return Check.assumeNotNull(product, "product must be set");}

	public BigDecimal getQtyCUsPerTUNotNull() {return Check.assumeNotNull(qtyCUsPerTU, "qtyCUsPerTU must be set");}
}
