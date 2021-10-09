package de.metas.security;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

import de.metas.common.util.time.SystemTime;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.user.UserId;
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
	public static UserRolePermissionsKey of(
			final RoleId adRoleId,
			final UserId adUserId,
			final ClientId adClientId,
			final LocalDate date)
	{
		return new UserRolePermissionsKey(adRoleId, adUserId, adClientId, date);
	}

	public static UserRolePermissionsKey fromContext(@NonNull final Properties ctx)
	{
		final RoleId roleId = Env.getLoggedRoleId(ctx);
		final UserId userId = Env
				.getLoggedUserIdIfExists(ctx)
				.orElseThrow(() -> new AdempiereException("Given ctx contains no " + Env.CTXNAME_AD_User_ID)
						.appendParametersToMessage()
						.setParameter("ctx", ctx));

		final ClientId adClientId = Env.getClientId(ctx);
		final LocalDate date = TimeUtil.asLocalDate(Env.getDate(ctx));
		return new UserRolePermissionsKey(roleId, userId, adClientId, date);
	}

	public static UserRolePermissionsKey fromContextOrNull(@NonNull final Properties ctx)
	{
		final RoleId roleId = Env.getLoggedRoleId(ctx);
		final UserId userId = Env.getLoggedUserIdIfExists(ctx).orElse(null);
		if (userId == null)
		{
			return null;
		}

		final ClientId adClientId = Env.getClientId(ctx);
		final LocalDate date = TimeUtil.asLocalDate(Env.getDate(ctx));
		return new UserRolePermissionsKey(roleId, userId, adClientId, date);
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

	private static String toPermissionsKeyString(final RoleId adRoleId, final UserId adUserId, final ClientId adClientId, final LocalDate date)
	{
		return toPermissionsKeyString(
				RoleId.toRepoId(adRoleId),
				UserId.toRepoId(adUserId),
				ClientId.toRepoId(adClientId),
				date);
	}

	private static String toPermissionsKeyString(final int adRoleId, final int adUserId, final int adClientId, final LocalDate date)
	{
		// NOTE: keep in sync with the counterpart (i.e. the constructor)
		return String.join("|", String.valueOf(adRoleId), String.valueOf(adUserId), String.valueOf(adClientId), date.toString());
	}

	public static String toPermissionsKeyString(final Properties ctx)
	{
		final int adRoleId = Env.getAD_Role_ID(ctx);
		final int adUserId = Env.getAD_User_ID(ctx);
		final int adClientId = Env.getAD_Client_ID(ctx);
		final LocalDate date = TimeUtil.asLocalDate(Env.getDate(ctx));
		return toPermissionsKeyString(adRoleId, adUserId, adClientId, date);
	}

	private static final transient Logger logger = LogManager.getLogger(UserRolePermissionsKey.class);

	@Getter
	private final RoleId roleId;
	@Getter
	private final UserId userId;
	@Getter
	private final ClientId clientId;
	@Getter
	private final LocalDate date;

	private transient String _permissionsKeyStr;

	@Builder
	private UserRolePermissionsKey(final RoleId roleId, final UserId userId, final ClientId clientId, final Date date)
	{
		this(roleId, userId, clientId,
				date != null ? TimeUtil.asLocalDate(date) : SystemTime.asLocalDate());
	}

	private UserRolePermissionsKey(
			@NonNull final RoleId roleId,
			@NonNull final UserId userId,
			final ClientId clientId,
			@NonNull final LocalDate date)
	{
		this.roleId = roleId;
		this.userId = userId;
		this.clientId = clientId;
		this.date = date;
	}

	private UserRolePermissionsKey(final String permissionsKeyStr)
	{
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
			date = LocalDate.parse(list[3]);
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
		String permissionsKeyStr = _permissionsKeyStr;
		if (permissionsKeyStr == null)
		{
			_permissionsKeyStr = permissionsKeyStr = toPermissionsKeyString(roleId, userId, clientId, date);
		}
		return permissionsKeyStr;
	}

	public static long normalizeDate(final Instant date)
	{
		return normalizeDate(TimeUtil.asDate(date));
	}

	public static long normalizeDate(final Date date)
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
