/******************************************************************************
 * Copyright (C) 2008 Elaine Tan                                              *
 * Copyright (C) 2008 Idalica Corporation
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.window;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Window;
import org.compiere.model.MRecordAccess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Toolbarbutton;

/**
 *  Record Access Dialog
 *  @author <a href="mailto:elaine.tan@idalica.com">Elaine</a>
 *  @date December 9, 2008
 */
public class WRecordAccessDialog extends Window implements EventListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3591753244744022795L;

	/**
	 * 	Record Access Dialog
	 *	@param owner owner
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 */
	public WRecordAccessDialog(Window parent, int AD_Table_ID, int Record_ID)
	{
		super();
		setTitle(Msg.translate(Env.getCtx(), "RecordAccessDialog"));
		setAttribute("modal", Boolean.TRUE);
		setBorder("normal");		
		setWidth("600px");
		setSizable(true);
		
		log.info("AD_Table_ID=" + AD_Table_ID + ", Record_ID=" + Record_ID);
		m_AD_Table_ID = AD_Table_ID;
		m_Record_ID = Record_ID;
		try
		{
			dynInit();
			jbInit();
		}
		catch (Exception e)
		{
			log.error("", e);
		}
		AEnv.showWindow(this);
	}	//	RecordAccessDialog

	private int				m_AD_Table_ID;
	private int				m_Record_ID;
	private ArrayList<MRecordAccess>	m_recordAccesss = new ArrayList<MRecordAccess>();
	private int				m_currentRow = 0;
	private MRecordAccess	m_currentData = null;
	private Logger			log = LogManager.getLogger(getClass());
	
	private Label roleLabel = new Label(Msg.translate(Env.getCtx(), "AD_Role_ID"));
	private Listbox roleField = null;	
	private Checkbox cbActive = new Checkbox();//Msg.translate(Env.getCtx(), "IsActive"));
	private Checkbox cbExclude = new Checkbox();//Msg.translate(Env.getCtx(), "IsExclude"));
	private Checkbox cbReadOnly = new Checkbox();//Msg.translate(Env.getCtx(), "IsReadOnly"));
	private Checkbox cbDependent = new Checkbox();//Msg.translate(Env.getCtx(), "IsDependentEntities"));
	private Toolbarbutton bDelete = new Toolbarbutton();//AEnv.getButton("Delete");
	private Toolbarbutton bNew = new Toolbarbutton();//AEnv.getButton("New");
	private Label rowNoLabel = new Label();

	private Toolbarbutton bUp = new Toolbarbutton();//AEnv.getButton("Previous");
	private Toolbarbutton bDown = new Toolbarbutton();//AEnv.getButton("Next");

	private ConfirmPanel confirmPanel = new ConfirmPanel(true);

	/**
	 * 	Dynamic Init
	 */
	private void dynInit()
	{
		//	Load Roles
		String sql = Env.getUserRolePermissions().addAccessSQL(
			"SELECT AD_Role_ID, Name FROM AD_Role ORDER BY 2", 
			"AD_Role", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		roleField = new Listbox(DB.getKeyNamePairs(sql, false));
		roleField.setMold("select");
		
		//	Load Record Access for all roles
		sql = "SELECT * FROM AD_Record_Access "
			+ "WHERE AD_Table_ID=? AND Record_ID=? AND AD_Client_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_AD_Table_ID);
			pstmt.setInt(2, m_Record_ID);
			pstmt.setInt(3, Env.getAD_Client_ID(Env.getCtx()));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				m_recordAccesss.add(new MRecordAccess(Env.getCtx(), rs, null));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		log.debug("#" + m_recordAccesss.size());
		setLine(0, false);
	}	//	dynInit

	/**
	 * 	Static Init
	 *	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		bDelete.setImage("/images/Delete16.png");
		bDelete.setTooltiptext(Msg.getMsg(Env.getCtx(), "Delete"));
		bNew.setImage("/images/New16.png");
		bNew.setTooltiptext(Msg.getMsg(Env.getCtx(), "New"));
		bUp.setImage("/images/Previous16.png");
		bUp.setTooltiptext(Msg.getMsg(Env.getCtx(), "Previous"));
		bDown.setImage("/images/Next16.png");
		bDown.setTooltiptext(Msg.getMsg(Env.getCtx(), "Next"));
		
		cbActive.setText(Msg.translate(Env.getCtx(), "IsActive"));
		cbExclude.setText(Msg.translate(Env.getCtx(), "IsExclude"));
		cbReadOnly.setText(Msg.translate(Env.getCtx(), "IsReadOnly"));
		cbDependent.setText(Msg.translate(Env.getCtx(), "IsDependentEntities"));
		
		Grid grid = GridFactory.newGridLayout();
		this.appendChild(grid);
		
		Rows rows = new Rows();
		grid.appendChild(rows);
		
		Row row = new Row();
		rows.appendChild(row);	
		row.appendChild(bUp);
		row.appendChild(new Label());
		row.appendChild(new Label());
		row.appendChild(new Label());
		row.appendChild(new Label());
		row.appendChild(new Label());
		row.appendChild(bNew);
		
		row = new Row();
		rows.appendChild(row);	
		row.appendChild(roleLabel);
		row.appendChild(roleField);
		row.appendChild(cbActive);
		row.appendChild(cbExclude);
		row.appendChild(cbReadOnly);
		row.appendChild(cbDependent);
		row.appendChild(bDelete);
		
		row = new Row();
		rows.appendChild(row);	
		row.appendChild(bDown);
		row.appendChild(new Label());
		row.appendChild(new Label());
		row.appendChild(new Label());
		row.appendChild(new Label());
		row.appendChild(new Label());
		row.appendChild(rowNoLabel);
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("7");
		Div div = new Div();
		div.setAlign("right");
		div.appendChild(confirmPanel);
		row.appendChild(div);
		
		bUp.addEventListener(Events.ON_CLICK, this);
		bDown.addEventListener(Events.ON_CLICK, this);
		bDelete.addEventListener(Events.ON_CLICK, this);
		bNew.addEventListener(Events.ON_CLICK, this);
		confirmPanel.addActionListener(this);
	}	//	jbInit

	/**
	 * 	Set Line
	 *	@param rowDelta delta to current row
	 *	@param newRecord new
	 */
	private void setLine (int rowDelta, boolean newRecord)
	{
		log.debug("delta=" + rowDelta + ", new=" + newRecord
			+ " - currentRow=" + m_currentRow + ", size=" + m_recordAccesss.size());
		int maxIndex = 0;
		//	nothing defined
		if (m_recordAccesss.size() == 0)
		{
			m_currentRow = 0;
			maxIndex = 0;
			newRecord = true;
			setLine(null);
		}
		else if (newRecord)
		{
			m_currentRow = m_recordAccesss.size();
			maxIndex = m_currentRow;
			setLine(null);
		}
		else
		{
			m_currentRow += rowDelta;
			maxIndex = m_recordAccesss.size() - 1;
			if (m_currentRow < 0)
				m_currentRow = 0;
			else if (m_currentRow > maxIndex)
				m_currentRow = maxIndex;
			//
			MRecordAccess ra = (MRecordAccess)m_recordAccesss.get(m_currentRow);
			setLine(ra);
		}
		//	Label
		StringBuffer txt = new StringBuffer();
		if (newRecord)
			txt.append("+");
		txt.append(m_currentRow+1).append("/").append(maxIndex+1);
		rowNoLabel.setText(txt.toString());
		//	set up/down
		bUp.setDisabled(m_currentRow <= 0);
		bDown.setDisabled(m_currentRow >= maxIndex);			
	}	//	setLine

	/**
	 * 	Set Selection
	 *	@param ra record access
	 */
	private void setLine (MRecordAccess ra)
	{
		int AD_Role_ID = 0;
		boolean active = true;
		boolean exclude = true;
		boolean readonly = false;
		boolean dependent = false;
		//
		if (ra != null)
		{
			AD_Role_ID = ra.getAD_Role_ID();
			active = ra.isActive();
			exclude = ra.isExclude();
			readonly = ra.isReadOnly();
			dependent = ra.isDependentEntities();
		}
		cbActive.setSelected(active);
		cbExclude.setSelected(exclude);
		cbReadOnly.setSelected(readonly);
		cbDependent.setSelected(dependent);
		bDelete.setDisabled(ra == null);
		//
		ListItem selection = null;
		for (int i = 0; i < roleField.getItemCount(); i++)
		{
			ListItem pp = roleField.getItemAtIndex(i);
			if (pp != null && (Integer)pp.getValue() != null)
			{
				if(((Integer)pp.getValue()).intValue() == AD_Role_ID)
					selection = pp;
			}
		}
		if (selection != null && ra != null)
		{
			roleField.setSelectedItem(selection);
			m_currentData = ra;
			log.debug("" + ra);
		}
		else
			m_currentData = null;
	}	//	setLine

	/**
	 * 	Action Listener
	 *	@param e event
	 */
	public void onEvent(Event e) throws Exception 
	{
		if (e.getTarget() == bUp)
			setLine(-1, false);
		else if (e.getTarget() == bDown)
			setLine(+1, false);
		else if (e.getTarget() == bNew)
			setLine(0, true);
		else
		{
			if (e.getTarget() == bDelete)
				cmd_delete();
			else if (e.getTarget().getId().equals(ConfirmPanel.A_OK))
			{
				if (!cmd_save())
					return;
			}
			dispose();
		}		
	}

	/**
	 * 	Save Command
	 *	@return true if saved
	 */
	private boolean cmd_save()
	{
		ListItem pp = roleField.getSelectedItem();
		if (pp == null)
			return false;
		int AD_Role_ID = ((Integer)pp.getValue()).intValue();
		//
		boolean isActive = cbActive.isSelected();
		boolean isExclude = cbExclude.isSelected();
		boolean isReadOnly = cbReadOnly.isSelected();
		boolean isDependentEntities = cbDependent.isSelected();
		//
		if (m_currentData == null)
		{
			m_currentData = new MRecordAccess (Env.getCtx(), AD_Role_ID, m_AD_Table_ID, m_Record_ID, null);
			m_recordAccesss.add(m_currentData);
			m_currentRow = m_recordAccesss.size()-1;
		}
		m_currentData.setIsActive(isActive);
		m_currentData.setIsExclude(isExclude);
		m_currentData.setIsReadOnly(isReadOnly);
		m_currentData.setIsDependentEntities(isDependentEntities);
		boolean success = m_currentData.save();
		//
		log.debug("Success=" + success);
		return success;
	}	//	cmd_save

	/**
	 * 	Delete Command
	 *	@return true if deleted
	 */
	private boolean cmd_delete()
	{
		boolean success = false;
		if (m_currentData == null)
			log.error("No data");
		else
		{
			success = m_currentData.delete(true);
			m_currentData = null;
			m_recordAccesss.remove(m_currentRow);
			log.debug("Success=" + success);
		}
		return success;
	}	//	cmd_delete
}
