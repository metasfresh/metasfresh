package de.metas.adempiere.gui;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import javax.swing.JComponent;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.ADialog;
import org.compiere.grid.IPayableDocument;
import org.compiere.grid.IVPaymentPanel;
import org.compiere.grid.VPayment.ProcessingCtx;
import org.compiere.grid.VPaymentPanelUIHelper;
import org.compiere.grid.ed.VNumber;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentValidate;
import org.compiere.model.MSysConfig;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

public class CreditCardPanel implements PropertyChangeListener, IVPaymentPanel
{
	private final Logger log = LogManager.getLogger(getClass());

	public static final String MODE_VALIDATE = CreditCardPanel.class.getName() + ".validate";
	public static final String MODE_PAY = CreditCardPanel.class.getName() + ".pay";

	public static final String EXP_ERROR = CreditCardPanel.class.getName() + ".expError";
	public static final String NUMBER_ERROR = CreditCardPanel.class.getName() + ".numberError";

	public static final String AMOUNT = CreditCardPanel.class.getName() + ".amount";
	public static final String AMOUNT_RW = CreditCardPanel.class.getName() + ".amount_rw";
	public static final String AMOUNT_SHOW = CreditCardPanel.class.getName() + ".amount_show";

	public static final String NUMBER = CreditCardPanel.class.getName() + ".number";
	public static final String NUMBER_RW = CreditCardPanel.class.getName() + ".number_rw";

	public static final String TYPES = CreditCardPanel.class.getName() + ".types";
	public static final String TYPES_SHOW = CreditCardPanel.class.getName() + ".types_show";
	public static final String NAME = CreditCardPanel.class.getName() + ".name";
	public static final String NAME_SHOW = CreditCardPanel.class.getName() + ".name_show";
	public static final String EXP = CreditCardPanel.class.getName() + ".exp";
	public static final String APPROVAL = CreditCardPanel.class.getName() + ".approval";
	public static final String STATUS = CreditCardPanel.class.getName() + ".status";
	public static final String TYPE_RW = CreditCardPanel.class.getName() + ".type_rw";
	public static final String TYPE_SELECTED = CreditCardPanel.class.getName() + ".type_selected";

	public static final String NAME_RW = CreditCardPanel.class.getName() + ".name_rw";
	public static final String EXP_RW = CreditCardPanel.class.getName() + ".exp_rw";
	public static final String APPROVAL_RW = CreditCardPanel.class.getName() + ".approval_rw";
	public static final String STATUS_RW = CreditCardPanel.class.getName() + ".status_rw";
	public static final String ONLINE_RW = CreditCardPanel.class.getName() + ".online_rw";
	public static final String APPROVAL_ERROR = CreditCardPanel.class.getName() + ".approval_error";

	private final GridBagLayout kLayout = new GridBagLayout();
	private final CPanel kPanel = new CPanel();
	private final CLabel kTypeLabel = new CLabel();
	private final CComboBox kTypeCombo = new CComboBox();
	private final CLabel kNumberLabel = new CLabel();
	private final CTextField kNumberField = new CTextField();
	private final CLabel kNameLabel = new CLabel();
	private final CTextField kNameField = new CTextField();
	private final CLabel kExpLabel = new CLabel();
	private final CTextField kExpField = new CTextField();
	private final CLabel kApprovalLabel = new CLabel();
	private final CTextField kApprovalField = new CTextField();
	private final CLabel kAmountLabel = new CLabel();
	private final VNumber kAmountField = new VNumber();
	private final CLabel kStatus = new CLabel();
	private final CButton kOnline = new CButton();
	private final JComponent[] fields = new JComponent[] {
			kTypeCombo,
			kNumberField,
			kNameField,
			kExpField,
			kApprovalField,
			kAmountField,
			kOnline,
	};
	private final VPaymentPanelUIHelper uiHelper = new VPaymentPanelUIHelper(this, fields);

	private final String mode;
	private IPayableDocument doc;
	private Timestamp date = TimeUtil.getDay(null); // today
	private int currencyId = -1;
	private boolean enabled;

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public CreditCardPanel()
	{
		this(MODE_PAY);
	}

	public CreditCardPanel(final String mode)
	{
		this.mode = mode;
		initUI();
	}

	private void initUI()
	{
		kPanel.setLayout(kLayout);

		kNumberField.setPreferredSize(new Dimension(160, 21));
		kNameField.setPreferredSize(new Dimension(160, 21));
		kExpField.setPreferredSize(new Dimension(40, 21));
		kApprovalField.setPreferredSize(new Dimension(120, 21));
		kTypeLabel.setText(Msg.translate(Env.getCtx(), "CreditCardType"));
		kNumberLabel.setText(Msg.translate(Env.getCtx(), "CreditCardNumber"));
		kNameLabel.setText(Msg.translate(Env.getCtx(), "Name"));
		kExpLabel.setText(Msg.getMsg(Env.getCtx(), "Expires"));
		kApprovalLabel.setText(Msg.translate(Env.getCtx(), "VoiceAuthCode"));
		kAmountLabel.setText(Msg.getMsg(Env.getCtx(), "Amount"));
		kOnline.setText(Msg.getMsg(Env.getCtx(), "Online"));

		kStatus.setText(" ");

		if (MODE_PAY.equals(mode))
		{

			kPanel.add(kTypeLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 2, 0), 0, 0));
			kPanel.add(kTypeCombo, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
		}
		kPanel.add(kNumberLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 2, 0), 0, 0));
		kPanel.add(kNumberField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 5, 2, 5), 0, 0));

		if (MODE_PAY.equals(mode))
		{
			kPanel.add(kNameLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 2, 0), 0, 0));
			kPanel.add(kNameField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 5, 2, 5), 0, 0));
		}
		kPanel.add(kExpLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 2, 0), 0, 0));
		kPanel.add(kExpField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));

		if (MODE_PAY.equals(mode))
		{
			kPanel.add(kAmountLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 5, 0), 0, 0));
			kAmountField.setDisplayType(DisplayType.Amount);
			kPanel.add(kAmountField, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 5, 5), 0, 0));
		}
		kPanel.add(kApprovalLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
		kPanel.add(kApprovalField, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		kPanel.add(kStatus, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		kPanel.add(kOnline, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		kOnline.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				pcs.firePropertyChange(ACTION_Online, false, true);
			}
		});
		
		uiHelper.updateFieldRO();
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(listener);
	}

	public String getExp()
	{
		return kExpField.getText();
	}

	public String getNumber()
	{
		return kNumberField.getText();
	}

	public String getCardCode()
	{
		return kApprovalField.getText();
	}

	@Override
	public void setAmount(BigDecimal amount)
	{
		if (!kAmountField.isEnabled())
		{
			// don't change the amount if is already readonly
			return;
		}
		kAmountField.setValue(amount);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt)
	{
		final String propName = evt.getPropertyName();
		final Object newValue = evt.getNewValue();
		setProperty(propName, newValue);

		// Forward event:
		PropertyChangeEvent evt2 = new PropertyChangeEvent(this, propName, null, newValue);
		evt2.setPropagationId(evt.getPropagationId());
		pcs.firePropertyChange(evt2);
	}

	private final boolean setProperty(final String propName, final Object newValue)
	{
		if (AMOUNT.equals(propName))
		{
			setAmount((BigDecimal)newValue);
		}
		else if (NUMBER.equals(propName))
		{
			kNumberField.setValue(newValue);
		}
		else if (NUMBER_RW.equals(propName))
		{
			boolean rw = (Boolean)newValue;
			uiHelper.setFieldReadOnly(kNumberField, !rw);
		}
		else if (NAME.equals(propName))
		{
			kNameField.setValue(newValue);
		}
		else if (NAME_RW.equals(propName))
		{
			boolean rw = (Boolean)newValue;
			uiHelper.setFieldReadOnly(kNameField, !rw);
		}
		else if (NAME_SHOW.equals(propName))
		{
			final Boolean show = (Boolean)newValue;
			kNameLabel.setVisible(show);
			kNameField.setVisible(show);
		}
		else if (EXP.equals(propName))
		{
			kExpField.setValue(newValue);
		}
		else if (EXP_RW.equals(propName))
		{
			boolean rw = (Boolean)newValue;
			uiHelper.setFieldReadOnly(kExpField, !rw);
		}
		else if (APPROVAL.equals(propName))
		{
			kApprovalField.setValue(newValue);
		}
		else if (APPROVAL_RW.equals(propName))
		{
			boolean rw = (Boolean)newValue;
			uiHelper.setFieldReadOnly(kApprovalField, !rw);
		}
		else if (APPROVAL_ERROR.equals(propName))
		{
			kApprovalField.setBackground(AdempierePLAF.getFieldBackground_Error());
		}
		else if (STATUS.equals(propName))
		{
			kStatus.setText((String)newValue);
		}
		else if (STATUS_RW.equals(propName))
		{
			boolean rw = (Boolean)newValue;
			uiHelper.setFieldReadOnly(kStatus, !rw);
		}
		else if (TYPE_RW.equals(propName))
		{
			boolean rw = (Boolean)newValue;
			uiHelper.setFieldReadOnly(kTypeCombo, !rw);
		}
		else if (AMOUNT_RW.equals(propName))
		{
			boolean rw = newValue instanceof Boolean ? ((Boolean)newValue).booleanValue() : false;
			uiHelper.setFieldReadOnly(kAmountField, !rw);
		}
		else if (ONLINE_RW.equals(propName))
		{
			boolean rw = newValue instanceof Boolean ? ((Boolean)newValue).booleanValue() : false;
			uiHelper.setFieldReadOnly(kOnline, !rw);
		}
		else if (EXP_ERROR.equals(propName) && Boolean.TRUE.equals(newValue))
		{
			kExpField.setBackground(AdempierePLAF.getFieldBackground_Error());
		}
		else if (NUMBER_ERROR.equals(propName) && Boolean.TRUE.equals(newValue))
		{
			kNumberField.setBackground(AdempierePLAF.getFieldBackground_Error());
		}
		else if (TYPES.equals(propName))
		{
			final ValueNamePair[] ccs = (ValueNamePair[])newValue;
			final String ccType = getCCType();
			kTypeCombo.removeAllItems();
			for (int i = 0; i < ccs.length; i++)
			{
				kTypeCombo.addItem(ccs[i]);

			}
			setCCType(ccType);
			isCreditCardTypesLoaded = true;
		}
		else if (TYPE_SELECTED.equals(propName))
		{
			String ccType = (String)newValue;
			setCCType(ccType);
		}
		else
		{
			return false; // nothing
		}

		return true; // property set
	}

	private void setCCType(final String newValue)
	{

		if (Util.isEmpty(newValue))
		{
			return;
		}

		for (int i = 0; i < kTypeCombo.getItemCount(); i++)
		{
			final ValueNamePair currentItem = (ValueNamePair)kTypeCombo.getItemAt(i);
			if (currentItem.getValue().equals(newValue))
			{
				kTypeCombo.setSelectedItem(currentItem);
				break;
			}
		}
	}

	public String getCCType()
	{
		ValueNamePair vp = (ValueNamePair)kTypeCombo.getSelectedItem();
		String CCType = vp == null ? null : vp.getValue();
		return CCType;
	}

	public void addOnlineButtonListener(final ActionListener l)
	{
		kOnline.addActionListener(l);
	}

	@Override
	public BigDecimal getAmount()
	{
		return (BigDecimal)kAmountField.getValue();
	}

	@Override
	public void setFrom(IPayableDocument doc)
	{
		this.doc = doc;
	}

	@Override
	public void setFrom(I_C_CashLine cashline)
	{
	}

	@Override
	public void setFrom(I_C_Payment payment)
	{
		if (payment == null)
		{
			set_TestData();
			return;
		}

		final MPayment paymentPO = (MPayment)InterfaceWrapperHelper.getPO(payment);
		final String ccExp = paymentPO.getCreditCardExp(null); // TODO: refactor and include it in PaymentBL

		loadCCTypes(paymentPO);

		// CreditCard
		setProperty(CreditCardPanel.TYPE_SELECTED, payment.getCreditCardType());
		setProperty(CreditCardPanel.NUMBER, payment.getCreditCardNumber());
		setProperty(CreditCardPanel.NAME, payment.getA_Name());
		setProperty(CreditCardPanel.EXP, ccExp);
		setProperty(CreditCardPanel.APPROVAL, payment.getCreditCardVV());
		setProperty(CreditCardPanel.STATUS, payment.getR_RespMsg());
		setProperty(CreditCardPanel.AMOUNT, payment.getPayAmt());

		// if approved/paid, don't let it change
		boolean isPaid = payment != null && payment.isProcessed();
		boolean isOnlineApproved = payment != null && payment.isOnlineApproved();
		setProperty(CreditCardPanel.TYPE_RW, !isOnlineApproved);
		setProperty(CreditCardPanel.NUMBER_RW, !isOnlineApproved);
		setProperty(CreditCardPanel.NAME_RW, !isOnlineApproved);
		setProperty(CreditCardPanel.EXP_RW, !isOnlineApproved);
		setProperty(CreditCardPanel.APPROVAL_RW, !isOnlineApproved);
		setProperty(CreditCardPanel.AMOUNT_RW, !isPaid && !isOnlineApproved);
		setProperty(CreditCardPanel.ONLINE_RW, !isOnlineApproved);
	}

	private boolean isCreditCardTypesLoaded = false;

	private void loadCCTypes(MPayment payment)
	{
		ValueNamePair[] ccs = new ValueNamePair[0];
		// metas: only load credit cards, if it is an available payment rule
		if (isEnabled())
		{
			ccs = payment.getCreditCards();
		}
		setProperty(CreditCardPanel.TYPES, ccs);
	}

	private void loadCCTypes()
	{
		ValueNamePair[] ccs = new ValueNamePair[0];
		if (isEnabled())
		{
			MPayment payment = new MPayment(getCtx(), 0, null);
			if (doc != null)
			{
				// payment.setAD_Client_ID(doc.getAD_Client_ID());
				payment.setAD_Org_ID(doc.getAD_Org_ID());
			}
			payment.setPayAmt(getAmount());
			payment.setC_Currency_ID(getC_Currency_ID());
			ccs = payment.getCreditCards();
		}
		setProperty(CreditCardPanel.TYPES, ccs);
	}

	public Properties getCtx()
	{
		return Env.getCtx();
	}

	@Override
	public java.awt.Container getComponent()
	{
		return kPanel;
	}

	@Override
	public void onActivate()
	{
		if (!isCreditCardTypesLoaded)
			loadCCTypes();
	}

	@Override
	public Timestamp getDate()
	{
		return date;
	}

	@Override
	public void setDate(Timestamp date)
	{
		this.date = date;
	}

	@Override
	public void setC_Currency_ID(int C_Currency_ID)
	{
		this.currencyId = C_Currency_ID;
	}

	@Override
	public int getC_Currency_ID()
	{
		return currencyId;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
		uiHelper.updateFieldRO();
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public boolean validate(boolean isInTrx)
	{
		boolean dataOK = true;

		String error = MPaymentValidate.validateCreditCardNumber(getNumber(), getCCType());
		if (error.length() != 0)
		{
			setProperty(NUMBER_ERROR, true);
			if (error.indexOf('?') == -1)
			{
				throw new AdempiereException("@" + error + "@");
			}
			else
			// warning
			{
				if (isInTrx)
					throw new AdempiereException("@" + error + "@"); // we can't interrupt transaction with user input
				if (!ADialog.ask(0, getComponent(), error))
					dataOK = false;
			}
		}

		error = MPaymentValidate.validateCreditCardExp(getExp());
		if (error.length() != 0)
		{
			setProperty(EXP_ERROR, true);
			throw new AdempiereException("@" + error + "@");
		}

		return dataOK;
	}

	public String getName()
	{
		return kNameField.getText();
	}

	@Override
	public boolean isAllowProcessing()
	{
		// we are processing credit card payments trough Online button
		return false;
	}

	@Override
	public void updatePayment(ProcessingCtx pctx, MPayment payment)
	{
		payment.setCreditCard(MPayment.TRXTYPE_Sales, this.getCCType(), this.getNumber(), this.getCardCode(), this.getExp());
		payment.setA_Name(this.getName());
		payment.setCreditCardVV(this.getCardCode()); // metas
		payment.setPaymentProcessor();

		pctx.isOnline = true;
	}

	@Override
	public int getC_CashBook_ID()
	{
		return -1;
	}

	@Override
	public String getTargetTableName()
	{
		return I_C_Payment.Table_Name;
	}

	private boolean readonly = true;

	@Override
	public void setReadOnly(boolean readOnly)
	{
		this.readonly = readOnly;
		uiHelper.updateFieldRO();
	}

	@Override
	public boolean isReadOnly()
	{
		return readonly;
	}

	private void set_TestData()
	{
		final String SYSCONFIG_Test = "de.schaeffer.pay.misc.TelecashProcessor.IsSandbox";
		boolean isTest = MSysConfig.getBooleanValue(SYSCONFIG_Test, false,
				Env.getAD_Client_ID(getCtx()),
				Env.getAD_Org_ID(getCtx()));
		if (!isTest)
			return;

		log.info("WARNING: Automatically setting test data because " + SYSCONFIG_Test + "=Y");

		if (doc != null && getC_Currency_ID() <= 0)
			setC_Currency_ID(doc.getC_Currency_ID());

		loadCCTypes();

		// CreditCard
		setProperty(CreditCardPanel.TYPE_SELECTED, org.compiere.model.X_C_Payment.CREDITCARDTYPE_Visa);
		setProperty(CreditCardPanel.NUMBER, "4111 1111 1111 1111");
		setProperty(CreditCardPanel.NAME, "Generated test name " + System.currentTimeMillis());
		setProperty(CreditCardPanel.EXP, "1212");
		setProperty(CreditCardPanel.APPROVAL, "123");
		setProperty(CreditCardPanel.STATUS, null);
		if (doc != null)
			setProperty(CreditCardPanel.AMOUNT, doc.getGrandTotal());
		else
			setProperty(CreditCardPanel.AMOUNT, Env.ONE);
	}
}
