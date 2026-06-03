package de.metas.frontend_testing.masterdata.hu;

import de.metas.frontend_testing.JsonTestId;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

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
	 * The generated scannable GRAI (canonical {@code companyPrefix.assetType.serial} format)
	 * when GRAI mapping was requested; otherwise {@code null}.
	 */
	String grai;
}
