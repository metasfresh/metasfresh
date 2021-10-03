package de.metas.security.permissions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

public class TableOrgPermissions extends AbstractPermissions<TableOrgPermission>
{
	public static TableOrgPermissions.Builder builder() {return new TableOrgPermissions.Builder();}

	private final ImmutableListMultimap<String, TableOrgPermission> permissionsByTableName;

	private TableOrgPermissions(final TableOrgPermissions.Builder builder)
	{
		super(builder);

		this.permissionsByTableName = Multimaps.index(
				builder.getPermissions().values(),
				TableOrgPermission::getTableName);

	}

	public TableOrgPermissions.Builder asNewBuilder()
	{
		final TableOrgPermissions.Builder builder = builder();
		builder.addPermissions(this, PermissionsBuilder.CollisionPolicy.Override);
		return builder;
	}

	@Override
	protected TableOrgPermission noPermission() {return null;}

	public Optional<Set<OrgId>> getOrgsWithAccess(@Nullable final String tableName, @NonNull final Access access)
	{
		// Does not apply if table name is not provided
		if (tableName == null || Check.isBlank(tableName))
		{
			return Optional.empty();
		}

		final ImmutableList<TableOrgPermission> tablePermissions = permissionsByTableName.get(tableName);
		if (tablePermissions.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableSet<OrgId> orgIds = tablePermissions.stream()
				.filter(permission -> permission.hasAccess(access))
				.map(TableOrgPermission::getOrgId)
				.collect(ImmutableSet.toImmutableSet());

		return Optional.of(orgIds);
	}

	public static class Builder extends PermissionsBuilder<TableOrgPermission, TableOrgPermissions>
	{

		@Override
		protected TableOrgPermissions createPermissionsInstance()
		{
			return new TableOrgPermissions(this);
		}
	}
}
