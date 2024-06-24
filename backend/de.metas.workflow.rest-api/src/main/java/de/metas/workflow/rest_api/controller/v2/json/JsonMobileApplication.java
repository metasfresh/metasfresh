package de.metas.workflow.rest_api.controller.v2.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableMap;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@Builder
@Jacksonized
public class JsonMobileApplication
{
	@NonNull String id;
	@NonNull String caption;
	boolean requiresWorkstation;
	boolean requiresWorkplace;
	boolean requiresLaunchersQRCodeFilter;
	boolean showFilters;
	boolean showFilterByDocumentNo;
	boolean showInMainMenu;
	int sortNo;
	@Nullable ImmutableMap<String, Object> applicationParameters;

	public static JsonMobileApplication of(final MobileApplicationInfo appInfo, final JsonOpts jsonOpts)
	{
		return builder()
				.id(appInfo.getId().getAsString())
				.caption(appInfo.getCaption().translate(jsonOpts.getAdLanguage()))
				.requiresWorkstation(appInfo.isRequiresWorkstation())
				.requiresWorkplace(appInfo.isRequiresWorkplace())
				.requiresLaunchersQRCodeFilter(appInfo.isRequiresLaunchersQRCodeFilter())
				.showFilters(appInfo.isShowFilters())
				.showFilterByDocumentNo(appInfo.isShowFilterByDocumentNo())
				.showInMainMenu(appInfo.isShowInMainMenu())
				.sortNo(appInfo.getSortNo())
				.applicationParameters(toJsonApplicationParameters(appInfo.getApplicationParameters()))
				.build();
	}

	@Nullable
	private static ImmutableMap<String, Object> toJsonApplicationParameters(@Nullable final Map<String, Object> applicationParameters)
	{
		if (applicationParameters == null || applicationParameters.isEmpty())
		{
			return null;
		}

		return applicationParameters.entrySet()
				.stream()
				.filter(entry -> entry.getValue() != null) // filter out null values
				.collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
	}
}
