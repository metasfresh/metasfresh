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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import javax.swing.border.TitledBorder;

import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.ed.VNumber;
import org.compiere.model.MCash;
import org.compiere.model.MCashBook;
import org.compiere.model.MCashLine;
import org.compiere.model.MOrder;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *  @author Comunidad de Desarrollo OpenXpertya 
 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
 *         *Jose A.Gonzalez, Conserti.
 * 
 *  @version $Id: CashSubFunctions.java,v 0.9 $
 * 
 *  @Colaborador $Id: Consultoria y Soporte en Redes y Tecnologias de la Informacion S.L.
 * 
 */

public class CashSubFunctions extends PosQuery implements ActionListener, InputMethodListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7496311215470523905L;

	/**
	 * 	Constructor
	 */
	public CashSubFunctions (PosBasePanel posPanel)
	{
		super(posPanel);
	}	//	PosQueryProduct

	private CButton f_displayInitialChange = null;
	private CButton f_inputsOutputs = null;
	private CButton f_tickets = null;
	private CButton f_closingCash = null;
	private CButton f_displayCashScrutiny = null;
	private CButton f_pos = null;
	private CPanel c;
	
	// for initial change
	private CPanel cInitial;
	private CLabel l_PreviousChange = null;
	private VNumber v_PreviousChange;
	private CLabel l_change = null;
	private VNumber v_change;
	private CButton f_change = null;
	
	// for cash scrutiny
	private CPanel cScrutiny;
	private CLabel l_previousBalance = null;
	private VNumber v_previousBalance;
	private CLabel l_ActualBalance = null;
	private VNumber v_ActualBalance;
	private CLabel l_difference = null;
	private VNumber v_difference;
	private CButton f_calculateDifference = null;

	private CPanel 			panel;
	private CScrollPane 	centerScroll;
	private ConfirmPanel	confirm;
	private Properties p_ctx;

	/**	Logger			*/
	private static Logger log = LogManager.getLogger(SubCheckout.class);
	
	/**
	 * 	Set up Panel 
	 */
	@Override
	protected void init()
	{
		CPanel main = new CPanel();
		main.setLayout(new BorderLayout(2,6));
		main.setPreferredSize(new Dimension(400,600));
		getContentPane().add(main);
		//	North
		panel = new CPanel(new GridBagLayout());
		main.add (panel, BorderLayout.CENTER);
		panel.setBorder(new TitledBorder(Msg.getMsg(p_ctx, "Cash Functions")));
		GridBagConstraints gbc = new GridBagConstraints();
		//
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = .3;
		gbc.weighty = 0.05;
		

		//********************  Main buttons **********************************
		
		f_displayInitialChange = createButtonAction("InitialChange", null);
		f_displayInitialChange.setText("Initial Change");
		f_displayInitialChange.setActionCommand("displayInitialChange");
		f_displayInitialChange.setMaximumSize(new Dimension(160,35));
		f_displayInitialChange.setMinimumSize(new Dimension(160,35));
		f_displayInitialChange.setPreferredSize(new Dimension(160,35));
		panel.add (f_displayInitialChange, gbc);
		//
		f_closingCash = createButtonAction("InitialChange", null);
		f_closingCash.setText("Cash Closing");
		f_closingCash.setActionCommand("CashClosing");
		f_closingCash.setPreferredSize(new Dimension(160,35));
		f_closingCash.setMaximumSize(new Dimension(160,35));
		f_closingCash.setMinimumSize(new Dimension(160,35));
		gbc.gridy = 1;
		panel.add (f_closingCash, gbc);		
		//
		f_displayCashScrutiny = createButtonAction("Scrutiny", null);
		f_displayCashScrutiny.setText("Cash Scrutiny");
		f_displayCashScrutiny.setActionCommand("displayCashScrutiny");
		f_displayCashScrutiny.setPreferredSize(new Dimension(160,35));
		f_displayCashScrutiny.setMaximumSize(new Dimension(160,35));
		f_displayCashScrutiny.setMinimumSize(new Dimension(160,35));
		gbc.gridy = 2;
		panel.add (f_displayCashScrutiny, gbc);
		//
		f_inputsOutputs = createButtonAction("Inputs", null);
		f_inputsOutputs.setText("Inputs and Outputs");
		f_inputsOutputs.setActionCommand("InputsOutputs");
		f_inputsOutputs.setPreferredSize(new Dimension(160,35));
		f_inputsOutputs.setMaximumSize(new Dimension(160,35));
		f_inputsOutputs.setMinimumSize(new Dimension(160,35));
		gbc.gridy = 3;
		panel.add (f_inputsOutputs, gbc);
		//
		f_tickets = createButtonAction("Tickets", null);
		f_tickets.setText("Tickets");
		f_tickets.setPreferredSize(new Dimension(160,35));
		f_tickets.setMaximumSize(new Dimension(160,35));
		f_tickets.setMinimumSize(new Dimension(160,35));
		gbc.gridy = 4;
		panel.add (f_tickets, gbc);
		//
		f_pos = createButtonAction("End", null);
		f_pos.setText("POS");
		f_pos.setPreferredSize(new Dimension(160,35));
		f_pos.setMaximumSize(new Dimension(160,35));
		f_pos.setMinimumSize(new Dimension(160,35));
		gbc.gridy = 5;
		panel.add (f_pos, gbc);

		//*************************** Panel to put buttons *************************
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy = 0;
		gbc.gridheight = 5;

		c = new CPanel(new GridBagLayout());
		c.setBorder(new TitledBorder(""));
		c.setMaximumSize(new Dimension(400,400));
		c.setMinimumSize(new Dimension(400,400));
		c.setPreferredSize(new Dimension(400,400));
		panel.add (c, gbc);
		
	

		//*************************** Panel for initial change *************************
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy = 0;
		gbc.gridheight = 5;
		cInitial = new CPanel(new GridBagLayout());
		cInitial.setBorder(new TitledBorder("Initial Change"));
		cInitial.setVisible(false);
		panel.add (cInitial, gbc);
		GridBagConstraints gbc0 = new GridBagConstraints();
		gbc0.anchor = GridBagConstraints.CENTER;
		//
		gbc0.gridx = 0;
		gbc0.gridy = 0;
		l_PreviousChange = new CLabel("Previous Change");
		cInitial.add (l_PreviousChange, gbc0);

		gbc0.gridx = 1;
		v_PreviousChange = new VNumber("PreviousChange", false, true, false, DisplayType.Amount, "PreviousChange");
		v_PreviousChange.setColumns(10, 25);
		cInitial.add(v_PreviousChange, gbc0);
		v_PreviousChange.setValue(Env.ZERO);
		//
		gbc0.gridx = 0;
		gbc0.gridy = 1;
		l_change = new CLabel("Initial Change");
		cInitial.add (l_change, gbc0);

		gbc0.gridx = 1;
		v_change = new VNumber("Change", false, false, true, DisplayType.Amount, "Change");
		v_change.setColumns(10, 25);
		cInitial.add(v_change, gbc0);
		v_change.setValue(Env.ZERO);
		
		gbc0.gridy = 2;
		gbc0.gridx = 0;
		gbc0.gridwidth = 2;
		//gbc0.fill = GridBagConstraints.HORIZONTAL;
		f_change = createButtonAction("InitialChange", null);
		f_change.setText("Save Change");
		f_change.setActionCommand("saveChange");
		cInitial.add (f_change, gbc0);	
		f_change.setPreferredSize(new Dimension(160,35));
		f_change.setMaximumSize(new Dimension(160,35));
		f_change.setMinimumSize(new Dimension(160,35));
		
		cInitial.setMaximumSize(new Dimension(400,400));
		cInitial.setMinimumSize(new Dimension(400,400));
		cInitial.setPreferredSize(new Dimension(400,400));

		
		//******************************  Panel for cash scrutiniy ************************
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy = 0;
		gbc.gridheight = 5;
		
	//	gbc.weightx = .7;

		cScrutiny = new CPanel(new GridBagLayout());
		cScrutiny.setBorder(new TitledBorder("Cash Scrutiny"));
		cScrutiny.setVisible(false);
		panel.add (cScrutiny, gbc);
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.anchor = GridBagConstraints.CENTER;
		
		//
		gbc1.gridx = 0;
		gbc1.gridy = 0; 
		l_previousBalance = new CLabel("Previous Balance");
		cScrutiny.add (l_previousBalance, gbc1);

		gbc1.gridx = 1;
		v_previousBalance = new VNumber("PreviousBalance", false, true, false, DisplayType.Amount, "PreviousBalance");
		v_previousBalance.setColumns(10, 25);
		cScrutiny.add(v_previousBalance, gbc1);
		v_previousBalance.setValue(Env.ZERO);

		//
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		l_ActualBalance = new CLabel("Actual Balance");
		cScrutiny.add (l_ActualBalance, gbc1);

		gbc1.gridx = 1;
		v_ActualBalance = new VNumber("ActualBalance", false, false, true, DisplayType.Amount, "ActualBalance");
		v_ActualBalance.setColumns(10, 25);
		v_ActualBalance.addActionListener(this);
		v_ActualBalance.addInputMethodListener(this);
		cScrutiny.add(v_ActualBalance, gbc1);
		v_ActualBalance.setValue(Env.ZERO);

		//
		gbc1.gridx = 0;
		gbc1.gridy = 2; 
		l_difference = new CLabel("Difference");
		cScrutiny.add (l_difference, gbc1);

		gbc1.gridx = 1;
		v_difference = new VNumber("Difference", false, true, false, DisplayType.Amount, "Difference");
		v_difference.setColumns(10, 25);
		cScrutiny.add(v_difference, gbc1);
		v_difference.setValue(Env.ZERO);

		//
		gbc1.gridx = 0;
		gbc1.gridy = 4;
		gbc1.gridwidth = 2;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		f_calculateDifference = createButtonAction("InitialChange", null);
		f_calculateDifference.setText("Annotate Difference");
		f_calculateDifference.setActionCommand("AnnotateDiference");
		cScrutiny.add (f_calculateDifference, gbc1);	
		
		cScrutiny.setMaximumSize(new Dimension(400,400));
		cScrutiny.setMinimumSize(new Dimension(400,400));
		cScrutiny.setPreferredSize(new Dimension(400,400));
	}	//	init

	
	/**
	 * 	Dispose
	 */
	@Override
	public void dispose()
	{
		removeAll();
		panel = null;
		centerScroll = null;
		confirm = null;
	}	//	dispose
	
	/**
	 * 	Action Listener
	 *	@param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		String action = e.getActionCommand();		
		if (action == null || action.length() == 0)
			return;
		log.info("PosCashSubFunctions - actionPerformed: " + action);
		
		//	to display panel with initial change
		if (action.equals("displayInitialChange"))
		{
			cmd_displayInitialChange();
		}
		//  to display panel with cash closing
		else if (action.equals("CashClosing"))
		{
			Timestamp today = TimeUtil.getDay(System.currentTimeMillis());

			MCash cash = MCash.get(p_pos.getCtx(), /*p_pos.getAD_Org_ID(),*/ p_pos.getC_CashBook_ID(), today, null); 

			MQuery query = new MQuery(MCash.Table_Name);
			query.addRestriction("C_Cash_ID", Operator.EQUAL, cash.getC_Cash_ID());
			AEnv.zoom(query);
		}
		//	to open window with inputs and outputs of cash
		else if (action.equals("InputsOutputs"))
		{
			Timestamp today = TimeUtil.getDay(System.currentTimeMillis());

			MCash cash = MCash.get(p_pos.getCtx(), /*p_pos.getAD_Org_ID(),*/ p_pos.getC_CashBook_ID(), today, null); 

			AEnv.zoom(MCash.Table_ID, cash.getC_Cash_ID());
		}
		else if (action.equals("Tickets"))
		{
			MQuery query = new MQuery(MOrder.Table_Name);
			query.addRestriction("C_DocTypeTarget_ID", Operator.EQUAL, p_pos.getC_DocType_ID());
			AEnv.zoom(query);
		}
		//	Cash (Payment)
		else if (action.equals("displayCashScrutiny"))
		{
			cmd_displayCashScrutiny();
		}
		else if (action.equals("End"))
		{
			super.dispose();
		}
		else if (action.equals("saveChange"))
		{
			cmd_saveChange();
		}
		else if (action.equals("AnnotateDifference"))
		{
			cmd_calculateDifference();
			cmd_annotateDifference();
		}
		else if (e.getSource() == v_ActualBalance)
			cmd_calculateDifference();
			

	}	//	actionPerformed
	
	/**
	 * Desplegar panel de Cambio Inicial
	 * 
	 * @author Comunidad de Desarrollo OpenXpertya 
	 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
	 *         *Copyright (c) ConSerTi
	 */
	private void cmd_displayInitialChange()
	{
		cScrutiny.setVisible(false);
		c.setVisible(false);
		cInitial.setVisible(true);
		
		Timestamp today = TimeUtil.getDay(System.currentTimeMillis());

		MCash cash = MCash.get(p_pos.getCtx(), /*p_pos.getAD_Org_ID(),*/ p_pos.getC_CashBook_ID(), today, null);

		if (cash != null)
		{
			v_PreviousChange.setValue(cash.getEndingBalance());
			v_change.setValue(cash.getEndingBalance());
		}
		else
			log.error("No Cash");
	}

	/**
	 * Desplegar panel de Scrutiny de caja 
	 * 
	 * @author Comunidad de Desarrollo OpenXpertya 
	 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
 	 *         *Copyright (c) ConSerTi
	 */
	private void cmd_displayCashScrutiny()
	{
		cInitial.setVisible(false);
		c.setVisible(false);
		cScrutiny.setVisible(true);

		// calculate total until the moment and shows it in scrutiny panel
		Timestamp today = TimeUtil.getDay(System.currentTimeMillis());

		MCash cash = MCash.get(p_pos.getCtx(), /*p_pos.getAD_Org_ID(),*/ p_pos.getC_CashBook_ID(), today, null);

		v_previousBalance.setValue(cash.getEndingBalance());
	}

	/**
	 * Save the initial change of the cash
	 * 
	 * @author Comunidad de Desarrollo OpenXpertya 
	 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
	 *         *Copyright (c) ConSerTi
	 */
	private void cmd_saveChange()
	{
		MCashBook cashBook = new MCashBook(p_ctx, p_pos.getC_CashBook_ID(), null);
		Timestamp today = TimeUtil.getDay(System.currentTimeMillis());

		MCash cash = MCash.get(p_ctx, /*p_pos.getAD_Org_ID(),*/ p_pos.getC_CashBook_ID(), today, null);
		
		BigDecimal initialChange = (BigDecimal)v_change.getValue();
		
		if (cash != null && cash.get_ID() != 0 && initialChange.compareTo(cash.getEndingBalance()) != 0)
		{
			MCashLine cl = new MCashLine (cash);
			cl.setCashType(MCashLine.CASHTYPE_Difference);
			cl.setAmount(initialChange.subtract(cash.getEndingBalance()));
			cl.setDescription("Initial Change Before: " + cash.getEndingBalance() + " Now: " + initialChange);
			cl.save();			
		}
		v_PreviousChange.setValue(initialChange);
	}
	
	/**
	 * Calculate difference between previous balance and actual
	 * for cash scrutiny
	 * 
	 * @author Comunidad de Desarrollo OpenXpertya 
	 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
	 *         *Copyright (c) ConSerTi
	 */
	private void cmd_calculateDifference()
	{
		// calculate difference between scrutiny of previous and actual balance
		BigDecimal previousValue, actualValue;		

		Timestamp today = TimeUtil.getDay(System.currentTimeMillis());
		MCash cash = MCash.get(p_pos.getCtx(), /*p_pos.getAD_Org_ID(),*/ p_pos.getC_CashBook_ID(), today, null);
		v_previousBalance.setValue(cash.getEndingBalance());
		previousValue = cash.getEndingBalance();

		actualValue = (BigDecimal)v_ActualBalance.getValue();
		
		v_difference.setValue(actualValue.subtract(previousValue));
	}
	
	/**
	 * Annotate the difference between previous balance and actual
	 * from cash scrutiny in the cash book
	 * 
	 * @author Comunidad de Desarrollo OpenXpertya 
	 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
	 *         *Copyright (c) ConSerTi
	 */
	private void cmd_annotateDifference()
	{
		// calculate difference from scrutiny between previous and actual balance
		BigDecimal previousValue, actualValue, difference;		
		previousValue = (BigDecimal)v_previousBalance.getValue();
		actualValue = (BigDecimal)v_ActualBalance.getValue();
		
		difference = actualValue.subtract(previousValue);

		MCashBook cashBook = new MCashBook(p_ctx, p_pos.getC_CashBook_ID(), null);
		Timestamp today = TimeUtil.getDay(System.currentTimeMillis());

		MCash cash = MCash.get(p_ctx, /*cashBook.getAD_Org_ID(),*/ cashBook.getC_CashBook_ID(), today, null);
		
		if (cash != null && cash.get_ID() != 0 && difference.compareTo(cash.getStatementDifference()) != 0)
		{
			MCashLine cl = new MCashLine (cash);
			cl.setCashType(MCashLine.CASHTYPE_Difference);
			cl.setAmount(difference);
			cl.setDescription(Msg.translate(p_pos.getCtx(), "Cash Scrutiny -> Before: ") + previousValue + " Now: " + actualValue);
			cl.save();			
		}
		cash = MCash.get(p_pos.getCtx(), /*p_pos.getAD_Org_ID(),*/ p_pos.getC_CashBook_ID(), today, null);
		v_previousBalance.setValue(cash.getEndingBalance());
		v_ActualBalance.setValue(Env.ZERO);
		v_difference.setValue(Env.ZERO);
	}


	/**
	 * calculate difference in cash scrutiny every time the cursor moves
	 */
	@Override
	public void caretPositionChanged(InputMethodEvent event) 
	{
		cmd_calculateDifference();
	}


	/**
	 * calculate difference between cash scrutiniy each time the actual balance text changes
	 */
	@Override
	public void inputMethodTextChanged(InputMethodEvent event) 
	{
		cmd_calculateDifference();
	}


	@Override
	protected void close() {
	}


	@Override
	protected void enableButtons() {		
	}


	@Override
	public void reset() {		
	}
	
}	//	CashSubFunctions
