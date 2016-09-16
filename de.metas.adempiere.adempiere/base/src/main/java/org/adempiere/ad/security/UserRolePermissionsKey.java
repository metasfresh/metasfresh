package org.adempiere.ad.security;

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

public final class UserRolePermissionsKey
{
	public static final UserRolePermissionsKey of(final int adRoleId, final int adUserId, final int adClientId, final Date date)
	{
		return new UserRolePermissionsKey(adRoleId, adUserId, adClientId, date);
	}

	public static final UserRolePermissionsKey of(final Properties ctx)
	{
		final int adRoleId = Env.getAD_Role_ID(ctx);
		final int adUserId = Env.getAD_User_ID(ctx);
		final int adClientId = Env.getAD_Client_ID(ctx);
		final Date date = Env.getDate(ctx);
		return new UserRolePermissionsKey(adRoleId, adUserId, adClientId, date);
	}

	private static final Logger logger = LogManager.getLogger(UserRolePermissionsKey.class);

	private final int adRoleId;
	private final int adUserId;
	private final int adClientId;
	private final Date date;

	private Integer _hashcode;

	private UserRolePermissionsKey(final int adRoleId, final int adUserId, final int adClientId, final Date date)
	{
		super();
		this.adRoleId = adRoleId;
		this.adUserId = adUserId;
		this.adClientId = adClientId;

		// Make sure we are we are caching only on day level, else would make no sense,
		// and performance penalty would be quite big
		// (i.e. retrieve and load and aggregate the whole shit everything we need to check something in permissions)
		final Date dateDay = normalizeDate(date);
		if (date == null || date.getTime() != dateDay.getTime())
		{
			logger.warn("For performance purpose, make sure you providing a date which is truncated on day level: {}", date);
		}
		this.date = dateDay;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("AD_Role_ID", adRoleId)
				.add("AD_User_ID", adUserId)
				.add("AD_Client_ID", adClientId)
				.add("date", date)
				.toString();
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(adRoleId, adUserId, adClientId, date);
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
				&& Objects.equals(date, other.date);
	}
	
	public static final Date normalizeDate(final Date date)
	{
		return TimeUtil.trunc(date, TimeUtil.TRUNC_DAY);
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

	public Date getDate()
	{
		return date;
	}
}
