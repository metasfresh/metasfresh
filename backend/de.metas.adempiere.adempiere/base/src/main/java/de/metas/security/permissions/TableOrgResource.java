package de.metas.security.permissions;

import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class TableOrgResource implements Resource
{
	@NonNull String tableName;
	@NonNull OrgId orgId;
}
