package org.adempiere.ad.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@SuppressWarnings("serial")
public final class UserRolePermissionsKey implements Serializable
{
	public static final UserRolePermissionsKey of(final int adRoleId, final int adUserId, final int adClientId, final Date date)
	{
		final long dateMillis = normalizeDate(date);
		return new UserRolePermissionsKey(adRoleId, adUserId, adClientId, dateMillis);
	}

	public static final UserRolePermissionsKey of(final Properties ctx)
	{
		final int adRoleId = Env.getAD_Role_ID(ctx);
		final int adUserId = Env.getAD_User_ID(ctx);
		final int adClientId = Env.getAD_Client_ID(ctx);
		final Date date = Env.getDate(ctx);
		final long dateMillis = normalizeDate(date);
		return new UserRolePermissionsKey(adRoleId, adUserId, adClientId, dateMillis);
	}

	/**
	 * @see #toPermissionsKeyString()
	 * @see #toPermissionsKeyString(int, int, int, long)
	 */
	public static UserRolePermissionsKey fromString(final String permissionsKeyStr)
	{
		return new UserRolePermissionsKey(permissionsKeyStr);
	}

	public static final String toPermissionsKeyString(final int adRoleId, final int adUserId, final int adClientId, final long dateMillis)
	{
		// NOTE: keep in sync with the counterpart (i.e. the constructor)
		return String.join("|", String.valueOf(adRoleId), String.valueOf(adUserId), String.valueOf(adClientId), String.valueOf(dateMillis));
	}

	public static final String toPermissionsKeyString(final Properties ctx)
	{
		final int adRoleId = Env.getAD_Role_ID(ctx);
		final int adUserId = Env.getAD_User_ID(ctx);
		final int adClientId = Env.getAD_Client_ID(ctx);
		final Date date = Env.getDate(ctx);
		final long dateMillis = normalizeDate(date);
		return toPermissionsKeyString(adRoleId, adUserId, adClientId, dateMillis);
	}

	private static final transient Logger logger = LogManager.getLogger(UserRolePermissionsKey.class);

	private final int adRoleId;
	private final int adUserId;
	private final int adClientId;
	private final long dateMillis;

	private transient Integer _hashcode;
	private transient String _permissionsKeyStr;

	private UserRolePermissionsKey(final int adRoleId, final int adUserId, final int adClientId, final long dateMillis)
	{
		super();
		this.adRoleId = adRoleId;
		this.adUserId = adUserId;
		this.adClientId = adClientId;
		this.dateMillis = dateMillis;
	}

	private UserRolePermissionsKey(final String permissionsKeyStr)
	{
		super();

		// NOTE: keep in sync with the counterpart method toPermissionsKeyString(...) !!!
		try
		{
			final String[] list = permissionsKeyStr.split("\\|");
			if (list.length != 4)
			{
				throw new IllegalArgumentException("invalid format");
			}

			adRoleId = Integer.parseInt(list[0]);
			adUserId = Integer.parseInt(list[1]);
			adClientId = Integer.parseInt(list[2]);
			dateMillis = Long.parseLong(list[3]);
			_permissionsKeyStr = permissionsKeyStr;
		}
		catch (final Exception e)
		{
			throw new IllegalArgumentException("Invalid permissionsKey string: " + permissionsKeyStr, e);
		}
	}

	/**
	 * @see #fromString(String)
	 */
	public String toPermissionsKeyString()
	{
		if (_permissionsKeyStr == null)
		{
			_permissionsKeyStr = toPermissionsKeyString(adRoleId, adUserId, adClientId, dateMillis);
		}
		return _permissionsKeyStr;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("AD_Role_ID", adRoleId)
				.add("AD_User_ID", adUserId)
				.add("AD_Client_ID", adClientId)
				.add("date", new Date(dateMillis))
				.toString();
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(adRoleId, adUserId, adClientId, dateMillis);
		}
		return _hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof UserRolePermissionsKey))
		{
			return false;
		}

		final UserRolePermissionsKey other = (UserRolePermissionsKey)obj;
		return adRoleId == other.adRoleId
				&& adUserId == other.adUserId
				&& adClientId == other.adClientId
				&& dateMillis == other.dateMillis;
	}

	public static final long normalizeDate(final Date date)
	{
		final long dateDayMillis = TimeUtil.truncToMillis(date, TimeUtil.TRUNC_DAY);

		// Make sure we are we are caching only on day level, else would make no sense,
		// and performance penalty would be quite big
		// (i.e. retrieve and load and aggregate the whole shit everything we need to check something in permissions)
		if (date == null || date.getTime() != dateDayMillis)
		{
			logger.warn("For performance purpose, make sure you providing a date which is truncated on day level: {}", date);
		}

		return dateDayMillis;
	}

	public int getAD_Role_ID()
	{
		return adRoleId;
	}

	public int getAD_User_ID()
	{
		return adUserId;
	}

	public int getAD_Client_ID()
	{
		return adClientId;
	}

	public long getDateMillis()
	{
		return dateMillis;
	}
}
