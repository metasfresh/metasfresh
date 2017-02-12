/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.  					  *
 * 																			  *
 * @sponsor www.metas.de													  *
 *****************************************************************************/
package org.compiere.apps;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

import org.adempiere.ad.security.permissions.UserPreferenceLevelConstraint;
import org.adempiere.ad.session.ISessionDAO;
import org.adempiere.ad.validationRule.IValidationRule;
import org.compiere.grid.VTable;
import org.compiere.model.GridField;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MTable;
import org.compiere.model.MUser;
import org.compiere.swing.CDialog;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.NamePair;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 * Change History for field
 * @author Heng Sin Low
 */
public class FieldRecordInfo extends CDialog
{
	private static final long serialVersionUID = -2860082302140462690L;
	
	public static final String CHANGE_LOG_COMMAND = "ChangeLog";
	/** The Menu Icon               */
	private static Icon s_icon = new ImageIcon(org.compiere.Adempiere.class.getResource("images/ChangeLog16.png"));
	
	private int AD_Table_ID;
	private int AD_Column_ID;
	private int Record_ID;

	/**
	 *	Record Info
	 *	@param owner owner
	 *	@param title title
	 *	@param AD_Table_ID
	 *  @param AD_Column_ID
	 *  @param Record_ID
	 */
	public FieldRecordInfo (Frame owner, String title, int AD_Table_ID, int AD_Column_ID, int Record_ID)
	{
		super (owner, title, true);
		
		this.AD_Table_ID = AD_Table_ID;
		this.AD_Column_ID = AD_Column_ID;
		this.Record_ID = Record_ID;
		
		try
		{			
			jbInit ( dynInit(title) );
		}
		catch (Exception e)
		{
			log.error("", e);
		}
		AEnv.positionCenterWindow (owner, this);
		AEnv.showCenterScreen(this);
	}	//	FieldRecordInfo


	private CPanel	mainPanel	= new CPanel (new BorderLayout(0,0));
	private CScrollPane	scrollPane = new CScrollPane ();
	private VTable table = new VTable ();
	private ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(false)
			.build();

	/**	Logger			*/
	protected Logger		log = LogManager.getLogger(getClass());
	/** The Data		*/
	private Vector<Vector<String>>	m_data = new Vector<Vector<String>>();
	
	/** Date Time Format		*/
	private SimpleDateFormat	m_dateTimeFormat = DisplayType.getDateFormat
		(DisplayType.DateTime, Env.getLanguage(Env.getCtx()));
	/** Date Format			*/
	private SimpleDateFormat	m_dateFormat = DisplayType.getDateFormat
		(DisplayType.DateTime, Env.getLanguage(Env.getCtx()));
	/** Number Format		*/
	private DecimalFormat		m_numberFormat = DisplayType.getNumberFormat
		(DisplayType.Number, Env.getLanguage(Env.getCtx()));
	/** Amount Format		*/
	private DecimalFormat		m_amtFormat = DisplayType.getNumberFormat
		(DisplayType.Amount, Env.getLanguage(Env.getCtx()));
	/** Number Format		*/
	private DecimalFormat		m_intFormat = DisplayType.getNumberFormat
		(DisplayType.Integer, Env.getLanguage(Env.getCtx()));

	/**
	 * 	Static Layout
	 *	@throws Exception
	 */
	private void jbInit (boolean showTable) throws Exception
	{
		getContentPane().add(mainPanel);
		//
		if (showTable)
		{
			mainPanel.add (scrollPane, BorderLayout.CENTER);
			scrollPane.getViewport().add(table);
			scrollPane.setPreferredSize(new Dimension(500,100));
		}
		//
		mainPanel.add (confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
	}	//	jbInit
	
	
	/**
	 * 	Dynamic Init
	 *	@param title title
	 *	@return true if table initialized
	 */
	private boolean dynInit(String title)
	{
		//	Title
		if (AD_Table_ID != 0)
		{
			MTable table1 = MTable.get (Env.getCtx(), AD_Table_ID);
			setTitle(title + " - " + table1.getName());
		}

		// Check if we are allowed to view the record's change log
		final UserPreferenceLevelConstraint preferenceLevel = Env.getUserRolePermissions().getPreferenceLevel();
		if (!preferenceLevel.canViewRecordChangeLog())
		{
			return false;
		}
		
		if (Record_ID == 0)
			return false;
		
		//	Data
		String sql = "SELECT AD_Column_ID, Updated, UpdatedBy, OldValue, NewValue "
			+ "FROM AD_ChangeLog "
			+ "WHERE AD_Table_ID=? AND Record_ID=? AND AD_Column_ID=?"
			+ "ORDER BY Updated DESC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, AD_Table_ID);
			pstmt.setInt (2, Record_ID);
			pstmt.setInt (3, AD_Column_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				addLine (rs.getInt(1), rs.getTimestamp(2), rs.getInt(3),
					rs.getString(4), rs.getString(5));
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		
		//
		Vector<String> columnNames = new Vector<String>();
		columnNames.add(Msg.translate(Env.getCtx(), "NewValue"));
		columnNames.add(Msg.translate(Env.getCtx(), "OldValue"));
		columnNames.add(Msg.translate(Env.getCtx(), "UpdatedBy"));
		columnNames.add(Msg.translate(Env.getCtx(), "Updated"));
		DefaultTableModel model = new DefaultTableModel(m_data, columnNames);
		table.setModel(model);
		table.autoSize(false);
		return true;
	}	//	dynInit
	
	/**
	 * 	Add Line
	 *	@param AD_Column_ID column
	 *	@param Updated updated
	 *	@param UpdatedBy user
	 *	@param OldValue old
	 *	@param NewValue new
	 */
	private void addLine (int AD_Column_ID, Timestamp Updated, int UpdatedBy,
		String OldValue, String NewValue)
	{
		Vector<String> line = new Vector<String>();
		//	Column
		MColumn column = MColumn.get (Env.getCtx(), AD_Column_ID);
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
					showOldValue = Msg.getMsg(Env.getCtx(), yes ? "Y" : "N");
				}
				if (NewValue != null)
				{
					boolean yes = NewValue.equals("true") || NewValue.equals("Y");
					showNewValue = Msg.getMsg(Env.getCtx(), yes ? "Y" : "N");
				}
			}
			else if (column.getAD_Reference_ID() == DisplayType.Amount)
			{
				if (OldValue != null)
					showOldValue = m_amtFormat
						.format (new BigDecimal (OldValue));
				if (NewValue != null)
					showNewValue = m_amtFormat
						.format (new BigDecimal (NewValue));
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
			else if (DisplayType.isLookup(column.getAD_Reference_ID ()))
			{
				MLookup lookup = MLookupFactory.get (Env.getCtx(), 0,
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
		MUser user = MUser.get(Env.getCtx(), UpdatedBy);
		line.add(user == null ? "<" + UpdatedBy + ">" : user.getName());
		//	Updated
		line.add(m_dateFormat.format(Updated));

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

	/**
	 * Add change log menu item
	 * @param l
	 * @param popupMenu
	 * @return CMenuItem
	 */
	public static CMenuItem addMenu (ActionListener l, JPopupMenu popupMenu)
	{
		CMenuItem mi = new CMenuItem (Msg.getElement(Env.getCtx(), "AD_ChangeLog_ID"), s_icon);
		mi.setActionCommand(CHANGE_LOG_COMMAND);
		mi.addActionListener(l);
		popupMenu.add(mi);
		return mi;
	}   //  addMenu

	/**
	 * Open field record info dialog
	 * @param mField
	 */
	public static void start(GridField mField) {
		int WindowNo = mField.getWindowNo();
		Frame frame = Env.getWindow(WindowNo);
		new FieldRecordInfo(frame, mField.getColumnName(), mField.getGridTab().getAD_Table_ID(), 
				mField.getAD_Column_ID(), mField.getGridTab().getRecord_ID());
	}
}	// FieldRecordInfo
