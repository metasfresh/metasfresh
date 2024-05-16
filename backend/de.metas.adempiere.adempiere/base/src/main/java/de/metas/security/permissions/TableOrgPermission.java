package de.metas.security.permissions;

import com.google.common.collect.ImmutableSet;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

@Builder(toBuilder = true)
@ToString
public class TableOrgPermission implements Permission
{
	@Getter
	@NonNull private final TableOrgResource resource;

	@NonNull
	@Singular
	private final ImmutableSet<Access> accesses;

	public String getTableName() {return resource.getTableName();}

	public OrgId getOrgId() {return resource.getOrgId();}

	@Override
	public boolean hasAccess(final Access access) {return accesses.contains(access);}

	@Override
	public TableOrgPermission mergeWith(final Permission from)
	{
		final TableOrgPermission tableOrgPermissionFrom = PermissionInternalUtils.checkCompatibleAndCastToTarget(this, from);
		return toBuilder()
				.accesses(tableOrgPermissionFrom.accesses)
				.build();
	}

}
