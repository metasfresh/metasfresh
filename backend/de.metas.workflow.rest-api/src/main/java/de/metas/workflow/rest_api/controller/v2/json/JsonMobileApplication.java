package de.metas.workflow.rest_api.controller.v2.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableMap;
import de.metas.workflow.rest_api.model.CustomApplicationParameter;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@Builder
@Jacksonized
public class JsonMobileApplication
{
	@NonNull String id;
	@NonNull String caption;
	boolean requiresLaunchersQRCodeFilter;
	boolean showFilters;
	@Nullable ImmutableMap<CustomApplicationParameter, Object> applicationParameters;

	public static JsonMobileApplication of(final MobileApplicationInfo appInfo, final JsonOpts jsonOpts)
	{
		return builder()
				.id(appInfo.getId().getAsString())
				.caption(appInfo.getCaption().translate(jsonOpts.getAdLanguage()))
				.requiresLaunchersQRCodeFilter(appInfo.isRequiresLaunchersQRCodeFilter())
				.showFilters(appInfo.isShowFilters())
				.applicationParameters(JsonApplicationParametersMapper.map(appInfo.getApplicationParameters()))
				.build();
	}
}
