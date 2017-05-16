package org.compiere.grid;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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

import javax.swing.JComponent;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.ADialog;
import org.compiere.grid.ed.VNumber;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentValidate;
import org.compiere.model.MSysConfig;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.ValueNamePair;

public class VCreditCardPanel extends AbstractCreditCardPanel
{
	private final Logger log = LogManager.getLogger(getClass());

	public static final String MODE_VALIDATE = VCreditCardPanel.class.getName() + ".validate";
	public static final String MODE_PAY = VCreditCardPanel.class.getName() + ".pay";

	public static final String EXP_ERROR = VCreditCardPanel.class.getName() + ".expError";
	public static final String NUMBER_ERROR = VCreditCardPanel.class.getName() + ".numberError";

	public static final String AMOUNT = VCreditCardPanel.class.getName() + ".amount";
	public static final String AMOUNT_RW = VCreditCardPanel.class.getName() + ".amount_rw";
	public static final String AMOUNT_SHOW = VCreditCardPanel.class.getName() + ".amount_show";

	public static final String NUMBER = VCreditCardPanel.class.getName() + ".number";
	public static final String NUMBER_RW = VCreditCardPanel.class.getName() + ".number_rw";

	public static final String TYPES = VCreditCardPanel.class.getName() + ".types";
	public static final String TYPES_SHOW = VCreditCardPanel.class.getName() + ".types_show";
	public static final String NAME = VCreditCardPanel.class.getName() + ".name";
	public static final String NAME_SHOW = VCreditCardPanel.class.getName() + ".name_show";
	public static final String EXP = VCreditCardPanel.class.getName() + ".exp";
	public static final String APPROVAL = VCreditCardPanel.class.getName() + ".approval";
	public static final String STATUS = VCreditCardPanel.class.getName() + ".status";
	public static final String TYPE_RW = VCreditCardPanel.class.getName() + ".type_rw";
	public static final String TYPE_SELECTED = VCreditCardPanel.class.getName() + ".type_selected";

	public static final String NAME_RW = VCreditCardPanel.class.getName() + ".name_rw";
	public static final String EXP_RW = VCreditCardPanel.class.getName() + ".exp_rw";
	public static final String APPROVAL_RW = VCreditCardPanel.class.getName() + ".approval_rw";
	public static final String STATUS_RW = VCreditCardPanel.class.getName() + ".status_rw";
	public static final String ONLINE_RW = VCreditCardPanel.class.getName() + ".online_rw";
	public static final String APPROVAL_ERROR = VCreditCardPanel.class.getName() + ".approval_error";

	private GridBagLayout kLayout = new GridBagLayout();
	private CPanel kPanel = new CPanel();
	private CLabel kTypeLabel = new CLabel();
	private CComboBox kTypeCombo = new CComboBox();
	private CLabel kNumberLabel = new CLabel();
	private CTextField kNumberField = new CTextField();
	private CLabel kNameLabel = new CLabel();
	private CTextField kNameField = new CTextField();
	private CLabel kExpLabel= new CLabel();
	private CTextField kExpField= new CTextField();
	private CLabel kApprovalLabel= new CLabel();
	private CTextField kApprovalField= new CTextField();
	private CLabel kAmountLabel= new CLabel();
	private CLabel kStatus= new CLabel();
	private CButton kOnline= new CButton();
	private final VNumber kAmountField = new VNumber();
	private JComponent[] fields= new JComponent[] {
			kTypeCombo,
			kNumberField,
			kNameField,
			kExpField,
			kApprovalField,
			kAmountField,
			kOnline,
	};
	private VPaymentPanelUIHelper uiHelper = new VPaymentPanelUIHelper(this, fields);

	private final String mode;

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public VCreditCardPanel()
	{
		this(MODE_PAY);
	}

	public VCreditCardPanel(final String mode)
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
		setProperty(VCreditCardPanel.TYPE_SELECTED, payment.getCreditCardType());
		setProperty(VCreditCardPanel.NUMBER, payment.getCreditCardNumber());
		setProperty(VCreditCardPanel.NAME, payment.getA_Name());
		setProperty(VCreditCardPanel.EXP, ccExp);
		setProperty(VCreditCardPanel.APPROVAL, payment.getCreditCardVV());
		setProperty(VCreditCardPanel.STATUS, payment.getR_RespMsg());
		setProperty(VCreditCardPanel.AMOUNT, payment.getPayAmt());

		// if approved/paid, don't let it change
		boolean isPaid = payment != null && payment.isProcessed();
		boolean isOnlineApproved = payment != null && payment.isOnlineApproved();
		setProperty(VCreditCardPanel.TYPE_RW, !isOnlineApproved);
		setProperty(VCreditCardPanel.NUMBER_RW, !isOnlineApproved);
		setProperty(VCreditCardPanel.NAME_RW, !isOnlineApproved);
		setProperty(VCreditCardPanel.EXP_RW, !isOnlineApproved);
		setProperty(VCreditCardPanel.APPROVAL_RW, !isOnlineApproved);
		setProperty(VCreditCardPanel.AMOUNT_RW, !isPaid && !isOnlineApproved);
		setProperty(VCreditCardPanel.ONLINE_RW, !isOnlineApproved);
	}

	private void loadCCTypes(MPayment payment)
	{
		setProperty(VCreditCardPanel.TYPES, retrieveCreditCardTypes(payment));
	}

	private void loadCCTypes()
	{
		setProperty(VCreditCardPanel.TYPES, retrieveCreditCardTypes());
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
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		uiHelper.updateFieldRO();
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
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		uiHelper.updateFieldRO();
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

		// CreditCard
		if (doc != null)
			setProperty(VCreditCardPanel.AMOUNT, doc.getGrandTotal());
		else
			setProperty(VCreditCardPanel.AMOUNT, Env.ONE);
		loadCCTypes();
		setProperty(VCreditCardPanel.TYPE_SELECTED, org.compiere.model.X_C_Payment.CREDITCARDTYPE_Visa);
		setProperty(VCreditCardPanel.NUMBER, "4111 1111 1111 1111");
		setProperty(VCreditCardPanel.NAME, "Generated test name " + System.currentTimeMillis());
		setProperty(VCreditCardPanel.EXP, "1212");
		setProperty(VCreditCardPanel.APPROVAL, "123");
		setProperty(VCreditCardPanel.STATUS, null);
	}
	
	public BigDecimal getAmount()
	{
		return (BigDecimal)kAmountField.getValue();
	}
	
	@Override
	public void setAmount(BigDecimal amount)
	{
		kAmountField.setValue(amount);
	}

}
