package de.metas.security.permissions;

import java.util.Set;
import java.util.function.Function;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_User_Record_Access;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.NonNull;
import lombok.Value;

/**
 * Defines permission access
 * 
 * @author tsa
 *
 */
@Value
public final class Access implements ReferenceListAwareEnum
{
	public static final Access LOGIN = new Access("LOGIN", "L");
	public static final Access READ = new Access("READ", X_AD_User_Record_Access.ACCESS_Read);
	public static final Access WRITE = new Access("WRITE", X_AD_User_Record_Access.ACCESS_Write);
	public static final Access REPORT = new Access("REPORT", X_AD_User_Record_Access.ACCESS_Report);
	public static final Access EXPORT = new Access("EXPORT", X_AD_User_Record_Access.ACCESS_Export);
	private static final ImmutableSet<Access> ALL_ACCESSES = ImmutableSet.of(LOGIN, READ, WRITE, REPORT, EXPORT);

	private static final ImmutableMap<String, Access> accessesByCode = ALL_ACCESSES.stream()
			.filter(access -> access.getCode() != null)
			.collect(ImmutableMap.toImmutableMap(Access::getCode, Function.identity()));

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

	private final String name;
	private final String code;

	private Access(@NonNull final String name, final String code)
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
	public String getCode()
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
