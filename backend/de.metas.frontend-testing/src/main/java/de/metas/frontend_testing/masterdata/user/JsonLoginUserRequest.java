package de.metas.frontend_testing.masterdata.user;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonLoginUserRequest
{
	String language;
	@Nullable Identifier workplace;
}
