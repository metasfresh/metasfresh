package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonInventoryExpectation
{
	@Nullable Boolean isExists;
	@Nullable String docStatus;
	@Nullable String description;
}
