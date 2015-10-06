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

/** Generated Interface for C_AdvComSystem_Type
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvComSystem_Type 
{

    /** TableName=C_AdvComSystem_Type */
    public static final String Table_Name = "C_AdvComSystem_Type";

    /** AD_Table_ID=540250 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

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

    /** Column name C_AdvCommissionType_ID */
    public static final String COLUMNNAME_C_AdvCommissionType_ID = "C_AdvCommissionType_ID";

	/** Set Provisionsart	  */
	public void setC_AdvCommissionType_ID (int C_AdvCommissionType_ID);

	/** Get Provisionsart	  */
	public int getC_AdvCommissionType_ID();

	public de.metas.commission.model.I_C_AdvCommissionType getC_AdvCommissionType() throws RuntimeException;

    /** Column name C_AdvComRank_Min_ID */
    public static final String COLUMNNAME_C_AdvComRank_Min_ID = "C_AdvComRank_Min_ID";

	/** Set Mindest-Rang.
	  * Minimaler Rang, den eine VP haben muss, um berücksichtigt zu werden
	  */
	public void setC_AdvComRank_Min_ID (int C_AdvComRank_Min_ID);

	/** Get Mindest-Rang.
	  * Minimaler Rang, den eine VP haben muss, um berücksichtigt zu werden
	  */
	public int getC_AdvComRank_Min_ID();

	public de.metas.commission.model.I_C_AdvCommissionSalaryGroup getC_AdvComRank_Min() throws RuntimeException;

    /** Column name C_AdvComRank_Min_Status */
    public static final String COLUMNNAME_C_AdvComRank_Min_Status = "C_AdvComRank_Min_Status";

	/** Set Status (Mindest-Rang).
	  * Status (Prognose oder Prov-Relevant) des Mindestrangs
	  */
	public void setC_AdvComRank_Min_Status (String C_AdvComRank_Min_Status);

	/** Get Status (Mindest-Rang).
	  * Status (Prognose oder Prov-Relevant) des Mindestrangs
	  */
	public String getC_AdvComRank_Min_Status();

    /** Column name C_AdvComSystem_ID */
    public static final String COLUMNNAME_C_AdvComSystem_ID = "C_AdvComSystem_ID";

	/** Set Vergütungsplan	  */
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID);

	/** Get Vergütungsplan	  */
	public int getC_AdvComSystem_ID();

	public de.metas.commission.model.I_C_AdvComSystem getC_AdvComSystem() throws RuntimeException;

    /** Column name C_AdvComSystem_Type_ID */
    public static final String COLUMNNAME_C_AdvComSystem_Type_ID = "C_AdvComSystem_Type_ID";

	/** Set Vergütungsplan - Provisionsart	  */
	public void setC_AdvComSystem_Type_ID (int C_AdvComSystem_Type_ID);

	/** Get Vergütungsplan - Provisionsart	  */
	public int getC_AdvComSystem_Type_ID();

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

    /** Column name DynamicCompression */
    public static final String COLUMNNAME_DynamicCompression = "DynamicCompression";

	/** Set Dynamische Kompression.
	  * Art der angewendeten dynamischen Kompression (falls Mindestumsatz unterschritten wird)
	  */
	public void setDynamicCompression (String DynamicCompression);

	/** Get Dynamische Kompression.
	  * Art der angewendeten dynamischen Kompression (falls Mindestumsatz unterschritten wird)
	  */
	public String getDynamicCompression();

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

    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/** Set Produkt-Kategorie.
	  * Kategorie eines Produktes
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/** Get Produkt-Kategorie.
	  * Kategorie eines Produktes
	  */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException;

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

    /** Column name RetroactiveEvaluation */
    public static final String COLUMNNAME_RetroactiveEvaluation = "RetroactiveEvaluation";

	/** Set Rückwirkende Neuberechnung.
	  * Rückwirkende Änderung von Provisionen bei Rang-Änderungen
	  */
	public void setRetroactiveEvaluation (String RetroactiveEvaluation);

	/** Get Rückwirkende Neuberechnung.
	  * Rückwirkende Änderung von Provisionen bei Rang-Änderungen
	  */
	public String getRetroactiveEvaluation();

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

    /** Column name UseGrossOrNetPoints */
    public static final String COLUMNNAME_UseGrossOrNetPoints = "UseGrossOrNetPoints";

	/** Set Benutzt Brutto- oder Nettopunkte	  */
	public void setUseGrossOrNetPoints (String UseGrossOrNetPoints);

	/** Get Benutzt Brutto- oder Nettopunkte	  */
	public String getUseGrossOrNetPoints();
}
