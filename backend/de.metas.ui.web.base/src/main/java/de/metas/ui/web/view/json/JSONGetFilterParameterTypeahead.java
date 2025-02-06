package de.metas.ui.web.view.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Value
@Builder
@Jacksonized
public class JSONGetFilterParameterTypeahead
{
	@NonNull String query;
	Map<String, Object> context;
}
