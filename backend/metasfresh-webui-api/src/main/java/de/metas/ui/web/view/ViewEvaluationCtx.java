package de.metas.ui.web.view;

import java.time.ZoneId;
import java.util.Optional;
import java.util.Properties;

import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import de.metas.security.UserRolePermissionsKey;
import de.metas.security.impl.AccessSqlStringExpression;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@ToString
public final class ViewEvaluationCtx
{
	public static ViewEvaluationCtx newInstanceFromCurrentContext()
	{
		final Properties ctx = Env.getCtx();

		return _builder()
				.loggedUserId(Env.getLoggedUserIdIfExists(ctx))
				.adLanguage(Env.getAD_Language(ctx))
				.timeZone(UserSession.getTimeZoneOrSystemDefault())
				.permissionsKey(UserRolePermissionsKey.fromContext(ctx))
				.build();
	}

	@Getter
	private final Optional<UserId> loggedUserId;
	@Getter
	private final String adLanguage;
	@Getter
	private final ZoneId timeZone;
	@Getter
	private final UserRolePermissionsKey permissionsKey;

	private transient Evaluatee _evaluatee; // lazy
	private transient JSONOptions _jsonOptions; // lazy

	@Builder(builderMethodName = "_builder")
	private ViewEvaluationCtx(
			@NonNull final Optional<UserId> loggedUserId,
			@NonNull final String adLanguage,
			@NonNull final ZoneId timeZone,
			@NonNull final UserRolePermissionsKey permissionsKey)
	{
		this.loggedUserId = loggedUserId;
		this.adLanguage = adLanguage;
		this.timeZone = timeZone;
		this.permissionsKey = permissionsKey;
	}

	public Evaluatee toEvaluatee()
	{
		Evaluatee evaluatee = _evaluatee;
		if (evaluatee == null)
		{
			evaluatee = _evaluatee = createEvaluatee();
		}
		return evaluatee;
	}

	private Evaluatee createEvaluatee()
	{
		return Evaluatees.mapBuilder()
				.put(Env.CTXNAME_AD_User_ID, loggedUserId.map(UserId::getRepoId).orElse(-1))
				.put(Env.CTXNAME_AD_Language, adLanguage)
				.put(AccessSqlStringExpression.PARAM_UserRolePermissionsKey.getName(), permissionsKey.toPermissionsKeyString())
				.build();
	}

	public JSONOptions toJSONOptions()
	{
		JSONOptions jsonOptions = this._jsonOptions;
		if (jsonOptions == null)
		{
			jsonOptions = _jsonOptions = createJSONOptions();
		}
		return jsonOptions;
	}

	private JSONOptions createJSONOptions()
	{
		return JSONOptions.builder()
				.adLanguage(adLanguage)
				.zoneId(timeZone)
				.build();
	}

}
