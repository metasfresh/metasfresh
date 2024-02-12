package de.metas.workflow.rest_api.model;

import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
public class MobileApplicationInfo
{
	@NonNull MobileApplicationId id;
	@NonNull ITranslatableString caption;
	boolean requiresLaunchersQRCodeFilter;
	boolean showFilters;
	@Builder.Default boolean showInMainMenu = true;
	@Builder.Default int sortNo = 100;
	@Nullable Map<CustomApplicationParameter, Object> applicationParameters;
}
