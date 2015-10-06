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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

/** Generated Model for P_AdvCommissionDetails
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_P_AdvCommissionDetails extends PO implements I_P_AdvCommissionDetails, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_P_AdvCommissionDetails (Properties ctx, int P_AdvCommissionDetails_ID, String trxName)
    {
      super (ctx, P_AdvCommissionDetails_ID, trxName);
      /** if (P_AdvCommissionDetails_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_P_AdvCommissionDetails (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_P_AdvCommissionDetails[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public de.metas.commission.model.I_C_AdvCommissionInstance getC_AdvCommissionInstance() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionInstance)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionInstance.Table_Name)
			.getPO(getC_AdvCommissionInstance_ID(), get_TrxName());	}

	/** Set Provisionsvorgang.
		@param C_AdvCommissionInstance_ID Provisionsvorgang	  */
	public void setC_AdvCommissionInstance_ID (int C_AdvCommissionInstance_ID)
	{
		if (C_AdvCommissionInstance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionInstance_ID, Integer.valueOf(C_AdvCommissionInstance_ID));
	}

	/** Get Provisionsvorgang.
		@return Provisionsvorgang	  */
	public int getC_AdvCommissionInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvCommissionTerm getC_AdvCommissionTerm() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionTerm)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionTerm.Table_Name)
			.getPO(getC_AdvCommissionTerm_ID(), get_TrxName());	}

	/** Set Provisionsart.
		@param C_AdvCommissionTerm_ID Provisionsart	  */
	public void setC_AdvCommissionTerm_ID (int C_AdvCommissionTerm_ID)
	{
		if (C_AdvCommissionTerm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionTerm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionTerm_ID, Integer.valueOf(C_AdvCommissionTerm_ID));
	}

	/** Get Provisionsart.
		@return Provisionsart	  */
	public int getC_AdvCommissionTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Belegart.
		@param C_DocType_ID 
		Belegart oder Verarbeitungsvorgaben
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Belegart oder Verarbeitungsvorgaben
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prozent Berechnung.
		@param commission_ca Prozent Berechnung	  */
	public void setcommission_ca (BigDecimal commission_ca)
	{
		set_ValueNoCheck (COLUMNNAME_commission_ca, commission_ca);
	}

	/** Get Prozent Berechnung.
		@return Prozent Berechnung	  */
	public BigDecimal getcommission_ca () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_commission_ca);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Punkte Berechnung.
		@param CommissionPoints_CA Punkte Berechnung	  */
	public void setCommissionPoints_CA (BigDecimal CommissionPoints_CA)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionPoints_CA, CommissionPoints_CA);
	}

	/** Get Punkte Berechnung.
		@return Punkte Berechnung	  */
	public BigDecimal getCommissionPoints_CA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionPoints_CA);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Punkte Prognose.
		@param CommissionPoints_PR Punkte Prognose	  */
	public void setCommissionPoints_PR (BigDecimal CommissionPoints_PR)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionPoints_PR, CommissionPoints_PR);
	}

	/** Get Punkte Prognose.
		@return Punkte Prognose	  */
	public BigDecimal getCommissionPoints_PR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionPoints_PR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Punkte Summe Berechnung.
		@param CommissionPointsSum_CA Punkte Summe Berechnung	  */
	public void setCommissionPointsSum_CA (BigDecimal CommissionPointsSum_CA)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionPointsSum_CA, CommissionPointsSum_CA);
	}

	/** Get Punkte Summe Berechnung.
		@return Punkte Summe Berechnung	  */
	public BigDecimal getCommissionPointsSum_CA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionPointsSum_CA);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Punkte Summe Prognose.
		@param CommissionPointsSum_PR Punkte Summe Prognose	  */
	public void setCommissionPointsSum_PR (BigDecimal CommissionPointsSum_PR)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionPointsSum_PR, CommissionPointsSum_PR);
	}

	/** Get Punkte Summe Prognose.
		@return Punkte Summe Prognose	  */
	public BigDecimal getCommissionPointsSum_PR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionPointsSum_PR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prozent Prognose.
		@param commission_pr Prozent Prognose	  */
	public void setcommission_pr (BigDecimal commission_pr)
	{
		set_ValueNoCheck (COLUMNNAME_commission_pr, commission_pr);
	}

	/** Get Prozent Prognose.
		@return Prozent Prognose	  */
	public BigDecimal getcommission_pr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_commission_pr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_Sponsor)MTable.get(getCtx(), de.metas.commission.model.I_C_Sponsor.Table_Name)
			.getPO(getC_Sponsor_ID(), get_TrxName());	}

	/** Set Sponsor.
		@param C_Sponsor_ID Sponsor	  */
	public void setC_Sponsor_ID (int C_Sponsor_ID)
	{
		if (C_Sponsor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_ID, Integer.valueOf(C_Sponsor_ID));
	}

	/** Get Sponsor.
		@return Sponsor	  */
	public int getC_Sponsor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Sponsor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Belegdatum.
		@param DateDoc 
		Datum des Belegs
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_ValueNoCheck (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Belegdatum.
		@return Datum des Belegs
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Kunde.
		@param kundenname Kunde	  */
	public void setkundenname (String kundenname)
	{
		set_ValueNoCheck (COLUMNNAME_kundenname, kundenname);
	}

	/** Get Kunde.
		@return Kunde	  */
	public String getkundenname () 
	{
		return (String)get_Value(COLUMNNAME_kundenname);
	}

	/** Set Kundennr..
		@param kundennr Kundennr.	  */
	public void setkundennr (String kundennr)
	{
		set_ValueNoCheck (COLUMNNAME_kundennr, kundennr);
	}

	/** Get Kundennr..
		@return Kundennr.	  */
	public String getkundennr () 
	{
		return (String)get_Value(COLUMNNAME_kundennr);
	}

	/** Set Ebene Berechnung.
		@param LevelCalculation Ebene Berechnung	  */
	public void setLevelCalculation (int LevelCalculation)
	{
		set_ValueNoCheck (COLUMNNAME_LevelCalculation, Integer.valueOf(LevelCalculation));
	}

	/** Get Ebene Berechnung.
		@return Ebene Berechnung	  */
	public int getLevelCalculation () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LevelCalculation);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ebene Prognose.
		@param LevelForecast Ebene Prognose	  */
	public void setLevelForecast (int LevelForecast)
	{
		set_ValueNoCheck (COLUMNNAME_LevelForecast, Integer.valueOf(LevelForecast));
	}

	/** Get Ebene Prognose.
		@return Ebene Prognose	  */
	public int getLevelForecast () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LevelForecast);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ebene Hierarchie.
		@param LevelHierarchy Ebene Hierarchie	  */
	public void setLevelHierarchy (int LevelHierarchy)
	{
		set_ValueNoCheck (COLUMNNAME_LevelHierarchy, Integer.valueOf(LevelHierarchy));
	}

	/** Get Ebene Hierarchie.
		@return Ebene Hierarchie	  */
	public int getLevelHierarchy () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LevelHierarchy);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zeile Nr..
		@param Line 
		Einzelne Zeile in dem Dokument
	  */
	public void setLine (BigDecimal Line)
	{
		set_ValueNoCheck (COLUMNNAME_Line, Line);
	}

	/** Get Zeile Nr..
		@return Einzelne Zeile in dem Dokument
	  */
	public BigDecimal getLine () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Line);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set month.
		@param month month	  */
	public void setmonth (BigDecimal month)
	{
		set_ValueNoCheck (COLUMNNAME_month, month);
	}

	/** Get month.
		@return month	  */
	public BigDecimal getmonth () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_month);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Produkt.
		@param Product Produkt	  */
	public void setProduct (String Product)
	{
		set_ValueNoCheck (COLUMNNAME_Product, Product);
	}

	/** Get Produkt.
		@return Produkt	  */
	public String getProduct () 
	{
		return (String)get_Value(COLUMNNAME_Product);
	}

	/** Set Provisionsart.
		@param provisionsart Provisionsart	  */
	public void setprovisionsart (String provisionsart)
	{
		set_ValueNoCheck (COLUMNNAME_provisionsart, provisionsart);
	}

	/** Get Provisionsart.
		@return Provisionsart	  */
	public String getprovisionsart () 
	{
		return (String)get_Value(COLUMNNAME_provisionsart);
	}

	/** Set Menge.
		@param Qty 
		Menge
	  */
	public void setQty (BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set round.
		@param round round	  */
	public void setround (BigDecimal round)
	{
		set_ValueNoCheck (COLUMNNAME_round, round);
	}

	/** Get round.
		@return round	  */
	public BigDecimal getround () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_round);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sponsor Nummer.
		@param sponsornr Sponsor Nummer	  */
	public void setsponsornr (String sponsornr)
	{
		set_ValueNoCheck (COLUMNNAME_sponsornr, sponsornr);
	}

	/** Get Sponsor Nummer.
		@return Sponsor Nummer	  */
	public String getsponsornr () 
	{
		return (String)get_Value(COLUMNNAME_sponsornr);
	}

	/** Set Stadt.
		@param stadt Stadt	  */
	public void setstadt (String stadt)
	{
		set_ValueNoCheck (COLUMNNAME_stadt, stadt);
	}

	/** Get Stadt.
		@return Stadt	  */
	public String getstadt () 
	{
		return (String)get_Value(COLUMNNAME_stadt);
	}

	/** Set Vorgang.
		@param vorgang Vorgang	  */
	public void setvorgang (String vorgang)
	{
		set_ValueNoCheck (COLUMNNAME_vorgang, vorgang);
	}

	/** Get Vorgang.
		@return Vorgang	  */
	public String getvorgang () 
	{
		return (String)get_Value(COLUMNNAME_vorgang);
	}

	/** Set year.
		@param year year	  */
	public void setyear (BigDecimal year)
	{
		set_ValueNoCheck (COLUMNNAME_year, year);
	}

	/** Get year.
		@return year	  */
	public BigDecimal getyear () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_year);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}