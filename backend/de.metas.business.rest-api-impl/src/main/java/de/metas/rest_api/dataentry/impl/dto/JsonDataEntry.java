package de.metas.rest_api.dataentry.impl.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.Value;

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonDataEntry
{
	@JsonProperty("tabs")
	List<JsonDataEntryTab> tabs;

	@Builder
	private JsonDataEntry(final List<JsonDataEntryTab> tabs)
	{
		this.tabs = tabs != null ? tabs : ImmutableList.of();
	}
}
