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

/** Generated Interface for PA_ReportCube
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_PA_ReportCube 
{

    /** TableName=PA_ReportCube */
    public static final String Table_Name = "PA_ReportCube";

    /** AD_Table_ID=53202 */
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

    /** Column name C_Calendar_ID */
    public static final String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

	/** Set Calendar.
	  * Accounting Calendar Name
	  */
	public void setC_Calendar_ID (int C_Calendar_ID);

	/** Get Calendar.
	  * Accounting Calendar Name
	  */
	public int getC_Calendar_ID();

	public I_C_Calendar getC_Calendar() throws RuntimeException;

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

    /** Column name IsActivityDim */
    public static final String COLUMNNAME_IsActivityDim = "IsActivityDim";

	/** Set Activity Dimension.
	  * Include Activity as a cube dimension
	  */
	public void setIsActivityDim (boolean IsActivityDim);

	/** Get Activity Dimension.
	  * Include Activity as a cube dimension
	  */
	public boolean isActivityDim();

    /** Column name IsBPartnerDim */
    public static final String COLUMNNAME_IsBPartnerDim = "IsBPartnerDim";

	/** Set Business Partner Dimension.
	  * Include Business Partner as a cube dimension
	  */
	public void setIsBPartnerDim (boolean IsBPartnerDim);

	/** Get Business Partner Dimension.
	  * Include Business Partner as a cube dimension
	  */
	public boolean isBPartnerDim();

    /** Column name IsCampaignDim */
    public static final String COLUMNNAME_IsCampaignDim = "IsCampaignDim";

	/** Set Campaign Dimension.
	  * Include Campaign as a cube dimension
	  */
	public void setIsCampaignDim (boolean IsCampaignDim);

	/** Get Campaign Dimension.
	  * Include Campaign as a cube dimension
	  */
	public boolean isCampaignDim();

    /** Column name IsGLBudgetDim */
    public static final String COLUMNNAME_IsGLBudgetDim = "IsGLBudgetDim";

	/** Set GL Budget Dimension.
	  * Include GL Budget as a cube dimension
	  */
	public void setIsGLBudgetDim (boolean IsGLBudgetDim);

	/** Get GL Budget Dimension.
	  * Include GL Budget as a cube dimension
	  */
	public boolean isGLBudgetDim();

    /** Column name IsLocFromDim */
    public static final String COLUMNNAME_IsLocFromDim = "IsLocFromDim";

	/** Set Location From Dimension.
	  * Include Location From as a cube dimension
	  */
	public void setIsLocFromDim (boolean IsLocFromDim);

	/** Get Location From Dimension.
	  * Include Location From as a cube dimension
	  */
	public boolean isLocFromDim();

    /** Column name IsLocToDim */
    public static final String COLUMNNAME_IsLocToDim = "IsLocToDim";

	/** Set Location To  Dimension.
	  * Include Location To as a cube dimension
	  */
	public void setIsLocToDim (boolean IsLocToDim);

	/** Get Location To  Dimension.
	  * Include Location To as a cube dimension
	  */
	public boolean isLocToDim();

    /** Column name IsOrgTrxDim */
    public static final String COLUMNNAME_IsOrgTrxDim = "IsOrgTrxDim";

	/** Set OrgTrx Dimension.
	  * Include OrgTrx as a cube dimension
	  */
	public void setIsOrgTrxDim (boolean IsOrgTrxDim);

	/** Get OrgTrx Dimension.
	  * Include OrgTrx as a cube dimension
	  */
	public boolean isOrgTrxDim();

    /** Column name IsProductDim */
    public static final String COLUMNNAME_IsProductDim = "IsProductDim";

	/** Set Product Dimension.
	  * Include Product as a cube dimension
	  */
	public void setIsProductDim (boolean IsProductDim);

	/** Get Product Dimension.
	  * Include Product as a cube dimension
	  */
	public boolean isProductDim();

    /** Column name IsProjectDim */
    public static final String COLUMNNAME_IsProjectDim = "IsProjectDim";

	/** Set Project Dimension.
	  * Include Project as a cube dimension
	  */
	public void setIsProjectDim (boolean IsProjectDim);

	/** Get Project Dimension.
	  * Include Project as a cube dimension
	  */
	public boolean isProjectDim();

    /** Column name IsProjectPhaseDim */
    public static final String COLUMNNAME_IsProjectPhaseDim = "IsProjectPhaseDim";

	/** Set Project Phase  Dimension.
	  * Include Project Phase as a cube dimension
	  */
	public void setIsProjectPhaseDim (boolean IsProjectPhaseDim);

	/** Get Project Phase  Dimension.
	  * Include Project Phase as a cube dimension
	  */
	public boolean isProjectPhaseDim();

    /** Column name IsProjectTaskDim */
    public static final String COLUMNNAME_IsProjectTaskDim = "IsProjectTaskDim";

	/** Set Project Task  Dimension.
	  * Include Project Task as a cube dimension
	  */
	public void setIsProjectTaskDim (boolean IsProjectTaskDim);

	/** Get Project Task  Dimension.
	  * Include Project Task as a cube dimension
	  */
	public boolean isProjectTaskDim();

    /** Column name IsSalesRegionDim */
    public static final String COLUMNNAME_IsSalesRegionDim = "IsSalesRegionDim";

	/** Set Sales Region Dimension.
	  * Include Sales Region as a cube dimension
	  */
	public void setIsSalesRegionDim (boolean IsSalesRegionDim);

	/** Get Sales Region Dimension.
	  * Include Sales Region as a cube dimension
	  */
	public boolean isSalesRegionDim();

    /** Column name IsSubAcctDim */
    public static final String COLUMNNAME_IsSubAcctDim = "IsSubAcctDim";

	/** Set Sub Acct Dimension.
	  * Include Sub Acct as a cube dimension
	  */
	public void setIsSubAcctDim (boolean IsSubAcctDim);

	/** Get Sub Acct Dimension.
	  * Include Sub Acct as a cube dimension
	  */
	public boolean isSubAcctDim();

    /** Column name IsUser1Dim */
    public static final String COLUMNNAME_IsUser1Dim = "IsUser1Dim";

	/** Set User 1 Dimension.
	  * Include User 1 as a cube dimension
	  */
	public void setIsUser1Dim (boolean IsUser1Dim);

	/** Get User 1 Dimension.
	  * Include User 1 as a cube dimension
	  */
	public boolean isUser1Dim();

    /** Column name IsUser2Dim */
    public static final String COLUMNNAME_IsUser2Dim = "IsUser2Dim";

	/** Set User 2 Dimension.
	  * Include User 2 as a cube dimension
	  */
	public void setIsUser2Dim (boolean IsUser2Dim);

	/** Get User 2 Dimension.
	  * Include User 2 as a cube dimension
	  */
	public boolean isUser2Dim();

    /** Column name IsUserElement1Dim */
    public static final String COLUMNNAME_IsUserElement1Dim = "IsUserElement1Dim";

	/** Set User Element 1 Dimension.
	  * Include User Element 1 as a cube dimension
	  */
	public void setIsUserElement1Dim (boolean IsUserElement1Dim);

	/** Get User Element 1 Dimension.
	  * Include User Element 1 as a cube dimension
	  */
	public boolean isUserElement1Dim();

    /** Column name IsUserElement2Dim */
    public static final String COLUMNNAME_IsUserElement2Dim = "IsUserElement2Dim";

	/** Set User Element 2 Dimension.
	  * Include User Element 2 as a cube dimension
	  */
	public void setIsUserElement2Dim (boolean IsUserElement2Dim);

	/** Get User Element 2 Dimension.
	  * Include User Element 2 as a cube dimension
	  */
	public boolean isUserElement2Dim();

    /** Column name LastRecalculated */
    public static final String COLUMNNAME_LastRecalculated = "LastRecalculated";

	/** Set Last Recalculated.
	  * The time last recalculated.
	  */
	public void setLastRecalculated (Timestamp LastRecalculated);

	/** Get Last Recalculated.
	  * The time last recalculated.
	  */
	public Timestamp getLastRecalculated();

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

    /** Column name PA_ReportCube_ID */
    public static final String COLUMNNAME_PA_ReportCube_ID = "PA_ReportCube_ID";

	/** Set Report Cube.
	  * Define reporting cube for pre-calculation of summary accounting data.
	  */
	public void setPA_ReportCube_ID (int PA_ReportCube_ID);

	/** Get Report Cube.
	  * Define reporting cube for pre-calculation of summary accounting data.
	  */
	public int getPA_ReportCube_ID();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

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
