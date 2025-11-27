package de.metas.workflow.rest_api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public enum WorkflowLauncherIndicator
{
	// note to dev: keep in sync with WorkflowLauncherIndicator.js
	STOCK_NOT_AVAILABLE("STOCK_NOT_AVAILABLE"),
	STOCK_PARTIALLY_AVAILABLE("STOCK_PARTIALLY_AVAILABLE"),
	STOCK_FULLY_AVAILABLE("STOCK_FULLY_AVAILABLE"),
	;

	private static final ImmutableMap<String, WorkflowLauncherIndicator> typesByJson = Maps.uniqueIndex(ImmutableList.copyOf(values()), WorkflowLauncherIndicator::toJson);

	@NonNull private final String json;

	@Nullable
	@JsonCreator
	public static WorkflowLauncherIndicator fromNullableJson(@Nullable final String json)
	{
		final String jsonNorm = StringUtils.trimBlankToNull(json);
		return jsonNorm != null ? fromJson(jsonNorm) : null;
	}

	@NonNull
	public static WorkflowLauncherIndicator fromJson(@NonNull final String json)
	{
		final WorkflowLauncherIndicator type = typesByJson.get(json);
		if (type == null)
		{
			throw new AdempiereException("Unknown: " + json);
		}
		return type;
	}

	@JsonValue
	public String toJson() {return json;}
}
