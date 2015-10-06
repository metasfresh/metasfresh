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

/** Generated Interface for C_RfQResponse
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_RfQResponse 
{

    /** TableName=C_RfQResponse */
    public static final String Table_Name = "C_RfQResponse";

    /** AD_Table_ID=674 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

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

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public I_AD_User getAD_User() throws RuntimeException;

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name CheckComplete */
    public static final String COLUMNNAME_CheckComplete = "CheckComplete";

	/** Set Check Complete	  */
	public void setCheckComplete (String CheckComplete);

	/** Get Check Complete	  */
	public String getCheckComplete();

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public I_C_Order getC_Order() throws RuntimeException;

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

    /** Column name C_RfQ_ID */
    public static final String COLUMNNAME_C_RfQ_ID = "C_RfQ_ID";

	/** Set RfQ.
	  * Request for Quotation
	  */
	public void setC_RfQ_ID (int C_RfQ_ID);

	/** Get RfQ.
	  * Request for Quotation
	  */
	public int getC_RfQ_ID();

	public I_C_RfQ getC_RfQ() throws RuntimeException;

    /** Column name C_RfQResponse_ID */
    public static final String COLUMNNAME_C_RfQResponse_ID = "C_RfQResponse_ID";

	/** Set RfQ Response.
	  * Request for Quotation Response from a potential Vendor
	  */
	public void setC_RfQResponse_ID (int C_RfQResponse_ID);

	/** Get RfQ Response.
	  * Request for Quotation Response from a potential Vendor
	  */
	public int getC_RfQResponse_ID();

    /** Column name DateInvited */
    public static final String COLUMNNAME_DateInvited = "DateInvited";

	/** Set Invited.
	  * Date when (last) invitation was sent
	  */
	public void setDateInvited (Timestamp DateInvited);

	/** Get Invited.
	  * Date when (last) invitation was sent
	  */
	public Timestamp getDateInvited();

    /** Column name DateResponse */
    public static final String COLUMNNAME_DateResponse = "DateResponse";

	/** Set Response Date.
	  * Date of the Response
	  */
	public void setDateResponse (Timestamp DateResponse);

	/** Get Response Date.
	  * Date of the Response
	  */
	public Timestamp getDateResponse();

    /** Column name DateWorkComplete */
    public static final String COLUMNNAME_DateWorkComplete = "DateWorkComplete";

	/** Set Work Complete.
	  * Date when work is (planned to be) complete
	  */
	public void setDateWorkComplete (Timestamp DateWorkComplete);

	/** Get Work Complete.
	  * Date when work is (planned to be) complete
	  */
	public Timestamp getDateWorkComplete();

    /** Column name DateWorkStart */
    public static final String COLUMNNAME_DateWorkStart = "DateWorkStart";

	/** Set Work Start.
	  * Date when work is (planned to be) started
	  */
	public void setDateWorkStart (Timestamp DateWorkStart);

	/** Get Work Start.
	  * Date when work is (planned to be) started
	  */
	public Timestamp getDateWorkStart();

    /** Column name DeliveryDays */
    public static final String COLUMNNAME_DeliveryDays = "DeliveryDays";

	/** Set Delivery Days.
	  * Number of Days (planned) until Delivery
	  */
	public void setDeliveryDays (int DeliveryDays);

	/** Get Delivery Days.
	  * Number of Days (planned) until Delivery
	  */
	public int getDeliveryDays();

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

    /** Column name IsComplete */
    public static final String COLUMNNAME_IsComplete = "IsComplete";

	/** Set Complete.
	  * It is complete
	  */
	public void setIsComplete (boolean IsComplete);

	/** Get Complete.
	  * It is complete
	  */
	public boolean isComplete();

    /** Column name IsSelectedWinner */
    public static final String COLUMNNAME_IsSelectedWinner = "IsSelectedWinner";

	/** Set Selected Winner.
	  * The resonse is the selected winner
	  */
	public void setIsSelectedWinner (boolean IsSelectedWinner);

	/** Get Selected Winner.
	  * The resonse is the selected winner
	  */
	public boolean isSelectedWinner();

    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/** Set Self-Service.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public void setIsSelfService (boolean IsSelfService);

	/** Get Self-Service.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService();

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

    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

	/** Set Price.
	  * Price
	  */
	public void setPrice (BigDecimal Price);

	/** Get Price.
	  * Price
	  */
	public BigDecimal getPrice();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name Ranking */
    public static final String COLUMNNAME_Ranking = "Ranking";

	/** Set Ranking.
	  * Relative Rank Number
	  */
	public void setRanking (int Ranking);

	/** Get Ranking.
	  * Relative Rank Number
	  */
	public int getRanking();

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
