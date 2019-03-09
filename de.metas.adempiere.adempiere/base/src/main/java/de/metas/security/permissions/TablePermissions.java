package de.metas.security.permissions;

import de.metas.security.permissions.PermissionsBuilder.CollisionPolicy;

public final class TablePermissions extends AbstractPermissions<TablePermission>
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private TablePermissions(PermissionsBuilder<TablePermission, TablePermissions> builder)
	{
		super(builder);
	}

	public Builder asNewBuilder()
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

	/**
	 * Access to Table
	 *
	 * @param AD_Table_ID table
	 * @param ro check read only access otherwise read write access level
	 * @return has RO/RW access to table
	 */
	public boolean isTableAccess(final int AD_Table_ID, final boolean ro)
	{
		return hasAccess(AD_Table_ID, ro ? Access.READ : Access.WRITE);
	}

	/**
	 * Can Report on table
	 *
	 * @param AD_Table_ID table
	 * @return true if access
	 */
	public boolean isCanReport(final int AD_Table_ID)
	{
		return hasAccess(AD_Table_ID, Access.REPORT);
	}

	public boolean isCanExport(final int AD_Table_ID)
	{
		return hasAccess(AD_Table_ID, Access.EXPORT);
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
