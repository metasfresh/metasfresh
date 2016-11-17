/**
 * 
 */

package de.metas.letters.report;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */



import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.adempiere.ad.service.IADMessageDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_Message;
import org.compiere.model.MClient;
import org.compiere.model.MNote;
import org.compiere.model.MUser;
import org.compiere.model.Query;

import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.email.impl.EMailSendException;
import de.metas.letters.model.IEMailEditor;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Send BoilerPlate to selected contacts
 * @author teo_sarca
 *
 */
public class AD_BoilderPlate_SendToUsers extends JavaProcess
{
	public static final String AD_Message_UserNotifyError = "de.metas.letters.UserNotifyError";
	
	/** From User (sender) */
	private int p_AD_User_ID = -1;
	private int p_AD_BoilerPlate_ID = -1;
	private String p_WhereClause = null;
	private int p_SMTPRetriesNo = 3;

	private MUser m_from = null;
	
	private int m_count_notes = 0;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
				;			
			else if ("AD_User_ID".equals(name))
			{
				p_AD_User_ID = para.getParameterAsInt();
			}
			else if (MADBoilerPlate.COLUMNNAME_AD_BoilerPlate_ID.equals(name))
			{
				p_AD_BoilerPlate_ID = para.getParameterAsInt();
			}
			else if ("WhereClause".equals(name))
			{
				p_WhereClause = para.getParameter().toString();
			}
			else if ("Retry".equals(name))
			{
				p_SMTPRetriesNo = para.getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		m_from = MUser.get(getCtx(), p_AD_User_ID);
		if (m_from == null)
			throw new FillMandatoryException("AD_User_ID");
		//
		if (p_AD_BoilerPlate_ID <= 0)
		{ // metas-ts: handle missing or wrong AD_BoilerPlate_ID
			throw new FillMandatoryException(MADBoilerPlate.COLUMNNAME_AD_BoilerPlate_ID);
		}
		final MADBoilerPlate text = MADBoilerPlate.get(getCtx(), p_AD_BoilerPlate_ID);
		if (text == null) 
		{
			final String msg = "Missing record; AD_BoilerPlate_ID=" + p_AD_BoilerPlate_ID;
			addLog(msg);
			throw new AdempiereException(msg);
		}
		int count_ok = 0;
		int count_all = 0;
		for (MUser user : getUsers())
		{
			if (notify(text, user))
			{
				count_ok++;
			}
			count_all++;
		}
		return "@Sent@ #"+count_ok+"/"+count_all
		+"<br>@AD_Note_ID@ #"+m_count_notes
		;
	}
	
	private List<MUser> getUsers()
	{
		return new Query(getCtx(), MUser.Table_Name, p_WhereClause, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.list();
	}

	
	private boolean notify(final MADBoilerPlate text, final MUser user)
	{
		boolean ok = true;
		try
		{
			notifyEMail(text, user);
		}
		catch (Exception e)
		{
			createNote(text, user, e);
			ok = false;
			if (LogManager.isLevelFine())
				e.printStackTrace();
		}
		return ok;
	}

	private void notifyEMail(final MADBoilerPlate text, final MUser user)
	{
		MADBoilerPlate.sendEMail(new IEMailEditor() {
			@Override
			public Object getBaseObject()
			{
				return user;
			}
			@Override
			public int getAD_Table_ID()
			{
				return user.get_Table_ID();
			}
			@Override
			public int getRecord_ID()
			{
				return user.get_ID();
			}
			@Override
			public EMail sendEMail(MUser from, String toEmail, String subject, Map<String, Object> variables)
			{
				String message = text.getTextSnippetParsed(variables);
				//
				StringTokenizer st = new StringTokenizer(toEmail, " ,;", false);
				String to = st.nextToken();
				MClient client = MClient.get(getCtx(), getAD_Client_ID());
				EMail email = client.createEMail(m_from, to,
						text.getName(),
						message,
						true);
				if (email == null)
				{
					throw new AdempiereException("Cannot create email. Check log.");
				}
				while (st.hasMoreTokens())
					email.addTo(st.nextToken());
				send(email);
				return email;
			}
		});
	}
	
	private void send(EMail email)
	{
		int maxRetries = p_SMTPRetriesNo > 0 ? p_SMTPRetriesNo : 0;
		int count = 0;
		do
		{
			final EMailSentStatus emailSentStatus = email.send();
			count++;
			if (emailSentStatus.isSentOK())
				return;
			// Timeout => retry
			if (emailSentStatus.isSentConnectionError() && maxRetries > 0 && count < maxRetries)
			{
				log.warn("SMTP error: " + emailSentStatus + " [ Retry " + count + " ]");
			}
			else
			{
				throw new EMailSendException(emailSentStatus);
			}
		}
		while(true);
	}
	
	private void createNote(MADBoilerPlate text, MUser user, Exception e)
	{
		final I_AD_Message msg = Services.get(IADMessageDAO.class).retrieveByValue(getCtx(), AD_Message_UserNotifyError);
		if (msg == null)
			throw new AdempiereException("@NotFound@ @AD_Message_ID@ "+AD_Message_UserNotifyError);
		//
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final String reference = msgBL.parseTranslation(getCtx(), "@AD_BoilerPlate_ID@: "+text.get_Translation(MADBoilerPlate.COLUMNNAME_Name))
			+", "+msgBL.parseTranslation(getCtx(), "@AD_User_ID@: "+user.getName())
			//+", "+Msg.parseTranslation(getCtx(), "@AD_PInstance_ID@: "+getAD_PInstance_ID())
		;
		final MNote note = new MNote(getCtx(),
				msg.getAD_Message_ID(),
				p_AD_User_ID,
				user.get_Table_ID(), user.getAD_User_ID(),
				reference,
				e.getLocalizedMessage(),
				get_TrxName());
		note.setAD_Org_ID(0);
		note.saveEx();
		m_count_notes++;
	}
}
