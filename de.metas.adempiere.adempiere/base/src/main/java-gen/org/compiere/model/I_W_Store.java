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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for W_Store
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_W_Store 
{

    /** TableName=W_Store */
    public static final String Table_Name = "W_Store";

    /** AD_Table_ID=778 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/** Set Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/** Get Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID();

	public I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name EMailFooter */
    public static final String COLUMNNAME_EMailFooter = "EMailFooter";

	/** Set EMail Footer.
	  * Footer added to EMails
	  */
	public void setEMailFooter (String EMailFooter);

	/** Get EMail Footer.
	  * Footer added to EMails
	  */
	public String getEMailFooter();

    /** Column name EMailHeader */
    public static final String COLUMNNAME_EMailHeader = "EMailHeader";

	/** Set EMail Header.
	  * Header added to EMails
	  */
	public void setEMailHeader (String EMailHeader);

	/** Get EMail Header.
	  * Header added to EMails
	  */
	public String getEMailHeader();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/** Set Default.
	  * Default value
	  */
	public void setIsDefault (boolean IsDefault);

	/** Get Default.
	  * Default value
	  */
	public boolean isDefault();

    /** Column name IsMenuAssets */
    public static final String COLUMNNAME_IsMenuAssets = "IsMenuAssets";

	/** Set Menu Assets.
	  * Show Menu Assets
	  */
	public void setIsMenuAssets (boolean IsMenuAssets);

	/** Get Menu Assets.
	  * Show Menu Assets
	  */
	public boolean isMenuAssets();

    /** Column name IsMenuContact */
    public static final String COLUMNNAME_IsMenuContact = "IsMenuContact";

	/** Set Menu Contact.
	  * Show Menu Contact
	  */
	public void setIsMenuContact (boolean IsMenuContact);

	/** Get Menu Contact.
	  * Show Menu Contact
	  */
	public boolean isMenuContact();

    /** Column name IsMenuInterests */
    public static final String COLUMNNAME_IsMenuInterests = "IsMenuInterests";

	/** Set Menu Interests.
	  * Show Menu Interests
	  */
	public void setIsMenuInterests (boolean IsMenuInterests);

	/** Get Menu Interests.
	  * Show Menu Interests
	  */
	public boolean isMenuInterests();

    /** Column name IsMenuInvoices */
    public static final String COLUMNNAME_IsMenuInvoices = "IsMenuInvoices";

	/** Set Menu Invoices.
	  * Show Menu Invoices
	  */
	public void setIsMenuInvoices (boolean IsMenuInvoices);

	/** Get Menu Invoices.
	  * Show Menu Invoices
	  */
	public boolean isMenuInvoices();

    /** Column name IsMenuOrders */
    public static final String COLUMNNAME_IsMenuOrders = "IsMenuOrders";

	/** Set Menu Orders.
	  * Show Menu Orders
	  */
	public void setIsMenuOrders (boolean IsMenuOrders);

	/** Get Menu Orders.
	  * Show Menu Orders
	  */
	public boolean isMenuOrders();

    /** Column name IsMenuPayments */
    public static final String COLUMNNAME_IsMenuPayments = "IsMenuPayments";

	/** Set Menu Payments.
	  * Show Menu Payments
	  */
	public void setIsMenuPayments (boolean IsMenuPayments);

	/** Get Menu Payments.
	  * Show Menu Payments
	  */
	public boolean isMenuPayments();

    /** Column name IsMenuRegistrations */
    public static final String COLUMNNAME_IsMenuRegistrations = "IsMenuRegistrations";

	/** Set Menu Registrations.
	  * Show Menu Registrations
	  */
	public void setIsMenuRegistrations (boolean IsMenuRegistrations);

	/** Get Menu Registrations.
	  * Show Menu Registrations
	  */
	public boolean isMenuRegistrations();

    /** Column name IsMenuRequests */
    public static final String COLUMNNAME_IsMenuRequests = "IsMenuRequests";

	/** Set Menu Requests.
	  * Show Menu Requests
	  */
	public void setIsMenuRequests (boolean IsMenuRequests);

	/** Get Menu Requests.
	  * Show Menu Requests
	  */
	public boolean isMenuRequests();

    /** Column name IsMenuRfQs */
    public static final String COLUMNNAME_IsMenuRfQs = "IsMenuRfQs";

	/** Set Menu RfQs.
	  * Show Menu RfQs
	  */
	public void setIsMenuRfQs (boolean IsMenuRfQs);

	/** Get Menu RfQs.
	  * Show Menu RfQs
	  */
	public boolean isMenuRfQs();

    /** Column name IsMenuShipments */
    public static final String COLUMNNAME_IsMenuShipments = "IsMenuShipments";

	/** Set Menu Shipments.
	  * Show Menu Shipments
	  */
	public void setIsMenuShipments (boolean IsMenuShipments);

	/** Get Menu Shipments.
	  * Show Menu Shipments
	  */
	public boolean isMenuShipments();

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Price List.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/** Get Price List.
	  * Unique identifier of a Price List
	  */
	public int getM_PriceList_ID();

	public I_M_PriceList getM_PriceList() throws RuntimeException;

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public I_M_Warehouse getM_Warehouse() throws RuntimeException;

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public I_AD_User getSalesRep() throws RuntimeException;

    /** Column name Stylesheet */
    public static final String COLUMNNAME_Stylesheet = "Stylesheet";

	/** Set Stylesheet.
	  * CSS (Stylesheet) used
	  */
	public void setStylesheet (String Stylesheet);

	/** Get Stylesheet.
	  * CSS (Stylesheet) used
	  */
	public String getStylesheet();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name URL */
    public static final String COLUMNNAME_URL = "URL";

	/** Set URL.
	  * Full URL address - e.g. http://www.adempiere.org
	  */
	public void setURL (String URL);

	/** Get URL.
	  * Full URL address - e.g. http://www.adempiere.org
	  */
	public String getURL();

    /** Column name WebContext */
    public static final String COLUMNNAME_WebContext = "WebContext";

	/** Set Web Context.
	  * Web Server Context - e.g. /wstore
	  */
	public void setWebContext (String WebContext);

	/** Get Web Context.
	  * Web Server Context - e.g. /wstore
	  */
	public String getWebContext();

    /** Column name WebInfo */
    public static final String COLUMNNAME_WebInfo = "WebInfo";

	/** Set Web Store Info.
	  * Web Store Header Information
	  */
	public void setWebInfo (String WebInfo);

	/** Get Web Store Info.
	  * Web Store Header Information
	  */
	public String getWebInfo();

    /** Column name WebOrderEMail */
    public static final String COLUMNNAME_WebOrderEMail = "WebOrderEMail";

	/** Set Web Order EMail.
	  * EMail address to receive notifications when web orders were processed
	  */
	public void setWebOrderEMail (String WebOrderEMail);

	/** Get Web Order EMail.
	  * EMail address to receive notifications when web orders were processed
	  */
	public String getWebOrderEMail();

    /** Column name WebParam1 */
    public static final String COLUMNNAME_WebParam1 = "WebParam1";

	/** Set Web Parameter 1.
	  * Web Site Parameter 1 (default: header image)
	  */
	public void setWebParam1 (String WebParam1);

	/** Get Web Parameter 1.
	  * Web Site Parameter 1 (default: header image)
	  */
	public String getWebParam1();

    /** Column name WebParam2 */
    public static final String COLUMNNAME_WebParam2 = "WebParam2";

	/** Set Web Parameter 2.
	  * Web Site Parameter 2 (default index page)
	  */
	public void setWebParam2 (String WebParam2);

	/** Get Web Parameter 2.
	  * Web Site Parameter 2 (default index page)
	  */
	public String getWebParam2();

    /** Column name WebParam3 */
    public static final String COLUMNNAME_WebParam3 = "WebParam3";

	/** Set Web Parameter 3.
	  * Web Site Parameter 3 (default left - menu)
	  */
	public void setWebParam3 (String WebParam3);

	/** Get Web Parameter 3.
	  * Web Site Parameter 3 (default left - menu)
	  */
	public String getWebParam3();

    /** Column name WebParam4 */
    public static final String COLUMNNAME_WebParam4 = "WebParam4";

	/** Set Web Parameter 4.
	  * Web Site Parameter 4 (default footer left)
	  */
	public void setWebParam4 (String WebParam4);

	/** Get Web Parameter 4.
	  * Web Site Parameter 4 (default footer left)
	  */
	public String getWebParam4();

    /** Column name WebParam5 */
    public static final String COLUMNNAME_WebParam5 = "WebParam5";

	/** Set Web Parameter 5.
	  * Web Site Parameter 5 (default footer center)
	  */
	public void setWebParam5 (String WebParam5);

	/** Get Web Parameter 5.
	  * Web Site Parameter 5 (default footer center)
	  */
	public String getWebParam5();

    /** Column name WebParam6 */
    public static final String COLUMNNAME_WebParam6 = "WebParam6";

	/** Set Web Parameter 6.
	  * Web Site Parameter 6 (default footer right)
	  */
	public void setWebParam6 (String WebParam6);

	/** Get Web Parameter 6.
	  * Web Site Parameter 6 (default footer right)
	  */
	public String getWebParam6();

    /** Column name WStoreEMail */
    public static final String COLUMNNAME_WStoreEMail = "WStoreEMail";

	/** Set Web Store EMail.
	  * EMail address used as the sender (From)
	  */
	public void setWStoreEMail (String WStoreEMail);

	/** Get Web Store EMail.
	  * EMail address used as the sender (From)
	  */
	public String getWStoreEMail();

    /** Column name W_Store_ID */
    public static final String COLUMNNAME_W_Store_ID = "W_Store_ID";

	/** Set Web Store.
	  * A Web Store of the Client
	  */
	public void setW_Store_ID (int W_Store_ID);

	/** Get Web Store.
	  * A Web Store of the Client
	  */
	public int getW_Store_ID();

    /** Column name WStoreUser */
    public static final String COLUMNNAME_WStoreUser = "WStoreUser";

	/** Set WebStore User.
	  * User ID of the Web Store EMail address
	  */
	public void setWStoreUser (String WStoreUser);

	/** Get WebStore User.
	  * User ID of the Web Store EMail address
	  */
	public String getWStoreUser();

    /** Column name WStoreUserPW */
    public static final String COLUMNNAME_WStoreUserPW = "WStoreUserPW";

	/** Set WebStore Password.
	  * Password of the Web Store EMail address
	  */
	public void setWStoreUserPW (String WStoreUserPW);

	/** Get WebStore Password.
	  * Password of the Web Store EMail address
	  */
	public String getWStoreUserPW();
}
