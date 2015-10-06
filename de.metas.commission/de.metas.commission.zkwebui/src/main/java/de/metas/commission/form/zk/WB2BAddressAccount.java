package de.metas.commission.form.zk;

/*
 * #%L
 * de.metas.commission.zkwebui
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


import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.window.WAutoCompleterCity;
import org.compiere.model.MBPBankAccount;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCountry;
import org.compiere.model.MLocation;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MUser;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zul.Div;
import org.zkoss.zul.Rows;

import de.metas.commission.interfaces.I_C_Order;

/**
 * @author Cristina Ghita, METAS.RO
 * 
 */
public class WB2BAddressAccount extends ADForm implements EventListener, ValueChangeListener
{
	private static final long serialVersionUID = -4375961112701451056L;

	private final Window panel = new Window();

	private final Div panelAddress = new Div();

	private final Div panelAccount = new Div();

	private static final String LABEL_STYLE = "white-space: nowrap;";

	private Label lblAddress1;

	private Label lblAddress2;

	private Label lblAddress3;

	private Label lblAddress4;

	private Label lblCity;

	private Label lblZip;

	private Label lblRegion;

	private Label lblPostal;

	private Label lblPostalAdd;

	private Label lblCountry;

	private Label lblName;

	private Textbox txtAddress1;

	private Textbox txtName;

	private Textbox txtAddress2;

	private Textbox txtAddress3;

	private WAutoCompleterCity txtCity;

	private Textbox txtPostal;

	private Listbox lstCountry;

	private Button btnSaveAddress;

	private Button btnDeleteAddress;

	private Grid addressGrid;

	private Grid bankAccountGrid;

	private Button btnNewAddress;

	private WTableDirEditor locationEditor;

	private MLocation location;

	private MBPartnerLocation bpLocation;

	private MBPBankAccount bpBankAccount;

	private final int windowNo;

	private final MUser user;

	private MLookup mLocationLookup;
	
	private Row pnlLocation;

	private MLookup lookupBankAccount; 
	private Row pnlBankAccount;
	
	private Label lblBillCheckbox;

	private Checkbox billCheckbox;

	private Label lblShipCheckbox;

	private Checkbox shipCheckbox;

//	private Label lblCommissionCheckbox;

//	private Checkbox commissionCheckbox;

	private Label lblBank;

	private Label lblBPBankAcctUse;

	private Label lblBankAccount;

	private Label lblAccountNo;

//	private Label lblBankAccountType;

	private WSearchEditor bankEditor;

	private Textbox txtAccountNo;

//	private WTableDirEditor bankAccountTypeEditor;

	private WTableDirEditor bankAccountEditor;

	private Button btnSaveAccount;

	private Button btnDeleteAccount;

	private Button btnNewAccount;

	private WTableDirEditor bpBankAcctUseEditor;

	public WB2BAddressAccount()
	{
		super();
		windowNo = SessionManager.getAppDesktop().registerWindow(this);
		Properties ctx = Env.getCtx();
		user = MUser.get(ctx, Env.getAD_User_ID(ctx));
	}

	public void init() throws Exception
	{
		this.appendChild(panel);
		this.setHeight("100%");
		Borderlayout layout = new Borderlayout();
		layout.setWidth("100%");
		layout.setHeight("100%");
		panel.appendChild(layout);
		panel.setWidth("40%");
		panel.setHeight("100%");

		North north = new North();
		layout.appendChild(north);
		north.appendChild(panelAddress);
		panelAddress.setWidth("100%");
		north.setBorder("none");
		north.setStyle("overflow:auto;");

		Center center = new Center();
		layout.appendChild(center);
		center.setBorder("none");
		center.appendChild(panelAccount);
		center.setStyle("overflow:auto;");
		
		initComponents();
		initAddress();
		initBankAccount();
		
		setLocation();
		setBankAccount();
	}

	private void initAddress() throws Exception
	{
		Rows rows = new Rows();

		pnlLocation = new Row();
		Label label = new Label(Msg.translate(Env.getCtx(),
				I_C_Order.COLUMNNAME_C_BPartner_Location_ID));
		pnlLocation.appendChild(label.rightAlign());

		mLocationLookup = MLookupFactory.get(Env.getCtx(),
				windowNo,
				0, // Column_ID,
				DisplayType.TableDir, // AD_Reference_ID,
				Env.getLanguage(Env.getCtx()), // language,
				"C_BPartner_Location_ID", // ColumnName,
				0, // AD_Reference_Value_ID,
				false, // IsParent,
				"C_BPartner_Location.C_BPartner_ID=" + user.getC_BPartner_ID() // ValidationCode
		);
		locationEditor = new WTableDirEditor(mLocationLookup,
							label.getValue(),
							"", false, false, true);

		locationEditor.addValueChangeListener(this);
		locationEditor.getComponent().setCols(30);
		pnlLocation.appendChild(locationEditor.getComponent());
		rows.appendChild(pnlLocation);

		Row pnlAddress1 = new Row();
		pnlAddress1.appendChild(lblAddress1.rightAlign());
		pnlAddress1.appendChild(txtAddress1);
		txtAddress1.addEventListener(Events.ON_CHANGE, this);
		rows.appendChild(pnlAddress1);

		Row pnlAddress2 = new Row();
		pnlAddress2.appendChild(lblAddress2.rightAlign());
		pnlAddress2.appendChild(txtAddress2);
		txtAddress2.addEventListener(Events.ON_CHANGE, this);
		rows.appendChild(pnlAddress2);

		Row pnlAddress3 = new Row();
		pnlAddress3.appendChild(lblAddress3.rightAlign());
		pnlAddress3.appendChild(txtAddress3);
		txtAddress3.addEventListener(Events.ON_CHANGE, this);
		rows.appendChild(pnlAddress3);

		Row pnlCity = new Row();
		pnlCity.appendChild(lblCity.rightAlign());
		pnlCity.appendChild(txtCity);
		txtCity.addEventListener(Events.ON_CHANGE, this);
		rows.appendChild(pnlCity);

		Row pnlPostal = new Row();
		pnlPostal.appendChild(lblPostal.rightAlign());
		pnlPostal.appendChild(txtPostal);
		txtPostal.addEventListener(Events.ON_CHANGE, this);
		rows.appendChild(pnlPostal);

		Row pnlCountry = new Row();
		pnlCountry.appendChild(lblCountry.rightAlign());
		pnlCountry.appendChild(lstCountry);
		lstCountry.addEventListener(Events.ON_CHANGE, this);
		rows.appendChild(pnlCountry);

		Row pnlBill = new Row();
		pnlBill.appendChild(lblBillCheckbox.rightAlign());
		pnlBill.appendChild(billCheckbox);
		billCheckbox.addEventListener(Events.ON_CHECK, this);
		rows.appendChild(pnlBill);

		Row pnlShip = new Row();
		pnlShip.appendChild(lblShipCheckbox.rightAlign());
		pnlShip.appendChild(shipCheckbox);
		shipCheckbox.addEventListener(Events.ON_CHECK, this);
		rows.appendChild(pnlShip);

//		Row pnlCommission = new Row();
//		pnlCommission.appendChild(lblCommissionCheckbox.rightAlign());
//		pnlCommission.appendChild(commissionCheckbox);
//		commissionCheckbox.addEventListener(Events.ON_CHECK, this);
//		rows.appendChild(pnlCommission);

		Row pnlButtons = new Row();
		pnlButtons.appendChild(btnSaveAddress);
		pnlButtons.setStyle("text-align:right");
		Panel pnlButton = new Panel();
		pnlButton.appendChild(btnDeleteAddress);
		pnlButton.appendChild(btnNewAddress);
		pnlButton.setStyle("text-align:left");
		pnlButtons.appendChild(pnlButton);
		rows.appendChild(pnlButtons);

		for (MCountry country : MCountry.getCountries(Env.getCtx()))
		{
			lstCountry.appendItem(country.toString(), country);
		}

		addressGrid.appendChild(rows);
		addressGrid.setVflex(true);

		panelAddress.appendChild(addressGrid);

	}

	private void initComponents()
	{
		lblAddress1 = new Label(Msg.getElement(Env.getCtx(), "Address1"));
		lblAddress1.setStyle(LABEL_STYLE);
		lblAddress2 = new Label(Msg.getElement(Env.getCtx(), "Address2"));
		lblAddress2.setStyle(LABEL_STYLE);
		lblAddress3 = new Label(Msg.getElement(Env.getCtx(), "Address3"));
		lblAddress3.setStyle(LABEL_STYLE);
		lblAddress4 = new Label(Msg.getElement(Env.getCtx(), "Address4"));
		lblAddress4.setStyle(LABEL_STYLE);
		lblCity = new Label(Msg.translate(Env.getCtx(), "City"));
		lblCity.setStyle(LABEL_STYLE);
		lblZip = new Label(Msg.translate(Env.getCtx(), "Postal"));
		lblZip.setStyle(LABEL_STYLE);
		lblRegion = new Label(Msg.translate(Env.getCtx(), "Region"));
		lblRegion.setStyle(LABEL_STYLE);
		lblPostal = new Label(Msg.translate(Env.getCtx(), "Postal"));
		lblPostal.setStyle(LABEL_STYLE);
		lblPostalAdd = new Label(Msg.translate(Env.getCtx(), "PostalAdd"));
		lblPostalAdd.setStyle(LABEL_STYLE);
		lblCountry = new Label(Msg.translate(Env.getCtx(), "Country"));
		lblCountry.setStyle(LABEL_STYLE);
		lblBillCheckbox = new Label(Msg.translate(Env.getCtx(), "IsBillTo"));
		lblBillCheckbox.setStyle(LABEL_STYLE);
		lblShipCheckbox = new Label(Msg.translate(Env.getCtx(), "IsShipTo"));
		lblShipCheckbox.setStyle(LABEL_STYLE);
//		lblCommissionCheckbox = new Label(Msg.translate(Env.getCtx(), "IsCommissionTo"));
//		lblCommissionCheckbox.setStyle(LABEL_STYLE);

		txtAddress1 = new Textbox();
		txtAddress1.setCols(20);
		txtAddress2 = new Textbox();
		txtAddress2.setCols(20);
		txtAddress3 = new Textbox();
		txtAddress3.setCols(20);

		billCheckbox = new Checkbox();
		shipCheckbox = new Checkbox();
//		commissionCheckbox = new Checkbox();

		// autocomplete City
		txtCity = new WAutoCompleterCity(windowNo);
		txtCity.setCols(20);
		txtCity.setButtonVisible(false);
		txtCity.setAutocomplete(true);
		txtCity.addEventListener(Events.ON_CHANGING, this);
		// txtCity

		txtPostal = new Textbox();
		txtPostal.setCols(20);

		lstCountry = new Listbox();
		lstCountry.setMold("select");
		lstCountry.setWidth("154px");
		lstCountry.setRows(0);

		btnSaveAddress = new Button();
		btnSaveAddress.setImage("/images/Save16.png");
		btnSaveAddress.addEventListener(Events.ON_CLICK, this);
		btnSaveAddress.setEnabled(false);
		btnDeleteAddress = new Button();
		btnDeleteAddress.setImage("/images/Delete16.png");
		btnDeleteAddress.addEventListener(Events.ON_CLICK, this);
		btnNewAddress = new Button();
		btnNewAddress.setImage("/images/New16.png");
		btnNewAddress.addEventListener(Events.ON_CLICK, this);

		addressGrid = new Grid();
		addressGrid.setOddRowSclass(null);

		// bank account

		btnSaveAccount = new Button();
		btnSaveAccount.setImage("/images/Save16.png");
		btnSaveAccount.addEventListener(Events.ON_CLICK, this);
		btnSaveAccount.setEnabled(false);
		btnDeleteAccount = new Button();
		btnDeleteAccount.setImage("/images/Delete16.png");
		btnDeleteAccount.addEventListener(Events.ON_CLICK, this);
		btnNewAccount = new Button();
		btnNewAccount.setImage("/images/New16.png");
		btnNewAccount.addEventListener(Events.ON_CLICK, this);

		lblBankAccount = new Label(Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"));
		lblBankAccount.setStyle(LABEL_STYLE);
		lblBank = new Label(Msg.translate(Env.getCtx(), "C_Bank_ID"));
		lblBank.setStyle(LABEL_STYLE);
		lblAccountNo = new Label(Msg.translate(Env.getCtx(), "AccountNo"));
		lblAccountNo.setStyle(LABEL_STYLE);
//		lblBankAccountType = new Label(Msg.translate(Env.getCtx(), "BankAccountType"));
//		lblBankAccountType.setStyle(LABEL_STYLE);
		lblName = new Label(Msg.translate(Env.getCtx(), "Name"));
		lblName.setStyle(LABEL_STYLE);
		lblBPBankAcctUse = new Label(Msg.translate(Env.getCtx(), "BPBankAcctUse"));
		lblBPBankAcctUse.setStyle(LABEL_STYLE);

		txtName = new Textbox();
		txtName.setCols(20);
		txtAccountNo = new Textbox();
		txtAccountNo.setCols(20);
		txtAccountNo.setId("AccountNo_Acct");
		bankAccountGrid = new Grid();
		bankAccountGrid.setOddRowSclass(null);
	}

	private void initBankAccount() throws Exception
	{
		Rows rows = new Rows();

		pnlBankAccount = new Row();
		pnlBankAccount.appendChild(lblBankAccount.rightAlign());
		lookupBankAccount = MLookupFactory.get(Env.getCtx(),
				windowNo,
				0, // Column_ID,
				DisplayType.TableDir, // AD_Reference_ID,
				Env.getLanguage(Env.getCtx()), // language,
				"C_BP_BankAccount_ID", // ColumnName,
				0, // AD_Reference_Value_ID,
				false, // IsParent,
				"C_BP_BankAccount.C_BPartner_ID=" + user.getC_BPartner_ID() // ValidationCode
		);

		bankAccountEditor = new WTableDirEditor(lookupBankAccount,
							lblBankAccount.getValue(),
							"", false, false, true);
		bankAccountEditor.addValueChangeListener(this);
		bankAccountEditor.getComponent().setId("bankAccount_Acct");
		bankAccountEditor.getComponent().setCols(30);
		pnlBankAccount.appendChild(bankAccountEditor.getComponent());
		rows.appendChild(pnlBankAccount);

		Row pnlName = new Row();
		pnlName.appendChild(lblName.rightAlign());
		pnlName.appendChild(txtName);
		txtName.addEventListener(Events.ON_CHANGE, this);
		txtName.setId("Name_Acct");
		rows.appendChild(pnlName);

		Row pnlBank = new Row();
		pnlBank.appendChild(lblBank.rightAlign());

		MLookup lookupBank = MLookupFactory.get(Env.getCtx(), windowNo,
                0, 3031, DisplayType.Search); 
			
		bankEditor = new WSearchEditor(lookupBank,
							lblBank.getValue(),
							"", false, false, true);
		bankEditor.getComponent().setId("Bank_Acct");
		bankEditor.addValueChangeListener(this);
		pnlBank.appendChild(bankEditor.getComponent());
		rows.appendChild(pnlBank);

		Row pnlAccountNo = new Row();
		pnlAccountNo.appendChild(lblAccountNo.rightAlign());
		pnlAccountNo.appendChild(txtAccountNo);
		txtAccountNo.addEventListener(Events.ON_CHANGE, this);
		rows.appendChild(pnlAccountNo);

//		Row pnlBankAccountType = new Row();
//		pnlBankAccountType.appendChild(lblBankAccountType.rightAlign());
//		MLookup lookupBankAccountType = MLookupFactory.get(Env.getCtx(),
//				windowNo,
//				0, // Column_ID,
//				DisplayType.List, // AD_Reference_ID,
//				Env.getLanguage(Env.getCtx()), // language,
//				"BankAccountType", // ColumnName,
//				216, // AD_Reference_Value_ID,
//				false, // IsParent,
//				"" // ValidationCode
//		);
//
//		bankAccountTypeEditor = new WTableDirEditor(lookupBankAccountType,
//							lblBank.getValue(),
//							"", false, false, true);
//		bankAccountTypeEditor.getComponent().setId("bankAccountTypeEditor_Acct");
//		bankAccountTypeEditor.addValueChangeListener(this);
//		pnlBankAccountType.appendChild(bankAccountTypeEditor.getComponent());
//		rows.appendChild(pnlBankAccountType);

		Row pnlBPBankAcctUse = new Row();
		pnlBPBankAcctUse.appendChild(lblBPBankAcctUse.rightAlign());
		MLookup lookupBPBankAcctUse = MLookupFactory.get(Env.getCtx(),
				windowNo,
				0, // Column_ID,
				DisplayType.List, // AD_Reference_ID,
				Env.getLanguage(Env.getCtx()), // language,
				"BPBankAcctUse", // ColumnName,
				393, // AD_Reference_Value_ID,
				false, // IsParent,
				"" // ValidationCode
		);

		bpBankAcctUseEditor = new WTableDirEditor(lookupBPBankAcctUse,
							lblBank.getValue(),
							"", false, false, true);
		bpBankAcctUseEditor.getComponent().setId("bpBankAcctUseEditor_Acct");
		bpBankAcctUseEditor.addValueChangeListener(this);
		pnlBPBankAcctUse.appendChild(bpBankAcctUseEditor.getComponent());
		rows.appendChild(pnlBPBankAcctUse);

		Row pnlButtons = new Row();
		pnlButtons.appendChild(btnSaveAccount);
		btnSaveAccount.setId("btnSaveAccount");
		pnlButtons.setStyle("text-align:right");
		Panel pnlButton = new Panel();
		pnlButton.appendChild(btnDeleteAccount);
		btnDeleteAccount.setId("btnDeleteAccount");
		pnlButton.appendChild(btnNewAccount);
		btnNewAccount.setId("btnNewAccount");
		pnlButton.setStyle("text-align:left");
		pnlButtons.appendChild(pnlButton);
		rows.appendChild(pnlButtons);

		bankAccountGrid.appendChild(rows);
		bankAccountGrid.setHeight("85%");
		bankAccountGrid.setStyle("overflow:auto");
		bankAccountGrid.setVflex(true);

		panelAccount.appendChild(bankAccountGrid);
	}

	private void setAddress()
	{
		if (locationEditor == null)
			return;
		bpLocation = new MBPartnerLocation(Env.getCtx(), (Integer)locationEditor.getValue(), null);
		location = (MLocation)bpLocation.getC_Location();
		txtAddress1.setText(location.getAddress1());
		txtAddress2.setText(location.getAddress2());
		txtAddress3.setText(location.getAddress3());
		txtCity.setText(location.getCity());
		txtPostal.setText(location.getPostal());
		setCountry();
		billCheckbox.setChecked(bpLocation.isBillTo());
		shipCheckbox.setChecked(bpLocation.isShipTo());
//		commissionCheckbox.setChecked(bpLocation.get_ValueAsBoolean("IsCommissionTo"));
	}

	private void setLocation()
	{
			List<Object> list = mLocationLookup.getData(true, true, true, false);
			Boolean defaultBill = false;
			for (Object bpl : list)
			{
				int id = ((KeyNamePair)bpl).getKey();
				MBPartnerLocation loc = new MBPartnerLocation(Env.getCtx(), id, null);
				defaultBill = (Boolean)loc.get_Value("IsBillToDefault");
				if (defaultBill)
				{
					locationEditor.setValue(id);
					setAddress();
					break;
				}
			}
			if (!defaultBill)
			{
				for (Object bpl : list)
				{
					int id = ((KeyNamePair)bpl).getKey();
					locationEditor.setValue(id);
					setAddress();
					break;
				}
			}
	}
	
	private void setBankAccount()
	{
		List<Object> list = lookupBankAccount.getData(true, true, true, false);
		for (Object bacc : list)
		{
			int id = ((KeyNamePair)bacc).getKey();
			bankAccountEditor.setValue(id);
			setAccount();
			break;
		}
	}
	
	private void setAccount()
	{
		if (bankAccountEditor == null)
			return;

		bpBankAccount = new MBPBankAccount(Env.getCtx(), (Integer)bankAccountEditor.getValue(), null);
		txtName.setText(bpBankAccount.getA_Name());
		bpBankAcctUseEditor.setValue(bpBankAccount.getBPBankAcctUse());
		if (bpBankAccount.getC_Bank_ID() > 0)
			bankEditor.setValue(bpBankAccount.getC_Bank_ID());
//		bankAccountTypeEditor.setValue(bpBankAccount.getBankAccountType());
		txtAccountNo.setText(bpBankAccount.getAccountNo());
	}

	private void setCountry()
	{
		List<?> listCountry = lstCountry.getChildren();
		Iterator<?> iter = listCountry.iterator();
		while (iter.hasNext())
		{
			ListItem listitem = (ListItem)iter.next();
			if (location.getCountry().equals(listitem.getValue()))
			{
				lstCountry.setSelectedItem(listitem);
			}
		}
	}

	@Override
	public void onEvent(Event event) throws Exception
	{
		if (event.getName().equals(Events.ON_CHECK) || event.getName().equals(Events.ON_CHANGE))
		{
			if (event.getTarget().getId().endsWith("_Acct"))
			{
				btnNewAccount.setDisabled(true);
				btnSaveAccount.setDisabled(false);
				btnDeleteAccount.setDisabled(true);
			}
			else
			{
				btnNewAddress.setDisabled(true);
				btnSaveAddress.setDisabled(false);
				btnDeleteAddress.setDisabled(true);
			}

		}

		if (btnSaveAddress.equals(event.getTarget()))
		{
			location.setAddress1(txtAddress1.getValue());
			location.setAddress2(txtAddress2.getValue());
			location.setAddress3(txtAddress3.getValue());
			location.setC_City_ID(txtCity.getC_City_ID());
			location.setCity(txtCity.getValue());
			location.setPostal(txtPostal.getValue());
			// Country
			MCountry country = (MCountry)lstCountry.getSelectedItem().getValue();
			location.setCountry(country);
			// Save changes
			location.saveEx();
			if (bpLocation.getC_Location_ID() != location.getC_Location_ID())
			{
				bpLocation = new MBPartnerLocation((MBPartner)user.getC_BPartner());
				bpLocation.setC_Location_ID(location.getC_Location_ID());
			}

			bpLocation.setName(location.getAddress1() + "_" + location.getCity()
								+ "_" + location.getC_Country().getCountryCode());
			bpLocation.setIsBillTo(billCheckbox.isChecked());
			bpLocation.setIsShipTo(shipCheckbox.isChecked());
//			bpLocation.set_ValueOfColumn("IsCommissionTo", commissionCheckbox.isChecked());
			bpLocation.saveEx();
			locationEditor.actionRefresh();
			locationEditor.setValue(bpLocation.getC_BPartner_Location_ID());
			pnlLocation.setVisible(true);

			//
			btnNewAddress.setDisabled(false);
			btnSaveAddress.setDisabled(true);
			btnDeleteAddress.setDisabled(false);
		}

		if (btnNewAddress.equals(event.getTarget()))
		{
			location = new MLocation(Env.getCtx(), 0, null);
			pnlLocation.setVisible(false);
			resetAddress();
			//
			setAddressRO(false);
			//
			btnNewAddress.setDisabled(true);
			btnSaveAddress.setDisabled(false);
			btnDeleteAddress.setDisabled(true);
		}

		if (btnDeleteAddress.equals(event.getTarget()))
		{
			if (location != null && location.getC_Location_ID() != 0)
			{
				bpLocation.deleteEx(false);
				location.deleteEx(false);
				resetAddress();
				locationEditor.actionRefresh();
			}
			//
			setAddressRO(true);
			//
			btnNewAddress.setDisabled(false);
			btnSaveAddress.setDisabled(true);
			btnDeleteAddress.setDisabled(true);
			//
		}

		//

		if (btnSaveAccount.equals(event.getTarget()))
		{
			bpBankAccount.setA_Name(txtName.getValue());
			bpBankAccount.setAccountNo(txtAccountNo.getValue());
//			bpBankAccount.setBankAccountType((String)bankAccountTypeEditor.getValue());
			bpBankAccount.setBPBankAcctUse((String)bpBankAcctUseEditor.getValue());
			Integer bank_id = (Integer)bankEditor.getValue();
			if (bank_id != null)
				bpBankAccount.setC_Bank_ID(bank_id);
			bpBankAccount.setC_BPartner_ID(user.getC_BPartner_ID());
			bpBankAccount.saveEx();
			pnlBankAccount.setVisible(true);
			//
			btnNewAccount.setDisabled(false);
			btnSaveAccount.setDisabled(true);
			btnDeleteAccount.setDisabled(false);

			bankAccountEditor.actionRefresh();
			bankAccountEditor.setValue(bpBankAccount.getC_BP_BankAccount_ID());
		}

		if (btnNewAccount.equals(event.getTarget()))
		{
			bpBankAccount = new MBPBankAccount(Env.getCtx(), 0, null);
			pnlBankAccount.setVisible(false);
			resetAccount();
			//
			setAccountRO(false);
			//
			btnNewAccount.setDisabled(true);
			btnSaveAccount.setDisabled(false);
			btnDeleteAccount.setDisabled(true);
		}

		if (btnDeleteAccount.equals(event.getTarget()))
		{
			if (bpBankAccount != null && bpBankAccount.getC_BP_BankAccount_ID() != 0)
			{
				bpBankAccount.deleteEx(false);
				resetAccount();
				bankAccountEditor.actionRefresh();
			}
			//
			setAccountRO(true);
			//
			btnNewAccount.setDisabled(false);
			btnSaveAccount.setDisabled(true);
			btnDeleteAccount.setDisabled(false);
		}

	}

	private void resetAddress()
	{
		txtAddress1.setText(null);
		txtAddress2.setText(null);
		txtAddress3.setText(null);
		txtCity.setText(null);
		txtPostal.setText(null);
		setCountry();
		billCheckbox.setChecked(false);
		shipCheckbox.setChecked(false);
		//
		btnNewAddress.setDisabled(true);
		btnSaveAddress.setDisabled(false);
		btnDeleteAddress.setDisabled(true);
		//
		locationEditor.setValue(null);
	}
	
	private void setAddressRO(boolean readonly)
	{
		txtAddress1.setReadonly(readonly);
		txtAddress2.setReadonly(readonly);
		txtAddress3.setReadonly(readonly);
		txtCity.setReadonly(readonly);
		txtPostal.setReadonly(readonly);
		lstCountry.setDisabled(readonly);
		billCheckbox.setDisabled(readonly);
		shipCheckbox.setDisabled(readonly);
	}
	
	
	private void resetAccount()
	{
		txtName.setText(bpBankAccount.getA_Name());
		txtAccountNo.setText(bpBankAccount.getAccountNo());
//		bankAccountTypeEditor.setValue(bpBankAccount.getBankAccountType());
		bpBankAcctUseEditor.setValue(bpBankAccount.getBPBankAcctUse());
		bankEditor.setValue(bpBankAccount.getC_Bank_ID());
		//
		btnNewAccount.setDisabled(true);
		btnSaveAccount.setDisabled(false);
		btnDeleteAccount.setDisabled(true);
		//
		bankAccountEditor.setValue(null);
	}

	private void setAccountRO(boolean readonly)
	{
		txtName.setDisabled(readonly);
		txtAccountNo.setDisabled(readonly);
		bpBankAcctUseEditor.setReadWrite(!readonly);
		bankEditor.setReadWrite(!readonly);
	}
	
	@Override
	public void valueChange(ValueChangeEvent evt)
	{
		if (evt.getSource().equals(locationEditor))
		{
			setAddress();
			//
			btnNewAddress.setDisabled(false);
			btnSaveAddress.setDisabled(true);
			btnDeleteAddress.setDisabled(false);
			setAddressRO(false);
		}

		if (evt.getSource().equals(bankAccountEditor))
		{
			setAccount();
			//
			btnNewAccount.setDisabled(false);
			btnSaveAccount.setDisabled(true);
			btnDeleteAccount.setDisabled(false);
			//
			setAccountRO(false);
		}

		if (evt.getSource().equals(bankEditor) 
			// || evt.getSource().equals(bankAccountTypeEditor)
				|| evt.getSource().equals(bpBankAcctUseEditor))
		{
			btnNewAccount.setDisabled(true);
			btnSaveAccount.setDisabled(false);
			btnDeleteAccount.setDisabled(true);
		}
	}

	@Override
	protected void initForm()
	{
		try
		{
			init();
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
}
