/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for W_Store
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_W_Store extends PO implements I_W_Store, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_W_Store (Properties ctx, int W_Store_ID, String trxName)
    {
      super (ctx, W_Store_ID, trxName);
      /** if (W_Store_ID == 0)
        {
			setC_PaymentTerm_ID (0);
			setIsDefault (false);
			setIsMenuAssets (true);
// Y
			setIsMenuContact (true);
// Y
			setIsMenuInterests (true);
// Y
			setIsMenuInvoices (true);
// Y
			setIsMenuOrders (true);
// Y
			setIsMenuPayments (true);
// Y
			setIsMenuRegistrations (true);
// Y
			setIsMenuRequests (true);
// Y
			setIsMenuRfQs (true);
// Y
			setIsMenuShipments (true);
// Y
			setM_PriceList_ID (0);
			setM_Warehouse_ID (0);
			setName (null);
			setSalesRep_ID (0);
			setURL (null);
			setWebContext (null);
			setW_Store_ID (0);
        } */
    }

    /** Load Constructor */
    public X_W_Store (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_W_Store[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
    {
		return (I_C_PaymentTerm)MTable.get(getCtx(), I_C_PaymentTerm.Table_Name)
			.getPO(getC_PaymentTerm_ID(), get_TrxName());	}

	/** Set Payment Term.
		@param C_PaymentTerm_ID 
		The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Payment Term.
		@return The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set EMail Footer.
		@param EMailFooter 
		Footer added to EMails
	  */
	public void setEMailFooter (String EMailFooter)
	{
		set_Value (COLUMNNAME_EMailFooter, EMailFooter);
	}

	/** Get EMail Footer.
		@return Footer added to EMails
	  */
	public String getEMailFooter () 
	{
		return (String)get_Value(COLUMNNAME_EMailFooter);
	}

	/** Set EMail Header.
		@param EMailHeader 
		Header added to EMails
	  */
	public void setEMailHeader (String EMailHeader)
	{
		set_Value (COLUMNNAME_EMailHeader, EMailHeader);
	}

	/** Get EMail Header.
		@return Header added to EMails
	  */
	public String getEMailHeader () 
	{
		return (String)get_Value(COLUMNNAME_EMailHeader);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Menu Assets.
		@param IsMenuAssets 
		Show Menu Assets
	  */
	public void setIsMenuAssets (boolean IsMenuAssets)
	{
		set_Value (COLUMNNAME_IsMenuAssets, Boolean.valueOf(IsMenuAssets));
	}

	/** Get Menu Assets.
		@return Show Menu Assets
	  */
	public boolean isMenuAssets () 
	{
		Object oo = get_Value(COLUMNNAME_IsMenuAssets);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Menu Contact.
		@param IsMenuContact 
		Show Menu Contact
	  */
	public void setIsMenuContact (boolean IsMenuContact)
	{
		set_Value (COLUMNNAME_IsMenuContact, Boolean.valueOf(IsMenuContact));
	}

	/** Get Menu Contact.
		@return Show Menu Contact
	  */
	public boolean isMenuContact () 
	{
		Object oo = get_Value(COLUMNNAME_IsMenuContact);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Menu Interests.
		@param IsMenuInterests 
		Show Menu Interests
	  */
	public void setIsMenuInterests (boolean IsMenuInterests)
	{
		set_Value (COLUMNNAME_IsMenuInterests, Boolean.valueOf(IsMenuInterests));
	}

	/** Get Menu Interests.
		@return Show Menu Interests
	  */
	public boolean isMenuInterests () 
	{
		Object oo = get_Value(COLUMNNAME_IsMenuInterests);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Menu Invoices.
		@param IsMenuInvoices 
		Show Menu Invoices
	  */
	public void setIsMenuInvoices (boolean IsMenuInvoices)
	{
		set_Value (COLUMNNAME_IsMenuInvoices, Boolean.valueOf(IsMenuInvoices));
	}

	/** Get Menu Invoices.
		@return Show Menu Invoices
	  */
	public boolean isMenuInvoices () 
	{
		Object oo = get_Value(COLUMNNAME_IsMenuInvoices);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Menu Orders.
		@param IsMenuOrders 
		Show Menu Orders
	  */
	public void setIsMenuOrders (boolean IsMenuOrders)
	{
		set_Value (COLUMNNAME_IsMenuOrders, Boolean.valueOf(IsMenuOrders));
	}

	/** Get Menu Orders.
		@return Show Menu Orders
	  */
	public boolean isMenuOrders () 
	{
		Object oo = get_Value(COLUMNNAME_IsMenuOrders);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Menu Payments.
		@param IsMenuPayments 
		Show Menu Payments
	  */
	public void setIsMenuPayments (boolean IsMenuPayments)
	{
		set_Value (COLUMNNAME_IsMenuPayments, Boolean.valueOf(IsMenuPayments));
	}

	/** Get Menu Payments.
		@return Show Menu Payments
	  */
	public boolean isMenuPayments () 
	{
		Object oo = get_Value(COLUMNNAME_IsMenuPayments);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Menu Registrations.
		@param IsMenuRegistrations 
		Show Menu Registrations
	  */
	public void setIsMenuRegistrations (boolean IsMenuRegistrations)
	{
		set_Value (COLUMNNAME_IsMenuRegistrations, Boolean.valueOf(IsMenuRegistrations));
	}

	/** Get Menu Registrations.
		@return Show Menu Registrations
	  */
	public boolean isMenuRegistrations () 
	{
		Object oo = get_Value(COLUMNNAME_IsMenuRegistrations);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Menu Requests.
		@param IsMenuRequests 
		Show Menu Requests
	  */
	public void setIsMenuRequests (boolean IsMenuRequests)
	{
		set_Value (COLUMNNAME_IsMenuRequests, Boolean.valueOf(IsMenuRequests));
	}

	/** Get Menu Requests.
		@return Show Menu Requests
	  */
	public boolean isMenuRequests () 
	{
		Object oo = get_Value(COLUMNNAME_IsMenuRequests);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Menu RfQs.
		@param IsMenuRfQs 
		Show Menu RfQs
	  */
	public void setIsMenuRfQs (boolean IsMenuRfQs)
	{
		set_Value (COLUMNNAME_IsMenuRfQs, Boolean.valueOf(IsMenuRfQs));
	}

	/** Get Menu RfQs.
		@return Show Menu RfQs
	  */
	public boolean isMenuRfQs () 
	{
		Object oo = get_Value(COLUMNNAME_IsMenuRfQs);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Menu Shipments.
		@param IsMenuShipments 
		Show Menu Shipments
	  */
	public void setIsMenuShipments (boolean IsMenuShipments)
	{
		set_Value (COLUMNNAME_IsMenuShipments, Boolean.valueOf(IsMenuShipments));
	}

	/** Get Menu Shipments.
		@return Show Menu Shipments
	  */
	public boolean isMenuShipments () 
	{
		Object oo = get_Value(COLUMNNAME_IsMenuShipments);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (I_M_PriceList)MTable.get(getCtx(), I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (I_M_Warehouse)MTable.get(getCtx(), I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	public I_AD_User getSalesRep() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Stylesheet.
		@param Stylesheet 
		CSS (Stylesheet) used
	  */
	public void setStylesheet (String Stylesheet)
	{
		set_Value (COLUMNNAME_Stylesheet, Stylesheet);
	}

	/** Get Stylesheet.
		@return CSS (Stylesheet) used
	  */
	public String getStylesheet () 
	{
		return (String)get_Value(COLUMNNAME_Stylesheet);
	}

	/** Set URL.
		@param URL 
		Full URL address - e.g. http://www.adempiere.org
	  */
	public void setURL (String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	/** Get URL.
		@return Full URL address - e.g. http://www.adempiere.org
	  */
	public String getURL () 
	{
		return (String)get_Value(COLUMNNAME_URL);
	}

	/** Set Web Context.
		@param WebContext 
		Web Server Context - e.g. /wstore
	  */
	public void setWebContext (String WebContext)
	{
		set_Value (COLUMNNAME_WebContext, WebContext);
	}

	/** Get Web Context.
		@return Web Server Context - e.g. /wstore
	  */
	public String getWebContext () 
	{
		return (String)get_Value(COLUMNNAME_WebContext);
	}

	/** Set Web Store Info.
		@param WebInfo 
		Web Store Header Information
	  */
	public void setWebInfo (String WebInfo)
	{
		set_Value (COLUMNNAME_WebInfo, WebInfo);
	}

	/** Get Web Store Info.
		@return Web Store Header Information
	  */
	public String getWebInfo () 
	{
		return (String)get_Value(COLUMNNAME_WebInfo);
	}

	/** Set Web Order EMail.
		@param WebOrderEMail 
		EMail address to receive notifications when web orders were processed
	  */
	public void setWebOrderEMail (String WebOrderEMail)
	{
		set_Value (COLUMNNAME_WebOrderEMail, WebOrderEMail);
	}

	/** Get Web Order EMail.
		@return EMail address to receive notifications when web orders were processed
	  */
	public String getWebOrderEMail () 
	{
		return (String)get_Value(COLUMNNAME_WebOrderEMail);
	}

	/** Set Web Parameter 1.
		@param WebParam1 
		Web Site Parameter 1 (default: header image)
	  */
	public void setWebParam1 (String WebParam1)
	{
		set_Value (COLUMNNAME_WebParam1, WebParam1);
	}

	/** Get Web Parameter 1.
		@return Web Site Parameter 1 (default: header image)
	  */
	public String getWebParam1 () 
	{
		return (String)get_Value(COLUMNNAME_WebParam1);
	}

	/** Set Web Parameter 2.
		@param WebParam2 
		Web Site Parameter 2 (default index page)
	  */
	public void setWebParam2 (String WebParam2)
	{
		set_Value (COLUMNNAME_WebParam2, WebParam2);
	}

	/** Get Web Parameter 2.
		@return Web Site Parameter 2 (default index page)
	  */
	public String getWebParam2 () 
	{
		return (String)get_Value(COLUMNNAME_WebParam2);
	}

	/** Set Web Parameter 3.
		@param WebParam3 
		Web Site Parameter 3 (default left - menu)
	  */
	public void setWebParam3 (String WebParam3)
	{
		set_Value (COLUMNNAME_WebParam3, WebParam3);
	}

	/** Get Web Parameter 3.
		@return Web Site Parameter 3 (default left - menu)
	  */
	public String getWebParam3 () 
	{
		return (String)get_Value(COLUMNNAME_WebParam3);
	}

	/** Set Web Parameter 4.
		@param WebParam4 
		Web Site Parameter 4 (default footer left)
	  */
	public void setWebParam4 (String WebParam4)
	{
		set_Value (COLUMNNAME_WebParam4, WebParam4);
	}

	/** Get Web Parameter 4.
		@return Web Site Parameter 4 (default footer left)
	  */
	public String getWebParam4 () 
	{
		return (String)get_Value(COLUMNNAME_WebParam4);
	}

	/** Set Web Parameter 5.
		@param WebParam5 
		Web Site Parameter 5 (default footer center)
	  */
	public void setWebParam5 (String WebParam5)
	{
		set_Value (COLUMNNAME_WebParam5, WebParam5);
	}

	/** Get Web Parameter 5.
		@return Web Site Parameter 5 (default footer center)
	  */
	public String getWebParam5 () 
	{
		return (String)get_Value(COLUMNNAME_WebParam5);
	}

	/** Set Web Parameter 6.
		@param WebParam6 
		Web Site Parameter 6 (default footer right)
	  */
	public void setWebParam6 (String WebParam6)
	{
		set_Value (COLUMNNAME_WebParam6, WebParam6);
	}

	/** Get Web Parameter 6.
		@return Web Site Parameter 6 (default footer right)
	  */
	public String getWebParam6 () 
	{
		return (String)get_Value(COLUMNNAME_WebParam6);
	}

	/** Set Web Store EMail.
		@param WStoreEMail 
		EMail address used as the sender (From)
	  */
	public void setWStoreEMail (String WStoreEMail)
	{
		set_Value (COLUMNNAME_WStoreEMail, WStoreEMail);
	}

	/** Get Web Store EMail.
		@return EMail address used as the sender (From)
	  */
	public String getWStoreEMail () 
	{
		return (String)get_Value(COLUMNNAME_WStoreEMail);
	}

	/** Set Web Store.
		@param W_Store_ID 
		A Web Store of the Client
	  */
	public void setW_Store_ID (int W_Store_ID)
	{
		if (W_Store_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_W_Store_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_W_Store_ID, Integer.valueOf(W_Store_ID));
	}

	/** Get Web Store.
		@return A Web Store of the Client
	  */
	public int getW_Store_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_Store_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set WebStore User.
		@param WStoreUser 
		User ID of the Web Store EMail address
	  */
	public void setWStoreUser (String WStoreUser)
	{
		set_Value (COLUMNNAME_WStoreUser, WStoreUser);
	}

	/** Get WebStore User.
		@return User ID of the Web Store EMail address
	  */
	public String getWStoreUser () 
	{
		return (String)get_Value(COLUMNNAME_WStoreUser);
	}

	/** Set WebStore Password.
		@param WStoreUserPW 
		Password of the Web Store EMail address
	  */
	public void setWStoreUserPW (String WStoreUserPW)
	{
		set_Value (COLUMNNAME_WStoreUserPW, WStoreUserPW);
	}

	/** Get WebStore Password.
		@return Password of the Web Store EMail address
	  */
	public String getWStoreUserPW () 
	{
		return (String)get_Value(COLUMNNAME_WStoreUserPW);
	}
}