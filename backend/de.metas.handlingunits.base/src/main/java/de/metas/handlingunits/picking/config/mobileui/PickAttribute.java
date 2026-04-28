package de.metas.handlingunits.picking.config.mobileui;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum PickAttribute
{
	BestBeforeDate("BestBeforeDate"),
	LotNo("LotNo"),
	;

	@NonNull private final String json;

	public static PickAttribute ofJson(@NonNull final String json)
	{
		return Stream.of(values())
				.filter(item -> item.json.equals(json))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("Invalid JSON: " + json));
	}

	@JsonValue
	public String toJson() {return json;}
}
