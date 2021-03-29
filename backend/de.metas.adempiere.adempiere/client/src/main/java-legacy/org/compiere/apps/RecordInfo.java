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
package org.compiere.apps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.swing.table.DefaultTableModel;

import org.adempiere.ad.table.ComposedRecordId;
import org.adempiere.ad.table.RecordChangeLog;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.ad.table.RecordChangeLogRepository;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.grid.VTable;
import org.compiere.model.I_AD_Table;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.CTextArea;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.security.permissions.UserPreferenceLevelConstraint;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Record Info (Who) With Change History
 * <p>
 * Change log:
 * <ul>
 * <li>2007-02-26 - teo_sarca - [ 1666598 ] RecordInfo shows ColumnName instead of name
 * </ul>
 *
 * @author Jorg Janke
 * @version $Id: RecordInfo.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
@SuppressWarnings("serial")
public class RecordInfo extends CDialog
{
	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IUserDAO usersRepo = Services.get(IUserDAO.class);

	private final Language language = Env.getLanguage();
	private final SimpleDateFormat dateTimeFormat = DisplayType.getDateFormat(DisplayType.DateTime, language);
	private final HashMap<UserId, String> userNamesById = new HashMap<>();

	public RecordInfo(
			final Frame owner,
			final int adTableId,
			@NonNull final ComposedRecordId recordId)
	{
		super(owner, "", /* modal */true);

		try
		{
			final RecordChangeLogRepository tableRecordChangeLogRepo = Adempiere.getBean(RecordChangeLogRepository.class);
			final RecordChangeLog changeLog = tableRecordChangeLogRepo.getByRecord(adTableId, recordId);

			final String summaryInfo = buildSummaryInfo(changeLog);
			final DefaultTableModel logEntriesTableModel = createLogEntriesTableModel(changeLog);

			setTitle(buildTitle(AdTableId.ofRepoIdOrNull(adTableId)));
			initLayout(summaryInfo, logEntriesTableModel);

			AEnv.positionCenterWindow(owner, this);
		}
		catch (Exception ex)
		{
			dispose();
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}	// RecordInfo

	private void initLayout(
			final String summaryInfo,
			final DefaultTableModel logEntriesTableModel)
	{
		final CPanel mainPanel = new CPanel(new BorderLayout(0, 0));
		getContentPane().add(mainPanel);

		final CTextArea info = new CTextArea(summaryInfo);
		info.setReadWrite(false);
		info.setOpaque(false);	// transparent
		info.setForeground(Color.blue);
		info.setBorder(null);

		//
		if (logEntriesTableModel != null)
		{
			final VTable table = new VTable();
			table.setModel(logEntriesTableModel);
			table.autoSize(false);

			final CScrollPane scrollPane = new CScrollPane();
			mainPanel.add(info, BorderLayout.NORTH);
			mainPanel.add(scrollPane, BorderLayout.CENTER);
			scrollPane.getViewport().add(table);
			scrollPane.setPreferredSize(new Dimension(500, 100));
		}
		else
		{
			info.setPreferredSize(new Dimension(400, 75));
			mainPanel.add(info, BorderLayout.CENTER);
		}

		//
		final ConfirmPanel confirmPanel = ConfirmPanel.newWithOK();
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
	}	// jbInit

	private String buildTitle(final AdTableId adTableId)
	{
		final StringBuilder title = new StringBuilder();

		title.append(msgBL.getMsg(Env.getCtx(), "Who"));

		if (adTableId != null)
		{
			final I_AD_Table table = Services.get(IADTableDAO.class).retrieveTable(adTableId);
			title.append(" - ").append(table.getName());
		}

		return title.toString();
	}

	private DefaultTableModel createLogEntriesTableModel(final RecordChangeLog changeLog)
	{
		// Check if we are allowed to view the record's change log
		final UserPreferenceLevelConstraint preferenceLevel = Env.getUserRolePermissions().getPreferenceLevel();
		if (!preferenceLevel.canViewRecordChangeLog())
		{
			return null;
		}

		final Vector<Vector<Object>> data = toTableRows(changeLog);

		final Vector<Object> columnNames = new Vector<>();
		columnNames.add(msgBL.translate(Env.getCtx(), "Name"));
		columnNames.add(msgBL.translate(Env.getCtx(), "NewValue"));
		columnNames.add(msgBL.translate(Env.getCtx(), "OldValue"));
		columnNames.add(msgBL.translate(Env.getCtx(), "UpdatedBy"));
		columnNames.add(msgBL.translate(Env.getCtx(), "Updated"));
		columnNames.add(msgBL.translate(Env.getCtx(), "ColumnName"));

		return new DefaultTableModel(data, columnNames);
	}

	private Vector<Vector<Object>> toTableRows(final RecordChangeLog log)
	{
		return log.getEntries()
				.stream()
				.sorted(Comparator.comparing(RecordChangeLogEntry::getChangedTimestamp).reversed())
				.map(this::toTableRow)
				.collect(Collectors.toCollection(Vector::new));
	}

	private Vector<Object> toTableRow(final RecordChangeLogEntry logEntry)
	{
		final String adLanguage = language.getAD_Language();

		final Vector<Object> row = new Vector<>();
		row.add(logEntry.getColumnDisplayName().translate(adLanguage)); // Name
		row.add(convertToDisplayValue(logEntry.getValueNew(), logEntry.getDisplayType())); // NewValue
		row.add(convertToDisplayValue(logEntry.getValueOld(), logEntry.getDisplayType())); // OldValue
		row.add(getUserName(logEntry.getChangedByUserId())); // UpdatedBy
		row.add(convertToDateTimeString(TimeUtil.asZonedDateTime(logEntry.getChangedTimestamp()))); // Updated
		row.add(logEntry.getColumnName()); // ColumnName
		return row;
	}

	private String convertToDisplayValue(final Object value, final int displayType)
	{
		if (value == null)
		{
			return "";
		}
		else if (value instanceof String)
		{
			return value.toString();
		}
		else if (value instanceof ITranslatableString)
		{
			return ((ITranslatableString)value).translate(language.getAD_Language());
		}
		else if (value instanceof Number)
		{
			final int numberDisplayType = DisplayType.isNumeric(displayType) ? displayType : DisplayType.Number;
			final DecimalFormat formatter = DisplayType.getNumberFormat(numberDisplayType, language);
			return formatter.format(value);
		}
		else if (DisplayType.isDate(displayType))
		{
			final SimpleDateFormat formatter = DisplayType.getDateFormat(displayType, language);
			return formatter.format(TimeUtil.asDate(value));
		}
		else if (value instanceof Boolean)
		{
			Boolean valueBool = (Boolean)value;
			return msgBL.getTranslatableMsgText(valueBool).translate(language.getAD_Language());
		}
		else if (value instanceof byte[])
		{
			return ""; // don't show LOBs
		}
		else
		{
			return value.toString();
		}
	}

	private String convertToDateTimeString(final ZonedDateTime dateTime)
	{
		if (dateTime == null)
		{
			return "";
		}

		return dateTimeFormat.format(TimeUtil.asDate(dateTime));
	}

	private String buildSummaryInfo(final RecordChangeLog changeLog)
	{
		final StringBuilder info = new StringBuilder();

		//
		// Created / Created By
		final UserId createdBy = changeLog.getCreatedByUserId();
		final ZonedDateTime createdTS = TimeUtil.asZonedDateTime(changeLog.getCreatedTimestamp());
		info.append(" ")
				.append(msgBL.translate(Env.getCtx(), "CreatedBy"))
				.append(": ").append(getUserName(createdBy))
				.append(" - ").append(convertToDateTimeString(createdTS)).append("\n");

		//
		// Last Changed / Last Changed By
		if (changeLog.hasChanges())
		{
			final UserId lastChangedBy = changeLog.getLastChangedByUserId();
			final ZonedDateTime lastChangedTS = TimeUtil.asZonedDateTime(changeLog.getLastChangedTimestamp());
			info.append(" ")
					.append(msgBL.translate(Env.getCtx(), "UpdatedBy"))
					.append(": ").append(getUserName(lastChangedBy))
					.append(" - ").append(convertToDateTimeString(lastChangedTS)).append("\n");
		}

		//
		// TableName / RecordId(s)
		info.append(changeLog.getTableName())
				.append(" (").append(changeLog.getRecordId().toInfoString()).append(")");

		return info.toString();
	}

	private final String getUserName(@Nullable final UserId userId)
	{
		if (userId == null)
		{
			return "?";
		}

		return userNamesById.computeIfAbsent(userId, usersRepo::retrieveUserFullName);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		dispose();
	}	// actionPerformed

}	// RecordInfo
