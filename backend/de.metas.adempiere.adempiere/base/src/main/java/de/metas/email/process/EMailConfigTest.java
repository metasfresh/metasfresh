/**
 *
 */
package de.metas.email.process;

import org.adempiere.service.ClientId;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.service.IClientDAO;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_MailConfig;

import de.metas.email.EMail;
import de.metas.email.EMailAddress;
import de.metas.email.EMailCustomType;
import de.metas.email.EMailSentStatus;
import de.metas.email.MailService;
import de.metas.email.mailboxes.ClientEMailConfig;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class EMailConfigTest extends JavaProcess
{
	private final IUserBL usersService = Services.get(IUserBL.class);
	private final IClientDAO clientsRepo = Services.get(IClientDAO.class);
	private final MailService mailService = Adempiere.getBean(MailService.class);

	public static final String PARA_AD_Client_ID = I_AD_MailConfig.COLUMNNAME_AD_Client_ID;
	public static final String PARA_AD_Org_ID = I_AD_MailConfig.COLUMNNAME_AD_Org_ID;
	public static final String PARA_AD_Process_ID = I_AD_MailConfig.COLUMNNAME_AD_Process_ID;
	public static final String PARA_CustomType = I_AD_MailConfig.COLUMNNAME_CustomType;
	public static final String PARA_From_User_ID = "From_User_ID";
	public static final String PARA_EMail_To = "EMail_To";
	public static final String PARA_Subject = "Subject";
	public static final String PARA_Message = "Message";
	public static final String PARA_IsHtml = "IsHtml";

	private ClientId p_AD_Client_ID;
	private OrgId p_AD_Org_ID;
	private AdProcessId p_AD_Process_ID;
	private EMailCustomType p_CustomType;
	private UserId p_From_User_ID;
	private EMailAddress p_EMail_To;
	private String p_Subject;
	private String p_Message;
	private boolean p_IsHtml;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{

			}
			else if (name.equals(PARA_AD_Client_ID))
			{
				p_AD_Client_ID = para.getParameterAsRepoId(ClientId::ofRepoIdOrNull);
			}
			else if (name.equals(PARA_AD_Org_ID))
			{
				p_AD_Org_ID = para.getParameterAsRepoId(OrgId::ofRepoIdOrNull);
			}
			else if (name.equals(PARA_AD_Process_ID))
			{
				p_AD_Process_ID = para.getParameterAsRepoId(AdProcessId::ofRepoIdOrNull);
			}
			else if (name.equals(PARA_CustomType))
			{
				p_CustomType = EMailCustomType.ofNullableCode(para.getParameterAsString());
			}
			else if (name.equals(PARA_From_User_ID))
			{
				p_From_User_ID = para.getParameterAsRepoId(UserId::ofRepoIdOrNull);
			}
			else if (name.equals(PARA_EMail_To))
			{
				p_EMail_To = EMailAddress.ofString(para.getParameterAsString());
			}
			else if (name.equals(PARA_Subject))
			{
				p_Subject = (String)para.getParameter();
			}
			else if (name.equals(PARA_Message))
			{
				p_Message = (String)para.getParameter();
			}
			else if (name.equals(PARA_IsHtml))
			{
				p_IsHtml = para.getParameterAsBoolean();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		testSend();

		// final MClient client = MClient.get(getCtx(), p_AD_Client_ID);
		// client.sendEMail(p_EMail_To, p_Subject, p_Message, null, p_IsHtml);

		return "Done";
	}

	public UserEMailConfig getUserEMailConfig()
	{
		return p_From_User_ID != null ? usersService.getEmailConfigById(p_From_User_ID) : null;
	}

	private void testSend()
	{
		final ClientEMailConfig tenantEmailConfig = clientsRepo.getEMailConfigById(p_AD_Client_ID);
		final UserEMailConfig userEmailConfig = getUserEMailConfig();

		final Mailbox mailbox = mailService.findMailBox(
				tenantEmailConfig,
				p_AD_Org_ID,
				p_AD_Process_ID,
				null,  // C_DocType - Task FRESH-203. This shall work as before
				p_CustomType)
				.mergeFrom(userEmailConfig);
		addLog("Using configuration: " + mailbox);

		final EMail email = mailService.createEMail(mailbox, p_EMail_To, p_Subject, p_Message, p_IsHtml);
		addLog("EMail: " + email);

		final EMailSentStatus emailSentStatus = email.send();
		if (!emailSentStatus.isSentOK())
		{
			addLog("Send error: " + emailSentStatus.getSentMsg());
		}
	}
}
