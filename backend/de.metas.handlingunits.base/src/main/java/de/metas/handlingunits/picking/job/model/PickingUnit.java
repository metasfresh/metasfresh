package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@RequiredArgsConstructor
public enum PickingUnit
{
	TU("TU"),
	CU("CU"),
	;

	@NonNull private final String json;

	@JsonCreator
	@Nullable
	public static PickingUnit ofNullableJson(@Nullable final String json)
	{
		final String jsonNorm = StringUtils.trimBlankToNull(json);
		return jsonNorm != null ? ofJson(jsonNorm) : null;
	}

	@NonNull
	public static PickingUnit ofJson(@NonNull final String json)
	{
		if (Objects.equals(TU.json, json))
		{
			return TU;
		}
		else if (Objects.equals(CU.json, json))
		{
			return CU;
		}
		else
		{
			throw new AdempiereException("Cannot convert json `" + json + "` to " + PickingUnit.class.getSimpleName());
		}
	}

	@JsonValue
	public String toJson() {return json;}

	public boolean isTU() {return TU.equals(this);}

	public boolean isCU() {return CU.equals(this);}

	public void assertIsCU()
	{
		if (!isCU())
		{
			throw new AdempiereException("Expected Picking Unit to be CU");
		}
	}

}
