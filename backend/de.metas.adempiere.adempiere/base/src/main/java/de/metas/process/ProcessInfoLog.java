/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package de.metas.process;

import de.metas.common.util.time.SystemTime;
import de.metas.error.AdIssueId;
import lombok.NonNull;
import org.adempiere.util.lang.ITableRecordReference;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Process Info Log (VO)
 *
 * @author Jorg Janke
 * @version $Id: ProcessInfoLog.java,v 1.3 2006/07/30 00:54:44 jjanke Exp $
 */
@SuppressWarnings("serial")
public final class ProcessInfoLog implements Serializable
{
	public static ProcessInfoLog ofMessage(final String message)
	{
		final Timestamp date = SystemTime.asTimestamp();
		final BigDecimal number = null;
		final ITableRecordReference recordReference = null;
		final AdIssueId adIssueId = null;
		final String trxName = null;

		return new ProcessInfoLog(date, number, message, recordReference, adIssueId, trxName);
	}

	public static ProcessInfoLog ofMessageAndTableReference(final String message, @Nullable final ITableRecordReference tableRecordReference, @Nullable final String trxName)
	{
		final Timestamp date = SystemTime.asTimestamp();
		final BigDecimal number = null;
		final AdIssueId adIssueId = null;
		return new ProcessInfoLog(date, number, message, tableRecordReference, adIssueId, trxName);
	}

	/**
	 * Create Process Info Log.
	 *
	 * @param P_Date               Process Date
	 * @param P_Number             Process Number
	 * @param P_Msg                Process Messagre
	 * @param tableRecordReference record reference of a record "altered" during process execution
	 * @param issueId              reference of an issue created during process execution
	 */
	ProcessInfoLog(
			final Timestamp P_Date,
			final BigDecimal P_Number,
			final String P_Msg,
			final ITableRecordReference tableRecordReference,
			final AdIssueId issueId,
			final String trxName)
	{
		this(nextLogId.getAndIncrement(), P_Date, P_Number, P_Msg, tableRecordReference, issueId, trxName);
	}    // ProcessInfoLog

	/**
	 * Create Process Info Log.
	 *
	 * @param Log_ID               Log ID
	 * @param P_Date               Process Date
	 * @param P_Number             Process Number
	 * @param P_Msg                Process message
	 * @param tableRecordReference record reference of a record "altered" during process execution
	 * @param ad_Issue_ID          reference of an issue created during process execution
	 */
	public ProcessInfoLog(
			final int Log_ID,
			final Timestamp P_Date,
			final BigDecimal P_Number,
			final String P_Msg,
			@Nullable final ITableRecordReference tableRecordReference,
			@Nullable final AdIssueId ad_Issue_ID,
			@Nullable final String trxName)
	{
		m_Log_ID = Log_ID;
		m_P_Date = P_Date;
		m_P_Number = P_Number;
		m_P_Msg = P_Msg;
		m_Table_Record_Ref = tableRecordReference;
		m_Ad_Issue_ID = ad_Issue_ID;
		this.trxName = trxName;
		warningMessages = null;
	}


	public ProcessInfoLog(@NonNull final  ProcessInfoLogRequest request)
	{
		m_Log_ID = request.getLog_ID();
		m_P_Date = request.getPDate();
		m_P_Number = request.getP_Number();
		m_P_Msg = request.getP_Msg();
		m_Ad_Issue_ID = request.getAd_Issue_ID();
		m_Table_Record_Ref = request.getTableRecordReference();
		trxName = request.getTrxName();
		warningMessages = request.getWarningMessages();
	}
	private static final AtomicInteger nextLogId = new AtomicInteger(1);

	private final int m_Log_ID;
	private final Timestamp m_P_Date;
	private final BigDecimal m_P_Number;
	private final String m_P_Msg;
	private final List<String> warningMessages;
	private boolean savedInDB = false;

	@Nullable
	private final AdIssueId m_Ad_Issue_ID;

	@Nullable
	private final ITableRecordReference m_Table_Record_Ref;

	@Nullable
	private final String trxName;

	public int getLog_ID()
	{
		return m_Log_ID;
	}

	public Timestamp getP_Date()
	{
		return m_P_Date;
	}

	public BigDecimal getP_Number()
	{
		return m_P_Number;
	}

	public String getP_Msg()
	{
		return m_P_Msg;
	}

	public List<String> getWarningMessages()
	{
		return warningMessages;
	}

	public void markAsSavedInDB()
	{
		savedInDB = true;
	}

	@Nullable
	public ITableRecordReference getTableRecordReference()
	{
		return m_Table_Record_Ref;
	}

	@Nullable
	public AdIssueId getAdIssueId()
	{
		return m_Ad_Issue_ID;
	}

	@Nullable
	public String getTrxName()
	{
		return trxName;
	}

	public boolean isSavedInDB()
	{
		return savedInDB;
	}
}
