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
package de.metas.letters.model;


/** Generated Interface for C_Letter
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Letter 
{

    /** TableName=C_Letter */
    public static final String Table_Name = "C_Letter";

    /** AD_Table_ID=540408 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_BoilerPlate_ID */
    public static final String COLUMNNAME_AD_BoilerPlate_ID = "AD_BoilerPlate_ID";

	/** Set Textbaustein	  */
	public void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID);

	/** Get Textbaustein	  */
	public int getAD_BoilerPlate_ID();

	public de.metas.letters.model.I_AD_BoilerPlate getAD_BoilerPlate() throws RuntimeException;

	public void setAD_BoilerPlate(de.metas.letters.model.I_AD_BoilerPlate AD_BoilerPlate);

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

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set DB-Tabelle.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get DB-Tabelle.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException;

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);
	
    /** Column name BPartnerAddress */
    public static final String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

	/** Set Anschrift-Text	  */
	public void setBPartnerAddress (java.lang.String BPartnerAddress);

	/** Get Anschrift-Text	  */
	public java.lang.String getBPartnerAddress();

    /** Column name C_BP_Contact_ID */
    public static final String COLUMNNAME_C_BP_Contact_ID = "C_BP_Contact_ID";

	/** Set Contact	  */
	public void setC_BP_Contact_ID (int C_BP_Contact_ID);

	/** Get Contact	  */
	public int getC_BP_Contact_ID();

	public org.compiere.model.I_AD_User getC_BP_Contact() throws RuntimeException;

	public void setC_BP_Contact(org.compiere.model.I_AD_User C_BP_Contact);

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

    /** Column name C_Letter_ID */
    public static final String COLUMNNAME_C_Letter_ID = "C_Letter_ID";

	/** Set Letter	  */
	public void setC_Letter_ID (int C_Letter_ID);

	/** Get Letter	  */
	public int getC_Letter_ID();

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

    /** Column name IsMembershipBadgeToPrint */
    public static final String COLUMNNAME_IsMembershipBadgeToPrint = "IsMembershipBadgeToPrint";

	/** Set Zu druckende Mitgliederausweise	  */
	public void setIsMembershipBadgeToPrint (boolean IsMembershipBadgeToPrint);

	/** Get Zu druckende Mitgliederausweise	  */
	public boolean isMembershipBadgeToPrint();

    /** Column name LetterBody */
    public static final String COLUMNNAME_LetterBody = "LetterBody";

	/** Set Body	  */
	public void setLetterBody (java.lang.String LetterBody);

	/** Get Body	  */
	public java.lang.String getLetterBody();

    /** Column name LetterBodyParsed */
    public static final String COLUMNNAME_LetterBodyParsed = "LetterBodyParsed";

	/** Set Body (parsed)	  */
	public void setLetterBodyParsed (java.lang.String LetterBodyParsed);

	/** Get Body (parsed)	  */
	public java.lang.String getLetterBodyParsed();

    /** Column name LetterSubject */
    public static final String COLUMNNAME_LetterSubject = "LetterSubject";

	/** Set Subject	  */
	public void setLetterSubject (java.lang.String LetterSubject);

	/** Get Subject	  */
	public java.lang.String getLetterSubject();

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Datensatz-ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Datensatz-ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();
	
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
