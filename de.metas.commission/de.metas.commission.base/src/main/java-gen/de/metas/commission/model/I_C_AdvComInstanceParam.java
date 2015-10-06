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
package de.metas.commission.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for C_AdvComInstanceParam
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvComInstanceParam 
{

    /** TableName=C_AdvComInstanceParam */
    public static final String Table_Name = "C_AdvComInstanceParam";

    /** AD_Table_ID=540192 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get AD_Client_ID.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set AD_Org_ID.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get AD_Org_ID.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/** Set AD_Reference_ID.
	  * Systemreferenz und Validierung
	  */
	public void setAD_Reference_ID (int AD_Reference_ID);

	/** Get AD_Reference_ID.
	  * Systemreferenz und Validierung
	  */
	public int getAD_Reference_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException;

    /** Column name AD_Reference_Value_ID */
    public static final String COLUMNNAME_AD_Reference_Value_ID = "AD_Reference_Value_ID";

	/** Set AD_Reference_Value_ID.
	  * Muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.
	  */
	public void setAD_Reference_Value_ID (int AD_Reference_Value_ID);

	/** Get AD_Reference_Value_ID.
	  * Muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.
	  */
	public int getAD_Reference_Value_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference_Value() throws RuntimeException;

    /** Column name C_AdvComInstanceParam_ID */
    public static final String COLUMNNAME_C_AdvComInstanceParam_ID = "C_AdvComInstanceParam_ID";

	/** Set C_AdvComInstanceParam_ID	  */
	public void setC_AdvComInstanceParam_ID (int C_AdvComInstanceParam_ID);

	/** Get C_AdvComInstanceParam_ID	  */
	public int getC_AdvComInstanceParam_ID();

    /** Column name C_AdvComSystem_Type_ID */
    public static final String COLUMNNAME_C_AdvComSystem_Type_ID = "C_AdvComSystem_Type_ID";

	/** Set Vergütungsplan - Provisionsart	  */
	public void setC_AdvComSystem_Type_ID (int C_AdvComSystem_Type_ID);

	/** Get Vergütungsplan - Provisionsart	  */
	public int getC_AdvComSystem_Type_ID();

	public de.metas.commission.model.I_C_AdvComSystem_Type getC_AdvComSystem_Type() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get CreatedBy.
	  * Nutzer, der diesen Eintrag erstellt hat
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

    /** Column name DisplayName */
    public static final String COLUMNNAME_DisplayName = "DisplayName";

	/** Set Name (Anzeige)	  */
	public void setDisplayName (String DisplayName);

	/** Get Name (Anzeige)	  */
	public String getDisplayName();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set IsActive.
	  * Der Eintrag ist im System aktiv
	  */
	public void setIsActive (boolean IsActive);

	/** Get IsActive.
	  * Der Eintrag ist im System aktiv
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

    /** Column name ParamValue */
    public static final String COLUMNNAME_ParamValue = "ParamValue";

	/** Set ParamValue	  */
	public void setParamValue (String ParamValue);

	/** Get ParamValue	  */
	public String getParamValue();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set SeqNo.
	  * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	  */
	public void setSeqNo (int SeqNo);

	/** Get SeqNo.
	  * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	  */
	public int getSeqNo();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get UpdatedBy.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
