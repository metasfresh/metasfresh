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

/** Generated Interface for AD_PrintForm
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_PrintForm 
{

    /** TableName=AD_PrintForm */
    public static final String Table_Name = "AD_PrintForm";

    /** AD_Table_ID=454 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(6);

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

    /** Column name AD_PrintForm_ID */
    public static final String COLUMNNAME_AD_PrintForm_ID = "AD_PrintForm_ID";

	/** Set Print Form.
	  * Form
	  */
	public void setAD_PrintForm_ID (int AD_PrintForm_ID);

	/** Get Print Form.
	  * Form
	  */
	public int getAD_PrintForm_ID();

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

    /** Column name Distrib_Order_MailText_ID */
    public static final String COLUMNNAME_Distrib_Order_MailText_ID = "Distrib_Order_MailText_ID";

	/** Set Distribution Order Mail Text.
	  * Email text used for sending Distribution Order
	  */
	public void setDistrib_Order_MailText_ID (int Distrib_Order_MailText_ID);

	/** Get Distribution Order Mail Text.
	  * Email text used for sending Distribution Order
	  */
	public int getDistrib_Order_MailText_ID();

	public I_R_MailText getDistrib_Order_MailText() throws RuntimeException;

    /** Column name Distrib_Order_PrintFormat_ID */
    public static final String COLUMNNAME_Distrib_Order_PrintFormat_ID = "Distrib_Order_PrintFormat_ID";

	/** Set Distribution Order Print Format.
	  * Print Format for printing Distribution Order
	  */
	public void setDistrib_Order_PrintFormat_ID (int Distrib_Order_PrintFormat_ID);

	/** Get Distribution Order Print Format.
	  * Print Format for printing Distribution Order
	  */
	public int getDistrib_Order_PrintFormat_ID();

	public I_AD_PrintFormat getDistrib_Order_PrintFormat() throws RuntimeException;

    /** Column name Invoice_MailText_ID */
    public static final String COLUMNNAME_Invoice_MailText_ID = "Invoice_MailText_ID";

	/** Set Invoice Mail Text.
	  * Email text used for sending invoices
	  */
	public void setInvoice_MailText_ID (int Invoice_MailText_ID);

	/** Get Invoice Mail Text.
	  * Email text used for sending invoices
	  */
	public int getInvoice_MailText_ID();

	public I_R_MailText getInvoice_MailText() throws RuntimeException;

    /** Column name Invoice_PrintFormat_ID */
    public static final String COLUMNNAME_Invoice_PrintFormat_ID = "Invoice_PrintFormat_ID";

	/** Set Invoice Print Format.
	  * Print Format for printing Invoices
	  */
	public void setInvoice_PrintFormat_ID (int Invoice_PrintFormat_ID);

	/** Get Invoice Print Format.
	  * Print Format for printing Invoices
	  */
	public int getInvoice_PrintFormat_ID();

	public I_AD_PrintFormat getInvoice_PrintFormat() throws RuntimeException;

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

    /** Column name Manuf_Order_MailText_ID */
    public static final String COLUMNNAME_Manuf_Order_MailText_ID = "Manuf_Order_MailText_ID";

	/** Set Manufacturing Order Mail Text.
	  * Email text used for sending Manufacturing Order
	  */
	public void setManuf_Order_MailText_ID (int Manuf_Order_MailText_ID);

	/** Get Manufacturing Order Mail Text.
	  * Email text used for sending Manufacturing Order
	  */
	public int getManuf_Order_MailText_ID();

	public I_R_MailText getManuf_Order_MailText() throws RuntimeException;

    /** Column name Manuf_Order_PrintFormat_ID */
    public static final String COLUMNNAME_Manuf_Order_PrintFormat_ID = "Manuf_Order_PrintFormat_ID";

	/** Set Manufacturing Order Print Format.
	  * Print Format for printing Manufacturing Order
	  */
	public void setManuf_Order_PrintFormat_ID (int Manuf_Order_PrintFormat_ID);

	/** Get Manufacturing Order Print Format.
	  * Print Format for printing Manufacturing Order
	  */
	public int getManuf_Order_PrintFormat_ID();

	public I_AD_PrintFormat getManuf_Order_PrintFormat() throws RuntimeException;

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

    /** Column name Order_MailText_ID */
    public static final String COLUMNNAME_Order_MailText_ID = "Order_MailText_ID";

	/** Set Order Mail Text.
	  * Email text used for sending order acknowledgements or quotations
	  */
	public void setOrder_MailText_ID (int Order_MailText_ID);

	/** Get Order Mail Text.
	  * Email text used for sending order acknowledgements or quotations
	  */
	public int getOrder_MailText_ID();

	public I_R_MailText getOrder_MailText() throws RuntimeException;

    /** Column name Order_PrintFormat_ID */
    public static final String COLUMNNAME_Order_PrintFormat_ID = "Order_PrintFormat_ID";

	/** Set Order Print Format.
	  * Print Format for Orders, Quotes, Offers
	  */
	public void setOrder_PrintFormat_ID (int Order_PrintFormat_ID);

	/** Get Order Print Format.
	  * Print Format for Orders, Quotes, Offers
	  */
	public int getOrder_PrintFormat_ID();

	public I_AD_PrintFormat getOrder_PrintFormat() throws RuntimeException;

    /** Column name Project_MailText_ID */
    public static final String COLUMNNAME_Project_MailText_ID = "Project_MailText_ID";

	/** Set Project Mail Text.
	  * Standard text for Project EMails
	  */
	public void setProject_MailText_ID (int Project_MailText_ID);

	/** Get Project Mail Text.
	  * Standard text for Project EMails
	  */
	public int getProject_MailText_ID();

	public I_R_MailText getProject_MailText() throws RuntimeException;

    /** Column name Project_PrintFormat_ID */
    public static final String COLUMNNAME_Project_PrintFormat_ID = "Project_PrintFormat_ID";

	/** Set Project Print Format.
	  * Standard Project Print Format
	  */
	public void setProject_PrintFormat_ID (int Project_PrintFormat_ID);

	/** Get Project Print Format.
	  * Standard Project Print Format
	  */
	public int getProject_PrintFormat_ID();

	public I_AD_PrintFormat getProject_PrintFormat() throws RuntimeException;

    /** Column name Remittance_MailText_ID */
    public static final String COLUMNNAME_Remittance_MailText_ID = "Remittance_MailText_ID";

	/** Set Remittance Mail Text.
	  * Email text used for sending payment remittances
	  */
	public void setRemittance_MailText_ID (int Remittance_MailText_ID);

	/** Get Remittance Mail Text.
	  * Email text used for sending payment remittances
	  */
	public int getRemittance_MailText_ID();

	public I_R_MailText getRemittance_MailText() throws RuntimeException;

    /** Column name Remittance_PrintFormat_ID */
    public static final String COLUMNNAME_Remittance_PrintFormat_ID = "Remittance_PrintFormat_ID";

	/** Set Remittance Print Format.
	  * Print Format for separate Remittances
	  */
	public void setRemittance_PrintFormat_ID (int Remittance_PrintFormat_ID);

	/** Get Remittance Print Format.
	  * Print Format for separate Remittances
	  */
	public int getRemittance_PrintFormat_ID();

	public I_AD_PrintFormat getRemittance_PrintFormat() throws RuntimeException;

    /** Column name Shipment_MailText_ID */
    public static final String COLUMNNAME_Shipment_MailText_ID = "Shipment_MailText_ID";

	/** Set Shipment Mail Text.
	  * Email text used for sending delivery notes
	  */
	public void setShipment_MailText_ID (int Shipment_MailText_ID);

	/** Get Shipment Mail Text.
	  * Email text used for sending delivery notes
	  */
	public int getShipment_MailText_ID();

	public I_R_MailText getShipment_MailText() throws RuntimeException;

    /** Column name Shipment_PrintFormat_ID */
    public static final String COLUMNNAME_Shipment_PrintFormat_ID = "Shipment_PrintFormat_ID";

	/** Set Shipment Print Format.
	  * Print Format for Shipments, Receipts, Pick Lists
	  */
	public void setShipment_PrintFormat_ID (int Shipment_PrintFormat_ID);

	/** Get Shipment Print Format.
	  * Print Format for Shipments, Receipts, Pick Lists
	  */
	public int getShipment_PrintFormat_ID();

	public I_AD_PrintFormat getShipment_PrintFormat() throws RuntimeException;

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
}
