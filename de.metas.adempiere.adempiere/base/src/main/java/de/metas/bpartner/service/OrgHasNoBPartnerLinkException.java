package de.metas.bpartner.service;

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

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;

public final class OrgHasNoBPartnerLinkException extends AdempiereException
{
	/**
	 *
	 */
	private static final long serialVersionUID = -3576321750973121078L;

	private static final String MSG = "NotExistsBPLinkedforOrgError";

	public final OrgId orgId;

	public OrgHasNoBPartnerLinkException(final int orgId)
	{
		this(OrgId.ofRepoIdOrNull(orgId));
	}

	public OrgHasNoBPartnerLinkException(final OrgId orgId)
	{
		super(buildMsg(orgId, null));
		this.orgId = orgId;
	}

	public OrgHasNoBPartnerLinkException(final int orgId, final String additionalMessage)
	{
		this(OrgId.ofRepoIdOrNull(orgId), additionalMessage);
	}

	public OrgHasNoBPartnerLinkException(final OrgId orgId, final String additionalMessage)
	{
		super(buildMsg(orgId, additionalMessage));
		this.orgId = orgId;
	}

	private static String buildMsg(final OrgId orgId, final String additionalMessage)
	{
		final Properties ctx = Env.getCtx();

		final StringBuilder sb = new StringBuilder();

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		sb.append(msgBL.getMsg(ctx, MSG));

		if (!Check.isEmpty(additionalMessage, true))
		{
			sb.append(" - ").append(additionalMessage);
		}

		if (orgId != null)
		{
			final String orgName = Services.get(IOrgDAO.class).retrieveOrgName(orgId);
			sb.append("(").append(msgBL.translate(ctx, "AD_Org_ID")).append(": ").append(orgName).append(")");
		}

		return sb.toString();
	}
}
