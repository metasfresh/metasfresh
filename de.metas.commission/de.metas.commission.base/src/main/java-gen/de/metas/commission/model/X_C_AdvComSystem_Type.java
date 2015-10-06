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
/** Generated Model - DO NOT CHANGE */
package de.metas.commission.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for C_AdvComSystem_Type
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvComSystem_Type extends PO implements I_C_AdvComSystem_Type, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvComSystem_Type (Properties ctx, int C_AdvComSystem_Type_ID, String trxName)
    {
      super (ctx, C_AdvComSystem_Type_ID, trxName);
      /** if (C_AdvComSystem_Type_ID == 0)
        {
			setC_AdvCommissionType_ID (0);
			setC_AdvComSystem_ID (0);
			setC_AdvComSystem_Type_ID (0);
			setDynamicCompression (null);
// Off
			setName (null);
			setRetroactiveEvaluation (null);
// Off
			setUseGrossOrNetPoints (null);
// GRO
        } */
    }

    /** Load Constructor */
    public X_C_AdvComSystem_Type (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_AdvComSystem_Type[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public de.metas.commission.model.I_C_AdvCommissionType getC_AdvCommissionType() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionType)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionType.Table_Name)
			.getPO(getC_AdvCommissionType_ID(), get_TrxName());	}

	/** Set Provisionsart.
		@param C_AdvCommissionType_ID Provisionsart	  */
	public void setC_AdvCommissionType_ID (int C_AdvCommissionType_ID)
	{
		if (C_AdvCommissionType_ID < 1) 
			set_Value (COLUMNNAME_C_AdvCommissionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvCommissionType_ID, Integer.valueOf(C_AdvCommissionType_ID));
	}

	/** Get Provisionsart.
		@return Provisionsart	  */
	public int getC_AdvCommissionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvCommissionSalaryGroup getC_AdvComRank_Min() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionSalaryGroup)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionSalaryGroup.Table_Name)
			.getPO(getC_AdvComRank_Min_ID(), get_TrxName());	}

	/** Set Mindest-Rang.
		@param C_AdvComRank_Min_ID 
		Minimaler Rang, den eine VP haben muss, um berücksichtigt zu werden
	  */
	public void setC_AdvComRank_Min_ID (int C_AdvComRank_Min_ID)
	{
		if (C_AdvComRank_Min_ID < 1) 
			set_Value (COLUMNNAME_C_AdvComRank_Min_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvComRank_Min_ID, Integer.valueOf(C_AdvComRank_Min_ID));
	}

	/** Get Mindest-Rang.
		@return Minimaler Rang, den eine VP haben muss, um berücksichtigt zu werden
	  */
	public int getC_AdvComRank_Min_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComRank_Min_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** C_AdvComRank_Min_Status AD_Reference_ID=540124 */
	public static final int C_ADVCOMRANK_MIN_STATUS_AD_Reference_ID=540124;
	/** Prognose = FORECAST */
	public static final String C_ADVCOMRANK_MIN_STATUS_Prognose = "FORECAST";
	/** Prov.-Relevant = ACTUAL */
	public static final String C_ADVCOMRANK_MIN_STATUS_Prov_Relevant = "ACTUAL";
	/** Set Status (Mindest-Rang).
		@param C_AdvComRank_Min_Status 
		Status (Prognose oder Prov-Relevant) des Mindestrangs
	  */
	public void setC_AdvComRank_Min_Status (String C_AdvComRank_Min_Status)
	{

		set_Value (COLUMNNAME_C_AdvComRank_Min_Status, C_AdvComRank_Min_Status);
	}

	/** Get Status (Mindest-Rang).
		@return Status (Prognose oder Prov-Relevant) des Mindestrangs
	  */
	public String getC_AdvComRank_Min_Status () 
	{
		return (String)get_Value(COLUMNNAME_C_AdvComRank_Min_Status);
	}

	public de.metas.commission.model.I_C_AdvComSystem getC_AdvComSystem() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvComSystem)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvComSystem.Table_Name)
			.getPO(getC_AdvComSystem_ID(), get_TrxName());	}

	/** Set Vergütungsplan.
		@param C_AdvComSystem_ID Vergütungsplan	  */
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID)
	{
		if (C_AdvComSystem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_ID, Integer.valueOf(C_AdvComSystem_ID));
	}

	/** Get Vergütungsplan.
		@return Vergütungsplan	  */
	public int getC_AdvComSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Vergütungsplan - Provisionsart.
		@param C_AdvComSystem_Type_ID Vergütungsplan - Provisionsart	  */
	public void setC_AdvComSystem_Type_ID (int C_AdvComSystem_Type_ID)
	{
		if (C_AdvComSystem_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_Type_ID, Integer.valueOf(C_AdvComSystem_Type_ID));
	}

	/** Get Vergütungsplan - Provisionsart.
		@return Vergütungsplan - Provisionsart	  */
	public int getC_AdvComSystem_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComSystem_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** DynamicCompression AD_Reference_ID=540126 */
	public static final int DYNAMICCOMPRESSION_AD_Reference_ID=540126;
	/** Keine = Off */
	public static final String DYNAMICCOMPRESSION_Keine = "Off";
	/** Verfallen lassen = Discard */
	public static final String DYNAMICCOMPRESSION_VerfallenLassen = "Discard";
	/** Nach oben versch. (Shift) = Shift */
	public static final String DYNAMICCOMPRESSION_NachObenVerschShift = "Shift";
	/** Set Dynamische Kompression.
		@param DynamicCompression 
		Art der angewendeten dynamischen Kompression (falls Mindestumsatz unterschritten wird)
	  */
	public void setDynamicCompression (String DynamicCompression)
	{

		set_Value (COLUMNNAME_DynamicCompression, DynamicCompression);
	}

	/** Get Dynamische Kompression.
		@return Art der angewendeten dynamischen Kompression (falls Mindestumsatz unterschritten wird)
	  */
	public String getDynamicCompression () 
	{
		return (String)get_Value(COLUMNNAME_DynamicCompression);
	}

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product_Category)MTable.get(getCtx(), org.compiere.model.I_M_Product_Category.Table_Name)
			.getPO(getM_Product_Category_ID(), get_TrxName());	}

	/** Set Produkt-Kategorie.
		@param M_Product_Category_ID 
		Kategorie eines Produktes
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Produkt-Kategorie.
		@return Kategorie eines Produktes
	  */
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** RetroactiveEvaluation AD_Reference_ID=540127 */
	public static final int RETROACTIVEEVALUATION_AD_Reference_ID=540127;
	/** Keine = Off */
	public static final String RETROACTIVEEVALUATION_Keine = "Off";
	/** Provisionsperiode = Period */
	public static final String RETROACTIVEEVALUATION_Provisionsperiode = "Period";
	/** Tag = Day */
	public static final String RETROACTIVEEVALUATION_Tag = "Day";
	/** Set Rückwirkende Neuberechnung.
		@param RetroactiveEvaluation 
		Rückwirkende Änderung von Provisionen bei Rang-Änderungen
	  */
	public void setRetroactiveEvaluation (String RetroactiveEvaluation)
	{

		set_Value (COLUMNNAME_RetroactiveEvaluation, RetroactiveEvaluation);
	}

	/** Get Rückwirkende Neuberechnung.
		@return Rückwirkende Änderung von Provisionen bei Rang-Änderungen
	  */
	public String getRetroactiveEvaluation () 
	{
		return (String)get_Value(COLUMNNAME_RetroactiveEvaluation);
	}

	/** UseGrossOrNetPoints AD_Reference_ID=540143 */
	public static final int USEGROSSORNETPOINTS_AD_Reference_ID=540143;
	/** Brutto = GRO */
	public static final String USEGROSSORNETPOINTS_Brutto = "GRO";
	/** Netto = NET */
	public static final String USEGROSSORNETPOINTS_Netto = "NET";
	/** Set Benutzt Brutto- oder Nettopunkte.
		@param UseGrossOrNetPoints Benutzt Brutto- oder Nettopunkte	  */
	public void setUseGrossOrNetPoints (String UseGrossOrNetPoints)
	{

		set_Value (COLUMNNAME_UseGrossOrNetPoints, UseGrossOrNetPoints);
	}

	/** Get Benutzt Brutto- oder Nettopunkte.
		@return Benutzt Brutto- oder Nettopunkte	  */
	public String getUseGrossOrNetPoints () 
	{
		return (String)get_Value(COLUMNNAME_UseGrossOrNetPoints);
	}
}