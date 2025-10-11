package de.metas.workflow.rest_api.controller.v2.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.mobile.application.MobileApplicationAction;
import de.metas.mobile.application.MobileApplicationInfo;
import de.metas.security.mobile_application.MobileApplicationPermissions;
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
	@NonNull ImmutableSet<String> actions;
	boolean requiresWorkstation;
	boolean requiresWorkplace;
	boolean showFilterByQRCode;
	boolean showFilters;
	boolean showFilterByDocumentNo;
	boolean showInMainMenu;
	int sortNo;
	@Nullable ImmutableMap<String, Object> applicationParameters;

	public static JsonMobileApplication of(
			final MobileApplicationInfo appInfo,
			final JsonOpts jsonOpts,
			final MobileApplicationPermissions mobileApplicationPermissions)
	{
		return builder()
				.id(appInfo.getId().getAsString())
				.caption(appInfo.getCaption().translate(jsonOpts.getAdLanguage()))
				.actions(appInfo.getActions()
						.stream()
						.filter(action -> mobileApplicationPermissions.isAllowAction(appInfo.getRepoId(), action.getId()))
						.map(MobileApplicationAction::getInternalName)
						.collect(ImmutableSet.toImmutableSet()))
				.requiresWorkstation(appInfo.isRequiresWorkstation())
				.requiresWorkplace(appInfo.isRequiresWorkplace())
				.showFilterByQRCode(appInfo.isShowFilterByQRCode())
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
