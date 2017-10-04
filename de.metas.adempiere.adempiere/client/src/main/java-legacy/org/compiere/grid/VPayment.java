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
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.BorderFactory;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.ed.VButton;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Order;
import org.compiere.model.MCashLine;
import org.compiere.model.MPayment;
import org.compiere.model.X_C_Order;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TrxRunnable2;
import org.compiere.util.ValueNamePair;

import de.metas.currency.ICurrencyDAO;
import de.metas.document.engine.IDocument;
import de.metas.i18n.IMsgBL;

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
 *  - If an invoice is B and has no Cash Entry, it is created
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
 * @author Michael Judd, Akuna Ltd <li>FR [ 2803341 ] Deprecate Cash Journal
 */
public class VPayment extends CDialog
		implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7931457502030396154L;

	private static final Map<String, Class<? extends IVPaymentPanel>> paymentRulePanelClasses = new HashMap<String, Class<? extends IVPaymentPanel>>();
	static
	{
		registerPaymentRulePanel(X_C_Order.PAYMENTRULE_Cash, VPaymentCash.class);
		registerPaymentRulePanel(X_C_Order.PAYMENTRULE_Check, VPaymentCheck.class);
		registerPaymentRulePanel(X_C_Order.PAYMENTRULE_CreditCard, VCreditCardPanel.class);
		registerPaymentRulePanel(X_C_Order.PAYMENTRULE_DirectDebit, VPaymentDirectDebit.class);
		registerPaymentRulePanel(X_C_Order.PAYMENTRULE_DirectDeposit, VPaymentDirectDebit.class);
		registerPaymentRulePanel(X_C_Order.PAYMENTRULE_OnCredit, VPaymentOnCredit.class);
	}

	public static void registerPaymentRulePanel(String paymentRule, Class<? extends IVPaymentPanel> clazz)
	{
		paymentRulePanelClasses.put(paymentRule, clazz);
	}

	/** Logger */
	private static final Logger log = LogManager.getLogger(VPayment.class);

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
	public VPayment(int WindowNo, GridTab mTab, VButton button)
	{
		super(Env.getWindow(WindowNo), Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Payment"), true);
		m_ctx = Env.getCtx();
		m_WindowNo = WindowNo;
		m_mTab = mTab;
		doc = InterfaceWrapperHelper.create(m_mTab, IPayableDocument.class);
		try
		{
			jbInit();
			m_initOK = dynInit(getPaymentRules(button)); // Null Pointer if order/invoice not saved yet
		}
		catch (Throwable ex)
		{
			log.error("VPayment", ex);
			ADialog.error(m_WindowNo, this, "PaymentError", ex.getLocalizedMessage());
			m_initOK = false;
			dispose();
			return;
		}
		//
		AEnv.positionCenterWindow(Env.getWindow(WindowNo), this);
	} // VPayment

	public Properties getCtx()
	{
		return m_ctx;
	}

	private final Properties m_ctx;
	/** Window */
	private final int m_WindowNo;
	/** Tab */
	private final GridTab m_mTab;
	private final IPayableDocument doc;

	// Data from Order/Invoice
	// private String m_DocStatus = null;
	/** Start Payment Rule */
	private String m_PaymentRule = "";

	//
	private boolean m_initOK = false;
	/** Only allow changing Rule */
	private boolean m_onlyRule = false;

	private boolean m_needSave = false;
	
	private boolean isReadOnly = false;

	//
	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel northPanel = new CPanel();
	private CPanel centerPanel = new CPanel();
	private FlowLayout northLayout = new FlowLayout();
	private CLabel paymentLabel = new CLabel();
	private CComboBox<ValueNamePair> paymentCombo = new CComboBox<>();
	private CardLayout centerLayout = new CardLayout();

	private final Map<String, IVPaymentPanel> paymentRulePanels = new HashMap<String, IVPaymentPanel>();

	private ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(true)
			.build();
	
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

	/**
	 * Static Init
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		centerPanel.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(mainPanel);
		mainPanel.setLayout(mainLayout);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		//
		northPanel.setLayout(northLayout);
		paymentLabel.setText(Services.get(IMsgBL.class).translate(getCtx(), "PaymentRule"));
		mainPanel.add(northPanel, BorderLayout.NORTH);
		northPanel.add(paymentLabel, null);
		northPanel.add(paymentCombo, null);
		//
		centerPanel.setLayout(centerLayout);
		//
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
	} // jbInit

	private static List<ValueNamePair> getPaymentRules(VButton button)
	{
		final List<ValueNamePair> list = new ArrayList<ValueNamePair>();

		final Map<String, String> values = button.getValues();
		for (Map.Entry<String, String> e : values.entrySet())
		{
			final String paymentRule = e.getKey(); // used for Panel selection
			final String paymentRuleName = e.getValue();
			list.add(new ValueNamePair(paymentRule, paymentRuleName));
		}
		return list;
	}

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

	private void addPaymentRulePanel(String paymentRule, IVPaymentPanel panel)
	{
		String name = paymentRule.toLowerCase() + "Panel";
		centerPanel.add((Component)panel.getComponent(), name);
		centerLayout.addLayoutComponent((Component)panel.getComponent(), name);
		paymentRulePanels.put(paymentRule, panel);
		
		panel.addPropertyChangeListener(paymentRulePanelsListener); //Only for credit card payment. Does nothing for the rest.
	}

	private void activatePaymentRulePanel(String paymentRule)
	{
		if (isOnlyRule())
			return;

		final String name = paymentRule.toLowerCase() + "Panel";
		centerLayout.show(centerPanel, name); // switch to panel

		final IVPaymentPanel panel = paymentRulePanels.get(paymentRule);
		panel.onActivate();

		confirmPanel.getOKButton().setEnabled(panel.isAllowProcessing());
	}

	private boolean dynInit(List<ValueNamePair> paymentRules) throws Exception
	{
		if (doc.getC_BPartner_ID() <= 0)
		{
			throw new AdempiereException("@SaveErrorRowNotFound@");
		}

		// Is the Trx posted?
		// String Posted = (String)m_mTab.getValue("Posted");
		// if (Posted != null && Posted.equals("Y"))
		// return false;

		// DocStatus
		final String docStatus = doc.getDocStatus() == null ? "" : doc.getDocStatus();
		log.info(docStatus);
		// Is the Trx closed? Reversed / Voided / Closed
		if (IDocument.STATUS_Reversed.equals(docStatus)
				|| IDocument.STATUS_Voided.equals(docStatus)
				|| IDocument.STATUS_Closed.equals(docStatus))
		{
			log.info("Document is reversed/voided/closed => nothing to do");
			return false;
		}

		m_onlyRule = isDocumentPayable(doc);

		centerPanel.setVisible(!m_onlyRule);

		dynInitPaymentRules(paymentRules);

		// Amount
		BigDecimal payAmount = doc.getGrandTotal();
		if (!m_onlyRule && Check.isEmpty(payAmount))
		{
			throw new AdempiereException("@PaymentZero@");
		}

		final int currencyPrecision = Services.get(ICurrencyDAO.class).getStdPrecision(getCtx(), doc.getC_Currency_ID());
		payAmount.setScale(currencyPrecision, BigDecimal.ROUND_HALF_UP);

		ProcessingCtx pctx = createContext(null);

		for (IVPaymentPanel panel : paymentRulePanels.values())
		{
			panel.setReadOnly(false);
			panel.setFrom(doc);
			panel.setFrom(pctx.oldPayment);
			panel.setFrom(pctx.oldCashLine);
			panel.setAmount(payAmount);
			panel.setC_Currency_ID(doc.getC_Currency_ID());
		}

		setPaymentRule(doc.getPaymentRule());
		
		final boolean hasPayments =
				pctx.oldPayment != null && pctx.oldPayment.getC_Payment_ID() > 0
				|| (pctx.oldCashLine != null && pctx.oldCashLine.getC_CashLine_ID() > 0);
		if (hasPayments)
		{
			setIsReadOnly(true);
		}

		//
		return true;
	} // dynInit

	private boolean isOnlyRule()
	{
		return m_onlyRule;
	}

	/**
	 * 
	 * @param doc
	 * @return true if we can create directly a payment for this document, false if only payment rule selection is
	 *         allowed
	 */
	private static boolean isDocumentPayable(IPayableDocument doc)
	{
		boolean isPayable = false;
		final String docStatus = doc.getDocStatus();
		if (IDocument.STATUS_Reversed.equals(docStatus)
				|| IDocument.STATUS_Voided.equals(docStatus)
				|| IDocument.STATUS_Closed.equals(docStatus))
		{
			log.info("Document is reversed/voided/closed => nothing to do");
			return false;
		}
		// Document is not complete - allow to change the Payment Rule only
		if (IDocument.STATUS_Completed.equals(docStatus)
				|| IDocument.STATUS_WaitingPayment.equals(docStatus))
		{
			log.info("Document is completed/WP => payable=false");
			return false;
		}
		else
		{
			log.info("Document is NOT completed/WP => onlyRule=true");
			isPayable = true;
		}
		// PO only Rule
		if (!isPayable
				&& !doc.isSOTrx()
				&& I_C_Order.Table_Name.equals(doc.get_TableName())) // Is Order
		{
			log.info("Document is an Order => onlyRule=true");
			isPayable = true;
		}

		return isPayable;
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
				log.warn("No panel found for payment rule " + paymentRule + " [SKIP]");
				continue;
			}
			panel.setEnabled(!isOnlyRule());

			paymentCombo.addItem(pp);
		}

		// Set PaymentRule
		paymentCombo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ValueNamePair vp = paymentCombo.getSelectedItem();
				String paymentRule = vp != null ? vp.getValue() : null;
				m_PaymentRule = paymentRule;

				activatePaymentRulePanel(paymentRule);
			}
		});
	}

	public static void loadComboValues(CComboBox<KeyNamePair> combo, KeyNamePair[] values, int defaultKey)
	{
		combo.removeAllItems();

		if (Check.isEmpty(values))
			return;

		KeyNamePair kp = null;
		for (KeyNamePair pp : values)
		{
			combo.addItem(pp);
			if (pp.getKey() == defaultKey)
			{
				kp = pp;
			}
		}
		// Set Selection
		if (kp != null)
			combo.setSelectedItem(kp);
	}

	/**
	 * Init OK to be able to make changes?
	 * 
	 * @return true if init OK
	 */
	public boolean isInitOK()
	{
		return m_initOK;
	} // isInitOK

	public Hashtable<Integer, KeyNamePair> getCurrencies()
	{
		if (s_Currencies == null)
			s_Currencies = loadCurrencies();
		return s_Currencies;
	}

	private static Hashtable<Integer, KeyNamePair> s_Currencies = null; // EMU Currencies

	/**
	 * Fill s_Currencies with EMU currencies
	 * 
	 * @return
	 */
	private static Hashtable<Integer, KeyNamePair> loadCurrencies()
	{
		final Hashtable<Integer, KeyNamePair> currencies = new Hashtable<Integer, KeyNamePair>(20);
		final String SQL = "SELECT C_Currency_ID, ISO_Code FROM C_Currency "
				+ "WHERE (IsEMUMember='Y' AND EMUEntryDate<now()) OR IsEuro='Y' "
				+ "ORDER BY 2";
		for (KeyNamePair knp : DB.getKeyNamePairs(SQL, false))
		{
			currencies.put(knp.getKey(), knp);
		}
		return currencies;
	} // loadCurrencies

	@Override
	public void actionPerformed(ActionEvent e)
	{
		log.debug("VPayment.actionPerformed - " + e.getActionCommand());

		// Finish
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			process();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}
	}

	private ProcessingCtx createContext(final String trxName)
	{
		final ProcessingCtx pctx = new ProcessingCtx();
		pctx.ctx = getCtx();
		pctx.trxName = trxName;
		pctx.isOnlyRule = isOnlyRule();
		pctx.newPaymentRule = getPaymentRule();

		if (doc.getC_CashLine_ID() > 0)
		{
			pctx.oldCashLine = new MCashLine(getCtx(), doc.getC_CashLine_ID(), trxName);
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
			pctx.oldPayment = new MPayment(getCtx(), doc.getC_Payment_ID(), trxName);
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

	/**
	 * Check Mandatory
	 * 
	 * @return true if all mandatory items are OK
	 */
	private boolean checkMandatory(String trxName)
	{
		String PaymentRule = getPaymentRule();
		// only Payment Rule
		if (isOnlyRule())
			return true;

		IVPaymentPanel panel = paymentRulePanels.get(PaymentRule);
		if (panel == null)
		{
			log.error("Unknown PaymentRule " + PaymentRule);
			ADialog.error(m_WindowNo, this, "Unknown PaymentRule " + PaymentRule);
			return false;
		}

		if (!panel.validate(trxName != null))
			return false;

		return true;
	} // checkMandatory

	public String getPaymentRule()
	{
		// value already set by paymentCombo actionListener
		return m_PaymentRule;
	}

	public void setPaymentRule(String paymentRule)
	{
		m_PaymentRule = paymentRule;

		for (int i = 0; i < paymentCombo.getItemCount(); i++)
		{
			ValueNamePair vp = paymentCombo.getItemAt(i);
			if (paymentRule.equals(vp.getValue()))
			{
				paymentCombo.setSelectedIndex(i);
				break;
			}
		}
	}
	
	public void setIsReadOnly(boolean isReadOnly)
	{
		this.isReadOnly = isReadOnly;
		
		// Update UI:
		paymentCombo.setEnabled(!isReadOnly);
		for (IVPaymentPanel panel : paymentRulePanels.values())
		{
			panel.setReadOnly(isReadOnly);
		}
	}
	
	public boolean isReadOnly()
	{
		return this.isReadOnly;
	}

	public int getC_Order_ID()
	{
		return Env.getContextAsInt(getCtx(), m_WindowNo, "C_Order_ID");
	}

	public int getC_Invoice_ID()
	{
		return Env.getContextAsInt(getCtx(), m_WindowNo, "C_Invoice_ID");
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

	private void process()
	{
		PaymentProcess p = new PaymentProcess();
		if (!p.prepare())
		{
			// we need more input corrections
			return;
		}

		Services.get(ITrxManager.class).run(p);
	}

	public static class ProcessingCtx
	{
		public Properties ctx;
		public String trxName;
		public String newPaymentRule;
		public boolean isParentNeedSave = false;
		public boolean isNegateAmt = false;

		public MPayment oldPayment;
		public MPayment newPayment;
		public boolean isOnline = false;

		public MCashLine oldCashLine;
		public MCashLine newCashLine;

		public int newC_PaymentTerm_ID = -1;

		public List<String> info = new ArrayList<String>();
		public boolean isOnlyRule;
	}

	private class PaymentProcess implements TrxRunnable2
	{
		private Cursor origCursor;

		private VPaymentProcess proc;
		private Throwable exception;

		public boolean prepare()
		{
			return checkMandatory(null);
		}

		@Override
		public final void run(String trxName)
		{
			origCursor = getCursor();
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			final ProcessingCtx pctx = createContext(trxName);
			final IVPaymentPanel panel = paymentRulePanels.get(pctx.newPaymentRule);
			proc = new VPaymentProcess(pctx, doc, panel);

			if (!IDocument.STATUS_Drafted.equals(doc.getDocStatus())) // metas: cg: 0217
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
			if (origCursor != null)
				setCursor(origCursor);

			if (exception != null)
			{
				log.info(exception.getLocalizedMessage(), exception);
				ADialog.error(m_WindowNo, VPayment.this, "PaymentError", exception.getLocalizedMessage());
				return;
			}

			proc.updatePayableDocument();
			String info = proc.getInfo();
			if (!Check.isEmpty(info))
				ADialog.info(m_WindowNo, VPayment.this, "PaymentCreated", info);
			VPayment.this.dispose();
		}

	}

} // VPayment
