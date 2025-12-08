package de.metas.picking.rest_api.json;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.handlingunits.picking.job.model.PickingJobProgress;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

@RequiredArgsConstructor
public enum JsonCompleteStatus
{
	NOT_STARTED("NOT_STARTED"),
	IN_PROGRESS("IN_PROGRESS"),
	COMPLETED("COMPLETED"),
	;

	@NonNull private final String json;

	@NonNull private static final ImmutableMap<String, JsonCompleteStatus> byJson = Maps.uniqueIndex(Arrays.asList(values()), JsonCompleteStatus::toJson);

	public static JsonCompleteStatus ofJson(@NonNull final String json)
	{
		final JsonCompleteStatus type = byJson.get(json);
		if (type == null)
		{
			throw new AdempiereException("Cannot convert json `" + json + "` to " + JsonCompleteStatus.class.getSimpleName());
		}
		return type;
	}

	public static JsonCompleteStatus of(@NonNull final PickingJobProgress progress)
	{
		switch (progress)
		{
			case NOT_STARTED:
				return NOT_STARTED;
			case IN_PROGRESS:
				return IN_PROGRESS;
			case DONE:
				return COMPLETED;
			default:
				throw new AdempiereException("Cannot convert " + PickingJobProgress.class.getSimpleName() + " `" + progress + "` to " + JsonCompleteStatus.class.getSimpleName());
		}
	}

	@JsonValue
	public String toJson() {return json;}

}
