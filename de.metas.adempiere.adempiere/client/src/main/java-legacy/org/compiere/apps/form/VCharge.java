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
package org.compiere.apps.form;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.ADialog;
import org.compiere.apps.ConfirmPanel;
import org.compiere.minigrid.MiniTable;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.i18n.Msg;

/**
 *  Create Charge from Accounts
 *
 *  @author Jorg Janke
 *  @version $Id: VCharge.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public class VCharge extends Charge
	implements FormPanel, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2478440763968206819L;

	private CPanel panel = new CPanel();
	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame parent frame
	 */
	@Override
	public void init (int WindowNo, FormFrame frame)
	{
		log.info("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		try
		{
			jbInit();
			dynInit();
			
			frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
			frame.getContentPane().add(confirmPanel, BorderLayout.SOUTH);
		}
		catch(Exception e)
		{
			log.error("", e);
		}
	}	//	init

	/**	FormFrame			*/
	private FormFrame 	m_frame;

	//
	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel newPanel = new CPanel();
	private TitledBorder newBorder;
	private GridBagLayout newLayout = new GridBagLayout();
	private JLabel valueLabel = new JLabel();
	private JTextField valueField = new JTextField();
	private JCheckBox isExpense = new JCheckBox();
	private JLabel nameLabel = new JLabel();
	private JTextField nameField = new JTextField();
	private JButton newButton = new JButton();
	private CPanel accountPanel = new CPanel();
	private TitledBorder accountBorder;
	private BorderLayout accountLayout = new BorderLayout();
	private CPanel accountOKPanel = new CPanel();
	private JButton accountButton = new JButton();
	private FlowLayout accountOKLayout = new FlowLayout();
	private JScrollPane dataPane = new JScrollPane();
	private MiniTable dataTable = new MiniTable();
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOK();

	/**
	 *  Static Init
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		AdempierePLAF.setDefaultBackground(panel);
		newBorder = new TitledBorder("");
		accountBorder = new TitledBorder("");
		mainPanel.setLayout(mainLayout);
		newPanel.setBorder(newBorder);
		newPanel.setLayout(newLayout);
		newBorder.setTitle(Msg.getMsg(Env.getCtx(), "ChargeNewAccount"));
		valueLabel.setText(Msg.translate(Env.getCtx(), "Value"));
		isExpense.setSelected(true);
		isExpense.setText(Msg.getMsg(Env.getCtx(), "Expense"));
		nameLabel.setText(Msg.translate(Env.getCtx(), "Name"));
		nameField.setColumns(20);
		valueField.setColumns(10);
		newButton.setText(Msg.getMsg(Env.getCtx(), "Create") + " " + Util.cleanAmp(Msg.getMsg(Env.getCtx(), "New")));
		newButton.addActionListener(this);
		accountPanel.setBorder(accountBorder);
		accountPanel.setLayout(accountLayout);
		accountBorder.setTitle(Msg.getMsg(Env.getCtx(), "ChargeFromAccount"));
		accountButton.setText(Msg.getMsg(Env.getCtx(), "Create") + " " + Msg.getMsg(Env.getCtx(), "From") + " " + Msg.getElement(Env.getCtx(), "Account_ID"));
		accountButton.addActionListener(this);
		accountOKPanel.setLayout(accountOKLayout);
		accountOKLayout.setAlignment(FlowLayout.RIGHT);
		confirmPanel.setActionListener(this);
		//
		mainPanel.add(newPanel, BorderLayout.NORTH);
		newPanel.add(valueLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		newPanel.add(valueField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		newPanel.add(nameLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		newPanel.add(nameField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		newPanel.add(isExpense, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		newPanel.add(newButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		mainPanel.add(accountPanel, BorderLayout.CENTER);
		accountPanel.add(accountOKPanel, BorderLayout.SOUTH);
		accountOKPanel.add(accountButton, null);
		accountPanel.add(dataPane, BorderLayout.CENTER);
		dataPane.getViewport().add(dataTable, null);
	}   //  jbInit

	/**
	 * 	Dispose
	 */
	@Override
	public void dispose()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	}	//	dispose

	/**
	 *	Action Listener
	 *  @param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		log.info(e.getActionCommand());
		//
		if (e.getActionCommand().equals(ConfirmPanel.A_OK) || m_C_Element_ID == 0)
			dispose();
		//  new Account
		else if (e.getSource().equals(newButton))
			createNew();
		else if (e.getSource().equals(accountButton))
			createAccount();
	}   //  actionPerformed

	/**
	 *  Create new Account and Charge
	 */
	private void createNew()
	{
		log.info("");
		//  Get Input
		String value = valueField.getText();
		if (value.length() == 0)
		{
			valueField.setBackground(AdempierePLAF.getFieldBackground_Error());
			return;
		}
		String name = nameField.getText();
		if (name.length() == 0)
		{
			nameField.setBackground(AdempierePLAF.getFieldBackground_Error());
			return;
		}
		//  Create Element
		int C_ElementValue_ID = createElementValue (value, name, isExpense.isSelected());
		if (C_ElementValue_ID == 0)
		{
			ADialog.error(m_WindowNo, panel, "ChargeNotCreated", name);
			return;
		}
		//  Create Charge
		int C_Charge_ID = createCharge(name, C_ElementValue_ID);
		if (C_Charge_ID == 0)
		{
			ADialog.error(m_WindowNo, panel, "ChargeNotCreated", name);
			return;
		}
		ADialog.info(m_WindowNo, panel, "ChargeCreated", name);
	}   //  createNew

	/**
	 *  Create Charges from Accounts
	 */
	private void createAccount()
	{
		createAccount(dataTable);
		
		if (listCreated.length() > 0)
			ADialog.info(m_WindowNo, panel, "ChargeCreated", listCreated.toString());
		if (listRejected.length() > 0)
			ADialog.error(m_WindowNo, panel, "ChargeNotCreated", listRejected.toString());
	}   //  createAccount
	
	/**
	 *  Dynamic Init
	 *  - Get defaults for primary AcctSchema
	 *  - Create Table with Accounts
	 */
	private void dynInit()
	{
		findChargeElementID();
		DefaultTableModel model = new DefaultTableModel(getData(), getColumnNames());
		dataTable.setModel(model);
		setColumnClass(dataTable);
		findTaxCategoryID();
	}

}   //  VCharge
