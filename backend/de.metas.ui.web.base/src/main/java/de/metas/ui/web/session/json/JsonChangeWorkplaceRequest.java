package de.metas.ui.web.session.json;

import de.metas.workplace.WorkplaceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonChangeWorkplaceRequest
{
	@NonNull WorkplaceId workplaceId;
}
