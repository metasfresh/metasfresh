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

/** Generated Interface for AD_Relation_Explicit_v1
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_Relation_Explicit_v1 
{

    /** TableName=AD_Relation_Explicit_v1 */
    public static final String Table_Name = "AD_Relation_Explicit_v1";

    /** AD_Table_ID=540272 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

    /** Column name AD_Relation_Explicit_v1_ID */
    public static final String COLUMNNAME_AD_Relation_Explicit_v1_ID = "AD_Relation_Explicit_v1_ID";

	/** Set AD_Relation_Explicit_v1	  */
	public void setAD_Relation_Explicit_v1_ID (int AD_Relation_Explicit_v1_ID);

	/** Get AD_Relation_Explicit_v1	  */
	public int getAD_Relation_Explicit_v1_ID();

    /** Column name AD_Relation_ID */
    public static final String COLUMNNAME_AD_Relation_ID = "AD_Relation_ID";

	/** Set Relation	  */
	public void setAD_Relation_ID (int AD_Relation_ID);

	/** Get Relation	  */
	public int getAD_Relation_ID();

	public I_AD_Relation getAD_Relation() throws RuntimeException;

    /** Column name AD_RelationType_ID */
    public static final String COLUMNNAME_AD_RelationType_ID = "AD_RelationType_ID";

	/** Set Relation Type	  */
	public void setAD_RelationType_ID (int AD_RelationType_ID);

	/** Get Relation Type	  */
	public int getAD_RelationType_ID();

	public I_AD_RelationType getAD_RelationType() throws RuntimeException;

    /** Column name AD_RelationType_InternalName */
    public static final String COLUMNNAME_AD_RelationType_InternalName = "AD_RelationType_InternalName";

	/** Set AD_RelationType_InternalName	  */
	public void setAD_RelationType_InternalName (String AD_RelationType_InternalName);

	/** Get AD_RelationType_InternalName	  */
	public String getAD_RelationType_InternalName();

    /** Column name AD_Table_Source_ID */
    public static final String COLUMNNAME_AD_Table_Source_ID = "AD_Table_Source_ID";

	/** Set Quell-Tabelle	  */
	public void setAD_Table_Source_ID (int AD_Table_Source_ID);

	/** Get Quell-Tabelle	  */
	public int getAD_Table_Source_ID();

	public org.compiere.model.I_AD_Table getAD_Table_Source() throws RuntimeException;

    /** Column name AD_Table_Target_ID */
    public static final String COLUMNNAME_AD_Table_Target_ID = "AD_Table_Target_ID";

	/** Set Ziel-Tabelle	  */
	public void setAD_Table_Target_ID (int AD_Table_Target_ID);

	/** Get Ziel-Tabelle	  */
	public int getAD_Table_Target_ID();

	public org.compiere.model.I_AD_Table getAD_Table_Target() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public boolean isActive();

    /** Column name Record_Source_ID */
    public static final String COLUMNNAME_Record_Source_ID = "Record_Source_ID";

	/** Set Quell-Datensatz-ID	  */
	public void setRecord_Source_ID (int Record_Source_ID);

	/** Get Quell-Datensatz-ID	  */
	public int getRecord_Source_ID();

    /** Column name Record_Target_ID */
    public static final String COLUMNNAME_Record_Target_ID = "Record_Target_ID";

	/** Set Ziel-Datensatz-ID	  */
	public void setRecord_Target_ID (int Record_Target_ID);

	/** Get Ziel-Datensatz-ID	  */
	public int getRecord_Target_ID();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
