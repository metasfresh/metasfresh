/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
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
package org.adempiere.webui.apps.form;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WNumberEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.editor.WStringEditor;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.window.FDialog;
import org.compiere.grid.CreateFromStatement;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.MBankStatement;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MPayment;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zul.Hbox;

public class WCreateFromStatementUI extends CreateFromStatement implements EventListener
{
	private static final long serialVersionUID = 1L;
	
	private WCreateFromWindow window;
	
	public WCreateFromStatementUI(GridTab tab) 
	{
		super(tab);
		log.info(getGridTab().toString());
		
		window = new WCreateFromWindow(this, getGridTab().getWindowNo());
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if (!dynInit())
				return;
			zkInit();
			setInitOK(true);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
			setInitOK(false);
		}
		AEnv.showWindow(window);
	}
	
	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	
	protected Label bankAccountLabel = new Label();
	protected WTableDirEditor bankAccountField;
	
	protected Label documentNoLabel = new Label(Msg.translate(Env.getCtx(), "DocumentNo"));
	protected WStringEditor documentNoField = new WStringEditor();

	protected Label documentTypeLabel = new Label();
	protected WTableDirEditor documentTypeField;

	protected Label authorizationLabel = new Label();
	protected WStringEditor authorizationField = new WStringEditor();

	protected Label tenderTypeLabel = new Label();
	protected WTableDirEditor tenderTypeField;
	
	protected Label amtFromLabel = new Label(Msg.translate(Env.getCtx(), "PayAmt"));
	protected WNumberEditor amtFromField = new WNumberEditor("AmtFrom", false, false, true, DisplayType.Amount, Msg.translate(Env.getCtx(), "AmtFrom"));
	protected Label amtToLabel = new Label("-");
	protected WNumberEditor amtToField = new WNumberEditor("AmtTo", false, false, true, DisplayType.Amount, Msg.translate(Env.getCtx(), "AmtTo"));
	
	protected Label BPartner_idLabel = new Label(Msg.translate(Env.getCtx(), "BPartner"));
	protected WEditor bPartnerLookup;

	protected Label dateFromLabel = new Label(Msg.translate(Env.getCtx(), "DateTrx"));
	protected WDateEditor dateFromField = new WDateEditor("DateFrom", false, false, true, Msg.translate(Env.getCtx(), "DateFrom"));
	protected Label dateToLabel = new Label("-");
	protected WDateEditor dateToField = new WDateEditor("DateTo", false, false, true, Msg.translate(Env.getCtx(), "DateTo"));

	/**
	 *  Dynamic Init
	 *  @throws Exception if Lookups cannot be initialized
	 *  @return true if initialized
	 */
	@Override
	public boolean dynInit() throws Exception
	{
		log.config("");
		
		super.dynInit();
		
		//Refresh button
		Button refreshButton = window.getConfirmPanel().createButton(ConfirmPanel.A_REFRESH);
		refreshButton.addEventListener(Events.ON_CLICK, this);
		window.getConfirmPanel().addButton(refreshButton);
				
		if (getGridTab().getValue("C_BankStatement_ID") == null)
		{
			FDialog.error(0, window, "SaveErrorRowNotFound");
			return false;
		}
		
		window.setTitle(getTitle());
		
		int AD_Column_ID = 4917;        //  C_BankStatement.C_BP_BankAccount_ID
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.TableDir);
		bankAccountField = new WTableDirEditor ("C_BP_BankAccount_ID", true, false, true, lookup);
		//  Set Default
		int C_BP_BankAccount_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_BP_BankAccount_ID");
		bankAccountField.setValue(new Integer(C_BP_BankAccount_ID));
		//  initial Loading
		authorizationField = new WStringEditor ("authorization", false, false, true, 10, 30, null, null);
		authorizationField.getComponent().addEventListener(Events.ON_CHANGE, this);

		lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, MColumn.getColumn_ID(MPayment.Table_Name, MPayment.COLUMNNAME_C_DocType_ID), DisplayType.TableDir);
		documentTypeField = new WTableDirEditor (MPayment.COLUMNNAME_C_DocType_ID,false,false,true,lookup);
		documentTypeField.getComponent().addEventListener(Events.ON_CHANGE, this);
		
		lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, MColumn.getColumn_ID(MPayment.Table_Name, MPayment.COLUMNNAME_TenderType), DisplayType.List);
		tenderTypeField = new WTableDirEditor (MPayment.COLUMNNAME_TenderType,false,false,true,lookup);
		tenderTypeField.getComponent().addEventListener(Events.ON_CHANGE, this);
		
		lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 3499, DisplayType.Search);
		bPartnerLookup = new WSearchEditor ("C_BPartner_ID", false, false, true, lookup);
		
		Timestamp date = Env.getContextAsDate(Env.getCtx(), p_WindowNo, MBankStatement.COLUMNNAME_StatementDate);
		dateToField.setValue(date);
	
		bankAccount = InterfaceWrapperHelper.create(Env.getCtx(), C_BP_BankAccount_ID, I_C_BP_BankAccount.class, null);
		
		loadBankAccount();
		
		return true;
	}   //  dynInit
	
	protected void zkInit() throws Exception
	{
		bankAccountLabel.setText(Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"));
    	authorizationLabel.setText(Msg.translate(Env.getCtx(), "R_AuthCode"));
    	
    	documentTypeLabel.setText(Msg.translate(Env.getCtx(), "C_DocType_ID"));
    	tenderTypeLabel.setText(Msg.translate(Env.getCtx(), "TenderType"));
    	
    	dateFromField.getComponent().setTooltiptext(Msg.translate(Env.getCtx(), "DateFrom"));
    	dateToField.getComponent().setTooltiptext(Msg.translate(Env.getCtx(), "DateTo"));
    	
    	amtFromField.getComponent().setTooltiptext(Msg.translate(Env.getCtx(), "AmtFrom"));
    	amtToField.getComponent().setTooltiptext(Msg.translate(Env.getCtx(), "AmtTo"));
    	
    	Borderlayout parameterLayout = new Borderlayout();
		parameterLayout.setHeight("110px");
		parameterLayout.setWidth("100%");
    	Panel parameterPanel = window.getParameterPanel();
		parameterPanel.appendChild(parameterLayout);
		
		Grid parameterBankLayout = GridFactory.newGridLayout();
    	Panel parameterBankPanel = new Panel();
    	parameterBankPanel.appendChild(parameterBankLayout);

		Center center = new Center();
		parameterLayout.appendChild(center);
		center.appendChild(parameterBankPanel);
		
		Rows rows = (Rows) parameterBankLayout.newRows();
		Row row = rows.newRow();
		row.appendChild(bankAccountLabel.rightAlign());
		row.appendChild(bankAccountField.getComponent());
		row.appendChild(documentNoLabel.rightAlign());
		row.appendChild(documentNoField.getComponent());
		
		row = rows.newRow();
		row.appendChild(documentTypeLabel.rightAlign());
		row.appendChild(documentTypeField.getComponent());
		row.appendChild(authorizationLabel.rightAlign());
		row.appendChild(authorizationField.getComponent());
		
		row = rows.newRow();
		row.appendChild(tenderTypeLabel.rightAlign());
		row.appendChild(tenderTypeField.getComponent());

		row.appendChild(amtFromLabel.rightAlign());
		Hbox hbox = new Hbox();
		hbox.appendChild(amtFromField.getComponent());
		hbox.appendChild(amtToLabel.rightAlign());
		hbox.appendChild(amtToField.getComponent());
		row.appendChild(hbox);
		
		row = rows.newRow();
		row.appendChild(BPartner_idLabel.rightAlign());
		row.appendChild(bPartnerLookup.getComponent());
		row.appendChild(dateFromLabel.rightAlign());
		
		hbox = new Hbox();
		hbox.appendChild(dateFromField.getComponent());
		hbox.appendChild(dateToLabel.rightAlign());
		hbox.appendChild(dateToField.getComponent());
		row.appendChild(hbox);
	}

	/**
	 *  Action Listener
	 *  @param e event
	 * @throws Exception 
	 */
	@Override
	public void onEvent(Event e) throws Exception
	{
		log.config("Action=" + e.getTarget().getId());
		if(e.getTarget().equals(window.getConfirmPanel().getButton(ConfirmPanel.A_REFRESH)))
		{
			loadBankAccount();
			window.tableChanged(null);
		}
	}
	
	protected void loadBankAccount()
	{
		loadTableOIS(getBankData(documentNoField.getValue().toString(), bPartnerLookup.getValue(), dateFromField.getValue(), dateToField.getValue(),
				amtFromField.getValue(), amtToField.getValue(), documentTypeField.getValue(), tenderTypeField.getValue(), 
				authorizationField.getValue().toString()));
	}
	
	protected void loadTableOIS (Vector<?> data)
	{
		window.getWListbox().clear();
		
		//  Remove previous listeners
		window.getWListbox().getModel().removeTableModelListener(window);
		//  Set Model
		ListModelTable model = new ListModelTable(data);
		model.addTableModelListener(window);
		window.getWListbox().setData(model, getOISColumnNames());
		//
		
		configureMiniTable(window.getWListbox());
	}
	
	/**
	 *  List total amount
	 */
	@Override
	public void info()
	{
		DecimalFormat format = DisplayType.getNumberFormat(DisplayType.Amount);

		BigDecimal total = new BigDecimal(0.0);
		int rows = window.getWListbox().getRowCount();
		int count = 0;
		for (int i = 0; i < rows; i++)
		{
			if (((Boolean)window.getWListbox().getValueAt(i, 0)).booleanValue())
			{
				total = total.add((BigDecimal)window.getWListbox().getValueAt(i, 4));
				count++;
			}
		}
		window.setStatusLine(count, Msg.getMsg(Env.getCtx(), "Sum") + "  " + format.format(total));
	}   //  infoStatement
	
	@Override
	public void showWindow()
	{
		window.setVisible(true);
	}
	
	@Override
	public void closeWindow()
	{
		window.dispose();
	}
}
