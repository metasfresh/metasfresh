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

/** Generated Interface for C_Sponsor_Cond
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_Sponsor_Cond 
{

    /** TableName=C_Sponsor_Cond */
    public static final String Table_Name = "C_Sponsor_Cond";

    /** AD_Table_ID=540332 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant fÃ¼r diese Installation.
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

    /** Column name CondChangeDate */
    public static final String COLUMNNAME_CondChangeDate = "CondChangeDate";

	/** Set Belegdatum.
	  * Datum, zu dem die Ã„nderungen in Kraft treten
	  */
	public void setCondChangeDate (Timestamp CondChangeDate);

	/** Get Belegdatum.
	  * Datum, zu dem die Ã„nderungen in Kraft treten
	  */
	public Timestamp getCondChangeDate();

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

    /** Column name C_Sponsor_Cond_ID */
    public static final String COLUMNNAME_C_Sponsor_Cond_ID = "C_Sponsor_Cond_ID";

	/** Set Sponsor Konditionen	  */
	public void setC_Sponsor_Cond_ID (int C_Sponsor_Cond_ID);

	/** Get Sponsor Konditionen	  */
	public int getC_Sponsor_Cond_ID();

    /** Column name C_SponsorCondLine_IncludedTab */
    public static final String COLUMNNAME_C_SponsorCondLine_IncludedTab = "C_SponsorCondLine_IncludedTab";

	/** Set Zeilen	  */
	public void setC_SponsorCondLine_IncludedTab (String C_SponsorCondLine_IncludedTab);

	/** Get Zeilen	  */
	public String getC_SponsorCondLine_IncludedTab();

    /** Column name C_Sponsor_ID */
    public static final String COLUMNNAME_C_Sponsor_ID = "C_Sponsor_ID";

	/** Set Sponsor	  */
	public void setC_Sponsor_ID (int C_Sponsor_ID);

	/** Get Sponsor	  */
	public int getC_Sponsor_ID();

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor() throws RuntimeException;

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

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Belegverarbeitung.
	  * Der zukÃ¼nftige Status des Belegs
	  */
	public void setDocAction (String DocAction);

	/** Get Belegverarbeitung.
	  * Der zukÃ¼nftige Status des Belegs
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Belegstatus.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Belegstatus.
	  * The current status of the document
	  */
	public String getDocStatus();

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

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Verarbeitet.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Verarbeitet.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

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
