package de.metas.hu_consolidation.mobile.rest_api.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class JsonHUConsolidationTargetGrais
{
	@NonNull List<String> graiCodes;
	int tuCount;

	@JsonCreator
	public JsonHUConsolidationTargetGrais(
			@JsonProperty("graiCodes") @NonNull final List<String> graiCodes,
			@JsonProperty("tuCount") final int tuCount)
	{
		this.graiCodes = graiCodes;
		this.tuCount = tuCount;
	}
}
