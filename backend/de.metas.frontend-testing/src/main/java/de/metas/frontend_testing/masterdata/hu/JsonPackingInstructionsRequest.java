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

	public Identifier getTuNotNull() {return Check.assumeNotNull(tu, "tu must be set");}

	public Identifier getProductNotNull() {return Check.assumeNotNull(product, "product must be set");}

	public BigDecimal getQtyCUsPerTUNotNull() {return Check.assumeNotNull(qtyCUsPerTU, "qtyCUsPerTU must be set");}
}
