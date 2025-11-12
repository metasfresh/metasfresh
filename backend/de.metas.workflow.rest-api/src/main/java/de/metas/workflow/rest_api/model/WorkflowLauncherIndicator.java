package de.metas.workflow.rest_api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public enum WorkflowLauncherIndicator
{
	RED("red"),
	YELLOW("yellow"),
	GREEN("green"),
	;

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
		if (RED.json.equals(json))
		{
			return RED;
		}
		else if (YELLOW.json.equals(json))
		{
			return YELLOW;
		}
		else if (GREEN.json.equals(json))
		{
			return GREEN;
		}
		else
			throw new AdempiereException("Unknown WorkflowLauncherIndicator: " + json);
	}

	@JsonValue
	public String toJson() {return json;}
}
