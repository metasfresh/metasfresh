package de.metas.cucumber.stepdefs.zoom_into;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;

import javax.annotation.Nullable;

@Value
@Builder
public class ReferenceFieldInfo
{
	@NonNull String sourceTableName;
	@NonNull String sourceColumnName;
	@NonNull String targetTableName;

	@Nullable AdWindowId resolvedWindowId;
	@Nullable String resolvedWindowName;
}
