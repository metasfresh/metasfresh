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

/** Generated Model for C_AdvCommissionInstance
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvCommissionInstance extends PO implements I_C_AdvCommissionInstance, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvCommissionInstance (Properties ctx, int C_AdvCommissionInstance_ID, String trxName)
    {
      super (ctx, C_AdvCommissionInstance_ID, trxName);
      /** if (C_AdvCommissionInstance_ID == 0)
        {
			setAD_Table_ID (0);
			setC_AdvCommissionInstance_ID (0);
			setC_AdvCommissionTerm_ID (0);
			setC_AdvComSystem_Type_ID (0);
			setC_Sponsor_Customer_ID (0);
			setC_Sponsor_SalesRep_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
			setIsClosed (false);
// N
			setIsCommissionLock (false);
// N
			setIsVolumeOfSales (false);
// N
			setName (null);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_AdvCommissionInstance (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_C_AdvCommissionInstance[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_PInstance getAD_PInstance() throws RuntimeException
    {
		return (org.compiere.model.I_AD_PInstance)MTable.get(getCtx(), org.compiere.model.I_AD_PInstance.Table_Name)
			.getPO(getAD_PInstance_ID(), get_TrxName());	}

	/** Set Prozess-Instanz.
		@param AD_PInstance_ID 
		Instanz eines Prozesses
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
	}

	/** Get Prozess-Instanz.
		@return Instanz eines Prozesses
	  */
	public int getAD_PInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Betrag auszuzahlen.
		@param AmtToPay Betrag auszuzahlen	  */
	public void setAmtToPay (BigDecimal AmtToPay)
	{
		throw new IllegalArgumentException ("AmtToPay is virtual column");	}

	/** Get Betrag auszuzahlen.
		@return Betrag auszuzahlen	  */
	public BigDecimal getAmtToPay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtToPay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

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

	public de.metas.commission.model.I_C_AdvCommissionRelevantPO getC_AdvCommissionRelevantPO() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionRelevantPO)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionRelevantPO.Table_Name)
			.getPO(getC_AdvCommissionRelevantPO_ID(), get_TrxName());	}

	/** Set Def. relevanter Datensatz.
		@param C_AdvCommissionRelevantPO_ID Def. relevanter Datensatz	  */
	public void setC_AdvCommissionRelevantPO_ID (int C_AdvCommissionRelevantPO_ID)
	{
		if (C_AdvCommissionRelevantPO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionRelevantPO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionRelevantPO_ID, Integer.valueOf(C_AdvCommissionRelevantPO_ID));
	}

	/** Get Def. relevanter Datensatz.
		@return Def. relevanter Datensatz	  */
	public int getC_AdvCommissionRelevantPO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionRelevantPO_ID);
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

	public de.metas.commission.model.I_C_AdvComSystem_Type getC_AdvComSystem_Type() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvComSystem_Type)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvComSystem_Type.Table_Name)
			.getPO(getC_AdvComSystem_Type_ID(), get_TrxName());	}

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

	public de.metas.commission.model.I_C_IncidentLine getC_IncidentLine() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_IncidentLine)MTable.get(getCtx(), de.metas.commission.model.I_C_IncidentLine.Table_Name)
			.getPO(getC_IncidentLine_ID(), get_TrxName());	}

	/** Set C_IncidentLine_ID.
		@param C_IncidentLine_ID C_IncidentLine_ID	  */
	public void setC_IncidentLine_ID (int C_IncidentLine_ID)
	{
		if (C_IncidentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_IncidentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_IncidentLine_ID, Integer.valueOf(C_IncidentLine_ID));
	}

	/** Get C_IncidentLine_ID.
		@return C_IncidentLine_ID	  */
	public int getC_IncidentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_IncidentLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor_Customer() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_Sponsor)MTable.get(getCtx(), de.metas.commission.model.I_C_Sponsor.Table_Name)
			.getPO(getC_Sponsor_Customer_ID(), get_TrxName());	}

	/** Set Sponsor-Kunde.
		@param C_Sponsor_Customer_ID Sponsor-Kunde	  */
	public void setC_Sponsor_Customer_ID (int C_Sponsor_Customer_ID)
	{
		if (C_Sponsor_Customer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_Customer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_Customer_ID, Integer.valueOf(C_Sponsor_Customer_ID));
	}

	/** Get Sponsor-Kunde.
		@return Sponsor-Kunde	  */
	public int getC_Sponsor_Customer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Sponsor_Customer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor_SalesRep() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_Sponsor)MTable.get(getCtx(), de.metas.commission.model.I_C_Sponsor.Table_Name)
			.getPO(getC_Sponsor_SalesRep_ID(), get_TrxName());	}

	/** Set Sponsor-Vertriebspartner.
		@param C_Sponsor_SalesRep_ID Sponsor-Vertriebspartner	  */
	public void setC_Sponsor_SalesRep_ID (int C_Sponsor_SalesRep_ID)
	{
		if (C_Sponsor_SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_SalesRep_ID, Integer.valueOf(C_Sponsor_SalesRep_ID));
	}

	/** Get Sponsor-Vertriebspartner.
		@return Sponsor-Vertriebspartner	  */
	public int getC_Sponsor_SalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Sponsor_SalesRep_ID);
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

	/** Set Geschlossen.
		@param IsClosed 
		The status is closed
	  */
	public void setIsClosed (boolean IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, Boolean.valueOf(IsClosed));
	}

	/** Get Geschlossen.
		@return The status is closed
	  */
	public boolean isClosed () 
	{
		Object oo = get_Value(COLUMNNAME_IsClosed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Provisionssperre.
		@param IsCommissionLock Provisionssperre	  */
	public void setIsCommissionLock (boolean IsCommissionLock)
	{
		set_Value (COLUMNNAME_IsCommissionLock, Boolean.valueOf(IsCommissionLock));
	}

	/** Get Provisionssperre.
		@return Provisionssperre	  */
	public boolean isCommissionLock () 
	{
		Object oo = get_Value(COLUMNNAME_IsCommissionLock);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verkaufsvolumen.
		@param IsVolumeOfSales 
		Gibt an, ob der Datensatz bei der Ermittlung von Rang, Kompression usw. als Verkaufsvolumen zu berücksichtigen ist
	  */
	public void setIsVolumeOfSales (boolean IsVolumeOfSales)
	{
		set_ValueNoCheck (COLUMNNAME_IsVolumeOfSales, Boolean.valueOf(IsVolumeOfSales));
	}

	/** Get Verkaufsvolumen.
		@return Gibt an, ob der Datensatz bei der Ermittlung von Rang, Kompression usw. als Verkaufsvolumen zu berücksichtigen ist
	  */
	public boolean isVolumeOfSales () 
	{
		Object oo = get_Value(COLUMNNAME_IsVolumeOfSales);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Ebene Berechnung.
		@param LevelCalculation Ebene Berechnung	  */
	public void setLevelCalculation (int LevelCalculation)
	{
		set_Value (COLUMNNAME_LevelCalculation, Integer.valueOf(LevelCalculation));
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
		set_Value (COLUMNNAME_LevelForecast, Integer.valueOf(LevelForecast));
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
		set_Value (COLUMNNAME_LevelHierarchy, Integer.valueOf(LevelHierarchy));
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

	/** Set Punkte-Berechnet.
		@param Points_Calculated Punkte-Berechnet	  */
	public void setPoints_Calculated (BigDecimal Points_Calculated)
	{
		throw new IllegalArgumentException ("Points_Calculated is virtual column");	}

	/** Get Punkte-Berechnet.
		@return Punkte-Berechnet	  */
	public BigDecimal getPoints_Calculated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Points_Calculated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Punkte-Prognostiziert.
		@param Points_Predicted Punkte-Prognostiziert	  */
	public void setPoints_Predicted (BigDecimal Points_Predicted)
	{
		throw new IllegalArgumentException ("Points_Predicted is virtual column");	}

	/** Get Punkte-Prognostiziert.
		@return Punkte-Prognostiziert	  */
	public BigDecimal getPoints_Predicted () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Points_Predicted);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Punkte-Summe-Prognostiziert.
		@param PointsSum_Predicted Punkte-Summe-Prognostiziert	  */
	public void setPointsSum_Predicted (BigDecimal PointsSum_Predicted)
	{
		throw new IllegalArgumentException ("PointsSum_Predicted is virtual column");	}

	/** Get Punkte-Summe-Prognostiziert.
		@return Punkte-Summe-Prognostiziert	  */
	public BigDecimal getPointsSum_Predicted () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_Predicted);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Punkte-Summe-Prov.-Relevant.
		@param PointsSum_ToCalculate Punkte-Summe-Prov.-Relevant	  */
	public void setPointsSum_ToCalculate (BigDecimal PointsSum_ToCalculate)
	{
		throw new IllegalArgumentException ("PointsSum_ToCalculate is virtual column");	}

	/** Get Punkte-Summe-Prov.-Relevant.
		@return Punkte-Summe-Prov.-Relevant	  */
	public BigDecimal getPointsSum_ToCalculate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_ToCalculate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Punkte-zu berechnen.
		@param Points_ToCalculate Punkte-zu berechnen	  */
	public void setPoints_ToCalculate (BigDecimal Points_ToCalculate)
	{
		throw new IllegalArgumentException ("Points_ToCalculate is virtual column");	}

	/** Get Punkte-zu berechnen.
		@return Punkte-zu berechnen	  */
	public BigDecimal getPoints_ToCalculate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Points_ToCalculate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}