package de.metas.frontend_testing.masterdata.hu;

import com.google.common.collect.ImmutableList;
import de.metas.frontend_testing.JsonTestId;
import de.metas.handlingunits.grai.GRAI;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonPackingInstructionsResponse
{
	String tuName;
	JsonTestId tuPIItemProductTestId;
	JsonTestId tuPITestId;

	String luName;
	JsonTestId luPIItemTestId;
	/** Test IDs of the product-specific LU items requested via {@code additionalLUItems}, in request order. */
	@Builder.Default
	List<JsonTestId> additionalLUPIItemTestIds = ImmutableList.of();
	JsonTestId luPITestId;

	/**
	 * The generated scannable GRAI when GRAI mapping was requested; otherwise {@code null}.
	 * Serialized to its canonical {@code companyPrefix.assetType.serial} string via {@link GRAI#toCanonicalString()}.
	 */
	@Nullable GRAI grai;
}
