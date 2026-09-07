package de.metas.ui.web.pattribute.callout.sql;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.common.handlingunits.JsonHUAttribute;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JsonSQLCalloutFunctionResponse
{
	@JsonIgnore
	private final LinkedHashMap<String, Object> attributes = new LinkedHashMap<>();

	@JsonAnyGetter
	public Map<String, Object> getAttributes()
	{
		return attributes;
	}

	@JsonAnySetter
	public void putAttribute(final String name, final Object value)
	{
		attributes.put(name, JsonHUAttribute.convertValueToJson(value));
	}
}
