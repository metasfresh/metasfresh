package de.metas.security.permissions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Map;

import javax.annotation.concurrent.Immutable;

import org.compiere.model.X_AD_Role;

import com.google.common.collect.ImmutableMap;

import de.metas.util.Check;
import lombok.NonNull;

/**
 * User's preference level.
 *
 * @author tsa
 * @see X_AD_Role.PREFERENCETYPE_*
 */
@Immutable
public final class UserPreferenceLevelConstraint extends Constraint
{
	/**
	 * Gets the preference level constraints for given preference type.
	 *
	 * @param preferenceType preference type (see X_AD_Role.PREFERENCETYPE_*)
	 * @return user preference type constraint; never returns null
	 * @throws IllegalArgumentException if preference type is not valid
	 */
	public static final UserPreferenceLevelConstraint forPreferenceType(@NonNull final String preferenceType)
	{
		final UserPreferenceLevelConstraint constraint = preferenceType2constraint.get(preferenceType);
		if (constraint == null)
		{
			throw new IllegalArgumentException("No " + UserPreferenceLevelConstraint.class + " found for " + preferenceType);
		}
		return constraint;
	}

	public static final UserPreferenceLevelConstraint CLIENT = new UserPreferenceLevelConstraint(X_AD_Role.PREFERENCETYPE_Client);
	public static final UserPreferenceLevelConstraint ORGANIZATION = new UserPreferenceLevelConstraint(X_AD_Role.PREFERENCETYPE_Organization);
	public static final UserPreferenceLevelConstraint USER = new UserPreferenceLevelConstraint(X_AD_Role.PREFERENCETYPE_User);
	public static final UserPreferenceLevelConstraint NONE = new UserPreferenceLevelConstraint(X_AD_Role.PREFERENCETYPE_None);
	private static final Map<String, UserPreferenceLevelConstraint> preferenceType2constraint = ImmutableMap.<String, UserPreferenceLevelConstraint> builder()
			.put(CLIENT.getPreferenceType(), CLIENT)
			.put(ORGANIZATION.getPreferenceType(), ORGANIZATION)
			.put(USER.getPreferenceType(), USER)
			.put(NONE.getPreferenceType(), NONE)
			.build();

	private final String preferenceType;

	private UserPreferenceLevelConstraint(final String preferenceType)
	{
		Check.assumeNotEmpty(preferenceType, "preferenceType not empty");
		this.preferenceType = preferenceType;
	}

	@Override
	public String toString()
	{
		// NOTE: we are making it translateable friendly because it's displayed in Prefereces->Info->Rollen
		final String preferenceTypeName;
		if (this == NONE)
		{
			preferenceTypeName = "@None@";
		}
		else if (this == CLIENT)
		{
			preferenceTypeName = "@AD_Client_ID@";
		}
		else if (this == ORGANIZATION)
		{
			preferenceTypeName = "@AD_Org_ID@";
		}
		else if (this == USER)
		{
			preferenceTypeName = "@AD_User_ID@";
		}
		else
		{
			// shall not happen
			preferenceTypeName = preferenceType;
		}

		return "@PreferenceType@: " + preferenceTypeName;
	}

	/** @return false, i.e. never inherit this constraint because it shall be defined by current role itself */
	@Override
	public boolean isInheritable()
	{
		return false;
	}

	/**
	 * @return preference type (see X_AD_Role.PREFERENCETYPE_*)
	 */
	public String getPreferenceType()
	{
		return preferenceType;
	}

	/**
	 * Show (Value) Preference Menu
	 *
	 * @return true if preference type is not {@link #NONE}.
	 */
	public boolean isShowPreference()
	{
		return !isNone();
	}

	public boolean isNone()
	{
		return this == NONE;
	}

	public boolean isClient()
	{
		return this == CLIENT;
	}

	public boolean isOrganization()
	{
		return this == ORGANIZATION;
	}

	public boolean isUser()
	{
		return this == USER;
	}

	/**
	 *
	 * @return true if this level allows user to view table record change log (i.e. this level is {@link #CLIENT})
	 */
	public boolean canViewRecordChangeLog()
	{
		return isClient();
	}

}
