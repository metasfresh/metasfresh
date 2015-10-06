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

/** Generated Interface for C_VAT_SmallBusiness
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_VAT_SmallBusiness 
{

    /** TableName=C_VAT_SmallBusiness */
    public static final String Table_Name = "C_VAT_SmallBusiness";

    /** AD_Table_ID=540112 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

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

    /** Column name C_VAT_SmallBusiness_ID */
    public static final String COLUMNNAME_C_VAT_SmallBusiness_ID = "C_VAT_SmallBusiness_ID";

	/** Set Kleinunternehmer-Steuerbefreiung 	  */
	public void setC_VAT_SmallBusiness_ID (int C_VAT_SmallBusiness_ID);

	/** Get Kleinunternehmer-Steuerbefreiung 	  */
	public int getC_VAT_SmallBusiness_ID();

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

    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/** Set Notiz.
	  * Optional weitere Information für ein Dokument
	  */
	public void setNote (String Note);

	/** Get Notiz.
	  * Optional weitere Information für ein Dokument
	  */
	public String getNote();

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

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set Gültig ab.
	  * Gültig ab inklusiv (erster Tag)
	  */
	public void setValidFrom (Timestamp ValidFrom);

	/** Get Gültig ab.
	  * Gültig ab inklusiv (erster Tag)
	  */
	public Timestamp getValidFrom();

    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/** Set Gültig bis.
	  * Gültig bis inklusiv (letzter Tag)
	  */
	public void setValidTo (Timestamp ValidTo);

	/** Get Gültig bis.
	  * Gültig bis inklusiv (letzter Tag)
	  */
	public Timestamp getValidTo();

    /** Column name VATaxID */
    public static final String COLUMNNAME_VATaxID = "VATaxID";

	/** Set Umsatzsteuer-ID	  */
	public void setVATaxID (String VATaxID);

	/** Get Umsatzsteuer-ID	  */
	public String getVATaxID();
}
