package de.metas.security.permissions;

import de.metas.security.permissions.PermissionsBuilder.CollisionPolicy;

public final class TablePermissions extends AbstractPermissions<TablePermission>
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private TablePermissions(final PermissionsBuilder<TablePermission, TablePermissions> builder)
	{
		super(builder);
	}

	public Builder toBuilder()
	{
		final Builder builder = builder();
		builder.addPermissions(this, CollisionPolicy.Override);
		return builder;
	}

	@Override
	protected TablePermission noPermission()
	{
		return TablePermission.NONE;
	}

	public final boolean hasAccess(final int adTableId, final Access access)
	{
		final TableResource resource = TableResource.ofAD_Table_ID(adTableId);
		return hasAccess(resource, access);
	}

	public boolean isCanReport(final int AD_Table_ID)
	{
		return hasAccess(AD_Table_ID, Access.REPORT);
	}

	public boolean isCanExport(final int AD_Table_ID)
	{
		return hasAccess(AD_Table_ID, Access.EXPORT);
	}

	/** The permission is restrictive only: it is granted unless the role explicitly forbids it. */
	public boolean isCanCreateNewRecords(final int AD_Table_ID)
	{
		final TablePermission permission = getPermissionOrDefault(TableResource.ofAD_Table_ID(AD_Table_ID));
		return !Boolean.FALSE.equals(permission.getCanCreateNewRecords());
	}

	public static class Builder extends PermissionsBuilder<TablePermission, TablePermissions>
	{
		@Override
		protected TablePermissions createPermissionsInstance()
		{
			return new TablePermissions(this);
		}
	}
}
