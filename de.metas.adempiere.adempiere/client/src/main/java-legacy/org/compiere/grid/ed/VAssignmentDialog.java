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
package org.compiere.grid.ed;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.WindowConstants;

import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.search.InfoSchedule;
import org.compiere.model.MResourceAssignment;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import de.metas.uom.LegacyUOMConversionUtils;

/**
 *	Resource Assignment Dialog
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VAssignmentDialog.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VAssignmentDialog extends CDialog
	implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4130085811212412204L;


	/**
	 * 	Assignment Dialog.
	 * 	<pre>
	 * 		Creates a new assignment oor displays an assignment
	 * 		Create new:	(ID == 0)
	 * 			check availability & create assignment
	 * 			(confirmed when order/incoice/timeExpense is processed)
	 * 			alternatively let InfoResource do the assignment
	 * 			return ID
	 * 		Existing assignment: (ID != 0)
	 * 			if confirmed - no change.
	 * 			ability to delete or change assignment
	 * 			return ID
	 * 	</pre>
	 *  @param frame parent
	 *  @param mAssignment Assignment
	 *  @param allowZoom allow to zoom to schedule
	 *  @param allowDelete allow to delete recorde
	 */
	public VAssignmentDialog (Frame frame, MResourceAssignment mAssignment, 
		boolean allowZoom, boolean allowDelete)
	{
		super (frame, Msg.getMsg(Env.getCtx(), "VAssignmentDialog"), true);
		log.info(mAssignment.toString());
		m_mAssignment = mAssignment;
		m_frame = frame;
		try
		{
			jbInit();
			if (!allowZoom)
				confirmPanel.getZoomButton().setVisible(false);
			delete.setVisible(allowDelete);
		}
		catch(Exception e)
		{
			log.error("", e);
		}
		setDisplay();	//	from mAssignment
		//
		AEnv.showCenterScreen(this);
	}	//	VAssignmentDialog

	/**	Assignment						*/
	private MResourceAssignment	m_mAssignment;
	/** Parent frame					*/
	private Frame		m_frame;
	/**	True if setting Value			*/
	private boolean		m_setting = false;
	/**	Logger							*/
	private static Logger log = LogManager.getLogger(VAssignmentDialog.class);
	/**	Lookup with Resource & UOM		*/
	private HashMap<KeyNamePair,KeyNamePair>	m_lookup = new HashMap<KeyNamePair,KeyNamePair>();
	
	//
	private CPanel mainPanel = new CPanel();
	private GridBagLayout mainLayout = new GridBagLayout();
	private CLabel lResource = new CLabel(Msg.translate(Env.getCtx(), "S_Resource_ID"));
	private VComboBox fResource = new VComboBox(getResources());
	private CLabel lDate = new CLabel(Msg.translate(Env.getCtx(), "DateFrom"));
	private VDate fDateFrom = new VDate(DisplayType.DateTime);
	private CLabel lQty = new CLabel(Msg.translate(Env.getCtx(), "Qty"));
	private VNumber fQty = new VNumber();
	private CLabel lUOM = new CLabel();
	private CLabel lName = new CLabel(Msg.translate(Env.getCtx(), "Name"));
	private CLabel lDescription = new CLabel(Msg.translate(Env.getCtx(), "Description"));
	private CTextField fName = new CTextField (30);
	private CTextField fDescription = new CTextField(30);
	private ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(true)
			.withZoomButton(true)
			.build();
	private JButton delete = ConfirmPanel.createDeleteButton(true);


	/**
	 * 	Static Init
	 * 	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		fResource.addActionListener(this);
		delete.addActionListener(this);
		confirmPanel.addButton(delete);
		confirmPanel.setActionListener(this);
		//
		mainPanel.setLayout(mainLayout);
		mainPanel.add(lResource,      new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 8, 4, 4), 0, 0));
		mainPanel.add(fResource,            new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 0, 4, 4), 0, 0));
		mainPanel.add(lDate,       new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 8, 4, 4), 0, 0));
		mainPanel.add(fDateFrom,            new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 0, 4, 8), 100, 0));
		mainPanel.add(lQty,       new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 8, 4, 4), 0, 0));
		mainPanel.add(fQty,          new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 4, 4), 0, 0));
		mainPanel.add(lUOM,         new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 4, 4, 8), 0, 0));
		mainPanel.add(lName,    new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 8, 4, 4), 0, 0));
		mainPanel.add(lDescription,   new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 8, 8, 4), 0, 0));
		mainPanel.add(fName,   new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 0, 4, 8), 0, 0));
		mainPanel.add(fDescription,    new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 0, 8, 8), 0, 0));
		//
		this.getContentPane().add(mainPanel,  BorderLayout.CENTER);
		this.getContentPane().add(confirmPanel,  BorderLayout.SOUTH);
	}	//	jbInit

	/**
	 * 	Initialize component & values from m_mAssignment
	 */
	private void setDisplay()
	{
		m_setting = true;

		//	Set Resource
		int S_Resource_ID = m_mAssignment.getS_Resource_ID();
		KeyNamePair[] resources = new KeyNamePair[m_lookup.size()];
		m_lookup.keySet().toArray(resources);
		for (int i = 0; i < resources.length; i++)
		{
			if (resources[i].getKey() == S_Resource_ID)
			{
				fResource.setSelectedItem(resources[i]);
				break;
			}
		}
		KeyNamePair check = (KeyNamePair)fResource.getSelectedItem();
		if (check == null || check.getKey() != S_Resource_ID)
		{
			if (m_mAssignment.getS_ResourceAssignment_ID() == 0)	//	new record select first
				fResource.setSelectedItem(fResource.getSelectedItem());		//	initiates UOM display
			else
				log.error("Resource not found ID=" + S_Resource_ID);
		}

		//	Set Date, Qty
		fDateFrom.setValue(m_mAssignment.getAssignDateFrom());
		fQty.setValue(m_mAssignment.getQty());

		//	Name, Description
		fName.setValue(m_mAssignment.getName());
		fDescription.setValue(m_mAssignment.getDescription());

		//	Set Editor to R/O if confirmed
		boolean readWrite = true;
		if (m_mAssignment.isConfirmed())
			readWrite = false;
		confirmPanel.getCancelButton().setVisible(readWrite);
		fResource.setReadWrite(readWrite);
		fDateFrom.setReadWrite(readWrite);
		fQty.setReadWrite(readWrite);

		m_setting = false;
	}	//	dynInit

	/**
	 * 	Action Listener
	 * 	@param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (m_setting)
			return;
		//	Update Assignment
		KeyNamePair resource = (KeyNamePair)fResource.getSelectedItem();
		if (resource != null)
		{
			int S_Resource_ID = resource.getKey();
			m_mAssignment.setS_Resource_ID (S_Resource_ID);
		}
		Timestamp assignDateFrom = fDateFrom.getTimestamp();
		if (assignDateFrom != null)
			m_mAssignment.setAssignDateFrom (assignDateFrom);
		BigDecimal qty = (BigDecimal)fQty.getValue();
		if (qty != null)
			m_mAssignment.setQty(qty);
		m_mAssignment.setName((String)fName.getValue());
		m_mAssignment.setDescription((String)fDescription.getValue());

		//	Resource - Look up UOM
		if (e.getSource() == fResource)
		{
			Object o = m_lookup.get(fResource.getSelectedItem());
			if (o == null)
				lUOM.setText(" ? ");
			else
				lUOM.setText(o.toString());
		}

		//	Zoom - InfoResource
		else if (e.getActionCommand().equals(ConfirmPanel.A_ZOOM))
		{
			InfoSchedule is = new InfoSchedule (m_frame,
				m_mAssignment, true);
			if (is.getMResourceAssignment() != null)
			{
				m_mAssignment = is.getMResourceAssignment();
			//	setDisplay();
				dispose();
			}
			is = null;
		}

		//	cancel - return
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}

		//	delete - delete and return
		else if (e.getActionCommand().equals(ConfirmPanel.A_DELETE))
		{
			if (m_mAssignment.delete(true))
			{
				m_mAssignment = null;
				dispose();
			}
			else
				ADialog.error(0, this, "ResourceAssignmentNotDeleted");
		}

		//	OK - Save
		else if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			if (cmd_save())
				dispose();
		}
	}	//	actionPerformed

	
	/**************************************************************************
	 * 	Get Assignment
	 * 	@return Assignment
	 */
	public MResourceAssignment getMResourceAssignment()
	{
		return m_mAssignment;
	}	//	getMResourceAssignment


	/**
	 * 	Check availability and insert record
	 *  @return true if saved/updated
	 */
	private boolean cmd_save()
	{
		log.info("");
		//	Set AssignDateTo
		final Timestamp assignDateFrom = fDateFrom.getTimestamp();
		final BigDecimal qty = (BigDecimal)fQty.getValue();
		final KeyNamePair uom = m_lookup.get(fResource.getSelectedItem());
		final int minutes = LegacyUOMConversionUtils.convertToMinutes(Env.getCtx(), uom.getKey(), qty);
		
		final Timestamp assignDateTo = TimeUtil.addMinutes(assignDateFrom, minutes);
		m_mAssignment.setAssignDateTo (assignDateTo);
		//
	//	m_mAssignment.dump();
		return m_mAssignment.save();
	}	//	cmdSave

	
	/**************************************************************************
	 * 	Load Resources.
	 *  called from variable constructor
	 * 	@return Array with resources
	 */
	private KeyNamePair[] getResources()
	{
		if (m_lookup.size() == 0)
		{
			String sql = Env.getUserRolePermissions().addAccessSQL(
				"SELECT r.S_Resource_ID, r.Name, r.IsActive,"	//	1..3
				+ "uom.C_UOM_ID,uom.UOMSymbol "					//	4..5
				+ "FROM S_Resource r, S_ResourceType rt, C_UOM uom "
				+ "WHERE r.S_ResourceType_ID=rt.S_ResourceType_ID AND rt.C_UOM_ID=uom.C_UOM_ID",
				"r", IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ);
			try
			{
				PreparedStatement pstmt = DB.prepareStatement(sql, null);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next())
				{
					StringBuffer sb = new StringBuffer (rs.getString(2));
					if (!"Y".equals(rs.getString(3)))
						sb.insert(0,'~').append('~');	//	inactive marker
					//	Key		S_Resource_ID/Name
					KeyNamePair key = new KeyNamePair (rs.getInt(1), sb.toString());
					//	Value	C_UOM_ID/Name
					KeyNamePair value = new KeyNamePair (rs.getInt(4), rs.getString(5).trim());
					m_lookup.put(key, value);
				}
				rs.close();
				pstmt.close();
			}
			catch (SQLException e)
			{
				log.error(sql, e);
			}
		}
		//	Convert to Array
		KeyNamePair[] retValue = new KeyNamePair[m_lookup.size()];
		m_lookup.keySet().toArray(retValue);
		Arrays.sort(retValue);
		return retValue;
	}	//	getResources

}	//	VAssignmentDialog
