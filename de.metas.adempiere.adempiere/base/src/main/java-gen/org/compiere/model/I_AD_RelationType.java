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
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for AD_RelationType
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_RelationType 
{

    /** TableName=AD_RelationType */
    public static final String Table_Name = "AD_RelationType";

    /** AD_Table_ID=53246 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

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

    /** Column name AD_Reference_Source_ID */
    public static final String COLUMNNAME_AD_Reference_Source_ID = "AD_Reference_Source_ID";

	/** Set Source Reference	  */
	public void setAD_Reference_Source_ID (int AD_Reference_Source_ID);

	/** Get Source Reference	  */
	public int getAD_Reference_Source_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference_Source() throws RuntimeException;

    /** Column name AD_Reference_Target_ID */
    public static final String COLUMNNAME_AD_Reference_Target_ID = "AD_Reference_Target_ID";

	/** Set Target Reference	  */
	public void setAD_Reference_Target_ID (int AD_Reference_Target_ID);

	/** Get Target Reference	  */
	public int getAD_Reference_Target_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference_Target() throws RuntimeException;

    /** Column name AD_RelationType_ID */
    public static final String COLUMNNAME_AD_RelationType_ID = "AD_RelationType_ID";

	/** Set Relation Type	  */
	public void setAD_RelationType_ID (int AD_RelationType_ID);

	/** Get Relation Type	  */
	public int getAD_RelationType_ID();

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

	/** Set Beschreibung.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Beschreibung.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name InternalName */
    public static final String COLUMNNAME_InternalName = "InternalName";

	/** Set Interner Name	  */
	public void setInternalName (String InternalName);

	/** Get Interner Name	  */
	public String getInternalName();

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

    /** Column name IsDirected */
    public static final String COLUMNNAME_IsDirected = "IsDirected";

	/** Set Directed.
	  * Tells whether one "sees" the other end of the relation from each end or just from the source
	  */
	public void setIsDirected (boolean IsDirected);

	/** Get Directed.
	  * Tells whether one "sees" the other end of the relation from each end or just from the source
	  */
	public boolean isDirected();

    /** Column name IsExplicit */
    public static final String COLUMNNAME_IsExplicit = "IsExplicit";

	/** Set Explizit	  */
	public void setIsExplicit (boolean IsExplicit);

	/** Get Explizit	  */
	public boolean isExplicit();

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

    /** Column name Role_Source */
    public static final String COLUMNNAME_Role_Source = "Role_Source";

	/** Set Source Role	  */
	public void setRole_Source (String Role_Source);

	/** Get Source Role	  */
	public String getRole_Source();

    /** Column name Role_Target */
    public static final String COLUMNNAME_Role_Target = "Role_Target";

	/** Set Target Role	  */
	public void setRole_Target (String Role_Target);

	/** Get Target Role	  */
	public String getRole_Target();

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
