package de.metas.server.ui_trace.rest;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JsonUITraceEvent
{
	private String id;
	private String eventName;
	private long timestamp;
	private LinkedHashMap<String, Object> properties = new LinkedHashMap<>();

	@JsonAnyGetter
	public LinkedHashMap<String, Object> getProperties() {return properties;}

	@JsonAnySetter
	public void putProperty(final String key, final Object value) {properties.put(key, value);}
}
