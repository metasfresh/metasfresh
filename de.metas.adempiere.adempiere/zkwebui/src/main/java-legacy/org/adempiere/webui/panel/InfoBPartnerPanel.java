/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.panel;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.event.WTableModelListener;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.MQuery;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Div;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Vbox;

/**
*	Search Business Partner and return selection
*   Based on InfoBPartner written by Jorg Janke
* 	@author Sendy Yagambrum
* 
* 	Zk Port
* 	@author Elaine
* 	@version	InfoBPartner.java Adempiere Swing UI 3.4.1 
*/


public class InfoBPartnerPanel extends InfoPanel implements EventListener, WTableModelListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5677624151607188344L;
	private Label lblValue ;
	private Textbox fieldValue ;
	private Label lblName;
	private Textbox fieldName ;
	private Label lblContact ;
	private Textbox fieldContact;
	private Label lblEMail ;
	private Textbox fieldEMail;
	private Label lblPostal;
	private Textbox fieldPostal;
	private Label lblPhone;
	private Textbox fieldPhone;
	private Checkbox checkAND ;
	private Checkbox checkCustomer;

	private int m_AD_User_ID_index = -1; // Elaine 2008/12/16
    private int m_C_BPartner_Location_ID_index = -1;
		
	/** SalesOrder Trx          */
	private boolean 		m_isSOTrx;
		
	/**	Logger			*/
	protected CLogger log = CLogger.getCLogger(getClass());
	private Borderlayout layout;
	private Vbox southBody;
	
	/** From Clause             */
	private static String s_partnerFROM = "C_BPartner"
		+ " LEFT OUTER JOIN C_BPartner_Location l ON (C_BPartner.C_BPartner_ID=l.C_BPartner_ID AND l.IsActive='Y')"
		+ " LEFT OUTER JOIN AD_User c ON (C_BPartner.C_BPartner_ID=c.C_BPartner_ID AND (c.C_BPartner_Location_ID IS NULL OR c.C_BPartner_Location_ID=l.C_BPartner_Location_ID) AND c.IsActive='Y')" 
		+ " LEFT OUTER JOIN C_Location a ON (l.C_Location_ID=a.C_Location_ID)";
	
	/**  Array of Column Info    */
	private static ColumnInfo[] s_partnerLayout = {
		new ColumnInfo(" ", "C_BPartner.C_BPartner_ID", IDColumn.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Value"), "C_BPartner.Value", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Name"), "C_BPartner.Name", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Contact"), "c.Name AS Contact", KeyNamePair.class, "c.AD_User_ID"),
		new ColumnInfo(Msg.translate(Env.getCtx(), "SO_CreditAvailable"), "C_BPartner.SO_CreditLimit-C_BPartner.SO_CreditUsed AS SO_CreditAvailable", BigDecimal.class, true, true, null),
		new ColumnInfo(Msg.translate(Env.getCtx(), "SO_CreditUsed"), "C_BPartner.SO_CreditUsed", BigDecimal.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Phone"), "c.Phone", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Postal"), "a.Postal", KeyNamePair.class, "l.C_BPartner_Location_ID"),
		new ColumnInfo(Msg.translate(Env.getCtx(), "City"), "a.City", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "TotalOpenBalance"), "C_BPartner.TotalOpenBalance", BigDecimal.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Revenue"), "C_BPartner.ActualLifetimeValue", BigDecimal.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Address1"), "a.Address1", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "IsShipTo"), "l.IsShipTo", Boolean.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "IsBillTo"), "l.IsBillTo", Boolean.class)
	};

	/**
	 *	Standard Constructor
	 *  @param  queryvalue   Query value Name or Value if contains numbers
	 *  @param isSOTrx  if false, query vendors only
	 *  @param whereClause where clause
	 */
	public InfoBPartnerPanel(String queryValue,int windowNo, boolean isSOTrx,boolean multipleSelection, String whereClause)
	{		
		this(queryValue, windowNo, isSOTrx, multipleSelection, whereClause, true);
	}

	/**
	 *	Standard Constructor
	 *  @param  queryvalue   Query value Name or Value if contains numbers
	 *  @param isSOTrx  if false, query vendors only
	 *  @param whereClause where clause
	 */
	public InfoBPartnerPanel(String queryValue,int windowNo, boolean isSOTrx,boolean multipleSelection, String whereClause, boolean lookup)
	{

		super (windowNo, "C_BPartner", "C_BPartner_ID",multipleSelection, whereClause, lookup);
		setTitle(Msg.getMsg(Env.getCtx(), "InfoBPartner"));
		m_isSOTrx = isSOTrx;
        initComponents();
        initLayout();
		initInfo(queryValue, whereClause);
        
        int no = contentPanel.getRowCount();
        setStatusLine(Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
        setStatusDB(Integer.toString(no));
        //
		if (queryValue != null && queryValue.length()>0)
		{
			 executeQuery();
             renderItems();
        }
		p_loadedOK = true; // Elaine 2008/07/28
			
	}
	
	private void initComponents()
	{
		lblValue = new Label();
		lblValue.setValue(Util.cleanAmp(Msg.translate(Env.getCtx(), "Value")));
		lblName = new Label();
		lblName.setValue(Util.cleanAmp(Msg.translate(Env.getCtx(), "Name")));
		lblContact = new Label();
		lblContact.setValue(Msg.translate(Env.getCtx(), "Contact"));
		lblEMail = new Label();
		lblEMail.setValue(Msg.getMsg(Env.getCtx(), "EMail"));
		lblPostal = new Label();
		lblPostal.setValue(Msg.getMsg(Env.getCtx(), "Postal"));
		lblPhone = new Label();
		lblPhone.setValue(Msg.translate(Env.getCtx(), "Phone"));
		
		fieldValue = new Textbox();
		fieldValue.setMaxlength(40);
		fieldName = new Textbox();
		fieldName.setMaxlength(40);
		fieldContact = new Textbox();
		fieldContact.setMaxlength(40);
		fieldEMail = new Textbox();
		fieldEMail.setMaxlength(40);
		fieldPostal = new Textbox();
		fieldPostal.setMaxlength(40);
		fieldPhone = new Textbox();
		fieldPhone.setMaxlength(40);
		
		checkAND = new Checkbox();
		checkAND.setLabel(Msg.getMsg(Env.getCtx(), "SearchAND"));
		checkAND.setChecked(true);
		checkAND.addEventListener(Events.ON_CHECK, this);
		checkCustomer = new Checkbox();
		checkCustomer.setChecked(true);
		checkCustomer.addEventListener(Events.ON_CHECK, this);
		if (m_isSOTrx)
			checkCustomer.setLabel(Msg.getMsg(Env.getCtx(), "OnlyCustomers"));
		else
			checkCustomer.setLabel(Msg.getMsg(Env.getCtx(), "OnlyVendors"));
	}
	
	private void initLayout()
	{
		fieldValue.setWidth("100%");
		fieldContact.setWidth("100%");
		fieldPhone.setWidth("100%");
		
		fieldName.setWidth("100%");
		fieldEMail.setWidth("100%");
		fieldPostal.setWidth("100%");
		
		Grid grid = GridFactory.newGridLayout();
		
		Rows rows = new Rows();
		grid.appendChild(rows);
		
		Row row = new Row();
		rows.appendChild(row);
		row.appendChild(lblValue.rightAlign());
		row.appendChild(fieldValue);
		row.appendChild(lblContact.rightAlign());
		row.appendChild(fieldContact);
		row.appendChild(lblPhone.rightAlign());
		row.appendChild(fieldPhone);
		row.appendChild(checkCustomer);

		row = new Row();
		rows.appendChild(row);
		row.appendChild(lblName.rightAlign());
		row.appendChild(fieldName);
		row.appendChild(lblEMail.rightAlign());
		row.appendChild(fieldEMail);
		row.appendChild(lblPostal.rightAlign());
		row.appendChild(fieldPostal);
		row.appendChild(checkAND);
        
		layout = new Borderlayout();
        layout.setWidth("100%");
        layout.setHeight("100%");
        if (!isLookup())
        {
        	layout.setStyle("position: absolute");
        }
        this.appendChild(layout);

        North north = new North();
        layout.appendChild(north);
		north.appendChild(grid);

        Center center = new Center();
		layout.appendChild(center);
		center.setFlex(true);
		Div div = new Div();
		div.appendChild(contentPanel);
		if (isLookup())
			contentPanel.setWidth("99%");
        else
        	contentPanel.setStyle("width: 99%; margin: 0px auto;");
        contentPanel.setVflex(true);
		div.setStyle("width :100%; height: 100%");
		center.appendChild(div);

		South south = new South();
		layout.appendChild(south);
		southBody = new Vbox();
		southBody.setWidth("100%");
		south.appendChild(southBody);
		southBody.appendChild(confirmPanel);
		southBody.appendChild(new Separator());
		southBody.appendChild(statusBar);
        		
	}	
	
	/**
	 *	Dynamic Init
	 *  @param value value
	 *  @param whereClause where clause
	 */
		
	private void initInfo(String value, String whereClause)
	{
			/**	From
				C_BPartner
				 LEFT OUTER JOIN C_BPartner_Location l ON (C_BPartner.C_BPartner_ID=l.C_BPartner_ID AND l.IsActive='Y') 
				 LEFT OUTER JOIN AD_User c ON (C_BPartner.C_BPartner_ID=c.C_BPartner_ID AND (c.C_BPartner_Location_ID IS NULL OR c.C_BPartner_Location_ID=l.C_BPartner_Location_ID) AND c.IsActive='Y') 
				 LEFT OUTER JOIN C_Location a ON (l.C_Location_ID=a.C_Location_ID)
			**/

			//	Create Grid
			StringBuffer where = new StringBuffer();
			where.append("C_BPartner.IsSummary='N' AND C_BPartner.IsActive='Y'");
			if (whereClause != null && whereClause.length() > 0)
				where.append(" AND ").append(whereClause);
			//
                          
			prepareTable(s_partnerLayout, s_partnerFROM, where.toString(), "C_BPartner.Value");
			
			// Get indexes
            for (int i = 0; i < p_layout.length; i++)
            {
            	// Elaine 2008/12/16
            	if (p_layout[i].getKeyPairColSQL().indexOf("AD_User_ID") != -1)
    				m_AD_User_ID_index = i;
            	//
                if (p_layout[i].getKeyPairColSQL().indexOf("C_BPartner_Location_ID") != -1)
                    m_C_BPartner_Location_ID_index = i;
            }
            //  Set Value
			if (value == null)
				value = "%";
			if (!value.endsWith("%"))
				value += "%";

			//	Put query string in Name if not numeric
			if (value.equals("%"))
				fieldName.setText(value);
			//	No Numbers entered
			else if ((value.indexOf('0')+value.indexOf('1')+value.indexOf('2')+value.indexOf('3')+value.indexOf('4') +value.indexOf('5')
				+value.indexOf('6')+value.indexOf('7')+value.indexOf('8')+value.indexOf('9')) == -10)
			{
				if (value.startsWith("%"))
					fieldName.setText(value);
				else
					fieldName.setText("%" + value);
			}
			//	Number entered
			else
				fieldValue.setText(value);
	}	//	initInfo

	/**
	 *  Set Parameters for Query.
	 *  (as defined in getSQLWhere)
	 *  @param pstmt pstmt
	 *  @param forCount for counting records
	 *  @throws SQLException
	 */
	public void setParameters(PreparedStatement pstmt, boolean forCount) throws SQLException
	{
		int index = 1;
		//	=> Value
		String value = fieldValue.getText().toUpperCase();
		if (!(value.equals("") || value.equals("%")))
		{
			if (!value.endsWith("%"))
				value += "%";
			pstmt.setString(index++, value);
			log.fine("Value: " + value);
		}
		//	=> Name
		String name = fieldName.getText().toUpperCase();
		if (!(name.equals("") || name.equals("%")))
		{
			if (!name.endsWith("%"))
				name += "%";
			pstmt.setString(index++, name);
			log.fine("Name: " + name);
		}
		//	=> Contact
		String contact = fieldContact.getText().toUpperCase();
		if (!(contact.equals("") || contact.equals("%")))
		{
			if (!contact.endsWith("%"))
				contact += "%";
			pstmt.setString(index++, contact);
			log.fine("Contact: " + contact);
		}
		//	=> EMail
		String email = fieldEMail.getText().toUpperCase();
		if (!(email.equals("") || email.equals("%")))
		{
			if (!email.endsWith("%"))
				email += "%";
			pstmt.setString(index++, email);
			log.fine("EMail: " + email);
		}
		//	=> Phone
		String phone = fieldPhone.getText().toUpperCase();
		if (!(phone.equals("") || phone.equals("%")))
		{
			if (!phone.endsWith("%"))
				phone += "%";
			pstmt.setString(index++, phone);
			log.fine("Phone: " + phone);
		}
		//	=> Postal
		String postal = fieldPostal.getText().toUpperCase();
		if (!(postal.equals("") || postal.equals("%")))
		{
			if (!postal.endsWith("%"))
				postal += "%";
			pstmt.setString(index++, postal);
			log.fine("Postal: " + postal);
		}
	}   //  setParameters

	/*************************************************************************/
	/*************************************************************************/
	/**
	 *	Construct SQL Where Clause and define parameters.
	 *  (setParameters needs to set parameters)
	 *  Includes first AND
	 *  @return WHERE clause
	 */
	public String getSQLWhere()
	{
		ArrayList<String> list = new ArrayList<String>();
		//	=> Value
		String value = fieldValue.getText().toUpperCase();
		if (!(value.equals("") || value.equals("%")))
			list.add ("UPPER(C_BPartner.Value) LIKE ?");
		//	=> Name
		String name = fieldName.getText().toUpperCase();
		if (!(name.equals("") || name.equals("%")))
			list.add ("UPPER(C_BPartner.Name) LIKE ?");
		//	=> Contact
		String contact = fieldContact.getText().toUpperCase();
		if (!(contact.equals("") || contact.equals("%")))
			list.add ("UPPER(c.Name) LIKE ?");
		//	=> EMail
		String email = fieldEMail.getText().toUpperCase();
		if (!(email.equals("") || email.equals("%")))
			list.add ("UPPER(c.EMail) LIKE ?");
		//	=> Phone
		String phone = fieldPhone.getText().toUpperCase();
		if (!(phone.equals("") || phone.equals("%")))
			list.add ("UPPER(c.Phone) LIKE ?");
		//	=> Postal
		String postal = fieldPostal.getText().toUpperCase();
		if (!(postal.equals("") || postal.equals("%")))
			list.add ("UPPER(a.Postal) LIKE ?");
		StringBuffer sql = new StringBuffer();
		int size = list.size();
		//	Just one
		if (size == 1)
			sql.append(" AND ").append(list.get(0));
		else if (size > 1)
		{
			boolean AND = checkAND.isChecked();
			sql.append(" AND ");
			if (!AND)
				sql.append("(");
			for (int i = 0; i < size; i++)
			{
				if (i > 0)
					sql.append(AND ? " AND " : " OR ");
				sql.append(list.get(i));
			}
			if (!AND)
				sql.append(")");
		}
			//	Static SQL
		if (checkCustomer.isChecked())
		{
			sql.append(" AND ");
			if (m_isSOTrx)
				sql.append ("C_BPartner.IsCustomer='Y'");
			else
				sql.append ("C_BPartner.IsVendor='Y'");
		}
		return sql.toString();

	}	//	getSQLWhere
    
    /*************************************************************************/

    /**
     *  Save Selection Details
     *  Get Location/Partner Info
     */
    public void saveSelectionDetail()
    {
        int row = contentPanel.getSelectedRow();
        if (row == -1)
            return;

        int AD_User_ID = 0;
        int C_BPartner_Location_ID = 0;
        
        // Elaine 2008/12/16
        if (m_AD_User_ID_index != -1)
        {
            Object data =contentPanel.getValueAt(row, m_AD_User_ID_index);
            if (data instanceof KeyNamePair)
            	AD_User_ID = ((KeyNamePair)data).getKey();
        }
        //
        if (m_C_BPartner_Location_ID_index != -1)
        {
            Object data =contentPanel.getValueAt(row, m_C_BPartner_Location_ID_index);
            if (data instanceof KeyNamePair)
                C_BPartner_Location_ID = ((KeyNamePair)data).getKey();
        }
        //  publish for Callout to read
        Integer ID = getSelectedRowKey();
        Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO, "C_BPartner_ID", ID == null ? "0" : ID.toString());
        Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO, "AD_User_ID", String.valueOf(AD_User_ID));
        Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO, "C_BPartner_Location_ID", String.valueOf(C_BPartner_Location_ID));
       
    }   //  saveSelectionDetail
    
    // Elaine 2008/12/16
	/**************************************************************************
	 *	Show History
	 */
	protected void showHistory()
	{
		log.info("");
		Integer C_BPartner_ID = getSelectedRowKey();
		if (C_BPartner_ID == null)
			return;
		InvoiceHistory ih = new InvoiceHistory (this, C_BPartner_ID.intValue(), 
			0, 0, 0);
		ih.setVisible(true);
		ih = null;
	}	//	showHistory

	/**
	 *	Has History
	 *  @return true
	 */
	protected boolean hasHistory()
	{
		return true;
	}	//	hasHistory

	/**
	 *	Zoom
	 */
	public void zoom()
	{
		log.info( "InfoBPartner.zoom");
		Integer C_BPartner_ID = getSelectedRowKey();
		if (C_BPartner_ID == null)
			return;
	//	AEnv.zoom(MBPartner.Table_ID, C_BPartner_ID.intValue(), true);	//	SO

		MQuery query = new MQuery("C_BPartner");
		query.addRestriction("C_BPartner_ID", MQuery.EQUAL, C_BPartner_ID);
		query.setRecordCount(1);
		int AD_WindowNo = getAD_Window_ID("C_BPartner", true);	//	SO
		AEnv.zoom (AD_WindowNo, query);
	}	//	zoom

	/**
	 *	Has Zoom
	 *  @return true
	 */
	protected boolean hasZoom()
	{
		return true;
	}	//	hasZoom

	/**
	 *	Customize
	 */
	protected void customize()
	{
		log.info( "InfoBPartner.customize");
	}	//	customize

	/**
	 *	Has Customize
	 *  @return false
	 */
	protected boolean hasCustomize()
	{
		return false;	//	for now
	}	//	hasCustomize
	//
	
    public void tableChanged(WTableModelEvent event)
    {
        
    }
	
}
