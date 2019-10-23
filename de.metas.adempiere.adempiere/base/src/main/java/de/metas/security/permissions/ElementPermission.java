package de.metas.security.permissions;

import com.google.common.collect.ImmutableSet;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/**
 * Application Dictionary element permission (e.g. Window, Process etc).
 *
 * @author tsa
 *
 */
@Value
public final class ElementPermission implements Permission
{
	public static ElementPermission ofReadWriteFlag(final ElementResource resource, final boolean readWrite)
	{
		final ImmutableSet.Builder<Access> accesses = ImmutableSet.builder();

		// READ access: this is implied if we are here
		accesses.add(Access.READ);

		// WRITE access:
		if (readWrite)
		{
			accesses.add(Access.WRITE);
		}

		return new ElementPermission(resource, accesses.build());
	}

	public static final ElementPermission none(final ElementResource resource)
	{
		return new ElementPermission(resource, ImmutableSet.of());
	}

	private final ElementResource resource;
	@Getter(AccessLevel.PRIVATE)
	private final ImmutableSet<Access> accesses;

	private ElementPermission(
			@NonNull final ElementResource resource,
			@NonNull final ImmutableSet<Access> accesses)
	{
		this.resource = resource;
		this.accesses = accesses;
	}

	public int getElementId()
	{
		return resource.getElementId();
	}

	@Override
	public boolean hasAccess(Access access)
	{
		return accesses.contains(access);
	}

	public boolean hasReadAccess()
	{
		return accesses.contains(Access.READ);
	}

	public boolean hasWriteAccess()
	{
		return accesses.contains(Access.WRITE);
	}

	public Boolean getReadWriteBoolean()
	{
		if (hasAccess(Access.WRITE))
		{
			return Boolean.TRUE;
		}
		else if (hasAccess(Access.READ))
		{
			return Boolean.FALSE;
		}
		else
		{
			return null;
		}
	}

	@Override
	public Permission mergeWith(final Permission permissionFrom)
	{
		final ElementPermission elementPermissionFrom = PermissionInternalUtils.checkCompatibleAndCastToTarget(this, permissionFrom);

		final ImmutableSet<Access> accesses = ImmutableSet.<Access> builder()
				.addAll(this.accesses)
				.addAll(elementPermissionFrom.accesses)
				.build();

		return new ElementPermission(this.resource, accesses);
	}
}
