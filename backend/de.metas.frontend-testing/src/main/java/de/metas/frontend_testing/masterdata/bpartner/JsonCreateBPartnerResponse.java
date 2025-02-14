package de.metas.frontend_testing.masterdata.bpartner;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonCreateBPartnerResponse
{
	String bpartnerCode;
}
