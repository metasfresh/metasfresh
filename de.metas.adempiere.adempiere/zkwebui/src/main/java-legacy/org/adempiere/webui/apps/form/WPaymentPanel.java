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
package org.adempiere.webui.apps.form;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.editor.WButtonEditor;
import org.adempiere.webui.window.FDialog;
import org.compiere.grid.IPayableDocument;
import org.compiere.grid.IVPaymentPanel;
import org.compiere.grid.VPayment.ProcessingCtx;
import org.compiere.grid.VPaymentProcess;
import org.compiere.model.GridTab;
import org.compiere.model.MCashLine;
import org.compiere.model.MPayment;
import org.compiere.model.X_C_Order;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.util.TrxRunnable2;
import org.compiere.util.Util;
import org.compiere.util.ValueNamePair;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Div;
import org.zkoss.zul.Space;

/**
 * Display (and process) Payment Options.
 * 
 * <pre>
 *  Payment Rule
 *  -B- Cash          (Date)          -> Cash Entry
 *  -P- Payment Term  (Term)
 *  -S- Check         (Routing, ..)   -> Payment Entry
 *  -K- CreditCard    (No)            -> Payment Entry
 *  -U- ACH Transfer  (Routing)       -> Payment Entry
 * 
 *  When processing:
 *  - If an invoice is a S/K/U, but has no Payment Entry, it is changed to P
 *  - If an invoive is B and has no Cash Entry, it is created
 *  - An invoice is "Open" if it is "P" and no Payment
 * 
 *  Entry:
 *  - If not processed, an invoice has no Cash or Payment entry
 *  - The entry is created, during "Online" and when Saving
 * 
 *  Changes/Reversals:
 *  - existing Cash Entries are reversed and newly created
 *  - existing Payment Entries are not changed and then "hang there" and need to be allocated
 * </pre>
 * 
 * @author Jorg Janke
 * @version $Id: VPayment.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>BF [ 1763488 ] Error on cash payment <li>BF [ 1789949 ] VPayment: is
 *         displaying just "CashNotCreated"
 */
public class WPaymentPanel extends Div
		implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5886626149128018585L;

	/**
	 * Constructor
	 * 
	 * @param WindowNo
	 *            owning window
	 * @param mTab
	 *            owning tab
	 * @param button
	 *            button with access information
	 */
	public WPaymentPanel(int WindowNo, GridTab mTab, WButtonEditor button)
	{
		// metas: tsa: begin ---------------------------------------------------------------------------
		this(WindowNo, mTab, button, false);
	}

	public WPaymentPanel(int WindowNo, GridTab mTab, WButtonEditor button, boolean isEmbedded)
	// metas: tsa: end -----------------------------------------------------------------------------
	{
		super();
		// this.setTitle(Msg.getMsg(Env.getCtx(), "Payment"));
		// this.setAttribute("mode", "modal");
		m_isEmbedded = isEmbedded;
		m_WindowNo = WindowNo;
		m_isSOTrx = "Y".equals(Env.getContext(Env.getCtx(), WindowNo, "IsSOTrx"));
		m_mTab = mTab;
		m_paymentRuleButton = button;
		doc = InterfaceWrapperHelper.create(m_mTab, IPayableDocument.class);
		try
		{
			zkInit();
			m_initOK = dynInit(button); // Null Pointer if order/invoice not saved yet
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "VPayment", ex);
			m_initOK = false;
		}
		//
		this.setHeight("400px");
		this.setWidth("500px");
	} // VPayment

	/** Window */
	private int m_WindowNo = 0;
	/** Tab */
	private GridTab m_mTab;
	private WButtonEditor m_paymentRuleButton; // metas: tsa

	// Data from Order/Invoice
	private String m_DocStatus = null;

	/** Is SOTrx */
	private boolean m_isSOTrx = true;

	private BigDecimal m_Amount = Env.ZERO; // Payment Amount
	//
	private boolean m_initOK = false;
	/** Only allow changing Rule */
	private boolean m_onlyRule = false;
	private boolean m_isEmbedded = false; // metas: tsa

	private boolean m_needSave = false;
	/** Logger */
	private static CLogger log = CLogger.getCLogger(WPaymentPanel.class);
	
	private final IPayableDocument doc;
	
	private final PropertyChangeListener paymentRulePanelsListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (IVPaymentPanel.ACTION_Online.equals(evt.getPropertyName()))
			{
				process();
			}
		}
	};

	//
	private Panel mainPanel = new Panel();
	private Borderlayout mainLayout = new Borderlayout();
	private Panel northPanel = new Panel();
	private Panel centerPanel = new Panel();
	public Listbox paymentCombo = ListboxFactory.newDropdownListbox();
	private Label paymentLabel = new Label();
	private List<Panel> centerLayout = new ArrayList<Panel>();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);

	private static final Map<String, Class<? extends IVPaymentPanel>> paymentRulePanelClasses = new HashMap<String, Class<? extends IVPaymentPanel>>();
	static
	{
		registerPaymentRulePanel(X_C_Order.PAYMENTRULE_Cash, WPaymentCash.class);
		registerPaymentRulePanel(X_C_Order.PAYMENTRULE_Check, WPaymentCheck.class);
		registerPaymentRulePanel(X_C_Order.PAYMENTRULE_CreditCard, WCreditCardPanel.class);
		registerPaymentRulePanel(X_C_Order.PAYMENTRULE_DirectDebit, WPaymentDirectDebit.class);
		registerPaymentRulePanel(X_C_Order.PAYMENTRULE_DirectDeposit, WPaymentDirectDebit.class);
		registerPaymentRulePanel(X_C_Order.PAYMENTRULE_OnCredit, WPaymentOnCredit.class);
	}

	public static void registerPaymentRulePanel(String paymentRule, Class<? extends IVPaymentPanel> clazz)
	{
		paymentRulePanelClasses.put(paymentRule, clazz);
	}
	/**
	 * Static Init
	 * 
	 * @throws Exception
	 */
	private void zkInit() throws Exception
	{
		this.appendChild(mainPanel);
		mainPanel.setHeight("100%");
		mainPanel.setWidth("100%");
		mainPanel.appendChild(mainLayout);
		mainPanel.setStyle("width: 100%; height: 100%; padding: 0; margin: 0");
		mainLayout.setHeight("100%");
		mainLayout.setWidth("100%");
		Center center = new Center();
		mainLayout.appendChild(center);
		center.appendChild(centerPanel);
		//
		paymentLabel.setText(Msg.translate(Env.getCtx(), "PaymentRule"));
		North north = new North();
		north.setStyle("border: none");
		mainLayout.appendChild(north);
		north.appendChild(northPanel);
		northPanel.appendChild(paymentLabel);
		northPanel.appendChild(new Space());
		northPanel.appendChild(paymentCombo);
		South south = new South();
		south.setStyle("border: none");
		mainLayout.appendChild(south);
		south.appendChild(confirmPanel);
		confirmPanel.addActionListener(this);
		if (m_isEmbedded)
			confirmPanel.setVisible(false); // metas: tsa
	} // jbInit

	/**************************************************************************
	 *	Dynamic Init.
	 *		B (Cash)		(Currency)
	 *		K (CreditCard)  Type, Number, Exp, Approval
	 *		L (DirectDebit)	BPartner_Bank
	 *		P (PaymentTerm)	PaymentTerm
	 *		S (Check)		(Currency) CheckNo, Routing
	 * 
	 * Currencies are shown, if member of EMU
	 * 
	 * @param button
	 *            payment type button
	 * @return true if init OK
	 * @throws Exception
	 */
	private boolean dynInit(WButtonEditor button) throws Exception
	{
		return dynInit(button, true);
	}

	private boolean dynInit(WButtonEditor button, boolean updateFields) throws Exception
	{
		m_DocStatus = (String)m_mTab.getValue("DocStatus");
		log.config(m_DocStatus);

		if (m_mTab.getValue("C_BPartner_ID") == null)
		{
			FDialog.error(0, this, "SaveErrorRowNotFound");
			return false;
		}

		// Is the Trx posted?
		// String Posted = (String)m_mTab.getValue("Posted");
		// if (Posted != null && Posted.equals("Y"))
		// return false;

		// DocStatus
		m_DocStatus = (String)m_mTab.getValue("DocStatus");
		if (m_DocStatus == null)
			m_DocStatus = "";
		// Is the Trx closed? Reversed / Voided / Cloased
		if (m_DocStatus.equals("RE") || m_DocStatus.equals("VO") || m_DocStatus.equals("CL"))
			return false;
		// Document is not complete - allow to change the Payment Rule only
		if (m_DocStatus.equals("CO") || m_DocStatus.equals("WP"))
			m_onlyRule = false;
		else
			m_onlyRule = true;
		// PO only Rule
		if (!m_onlyRule // Only order has Warehouse
				&& !m_isSOTrx && m_mTab.getValue("M_Warehouse_ID") != null)
			m_onlyRule = true;
		if (m_isEmbedded)
			m_onlyRule = false; // metas: tsa

		centerPanel.setVisible(!m_onlyRule);

		// Amount
		m_Amount = (BigDecimal)m_mTab.getValue("GrandTotal");
		if (!m_onlyRule && m_Amount.compareTo(Env.ZERO) == 0 && !m_isEmbedded)
		{
			FDialog.error(m_WindowNo, this, "PaymentZero");
			return false;
		}

		dynInitPaymentRules(getPaymentRules(button));

		return true;
	} // dynInit

	/**
	 * Init OK to be able to make changes?
	 * 
	 * @return true if init OK
	 */
	public boolean isInitOK()
	{
		return m_initOK;
	} // isInitOK
	/**************************************************************************
	 * Action Listener
	 * 
	 * @param e
	 *            event
	 */
	public void onEvent(Event e)
	{
		log.fine("WPayment.actionPerformed - " + e.getTarget().getId());

		// Finish
		if (e.getTarget().getId().equals(ConfirmPanel.A_OK))
		{
			if (checkMandatory(null))
			{
				process();
			}
		}
		else if (e.getTarget().getId().equals(ConfirmPanel.A_CANCEL))
			dispose();

		// Payment Method Change
		else if (e.getTarget() == paymentCombo)
		{
			onPaymentComboSelection();
		}

	} // actionPerformed

	private void onPaymentComboSelection()
	{
		// get selection
		ListItem selectedItem = paymentCombo.getSelectedItem();
		ValueNamePair pp = selectedItem != null ? selectedItem.toValueNamePair() : null;
		if (pp != null)
		{
			String s = pp.getValue().toLowerCase();
			if (X_C_Order.PAYMENTRULE_DirectDebit.equalsIgnoreCase(s))
				s = X_C_Order.PAYMENTRULE_DirectDeposit.toLowerCase();
			s += "Panel";
			show(s); // switch to panel

		}
	}

	private void show(String s)
	{
		for (Panel p : centerLayout)
		{
			if (p.getId().equals(s))
			{
				if (!p.isVisible())
					p.setVisible(true);
			}
			else if (p.isVisible())
			{
				p.setVisible(false);
			}
		}
	}

	private void dispose()
	{
		if (getParent() instanceof Window)
		{
			((Window)getParent()).dispose();
		}
	}
	
	private void process()
	{
		PaymentProcess p = new PaymentProcess();
		if (!p.prepare())
		{
			// we need more input corrections
			return;
		}

		Trx.run(p);
	}



	/**
	 * Need Save record (payment with waiting order)
	 * 
	 * @return true if payment with waiting order
	 */
	public boolean needSave()
	{
		return m_needSave;
	} // needSave

	public void reset()
	{
		m_DocStatus = null;
		m_isSOTrx = true;
		m_Amount = Env.ZERO; // Payment Amount

		try
		{
			m_initOK = false;
			dynInit(m_paymentRuleButton, true);
			m_initOK = true;
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}

	public String getPaymentRule()
	{
		ValueNamePair vp = paymentCombo.getSelectedItem().toValueNamePair();
		String PaymentRule = vp.getValue();
		return PaymentRule;
	}

	public void showInfo(String adMessage, String message)
	{
		if (m_isEmbedded)
			log.info("" + adMessage + " - " + message);
		else
			FDialog.info(m_WindowNo, this, adMessage, message);
	}

	public void showError(String adMessage, String message)
	{
		if (m_isEmbedded)
		{
			log.warning("" + adMessage + " - " + message);
		}
		else
		{
			FDialog.error(m_WindowNo, this, adMessage, message);
		}
	}

	private class PaymentProcess implements TrxRunnable2
	{
		private VPaymentProcess proc;
		private Throwable exception;

		public boolean prepare()
		{
			return checkMandatory(null);
		}

		@Override
		public final void run(String trxName)
		{

			final ProcessingCtx pctx = createContext(trxName);
			final IVPaymentPanel panel = paymentRulePanels.get(pctx.newPaymentRule);
			proc = new VPaymentProcess(pctx, doc, panel);

			if (!DocAction.STATUS_Drafted.equals(doc.getDocStatus())) // metas: cg: 0217
				proc.process();
		}

		@Override
		public boolean doCatch(Throwable e) throws Exception
		{
			exception = e;
			return true; // don't throw the error, but rollback the transaction
		}

		@Override
		public void doFinally()
		{
			if (exception != null)
			{
				log.log(Level.INFO, exception.getLocalizedMessage(), exception);
				FDialog.error(m_WindowNo, WPaymentPanel.this, "PaymentError", exception.getLocalizedMessage());
				return;
			}

			proc.updatePayableDocument();
			String info = proc.getInfo();
			if (!Util.isEmpty(info))
				FDialog.info(m_WindowNo, WPaymentPanel.this, "PaymentCreated", info);
			WPaymentPanel.this.dispose();
		}

	}
	
	/**
	 * Check Mandatory
	 * 
	 * @return true if all mandatory items are OK
	 */
	private boolean checkMandatory(String trxName)
	{
		String PaymentRule = getPaymentRule();
		// only Payment Rule
		if (m_onlyRule)
			return true;

		IVPaymentPanel panel = paymentRulePanels.get(PaymentRule);
		if (panel == null)
		{
			log.log(Level.SEVERE, "Unknown PaymentRule " + PaymentRule);
			return false;
		}

		if (!panel.validate(trxName != null))
			return false;

		return true;
	} // checkMandatory
	
	private IVPaymentPanel addPaymentRulePanel(String paymentRule)
	{
		Class<? extends IVPaymentPanel> clazz = paymentRulePanelClasses.get(paymentRule);
		if (clazz == null)
		{
			throw new AdempiereException("No payment rule panel class found for " + paymentRule);
		}
		return addPaymentRulePanel(paymentRule, clazz);
	}

	private <T extends IVPaymentPanel> T addPaymentRulePanel(String paymentRule, Class<T> clazz)
	{
		final T panel;
		try
		{
			panel = clazz.newInstance();
		}
		catch (Throwable e)
		{
			throw new AdempiereException(e);
		}
		addPaymentRulePanel(paymentRule, panel);
		return panel;
	}
	
	private final Map<String, IVPaymentPanel> paymentRulePanels = new HashMap<String, IVPaymentPanel>();
	private void addPaymentRulePanel(String paymentRule, IVPaymentPanel panel)
	{
		final Component component = (Component)panel.getComponent();
		final Panel panelComponent;
		if (component instanceof Panel)
		{
			panelComponent = (Panel)panel.getComponent();
		}
		else
		{
			log.log(Level.WARNING, "Panel component not supported for "+paymentRule+" (panel="+panel+")");
			return ;
		}
		
		centerPanel.appendChild(panelComponent);
		centerLayout.add(panelComponent);
		paymentRulePanels.put(paymentRule, panel);
		
		panel.addPropertyChangeListener(paymentRulePanelsListener); //Only for credit card payment. Does nothing for the rest.
	}
	
	private ProcessingCtx createContext(final String trxName)
	{
		final ProcessingCtx pctx = new ProcessingCtx();
		pctx.ctx = Env.getCtx();
		pctx.trxName = trxName;
		pctx.isOnlyRule = m_onlyRule;
		pctx.newPaymentRule = getPaymentRule();

		if (doc.getC_CashLine_ID() > 0)
		{
			pctx.oldCashLine = new MCashLine(pctx.ctx, doc.getC_CashLine_ID(), trxName);
			// metas: tsa: because there is no FK on C_Order.C_Payment_ID
			// we need to validate if the payment is still there
			if (pctx.oldCashLine.getC_CashLine_ID() != doc.getC_CashLine_ID())
				pctx.oldCashLine = null;
		}
		else
		{
			pctx.oldCashLine = null;
		}

		pctx.newPayment = null;
		if (doc.getC_Payment_ID() > 0)
		{
			pctx.oldPayment = new MPayment(pctx.ctx, doc.getC_Payment_ID(), trxName);
			// metas: tsa: because there is no FK on C_Order.C_Payment_ID
			// we need to validate if the payment is still there
			if (pctx.oldPayment.getC_Payment_ID() != doc.getC_Payment_ID())
				pctx.oldPayment = null;
		}
		else
		{
			pctx.oldPayment = null;
			// pctx.newPayment = newPayment(trxName);
		}

		return pctx;
	}
	
	private static List<ValueNamePair> getPaymentRules(WButtonEditor button)
	{
		final List<ValueNamePair> list = new ArrayList<ValueNamePair>();

		final HashMap<?, ?> values = button.getValues();
		for (Map.Entry<?, ?> e : values.entrySet())
		{
			final String paymentRule = (String)e.getKey(); // used for Panel selection
			final String paymentRuleName = (String)e.getValue();
			list.add(new ValueNamePair(paymentRule, paymentRuleName));
		}
		return list;
	}
	
	private void dynInitPaymentRules(List<ValueNamePair> paymentRules)
	{
		for (ValueNamePair pp : paymentRules)
		{
			final String paymentRule = pp.getValue();

			// metas: commenting this out because e.g. for a credit memo one
			// might want to select DirectDeposit despite IsSOTrx='Y'
			/*
			 * if (X_C_Order.PAYMENTRULE_DirectDebit.equals(PaymentRule) // SO && !m_isSOTrx) continue; else if
			 * (X_C_Order.PAYMENTRULE_DirectDeposit.equals(PaymentRule) // PO && m_isSOTrx) continue;
			 */
			// metas end

			//
			// If there is no panel for this payment rule, we are not including it in drop down
			IVPaymentPanel panel = addPaymentRulePanel(paymentRule);
			if (panel == null)
			{
				log.warning("No panel found for payment rule " + paymentRule + " [SKIP]");
				continue;
			}
			panel.setEnabled(!m_onlyRule);

			paymentCombo.addItem(pp);
		}
		
		ValueNamePair vp = paymentRules.get(0);
		
		paymentCombo.addActionListener(this);
		if (vp != null)
		{
			paymentCombo.setSelectedValueNamePair(vp);
			onPaymentComboSelection();
		}
		// Set PaymentRule

	}

	// metas: end
} // VPayment
