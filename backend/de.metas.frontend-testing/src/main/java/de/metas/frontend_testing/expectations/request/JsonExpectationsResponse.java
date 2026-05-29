package de.metas.frontend_testing.expectations.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.frontend_testing.expectations.assertions.JsonFailure;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.ResponseEntity;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonExpectationsResponse
{
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable JsonFailure failure;

	@NonNull Map<String, Object> context;

	public ResponseEntity<JsonExpectationsResponse> toResponseEntity()
	{
		return failure == null
				? ResponseEntity.ok().body(this)
				: ResponseEntity.badRequest().body(this);
	}
}
