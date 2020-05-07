package de.metas.security.permissions;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Defines permissiom on a particular {@link TableResource}.
 *
 * @author tsa
 *
 */
@EqualsAndHashCode
@ToString
public final class TablePermission implements Permission
{
	public static final ImmutableSet<Access> ALL_ACCESSES = ImmutableSet.of(Access.READ, Access.WRITE, Access.REPORT, Access.EXPORT);

	public static final TablePermission NONE = builder()
			.resource(TableResource.ANY_TABLE)
			.accesses(ImmutableSet.of())
			.build();

	public static final TablePermission ALL = builder()
			.resource(TableResource.ANY_TABLE)
			.accesses(ALL_ACCESSES)
			.build();

	@Getter
	private final TableResource resource;
	private final ImmutableSet<Access> accesses;

	@lombok.Builder(toBuilder = true)
	private TablePermission(
			@NonNull TableResource resource,
			final Set<Access> accesses)
	{
		this.resource = resource;
		this.accesses = accesses != null ? ImmutableSet.copyOf(accesses) : ImmutableSet.of();
	}

	@Override
	public Permission mergeWith(final Permission permissionFrom)
	{
		final TablePermission tablePermissionFrom = PermissionInternalUtils.checkCompatibleAndCastToTarget(this, permissionFrom);
		return toBuilder()
				.accesses(ImmutableSet.<Access> builder()
						.addAll(this.accesses)
						.addAll(tablePermissionFrom.accesses)
						.build())
				.build();
	}

	@Override
	public boolean hasAccess(final Access access)
	{
		return accesses.contains(access);
	}
}
