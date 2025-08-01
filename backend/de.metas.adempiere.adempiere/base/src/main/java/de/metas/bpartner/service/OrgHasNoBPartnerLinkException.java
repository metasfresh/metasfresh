package de.metas.bpartner.service;

import de.metas.i18n.IMsgBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import java.io.Serial;
import java.util.Properties;

public final class OrgHasNoBPartnerLinkException extends AdempiereException
{
	/**
	 *
	 */
	@Serial
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
