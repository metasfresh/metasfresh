package de.metas.frontend_testing.masterdata.uom;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonUOMRequest
{
	int precision;
}
