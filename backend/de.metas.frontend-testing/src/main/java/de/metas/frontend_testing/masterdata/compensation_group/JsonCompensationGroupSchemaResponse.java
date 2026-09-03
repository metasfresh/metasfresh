package de.metas.frontend_testing.masterdata.compensation_group;

import de.metas.order.compensationGroup.GroupTemplateId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonCompensationGroupSchemaResponse
{
	@NonNull GroupTemplateId id;
	@NonNull String name;
	int templateLineCount;
}
