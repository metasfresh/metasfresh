/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
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

package org.compiere.pos;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.border.TitledBorder;

import org.compiere.grid.ed.VNumber;
import org.compiere.model.MOrder;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *	Checkout Sub Panel
 *	
 *  @author Comunidad de Desarrollo OpenXpertya 
 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
 *         *Copyright � Jorg Janke
 *  @version $Id: SubCheckout.java,v 1.1 2004/07/12 04:10:04 jjanke Exp $
 */
public class SubCheckout extends PosSubPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6359287546081954105L;

	/**
	 * 	Constructor
	 *	@param posPanel POS Panel
	 */
	public SubCheckout (PosBasePanel posPanel)
	{
		super (posPanel);
	}	//	PosSubCheckout
	
	private CButton f_summary = null;
	
	//TODO: credit card
/*	private CLabel f_lcreditCardNumber = null;
	private CTextField f_creditCardNumber = null;
	private CLabel f_lcreditCardExp = null;
	private CTextField f_creditCardExp = null;
	private CLabel f_lcreditCardVV = null;
	private CTextField f_creditCardVV = null;
	private CButton f_creditPayment = null;
*/
	private CLabel f_lcashGiven = null;
	private VNumber f_cashGiven;
	private CLabel f_lcashReturn = null;
	private VNumber f_cashReturn;
	
	private CButton f_cashRegisterFunctions;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(SubCheckout.class);
	
	/**
	 * 	Initialize
	 */
	public void init()
	{
		//	Content
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		//	BOX	1 - CASH 
		gbc.gridx = 0;
		gbc.gridheight = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = .1;
		CPanel cash = new CPanel(new GridBagLayout());
		cash.setBackground(java.awt.Color.lightGray);
		cash.setBorder(new TitledBorder(Msg.getMsg(Env.getCtx(), "Checkout")));
		gbc.gridy = 0;
		add (cash, gbc);
		GridBagConstraints gbc0 = new GridBagConstraints();
//		gbc0.anchor = GridBagConstraints.EAST;
		//
		f_lcashGiven = new CLabel(Msg.getMsg(Env.getCtx(),"CashGiven"));
		cash.add (f_lcashGiven, gbc0);
		f_cashGiven = new VNumber("CashGiven", false, false, true, DisplayType.Amount, Msg.translate(Env.getCtx(), "CashGiven"));
		f_cashGiven.setColumns(12, 25);
		cash.add (f_cashGiven, gbc0);
		f_cashGiven.setValue(Env.ZERO);
		f_cashGiven.addActionListener(this);  //to update the change with the money
		//
		f_lcashReturn = new CLabel(Msg.getMsg(Env.getCtx(),"CashReturn"));
		cash.add (f_lcashReturn, gbc0);
		f_cashReturn = new VNumber("CashReturn", false, true, false, DisplayType.Amount, "CashReturn");
		f_cashReturn.setColumns(8, 25);
		cash.add (f_cashReturn, gbc0);
		f_cashReturn.setValue(Env.ZERO);
		
		//	BOX 2 - UTILS
		CPanel utils = new CPanel(new GridBagLayout());
		utils.setBorder(new TitledBorder(Msg.getMsg(Env.getCtx(), "Utils")));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = .1;
		add (utils, gbc);
		GridBagConstraints gbcU = new GridBagConstraints();
		gbcU.anchor = GridBagConstraints.EAST;
		//CASH FUNCTIONS
		f_cashRegisterFunctions = createButtonAction("CashRegisterFunction", null);
		f_cashRegisterFunctions.setText("Cash Functions");
	    f_cashRegisterFunctions.setPreferredSize(new Dimension(130,37));
	    f_cashRegisterFunctions.setMaximumSize(new Dimension(130,37));
		f_cashRegisterFunctions.setMinimumSize(new Dimension(130,37));
		utils.add(f_cashRegisterFunctions, gbcU);
			//SUMMARY
		f_summary = createButtonAction("Summary", null);
 		utils.add (f_summary, gbcU);

		
	
//TODO: Credit card
/*  Panel para la introducci�n de los datos de CreditCard para el pago quitada por ConSerTi al no considerar
 *  que sea �til de momento
 	
		//	--	1 -- Creditcard 
		CPanel creditcard = new CPanel(new GridBagLayout());
		creditcard.setBorder(new TitledBorder(Msg.translate(Env.getCtx(), "CreditCardType")));
		gbc.gridy = 2;
		add (creditcard, gbc);
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.insets = INSETS2;
		gbc1.anchor = GridBagConstraints.WEST;
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		f_lcreditCardNumber = new CLabel(Msg.translate(Env.getCtx(), "CreditCardNumber"));
		creditcard.add (f_lcreditCardNumber, gbc1);
		gbc1.gridy = 1;
		f_creditCardNumber = new CTextField(18); 
		creditcard.add (f_creditCardNumber, gbc1);
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		f_lcreditCardExp = new CLabel(Msg.translate(Env.getCtx(),"CreditCardExp"));
		creditcard.add (f_lcreditCardExp, gbc1);
		gbc1.gridy = 1;
		f_creditCardExp = new CTextField(5); 
		creditcard.add (f_creditCardExp, gbc1);
		gbc1.gridx = 2;
		gbc1.gridy = 0;
		f_lcreditCardVV = new CLabel(Msg.translate(Env.getCtx(), "CreditCardVV"));
		creditcard.add (f_lcreditCardVV, gbc1);
		gbc1.gridy = 1;
		f_creditCardVV = new CTextField(5); 
		creditcard.add (f_creditCardVV, gbc1);
		//
		gbc1.gridx = 3;
		gbc1.gridy = 0;
		gbc1.gridheight = 2;
		f_creditPayment = createButtonAction("Payment", null);
		f_creditPayment.setActionCommand("CreditCard");
		gbc1.anchor = GridBagConstraints.EAST;
		gbc1.weightx = 0.1;
		creditcard.add (f_creditPayment, gbc1);
		
		**/ //fin del comentario para quitar la parte del CreditCard
	}	//	init

	/**
	 * 	Dispose - Free Resources
	 */
	public void dispose()
	{
		super.dispose();
	}	//	dispose


	/**
	 * 	Action Listener
	 *	@param e event
	 */
	public void actionPerformed (ActionEvent e)
	{
		String action = e.getActionCommand();		
		if (action == null || action.length() == 0)
			return;
		log.info( "PosSubCheckout - actionPerformed: " + action);
		

		if (action.equals("Summary"))
		{
			//displaySummary();			
		}
		
		else if (action.equals("CashRegisterFunction"))
		{
			CashSubFunctions csf = new CashSubFunctions(p_posPanel);
			csf.setVisible(true);
		}
		else if (e.getSource() == f_cashGiven)
			//displayReturn();
		
/*		//	CreditCard (Payment)
		else if (action.equals("CreditCard"))
		{
			Log.print("CreditCard");
		}  fin del comentario para la Credit Card*/
		
		p_posPanel.updateInfo();
	}	//	actionPerformed



	


	

}	//	PosSubCheckout
