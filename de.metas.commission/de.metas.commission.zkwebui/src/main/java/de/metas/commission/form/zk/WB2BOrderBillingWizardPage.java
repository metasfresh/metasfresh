/**
 * 
 */
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


import java.text.Format;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GridTabWrapper;
import org.adempiere.model.POWrapper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.webui.apps.form.WPaymentPanel;
import org.adempiere.webui.component.Column;
import org.adempiere.webui.component.Columns;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WButtonEditor;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.panel.ADTabpanel;
import org.compiere.model.GridTab;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.X_C_Order;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.util.TrxRunnable;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Div;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;

import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.webui.component.PropertyInfoPanel;
import de.metas.adempiere.webui.component.WizardPage;
import de.metas.adempiere.webui.component.WizardPanel;
import de.metas.adempiere.webui.util.ZkUtil;
import de.metas.commission.interfaces.I_C_Order;
import de.metas.commission.interfaces.I_C_OrderLine;

/**
 * @author tsa
 * @author Cristina Ghita, METAS.RO
 */
public class WB2BOrderBillingWizardPage implements WizardPage
{
	private static final CLogger log = CLogger.getCLogger(WB2BOrderBillingWizardPage.class);

	private final Div panel = new Div();
	private final Div panelBilling = new Div();
	private final Div panelPayment = new Div();
	private final Div panelOverview = new Div();

	private WizardPanel wizardPanel = null;

	private GridTab orderGridTab;

	private ADTabpanel tabpanelOrder;

	PropertyInfoPanel orderInfo = new PropertyInfoPanel();
	PropertyInfoPanel orderLinesInfo = new PropertyInfoPanel();

	private boolean isInitialized;

	private boolean processed = false;

	private WPaymentPanel payment;

	private WEditor partnerLocationEditor;
	private WEditor partnerContactEditor;
	private WEditor billPartnerEditor;
	private WEditor billPartnerContactEditor;
	private WEditor billPartnerLocationEditor;

	
	public static final int COLUMN_ID_Bill_Location_ID = 8766;
	public static final int COLUMN_ID_Bill_User_ID = 8763;
	public static final int COLUMN_ID_C_BPartner_Location_ID = 3400;
	public static final int COLUMN_ID_AD_User_ID = 2763;
	
	private static class RefreshComboboxOnOpenListener implements EventListener
	{
		private final WTableDirEditor editor;

		public static void addEventListener(WEditor editor)
		{
			if (editor instanceof WTableDirEditor)
			{
				editor.getComponent().addEventListener(Events.ON_OPEN, new RefreshComboboxOnOpenListener((WTableDirEditor)editor));
			}
			else
			{
				log.warning("Editor " + editor + " is not a WTableDirEditor. Skip adding listener");
			}
		}

		private RefreshComboboxOnOpenListener(WTableDirEditor editor)
		{
			this.editor = editor;
		}

		@Override
		public void onEvent(Event event) throws Exception
		{
			if (Events.ON_OPEN.equals(event.getName()) && ((OpenEvent)event).isOpen())
			{
				//editor.refresh(true);
				editor.actionRefresh();
			}
		}
	}

	public WB2BOrderBillingWizardPage(ADTabpanel tabpanelOrder)
	{

		this.tabpanelOrder = tabpanelOrder;
		this.orderGridTab = tabpanelOrder.getGridTab();
		// this.windowNo = orderGridTab.getWindowNo();
		init();
	}

	@Override
	public void init()
	{
		if (isInitialized)
			return;

		// Layout
		Borderlayout layout = new Borderlayout();
		layout.setWidth("99%");
		layout.setHeight("100%");
		panel.appendChild(layout);
		panel.setWidth("100%");
		panel.setHeight("100%");

		North north = new North();
		layout.appendChild(north);
		north.appendChild(panelOverview);
		north.setHeight("25%");

		West west = new West();
		layout.appendChild(west);
		west.appendChild(panelPayment);
		west.setWidth("500px");

		Center center = new Center();
		layout.appendChild(center);
		center.appendChild(panelBilling);

		initPaymentPanel();
		initBillingPanel();
		initOverviewPanel();

		isInitialized = true;
	}

	public void setOverviewInfo()
	{
		//
		Properties ctx = Env.getCtx();
		final Format mf = DisplayType.getNumberFormat(DisplayType.Amount, Env.getLanguage(ctx));
		//
		de.metas.commission.interfaces.I_C_Order order = GridTabWrapper.create(orderGridTab, de.metas.commission.interfaces.I_C_Order.class);
		IOrderBL orderBL = Services.get(IOrderBL.class);
		ZkUtil.setOrderOverview(orderInfo, orderBL, order);

		MOrder orderPO = new MOrder(ctx, orderGridTab.getRecord_ID(), null);
		//
		orderLinesInfo.resetValues();
		orderLinesInfo.resetProperties();
		for (MOrderLine ol : orderPO.getLines(true, null))
		{
			String property = Msg.translate(ctx, I_C_OrderLine.COLUMNNAME_Line) + " "
					+ ol.getLine() + ":  " + ol.getQtyOrdered() + " x "
					+ ol.getM_Product().getValue() + " " + ol.getM_Product().getName()
					+ (Check.isEmpty(ol.getDescription(), true) ? "" : ", " + ol.getDescription())
					+ " :";
			I_C_OrderLine olW = POWrapper.create(ol, I_C_OrderLine.class);

			String value = mf.format(ol.getLineNetAmt()) + " " + ol.getC_Currency().getCurSymbol()
					+ ", " + Msg.translate(ctx, I_C_OrderLine.COLUMNNAME_CommissionPointsSum)
					+ " " + mf.format(olW.getCommissionPointsSum())
					+ ", " + olW.getC_Tax().getName();
			orderLinesInfo.setValues(property, value);
		}
	}

	private void initOverviewPanel()
	{
		Borderlayout content = new Borderlayout();
		panelOverview.appendChild(content);
		panelOverview.setWidth("100%");
		panelOverview.setHeight("100%");

		content.setWidth("100%");
		content.setHeight("100%");
		//
		West west = new West();
		content.appendChild(west);
		west.setWidth("500px");
		west.appendChild(orderInfo);
		orderInfo.setVflex(true);
		//
		Center center = new Center();
		content.appendChild(center);
		center.appendChild(orderLinesInfo);
		orderLinesInfo.setSize("50%", "50%");
		orderLinesInfo.setVflex(true);
	}

	private void initBillingPanel()
	{
		Borderlayout content = new Borderlayout();
		panelBilling.appendChild(content);
		panelBilling.setWidth("100%");
		panelBilling.setHeight("100%");

		North north = new North();
		content.appendChild(north);
		north.setHeight("50%");
		Grid grid = new Grid();
		grid.makeNoStrip();
		north.appendChild(grid);

		Rows rows = new Rows();
		Columns columns = new Columns();
		grid.appendChild(columns);
		grid.appendChild(rows);

		Column partnerColumn = new Column();
		columns.appendChild(partnerColumn);

		Column partnerFieldsColumn = new Column();
		columns.appendChild(partnerFieldsColumn);

		final Properties ctx = Env.getCtx();
		final EditorsValueChanged evc = new EditorsValueChanged(this);
		//
		Row row = new Row();
		rows.appendChild(row);
		row.appendChild(new Label(Msg.translate(ctx, I_C_Order.COLUMNNAME_C_BPartner_ID)));
		WEditor partnerEditor = tabpanelOrder.getEditor(I_C_Order.COLUMNNAME_C_BPartner_ID);
		partnerEditor.setReadWrite(false);
		row.appendChild(partnerEditor.getComponent());

		//
		row = new Row();
		rows.appendChild(row);
		Label lblBPContact = new Label(Msg.translate(ctx, I_C_Order.COLUMNNAME_AD_User_ID));
		row.appendChild(lblBPContact);
		MLookup lookupAD_User = MLookupFactory.get(Env.getCtx(), orderGridTab.getWindowNo(), 0, COLUMN_ID_AD_User_ID, DisplayType.Table);
		partnerContactEditor = new WTableDirEditor(lookupAD_User, lblBPContact.getValue(), "", true, false, true);
		partnerContactEditor.setValue(orderGridTab.getValue(I_C_Order.COLUMNNAME_AD_User_ID));
		
		RefreshComboboxOnOpenListener.addEventListener(partnerContactEditor);
		partnerContactEditor.addValueChangeListener(evc);
		row.appendChild(partnerContactEditor.getComponent());

		//
		Group groupShipment = new Group();
		groupShipment.setLabel(Msg.translate(Env.getAD_Language(ctx), "IsShipTo"));
		rows.appendChild(groupShipment);
		//
		row = new Row();
		rows.appendChild(row);
		Label lblBPLoc = new Label(Msg.translate(ctx, I_C_Order.COLUMNNAME_C_BPartner_Location_ID));
		row.appendChild(lblBPLoc);
		MLookup lookupC_BPartner_Location = MLookupFactory.get(Env.getCtx(), orderGridTab.getWindowNo(), 0, COLUMN_ID_C_BPartner_Location_ID, DisplayType.Table);
		partnerLocationEditor = new WTableDirEditor(lookupC_BPartner_Location, lblBPLoc.getValue(), "", true, false, true);
		partnerLocationEditor.setValue(orderGridTab.getValue(I_C_Order.COLUMNNAME_C_BPartner_Location_ID));
		RefreshComboboxOnOpenListener.addEventListener(partnerLocationEditor);
		partnerLocationEditor.addValueChangeListener(evc);
		row.appendChild(partnerLocationEditor.getComponent());

		//
		row = new Row();
		rows.appendChild(row);
		row.appendChild(new Label());
		WEditor partnerAddressEditor = tabpanelOrder.getEditor("BPartnerAddress");
		partnerAddressEditor.setReadWrite(false);
		row.appendChild(partnerAddressEditor.getComponent());

		//
		grid = new Grid();
		grid.makeNoStrip();
		South south = new South();
		content.appendChild(south);
		south.appendChild(grid);
		south.setHeight("50%");
		rows = new Rows();
		columns = new Columns();
		grid.appendChild(columns);
		grid.appendChild(rows);

		Column billPartnerColumn = new Column();
		columns.appendChild(billPartnerColumn);

		Column billPartnerFieldsColumn = new Column();
		columns.appendChild(billPartnerFieldsColumn);

		row = new Row();
		rows.appendChild(row);
		row.appendChild(new Label(Msg.translate(ctx, I_C_Order.COLUMNNAME_Bill_BPartner_ID)));
		billPartnerEditor = tabpanelOrder.getEditor(I_C_Order.COLUMNNAME_Bill_BPartner_ID);
		billPartnerEditor.setReadWrite(false);
		row.appendChild(billPartnerEditor.getComponent());

		row = new Row();
		rows.appendChild(row);
		Label lblBillContact = new Label(Msg.translate(ctx, I_C_Order.COLUMNNAME_Bill_User_ID));
		row.appendChild(lblBillContact);
		MLookup lookupBill_User = MLookupFactory.get(Env.getCtx(), orderGridTab.getWindowNo(), 0, COLUMN_ID_Bill_User_ID, DisplayType.Table);
		billPartnerContactEditor = new WTableDirEditor(lookupBill_User, lblBillContact.getValue(), "", true, false, true);
		billPartnerContactEditor.setValue(orderGridTab.getValue(I_C_Order.COLUMNNAME_Bill_User_ID));
		RefreshComboboxOnOpenListener.addEventListener(billPartnerContactEditor);
		billPartnerContactEditor.addValueChangeListener(evc);
		row.appendChild(billPartnerContactEditor.getComponent());

		Group groupBill = new Group();
		groupBill.setLabel(Msg.translate(Env.getAD_Language(ctx), "IsBillTo"));
		rows.appendChild(groupBill);

		row = new Row();
		rows.appendChild(row);
		Label lblBillLocation = new Label(Msg.translate(ctx, I_C_Order.COLUMNNAME_Bill_Location_ID));
		row.appendChild(lblBillLocation);
		MLookup lookupBill_Location = MLookupFactory.get(Env.getCtx(), orderGridTab.getWindowNo(), 0, COLUMN_ID_Bill_Location_ID, DisplayType.Table);
		billPartnerLocationEditor = new WTableDirEditor(lookupBill_Location, lblBillLocation.getValue(), "", true, false, true);
		billPartnerLocationEditor.setValue(orderGridTab.getValue(I_C_Order.COLUMNNAME_Bill_Location_ID));
		RefreshComboboxOnOpenListener.addEventListener(billPartnerLocationEditor);
		billPartnerLocationEditor.addValueChangeListener(evc);
		row.appendChild(billPartnerLocationEditor.getComponent());

		row = new Row();
		rows.appendChild(row);
		row.appendChild(new Label());
		WEditor billPartnerAddressEditor = tabpanelOrder.getEditor("BillToAddress");
		billPartnerAddressEditor.setReadWrite(false);
		row.appendChild(billPartnerAddressEditor.getComponent());

	}

	private void initPaymentPanel()
	{
		if (payment != null)
		{
			payment.detach();
			payment = null;
		}
		WButtonEditor btnPaymentRule = (WButtonEditor)tabpanelOrder.getEditor(I_C_Order.COLUMNNAME_PaymentRule);
		// WPayment paymentWindow = new WPayment(orderGridTab.getWindowNo(), orderGridTab, btnPaymentRule, true);
		payment = new WPaymentPanel(orderGridTab.getWindowNo(), orderGridTab, btnPaymentRule, true);
		panelPayment.appendChild(payment);
		payment.setWidth("500px");
		payment.setWidth("400px");
	}

	@Override
	public Component getComponent()
	{
		return panel;
	}

	@Override
	public boolean isAllowAction(String name)
	{
		if (processed)
			return false;
		if (WizardPanel.ACTION_Next.equals(name) || name.equals(WizardPanel.ACTION_New)
				|| WizardPanel.ACTION_Cancel.equals(name))
			return false;
		return true;
	}

	@Override
	public void onHide(String action)
	{
		orderGridTab.dataSave(true);
		//
		if (WizardPanel.ACTION_Finish.equals(action))
		{
			processOrder();
			WB2BOrderHistoryWizardPage hwp = (WB2BOrderHistoryWizardPage)getWizardPanel().getPage(2);
			hwp.setC_Order_ID(orderGridTab.getRecord_ID());
			getWizardPanel().actionNext();
		}
	}

	@Override
	public void onShow()
	{
		throw new UnsupportedOperationException(); // FIXME implement task 04464
		/*
		setOverviewInfo();
		initPaymentPanel();
		payment.setAmount((BigDecimal)orderGridTab.getValue("GrandTotal"));
		*/
	}

	@Override
	public void setWizardPanel(WizardPanel panel)
	{
		this.wizardPanel = panel;
	}

	public WizardPanel getWizardPanel()
	{
		return wizardPanel;
	}

	public void setGridTab(GridTab orderGridTab)
	{
		this.orderGridTab = orderGridTab;
	}

	@Override
	public boolean isInitialized()
	{
		return this.isInitialized;
	}

	private void processOrder()
	{
		if (processed)
			return;

		Trx.run(new TrxRunnable()
		{
			@Override
			public void run(String trxName)
			{
				processOrder0(trxName);
			}
		});
	}

	private void processOrder0(String trxName)
	{
		this.processed = false; // just in case

		int C_Order_ID = this.orderGridTab.getRecord_ID();
		if (C_Order_ID <= 0)
			throw new AdempiereException("@NotFound@ @C_Order_ID@");

		MOrder order = new MOrder(Env.getCtx(), C_Order_ID, trxName);

		if (X_C_Order.PAYMENTRULE_CreditCard.equalsIgnoreCase(payment.getPaymentRule()))
		{
			String result = checkCreditCard(order);
			if (Check.isEmpty(result))
			{
				result = "Karte OK"; // TODO: trl
			}
			else
			{
				throw new AdempiereException(result);
			}
		}

		order.setDocAction(MOrder.DOCACTION_Complete);
		if (!order.processIt(MOrder.DOCACTION_Complete))
		{
			order.saveEx();
			throw new AdempiereException(order.getProcessMsg());
		}
		order.saveEx();

		//
		// Process payment
		processPayment(trxName);

		this.processed = true;
		orderGridTab.lock(Env.getCtx(), C_Order_ID, false);
	}

	private void processPayment(String trxName)
	{
		throw new UnsupportedOperationException(); // FIXME implement 04464
		/*
		if (!payment.saveChangesInTrx(trxName))
		{
			return;
		}
		final MPayment paymentPO = payment.getC_Payment();

		if (paymentPO == null || paymentPO.getC_Payment_ID() <= 0)
		{
			; // no payment was generated; could be ok for OnCredit
		}
		else if (!MPayment.DOCSTATUS_Completed.equals(paymentPO.getDocStatus()))
		{
			throw new AdempiereException("@C_Payment_ID@ " + paymentPO + " - @DocStatus@<>" + MPayment.DOCSTATUS_Completed);
		}
		if (MOrder.PAYMENTRULE_CreditCard.equals(payment.getPaymentRule()))
		{
			if (!paymentPO.processOnline())
			{
				throw new AdempiereException("@PaymentNotProcessed@ " + paymentPO.getR_RespMsg());
			}
			paymentPO.saveEx();
			if (!paymentPO.isOnlineApproved())
			{
				throw new AdempiereException("@IsOnlineApproved@: @Y@ - " + paymentPO.getR_RespMsg());
			}
		}
		*/
	}

	private String checkCreditCard(MOrder order)
	{
		throw new UnsupportedOperationException(); // FIXME implement 04464
		/*
		final ITelecashBL teleCashBL = Services.get(ITelecashBL.class);
		Integer mm = MPaymentValidate.getCreditCardExpMM(payment.kExpField.getText());
		Integer yy = MPaymentValidate.getCreditCardExpYY(payment.kExpField.getText());

		String result;
		try
		{
			result = teleCashBL.validateCard(order.getAD_Client_ID(), order.getAD_Org_ID(), payment.kNumberField.getText(),
					mm.toString(), yy.toString(), payment.kApprovalField.getText());
		}
		catch (TelecashRuntimeException ex)
		{
			result = ex.getMessage();
		}

		return result;
		*/
	}

	public void setProcessed(boolean isProcessed)
	{
		this.processed = isProcessed;
	}

	public GridTab getOrderGridTab()
	{
		return this.orderGridTab;
	}

}

class EditorsValueChanged implements ValueChangeListener
{
	WB2BOrderBillingWizardPage page = null;

	public EditorsValueChanged(WB2BOrderBillingWizardPage page)
	{
		this.page = page;
	}

	@Override
	public void valueChange(ValueChangeEvent evt)
	{
//		GridTab oGridTab = ((WB2BOrderBillingWizardPage)page).getOrderGridTab();
//		Integer newVal = (Integer)evt.getNewValue();
//		if (evt.getSource() instanceof WTableDirEditor)
//		{
//			WTableDirEditor source = (WTableDirEditor)evt.getSource(); 
//			int column_id = ((MLookup)source.getLookup()).getColumnId();
//			if (WB2BOrderBillingWizardPage.COLUMN_ID_C_BPartner_Location_ID == column_id)
//			{
//				oGridTab.dataRefresh();
//				setValueAndSave(oGridTab, X_C_Order.COLUMNNAME_C_BPartner_Location_ID, newVal);
//				//
//				WB2BOrderProductWizardPage pwp = (WB2BOrderProductWizardPage)page.getWizardPanel().getPage(0);
//				GridTab olGridTab = pwp.getOrderLineGridTab();
//				olGridTab.dataRefresh();
//				setValueAndSave(olGridTab, X_C_OrderLine.COLUMNNAME_C_BPartner_Location_ID, (Integer)oGridTab.getValue(X_C_OrderLine.COLUMNNAME_C_BPartner_Location_ID));
//	
//				// save for freight cost
//				int C_Order_ID = (Integer)oGridTab.getValue(X_C_Order.COLUMNNAME_C_Order_ID);
//				MOrder order = new MOrder(Env.getCtx(), C_Order_ID, null);
//				MFreightCost freightCost = MFreightCost.retrieveFor(order);
//				String whereClause = X_C_OrderLine.COLUMNNAME_M_Product_ID + " = ? AND "
//						+ X_C_OrderLine.COLUMNNAME_C_Order_ID + " = ? ";
//				I_C_OrderLine ol = new Query(Env.getCtx(), X_C_OrderLine.Table_Name, whereClause, null)
//						.setParameters(freightCost.getM_Product_ID(), C_Order_ID)
//						.first(I_C_OrderLine.class);
//				olGridTab.setCurrentRow(ol.getC_OrderLine_ID());
//				setValueAndSave(olGridTab, X_C_OrderLine.COLUMNNAME_C_BPartner_Location_ID, (Integer)oGridTab.getValue(X_C_OrderLine.COLUMNNAME_C_BPartner_Location_ID));
//	
//			}
//			else if (WB2BOrderBillingWizardPage.COLUMN_ID_AD_User_ID == column_id)
//			{
//				oGridTab.dataRefresh();
//				setValueAndSave(oGridTab, X_C_Order.COLUMNNAME_AD_User_ID, newVal);
//			}
//			else if (WB2BOrderBillingWizardPage.COLUMN_ID_Bill_Location_ID == column_id)
//			{
//				oGridTab.dataRefresh();
//				setValueAndSave(oGridTab, X_C_Order.COLUMNNAME_Bill_Location_ID, newVal);
//			}
//			else if (WB2BOrderBillingWizardPage.COLUMN_ID_Bill_User_ID == column_id)
//			{
//				oGridTab.dataRefresh();
//				setValueAndSave(oGridTab, X_C_Order.COLUMNNAME_Bill_User_ID, newVal);
//			}
//			this.page.setOverviewInfo();
//		}
		// TODO fix later
	}

	private void setValueAndSave(GridTab tab, String column, Integer value)
	{
		Integer actualValue = (Integer)tab.getValue(column);
		if (value != null && (actualValue == null || value.compareTo(actualValue) != 0))
		{
			tab.setValue(column, value);
			saveTab(tab);
		}
	}

	private void saveTab(GridTab tab)
	{
		if (!tab.dataSave(true))
		{
			throw new AdempiereException();
		}
		else
			tab.dataRefresh();
	}
}
