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

/** Generated Interface for C_Sponsor_CondLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_Sponsor_CondLine 
{

    /** TableName=C_Sponsor_CondLine */
    public static final String Table_Name = "C_Sponsor_CondLine";

    /** AD_Table_ID=540333 */
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

    /** Column name C_AdvCommissionCondition_ID */
    public static final String COLUMNNAME_C_AdvCommissionCondition_ID = "C_AdvCommissionCondition_ID";

	/** Set Provisionsvertrag	  */
	public void setC_AdvCommissionCondition_ID (int C_AdvCommissionCondition_ID);

	/** Get Provisionsvertrag	  */
	public int getC_AdvCommissionCondition_ID();

	public de.metas.commission.model.I_C_AdvCommissionCondition getC_AdvCommissionCondition() throws RuntimeException;

    /** Column name C_AdvComSystem_ID */
    public static final String COLUMNNAME_C_AdvComSystem_ID = "C_AdvComSystem_ID";

	/** Set VergÃ¼tungsplan	  */
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID);

	/** Get VergÃ¼tungsplan	  */
	public int getC_AdvComSystem_ID();

	public de.metas.commission.model.I_C_AdvComSystem getC_AdvComSystem() throws RuntimeException;

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set GeschÃ¤ftspartner.
	  * Bezeichnet einen GeschÃ¤ftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get GeschÃ¤ftspartner.
	  * Bezeichnet einen GeschÃ¤ftspartner
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

    /** Column name C_Sponsor_Cond_ID */
    public static final String COLUMNNAME_C_Sponsor_Cond_ID = "C_Sponsor_Cond_ID";

	/** Set Sponsor Konditionen	  */
	public void setC_Sponsor_Cond_ID (int C_Sponsor_Cond_ID);

	/** Get Sponsor Konditionen	  */
	public int getC_Sponsor_Cond_ID();

	public de.metas.commission.model.I_C_Sponsor_Cond getC_Sponsor_Cond() throws RuntimeException;

    /** Column name C_Sponsor_CondLine_ID */
    public static final String COLUMNNAME_C_Sponsor_CondLine_ID = "C_Sponsor_CondLine_ID";

	/** Set Konditionen-Zeile	  */
	public void setC_Sponsor_CondLine_ID (int C_Sponsor_CondLine_ID);

	/** Get Konditionen-Zeile	  */
	public int getC_Sponsor_CondLine_ID();

    /** Column name C_Sponsor_Parent_ID */
    public static final String COLUMNNAME_C_Sponsor_Parent_ID = "C_Sponsor_Parent_ID";

	/** Set Eltern-Sponsor	  */
	public void setC_Sponsor_Parent_ID (int C_Sponsor_Parent_ID);

	/** Get Eltern-Sponsor	  */
	public int getC_Sponsor_Parent_ID();

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor_Parent() throws RuntimeException;

    /** Column name DateFrom */
    public static final String COLUMNNAME_DateFrom = "DateFrom";

	/** Set Datum von.
	  * Startdatum eines Abschnittes
	  */
	public void setDateFrom (Timestamp DateFrom);

	/** Get Datum von.
	  * Startdatum eines Abschnittes
	  */
	public Timestamp getDateFrom();

    /** Column name DateTo */
    public static final String COLUMNNAME_DateTo = "DateTo";

	/** Set Datum bis.
	  * Enddatum eines Abschnittes
	  */
	public void setDateTo (Timestamp DateTo);

	/** Get Datum bis.
	  * Enddatum eines Abschnittes
	  */
	public Timestamp getDateTo();

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

    /** Column name SponsorSalesRepType */
    public static final String COLUMNNAME_SponsorSalesRepType = "SponsorSalesRepType";

	/** Set Art	  */
	public void setSponsorSalesRepType (String SponsorSalesRepType);

	/** Get Art	  */
	public String getSponsorSalesRepType();

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
