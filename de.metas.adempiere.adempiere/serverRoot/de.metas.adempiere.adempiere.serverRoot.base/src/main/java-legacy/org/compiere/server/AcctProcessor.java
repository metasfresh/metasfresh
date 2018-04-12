/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.acct.api.IDocFactory;
import org.adempiere.acct.api.IDocMetaInfo;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.acct.Doc;
import org.compiere.model.MAcctProcessor;
import org.compiere.model.MAcctProcessorLog;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

/**
 * Accounting Processor
 *
 * @author Jorg Janke
 * @version $Id: AcctProcessor.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class AcctProcessor extends AdempiereServer
{
	/**
	 * Accounting Processor
	 *
	 * @param model model
	 */
	public AcctProcessor(final MAcctProcessor model)
	{
		super(model, 30);	// 30 seconds delay
		m_model = model;
	}	// AcctProcessor

	/** The Concrete Model */
	private MAcctProcessor m_model = null;
	/** Last Summary */
	private StringBuffer m_summary = new StringBuffer();

	@Override
	protected void doWork()
	{
		m_summary = new StringBuffer();

		//
		// If the accounting module is not enabled,
		// * create a log entry about that (to let the SysAdm know why this processor is doing nothing)
		// * do nothing.
		if (!Services.get(IPostingService.class).isEnabled())
		{
			final MAcctProcessorLog pLog = new MAcctProcessorLog(m_model, "Accounting module not activated. Consider enabling it or disabling this processor.");
			pLog.save();
			return;
		}

		//
		postSession();
		//
		final int no = m_model.deleteLog();
		m_summary.append("Logs deleted=").append(no);
		//
		final MAcctProcessorLog pLog = new MAcctProcessorLog(m_model, m_summary.toString());
		pLog.setReference("#" + String.valueOf(p_runCount) + " - " + TimeUtil.formatElapsed(new Timestamp(p_startWork)));
		pLog.save();
	}	// doWork

	/**
	 * Post Session
	 */
	private void postSession()
	{
		// services
		final IDocFactory docFactory = Services.get(IDocFactory.class);

		final MAcctSchema[] acctSchemas = getAcctSchemas();
		final int adClientId = getAD_Client_ID();

		for (final IDocMetaInfo docMetaInfo : docFactory.getDocMetaInfoList())
		{
			//
			// Skip document types which are not eligible
			if (!isEligible(docMetaInfo))
			{
				continue;
			}

			// SELECT * FROM table
			final String tableName = docMetaInfo.getTableName();
			final StringBuilder sql = new StringBuilder("SELECT * FROM ").append(tableName)
					.append(" WHERE AD_Client_ID=?")
					.append(" AND Processed='Y' AND Posted='N' AND IsActive='Y'")
					.append(" ORDER BY Created");
			//
			final AtomicInteger count = new AtomicInteger(0);
			final AtomicInteger countError = new AtomicInteger(0);
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
				pstmt.setInt(1, adClientId);

				rs = pstmt.executeQuery();
				while (!isInterrupted() && rs.next())
				{
					count.incrementAndGet();
					
					boolean postOk = false; // was the posting ok?
					try
					{
						final Doc<?> doc = docFactory.get(getCtx(), docMetaInfo, acctSchemas, rs, ITrx.TRXNAME_None);
						if (doc == null)
						{
							log.error(getName() + ": No Doc for " + tableName);
							postOk = false;
						}
						else
						{
							final String error = doc.post(false, false);   // post no force/repost
							postOk = (error == null);
						}
					}
					catch (Exception e)
					{
						postOk = false;
						log.error(getName() + ": " + tableName + ": " + e.getLocalizedMessage(), e);
					}
					finally
					{
						if (!postOk)
						{
							countError.incrementAndGet();
						}
					}
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
			if (count.get() > 0)
			{
				m_summary.append(tableName).append("=").append(count);
				if (countError.get() > 0)
					m_summary.append("(Errors=").append(countError).append(")");
				m_summary.append(" - ");
				log.trace(getName() + ": " + m_summary.toString());
			}
			else
			{
				log.trace(getName() + ": " + tableName + " - no work");
			}
		}
	}	// postSession

	/** @return true if is eligible to be accounted */
	private final boolean isEligible(final IDocMetaInfo docMetaInfo)
	{
		// If processor is configured to post only a particular document type,
		// => check it if matches
		final int onlyTableId = m_model.getAD_Table_ID();
		if (onlyTableId > 0 && onlyTableId != docMetaInfo.getAD_Table_ID())
		{
			return false;
		}

		return true; // eligible to be posted
	}

	private final MAcctSchema[] getAcctSchemas()
	{
		final int onlyAcctSchemaId = m_model.getC_AcctSchema_ID();
		if (onlyAcctSchemaId <= 0)
		{
			return MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID());
		}
		else
		{
			// only specific accounting schema
			return new MAcctSchema[] { new MAcctSchema(getCtx(), onlyAcctSchemaId, ITrx.TRXNAME_None) };
		}
	}

	private final int getAD_Client_ID()
	{
		return m_model.getAD_Client_ID();
	}

	/**
	 * Get Server Info
	 *
	 * @return info
	 */
	@Override
	public final String getServerInfo()
	{
		final StringBuffer sumary = this.m_summary;
		return "#" + p_runCount + " - Last=" + (sumary == null ? "-" : sumary.toString());
	}	// getServerInfo

}	// AcctProcessor
