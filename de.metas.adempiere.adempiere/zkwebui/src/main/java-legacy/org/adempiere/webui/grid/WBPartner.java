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

package org.adempiere.webui.grid;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.util.Services;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.component.VerticalBox;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.editor.WLocationEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MLocation;
import org.compiere.model.MLocationLookup;
import org.compiere.model.MUser;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Separator;

import de.metas.document.documentNo.IDocumentNoBuilderFactory;

/**
 * Business Partner : Based on VBPartner
 *
 * @author 	Niraj Sohun
 * 			Aug 15, 2007
 *
 */

public class WBPartner extends Window implements EventListener, ValueChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5842369060073088746L;

	private static Logger log = LogManager.getLogger(WBPartner.class);
	
	private int m_WindowNo;
	
	/** The Partner				*/
	private MBPartner m_partner = null;
	
	/** The Location			*/
	private MBPartnerLocation m_pLocation = null;
	
	/** The User				*/
	private MUser m_user = null;
	
	/** Read Only				*/
	private boolean m_readOnly = false;

	private Object[] m_greeting;
	
	private Textbox fValue = new Textbox();
	private Listbox fGreetingBP = new Listbox();
	private Textbox fName = new Textbox();
	private Textbox fName2 = new Textbox();
	private Textbox fContact = new Textbox();
	private Listbox fGreetingC = new Listbox();
	private Textbox fTitle = new Textbox();
	private Textbox fEMail = new Textbox();
	private Textbox fPhone = new Textbox();
	private Textbox fPhone2 = new Textbox();
	private Textbox fFax = new Textbox();
	
	private WLocationEditor fAddress;/* = new WLocationDialog();*/
	
	private VerticalBox centerPanel = new VerticalBox();
	
	private ConfirmPanel confirmPanel = new ConfirmPanel(true, false, false, false, false, false);
	
	/**
	 *	Constructor.
	 *	Requires call loadBPartner
	 * 	@param frame	parent
	 * 	@param WindowNo	Window No
	 */
	
	public WBPartner(int WindowNo)
	{
		super();
		
		m_WindowNo = WindowNo;
		m_readOnly = !Env.getUserRolePermissions().canUpdate(
			Env.getAD_Client_ID(Env.getCtx()), Env.getAD_Org_ID(Env.getCtx()), 
			MBPartner.Table_ID, 0, false);
		log.info("R/O=" + m_readOnly);
		
		try
		{
			jbInit();
		}
		catch(Exception ex)
		{
			log.error(ex.getMessage());
		}
		
		initBPartner();
		
	}	//	WBPartner
	
	/**
	 *	Static Init
	 * 	@throws Exception
	 */
	
	void jbInit() throws Exception
	{
		this.setWidth("350px");
		this.setBorder("normal");
		this.setClosable(true);
		this.setTitle("Business Partner");
		this.setAttribute("mode", "modal");
		this.appendChild(centerPanel);
		this.appendChild(confirmPanel);
		
		confirmPanel.addActionListener(Events.ON_CLICK, this);
	}
	
	/**
	 *	Dynamic Init
	 */
	private void initBPartner()
	{
		//	Get Data
		m_greeting = fillGreeting();

		//	Value
		fValue.addEventListener(Events.ON_CHANGE , this);
		createLine (fValue, "Value", true);
		
		//	Greeting Business Partner
		fGreetingBP.setMold("select");
		fGreetingBP.setRows(0);
		
		for (int i = 0; i < m_greeting.length; i++)
			fGreetingBP.appendItem(m_greeting[i].toString(), m_greeting[i]);
		createLine (fGreetingBP, "Greeting", false);
		
		//	Name
		fName.addEventListener(Events.ON_CLICK, this);
		createLine (fName, "Name", false)/*.setFontBold(true)*/;

		//	Name2
		createLine (fName2, "Name2", false);
		
		//	Contact
		createLine (fContact, "Contact", true)/*.setFontBold(true)*/;

		//	Greeting Contact
		fGreetingC.setMold("select");
		fGreetingC.setRows(0);
		
		for (int i = 0; i < m_greeting.length; i++)
			fGreetingC.appendItem(m_greeting[i].toString(), m_greeting[i]);
		
		createLine (fGreetingC, "Greeting", false);
		
		//	Title
		createLine(fTitle, "Title", false);

		//	Email
		createLine (fEMail, "EMail", false);
		
		//	Location
		boolean ro = m_readOnly;
		
		if (!ro)
			ro = !Env.getUserRolePermissions().canUpdate(
				Env.getAD_Client_ID(Env.getCtx()), Env.getAD_Org_ID(Env.getCtx()), 
				MBPartnerLocation.Table_ID, 0, false);
		
		if (!ro)
			ro = !Env.getUserRolePermissions().canUpdate(
				Env.getAD_Client_ID(Env.getCtx()), Env.getAD_Org_ID(Env.getCtx()), 
				MLocation.Table_ID, 0, false);
		
		fAddress = new WLocationEditor("C_Location_ID", false, ro, true, 
				new MLocationLookup (Env.getCtx(), m_WindowNo));
		fAddress.addValueChangeListener(this);
		fAddress.setValue (null);
		createLine (fAddress.getComponent(), "C_Location_ID", true)/*.setFontBold(true)*/;
		
		//	Phone
		createLine (fPhone, "Phone", true);
		
		//	Phone2
		createLine (fPhone2, "Phone2", false);
		
		//	Fax
		createLine (fFax, "Fax", false);
	}	//	initBPartner

	/**
	 * 	Create Line
	 * 	@param field 	field
	 * 	@param title	label value
	 * 	@param addSpace	add more space
	 * 	@return label
	 */
	
	private Label createLine (Component field, String title, boolean addSpace)
	{
		Hbox hbox = new Hbox(); 
		
		hbox.setWidth("100%");
		hbox.setWidths("30%, 70%");
		
		Label label = new Label(Msg.translate(Env.getCtx(), title));
		hbox.appendChild(label);

		hbox.appendChild(field);
		
		centerPanel.appendChild(hbox);
		centerPanel.appendChild(new Separator());
		
		return label;
	}	//	createLine

	/**
	 *	Fill Greeting
	 * 	@return KeyNamePair Array of Greetings
	 */
	
	private Object[] fillGreeting()
	{
		String sql = "SELECT C_Greeting_ID, Name FROM C_Greeting WHERE IsActive='Y' ORDER BY 2";
		sql = Env.getUserRolePermissions().addAccessSQL(sql, "C_Greeting", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		
		return DB.getKeyNamePairs(sql, true);
	}	//	fillGreeting

	/**
	 *	Search m_greeting for key
	 * 	@param key	C_Greeting_ID
	 * 	@return	Greeting
	 */
	
	private KeyNamePair getGreeting (int key)
	{
		for (int i = 0; i < m_greeting.length; i++)
		{
			KeyNamePair p = (KeyNamePair)m_greeting[i];
			if (p.getKey() == key)
				return p;
		}
		
		return new KeyNamePair(-1, " ");
	}	//	getGreeting

	/**
	 *	Load BPartner
	 *  @param C_BPartner_ID - existing BPartner or 0 for new
	 * 	@return true if loaded
	 */
	
	public boolean loadBPartner (int C_BPartner_ID)
	{
		log.info("C_BPartner_ID=" + C_BPartner_ID);
		
		//  New bpartner
		if (C_BPartner_ID == 0)
		{
			m_partner = null;
			m_pLocation = null;
			m_user = null;
			return true;
		}

		m_partner = new MBPartner (Env.getCtx(), C_BPartner_ID, null);
		
		if (m_partner.get_ID() == 0)
		{
			FDialog.error(m_WindowNo, this, "BPartnerNotFound");
			return false;
		}

		//	BPartner - Load values
		fValue.setText(m_partner.getValue());
		
		KeyNamePair keynamepair = getGreeting(m_partner.getC_Greeting_ID());
		
		for (int i = 0; i < fGreetingBP.getItemCount(); i++)
		{
			ListItem listitem = fGreetingBP.getItemAtIndex(i);
			KeyNamePair compare = (KeyNamePair)listitem.getValue();
			
			if (compare == keynamepair)
			{
				fGreetingBP.setSelectedIndex(i);
				break;
			}
		}
		
		fName.setText(m_partner.getName());
		fName2.setText(m_partner.getName2());

		//	Contact - Load values
		m_pLocation = m_partner.getLocation(
			Env.getContextAsInt(Env.getCtx(), m_WindowNo, "C_BPartner_Location_ID"));
		
		if (m_pLocation != null)
		{
			int location = m_pLocation.getC_Location_ID();
			fAddress.setValue (new Integer(location));
			
			fPhone.setText(m_pLocation.getPhone());
			fPhone2.setText(m_pLocation.getPhone2());
			fFax.setText(m_pLocation.getFax());
		}
		//	User - Load values
		m_user = m_partner.getContact(
			Env.getContextAsInt(Env.getCtx(), m_WindowNo, "AD_User_ID"));
		
		if (m_user != null)
		{
			keynamepair = getGreeting(m_user.getC_Greeting_ID());
			
			for (int i = 0; i < fGreetingC.getItemCount(); i++)
			{
				ListItem listitem = fGreetingC.getItemAtIndex(i);
				KeyNamePair compare = (KeyNamePair)listitem.getValue();
				
				if (compare == keynamepair)
				{
					fGreetingC.setSelectedIndex(i);
					break;
				}
			}

			fContact.setText(m_user.getName());
			fTitle.setText(m_user.getTitle());
			fEMail.setText(m_user.getEMail());
			
			fPhone.setText(m_user.getPhone());
			fPhone2.setText(m_user.getPhone2());
			fFax.setText(m_user.getFax());
		}
		return true;
	}	//	loadBPartner

	/**
	 *	Save.
	 *	Checks mandatory fields and saves Partner, Contact and Location
	 * 	@return true if saved
	 */
	
	private boolean actionSave()
	{
		log.info("");

		//	Check Mandatory fields
		if (fName.getText().equals(""))
		{
			throw new WrongValueException(fName, Msg.translate(Env.getCtx(), "FillMandatory"));
		}
			
		if (fAddress.getC_Location_ID() == 0)
		{
			throw new WrongValueException(fAddress.getComponent(), Msg.translate(Env.getCtx(), "FillMandatory"));
		}

		//	***** Business Partner *****
		
		if (m_partner == null)
		{
			int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
			m_partner = MBPartner.getTemplate(Env.getCtx(), AD_Client_ID);
			m_partner.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx())); // Elaine 2009/07/03
			boolean isSOTrx = !"N".equals(Env.getContext(Env.getCtx(), m_WindowNo, "IsSOTrx"));
			m_partner.setIsCustomer (isSOTrx);
			m_partner.setIsVendor (!isSOTrx);
		}
		
		//	Check Value
		
		String value = fValue.getText();
		
		if (value == null || value.length() == 0)
		{
			//	get Table Document No
			final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
			value = documentNoFactory.forTableName(I_C_BPartner.Table_Name, Env.getAD_Client_ID(Env.getCtx()), -1) // AD_Org_ID=N/A
					.build();
			fValue.setText(value);
		}
		
		m_partner.setValue(fValue.getText());
		
		m_partner.setName(fName.getText());
		m_partner.setName2(fName2.getText());
		
		ListItem listitem = fGreetingBP.getSelectedItem();
		KeyNamePair p = listitem != null ? (KeyNamePair)listitem.getValue() : null;
		
		if (p != null && p.getKey() > 0)
			m_partner.setC_Greeting_ID(p.getKey());
		else
			m_partner.setC_Greeting_ID(0);
		
		if (m_partner.save())
			log.debug("C_BPartner_ID=" + m_partner.getC_BPartner_ID());
		else
			FDialog.error(m_WindowNo, this, "BPartnerNotSaved");
		
		//	***** Business Partner - Location *****
		
		if (m_pLocation == null)
			m_pLocation = new MBPartnerLocation(m_partner);
		
		m_pLocation.setC_Location_ID(fAddress.getC_Location_ID());

		m_pLocation.setPhone(fPhone.getText());
		m_pLocation.setPhone2(fPhone2.getText());
		m_pLocation.setFax(fFax.getText());
		
		if (m_pLocation.save())
			log.debug("C_BPartner_Location_ID=" + m_pLocation.getC_BPartner_Location_ID());
		else
			FDialog.error(m_WindowNo, this, "BPartnerNotSaved", Msg.translate(Env.getCtx(), "C_BPartner_Location_ID"));
			
		//	***** Business Partner - User *****
		
		String contact = fContact.getText();
		String email = fEMail.getText();
		
		if (m_user == null && (contact.length() > 0 || email.length() > 0))
			m_user = new MUser (m_partner);
		
		if (m_user != null)
		{
			if (contact.length() == 0)
				contact = fName.getText();
		
			m_user.setName(contact);
			m_user.setEMail(email);
			m_user.setTitle(fTitle.getText());
			
			listitem = fGreetingC.getSelectedItem();
			p = listitem != null ? (KeyNamePair)listitem.getValue() : null;
			
			if (p != null && p.getKey() > 0)
				m_user.setC_Greeting_ID(p.getKey());
			else
				m_user.setC_Greeting_ID(0);
			
			m_user.setPhone(fPhone.getText());
			m_user.setPhone2(fPhone2.getText());
			m_user.setFax(fFax.getText());
			
			if (m_user.save())
				log.debug("AD_User_ID=" + m_user.getAD_User_ID());
			else
				FDialog.error(m_WindowNo, this, "BPartnerNotSaved", Msg.translate(Env.getCtx(), "AD_User_ID"));
		}
		return true;
	}	//	actionSave

	/**
	 *	Returns BPartner ID
	 *	@return C_BPartner_ID (0 = not saved)
	 */
	
	public int getC_BPartner_ID()
	{
		if (m_partner == null)
			return 0;
		
		return m_partner.getC_BPartner_ID();
	}	//	getBPartner_ID

	public void onEvent(Event e) throws Exception 
	{
		if (m_readOnly)
			this.detach();
		
		//	copy value
		
		else if (e.getTarget() == fValue)
		{
			if (fName.getText() == null || fName.getText().length() == 0)
				fName.setText(fValue.getText());
		}
		else if (e.getTarget() == fName)
		{
			if (fContact.getText() == null || fContact.getText().length() == 0)
				fContact.setText(fName.getText());
		}
		
		//	OK pressed
		else if ((e.getTarget() == confirmPanel.getButton("Ok")) && actionSave())
			this.detach();
		
		//	Cancel pressed
		else if (e.getTarget() == confirmPanel.getButton("Cancel"))
			this.detach();
		
	}

	public void valueChange(ValueChangeEvent evt)
	{
		
	}
}
