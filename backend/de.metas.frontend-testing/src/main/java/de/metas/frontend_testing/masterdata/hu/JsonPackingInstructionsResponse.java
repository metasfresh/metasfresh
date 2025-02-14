package de.metas.frontend_testing.masterdata.hu;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPackingInstructionsResponse
{
	String tuName;
	String luName;
}
