package de.metas.workflow.rest_api.controller.v2.json;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@EqualsAndHashCode
@ToString
public class JsonSettings
{
	@JsonIgnore
	private final HashMap<String, Object> map;

	private JsonSettings(final Map<String, String> map)
	{
		this.map = new HashMap<>(map);
	}

	public static JsonSettings ofMap(final Map<String, String> map)
	{
		return new JsonSettings(map);
	}

	@JsonAnyGetter
	public Map<String, Object> getMap()
	{
		return map;
	}
}
