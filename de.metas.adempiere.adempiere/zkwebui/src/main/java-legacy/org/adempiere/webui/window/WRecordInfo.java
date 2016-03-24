/******************************************************************************
// * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
package org.adempiere.webui.window;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.adempiere.ad.security.permissions.UserPreferenceLevelConstraint;
import org.adempiere.ad.session.ISessionDAO;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.SimpleListModel;
import org.adempiere.webui.component.Window;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MTable;
import org.compiere.model.MUser;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.NamePair;
import org.slf4j.Logger;
import org.slf4j.Logger;
import org.zkoss.zhtml.Pre;
import org.zkoss.zhtml.Text;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;

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
 * 
 * Zk Port
 * @author Low Heng Sin
 */
public class WRecordInfo extends Window implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8325604065202356483L;

	/**
	 *	Record Info
	 *	@param title title
	 *	@param dse data status event
	 */
	public WRecordInfo (String title, DataStatusEvent dse)
	{
		super ();
		this.setTitle(title);
		this.setAttribute("modal", Boolean.TRUE);
		this.setWidth("500px");
		this.setHeight("400px");
		this.setBorder("normal");
		this.setSizable(true);
		this.setClosable(true);
		
		try
		{
			init ( dynInit(dse, title) );
		}
		catch (Exception e)
		{
			log.error("", e);
		}
		AEnv.showCenterScreen(this);
	}	//	RecordInfo


	private Listbox table = new Listbox();
	private ConfirmPanel confirmPanel = new ConfirmPanel (false);

	/**	Logger			*/
	protected Logger		log = LogManager.getLogger(getClass());
	/** The Data		*/
	private Vector<Vector<String>>	m_data = new Vector<Vector<String>>();
	/** Info			*/
	private StringBuffer	m_info = new StringBuffer();

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
	private void init (boolean showTable) throws Exception
	{

		Div div = new Div();
		div.setStyle("width: 100%; height: 100%");
		Pre pre = new Pre();
		Text text = new Text(m_info.toString());
		text.setParent(pre);
		pre.setParent(div);
		//
		
		Borderlayout layout = new Borderlayout();
		layout.setParent(this);
		layout.setWidth("100%");
		layout.setHeight("100%");
		
		Center center = new Center();
		center.setParent(layout);
		center.setFlex(true);
		if (showTable)
		{
			North north = new North();
			north.setParent(layout);
			north.appendChild(div);
						
			center.appendChild(table);
			table.setWidth("100%");
			table.setVflex(true);
		}
		else
		{
			center.appendChild(div);
		}
		//
		South south = new South();
		south.setParent(layout);
		south.appendChild(confirmPanel);
		
		confirmPanel.addActionListener(Events.ON_CLICK, this);
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
		MUser user = MUser.get(Env.getCtx(), dse.CreatedBy.intValue());
		m_info.append(" ")
			.append(Msg.translate(Env.getCtx(), "CreatedBy"))
			.append(": ").append(user.getName())
			.append(" - ").append(m_dateTimeFormat.format(dse.Created)).append("\n");
		
		if (!dse.Created.equals(dse.Updated) 
			|| !dse.CreatedBy.equals(dse.UpdatedBy))
		{
			if (!dse.CreatedBy.equals(dse.UpdatedBy))
				user = MUser.get(Env.getCtx(), dse.UpdatedBy.intValue());
			m_info.append(" ")
				.append(Msg.translate(Env.getCtx(), "UpdatedBy"))
				.append(": ").append(user.getName())
				.append(" - ").append(m_dateTimeFormat.format(dse.Updated)).append("\n");
		}
		if (dse.Info != null && dse.Info.length() > 0)
			m_info.append("\n (").append(dse.Info).append(")");
		
		//	Title
		if (dse.AD_Table_ID != 0)
		{
			MTable table1 = MTable.get (Env.getCtx(), dse.AD_Table_ID);
			setTitle(title + " - " + table1.getName());
		}

		// Check if we are allowed to view the record's change log
		final UserPreferenceLevelConstraint preferenceLevel = Env.getUserRolePermissions().getPreferenceLevel();
		if (!preferenceLevel.canViewRecordChangeLog())
		{
			return false;
		}
		
		int Record_ID = 0;
		if (dse.Record_ID instanceof Integer)
			Record_ID = ((Integer)dse.Record_ID).intValue();
		else
			log.info("dynInit - Invalid Record_ID=" + dse.Record_ID);
		if (Record_ID == 0)
			return false;
		
		//	Data
		String sql = "SELECT AD_Column_ID, Updated, UpdatedBy, OldValue, NewValue "
			+ "FROM AD_ChangeLog "
			+ "WHERE AD_Table_ID=? AND Record_ID=? "
			+ "ORDER BY Updated DESC";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, dse.AD_Table_ID);
			pstmt.setInt (2, Record_ID);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				addLine (rs.getInt(1), rs.getTimestamp(2), rs.getInt(3),
					rs.getString(4), rs.getString(5));
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		
		//
		Vector<String> columnNames = new Vector<String>();
		columnNames.add(Msg.translate(Env.getCtx(), "Name"));
		columnNames.add(Msg.translate(Env.getCtx(), "NewValue"));
		columnNames.add(Msg.translate(Env.getCtx(), "OldValue"));
		columnNames.add(Msg.translate(Env.getCtx(), "UpdatedBy"));
		columnNames.add(Msg.translate(Env.getCtx(), "Updated"));
		columnNames.add(Msg.translate(Env.getCtx(), "AD_Column_ID"));
		
		Listhead listhead = new Listhead();
		listhead.setSizable(true);
		
		for (int i = 0; i < columnNames.size(); i++)
		{
			Listheader listheader = new Listheader(columnNames.get(i).replaceAll("[&]", ""));
			listhead.appendChild(listheader);
		}
		
		table.appendChild(listhead);
		SimpleListModel model = new SimpleListModel(m_data);
		table.setItemRenderer(model);
		table.setModel(model);
		
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
		line.add(Msg.translate(Env.getCtx(), column.getColumnName()));
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
					Env.getLanguage(Env.getCtx()), column.getColumnName(),
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
		line.add(user.getName());
		//	Updated
		line.add(m_dateFormat.format(Updated));
		//	Column Name
		line.add(column.getColumnName());

		m_data.add(line);
	}	//	addLine
	
	
	@Override
	public void onEvent(Event event) throws Exception {
		this.detach();
	}

}	// WRecordInfo
