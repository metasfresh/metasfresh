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
package org.compiere.server;

import com.google.common.collect.ImmutableList;
import de.metas.event.Topic;
import de.metas.i18n.Msg;
import de.metas.impexp.spreadsheet.excel.ExcelFormats;
import de.metas.impexp.spreadsheet.excel.JdbcExcelExporter;
import de.metas.impexp.spreadsheet.service.SpreadsheetExporterService;
import de.metas.logging.MetasfreshLastError;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.MAlert;
import org.compiere.model.MAlertProcessor;
import org.compiere.model.MAlertProcessorLog;
import org.compiere.model.MAlertRule;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.ValueNamePair;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Alert Processor
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>FR [ 1894573 ] Alert Processor Improvements
 * <li>FR [ 2453882 ] Alert Processor : attached file name improvement
 * @author Kubotti
 * <li>BF [ 2785633 ] Adding a Notice in Alert Processor
 * @version $Id: AlertProcessor.java,v 1.4 2006/07/30 00:53:33 jjanke Exp $
 */
public class AlertProcessor extends AdempiereServer
{
	private static final Topic USER_NOTIFICATIONS_TOPIC = Topic.distributed("de.metas.alerts.UserNotifications");

	public AlertProcessor(MAlertProcessor model)
	{
		super(model, 180);        // 3 minute delay
		m_model = model;
	}    // AlertProcessor

	/**
	 * The Concrete Model
	 */
	private MAlertProcessor m_model = null;
	/**
	 * Last Summary
	 */
	private StringBuffer m_summary = new StringBuffer();
	/**
	 * Last Error Msg
	 */
	private StringBuffer m_errors = new StringBuffer();

	/**
	 * Work
	 */
	@Override
	protected void doWork()
	{
		m_summary = new StringBuffer();
		m_errors = new StringBuffer();
		//
		int count = 0;
		int countError = 0;
		MAlert[] alerts = m_model.getAlerts(false);
		for (int i = 0; i < alerts.length; i++)
		{
			if (!processAlert(alerts[i]))
				countError++;
			count++;
		}
		//
		String summary = "Total=" + count;
		if (countError > 0)
			summary += ", Not processed=" + countError;
		summary += " - ";
		m_summary.insert(0, summary);
		//
		int no = m_model.deleteLog();
		m_summary.append("Logs deleted=").append(no);
		//
		MAlertProcessorLog pLog = new MAlertProcessorLog(m_model, m_summary.toString());
		pLog.setReference("#" + getRunCount() + " - " + TimeUtil.formatElapsed(getStartWork()));
		pLog.setTextMsg(m_errors.toString());
		pLog.save();
	}    // doWork

	private boolean isSendAttachmentsAsXls()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue("ALERT_SEND_ATTACHMENT_AS_XLS", true, Env.getAD_Client_ID(getCtx()));
	}

	private boolean processAlert(MAlert alert)
	{
		if (!alert.isValid())
			return false;
		log.info("{}", alert);

		StringBuffer message = new StringBuffer(alert.getAlertMessage())
				.append(Env.NL);
		//
		boolean valid = true;
		boolean processed = false;
		ArrayList<File> attachments = new ArrayList<>();
		MAlertRule[] rules = alert.getRules(false);
		for (int i = 0; i < rules.length; i++)
		{
			if (i > 0)
			{
				message.append(Env.NL);
			}

			MAlertRule rule = rules[i];
			if (!rule.isValid())
				continue;
			log.debug("{}", rule);

			// Pre
			final String sqlPreProcessing = rule.getPreProcessing();
			if (!Check.isEmpty(sqlPreProcessing, true))
			{
				int no = DB.executeUpdateAndIgnoreErrorOnFail(sqlPreProcessing, false, ITrx.TRXNAME_ThreadInherited);
				if (no == -1)
				{
					ValueNamePair error = MetasfreshLastError.retrieveError();
					rule.setErrorMsg("Pre=" + error.getName());
					m_errors.append("Pre=" + error.getName());
					rule.setIsValid(false);
					rule.save();
					valid = false;
					break;
				}
			}    // Pre

			// The processing
			try
			{
				final String sql = rule.getSql(true);
				String text = null;
				if (isSendAttachmentsAsXls())
				{
					text = getExcelReport(rule, sql, attachments);
				}
				else
				{
					text = getPlainTextReport(rule, sql, ITrx.TRXNAME_ThreadInherited, attachments);
				}
				if (!Check.isEmpty(text, true))
				{
					message.append(text);
					processed = true;
				}
			}
			catch (Exception e)
			{
				rule.setErrorMsg("Select=" + e.getLocalizedMessage());
				m_errors.append("Select=" + e.getLocalizedMessage());
				rule.setIsValid(false);
				rule.save();
				valid = false;
				break;
			}

			// Post
			final String sqlPostProcessing = rule.getPostProcessing();
			if (!Check.isEmpty(sqlPostProcessing, true))
			{
				int no = DB.executeUpdateAndIgnoreErrorOnFail(sqlPostProcessing, false, ITrx.TRXNAME_ThreadInherited);
				if (no == -1)
				{
					ValueNamePair error = MetasfreshLastError.retrieveError();
					rule.setErrorMsg("Post=" + error.getName());
					m_errors.append("Post=" + error.getName());
					rule.setIsValid(false);
					rule.save();
					valid = false;
					break;
				}
			}    // Post
		}    // for all rules

		// Update header if error
		if (!valid)
		{
			alert.setIsValid(false);
			alert.save();
			return false;
		}

		// Nothing to report
		if (!processed)
		{
			m_summary.append(alert.getName()).append("=No Result - ");
			return true;
		}

		//
		// Report footer - Date Generated
		DateFormat df = DisplayType.getDateFormat(DisplayType.DateTime);
		message.append("\n\n");
		message.append(Msg.translate(getCtx(), "Date")).append(" : ")
				.append(df.format(new Timestamp(System.currentTimeMillis())));

		final Set<UserId> userIds = alert.getRecipientUsers();
		notifyUsers(userIds, alert.getAlertSubject(), message.toString(), attachments);

		m_summary.append(alert.getName()).append(" - ");
		return valid;
	}    // processAlert

	private void notifyUsers(
			final Set<UserId> userIds,
			final String subject,
			final String message,
			final Collection<File> attachments)
	{
		if (userIds.isEmpty())
		{
			return;
		}

		final List<FileSystemResource> attachmentResources = attachments.stream()
				.map(FileSystemResource::new)
				.collect(ImmutableList.toImmutableList());

		final INotificationBL userNotificationsService = Services.get(INotificationBL.class);
		userIds.stream()
				.filter(Objects::nonNull)
				.map(userId -> UserNotificationRequest.builder()
						.topic(USER_NOTIFICATIONS_TOPIC)
						.recipientUserId(userId)
						.subjectPlain(subject)
						.contentPlain(message)
						.attachments(attachmentResources)
						.build())
				.forEach(userNotificationsService::sendAfterCommit);
	}

	/**
	 * Get Plain Text Report (old functionality)
	 *
	 * @param rule        (ignored)
	 * @param sql         sql select
	 * @param trxName     transaction
	 * @param attachments (ignored)
	 * @return list of rows & values
	 * @throws Exception
	 * @deprecated
	 */
	@Deprecated
	private String getPlainTextReport(MAlertRule rule, String sql, String trxName, Collection<File> attachments)
			throws Exception
	{
		StringBuffer result = new StringBuffer();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Exception error = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			rs = pstmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			while (rs.next())
			{
				result.append("------------------").append(Env.NL);
				for (int col = 1; col <= meta.getColumnCount(); col++)
				{
					result.append(meta.getColumnLabel(col)).append(" = ");
					result.append(rs.getString(col));
					result.append(Env.NL);
				}    // for all columns
			}
			if (result.length() == 0)
				log.debug("No rows selected");
		}
		catch (Throwable e)
		{
			log.error(sql, e);
			if (e instanceof Exception)
				error = (Exception)e;
			else
				error = new Exception(e.getMessage(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// Error occurred
		if (error != null)
			throw new Exception("(" + sql + ") " + Env.NL
					+ error.getLocalizedMessage());

		return result.toString();
	}

	/**
	 * Get Excel Report
	 *
	 * @param rule
	 * @param sql
	 * @param trxName
	 * @param attachments
	 * @return summary message to be added into mail content
	 * @throws Exception
	 */
	private String getExcelReport(final MAlertRule rule, final String sql, final Collection<File> attachments) throws Exception
	{
		final SpreadsheetExporterService spreadsheetExporterService = SpringContextHolder.instance.getBean(SpreadsheetExporterService.class);

		final File file = rule.createReportFile(ExcelFormats.getDefaultFileExtension());

		final JdbcExcelExporter jdbcExcelExporter = JdbcExcelExporter.builder()
				.ctx(getCtx())
				.resultFile(file)
				.build();

		spreadsheetExporterService.processDataFromSQL(sql, jdbcExcelExporter);

		if(jdbcExcelExporter.isNoDataAddedYet())
		{
			return null;
		}

		attachments.add(file);

		final String msg = rule.getName() + " (@SeeAttachment@ " + file.getName() + ")" + Env.NL;
		return Msg.parseTranslation(Env.getCtx(), msg);
	}

	/**
	 * Get Server Info
	 *
	 * @return info
	 */
	@Override
	public String getServerInfo()
	{
		return "#" + getRunCount() + " - Last=" + m_summary.toString();
	}    // getServerInfo
}
