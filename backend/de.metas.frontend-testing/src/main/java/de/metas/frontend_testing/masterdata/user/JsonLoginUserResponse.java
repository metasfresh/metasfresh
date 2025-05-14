package de.metas.frontend_testing.masterdata.user;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonLoginUserResponse
{
	@NonNull String username;
	@NonNull String password;
}
