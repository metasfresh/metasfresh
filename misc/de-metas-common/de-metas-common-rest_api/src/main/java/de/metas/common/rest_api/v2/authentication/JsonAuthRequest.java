package de.metas.common.rest_api.v2.authentication;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(exclude = "password")
@JsonDeserialize(builder = JsonAuthRequest.JsonAuthRequestBuilder.class)
public class JsonAuthRequest
{
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonAuthRequestBuilder {}

	@NonNull String username;
	@NonNull String password;
}
