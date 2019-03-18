package de.metas.security.permissions;

import com.google.common.collect.ImmutableMap;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

/**
 * Defines permission access
 * 
 * @author tsa
 *
 */
@Value
public final class Access
{
	public static final Access LOGIN = new Access("LOGIN");
	public static final Access READ = new Access("READ");
	public static final Access WRITE = new Access("WRITE");
	public static final Access REPORT = new Access("REPORT");
	public static final Access EXPORT = new Access("EXPORT");

	private static final ImmutableMap<String, Access> accessesByName = ImmutableMap.<String, Access> builder()
			.put(LOGIN.getName(), LOGIN)
			.put(READ.getName(), READ)
			.put(WRITE.getName(), WRITE)
			.put(REPORT.getName(), REPORT)
			.put(EXPORT.getName(), EXPORT)
			.build();

	public static Access ofName(@NonNull final String accessName)
	{
		final Access access = accessesByName.get(accessName);
		return access != null ? access : new Access(accessName);
	}

	private final String name;

	private Access(@NonNull final String name)
	{
		Check.assumeNotEmpty(name, "name not empty");
		this.name = name;
	}

	@Override
	public String toString()
	{
		return getName();
	}

	public boolean isReadOnly()
	{
		return READ.equals(this);
	}

	public boolean isReadWrite()
	{
		return WRITE.equals(this);
	}

}
