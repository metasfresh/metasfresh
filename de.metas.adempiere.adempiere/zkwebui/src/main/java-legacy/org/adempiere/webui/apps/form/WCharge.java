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

/**
 * 2007, Modified by Posterita Ltd.
 */

package org.adempiere.webui.apps.form;

import java.util.logging.Level;

import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.window.FDialog;
import org.compiere.apps.form.Charge;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Separator;

/**
 * This class represents the Custom Form for generating charges
 * from natural accounts.
 *
 * The form is comprised of two parts.
 * The upper portion can be used to create new charges using the general charge accounts.
 * The lower portion can be used to create charges based on the natural account.
 *
 * @author Andrew Kimball
 *
 */
public class WCharge extends Charge implements IFormController, EventListener
{
    /**
	 *
	 */
	private static final long serialVersionUID = 4210542409436277344L;

	private CustomForm form = new CustomForm();

	/** AD_Message for "Create". */
    private static final String AD_MESSAGE_CREATE = "Create";
    /** Logger.          */
    private static CLogger log = CLogger.getCLogger(WCharge.class);

    // new panel
    /** Grid for components for creating a new charge account. */
    private Grid m_grdNew = GridFactory.newGridLayout();
    /** Value (key) field label. */
    private Label m_lblValue = new Label();
    /** Field for specifying value (key) of new account. */
    private Textbox m_txbValueField = new Textbox();
    /** Checkbox for specifying whether or not the new account is an expense account. */
    private Checkbox m_chbIsExpense = new Checkbox();
    /** Name field label. */
    private Label m_lblName = new Label();
    /** Field for specifying name of new account. */
    private Textbox m_txbNameField = new Textbox();
    /** Button to create new account. */
    private Button m_btnNew = new Button();

    // account panel
    /** Grid for components for creating a charge form a selected account. **/
    private Panel m_pnlAccount = new Panel();
    /** Button to create charge from selected account. */
    private Button m_btnAccount = new Button();
    /** Table to hold data of accounts. */
    private WListbox m_tblData = new WListbox();

    /** confirmation panel. */
    private ConfirmPanel m_pnlConfirm = new ConfirmPanel();
    /** Confirmation Grid. */
    private Grid m_grdConfirm = GridFactory.newGridLayout();

    /** Enumeration of column names and indices. */
    private enum EColumn
    {
        /** Select column to record whether the account is selected. */
        SELECT(0, "Select"),
        /** Value column to hold the account key. */
        VALUE(1, "Value"),
        /** Name column to hold the account name. */
        NAME(2, "Name"),
        /** Expense column to indicate whether or not the account is an expense account. */
        EXPENSE(3, "Expense");

        /** The column's index. */
        private final int m_index;
        /** The column's name. */
        private final String m_title;

        /**
         * Constructor.
         *
         * @param index index of the column
         * @param title name of the column
         */
        EColumn(int index, String title)
        {
            m_index = index;
            m_title = title;
        }

        /**
         * Gets the index of the column.
         *
         *  @return the column index.
         */
        public int index()
        {
            return m_index;
        }

        /**
         * Gets the name of the column.
         *
         * @return the column's name
         */
        public String title()
        {
            return m_title;
        }

        /**
         * Gets the number of columns.
         *
         * @return the number of columns.
         */
        public static int count()
        {
            return values().length;
        }

    }

    /**
     * Default constructor.
     */
    public WCharge()
    {
        super();
        initForm();
    }


    /**
     * Initialises the panel.
     *
     * @param adFormId  The Adempiere identifier for the form
     * @param name      The name of the form
     */
    protected void initForm()
    {
        log.info("");
        try
        {
            staticInitialise();
            dynamicInitialise();
            zkInit();
        }
        catch(Exception e)
        {
            log.log(Level.SEVERE, "", e);
        }

        return;
    }


    /**
     * Initialises the static components of the form.
     */
    private void staticInitialise()
    {
        createNewChargePanel();
        createAccountPanel();
        createConfirmPanel();

        return;
    }

    private void zkInit()
	{
		Borderlayout contentPane = new Borderlayout();
		form.appendChild(contentPane);

		North north = new North();
		contentPane.appendChild(north);
		north.appendChild(m_grdNew);

		Center center = new Center();
        contentPane.appendChild(center);
		center.appendChild(m_pnlAccount);

		South south = new South();
		contentPane.appendChild(south);
		Panel southPanel = new Panel();
		south.appendChild(southPanel);
		southPanel.appendChild(new Separator());
		southPanel.appendChild(m_grdConfirm);
	}

    /**
     * Creates the account panel.
     *
     * The account panel contains:
     * <li>a table detailing all accounts
     * <li>a button for creating charges for selected accounts
     */
    private void createAccountPanel()
    {
    	Borderlayout borderlayout = new Borderlayout();
    	borderlayout.setStyle("position: absolute");
    	borderlayout.setWidth("100%");
    	borderlayout.setHeight("100%");
    	m_pnlAccount.appendChild(borderlayout);

		North north = new North();
		north.setBorder("none");
		borderlayout.appendChild(north);
        Label label = new Label(Msg.getMsg(Env.getCtx(), "ChargeFromAccount"));
        label.setStyle("font-weight: bold;");
		north.appendChild(label);

		Center center = new Center();
		center.setBorder("none");
		center.setFlex(true);
		center.setAutoscroll(true);
		borderlayout.appendChild(center);
		center.appendChild(m_tblData);

		South south = new South();
		south.setBorder("none");
		borderlayout.appendChild(south);
		Panel southPanel = new Panel();
		southPanel.setAlign("right");
		south.appendChild(southPanel);
		m_btnAccount.setLabel(Msg.getMsg(Env.getCtx(), AD_MESSAGE_CREATE) + " " + Msg.getMsg(Env.getCtx(), "From") + " " + Msg.getElement(Env.getCtx(), "Account_ID"));
        m_btnAccount.addEventListener(Events.ON_CLICK, this);
		southPanel.appendChild(m_btnAccount);

        return;
    }

    /**
     * Creates the New Charge panel.
     *
     * The New Charge panel is used to specify the name and key of an account
     * and whether or not the account is a charge account.
     */
    private void createNewChargePanel()
    {
        final int nameFieldColumns = 20;
        final int valueFieldColumns = 10;

        // top row
        m_lblValue.setValue(Msg.translate(Env.getCtx(), EColumn.VALUE.title()));
        m_txbValueField.setCols(valueFieldColumns);
        m_chbIsExpense.setChecked(true);
        m_chbIsExpense.setLabel(Msg.getMsg(Env.getCtx(), EColumn.EXPENSE.title()));

        // bottom row
        m_lblName.setValue(Msg.translate(Env.getCtx(), EColumn.NAME.title()));
        m_txbNameField.setCols(nameFieldColumns);
        m_btnNew.setLabel(Msg.getMsg(Env.getCtx(), AD_MESSAGE_CREATE) + " " + Util.cleanAmp(Msg.getMsg(Env.getCtx(), "New")));
        m_btnNew.addEventListener(Events.ON_CLICK, this);

    	Rows rows = new Rows();
    	m_grdNew.appendChild(rows);

    	Row row = new Row();
        rows.appendChild(row);
        row.setSpans("3");
        Label label = new Label(Msg.getMsg(Env.getCtx(), "ChargeNewAccount"));
        label.setStyle("font-weight: bold;");
        row.appendChild(label);

    	row = new Row();
        rows.appendChild(row);
        row.appendChild(m_lblValue);
        row.appendChild(m_txbValueField);
        row.appendChild(m_chbIsExpense);

        row = new Row();
        rows.appendChild(row);
        row.appendChild(m_lblName);
        row.appendChild(m_txbNameField);
        row.appendChild(m_btnNew);

        row = new Row();
        rows.appendChild(row);
        row.setSpans("3");
        row.appendChild(new Separator());

        return;
    }


    /**
     *  Initialises the dynamic components of the form.
     *  <li>Gets defaults for primary AcctSchema
     *  <li>Creates Table with Accounts
     */
    private void dynamicInitialise()
    {
    	findChargeElementID();
        ListModelTable model = new ListModelTable(getData());
        m_tblData.setData(model, getColumnNames());
		setColumnClass(m_tblData);
		findTaxCategoryID();

        return;
    }   //  dynInit

    /**
     *  Event Listener.
     *
     *  @param event event that has been fired.
     */
    public void onEvent(Event event)
    {
        log.info(event.getName());
        //
        if (event.getTarget().getId().equals(ConfirmPanel.A_OK) || m_C_Element_ID == 0)
        {
            close();
        }
        //  new Account
        else if (event.getTarget().equals(m_btnNew))
        {
            createNew();
        }
        else if (event.getTarget().equals(m_btnAccount))
        {
            createAccount();
        }

        return;
    }

    /**
     *  Create new Chargeable Account.
     */
    private void createNew()
    {
        String value;
        String name;

        log.config("");
        //  Get Input
        value = m_txbValueField.getValue();
        if (value.length() == 0)
        {
        	throw new WrongValueException(m_txbValueField, Msg.getMsg(Env.getCtx(), "FillMandatory") + m_lblValue.getValue());
        }

        name = m_txbNameField.getText();
        if (name.length() == 0)
        {
        	throw new WrongValueException(m_txbNameField, Msg.getMsg(Env.getCtx(), "FillMandatory") + m_lblName.getValue());
        }

        //  Create Element
        int elementValueId = createElementValue (value, name, m_chbIsExpense.isChecked());
        if (elementValueId == 0)
        {
            FDialog.error(form.getWindowNo(), form, "ChargeNotCreated", name);
            return;
        }
        //  Create Charge
        int chargeId = createCharge(name, elementValueId);
        if (chargeId == 0)
        {
            FDialog.error(form.getWindowNo(), form, "ChargeNotCreated", name);
            return;
        }
        FDialog.info(form.getWindowNo(), form, "ChargeCreated", name);
    }   //  createNew

    /**
     *  Creates Charges from Accounts.
     *  Charges are created for the selected accounts.
     *  The selection is cleared upon completion.
     */
    private void createAccount()
    {
        createAccount(m_tblData);
        if (listCreated.length() > 0)
        {
            FDialog.info(form.getWindowNo(), form, "ChargeCreated", listCreated.toString());
        }
        if (listRejected.length() > 0)
        {
            FDialog.error(form.getWindowNo(), form, "ChargeNotCreated", listRejected.toString());
        }

        return;
    }   //  createAccount

    /**
     *  Create Confirmation Panel with OK Button.
     */
    public void createConfirmPanel()
    {
        Rows rows = new Rows();
        Row row = new Row();
        m_pnlConfirm.addActionListener(this);
        row.appendChild(m_pnlConfirm);
        rows.appendChild(row);
        m_grdConfirm.appendChild(rows);

        return;
    }   //  ConfirmPanel


    public void close()
    {
        SessionManager.getAppDesktop().closeActiveWindow();
    }


	public ADForm getForm() {
		return form;
	}
}


