/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin.  All Rights Reserved.                     *
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

package org.adempiere.webui.editor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.webui.component.Combinationbox;
import org.adempiere.webui.event.ContextMenuEvent;
import org.adempiere.webui.event.ContextMenuListener;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.window.WAccountDialog;
import org.adempiere.webui.window.WFieldRecordInfo;
import org.compiere.model.GridField;
import org.compiere.model.MAccountLookup;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

/**
 *
 * @author Low Heng Sin
 *
 */
public class WAccountEditor extends WEditor implements ContextMenuListener
{
	private static final String[] LISTENER_EVENTS = {Events.ON_CLICK, Events.ON_CHANGE, Events.ON_OK};

	private MAccountLookup		m_mAccount;

	private Object m_value;

	private WEditorPopupMenu popupMenu;

	/**	Logger			*/
	private static Logger log = LogManager.getLogger(WAccountEditor.class);

	public WAccountEditor(GridField gridField)
	{
		super(new Combinationbox(), gridField);
		getComponent().setButtonImage("/images/Account10.png");

		m_mAccount = new MAccountLookup (gridField.getVO().getCtx(), gridField.getWindowNo());
		
		popupMenu = new WEditorPopupMenu(false, false, Env.getUserRolePermissions().isShowPreference());
		popupMenu.addMenuListener(this);
		if (gridField != null && gridField.getGridTab() != null)
		{
			WFieldRecordInfo.addMenu(popupMenu);
		}
		getComponent().setContext(popupMenu.getId());
	}

	@Override
	public Combinationbox getComponent() {
		return (Combinationbox) component;
	}

	@Override
	public void setValue(Object value)
	{
		m_value = value;
		getComponent().setText(m_mAccount.getDisplay(value));	//	loads value
		getComponent().setToolTipText(m_mAccount.getDescription());
	}

	@Override
	public Object getValue()
	{
		return m_mAccount.getC_ValidCombination_ID();
	}

	@Override
	public String getDisplay()
	{
		return getComponent().getText();
	}

	/**
	 *	Button - Start Dialog
	 */
	public void cmd_button()
	{
		int C_AcctSchema_ID = Env.getContextAsInt(Env.getCtx(), gridField.getWindowNo(), "C_AcctSchema_ID");
		// Try to get C_AcctSchema_ID from global context - teo_sarca BF [ 1830531 ]
		if (C_AcctSchema_ID <= 0)
		{
			C_AcctSchema_ID = Env.getContextAsInt(Env.getCtx(), "$C_AcctSchema_ID");
		}
		WAccountDialog ad = new WAccountDialog (gridField.getHeader(), m_mAccount, C_AcctSchema_ID);
		//
		Integer newValue = ad.getValue();
		if (newValue == null)
			return;

		Object oldValue = m_value;

		//	set & redisplay
		setValue(newValue);
		ValueChangeEvent changeEvent = new ValueChangeEvent(this, this.getColumnName(), oldValue, newValue);
		fireValueChange(changeEvent);
	}	//	cmd_button

	/**
	 *	Text - try to find Alias or start Dialog
	 */
	public void cmd_text()
	{
		String text = getComponent().getText();
		log.info("Text=" + text);
		if (text == null || text.length() == 0 || text.equals("%"))
		{
			cmd_button();
			return;
		}
		if (!text.endsWith("%"))
			text += "%";
		//
		String sql = "SELECT C_ValidCombination_ID FROM C_ValidCombination "
			+ "WHERE C_AcctSchema_ID=?"
			+ " AND (UPPER(Alias) LIKE ? OR UPPER(Combination) LIKE ?)";
		sql = Env.getUserRolePermissions().addAccessSQL(sql,
			"C_ValidCombination", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		int C_AcctSchema_ID = Env.getContextAsInt(Env.getCtx(), gridField.getWindowNo(), "C_AcctSchema_ID");
		//
		int C_ValidCombination_ID = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_AcctSchema_ID);
			pstmt.setString(2, text.toUpperCase());
			pstmt.setString(3, text.toUpperCase());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				C_ValidCombination_ID = rs.getInt(1);
				if (rs.next())		//	only one
					C_ValidCombination_ID = 0;
			}
			rs.close();
			pstmt.close();
			pstmt = null;
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

		//	We have a Value
		if (C_ValidCombination_ID > 0)
		{
			Integer newValue = new Integer(C_ValidCombination_ID);
			Object oldValue = m_value;
			m_value = newValue;
			ValueChangeEvent changeEvent = new ValueChangeEvent(this, this.getColumnName(), oldValue, newValue);
			fireValueChange(changeEvent);
		}
		else
			cmd_button();
	}	//	actionPerformed

	public void onEvent(Event event)
	{
		if (Events.ON_CHANGE.equals(event.getName()) || Events.ON_OK.equals(event.getName()))
		{
			cmd_text();
		}
		else if (Events.ON_CLICK.equals(event.getName()))
		{
			cmd_button();
		}
	}

	public String[] getEvents()
    {
        return LISTENER_EVENTS;
    }


	@Override
	public boolean isReadWrite() {
		return getComponent().isEnabled();
	}


	@Override
	public void setReadWrite(boolean readWrite) {
		getComponent().setEnabled(readWrite);
	}

	@Override
	public void onMenu(ContextMenuEvent evt) {
		if (WEditorPopupMenu.CHANGE_LOG_EVENT.equals(evt.getContextEvent()))
		{
			WFieldRecordInfo.start(gridField);
		}
	}


}
