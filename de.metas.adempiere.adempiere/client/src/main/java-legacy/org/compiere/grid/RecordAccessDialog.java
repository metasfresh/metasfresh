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
package org.compiere.grid;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.adempiere.ad.security.IUserRolePermissions;
import org.compiere.apps.AEnv;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.MRecordAccess;
import org.compiere.swing.CButton;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;


/**
 * 	Record Access Dialog
 *	
 *  @author Jorg Janke
 *  @version $Id: RecordAccessDialog.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public class RecordAccessDialog extends CDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5208621373205009190L;

	/**
	 * 	Record Access Dialog
	 *	@param owner owner
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 */
	public RecordAccessDialog(JFrame owner, int AD_Table_ID, int Record_ID)
	{
		super(owner, Msg.translate(Env.getCtx(), "RecordAccessDialog"));
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
			log.log(Level.SEVERE, "", e);
		}
		AEnv.showCenterWindow(owner, this);
	}	//	RecordAccessDialog

	private int				m_AD_Table_ID;
	private int				m_Record_ID;
	private ArrayList<MRecordAccess>	m_recordAccesss = new ArrayList<MRecordAccess>();
	private int				m_currentRow = 0;
	private MRecordAccess	m_currentData = null;
	private CLogger			log = CLogger.getCLogger(getClass());

	private CPanel centerPanel = new CPanel(new ALayout());
	private BorderLayout mainLayout = new BorderLayout();
	
	private CLabel roleLabel = new CLabel(Msg.translate(Env.getCtx(), "AD_Role_ID"));
	private CComboBox roleField = null;	
	private CCheckBox cbActive = new CCheckBox(Msg.translate(Env.getCtx(), "IsActive"));
	private CCheckBox cbExclude = new CCheckBox(Msg.translate(Env.getCtx(), "IsExclude"));
	private CCheckBox cbReadOnly = new CCheckBox(Msg.translate(Env.getCtx(), "IsReadOnly"));
	private CCheckBox cbDependent = new CCheckBox(Msg.translate(Env.getCtx(), "IsDependentEntities"));
	private CButton bDelete = AEnv.getButton("Delete");
	private CButton bNew = AEnv.getButton("New");
	private JLabel rowNoLabel = new JLabel();

	private CButton bUp = AEnv.getButton("Previous");
	private CButton bDown = AEnv.getButton("Next");

	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();

	/**
	 * 	Dynamic Init
	 */
	private void dynInit()
	{
		//	Load Roles
		String sql = Env.getUserRolePermissions().addAccessSQL(
			"SELECT AD_Role_ID, Name FROM AD_Role ORDER BY 2", 
			"AD_Role", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		roleField = new CComboBox(DB.getKeyNamePairs(sql, false));
		
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
			log.log(Level.SEVERE, sql, e);
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
		log.fine("#" + m_recordAccesss.size());
		setLine(0, false);
	}	//	dynInit

	/**
	 * 	Static Init
	 *	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.getContentPane().setLayout(mainLayout);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		this.getContentPane().add(confirmPanel, BorderLayout.SOUTH);
		//
		centerPanel.add(bUp, new ALayoutConstraint(0,0));
		centerPanel.add(bNew, new ALayoutConstraint(0,6));
		centerPanel.add(roleLabel, new ALayoutConstraint(1,0));
		centerPanel.add(roleField, null);
		centerPanel.add(cbActive, null);
		centerPanel.add(cbExclude, null);
		centerPanel.add(cbReadOnly, null);
		centerPanel.add(cbDependent, null);
		centerPanel.add(bDelete, null);
		centerPanel.add(bDown, new ALayoutConstraint(2,0));
		centerPanel.add(rowNoLabel, new ALayoutConstraint(2,6));
		//
		Dimension size = centerPanel.getPreferredSize();
		size.width = 600;
		centerPanel.setPreferredSize(size);
		//
		bUp.addActionListener(this);
		bDown.addActionListener(this);
		bDelete.addActionListener(this);
		bNew.addActionListener(this);
		confirmPanel.setActionListener(this);
	}	//	jbInit

	/**
	 * 	Set Line
	 *	@param rowDelta delta to current row
	 *	@param newRecord new
	 */
	private void setLine (int rowDelta, boolean newRecord)
	{
		log.fine("delta=" + rowDelta + ", new=" + newRecord
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
			MRecordAccess ra = m_recordAccesss.get(m_currentRow);
			setLine(ra);
		}
		//	Label
		StringBuffer txt = new StringBuffer();
		if (newRecord)
			txt.append("+");
		txt.append(m_currentRow+1).append("/").append(maxIndex+1);
		rowNoLabel.setText(txt.toString());
		//	set up/down
		bUp.setEnabled(m_currentRow > 0);
		bDown.setEnabled(m_currentRow < maxIndex);			
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
		bDelete.setEnabled(ra != null);
		//
		KeyNamePair selection = null;
		for (int i = 0; i < roleField.getItemCount(); i++)
		{
			KeyNamePair pp = (KeyNamePair)roleField.getItemAt(i);
			if (pp.getKey() == AD_Role_ID)
				selection = pp;		
		}
		if (selection != null && ra != null)
		{
			roleField.setSelectedItem(selection);
			m_currentData = ra;
			log.fine("" + ra);
		}
		else
			m_currentData = null;
	}	//	setLine

	/**
	 * 	Action Listener
	 *	@param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bUp)
			setLine(-1, false);
		else if (e.getSource() == bDown)
			setLine(+1, false);
		else if (e.getSource() == bNew)
			setLine(0, true);
		else
		{
			if (e.getSource() == bDelete)
				cmd_delete();
			else if (e.getActionCommand().equals(ConfirmPanel.A_OK))
			{
				if (!cmd_save())
					return;
			}
			dispose();
		}
	}	//	actionPerformed

	/**
	 * 	Save Command
	 *	@return true if saved
	 */
	private boolean cmd_save()
	{
		KeyNamePair pp = (KeyNamePair)roleField.getSelectedItem();
		roleField.setBackground(pp == null);
		if (pp == null)
			return false;
		int AD_Role_ID = pp.getKey();
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
		log.fine("Success=" + success);
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
			log.log(Level.SEVERE, "No data");
		else
		{
			success = m_currentData.delete(true);
			m_currentData = null;
			m_recordAccesss.remove(m_currentRow);
			log.fine("Success=" + success);
		}
		return success;
	}	//	cmd_delete

}	//	RecordAccessDialog
