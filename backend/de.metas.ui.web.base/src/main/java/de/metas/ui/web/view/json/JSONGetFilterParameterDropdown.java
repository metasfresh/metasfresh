package de.metas.ui.web.view.json;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Value
@Builder
@Jacksonized
public class JSONGetFilterParameterDropdown
{
	Map<String, Object> context;
}
