package de.metas.security.permissions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_User_Record_Access;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Defines permission access
 *
 * @author tsa
 */
@Value
public class Access implements ReferenceListAwareEnum
{
	public static final Access LOGIN = new Access("LOGIN", "L");
	public static final Access READ = new Access("READ", X_AD_User_Record_Access.ACCESS_Read);
	public static final Access WRITE = new Access("WRITE", X_AD_User_Record_Access.ACCESS_Write);
	public static final Access REPORT = new Access("REPORT", X_AD_User_Record_Access.ACCESS_Report);
	public static final Access EXPORT = new Access("EXPORT", X_AD_User_Record_Access.ACCESS_Export);
	private static final ImmutableSet<Access> ALL_ACCESSES = ImmutableSet.of(LOGIN, READ, WRITE, REPORT, EXPORT);

	private static final ImmutableMap<String, Access> accessesByCode = Maps.uniqueIndex(ALL_ACCESSES, Access::getCode);

	@JsonCreator
	public static Access ofCode(@NonNull final String code)
	{
		final Access access = accessesByCode.get(code);
		if (access == null)
		{
			throw new AdempiereException("No Access found for code: " + code);
		}
		return access;
	}

	public static Set<Access> values()
	{
		return ALL_ACCESSES;
	}

	@NonNull String name;
	@NonNull String code;

	private Access(@NonNull final String name, final @NotNull String code)
	{
		Check.assumeNotEmpty(name, "name not empty");

		this.name = name;
		this.code = code;
	}

	@Override
	public String toString()
	{
		return getName();
	}

	@Override
	@JsonValue
	public @NotNull String getCode()
	{
		return code;
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
