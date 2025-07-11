package de.metas.ui.web.login.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.stream.Collector;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
@Builder
@Jacksonized
public class JSONOAuth2Providers
{
	@NonNull List<JSONOAuth2Provider> list;

	public static JSONOAuth2Providers ofList(List<JSONOAuth2Provider> list)
	{
		return builder().list(list).build();
	}

	public static Collector<JSONOAuth2Provider, ?, JSONOAuth2Providers> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(JSONOAuth2Providers::ofList);
	}

}
