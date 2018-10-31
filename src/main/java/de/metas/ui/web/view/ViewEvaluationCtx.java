package de.metas.ui.web.view;

import java.util.Properties;
import java.util.function.Supplier;

import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.ad.security.impl.AccessSqlStringExpression;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import com.google.common.base.Suppliers;

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
	public static final ViewEvaluationCtx newInstanceFromCurrentContext()
	{
		final Properties ctx = Env.getCtx();

		return ViewEvaluationCtx.builder()
				.loggedUserId(Env.getAD_User_ID(ctx))
				.adLanguage(Env.getAD_Language(ctx))
				.permissionsKey(UserRolePermissionsKey.of(ctx))
				.build();
	}

	@Getter
	private final int loggedUserId;
	@Getter
	private final String adLanguage;
	@Getter
	private final UserRolePermissionsKey permissionsKey;

	private final Supplier<Evaluatee> evaluateeSupplier;

	@Builder
	private ViewEvaluationCtx(
			final int loggedUserId,
			@NonNull final String adLanguage,
			@NonNull final UserRolePermissionsKey permissionsKey)
	{
		this.loggedUserId = loggedUserId;
		this.adLanguage = adLanguage;
		this.permissionsKey = permissionsKey;
		this.evaluateeSupplier = Suppliers.memoize(() -> createEvaluatee());
	}

	private final Evaluatee createEvaluatee()
	{
		return Evaluatees.mapBuilder()
				.put(Env.CTXNAME_AD_User_ID, loggedUserId)
				.put(Env.CTXNAME_AD_Language, adLanguage)
				.put(AccessSqlStringExpression.PARAM_UserRolePermissionsKey.getName(), permissionsKey.toPermissionsKeyString())
				.build();
	}

	public Evaluatee toEvaluatee()
	{
		return evaluateeSupplier.get();
	}
}
