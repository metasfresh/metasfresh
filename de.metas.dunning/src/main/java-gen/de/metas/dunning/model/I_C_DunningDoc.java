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


/** Generated Interface for C_DunningDoc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_DunningDoc 
{

    /** TableName=C_DunningDoc */
    public static final String Table_Name = "C_DunningDoc";

    /** AD_Table_ID=540401 */
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

    /** Column name BPartnerAddress */
    public static final String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

	/** Set Anschrift-Text	  */
	public void setBPartnerAddress (java.lang.String BPartnerAddress);

	/** Get Anschrift-Text	  */
	public java.lang.String getBPartnerAddress();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Geschäftspartner.
	  * Bezeichnet einen Geschäftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Geschäftspartner.
	  * Bezeichnet einen Geschäftspartner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Standort.
	  * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Standort.
	  * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column name C_Dunning_Contact_ID */
    public static final String COLUMNNAME_C_Dunning_Contact_ID = "C_Dunning_Contact_ID";

	/** Set Mahnkontakt	  */
	public void setC_Dunning_Contact_ID (int C_Dunning_Contact_ID);

	/** Get Mahnkontakt	  */
	public int getC_Dunning_Contact_ID();

	public org.compiere.model.I_AD_User getC_Dunning_Contact() throws RuntimeException;

	public void setC_Dunning_Contact(org.compiere.model.I_AD_User C_Dunning_Contact);

    /** Column name C_DunningDoc_ID */
    public static final String COLUMNNAME_C_DunningDoc_ID = "C_DunningDoc_ID";

	/** Set Dunning Document	  */
	public void setC_DunningDoc_ID (int C_DunningDoc_ID);

	/** Get Dunning Document	  */
	public int getC_DunningDoc_ID();

    /** Column name C_DunningLevel_ID */
    public static final String COLUMNNAME_C_DunningLevel_ID = "C_DunningLevel_ID";

	/** Set Mahnstufe	  */
	public void setC_DunningLevel_ID (int C_DunningLevel_ID);

	/** Get Mahnstufe	  */
	public int getC_DunningLevel_ID();

	public org.compiere.model.I_C_DunningLevel getC_DunningLevel() throws RuntimeException;

	public void setC_DunningLevel(org.compiere.model.I_C_DunningLevel C_DunningLevel);

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (java.lang.String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public java.lang.String getDocumentNo();

    /** Column name DunningDate */
    public static final String COLUMNNAME_DunningDate = "DunningDate";

	/** Set Dunning Date.
	  * Date of Dunning
	  */
	public void setDunningDate (java.sql.Timestamp DunningDate);

	/** Get Dunning Date.
	  * Date of Dunning
	  */
	public java.sql.Timestamp getDunningDate();

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

    /** Column name IsUseBPartnerAddress */
    public static final String COLUMNNAME_IsUseBPartnerAddress = "IsUseBPartnerAddress";

	/** Set Benutze abw. Adresse	  */
	public void setIsUseBPartnerAddress (boolean IsUseBPartnerAddress);

	/** Get Benutze abw. Adresse	  */
	public boolean isUseBPartnerAddress();

    /** Column name IsWriteOff */
    public static final String COLUMNNAME_IsWriteOff = "IsWriteOff";

	/** Set Massenaustritt	  */
	public void setIsWriteOff (boolean IsWriteOff);

	/** Get Massenaustritt	  */
	public boolean isWriteOff();

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
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
