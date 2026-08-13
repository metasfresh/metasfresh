package de.metas.frontend_testing.masterdata.hu;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.gs1.ean13.EAN13;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
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

	//
	// LU
	@Nullable Identifier lu;
	int qtyTUsPerLU;

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

	public Identifier getTuNotNull() {return Check.assumeNotNull(tu, "tu must be set");}

	public Identifier getProductNotNull() {return Check.assumeNotNull(product, "product must be set");}

	public BigDecimal getQtyCUsPerTUNotNull() {return Check.assumeNotNull(qtyCUsPerTU, "qtyCUsPerTU must be set");}
}
