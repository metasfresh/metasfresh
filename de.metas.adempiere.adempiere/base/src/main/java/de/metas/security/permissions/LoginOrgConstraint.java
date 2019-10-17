package de.metas.security.permissions;

import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

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

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.organization.OrgId;

@Immutable
public final class LoginOrgConstraint extends Constraint
{
	public static final LoginOrgConstraint of(final OrgId loginOrgId, final boolean orgLoginMandatory)
	{
		return new LoginOrgConstraint(loginOrgId, orgLoginMandatory);
	}

	public static final LoginOrgConstraint DEFAULT = new LoginOrgConstraint();

	private static final transient Logger logger = LogManager.getLogger(LoginOrgConstraint.class);

	private final OrgId loginOrgId;
	private final boolean orgLoginMandatory;

	/** Defaults constructor */
	private LoginOrgConstraint()
	{
		this.loginOrgId = null;
		this.orgLoginMandatory = false;
	}

	private LoginOrgConstraint(@Nullable final OrgId loginOrgId, boolean orgLoginMandatory)
	{
		this.loginOrgId = loginOrgId;
		this.orgLoginMandatory = orgLoginMandatory;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();

		if (loginOrgId != null)
		{
			sb.append("@Login_Org_ID@=" + loginOrgId.getRepoId());
		}

		if (sb.length() > 0)
		{
			sb.append(", ");
		}
		sb.append("@IsOrgLoginMandatory@=@" + (orgLoginMandatory ? "Y" : "N") + "@");

		return sb.insert(0, getClass().getSimpleName() + "[")
				.append("]")
				.toString();
	}

	@Override
	public boolean isInheritable()
	{
		return false;
	}

	private OrgId getLoginOrgId()
	{
		return loginOrgId;
	}

	private boolean isOrgLoginMandatory()
	{
		return orgLoginMandatory;
	}

	public boolean isValidOrg(final OrgResource org)
	{
		if (isOrgLoginMandatory() && !org.isRegularOrg())
		{
			logger.debug("Not valid {} because is OrgLoginMandatory is set", org);
			return false;
		}

		// Enforce Login_Org_ID:
		final OrgId loginOrgId = getLoginOrgId();
		if (loginOrgId != null && !OrgId.equals(loginOrgId, org.getOrgId()))
		{
			logger.debug("Not valid {} because is not Login_Org_ID", org);
			return false;
		}

		return true;
	}

	public Predicate<OrgResource> asOrgResourceMatcher()
	{
		return this::isValidOrg;
	}
}
