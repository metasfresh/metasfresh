package de.metas.security.permissions;

import java.util.Set;

import javax.annotation.Nullable;

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

	/** {@code null} means the role states no opinion about creating new records in this table. */
	@Getter
	@Nullable
	private final Boolean canCreateNewRecords;

	@lombok.Builder(toBuilder = true)
	private TablePermission(
			@NonNull TableResource resource,
			final Set<Access> accesses,
			@Nullable final Boolean canCreateNewRecords)
	{
		this.resource = resource;
		this.accesses = accesses != null ? ImmutableSet.copyOf(accesses) : ImmutableSet.of();
		this.canCreateNewRecords = canCreateNewRecords;
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
				.canCreateNewRecords(mergeCanCreateNewRecords(this.canCreateNewRecords, tablePermissionFrom.canCreateNewRecords))
				.build();
	}

	/** An explicit {@code true} wins over an explicit {@code false}, as merging accesses is a union too. */
	@Nullable
	private static Boolean mergeCanCreateNewRecords(@Nullable final Boolean value1, @Nullable final Boolean value2)
	{
		if (Boolean.TRUE.equals(value1) || Boolean.TRUE.equals(value2))
		{
			return Boolean.TRUE;
		}
		return value1 != null ? value1 : value2;
	}

	@Override
	public boolean hasAccess(final Access access)
	{
		return accesses.contains(access);
	}
}
