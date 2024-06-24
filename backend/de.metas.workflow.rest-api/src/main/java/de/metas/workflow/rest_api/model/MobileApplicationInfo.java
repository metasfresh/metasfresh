package de.metas.workflow.rest_api.model;

import com.google.common.collect.ImmutableMap;
import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class MobileApplicationInfo
{
	@NonNull MobileApplicationId id;
	@NonNull ITranslatableString caption;
	boolean requiresWorkstation;
	boolean requiresWorkplace;
	boolean requiresLaunchersQRCodeFilter;
	boolean showFilters;
	boolean showFilterByDocumentNo;
	@Builder.Default boolean showInMainMenu = true;
	@Builder.Default int sortNo = 100;
	@NonNull @Singular ImmutableMap<String, Object> applicationParameters;
}
