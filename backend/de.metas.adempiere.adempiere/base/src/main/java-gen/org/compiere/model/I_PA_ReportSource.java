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

/** Generated Interface for PA_ReportSource
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_PA_ReportSource 
{

    /** TableName=PA_ReportSource */
    public static final String Table_Name = "PA_ReportSource";

    /** AD_Table_ID=450 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

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

    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/** Set Trx Organization.
	  * Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/** Get Trx Organization.
	  * Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID();

    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/** Set Activity.
	  * Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID);

	/** Get Activity.
	  * Business Activity
	  */
	public int getC_Activity_ID();

	public I_C_Activity getC_Activity() throws RuntimeException;

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

    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/** Set Campaign.
	  * Marketing Campaign
	  */
	public void setC_Campaign_ID (int C_Campaign_ID);

	/** Get Campaign.
	  * Marketing Campaign
	  */
	public int getC_Campaign_ID();

	public I_C_Campaign getC_Campaign() throws RuntimeException;

    /** Column name C_ElementValue_ID */
    public static final String COLUMNNAME_C_ElementValue_ID = "C_ElementValue_ID";

	/** Set Account Element.
	  * Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID);

	/** Get Account Element.
	  * Account Element
	  */
	public int getC_ElementValue_ID();

	public I_C_ElementValue getC_ElementValue() throws RuntimeException;

    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/** Set Address.
	  * Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID);

	/** Get Address.
	  * Location or Address
	  */
	public int getC_Location_ID();

	public I_C_Location getC_Location() throws RuntimeException;

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/** Set Project.
	  * Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID);

	/** Get Project.
	  * Financial Project
	  */
	public int getC_Project_ID();

	public I_C_Project getC_Project() throws RuntimeException;

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

    /** Column name C_SalesRegion_ID */
    public static final String COLUMNNAME_C_SalesRegion_ID = "C_SalesRegion_ID";

	/** Set Sales Region.
	  * Sales coverage region
	  */
	public void setC_SalesRegion_ID (int C_SalesRegion_ID);

	/** Get Sales Region.
	  * Sales coverage region
	  */
	public int getC_SalesRegion_ID();

	public I_C_SalesRegion getC_SalesRegion() throws RuntimeException;

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

    /** Column name ElementType */
    public static final String COLUMNNAME_ElementType = "ElementType";

	/** Set Type.
	  * Element Type (account or user defined)
	  */
	public void setElementType (String ElementType);

	/** Get Type.
	  * Element Type (account or user defined)
	  */
	public String getElementType();

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

    /** Column name IsIncludeNullsActivity */
    public static final String COLUMNNAME_IsIncludeNullsActivity = "IsIncludeNullsActivity";

	/** Set Include Nulls in Activity.
	  * Include nulls in the selection of the activity
	  */
	public void setIsIncludeNullsActivity (boolean IsIncludeNullsActivity);

	/** Get Include Nulls in Activity.
	  * Include nulls in the selection of the activity
	  */
	public boolean isIncludeNullsActivity();

    /** Column name IsIncludeNullsBPartner */
    public static final String COLUMNNAME_IsIncludeNullsBPartner = "IsIncludeNullsBPartner";

	/** Set Include Nulls in BPartner.
	  * Include nulls in the selection of the business partner
	  */
	public void setIsIncludeNullsBPartner (boolean IsIncludeNullsBPartner);

	/** Get Include Nulls in BPartner.
	  * Include nulls in the selection of the business partner
	  */
	public boolean isIncludeNullsBPartner();

    /** Column name IsIncludeNullsCampaign */
    public static final String COLUMNNAME_IsIncludeNullsCampaign = "IsIncludeNullsCampaign";

	/** Set Include Nulls in Campaign.
	  * Include nulls in the selection of the campaign
	  */
	public void setIsIncludeNullsCampaign (boolean IsIncludeNullsCampaign);

	/** Get Include Nulls in Campaign.
	  * Include nulls in the selection of the campaign
	  */
	public boolean isIncludeNullsCampaign();

    /** Column name IsIncludeNullsElementValue */
    public static final String COLUMNNAME_IsIncludeNullsElementValue = "IsIncludeNullsElementValue";

	/** Set Include Nulls in Account.
	  * Include nulls in the selection of the account
	  */
	public void setIsIncludeNullsElementValue (boolean IsIncludeNullsElementValue);

	/** Get Include Nulls in Account.
	  * Include nulls in the selection of the account
	  */
	public boolean isIncludeNullsElementValue();

    /** Column name IsIncludeNullsLocation */
    public static final String COLUMNNAME_IsIncludeNullsLocation = "IsIncludeNullsLocation";

	/** Set Include Nulls in Location.
	  * Include nulls in the selection of the location
	  */
	public void setIsIncludeNullsLocation (boolean IsIncludeNullsLocation);

	/** Get Include Nulls in Location.
	  * Include nulls in the selection of the location
	  */
	public boolean isIncludeNullsLocation();

    /** Column name IsIncludeNullsOrg */
    public static final String COLUMNNAME_IsIncludeNullsOrg = "IsIncludeNullsOrg";

	/** Set Include Nulls in Org.
	  * Include nulls in the selection of the organization
	  */
	public void setIsIncludeNullsOrg (boolean IsIncludeNullsOrg);

	/** Get Include Nulls in Org.
	  * Include nulls in the selection of the organization
	  */
	public boolean isIncludeNullsOrg();

    /** Column name IsIncludeNullsOrgTrx */
    public static final String COLUMNNAME_IsIncludeNullsOrgTrx = "IsIncludeNullsOrgTrx";

	/** Set Include Nulls in Org Trx.
	  * Include nulls in the selection of the organization transaction
	  */
	public void setIsIncludeNullsOrgTrx (boolean IsIncludeNullsOrgTrx);

	/** Get Include Nulls in Org Trx.
	  * Include nulls in the selection of the organization transaction
	  */
	public boolean isIncludeNullsOrgTrx();

    /** Column name IsIncludeNullsProduct */
    public static final String COLUMNNAME_IsIncludeNullsProduct = "IsIncludeNullsProduct";

	/** Set Include Nulls in Product.
	  * Include nulls in the selection of the product
	  */
	public void setIsIncludeNullsProduct (boolean IsIncludeNullsProduct);

	/** Get Include Nulls in Product.
	  * Include nulls in the selection of the product
	  */
	public boolean isIncludeNullsProduct();

    /** Column name IsIncludeNullsProject */
    public static final String COLUMNNAME_IsIncludeNullsProject = "IsIncludeNullsProject";

	/** Set Include Nulls in Project.
	  * Include nulls in the selection of the project
	  */
	public void setIsIncludeNullsProject (boolean IsIncludeNullsProject);

	/** Get Include Nulls in Project.
	  * Include nulls in the selection of the project
	  */
	public boolean isIncludeNullsProject();

    /** Column name IsIncludeNullsSalesRegion */
    public static final String COLUMNNAME_IsIncludeNullsSalesRegion = "IsIncludeNullsSalesRegion";

	/** Set Include Nulls in Sales Region.
	  * Include nulls in the selection of the sales region
	  */
	public void setIsIncludeNullsSalesRegion (boolean IsIncludeNullsSalesRegion);

	/** Get Include Nulls in Sales Region.
	  * Include nulls in the selection of the sales region
	  */
	public boolean isIncludeNullsSalesRegion();

    /** Column name IsIncludeNullsUserElement1 */
    public static final String COLUMNNAME_IsIncludeNullsUserElement1 = "IsIncludeNullsUserElement1";

	/** Set Include Nulls in User Element 1.
	  * Include nulls in the selection of the user element 1
	  */
	public void setIsIncludeNullsUserElement1 (boolean IsIncludeNullsUserElement1);

	/** Get Include Nulls in User Element 1.
	  * Include nulls in the selection of the user element 1
	  */
	public boolean isIncludeNullsUserElement1();

    /** Column name IsIncludeNullsUserElement2 */
    public static final String COLUMNNAME_IsIncludeNullsUserElement2 = "IsIncludeNullsUserElement2";

	/** Set Include Nulls in User Element 2.
	  * Include nulls in the selection of the user element 2
	  */
	public void setIsIncludeNullsUserElement2 (boolean IsIncludeNullsUserElement2);

	/** Get Include Nulls in User Element 2.
	  * Include nulls in the selection of the user element 2
	  */
	public boolean isIncludeNullsUserElement2();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public I_M_Product getM_Product() throws RuntimeException;

    /** Column name Org_ID */
    public static final String COLUMNNAME_Org_ID = "Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setOrg_ID (int Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getOrg_ID();

    /** Column name PA_ReportLine_ID */
    public static final String COLUMNNAME_PA_ReportLine_ID = "PA_ReportLine_ID";

	/** Set Report Line	  */
	public void setPA_ReportLine_ID (int PA_ReportLine_ID);

	/** Get Report Line	  */
	public int getPA_ReportLine_ID();

	public I_PA_ReportLine getPA_ReportLine() throws RuntimeException;

    /** Column name PA_ReportSource_ID */
    public static final String COLUMNNAME_PA_ReportSource_ID = "PA_ReportSource_ID";

	/** Set Report Source.
	  * Restriction of what will be shown in Report Line
	  */
	public void setPA_ReportSource_ID (int PA_ReportSource_ID);

	/** Get Report Source.
	  * Restriction of what will be shown in Report Line
	  */
	public int getPA_ReportSource_ID();

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

    /** Column name UserElement1_ID */
    public static final String COLUMNNAME_UserElement1_ID = "UserElement1_ID";

	/** Set User Element 1.
	  * User defined accounting Element
	  */
	public void setUserElement1_ID (int UserElement1_ID);

	/** Get User Element 1.
	  * User defined accounting Element
	  */
	public int getUserElement1_ID();

    /** Column name UserElement2_ID */
    public static final String COLUMNNAME_UserElement2_ID = "UserElement2_ID";

	/** Set User Element 2.
	  * User defined accounting Element
	  */
	public void setUserElement2_ID (int UserElement2_ID);

	/** Get User Element 2.
	  * User defined accounting Element
	  */
	public int getUserElement2_ID();
}
