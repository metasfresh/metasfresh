package de.metas.frontend_testing.expectations.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.frontend_testing.expectations.assertions.JsonFailure;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonExpectationsResponse
{
	public static final JsonExpectationsResponse OK = builder().build();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable JsonFailure failure;

	public static JsonExpectationsResponse failure(@NonNull Exception exception)
	{
		return builder().failure(JsonFailure.ofException(exception)).build();
	}
}
