/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                           *
 * http://www.adempiere.org                                            *
 *                                                                     *
 * Copyright (C) Carlos Ruiz - globalqss                               *
 * Copyright (C) Contributors                                          *
 *                                                                     *
 * This program is free software; you can redistribute it and/or       *
 * modify it under the terms of the GNU General Public License         *
 * as published by the Free Software Foundation; either version 2      *
 * of the License, or (at your option) any later version.              *
 *                                                                     *
 * This program is distributed in the hope that it will be useful,     *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of      *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
 * GNU General Public License for more details.                        *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with this program; if not, write to the Free Software         *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
 * MA 02110-1301, USA.                                                 *
 *                                                                     *
 * Contributors:                                                       *
 * - Carlos Ruiz - globalqss                                           *
 *                                                                     *
 * Sponsors:                                                           *
 * - Company (http://www.globalqss.com)                                *
 ***********************************************************************/

package org.adempiere.process;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.acct.api.IDocFactory;
import org.adempiere.acct.api.IDocMetaInfo;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MCost;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Trx;

/**
 * Client Accounting Processor
 *
 * @author Carlos Ruiz
 */
public class ClientAcctProcessor extends SvrProcess
{
	// services
	private final transient IPostingService postingService = Services.get(IPostingService.class);

	/* The Accounting Schema */
	private int p_C_AcctSchema_ID;
	/* The Table */
	private int p_AD_Table_ID;

	/** Last Summary */
	private StringBuilder m_summary = new StringBuilder();
	/** Client info */
	private MClient m_client = null;
	/** Accounting Schema */
	private MAcctSchema[] m_ass = null;

	/**
	 * Prepare
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_AcctSchema_ID"))
				p_C_AcctSchema_ID = para[i].getParameterAsInt();
			else if (name.equals("AD_Table_ID"))
				p_AD_Table_ID = para[i].getParameterAsInt();
			else
				log.error("Unknown Parameter: " + name);
		}
	}	// prepare

	/**
	 * Process
	 *
	 * @return info
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("C_AcctSchema_ID=" + p_C_AcctSchema_ID + ", AD_Table_ID=" + p_AD_Table_ID);

		if (!postingService.isClientAccountingEnabled())
		{
			throw new AdempiereException("@ClientAccountingNotEnabled@");
		}

		m_client = MClient.get(getCtx(), getAD_Client_ID());

		if (p_C_AcctSchema_ID == 0)
			m_ass = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID());
		else
			// only specific accounting schema
			m_ass = new MAcctSchema[] { new MAcctSchema(getCtx(), p_C_AcctSchema_ID, get_TrxName()) };

		postSession();
		MCost.create(m_client);

		addLog(m_summary.toString());

		return "@OK@";
	}	// doIt

	/**
	 * Post Session
	 */
	private void postSession()
	{
		final IDocFactory docFactory = Services.get(IDocFactory.class);
		for (final IDocMetaInfo docMetaInfo : docFactory.getDocMetaInfoList())
		{
			final int AD_Table_ID = docMetaInfo.getAD_Table_ID();
			final String TableName = docMetaInfo.getTableName();

			// Post only special documents
			if (p_AD_Table_ID != 0
					&& p_AD_Table_ID != AD_Table_ID)
			{
				continue;
			}

			// SELECT * FROM table
			StringBuilder sql = new StringBuilder("SELECT * FROM ").append(TableName)
					.append(" WHERE AD_Client_ID=?")
					.append(" AND Processed='Y' AND Posted='N' AND IsActive='Y'")
					.append(" ORDER BY Created");
			//
			int count = 0;
			int countError = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
				pstmt.setInt(1, getAD_Client_ID());
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					count++;
					boolean ok = true;
					// Run every posting document in own transaction
					String innerTrxName = Trx.createTrxName("CAP");
					Trx innerTrx = Trx.get(innerTrxName, true);
					try
					{
						Doc doc = docFactory.get(getCtx(), docMetaInfo, m_ass, rs, innerTrxName);
						if (doc == null)
						{
							log.error(getName() + ": No Doc for " + TableName);
							ok = false;
						}
						else
						{
							String error = doc.post(false, false);   // post no force/repost
							ok = (error == null);
						}
					}
					catch (Exception e)
					{
						log.error(getName() + ": " + TableName + ": " + e.getLocalizedMessage(), e); // metas: improve error logging
						ok = false;
					}
					finally
					{
						if (ok)
							innerTrx.commit();
						else
							innerTrx.rollback();
						innerTrx.close();
						innerTrx = null;
					}
					if (!ok)
						countError++;
				}
			}
			catch (Exception e)
			{
				log.error(sql.toString(), e);
			}
			finally
			{
				DB.close(rs, pstmt);
			}

			//
			if (count > 0)
			{
				m_summary.append(TableName).append("=").append(count);
				if (countError > 0)
					m_summary.append("(Errors=").append(countError).append(")");
				m_summary.append(" - ");
				log.trace(getName() + ": " + m_summary.toString());
			}
			else
				log.trace(getName() + ": " + TableName + " - no work");
		}
	}	// postSession

}	// ClientAcctProcessor
