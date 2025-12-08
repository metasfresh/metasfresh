package de.metas.ui.web.session.json;

import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonGetWorkplaceResponse
{
	public static final JsonGetWorkplaceResponse NOT_ENABLED = builder().workplacesEnabled(false).build();

	boolean workplacesEnabled;
	@Nullable JSONLookupValue currentWorkplace;
	@Nullable List<JSONLookupValue> available;
}
