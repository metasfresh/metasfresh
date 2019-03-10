package de.metas.security;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;

import org.adempiere.service.ClientId;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
@EqualsAndHashCode
@ToString(exclude = "_permissionsKeyStr")
public final class UserRolePermissionsKey implements Serializable
{
	@Deprecated
	public static final UserRolePermissionsKey of(final int adRoleId, final int adUserId, final int adClientId, final Date date)
	{
		return of(RoleId.ofRepoId(adRoleId),
				UserId.ofRepoId(adUserId),
				ClientId.ofRepoId(adClientId),
				TimeUtil.asInstant(date));
	}

	public static final UserRolePermissionsKey of(
			@NonNull final RoleId adRoleId,
			@NonNull final UserId adUserId,
			@NonNull final ClientId adClientId,
			@NonNull final Instant date)
	{
		final long dateMillis = normalizeDate(date);
		return new UserRolePermissionsKey(adRoleId, adUserId, adClientId, dateMillis);
	}

	public static final UserRolePermissionsKey of(@NonNull final Properties ctx)
	{
		final RoleId roleId = Env.getLoggedRoleId(ctx);
		final UserId userId = Env.getLoggedUserId(ctx);
		final ClientId adClientId = Env.getClientId(ctx);
		final Date date = Env.getDate(ctx);
		final long dateMillis = normalizeDate(date);
		return new UserRolePermissionsKey(roleId, userId, adClientId, dateMillis);
	}

	/**
	 * @see #toPermissionsKeyString()
	 * @see #toPermissionsKeyString(int, int, int, long)
	 */
	public static UserRolePermissionsKey fromString(final String permissionsKeyStr)
	{
		return new UserRolePermissionsKey(permissionsKeyStr);
	}

	public static UserRolePermissionsKey fromEvaluatee(final Evaluatee evaluatee, final String variableName)
	{
		final Object permissionsKeyStrObj = evaluatee.get_ValueIfExists(variableName, String.class).orElse(null);
		if (permissionsKeyStrObj == null)
		{
			throw new IllegalArgumentException("No permissions key found for " + variableName + " in " + evaluatee);
		}

		return UserRolePermissionsKey.fromString(permissionsKeyStrObj.toString());
	}

	public static final String toPermissionsKeyString(final RoleId adRoleId, final UserId adUserId, final ClientId adClientId, final long dateMillis)
	{
		return toPermissionsKeyString(
				RoleId.toRepoId(adRoleId),
				UserId.toRepoId(adUserId),
				ClientId.toRepoId(adClientId),
				dateMillis);
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

	@Getter
	private final RoleId roleId;
	@Getter
	private final UserId userId;
	@Getter
	private final ClientId clientId;
	@Getter
	private final long dateMillis;

	private transient String _permissionsKeyStr;

	@Builder
	private UserRolePermissionsKey(final RoleId roleId, final UserId userId, final ClientId clientId, final Date date)
	{
		this(roleId, userId, clientId,
				normalizeDate(date != null ? date : SystemTime.asDayTimestamp()) // dateMillis
		);
	}

	private UserRolePermissionsKey(
			final RoleId roleId,
			final UserId userId,
			final ClientId clientId,
			final long dateMillis)
	{
		this.roleId = roleId;
		this.userId = userId;
		this.clientId = clientId;
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

			roleId = RoleId.ofRepoId(Integer.parseInt(list[0]));
			userId = UserId.ofRepoId(Integer.parseInt(list[1]));
			clientId = ClientId.ofRepoId(Integer.parseInt(list[2]));
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
			_permissionsKeyStr = toPermissionsKeyString(roleId, userId, clientId, dateMillis);
		}
		return _permissionsKeyStr;
	}

	public static final long normalizeDate(final Instant date)
	{
		return normalizeDate(TimeUtil.asDate(date));
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
}
