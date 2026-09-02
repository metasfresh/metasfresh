package de.metas.frontend_testing.masterdata.hu;

import de.metas.frontend_testing.JsonTestId;
import de.metas.handlingunits.grai.GRAI;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

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
	JsonTestId luPITestId;

	/**
	 * The generated scannable GRAI when GRAI mapping was requested; otherwise {@code null}.
	 * Serialized to its canonical {@code companyPrefix.assetType.serial} string via {@link GRAI#toCanonicalString()}.
	 */
	@Nullable GRAI grai;
}
