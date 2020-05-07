package org.adempiere.ad.security;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import javax.annotation.concurrent.Immutable;

import com.google.common.collect.ImmutableMap;

/**
 * Table Access Level
 *
 * @author tsa
 *
 */
@Immutable
public enum TableAccessLevel
{
	None(0, "AccessNone"), // NOTE: mainly introduced to avoid using null
	Organization(1, "AccessOrg"),
	ClientOnly(2, "AccessClient"),
	ClientPlusOrganization(3, "AccessClientOrg"),
	SystemOnly(4, "AccessSystem"),
	// SystemPlusOrganization(5), // NOTE: not a valid permission
	SystemPlusClient(6, "AccessSystemClient"),
	All(7, "AccessShared");

	// ----------------------------------------------------------------------------

	/** System/Client/Org flags bitmap */
	private final int accesses;
	/** User friendly AD_Message of this level */
	private final String adMessage;

	TableAccessLevel(final int mask, final String adMessage)
	{
		this.accesses = mask;
		this.adMessage = adMessage;
	}

	public static final TableAccessLevel forAccessLevel(final String accessLevelStr)
	{
		final int accessLevel = Integer.parseInt(accessLevelStr);
		return forAccessLevel(accessLevel);
	}

	public static final TableAccessLevel forAccessLevel(final int accessLevel)
	{
		final TableAccessLevel level = accessLevelInt2accessLevel.get(accessLevel);
		if (level == null)
		{
			throw new IllegalArgumentException("Invalid access level: " + accessLevel);
		}
		return level;
	}

	/**
	 * @return minimum required access level to access a record with given AD_Client_ID/AD_Org_ID.
	 */
	public static final TableAccessLevel forClientOrg(final int AD_Client_ID, final int AD_Org_ID)
	{
		// System == Client=0 & Org=0
		if (AD_Client_ID == 0 && AD_Org_ID == 0)
		{
			return TableAccessLevel.SystemOnly;
		}
		// Client == Client>0 & Org=0
		else if (AD_Client_ID > 0 && AD_Org_ID == 0)
		{
			return TableAccessLevel.ClientOnly;
		}
		// Organization == Client>0 & Org>0
		else if (AD_Client_ID > 0 && AD_Org_ID > 0)
		{
			return TableAccessLevel.Organization;
		}
		else
		{
			throw new IllegalArgumentException("Invalid AD_Client_ID=" + AD_Client_ID + " / AD_Org_ID=" + AD_Org_ID);
		}

	}

	/**
	 *
	 * @param userLevel user level (SCO format)
	 * @return {@link TableAccessLevel}
	 */
	public static TableAccessLevel forUserLevel(final String userLevel)
	{
		if (userLevel == null || userLevel.length() != 3)
		{
			throw new IllegalArgumentException("Invalid user level: " + userLevel);
		}

		final int accessLevel = (userLevel.charAt(0) == 'S' ? 4 : 0)
				+ (userLevel.charAt(1) == 'C' ? 2 : 0)
				+ (userLevel.charAt(2) == 'O' ? 1 : 0);
		final TableAccessLevel level = accessLevelInt2accessLevel.get(accessLevel);
		if (level == null)
		{
			throw new IllegalArgumentException("Invalid user level: " + userLevel);
		}
		return level;
	}

	/**
	 * @return access level as integer (i.e. the accesses bitmap)
	 */
	public int getAccessLevelInt()
	{
		return accesses;
	}

	/**
	 * @return access level code as string (i.e. X_AD_Table.ACCESSLEVEL_ *)
	 */
	public String getAccessLevelString()
	{
		return String.valueOf(accesses);
	}

	/**
	 * @return access level in "SCO" format (i.e. X_AD_Role.USERLEVEL_*)
	 */
	public String getUserLevelString()
	{
		return String.valueOf(new char[] {
				(isSystem() ? 'S' : '_')
				, (isClient() ? 'C' : '_')
				, (isOrganization() ? 'O' : '_')
		});
	}

	/**
	 * @param accessLevel
	 * @return true if this access level and the given one share at least one common level (System, Client, Org)
	 */
	public boolean hasCommonLevels(final TableAccessLevel accessLevel)
	{
		return (accesses & accessLevel.accesses) > 0;
	}

	/**
	 * Checks if given access level can access this access level.
	 * 
	 * @param accessLevel
	 * @return true if this access level can be accessed by given access level
	 */
	public boolean canBeAccessedBy(final TableAccessLevel accessLevel)
	{
		// 0 - None
		if (this == None)
			return false;

		// 7 - All
		if (this == All)
			return true;

		// 4 - System data requires S
		else if (this == SystemOnly && !accessLevel.isSystem())
			return false;

		// 2 - Client data requires C
		else if (this == ClientOnly && !accessLevel.isClient())
			return false;

		// 1 - Organization data requires O
		else if (this == Organization && !accessLevel.isOrganization())
			return false;

		// 3 - Client Shared requires C or O
		else if (this == ClientPlusOrganization && (!(accessLevel.isClient() || accessLevel.isOrganization())))
			return false;

		// 6 - System/Client requires S or C
		else if (this == SystemPlusClient && (!(accessLevel.isSystem() || accessLevel.isClient())))
			return false;

		return true;
	}

	/**
	 * @return true if has ALL flags set (System, Client, Org)
	 */
	public boolean isAll()
	{
		return this == All;
	}

	/**
	 * @return true if it has System flag set
	 */
	public boolean isSystem()
	{
		return (accesses & SystemOnly.accesses) > 0;
	}

	/**
	 * @return true if it has only the System flag set (i.e. it's {@value #SystemOnly})
	 */
	public boolean isSystemOnly()
	{
		return this == SystemOnly;
	}

	/**
	 * @return true if it has Client flag set
	 */
	public boolean isClient()
	{
		return (accesses & ClientOnly.accesses) > 0;
	}

	/**
	 * @return true if it has only the Client flag set (i.e. it's {@value #ClientOnly})
	 */
	public boolean isClientOnly()
	{
		return this == ClientOnly;
	}

	/**
	 * @return true if it has Organization flag set
	 */
	public boolean isOrganization()
	{
		return (accesses & Organization.accesses) > 0;
	}

	/**
	 * @return true if it has only the Organization flag set (i.e. it's {@value #Organization})
	 */
	public boolean isOrganizationOnly()
	{
		return this == Organization;
	}

	/**
	 * @return user friendly AD_Message of this access level.
	 */
	public String getAD_Message()
	{
		return adMessage;
	}

	/**
	 * Gets user friendly description of enabled flags.
	 * 
	 * e.g. "3 - Client - Org"
	 * 
	 * @return user friendly description of enabled flags
	 */
	public String getDescription()
	{
		final StringBuilder accessLevelInfo = new StringBuilder();
		accessLevelInfo.append(getAccessLevelString());
		if (isSystem())
		{
			accessLevelInfo.append(" - System");
		}
		if (isClient())
		{
			accessLevelInfo.append(" - Client");
		}
		if (isOrganization())
		{
			accessLevelInfo.append(" - Org");
		}
		return accessLevelInfo.toString();
	}

	// --------------------------------------
	// Pre-indexed values for optimization
	private static ImmutableMap<Integer, TableAccessLevel> accessLevelInt2accessLevel;
	static
	{
		final ImmutableMap.Builder<Integer, TableAccessLevel> builder = new ImmutableMap.Builder<Integer, TableAccessLevel>();
		for (final TableAccessLevel l : values())
		{
			builder.put(l.getAccessLevelInt(), l);
		}
		accessLevelInt2accessLevel = builder.build();
	}
}
