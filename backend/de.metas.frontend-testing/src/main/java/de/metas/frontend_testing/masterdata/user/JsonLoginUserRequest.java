package de.metas.frontend_testing.masterdata.user;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonLoginUserRequest
{
	String language;
}
