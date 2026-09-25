package de.metas.security.permissions;

import de.metas.security.permissions.PermissionsBuilder.CollisionPolicy;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;

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

	public final boolean hasAccess(@NonNull final AdTableId adTableId, final Access access)
	{
		// TableResource is the int-keyed resource model (ANY_TABLE is the default); unwrap once here at that boundary.
		final TableResource resource = TableResource.ofAD_Table_ID(adTableId.getRepoId());
		return hasAccess(resource, access);
	}

	public boolean isCanReport(@NonNull final AdTableId adTableId)
	{
		return hasAccess(adTableId, Access.REPORT);
	}

	public boolean isCanExport(@NonNull final AdTableId adTableId)
	{
		return hasAccess(adTableId, Access.EXPORT);
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
