package de.metas.security;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode
public class RoleGroup
{
	@Getter private final String name;

	private static final ConcurrentHashMap<String, RoleGroup> cache = new ConcurrentHashMap<>();

	private RoleGroup(@NonNull final String name)
	{
		this.name = name;
	}

	@Nullable
	public static RoleGroup ofNullableString(@Nullable final String name)
	{
		final String nameNorm = StringUtils.trimBlankToNull(name);
		if (nameNorm == null)
		{
			return null;
		}

		return cache.computeIfAbsent(name, RoleGroup::new);
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getName();
	}
}
