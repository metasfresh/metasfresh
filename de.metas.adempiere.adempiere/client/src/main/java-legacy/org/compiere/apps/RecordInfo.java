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
package org.compiere.apps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.adempiere.ad.security.permissions.UserPreferenceLevelConstraint;
import org.adempiere.ad.session.ISessionDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.grid.VTable;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.I_AD_ChangeLog;
import org.compiere.model.I_AD_User;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MTable;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.CTextArea;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.NamePair;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

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
public class RecordInfo extends CDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8984246906639442417L;
	
	// services
	private static final transient Logger log = LogManager.getLogger(RecordInfo.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IUserDAO userDAO = Services.get(IUserDAO.class);


	/**
	 *	Record Info
	 *	@param owner owner
	 *	@param title title
	 *	@param dse data status event
	 */
	public RecordInfo (Frame owner, String title, DataStatusEvent dse)
	{
		super (owner, title, true);
		try
		{
			final boolean showTable = dynInit(dse, title);
			jbInit(showTable);
		}
		catch (Exception e)
		{
			log.error("", e);
		}
		AEnv.positionCenterWindow (owner, this);
	}	//	RecordInfo


	private CPanel	mainPanel	= new CPanel (new BorderLayout(0,0));
	private VTable table = new VTable();
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOK();

	/** The Data		*/
	private Vector<Vector<String>>	m_data = new Vector<Vector<String>>();
	/** Info			*/
	private StringBuffer	m_info = new StringBuffer();

	
	
	/** Date Time Format		*/
	private SimpleDateFormat	m_dateTimeFormat = DisplayType.getDateFormat(DisplayType.DateTime, Env.getLanguage(Env.getCtx()));
	/** Date Format			*/
	private SimpleDateFormat	m_dateFormat = DisplayType.getDateFormat(DisplayType.DateTime, Env.getLanguage(Env.getCtx()));
	/** Number Format		*/
	private DecimalFormat		m_numberFormat = DisplayType.getNumberFormat(DisplayType.Number, Env.getLanguage(Env.getCtx()));
	/** Amount Format		*/
	private DecimalFormat		m_amtFormat = DisplayType.getNumberFormat(DisplayType.Amount, Env.getLanguage(Env.getCtx()));
	/** Number Format		*/
	private DecimalFormat		m_intFormat = DisplayType.getNumberFormat(DisplayType.Integer, Env.getLanguage(Env.getCtx()));

	/**
	 * 	Static Layout
	 *	@throws Exception
	 */
	private void jbInit (boolean showTable) throws Exception
	{
		getContentPane().add(mainPanel);
		CTextArea info = new CTextArea(m_info.toString());
		info.setReadWrite(false);
		info.setOpaque(false);	//	transparent
		info.setForeground(Color.blue);
		info.setBorder(null);
		//
		if (showTable)
		{
			final CScrollPane scrollPane = new CScrollPane();
			mainPanel.add (info, BorderLayout.NORTH);
			mainPanel.add (scrollPane, BorderLayout.CENTER);
			scrollPane.getViewport().add(table);
			scrollPane.setPreferredSize(new Dimension(500,100));
		}
		else
		{
			info.setPreferredSize(new Dimension(400,75));
			mainPanel.add (info, BorderLayout.CENTER);
		}
		//
		mainPanel.add (confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
	}	//	jbInit
	
	
	/**
	 * 	Dynamic Init
	 *	@param dse data status event
	 *	@param title title
	 *	@return true if table initialized
	 */
	private boolean dynInit(DataStatusEvent dse, String title)
	{
		if (dse.CreatedBy == null)
			return false;
		
		//  Info
		final String createdByUserName = getUserName(dse.CreatedBy);
		
		m_info.append(" ")
			.append(msgBL.translate(Env.getCtx(), "CreatedBy"))
			.append(": ").append(createdByUserName)
			.append(" - ").append(m_dateTimeFormat.format(dse.Created)).append("\n");
		
		if (!Check.equals(dse.Created, dse.Updated) || !Check.equals(dse.CreatedBy, dse.UpdatedBy))
		{
			final String updatedByUserName;
			if (!Check.equals(dse.CreatedBy, dse.UpdatedBy))
			{
				updatedByUserName = getUserName(dse.UpdatedBy);
			}
			else
			{
				updatedByUserName = createdByUserName;
			}
			m_info.append(" ")
				.append(msgBL.translate(Env.getCtx(), "UpdatedBy"))
				.append(": ").append(updatedByUserName)
				.append(" - ").append(m_dateTimeFormat.format(dse.Updated)).append("\n");
		}
		
		if (!Check.isEmpty(dse.Info, true))
		{
			m_info.append("\n (").append(dse.Info).append(")");
		}
		
		//	Title
		if (dse.AD_Table_ID > 0)
		{
			final MTable table = MTable.get (Env.getCtx(), dse.AD_Table_ID);
			setTitle(title + " - " + table.getName());
		}

		// Check if we are allowed to view the record's change log
		final UserPreferenceLevelConstraint preferenceLevel = Env.getUserRolePermissions().getPreferenceLevel();
		if (!preferenceLevel.canViewRecordChangeLog())
		{
			return false;
		}

		//
		// Record_ID
		int Record_ID = 0;
		if (dse.Record_ID instanceof Integer)
		{
			Record_ID = ((Integer)dse.Record_ID).intValue();
		}
		else
		{
			log.info("dynInit - Invalid Record_ID=" + dse.Record_ID);
		}
		if (Record_ID <= 0)
		{
			return false;
		}
		
		//
		//	Data
		final String sql = "SELECT AD_Column_ID, Updated, UpdatedBy, OldValue, NewValue "
			+ "FROM "+I_AD_ChangeLog.Table_Name
			+ " WHERE AD_Table_ID=? AND Record_ID=? "
			+ " ORDER BY Updated DESC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, ITrx.TRXNAME_None);
			pstmt.setInt (1, dse.AD_Table_ID);
			pstmt.setInt (2, Record_ID);
			rs = pstmt.executeQuery ();
			while (rs.next())
			{
				addLine (rs.getInt(1), rs.getTimestamp(2), rs.getInt(3), rs.getString(4), rs.getString(5));
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		//
		final Vector<String> columnNames = new Vector<String>();
		columnNames.add(msgBL.translate(Env.getCtx(), "Name"));
		columnNames.add(msgBL.translate(Env.getCtx(), "NewValue"));
		columnNames.add(msgBL.translate(Env.getCtx(), "OldValue"));
		columnNames.add(msgBL.translate(Env.getCtx(), "UpdatedBy"));
		columnNames.add(msgBL.translate(Env.getCtx(), "Updated"));
		columnNames.add(msgBL.translate(Env.getCtx(), "AD_Column_ID"));
		DefaultTableModel model = new DefaultTableModel(m_data, columnNames);
		table.setModel(model);
		table.autoSize(false);
		return true;
	}	//	dynInit
	
	private final String getUserName(final Integer userId)
	{
		if (userId == null)
		{
			return "?";
		}

		final Properties ctx = Env.getCtx();
		final I_AD_User user = userDAO.retrieveUser(ctx, userId);
		if (user == null)
		{
			return "<" + userId + ">";
		}
		return user.getName();
	}
	
	/**
	 * 	Add Line
	 *	@param AD_Column_ID column
	 *	@param Updated updated
	 *	@param UpdatedBy user
	 *	@param OldValue old
	 *	@param NewValue new
	 */
	private void addLine (int AD_Column_ID, Timestamp Updated, int UpdatedBy, String OldValue, String NewValue)
	{
		final Vector<String> line = new Vector<String>();
		
		//	Column
		MColumn column = MColumn.get(Env.getCtx(), AD_Column_ID);
		if (column != null)
		{
			line.add(msgBL.translate(Env.getCtx(), column.getColumnName()));
		}
		else
		{
			line.add("-");
		}
		
		//
		if (OldValue != null && OldValue.equals(ISessionDAO.CHANGELOG_NullValue))
			OldValue = null;
		String showOldValue = OldValue;
		if (NewValue != null && NewValue.equals(ISessionDAO.CHANGELOG_NullValue))
			NewValue = null;
		String showNewValue = NewValue;
		//
		try
		{
			if (DisplayType.isText (column.getAD_Reference_ID ()))
				;
			else if (column.getAD_Reference_ID() == DisplayType.YesNo)
			{
				if (OldValue != null)
				{
					boolean yes = OldValue.equals("true") || OldValue.equals("Y");
					showOldValue = msgBL.getMsg(Env.getCtx(), yes ? "Y" : "N");
				}
				if (NewValue != null)
				{
					boolean yes = NewValue.equals("true") || NewValue.equals("Y");
					showNewValue = msgBL.getMsg(Env.getCtx(), yes ? "Y" : "N");
				}
			}
			else if (column.getAD_Reference_ID() == DisplayType.Amount)
			{
				if (OldValue != null)
					showOldValue = m_amtFormat.format (new BigDecimal (OldValue));
				if (NewValue != null)
					showNewValue = m_amtFormat.format (new BigDecimal (NewValue));
			}
			else if (column.getAD_Reference_ID() == DisplayType.Integer)
			{
				if (OldValue != null)
					showOldValue = m_intFormat.format (new Integer (OldValue));
				if (NewValue != null)
					showNewValue = m_intFormat.format (new Integer (NewValue));
			}
			else if (DisplayType.isNumeric (column.getAD_Reference_ID ()))
			{
				if (OldValue != null)
					showOldValue = m_numberFormat.format (new BigDecimal (OldValue));
				if (NewValue != null)
					showNewValue = m_numberFormat.format (new BigDecimal (NewValue));
			}
			else if (column.getAD_Reference_ID() == DisplayType.Date)
			{
				if (OldValue != null)
					showOldValue = m_dateFormat.format (Timestamp.valueOf (OldValue));
				if (NewValue != null)
					showNewValue = m_dateFormat.format (Timestamp.valueOf (NewValue));
			}
			else if (column.getAD_Reference_ID() == DisplayType.DateTime)
			{
				if (OldValue != null)
					showOldValue = m_dateTimeFormat.format (Timestamp.valueOf (OldValue));
				if (NewValue != null)
					showNewValue = m_dateTimeFormat.format (Timestamp.valueOf (NewValue));
			}
			else if (DisplayType.isLookup(column.getAD_Reference_ID()))
			{
				final int windowNo = Env.getWindowNo(getParent()); // metas: 03090: use WindowNo when creating the lookup 
				MLookup lookup = MLookupFactory.get (Env.getCtx(), windowNo,
					AD_Column_ID, column.getAD_Reference_ID(),
					column.getColumnName(),
					column.getAD_Reference_Value_ID(),
					column.isParent(), IValidationRule.AD_Val_Rule_ID_Null);
				if (OldValue != null)
				{
					Object key = OldValue; 
					NamePair pp = lookup.get(key);
					if (pp != null)
						showOldValue = pp.getName();
				}
				if (NewValue != null)
				{
					Object key = NewValue; 
					NamePair pp = lookup.get(key);
					if (pp != null)
						showNewValue = pp.getName();
				}
			}
			else if (DisplayType.isLOB (column.getAD_Reference_ID ()))
				;
		}
		catch (Exception e)
		{
			log.warn(OldValue + "->" + NewValue, e);
		}
		//
		line.add(showNewValue);
		line.add(showOldValue);
		//	UpdatedBy
		final String updatedByUserName = getUserName(UpdatedBy);
		line.add(updatedByUserName);
		//	Updated
		line.add(m_dateFormat.format(Updated));
		//	Column Name
		line.add(column.getColumnName());

		m_data.add(line);
	}	//	addLine
	
	
	/**
	 *	ActionListener
	 *  @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		dispose();
	}	//	actionPerformed

}	// RecordInfo
