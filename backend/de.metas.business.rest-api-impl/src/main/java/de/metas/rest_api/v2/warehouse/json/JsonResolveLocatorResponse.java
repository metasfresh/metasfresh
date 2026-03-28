package de.metas.rest_api.v2.warehouse.json;

import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.rest_api.utils.v2.JsonErrors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonResolveLocatorResponse
{
	@Nullable JsonErrorItem error;
	@Nullable JsonLocator locator;

	public static JsonResolveLocatorResponse ok(@NonNull final JsonLocator locator)
	{
		return builder().locator(locator).build();
	}

	public static JsonResolveLocatorResponse error(@NonNull final Exception exception, @NonNull final String adLanguage)
	{
		return builder().error(JsonErrors.ofThrowable(exception, adLanguage)).build();
	}
}
