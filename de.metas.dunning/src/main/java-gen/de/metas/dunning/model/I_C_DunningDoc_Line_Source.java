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
package de.metas.dunning.model;


/** Generated Interface for C_DunningDoc_Line_Source
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_DunningDoc_Line_Source 
{

    /** TableName=C_DunningDoc_Line_Source */
    public static final String Table_Name = "C_DunningDoc_Line_Source";

    /** AD_Table_ID=540403 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column name C_Dunning_Candidate_ID */
    public static final String COLUMNNAME_C_Dunning_Candidate_ID = "C_Dunning_Candidate_ID";

	/** Set Mahnungsdisposition	  */
	public void setC_Dunning_Candidate_ID (int C_Dunning_Candidate_ID);

	/** Get Mahnungsdisposition	  */
	public int getC_Dunning_Candidate_ID();

	public de.metas.dunning.model.I_C_Dunning_Candidate getC_Dunning_Candidate() throws RuntimeException;

	public void setC_Dunning_Candidate(de.metas.dunning.model.I_C_Dunning_Candidate C_Dunning_Candidate);

    /** Column name C_DunningDoc_Line_ID */
    public static final String COLUMNNAME_C_DunningDoc_Line_ID = "C_DunningDoc_Line_ID";

	/** Set Dunning Document Line	  */
	public void setC_DunningDoc_Line_ID (int C_DunningDoc_Line_ID);

	/** Get Dunning Document Line	  */
	public int getC_DunningDoc_Line_ID();

	public de.metas.dunning.model.I_C_DunningDoc_Line getC_DunningDoc_Line() throws RuntimeException;

	public void setC_DunningDoc_Line(de.metas.dunning.model.I_C_DunningDoc_Line C_DunningDoc_Line);

    /** Column name C_DunningDoc_Line_Source_ID */
    public static final String COLUMNNAME_C_DunningDoc_Line_Source_ID = "C_DunningDoc_Line_Source_ID";

	/** Set Dunning Document Line Source	  */
	public void setC_DunningDoc_Line_Source_ID (int C_DunningDoc_Line_Source_ID);

	/** Get Dunning Document Line Source	  */
	public int getC_DunningDoc_Line_Source_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public java.sql.Timestamp getCreated();

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

    /** Column name IsWriteOff */
    public static final String COLUMNNAME_IsWriteOff = "IsWriteOff";

	/** Set Massenaustritt	  */
	public void setIsWriteOff (boolean IsWriteOff);

	/** Get Massenaustritt	  */
	public boolean isWriteOff();

    /** Column name IsWriteOffApplied */
    public static final String COLUMNNAME_IsWriteOffApplied = "IsWriteOffApplied";

	/** Set Massenaustritt Applied	  */
	public void setIsWriteOffApplied (boolean IsWriteOffApplied);

	/** Get Massenaustritt Applied	  */
	public boolean isWriteOffApplied();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public void setProcessed (boolean Processed);

	/** Get Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public boolean isProcessed();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
