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

/** Generated Interface for PA_Hierarchy
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_PA_Hierarchy 
{

    /** TableName=PA_Hierarchy */
    public static final String Table_Name = "PA_Hierarchy";

    /** AD_Table_ID=821 */
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

    /** Column name AD_Tree_Account_ID */
    public static final String COLUMNNAME_AD_Tree_Account_ID = "AD_Tree_Account_ID";

	/** Set Account Tree.
	  * Tree for Natural Account Tree
	  */
	public void setAD_Tree_Account_ID (int AD_Tree_Account_ID);

	/** Get Account Tree.
	  * Tree for Natural Account Tree
	  */
	public int getAD_Tree_Account_ID();

	public I_AD_Tree getAD_Tree_Account() throws RuntimeException;

    /** Column name AD_Tree_Activity_ID */
    public static final String COLUMNNAME_AD_Tree_Activity_ID = "AD_Tree_Activity_ID";

	/** Set Activity Tree.
	  * Tree to determine activity hierarchy
	  */
	public void setAD_Tree_Activity_ID (int AD_Tree_Activity_ID);

	/** Get Activity Tree.
	  * Tree to determine activity hierarchy
	  */
	public int getAD_Tree_Activity_ID();

	public I_AD_Tree getAD_Tree_Activity() throws RuntimeException;

    /** Column name AD_Tree_BPartner_ID */
    public static final String COLUMNNAME_AD_Tree_BPartner_ID = "AD_Tree_BPartner_ID";

	/** Set BPartner Tree.
	  * Tree to determine business partner hierarchy
	  */
	public void setAD_Tree_BPartner_ID (int AD_Tree_BPartner_ID);

	/** Get BPartner Tree.
	  * Tree to determine business partner hierarchy
	  */
	public int getAD_Tree_BPartner_ID();

	public I_AD_Tree getAD_Tree_BPartner() throws RuntimeException;

    /** Column name AD_Tree_Campaign_ID */
    public static final String COLUMNNAME_AD_Tree_Campaign_ID = "AD_Tree_Campaign_ID";

	/** Set Campaign Tree.
	  * Tree to determine marketing campaign hierarchy
	  */
	public void setAD_Tree_Campaign_ID (int AD_Tree_Campaign_ID);

	/** Get Campaign Tree.
	  * Tree to determine marketing campaign hierarchy
	  */
	public int getAD_Tree_Campaign_ID();

	public I_AD_Tree getAD_Tree_Campaign() throws RuntimeException;

    /** Column name AD_Tree_Org_ID */
    public static final String COLUMNNAME_AD_Tree_Org_ID = "AD_Tree_Org_ID";

	/** Set Organization Tree.
	  * Tree to determine organizational hierarchy
	  */
	public void setAD_Tree_Org_ID (int AD_Tree_Org_ID);

	/** Get Organization Tree.
	  * Tree to determine organizational hierarchy
	  */
	public int getAD_Tree_Org_ID();

	public I_AD_Tree getAD_Tree_Org() throws RuntimeException;

    /** Column name AD_Tree_Product_ID */
    public static final String COLUMNNAME_AD_Tree_Product_ID = "AD_Tree_Product_ID";

	/** Set Product Tree.
	  * Tree to determine product hierarchy
	  */
	public void setAD_Tree_Product_ID (int AD_Tree_Product_ID);

	/** Get Product Tree.
	  * Tree to determine product hierarchy
	  */
	public int getAD_Tree_Product_ID();

	public I_AD_Tree getAD_Tree_Product() throws RuntimeException;

    /** Column name AD_Tree_Project_ID */
    public static final String COLUMNNAME_AD_Tree_Project_ID = "AD_Tree_Project_ID";

	/** Set Project Tree.
	  * Tree to determine project hierarchy
	  */
	public void setAD_Tree_Project_ID (int AD_Tree_Project_ID);

	/** Get Project Tree.
	  * Tree to determine project hierarchy
	  */
	public int getAD_Tree_Project_ID();

	public I_AD_Tree getAD_Tree_Project() throws RuntimeException;

    /** Column name AD_Tree_SalesRegion_ID */
    public static final String COLUMNNAME_AD_Tree_SalesRegion_ID = "AD_Tree_SalesRegion_ID";

	/** Set Sales Region Tree.
	  * Tree to determine sales regional hierarchy
	  */
	public void setAD_Tree_SalesRegion_ID (int AD_Tree_SalesRegion_ID);

	/** Get Sales Region Tree.
	  * Tree to determine sales regional hierarchy
	  */
	public int getAD_Tree_SalesRegion_ID();

	public I_AD_Tree getAD_Tree_SalesRegion() throws RuntimeException;

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

    /** Column name PA_Hierarchy_ID */
    public static final String COLUMNNAME_PA_Hierarchy_ID = "PA_Hierarchy_ID";

	/** Set Reporting Hierarchy.
	  * Optional Reporting Hierarchy - If not selected the default hierarchy trees are used.
	  */
	public void setPA_Hierarchy_ID (int PA_Hierarchy_ID);

	/** Get Reporting Hierarchy.
	  * Optional Reporting Hierarchy - If not selected the default hierarchy trees are used.
	  */
	public int getPA_Hierarchy_ID();

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
