package de.metas.workflow.rest_api.controller.v2.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableList;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Comparator;
import java.util.List;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@Builder
@Jacksonized
public class JsonMobileApplicationsList
{
	@NonNull ImmutableList<JsonMobileApplication> applications;

	public static JsonMobileApplicationsList of(
			@NonNull final List<MobileApplicationInfo> list,
			@NonNull final JsonOpts jsonOpts)
	{
		return builder()
				.applications(list.stream()
						.map(appInfo -> JsonMobileApplication.of(appInfo, jsonOpts))
						.sorted(Comparator.comparing(JsonMobileApplication::getCaption))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
