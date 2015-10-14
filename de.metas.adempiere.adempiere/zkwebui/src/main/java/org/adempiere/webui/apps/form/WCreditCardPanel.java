package org.adempiere.webui.apps.form;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.editor.WNumberEditor;
import org.compiere.grid.AbstractCreditCardPanel;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentValidate;
import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.ValueNamePair;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Space;

import de.metas.adempiere.form.IClientUI;

public class WCreditCardPanel extends AbstractCreditCardPanel
{
	private final CLogger log = CLogger.getCLogger(getClass());

	public static final String MODE_VALIDATE = WCreditCardPanel.class.getName() + ".validate";
	public static final String MODE_PAY = WCreditCardPanel.class.getName() + ".pay";

	public static final String EXP_ERROR = WCreditCardPanel.class.getName() + ".expError";
	public static final String NUMBER_ERROR = WCreditCardPanel.class.getName() + ".numberError";

	public static final String AMOUNT = WCreditCardPanel.class.getName() + ".amount";
	public static final String AMOUNT_RW = WCreditCardPanel.class.getName() + ".amount_rw";
	public static final String AMOUNT_SHOW = WCreditCardPanel.class.getName() + ".amount_show";

	public static final String NUMBER = WCreditCardPanel.class.getName() + ".number";
	public static final String NUMBER_RW = WCreditCardPanel.class.getName() + ".number_rw";

	public static final String TYPES = WCreditCardPanel.class.getName() + ".types";
	public static final String TYPES_SHOW = WCreditCardPanel.class.getName() + ".types_show";
	public static final String NAME = WCreditCardPanel.class.getName() + ".name";
	public static final String NAME_SHOW = WCreditCardPanel.class.getName() + ".name_show";
	public static final String EXP = WCreditCardPanel.class.getName() + ".exp";
	public static final String APPROVAL = WCreditCardPanel.class.getName() + ".approval";
	public static final String STATUS = WCreditCardPanel.class.getName() + ".status";
	public static final String TYPE_RW = WCreditCardPanel.class.getName() + ".type_rw";
	public static final String TYPE_SELECTED = WCreditCardPanel.class.getName() + ".type_selected";

	public static final String NAME_RW = WCreditCardPanel.class.getName() + ".name_rw";
	public static final String EXP_RW = WCreditCardPanel.class.getName() + ".exp_rw";
	public static final String APPROVAL_RW = WCreditCardPanel.class.getName() + ".approval_rw";
	public static final String STATUS_RW = WCreditCardPanel.class.getName() + ".status_rw";
	public static final String ONLINE_RW = WCreditCardPanel.class.getName() + ".online_rw";
	public static final String APPROVAL_ERROR = WCreditCardPanel.class.getName() + ".approval_error";
	
	private Panel kPanel = new Panel();
	private Grid kLayout = GridFactory.newGridLayout();
	
	private Label kTypeLabel = new Label();
	public Listbox kTypeCombo = ListboxFactory.newDropdownListbox();
	
	private Label kNumberLabel = new Label();
	public Textbox kNumberField = new Textbox();
	
	private Label kNameLabel = new Label(); // metas
	public Textbox kNameField = new Textbox(); // metas
	
	private Label kExpLabel = new Label();
	public Textbox kExpField = new Textbox();
	
	private Label kApprovalLabel = new Label();
	public Textbox kApprovalField = new Textbox();
	
	private Label kAmountLabel = new Label();
	private WNumberEditor kAmountField = new WNumberEditor();
	
	private Button kOnline = new Button();
	
	private Label kStatus = new Label();
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public WCreditCardPanel()
	{
		initUI();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		final String propName = evt.getPropertyName();
		final Object newValue = evt.getNewValue();
		setProperty(propName, newValue);

		// Forward event:
		PropertyChangeEvent evt2 = new PropertyChangeEvent(this, propName, null, newValue);
		evt2.setPropagationId(evt.getPropagationId());
		pcs.firePropertyChange(evt2);

	}

	@Override
	public Object getComponent()
	{
		return kPanel;
	}
	
	@Override
	public String getExp()
	{
		return kExpField.getText();
	}
	
	@Override
	public String getName()
	{
		return kNameField.getText();
	}
	
	@Override
	public String getNumber()
	{
		return kNumberField.getText();
	}

	@Override
	public void onActivate()
	{
		if (!isCreditCardTypesLoaded)
			loadCCTypes();
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
		setProperty(WCreditCardPanel.TYPE_SELECTED, payment.getCreditCardType());
		setProperty(WCreditCardPanel.NUMBER, payment.getCreditCardNumber());
		setProperty(WCreditCardPanel.NAME, payment.getA_Name());
		setProperty(WCreditCardPanel.EXP, ccExp);
		setProperty(WCreditCardPanel.APPROVAL, payment.getCreditCardVV());
		setProperty(WCreditCardPanel.STATUS, payment.getR_RespMsg());
		setProperty(WCreditCardPanel.AMOUNT, payment.getPayAmt());

		// if approved/paid, don't let it change
		boolean isPaid = payment != null && payment.isProcessed();
		boolean isOnlineApproved = payment != null && payment.isOnlineApproved();
		setProperty(WCreditCardPanel.TYPE_RW, !isOnlineApproved);
		setProperty(WCreditCardPanel.NUMBER_RW, !isOnlineApproved);
		setProperty(WCreditCardPanel.NAME_RW, !isOnlineApproved);
		setProperty(WCreditCardPanel.EXP_RW, !isOnlineApproved);
		setProperty(WCreditCardPanel.APPROVAL_RW, !isOnlineApproved);
		setProperty(WCreditCardPanel.AMOUNT_RW, !isPaid && !isOnlineApproved);
		setProperty(WCreditCardPanel.ONLINE_RW, !isOnlineApproved);
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
				if (!Services.get(IClientUI.class).ask(0, "Error", error))
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

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public BigDecimal getAmount()
	{
		return (BigDecimal)kAmountField.getValue();
	}
	
	public String getCardCode()
	{
		return kApprovalField.getText();
	}

	@Override
	public String getCCType()
	{
		final ListItem item = kTypeCombo.getSelectedItem();
		final ValueNamePair vp = (ValueNamePair)item.getValue();
		return vp != null ? vp.getValue() : null;
	}
	
	
	@Override
	public void setAmount(BigDecimal amount)
	{
		if (!kAmountField.isReadWrite())
		{
			// don't change the amount if is already readonly
			return;
		}
		kAmountField.setValue(amount);
	}
	
	private void initUI()
	{
		kPanel.appendChild(kLayout);
		kNumberField.setWidth("160pt");
		kExpField.setWidth("40pt");
		kApprovalField.setWidth("120pt");
		kTypeLabel.setText(Msg.translate(Env.getCtx(), "CreditCardType"));
		kNumberLabel.setText(Msg.translate(Env.getCtx(), "CreditCardNumber"));
		kNameLabel.setText(Msg.translate(Env.getCtx(), "Name")); // metas
		kExpLabel.setText(Msg.getMsg(Env.getCtx(), "Expires"));
		kApprovalLabel.setText(Msg.translate(Env.getCtx(), "VoiceAuthCode"));
		kAmountLabel.setText(Msg.getMsg(Env.getCtx(), "Amount"));
		kOnline.setLabel(Msg.getMsg(Env.getCtx(), "Online"));
		LayoutUtils.addSclass("action-text-button", kOnline);
		kOnline.setVisible(true);

		kStatus.setText(" ");
		kPanel.setId("kPanel");

		Rows rows = kLayout.newRows();
		Row row = rows.newRow();
		row.appendChild(kTypeLabel.rightAlign());
		row.appendChild(kTypeCombo);
		row.appendChild(new Space());
		row.appendChild(new Space());

		row = rows.newRow();
		row.appendChild(kNumberLabel.rightAlign());
		row.appendChild(kNumberField);
		row.appendChild(new Space());
		row.appendChild(new Space());

		// metas: begin
		row = rows.newRow();
		row.appendChild(kNameLabel.rightAlign());
		row.appendChild(kNameField);
		row.appendChild(new Space());
		row.appendChild(new Space());
		// metas: end

		row = rows.newRow();
		row.appendChild(kExpLabel.rightAlign());
		row.appendChild(kExpField);
		row.appendChild(new Space());
		row.appendChild(new Space());

		row = rows.newRow();
		row.appendChild(kAmountLabel.rightAlign());
		row.appendChild(kAmountField.getComponent());

		row.appendChild(new Space());
		row.appendChild(new Space());

		row = rows.newRow();
		row.appendChild(kApprovalLabel.rightAlign());
		row.appendChild(kApprovalField);
		row.appendChild(new Space());
		row.appendChild(kOnline);

		row = rows.newRow();
		row.setSpans("3,1");
		row.appendChild(kStatus);
		row.appendChild(new Space());
		
		kOnline.addActionListener(new EventListener()
		{
			@Override
			public void onEvent(Event event) throws Exception
			{
				pcs.firePropertyChange(ACTION_Online, false, true);
				
			}
		});
	}
	
	private final boolean setProperty(final String propName, final Object newValue)
	{
		if (AMOUNT.equals(propName))
		{
			setAmount((BigDecimal)newValue);
		}
		else if (NUMBER.equals(propName))
		{
			kNumberField.setValue(newValue.toString());
		}
		else if (NUMBER_RW.equals(propName))
		{
			boolean rw = (Boolean)newValue;
			kNumberField.setEnabled(rw);
		}
		else if (NAME.equals(propName))
		{
			kNameField.setValue(newValue.toString());
		}
		else if (NAME_RW.equals(propName))
		{
			boolean rw = (Boolean)newValue;
			kNameField.setEnabled(rw);
		}
		else if (NAME_SHOW.equals(propName))
		{
			final Boolean show = (Boolean)newValue;
			kNameLabel.setVisible(show);
			kNameField.setVisible(show);
		}
		else if (EXP.equals(propName))
		{
			kExpField.setValue(newValue.toString());
		}
		else if (EXP_RW.equals(propName))
		{
			boolean rw = (Boolean)newValue;
			kExpField.setEnabled(rw);
		}
		else if (APPROVAL.equals(propName))
		{
			kApprovalField.setValue(newValue.toString());
		}
		else if (APPROVAL_RW.equals(propName))
		{
			boolean rw = (Boolean)newValue;
			kApprovalField.setEnabled(rw);
		}
		else if (APPROVAL_ERROR.equals(propName))
		{
//			kApprovalField.setStyle("background-color:"+AdempierePLAF.getFieldBackground_Error());
		}
		else if (STATUS.equals(propName))
		{
			kStatus.setText((String)newValue);
		}
		else if (STATUS_RW.equals(propName))
		{
//			boolean rw = (Boolean)newValue;
//			uiHelper.setFieldReadOnly(kStatus, !rw);
		}
		else if (TYPE_RW.equals(propName))
		{
			boolean rw = (Boolean)newValue;
			kTypeCombo.setEnabled(rw);
		}
		else if (AMOUNT_RW.equals(propName))
		{
			boolean rw = newValue instanceof Boolean ? ((Boolean)newValue).booleanValue() : false;
			kAmountField.setReadWrite(rw);
		}
		else if (ONLINE_RW.equals(propName))
		{
			boolean rw = newValue instanceof Boolean ? ((Boolean)newValue).booleanValue() : false;
			kOnline.setEnabled(rw);
		}
		else if (EXP_ERROR.equals(propName) && Boolean.TRUE.equals(newValue))
		{
//			kExpField.setStyle("background-color:"+AdempierePLAF.getFieldBackground_Error());
		}
		else if (NUMBER_ERROR.equals(propName) && Boolean.TRUE.equals(newValue))
		{
//			kNumberField.setStyle("background-color:"+AdempierePLAF.getFieldBackground_Error());
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
		if (Check.isEmpty(newValue))
		{
			return;
		}
		for (int i = 0; i < kTypeCombo.getItemCount(); i++)
		{
			final ValueNamePair currentItem = (ValueNamePair)kTypeCombo.getItemAtIndex(i).getValue();
			if (currentItem.getValue().equals(newValue))
			{
				kTypeCombo.setSelectedIndex(i);
				return;
			}
		}
		kTypeCombo.setSelectedItem(null);
	}
	
	private void loadCCTypes(MPayment payment)
	{
		setProperty(WCreditCardPanel.TYPES, retrieveCreditCardTypes(payment));
	}

	private void loadCCTypes()
	{
		setProperty(WCreditCardPanel.TYPES, retrieveCreditCardTypes());
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
			setProperty(WCreditCardPanel.AMOUNT, doc.getGrandTotal());
		else
			setProperty(WCreditCardPanel.AMOUNT, Env.ONE);
		loadCCTypes();
		setProperty(WCreditCardPanel.TYPE_SELECTED, org.compiere.model.X_C_Payment.CREDITCARDTYPE_Visa);
		setProperty(WCreditCardPanel.NUMBER, "4111 1111 1111 1111");
		setProperty(WCreditCardPanel.NAME, "Generated test name " + System.currentTimeMillis());
		setProperty(WCreditCardPanel.EXP, "1212");
		setProperty(WCreditCardPanel.APPROVAL, "123");
		setProperty(WCreditCardPanel.STATUS, null);
	}
	
	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		kTypeCombo.setEnabled(enabled);
		kNumberField.setEnabled(enabled);
		kNameField.setEnabled(enabled);
		kExpField.setEnabled(enabled);
		kApprovalField.setEnabled(enabled);
		kAmountField.setReadWrite(enabled);
		kOnline.setEnabled(enabled);
	}

	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		kTypeCombo.setEnabled(!readOnly);
		kNumberField.setEnabled(!readOnly);
		kNameField.setEnabled(!readOnly);
		kExpField.setEnabled(!readOnly);
		kApprovalField.setEnabled(!readOnly);
		kAmountField.setReadWrite(!readOnly);
		kOnline.setEnabled(!readOnly);
	}
}
