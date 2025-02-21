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

	String luName;
	JsonTestId luPIItemTestId;
}
