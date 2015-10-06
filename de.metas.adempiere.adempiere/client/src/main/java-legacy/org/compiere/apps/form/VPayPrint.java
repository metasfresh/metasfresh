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
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import org.compiere.apps.ADialog;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.ed.VNumber;
import org.compiere.model.MPaySelectionCheck;
import org.compiere.model.MPaymentBatch;
import org.compiere.plaf.CompiereColor;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.ValueNamePair;

/**
 *  Payment Print & Export
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VPayPrint.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VPayPrint extends PayPrint implements FormPanel, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6359854263967310497L;
	
	private CPanel panel = new CPanel();

	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame frame
	 */
	public void init (int WindowNo, FormFrame frame)
	{
		log.info("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		try
		{
			jbInit();
			dynInit();
			frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
			frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
	}	//	init

	
	/**	FormFrame			*/
	private FormFrame 		m_frame;
	
	//  Static Variables
	private CPanel centerPanel = new CPanel();
	private CPanel southPanel = new CPanel();
	private FlowLayout southLayout = new FlowLayout();
	private GridBagLayout centerLayout = new GridBagLayout();
	private JButton bPrint = ConfirmPanel.createPrintButton(true);
	private JButton bExport = ConfirmPanel.createExportButton(true);
	private JButton bCancel = ConfirmPanel.createCancelButton(true);
	private JButton bProcess = ConfirmPanel.createProcessButton(Msg.getMsg(Env.getCtx(), "VPayPrintProcess"));
	private CLabel lPaySelect = new CLabel();
	private CComboBox fPaySelect = new CComboBox();
	private CLabel lBank = new CLabel();
	private CLabel fBank = new CLabel();
	private CLabel lPaymentRule = new CLabel();
	private CComboBox fPaymentRule = new CComboBox();
	private CLabel lDocumentNo = new CLabel();
	private VNumber fDocumentNo = new VNumber();
	private CLabel lNoPayments = new CLabel();
	private CLabel fNoPayments = new CLabel();
	private CLabel lBalance = new CLabel();
	private VNumber fBalance = new VNumber();
	private CLabel lCurrency = new CLabel();
	private CLabel fCurrency = new CLabel();

	/**
	 *  Static Init
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		CompiereColor.setBackground(panel);
		//
		southPanel.setLayout(southLayout);
		southLayout.setAlignment(FlowLayout.RIGHT);
		centerPanel.setLayout(centerLayout);
		//
		bPrint.addActionListener(this);
		bExport.addActionListener(this);
		bCancel.addActionListener(this);
		//
		bProcess.setText(Msg.getMsg(Env.getCtx(), "EFT"));
		bProcess.setEnabled(false);
		bProcess.addActionListener(this);
		//
		lPaySelect.setText(Msg.translate(Env.getCtx(), "C_PaySelection_ID"));
		fPaySelect.addActionListener(this);
		//
		lBank.setText(Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"));
		//
		lPaymentRule.setText(Msg.translate(Env.getCtx(), "PaymentRule"));
		fPaymentRule.addActionListener(this);
		//
		lDocumentNo.setText(Msg.translate(Env.getCtx(), "DocumentNo"));
		fDocumentNo.setDisplayType(DisplayType.Integer);
		lNoPayments.setText(Msg.getMsg(Env.getCtx(), "NoOfPayments"));
		fNoPayments.setText("0");
		lBalance.setText(Msg.translate(Env.getCtx(), "CurrentBalance"));
		fBalance.setReadWrite(false);
		fBalance.setDisplayType(DisplayType.Amount);
		lCurrency.setText(Msg.translate(Env.getCtx(), "C_Currency_ID"));
		//
		southPanel.add(bCancel, null);
		southPanel.add(bExport, null);
		southPanel.add(bPrint, null);
		southPanel.add(bProcess, null);
		//
		centerPanel.add(lPaySelect,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(12, 12, 5, 5), 0, 0));
		centerPanel.add(fPaySelect,    new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 0, 5, 12), 0, 0));
		centerPanel.add(lBank,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(fBank,    new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 12), 0, 0));
		centerPanel.add(lPaymentRule,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(fPaymentRule,    new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 12), 0, 0));
		centerPanel.add(lDocumentNo,   new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(fDocumentNo,    new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 12), 0, 0));
		centerPanel.add(lNoPayments,   new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(fNoPayments,    new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 12), 0, 0));
		centerPanel.add(lBalance,    new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(fBalance,    new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 12), 0, 0));
		centerPanel.add(lCurrency,  new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 12, 5), 0, 0));
		centerPanel.add(fCurrency,   new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 12, 12), 0, 0));
	}   //  VPayPrint

	/**
	 *  Dynamic Init
	 */
	private void dynInit()
	{
		ArrayList<KeyNamePair> data = getPaySelectionData();
		for(KeyNamePair pp : data)
			fPaySelect.addItem(pp);
		
		if (fPaySelect.getItemCount() == 0)
			ADialog.info(m_WindowNo, panel, "VPayPrintNoRecords");
		else
		{
			fPaySelect.setSelectedIndex(0);
			loadPaySelectInfo();
		}
	}   //  dynInit

	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	}	//	dispose

	/**
	 * 	Set Payment Selection
	 *	@param C_PaySelection_ID id
	 */
	public void setPaySelection (int C_PaySelection_ID)
	{
		if (C_PaySelection_ID == 0)
			return;
		//
		for (int i = 0; i < fPaySelect.getItemCount(); i++)
		{
			KeyNamePair pp = (KeyNamePair)fPaySelect.getItemAt(i);
			if (pp.getKey() == C_PaySelection_ID)
			{
				fPaySelect.setSelectedIndex(i);
				return;
			}
		}
	}	//	setsetPaySelection

	
	/**************************************************************************
	 *  Action Listener
	 *  @param e event
	 */
	public void actionPerformed(ActionEvent e)
	{
	//	log.config( "VPayPrint.actionPerformed" + e.toString());
		if (e.getSource() == fPaySelect)
			loadPaySelectInfo();
		else if (e.getSource() == fPaymentRule)
			loadPaymentRuleInfo();
		//
		else if (e.getSource() == bCancel)
			dispose();
		else if (e.getSource() == bExport)
			cmd_export();
		else if (e.getSource() == bProcess)
			cmd_EFT();
		else if (e.getSource() == bPrint)
			cmd_print();
	}   //  actionPerformed

	/**
	 *  PaySelect changed - load Bank
	 */
	private void loadPaySelectInfo()
	{
		log.info( "VPayPrint.loadPaySelectInfo");
		if (fPaySelect.getSelectedIndex() == -1)
			return;
		
		int C_PaySelection_ID = ((KeyNamePair)fPaySelect.getSelectedItem()).getKey();
		loadPaySelectInfo(C_PaySelection_ID);
		
		fBank.setText(bank);
		fCurrency.setText(currency);
		fBalance.setValue(balance);
		
		loadPaymentRule();
	}   //  loadPaySelectInfo

	/**
	 *  Bank changed - load PaymentRule
	 */
	private void loadPaymentRule()
	{
		log.info("");
		if (m_C_BP_BankAccount_ID == -1)
			return;
		
		fPaymentRule.removeAllItems();
		
		int C_PaySelection_ID = ((KeyNamePair)fPaySelect.getSelectedItem()).getKey();
		ArrayList<ValueNamePair> data = loadPaymentRule(C_PaySelection_ID);
		for(ValueNamePair pp : data)
			fPaymentRule.addItem(pp);
		
		if (fPaymentRule.getItemCount() > 0)
			fPaymentRule.setSelectedIndex(0);
		
		loadPaymentRuleInfo();
	}   //  loadPaymentRule

	/**
	 *  PaymentRule changed - load DocumentNo, NoPayments,
	 *  enable/disable EFT, Print
	 */
	private void loadPaymentRuleInfo()
	{
		ValueNamePair pp = (ValueNamePair)fPaymentRule.getSelectedItem();
		if (pp == null)
			return;
		String PaymentRule = pp.getValue();

		log.info("PaymentRule=" + PaymentRule);
		fNoPayments.setText(" ");
		
		int C_PaySelection_ID = ((KeyNamePair)fPaySelect.getSelectedItem()).getKey();
		String msg = loadPaymentRuleInfo(C_PaySelection_ID, PaymentRule);
		
		if(noPayments != null)
			fNoPayments.setText(noPayments);
		
		bProcess.setEnabled(PaymentRule.equals("T"));
		
		if(documentNo != null)
			fDocumentNo.setValue(documentNo);
		
		if(msg != null && msg.length() > 0)
			ADialog.error(m_WindowNo, panel, msg);
	}   //  loadPaymentRuleInfo


	/**************************************************************************
	 *  Export payments to file
	 */
	private void cmd_export()
	{
		String PaymentRule = ((ValueNamePair)fPaymentRule.getSelectedItem()).getValue();
		log.info(PaymentRule);
		if (!getChecks(PaymentRule))
			return;

		//  Get File Info
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(Msg.getMsg(Env.getCtx(), "Export"));
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);
		fc.setSelectedFile(new java.io.File("paymentExport.txt"));
		if (fc.showSaveDialog(panel) != JFileChooser.APPROVE_OPTION)
			return;

		//  Create File
		int no = MPaySelectionCheck.exportToFile(m_checks, fc.getSelectedFile());
		ADialog.info(m_WindowNo, panel, "Saved",
			fc.getSelectedFile().getAbsolutePath() + "\n"
			+ Msg.getMsg(Env.getCtx(), "NoOfLines") + "=" + no);

		if (ADialog.ask(m_WindowNo, panel, "VPayPrintSuccess?"))
		{
		//	int lastDocumentNo = 
			MPaySelectionCheck.confirmPrint (m_checks, m_batch);
			//	document No not updated
		}
		dispose();
	}   //  cmd_export

	/**
	 *  Create EFT payment
	 */
	private void cmd_EFT()
	{
		String PaymentRule = ((ValueNamePair)fPaymentRule.getSelectedItem()).getValue();
		log.info(PaymentRule);
		if (!getChecks(PaymentRule))
			return;
		dispose();
	}   //  cmd_EFT

	/**
	 *  Print Checks and/or Remittance
	 */
	private void cmd_print()
	{
		String PaymentRule = ((ValueNamePair)fPaymentRule.getSelectedItem()).getValue();
		log.info(PaymentRule);
		if (!getChecks(PaymentRule))
			return;

		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		boolean somethingPrinted = false;
		boolean directPrint = !Ini.isPropertyBool(Ini.P_PRINTPREVIEW);
		//	for all checks
		for (int i = 0; i < m_checks.length; i++)
		{
			MPaySelectionCheck check = m_checks[i];
			//	ReportCtrl will check BankAccountDoc for PrintFormat
			boolean ok = ReportCtl.startDocumentPrint(ReportEngine.CHECK, check.get_ID(), null, Env.getWindowNo(panel), directPrint);
			if (!somethingPrinted && ok)
				somethingPrinted = true;
		}

		//	Confirm Print and Update BankAccountDoc
		if (somethingPrinted && ADialog.ask(m_WindowNo, panel, "VPayPrintSuccess?"))
		{
			int lastDocumentNo = MPaySelectionCheck.confirmPrint (m_checks, m_batch);
			if (lastDocumentNo != 0)
			{
				StringBuffer sb = new StringBuffer();
				sb.append("UPDATE C_BankAccountDoc SET CurrentNext=").append(++lastDocumentNo)
					// task 08354: this will fail, but we just don't care, because we most probably will never use
					// in C_BankAccountDoc *ever*..and if we do, we can fix it then
					.append(" WHERE C_BP_BankAccount_ID=") 
					.append(m_C_BP_BankAccount_ID)
					.append(" AND PaymentRule='").append(PaymentRule).append("'");
				DB.executeUpdate(sb.toString(), null);
			}
		}	//	confirm

		if (ADialog.ask(m_WindowNo, panel, "VPayPrintPrintRemittance"))
		{
			for (int i = 0; i < m_checks.length; i++)
			{
				MPaySelectionCheck check = m_checks[i];
				ReportCtl.startDocumentPrint(ReportEngine.REMITTANCE, check.get_ID(), null, Env.getWindowNo(panel), directPrint);
			}
		}	//	remittance

		panel.setCursor(Cursor.getDefaultCursor());
		dispose();
	}   //  cmd_print

	
	/**************************************************************************
	 *  Get Checks
	 *  @param PaymentRule Payment Rule
	 *  @return true if payments were created
	 */
	private boolean getChecks(String PaymentRule)
	{
		//  do we have values
		if (fPaySelect.getSelectedIndex() == -1 || m_C_BP_BankAccount_ID == -1
			|| fPaymentRule.getSelectedIndex() == -1 || fDocumentNo.getValue() == null)
		{
			ADialog.error(m_WindowNo, panel, "VPayPrintNoRecords",
				"(" + Msg.translate(Env.getCtx(), "C_PaySelectionLine_ID") + "=0)");
			return false;
		}

		//  get data
		int C_PaySelection_ID = ((KeyNamePair)fPaySelect.getSelectedItem()).getKey();
		int startDocumentNo = ((Number)fDocumentNo.getValue()).intValue();

		log.config("C_PaySelection_ID=" + C_PaySelection_ID + ", PaymentRule=" +  PaymentRule + ", DocumentNo=" + startDocumentNo);
		//
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		//	get Slecetions
		m_checks = MPaySelectionCheck.get(C_PaySelection_ID, PaymentRule, startDocumentNo, null);

		panel.setCursor(Cursor.getDefaultCursor());
		//
		if (m_checks == null || m_checks.length == 0)
		{
			ADialog.error(m_WindowNo, panel, "VPayPrintNoRecords",
				"(" + Msg.translate(Env.getCtx(), "C_PaySelectionLine_ID") + " #0");
			return false;
		}
		m_batch = MPaymentBatch.getForPaySelection (Env.getCtx(), C_PaySelection_ID, null);
		return true;
	}   //  getChecks

}   //  PayPrint
