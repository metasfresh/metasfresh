package de.metas.frontend_testing.expectations.assertions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.rest_api.utils.v2.JsonErrors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.compiere.util.Env;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Value
@Builder
@Jacksonized
public class JsonFailure
{
	@NonNull String message;
	@NonNull String stacktrace;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@NonNull Map<String, Object> context;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@NonNull List<JsonFailure> causes;

	public static JsonFailure ofException(@NonNull final Exception exception)
	{
		return of(Failure.ofException(exception));
	}

	public static JsonFailure of(@NonNull final Failure failure)
	{
		return builder()
				.message(failure.getMessage())
				.stacktrace(failure.getStacktrace())
				.context(JsonErrors.convertParametersMapToJson(failure.getContext(), Env.getADLanguageOrBaseLanguage()))
				.causes(failure.getCauses()
						.stream()
						.map(JsonFailure::of)
						.collect(Collectors.toList()))
				.build();
	}
}
